/**
 * @(#)OaDelegateAction.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2016-4-21 limin 创建版本
 */
package com.hd.agent.oa.action;


import com.hd.agent.activiti.model.Process;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.oa.model.OaDelegate;
import com.hd.agent.oa.service.IOaDelegateService;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工作委托规则Action
 *
 * @author limin
 */
public class OaDelegateAction extends BaseOaAction {

    private OaDelegate delegate;

    private IOaDelegateService oaDelegateService;

    public OaDelegate getDelegate() {
        return delegate;
    }

    public void setDelegate(OaDelegate delegate) {
        this.delegate = delegate;
    }

    public IOaDelegateService getOaDelegateService() {
        return oaDelegateService;
    }

    public void setOaDelegateService(IOaDelegateService oaDelegateService) {
        this.oaDelegateService = oaDelegateService;
    }

    /**
     * 工作委托页面
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 22, 2016
     */
    public String oaDelegatePage() throws Exception {

        return SUCCESS;
    }

    /**
     * 工作委托处理页面
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 22, 2016
     */
    public String oaDelegateHandlePage() throws Exception {

        boolean sign = "1".equals(request.getParameter("sign"));
        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        Process process = workService.getProcess(processid, "1");
        OaDelegate delegate = oaDelegateService.selectOaDelegate(id);

        request.setAttribute("process", process);
        request.setAttribute("delegate", delegate);
        request.setAttribute("userid", getSysUser().getUserid());

        if(TO_PHONE.equals(request.getParameter("to"))) {
            return sign ? TO_SIGN : TO_PHONE;
        }
        return SUCCESS;
    }

    /**
     * 工作委托处理页面
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 22, 2016
     */
    public String oaDelegateViewPage() throws Exception {

        String id = request.getParameter("id");
        String processid = request.getParameter("processid");

        Process process = workService.getProcess(processid, "1");
        OaDelegate delegate = oaDelegateService.selectOaDelegate(id);

        request.setAttribute("process", process);
        request.setAttribute("delegate", delegate);

        if(TO_PHONE.equals(request.getParameter("to"))) {
            return TO_PHONE;
        }
        return SUCCESS;
    }

    /**
     * 新增工作委托规则
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 22, 2016
     */
    public String addOaDelegate() throws Exception {

        int ret = oaDelegateService.addOaDelegate(delegate);

        Map map = new HashMap();
        map.put("flag", ret);
        map.put("backid", delegate.getId());
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 新增工作委托规则
     * @return
     * @throws Exception
     * @author limin
     * @date Apr 22, 2016
     */
    public String editOaDelegate() throws Exception {

        int ret = oaDelegateService.editOaDelegate(delegate);

        Map map = new HashMap();
        map.put("flag", ret);
        map.put("backid", delegate.getId());
        addJSONObject(map);
        return SUCCESS;
    }
}