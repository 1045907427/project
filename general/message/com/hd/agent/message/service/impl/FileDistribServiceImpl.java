/**
 * @(#)FileDistributionServiceImpl.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-9-13 zhanghonghui 创建版本
 */
package com.hd.agent.message.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.dao.FileDistribMapper;
import com.hd.agent.message.model.FileDistrib;
import com.hd.agent.message.model.FileDistribReceive;
import com.hd.agent.message.service.IFileDistribService;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class FileDistribServiceImpl extends BaseServiceImpl implements IFileDistribService {

	private FileDistribMapper fileDistribMapper;

	/**
	 * 附件
	 */
	private IAttachFileService attachFileService;

	public FileDistribMapper getFileDistribMapper() {
		return fileDistribMapper;
	}

	public void setFileDistribMapper(
			FileDistribMapper fileDistribMapper) {
		this.fileDistribMapper = fileDistribMapper;
	}

	public IAttachFileService getAttachFileService() {
		return attachFileService;
	}

	public void setAttachFileService(IAttachFileService attachFileService) {
		this.attachFileService = attachFileService;
	}

	@Override
	public boolean addFileDistrib(FileDistrib fileDistrib) throws Exception{
		boolean flag=false;
		if("1".equals(fileDistrib.getType())){
			if(StringUtils.isEmpty(fileDistrib.getContid())){
				return flag;
			}
			AttachFile attach=attachFileService.getAttachFile(fileDistrib.getContid());
			if(null==attach ){
				return flag;
			}
			if(StringUtils.isNotEmpty(attach.getPdfpath())){
				fileDistrib.setContent(attach.getPdfpath());
				fileDistrib.setCftype("1");
			}else if(StringUtils.isNotEmpty(attach.getHtmlpath())){
				fileDistrib.setContent(attach.getHtmlpath());
				fileDistrib.setCftype("2");
			}else if(StringUtils.isNotEmpty(attach.getFullpath())){
				fileDistrib.setContent(attach.getFullpath());
				fileDistrib.setCftype("0");
			}else if(StringUtils.isNotEmpty(attach.getSwfpath())){
				fileDistrib.setContent(attach.getSwfpath());
				fileDistrib.setCftype("3");
			}else {
				return flag;
			}

			fileDistrib.setOrgcont(attach.getFullpath());
		}
		flag= fileDistribMapper.insertFileDistrib(fileDistrib)>0;
		if(flag){

			/*
			if(StringUtils.isNotEmpty(fileDistrib.getAttach())){
			//添加附件权限
				attachFileService.updateAttachFileAuth(fileDistrib.getAttach(),fileDistrib.getReceiveuser(),fileDistrib.getReceivedept(),fileDistrib.getReceiverole());
			}

			if(StringUtils.isNotEmpty(fileDistrib.getContid())){
				attachFileService.updateAttachFileAuth(fileDistrib.getContid(),fileDistrib.getReceiveuser(),fileDistrib.getReceivedept(),fileDistrib.getReceiverole());
			}
			*/
		}
		return flag;
	}
	@Override
	public boolean addFileDistrib(FileDistrib fileDistrib,Map map) throws Exception{
		boolean isok=addFileDistrib(fileDistrib);
		if(isok){
			if(map.containsKey("delCFileIdarrs") && null!=map.get("delCFileIdarrs") && !"".equals(map.get("delCFileIdarrs").toString())){
				map.put("idarrs", map.get("delCFileIdarrs"));
				map.remove("delCFileIdarrs");
				attachFileService.deleteAttachWithPhysical(map);
			}
			if(map.containsKey("delAttachIdarrs") && null!=map.get("delAttachIdarrs") && !"".equals(map.get("delAttachIdarrs").toString())){
				map.put("idarrs", map.get("delAttachIdarrs"));
				map.remove("delAttachIdarrs");
				attachFileService.deleteAttachWithPhysical(map);
			}
		}
		return isok;
	}
	@Override
	public boolean updateFileDistrib(FileDistrib fileDistrib) throws Exception{
		boolean flag=false;
		if("1".equals(fileDistrib.getType())){
			if(StringUtils.isEmpty(fileDistrib.getContid())){
				return flag;
			}
			AttachFile attach=attachFileService.getAttachFile(fileDistrib.getContid());
			if(null==attach ){
				return flag;
			}
			if(StringUtils.isNotEmpty(attach.getSwfpath())){
				fileDistrib.setContent(attach.getSwfpath());
				fileDistrib.setCftype("1");
			}else if(StringUtils.isNotEmpty(attach.getHtmlpath())){
				fileDistrib.setContent(attach.getHtmlpath());
				fileDistrib.setCftype("2");
			}else if(StringUtils.isNotEmpty(attach.getFullpath())){
				fileDistrib.setContent(attach.getFullpath());
				fileDistrib.setCftype("0");
			}else {
				return flag;
			}

			fileDistrib.setOrgcont(attach.getFullpath());
		}
		flag= fileDistribMapper.updateFileDistrib(fileDistrib)>0;

		if(flag){

			if(StringUtils.isNotEmpty(fileDistrib.getAttach())){
			//添加附件权限
				//attachFileService.updateAttachFileAuth(fileDistrib.getAttach(),fileDistrib.getReceiveuser(),fileDistrib.getReceivedept(),fileDistrib.getReceiverole());
			}

			if(StringUtils.isNotEmpty(fileDistrib.getContid())){
				//attachFileService.updateAttachFileAuth(fileDistrib.getContid(),fileDistrib.getReceiveuser(),fileDistrib.getReceivedept(),fileDistrib.getReceiverole());
			}

			if("1".equals(fileDistrib.getState()) &&  "1".equals(fileDistrib.getIsmsg())){
				//保存成功并且，需要发短信通知
				sendNoticeInnerMsg(fileDistrib);
			}
		}
		return flag;
	}

	@Override
	public boolean updateFileDistrib(FileDistrib fileDistrib,Map map) throws Exception{
		boolean isok=updateFileDistrib(fileDistrib);
		if(isok){
			if(map.containsKey("delCFileIdarrs") && null!=map.get("delCFileIdarrs") && !"".equals(map.get("delCFileIdarrs").toString())){
				map.put("idarrs", map.get("delCFileIdarrs"));
				map.remove("delCFileIdarrs");
				attachFileService.deleteAttachWithPhysical(map);
			}
			if(map.containsKey("delAttachIdarrs") && null!=map.get("delAttachIdarrs") && !"".equals(map.get("delAttachIdarrs").toString())){
				map.put("idarrs", map.get("delAttachIdarrs"));
				map.remove("delAttachIdarrs");
				attachFileService.deleteAttachWithPhysical(map);
			}
		}
		return isok;		
	}
	@Override
	public FileDistrib showPureFileDistrib(String id) throws Exception{
		return fileDistribMapper.showPureFileDistrib(id);
	}
	@Override
	public FileDistrib showFileDistrib(String id) throws Exception{
		String cols = getAccessColumnList("t_msg_filedistrib",null);
		Map map=new HashMap();
		map.put("id", id);
		map.put("cols",cols );
		FileDistrib fileDistrib= fileDistribMapper.showFileDistrib(map);
		if(null!=fileDistrib){
			DepartMent departMent =null;
			SysUser addUser=null;
			if(StringUtils.isNotEmpty(fileDistrib.getAdduserid())){
				addUser=getBaseSysUserMapper().getUserById(fileDistrib.getAdduserid());
				if(null!=addUser){
					fileDistrib.setAddusername(addUser.getName());
				}
			}
			if(StringUtils.isNotEmpty(fileDistrib.getAdddeptid())){
				departMent=getBaseDepartMentMapper().getDepartmentInfo(fileDistrib.getAdddeptid());
				if(null!=departMent){
					fileDistrib.setAdddeptname(departMent.getName());
				}
			}
		}
		return fileDistrib;
	}
	@Override
	public PageData showFileDistribReceivePageList(PageMap pageMap) throws Exception{
		PageData pageData=new PageData(fileDistribMapper.getFileDistribReceivePageCount(pageMap),
				fileDistribMapper.getFileDistribReceivePageList(pageMap), pageMap);
		return pageData;
	}
	@Override
	public int getFileDistribReceivePageCount(PageMap pageMap) throws Exception{
		return fileDistribMapper.getFileDistribReceivePageCount(pageMap);
	}
	@Override
	public PageData showFileDistribPublishPageList(PageMap pageMap) throws Exception{
		String cols = getAccessColumnList("t_msg_filedistrib","f");
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_msg_filedistrib","f");
		pageMap.setDataSql(sql);
		PageData pageData=new PageData(fileDistribMapper.getFileDistribPublishPageCount(pageMap),
				fileDistribMapper.getFileDistribPublishPageList(pageMap), pageMap);
		return pageData;
	}
	
	@Override
	public List getFileDistribList(Map queryMap) throws Exception{
		List list=fileDistribMapper.getFileDistribList(queryMap);
		return list;
	}
	@Override
	public boolean updateFileDistribBy(Map map) throws Exception{
		if(!map.containsKey("isdataauth") || !"0".equals(map.get("isdataauth").toString().trim())){
			String sql = getDataAccessRule("t_msg_filedsitrib",null);
			map.put("authDataSql", sql);
		}
		int irows=fileDistribMapper.updateFileDistribBy(map);
		return irows>0;
	}
	@Override
	public boolean deleteFileDistribBy(Map map) throws Exception{
		String sql = getDataAccessRule("t_msg_filedsitrib",null);
		map.put("authDataSql", sql);
		int irows=fileDistribMapper.deleteFileDistribBy(map);
		return irows>0;
	}
	@Override
	public Map openFileDsitrib(String ids) throws Exception{
		Map<String,Object> queryMap=new HashMap<String,Object>();
		Map resultMap=new HashMap();

		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		boolean ismuti=false;
		queryMap.put("idarrs", ids);
		queryMap.put("withrecvuser", "0");
		queryMap.put("withcontent", "0");
		queryMap.put("withattachment", "0");
		List<FileDistrib> list=getFileDistribList(queryMap);

		if(list.size()>0){
			ismuti=true;
		}
		queryMap.clear();
		queryMap.put("state", "1");	//启用状态
		//queryMap.put("wstatearr", "0,2");
		for(FileDistrib item : list){
			if("2".equals(item.getState()) || "0".equals(item.getState())){
				queryMap.put("id", item.getId());
				if(updateFileDistribBy(queryMap)){
					iSuccess=iSuccess+1;
					if("1".equals(item.getIsmsg())){
						//保存成功并且，需要发短信通知
						sendNoticeInnerMsg(item);
					}
				}else{
					iFailure=iFailure+1;					
				}
			}else{
				iNohandle=iNohandle+1;				
			}
		}
		resultMap.put("flag", true);
		resultMap.put("ismuti", ismuti);
		resultMap.put("isuccess", iSuccess);
		resultMap.put("ifailure", iFailure);
		resultMap.put("inohandle", iNohandle);
		return resultMap;
	}
	
	/**
	 * 发送内部消息
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-15
	 */
	private void sendNoticeInnerMsg(FileDistrib fileDistrib) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("mtiptype", "1");	//短信
		map.put("msgtype", "6"); //传阅件
		map.put("senduserid",getSysUser().getUserid());
		String[] tmparr=null;
		List<String> list=new ArrayList<String>();
		List<SysUser> userList=null;
		if(StringUtils.isNotEmpty(fileDistrib.getReceiveuser())){
			if("ALL".equals(fileDistrib.getReceiveuser().toUpperCase())){
				userList=getSysUserListByEnable();
				if(null!=userList && userList.size()>0){
					for(SysUser userItem: userList ){
						if(StringUtils.isNotEmpty(userItem.getUserid())){
							list.add(userItem.getUserid());
						}
					}
				}
			}else{
				tmparr=fileDistrib.getReceiveuser().split(",");
				if(null!=tmparr && tmparr.length>0){
					list.addAll(Arrays.asList(tmparr));
				}
			}
		}
		if(StringUtils.isNotEmpty(fileDistrib.getReceivedept())){
			if("ALL".equals(fileDistrib.getReceivedept().toUpperCase())){
				userList=getSysUserListByEnable();
				if(null!=userList && userList.size()>0){
					for(SysUser userItem: userList ){
						if(StringUtils.isNotEmpty(userItem.getUserid())){
							list.add(userItem.getUserid());
						}
					}
				}
			}else{
				tmparr= fileDistrib.getReceivedept().split(",");
				if(null!=tmparr && tmparr.length>0){
					for(String item : tmparr){
						if(null!=item && !"".equals(item.trim())){
							userList=getSysUserListByDept(item.trim());
							if(null!=userList && userList.size()>0){
								for(SysUser userItem: userList ){
									if(StringUtils.isNotEmpty(userItem.getUserid())){
										list.add(userItem.getUserid());
									}
								}
							}
						}
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(fileDistrib.getReceiverole())){
			if("ALL".equals(fileDistrib.getReceiverole().toUpperCase())){
				userList=getSysUserListByEnable();
				if(null!=userList && userList.size()>0){
					for(SysUser userItem: userList ){
						if(StringUtils.isNotEmpty(userItem.getUserid())){
							list.add(userItem.getUserid());
						}
					}
				}
			}else{
				tmparr= fileDistrib.getReceiverole().split(",");
				if(null!=tmparr && tmparr.length>0){
					for(String item : tmparr){
						if(null!=item && !"".equals(item.trim())){
							userList=getSysUserListByRoleid(item.trim());
							if(null!=userList && userList.size()>0){
								for(SysUser userItem: userList ){
									if(StringUtils.isNotEmpty(userItem.getUserid())){
										list.add(userItem.getUserid());
									}
								}
							}
						}
					}
				}
			}
		}
		if(list.size()>0){
			//去掉重复的
			HashSet<String> hs=new HashSet<String>(list);
			list.clear();
			list.addAll(hs);
			tmparr=(String[])list.toArray(new String[0]);
			if(tmparr!=null && tmparr.length>0){
				map.put("receivers",StringUtils.join(tmparr,","));
				map.put("titile", "请查收我的传阅件信息！标题："+fileDistrib.getTitle());
				map.put("tabtitle", "传阅件");
				map.put("content", "请查收我的传阅件！<br/>标题："+fileDistrib.getTitle()+"<br/>");
				map.put("remindurl", "message/filedistrib/fileDistribReceiveDetailPage.do?id="+fileDistrib.getId());	//提醒地址
				addMessageReminder(map);
			}
		}
	}
	@Override
	public boolean insertFileDistribReceive(FileDistribReceive fileDistribReceive) throws Exception{
		return fileDistribMapper.insertFileDistribReceive(fileDistribReceive)>0;
	}
	@Override
	public int getFileDistribReceiveCountBy( String fid, String receiveuserid) throws Exception{
		return fileDistribMapper.getFileDistribReceiveCountBy(fid, receiveuserid);
	}
	@Override
	public boolean updateFileDistribReceiveRead( String fid, String receiveuserid) throws Exception{
		return fileDistribMapper.updateFileDistribReceiveRead(fid, receiveuserid)>0;
	}
	@Override
	public boolean addFileDistribRead(String idarrs) throws Exception{
		boolean flag=false;
		SysUser sysUser=getSysUser();
		FileDistribReceive fDistribReceive=new FileDistribReceive();
		fDistribReceive.setReceiveuserid(sysUser.getUserid());
		fDistribReceive.setReadcount(0);
		String[] idarr=idarrs.split(",");
		Date date=new Date();
		for(String item : idarr){
			if(item!=null && !"".equals(item.trim()) && StringUtils.isNumeric(item.trim())){
				flag=true;
				if(getFileDistribReceiveCountBy(item.trim(),sysUser.getUserid())==0){
					fDistribReceive.setFid(Integer.parseInt(item.trim()));
					fDistribReceive.setReceivetime(date);
					fDistribReceive.setReadtime(date);
					flag=insertFileDistribReceive(fDistribReceive) || flag;
				}else{
					flag=updateFileDistribReceiveRead(item.trim(),sysUser.getUserid()) || flag;
				}
 			}
		}
		return flag;
	}
	public PageData showFileDistribReadPageList(PageMap pageMap) throws Exception{
		PageData pageData = new PageData(fileDistribMapper
				.getFileDistribReadCount(pageMap), fileDistribMapper
				.getFileDistribReadPageList(pageMap), pageMap);
		return pageData;
	}
}

