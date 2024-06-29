package com.turing.code.common.utils.encrypt;

import com.turing.code.common.utils.CommUtil;
import com.turing.code.common.utils.PathUtil;
import com.turing.code.common.utils.UUIDUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("restriction")
public class Base64Util {

    private static final String BASE_PATH = "/files";
    private static BASE64Decoder decoder = new BASE64Decoder();

    /**
     * @param imgStr base64编码字符串
     * @param path   图片路径-具体到文件
     * @return
     * @Description: 将base64编码字符串转换为图片
     */
    public static String generateImage(HttpServletRequest request, String path, String imgStr, boolean isFull) {
        path = BASE_PATH + path;
        if (imgStr == null) return null;
        try {
            String suffix = "";
            String regex = "data:image/(.*?);base64,";
            Pattern pattern = Pattern.compile(regex);
            Matcher m = pattern.matcher(imgStr);
            if (m.find()) {
                suffix = m.group(1);
            }
            String img = imgStr.replace("data:image/" + suffix + ";base64,", "");
            // 解密
            byte[] b = decoder.decodeBuffer(img);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            String fileName = UUIDUtil.getCode() + "." + suffix;
            String basePath = PathUtil.getFullRealPath(request) + path;
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            OutputStream out = new FileOutputStream(basePath + "/" + fileName);
            out.write(b);
            out.flush();
            out.close();
            if (isFull) {
                return PathUtil.getDomain(request) + path + fileName;
            } else {
                return path + fileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String generateImage(HttpServletRequest request, String path, byte[] imageData, boolean isFull) {
        path = BASE_PATH + path;
        if (imageData == null) return null;
        try {
            String fileName = UUIDUtil.getCode() + ".jpg";  // 使用 jpg 作为默认格式
            String basePath = PathUtil.getFullRealPath(request) + path;
            File file = new File(basePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            OutputStream out = new FileOutputStream(basePath + "/" + fileName);
            out.write(imageData);
            out.flush();
            out.close();
            if (isFull) {
                return PathUtil.getDomain(request) + path + fileName;
            } else {
                return path + fileName;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param imgStr base64编码字符串集合
     * @param path   图片路径-具体到文件
     * @return List上传成功后的路径集合
     * @Description: 将base64编码字符串集合 转换为图片
     */
    public static List<String> generateImages(HttpServletRequest request, String path, List<String> imgStr) {
        path = BASE_PATH + path;
        if (imgStr == null) return null;
        String fileName = null;
        List<String> list = new ArrayList<String>();
        String basePath = PathUtil.getFullRealPath(request) + path;
        File file = new File(basePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            for (int i = 0; i < imgStr.size(); i++) {
                if (imgStr.get(i).startsWith("data:image")) {
                    String img = imgStr.get(i).replaceAll("data:image/(.*?);base64,", "");
                    // 解密
                    byte[] b = decoder.decodeBuffer(img);

                    // 处理数据
                    for (int j = 0; j < b.length; ++j) {
                        if (b[j] < 0) {
                            b[j] += 256;
                        }
                    }
                    fileName = UUIDUtil.getCode() + ".jpeg";
                    String filePath = basePath + "/" + fileName;
                    OutputStream out = new FileOutputStream(filePath);
                    out.write(b);
                    out.flush();
                    out.close();
                    list.add(path + fileName);
                } else {
                    list.add(imgStr.get(i));
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * @return
     * @Description: 根据图片地址转换为base64编码字符串
     * @Author:
     * @CreateTime:
     */
    public static String getImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 通过图片链接下载图片到本地
     *
     * @param request
     * @param imageUrl
     * @return
     * @throws IOException
     */
    public static String getImageForUrl(HttpServletRequest request, String imageUrl, String relativePath) throws IOException {
        //new一个URL对象
        URL url = new URL(imageUrl);
        //打开链接  
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置请求方式为"GET"  
        conn.setRequestMethod("GET");
        //超时响应时间为5秒  
        conn.setConnectTimeout(5 * 1000);
        //通过输入流获取图片数据
        InputStream inStream = null;
        try {
            inStream = conn.getInputStream();
        } catch (Exception e) {
            return null;
        }
        //得到图片的二进制数据，以二进制封装得到数据，具有通用性  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        //创建一个Buffer字符串  
        byte[] buffer = new byte[1024];
        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
        int len = 0;
        //使用一个输入流从buffer里把数据读取出来  
        while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
            outStream.write(buffer, 0, len);
        }
        //关闭输入流  
        inStream.close();
        byte[] data = outStream.toByteArray();
        //new一个文件对象用来保存图片，默认保存当前工程根目录
        String basePath = PathUtil.getFullRealPath(request) + relativePath;
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = UUIDUtil.getCode() + ".jpeg";
        File imageFile = new File(basePath + fileName);
        //创建输出流  
        FileOutputStream outStream2 = new FileOutputStream(imageFile);
        //写入数据  
        outStream2.write(data);
        //关闭输出流  
        outStream2.close();
        return relativePath + fileName;
    }

}
