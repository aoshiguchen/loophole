package com.aoshiguchen.loophole.core.foundation.internals.provider;

import com.aoshiguchen.loophole.core.dto.Ipv4;
import com.aoshiguchen.loophole.core.dto.Ipv4Segment;
import com.aoshiguchen.loophole.core.foundation.spi.provider.Ipv4Provider;
import com.aoshiguchen.loophole.core.foundation.spi.provider.Provider;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    @Override
    public void ergodicPublicNetworkIp(String from, String to, BiConsumer<Ipv4, Integer> consumer) {
        Ipv4Segment ipv4Segment = Ipv4Segment.from(Ipv4.from(from),Ipv4.from(to));
        List<Ipv4Segment> list = ipv4Segment.getPublicNetworkSegments();
        long total = list.stream().collect(Collectors.summingLong(Ipv4Segment::getCount));
        long count = 0;
        for(Ipv4Segment segment : list){
            count++;
            consumer.accept(segment.get(), (int)Math.ceil(count * 100 / total));
            while (segment.next() != null){
                count++;
                consumer.accept(segment.get(),(int)Math.ceil(count * 100 / total));
            }
        }
    }

    @Override
    public void ergodicPublicNetworkIp(String from, String to, Function<Ipv4, Boolean> filter, BiConsumer<Ipv4,Integer> progress, Consumer<Ipv4> consumer) {
        ergodicPublicNetworkIp(Ipv4Segment.from(from,to),filter,progress,consumer);
    }

    @Override
    public void ergodicPublicNetworkIp(Ipv4Segment ipv4Segment, Function<Ipv4, Boolean> filter, BiConsumer<Ipv4, Integer> progress, Consumer<Ipv4> consumer) {
        if(null == ipv4Segment){
            throw new RuntimeException("ipv4Segment不能为空!");
        }

        if(null == filter){
            filter = ip -> true;
        }

        if(null == progress){
            progress = (ipv4, integer) -> {};
        }

        List<Ipv4Segment> list = ipv4Segment.getPublicNetworkSegments();
        long total = list.stream().collect(Collectors.summingLong(Ipv4Segment::getCount));
        long count = 0;
        int beforeProgress = 0;
        int nowProgress = 0;

        for(Ipv4Segment segment : list){
            count++;
            nowProgress = (int)Math.ceil(count * 100 / total);
            if(nowProgress != beforeProgress){
                beforeProgress = nowProgress;
                progress.accept(segment.get(),nowProgress);
            }
            if(filter.apply(segment.get())){
                consumer.accept(segment.get());
            }
            while (segment.next() != null){
                count++;
                nowProgress = (int)Math.ceil(count * 100 / total);
                if(nowProgress != beforeProgress){
                    beforeProgress = nowProgress;
                    progress.accept(segment.get(),nowProgress);
                }
                if(filter.apply(segment.get())){
                    consumer.accept(segment.get());
                }
            }
        }

    }
}
