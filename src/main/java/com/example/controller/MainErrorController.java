package com.example.controller;

import java.io.PrintStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;

@Controller
public class MainErrorController
  implements ErrorController
{
  public String getErrorPath()
  {
    return null;
  }

  public String handlerError(HttpServletRequest req, HttpServletResponse resp)
  {
    System.out.println(resp.getStatus() + "          !");
    if (resp.getStatus() == 404) {
      return "forward:404.html";
    }
    if (resp.getStatus() == 500) {
      return "forward:500.html";
    }
    return "forward:index.html";
  }
}