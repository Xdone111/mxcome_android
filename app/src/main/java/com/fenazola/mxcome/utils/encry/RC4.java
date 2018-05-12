package com.fenazola.mxcome.utils.encry;

import org.bouncycastle.crypto.StreamCipher;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.params.KeyParameter;

/**
 * a simple implementation of the RC4 (tm) encryption algorithm
 */
public class RC4 {
	
	public static byte[] process(byte[] data, byte[] key) {
		StreamCipher cipher = new RC4Engine();
		cipher.init(true, new KeyParameter(key));
		byte[] btmp = new byte[256];
		cipher.processBytes(btmp, 0, 256, btmp, 0);
		byte[] out = new byte[data.length];
		cipher.processBytes(data, 0, data.length, out, 0);
		return out;
	}
	

	public static void main(String[] args) throws Exception {
		String key = "12345678123456781234567812345678";
		String datas = "C800C24EFC5559812AD8D2A5FCEEF852991CB35CDF2347FEBEB038ABDCADB17B82FC61D99525BB0464D4A19487209AC67EA7E5CE66FA09E5099C54BC770CF8FAEE242826A099993FBAA9170D9489A80BF63A997707411C670A534A0D9F253A397CD535539503350D19DE4272AC8D58F548B69F20C1D771E8A02834643B842815";
		
		byte[] p1 = RC4.process(datas.getBytes(), key.getBytes());
		System.out.println("p1: "+Charset.bytesToHex(p1));
		
		byte[] p2 = RC4.process(p1, key.getBytes());
		System.out.println("p2: "+new String(p2));
	}
}