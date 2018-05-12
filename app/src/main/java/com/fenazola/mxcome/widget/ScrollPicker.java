package com.fenazola.mxcome.widget;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.fenazola.mxcome.R;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.animator.Techniques;
import com.zss.library.animator.YoYo;
import com.zss.library.utils.DPUtils;

/**
 * 可以滚动Picker
 * @author zm
 *
 */
public class ScrollPicker<T> extends LinearLayout implements OnScrollListener {

    private ListView mListView;
    private ImageView schedu, talk;
    private View pickerDv;
    private int mRowHeightDp = 130;
    private int mRowHeightPx = 0;
    private int mFirstShowPosition = 0;
    private int mAdjustSpace;
    private static final int MSG_ADJUST = 0;
    private static final int SPACE = 3;
    private CommonAdapter<T> mAdapter;
    private OnItemSelectedListener mItemListener;

    public ScrollPicker(Context context) {
        super(context);
        initView();
    }

    public ScrollPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mRowHeightPx = DPUtils.dip2px(getContext(), mRowHeightDp);
        inflate(getContext(), R.layout.scroll_picker, this);
        schedu = (ImageView) findViewById(R.id.schedu);
        talk = (ImageView) findViewById(R.id.talk);
        pickerDv = findViewById(R.id.picker_dv);
        pickerDv.setVisibility(INVISIBLE);
        mListView = (ListView) findViewById(R.id.picket_list);
        mListView.setOnScrollListener(this);
        schedu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemListener != null){
                    mItemListener.onItemSelectedSchedu(ScrollPicker.this, getSelectedPosition());
                }
            }
        });
        talk.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemListener != null){
                    mItemListener.onItemSelectedTalk(ScrollPicker.this, getSelectedPosition());
                }
            }
        });
    }

    public void setAdapter(CommonAdapter mAdapter) {
        this.mAdapter = mAdapter;
        if (mAdapter.getData() != null) {
            mListView.setAdapter(mAdapter);
            mListView.setSelection(mFirstShowPosition);
            mAdapter.notifyDataSetChanged();
        }
    }

    public int getSelectedPosition() {
        if (mAdapter.getData() != null && mAdapter.getData().size() > 0) {
            return mFirstShowPosition + 1;
        } else {
            return -1;
        }
    }
    
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_ADJUST:
                adjustScroll();
                break;
            }
        }
    };

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
        mListView.setOnItemClickListener(listener);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mItemListener = listener;
    }

    private void adjustScroll() {
        int space = 0;
        if (mAdjustSpace > 0) {
            if (mAdjustSpace >= SPACE) {
                space = SPACE;
            } else {
                space = 1;
            }
        } else if (mAdjustSpace < 0) {
            if (mAdjustSpace <= -SPACE) {
                space = -SPACE;
            } else {
                space = -1;
            }
        }
        if (space != 0) {
            mListView.scrollBy(0, space);
            if (Math.abs(mAdjustSpace) <= Math.abs(space)) {
                mListView.scrollTo(0, 0);
                mListView.setSelection(mFirstShowPosition);
            } else {
                mAdjustSpace -= space;
                mHandler.sendEmptyMessageDelayed(MSG_ADJUST, 5);
            }
            if(!pickerDv.isShown()){
                pickerDv.setVisibility(VISIBLE);
            }

        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        boolean bl = getListView().canScrollVertically(1);
        if(bl){
            mFirstShowPosition = firstVisibleItem;
            if (mAdjustSpace != 0) {
                mAdjustSpace = 0;
                mHandler.removeMessages(MSG_ADJUST);
            }
        }else{
            pickerDv.setVisibility(INVISIBLE);
        }

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            boolean bl = getListView().canScrollVertically(1);
            if(bl){
                if (mAdjustSpace == 0) {
                    computeSelectIdx();
                    if (mAdjustSpace != 0) {
                        adjustScroll();
                    }
                }
            }
        }
    }

    private void computeSelectIdx() {
        if (mListView.getChildCount() > 0) {
            int vTop = mListView.getChildAt(0).getTop();
            if (vTop < 0) {
                if (-vTop > mRowHeightPx / 2) {//向上滚动调整
                    mAdjustSpace = mRowHeightPx + vTop;
                    mFirstShowPosition = mListView.getFirstVisiblePosition() + 1;
                } else {//向下滚动调整
                    mAdjustSpace = vTop;
                    mFirstShowPosition = mListView.getFirstVisiblePosition();
                }
            } else {
                mFirstShowPosition = mListView.getFirstVisiblePosition();
            }
        }
    }

    public interface OnItemSelectedListener {

        public void onItemSelectedSchedu(ScrollPicker picker, int position);

        public void onItemSelectedTalk(ScrollPicker picker, int position);
    }

    public ListView getListView() {
        return mListView;
    }
}
