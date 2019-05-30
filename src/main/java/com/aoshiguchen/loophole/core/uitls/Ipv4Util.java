package com.aoshiguchen.loophole.core.uitls;

import org.junit.platform.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO
 * Author: yangwen
 * Date:  2019/5/29
 */
public class Ipv4Util {

    /**
     * ip地址范围
     */
    public static final long IP_NUMBER_MIN = toNumber(0,0,0,0);
    public static final long IP_NUMBER_MAX = toNumber(255,255,255,255);

    /**
     * A类Ip范围
     */
    public static final long IP_A_NUMBER_MIN = toNumber(1,0,0,0);
    public static final long IP_A_NUMBER_MAX = toNumber(127,255,255,255);
    public static final long IP_A_PRIVATE_NUMBER_MIN = toNumber(10,0,0,0);
    public static final long IP_A_PRIVATE_NUMBER_MAX = toNumber(10,255,255,255);

    /**
     * B类Ip范围
     */
    public static final long IP_B_NUMBER_MIN = toNumber(128,0,0,0);
    public static final long IP_B_NUMBER_MAX = toNumber(191,255,255,255);
    public static final long IP_B_PRIVATE_NUMBER_MIN = toNumber(172,16,0,0);
    public static final long IP_B_PRIVATE_NUMBER_MAX = toNumber(172,31,255,255);

    /**
     * C类Ip范围
     */
    public static final long IP_C_NUMBER_MIN = toNumber(192,0,0,0);
    public static final long IP_C_NUMBER_MAX = toNumber(223,255,255,255);
    public static final long IP_C_PRIVATE_NUMBER_MIN = toNumber(192,168,0,0);
    public static final long IP_C_PRIVATE_NUMBER_MAX = toNumber(192,168,255,255);

    /**
     * D类Ip范围
     */
    public static final long IP_D_NUMBER_MIN = toNumber(224,0,0,0);
    public static final long IP_D_NUMBER_MAX = toNumber(239,255,255,255);

    /**
     * E类Ip范围
     */
    public static final long IP_E_NUMBER_MIN = toNumber(240,0,0,0);
    public static final long IP_E_NUMBER_MAX = toNumber(255,255,255,255);

    /**
     * 系统回环地址范围
     */
    public static final long IP_LOOPBACK_MIN = toNumber(127,0,0,0);
    public static final long IP_LOOPBACK_MAX = toNumber(127,255,255,255);

    private static long toNumber(long a, long b, long c, long d){
        return a * (long)Math.pow(256,3) + b * (long)Math.pow(256,2) + c * 256 + d;
    }

    public static void check(String str){
        if(StringUtils.isBlank(str)){
            throw new RuntimeException("str不能为空!");
        }

        String[] tmp = str.split("\\.");
        if(null == tmp || tmp.length != 4){
            throw new RuntimeException("str格式有误!");
        }

        for(String t : tmp){
            try{
                long v = Long.parseLong(t);
                if(v < 0 || v > 255){
                    throw new RuntimeException("str格式有误!");
                }
            }catch (Exception e){
                throw new RuntimeException("str格式有误!");
            }
        }
    }

    public static void check(long num){
        if(num < IP_NUMBER_MIN || num > IP_NUMBER_MAX){
            throw new RuntimeException("num必须在" + IP_NUMBER_MIN + "~" + IP_NUMBER_MAX + "之间!");
        }
    }


    public static long toNumber(String ipv4){
        check(ipv4);

        String[] tmp = ipv4.split("\\.");
        return toNumber(Long.parseLong(tmp[0]), Long.parseLong(tmp[1]), Long.parseLong(tmp[2]), Long.parseLong(tmp[3]));
    }

    public static String toString(long number){
        check(number);

        List<Long> list = new ArrayList<>();
        while(number > 0){
            list.add(number % 256);
            number /= 256;
        }

        while(list.size() < 4){
            list.add(0L);
        }
        Collections.reverse(list);
        return list.stream().map(String::valueOf).collect(Collectors.joining("."));
    }

}
