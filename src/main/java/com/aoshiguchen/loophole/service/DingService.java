package com.aoshiguchen.loophole.service;

import com.aoshiguchen.loophole.base.SystemConfig;
import com.aoshiguchen.loophole.core.foundation.Foundation;
import com.aoshiguchen.loophole.core.uitls.DateUtil;
import com.aoshiguchen.loophole.core.uitls.DingTalkUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 钉钉服务
 * Author: yangwen
 * Date:  2019/6/2
 */
@Service
public class DingService {



    /**
     * 发送钉钉通知
     * @param title
     * @param msg
     */
    public void notice(String title, String msg) {
        if(StringUtils.isEmpty(SystemConfig.DING_NOTICE_TOKEN)){
            Foundation.ding().notice(title, msg);
        }else{
            DingTalkUtil.sendSimpleMsg(SystemConfig.DING_NOTICE_TOKEN, title, msg);
        }
    }

}
