package com.example.controller;

import com.example.domain.AccessToken;
import com.example.util.CheckUtil;
import com.example.util.MessageUtil;
import com.example.util.WeixinUtil;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/wx.do"})
public class WexinServlet
{
  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public void CheckWexin(HttpServletRequest request, HttpServletResponse resp)
    throws Exception
  {
    String signature = request.getParameter("signature");
    String timestamp = request.getParameter("timestamp");
    String nonce = request.getParameter("nonce");
    String echostr = request.getParameter("echostr");

    PrintWriter out = resp.getWriter();
    if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
      AccessToken token = WeixinUtil.getToken();
      String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
      WeixinUtil.createMenu(token.getToken(), menu);
      out.print(echostr);
    }
  }

  @RequestMapping(method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public void reMessage(HttpServletRequest request, HttpServletResponse resp) throws IOException {
    request.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    PrintWriter out = resp.getWriter();
    try {
      Map map = MessageUtil.xml2Map(request);
      String fromUserName = (String)map.get("FromUserName");
      String toUserName = (String)map.get("ToUserName");
      String msgType = (String)map.get("MsgType");
      String content = (String)map.get("Content");

      String message = null;
      if ("text".equals(msgType)) {
        if ("1".equals(content)) {
          message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menu1());
          System.out.println("fromUserName:" + fromUserName);
        } else if ("2".equals(content)) {
          message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menu2());
        } else if ("3".equals(content)) {
          message = MessageUtil.initNewsMessage(toUserName, fromUserName);
        } else if ("4".equals(content)) {
          message = MessageUtil.initImageMessage(toUserName, fromUserName);
        } else if (("?".equals(content)) || ("？".equals(content))) {
          message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
        }
      } else if ("event".equals(msgType)) {
        String eventType = (String)map.get("Event");
        if ("subscribe".equals(eventType)) {
          message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
        } else if ("CLICK".equals(eventType)) {
          message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
        }
        else if ("VIEW".equals(eventType)) {
          String url = (String)map.get("EventKey");
          message = MessageUtil.initText(toUserName, fromUserName, url);
        } else if ("scancode_push".equals(eventType)) {
          String url = (String)map.get("ScanCodeInfo");
          message = MessageUtil.initText(toUserName, fromUserName, url);
        }
      } else if ("location".equals(msgType)) {
        String Label = (String)map.get("Label");
        message = MessageUtil.initText(toUserName, fromUserName, Label);
      }
      out.print(message);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      out.close();
    }
  }
}