package com.hd.agent.agprint.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.agprint.model.PrintPaperSize;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.dao.PrintTempletResourceMapper;
import com.hd.agent.agprint.dao.PrintTempletSubjectMapper;
import com.hd.agent.agprint.model.PrintTempletResource;
import com.hd.agent.agprint.model.PrintTempletSubject;
import com.hd.agent.agprint.service.IPrintTempletResourceService;
import com.hd.agent.common.dao.AttachFileMapper;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
/**
 * 打印模板资源操作服务层
 * @author master
 *
 */
public class PrintTempletResourceServiceImpl extends BasePrintTempletServiceImpl implements IPrintTempletResourceService {

	/**
	 * 附件数据Mapper
	 */
	private AttachFileMapper attachFileMapper;
	
	public AttachFileMapper getAttachFileMapper() {
		return attachFileMapper;
	}

	public void setAttachFileMapper(AttachFileMapper attachFileMapper) {
		this.attachFileMapper = attachFileMapper;
	}
	@Override
	public PageData showPrintTempletResourcePageList(PageMap pageMap) throws Exception{
		int total=getPrintTempletResourceMapper().getPrintTempletResourceCount(pageMap);
		List<PrintTempletResource> list=getPrintTempletResourceMapper().getPrintTempletResourcePageList(pageMap);
		for(PrintTempletResource item:list){
			StringBuilder sb=null;
			if(StringUtils.isNotEmpty(item.getCode())){
				PrintTempletSubject printTempletSubject=getPrintTempletSubjectInfo(item.getCode());
				if(null!=printTempletSubject){
					sb = new StringBuilder();
					sb.append(printTempletSubject.getName());
					if (!"1".equals(printTempletSubject.getState())) {
						sb.append("(未启用)");
					}
					item.setCodename(sb.toString());
				}
			}
			if(StringUtils.isNotEmpty(item.getPapersizeid())) {
				PrintPaperSize printPaperSize = getPrintPaperSizeInfo(item.getPapersizeid());
				if (null != printPaperSize) {
					sb = new StringBuilder();
					sb.append(printPaperSize.getName());
					if (!"1".equals(printPaperSize.getState())) {
						sb.append("(未启用)");
					}
					item.setPapersizename(sb.toString());
				}
			}
		}
		PageData pageData=new PageData(total, list, pageMap);
		return pageData;
	}
	@Override
	public PrintTempletResource showPurePrintTempletResource(String id) throws Exception{
		PrintTempletResource printTempletResource=getPrintTempletResourceMapper().getPrintTempletResource(id);
		if(null!=printTempletResource){
			if(StringUtils.isNotEmpty(printTempletResource.getCode())){
				PrintTempletSubject printTempletSubject=getPrintTempletSubjectInfo(printTempletResource.getCode());

				printTempletResource.setPrintTempletSubjectInfo(printTempletSubject);
			}

			if(StringUtils.isNotEmpty(printTempletResource.getPapersizeid())){
				PrintPaperSize printPaperSize=getPrintPaperSizeInfo(printTempletResource.getPapersizeid());
				printTempletResource.setPrintPaperSizeInfo(printPaperSize);
			}
		}
		return printTempletResource;
	}

	@Override
	public PrintTempletResource showPrintTempletResourceInfo(String id) throws Exception{
		PrintTempletResource printTempletResource=getPrintTempletResourceMapper().getPrintTempletResource(id);
		if(null!=printTempletResource){
			if(StringUtils.isNotEmpty(printTempletResource.getCode())){
				PrintTempletSubject printTempletSubject=getPrintTempletSubjectInfo(printTempletResource.getCode());

				printTempletResource.setPrintTempletSubjectInfo(printTempletSubject);
			}

			if(StringUtils.isNotEmpty(printTempletResource.getPapersizeid())){
				PrintPaperSize printPaperSize=getPrintPaperSizeInfo(printTempletResource.getPapersizeid());
				printTempletResource.setPrintPaperSizeInfo(printPaperSize);
			}
		}
		return printTempletResource;
	}
	/**
	 * 添加打印模板资源
	 * @param printTempletResource
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public Map addPrintTempletResource(PrintTempletResource printTempletResource) throws Exception{
		Map resultMap=new HashMap();
		
		if(null==printTempletResource){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关数据");
			return resultMap;
		}
		AttachFile attachFile=attachFileMapper.getAttachFile(printTempletResource.getSourcefileid());
		if(null==attachFile){
			resultMap.put("flag", false);
			resultMap.put("msg", "请上传模板源文件(jrxml)");
			return resultMap;
		}
		
		printTempletResource.setSourcefile(attachFile.getOldfilename());
		printTempletResource.setSourcepath(attachFile.getFullpath());
		attachFile=attachFileMapper.getAttachFile(printTempletResource.getTempletfileid());
		if(null==attachFile){
			resultMap.put("flag", false);
			resultMap.put("msg", "请上传模板文件(jasper)");
			return resultMap;
		}
		printTempletResource.setTempletfile(attachFile.getOldfilename());
		printTempletResource.setTempletpath(attachFile.getFullpath());
		
		SysUser sysUser=getSysUser();
		printTempletResource.setAdduserid(sysUser.getUserid());
		printTempletResource.setAddusername(sysUser.getName());
		printTempletResource.setAddtime(new Date());
		boolean flag= getPrintTempletResourceMapper().insertPrintTempletResource(printTempletResource)>0;
		resultMap.put("flag", flag);
		return resultMap;
	}
	/**
	 * 编辑打印模板资源
	 * @param printTempletResource
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public Map editPrintTempletResource(PrintTempletResource printTempletResource) throws Exception{
		Map resultMap=new HashMap();
		if(null==printTempletResource){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关数据");
			return resultMap;
		}
		SysUser sysUser=getSysUser();
		printTempletResource.setModifyuserid(sysUser.getUserid());
		printTempletResource.setModifyusername(sysUser.getName());
		printTempletResource.setModifytime(new Date());
		int icount = getPrintTempletResourceMapper().updatePrintTempletResource(printTempletResource);
		resultMap.put("flag", icount>0);
		return resultMap;
	}
	/**
	 * 禁用打印模板资源
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public Map disablePrintTempletResource(String id) throws Exception{
		Map resultMap=new HashMap();
		if(null== id ||"".equals(id.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关数据");
			return resultMap;			
		}
		PrintTempletResource oldModel=getPrintTempletResourceMapper().getPrintTempletResource(id.trim());
	
		
		if(null==oldModel){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关数据");
			return resultMap;			
		}
		if(!"1".equals(oldModel.getState())){
			resultMap.put("flag", false);
			resultMap.put("msg", "启用的数据才能禁用");
			return resultMap;					
		}
		PrintTempletResource uPrintTempletResource=new PrintTempletResource();
		SysUser sysUser=getSysUser();
		uPrintTempletResource.setId(oldModel.getId());
		uPrintTempletResource.setClosetime(new Date());
		uPrintTempletResource.setCloseuserid(sysUser.getUserid());
		uPrintTempletResource.setCloseusername(sysUser.getName());
		int icount=getPrintTempletResourceMapper().disablePrintTempletResource(uPrintTempletResource);
		resultMap.put("flag", icount>0);
		return resultMap;
	}
	/**
	 * 启用打印模板资源
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public Map enablePrintTempletResource(String id) throws Exception{
		Map resultMap=new HashMap();
		if(null== id ||"".equals(id.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关数据");
			return resultMap;			
		}
		PrintTempletResource oldModel=getPrintTempletResourceMapper().getPrintTempletResource(id.trim());
	
		
		if(null==oldModel){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关数据");
			return resultMap;			
		}
		if(StringUtils.isEmpty(oldModel.getState()) || !"0".equals(oldModel.getState())){
			resultMap.put("flag", false);
			resultMap.put("msg", "启用的数据才能禁用");
			return resultMap;					
		}
		PrintTempletResource uPrintTempletResource=new PrintTempletResource();
		SysUser sysUser=getSysUser();
		uPrintTempletResource.setId(oldModel.getId());
		uPrintTempletResource.setClosetime(new Date());
		uPrintTempletResource.setCloseuserid(sysUser.getUserid());
		uPrintTempletResource.setCloseusername(sysUser.getName());
		int icount=getPrintTempletResourceMapper().enablePrintTempletResource(uPrintTempletResource);
		resultMap.put("flag", icount>0);
		return resultMap;
	}
	/**
	 * 删除打印模板资源
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年12月1日
	 */
	public Map deletePrintTempletResource(String id)throws Exception{
		Map resultMap=new HashMap();
		if(null==id || "".equals(id.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关数据");
			return resultMap;
			
		}
		boolean delFlag=canTableDataDictDelete("t_print_templet_resource", id.trim());
		if(delFlag){
			PrintTempletResource oldModel=getPrintTempletResourceMapper().getPrintTempletResource(id.trim());
			
			if(null==oldModel){
				resultMap.put("flag", false);
				resultMap.put("msg", "未找到相关数据");
				return resultMap;			
			}
			if("1".equals(oldModel.getIssystem())){
				resultMap.put("flag", false);
				resultMap.put("msg", "系统预置的资源不能被删除");
				return resultMap;					
			}
			if(!"0".equals(oldModel.getState())){
				resultMap.put("flag", false);
				resultMap.put("msg", "禁用的数据才能被删除");
				return resultMap;					
			}
			int icount=getPrintTempletResourceMapper().deleteUnEnablePrintTempletResource(id.trim());
			resultMap.put("flag", icount>0);
			return resultMap;
		}else{
			resultMap.put("flag", false);
			resultMap.put("msg", "被引用的数据不能被删除");
			return resultMap;		
		}
	}
	@Override
	public Map deletePrintTempletResourceMore(String idarrs)throws Exception{
		Map map=new HashMap();
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		
		if(null==idarrs || "".equals(idarrs.trim())){

			map.put("flag", false);
			map.put("isuccess", iSuccess);
			map.put("ifailure", iFailure);
			return map;
		}
		String[] idArr=idarrs.trim().split(",");
		for(String id : idArr){
			if(null==id || "".equals(id.trim())){
				continue;
			}
			Map resultMap=deletePrintTempletResource(id);
			Boolean flag=false;
			if(null!=resultMap){
				flag=(Boolean)resultMap.get("flag");
				if(null==flag){
					flag=false;
				}
			}
			if(flag){
				iSuccess=iSuccess+1;
			}else{
				iFailure=iFailure+1;
			}
		}
		map.clear();
		if(iSuccess>0){
			map.put("flag", true);
		}else{
			map.put("flag", false);
		}
		map.put("isuccess", iSuccess);
		map.put("ifailure", iFailure);
		return map;
	}
}
