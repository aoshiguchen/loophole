package com.aoshiguchen.loophole.service;

import com.aoshiguchen.loophole.base.SystemConfig;
import com.aoshiguchen.loophole.core.uitls.DingTalkUtil;
import org.springframework.stereotype.Service;

/**
 * 钉钉服务
 * Author: yangwen
 * Date:  2019/6/2
 */
@Service
public class DingService {

    /**
     * 发送钉钉通知
     * @param msg
     */
    public void notice(String msg) {
        DingTalkUtil.sendDingTalkMsg(SystemConfig.DING_NOTICE_TOKEN,msg);
    }

}
