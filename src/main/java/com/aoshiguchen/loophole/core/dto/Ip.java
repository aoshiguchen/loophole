package com.aoshiguchen.loophole.core.dto;

/**
 * IP地址
 * Author: yangwen
 * Date:  2019/5/30
 */
public interface Ip {

    /**
     * 检测ip是否能ping通
     * @return
     */
    boolean ping(int timeout);

    /**
     * 是否有下一个ip
     * @return
     */
    boolean hasNext();

    /**
     * 下一个ip
     * @return
     */
    Ip next();

    /**
     * 下一个公网ip
     * @return
     */
    Ip nextPublickNetworkIp();

    /**
     * 下一个非ip
     * @return
     */
    Ip nextNotPublickNetworkIp();

    /**
     * 是否是公网ip
     * @return
     */
    boolean isPublicNetwork();

    /**
     * 获取字符串表示
     * @return
     */
    String getString();

    /**
     * 获取数字表示
     * @return
     */
    Long getNumber();

    /**
     * 获取ip地址类型（A、B、C、D、E 类）
     * @return
     */
    String getType();

    /**
     * 克隆
     * @return
     */
    Ip clone();
}
