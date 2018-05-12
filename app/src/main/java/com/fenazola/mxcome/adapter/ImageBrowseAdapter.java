package com.fenazola.mxcome.adapter;

/**
 * Created by Administrator on 2017/6/6.
 */

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.ransfer.InformationEntry;

import java.util.List;

/**
 * 杂志Viewpager适配器
 */
public class ImageBrowseAdapter extends PagerAdapter {

    private Context context;
    /**接受欲显示的杂志实体列表*/
    private List<InformationEntry> informationEntrys;

    public ImageBrowseAdapter(Context context, List<InformationEntry> informationEntrys) {
        this.context = context;
        this.informationEntrys = informationEntrys;
    }

    @Override
    public int getCount() {
        return informationEntrys.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        //将ImageView添加进Viewpager容器
        ImageView imageView = new ImageView(context);
        //设置图片的显示方式
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context).load(informationEntrys.get(position).getImgUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.me_security_logo).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 跳转呀 but 跳转去哪里 ？？
                Toast.makeText(context,"你点我干嘛呀："+ informationEntrys.get(position).getTitleName(),Toast.LENGTH_SHORT).show();
            }
        });
        view.addView(imageView);
        return imageView;
    }
}
