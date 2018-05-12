package com.fenazola.mxcome.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.zss.library.adapter.CommonAdapter;
import com.zss.library.adapter.ViewHolder;
import com.zss.library.utils.DPUtils;

import java.util.List;

/**
 * 公用底部对话框
 * @author zm
 */
public class BottomListDialog extends Dialog {

    private ListView listView;
    private CommonAdapter adapter;
    private AdapterView.OnItemClickListener listener;

    public BottomListDialog(Context context, AdapterView.OnItemClickListener listener) {
        super(context, R.style.CommonDialog);
        setContentView(R.layout.dialog_bottom_list);
        this.listener = listener;
        setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = DPUtils.getScreenW(getContext());
        params.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(params);
        initView();
    }

    private void initView() {
        listView = (ListView)findViewById(R.id.listView);
        adapter = new CommonAdapter<String>(getContext(), R.layout.listitem_bottom) {
            @Override
            protected void convert(ViewHolder viewHolder, String item, int position) {
                TextView text = viewHolder.findViewById(R.id.text);
                text.setText(item);
            }
        };
        listView.setAdapter(adapter);
        if(listener != null)
            listView.setOnItemClickListener(listener);
    }

    public void setDatas(List<String> datas){
        if(datas != null){
            adapter.replaceAll(datas);
        }
    }

    public void show() {
        try {
            super.show();
        } catch (Exception e) {
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
        }
    }

}
