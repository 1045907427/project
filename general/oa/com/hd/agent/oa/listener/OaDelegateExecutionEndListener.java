/**
 * @(#)OaDelegateExecutionEndListener.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015-1-14 limin 创建版本
 */
package com.hd.agent.oa.listener;

import com.hd.agent.oa.model.OaDelegate;
import com.hd.agent.oa.service.IOaBusinessService;
import com.hd.agent.oa.service.IOaDelegateService;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.impl.BusinessEndListenerImpl;

/**
 * 工作委托申请单End Listener
 *
 * @author limin
 */
public class OaDelegateExecutionEndListener extends BusinessEndListenerImpl {

    private IOaDelegateService oaDelegateService;

    private IOaBusinessService businessService;

    public IOaDelegateService getOaDelegateService() {
        return oaDelegateService;
    }

    public void setOaDelegateService(IOaDelegateService oaDelegateService) {
        this.oaDelegateService = oaDelegateService;
    }

    public IOaBusinessService getBusinessService() {
        return businessService;
    }

    public void setBusinessService(IOaBusinessService businessService) {
        this.businessService = businessService;
    }

    @Override
    public void delete(Process process) throws Exception {

        if(process == null || StringUtils.isEmpty(process.getBusinessid())) {

            return ;
        }

        OaDelegate delegate = oaDelegateService.selectOaDelegate(process.getBusinessid());
        if(delegate == null) {

            return ;
        }

        businessService.rollbackDelegateByOaDelegate(delegate);
    }

    @Override
    public void end(Process process) throws Exception {

    }

    @Override
    public boolean check(Process process) throws Exception {

        if(process == null || StringUtils.isEmpty(process.getBusinessid())) {

            return true;
        }

        OaDelegate delegate = oaDelegateService.selectOaDelegate(process.getBusinessid());
        if(delegate == null) {

            return true;
        }

        return businessService.checkDelegateByOaDelegate(delegate);
    }

}

