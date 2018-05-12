package com.fenazola.mxcome.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PicturePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<String> urls;
    private OnImageClickListener listener;

    public PicturePagerAdapter(Context mContext, OnImageClickListener listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        if (urls == null) {
            return 0;
        }
        return urls.size();
    }

    @Override
    public Object instantiateItem(final ViewGroup view, final int position) {
        final ImageView image = new ImageView(mContext);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(mContext).load(urls.get(position)).into(image);
        ((ViewPager) view).addView(image, 0);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.click(image, urls, position);
                }
            }
        });
        return image;
    }

    @Override
    public boolean isViewFromObject(View view, Object arg1) {
        return (view == arg1);
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object arg2) {
        ((ViewPager) view).removeView((View) arg2);
    }

    public void addAll(List<String> urls){
        this.urls = urls;
        notifyDataSetChanged();
    }

    public interface OnImageClickListener{
        void click(View item, List<String> urls, int position);
    }

}