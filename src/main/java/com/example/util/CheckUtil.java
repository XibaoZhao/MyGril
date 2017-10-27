package com.example.util;

import java.util.Arrays;

public class CheckUtil {
	private static final String token = "imooc1";

	public static boolean checkSignature(String signature, String timestamp, String nonce) {
		String[] arr = { "imooc1", timestamp, nonce };

		Arrays.sort(arr);

		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}

		String temp = SHA1.encode(content.toString());

		return temp.equals(signature);
	}
}