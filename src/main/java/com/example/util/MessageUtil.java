package com.example.util;

import com.example.domain.Image;
import com.example.domain.ImageMessage;
import com.example.domain.News;
import com.example.domain.NewsMessage;
import com.example.domain.TextMessage;
import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class MessageUtil {
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "CLICK";
	public static final String MESSAGE_VIEW = "VIEW";
	public static final String MESSAGE_SCANCODE_PUSH = "scancode_push";


	public static String textMessage2Xml(TextMessage textMessage) {
		XStream xStream = new XStream();
		xStream.alias("xml", textMessage.getClass());
		return xStream.toXML(textMessage);
	}

	public static String newsMessage2Xml(NewsMessage newsMessage) {
		XStream xStream = new XStream();
		xStream.alias("xml", newsMessage.getClass());
		xStream.alias("item", new News().getClass());
		return xStream.toXML(newsMessage);
	}

	public static String initText(String toUserName, String fromUserName, String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType("text");
		text.setCreateTime(Long.valueOf(new Date().getTime()));
		text.setContent(content);
		return textMessage2Xml(text);
	}

	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("感谢您的关注,请按照菜单提示进行操作:\n\n");
		sb.append("1、员工\n");
		sb.append("2、业务\n");
		sb.append("3、网红1\n");
		sb.append("4、网红2\n");
		sb.append("回复?调出此菜单");
		return sb.toString();
	}

	public static String menu1() {
		StringBuffer sb = new StringBuffer();
		sb.append("员工");
		return sb.toString();
	}

	public static String menu2() {
		StringBuffer sb = new StringBuffer();
		sb.append("记录");
		return sb.toString();
	}

	public static String initNewsMessage(String toUserName, String fromUserName) {
		String message = null;
		List newsList = new ArrayList();
		NewsMessage newsMessage = new NewsMessage();

		News news = new News();
		news.setTitle("wow群");
		news.setDescription("九区十八组--太阳之井 vip休闲公会");
		news.setPicUrl("http://mytest.tunnel.qydev.com/img/2.jpg");
		news.setUrl("http://bbs.ngacn.cc/thread.php?fid=-7&rand=741");
		newsList.add(news);

		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(Long.valueOf(new Date().getTime()));
		newsMessage.setMsgType("news");
		newsMessage.setArticleCount(newsList.size());
		newsMessage.setArticles(newsList);

		message = newsMessage2Xml(newsMessage);
		return message;
	}

	public static String initImageMessage(String toUserName, String fromUserName) throws Exception {
		String message = null;
		Image image = new Image();

		image.setMediaId("YffXPBoA4fokFZHMxVeQzX5_nXIxByKQqiq7jKKzdMhOj2L98-Q-9k9-Fq-D2XPW");
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setFromUserName(toUserName);
		imageMessage.setToUserName(fromUserName);
		imageMessage.setCreateTime(Long.valueOf(new Date().getTime()));
		imageMessage.setMsgType("image");
		imageMessage.setImage(image);
		message = imageMessage2Xml(imageMessage);
		return message;
	}

	public static String imageMessage2Xml(ImageMessage imageMessage) {
		XStream xStream = new XStream();
		xStream.alias("xml", imageMessage.getClass());

		return xStream.toXML(imageMessage);
	}

	public static Map<String, String> xml2Map(HttpServletRequest request)
		    throws IOException, DocumentException
		  {
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
}