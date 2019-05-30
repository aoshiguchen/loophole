package com.aoshiguchen.loophole.core.foundation.spi.provider;

/**
 * 钉钉服务提供
 * Author: yangwen
 * Date:  2019/5/30
 */
public interface DingProvider extends Provider{

    void notice(String msg);

}
