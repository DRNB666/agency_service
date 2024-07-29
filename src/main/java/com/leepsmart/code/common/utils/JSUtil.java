package com.leepsmart.code.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JSUtil {
    public static void alert(HttpServletResponse response, String str, String url) throws IOException {
        response.setContentType("text/html; charset=UTF-8"); //转码
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println("alert('" + str + "');");
        out.println("window.location.href='" + url + "'");
        out.println("</script>");
        out.flush();
    }
}
