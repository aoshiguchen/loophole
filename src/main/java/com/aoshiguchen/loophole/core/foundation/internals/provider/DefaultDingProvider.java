package com.aoshiguchen.loophole.core.foundation.internals.provider;

import com.aoshiguchen.loophole.core.foundation.spi.provider.DingProvider;
import com.aoshiguchen.loophole.core.foundation.spi.provider.Provider;
import com.aoshiguchen.loophole.core.uitls.DingTalkUtil;

/**
 * TODO
 * Author: yangwen
 * Date:  2019/5/30
 */
public class DefaultDingProvider implements DingProvider {

    /**
     * 发布通知的accessToken
     */
    private String noticeAccessToken;

    @Override
    public void notice(String msg) {
        DingTalkUtil.sendDingTalkMsg(noticeAccessToken,msg);
    }

    @Override
    public Class<? extends Provider> getType() {
        return DingProvider.class;
    }

    @Override
    public String getProperty(String name, String defaultValue) {
        return null;
    }

    @Override
    public void initialize() {
        this.noticeAccessToken = "fa81c6fb600c37f71b4332c188c8c266a0abea54f015bb6c7a8d3792c9be6c3e";
    }
}
