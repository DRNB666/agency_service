package com.leepsmart.code.common.utils;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static JSONObject typeObj;

    private static String BASE_PATH = "/files";

    /**
     * 上传单个文件
     *
     * @param relativePath 相对路径
     * @param file         文件
     * @param fileType     文件类型
     * @return 文件路径
     */
    public static String upload(String relativePath, MultipartFile file, String fileType, HttpServletRequest request) {
        relativePath = BASE_PATH + relativePath;
        String basePath = PathUtil.getFullRealPath(request) + relativePath;
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName;
        if (!file.isEmpty()) {

            try {
                String name = file.getOriginalFilename();
                fileName = checkFile(name, fileType);
                //判断文件类型并返回文件名
                if (fileName != null) {
                    File realFile = new File(basePath + fileName);
                    file.transferTo(realFile);
                } else {
                    return null;
                }
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                return null;
            }
            return relativePath + fileName;
        } else {
            return null;
        }
    }

    public static String upload(String relativePath, File file, String fileType, HttpServletRequest request) {
        relativePath = BASE_PATH + relativePath;
        String basePath = PathUtil.getFullRealPath(request) + relativePath;
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName;
        if (file != null && file.isFile()) { // 检查file是否为文件类型
            try {
                String name = file.getName(); // 获取文件名
                fileName = checkFile(name, fileType); // 检查文件类型并返回文件名
                if (fileName != null) {
                    File realFile = new File(basePath + fileName);
                    // 使用文件复制而不是 MultipartFile 的 transferTo 方法
                    org.apache.commons.io.FileUtils.copyFile(file, realFile);
                } else {
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return relativePath + fileName;
        } else {
            return null;
        }
    }

    /**
     * 上传多个文件
     *
     * @param relativePath 相对路径
     * @param files        文件
     * @return 完整路径名列表
     */
    public static List<String> uploads(String relativePath, MultipartFile[] files, String fileType) {
        String basePath = PathUtil.getFullRealPath() + relativePath;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isEmpty()) {
                try {
                    String name = files[i].getOriginalFilename(); //获取原文件名
                    String fileName = checkFile(name, fileType); //获取新文件名
                    //判断文件类型并返回文件名
                    if (fileName != null) {
                        File realFile = new File(basePath + fileName);
                        files[i].transferTo(realFile);
                        list.add(relativePath + fileName);
                    } else {
                        return null;
                    }
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }
        }
        return list;
    }

    /**
     * 单文件删除
     *
     * @param fileName 文件名
     * @return 返回boolean 删除成功或失败
     */
    public static boolean delete(String fileName) {
        String basePath = PathUtil.getFullRealPath() + fileName;
        LogUtil.info("删除单个文件，路径：{}", basePath);
        File file = new File(basePath);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                LogUtil.info("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                LogUtil.info("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            LogUtil.info("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 判断是否为允许的上传文件类型
     *
     * @return null或文件名
     */
    public static String checkFile(String fileName, String type) {
        if (typeObj == null) {
            typeObj = new JSONObject();
            typeObj.put("image", "jpg,gif,png,ico,bmp,jpeg");
            typeObj.put("file", "txt,doc,docx,xlsx,xls,pdf,jpg,gif,png,ico,bmp,jpeg");
            typeObj.put("video", "mp4,avi,mp3,3gp,mkv");
        }
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (type == null) {
            return UUIDUtil.getCode() + "." + suffix.trim().toLowerCase();
        } else {
            String suffixList = (String) typeObj.get(type);
            if (suffixList.contains(suffix.trim().toLowerCase())) {
                return UUIDUtil.getCode() + "." + suffix.trim().toLowerCase();
            }
        }
        return null;
    }


    /**
     * 下载
     *
     * @param request  文件名(相对路径)
     * @param response
     * @return
     */
    public static boolean downloadFile(HttpServletRequest request, HttpServletResponse response) {
        String fileName = request.getParameter("fileName");
        if (fileName != null) {
            File file = new File(PathUtil.getFullRealPath(), fileName);
            if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }


    /**
     * FileWriter类文件写入
     *
     * @throws IOException
     */
    public static void writeFile(String fileName, List<String> fileContent) throws IOException {
        FileWriter fileWriter = null;
        try {
            //使用这个构造函数时，如果存在fileName文件，
            //则先把这个文件给删除掉，然后创建新的fileName
            fileWriter = new FileWriter(fileName);
            for (int i = 0; i < fileContent.size(); i++) {
                fileWriter.write(fileContent.get(i) + "\n");
            }
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
