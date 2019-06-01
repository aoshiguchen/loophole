package com.aoshiguchen.loophole.core.foundation.internals.provider;

import com.aoshiguchen.loophole.core.foundation.spi.provider.Provider;
import com.aoshiguchen.loophole.core.foundation.spi.provider.RedisProvider;
import redis.clients.jedis.Jedis;

/**
 * 默认的redis服务提供者
 * Author: yangwen
 * Date:  2019/6/1
 */
public class DefaultRedisProvider implements RedisProvider {

    @Override
    public boolean check(String host) {
        return check(host,6379);
    }

    @Override
    public boolean check(String host, int port) {
        try{
            Jedis jedis = new Jedis(host,port);
            jedis.connect();
            return jedis.isConnected();
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Class<? extends Provider> getType() {
        return RedisProvider.class;
    }

    @Override
    public String getProperty(String name, String defaultValue) {
        return null;
    }

    @Override
    public void initialize() {

    }
}
