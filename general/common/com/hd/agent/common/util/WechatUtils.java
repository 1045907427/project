package com.hd.agent.common.util;

import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by limin on 2016/5/9.
 */
public class WechatUtils {

    /**
     * 微信统一下单 URL
     */
    private static String STR_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * post
     * @param url
     * @param jsonStr
     * @return
     * @author limin
     * @date May 9, 2016
     */
    public static JSONObject post(String url, String jsonStr) {

        HttpClient client = null;
        HttpPost post = null;

        try {

            client = new DefaultHttpClient();

            StringEntity se = new StringEntity(jsonStr, "UTF-8");
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            post = new HttpPost(url);
            post.setEntity(se);
            post.setHeader(HTTP.CONTENT_TYPE, "application/json");

            HttpResponse res = client.execute(post);
            HttpEntity entity = res.getEntity();
            String responseContent = EntityUtils.toString(entity, "UTF-8");
            JSONObject json = JSONObject.fromObject(responseContent);
            return json;

        } catch (Exception e) {

            e.printStackTrace();

        } finally { // 关闭连接 ,释放资源

            if(client != null) {
                client.getConnectionManager().shutdown();
            }
        }

        JSONObject json = JSONObject.fromObject("{\"errorcode\":\"-1\"}");
        return json;
    }

    /**
     * post
     * @param url
     * @param params
     * @return
     * @author limin
     * @date May 9, 2016
     */
    public static JSONObject get(String url, String[] params) {

        String.format(url, params);

        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(String.format(url, params));

        try {
            HttpResponse res = client.execute(get);
            HttpEntity entity = res.getEntity();
            String responseContent = EntityUtils.toString(entity, "UTF-8"); // 响应内容
            JSONObject json = JSONObject.fromObject(responseContent);
            return json;

        } catch (Exception e) {
            e.printStackTrace();
        } finally { // 关闭连接 ,释放资源

            if(client != null) {
                client.getConnectionManager().shutdown();
            }
        }

        return null;
    }

    /**
     * 从servlet context 中获取 access_token
     * @return
     * @author limin
     * @date May 10, 2016
     */
    public static String getAccessToken() {

        String token = (String) ((XmlWebApplicationContext) SpringContextUtils.getApplicationContext()).getServletContext().getAttribute("access_token");
        return token;
    }

    /**
     * 从servlet context 中获取 jsapi_ticket
     *
     * @return
     * @author limin
     * @date May 10, 2016
     */
    public static String getJsapiTicket() {

        String ticket = (String) ((XmlWebApplicationContext) SpringContextUtils.getApplicationContext()).getServletContext().getAttribute("jsapi_ticket");
        return ticket;
    }


    /**
     * sha1加密
     *
     * @param decript
     * @return
     * @author limin
     * @date May 20, 2016
     */
    private static String sha1(String decript) {

        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取jssdk signature
     *
     * @param request
     * @return
     * @author limin
     * @date May 20, 2016
     */
    public static Map getJssdkSignature(HttpServletRequest request) {

        String ticket = getJsapiTicket();
        String noncestr = CommonUtils.MD5(CommonUtils.getDataNumberSendsWithRand()).toUpperCase();
        String timestamp = new Date().toString();

        String url = new String(request.getRequestURL());
        String query = request.getQueryString();

        // 当url 中含有参数时，形式如：http://xx.xxxx.xx:80/page?param=value
        if(StringUtils.isNotEmpty(query)) {

            url = url + "?" + query;
        }

        String[] sha1Params = {ticket, noncestr, timestamp, url};
        String string1 = String.format("jsapi_ticket=%s&noncestr=%s&timestamp=%s&url=%s", sha1Params);

        String signature = sha1(string1);

        Map map = new HashMap();
        map.put("ticket", ticket);
        map.put("noncestr", noncestr);
        map.put("timestamp", timestamp);
        map.put("url", url);
        map.put("string1", string1);
        map.put("signature", signature);
        map.put("corpid", WechatProperties.getValue("corpid"));
        return map;
    }

}
