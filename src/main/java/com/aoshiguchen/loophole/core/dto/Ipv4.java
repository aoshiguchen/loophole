package com.aoshiguchen.loophole.core.dto;

import com.aoshiguchen.loophole.core.uitls.Ipv4Util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Ipv4
 * Author: yangwen
 * Date:  2019/5/29
 */
public class Ipv4 implements Ip{

    public static final Ipv4 MIN = getInstance(Ipv4Util.IP_NUMBER_MIN);
    public static final Ipv4 MAX = getInstance(Ipv4Util.IP_NUMBER_MAX);

    public static final Ipv4 A_MIN = getInstance(Ipv4Util.IP_A_NUMBER_MIN);
    public static final Ipv4 A_MAX = getInstance(Ipv4Util.IP_A_NUMBER_MAX);
    public static final Ipv4 A_PRIVATE_MIN = getInstance(Ipv4Util.IP_A_PRIVATE_NUMBER_MIN);
    public static final Ipv4 A_PRIVATE_MAX = getInstance(Ipv4Util.IP_A_PRIVATE_NUMBER_MAX);

    public static final Ipv4 B_MIN = getInstance(Ipv4Util.IP_B_NUMBER_MIN);
    public static final Ipv4 B_MAX = getInstance(Ipv4Util.IP_B_NUMBER_MAX);
    public static final Ipv4 B_PRIVATE_MIN = getInstance(Ipv4Util.IP_B_PRIVATE_NUMBER_MIN);
    public static final Ipv4 B_PRIVATE_MAX = getInstance(Ipv4Util.IP_B_PRIVATE_NUMBER_MAX);

    public static final Ipv4 C_MIN = getInstance(Ipv4Util.IP_C_NUMBER_MIN);
    public static final Ipv4 C_MAX = getInstance(Ipv4Util.IP_C_NUMBER_MAX);
    public static final Ipv4 C_PRIVATE_MIN = getInstance(Ipv4Util.IP_C_PRIVATE_NUMBER_MIN);
    public static final Ipv4 C_PRIVATE_MAX = getInstance(Ipv4Util.IP_C_PRIVATE_NUMBER_MAX);

    public static final Ipv4 D_MIN = getInstance(Ipv4Util.IP_D_NUMBER_MIN);
    public static final Ipv4 D_MAX = getInstance(Ipv4Util.IP_D_NUMBER_MAX);

    public static final Ipv4 E_MIN = getInstance(Ipv4Util.IP_E_NUMBER_MIN);
    public static final Ipv4 E_MAX = getInstance(Ipv4Util.IP_E_NUMBER_MAX);

    public static final Ipv4 LOOPBACK_MIN = getInstance(Ipv4Util.IP_LOOPBACK_MIN);
    public static final Ipv4 LOOPBACK_MAX = getInstance(Ipv4Util.IP_LOOPBACK_MAX);

    private String str;

    private Long num;

    public Ipv4(String str){
        Ipv4Util.check(str);

        this.str = str;
        this.num = null;
    }

    public Ipv4(long num){
        Ipv4Util.check(num);

        this.str = null;
        this.num = num;
    }

    @Override
    public boolean ping(int timeout){
        boolean result = false;

        try{
            result = InetAddress.getByName(getString()).isReachable(timeout);
        }catch (Exception e){

        }

        return result;
    }

    @Override
    public boolean ping(int port, int timeout) {
        boolean result = false;

        try{
            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(getString(), port);
            socket.connect(socketAddress,timeout);
            return true;
        }catch (Exception e){

        }

        return result;
    }

    public static Ipv4 getInstance(String str){
        return new Ipv4(str);
    }

    public static Ipv4 getInstance(long num){
        return new Ipv4(num);
    }

    public static Ipv4 from(String str){
        return new Ipv4(str);
    }

    public static Ipv4 from(long num){
        return new Ipv4(num);
    }

    @Override
    public boolean hasNext(){
        if(getNumber() < Ipv4Util.IP_NUMBER_MAX){
            return true;
        }

        return false;
    }

    @Override
    public Ipv4 next(){
        if(!hasNext()){
            return null;
        }

        return getInstance(getNumber() + 1);
    }

    @Override
    public Ipv4 nextPublickNetworkIp() {
        if(!hasNext()){
            return null;
        }
        long v = getNumber().longValue();

        if(Ipv4Segment.A_SEG.contains(this)){
            if(v < Ipv4Util.IP_A_PRIVATE_NUMBER_MIN - 1){
                return Ipv4.from(v + 1);
            }else if(v <= Ipv4Util.IP_A_PRIVATE_NUMBER_MAX){
                return Ipv4.from(Ipv4Util.IP_A_PRIVATE_NUMBER_MAX + 1);
            }else if(v < Ipv4Util.IP_LOOPBACK_MIN - 1){
                return Ipv4.from(v + 1);
            }else if(v <= Ipv4Util.IP_A_NUMBER_MAX){
                return Ipv4.B_MIN;
            }
        }else if(Ipv4Segment.B_SEG.contains(this)){
            if(v < Ipv4Util.IP_B_PRIVATE_NUMBER_MIN - 1){
                return Ipv4.from(v + 1);
            }else if(v <= Ipv4Util.IP_B_PRIVATE_NUMBER_MAX){
                return Ipv4.from(Ipv4Util.IP_B_PRIVATE_NUMBER_MAX + 1);
            }else if(v <= Ipv4Util.IP_B_NUMBER_MAX){
                return Ipv4.from(v + 1);
            }
        }else if(Ipv4Segment.C_SEG.contains(this)){
            if(v < Ipv4Util.IP_C_PRIVATE_NUMBER_MIN - 1){
                return Ipv4.from(v + 1);
            }else if(v <= Ipv4Util.IP_C_PRIVATE_NUMBER_MAX){
                return Ipv4.from(Ipv4Util.IP_C_PRIVATE_NUMBER_MAX + 1);
            }else if(v < Ipv4Util.IP_C_NUMBER_MAX){
                return Ipv4.from(v + 1);
            }else{
                return null;
            }
        }else if(Ipv4Segment.D_SEG.contains(this)){
            return null;
        }else if(Ipv4Segment.E_SEG.contains(this)){
            return null;
        }

        return Ipv4.A_MIN;
    }

    @Override
    public Ipv4 nextNotPublickNetworkIp() {
        if(!hasNext()){
            return null;
        }
        long v = getNumber().longValue();

        if(Ipv4Segment.A_SEG.contains(this)){
            if(v < Ipv4Util.IP_A_PRIVATE_NUMBER_MIN){
                return Ipv4.A_PRIVATE_MIN;
            }else if(v < Ipv4Util.IP_A_PRIVATE_NUMBER_MAX){
                return Ipv4.from(v + 1);
            }else if(v < Ipv4Util.IP_LOOPBACK_MIN){
                return Ipv4.LOOPBACK_MIN;
            }else if(v < Ipv4Util.IP_A_NUMBER_MAX){
                return Ipv4.from(v + 1);
            }else{
                return Ipv4.B_PRIVATE_MIN;
            }
        }else if(Ipv4Segment.B_SEG.contains(this)){
            if(v < Ipv4Util.IP_B_PRIVATE_NUMBER_MIN){
                return Ipv4.B_PRIVATE_MIN;
            }else if(v < Ipv4Util.IP_B_PRIVATE_NUMBER_MAX){
                return Ipv4.from(v + 1);
            }else if(v <= Ipv4Util.IP_B_NUMBER_MAX){
                return Ipv4.C_PRIVATE_MIN;
            }
        }else if(Ipv4Segment.C_SEG.contains(this)){
            if(v < Ipv4Util.IP_C_PRIVATE_NUMBER_MIN){
                return Ipv4.C_PRIVATE_MIN;
            }else if(v < Ipv4Util.IP_C_PRIVATE_NUMBER_MAX){
                return Ipv4.from(v + 1);
            }else if(v <= Ipv4Util.IP_C_NUMBER_MAX){
                return Ipv4.D_MIN;
            }else{
                return null;
            }
        }else if(Ipv4Segment.D_SEG.contains(this)){
            return Ipv4.from(v + 1);
        }else if(Ipv4Segment.E_SEG.contains(this)){
            if(v < Ipv4Util.IP_NUMBER_MAX){
                return Ipv4.from(v + 1);
            }else{
                return null;
            }
        }

        if(getNumber().longValue() < Ipv4Util.IP_A_NUMBER_MIN - 1){
            return Ipv4.from(getNumber() + 1);
        }

        return Ipv4.A_PRIVATE_MIN;
    }

    @Override
    public String getString(){
        if(null == str){
            synchronized (this){
                if(null == str){
                    str = Ipv4Util.toString(this.num);
                }
            }
        }

        return str;
    }

    @Override
    public Long getNumber(){
        if(null == num){
            synchronized (this){
                if(null == num){
                    num = Ipv4Util.toNumber(this.str);
                }
            }
        }

        return num;
    }

    @Override
    public boolean equals(Object obj) {
        if(null == obj) return false;

        if(obj instanceof Ipv4){
            Ipv4 ip = (Ipv4)obj;
            return getNumber().equals(ip.getNumber());
        }

        return false;
    }

    @Override
    public String toString() {
        return getString();
    }

    @Override
    public String getType() {
        if(Ipv4Segment.A_SEG.contains(this)){
            return "A";
        }else if(Ipv4Segment.B_SEG.contains(this)){
            return "B";
        }else if(Ipv4Segment.C_SEG.contains(this)){
            return "C";
        }else if(Ipv4Segment.D_SEG.contains(this)){
            return "D";
        }else if(Ipv4Segment.E_SEG.contains(this)){
            return "E";
        }

        return null;
    }

    @Override
    public boolean isPublicNetwork() {
        String type = getType();
        if(null == type){
            return false;
        }

        switch (type){
            case "A": return !Ipv4Segment.A_PRIVATE_SEG.contains(this) && !Ipv4Segment.LOOPBACK_SEG.contains(this);
            case "B": return !Ipv4Segment.B_PRIVATE_SEG.contains(this);
            case "C": return !Ipv4Segment.C_PRIVATE_SEG.contains(this);
        }

        return false;
    }

    @Override
    public Ipv4 clone() {
        return Ipv4.from(getNumber());
    }
}
