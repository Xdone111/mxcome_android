package com.fenazola.mxcome.widget;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.project.StyleEntry;
import com.fenazola.mxcome.utils.Constant;
import com.zss.library.utils.LogUtils;
import com.zss.library.widget.CommonDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 套餐风格详情
 *
 * @author zm
 */
public class StyleDescLayout extends LinearLayout {

    private ViewPager viewPager;
    private MyPagerAdapter adapter;
    private LinearLayout linear_del;
    private ImageView img_left;
    private ImageView img_right;
    private TextView style_desc;
    private List<StyleEntry> list;

    public StyleDescLayout(Context context) {
        super(context);
        initView();
    }

    public StyleDescLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView() {
        inflate(getContext(), R.layout.layout_meal_style_desc, this);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        linear_del = (LinearLayout) findViewById(R.id.linear_del);
        img_left = (ImageView) findViewById(R.id.img_left);
        img_left.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if(currentItem > 0){
                    currentItem--;
                    viewPager.setCurrentItem(currentItem);
                }
            }
        });
        img_right = (ImageView) findViewById(R.id.img_right);
        img_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                if(currentItem < list.size() - 1){
                    currentItem++;
                    viewPager.setCurrentItem(currentItem);
                }
            }
        });
        style_desc = (TextView) findViewById(R.id.style_desc);
        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                StyleEntry item = list.get(position);
                style_desc.setText(item.getDescinfo());
                if(position == 0){
                    img_left.setVisibility(GONE);
                }else if(position == list.size() - 1){
                    img_right.setVisibility(GONE);
                }else{
                    img_left.setVisibility(VISIBLE);
                    img_right.setVisibility(VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setTitleAndImage(List<StyleEntry> list){
        if(list == null){
            this.list = new ArrayList<>();
        }else{
            this.list = list;
        }
        if(this.list.size() > 0){
            style_desc.setText(this.list.get(0).getDescinfo());
        }else{
            style_desc.setText("");
        }
        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
    }

    public void setOnClickCloseListener(OnClickListener listener){
        linear_del.setOnClickListener(listener);
    }

   class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            if(list != null){
                return list.size();
            }else{
                return 0;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = new ImageView(getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            StyleEntry item = list.get(position);
            Glide.with(getContext()).load(Constant.imageUrl + item.getPic()).into(view);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
