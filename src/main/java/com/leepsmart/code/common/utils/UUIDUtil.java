package com.leepsmart.code.common.utils;

import com.leepsmart.code.common.utils.encrypt.MD5Util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author leepsmart
 */
public class UUIDUtil {

    private static AtomicLong timeMills = new AtomicLong(0);

    /**
     * 获取唯一时间戳值
     *
     * @return
     */
    public static Long getTimeId() {
        while (true) {
            long currentTimeMillis = System.nanoTime();
            long currentMill = timeMills.get();
            if (currentTimeMillis > currentMill && timeMills.compareAndSet(currentMill, currentTimeMillis)) {
                //返回唯一时间戳
                return currentTimeMillis;
            }
        }
    }

    /**
     * 获取 唯一码
     *
     * @return
     */
    public static String getCode() {
        Long timeId = getTimeId();
        return MD5Util.MD5Encode(timeId.toString(), "UTF-8");
    }

}
