package com.turing.code.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.turing.code.common.ex.ServiceException;
import com.turing.code.common.swagger.apifox.ApiFoxEndpointOverwriteBehavior;
import com.turing.code.common.swagger.apifox.ApiFoxVersion;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Turing
 */
public class CommUtil {

    /**
     * 生成随机字符串
     *
     * @param length     字符串长度
     * @param numberFlag 是否纯数字 true:纯数字 false:数英混合
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    /**
     * 非空验证
     * 可校验多个参数，多个数据类型，其中一个为空或者长度为0则返回false,全部不为空或者不为0则返回true
     * @param str 参数类型...
     */
    public static boolean checkNull(Object... str) {
        if (null == str) {
            return false;
        }
        for (Object o : str) {
            if (null == o || "".equals(o) || "[]".equals(o)) {
                return false;
            }
            if (o instanceof Object[]) {
                Object[] array = (Object[]) o;
                if (array.length == 0) {
                    return false;
                }
            }
            if (o instanceof List<?>) {
                List<?> list = (List<?>) o;
                if (list.isEmpty()||list.get(0)==null) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 校验JSON对象是否包含key，并且key不为空
     * @param obj json对象
     * @param hasPojo 是否为数据库实体类
     * @param keys 需要校验的键（可多个）
     */
    public static boolean checkNullJSON(JSONObject obj,Boolean hasPojo,String... keys) {
        if (!CommUtil.checkNull(obj, keys)) {
            return false;
        }
        String replace="";
        if (hasPojo){
            String[] replaces = keys[0].split("\\.");
            if (CommUtil.checkNull((Object) replaces)){
                replace=replaces[0]+".";
            }
        }
        for (String key : keys) {
            key=key.replace(replace,"");
            if (!obj.containsKey(key)) {
                throw new ServiceException(String.format("参数%s不能为空",key));
            }
            if (!CommUtil.checkNull(obj.get(key))) {
                throw new ServiceException(String.format("参数%s不能为空",key));
            }
        }
        return true;
    }


    /**
     * 获取客户端真实IP
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = "123";//
        if (request != null) {
            ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        }
        return ip;
    }


    /**
     * 执行cmd命令
     *
     * @param cmd cmd命令字符串
     * @return 成功或失败
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws IOException
     */
    public static boolean runCmd(String cmd) throws InterruptedException, ExecutionException, IOException {
        LogUtil.info(cmd);
        // 执行cmd命令
        Process process = Runtime.getRuntime().exec(cmd);
        // 读取输入流与错误信息
        InputStream stream = process.getInputStream();
        InputStream error = process.getErrorStream();

        CompletableFuture<String> t1 = CompletableFuture.supplyAsync(() -> {
            BufferedReader br1 = new BufferedReader(new InputStreamReader(stream));
            String line1;
            StringBuilder sb = new StringBuilder();
            try {
                while ((line1 = br1.readLine()) != null) {
                    sb.append(line1).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        });

        CompletableFuture<String> t2 = CompletableFuture.supplyAsync(() -> {
            BufferedReader br2 = new BufferedReader(new InputStreamReader(error));
            StringBuilder sb = new StringBuilder();
            try {
                String line2;
                while ((line2 = br2.readLine()) != null) {
                    sb.append(line2).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    error.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        });
        process.waitFor();
//        process.destroy();
        int status = process.exitValue();
        LogUtil.info(t1.get());
        if (status != 0) {
            System.err.println(t2.get());
            return false;
        }
        return true;
    }

    /**
     * 唯一Id 数字
     * @return
     */
    public static String getUUID() {
        int first = new Random().nextInt(899) + 100;
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        return first + String.format("%011d", hashCodeV);
    }

    /**
     * 获取唯一时间戳值
     * @return
     */
    public static Long getTimeId(){
        AtomicLong timeMills = new AtomicLong(0);
        while (true){
            long currentTimeMillis = System.nanoTime();
            long currentMill = timeMills.get();
            if(currentTimeMillis > currentMill && timeMills.compareAndSet(currentMill, currentTimeMillis)){
                //返回唯一时间戳
                return currentTimeMillis;
            }
        }
    }

    /**
     * 临时模板id
     * @return
     */
    public static String temporaryId() {
        int first = new Random().nextInt(899) + 100;
        int in = new Random().nextInt(50) + 49;
        int end=new Random().nextInt(50)+ 49;
        System.out.println(first);
        System.out.println(in);
        System.out.println(end);
        return first+String.format("%d",in)+String.format("%d",end);
    }

    public static void main(String[] args) {
        System.out.println(new Date().getTime());

    }

}

