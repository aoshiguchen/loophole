package com.aoshiguchen.loophole.core.foundation.internals.provider;

import com.aoshiguchen.loophole.core.dto.Ipv4;
import com.aoshiguchen.loophole.core.dto.Ipv4Segment;
import com.aoshiguchen.loophole.core.foundation.spi.provider.Ipv4Provider;
import com.aoshiguchen.loophole.core.foundation.spi.provider.Provider;

import java.util.List;
import java.util.function.Consumer;

/**
 * 默认的ipv4提供者
 * Author: yangwen
 * Date:  2019/5/30
 */
public class DefaultIpv4Provider implements Ipv4Provider {

    @Override
    public Class<? extends Provider> getType() {
        return Ipv4Provider.class;
    }

    @Override
    public String getProperty(String name, String defaultValue) {
        return null;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void ergodicPublicNetworkIp(String from, String to, Consumer<Ipv4> consumer) {
        Ipv4Segment ipv4Segment = Ipv4Segment.from(Ipv4.from(from),Ipv4.from(to));
        List<Ipv4Segment> list = ipv4Segment.getPublicNetworkSegments();
        for(Ipv4Segment segment : list){
            consumer.accept(segment.get());
            while (segment.next() != null){
                consumer.accept(segment.get());
            }
        }
    }
}
