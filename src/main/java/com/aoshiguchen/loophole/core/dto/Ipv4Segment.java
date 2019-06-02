package com.aoshiguchen.loophole.core.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Ipv4地址段
 * Author: yangwen
 * Date:  2019/5/30
 */
public class Ipv4Segment implements IpSegment {

    private Ipv4 beginIp;

    private Ipv4 endIp;

    private volatile Ipv4 _ip;

    public static final Ipv4Segment FULL_SEG = getInstance(Ipv4.MIN,Ipv4.MAX);
    public static final Ipv4Segment A_SEG = getInstance(Ipv4.A_MIN,Ipv4.A_MAX);
    public static final Ipv4Segment B_SEG = getInstance(Ipv4.B_MIN,Ipv4.B_MAX);
    public static final Ipv4Segment C_SEG = getInstance(Ipv4.C_MIN,Ipv4.C_MAX);
    public static final Ipv4Segment D_SEG = getInstance(Ipv4.D_MIN,Ipv4.D_MAX);
    public static final Ipv4Segment E_SEG = getInstance(Ipv4.E_MIN,Ipv4.E_MAX);
    public static final Ipv4Segment A_PRIVATE_SEG = getInstance(Ipv4.A_PRIVATE_MIN,Ipv4.A_PRIVATE_MAX);
    public static final Ipv4Segment B_PRIVATE_SEG = getInstance(Ipv4.B_PRIVATE_MIN,Ipv4.B_PRIVATE_MAX);
    public static final Ipv4Segment C_PRIVATE_SEG = getInstance(Ipv4.C_PRIVATE_MIN,Ipv4.C_PRIVATE_MAX);
    public static final Ipv4Segment LOOPBACK_SEG = getInstance(Ipv4.LOOPBACK_MIN,Ipv4.LOOPBACK_MAX);

    public Ipv4Segment(Ipv4 beginIp, Ipv4 endIp){
        if(null == beginIp || null == endIp){
            throw new RuntimeException("ip地址不能为null!");
        }

        if(beginIp.getNumber() > endIp.getNumber()){
            throw new RuntimeException("起始ip不能大于结束地址!");
        }

        this.beginIp = beginIp;
        this.endIp = endIp;
        this._ip = beginIp;
    }

    public static Ipv4Segment getInstance(Ipv4 beginIp, Ipv4 endIp){
        return new Ipv4Segment(beginIp, endIp);
    }

    public static Ipv4Segment from(Ipv4 beginIp, Ipv4 endIp){
        return new Ipv4Segment(beginIp, endIp);
    }

    public static Ipv4Segment from(String beginIp, String endIp){
        return new Ipv4Segment(Ipv4.from(beginIp), Ipv4.from(endIp));
    }

    @Override
    public boolean hasNext() {
        if(this._ip.getNumber() < this.endIp.getNumber()){
            return true;
        }

        return false;
    }

    @Override
    public Ipv4 next() {
        if(hasNext()){
            synchronized (this){
                if(hasNext()){
                    this._ip = Ipv4.getInstance(this._ip.getNumber() + 1);
                    return this._ip;
                }
            }
        }

        return null;
    }

    @Override
    public Ipv4 get() {
        return this._ip;
    }

    @Override
    public boolean isBegin() {
        return this._ip.equals(this.beginIp);
    }

    @Override
    public boolean isEnd() {
        return this._ip.equals(this.endIp);
    }

    @Override
    public boolean contains(Ip ip) {
        if(null == ip){
            return false;
        }

        return ip.getNumber().longValue() >= this.beginIp.getNumber().longValue() && ip.getNumber().longValue() <= this.endIp.getNumber().longValue();
    }

    @Override
    public long getCount() {
        return this.endIp.getNumber() - this.beginIp.getNumber() + 1;
    }

    @Override
    public List<Ipv4Segment> getPublicNetworkSegments() {
        List<Ipv4Segment> result = new ArrayList<>();

        Ipv4 a = beginIp.clone();
        while(true){
            if(a.isPublicNetwork()){
                Ipv4 b = a.nextNotPublickNetworkIp();
                b = Ipv4.from(b.getNumber() - 1);
                if(a.getNumber().longValue() > b.getNumber().longValue() || a.getNumber().longValue() > endIp.getNumber().longValue()){
                    break;
                }
                if(b.getNumber().longValue() > endIp.getNumber().longValue()){
                    result.add(Ipv4Segment.from(a,endIp.clone()));
                    break;
                }
                result.add(Ipv4Segment.from(a,b));
                a = b.nextPublickNetworkIp();
                if(null == a){
                    break;
                }
            }else{
                a = a.nextPublickNetworkIp();
                if(null == a || a.getNumber().longValue() > endIp.getNumber().longValue()){
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public Ip getBeginIp() {
        return this.beginIp;
    }

    @Override
    public Ip getEndIp() {
        return this.endIp;
    }

    @Override
    public String getString() {
        return this.beginIp + ":" + this.endIp;
    }

    @Override
    public String toString() {
        return getString();
    }

    @Override
    public Ipv4Segment clone() {
        return from(this.beginIp.clone(),endIp.clone());
    }
}
