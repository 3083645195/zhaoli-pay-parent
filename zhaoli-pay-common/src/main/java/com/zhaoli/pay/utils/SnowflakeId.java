package com.zhaoli.pay.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

import java.io.Serializable;

public class SnowflakeId implements Serializable {
    private static Snowflake snowflake;

    static {
        snowflake = IdUtil.getSnowflake(1, 1);
    }


    public static String getIdStr() {
        return snowflake.nextIdStr();
    }

    public static long getId() {
        return snowflake.nextId();
    }
}