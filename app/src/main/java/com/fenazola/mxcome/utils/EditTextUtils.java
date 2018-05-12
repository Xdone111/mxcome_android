package com.fenazola.mxcome.utils;

import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

/**
 * Created by zm on 2017/2/24.
 */

public class EditTextUtils {

    public static int getPasswordType(){
        return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
    }

    public static int getTextType(){
        return InputType.TYPE_CLASS_TEXT;
    }

    /**
     * 拨号键盘
     * @return
     */
    public static int getPhoneType(){
        return InputType.TYPE_CLASS_PHONE;
    }

    /**
     * 数字格式
     * @return
     */
    public static int getNumberType(){
        return InputType.TYPE_CLASS_NUMBER;
    }

    /**
     * 有符号数字格式
     * @return
     */
    public static int getNumberSignedType(){
        return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED;
    }

    /**
     * 可以带小数点的浮点格式
     * @return
     */
    public static int getNumberDecimalType(){
        return InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;
    }

    public static void setMaxLength(EditText editText, int length){
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    public static void setPasswordAcceptedChars(EditText editText){
        KeyListener listener = new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".toCharArray();
            }

            @Override
            public int getInputType() {
                return getPasswordType();
            }
        };
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        editText.setKeyListener(listener);
    }

    public static void setPhoneAcceptedChars(EditText editText){
        KeyListener listener = new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return "+0123456789".toCharArray();
            }

            @Override
            public int getInputType() {
                return getPhoneType();
            }
        };
        editText.setKeyListener(listener);
        InputFilter startFilter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                String nowStr = source.toString();
                String beforeStr = dest.toString();
                if (dstart == 0 && dend == 0) {
                    if (nowStr.startsWith("+") || nowStr.startsWith("1")) {
                        return source;
                    } else {
                        return "";
                    }
                } else {
                    if(beforeStr.startsWith("+")){
                        if(beforeStr.length() > 13){
                            return "";
                        }
                    } else if(beforeStr.startsWith("1")){
                        if(beforeStr.length() > 10){
                            return "";
                        }
                    } else{
                        return "";
                    }
                }
                return nowStr;
            }
        };
        editText.setFilters(new InputFilter[]{ startFilter});
    }

}
