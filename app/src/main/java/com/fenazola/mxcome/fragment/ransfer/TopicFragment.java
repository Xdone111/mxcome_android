package com.fenazola.mxcome.fragment.ransfer;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.TopicEntry;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.fragment.BaseFragment;
import com.zss.library.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 话题
 */
public class TopicFragment extends BaseFragment {

    private GridView gridView;
    private CommonAdapter<TopicEntry> adapter;
    private List<TopicEntry> datas;
    private int [] mBgs = new int[] { R.color.topic_bg_color1, R.color.topic_bg_color2, R.color.topic_bg_color3, R.color.topic_bg_color4, R.color.topic_bg_color5, R.color.topic_bg_color6  };

    private int [] mTexts = new int[] { R.color.topic_text_color1, R.color.topic_text_color2, R.color.topic_text_color3, R.color.topic_text_color4, R.color.topic_text_color5, R.color.topic_text_color6  };

    private int [] mImgBgs = new int[] { R.mipmap.topic_bg1, R.mipmap.topic_bg2, R.mipmap.topic_bg3, R.mipmap.topic_bg4, R.mipmap.topic_bg5, R.mipmap.topic_bg6  };

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_grid;
    }

    @Override
    public void initView() {
        super.initView();
        gridView = (GridView)findViewById(R.id.gridView);
        gridView.setNumColumns(3);
        adapter = new CommonAdapter<TopicEntry>(getActivity(), R.layout.griditem_topic) {
            @Override
            protected void convert(ViewHolder viewHolder, TopicEntry item, int position) {
                int color = getColor(mTexts[position%6]);
                View view = viewHolder.findViewById(R.id.ll_top);
                view.setBackgroundResource(mBgs[position%6]);
                TextView title = (TextView)viewHolder.findViewById(R.id.title);
                title.setText(item.getTitle());
                title.setTextColor(color);

                TextView summary = (TextView)viewHolder.findViewById(R.id.summary);
                summary.setText(item.getSummary());
                summary.setTextColor(color);

                ImageView iconBg = (ImageView)viewHolder.findViewById(R.id.icon_bg);
                iconBg.setImageResource(mImgBgs[position%6]);

                String content = item.getCnt1()+"讨论";
                SpannableStringBuilder mBuilder = StringUtils.parse(content, 0, content.length() - 2, color);
                TextView cnt1 = (TextView)viewHolder.findViewById(R.id.cnt1);
                cnt1.setText(mBuilder);

                content = item.getCnt1()+"篇";
                mBuilder = StringUtils.parse(content, 0, content.length() - 1, color);
                TextView cnt2 = (TextView)viewHolder.findViewById(R.id.cnt2);
                cnt2.setText(mBuilder);
            }
        };
        gridView.setAdapter(adapter);
    }

    @Override
    public void initData(Bundle savedInstanceState)  {
        super.initData(savedInstanceState);
        datas = new ArrayList<>();
        for(int i=0; i<10; i++){
            TopicEntry item = new TopicEntry();
            item.setTitle("设计之魂");
            item.setSummary("设计专区"+i);
            item.setCnt1("1088");
            item.setCnt2("1988");
            datas.add(item);
        }
        adapter.addAll(datas);
    }

}
