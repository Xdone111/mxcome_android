package com.fenazola.mxcome.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Gson工具类
 * @author zm
 *
 */
public class GsonUtils {

	public static Gson gson = new Gson();

	/**
	 * 返回List对象
	 * @param str
	 * @param type new TypeToken<ArrayList<T>>(){}.getType()
	 * @param <T>
     * @return
     */
	public static <T> T getListFromJSON(String str, Type type) {
		if (!TextUtils.isEmpty(str)) {
			return gson.fromJson(str, type);
		}
		return null;
	}

	/**
	 * 返回List对象
	 * @param str
	 * @param cls
	 * @param <T>
     * @return
     */
	public static <T> List<T> getListFromJSON(String str, Class<T> cls)
	{
		Type type = new TypeToken<ArrayList<JsonObject>>()
		{}.getType();
		ArrayList<JsonObject> jsonObjects = gson.fromJson(str, type);
		ArrayList<T> arrayList = new ArrayList<>();
		for (JsonObject jsonObject : jsonObjects)
		{
			arrayList.add(gson.fromJson(jsonObject, cls));
		}
		return arrayList;
	}

	/**
	 * 返回对象
	 * @param str
	 * @param cls
	 * @param <T>
     * @return
     */
	public static <T> T getObjFromJSON(String str, Class<T> cls) {
		if (!TextUtils.isEmpty(str)) {
			return gson.fromJson(str, cls);
		}
		return null;
	}



}
