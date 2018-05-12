package com.fenazola.mxcome.utils.encry;

public class DES {

	static final int skb0[] = {
			// for C bits (numbered as per FIPS 46) 1 2 3 4 5 6
			0x00000000, 0x00000010, 0x20000000, 0x20000010, 0x00010000, 0x00010010, 0x20010000, 0x20010010, 0x00000800,
			0x00000810, 0x20000800, 0x20000810, 0x00010800, 0x00010810, 0x20010800, 0x20010810, 0x00000020, 0x00000030,
			0x20000020, 0x20000030, 0x00010020, 0x00010030, 0x20010020, 0x20010030, 0x00000820, 0x00000830, 0x20000820,
			0x20000830, 0x00010820, 0x00010830, 0x20010820, 0x20010830, 0x00080000, 0x00080010, 0x20080000, 0x20080010,
			0x00090000, 0x00090010, 0x20090000, 0x20090010, 0x00080800, 0x00080810, 0x20080800, 0x20080810, 0x00090800,
			0x00090810, 0x20090800, 0x20090810, 0x00080020, 0x00080030, 0x20080020, 0x20080030, 0x00090020, 0x00090030,
			0x20090020, 0x20090030, 0x00080820, 0x00080830, 0x20080820, 0x20080830, 0x00090820, 0x00090830, 0x20090820,
			0x20090830, };

	static final int skb1[] = {
			// for C bits (numbered as per FIPS 46) 7 8 10 11 12 13
			0x00000000, 0x02000000, 0x00002000, 0x02002000, 0x00200000, 0x02200000, 0x00202000, 0x02202000, 0x00000004,
			0x02000004, 0x00002004, 0x02002004, 0x00200004, 0x02200004, 0x00202004, 0x02202004, 0x00000400, 0x02000400,
			0x00002400, 0x02002400, 0x00200400, 0x02200400, 0x00202400, 0x02202400, 0x00000404, 0x02000404, 0x00002404,
			0x02002404, 0x00200404, 0x02200404, 0x00202404, 0x02202404, 0x10000000, 0x12000000, 0x10002000, 0x12002000,
			0x10200000, 0x12200000, 0x10202000, 0x12202000, 0x10000004, 0x12000004, 0x10002004, 0x12002004, 0x10200004,
			0x12200004, 0x10202004, 0x12202004, 0x10000400, 0x12000400, 0x10002400, 0x12002400, 0x10200400, 0x12200400,
			0x10202400, 0x12202400, 0x10000404, 0x12000404, 0x10002404, 0x12002404, 0x10200404, 0x12200404, 0x10202404,
			0x12202404, };

	static final int skb2[] = {
			// for C bits (numbered as per FIPS 46) 14 15 16 17 19 20
			0x00000000, 0x00000001, 0x00040000, 0x00040001, 0x01000000, 0x01000001, 0x01040000, 0x01040001, 0x00000002,
			0x00000003, 0x00040002, 0x00040003, 0x01000002, 0x01000003, 0x01040002, 0x01040003, 0x00000200, 0x00000201,
			0x00040200, 0x00040201, 0x01000200, 0x01000201, 0x01040200, 0x01040201, 0x00000202, 0x00000203, 0x00040202,
			0x00040203, 0x01000202, 0x01000203, 0x01040202, 0x01040203, 0x08000000, 0x08000001, 0x08040000, 0x08040001,
			0x09000000, 0x09000001, 0x09040000, 0x09040001, 0x08000002, 0x08000003, 0x08040002, 0x08040003, 0x09000002,
			0x09000003, 0x09040002, 0x09040003, 0x08000200, 0x08000201, 0x08040200, 0x08040201, 0x09000200, 0x09000201,
			0x09040200, 0x09040201, 0x08000202, 0x08000203, 0x08040202, 0x08040203, 0x09000202, 0x09000203, 0x09040202,
			0x09040203, };

	static final int skb3[] = {
			// for C bits (numbered as per FIPS 46) 21 23 24 26 27 28
			0x00000000, 0x00100000, 0x00000100, 0x00100100, 0x00000008, 0x00100008, 0x00000108, 0x00100108, 0x00001000,
			0x00101000, 0x00001100, 0x00101100, 0x00001008, 0x00101008, 0x00001108, 0x00101108, 0x04000000, 0x04100000,
			0x04000100, 0x04100100, 0x04000008, 0x04100008, 0x04000108, 0x04100108, 0x04001000, 0x04101000, 0x04001100,
			0x04101100, 0x04001008, 0x04101008, 0x04001108, 0x04101108, 0x00020000, 0x00120000, 0x00020100, 0x00120100,
			0x00020008, 0x00120008, 0x00020108, 0x00120108, 0x00021000, 0x00121000, 0x00021100, 0x00121100, 0x00021008,
			0x00121008, 0x00021108, 0x00121108, 0x04020000, 0x04120000, 0x04020100, 0x04120100, 0x04020008, 0x04120008,
			0x04020108, 0x04120108, 0x04021000, 0x04121000, 0x04021100, 0x04121100, 0x04021008, 0x04121008, 0x04021108,
			0x04121108, };

	static final int skb4[] = {
			// for D bits (numbered as per FIPS 46) 1 2 3 4 5 6
			0x00000000, 0x10000000, 0x00010000, 0x10010000, 0x00000004, 0x10000004, 0x00010004, 0x10010004, 0x20000000,
			0x30000000, 0x20010000, 0x30010000, 0x20000004, 0x30000004, 0x20010004, 0x30010004, 0x00100000, 0x10100000,
			0x00110000, 0x10110000, 0x00100004, 0x10100004, 0x00110004, 0x10110004, 0x20100000, 0x30100000, 0x20110000,
			0x30110000, 0x20100004, 0x30100004, 0x20110004, 0x30110004, 0x00001000, 0x10001000, 0x00011000, 0x10011000,
			0x00001004, 0x10001004, 0x00011004, 0x10011004, 0x20001000, 0x30001000, 0x20011000, 0x30011000, 0x20001004,
			0x30001004, 0x20011004, 0x30011004, 0x00101000, 0x10101000, 0x00111000, 0x10111000, 0x00101004, 0x10101004,
			0x00111004, 0x10111004, 0x20101000, 0x30101000, 0x20111000, 0x30111000, 0x20101004, 0x30101004, 0x20111004,
			0x30111004, };

	static final int skb5[] = {
			// for D bits (numbered as per FIPS 46) 8 9 11 12 13 14
			0x00000000, 0x08000000, 0x00000008, 0x08000008, 0x00000400, 0x08000400, 0x00000408, 0x08000408, 0x00020000,
			0x08020000, 0x00020008, 0x08020008, 0x00020400, 0x08020400, 0x00020408, 0x08020408, 0x00000001, 0x08000001,
			0x00000009, 0x08000009, 0x00000401, 0x08000401, 0x00000409, 0x08000409, 0x00020001, 0x08020001, 0x00020009,
			0x08020009, 0x00020401, 0x08020401, 0x00020409, 0x08020409, 0x02000000, 0x0A000000, 0x02000008, 0x0A000008,
			0x02000400, 0x0A000400, 0x02000408, 0x0A000408, 0x02020000, 0x0A020000, 0x02020008, 0x0A020008, 0x02020400,
			0x0A020400, 0x02020408, 0x0A020408, 0x02000001, 0x0A000001, 0x02000009, 0x0A000009, 0x02000401, 0x0A000401,
			0x02000409, 0x0A000409, 0x02020001, 0x0A020001, 0x02020009, 0x0A020009, 0x02020401, 0x0A020401, 0x02020409,
			0x0A020409, };

	static final int skb6[] = {
			// for D bits (numbered as per FIPS 46) 16 17 18 19 20 21
			0x00000000, 0x00000100, 0x00080000, 0x00080100, 0x01000000, 0x01000100, 0x01080000, 0x01080100, 0x00000010,
			0x00000110, 0x00080010, 0x00080110, 0x01000010, 0x01000110, 0x01080010, 0x01080110, 0x00200000, 0x00200100,
			0x00280000, 0x00280100, 0x01200000, 0x01200100, 0x01280000, 0x01280100, 0x00200010, 0x00200110, 0x00280010,
			0x00280110, 0x01200010, 0x01200110, 0x01280010, 0x01280110, 0x00000200, 0x00000300, 0x00080200, 0x00080300,
			0x01000200, 0x01000300, 0x01080200, 0x01080300, 0x00000210, 0x00000310, 0x00080210, 0x00080310, 0x01000210,
			0x01000310, 0x01080210, 0x01080310, 0x00200200, 0x00200300, 0x00280200, 0x00280300, 0x01200200, 0x01200300,
			0x01280200, 0x01280300, 0x00200210, 0x00200310, 0x00280210, 0x00280310, 0x01200210, 0x01200310, 0x01280210,
			0x01280310, };

	static final int skb7[] = {
			// for D bits (numbered as per FIPS 46) 22 23 24 25 27 28
			0x00000000, 0x04000000, 0x00040000, 0x04040000, 0x00000002, 0x04000002, 0x00040002, 0x04040002, 0x00002000,
			0x04002000, 0x00042000, 0x04042000, 0x00002002, 0x04002002, 0x00042002, 0x04042002, 0x00000020, 0x04000020,
			0x00040020, 0x04040020, 0x00000022, 0x04000022, 0x00040022, 0x04040022, 0x00002020, 0x04002020, 0x00042020,
			0x04042020, 0x00002022, 0x04002022, 0x00042022, 0x04042022, 0x00000800, 0x04000800, 0x00040800, 0x04040800,
			0x00000802, 0x04000802, 0x00040802, 0x04040802, 0x00002800, 0x04002800, 0x00042800, 0x04042800, 0x00002802,
			0x04002802, 0x00042802, 0x04042802, 0x00000820, 0x04000820, 0x00040820, 0x04040820, 0x00000822, 0x04000822,
			0x00040822, 0x04040822, 0x00002820, 0x04002820, 0x00042820, 0x04042820, 0x00002822, 0x04002822, 0x00042822,
			0x04042822, };

	// The key round shifts
	static final boolean shifts2[] = { false, false, true, true, true, true, true, true, false, true, true, true, true,
			true, true, false };

	static final int SP0[] = { 0x00410100, 0x00010000, 0x40400000, 0x40410100, 0x00400000, 0x40010100, 0x40010000,
			0x40400000, 0x40010100, 0x00410100, 0x00410000, 0x40000100, 0x40400100, 0x00400000, 0x00000000, 0x40010000,
			0x00010000, 0x40000000, 0x00400100, 0x00010100, 0x40410100, 0x00410000, 0x40000100, 0x00400100, 0x40000000,
			0x00000100, 0x00010100, 0x40410000, 0x00000100, 0x40400100, 0x40410000, 0x00000000, 0x00000000, 0x40410100,
			0x00400100, 0x40010000, 0x00410100, 0x00010000, 0x40000100, 0x00400100, 0x40410000, 0x00000100, 0x00010100,
			0x40400000, 0x40010100, 0x40000000, 0x40400000, 0x00410000, 0x40410100, 0x00010100, 0x00410000, 0x40400100,
			0x00400000, 0x40000100, 0x40010000, 0x00000000, 0x00010000, 0x00400000, 0x40400100, 0x00410100, 0x40000000,
			0x40410000, 0x00000100, 0x40010100, };

	static final int SP1[] = { 0x08021002, 0x00000000, 0x00021000, 0x08020000, 0x08000002, 0x00001002, 0x08001000,
			0x00021000, 0x00001000, 0x08020002, 0x00000002, 0x08001000, 0x00020002, 0x08021000, 0x08020000, 0x00000002,
			0x00020000, 0x08001002, 0x08020002, 0x00001000, 0x00021002, 0x08000000, 0x00000000, 0x00020002, 0x08001002,
			0x00021002, 0x08021000, 0x08000002, 0x08000000, 0x00020000, 0x00001002, 0x08021002, 0x00020002, 0x08021000,
			0x08001000, 0x00021002, 0x08021002, 0x00020002, 0x08000002, 0x00000000, 0x08000000, 0x00001002, 0x00020000,
			0x08020002, 0x00001000, 0x08000000, 0x00021002, 0x08001002, 0x08021000, 0x00001000, 0x00000000, 0x08000002,
			0x00000002, 0x08021002, 0x00021000, 0x08020000, 0x08020002, 0x00020000, 0x00001002, 0x08001000, 0x08001002,
			0x00000002, 0x08020000, 0x00021000, };

	static final int SP2[] = { 0x20800000, 0x00808020, 0x00000020, 0x20800020, 0x20008000, 0x00800000, 0x20800020,
			0x00008020, 0x00800020, 0x00008000, 0x00808000, 0x20000000, 0x20808020, 0x20000020, 0x20000000, 0x20808000,
			0x00000000, 0x20008000, 0x00808020, 0x00000020, 0x20000020, 0x20808020, 0x00008000, 0x20800000, 0x20808000,
			0x00800020, 0x20008020, 0x00808000, 0x00008020, 0x00000000, 0x00800000, 0x20008020, 0x00808020, 0x00000020,
			0x20000000, 0x00008000, 0x20000020, 0x20008000, 0x00808000, 0x20800020, 0x00000000, 0x00808020, 0x00008020,
			0x20808000, 0x20008000, 0x00800000, 0x20808020, 0x20000000, 0x20008020, 0x20800000, 0x00800000, 0x20808020,
			0x00008000, 0x00800020, 0x20800020, 0x00008020, 0x00800020, 0x00000000, 0x20808000, 0x20000020, 0x20800000,
			0x20008020, 0x00000020, 0x00808000, };

	static final int SP3[] = { 0x00080201, 0x02000200, 0x00000001, 0x02080201, 0x00000000, 0x02080000, 0x02000201,
			0x00080001, 0x02080200, 0x02000001, 0x02000000, 0x00000201, 0x02000001, 0x00080201, 0x00080000, 0x02000000,
			0x02080001, 0x00080200, 0x00000200, 0x00000001, 0x00080200, 0x02000201, 0x02080000, 0x00000200, 0x00000201,
			0x00000000, 0x00080001, 0x02080200, 0x02000200, 0x02080001, 0x02080201, 0x00080000, 0x02080001, 0x00000201,
			0x00080000, 0x02000001, 0x00080200, 0x02000200, 0x00000001, 0x02080000, 0x02000201, 0x00000000, 0x00000200,
			0x00080001, 0x00000000, 0x02080001, 0x02080200, 0x00000200, 0x02000000, 0x02080201, 0x00080201, 0x00080000,
			0x02080201, 0x00000001, 0x02000200, 0x00080201, 0x00080001, 0x00080200, 0x02080000, 0x02000201, 0x00000201,
			0x02000000, 0x02000001, 0x02080200, };

	static final int SP4[] = { 0x01000000, 0x00002000, 0x00000080, 0x01002084, 0x01002004, 0x01000080, 0x00002084,
			0x01002000, 0x00002000, 0x00000004, 0x01000004, 0x00002080, 0x01000084, 0x01002004, 0x01002080, 0x00000000,
			0x00002080, 0x01000000, 0x00002004, 0x00000084, 0x01000080, 0x00002084, 0x00000000, 0x01000004, 0x00000004,
			0x01000084, 0x01002084, 0x00002004, 0x01002000, 0x00000080, 0x00000084, 0x01002080, 0x01002080, 0x01000084,
			0x00002004, 0x01002000, 0x00002000, 0x00000004, 0x01000004, 0x01000080, 0x01000000, 0x00002080, 0x01002084,
			0x00000000, 0x00002084, 0x01000000, 0x00000080, 0x00002004, 0x01000084, 0x00000080, 0x00000000, 0x01002084,
			0x01002004, 0x01002080, 0x00000084, 0x00002000, 0x00002080, 0x01002004, 0x01000080, 0x00000084, 0x00000004,
			0x00002084, 0x01002000, 0x01000004, };

	static final int SP5[] = { 0x10000008, 0x00040008, 0x00000000, 0x10040400, 0x00040008, 0x00000400, 0x10000408,
			0x00040000, 0x00000408, 0x10040408, 0x00040400, 0x10000000, 0x10000400, 0x10000008, 0x10040000, 0x00040408,
			0x00040000, 0x10000408, 0x10040008, 0x00000000, 0x00000400, 0x00000008, 0x10040400, 0x10040008, 0x10040408,
			0x10040000, 0x10000000, 0x00000408, 0x00000008, 0x00040400, 0x00040408, 0x10000400, 0x00000408, 0x10000000,
			0x10000400, 0x00040408, 0x10040400, 0x00040008, 0x00000000, 0x10000400, 0x10000000, 0x00000400, 0x10040008,
			0x00040000, 0x00040008, 0x10040408, 0x00040400, 0x00000008, 0x10040408, 0x00040400, 0x00040000, 0x10000408,
			0x10000008, 0x10040000, 0x00040408, 0x00000000, 0x00000400, 0x10000008, 0x10000408, 0x10040400, 0x10040000,
			0x00000408, 0x00000008, 0x10040008, };

	static final int SP6[] = { 0x00000800, 0x00000040, 0x00200040, 0x80200000, 0x80200840, 0x80000800, 0x00000840,
			0x00000000, 0x00200000, 0x80200040, 0x80000040, 0x00200800, 0x80000000, 0x00200840, 0x00200800, 0x80000040,
			0x80200040, 0x00000800, 0x80000800, 0x80200840, 0x00000000, 0x00200040, 0x80200000, 0x00000840, 0x80200800,
			0x80000840, 0x00200840, 0x80000000, 0x80000840, 0x80200800, 0x00000040, 0x00200000, 0x80000840, 0x00200800,
			0x80200800, 0x80000040, 0x00000800, 0x00000040, 0x00200000, 0x80200800, 0x80200040, 0x80000840, 0x00000840,
			0x00000000, 0x00000040, 0x80200000, 0x80000000, 0x00200040, 0x00000000, 0x80200040, 0x00200040, 0x00000840,
			0x80000040, 0x00000800, 0x80200840, 0x00200000, 0x00200840, 0x80000000, 0x80000800, 0x80200840, 0x80200000,
			0x00200840, 0x00200800, 0x80000800, };

	static final int SP7[] = { 0x04100010, 0x04104000, 0x00004010, 0x00000000, 0x04004000, 0x00100010, 0x04100000,
			0x04104010, 0x00000010, 0x04000000, 0x00104000, 0x00004010, 0x00104010, 0x04004010, 0x04000010, 0x04100000,
			0x00004000, 0x00104010, 0x00100010, 0x04004000, 0x04104010, 0x04000010, 0x00000000, 0x00104000, 0x04000000,
			0x00100000, 0x04004010, 0x04100010, 0x00100000, 0x00004000, 0x04104000, 0x00000010, 0x00100000, 0x00004000,
			0x04000010, 0x04104010, 0x00004010, 0x04000000, 0x00000000, 0x00104000, 0x04100010, 0x04004010, 0x04004000,
			0x00100010, 0x04104000, 0x00000010, 0x00100010, 0x04004000, 0x04104010, 0x00100000, 0x04100000, 0x04000010,
			0x00104000, 0x00004010, 0x04004010, 0x04100000, 0x00000010, 0x04104000, 0x00104010, 0x00000000, 0x04000000,
			0x04100010, 0x00004000, 0x00104010, };

	public static final boolean ENCRYPTION = true;
	public static final boolean DECRYPTION = false;

	public DES() {
	}

	private byte[] ecb(int[] ks, byte[] input, boolean mode)

	// ks : key schedule = array of 32 keys of 32-bit size
	// input: array of 8 bytes for a 64-bit block to be cyphered
	// mode : true = encryption, false = decryption

	{

		int left, right; // left and right part of the block
		int t, u;
		int[] tmp = new int[2];
		byte[] output = new byte[8];

		// We first structure the input in the proper way for future processing

		left = ((int) input[0] & 0xff) | (((int) input[1] & 0xff) << 8) | (((int) input[2] & 0xff) << 16)
				| (((int) input[3] & 0xff) << 24);

		right = ((int) input[4] & 0xff) | (((int) input[5] & 0xff) << 8) | (((int) input[6] & 0xff) << 16)
				| (((int) input[7] & 0xff) << 24);

		// We perform the initial permutation
		tmp = iperm(left, right);
		left = tmp[0];
		right = tmp[1];

		// If ENCRYPTION (mode = true)
		if (mode) {
			for (int i = 0; i < 32; i += 4) {
				t = ((right << 1) | (right >>> 31)) & 0xffffffff;
				u = t ^ ks[i];
				t = t ^ ks[i + 1];
				t = ((t >>> 4) | (t << 28)) & 0xffffffff;
				left ^= SP1[t & 0x3f] | SP3[(t >>> 8) & 0x3f] | SP5[(t >>> 16) & 0x3f] | SP7[(t >>> 24) & 0x3f]
						| SP0[u & 0x3f] | SP2[(u >>> 8) & 0x3f] | SP4[(u >>> 16) & 0x3f] | SP6[(u >>> 24) & 0x3f];

				t = ((left << 1) | (left >>> 31)) & 0xffffffff;
				u = t ^ ks[i + 2];
				t = t ^ ks[i + 3];
				t = ((t >>> 4) | (t << 28)) & 0xffffffff;
				right ^= SP1[t & 0x3f] | SP3[(t >>> 8) & 0x3f] | SP5[(t >>> 16) & 0x3f] | SP7[(t >>> 24) & 0x3f]
						| SP0[u & 0x3f] | SP2[(u >>> 8) & 0x3f] | SP4[(u >>> 16) & 0x3f] | SP6[(u >>> 24) & 0x3f];
			} // end of for loop
		} // end of if statement

		else
		// else DECRYPTION (mode = false)
		{
			for (int i = 30; i > 0; i -= 4) {
				t = ((right << 1) | (right >>> 31)) & 0xffffffff;
				u = t ^ ks[i];
				t = t ^ ks[i + 1];
				t = ((t >>> 4) | (t << 28)) & 0xffffffff;
				left ^= SP1[t & 0x3f] | SP3[(t >>> 8) & 0x3f] | SP5[(t >>> 16) & 0x3f] | SP7[(t >>> 24) & 0x3f]
						| SP0[u & 0x3f] | SP2[(u >>> 8) & 0x3f] | SP4[(u >>> 16) & 0x3f] | SP6[(u >>> 24) & 0x3f];

				t = ((left << 1) | (left >>> 31)) & 0xffffffff;
				u = t ^ ks[i - 2];
				t = t ^ ks[i - 1];
				t = ((t >>> 4) | (t << 28)) & 0xffffffff;
				right ^= SP1[t & 0x3f] | SP3[(t >>> 8) & 0x3f] | SP5[(t >>> 16) & 0x3f] | SP7[(t >>> 24) & 0x3f]
						| SP0[u & 0x3f] | SP2[(u >>> 8) & 0x3f] | SP4[(u >>> 16) & 0x3f] | SP6[(u >>> 24) & 0x3f];
			} // end of for loop
		} // end of else statement

		// We perform the final permutation
		tmp = fperm(left, right);
		left = tmp[0];
		right = tmp[1];

		// We put together both left and right part
		output[0] = (byte) (left & 0xff);
		output[1] = (byte) (left >>> 8);
		output[2] = (byte) (left >>> 16);
		output[3] = (byte) (left >>> 24);
		output[4] = (byte) (right & 0xff);
		output[5] = (byte) (right >> 8);
		output[6] = (byte) (right >> 16);
		output[7] = (byte) (right >> 24);

		return output;

	} // end of method ecb

	private int[] fperm(int left, int right)
	// left : left part of the block to be cyphered
	// right : right part of the block to be cyphered
	// the function returns an array of two integers corresponding to the
	// permuted input
	{
		int t;
		int[] out = new int[2];

		t = ((right >>> 1) ^ left) & 0x55555555;
		right ^= (t << 1);
		left ^= t;
		t = ((left >>> 8) ^ right) & 0x00ff00ff;
		left ^= (t << 8);
		right ^= t;
		t = ((right >>> 2) ^ left) & 0x33333333;
		right ^= (t << 2);
		left ^= t;
		t = ((left >> 16) ^ right) & 0x0000ffff;
		left ^= (t << 16);
		right ^= t;
		t = ((right >>> 4) ^ left) & 0x0f0f0f0f;
		right ^= (t << 4);
		left ^= t;
		left &= 0xffffffff;
		right &= 0xffffffff;

		out[0] = left;
		out[1] = right;

		return out;
	}

	private int[] iperm(int left, int right)
	// left : left part of the block to be cyphered
	// right : right part of the block to be cyphered
	// the function returns an array of two integers corresponding to the
	// permuted input
	{
		int t;
		int[] out = new int[2];

		t = ((right >>> 4) ^ left) & 0x0f0f0f0f;
		right ^= (t << 4);
		left ^= t;
		t = ((left >>> 16) ^ right) & 0x0000ffff;
		left ^= (t << 16);
		right ^= t;
		t = ((right >>> 2) ^ left) & 0x33333333;
		right ^= (t << 2);
		left ^= t;
		t = ((left >>> 8) ^ right) & 0x00ff00ff;
		left ^= (t << 8);
		right ^= t;
		t = ((right >>> 1) ^ left) & 0x55555555;
		right ^= (t << 1);
		left ^= t;
		t = left;
		left = right & 0xffffffff;
		right = t & 0xffffffff;

		out[0] = left;
		out[1] = right;

		return out;
	}

	public static void main(String args[]) {
		byte src[] = { -100, -111, 56, -103, -122, 42, 36, -9, -115, 99, 64, -78, 53, 114, 18, 19 };
		String key = "ZHONGMC";

		byte ks[] = new byte[20];

		System.arraycopy(key.getBytes(), 0, ks, 0, key.length());

		DES des = new DES();
		byte dst[] = des.processByteArray(ks, src, false);
	}

	public byte[] encrypt(String key, byte[] src) {
		return encrypt(key.getBytes(), src);
	}

	public byte[] encrypt(byte[] key, byte[] src) {
		byte[] keyBuf = new byte[8];

		int keyLen = key.length; // ();
		if (keyLen > 8)
			keyLen = 8;

		System.arraycopy(key, 0, keyBuf, 0, keyLen);

		int length = src.length;

		byte[] srcBuf;

		if ((length % 8) > 0) {
			int len = length;
			length = 8 * (length / 8) + 8;
			srcBuf = new byte[length];
			System.arraycopy(src, 0, srcBuf, 0, len);
			for (int i = len; i < length; i++)
				srcBuf[i] = (byte) 0x20;
		} else
			srcBuf = src;

		return processByteArray(keyBuf, srcBuf, ENCRYPTION);

	}

	public byte[] decrypt(String key, byte[] src) {
		return decrypt(key.getBytes(), src);
	}

	public byte[] decrypt(byte[] key, byte[] src) {
		byte[] keyBuf = new byte[8];

		int keyLen = key.length; // ();
		if (keyLen > 8)
			keyLen = 8;

		System.arraycopy(key, 0, keyBuf, 0, keyLen);

		byte[] decBuf = processByteArray(keyBuf, src, this.DECRYPTION);

		int length = decBuf.length;

		int len = length;
		int count = 0;
		while (decBuf[len - 1] == 0x20) {
			count++;
			len--;
			if (len == 0)
				break;
		}

		if (count > 0) // trim the padding ' '
		{
			byte[] tmp = new byte[length - count];
			System.arraycopy(decBuf, 0, tmp, 0, length - count);
			return tmp;
		} else
			return decBuf;

	}

	private byte[] processByteArray(byte[] key, byte[] input, boolean mode) {
		int[] ks = new int[32];

		byte[] in_block = new byte[8];
		byte[] out_block = new byte[8];
		byte[] output = new byte[input.length];

		ks = set_key(key);

		for (int i = 0, j = input.length / 8; i < j; i++) {
			System.arraycopy(input, 8 * i, in_block, 0, 8);
			out_block = ecb(ks, in_block, mode);
			System.arraycopy(out_block, 0, output, 8 * i, 8);
		}
		return output;
	}

	private int[] set_key(byte[] key) {
		int ks[] = new int[32];
		int c, d, s, t, z;

		c = ((int) key[0] & 0xff) | (((int) key[1] & 0xff) << 8) | (((int) key[2] & 0xff) << 16)
				| (((int) key[3] & 0xff) << 24);

		d = ((int) key[4] & 0xff) | (((int) key[5] & 0xff) << 8) | (((int) key[6] & 0xff) << 16)
				| (((int) key[7] & 0xff) << 24);

		t = ((d >> 4) ^ c) & 0x0f0f0f0f;
		d ^= (t << 4);
		c ^= t;

		// C do c first
		t = ((c << 18) ^ c) & 0xcccc0000;
		c = c ^ t ^ (t >>> 18);
		t = ((c << 17) ^ c) & 0xaaaa0000;
		c = c ^ t ^ (t >>> 17);
		t = ((c << 8) ^ c) & 0x00ff0000;
		c = c ^ t ^ (t >>> 8);
		t = ((c << 17) ^ c) & 0xaaaa0000;
		c = c ^ t ^ (t >>> 17);

		// C now do d
		t = ((d << 24) ^ d) & 0xff000000;
		d = d ^ t ^ (t >>> 24);
		t = ((d << 8) ^ d) & 0x00ff0000;
		d = d ^ t ^ (t >>> 8);
		t = ((d << 14) ^ d) & 0x33330000;
		d = d ^ t ^ (t >>> 14);
		d = ((d & 0x00aa00aa) << 7) | ((d & 0x55005500) >>> 7) | (d & 0xaa55aa55);
		d = (d >>> 8) | ((c & 0xf0000000) >>> 4);
		c &= 0x0fffffff;

		for (int i = 0; i < 16; i++) {
			if (shifts2[i]) {
				c = (c >>> 2) | (c << 26);
				d = (d >>> 2) | (d << 26);
			} else {
				c = (c >>> 1) | (c << 27);
				d = (d >>> 1) | (d << 27);
			}

			c &= 0x0fffffff;
			d &= 0x0fffffff;

			s = skb0[c & 0x3f] | skb1[(((c >>> 6) & 0x03) | ((c >>> 7) & 0x3c))]
					| skb2[(((c >>> 13) & 0x0f) | ((c >>> 14) & 0x30))]
					| skb3[(((c >>> 20) & 0x01) | ((c >>> 21) & 0x06) | ((c >>> 22) & 0x38))];

			t = skb4[(d & 0x3f)] | skb5[(((d >>> 7) & 0x03) | ((d >>> 8) & 0x3c))] | skb6[((d >>> 15) & 0x3f)]
					| skb7[(((d >>> 21) & 0x0f) | ((d >>> 22) & 0x30))];

			ks[2 * i] = ((t << 16) | (s & 0x0000ffff)) & 0xffffffff;
			s = (s >>> 16) | (t & 0xffff0000);
			ks[2 * i + 1] = ((s << 4) | (s >>> 28)) & 0xffffffff;

		} // end of for loop

		return ks;
	}

	/**
	 * This static method is used to get messages with a length compatible with
	 * a byte representation <=> length % 8 = 0. To do so, we add some blank
	 * characters (up to 7) at the end of the string. If we really want to get
	 * the exact string, we would have to store the length somewhere.
	 * 
	 * @param String
	 *            in the input String
	 * @return the padded String. At most, the length is increased by 7.
	 */
	protected static String strPadding(String in) {
		int t = in.length() % 8;
		if (t == 0)
			return in;
		else {
			StringBuffer buf = new StringBuffer(in);
			for (int i = 0; i < ((8 - t) % 8); i++)
				buf.append(" ");
			return buf.toString();
		}
	}

}
