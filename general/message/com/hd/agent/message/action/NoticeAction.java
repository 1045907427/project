/**
 * @(#)NoticeAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-25 zhanghonghui 创建版本
 */
package com.hd.agent.message.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.Authority;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.MsgNotice;
import com.hd.agent.message.model.MsgNoticeread;
import com.hd.agent.message.service.INoticeService;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class NoticeAction extends BaseAction {
	//公告通知服务
	private INoticeService noticeService;
	private MsgNotice msgNotice;
	
	public INoticeService getNoticeService() {
		return noticeService;
	}

	public void setNoticeService(INoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public MsgNotice getMsgNotice() {
		return msgNotice;
	}

	public void setMsgNotice(MsgNotice msgNotice) {
		this.msgNotice = msgNotice;
	}
	
	/**
	 * 首页显示最新业务
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-20
	 */
	public String noticeIndexShowListPage() throws Exception{
		SysUser sysUser=getSysUser();
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		queryMap.put("isqueryviewrang", "1");

		queryMap.put("delflag", "1");	//默认取未删除数据
		queryMap.put("state", "1");		//读取启用状态的数据
		queryMap.put("enddate", (new Date()));	//读取终止时间未到的数据
		queryMap.put("isshowcuruserrc", 1);	//显示阅读数
		if(!queryMap.containsKey("rows")){
			queryMap.put("rows", 10);
		}
		if(!queryMap.containsKey("order") && ! queryMap.containsKey("sort")){
			queryMap.put("sort","istop desc");
			queryMap.put("order", ",addtime desc");
		}
		queryMap.put("curuserid", sysUser.getUserid());	//当前用户编号
		queryMap.put("curuserdept", sysUser.getDepartmentid());	//当前用户部门编号
		
		
		String isCurUserNotRead=(String)queryMap.get("iscurusernotread");
		if("true".equals(isCurUserNotRead)){
			queryMap.put("isCurUserNotRead", "1");
			queryMap.put("notReadCountUserid", sysUser.getUserid());
		}
		
		//当前权限
		List<String> authList=getUserAuthorityList();
		List<String> roleList=new ArrayList<String>();
		if(null!=authList && authList.size()>0 ){
			for(String item : authList){
				if(null!=item && !"".equals(item)){
					roleList.add(item.trim());
				}
			}
			if(roleList.size()>0){
				queryMap.put("curusrolelist", roleList);
			}
		}
		
		queryMap.put("notQueryPageCount", "true");

		pageMap.setCondition(queryMap);
		PageData pageData=noticeService.showMsgNoticePageList(pageMap);
		List<MsgNotice> list=new ArrayList<MsgNotice>();
		if(pageData!=null && pageData.getList()!=null){
			list=pageData.getList();
		}
		request.setAttribute("dataList", list);
		return "success";
	}

	/**
	 * 公告通知分页
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public String noticeListPage() throws Exception{
		return "success";
	}

	/**
	 * 公告通知分页数据，既当前用户能看到的公告分页<br/>
	 * 当前用户：设置map中的key为isqueryviewrang,值为1<br/>
	 * 超级用户：不设置isqueryviewrang，或者值为1外的任何值 
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public String showNoticePageList()throws Exception{
		SysUser sysUser=getSysUser();
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		//显示查看范围
		queryMap.put("isqueryviewrang", "1");

		if(!queryMap.containsKey("delfalg")){
			queryMap.put("delflag", "1");	//默认取未删除数据
		}
		if(!queryMap.containsKey("state")){
			queryMap.put("state", "1");		//读取启用状态的数据
		}
		if(!queryMap.containsKey("enddate")){
			queryMap.put("enddate", (new Date()));	//读取终止时间未到的数据
		}
		if(!queryMap.containsKey("isshowcuruserrc")){
			queryMap.put("isshowcuruserrc", 1);		//显示当前用户是否已读
		}		
		queryMap.put("readcountuserid", sysUser.getUserid()); 	//当前用户是否已经阅读
		queryMap.put("curuserid", sysUser.getUserid());	//当前用户编号
		
		if(StringUtils.isNotEmpty(sysUser.getPersonnelid())){
			Personnel personnel=getPersonnelInfoById(sysUser.getPersonnelid());
			if(null!=personnel && StringUtils.isNotEmpty(personnel.getId())){
				queryMap.put("curuserdept", personnel.getBelongdeptid());	//当前用户部门编号					
			}else{
				queryMap.put("curuserdept", sysUser.getDepartmentid());	//当前用户部门编号
			}
		}else{
			queryMap.put("curuserdept", sysUser.getDepartmentid());	//当前用户部门编号
		}
		
		//当前权限
		List<String> authList=getUserAuthorityList();
		List<String> roleList=new ArrayList<String>();
		if(null!=authList && authList.size()>0 ){
			for(String item : authList){
				if(null!=item && !"".equals(item)){
					roleList.add(item.trim());
				}
			}
			if(roleList.size()>0){
				queryMap.put("curusrolelist", roleList);
			}
		}

		pageMap.setCondition(queryMap);
		PageData pageData=noticeService.showMsgNoticePageList(pageMap);
		addJSONObject(pageData);
		return "success";
	}

	/**
	 * 公告通知发布分页列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public String noticePublishListPage() throws Exception{
		return "success";
	}
	/**
	 * 公告通知发布分页数据,设置当前发布人
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public String showNoticePublishPageList() throws Exception{
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		SysUser sysUser=getSysUser();
		//queryMap.put("adduserid", sysUser.getUserid());
		if(!queryMap.containsKey("delfalg")){
			queryMap.put("delflag", "1");	//默认取未删除数据
		}
		pageMap.setCondition(queryMap);
		PageData pageData=noticeService.getMsgNoticePublishPageList(pageMap);
		addJSONObject(pageData);
		return "success";
	}

	/**
	 * 公告通知操作页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-18
	 */
	public String noticePage() throws Exception{
		String type = request.getParameter("type");
		String id = request.getParameter("id");
		request.setAttribute("type", type);
		request.setAttribute("id", id);
		return "success";
	}

	/**
	 * 公告通知添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public String noticeAddPage() throws Exception{
		List sysCodes = getBaseSysCodeService().showSysCodeListByType("state");
		Date today=new Date();
		SimpleDateFormat dateToStr = new SimpleDateFormat ("yyyy-MM-dd");
		String datestr=dateToStr.format(today);
		request.setAttribute("today", datestr);
		request.setAttribute("statelist", sysCodes);
		return "success";
	}

	/**
	 * 公告通知保存
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	@UserOperateLog(key="Message-Notice",type=2)
	public String noticeAdd() throws Exception{
		boolean flag=false;
		String addType = request.getParameter("addType");
		if("temp".equals(addType)){
			msgNotice.setState("3");	//暂存
		}else if("real".equals(addType)){
			msgNotice.setState("2");
		}else{
			msgNotice.setState("3");//暂存
		}
		Map<String, Object> map=new HashMap<String, Object>();
		if("2".equals(msgNotice.getState())){
			if(StringUtils.isEmpty(msgNotice.getReceiveuser()) 
					&& StringUtils.isEmpty(msgNotice.getReceivedept()) 
					&& StringUtils.isEmpty(msgNotice.getReceiverole())){
				map.put("flag", flag);
				map.put("msg","请选择发布范围");
				
				addJSONObject(map);
			}
			if(StringUtils.isEmpty(msgNotice.getContent())){
				map.put("flag", flag);
				map.put("msg","请填写公告内容");
				
				addJSONObject(map);
			}
		}
		SysUser sysUser=getSysUser();
		msgNotice.setAdduserid(sysUser.getUserid());
		msgNotice.setAdddeptid(sysUser.getDepartmentid());
		msgNotice.setAddtime(new Date());
		msgNotice.setAdddeptid(sysUser.getDepartmentid());
		msgNotice.setModifyuserid(sysUser.getUserid());
		msgNotice.setModifytime(msgNotice.getAddtime());
		msgNotice.setState("2");	//保存状态

		String attachdelete=request.getParameter("attachdelete");
		Map attachParam=new HashMap();
		attachParam.put("delAttachIdarrs", attachdelete);
		attachParam.put("sysUser", sysUser);
		attachParam.put("authList", getUserAuthorityList());
		
		flag=noticeService.addMsgNotice(msgNotice,attachParam);
		if(flag){
			logStr="添加公告成功";
		}else{
			logStr="添加公告失败";
		}
		map.put("flag", flag);
		map.put("backid", msgNotice.getId());
		map.put("type", "add");
		
		addJSONObject(map);
		return "success";
	}
	
	/**
	 * 公告通知修改
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public String noticeEditPage() throws Exception{
		List sysCodes = getBaseSysCodeService().showSysCodeListByType("state");
		String id=request.getParameter("id");
		String type = request.getParameter("type");
		MsgNotice msgNotice=noticeService.showMsgNotice(id);
		request.setAttribute("msgNotice", msgNotice);
		request.setAttribute("type", type);
		request.setAttribute("statelist", sysCodes);
		return "success";		
	}
	
	/**
	 * 公告通知修改
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public String noticeViewPage() throws Exception{
		List sysCodes = getBaseSysCodeService().showSysCodeListByType("state");
		String id=request.getParameter("id");
		String type = request.getParameter("type");
		MsgNotice msgNotice=noticeService.showMsgNotice(id);
		noticeService.setDeptUserRoleName(msgNotice,10,"...");
		request.setAttribute("msgNotice", msgNotice);
		request.setAttribute("type", type);
		request.setAttribute("statelist", sysCodes);
		return "success";		
	}
	
	/**
	 * 公告通知修改
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	@UserOperateLog(key="Message-Notice",type=3)
	public String noticeEdit() throws Exception{		
		boolean flag=false;
		Map<String,Object> msgMap=new HashMap<String,Object>();
		if("".equals(msgNotice.getTitle())){
			msgMap.put("msg", "请填写公告通知标题");
			msgMap.put("flag", flag);	
			addJSONObject(msgMap);
			return "success";			
		}
		MsgNotice notice =noticeService.showMsgNotice(msgNotice.getId().toString());
		if(notice==null){
			msgMap.put("msg", "未能找到要修改的公告通知");
			msgMap.put("flag", flag);	
			addJSONObject(msgMap);
			return "success";		
		}
		SysUser sysUser=getSysUser();
		msgNotice.setAddtime(new Date());
		msgNotice.setModifyuserid(sysUser.getUserid());
		msgNotice.setModifytime(msgNotice.getAddtime());
		if(StringUtils.isNotEmpty(notice.getState())){
			msgNotice.setState(notice.getState());	//保存状态
		}else if(StringUtils.isNotEmpty(msgNotice.getState())){
		} else {
			msgNotice.setState("2");
		}
		
		String attachdelete=request.getParameter("attachdelete");
		Map attachParam=new HashMap();
		attachParam.put("delAttachIdarrs", attachdelete);
		attachParam.put("sysUser", sysUser);
		attachParam.put("authList", getUserAuthorityList());
		
		flag=noticeService.updateMsgNotice(msgNotice,attachParam);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("flag", flag);
		map.put("backid", notice.getId());
		map.put("type", "edit");			
		addJSONObject(map);
		if(flag){
			logStr="修改公告成功";
		}else{
			logStr="修改公告失败";
		}
		return "success";
	}
	
	/**
	 * 公告通知删除
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	@UserOperateLog(key="Message-Notice",type=4)
	public String noticeDelete() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		Map<String,Object> msgMap=new HashMap<String,Object>();
		String ids=request.getParameter("ids");
		if("".equals(ids)){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到删除的公告通知");
			addJSONObject(msgMap);
			return "success";
		}
		String[] idarr=ids.split(",");
		if(idarr.length==0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到删除的公告通知");
			addJSONObject(msgMap);
			return "success";			
		}
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("idarrs", StringUtils.join(idarr,","));
		List<MsgNotice> list=noticeService.getMsgNoticeList(queryMap);

		if(list.size()>0){
			ismuti=true;
		}
		
		for(MsgNotice item : list){
			if("0".equals(item.getState())){
				if(item.getId()!=null && item.getId()>0){
					queryMap.clear();
					queryMap.put("id",item.getId());
					queryMap.put("delflag", "0");
					queryMap.put("deltime", (new Date()));
					queryMap.put("wstate", "0");	
					if(noticeService.updateMsgNoticeBy(queryMap)){
						iSuccess=iSuccess+1;
					}else{
						iFailure=iFailure+1;
					}
				}else{
					iNohandle=iNohandle+1;
				}
			}else if(!"1".equals(item.getState())){
				if(item.getId()!=null && item.getId()>0){
					queryMap.clear();
					queryMap.put("id",item.getId());
					queryMap.put("wnotstatearr", "0,1");	//不是暂存或保存状态下的数据
					if(noticeService.deleteMsgNoticeBy(queryMap)){
						iSuccess=iSuccess+1;
					}else{
						iFailure=iFailure+1;
					}
				}else{
					iNohandle=iNohandle+1;
				}
			}else{
                iNohandle=iNohandle+1;
            }
		}
        iFailure = iNohandle + iFailure;//将没有权限删除的和删除失败的合并为删除失败
		if(flag){
			logStr="公告通告更新删除标志成功";
		}else{
			logStr="公告通告更新删除标志失败";
		}
		msgMap.clear();		
		msgMap.put("flag", true);
		msgMap.put("ismuti", ismuti);
		msgMap.put("isuccess", iSuccess);
		msgMap.put("ifailure", iFailure);
		msgMap.put("inohandle", iNohandle);
		addJSONObject(msgMap);
		return "success";		
	}
	
	/**
	 * 公告通知启用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	@UserOperateLog(key="Message-Notice",type=3)
	public String noticeEnable() throws Exception{
		boolean flag=false;
		Map<String,Object> msgMap=new HashMap<String, Object>();
		String ids=request.getParameter("ids");
		if("".equals(ids)){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到启用的公告通知");
			addJSONObject(msgMap);
			return "success";
		}
		String[] idarr=ids.split(",");
		if(idarr.length==0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到启用的公告通知");
			addJSONObject(msgMap);
			return "success";			
		}

		msgMap= noticeService.auditMsgNotice(ids);
		addJSONObject(msgMap);
		Integer iSuccess =0;
		if(msgMap!=null){
			if(msgMap.containsKey("iSuccess")){
				iSuccess=(Integer) msgMap.get("iSuccess");
				if(iSuccess==null){
					iSuccess=0;
				}
			}
		}
		if(iSuccess>0){
			addLog("启用公告成功 编号："+ids, true);
		}else{
			addLog("启用公告失败 编号："+ids, false);
		}
		return "success";
	}
	/**
	 * 公告通知禁用
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	@UserOperateLog(key="Message-Notice",type=3)
	public String noticeDisable() throws Exception{
		boolean flag=false;
		boolean ismuti=false;
		Map<String,Object> msgMap=new HashMap<String,Object>();
		String ids=request.getParameter("ids");
		if("".equals(ids)){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到禁用的公告通知");
			addJSONObject(msgMap);
			return "success";
		}
		String[] idarr=ids.split(",");
		if(idarr.length==0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到禁用的公告通知");
			addJSONObject(msgMap);
			return "success";			
		}
		if(idarr.length>0){
			ismuti=true;
		}

		msgMap= noticeService.oppauditMsgNotice(ids);
		addJSONObject(msgMap);
		Integer iSuccess =0;
		if(msgMap!=null){
			if(msgMap.containsKey("iSuccess")){
				iSuccess=(Integer) msgMap.get("iSuccess");
				if(iSuccess==null){
					iSuccess=0;
				}
			}
		}
		if(iSuccess>0){
			addLog("禁用公告成功 编号："+ids, true);
		}else{
			addLog("禁用公告失败 编号："+ids, false);
		}
		return "success";
	}

	/**
	 * 公告通知查看范围
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public String noticePublishRangePage() throws Exception{
		String noticeid=request.getParameter("noticeid");
		MsgNotice msgNotice=noticeService.showMsgNotice(noticeid);
		if(msgNotice!=null){
			noticeService.setDeptUserRoleName(msgNotice,10,"...");
			request.setAttribute("recvdeptname",msgNotice.getReceivedeptname());
			request.setAttribute("recvrolename",msgNotice.getReceiverolename());
			request.setAttribute("recvusername",msgNotice.getReceiveusername());
			request.setAttribute("msgNotice",msgNotice);
		}
		return "success";
	}
	/**
	 * 接收人公告通知查看
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public String noticeDetailPage() throws Exception{
		String noticeid=request.getParameter("noticeid");
		if(null==noticeid || "".equals(noticeid.trim())){
			request.setAttribute("msgNoData", "1");
			return "success";			
		}
		MsgNotice msgnotice=noticeService.showMsgNotice(noticeid);
		if(null==msgnotice){
			request.setAttribute("msgNoData", "1"); //未找到
			return "success";
		}else if("0".equals(msgnotice.getState())){
			request.setAttribute("msgNoData", "5");	//禁用
			return "success";			
		}else if(!"1".equals(msgnotice.getState())){
			request.setAttribute("msgNoData", "3");	//未启用
			return "success";			
		}else if(!"1".equals(msgnotice.getDelflag())){
			request.setAttribute("msgNoData", "4");	//删除了
			return "success";			
		}else if (StringUtils.isNotEmpty(msgnotice.getEnddate()) && msgnotice.getEnddate().compareTo(CommonUtils.getTodayDataStr())<0 ){
			request.setAttribute("msgNoData", "5");	//禁用
			return "success";		
		}
		SysUser sysUser=getSysUser();
		Map queryMap = new HashMap();
		queryMap.put("querynoticeid", noticeid);
		//显示查看范围
		queryMap.put("isqueryviewrang", "1");
		if(StringUtils.isNotEmpty(msgnotice.getReceiveuser()) && !"ALL".equals(msgnotice.getReceiveuser().toUpperCase())){ 
			queryMap.put("curuserid", sysUser.getUserid());	//当前用户编号
		}
		if(StringUtils.isNotEmpty(msgnotice.getReceivedept()) && !"ALL".equals(msgnotice.getReceivedept().toUpperCase())) {
			if(StringUtils.isNotEmpty(sysUser.getPersonnelid())){
				Personnel personnel=getPersonnelInfoById(sysUser.getPersonnelid());
				if(null!=personnel && StringUtils.isNotEmpty(personnel.getId())){
					queryMap.put("curuserdept", personnel.getBelongdeptid());	//当前用户部门编号					
				}else{
					queryMap.put("curuserdept", sysUser.getDepartmentid());	//当前用户部门编号
				}
			}else{
				queryMap.put("curuserdept", sysUser.getDepartmentid());	//当前用户部门编号
			}
		}
		if(StringUtils.isNotEmpty(msgnotice.getReceiverole()) && !"ALL".equals(msgnotice.getReceiverole().toUpperCase())){
			//当前权限
			List<String> authList=getUserAuthorityList();
			List<String> roleList=new ArrayList<String>();
			if(null!=authList && authList.size()>0 ){
				for(String item : authList){
					if(null!=item && !"".equals(item)){
						roleList.add(item.trim());
					}
				}
				if(roleList.size()>0){
					queryMap.put("curusrolelist", roleList);
				}
			}
		}
		pageMap.setCondition(queryMap);
		int iread=noticeService.getMsgNoticeCount(pageMap);
		if(iread<1){
			request.setAttribute("msgNoData", "2");
			return "success";
		}
		if(null!=msgnotice ){
			if( null!=msgnotice.getAdduserid() && !"".equals(msgnotice.getAdduserid().trim())){
				Map<String,Object> map=new HashMap<String,Object>();
				String[] idarr={msgnotice.getAdduserid()};
				map.put("userList", idarr );
				pageMap.setCondition(map);
				PageData pageData=getSysUserListByConditon(pageMap,false);
				StringBuffer sbBuffer=new StringBuffer();
				if(pageData.getList()!=null){
					List<SysUser> list=pageData.getList();
					if(list.size()>0){
						SysUser sUser=list.get(0);
						if(null!=sUser){
							msgnotice.setAddusername(sUser.getName());
							msgnotice.setAdddeptname(sUser.getDepartmentname());
						}
					}
				}
			}
			request.setAttribute("msgNoData", "0");
		}
		request.setAttribute("msgNotice", msgnotice);
		return "success";
	}

	/**
	 * 添加阅读人
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-30
	 */
	public String addNoticeread() throws Exception{
		boolean flag=false;
		Map<String,Object> msgMap=new HashMap<String,Object>();
		SysUser sysUser=getSysUser();
		String ids=request.getParameter("ids");
		if(null==ids|| "".equals(ids.trim())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到要标记的公告通知");
			return "success";
		}
		String[] idarr=ids.split(",");
		if(idarr.length==0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到要标记的公告通知");
			addJSONObject(msgMap);
			return "success";			
		}
		MsgNoticeread msgNoticeread=new MsgNoticeread();
		msgNoticeread.setReceiveuserid(sysUser.getUserid());
		for(String item : idarr){
			if(item!=null && !"".equals(item.trim()) && StringUtils.isNumeric(item.trim())){
				flag=true;
				if(noticeService.getMsgNoticereadCountBy(item.trim(),sysUser.getUserid())==0){
					msgNoticeread.setNoticeid(Integer.parseInt(item.trim()));
					msgNoticeread.setReceivetime(new Date());
					noticeService.addMsgNoticeread(msgNoticeread);
				}
 			}
		}
		logStr="公告通知标志阅读标志";
		flag=true;
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 公告通知阅读人分页列表
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public String noticereadListPage() throws Exception{
		String noticeid=request.getParameter("noticeid");
		MsgNotice msgNotice=noticeService.showMsgNotice(noticeid);
		Map<String,Object> parmap=new HashMap<String,Object>();
		if(msgNotice!=null){
			noticeService.setDeptUserRoleName(msgNotice,10,"...");
			request.setAttribute("noticeid", noticeid);
			request.setAttribute("recvdeptname",msgNotice.getReceivedeptname());
			request.setAttribute("recvrolename",msgNotice.getReceiverolename());
			request.setAttribute("recvusername",msgNotice.getReceiveusername());
		}
		return "success";				
	}
	
	/**
	 * 公告通知阅读人分页数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public String showNoticereadPageList() throws Exception{
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(queryMap);
		PageData pageData=noticeService.showMsgNoticereadPageList(pageMap);
		addJSONObject(pageData);
		return "success";

	}
	
	/**
	 * 根据公告通知编号清空阅读人
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-26
	 */
	public String noticereadClearAll() throws Exception{
		String noticeid=request.getParameter("noticeid");
		//MsgNotice msgNotice=noticeService.showMsgNotice(noticeid);
		boolean flag=noticeService.deleteMsgNoticereadByNotice(noticeid);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 公告通知查看部门范围页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-1-26
	 */
	public String showNoticePublishRangeDepartPage() throws Exception{
		String noticeid=request.getParameter("noticeid");
		MsgNotice reqMsgNotice=null;
		if(null!=noticeid && !"".equals(noticeid.trim())){
			reqMsgNotice=noticeService.showMsgNotice(noticeid);
		}
		if(null==reqMsgNotice){
			reqMsgNotice=new MsgNotice();
		}
		request.setAttribute("checkall","");
		if("ALL".equals(reqMsgNotice.getReceivedept())){
			request.setAttribute("checkall","true");
		}
		request.setAttribute("receivedept",reqMsgNotice.getReceivedept());
		request.setAttribute("msgNotice",reqMsgNotice);
		return SUCCESS;
	}


	/**
	 * 公告通知查看角色范围页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-1-26
	 */
	public String showNoticePublishRangeRolePage() throws Exception{
		return SUCCESS;
	}

	/**
	 * 公告通知查看角色范围列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-1-26
	 */
	public String showNoticePublishRangeRolePageList() throws Exception{
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(queryMap);
		PageData pageData=noticeService.showMsgNoticeRangeRoleList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 公告通知查看人员范围页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-1-26
	 */
	public String showNoticePublishRangeUserPage() throws Exception{
		return SUCCESS;
	}

	/**
	 * 公告通知查看人员范围列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-1-26
	 */
	public String showNoticePublishRangeUserPageList() throws Exception{
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(queryMap);
		PageData pageData=noticeService.showMsgNoticeRangeUserList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	
}