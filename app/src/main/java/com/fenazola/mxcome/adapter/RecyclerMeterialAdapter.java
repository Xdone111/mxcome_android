package com.fenazola.mxcome.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.MaterialListEntry;

import java.util.List;

/**
 * Created by XDONE on 2017/5/11.
 */

public class RecyclerMeterialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MaterialListEntry> datas;
    private Context context;

    public enum ITEM_TYPE {
        ITEM1,
        ITEM2
    }

    public RecyclerMeterialAdapter(List<MaterialListEntry> datas, Context context) {
        super();
        this.datas = datas;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM1.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reycleritem_materail1, parent, false);
            return new Item1ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reycleritem_materail2, parent, false);
            return new Item2ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Item1ViewHolder) {
            ((Item1ViewHolder) holder).itemText1.setText(datas.get(position).getName());
            ((Item1ViewHolder) holder).material_num.setText(datas.get(position).getMum());
        } else if (holder instanceof Item2ViewHolder) {
            ((Item2ViewHolder) holder).itemText1.setText(datas.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? ITEM_TYPE.ITEM1.ordinal() : ITEM_TYPE.ITEM2.ordinal();
    }

    //item1 的ViewHolder
    public class Item1ViewHolder extends RecyclerView.ViewHolder {
        TextView itemText1;
        TextView material_num;

        public Item1ViewHolder(View itemView) {
            super(itemView);
            itemText1 = (TextView) itemView.findViewById(R.id.text1);
            material_num = (TextView) itemView.findViewById(R.id.material_num);
        }
    }

    //item2 的ViewHolder
    public class Item2ViewHolder extends RecyclerView.ViewHolder {
        TextView itemText1;

        public Item2ViewHolder(View itemView) {
            super(itemView);
            itemText1 = (TextView) itemView.findViewById(R.id.text1);

        }
    }
}
