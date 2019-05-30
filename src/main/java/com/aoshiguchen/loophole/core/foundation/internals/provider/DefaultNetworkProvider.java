package com.aoshiguchen.loophole.core.foundation.internals.provider;

import com.aoshiguchen.loophole.core.foundation.internals.NetworkInterfaceManager;
import com.aoshiguchen.loophole.core.foundation.spi.provider.NetworkProvider;
import com.aoshiguchen.loophole.core.foundation.spi.provider.Provider;

/**
 * 默认的网络提供者
 * Author: yangwen
 * Date:  2019/5/29
 */
public class DefaultNetworkProvider implements NetworkProvider {

    @Override
    public String getProperty(String name, String defaultValue) {
        if ("host.address".equalsIgnoreCase(name)) {
            String val = getHostAddress();
            return val == null ? defaultValue : val;
        } else if ("host.name".equalsIgnoreCase(name)) {
            String val = getHostName();
            return val == null ? defaultValue : val;
        } else {
            return defaultValue;
        }
    }

    @Override
    public void initialize() {

    }

    @Override
    public String getHostAddress() {
        return NetworkInterfaceManager.INSTANCE.getLocalHostAddress();
    }

    @Override
    public String getHostName() {
        return NetworkInterfaceManager.INSTANCE.getLocalHostName();
    }

    @Override
    public Class<? extends Provider> getType() {
        return NetworkProvider.class;
    }

    @Override
    public String toString() {
        return "hostName [" + getHostName() + "] hostIP [" + getHostAddress() + "] (DefaultNetworkProvider)";
    }

}
