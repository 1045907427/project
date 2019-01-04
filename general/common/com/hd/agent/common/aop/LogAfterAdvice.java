/**
 * @(#)LogAfterAdvice.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-11 chenwei 创建版本
 */
package com.hd.agent.common.aop;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.model.SysLog;
import com.hd.agent.common.service.ISysLogService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.StrutsStatics;
import org.springframework.aop.AfterReturningAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class LogAfterAdvice implements AfterReturningAdvice {
	private static final Logger logger = Logger.getLogger(LogAfterAdvice.class);

	private ISysLogService sysLogService;

	public void setSysLogService(ISysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}
	
	public void afterReturning(Object arg0, Method arg1, Object[] arg2,
			Object arg3) {
		//判断方法是否注解了UserOperateLog
		if("success".equals(arg0)){
			UserOperateLog anno = arg1.getAnnotation(UserOperateLog.class);  
	        if(null!=anno){
	        	ActionContext ac = ActionContext.getContext();
				HttpServletRequest request = (HttpServletRequest) ac.get(StrutsStatics.HTTP_REQUEST);
	        	BaseAction baseAction = (BaseAction) arg3;
				String message = null;
				//判断是否有自定义的日志内容
				if(null!=baseAction&&null!=baseAction.getLogStr()&&!"".equals(baseAction.getLogStr())){
					message = baseAction.getLogStr();
				}else{
					message = anno.value();
				}
	        	int type = anno.type();
	        	String key = anno.key();
	        	try {
	        		//获取当前登录的用户
					SysUser sysUser = baseAction.getSysUser();
                    SysLog sysLog = new SysLog();
                    String dataid = CommonUtils.getDataNumberSendsWithRand();
                    //修改操作 添加修改内容
                    if(type==3){
                        String jsonLog = request.getParameter("oldFromData");
                        if(StringUtils.isNotEmpty(jsonLog)){
                            Map oldmap = JSONUtils.jsonStrToMap(jsonLog);
                            Map newmap = CommonUtils.changeMap(request.getParameterMap());
                            Map changeMap = new HashMap();
                            if(null!=newmap){
                                newmap.remove("oldFromData");
                            }
                            Set set = newmap.entrySet();
                            Iterator i = set.iterator();
                            while(i.hasNext()){
                                Map.Entry<String, String> entry=(Map.Entry<String, String>)i.next();
                                String keyStr = entry.getKey();
                                String newValue = entry.getValue();
                                String oldValue = (String) oldmap.get(keyStr);
                                boolean flag = false;
                                if(null!=newValue && newValue.equals(oldValue)){
                                    flag = true;
                                }else if(StringUtils.isEmpty(newValue) && StringUtils.isEmpty(oldValue)){
                                    flag = true;
                                }
                                if(!flag){
                                    if(StringUtils.isEmpty(oldValue)){
                                        oldValue = "(空)";
                                    }
                                    if(StringUtils.isEmpty(newValue)){
                                        newValue = "(空)";
                                    }
                                    String changeLog = oldValue+" ===> " +newValue;
                                    changeMap.put(keyStr,changeLog);
                                }
                            }
                            //判断记录的数据长度 超长后处理
                            if(StringUtils.isNotEmpty(jsonLog) &&jsonLog.length()>60000){
                                jsonLog = jsonLog.substring(0,60000);
                            }
                            String newData = JSONUtils.mapToJsonStr(newmap);
                            if(StringUtils.isNotEmpty(newData) &&newData.length()>60000){
                                newData = newData.substring(0,60000);
                            }
                            String changedata = JSONUtils.mapToJsonStr(changeMap);
                            if(StringUtils.isNotEmpty(changedata) &&changedata.length()>60000){
                                changedata = changedata.substring(0,60000);
                            }
                            boolean flag = sysLogService.addSysLogData(dataid,jsonLog,newData,changedata);
                            if(flag){
                                sysLog.setDataid(dataid);
                            }
                        }
                    }else if(type==2){
                        //新增操作添加新增的数据
                        Map map = CommonUtils.changeMap(request.getParameterMap());
                        String allLog = "";
                        if(null!=map){
                            map.remove("formChangeLog");
                            allLog = JSONUtils.mapToJsonStrClearNull(map);
                        }
                        if(StringUtils.isNotEmpty(allLog) &&allLog.length()>60000){
                            allLog = allLog.substring(0,60000);
                        }
                        boolean flag = sysLogService.addSysLogData(dataid,"",allLog,"");
                        if(flag){
                            sysLog.setDataid(dataid);
                        }
                    }

		        	sysLog.setKeyname(key);
		        	sysLog.setContent(message);
		        	sysLog.setType(type+"");
		        	sysLog.setIp(CommonUtils.getIP(request));
		        	sysLog.setUserid(sysUser.getUserid());
		        	if("2".equals(sysUser.getLoginType())){
		        		sysLog.setName(sysUser.getName()+"(手机)");
		        	}else{
		        		sysLog.setName(sysUser.getName());
		        	}
		        	sysLogService.addSysLog(sysLog);
				} catch (Exception e) {
					logger.error(e, e);
				}
	        	
	        }
		}
	}
}
