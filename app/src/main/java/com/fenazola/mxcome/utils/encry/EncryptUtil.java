package com.fenazola.mxcome.utils.encry;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtil {

	private static BouncyCastleProvider bouncyCastleProvider;

	static {
		if (bouncyCastleProvider == null)
			bouncyCastleProvider = new BouncyCastleProvider();
	}

	private static final String AES = "AES/CBC/PKCS7Padding";
	private static final String DES = "DES/CBC/PKCS5Padding";
	private static final String TDES = "DESede/CBC/PKCS5Padding";
	private static String key = "MXCOME_123456789";
	private static String ivec = "zxcvbnmasdfghjkl";
																			
	public static byte[] getSecureRandom(int size) {
		Security.addProvider(bouncyCastleProvider);
		SecureRandom sr = new SecureRandom();
		return sr.generateSeed(size);
	}

	public static byte[] TdesEncrypt(byte[] key, byte[] ivec, byte[] src)
			throws Exception {
		Security.addProvider(bouncyCastleProvider);
		SecretKeySpec secretKey = new SecretKeySpec(key, TDES);
		IvParameterSpec iv = new IvParameterSpec(ivec);
		Cipher cipher = Cipher.getInstance(TDES, "BC");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return cipher.doFinal(src);
	}

	public static byte[] TdesDecrypt(byte[] key, byte[] ivec, byte[] src)
			throws Exception {
		Security.addProvider(bouncyCastleProvider);
		SecretKeySpec desKey = new SecretKeySpec(key, TDES);
		IvParameterSpec iv = new IvParameterSpec(ivec);
		Cipher cipher = Cipher.getInstance(TDES, "BC");
		cipher.init(Cipher.DECRYPT_MODE, desKey, iv);
		return cipher.doFinal(src);
	}

	public static byte[] DesEncrypt(byte[] key, byte[] ivec, byte[] src)
			throws Exception {
		Security.addProvider(bouncyCastleProvider);
		SecretKeySpec secretKey = new SecretKeySpec(key, DES);
		IvParameterSpec iv = new IvParameterSpec(ivec);
		Cipher cipher = Cipher.getInstance(DES, "BC");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		return cipher.doFinal(src);
	}

	public static byte[] DesDecrypt(byte[] key, byte[] ivec, byte[] src)
			throws Exception {
		Security.addProvider(bouncyCastleProvider);
		SecretKeySpec desKey = new SecretKeySpec(key, DES);
		IvParameterSpec iv = new IvParameterSpec(ivec);
		Cipher cipher = Cipher.getInstance(DES, "BC");
		cipher.init(Cipher.DECRYPT_MODE, desKey, iv);
		return cipher.doFinal(src);
	}

	public static byte[] AesEncrypt(byte[] key, byte[] ivec,  byte[] src)
			throws Exception {
		Security.addProvider(bouncyCastleProvider);
		SecretKeySpec aeskey = new SecretKeySpec(key, AES);
		IvParameterSpec iv = new IvParameterSpec(ivec);
		Cipher cipher = Cipher.getInstance(AES, "BC");
		cipher.init(Cipher.ENCRYPT_MODE, aeskey, iv);
		return cipher.doFinal(src);
	}

	public static byte[] AesDecrypt(byte[] key, byte[] ivec, byte[] src)
			throws Exception {
		Security.addProvider(bouncyCastleProvider);
		SecretKeySpec aeskey = new SecretKeySpec(key, AES);
		IvParameterSpec iv = new IvParameterSpec(ivec);
		Cipher cipher = Cipher.getInstance(AES, "BC");
		cipher.init(Cipher.DECRYPT_MODE, aeskey, iv);
		return cipher.doFinal(src);
	}

	public static String AesEncrypt(String src){
		try{
			byte[] ret = EncryptUtil.AesEncrypt(key.getBytes(), ivec.getBytes(), src.getBytes());
			return Charset.bytesToHex(ret);
		}catch (Exception e){
			return "";
		}
	}

	public static String AesDecrypt(String src){
		try{
			byte[] ret = EncryptUtil.AesDecrypt(key.getBytes(), ivec.getBytes(), Charset.hexToBytes(src));
			return new String(ret);
		}catch (Exception e){
			return "";
		}
	}

	public static void main(String[] args) throws Exception {
		String src = "helloworld";
		byte[] ret = EncryptUtil.AesEncrypt(key.getBytes(), ivec.getBytes(), src.getBytes());
		String hexStr =  Charset.bytesToHex(ret);
		System.out.println("aes encrypt: "+hexStr);
		System.out.println("aes encrypt length: "+hexStr.length());
		ret = EncryptUtil.AesDecrypt(key.getBytes(), ivec.getBytes(), ret);
		System.out.println("aes decrypt: "+new String(ret));
	}

}
