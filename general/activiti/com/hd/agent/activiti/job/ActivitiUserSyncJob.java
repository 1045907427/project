/**
 * @(#)ActivitiUserSyncJob.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2017-6-7 limin 创建版本
 */
package com.hd.agent.activiti.job;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.accesscontrol.service.ISysUserService;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.job.BaseJob;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * activiti用户与erp系统用户同步
 * Created by limin on 2017/6/7.
 */
public class ActivitiUserSyncJob extends BaseJob {

    private IdentityService identityService;

    private ISysUserService sysUserService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext)
            throws JobExecutionException {
        try {

//            sysUserService = (ISysUserService)SpringContextUtils.getBean("sysUserService");
//            identityService = (IdentityService)SpringContextUtils.getBean("identityService");
//
//            List<SysUser> sysUserList = sysUserService.getSysUserList();
//            for(SysUser erpUser : sysUserList) {
//
//                User activitiUser = identityService.createUserQuery().userId(erpUser.getUserid()).singleResult();
////                User newActivitiUser = null;
//                if(activitiUser == null) {
//                    activitiUser = new UserEntity();
//                }
//                activitiUser.setId(erpUser.getUserid());
//                activitiUser.setFirstName(erpUser.getName());
//                activitiUser.setLastName(erpUser.getName());
//                activitiUser.setEmail(erpUser.getEmail());
//                activitiUser.setPassword(erpUser.getPassword());
//                identityService.saveUser(activitiUser);
//            }

            flag = true;
        } catch (Exception e) {
            logger.error("定时器执行异常 Activiti用户同步失败", e);
        }
        super.executeInternal(jobExecutionContext);
    }

}
