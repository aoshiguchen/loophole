package com.aoshiguchen.loophole.core.foundation.spi.provider;

/**
 * TODO
 * Author: yangwen
 * Date:  2019/5/29
 */
public interface NetworkProvider extends Provider {

    String getHostAddress();

    String getHostName();

}
