/**
 * @(#)WechatInitListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-5-10 limin 创建版本
 */
package com.hd.agent.common.listener;

import com.hd.agent.common.util.WechatProperties;
import com.hd.agent.common.util.WechatUtils;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * 获取微信 access_token、jsapi_ticket Listener
 *
 * @author limin
 */
public class WechatInitListener implements ServletContextListener {

    /**
     * logger
     */
    public Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void contextInitialized(ServletContextEvent event) {

        logger.info("*****************  WechatConfig Init Start *****************");

        String serviceid = WechatProperties.getValue("serviceid");
        String servicesecret = WechatProperties.getValue("servicesecret");

        // get access token
        String[] tokenParams = {serviceid, servicesecret};
        JSONObject tokenJson = WechatUtils.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s", tokenParams);
        if(tokenJson.containsKey("access_token")) {

            String token = (String) tokenJson.get("access_token");

            //System.out.println("access_token: " + token);
            //logger.info("access_token: " + token);

            event.getServletContext().setAttribute("access_token", token);

//            // get js api ticket
//            String[] ticketParams = {token};
//            JSONObject ticketJson = WechatUtils.get("https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=%s&type=jsapi", ticketParams);
//            if(ticketJson.containsKey("errcode") && "0".equals(ticketJson.getString("errcode"))) {
//
//                String ticket = ticketJson.getString("ticket");
//
//                //System.out.println("jsapi_ticket: " + ticket);
//                //logger.info("jsapi_ticket: " + ticket);
//
//                event.getServletContext().setAttribute("jsapi_ticket", ticket);
//
//            } else {
//
//                System.out.println("get_jsapi_ticket error");
//                logger.error("get_jsapi_ticket error");
//            }

        } else {

            logger.error("gettoken error");
        }

        logger.info("*****************  WechatConfig Init End   *****************");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {

        event.getServletContext().removeAttribute("access_token");
        event.getServletContext().removeAttribute("jsapi_ticket");
    }
}
