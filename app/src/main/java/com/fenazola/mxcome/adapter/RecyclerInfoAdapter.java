package com.fenazola.mxcome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.MaterialEntry;
import com.zss.library.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XDONE on 2017/3/30.
 */

public class RecyclerInfoAdapter extends RecyclerView.Adapter<RecyclerInfoAdapter.MyViewHolder> {

    private List<MaterialEntry> datas = new ArrayList<>();
    private Context context;
    private OnItemClickLitener mOnItemClickLitener;
    private int currentPos = 0;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public void replaceAll(List<MaterialEntry> datas) {
        this.currentPos = 0;
        this.datas.clear();
        this.datas.addAll(datas);
        this.notifyDataSetChanged();
    }

    public RecyclerInfoAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycleritem_info_image, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MaterialEntry entry = datas.get(position);
        String flname = entry.getItem_name();
        if(TextUtils.isEmpty(flname)){
            flname = "未分类";
        }
        if(currentPos == position){
            holder.textView.setText(flname);
            holder.textView.setTextColor(BaseActivity.getColor(context, R.color.colorBlue));
        } else {
            holder.textView.setText(flname);
            holder.textView.setTextColor(BaseActivity.getColor(context, R.color.colorGrey));
        }
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                    currentPos = position;
                    notifyDataSetChanged();

                }

            });
        }
    }

    @Override
    public int getItemCount() {
        if (datas == null) {
            return 0;
        }
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public MyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
        }
    }

    public MaterialEntry getItem(int position){
        return datas.get(position);
    }


    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
        this.notifyDataSetChanged();
    }
}


