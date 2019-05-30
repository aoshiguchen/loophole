package com.aoshiguchen.loophole.core.utils;

import com.aoshiguchen.loophole.core.dto.Ipv4Segment;
import org.junit.jupiter.api.Test;

/**
 * TODO
 * Author: yangwen
 * Date:  2019/5/30
 */
public class Ipv4SegmentTest {

    @Test
    public void test1(){
        System.out.println("ipv4范围：" + Ipv4Segment.FULL_SEG + " 个数:" + Ipv4Segment.FULL_SEG.getCount());
        System.out.println("ipv4 A类地址范围：" + Ipv4Segment.A_SEG + " 个数:" + Ipv4Segment.A_SEG.getCount());
        System.out.println("ipv4 B类地址范围：" + Ipv4Segment.B_SEG + " 个数:" + Ipv4Segment.B_SEG.getCount());
        System.out.println("ipv4 C类地址范围：" + Ipv4Segment.C_SEG + " 个数:" + Ipv4Segment.C_SEG.getCount());
        System.out.println("ipv4 D类地址范围：" + Ipv4Segment.D_SEG + " 个数:" + Ipv4Segment.D_SEG.getCount());
        System.out.println("ipv4 E类地址范围：" + Ipv4Segment.E_SEG + " 个数:" + Ipv4Segment.E_SEG.getCount());

        System.out.println("ipv4 A类私有地址范围：" + Ipv4Segment.A_PRIVATE_SEG + " 个数:" + Ipv4Segment.A_PRIVATE_SEG.getCount());
        System.out.println("ipv4 B类私有地址范围：" + Ipv4Segment.B_PRIVATE_SEG + " 个数:" + Ipv4Segment.B_PRIVATE_SEG.getCount());
        System.out.println("ipv4 C类私有地址范围：" + Ipv4Segment.C_PRIVATE_SEG + " 个数:" + Ipv4Segment.C_PRIVATE_SEG.getCount());
        System.out.println("ipv4 系统回环地址范围：" + Ipv4Segment.LOOPBACK_SEG + " 个数:" + Ipv4Segment.LOOPBACK_SEG.getCount());
    }

    @Test
    public void test2(){
        System.out.println(Ipv4Segment.A_SEG.getPublicNetworkSegments());
        System.out.println(Ipv4Segment.B_SEG.getPublicNetworkSegments());
        System.out.println(Ipv4Segment.C_SEG.getPublicNetworkSegments());
        System.out.println(Ipv4Segment.D_SEG.getPublicNetworkSegments());
        System.out.println(Ipv4Segment.E_SEG.getPublicNetworkSegments());
        System.out.println(Ipv4Segment.FULL_SEG.getPublicNetworkSegments());
    }

}
