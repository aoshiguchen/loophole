package com.aoshiguchen.loophole.core.foundation;

import com.aoshiguchen.loophole.core.foundation.internals.NullProviderManager;
import com.aoshiguchen.loophole.core.foundation.internals.ServiceBootstrap;
import com.aoshiguchen.loophole.core.foundation.spi.ProviderManager;
import com.aoshiguchen.loophole.core.foundation.spi.provider.DingProvider;
import com.aoshiguchen.loophole.core.foundation.spi.provider.Ipv4Provider;
import com.aoshiguchen.loophole.core.foundation.spi.provider.NetworkProvider;
import com.aoshiguchen.loophole.core.foundation.spi.provider.RedisProvider;

/**
 * TODO
 * Author: yangwen
 * Date:  2019/5/29
 */
public class Foundation {
    private static Object lock = new Object();

    private static volatile ProviderManager s_manager;

    // Encourage early initialization and fail early if it happens.
    static {
        getManager();
    }

    private static ProviderManager getManager() {
        try {
            if (s_manager == null) {
                // Double locking to make sure only one thread initializes ProviderManager.
                synchronized (lock) {
                    if (s_manager == null) {
                        s_manager = ServiceBootstrap.loadFirst(ProviderManager.class);
                    }
                }
            }

            return s_manager;
        } catch (Throwable ex) {
            s_manager = new NullProviderManager();
            return s_manager;
        }
    }

    public static NetworkProvider net() {
        try {
            return getManager().provider(NetworkProvider.class);
        } catch (Exception ex) {
            return NullProviderManager.provider;
        }
    }

    public static Ipv4Provider ipv4(){
        try {
            return getManager().provider(Ipv4Provider.class);
        } catch (Exception ex) {
            return NullProviderManager.provider;
        }
    }

    public static DingProvider ding(){
        try {
            return getManager().provider(DingProvider.class);
        } catch (Exception ex) {
            return NullProviderManager.provider;
        }
    }

    public static RedisProvider redis(){
        try {
            return getManager().provider(RedisProvider.class);
        } catch (Exception ex) {
            return NullProviderManager.provider;
        }
    }

}
