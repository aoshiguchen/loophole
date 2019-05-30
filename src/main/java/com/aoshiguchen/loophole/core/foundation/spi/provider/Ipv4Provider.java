package com.aoshiguchen.loophole.core.foundation.spi.provider;

import com.aoshiguchen.loophole.core.dto.Ipv4;

import java.util.function.Consumer;

/**
 * ipv4服务提供
 * Author: yangwen
 * Date:  2019/5/29
 */
public interface Ipv4Provider extends Provider{

    /**
     * 遍历公网ip
     * @param from
     * @param to
     * @param consumer
     */
    void ergodicPublicNetworkIp(String from, String to, Consumer<Ipv4> consumer);

}
