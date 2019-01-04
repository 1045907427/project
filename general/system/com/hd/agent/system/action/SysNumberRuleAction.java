/**
 * @(#)SysNumberRuleAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-27 panxiaoxiao 创建版本
 */
package com.hd.agent.system.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.system.model.SysNumber;
import com.hd.agent.system.model.SysNumberRule;
import com.hd.agent.system.service.ISysNumberRuleService;
import com.hd.agent.system.service.ISysNumberService;

/**
 * 单据编号
 * 对映struts-system配置
 * @author panxiaoxiao
 */
public class SysNumberRuleAction extends BaseAction{

	private SysNumber sysNumber;
	public SysNumber getSysNumber() {
		return sysNumber;
	}
	public void setSysNumber(SysNumber sysNumber) {
		this.sysNumber = sysNumber;
	}

	private SysNumberRule sysNumberRule;
	private ISysNumberRuleService sysNumberRuleService;
	private ISysNumberService sysNumberService;
	public ISysNumberService getSysNumberService() {
		return sysNumberService;
	}
	public void setSysNumberService(ISysNumberService sysNumberService) {
		this.sysNumberService = sysNumberService;
	}
	public SysNumberRule getSysNumberRule() {
		return sysNumberRule;
	}
	public void setSysNumberRule(SysNumberRule sysNumberRule) {
		this.sysNumberRule = sysNumberRule;
	}
	public ISysNumberRuleService getSysNumberRuleService() {
		return sysNumberRuleService;
	}
	public void setSysNumberRuleService(ISysNumberRuleService sysNumberRuleService) {
		this.sysNumberRuleService = sysNumberRuleService;
	}
	
	/**
	 * 查看单据编号规则详情-列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-27
	 */
	public String showSysNumberRuleList()throws Exception{
		String numberid=request.getParameter("numberid");
		List sysNumberRuleList=sysNumberRuleService.showSysNumberRuleList(numberid);
		addJSONArray(sysNumberRuleList);
		return "success";
	}
	
	/**
	 * 显示单据编号规则添加页面 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-11
	 */
	public String showSysNumberRuleAddPage() throws Exception{
		String seriallength=request.getParameter("seriallength");
		String numberid=request.getParameter("numberid");
		String serialstart=request.getParameter("serialstart");
		request.setAttribute("numberid", numberid);
		request.setAttribute("seriallength", seriallength);
		request.setAttribute("serialstart", serialstart);
		return "success";
	}
	/**
	 * 显示单据编号规则编辑页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-11
	 */
	public String showSysNumberRuleEditPage() throws Exception{
		String seriallength=request.getParameter("seriallength");
		String serialstart=request.getParameter("serialstart");
		String numberruleid=request.getParameter("numberruleid");
		request.setAttribute("seriallength", seriallength);
		request.setAttribute("serialstart", serialstart);
		SysNumberRule sysNumberRule = sysNumberRuleService.showSysNumberRuleInfo(numberruleid);
		String oldSysNumberRuleVal=sysNumberRule.getVal();
		request.setAttribute("sysNumberRule", sysNumberRule);
		request.setAttribute("oldSysNumberRuleVal", oldSysNumberRuleVal);
		return "success";
	}
	/**
	 * 添加单据编号规则
	 * @return
	 * @param newSysNumberRuleLength 新建单据编号规则的值
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-27
	 */
	@UserOperateLog(key="SysNumberRule",type=2,value="")
	public String addSysNumberRule()throws Exception{
		boolean flagNum=false, flag=false,flagRule=false;
		String sAddMiddle="",sLastSerail="";
		int newSysNumberRuleLength=0,checkSysNumberLength=0,nowLength=0,iState = 0;
		String numberid=request.getParameter("numberid");
		Map retMap = new HashMap();
		//流水号
		sysNumber.setAfterserialstart(null);
		boolean flagSysNumber = sysNumberService.editSysNumber(sysNumber);
		if(flagSysNumber){
			if(sysNumber.getSeriallength() != 0 && sysNumber.getSerialstart() != 0){
				for(int l=0;l<sysNumber.getSeriallength()-String.valueOf(sysNumber.getSerialstart()).length();l++){
					sLastSerail="0"+sLastSerail;
				}
				sLastSerail=sLastSerail+String.valueOf(sysNumber.getSerialstart());
			}
		}
		//判断类型是否为固定值
		if("1".equals(sysNumberRule.getColtype())){
			sAddMiddle=sysNumberRule.getVal();
		}
		else{
			if(null != sysNumberRule.getLength()){
				nowLength= sysNumberRule.getLength();
				for(int i=0;i<nowLength;i++){
					sAddMiddle="*"+sAddMiddle;
				}
				
			}
		}
		//判断单据编号规则是否存在
		String sPreview=sysNumberRuleService.getSysNumberPreview(sysNumberRule.getNumberid());
		if(null != sPreview && !"".equals("sPreview")){
			newSysNumberRuleLength=(sysNumberRule.getPrefix()+sysNumberRule.getSuffix()).length()+nowLength;
			checkSysNumberLength=sPreview.length()+newSysNumberRuleLength+sysNumber.getSeriallength();
			if(20>=checkSysNumberLength){
				List<SysNumberRule> sysNumberRuleList=sysNumberRuleService.showSysNumberRuleList(numberid);
				for(SysNumberRule sysNumberRule : sysNumberRuleList){
					if("1".equals(sysNumberRule.getState())){
						iState = iState + 1;
					}
				}
				if(iState > 1){
					retMap.put("iState", iState);
				}
				else{
					//新的预览效果
					sPreview=sPreview+sysNumberRule.getPrefix()+sAddMiddle+sysNumberRule.getSuffix()+sLastSerail;
					flagRule=sysNumberRuleService.addSysNumberRule(sysNumberRule);
					if(flagRule){
						SysNumber sysNumber=new SysNumber();
						sysNumber.setNumberid(sysNumberRule.getNumberid());
						sysNumber.setPreview(sPreview);
						sysNumber.setSerialnumber(sLastSerail);
						flagNum=sysNumberService.editSysNumber(sysNumber);
						
						if(flagNum){
							flag=true;
							retMap.put("serailNum", sLastSerail);
						}
					}
				}
			}
			else{
				retMap.put("sTooLong", "sTooLong");
			}
		}
		else{
			sPreview=sysNumberRule.getPrefix()+sAddMiddle+sysNumberRule.getSuffix()+sLastSerail;
			if(sPreview.length()<=20){
				List<SysNumberRule> sysNumberRuleList=sysNumberRuleService.showSysNumberRuleList(numberid);
				for(SysNumberRule sysNumberRule : sysNumberRuleList){
					if("1".equals(sysNumberRule.getState())){
						iState = iState + 1;
					}
				}
				if(iState > 1){
					retMap.put("iState", iState);
				}
				else{
					flagRule=sysNumberRuleService.addSysNumberRule(sysNumberRule);
					if(flagRule){
						SysNumber sysNumber=new SysNumber();
						sysNumber.setNumberid(sysNumberRule.getNumberid());
						sysNumber.setPreview(sPreview);
						sysNumber.setSerialnumber(sLastSerail);
						flagNum=sysNumberService.editSysNumber(sysNumber);
						
						if(flagNum){
							flag=true;
							retMap.put("serailNum", sLastSerail);
						}
					}
				}
			}
			else{
				retMap.put("sTooLong", "sTooLong");
			}
		}
		retMap.put("flag", flag);
		//添加日志内容
		addLog("新增单据编号规则  编号:"+sysNumberRule.getNumberruleid(),flag);
		addJSONObject(retMap);
		return "success";
	}
	/**
	 * 修改单据编号规则
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-27
	 */
	@UserOperateLog(key="SysNumberRule",type=3,value="")
	public String editSysNumberRule()throws Exception{
		String sLastSerail="";
		int iSysNumberRuleValLength=0,newNumberPreView=0,oldSysNumberRuleValLength=0,iState=0;//iState是否为流水依据的个数
		boolean flagNum=false, flag=false,flagRule=false;
		Map retMap = new HashMap();
		String oldSysNumberRuleVal=request.getParameter("sysNumberRuleOldSysNumberRuleVal");
		int oldNumberPreView=sysNumberRuleService.getSysNumberPreview(sysNumberRule.getNumberid()).length();//原来浏览效果的总长度
		String sSysNumberRuleVal=sysNumberRule.getVal();
		if(null != sSysNumberRuleVal || "".equals(sSysNumberRuleVal)){
			iSysNumberRuleValLength=sSysNumberRuleVal.length();//更改的值的长度
		}
		if(null != oldSysNumberRuleVal || "".equals(oldSysNumberRuleVal)){
			oldSysNumberRuleValLength=oldSysNumberRuleVal.length();//原来值得长度
		}
		newNumberPreView=oldNumberPreView-oldSysNumberRuleValLength+iSysNumberRuleValLength;
		if(newNumberPreView >= 20){
			retMap.put("sTooLong", "sTooLong");
		}
		else{
			String numberid=request.getParameter("numberid");
			List<SysNumberRule> sysNumberRuleList=sysNumberRuleService.showSysNumberRuleList(numberid);
			for(SysNumberRule sysNumberRule : sysNumberRuleList){
				if("1".equals(sysNumberRule.getState())){
					iState = iState + 1;
				}
			}
			if(iState > 1){
				retMap.put("iState", iState);
			}
			else{
				flagRule=sysNumberRuleService.editSysNumberRule(sysNumberRule);
				if(flagRule == true){
					if(sysNumber.getSeriallength() != 0 && sysNumber.getSerialstart() != 0){
						for(int l=0;l<sysNumber.getSeriallength()-String.valueOf(sysNumber.getSerialstart()).length();l++){
							sLastSerail="0"+sLastSerail;
						}
						sLastSerail=sLastSerail+String.valueOf(sysNumber.getSerialstart());
					}
					String sPreview=sysNumberRuleService.getSysNumberPreview(sysNumberRule.getNumberid())+sLastSerail;
					SysNumber sysNumber=new SysNumber();
					sysNumber.setNumberid(sysNumberRule.getNumberid());
					sysNumber.setPreview(sPreview);
					flagNum=sysNumberService.editSysNumber(sysNumber);
				}
				if(flagRule == true && flagNum == true){
					flag=true;
				}
			}
		}
		retMap.put("flag", flag);
		//添加日志内容
		addLog("修改单据编号规则  编号:"+sysNumberRule.getNumberruleid(),flag);
		addJSONObject(retMap);
		return "success";
	}
	/**
	 * 删除单据编号规则
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-27
	 */
	@UserOperateLog(key="SysNumberRule",type=4,value="")
	public String deleteSysNumberRule()throws Exception{
		boolean flagNum=false, flag=false;
		String sLastSerail="",sPreview="";
		String numberruleid=request.getParameter("numberruleid");
		String numberid=request.getParameter("numberid");
		boolean flagRule=sysNumberRuleService.deleteSysNumberRule(numberruleid);
		if(flagRule == true){
			String retPreview = sysNumberRuleService.getSysNumberPreview(numberid);
			if(null != retPreview||"".equals(retPreview)||"null".equals(retPreview)){
				SysNumber sysNumberInfo=sysNumberService.getSysNumberInfo(numberid);
				//流水号
				if(sysNumberInfo.getSeriallength() != 0 && sysNumberInfo.getSerialstart() != 0){
					for(int l=0;l<sysNumberInfo.getSeriallength()-String.valueOf(sysNumberInfo.getSerialstart()).length();l++){
						sLastSerail="0"+sLastSerail;
					}
					sLastSerail=sLastSerail+String.valueOf(sysNumberInfo.getSerialstart());
				}
				sPreview=retPreview+sLastSerail;
			}
			SysNumber sysNumber=new SysNumber();
			sysNumber.setNumberid(numberid);
			sysNumber.setPreview(sPreview);
			flagNum=sysNumberService.editSysNumber(sysNumber);
		}
		if(flagRule == true && flagNum == true){
			flag=true;
		}
		addJSONObject("flag", flag);
		//添加日志内容
		addLog("删除单据编号规则  编号:"+numberruleid,flag);
		return "success";
	}

//	public String isFlowAccording()throws Exception{
//		String coltype=request.getParameter("coltype");
//		List jsonList = new ArrayList(); 
//		if("1" == coltype){
//			Map map = new HashMap();
//			map.put("value", "0");
//			map.put("textname","否" );
//			jsonList.add(map);
//			addJSONArray(jsonList);
//		}
//		if("2" == coltype){
//			
//		}
//		return "success";
//	}
}

