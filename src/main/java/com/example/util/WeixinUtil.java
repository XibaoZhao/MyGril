package com.example.util;

import com.example.domain.AccessToken;
import com.example.menu.Button;
import com.example.menu.ClickButton;
import com.example.menu.Menu;
import com.example.menu.ViewButton;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONException;
import org.json.JSONObject;

public class WeixinUtil {
	private static String APPID = "wx430c335a0c013ec3";
	private static String APPSECRET = "8439794490432762941b42a0b9a86e9f";
	private static String WEIXIN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static String UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	private static String WEIXIN_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	public static Map<String, String> xml2Map(HttpServletRequest request) throws IOException, DocumentException {
		Map map = new HashMap();
		SAXReader reader = new SAXReader();

		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);

		Element root = doc.getRootElement();

		List<Element> list = root.elements();
		
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}

		ins.close();

		return map;
	}
	
	
	public static JSONObject doGet(String url) throws MalformedURLException, IOException, JSONException {
		URLConnection connection = new URL(url).openConnection();
		connection.connect();
		InputStream ins = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));

		StringBuffer buffer = new StringBuffer();
		String temp = null;

		while ((temp = br.readLine()) != null) {
			buffer.append(temp);
		}

		JSONObject json = new JSONObject(buffer.toString());

		return json;
	}

	public static JSONObject doPostStr(String url, String outStr) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(outStr, "utf-8"));
		JSONObject json = null;
		try {
			CloseableHttpResponse response = httpClient.execute(post);
			String result = EntityUtils.toString(response.getEntity());
			json = new JSONObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}

	public static AccessToken getToken() throws Exception {
		String url = WEIXIN_URL.replace("APPID", APPID).replace("APPSECRET", APPSECRET);
		AccessToken token = new AccessToken();
		JSONObject json = doGet(url);
		if (json != null) {
			token.setExpires(json.getInt("expires_in"));
			token.setToken(json.getString("access_token"));
		}
		return token;
	}

	public static String upLoadUtil(String filePath, String accessToken, String type) throws IOException {
		File file = new File(filePath);
		if ((!file.exists()) || (!file.isFile())) {
			throw new IOException("文件不存在");
		}

		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		URLConnection connection = new URL(url).openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setUseCaches(false);

		connection.setRequestProperty("Connection", "Keep-Alive");
		connection.setRequestProperty("Charset", "UTF-8");

		String BOUNDARY = new StringBuilder().append("-------").append(System.currentTimeMillis()).toString();
		connection.setRequestProperty("Content-type",
				new StringBuilder().append("multipart/form-data;boundary=").append(BOUNDARY).toString());
		StringBuilder sb = new StringBuilder();

		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append(new StringBuilder().append("Content-Disposition:form-data;name=\"file\";filename=\"")
				.append(file.getName()).append("\"\r\n").toString());
		sb.append("Content-type:application/octet-stream\r\n\r\n");

		byte[] head = sb.toString().getBytes("utf-8");

		OutputStream out = new DataOutputStream(connection.getOutputStream());

		out.write(head);

		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = dis.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		dis.close();

		byte[] foot = new StringBuilder().append("\r\n--").append(BOUNDARY).append("--\r\n").toString()
				.getBytes("utf-8");
		out.write(foot);
		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try {
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			result = buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
		String mediaId = null;
		if (result != null) {
			try {
				JSONObject json = new JSONObject(result);
				System.out.println(json);
				mediaId = json.getString("media_id");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return mediaId;
	}

	public static Menu initMenu() {
		Menu menu = new Menu();

		ClickButton button11 = new ClickButton();
		button11.setName("Click菜单1");
		button11.setType("click");
		button11.setKey("11");

		ViewButton button21 = new ViewButton();
		button21.setName("view菜单1");
		button21.setType("view");
		button21.setUrl("http://bbs.ngacn.cc");

		ClickButton button31 = new ClickButton();
		button31.setName("扫码事件");
		button31.setType("scancode_push");
		button31.setKey("31");

		ClickButton button32 = new ClickButton();
		button32.setName("地理位置");
		button32.setType("location_select");
		button32.setKey("32");

		Button button = new Button();
		button.setName("菜单");
		button.setSub_button(new Button[] { button31, button32 });

		menu.setButton(new Button[] { button11, button21, button });
		return menu;
	}

	public static int createMenu(String token, String menu) {
		int result = 0;
		String url = WEIXIN_MENU.replace("ACCESS_TOKEN", token);
		JSONObject jsonObject = doPostStr(url, menu);
		if (jsonObject != null) {
			try {
				result = jsonObject.getInt("errcode");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}