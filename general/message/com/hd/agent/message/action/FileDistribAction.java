/**
 * @(#)FileDistributionAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-9-13 zhanghonghui 创建版本
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
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.FileDistrib;
import com.hd.agent.message.service.IFileDistribService;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class FileDistribAction extends BaseAction  {
	private FileDistrib fileDistrib;
	private IFileDistribService fileDistribService;

	
	public FileDistrib getFileDistrib() {
		return fileDistrib;
	}

	public void setFileDistrib(FileDistrib fileDistrib) {
		this.fileDistrib = fileDistrib;
	}
	
	public IFileDistribService getFileDistribService() {
		return fileDistribService;
	}

	public void setFileDistribService(
			IFileDistribService fileDistribService) {
		this.fileDistribService = fileDistribService;
	}

	/**
	 * 传阅件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-16
	 */
	public String fileDistribPage() throws Exception{
		String id=request.getParameter("id");
		String type=request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		return SUCCESS;
	}
	/**
	 * 传阅件添加页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-16
	 */
	public String fileDistribAddPage() throws Exception{
		List sysCodes = getBaseSysCodeService().showSysCodeListByType("state");
		Date today=new Date();
		SimpleDateFormat dateToStr = new SimpleDateFormat ("yyyy-MM-dd");
		String datestr=dateToStr.format(today);
		request.setAttribute("today", datestr);
		request.setAttribute("statelist", sysCodes);
		return SUCCESS;
	}
	/**
	 * 添加传阅件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-16
	 */
	public String addFileDistrib() throws Exception{
		String addType = request.getParameter("addType");
		if("temp".equals(addType)){
			fileDistrib.setState("3");	//暂存
		}else if("real".equals(addType)){
			fileDistrib.setState("2");
		}else{
			fileDistrib.setState("3");//暂存
		}
		boolean flag=false;
		Map map=new HashMap();
		String ctmp="";
		if("1".equals(fileDistrib.getType())){
			ctmp=request.getParameter("cfile");
			fileDistrib.setContid(ctmp);
			fileDistrib.setContent("");
		}else if("2".equals(fileDistrib.getType())){
			
		}else{
			ctmp=request.getParameter("content");
			fileDistrib.setContent(ctmp);
			fileDistrib.setContid("");
		}
		if("2".equals(fileDistrib.getState())){
			if("1".equals(fileDistrib.getType())){
				if(StringUtils.isEmpty(fileDistrib.getContid())){
					map.put("flag", flag);
					map.put("msg", "请上传文档");

					addJSONObject(map);
					return SUCCESS;
				}
			}else{
				if(StringUtils.isEmpty(fileDistrib.getContent())){
					map.put("flag", flag);
					map.put("msg", "请填写内容");
					addJSONObject(map);
					return SUCCESS;
				}
			}
		}
		
		SysUser sysUser=getSysUser();
		fileDistrib.setAdduserid(sysUser.getUserid());
		fileDistrib.setAdddeptid(sysUser.getDepartmentid());
		fileDistrib.setAddtime(new Date());
		fileDistrib.setAdddeptid(sysUser.getDepartmentid());
		fileDistrib.setModifyuserid(sysUser.getUserid());
		fileDistrib.setModifytime(fileDistrib.getAddtime());

		String attachdelete=request.getParameter("attachdelete");
		String cfiledelete=request.getParameter("cfiledelete");
		Map attachParam=new HashMap();
		attachParam.put("delAttachIdarrs", attachdelete);
		attachParam.put("delCFileIdarrs", cfiledelete);
		attachParam.put("sysUser", sysUser);
		attachParam.put("authList", getUserAuthorityList());
		
		flag=fileDistribService.addFileDistrib(fileDistrib,attachParam);
		map.put("flag", flag);
		map.put("backid", fileDistrib.getId());
		map.put("opertype", "add");
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 传阅件编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-16
	 */
	public String fileDistribEditPage() throws Exception{
		String id=request.getParameter("id");
		FileDistrib fDistrib=fileDistribService.showFileDistrib(id);
		request.setAttribute("fileDistrib", fDistrib);
		List sysCodes = getBaseSysCodeService().showSysCodeListByType("state");
		request.setAttribute("statelist", sysCodes);
		return SUCCESS;
	}
	/**
	 * 编辑传阅件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-16
	 */
	@UserOperateLog(key="Message-FileDistrib",type=3)
	public String editFileDistrib() throws Exception{
		boolean flag=false;

		Map<String,Object> map=new HashMap<String,Object>();
		FileDistrib fDistrib =fileDistribService.showPureFileDistrib(fileDistrib.getId().toString());
		if(fDistrib==null){
			map.put("msg", "未能找到要修改的传阅件信息");
			map.put("flag", flag);	
			addJSONObject(map);
			return "success";		
		}
		if( "0".equals(fDistrib.getState())){
			map.put("msg", "处于禁用状态的传阅件不可修改");
			map.put("flag", flag);		
			addJSONObject(map);
			return "success";				
		}
		String ctmp="";
		if("1".equals(fileDistrib.getType())){
			ctmp=request.getParameter("cfile");
			fileDistrib.setContid(ctmp);
			if(StringUtils.isNotEmpty(fileDistrib.getContid())){
				fileDistrib.setContid(ctmp);
			}else{
				if("1".equals(fDistrib.getType())){
					fileDistrib.setContid(fDistrib.getContid());
				}else{
					map.put("msg", "请上传文档内容");
					map.put("flag", flag);		
					addJSONObject(map);
					return "success";	
				}
			}
			fileDistrib.setContent("");
		}else if("2".equals(fileDistrib.getType())){
			
		}else{
			ctmp=request.getParameter("content");
			fileDistrib.setContent(ctmp);
			fileDistrib.setContid("");
			if(StringUtils.isEmpty(fileDistrib.getContent())){
				map.put("msg", "请填写内容");
				map.put("flag", flag);		
				addJSONObject(map);
				return "success";	
			}
		}
		SysUser sysUser=getSysUser();
		fileDistrib.setAddtime(new Date());
		fileDistrib.setModifyuserid(sysUser.getUserid());
		fileDistrib.setModifytime(fileDistrib.getAddtime());
		if(StringUtils.isNotEmpty(fDistrib.getState())){
			fileDistrib.setState(fDistrib.getState());	//保存状态
		}else if(StringUtils.isNotEmpty(fileDistrib.getState())){
		} else {
			fileDistrib.setState("2");
		}
		String attachdelete=request.getParameter("attachdelete");
		String cfiledelete=request.getParameter("cfiledelete");
		Map attachParam=new HashMap();
		attachParam.put("delAttachIdarrs", attachdelete);
		attachParam.put("delCFileIdarrs", cfiledelete);
		attachParam.put("sysUser", sysUser);
		attachParam.put("authList", getUserAuthorityList());
		
		flag=fileDistribService.updateFileDistrib(fileDistrib,attachParam);
		map.clear();
		map.put("flag", flag);
		map.put("backid", fileDistrib.getId());
		map.put("opertype", "edit");
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 传阅件查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-16
	 */
	public String fileDistribViewPage() throws Exception{
		String id=request.getParameter("id");
		FileDistrib fDistrib=fileDistribService.showFileDistrib(id);
		request.setAttribute("fileDistrib", fDistrib);
		List sysCodes = getBaseSysCodeService().showSysCodeListByType("state");
		request.setAttribute("statelist", sysCodes);
		return SUCCESS;
	}

	/**
	 * 传阅件发布列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-16
	 */
	public String fileDistribPublishListPage() throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 传阅件发布分页列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-16
	 */
	public String showFileDistribPublishPageList() throws Exception{
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		SysUser sysUser=getSysUser();
		//queryMap.put("adduserid", sysUser.getUserid());
		if(!queryMap.containsKey("delfalg")){
			queryMap.put("delflag", "1");	//默认取未删除数据
		}
		pageMap.setCondition(queryMap);
		PageData pageData=fileDistribService.showFileDistribPublishPageList(pageMap);
		if(pageData.getList()!=null){
			List<FileDistrib> list=pageData.getList();
			String[] idarr=null;
			for(FileDistrib item:list){
				setDeptUserRoleName(item);
			}
		}
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 发布管理-删除传阅件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-18
	 */
	public String fileDistribPublishDelete() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		Map<String,Object> msgMap=new HashMap<String,Object>();
		String ids=request.getParameter("ids");
		if("".equals(ids)){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到删除的传阅件");
			addJSONObject(msgMap);
			return "success";
		}
		String[] idarr=ids.split(",");
		if(idarr.length==0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到删除的传阅件");
			addJSONObject(msgMap);
			return "success";			
		}
		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("idarrs", StringUtils.join(idarr,","));
		queryMap.put("withrecvuser", "0");
		queryMap.put("withcontent", "0");
		queryMap.put("withattachment", "0");
		List<FileDistrib> list=fileDistribService.getFileDistribList(queryMap);

		if(list.size()>1){
			ismuti=true;
		}

		queryMap.clear();
		queryMap.put("wnotstatearr", "1");	//不是暂存或保存状态下的数据
		for(FileDistrib item : list){
			if(!"1".equals(item.getState())){
				if(item.getId()!=null && item.getId()>0){
					queryMap.put("id",item.getId());
					if(fileDistribService.deleteFileDistribBy(queryMap)){
						iSuccess=iSuccess+1;
					}else{
						iFailure=iFailure+1;
					}
				}else{
					iNohandle=iNohandle+1;
				}
			}
		}
		logStr="传阅件更新删除标志，删除编号："+ids;
		msgMap.clear();		
		msgMap.put("flag", true);
		msgMap.put("ismuti", ismuti);
		msgMap.put("isuccess", iSuccess);
		msgMap.put("ifailure", iFailure);
		msgMap.put("inohandle", iNohandle);
		addJSONObject(msgMap);	
		return SUCCESS;
	}
	/**
	 * 发布管理-启用传阅件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-18
	 */
	public String openFileDistrib() throws Exception{
		boolean flag=false;
		Map<String,Object> msgMap=new HashMap<String, Object>();
		String ids=request.getParameter("ids");
		if("".equals(ids)){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到要启用的传阅件信息");
			addJSONObject(msgMap);
			return "success";
		}
		String[] idarr=ids.split(",");
		if(idarr.length==0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到要启用的传阅件信息");
			addJSONObject(msgMap);
			return "success";			
		}

		msgMap= fileDistribService.openFileDsitrib(ids);
		addJSONObject(msgMap);
		return SUCCESS;
	}
	/**
	 * 发布管理-禁用传阅件
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-18
	 */
	public String closeFileDistrib() throws Exception{
		boolean flag=false;
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		Map<String,Object> msgMap=new HashMap<String,Object>();
		String ids=request.getParameter("ids");
		if("".equals(ids)){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到要禁用的传阅件信息");
			addJSONObject(msgMap);
			return "success";
		}
		String[] idarr=ids.split(",");
		if(idarr.length==0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到禁用的传阅件信息");
			addJSONObject(msgMap);
			return "success";			
		}

		Map<String,Object> queryMap=new HashMap<String,Object>();
		queryMap.put("idarrs", StringUtils.join(idarr,","));
		queryMap.put("withrecvuser", "0");
		queryMap.put("withcontent", "0");
		queryMap.put("withattachment", "0");
		List<FileDistrib> list=fileDistribService.getFileDistribList(queryMap);

		if(list.size()>1){
			ismuti=true;
		}

		queryMap.clear();
		queryMap.put("state", "0");	//禁用状态
		queryMap.put("wstate", "1");	//只能禁用启用数据
		for(FileDistrib item : list){
			if("1".equals(item.getState())){
				if(item.getId()!=null && item.getId()>0){
					queryMap.put("id",item.getId());
					if(fileDistribService.updateFileDistribBy(queryMap)){
						iSuccess=iSuccess+1;
					}else{
						iFailure=iFailure+1;
					}
				}else{
					iNohandle=iNohandle+1;
				}
			}
		}
		msgMap.clear();		
		msgMap.put("flag", true);
		msgMap.put("ismuti", ismuti);
		msgMap.put("isuccess", iSuccess);
		msgMap.put("ifailure", iFailure);
		msgMap.put("inohandle", iNohandle);
		addJSONObject(msgMap);
		return SUCCESS;
	}
	/**
	 * 传阅件接收页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-21
	 */
	public String fileDistribReceiveListPage() throws Exception{
		
		return SUCCESS;
	}
	/**
	 * 显示传阅件分页数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-21
	 */
	public String showFileDistribReceivePageList() throws Exception{
		SysUser sysUser=getSysUser();
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		//显示查看范围
		queryMap.put("isqueryviewrang", "1");
		
		if(!queryMap.containsKey("state")){
			queryMap.put("state", "1");		//读取启用状态的数据
		}
		if(!queryMap.containsKey("enddate")){
			queryMap.put("enddate", (new Date()));	//读取终止时间未到的数据
		}
		if(!queryMap.containsKey("isshowcuruserrc")){
			queryMap.put("isshowcuruserrc", 1);		//显示当前用户是否已读
		}		
		queryMap.put("curuserid", sysUser.getUserid());	//当前用户编号
		queryMap.put("curuserdept", sysUser.getDepartmentid());	//当前用户部门编号
		
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
		PageData pageData=fileDistribService.showFileDistribReceivePageList(pageMap);
		if(pageData.getList()!=null){
			List<FileDistrib> list=pageData.getList();
			String[] idarr=null;
			for(FileDistrib item:list){
				setDeptUserRoleName(item);
			}
		}
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 添加阅读人
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-1-30
	 */
	public String addFileDistribread() throws Exception{
		boolean flag=false;
		Map<String,Object> msgMap=new HashMap<String,Object>();
		String ids=request.getParameter("ids");
		if(null==ids|| "".equals(ids.trim())){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到要标记的传阅件");
			return "success";
		}
		String[] idarr=ids.split(",");
		if(idarr.length==0){
			msgMap.put("flag", flag);
			msgMap.put("msg", "未能找到要标记的传阅件");
			addJSONObject(msgMap);
			return "success";			
		}
		flag=fileDistribService.addFileDistribRead(ids);
		logStr="传阅件标志阅读标志";
		flag=true;
		addJSONObject("flag", flag);
		return "success";
	}
	/**
	 * 传阅件接收查看页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-23
	 */
	public String fileDistribReceiveDetailPage() throws Exception{
		String id=request.getParameter("id");
		if(null==id || "".equals(id.trim())){
			request.setAttribute("noData", "1");
			return SUCCESS;
		}

		SysUser sysUser=getSysUser();
		Map queryMap = new HashMap();
		queryMap.put("queryfdid", id);
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
		queryMap.put("curuserid", sysUser.getUserid());	//当前用户编号
		queryMap.put("curuserdept", sysUser.getDepartmentid());	//当前用户部门编号
		
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
		int iread=fileDistribService.getFileDistribReceivePageCount(pageMap);
		if(iread<1){
			request.setAttribute("noData", "2");
			return SUCCESS;
		}
		FileDistrib fDistrib=fileDistribService.showFileDistrib(id);
		if(null==fDistrib){
			request.setAttribute("noData", "1");
		}else{
			request.setAttribute("noData", "0");			
		}
		request.setAttribute("fileDistrib", fDistrib);
		return SUCCESS;
	}

	/**
	 * 传阅件接收内容编辑页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-23
	 */
	public String fileDistribReceiveEditPage() throws Exception{
		return SUCCESS;
	}
	/**
	 * 传阅件阅读列表页面
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-25
	 */
	public String fileDistribReadListPage() throws Exception{
		String fid=request.getParameter("fid");
		request.setAttribute("fid", fid);
		return SUCCESS;
	}
	/**
	 * 传阅件阅读列表数据
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-25
	 */
	public String showFileDistribReadPageList() throws Exception{
		Map queryMap = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(queryMap);
		PageData pageData=fileDistribService.showFileDistribReadPageList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	/**
	 * 设置部门、用户、权限名称
	 * @param item
	 * @author zhanghonghui 
	 * @date 2013-3-22
	 */
	private void setDeptUserRoleName(FileDistrib item) throws Exception{
		if(null==item){
			return;
		}
		Map<String, Object> queryMap=new HashMap<String, Object>();
		String[] idarr=null;
		List<String> list=null;
		if(null!=item.getReceivedept() && !"".equals(item.getReceivedept().trim())){
			if("ALL".equals(item.getReceivedept().trim())){
				item.setReceivedeptname("所有部门");
			}else{
				queryMap.clear();
				idarr=item.getReceivedept().trim().split(",");
				if(idarr.length>0){
					item.setReceivedeptname(getDeptNameStringList(idarr));
				}
			}
		}
		if(null!=item.getReceiverole() && !"".equals(item.getReceiverole().trim())){
			if("ALL".equals(item.getReceiverole().trim())){
				item.setReceiverolename("所有权限");
			}else{
				queryMap.clear();
				idarr=item.getReceiverole().trim().split(",");
				if(idarr.length>0){	
					list=new ArrayList<String>();
					for(String str : idarr){
						if(null!=str && !"".equals(str.trim())){
							list.add(str);
						}
					}					
					idarr=list.toArray((new String[0]));					
					if(idarr.length>0){
						item.setReceiverolename(getRoleNameStringList(idarr));
					}
				}
			}
		}

		if(null!=item.getReceiveuser() && !"".equals(item.getReceiveuser().trim())){
			if("ALL".equals(item.getReceiveuser())){
				item.setReceiveusername("所有人员");
			}else{
				queryMap.clear();
				idarr=item.getReceiveuser().split(",");
				if(idarr.length>0){
					item.setReceiveusername(getUserNameStringList(idarr));						
				}
			}
		}
	}
	
	/**
	 * 根据角色编号，获取用户名称字符串，返回格式：a,b,c
	 * @param useridarr
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-13
	 */
	private String getUserNameStringList(String[] useridarr) throws Exception{
		PageMap pageMap=new PageMap();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userList", useridarr);
		pageMap.setCondition(map);
		PageData pageData=getSysUserListByConditon(pageMap,false);
		StringBuffer sbBuffer=new StringBuffer();
		if(pageData.getList()!=null){
			List<SysUser> list=pageData.getList();
			for(SysUser item : list){
				if(StringUtils.isNotEmpty(item.getName())){
					if(sbBuffer.length()>0){
						sbBuffer.append(",");
					}
					sbBuffer.append(item.getName());
				}
			}
		}
		return sbBuffer.toString();
	}
	
	/**
	 * 根据角色编号，获取角色名称字符串，返回格式：a,b,c
	 * @param useridarr
	 * @return 
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-13
	 */
	private String getRoleNameStringList(String[] idarr) throws Exception{
		List<Authority> list= getAuthorityListByIds(StringUtils.join(idarr,","));
		StringBuffer sbBuffer=new StringBuffer();
		if(list!=null && list.size()>0){
			for(Authority item: list){
				if(StringUtils.isNotEmpty(item.getAuthorityname())){
					if(sbBuffer.length()>0){
						sbBuffer.append(",");
					}
					sbBuffer.append(item.getAuthorityname());
				}
			}
		}
		return sbBuffer.toString();
	}
	
	/**
	 * 根据部门编号，获取部门名称字符串，返回格式：a,b,c
	 * @param useridarr
	 * @return 
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-13
	 */
	private String getDeptNameStringList(String[] idarr) throws Exception{
		List<DepartMent> list= getDeptListByIds(StringUtils.join(idarr,","));
		StringBuffer sbBuffer=new StringBuffer();
		if(list!=null && list.size()>0){
			for(DepartMent item: list){
				if(StringUtils.isNotEmpty(item.getName())){
					if(sbBuffer.length()>0){
						sbBuffer.append(",");
					}
					sbBuffer.append(item.getName());
				}
			}
		}
		return sbBuffer.toString();
	}
}

