package com.fenazola.mxcome.fragment.diary;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.widget.CommonWheelDialog;

/**
 * Created by XDONE on 2017/4/1.
 * 新建日记
 */

public class InfoEditFragment extends BaseFragment implements View.OnClickListener {

    private EditText edit1, edit2, edit3;
    private EditText edit4, edit6;
    private ImageView location;
    private ImageView pull1;
    private ImageView pull2;
    private String SelectValue;
    private Button next;
    private TextView text_style;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_diary_info_edite;
    }

    @Override
    public void initView() {
        super.initView();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);
        edit3 = (EditText) findViewById(R.id.edit3);
        edit4 = (EditText) findViewById(R.id.edit4);
        text_style = (TextView) findViewById(R.id.text_style);
        edit6 = (EditText) findViewById(R.id.edit6);
        location = (ImageView) findViewById(R.id.iv_location);
        pull1 = (ImageView) findViewById(R.id.pull_down1);
        pull2 = (ImageView) findViewById(R.id.pull_down2);
        next = (Button) findViewById(R.id.next);

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        pull2.setOnClickListener(this);
        next.setOnClickListener(this);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        getBaseActivity().setStatusBarColor(R.color.white);

        CommonToolbar toolbar = getToolbar();
        toolbar.setTitle("新建日记");
        toolbar.setLeftImage(R.mipmap.icon_grey_back);
        toolbar.setBgColor(getColor(R.color.white));
        toolbar.setTitleColor(getColor(R.color.black));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_location:
                break;
            case R.id.pull_down1:
                break;
            case R.id.pull_down2:
                showWheelDialog(getStringArray(R.array.arrays_style));
                break;
            case R.id.next:
                CreateDiaryFragment fragment = new CreateDiaryFragment();
                addFragment(fragment);

        }
    }

    private void showWheelDialog(String[] arr) {
        CommonWheelDialog dialog = new CommonWheelDialog(getActivity(), arr);
        dialog.setOnSelectedListener(new CommonWheelDialog.OnSelectedListener() {
            @Override
            public void onSelected(int wheelItem, int position, String value) {
                SelectValue = value;
            }
        });
        dialog.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_style.setText(SelectValue);
            }
        });
        dialog.show();
    }
}
