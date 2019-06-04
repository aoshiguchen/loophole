package com.aoshiguchen.loophole.core.foundation.internals.provider;

import com.aoshiguchen.loophole.core.dto.Ipv4;
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
    public boolean check(Ipv4 ip) {
        return check(ip.getString());
    }

    @Override
    public boolean check(Ipv4 ip, int timeout) {
        return check(ip,6379,timeout);
    }

    @Override
    public boolean check(String host, int port, int timeout) {
        return check(Ipv4.from(host),port,timeout);
    }

    @Override
    public boolean check(Ipv4 ip, int port, int timeout) {
        return (ip.ping(port, timeout) && check(ip.getString(), port));
    }

    @Override
    public boolean check(String host, int port) {
        try{
            Jedis jedis = new Jedis(host,port);
            jedis.connect();
            jedis.set("test","hello");
            jedis.del("test");
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
