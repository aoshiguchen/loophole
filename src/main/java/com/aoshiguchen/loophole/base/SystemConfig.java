package com.aoshiguchen.loophole.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 系统配置
 * Author: yangwen
 * Date:  2019/6/2
 */
@Slf4j
@Component
public class SystemConfig {

    /**
     * 发布扫描结果通知的钉钉机器人token
     */
    public static String DING_NOTICE_TOKEN;

    /**
     * 扫描起始ip
     */
    public static String SCAN_FROM_IP;

    /**
     * 扫描终止ip
     */
    public static String SCAN_TO_IP;

    @Autowired
    private Environment env;

    @PostConstruct
    public void init(){
        log.info("加载系统配置...");
        DING_NOTICE_TOKEN = env.getProperty("sys.config.DING_NOTICE_TOKEN");
        SCAN_FROM_IP = env.getProperty("sys.config.scan.FROM_IP");
        SCAN_TO_IP = env.getProperty("sys.config.scan.TO_IP");
    }

}
