package com.aoshiguchen.loophole.core.dto;

import org.junit.jupiter.api.Test;

/**
 * TODO
 * Author: yangwen
 * Date:  2019/5/29
 */
public class Ipv4Test {

    @Test
    public void test1(){
        Ipv4 ipV4 = new Ipv4(-1);
    }

    @Test
    public void test2(){
        Ipv4 ipV4 = new Ipv4(10);
    }

    @Test
    public void test3(){
        Ipv4 ipV4 = new Ipv4(256L * 256 * 256 * 256 + 1);
    }

    @Test
    public void test4(){
        Ipv4 ipV4 = new Ipv4(null);
    }

    @Test
    public void test5(){
        Ipv4 ipV4 = new Ipv4("");
    }

    @Test
    public void test6(){
        Ipv4 ipV4 = new Ipv4("  ");
    }

    @Test
    public void test7(){
        Ipv4 ipV4 = new Ipv4("124");
    }

    @Test
    public void test8(){
        Ipv4 ipV4 = new Ipv4("1.1.1.1");
    }

    @Test
    public void test9(){
        Ipv4 ipV4 = new Ipv4("-1.1.1.1");
    }
}
