package com.aoshiguchen.loophole.core.dto;

import java.util.List;

/**
 * IP地址段
 * Author: yangwen
 * Date:  2019/5/30
 */
public interface IpSegment {

    /**
     * 是否有下一个
     * @return
     */
    boolean hasNext();

    /**
     * 下一个ip，没有下一个时返回null
     * @return
     */
    Ip next();

    /**
     * 获取当前ip，开始为起始ip
     * @return
     */
    Ip get();

    /**
     * 获取包含的公网段
     * @return
     */
    List<? extends IpSegment> getPublicNetworkSegments();

    /**
     * 获取ip数量
     * @return
     */
    long getCount();

    /**
     * 当前ip是否为起始ip
     * @return
     */
    boolean isBegin();

    /**
     * 当前ip是否为终止ip
     * @return
     */
    boolean isEnd();

    /**
     * ip段是否包含此ip
     * @param ip
     * @return
     */
    boolean contains(Ip ip);

    /**
     * 获取字符串形式表示
     * @return
     */
    String getString();

    /**
     * 克隆
     * @return
     */
    IpSegment clone();

}
