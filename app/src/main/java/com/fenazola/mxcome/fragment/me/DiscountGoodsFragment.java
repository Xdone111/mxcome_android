package com.fenazola.mxcome.fragment.me;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.widget.RotateTextView;
import com.zss.library.fragment.BaseFragment;

/**
 *
 * 优惠商品列表
 * Created by xuhuixiang on 2017/7/13.
 */

public class DiscountGoodsFragment  extends BaseFragment implements View.OnClickListener{
    /**第一栏标题*/
    private TextView title1;
    /**第一栏副标题*/
    private TextView subTitle1;
    /**第二栏标题*/
    private TextView title2;
    /**第一栏加载鞥多*/
    private TextView title1More;
    /**第二栏加载更多*/
    private TextView title2More;

    /**条目一标题*/
    private TextView itemName1Tv;
    /**条目一价格*/
    private TextView itemPrice1Tv;
    /**条目一价格1*/
    private TextView itemPriceShow1Tv;
    /**条目一销量*/
    private TextView itemCount1Tv;


    /**条目二标题*/
    private TextView itemName2Tv;
    /**条木耳价格*/
    private TextView itemPrice2Tv;
    /**条目二价格1*/
    private TextView itemPriceShow2Tv;
    /**条目二销量*/
    private TextView itemCount2Tv;

    /**条目三标题*/
    private TextView itemName3Tv;
    /**条目三价格*/
    private TextView itemPrice3Tv;
    /**条目三价格1*/
    private TextView itemPriceShow3Tv;
    /**条目三销量*/
    private TextView itemCount3Tv;

    /**条目四标题*/
    private TextView itemName4Tv;
    /**条目四价格*/
    private TextView itemPrice4Tv;
    /**条目四价格1*/
    private TextView itemPriceShow4Tv;
    /**条目4售价*/
    private TextView itemCount4Tv;

    /**条目五标题*/
    private TextView itemName5Tv;
    /**条目五价格*/
    private TextView itemPrice5Tv;
    /**条目五价格1*/
    private TextView itemPriceShow5Tv;
    /**条目五销量*/
    private TextView itemCount5Tv;

    /**条目六标题*/
    private TextView itemName6Tv;
    /**条目六价格*/
    private TextView itemPrice6Tv;
    /**条目六价格1*/
    private TextView itemPriceShow6Tv;
    /**条目六销量*/
    private TextView itemCount6Tv;

    /**图片1*/
    private ImageView itemIv1;
    /**图片2*/
    private ImageView itemIv2;
    /**图片3*/
    private ImageView itemIv3;
    /**图片4*/
    private ImageView itemIv4;
    /**图片5*/
    private ImageView itemIv5;
    /**图片6*/
    private ImageView itemIv6;

    /**角标3*/
    private RotateTextView itemTl3;
    /**角标4*/
    private RotateTextView itemTl4;
    /**角标5*/
    private RotateTextView itemTl5;
    /**角标6*/
    private RotateTextView itemTl6;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_coupon_goods;
    }

    @Override
    public void initView() {
        super.initView();

        title1=(TextView)findViewById(R.id.classify);
        subTitle1=(TextView)findViewById(R.id.classify1);
        title1More=(TextView)findViewById(R.id.more);

        title2=(TextView)findViewById(R.id.classify2);
        title2More=(TextView)findViewById(R.id.more1);

        itemName1Tv=(TextView)findViewById(R.id.pic_tv1);
        itemPrice1Tv=(TextView)findViewById(R.id.title1);
        itemPriceShow1Tv=(TextView)findViewById(R.id.title1_show);
        itemCount1Tv=(TextView)findViewById(R.id.more_1);
        itemIv1=(ImageView)findViewById(R.id.image1);

        itemName2Tv=(TextView)findViewById(R.id.pic_tv2);
        itemPrice2Tv=(TextView)findViewById(R.id.title2);
        itemPriceShow2Tv=(TextView)findViewById(R.id.title2_show);
        itemCount2Tv=(TextView)findViewById(R.id.more_2);
        itemIv2=(ImageView)findViewById(R.id.image2);

        itemName3Tv=(TextView)findViewById(R.id.pic_tv3);
        itemPrice3Tv=(TextView)findViewById(R.id.title3);
        itemPriceShow3Tv=(TextView)findViewById(R.id.title3_show);
        itemCount3Tv=(TextView)findViewById(R.id.more_3);
        itemIv3=(ImageView)findViewById(R.id.image3);
        itemTl3=(RotateTextView)findViewById(R.id.topLeft_tv_3);

        itemName4Tv=(TextView)findViewById(R.id.pic_tv4);
        itemPrice4Tv=(TextView)findViewById(R.id.title4);
        itemPriceShow4Tv=(TextView)findViewById(R.id.title4_show);
        itemCount4Tv=(TextView)findViewById(R.id.more_4);
        itemIv4=(ImageView)findViewById(R.id.image4);
        itemTl4=(RotateTextView)findViewById(R.id.topLeft_tv_4);

        itemName5Tv=(TextView)findViewById(R.id.pic_tv5);
        itemPrice5Tv=(TextView)findViewById(R.id.title5);
        itemPriceShow5Tv=(TextView)findViewById(R.id.title5_show);
        itemCount5Tv=(TextView)findViewById(R.id.more_5);
        itemIv5=(ImageView)findViewById(R.id.image5);
        itemTl5=(RotateTextView)findViewById(R.id.topLeft_tv_5);

        itemName6Tv=(TextView)findViewById(R.id.pic_tv6);
        itemPrice6Tv=(TextView)findViewById(R.id.title6);
        itemPriceShow6Tv=(TextView)findViewById(R.id.title6_show);
        itemCount6Tv=(TextView)findViewById(R.id.more_6);
        itemIv6=(ImageView)findViewById(R.id.image6);
        itemTl6=(RotateTextView)findViewById(R.id.topLeft_tv_6);

        itemPriceShow1Tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        itemPriceShow2Tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        itemPriceShow3Tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        itemPriceShow4Tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        itemPriceShow5Tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        itemPriceShow6Tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰

        itemTl3.setText("5折");
        itemTl4.setText("6折");
        itemTl5.setText("7折");
        itemTl6.setText("8折");

        title1More.setOnClickListener(this);
        title2More.setOnClickListener(this);
        itemIv1.setOnClickListener(this);
        itemIv2.setOnClickListener(this);
        itemIv3.setOnClickListener(this);
        itemIv4.setOnClickListener(this);
        itemIv5.setOnClickListener(this);
        itemIv6.setOnClickListener(this);

        subTitle1.setText(Html.fromHtml("距离结束还有<font color='#4BB7FD'>1</font>时<font color='#4BB7FD'>4</font><font color='#4BB7FD'>8</font>秒"));


    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more:
                break;
            case R.id.more1:
                break;
            case R.id.image1:
                break;
            case R.id.image2:
                break;
            case R.id.image3:
                break;
            case R.id.image4:
                break;
            case R.id.image5:
                break;
            case R.id.image6:
                break;

        }
    }
}
