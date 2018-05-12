package com.fenazola.mxcome.fragment.main.demand;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.db.BaseDaoImpl;
import com.fenazola.mxcome.db.TableArea;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.widget.PCDDialog;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.toolbar.CommonToolbar;
import com.zss.library.utils.StringUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/6/19.
 * 免费设计
 */

public class FreeDesignerFragment extends BaseFragment implements View.OnClickListener {
    /**
     * 活动 名师免费设计
     */
    public static final int INPT_ACT_TYPE = 1;
    /**
     * 活动 高级免费设计
     */
    public static final int INPT_HIGH_ACT_TYPE = 2;
    private TextView tvTitle;
    private ImageView ivImage;
    private ImageView ivLeft;
    private EditText edit1;
    private EditText edit2;
    private EditText edit3;
    private TextView tvNumber;
    private ImageView iv_corner;
    private TextView tvCntent;
    private boolean flag = false;
    private TextView tvDesign;
    private ImageView pullIcon;
    private List<TableArea> provList;
    private List<TableArea> cityList;
    private List<TableArea> distList;
    private TableArea currentCountry, currentProv, currentCity, currentDist;
    private PCDDialog dialog;
    private List<TableArea> countryList;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_designer_coupon;
    }

    @Override
    public void initView() {
        super.initView();
        tvTitle = (TextView) findViewById(R.id.show_title_tv);
        ivImage = (ImageView) findViewById(R.id.show_title_right);
        ivLeft = (ImageView) findViewById(R.id.show_title_left);
        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);
        edit3 = (EditText) findViewById(R.id.edit3);
        tvNumber = (TextView) findViewById(R.id.tv_number);
        iv_corner = (ImageView) findViewById(R.id.iv_corner);
        tvCntent = (TextView) findViewById(R.id.tv_content);
        tvDesign = (TextView) findViewById(R.id.tvDesign);
        pullIcon = (ImageView) findViewById(R.id.pull_icon);

        tvTitle.setText("设计劵申请");
        tvTitle.setTextColor(getColor(R.color.free_designer_color));
        ivImage.setImageResource(R.mipmap.share_grey);
        ivLeft.setImageResource(R.mipmap.free_designer_back);
        pullIcon.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Bundle args = getArguments();
        if (args != null && args.containsKey(Constant.key1)) {
            int actType = args.getInt(Constant.key1);

            if (actType == INPT_ACT_TYPE) {
                tvDesign.setText("免费名师设计");
            } else {

            }
        }

        ivLeft.setOnClickListener(this);
        iv_corner.setOnClickListener(this);

        String str = getString(R.string.mxcome_clause, "《MXCOME装修常见问题条款》");
        int color = getColor(R.color.colorYellow);
        tvCntent.setText(StringUtils.parse(str, 7, str.length(), color));


        countryList = queryData(0, 0);
        currentCountry = countryList.get(0);
        //country.setText(currentCountry.getArea());
        provList = queryData(currentCountry.getAreaid(), 1);
        currentProv = provList.get(0);
        cityList = queryData(currentProv.getAreaid(), 2);
        currentCity = cityList.get(0);
        distList = queryData(currentCity.getAreaid(), 3);
        currentDist = distList.get(0);
    }

    @Override
    public void setTopBar() {
        super.setTopBar();
        CommonToolbar toolbar = getToolbar();
        toolbar.setVisibility(View.GONE);
    }

    public void showPCDDialog() {
        dialog = new PCDDialog(getActivity(), provList, cityList, distList);
        dialog.setOnSelectedListener(new PCDDialog.OnSelectedListener() {
            @Override
            public void onSelected(int wheelItem, int position) {
                if (wheelItem == 0) {
                    currentProv = provList.get(position);
                    cityList = queryData(currentProv.getAreaid(), 2);
                    if (cityList != null && cityList.size() == 0) {
                        cityList.clear();
                        cityList.add(currentProv);
                        distList.clear();
                        distList.add(currentProv);
                    } else {
                        currentCity = cityList.get(0);
                        distList = queryData(currentCity.getAreaid(), 3);
                    }
                    dialog.replace(cityList, distList);
                } else if (wheelItem == 1) {
                    currentCity = cityList.get(position);
                    distList = queryData(currentCity.getAreaid(), 3);
                    distList.add(currentCity);
                    dialog.replace(null, distList);

                } else if (wheelItem == 2) {
                    currentDist = distList.get(position);
                }
            }
        });
        dialog.setOnRightListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit3.setText(currentProv.getArea() + "-" + currentCity.getArea() + "-" + currentDist.getArea());
            }
        });
        dialog.show();
    }

    public List<TableArea> queryData(Object parentid, Object areatype) {
        BaseDaoImpl dao = new BaseDaoImpl(TableArea.class);
        try {
            String[] columnNames = new String[]{"parentid", "areatype"};
            Object[] columnValues = new Object[]{parentid, areatype};
            return dao.query(columnNames, columnValues);
        } catch (SQLException e) {
            return new ArrayList<TableArea>();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_title_left:
                getActivity().onBackPressed();
                break;
            case R.id.iv_corner:
                if (flag) {
                    iv_corner.setImageResource(R.mipmap.free_selected);
                } else {
                    iv_corner.setImageResource(R.mipmap.free_unselect);
                }
                flag = !flag;
                break;
            case R.id.pull_icon:
                showPCDDialog();
                break;
        }
    }
}
