package org.songxueyu.cdgy.fruitsaleproject.Util;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class UuidUtil {
    /**
     * 同步函数，不允许同一时间两个线程调用
     * @param
     * @return 返回uuid去除连字符后的字符串
     *
     * */
    public static synchronized String getUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-","");
    }
}
