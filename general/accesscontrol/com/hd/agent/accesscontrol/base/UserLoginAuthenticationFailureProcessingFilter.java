package com.hd.agent.accesscontrol.base;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wanghongteng on 2017/2/8.
 */
public class UserLoginAuthenticationFailureProcessingFilter extends SimpleUrlAuthenticationFailureHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private String defaultFailureUrl;
    private String wechatFailureUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {
        String AuthenticationFailureFailureUrl=   (String)request.getSession().getAttribute("AuthenticationFailureFailureUrl");
        if(StringUtils.isNotEmpty(AuthenticationFailureFailureUrl)){
            if("wechat".equals(AuthenticationFailureFailureUrl)){
                redirectStrategy.sendRedirect(request, response, wechatFailureUrl);
            }else{
                redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
            }
        }else{
            redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
        }

//        super.onAuthenticationFailure(request, response, exception);
    }


    public void setDefaultFailureUrl(String defaultFailureUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl), "'"
                + defaultFailureUrl + "' is not a valid redirect URL");
        this.defaultFailureUrl = defaultFailureUrl;
    }

    public void setWechatFailureUrl(String wechatFailureUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(wechatFailureUrl), "'"
                + wechatFailureUrl + "' is not a valid redirect URL");
        this.wechatFailureUrl = wechatFailureUrl;
    }
}
