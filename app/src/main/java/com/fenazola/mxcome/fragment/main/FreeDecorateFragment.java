package com.fenazola.mxcome.fragment.main;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.widget.AutoTextView;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.DPUtils;
import com.zss.library.widget.CommonEditWidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/6/6.
 */

public class FreeDecorateFragment extends BaseFragment {

    private CommonEditWidget nameWidget;
    private CommonEditWidget phoneWidget;
    private List<String> mList;
    private AutoTextView mTextView, mTextView1;
    private Handler mHandler = new Handler();

    private int mTextCount;
    //290
    int width;
    private ImageView image;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_free_decorate;
    }

    @Override
    public void initView() {
        super.initView();
        setBgColor(R.color.white);
        WindowManager wm = getActivity().getWindowManager();
        width = wm.getDefaultDisplay().getWidth();
        nameWidget = (CommonEditWidget) findViewById(R.id.nameWidget);
        phoneWidget = (CommonEditWidget) findViewById(R.id.phoneWidget);
        mTextView = (AutoTextView) findViewById(R.id.show_atv);
        mTextView1 = (AutoTextView) findViewById(R.id.show_atv1);
        image = (ImageView) findViewById(R.id.image);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        changeItemH(image);

        nameWidget.setHint("请教您的尊姓大名");
        phoneWidget.setHint("请输入您的电话号码");
        int dp = DPUtils.dip2px(getContext(), 10);
        nameWidget.setCenterPadding(dp, 0, 0, 0);
        phoneWidget.setCenterPadding(dp, 0, 0, 0);

        mList = new ArrayList<String>();
        //TODO: 2017/6/26
        mList.add("芙蓉区 XXX1 3秒前上传");
        mList.add("开福区 XXX2 4秒前上传");
        mList.add("芙蓉区 XXX3 5秒前上传");
        mList.add("开福区 XXX4 6秒前上传");
        mList.add("芙蓉区 XXX5 7秒前上传");
        mList.add("开福区 XXX6 8秒前上传");
        mList.add("芙蓉区 XXX7 9秒前上传");
        mList.add("开福区 XXX8 10秒前上传");
        mTextView.setText(mList.get(0));
        mTextView1.setText(mList.get(0));
        mTextCount = mList.size();
        mHandler.postDelayed(runnable, 2000);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);
        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("免费装修");
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setTitleColor(getColor(R.color.black));
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
        toolbar.setRightText("活动规则");
        toolbar.setRightTextColor(getColor(R.color.colorGrey));

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mTextView.next();
            mTextCount++;
            if (mTextCount >= Integer.MAX_VALUE) {
                mTextCount = mList.size();
            }
            mTextView.setText(mList.get(mTextCount % (mList.size())));
            mTextView1.setText(mList.get(mTextCount % (mList.size())));
            if (mList.size() > 1) {
                mHandler.postDelayed(this, 2000);
            }
        }
    };

    private void changeItemH(ImageView iv) {
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) iv.getLayoutParams();
        linearParams.height = (int) ((float) width * 0.586);
        iv.setLayoutParams(linearParams);
    }
}
