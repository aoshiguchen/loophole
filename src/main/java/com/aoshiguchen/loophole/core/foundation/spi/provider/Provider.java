package com.aoshiguchen.loophole.core.foundation.spi.provider;

/**
 * TODO
 * Author: yangwen
 * Date:  2019/5/29
 */
public interface Provider {

    Class<? extends Provider> getType();

    String getProperty(String name, String defaultValue);

    void initialize();

}
