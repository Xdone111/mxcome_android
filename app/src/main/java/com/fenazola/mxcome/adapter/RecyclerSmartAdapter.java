package com.fenazola.mxcome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.AdvertsEntry;
import com.fenazola.mxcome.entry.SmartApplianceEntry;
import com.fenazola.mxcome.utils.Constant;
import com.fenazola.mxcome.utils.DataCache;
import com.fenazola.mxcome.utils.Utils;
import com.fenazola.mxcome.widget.chat.MsgImageLeft;
import com.fenazola.mxcome.widget.chat.MsgImageRight;
import com.fenazola.mxcome.widget.chat.MsgTextLeft;
import com.fenazola.mxcome.widget.chat.MsgTextRight;
import com.fenazola.mxcome.widget.chat.MsgVoiceLeft;
import com.fenazola.mxcome.widget.chat.MsgVoiceRight;
import com.zss.library.utils.LogUtils;
import com.zss.library.viewpager.AdViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/5/4.
 */

public class RecyclerSmartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    private Context mContext;
    private List<AdvertsEntry> advList;
    private List<SmartApplianceEntry> proList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener = null;
    private List<Integer> selectIds = new ArrayList<>();
    private FLAG curFlag;

    public enum FLAG{
        HOME_FURNISED, APPLIANCE;
    }

    public RecyclerSmartAdapter(Context context, FLAG flag) {
        mContext = context;
        curFlag = flag;
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case ITEM_TYPE_HEADER: {
                View view = LayoutInflater.from(mContext).inflate(R.layout.layout_recycler_header, parent, false);
                holder = new HeaderViewHolder(view);
                view.setOnClickListener(this);
            }
            break;
            case ITEM_TYPE_CONTENT: {
                View view = LayoutInflater.from(mContext).inflate(R.layout.recycleritem_smart_appliance, parent, false);
                holder = new ContentViewHolder(view);
                view.setOnClickListener(this);
            }
            break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int msgType = getItemViewType(position);
        switch (msgType) {
            case ITEM_TYPE_HEADER: {
                HeaderViewHolder headerHolder = (HeaderViewHolder) holder;
                List<String> list = new ArrayList<>();
                if(advList != null && advList.size() > 0){
                    for(AdvertsEntry item: advList){
                        list.add(Constant.imageUrl + Utils.getFirstJSONArray(item.getAdvert_pics()));
                    }
                }
                LogUtils.i("---zss---", "------ list = " + list);
                if(curFlag == FLAG.APPLIANCE){
                    headerHolder.choice_item.setVisibility(View.GONE);
                } else {
                    headerHolder.choice_item.setVisibility(View.VISIBLE);
                }
                headerHolder.adViewPager.setUrls(list);
                headerHolder.itemView.setTag(position);
            }
            break;
            case ITEM_TYPE_CONTENT: {
                ContentViewHolder contentHolder = (ContentViewHolder) holder;
                SmartApplianceEntry mEntry = proList.get(position - 1);
                contentHolder.textView.setText(mEntry.getItem_name());
                LogUtils.i("---zss---", "------ show_pic = " + Constant.imageUrl + mEntry.getShow_pic());
                Glide.with(mContext).load(Constant.imageUrl + mEntry.getShow_pic()).into(contentHolder.imageView);
                Integer realPosition = position - 1;
                if (selectIds.contains(realPosition)) {
                    contentHolder.check.setImageResource(R.mipmap.project_choiced);
                } else {
                    contentHolder.check.setImageResource(R.drawable.corner_shape_grey);
                }
                contentHolder.itemView.setTag(position - 1);
            }
            break;
        }
    }

    //头部 ViewHolder
    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public ImageView choice_item;
        public AdViewPager adViewPager;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            choice_item = (ImageView) itemView.findViewById(R.id.choice_item);
            adViewPager = (AdViewPager)itemView.findViewById(R.id.adViewPager);
        }
    }

    //内容 ViewHolder
    public class ContentViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public ImageView check;

        public ContentViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            textView = (TextView) itemView.findViewById(R.id.text);
            check = (ImageView) itemView.findViewById(R.id.check);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    @Override
    public int getItemCount() {
        if(proList == null || proList.size() == 0){
            return 0;
        }else {
            return proList.size() + 1;
        }
    }

    public SmartApplianceEntry getRealPosition(int position){
        return proList.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public List<Integer> getDefSelect() {
        return this.selectIds;
    }

    public void setDefSelect(Integer position) {
        if(selectIds.contains(position)){
            this.selectIds.remove(position);
        } else {
            this.selectIds.add(position);
        }
        this.notifyDataSetChanged();
    }

    public void addAll(List<AdvertsEntry> advList, List<SmartApplianceEntry> proList){
        this.advList = advList;
        this.proList = proList;
        List<SmartApplianceEntry> tempData;
        if(curFlag == FLAG.HOME_FURNISED){
            tempData = DataCache.material10070001;
        }else{
            tempData = DataCache.material10070002;
        }
        if(tempData != null && proList != null && proList.size() > 0){
            for(int i = 0; i<proList.size(); i++){
                for(SmartApplianceEntry item : tempData){
                    if(item.getItem_id().equals(proList.get(i).getItem_id())){
                        selectIds.add(i);
                    }
                }
            }
        }
        this.notifyDataSetChanged();
    }

}
