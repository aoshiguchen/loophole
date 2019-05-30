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

}
