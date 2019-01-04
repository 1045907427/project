/**
 * @(#)LoginUrlEntryPoint.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-12 chenwei 创建版本
 */
package com.hd.agent.accesscontrol.base;

import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.WechatProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理未登录后的用户页面跳转
 * @author chenwei
 */
public class LoginUrlEntryPoint implements AuthenticationEntryPoint {


    private String wechatRedirectLoginURL;

    public String getWechatRedirectLoginURL() {
        return wechatRedirectLoginURL;
    }

    public void setWechatRedirectLoginURL(String wechatRedirectLoginURL) {
        this.wechatRedirectLoginURL = wechatRedirectLoginURL;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException arg2) throws IOException, ServletException {
        //判断是否为ajax请求
        String requestType = request.getHeader("X-Requested-With");
        if(requestType !=null && "XMLHttpRequest".equals(requestType)){
            //设置返回状态代码 901表示未登录 902无权限 903session并发
            response.setStatus(901);
        }


        // 微信
        String code = request.getParameter("code");
        String type = request.getParameter("state");
        String msgid = request.getParameter("id");

        String userAgent = request.getHeader("USER-AGENT" );
        // 微信访问
        if(StringUtils.isNotEmpty(userAgent) && userAgent.toLowerCase().indexOf("micromessenger") > 0 && StringUtils.isEmpty(code)) {
            String wechatLoginType="";

            Cookie[] cookies = request.getCookies();//这样便可以获取一个cookie数组
            for(Cookie cookie : cookies){
                if("wechatLoginType".equals(cookie.getName())){
                    wechatLoginType =cookie.getValue();
                }
            }
            String corpid = "";
            String target = "";
            if("company".equals(wechatLoginType)){
                corpid= WechatProperties.getValue("corpid");
                target= URLEncoder.encode(new String(request.getRequestURL()).substring(7));     // 访问目标url
            }else if("service".equals(wechatLoginType)){
                corpid= WechatProperties.getValue("serviceid");
                target= URLEncoder.encode("http://"+new String(request.getRequestURL()).substring(7));     // 访问目标url
            }

            String[] param = {corpid, target,wechatLoginType};

            String redirect=String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect", param);
            response.sendRedirect(redirect);
//            response.setContentType("text/html;charset=UTF-8");
//            response.getWriter().write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">");
//            response.getWriter().write("<html>");
//            response.getWriter().write("<head>");
//            response.getWriter().write("<title>微信登陆中...</title>");
//            response.getWriter().write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
//            response.getWriter().write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
//            response.getWriter().write("<meta http-equiv=\"pragma\" content=\"no-cache\"/>");
//            response.getWriter().write("<meta http-equiv=\"cache-control\" content=\"no-cache\"/>");
//            response.getWriter().write("<meta http-equiv=\"expires\" content=\"0\"/>");
//            response.getWriter().write("</head>");
//            response.getWriter().write("<body>");
//            response.getWriter().write("微信登陆中...");
//            response.getWriter().write("</body>");
////            response.getWriter().write("<script type=\"text/javascript\">setTimeout(function(){window.location.href=\"" + redirect + "\"}, 1000);</script>");
//            response.getWriter().write("</html>");
//            response.sendRedirect(redirect);
            return ;
        }

        if(StringUtils.isNotEmpty(userAgent) && userAgent.toLowerCase().indexOf("micromessenger") > 0 && StringUtils.isNotEmpty(code)) {

            // 重定向到登录页面
            String url = URLEncoder.encode(new String(request.getRequestURL()));
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + (request.getServerPort() == 80 ? "" : ":" + request.getServerPort()) + path;

            response.sendRedirect(basePath + wechatRedirectLoginURL + "?type=wechat&code=" + code + "&url=" + url+"&msgid="+msgid+"&urltype="+type);
            return ;
        }

        // 访问 ^/goshopping/.* 的链接，需要登录
        String requestUrl = request.getRequestURI().substring(request.getContextPath().length());
        String requestHeader = request.getHeader("user-agent");
        Pattern pattern = Pattern.compile("^/web/.*");
        Matcher matcher = pattern.matcher(requestUrl);
        if(matcher.matches()) {

            if(CommonUtils.isMobileDevice(requestHeader)) {

                String targetUrl = "/phone/weblogin";
                targetUrl = request.getContextPath() + targetUrl;
                response.sendRedirect(targetUrl);
                return ;
            } else {
                String targetUrl = "/weblogin.do";
                targetUrl = request.getContextPath() + targetUrl;
                response.sendRedirect(targetUrl);
                return ;
            }
        }

        String targetUrl = "/noLogin.do";
        targetUrl = request.getContextPath() + targetUrl;
        response.sendRedirect(targetUrl);
    }




//	@Override
//	public void commence(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException arg2) throws IOException, ServletException {
//        //判断是否为ajax请求
//        String requestType = request.getHeader("X-Requested-With");
//        if(requestType !=null && "XMLHttpRequest".equals(requestType)){
//        	//设置返回状态代码 901表示未登录 902无权限 903session并发
//        	response.setStatus(901);
//        }else{
//        	String targetUrl = "/noLogin.do";
//            targetUrl = request.getContextPath() + targetUrl;
//            response.sendRedirect(targetUrl);
//        }
//	}

}

