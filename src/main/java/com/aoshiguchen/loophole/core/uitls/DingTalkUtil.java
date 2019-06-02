package com.aoshiguchen.loophole.core.uitls;

import com.alibaba.fastjson.JSONObject;
import com.aoshiguchen.loophole.base.SystemConfig;
import com.aoshiguchen.loophole.core.foundation.Foundation;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.junit.platform.commons.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 钉钉消息工具类
 * Author: liuhuan
 * Date:  17/8/8 下午7:59
 */
public class DingTalkUtil {

    private static final SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATE_FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND4);

    /**
     * 发送消息（适用于简短的通知消息）
     * @param accessToken
     * @param title
     * @param content
     */
    public static void sendSimpleMsg(String accessToken, String title, String content){
        StringBuffer sb = new StringBuffer();
        sb.append("发送IP:").append(Foundation.net().getHostAddress()).append("\n");
        sb.append("【").append(title).append(" ").append(sdf.format(Calendar.getInstance().getTime())).append("】\n");
        sb.append(content);

        sendDingTalkMsg(accessToken, sb.toString());
    }

    /**
     * 发送消息（适用于简短的通知消息）
     * @param accessToken
     * @param content
     */
    public static void sendSimpleMsg(String accessToken, String content){
        StringBuffer sb = new StringBuffer();
        sb.append("发送IP:").append(Foundation.net().getHostAddress()).append("\n");
        sb.append(content);

        sendDingTalkMsg(accessToken, sb.toString());
    }

    /**
     * 发送钉钉消息
     * @param accessToken accessToken
     * @param msg 消息内容
     */
    public static boolean sendDingTalkMsg(String accessToken, String msg) {
        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(msg)) {
            throw new IllegalArgumentException("参数不能为空！");
        }
        for (int i = 0; i < 3; i++) {
            CloseableHttpResponse response = null;
            try {
                HttpPost httpPost = new HttpPost("https://oapi.dingtalk.com/robot/send?access_token=" + accessToken);
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).build();//设置请求和传输超时时间
                httpPost.setConfig(requestConfig);
                // 接收参数json列表
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("msgtype", "text");
                JSONObject jsonParamContent = new JSONObject();
                jsonParamContent.put("content", msg);
                jsonParam.put("text", jsonParamContent);
                StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");//解决中文乱码问题
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                httpPost.setEntity(entity);
                response = HttpUtil.getHttpClient().execute(httpPost);
                String responseText = EntityUtils.toString(response.getEntity());
                if (JSONObject.parseObject(responseText).get("errcode").equals(0)) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                HttpUtil.closeQuietly(response);
            }
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
