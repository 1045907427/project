package com.hd.agent.message.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.MobileSms;
import com.hd.agent.message.service.IMobileSmsService;
import com.hd.agent.system.service.ITaskScheduleService;

public class MobileSmsAction  extends BaseAction {
	
	private ITaskScheduleService taskScheduleService;
	/**
	 * 短信内容
	 */
	private MobileSms smsContent;
	private IMobileSmsService mobileSmsService;

	public MobileSms getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(MobileSms smsContent) {
		this.smsContent = smsContent;
	}
	
	public IMobileSmsService getMobileSmsService() {
		return mobileSmsService;
	}

	public void setMobileSmsService(IMobileSmsService mobileSmsService) {
		this.mobileSmsService = mobileSmsService;
	}

	public ITaskScheduleService getTaskScheduleService() {
		return taskScheduleService;
	}

	public void setTaskScheduleService(ITaskScheduleService taskScheduleService) {
		this.taskScheduleService = taskScheduleService;
	}

	/**
	 * 短信发送页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-7
	 */
	@UserOperateLog(key="Message-MobileSms",type=2)
	public String smsSendPage() throws Exception{
		request.setAttribute("now", (new Date()));
		SysUser sysUser=getSysUser();
		request.setAttribute("username", sysUser.getName());
		request.setAttribute("deptname", sysUser.getDepartmentname());
		String msgSendPageId=request.getParameter("msgSendPageId");
		if(null==msgSendPageId || "".equals(msgSendPageId)){
			msgSendPageId="";
		}
		request.setAttribute("msgSendPageId", msgSendPageId);
		return "success";
	}
	
	/**
	 * 短信发送
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-7
	 */
	public String mobileSmsSend() throws Exception{
		//操作标志
		boolean flag=false;
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.isEmpty(smsContent.getReceivers()) && StringUtils.isEmpty(smsContent.getRecvphones())&& StringUtils.isEmpty(smsContent.getRecvpersons())){
			map.put("flag", flag);
			map.put("msg", "短信接收人不能为空");
			
			addJSONObject(map);
			return "success";	
		}
		if(StringUtils.isEmpty( smsContent.getContent())){
			map.put("flag", flag);
			map.put("msg", "请输入短信内容不能为空");
			
			addJSONObject(map);
			return "success";			
		}
		Date now=new Date();
		if(null!=smsContent.getSendtime()){
			if((now.getTime() > smsContent.getSendtime().getTime()) && (now.getTime() - smsContent.getSendtime().getTime() > 600000)){
				map.put("flag", flag);
				map.put("msg", "抱歉，请重新填写发送时间");
				
				addJSONObject(map);
				return "success";	
			}
		}
		SysUser sysUser=getSysUser();
		smsContent.setAdduserid(sysUser.getUserid());
		String serialno=mobileSmsService.getSerialno();
		if(StringUtils.isEmpty(serialno)){
			serialno=CommonUtils.getDataNumber();
		}
		smsContent.setSerialno(serialno);
		map.clear();
		String[] sarr=null;
		List<MobileSms> smsrList=new ArrayList<MobileSms>();
		List<String> mobiList=new ArrayList<String>();
		List<String> idList=new ArrayList<String>();
		MobileSms smsr=null;
		if(StringUtils.isNotEmpty(smsContent.getReceivers())){
			sarr=smsContent.getReceivers().split(",");
			
			for(String item: sarr){
				if(null!=item&&!"".equals(item.trim())){
					idList.add(item);
				}
			}
			if(idList.size()>0){
				sarr=idList.toArray((new String[0]));
				map.put("userList", sarr);
				PageMap pmap=new PageMap();
				pmap.setCondition(map);
				PageData pageData=getSysUserListByConditon(pmap,false);
				if(pageData!=null && pageData.getList()!=null){
					List<SysUser> syslist=pageData.getList();
					for(SysUser item : syslist){
						if(StringUtils.isNotEmpty(item.getMobilephone())){
							mobiList.add(item.getMobilephone());
							smsr=new MobileSms();
							smsr.setAdduserid(sysUser.getUserid());
							smsr.setDelflag("1");
							smsr.setSendflag("1");
							smsr.setContent(smsContent.getContent());
							smsr.setSendtime(smsContent.getSendtime());
							smsr.setMobile(item.getMobilephone());
							smsr.setRecvuserid(item.getUserid());
							smsr.setSerialno(smsContent.getSerialno());
							smsrList.add(smsr);
						}
					}
				}
			}
		}

		if(StringUtils.isNotEmpty(smsContent.getRecvpersons())){
			sarr=smsContent.getRecvpersons().split(",");
			
			for(String item: sarr){
				if(null!=item&&!"".equals(item.trim())){
					idList.add(item);
				}
			}
			List<Personnel> perList=null;
			if(idList.size()>0){
				sarr=idList.toArray((new String[0]));
				perList=getPersonnelListByIds(StringUtils.join(sarr,","));
			}
			if(perList!=null && perList.size()>0){
				for(Personnel item : perList){
					if(StringUtils.isNotEmpty(item.getTelphone()) && !mobiList.contains(item.getTelphone())){
						smsr=new MobileSms();
						smsr.setAdduserid(sysUser.getUserid());
						smsr.setDelflag("1");
						smsr.setSendflag("1");
						smsr.setContent(smsContent.getContent());
						smsr.setSendtime(smsContent.getSendtime());
						smsr.setMobile(item.getTelphone());
						smsr.setSerialno(smsContent.getSerialno());
						smsrList.add(smsr);						
					}
				}
			}
		}
		
		if(StringUtils.isNotEmpty(smsContent.getRecvphones())){
			sarr=smsContent.getRecvphones().split(",");
			
			for(String item : sarr){
				if(StringUtils.isNotEmpty(item) && !mobiList.contains(item)){
					smsr=new MobileSms();
					smsr.setAdduserid(sysUser.getUserid());
					smsr.setDelflag("1");
					smsr.setSendflag("1");
					smsr.setContent(smsContent.getContent());
					smsr.setSendtime(smsContent.getSendtime());
					smsr.setSerialno(smsContent.getSerialno());
					smsr.setMobile(item);
					smsrList.add(smsr);
				}
			}
		}
		
		
		
		flag=mobileSmsService.addMobileSmsList(smsrList);
		//返回json字符串
		addJSONObject("flag",flag);
		logStr="添加手机短信到数据";
		return "success";
	}
	
	/**
	 * 手机短信管理
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-8
	 */
	public String smsListPage() throws Exception{
		return "success";
	}
	/**
	 * 手机短信发送分页列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-8
	 */
	public String smsSendListPage() throws Exception{
		return "success";
	}
	/**
	 * 手机短信收件分页列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-8
	 */
	public String smsReceiveListPage() throws Exception{
		return "success";
	}
	
	/**
	 * 手机短信发送分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-8
	 */
	public String showSendMobileSmsPageList() throws Exception{
		SysUser sysUser=getSysUser();
	
		// 获取页面传过来的参数 封装到map里面
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());

		queryMap.put("adduserid", sysUser.getUserid());

		if(!queryMap.containsKey("delflag")){
			queryMap.put("delflag", "1");	//delflag=1表示未删除
		}
		
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(queryMap);
		PageData pageData=mobileSmsService.showSendMobileSmsPageList(pageMap);
		addJSONObject(pageData);
		logStr="手机短信消息已发送分页数据";
		return "success";
	}
	/**
	 * 手机短信接收分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-8
	 */
	public String showReceiveMobileSmsPageList() throws Exception{
		SysUser sysUser=getSysUser();
	
		// 获取页面传过来的参数 封装到map里面
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		
		queryMap.put("recvuserid", sysUser.getUserid());

		queryMap.put("sendflag","0");

		queryMap.put("delflag", "1");	//delfalg表示未删除
		
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(queryMap);
		PageData pageData=mobileSmsService.showReceiveMobileSmsPageList(pageMap);
		addJSONObject(pageData);
		logStr="手机短信消息已发送分页数据";
		return "success";
	}
	/**
	 * 删除手机短信
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-8
	 */
	@UserOperateLog(key="Message-MobileSms",type=4)
	public String deleteMobileSms() throws Exception{
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		String ids=request.getParameter("ids");
		Map<String,Object> map=new HashMap<String,Object>();	
		if(StringUtils.isEmpty(ids)){
			map.put("msg", "请选择要删除的短信");
			map.put("flag", false);	
			addJSONObject(map);
			return "success";		
		}
		String[] idarr=ids.split(",");
		if(idarr.length==0){
			map.put("msg", "请选择要删除的短信");
			map.put("flag", false);	
			addJSONObject(map);
			return "success";
		}
		if(idarr.length>0){
			ismuti=true;
		}
		map.clear();
		SysUser sysUser = getSysUser();
		map.put("delflag", "0");
		map.put("deltime", (new Date()));
		map.put("adduserid", sysUser.getUserid());
		map.put("wnotsendflag", "0");
		map.put("isdataauth", "1");	//检查数据权限
		for(String item: idarr){
			if(item !=null && !"".equals(item.trim())){
				map.put("id", item.trim());
				if(mobileSmsService.updateMobileSmsBy(map)){
					iSuccess=iSuccess+1;
				}else{
					iFailure=iFailure+1;
				}
			}else{
				iNohandle=iNohandle+1;
			}
		}
		Map<String, Object> msgMap=new HashMap<String, Object>();		
		msgMap.put("flag", true);
		msgMap.put("ismuti", ismuti);
		msgMap.put("isuccess", iSuccess);
		msgMap.put("ifailure", iFailure);
		msgMap.put("inohandle", iNohandle);
		addJSONObject(msgMap);
		return "success";
	}
}
