package com.aoshiguchen.loophole.core.foundation;

import org.junit.jupiter.api.Test;

/**
 * TODO
 * Author: yangwen
 * Date:  2019/5/29
 */
public class FoundationTest {

    @Test
    public void test1(){
        System.out.println(Foundation.net().getHostAddress());
    }

    @Test
    public void test2(){
        Foundation.ipv4().ergodicPublicNetworkIp("135.10.10.5","135.10.10.20",ipv4 -> {
            System.out.println(ipv4);
        });
    }

    @Test
    public void test3(){
        Foundation.ipv4().ergodicPublicNetworkIp("9.255.255.250","11.0.0.10",ipv4 -> {
            System.out.println(ipv4);
        });
    }

    @Test
    public void test4(){
        Foundation.ding().notice("测试通知");
    }

    @Test
    public void test5(){
        Foundation.ipv4().ergodicPublicNetworkIp("9.255.255.250","11.0.0.10",(ipv4,progress) -> {
            System.out.println(ipv4 + " " + progress + "%");
        });
    }

    @Test
    public void test6(){
        Foundation.ipv4().ergodicPublicNetworkIp("9.255.255.250","11.0.0.10",
                ipv4 -> !"11.0.0.9".equals(ipv4.getString()),
                (ipv4,progress) -> System.out.println(progress),
                ipv4 -> {
            System.out.println(ipv4);
        });
    }

    @Test
    public void test7(){
        System.out.println(Foundation.redis().check("110.10.147.36"));
    }

}
