package com.leepsmart.code.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

@Component
public class PathUtil {

    @Value("${upLoadDir}")
    private String upLoadDir;
    private static String sUpLoadDir;

    @PostConstruct
    public void init(){
        sUpLoadDir=upLoadDir;
    }
    /**
     * 获取文件上传绝对路径
     *
     * @return
     */
    public static String getFullRealPath() {
        return System.getProperty("catalina.home") + "/webapps/";
    }



    /**
     * 获取文件上传绝对路径
     *
     * @param request
     * @return
     */
    public static String getFullRealPath(HttpServletRequest request) {
//        String path = request.getSession().getServletContext().getRealPath("");
//        if ("".equals(request.getContextPath())) {
//            path = path.replace("ROOT", "");
//        } else {
//            path = path.replace(request.getContextPath().replace("/", ""), "");
//        }
//        return path;
        return sUpLoadDir;
    }

    /**
     * 获取域名(http://xx.xx.xx)
     *
     * @param request
     * @return
     */
    public static String getDomain(HttpServletRequest request) {
        String head = request.getRequestURL().toString().startsWith("https") ? "https://" : "http://";
        String port = (request.getServerPort() == 80 || request.getServerPort() == 443) ? "" : (":" + request.getServerPort());
        return head + request.getServerName() + port;
    }

    /**
     * 获取域名+项目名称(http://xx.xx.xx/xx)
     *
     * @param request
     * @return
     */
    public static String getFileUrl(HttpServletRequest request) {
        String head = request.getRequestURL().toString().startsWith("https") ? "https://" : "http://";
        String port = (request.getServerPort() == 80 || request.getServerPort() == 443) ? "" : (":" + request.getServerPort());
        return head + request.getServerName() + port + request.getRequestURI();
    }

}
