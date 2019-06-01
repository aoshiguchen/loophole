package com.aoshiguchen.loophole.core;

import com.aoshiguchen.loophole.core.foundation.Foundation;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * TODO
 * Author: yangwen
 * Date:  2019/5/30
 */
public class Test1 {

    private static final String from = "115.0.0.0";
    private static final String to = "120.0.0.0";

    /**
     * 抓取暴露redis外网端口的ip
     * @throws Exception
     */
    @Test
    public void test1() throws Exception{
        final BufferedWriter bw = new BufferedWriter(new FileWriter("6379端口ip[" + from + "-" + to + "].txt"));

        Foundation.ipv4().ergodicPublicNetworkIp(from,to, ip -> {
            if(check(ip.getString(),6379,500)){
                try{
                    bw.write(ip.getString() + "\r\n");
                    bw.flush();
                }catch (Exception e){

                }
                Foundation.ding().notice("6379端口ip:" + ip);
            }
        });
        bw.close();
    }

    @Test
    public void test2() throws Exception{
        final BufferedWriter bw = new BufferedWriter(new FileWriter("ping通ip[" + from + "-" + to + "].txt"));

        Foundation.ipv4().ergodicPublicNetworkIp(from,to, ip -> {
            if(ip.ping(3000)){
                try{
                    bw.write(ip.getString() + "\r\n");
                    bw.flush();
                }catch (Exception e){

                }
            }
        });
        bw.close();
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

    public static void main(String[] args) {
//        System.out.println(check("r-uf6997427dc281a4.redis.rds.aliyuncs.com",6379,200));
//        System.out.println(Ipv4.from("118.89.63.66").ping(3000));
    }

}
