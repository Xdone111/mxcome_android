package com.fenazola.mxcome.utils;

import com.fenazola.mxcome.entry.AdvertsEntry;
import com.fenazola.mxcome.entry.SchemeEntry;
import com.fenazola.mxcome.entry.SmartApplianceEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zm on 2017/4/17.
 */

public class DataCache {

    public static String ENUM_CD_ZT = "ENUM_CD_ZT"; //整体装修
    public static String ENUM_CD_MK = "ENUM_CD_MK"; //模块装修
    public static String ENUM_CD_DX = "ENUM_CD_DX"; //单项发布


    public static String ENUM_CD_SPF = "ENUM_CD_SPF"; //商品房装修
    public static String ENUM_CD_BS = "ENUM_CD_BS"; //别墅装修

    public static String ENUM_FG_JY = "ENUM_FG_JY";  //简约
    public static String ENUM_FG_ZS = "ENUM_FG_ZS"; //中式
    public static String ENUM_FG_RS = "ENUM_FG_RS"; //日式
    public static String ENUM_FG_XD = "ENUM_FG_XD"; //现代
    public static String ENUM_FG_XGD = "ENUM_FG_XGD"; //新古典
    public static String ENUM_FG_OS = "ENUM_FG_OS"; //欧式
    public static String ENUM_FG_YJ = "ENUM_FG_YJ"; //宜家
    public static String ENUM_FG_HD = "ENUM_FG_HD"; //混搭
    public static String ENUM_FG_BO = "ENUM_FG_BO"; //北欧
    public static String ENUM_FG_DNY = "ENUM_FG_DNY"; //东南亚
    public static String ENUM_FG_TY = "ENUM_FG_TY"; //田园
    public static String ENUM_FG_JO = "ENUM_FG_JO"; //简欧
    public static String ENUM_FG_MS = "ENUM_FG_MS"; //美式
    public static String ENUM_FG_ZZH = "ENUM_FG_ZZH"; //地中海

    public static String ENUM_PW_SHE = "ENUM_PW_SHE"; //奢
    public static String ENUM_PW_HAO = "ENUM_PW_HAO"; //豪
    public static String ENUM_PW_SHU = "ENUM_PW_SHU"; //舒
    public static String ENUM_PW_JIAN = "ENUM_PW_JIAN"; //简

    public static String ENUM_CD_QIB = "ENUM_CD_QIB"; //轻包
    public static String ENUM_CD_BIB = "ENUM_CD_BIB"; //半包
    public static String ENUM_CD_QUB = "ENUM_CD_QUB"; //全包

    public static String SHEJI = "SHEJI"; //设计
    public static String CHAIGAI = "CHAIGAI"; //拆改
    public static String SHUIDIAN = "SHUIDIAN"; //水电
    public static String NIGONG = "NIGONG"; //泥工
    public static String MUGONG = "MUGONG"; //木工
    public static String YOUQI = "YOUQI"; //油漆
    public static String DIBAN = "DIBAN"; //地板
    public static String FANGSHUI = "FANGSHUI"; //防水


    public static HashMap<String, String> material1001 = new HashMap();

    public static HashMap<String, String> material1002 = new HashMap();

    public static HashMap<String, String> material1003 = new HashMap();

    public static HashMap<String, String> material1004 = new HashMap();

    public static HashMap<String, String> material1005 = new HashMap();

    public static HashMap<String, String> material1006 = new HashMap();

    public static AdvertsEntry brand10070001 = null;

    public static List<SmartApplianceEntry> material10070001 = new ArrayList<>();

    public static List<SmartApplianceEntry> material10070002  = new ArrayList<>();

    public static HashMap<String, String> dictMap = new HashMap();

    public static ArrayList<SchemeEntry> enableList = new ArrayList<>();

    public static ArrayList<Integer> disableList = new ArrayList<>();

    public static void clear() {
        material1001.clear();
        material1002.clear();
        material1003.clear();
        material1004.clear();
        material1005.clear();
        material1006.clear();
        brand10070001 = null;
        material10070001.clear();
        material10070002.clear();
        disableList.clear();
    }

    static {
        dictMap.put(ENUM_CD_ZT, "整体装修");
        dictMap.put(ENUM_CD_MK, "模块装修");
        dictMap.put(ENUM_CD_SPF, "商品房装修");
        dictMap.put(ENUM_CD_BS, "别墅装修");
        dictMap.put(ENUM_FG_JY, "简约");
        dictMap.put(ENUM_FG_ZS, "中式");
        dictMap.put(ENUM_FG_RS, "日式");
        dictMap.put(ENUM_FG_XD, "现代");
        dictMap.put(ENUM_FG_XGD, "新古典");
        dictMap.put(ENUM_FG_OS, "欧式");
        dictMap.put(ENUM_FG_YJ, "宜家");
        dictMap.put(ENUM_FG_HD, "混搭");
        dictMap.put(ENUM_FG_BO, "北欧");
        dictMap.put(ENUM_FG_DNY, "东南亚");
        dictMap.put(ENUM_FG_TY, "田园");
        dictMap.put(ENUM_FG_JO, "简欧");
        dictMap.put(ENUM_FG_MS, "美式");
        dictMap.put(ENUM_FG_ZZH, "地中海");
        dictMap.put(ENUM_PW_SHE, "奢档");
        dictMap.put(ENUM_PW_HAO, "豪档");
        dictMap.put(ENUM_PW_SHU, "舒档");
        dictMap.put(ENUM_PW_JIAN, "简档");
        dictMap.put(ENUM_CD_QIB, "轻包");
        dictMap.put(ENUM_CD_BIB, "半包");
        dictMap.put(ENUM_CD_QUB, "全包");

        dictMap.put(SHEJI, "设计");
        dictMap.put(CHAIGAI, "拆改");
        dictMap.put(SHUIDIAN, "水电");
        dictMap.put(NIGONG, "泥工");
        dictMap.put(MUGONG, "木工");
        dictMap.put(YOUQI, "油漆");
        dictMap.put(DIBAN, "地板");
        dictMap.put(FANGSHUI, "防水");

    }
}
