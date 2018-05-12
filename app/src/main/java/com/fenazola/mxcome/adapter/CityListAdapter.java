package com.fenazola.mxcome.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.db.TableArea;
import com.fenazola.mxcome.entry.LocateState;
import com.fenazola.mxcome.utils.PinyinUtils;
import com.fenazola.mxcome.widget.WrapHeightGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 城市适配器
 */
public class CityListAdapter extends BaseAdapter {
    private static final int VIEW_TYPE_COUNT = 3;


    private Context mContext;
    private LayoutInflater inflater;
    private List<TableArea> mCities;
    private HashMap<String, Integer> letterIndexes;
    private String[] sections;
    private OnCityClickListener onCityClickListener;
    private int locateState = LocateState.LOCATING;
    private HotCityGridAdapter mGridAdapter;
    /**本地城市*/
    private String locatedCity;
    /**热门城市适配器*/
    private List<String> hotCity;

    public CityListAdapter(Context mContext, List<String> hotCity, List<TableArea> mCities) {
        this.mContext = mContext;
        this.mCities = mCities;
        this.hotCity = hotCity;
        this.inflater = LayoutInflater.from(mContext);
        if (mCities == null){
            mCities = new ArrayList<>();
        }
        TableArea area1 = new TableArea();
        area1.setArea("定位");
        area1.setKeyword("0");
        TableArea area2 = new TableArea();
        area2.setArea("热门");
        area2.setKeyword("1");
        mCities.add(0, area1);
        mCities.add(1, area2);
        int size = mCities.size();
        letterIndexes = new HashMap<>();
        sections = new String[size];
        for (int index = 0; index < size; index++){
            //当前城市拼音首字母
            String currentLetter = PinyinUtils.getFirstLetter(mCities.get(index).getKeyword());
            //上个首字母，如果不存在设为""
            String previousLetter = index >= 1 ? PinyinUtils.getFirstLetter(mCities.get(index - 1).getKeyword()) : "";
            if (!TextUtils.equals(currentLetter, previousLetter)){
                letterIndexes.put(currentLetter, index);
                sections[index] = currentLetter;
            }
        }
    }

    /**
     * 更新定位状态
     * @param state
     */
    public void updateLocateState(int state, String city){
        this.locateState = state;
        this.locatedCity = city;
        notifyDataSetChanged();
    }

    /**
     * 获取字母索引的位置
     * @param letter
     * @return
     */
    public int getLetterPosition(String letter){
        Integer integer = letterIndexes.get(letter);
        return integer == null ? -1 : integer;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position < VIEW_TYPE_COUNT - 1 ? position : VIEW_TYPE_COUNT - 1;
    }

    @Override
    public int getCount() {
        return mCities == null ? 0: mCities.size();
    }

    @Override
    public TableArea getItem(int position) {
        return mCities == null ? null : mCities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        CityViewHolder holder;
        int viewType = getItemViewType(position);
        switch (viewType){
            case 0:     //定位
                view = inflater.inflate(R.layout.cp_view_locate_city, parent, false);
                TextView state = (TextView) view.findViewById(R.id.tv_located_city);
                switch (locateState){
                    case LocateState.LOCATING:
                        state.setText("定位中");
                        break;
                    case LocateState.FAILED:
                        state.setText("定位失败");
                        break;
                    case LocateState.SUCCESS:
                        state.setText(locatedCity);
                        break;
                }
                state.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (locateState == LocateState.FAILED){
                            //重新定位
                            if (onCityClickListener != null){
                                onCityClickListener.onLocateClick();
                            }
                        }else if (locateState == LocateState.SUCCESS){
                            //返回定位城市
                            if (onCityClickListener != null){
                                onCityClickListener.onCityClick(locatedCity);
                            }
                        }
                    }
                });
                break;
            case 1:     //热门
                view = inflater.inflate(R.layout.cp_view_hot_city, parent, false);
                WrapHeightGridView gridView = (WrapHeightGridView) view.findViewById(R.id.gridview_hot_city);
                mGridAdapter = new HotCityGridAdapter(mContext, hotCity);
                gridView.setAdapter(mGridAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (onCityClickListener != null){
                            onCityClickListener.onCityClick(mGridAdapter.getItem(position));
                        }
                    }
                });
                break;
            case 2:     //所有
                if (view == null){
                    view = inflater.inflate(R.layout.cp_item_city_listview, parent, false);
                    holder = new CityViewHolder();
                    holder.letter = (TextView) view.findViewById(R.id.tv_item_city_listview_letter);
                    holder.name = (TextView) view.findViewById(R.id.tv_item_city_listview_name);
                    view.setTag(holder);
                }else{
                    holder = (CityViewHolder) view.getTag();
                }
                if (position >= 1){
                    final String city = mCities.get(position).getArea();
                    holder.name.setText(city);
                    String currentLetter = PinyinUtils.getFirstLetter(mCities.get(position).getKeyword());
                    String previousLetter = position >= 1 ? PinyinUtils.getFirstLetter(mCities.get(position - 1).getKeyword()) : "";
                    if (!TextUtils.equals(currentLetter, previousLetter)){
                        holder.letter.setVisibility(View.VISIBLE);
                        holder.letter.setText(currentLetter);
                    }else{
                        holder.letter.setVisibility(View.GONE);
                    }
                    holder.name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (onCityClickListener != null){
                                onCityClickListener.onCityClick(city);
                            }
                        }
                    });
                }
                break;
        }
        return view;
    }

    public static class CityViewHolder{
        TextView letter;
        TextView name;
    }

    public void setOnCityClickListener(OnCityClickListener listener){
        this.onCityClickListener = listener;
    }

    public interface OnCityClickListener{
        void onCityClick(String name);
        void onLocateClick();
    }

}
