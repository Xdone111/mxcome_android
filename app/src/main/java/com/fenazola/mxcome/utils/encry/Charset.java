package com.fenazola.mxcome.utils.encry;

import java.io.UnsupportedEncodingException;

public class Charset {
	
	private static String HEX = "0123456789ABCDEF";

	private static byte[] _HexToBytes(String hex) {
		String _hex = hex.toUpperCase();
		byte[] result = new byte[hex.length() / 2];
		for (int i = 0; i < _hex.length() / 2; i++) {
			byte HH = (byte) (HEX.indexOf(_hex.substring(2 * i, 2 * i + 1)) << 4);
			byte HL = (byte) (HEX.indexOf(_hex.substring(2 * i + 1, 2 * i + 2)));
			result[i] = (byte) (HH | HL);
		}
		return result;
	}

	public static String toHex(byte one) {
		String HEX = "0123456789ABCDEF";
		byte[] bTmp = new byte[2];
		bTmp[0] = (byte) (HEX.charAt((one & 0xf0) >> 4));
		bTmp[1] = (byte) (HEX.charAt(one & 0x0f));
		String result = new String(bTmp);
		return result;
	}

	
	public static String StrToHex(String value) {
		if (value == null)
			return "";
		String str = "";
		byte[] btmp = value.getBytes();
		for (int i = 0; i < btmp.length; i++) {
			str = str + toHex(btmp[i]);
		}
		return str;
	}

	
	public static String HexToStr(String hex) {
		if (hex == null)
			return "";
		byte temp[] = _HexToBytes(hex);
		String result = new String(temp);
		return result;
	}

	
	public static byte[] hexToBytes(String hex) {
		byte temp[] = _HexToBytes(hex);
		return temp;
	}


	public static String bytesToHex(byte[] bytes) {
		String ret = "";
		for (int i = 0; i < bytes.length; i++) {
			ret += toHex(bytes[i]);
		}
		return ret;
	}

	
	public static String toUnicode(String value) {
		if (value == null)
			return "";
		byte[] tmp = new byte[value.length()];
		int I = 0;
		while (I < value.length()) {
			tmp[I] = (byte) (value.charAt(I) & 0x00FF);// 双锟街斤拷
			I++;
		}
		String result = new String(tmp);
		return result;
	}

	public static String GB_2_ISO8859(String str) {
		if (str == null)
			return "";
		try {
			str = new String(str.getBytes("GBK"), "iso-8859-1");
			return str;
		} catch (Exception ex) {
		}
		return null;
	}


	public static String ISO8859_2_Gb(String str) {
		if (str == null)
			return "";
		try {
			str = new String(str.getBytes("iso-8859-1"), "GBK");
			return str;
		} catch (Exception ex) {
		}
		return null;
	}

	public static void main(String[] sky) throws UnsupportedEncodingException{
		String t =Charset.HexToStr("D08144195CB5716120930BCEF64A010AF1361E334094A0071BE14A9D9FEF50A56BA22D94646D6726D937977F1D6756B17B3FEE4BCF43980C15F3E148BD8DB62A131F30F5D24F5369E6487375D99984720BB14AD79EB198E0F744A6885F911CB3084AC9A079613DFEFE97102B5D733A0053C3C40DC1AC15C72115EC961962A2E797B367426620BE5B39E97EEB63C3E5FF63231A1D5B9EA2D6150C6040F7BA664758548452D40CE853C74F2E8A81BC64630ABBEF8F26DFE46739577864CBDFF692462E1915E41DCEB4A4698FE96EF414DC23385D5A1A0860338E30D598C7C9F52FB92C44E9B81762271C8FCCA63C4423E0AAD3267484168D8E3045A109498DF49A68F43B4298F4EC32F7894FEB09B9EDCDFEA12AFD9A5725CBF8F20932990DA179CAB8E3E7412454DCAC5C04292815F146B72DF5C6D42A392BC385278E46D2A3C0E6165D3FC3");
		System.out.println(t);
	}

}
