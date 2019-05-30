package com.aoshiguchen.loophole.core.utils;

import com.aoshiguchen.loophole.core.dto.Ip;
import com.aoshiguchen.loophole.core.dto.Ipv4;
import com.aoshiguchen.loophole.core.uitls.Ipv4Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * TODO
 * Author: yangwen
 * Date:  2019/5/29
 */
public class Ipv4UtilTest {

    @Test
    public void test1(){
        System.out.println(Ipv4Util.IP_NUMBER_MIN == Ipv4Util.toNumber("0.0.0.0"));
        System.out.println(Ipv4Util.IP_NUMBER_MAX == Ipv4Util.toNumber("255.255.255.255"));

        System.out.println(Ipv4Util.IP_A_NUMBER_MIN == Ipv4Util.toNumber("1.0.0.0"));
        System.out.println(Ipv4Util.IP_A_NUMBER_MAX == Ipv4Util.toNumber("127.255.255.255"));

        System.out.println(Ipv4Util.IP_B_NUMBER_MIN == Ipv4Util.toNumber("128.0.0.0"));
        System.out.println(Ipv4Util.IP_B_NUMBER_MAX == Ipv4Util.toNumber("191.255.255.255"));

        System.out.println(Ipv4Util.IP_C_NUMBER_MIN == Ipv4Util.toNumber("192.0.0.0"));
        System.out.println(Ipv4Util.IP_C_NUMBER_MAX == Ipv4Util.toNumber("223.255.255.255"));

        System.out.println(Ipv4Util.IP_D_NUMBER_MIN == Ipv4Util.toNumber("224.0.0.0"));
        System.out.println(Ipv4Util.IP_D_NUMBER_MAX == Ipv4Util.toNumber("239.255.255.255"));

        System.out.println(Ipv4Util.IP_E_NUMBER_MIN == Ipv4Util.toNumber("240.0.0.0"));
        System.out.println(Ipv4Util.IP_E_NUMBER_MAX == Ipv4Util.toNumber("255.255.255.255"));
    }

    @Test
    public void test2(){
        System.out.println("ipv4范围：" + Ipv4.MIN + " - " + Ipv4.MAX);
        System.out.println("ipv4 A类地址范围：" + Ipv4.A_MIN + " - " + Ipv4.A_MAX);
        System.out.println("ipv4 B类地址范围：" + Ipv4.B_MIN + " - " + Ipv4.B_MAX);
        System.out.println("ipv4 C类地址范围：" + Ipv4.C_MIN + " - " + Ipv4.C_MAX);
        System.out.println("ipv4 D类地址范围：" + Ipv4.D_MIN + " - " + Ipv4.D_MAX);
        System.out.println("ipv4 E类地址范围：" + Ipv4.E_MIN + " - " + Ipv4.E_MAX);
    }

    @Test
    public void test3(){
        System.out.println(Ipv4.getInstance(100).toString());
        System.out.println(Ipv4.getInstance(100000059).toString());
        System.out.println(Ipv4.getInstance(Ipv4Util.IP_NUMBER_MIN));

    }

    @Test
    public void test4(){
        Ipv4 beginIp = Ipv4.getInstance("255.255.255.250");
        while(beginIp.hasNext()){
            beginIp = beginIp.next();
            System.out.println(beginIp.toString());
            System.out.println(beginIp.ping(500));
        }
    }

    @Test
    public void test5(){
        Ip ip1 = Ipv4.from(100000);
        Ip ip2 = Ipv4.from("0.1.134.160");
        System.out.println(ip1.equals(ip2));
    }

    @Test
    public void test6(){
        Ip ip1 = Ipv4.from(100000);
        Ip ip2 = Ipv4.from("0.1.134.161");
        System.out.println(ip1.equals(ip2));
    }

    @Test
    public void test7(){
        Ip ip = Ipv4.from("192.168.0.10");
        System.out.println(ip.getType());
    }

    @Test
    public void test8(){
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_NUMBER_MIN).isPublicNetwork(),false);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_NUMBER_MAX).isPublicNetwork(),false);

        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_A_NUMBER_MIN).isPublicNetwork(),true);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_A_NUMBER_MAX).isPublicNetwork(),false);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_A_PRIVATE_NUMBER_MIN).isPublicNetwork(),false);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_A_PRIVATE_NUMBER_MAX).isPublicNetwork(),false);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_A_PRIVATE_NUMBER_MIN - 1).isPublicNetwork(),true);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_A_PRIVATE_NUMBER_MAX + 1).isPublicNetwork(),true);

        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_B_NUMBER_MIN).isPublicNetwork(),true);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_B_NUMBER_MAX).isPublicNetwork(),true);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_B_PRIVATE_NUMBER_MIN).isPublicNetwork(),false);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_B_PRIVATE_NUMBER_MAX).isPublicNetwork(),false);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_B_PRIVATE_NUMBER_MIN - 1).isPublicNetwork(),true);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_B_PRIVATE_NUMBER_MAX + 1).isPublicNetwork(),true);

        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_C_NUMBER_MIN).isPublicNetwork(),true);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_C_NUMBER_MAX).isPublicNetwork(),true);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_C_PRIVATE_NUMBER_MIN).isPublicNetwork(),false);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_C_PRIVATE_NUMBER_MAX).isPublicNetwork(),false);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_C_PRIVATE_NUMBER_MIN - 1).isPublicNetwork(),true);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_C_PRIVATE_NUMBER_MAX + 1).isPublicNetwork(),true);

        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_D_NUMBER_MIN).isPublicNetwork(),false);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_D_NUMBER_MAX).isPublicNetwork(),false);

        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_E_NUMBER_MIN).isPublicNetwork(),false);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_E_NUMBER_MAX).isPublicNetwork(),false);

        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_LOOPBACK_MIN).isPublicNetwork(),false);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_LOOPBACK_MAX).isPublicNetwork(),false);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_LOOPBACK_MIN - 1).isPublicNetwork(),true);
        Assertions.assertEquals(Ipv4.from(Ipv4Util.IP_LOOPBACK_MAX + 1).isPublicNetwork(),true);
    }
}
