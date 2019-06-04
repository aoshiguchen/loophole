package com.aoshiguchen.loophole.core.foundation;

import com.aoshiguchen.loophole.base.SystemConfig;
import org.junit.jupiter.api.Test;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

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
        System.out.println(Foundation.redis().check("110.42.0.39"));
    }

    @Test
    public void test8(){
        Foundation.ding().notice("标题","内容");
    }

    // 74秒
    @Test
    public void test9(){
        long now = System.currentTimeMillis();

        Foundation.ipv4().ergodicPublicNetworkIp("110.19.109.10","110.19.109.50", // 设置扫描ip段
                ipv4 ->  Foundation.redis().check(ipv4.getString()), // 过滤暴露6379外网端口的ip
                (ipv4,progress) -> System.out.println(progress + "%")    // 输出扫描进度
                ,ipv4 -> {
                    System.out.println("扫描到redis漏洞ip:" + ipv4);
                });

        System.out.println("耗时：" + ((System.currentTimeMillis() - now)/1000) + "秒");
    }

    // 平均1.8秒
    @Test
    public void test10(){
        long now = System.currentTimeMillis();
        System.out.println(Foundation.redis().check("110.19.109.0"));
        System.out.println("耗时：" + ((System.currentTimeMillis() - now)/1000) + "秒");
    }

    // 平均200毫秒
    @Test
    public void test11(){
        long now = System.currentTimeMillis();
        System.out.println(check("110.19.109.0",6379,200));
        System.out.println("耗时：" + (System.currentTimeMillis() - now) + "毫秒");
    }

    // 平均220毫秒
    @Test
    public void test12(){
        long now = System.currentTimeMillis();
        System.out.println(Foundation.redis().check("110.19.109.34",6379,200));
        System.out.println("耗时：" + (System.currentTimeMillis() - now) + "毫秒");
    }

    // 8秒
    @Test
    public void test13(){
        long now = System.currentTimeMillis();

        Foundation.ipv4().ergodicPublicNetworkIp("110.19.109.10","110.19.109.50", // 设置扫描ip段
                ipv4 ->  Foundation.redis().check(ipv4,200), // 过滤暴露6379外网端口的ip
                (ipv4,progress) -> System.out.println(progress + "%")    // 输出扫描进度
                ,ipv4 -> {
                    System.out.println("扫描到redis漏洞ip:" + ipv4);
                });

        System.out.println("耗时：" + ((System.currentTimeMillis() - now)/1000) + "秒");
    }

    private static boolean check(String ip,int port,int timeout){
        boolean result = false;

        try{
            Socket socket = new Socket();
            SocketAddress socketAddress = new InetSocketAddress(ip, port);
            socket.connect(socketAddress,timeout);
            return true;
        }catch (Exception e){

        }

        return result;
    }
}
