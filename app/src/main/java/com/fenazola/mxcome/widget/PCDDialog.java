package com.fenazola.mxcome.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fenazola.mxcome.db.BaseDao;
import com.fenazola.mxcome.db.TableArea;
import com.zss.library.utils.DPUtils;
import com.zss.library.wheelview.OnWheelChangedListener;
import com.zss.library.wheelview.WheelView;
import com.zss.library.wheelview.adapter.BaseWheelAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 省市区
 *
 * @author zm
 */
public class PCDDialog extends Dialog {

    private List<TableArea> provList;
    private List<TableArea> cityList;
    private List<TableArea> distList;
    private OnSelectedListener listener;
    private LinearLayout bottomLayout;
    private List<WheelView> mWheelView;
    private List<BaseWheelAdapter> mAdapter;
    private Button leftBtn;
    private Button rightBtn;

    public PCDDialog(Context context, List<TableArea> provList, List<TableArea> cityList, List<TableArea> distList) {
        super(context, com.zss.library.R.style.CommonDialog);
        setContentView(com.zss.library.R.layout.common_wheel_dialog);
        setCanceledOnTouchOutside(true);
        this.provList = provList;
        this.cityList = cityList;
        this.distList = distList;
        int width = android.view.WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setLayout(width, width);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
        initView();
    }

    private void initView() {
        mWheelView = new ArrayList<WheelView>();
        mAdapter = new ArrayList<BaseWheelAdapter>();
        bottomLayout = (LinearLayout) findViewById(com.zss.library.R.id.common_ll_bottom);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        for (int i = 0; i < 3; i++) {
            List<TableArea> items = new ArrayList<>();
            if (i == 0) {
                items.addAll(provList);
            } else if (i == 1) {
                items.addAll(cityList);
            } else {
                items.addAll(distList);
            }
            BaseWheelAdapter adapter = new BaseWheelAdapter<TableArea>(getContext(), items) {
                @Override
                public String getItemText(TableArea item) {
                    return item.getArea();
                }
            };
            adapter.setItemResource(com.zss.library.R.layout.common_item_wheel);
            adapter.setItemTextResource(com.zss.library.R.id.text);
            adapter.setEmptyItemResource(com.zss.library.R.layout.common_item_wheel);
            WheelView wheelView = new WheelView(getContext());
            wheelView.setTag(i);
            wheelView.setViewAdapter(adapter);
            wheelView.setCurrentItem(0, false);
            wheelView.addChangingListener(new OnWheelChangedListener() {
                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    String tag = wheel.getTag().toString();
                    int wheelItem = Integer.parseInt(tag);
                    if(listener != null){
                        listener.onSelected(wheelItem, newValue);
                    }
                }
            });
            bottomLayout.addView(wheelView, params);
            mWheelView.add(wheelView);
            mAdapter.add(adapter);
        }
        leftBtn = (Button) findViewById(com.zss.library.R.id.common_btn_left);
        leftBtn.setOnClickListener(mDefaultDismiss);

        rightBtn = (Button) findViewById(com.zss.library.R.id.common_btn_right);
        rightBtn.setOnClickListener(mDefaultDismiss);
    }

    public void replace(List<TableArea> cityList, List<TableArea> distList){
        if(cityList != null && cityList.size()>0){
            this.cityList.clear();
            this.cityList.addAll(cityList);
            mAdapter.get(1).replace(this.cityList);
            mWheelView.get(1).setCurrentItem(0, false);
        }
        if(distList != null && distList.size()>0){
            this.distList.clear();
            this.distList.addAll(distList);
            mAdapter.get(2).replace(this.distList);
            mWheelView.get(2).setCurrentItem(0, false);
        }
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.listener = listener;
    }

    public List<Integer> getCurrentItems() {
        List<Integer> curentItems = new ArrayList<>();
        for (int i = 0; i < mWheelView.size(); i++) {
            Integer item = mWheelView.get(i).getCurrentItem();
            curentItems.add(item);
        }
        return curentItems;
    }

    public List<TableArea> getCurrentValues() {
        List<TableArea> curentValues = new ArrayList<>();
        for (int i = 0; i < mWheelView.size(); i++) {
            int currentItem = mWheelView.get(i).getCurrentItem();
            TableArea entry;
            if (i == 0) {
                entry = provList.get(currentItem);
            } else if (i == 1) {
                entry = cityList.get(currentItem);
            } else {
                entry = distList.get(currentItem);
            }
            curentValues.add(entry);
        }
        return curentValues;
    }

    public void setCurrentItems(List<Integer> curentItems) {
        for (int i = 0; i < mWheelView.size(); i++) {
            int curentItem = curentItems.get(i);
            mWheelView.get(i).setCurrentItem(curentItem, false);
        }
    }

    public PCDDialog setOnLeftListener(
            final View.OnClickListener listener) {
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dismiss();
            }
        });
        return this;
    }

    public PCDDialog setOnRightListener(final View.OnClickListener listener) {
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(v);
                }
                dismiss();
            }
        });
        return this;
    }

    private View.OnClickListener mDefaultDismiss = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dismiss();
        }
    };

    /**
     * 显示对话框
     */
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
        }
    }

    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
        }
    }

    public interface OnSelectedListener {
        public void onSelected(int wheelItem, int position);
    }

}