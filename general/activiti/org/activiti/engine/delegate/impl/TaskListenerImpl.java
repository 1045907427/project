package org.activiti.engine.delegate.impl;

import com.hd.agent.accesscontrol.dao.SysUserMapper;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.activiti.model.DataTrace;
import com.hd.agent.activiti.model.HandlerLog;
import com.hd.agent.activiti.model.Process;
import com.hd.agent.activiti.service.IWorkService;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.oa.service.IOaBusinessService;
import com.hd.agent.oa.service.IOaPayService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.*;
import java.util.Map;

/**
 * Created by limin on 2016/4/13.
 */
public class TaskListenerImpl implements TaskListener {

    protected IOaPayService payService;

    protected IOaBusinessService businessService;

    protected IWorkService workService;

    protected SysUserMapper sysUserMapper;

    public IOaPayService getPayService() {
        return payService;
    }

    public void setPayService(IOaPayService payService) {
        this.payService = payService;
    }

    public IOaBusinessService getBusinessService() {
        return businessService;
    }

    public void setBusinessService(IOaBusinessService businessService) {
        this.businessService = businessService;
    }

    public IWorkService getWorkService() {
        return workService;
    }

    public void setWorkService(IWorkService workService) {
        this.workService = workService;
    }

    public SysUserMapper getSysUserMapper() {
        return sysUserMapper;
    }

    public void setSysUserMapper(SysUserMapper sysUserMapper) {
        this.sysUserMapper = sysUserMapper;
    }

    @Override
    public void notify(DelegateTask paramDelegateTask) throws Exception {

    }

    protected int addHandlerLog(DelegateTask task, Class clazz, Object returnobj, Object... params) throws Exception {

        String instanceid = task.getProcessInstanceId();

        Process process = workService.getProcess(instanceid, "2");

        if(process != null) {

            SysUser user = getSysUser();

            HandlerLog hl = new HandlerLog();
            String definitionid = task.getProcessDefinitionId();
            String definitionkey = definitionid.split(":")[0];

            hl.setDefinitionkey(definitionkey);
            hl.setDefinitionid(definitionid);
            hl.setProcessid(process.getId());
            hl.setTaskkey(task.getTaskDefinitionKey());
            hl.setTaskname(task.getName());
            hl.setEvent(task.getEventName());
            hl.setHandler(clazz.getName());
            hl.setClazz(clazz.getName());
            hl.setHandlerdescription(clazz.getName());
            hl.setLoguserid(user.getUserid());
            hl.setLogusername(user.getName());

            switch (params.length) {
                case 5: {
                    hl.setParam5(objToBytes(params[4]));
                    hl.setParam5clazz(params[4].getClass().getName());
                }
                case 4: {
                    hl.setParam4(objToBytes(params[3]));
                    hl.setParam4clazz(params[3].getClass().getName());
                }
                case 3: {
                    hl.setParam3(objToBytes(params[2]));
                    hl.setParam3clazz(params[2].getClass().getName());
                }
                case 2: {
                    hl.setParam2(objToBytes(params[1]));
                    hl.setParam2clazz(params[1].getClass().getName());
                }
                case 1: {
                    hl.setParam1(objToBytes(params[0]));
                    hl.setParam1clazz(params[0].getClass().getName());
                }
            }
            hl.setReturnobj(objToBytes(returnobj));
            hl.setReturnobjclazz(returnobj.getClass().getName());

            int ret = workService.addHandlerLog(hl);
            return ret;
        }
        return 0;
    }

    /**
     * 获取当前登录的用户
     * @return
     * @throws Exception
     * @author chenwei
     * @date Feb 27, 2013
     */
    public SysUser getSysUser() throws Exception{
        //获取security中的用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null!=authentication){
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            String username1 = username;
            if(username.startsWith("P_")){
                String[] strArr = username.split("P_");
                username1 = strArr[1];
            }
            //根据用户名获取用户详细信息
            SysUser sysUser = sysUserMapper.getUser(username1);
            if(username.startsWith("P_")){
                sysUser.setLoginType("2");
            }
            return sysUser;
        }else{
            return null;
        }
    }

    /**
     * Object → byte[]
     * @param obj
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private byte[] objToBytes(Object obj) throws IOException {

        if(obj == null) {
            return null;
        }

        byte[] bytes = null;
        ByteArrayOutputStream bo = null;
        ObjectOutputStream oo = null;

        try {
            bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            oo.close();
            bo.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if(oo != null) {
                oo.close();
                oo.flush();
            }
            if(bo != null) {
                bo.close();
                bo.flush();
            }
        }

        return bytes;
    }

    /**
     * 记录data trace
     * @param process
     * @param data
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    protected int addDataTrace(Process process, Map data) throws Exception {

        try {

            DataTrace trace = new DataTrace();
            SysUser user = getSysUser();
            trace.setProcessid(Integer.parseInt(process.getId()));
            trace.setTrace(JSONUtils.mapToJsonStr(data).getBytes("UTF-8"));
            trace.setAdduserid(user.getUserid());
            trace.setAddusername(user.getName());
            trace.setAdddeptid(user.getDepartmentid());
            trace.setAdddeptname(user.getDepartmentname());
            trace.setDefinitionid(process.getDefinitionid());
            trace.setTaskid(process.getTaskid());
            trace.setTaskkey(process.getTaskkey());
            trace.setTaskname(process.getTaskname());

            int ret = workService.addDataTrace(trace);
            return ret;

        }catch (Exception e) {
            return 0;
        }
    }

    /**
     * 从根据task 获取process 情报
     * @param task
     * @return
     * @throws Exception
     */
    protected Process getProcess(DelegateTask task) throws Exception {

        String instanceid = task.getProcessInstanceId();
        Process process = workService.getProcess(instanceid, "2");

        return process;
    }

}
