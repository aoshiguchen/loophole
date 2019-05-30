package com.aoshiguchen.loophole.core.foundation.spi;

import com.aoshiguchen.loophole.core.foundation.spi.provider.Provider;

/**
 * TODO
 * Author: yangwen
 * Date:  2019/5/29
 */
public interface ProviderManager {

    String getProperty(String name, String defaultValue);

    <T extends Provider> T provider(Class<T> clazz);

}
