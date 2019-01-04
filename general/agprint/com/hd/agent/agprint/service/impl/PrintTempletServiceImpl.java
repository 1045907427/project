/**
 * @(#)AgprintTempletServiceImpl.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-11-6 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.agprint.model.*;
import com.hd.agent.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.service.IPrintTempletService;
import com.hd.agent.common.util.OfficeUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

/**
 * 
 * 打印模板服务
 * @author zhanghonghui
 */
public class PrintTempletServiceImpl extends BasePrintTempletServiceImpl implements IPrintTempletService {

	@Override
	public Map addPrintTemplet(PrintTemplet printTemplet) throws Exception{
		Map map=new HashMap();
		
		if(StringUtils.isEmpty(printTemplet.getTplresourceid())){
			map.put("flag", false);
			map.put("msg", "未能找到相关的打印模板资源信息");
			return map;			
		}
		
		PrintTempletResource printTempletResource=getPrintTempletResourceInfo(printTemplet.getTplresourceid());
		
		if(null==printTempletResource){
			map.put("flag", false);
			map.put("msg", "未能找到相关的打印模板资源信息");
			return map;						
		}

		printTemplet.setSourcefileid(printTempletResource.getSourcefileid());
		printTemplet.setSourcefile(printTempletResource.getSourcefile());
		printTemplet.setSourcepath(printTempletResource.getSourcepath());

		printTemplet.setTempletfileid(printTempletResource.getTempletfileid());
		printTemplet.setTempletfile(printTempletResource.getTempletfile());
		printTemplet.setTempletpath(printTempletResource.getTempletpath());
		//设置预置
		printTemplet.setIssystem(printTempletResource.getIssystem());
		
		printTemplet.setState("0");
		SysUser sysUser=getSysUser();
		printTemplet.setAdduserid(sysUser.getUserid());
		printTemplet.setAddusername(sysUser.getName());
		printTemplet.setAddtime(new Date());
		if(StringUtils.isEmpty(printTemplet.getLinktype())){
			printTemplet.setLinktype("0");
			printTemplet.setLinkdata("");
		}else{
			if("0".equals(printTemplet.getLinktype())){
				printTemplet.setLinkdata("");
			}else if("CUSTOMERID".equals(printTemplet.getLinktype())){
				printTemplet.setCustomerid(printTemplet.getLinkdata());
				printTemplet.setCustomername(printTemplet.getLinkdataname());
			}else if("DEPTID".equals(printTemplet.getLinktype())){
				printTemplet.setDeptid(printTemplet.getLinkdata());
				printTemplet.setDeptname(printTemplet.getLinkdataname());
			}else if("SALESAREA".equals(printTemplet.getLinktype())){
				printTemplet.setSalesarea(printTemplet.getLinkdata());
				printTemplet.setSalesareaname(printTemplet.getLinkdataname());
			}
		}
		boolean flag= getPrintTempletMapper().addPrintTemplet(printTemplet)>0;
		map.put("flag", flag);
		return map;
	}
	@Override
	public Map updatePrintTemplet(PrintTemplet printTemplet) throws Exception{
		Map map=new HashMap();
		
		if(StringUtils.isEmpty(printTemplet.getTplresourceid())){
			map.put("flag", false);
			map.put("msg", "未能找到相关的打印模板资源信息");
			return map;			
		}
		PrintTempletResource printTempletResource=getPrintTempletResourceInfo(printTemplet.getTplresourceid());
		
		if(null==printTempletResource){
			map.put("flag", false);
			map.put("msg", "未能找到相关的打印模板资源信息");
			return map;						
		}
		printTemplet.setSourcefileid(printTempletResource.getSourcefileid());
		printTemplet.setSourcefile(printTempletResource.getSourcefile());
		printTemplet.setSourcepath(printTempletResource.getSourcepath());
		
		printTemplet.setTempletfileid(printTempletResource.getTempletfileid());
		printTemplet.setTempletfile(printTempletResource.getTempletfile());
		printTemplet.setTempletpath(printTempletResource.getTempletpath());
		
		printTemplet.setIssystem(printTempletResource.getIssystem());
		
		SysUser sysUser=getSysUser();
		printTemplet.setModifyuserid(sysUser.getUserid());
		printTemplet.setModifyusername(sysUser.getName());
		printTemplet.setModifytime(new Date());
		printTemplet.setCustomerid("");
		printTemplet.setCustomername("");
		printTemplet.setDeptid("");
		printTemplet.setDeptname("");
		printTemplet.setSalesarea("");
		printTemplet.setSalesareaname("");

		if(StringUtils.isEmpty(printTemplet.getLinktype())){
			printTemplet.setLinktype("0");
			printTemplet.setLinkdata("");
		}else{
			if("0".equals(printTemplet.getLinktype())){
				printTemplet.setLinkdata("");
			}else if("CUSTOMERID".equals(printTemplet.getLinktype())){
				printTemplet.setCustomerid(printTemplet.getLinkdata());
				printTemplet.setCustomername(printTemplet.getLinkdataname());
			}else if("DEPTID".equals(printTemplet.getLinktype())){
				printTemplet.setDeptid(printTemplet.getLinkdata());
				printTemplet.setDeptname(printTemplet.getLinkdataname());
			}else if("SALESAREA".equals(printTemplet.getLinktype())){
				printTemplet.setSalesarea(printTemplet.getLinkdata());
				printTemplet.setSalesareaname(printTemplet.getLinkdataname());
			}
		}
		boolean flag=  getPrintTempletMapper().updatePrintTemplet(printTemplet)>0;	
		map.put("flag", flag);
		return map;
	}
	@Override
	public PageData showPrintTempletPageListData(PageMap pageMap) throws Exception{
		Map condition=pageMap.getCondition();

		if(condition.containsKey("linkdataarr")){
			String deptids=(String) condition.get("linkdataarr");
			String[] deptarr=deptids.split(",");
			if(null!=deptarr){
				condition.put("linkdatalist", deptarr);
			}
		}
		String sort=(String) condition.get("sort");
		String order=(String) condition.get("order");
		if((null==sort || "".equals(sort.trim())) || (null==order || "".equals(order.trim()))){
			condition.remove("sort");
			condition.remove("order");
			pageMap.setOrderSql("state desc,seq asc,id desc");
		}
		
		List<PrintTemplet> list=getPrintTempletMapper().getPrintTempletPageList(pageMap);
		for(PrintTemplet item :list){
			StringBuilder sb=null;
			if(StringUtils.isNotEmpty(item.getCode())) {
				//打印模板代码
				PrintTempletSubject printTempletSubject = getPrintTempletSubjectInfo(item.getCode());
				if (null != printTempletSubject) {
					sb = new StringBuilder();
					sb.append(printTempletSubject.getName());
					if (!"1".equals(printTempletSubject.getState())) {
						sb.append("(未启用)");
					}
					item.setCodename(sb.toString());

				}
			}
			if(StringUtils.isNotEmpty(item.getTplresourceid())) {
				//打印模板资源
				PrintTempletResource printTempletResource = getPrintTempletResourceInfo(item.getTplresourceid());
				if (null != printTempletResource) {
					sb = new StringBuilder();
					sb.append("【");
					sb.append(printTempletResource.getViewid());
					sb.append("】");
					sb.append(printTempletResource.getName());
					if (!"1".equals(printTempletResource.getState())) {
						sb.append("(未启用)");
					}
					item.setTplresourcename(sb.toString());

				}
			}
			if(StringUtils.isNotEmpty(item.getTplorderseqid())) {
				//打印模板内容排序策略
				PrintOrderSeq printOrderSeq = getPrintOrderSeqInfo(item.getTplorderseqid());
				if (null != printOrderSeq) {
					sb = new StringBuilder();
					sb.append("【");
					sb.append(printOrderSeq.getViewid());
					sb.append("】");
					sb.append(printOrderSeq.getName());
					if (!"1".equals(printOrderSeq.getState())) {
						sb.append("(未启用)");
					}
					item.setTplorderseqname(sb.toString());
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
		int count = getPrintTempletMapper().getPrintTempletPageCount(pageMap);
		PageData pageData=new PageData(count, list, pageMap);
		return pageData;
	}
	@Override
	public int showPrintTempletPageCount(Map map) throws Exception{
		PageMap pageMap=new PageMap();
		pageMap.setCondition(map);
		return getPrintTempletMapper().getPrintTempletPageCount(pageMap);
	}
	@Override
	public PrintTemplet showPrintTemplet(String id) throws Exception{
		PrintTemplet printTemplet= getPrintTempletMapper().showPrintTemplet(id);
		if(null!=printTemplet){
			if(StringUtils.isNotEmpty(printTemplet.getCode())){
				PrintTempletSubject printTempletSubject=getPrintTempletSubjectInfo(printTemplet.getCode());
				
				printTemplet.setPrintTempletSubjectInfo(printTempletSubject);
			}
			if(StringUtils.isNotEmpty(printTemplet.getTplresourceid())){
				PrintTempletResource printTempletResource=getPrintTempletResourceInfo(printTemplet.getTplresourceid());
				
				printTemplet.setPrintTempletResourceInfo(printTempletResource);
			}
			if(StringUtils.isNotEmpty(printTemplet.getTplorderseqid())){
				PrintOrderSeq printOrderSeq=getPrintOrderSeqInfo(printTemplet.getTplorderseqid());
				
				printTemplet.setPrintOrderSeqInfo(printOrderSeq);
			}
			if(StringUtils.isNotEmpty(printTemplet.getPapersizeid())){
				PrintPaperSize printPaperSize=getPrintPaperSizeInfo(printTemplet.getPapersizeid());
				printTemplet.setPrintPaperSizeInfo(printPaperSize);
			}
		}
		return printTemplet;
	}
	@Override
	public PrintTemplet showPurePrintTemplet(String id) throws Exception{
		return getPrintTempletMapper().showPrintTemplet(id);
	}
	@Override
	public Map deletePrintTemplet(String id) throws Exception{
		Map map=new HashMap();
		PrintTemplet printTemplet=getPrintTempletMapper().showPrintTemplet(id);
		if(null==printTemplet){
			map.put("flag", false);
			map.put("msg", "未能找到相关打印模板信息");
			return map;
		}
		//if("1".equals(printTemplet.getIssystem())){
		//	map.put("flag", false);
		//	map.put("msg", "预置打印模板不能被删除");
		//	return map;
		//}
		if("1".equals(printTemplet.getState())){
			map.put("flag", false);
			map.put("msg", "启用的打印模板不能被删除");
			return map;
		}
		boolean flag=getPrintTempletMapper().deletePrintTemplet(id)>0;
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map updatePrintTempletDefault(String id) throws Exception{
		Map map=new HashMap();
		PrintTemplet printTemplet=getPrintTempletMapper().showPrintTemplet(id);
		if(null==printTemplet || StringUtils.isEmpty(printTemplet.getCode())){
			map.put("flag", false);
			map.put("msg", "未能找到相关模板信息");
			return map;
		}
		if("1".equals(printTemplet.getIsdefault())){
			map.put("flag", true);
			return map;
		}
		SysUser sysUser=getSysUser();
		boolean flag=false;
		PrintTemplet aPrintTemplet=new PrintTemplet();
		//去掉相同模板代码下，默认的代码
		aPrintTemplet.setCode(printTemplet.getCode());
		aPrintTemplet.setIsdefault("0");
		aPrintTemplet.setModifytime(new Date());
		aPrintTemplet.setModifyuserid(sysUser.getUserid());
		aPrintTemplet.setModifyusername(sysUser.getName());
		getPrintTempletMapper().updatePrintTempletDefault(aPrintTemplet);
		

		//默认模板序号等于id的模板
		aPrintTemplet.setId(printTemplet.getId());
		aPrintTemplet.setIsdefault("1");
		flag=getPrintTempletMapper().updatePrintTempletDefault(aPrintTemplet)>0;
		
		map.put("flag", flag);
		return map;
	}
	/**
	 * 启用模板文件 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-7
	 */
	public Map enablePrintTemplet(String id) throws Exception{
		Map map=new HashMap();
		PrintTemplet printTemplet=getPrintTempletMapper().showPrintTemplet(id);
		if(null==printTemplet){
			map.put("flag", false);
			map.put("msg", "未能找到相关模板信息");
			return map;
		}
		if("1".equals(printTemplet.getState())){
			map.put("flag", false);
			map.put("msg", "该模板已经启用");
			return map;
		}
		SysUser sysUser=getSysUser();
		PrintTemplet aPrintTemplet=new PrintTemplet();
		aPrintTemplet.setId(printTemplet.getId());
		aPrintTemplet.setCode(printTemplet.getCode());
		aPrintTemplet.setOpentime(new Date());
		aPrintTemplet.setOpenuserid(sysUser.getUserid());
		aPrintTemplet.setOpenusername(sysUser.getName());
		boolean flag=getPrintTempletMapper().enablePrintTemplet(aPrintTemplet)>0;
		map.put("flag", flag);
		return map;
	}
	/**
	 * 禁用模板文件 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2014-11-7
	 */
	public Map disablePrintTemplet(String id) throws Exception{
		Map map=new HashMap();
		PrintTemplet printTemplet=getPrintTempletMapper().showPrintTemplet(id);
		if(null==printTemplet){
			map.put("flag", false);
			map.put("msg", "未能找到相关模板信息");
			return map;
		}
		if("0".equals(printTemplet.getState())){
			map.put("flag", true);
			return map;
		}
		SysUser sysUser=getSysUser();
		PrintTemplet aPrintTemplet=new PrintTemplet();
		aPrintTemplet.setId(printTemplet.getId());
		aPrintTemplet.setCode(printTemplet.getCode());
		aPrintTemplet.setClosetime(new Date());
		aPrintTemplet.setCloseuserid(sysUser.getUserid());
		aPrintTemplet.setCloseusername(sysUser.getName());
		boolean flag=getPrintTempletMapper().disablePrintTemplet(aPrintTemplet)>0;
		map.put("flag", flag);
		return map;
	}
	@Override
	public String showPrintTempletPathBy(Map map) throws Exception{
		PrintTemplet printTemplet=null;
		String code=(String)map.get("code");
		String realpath=(String)map.get("realServerPath");
		String templetid=(String) map.get("templetid");
		
		if(null==realpath){
			realpath="";
		}
		if(null==code || "".equals(code.trim())){
			return "";
		}
		Map queryMap=new HashMap();
		if(null==printTemplet){
			queryMap.clear();
			queryMap.put("code", code.trim());
			queryMap.put("templetid", templetid);
			printTemplet=getPrintTempletMapper().showThePrintTempletBy(queryMap);
		}
		if(null!=printTemplet && StringUtils.isNotEmpty(printTemplet.getTempletpath())){
			if("1".equals(printTemplet.getIssystem())){
				if(printTemplet.getTempletpath().indexOf("/")==0){
					realpath=realpath+"/"+printTemplet.getTempletpath().substring(1);
				}else{
					realpath=realpath+"/"+printTemplet.getTempletpath();
				}
			}else{
				realpath=CommonUtils.getDownFilePhysicalpath(OfficeUtils.getFilepath(), printTemplet.getTempletpath());
			}
		}
		if(null==realpath){
			realpath="";
		}
		realpath=CommonUtils.filterFilePathSaparator(realpath.trim());
		return realpath;
	}
	public List getPrintTempletListBy(Map map) throws Exception{
		String showDataAuthSql = (String) map.get("showDataAuthSql");
		if ("true".equals(showDataAuthSql) || !"1".equals(showDataAuthSql)) {
			String dataSql = getDataAccessRule("t_print_templet", null);
			map.put("dataSql", dataSql);
		}
		return getPrintTempletMapper().getPrintTempletListBy(map);
	}
	@Override
	public Map showPrintTempletByPrintQuery(Map map) throws Exception{
		Map resultMap=new HashMap();
		//打印模板文件
		resultMap.put("printTempletFile", "");
		//打印内容排序策略
		resultMap.put("printDataOrderSeq", "");
		resultMap.put("paperwidth",0);
		resultMap.put("paperheight",0);
		resultMap.put("paperintwidth",0);
		resultMap.put("paperintheight",0);
		resultMap.put("papersizename","");
		resultMap.put("lodophtmlmodel","1");
		resultMap.put("countperpage",0);
		resultMap.put("isfillblank","0");
		PrintTemplet printTemplet=null;
		//指定模板编号
		String templetid=(String)map.get("templetid");
		if(null==templetid){
			templetid="";
		}
		templetid=templetid.trim();
		//不使用关联
		String notUseLinkType=(String)map.get("notUseLinkType");
		if(!"1".equals(notUseLinkType)){
			notUseLinkType="0";
		}
		notUseLinkType=notUseLinkType.trim();
		//强制使用linktype
		String theLinkType=(String)map.get("theLinkType");
		if(null==theLinkType){
			theLinkType="";
		}
		theLinkType=theLinkType.trim();
		//强制使用linkdata
		String theLinkData=(String)map.get("theLinkData");
		if(null==theLinkData){
			theLinkData="";
		}
		theLinkData=theLinkData.trim();
		
		//关联的用户编号
		String linkCustomerid=(String)map.get("linkCustomerid");
		if(null==linkCustomerid){
			linkCustomerid="";
		}
		linkCustomerid=linkCustomerid.trim();
		//关联的上级用户编号
		String linkPCustomerid=(String)map.get("linkPCustomerid");
		if(null==linkPCustomerid){
			linkPCustomerid="";
		}
		linkPCustomerid=linkPCustomerid.trim();
		//关联的部门编号
		String linkDeptid=(String)map.get("linkDeptid");
		if(null==linkDeptid){
			linkDeptid="";
		}
		linkDeptid=linkDeptid.trim();
		//关联的销售区域
		String linkSalesarea=(String)map.get("linkSalesarea");
		if(null==linkSalesarea){
			linkSalesarea="";
		}
		linkSalesarea=linkSalesarea.trim();
		//关联的电商物流公司
		String linkEbshopwlgs=(String)map.get("linkEbshopwlgs");
		if(null==linkEbshopwlgs){
			linkEbshopwlgs="";
		}
		linkEbshopwlgs=linkEbshopwlgs.trim();
		//打印代码分类
		String code=(String)map.get("code");
		if(null==code || "".equals(code.trim())){
			return resultMap;
		}
		code=code.trim();
		//服务器地址
		String realpath=(String)map.get("realServerPath");		
		if(null==realpath){
			realpath="";
		}
		PrintTempletSubject printTempletSubject=getPrintTempletSubjectInfo(code.trim());
		if(null==printTempletSubject){
			return resultMap;
		}
		Map queryMap=new HashMap();
		if(!"".equals(templetid)){
			//使用模板编号
			queryMap.put("templetid", templetid);
			queryMap.put("code", code.trim());
			printTemplet=getPrintTempletMapper().showThePrintTempletBy(queryMap);
		}else if(!"".equals(theLinkType) 
				&& !"".equals(theLinkData)
				&& !"1".equals(notUseLinkType)){
			//强制使用指定关联
			queryMap.put("linktype", theLinkType);
			queryMap.put("linkdata", theLinkData);
			queryMap.put("code", code.trim());
			printTemplet=getPrintTempletMapper().showThePrintTempletBy(queryMap);

			//如果没有，则取没有关联模板
			if(null==printTemplet){
				queryMap.put("notuselinktype", "1");
				queryMap.put("code", code.trim());
				printTemplet=getPrintTempletMapper().showThePrintTempletBy(queryMap);
			}
		}else if("1".equals(printTempletSubject.getUselinktype()) 
				&& StringUtils.isNotEmpty(printTempletSubject.getLinktypeseq()) 
				&& !"1".equals(notUseLinkType)){
			String[] linkseqArr=printTempletSubject.getLinktypeseq().split(",");
			if(null==linkseqArr){
				return resultMap;
			}
			for(String linkseq:linkseqArr){
				if("CUSTOMERID".equals(linkseq) ){
					queryMap.put("linktype", "CUSTOMERID");
					if(!"".equals(linkCustomerid)){
						queryMap.put("linkdata", linkCustomerid);
						queryMap.put("code", code.trim());
						printTemplet=getPrintTempletMapper().showThePrintTempletBy(queryMap);
						if(null!=printTemplet){
							break;
						}
					}
					if(!"".equals(linkPCustomerid)){
						queryMap.put("linkdata", linkPCustomerid);
						queryMap.put("code", code.trim());
						printTemplet=getPrintTempletMapper().showThePrintTempletBy(queryMap);
						if(null!=printTemplet){
							break;
						}
					}
				}else if("DEPTID".equals(linkseq) && !"".equals(linkDeptid)){
					queryMap.put("linktype", "DEPTID");
					queryMap.put("linkdata", linkDeptid);
				}else if("SALESAREA".equals(linkseq) && !"".equals(linkSalesarea)){
					queryMap.put("linktype", "SALESAREA");
					queryMap.put("linkdata", linkSalesarea);					
				}else if("EBSHOPWLGS".equals(linkseq) && !"".equals(linkEbshopwlgs)){
					queryMap.put("linktype", "EBSHOPWLGS");
					queryMap.put("linkdata", linkEbshopwlgs);					
				}else{
					printTemplet=null;
					continue;
				}
				queryMap.put("code", code.trim());
				printTemplet=getPrintTempletMapper().showThePrintTempletBy(queryMap);
				if(null!=printTemplet){
					break;
				}
			}
			//如果没有，则取没有关联模板
			if(null==printTemplet){
				queryMap.clear();
				queryMap.put("notuselinktype", "1");
				queryMap.put("code", code.trim());
				printTemplet=getPrintTempletMapper().showThePrintTempletBy(queryMap);
			}
		}else{
			queryMap.clear();
			queryMap.put("code", code.trim());
			printTemplet=getPrintTempletMapper().showThePrintTempletBy(queryMap);
			
		}
		if(null==printTemplet){
			return resultMap;
		}
		//打印模板文件
		String printTempletFile="";
		if(null!=printTemplet && StringUtils.isNotEmpty(printTemplet.getTempletpath())){
			if("1".equals(printTemplet.getIssystem())){
				if(printTemplet.getTempletpath().indexOf("/")==0){
					printTempletFile=realpath+"/"+printTemplet.getTempletpath().substring(1);
				}else{
					printTempletFile=realpath+"/"+printTemplet.getTempletpath();
				}
			}else{
				printTempletFile=CommonUtils.getDownFilePhysicalpath(OfficeUtils.getFilepath(), printTemplet.getTempletpath());
			}
		}else{
			printTempletFile="";
		}
		if(null==printTempletFile){
			printTempletFile="";
		}
		printTempletFile=CommonUtils.filterFilePathSaparator(printTempletFile.trim());
		resultMap.put("printTempletFile", printTempletFile.trim());
		PrintOrderSeq printOrderSeq=getPrintOrderSeqInfo(printTemplet.getTplorderseqid());
		if(null!=printOrderSeq && StringUtils.isNotEmpty(printOrderSeq.getOrderseq())){
			resultMap.put("printDataOrderSeq", printOrderSeq.getOrderseq());
		}
		if(StringUtils.isNotEmpty(printTemplet.getPapersizeid())){
			PrintPaperSize printPaperSize=getPrintPaperSizeInfo(printTemplet.getPapersizeid());
			if(null!=printPaperSize){
				if(null!= printPaperSize.getWidth() && null!=printPaperSize.getHeight()){
					resultMap.put("paperwidth",printPaperSize.getWidth());
					resultMap.put("paperheight",printPaperSize.getHeight());
					resultMap.put("paperintwidth",printPaperSize.getIntwidth());
					resultMap.put("paperintheight",printPaperSize.getIntheight());
					resultMap.put("papersizename",printPaperSize.getName());
				}
			}
		}

		resultMap.put("companytitle",printTemplet.getCompanytitle());
		resultMap.put("countperpage",printTemplet.getCountperpage());
		resultMap.put("isfillblank",printTemplet.getIsfillblank());
		resultMap.put("lodophtmlmodel",printTemplet.getLodophtmlmodel());
		PrintTemplet cpPrintTemplet=(PrintTemplet)CommonUtils.deepCopy(printTemplet);
		resultMap.put("printTempletInfo",cpPrintTemplet);
		return resultMap;
	}

	@Override
	public void setTempletCommonParameter(Map parameters) throws Exception{
		if(null==parameters){
			parameters=new HashMap();
		}
		/*共用参数*/
		String companyName=getSysParamValue("CompanyNameForPrint");
		if(null==companyName || "".equals(companyName.trim())){
			companyName=getSysParamValue("COMPANYNAME");
		}

		//parameters.put("P_CompanyName", companyName);	//公司名称
		parameters.put("P_SYSAUTO_CompanyName", companyName);	//公司名称
		if(parameters.containsKey("P_TPL_COMPANYNAME")){
			String tplCompanyName=(String)parameters.get("P_TPL_COMPANYNAME");
			if(null!=tplCompanyName && !"".equals(tplCompanyName.trim())){
				parameters.put("P_SYSAUTO_CompanyName", tplCompanyName.trim());	//公司名称
			}
		}

		//公司销售电话
		parameters.put("P_CompanyTel", getSysParamValue("CompanyTelForSalesPrint"));
		//公司地址
		parameters.put("P_CompanyAddr", getSysParamValue("CompanyAddressForPrint"));
		//公司传真
		parameters.put("P_CompanyFax", getSysParamValue("CompanyFaxForPrint"));
	}

	/**
	 * 复制模板文件
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2014-11-7
	 */
	@Override
	public Map addCopyPrintTemplet(String id) throws Exception {
		Map map=new HashMap();
		PrintTemplet printTemplet=getPrintTempletMapper().showPrintTemplet(id);
		if(null==printTemplet){
			map.put("flag", false);
			map.put("msg", "未能找到相关模板信息");
			return map;
		}

		SysUser sysUser=getSysUser();
		printTemplet.setId(null);
		if(!printTemplet.getName().contains("(复制)")) {
			printTemplet.setName(printTemplet.getName() + "(复制)");
		}
		printTemplet.setAdduserid(sysUser.getUserid());
		printTemplet.setAddusername(sysUser.getName());
		printTemplet.setAddtime(new Date());
		printTemplet.setClosetime(null);
		printTemplet.setCloseuserid("");
		printTemplet.setCloseusername("");
		printTemplet.setState("0");
		boolean flag=getPrintTempletMapper().addPrintTemplet(printTemplet)>0;
		map.put("flag", flag);
		return map;
	}
}

