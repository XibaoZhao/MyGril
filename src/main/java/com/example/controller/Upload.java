package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@Component
public class Upload
{

  @Value("${web.upload-path}")
  String outUrl;

  @RequestMapping({"/imgUpload"})
  @ResponseBody
  public String upload(HttpServletRequest request, MultipartFile[] files)
    throws IOException
  {
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request
      .getSession().getServletContext());
    String result = "";
    if (multipartResolver.isMultipart(request)) {
      MultipartHttpServletRequest mr = (MultipartHttpServletRequest)request;
      Iterator iter = mr.getFileNames();
      try {
        MultipartFile file = mr.getFile((String)iter.next());
        File f = new File(this.outUrl, file.getOriginalFilename());
        file.transferTo(f);
        result = file.getOriginalFilename();
      } catch (IllegalStateException e) {
        result = "FALSE";
        e.printStackTrace();
      }
    }
    return result;
  }
}