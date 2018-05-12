package com.fenazola.mxcome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.fenazola.mxcome.R;
import com.fenazola.mxcome.entry.friend.FriendEntity;
import com.fenazola.mxcome.widget.clipView.CircleImageView;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Created by quan on 2016/5/7.
 */
public class FriendAdapter extends BaseAdapter implements SectionIndexer {

    Context context;
    List<FriendEntity> data = getCityList();

    public FriendAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CityViewHolder_2 holder_2 = null;
        if (convertView == null) {
            holder_2 = new CityViewHolder_2();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_listview_friend, null);
            holder_2.word_ly=(LinearLayout)convertView.findViewById(R.id.city_word_ly);
            holder_2.word_tv = (TextView) convertView.findViewById(R.id.city_word_tv);
            holder_2.city_tv = (TextView) convertView.findViewById(R.id.city_list_tv);
            holder_2.phone=(CircleImageView)convertView.findViewById(R.id.photo);
            holder_2.isCheck=(ImageView)convertView.findViewById(R.id.is_check);
            convertView.setTag(holder_2);
        } else {
            holder_2 = (CityViewHolder_2) convertView.getTag();
        }
        holder_2.city_tv.setText(getCityList().get(position).getFriendName());
        holder_2.word_tv.setText(getFirstChar(getCityList().get(position).getWord()));

        int section = getSectionForPosition(position);
        int sectionPos = getPositionForSection(section);
        if (position == sectionPos) {
            holder_2.word_ly.setVisibility(View.VISIBLE);

        } else {
            holder_2.word_ly.setVisibility(View.GONE);
        }
        if(position%2==0){
            holder_2.isCheck.setImageResource(R.mipmap.icon_seletct_add);
        }else{
            holder_2.isCheck.setImageResource(R.drawable.corner_shape_grey);

        }
        return convertView;
    }

    private String getFirstChar(String sortKey) {
        return sortKey.substring(0, 1).toUpperCase(Locale.CHINA);
    }

    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        int position = -1;
        for (int i = 0; i < data.size(); i++) {
            int currentChar = getSectionForPosition(i);
            if (currentChar == sectionIndex) {
                position = i;
                break;
            }
        }
        return position;
    }

    @Override
    public int getSectionForPosition(int position) {
        return data.get(position).getWord().toUpperCase(Locale.CHINA).charAt(0);
    }

    class CityViewHolder_2 {
        TextView word_tv, city_tv;
        CircleImageView phone;
        ImageView isCheck;
        LinearLayout word_ly;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<FriendEntity> getCityList() {
        List<FriendEntity> data = new ArrayList<FriendEntity>();
        List<String> names = new ArrayList<String>();
        names.add("李伟");
        names.add("张三");
        names.add("阿三");
        names.add("阿四");
        names.add("段誉");
        names.add("段正淳");
        names.add("张三丰");
        names.add("陈坤");
        names.add("林俊杰1");
        names.add("陈坤2");
        names.add("王二a");
        names.add("林俊杰a");
        names.add("张四");
        names.add("林俊杰");
        names.add("王二");
        names.add("王二b");
        names.add("赵四");
        names.add("杨坤");
        names.add("赵子龙");
        names.add("杨坤1");
        names.add("李伟1");
        names.add("宋江");
        names.add("宋江1");
        names.add("李伟3");
        data.add(new FriendEntity("人鱼小姐", "#"));
        for (int i=0;i<names.size();i++) {
            data.add(new FriendEntity(names.get(i), getPingYin(names.get(i))));
        }

        Collections.sort(data, new Comparator<FriendEntity>() {
            @Override
            public int compare(FriendEntity lhs, FriendEntity rhs) {
                return lhs.getWord().compareTo(rhs.getWord());
            }
        });
        return data;
    }

    /**
     * 将字符串中的中文转化为拼音,英文字符不变
     *
     * @param inputString 汉字
     * @return
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        String output = "";
        if (inputString != null && inputString.length() > 0
                && !"null".equals(inputString)) {
            char[] input = inputString.trim().toCharArray();
            try {
                for (int i = 0; i < input.length; i++) {
                    if (Character.toString(input[i]).matches(
                            "[\\u4E00-\\u9FA5]+")) {
                        String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                        output += temp[0];
                    } else
                        output += Character.toString(input[i]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        } else {
            return "*";
        }
        return output;
    }
}
