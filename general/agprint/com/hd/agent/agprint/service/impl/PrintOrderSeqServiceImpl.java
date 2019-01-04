package com.hd.agent.agprint.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintOrderSeq;
import com.hd.agent.agprint.model.PrintTempletSubject;
import com.hd.agent.agprint.service.IPrintOrderSeqService;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

public class PrintOrderSeqServiceImpl extends BasePrintTempletServiceImpl implements IPrintOrderSeqService	 {
	

	@Override
	public PageData showPrintOrderSeqPageListData(PageMap pageMap) throws Exception {
		Map condition=pageMap.getCondition();
		String sort=(String) condition.get("sort");
		String order=(String) condition.get("order");
		if((null==sort || "".equals(sort.trim())) || (null==order || "".equals(order.trim()))){
			condition.remove("sort");
			condition.remove("order");
			pageMap.setOrderSql("state desc,id desc");
		}
		List<PrintOrderSeq> list=getPrintOrderSeqMapper().getPrintOrderSeqPageList(pageMap);
		for(PrintOrderSeq item : list){
			if(null==item){
				continue;
			}
			if(StringUtils.isNotEmpty(item.getCode())){
				PrintTempletSubject printTempletSubject=getPrintTempletSubjectInfo(item.getCode());
				if(null!=printTempletSubject){
					item.setCodename(printTempletSubject.getName());
				}
			}
		}
		int iCount=getPrintOrderSeqMapper().getPrintOrderSeqPageCount(pageMap);
		PageData pageData=new PageData(iCount, list, pageMap);
		return pageData;
	}

	@Override
	public Map addPrintOrderSeq(PrintOrderSeq printOrderSeq) throws Exception {
		Map resultMap=new HashMap();
		Date nowDate=new Date();
		SysUser sysUser=getSysUser();
		printOrderSeq.setAdduserid(sysUser.getUserid());
		printOrderSeq.setAddusername(sysUser.getName());
		printOrderSeq.setAddtime(nowDate);
		if("1".equals(printOrderSeq.getState())){
			printOrderSeq.setOpentime(nowDate);
			printOrderSeq.setOpenuserid(sysUser.getUserid());
			printOrderSeq.setOpenusername(sysUser.getName());
		}else if("0".equals(printOrderSeq.getState())){
			printOrderSeq.setClosetime(nowDate);
			printOrderSeq.setCloseuserid(sysUser.getUserid());
			printOrderSeq.setCloseusername(sysUser.getName());
		}else{
			printOrderSeq.setState("0");
		}
		
		boolean isok= getPrintOrderSeqMapper().insertPrintOrderSeq(printOrderSeq)>0;

		resultMap.put("flag", isok);
		return resultMap;
	}

	@Override
	public Map updatePrintOrderSeq(PrintOrderSeq printOrderSeq) throws Exception {
		Map resultMap=new HashMap();
		if(null==printOrderSeq){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关打印内容排序策略");
			return resultMap;
		}

		PrintOrderSeq oldPrintOrderSeq=getPrintOrderSeqMapper().getPrintOrderSeq(printOrderSeq.getId());
		if(null==oldPrintOrderSeq){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关打印内容排序策略");
			return resultMap;
		}
		
		SysUser sysUser=getSysUser();
		printOrderSeq.setModifyuserid(sysUser.getUserid());
		printOrderSeq.setModifyusername(sysUser.getName());
		printOrderSeq.setModifytime(new Date());
		boolean isok= getPrintOrderSeqMapper().updatePrintOrderSeq(printOrderSeq)>0;

		resultMap.put("flag", isok);
		return resultMap;
	}

	@Override
	public Map deletePrintOrderSeq(String id) throws Exception {
		Map resultMap=new HashMap();
		PrintOrderSeq printOrderSeq=getPrintOrderSeqMapper().getPrintOrderSeq(id);
		if(null==printOrderSeq){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关打印内容排序策略");
			return resultMap;
		}
		if("1".equals(printOrderSeq.getState())){
			resultMap.put("flag", false);
			resultMap.put("msg", "启用的打印内容排序策略不能被删除");
			return resultMap;
		}
		boolean delFlag=canTableDataDictDelete("t_print_orderseq", id.trim());
		boolean isok=false;
		if(delFlag){
			isok=getPrintOrderSeqMapper().deletePrintOrderSeq(id)>0;
			resultMap.put("flag", isok);
		}else{
			resultMap.put("flag", false);
			resultMap.put("msg", "被引用的数据不能被删除");
		}
		return resultMap;
	}
	

	@Override
	public Map deletePrintOrderSeqMore(String idarrs)throws Exception{
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
			Map resultMap=deletePrintOrderSeq(id);
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

	@Override
	public Map enablePrintOrderSeq(String id) throws Exception {
		Map resultMap=new HashMap();
		PrintOrderSeq printOrderSeq=getPrintOrderSeqMapper().getPrintOrderSeq(id);
		if(null==printOrderSeq){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关打印内容排序策略");
			return resultMap;
		}
		if("1".equals(printOrderSeq.getState())){
			resultMap.put("flag", false);
			resultMap.put("msg", "当前打印内容排序策略已经启用");
			return resultMap;			
		}
		SysUser sysUser=getSysUser();
		PrintOrderSeq upPrintOrderSeq=new PrintOrderSeq();
		upPrintOrderSeq.setId(printOrderSeq.getId());
		upPrintOrderSeq.setOpentime(new Date());
		upPrintOrderSeq.setOpenuserid(sysUser.getUserid());
		upPrintOrderSeq.setOpenusername(sysUser.getName());
		boolean isok= getPrintOrderSeqMapper().enablePrintOrderSeq(upPrintOrderSeq)>0;
		resultMap.put("flag", isok);
		return resultMap;
	}

	@Override
	public Map disablePrintOrderSeq(String id) throws Exception {
		Map resultMap=new HashMap();
		PrintOrderSeq printOrderSeq=getPrintOrderSeqMapper().getPrintOrderSeq(id);
		if(null==printOrderSeq){
			resultMap.put("flag", false);
			resultMap.put("msg", "未能找到相关打印内容排序策略");
			return resultMap;
		}
		if(!"1".equals(printOrderSeq.getState())){
			resultMap.put("flag", false);
			resultMap.put("msg", "当前打印内容排序策略已经禁用");
			return resultMap;			
		}
		SysUser sysUser=getSysUser();
		PrintOrderSeq upPrintOrderSeq=new PrintOrderSeq();
		upPrintOrderSeq.setId(printOrderSeq.getId());
		upPrintOrderSeq.setClosetime(new Date());
		upPrintOrderSeq.setCloseuserid(sysUser.getUserid());
		upPrintOrderSeq.setCloseusername(sysUser.getName());
		boolean isok= getPrintOrderSeqMapper().disablePrintOrderSeq(upPrintOrderSeq)>0;
		resultMap.put("flag", isok);
		return resultMap;
	}

	@Override
	public PrintOrderSeq showPrintOrderSeqInfo(String id) throws Exception {
		PrintOrderSeq printOrderSeq=getPrintOrderSeqMapper().getPrintOrderSeq(id);
		return printOrderSeq;
	}
}
