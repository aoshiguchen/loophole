package com.aoshiguchen.loophole.core.foundation.spi.provider;

import com.aoshiguchen.loophole.core.dto.Ipv4;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

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

    /**
     * 遍历公网ip
     * @param from
     * @param to
     * @param consumer
     */
    void ergodicPublicNetworkIp(String from, String to, BiConsumer<Ipv4,Integer> consumer);

    /**
     * 遍历公网ip
     * @param from
     * @param to
     * @param filter
     * @param progress
     * @param consumer
     */
    void ergodicPublicNetworkIp(String from, String to, Function<Ipv4, Boolean> filter, BiConsumer<Ipv4,Integer> progress,Consumer<Ipv4> consumer);
}
