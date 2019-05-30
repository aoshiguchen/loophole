package com.aoshiguchen.loophole.core.uitls;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * Http工具类
 * User: yunai
 * Date: 13-9-26
 * Time: 下午11:02
 */
public class HttpUtil {

    private static String charsetName;

    /**
     * 内容类型 - UTF-8
     */
    public static final ContentType TEXT_PLAIN_UTF_8 = ContentType.create("text/plain", Consts.UTF_8);
    private static  boolean DEV ;

    public static void init(boolean dev,String charsetName){
        DEV = dev;
        HttpUtil.charsetName = charsetName;
    }

    /**
     * HttpClient单例持有
     */
    private static class HttpClientHolder {
        private static final CloseableHttpClient INSTANCE = HttpClients.custom()
                .disableAutomaticRetries()
                .setMaxConnTotal(10240)
                .setMaxConnPerRoute(512)
                .setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout((int) (30 * DateUtil.SECOND_LONG)).setSocketTimeout((int) (30 * DateUtil.SECOND_LONG)).build())// TODO 【要设置下httpclient的默认超时时间】
                .setUserAgent("")
                .build();
    }
    /**
     * @return HttpClient单例
     */
    public static CloseableHttpClient getHttpClient() {
        return HttpClientHolder.INSTANCE;
    }

    /**
     * 安静的关闭，即使抛出异常
     *
     * @param response 响应
     */
    public static void closeQuietly(CloseableHttpResponse response) {
        if (response == null) {
            return;
        }
        try {
            response.close();
        } catch (IOException ignored) {
        }
    }

    /**
     * 根据map建立queryString。主要用于生成Http的Get请求的参数
     *
     * @param params 参数集
     * @return queryString
     */
    public static String buildQueryString(Map<String, Object> params) {
        if (params.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * url参数编码，按照UTF-8来
     *
     * @param value 参数
     * @return 编码后的参数
     */
    public static String urlEncode(String value) {
        try {
            return URLEncoder.encode(value, charsetName);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<NameValuePair> getParams(Map<String, ?> params) {
        List<NameValuePair> nvps = new ArrayList<>();
        for (Map.Entry<String, ?> param : params.entrySet()) {
            if (param.getValue() == null) {
                continue;
            }
            nvps.add(new BasicNameValuePair(param.getKey(), String.valueOf(param.getValue())));
        }
        return nvps;
    }

    public static byte[] requestForBytes(String uri, String method, Map<String, Object> params, Map<String, String> headers,
                                 RequestConfig config, HttpClientContext context) {
        HttpRequestBase req;
        CloseableHttpResponse response = null;
        try {
            if (method.equalsIgnoreCase("post")) {
                req = new HttpPost(uri);
                HttpPost post = (HttpPost) req;
                List<NameValuePair> nvps = getParams(params);
                post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            } else if (method.equalsIgnoreCase("get")) {
                if (params.isEmpty()) {
                    req = new HttpGet(uri);
                } else {
                    URIBuilder builder = new URIBuilder();
                    builder.setPath(uri);
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        builder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
                    }
                    req = new HttpGet(builder.toString());
                }
            } else if (method.equals("post_with_file")) {
                req = new HttpPost(uri);
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry.getValue() instanceof Object[]) {
                        Object[] values = (Object[]) entry.getValue();
                        if ("binary".equals(values[0])) {
                            builder.addBinaryBody(entry.getKey(), (byte[]) values[1], ContentType.APPLICATION_OCTET_STREAM, (String) values[2]);
                        }
                    } else {
                        builder.addTextBody(entry.getKey(), String.valueOf(entry.getValue()), HttpUtil.TEXT_PLAIN_UTF_8);
                    }
                }
                ((HttpPost) req).setEntity(builder.build());
            } else {
                throw new IllegalArgumentException(method);
            }

            if (null != headers && headers.size() > 0) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    req.addHeader(header.getKey(), header.getValue());
                }
            }

            if (config != null) {
                req.setConfig(config);
            }

            if (context == null) {
                context = HttpClientContext.create();
                context.setCookieStore(new BasicCookieStore()); // 防止其他请求的cookie影响
            }
            response = HttpUtil.getHttpClient().execute(req, context);
            return EntityUtils.toByteArray(response.getEntity());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            HttpUtil.closeQuietly(response);
        }
    }

    public static String request(String uri, String method, Map<String, Object> params, Map<String, String> headers,
                                 RequestConfig config, HttpClientContext context, String charset) {
        HttpRequestBase req;
        CloseableHttpResponse response = null;
        try {
            if (method.equalsIgnoreCase("post")) {
                req = new HttpPost(uri);
                HttpPost post = (HttpPost) req;
                List<NameValuePair> nvps = getParams(params);
                post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            } else if (method.equalsIgnoreCase("get")) {
                if (params.isEmpty()) {
                    req = new HttpGet(uri);
                } else {
                    URIBuilder builder = new URIBuilder();
                    builder.setPath(uri);
                    for (Map.Entry<String, Object> entry : params.entrySet()) {
                        builder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
                    }
                    req = new HttpGet(builder.toString());
                }
            } else if (method.equals("post_with_file")) {
                req = new HttpPost(uri);
                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    if (entry.getValue() instanceof Object[]) {
                        Object[] values = (Object[]) entry.getValue();
                        if ("binary".equals(values[0])) {
                            builder.addBinaryBody(entry.getKey(), (byte[]) values[1], ContentType.APPLICATION_OCTET_STREAM, (String) values[2]);
                        }
                    } else {
                        builder.addTextBody(entry.getKey(), String.valueOf(entry.getValue()), HttpUtil.TEXT_PLAIN_UTF_8);
                    }
                }
                ((HttpPost) req).setEntity(builder.build());
            } else {
                throw new IllegalArgumentException(method);
            }

            if (null != headers && headers.size() > 0) {
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    req.addHeader(header.getKey(), header.getValue());
                }
            }

            if (config != null) {
                req.setConfig(config);
            }

            if (context == null) {
                context = HttpClientContext.create();
                context.setCookieStore(new BasicCookieStore()); // 防止其他请求的cookie影响
            }
            response = HttpUtil.getHttpClient().execute(req, context);
            return EntityUtils.toString(response.getEntity(), charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            HttpUtil.closeQuietly(response);
        }
    }

    public static String request(String uri, String method, Map<String, Object> params, Map<String, String> headers,
                                 RequestConfig config, HttpClientContext context) {
        return request(uri, method, params, headers, config, context, "utf-8");
    }

    public static String request(String uri, String method, Map<String, Object> params, Map<String, String> headers,
                                 RequestConfig config) {
        return request(uri, method, params, headers, config, null);
    }

    /**
     * @param key 键
     * @return 新键。防止和其他应用cookie冲突
     */
    public static String genCookieKey(String key) {
        return "apps_server_" + key;
    }



    public static String jsonPost(String uri, JSONObject param) {
        CloseableHttpResponse response = null;
        try {
            HttpPost request = new HttpPost(uri);
            request.addHeader(HTTP.CONTENT_TYPE, "application/json");
            StringEntity se = new StringEntity(param.toString(), "utf-8");
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            request.setEntity(se);
            HttpResponse httpResponse = HttpUtil.getHttpClient().execute(request);
            String retSrc = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            return retSrc;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            HttpUtil.closeQuietly(response);
        }
    }

    /**
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方法
     * @param outputStr 参数
     */
    public static String httpRequest(String requestUrl,String requestMethod, String outputStr, String charsetName){
        // 创建SSLContext
        StringBuffer buffer=null;
        try{
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();
            //往服务器端写内容
            if(null != outputStr){
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes(charsetName));
                os.close();
            }
            // 读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, charsetName);
            BufferedReader br = new BufferedReader(isr);
            buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String[] shortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = String.valueOf(new Random().nextInt(1000000));
        // 要使用生成 URL 的字符
        String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z" };

        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = DigestUtils.md5Hex(key + url);
        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
            // long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }

            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl;
    }

}
