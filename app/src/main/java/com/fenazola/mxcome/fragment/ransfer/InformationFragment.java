package com.fenazola.mxcome.fragment.ransfer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.adapter.ImageBrowseAdapter;
import com.fenazola.mxcome.entry.ransfer.InformationEntry;
import com.zss.library.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 杂志
 * @author xhx
 * TODO 1.记得去xml页面添加上一页 下一页的图标 2.将模拟数据对接为正式数据
 */
public class InformationFragment extends BaseFragment implements View.OnClickListener{
    /**杂志副标题*/
    private TextView subtitleTv;
    /**杂志标题*/
    private TextView titleTv;
    /**TODO 往期 缺原型*/
    private TextView pastTv;
    /**杂志列表适配器*/
    private ViewPager vp;
    /**滚向之前的杂志*/
    private ImageView lastInfoIv;
    /**滚向之后的杂志*/
    private ImageView nextInfoIv;
    /**模拟数据啦*/
    private List<InformationEntry> informationEntrys = new ArrayList<InformationEntry>();
    /**监听ViewPager滑动到了哪个页面 并记录下标 供上一页 下一页的点击事件提供下标 和标识*/
    private int selectIndex=0;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_information;
    }

    @Override
    public void initView() {
        super.initView();
        subtitleTv=(TextView)findViewById(R.id.left_second_title_tv);
        titleTv=(TextView)findViewById(R.id.first_title_tv);
        pastTv=(TextView)findViewById(R.id.past_tv);
        vp=(ViewPager) findViewById(R.id.viewPager);
        lastInfoIv=(ImageView) findViewById(R.id.last_iv);
        nextInfoIv=(ImageView) findViewById(R.id.next_iv);
        pastTv.setOnClickListener(this);
        lastInfoIv.setOnClickListener(this);
        nextInfoIv.setOnClickListener(this);
    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        //TODO 对接网络数据 现在的是模拟数据
        informationEntrys.add(new InformationEntry("标题1","正标题1","http://seopic.699pic.com/photo/00005/5186.jpg_wh1200.jpg"));
        informationEntrys.add(new InformationEntry("标题2","正标题2","http://seopic.699pic.com/photo/50010/0719.jpg_wh1200.jpg"));
        informationEntrys.add(new InformationEntry("标题3","正标题3","http://seopic.699pic.com/photo/50009/9449.jpg_wh1200.jpg"));
        informationEntrys.add(new InformationEntry("标题4","正标题4","http://seopic.699pic.com/photo/50002/5923.jpg_wh1200.jpg"));
        informationEntrys.add(new InformationEntry("标题5","正标题5","http://seopic.699pic.com/photo/50001/9330.jpg_wh1200.jpg"));
        informationEntrys.add(new InformationEntry("标题6","正标题6","http://seopic.699pic.com/photo/50009/9191.jpg_wh1200.jpg"));
        informationEntrys.add(new InformationEntry("标题7","正标题7","http://seopic.699pic.com/photo/00005/5186.jpg_wh1200.jpg"));
        informationEntrys.add(new InformationEntry("标题8","正标题8","http://seopic.699pic.com/photo/50010/0719.jpg_wh1200.jpg"));

        //规避没数据 空指针
        if(informationEntrys ==null|| informationEntrys.size()<1)
            return;

        // 设置左右三列缓存的数目
        vp.setOffscreenPageLimit(3);
        // 添加Adapter
        PagerAdapter adapter = new ImageBrowseAdapter(getActivity(), informationEntrys);
        vp.setAdapter(adapter);
        //初始化标题啦 viewpager的图片啊什么的
        subtitleTv.setText(informationEntrys.get(selectIndex).getSubtitleTitleName());
        titleTv.setText(informationEntrys.get(selectIndex).getTitleName());
        vp.setCurrentItem(selectIndex);
        //监听viewpageer的切换
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                selectIndex=arg0;
                subtitleTv.setText(informationEntrys.get(selectIndex).getSubtitleTitleName());
                titleTv.setText(informationEntrys.get(selectIndex).getTitleName());
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // arg0 :当前页面，及你点击滑动的页面；
                // arg1:当前页面偏移的百分比；
                // arg2:当前页面偏移的像素位置
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                //arg0 ==1的时表示正在滑动，arg0==2的时表示滑动完毕了，arg0==0的时表示什么都没做。

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.past_tv:
                //TODO 缺原型
                Toast.makeText(getActivity(),"还没做哦",Toast.LENGTH_SHORT).show();
                break;
            case R.id.last_iv:
                if(selectIndex==0)
                    return;
                selectIndex-=1;
                vp.setCurrentItem(selectIndex);
                break;
            case R.id.next_iv:
                if(selectIndex== informationEntrys.size()-1)
                    return;
                selectIndex+=1;
                vp.setCurrentItem(selectIndex);
                break;
        }

    }



}
