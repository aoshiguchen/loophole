package com.aoshiguchen.loophole.core.foundation.spi.provider;

import com.aoshiguchen.loophole.core.dto.Ipv4;

/**
 * redis服务
 * Author: yangwen
 * Date:  2019/6/1
 */
public interface RedisProvider extends Provider{

    /**
     * 检查该主机是否暴露了redis 6379端口，且未设置认证密码
     * @param host
     * @return
     */
    boolean check(String host);

    /**
     * 检查该主机是否暴露了redis 6379端口，且未设置认证密码
     * @param ip
     * @return
     */
    boolean check(Ipv4 ip);

    /**
     * 检查该主机是否暴露了redis外网端口，且未设置认证密码
     * @param host
     * @return
     */
    boolean check(String host, int port);

    /**
     * 检查该主机是否暴露了redis 6379端口，且未设置认证密码
     * @param ip
     * @param timeout
     * @return
     */
    boolean check(Ipv4 ip, int timeout);

    /**
     * 检查该主机是否暴露了redis 6379端口，且未设置认证密码
     * @param ip
     * @param port
     * @param timeout
     * @return
     */
    boolean check(Ipv4 ip, int port, int timeout);

    /**
     * 检查该主机是否暴露了redis外网端口，且未设置认证密码
     * @param host
     * @param timeout
     * @return
     */
    boolean check(String host, int port, int timeout);

}
