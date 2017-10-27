package com.example.test;

import com.example.domain.AccessToken;
import com.example.util.WeixinUtil;
import java.io.PrintStream;
import java.util.Arrays;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

public class WeixinTest {
	@Test
	public void test() throws Exception {
		AccessToken token = WeixinUtil.getToken();
	}

	public static void main(String[] args) throws Exception {
		AccessToken token = WeixinUtil.getToken();

		String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();

		int result = WeixinUtil.createMenu(token.getToken(), menu);

		int[] a = { 1, 2, 3, 4, 5, 6, 7 };
		ArrayUtils.reverse(a);
		System.out.println(Arrays.toString(a));
	}
}