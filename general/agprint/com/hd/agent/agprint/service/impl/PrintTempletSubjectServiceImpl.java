/**
 * @(#)PrintTempletSubjectServiceImpl.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-15 zhanghonghui 创建版本
 */
package com.hd.agent.agprint.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.agprint.model.PrintTempletSubject;
import com.hd.agent.agprint.service.IPrintTempletSubjectService;
import com.hd.agent.common.util.EhcacheUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 处理打印模板代码的业务逻辑的实现类
 * @author zhanghonghui
 */
public class PrintTempletSubjectServiceImpl extends BasePrintTempletServiceImpl implements IPrintTempletSubjectService {
	
	/**
	 * 业务逻辑实现方法
	 */
	
	@Override
	public List showPrintTempletSubjectList() throws Exception{
		List list=getPrintTempletSubjectMapper().getPrintTempletSubjectList();
		return list;
	}
	@Override
	public PageData showPrintTempletSubjectPageList(PageMap pageMap) throws Exception{
		int total=getPrintTempletSubjectMapper().getPrintTempletSubjectCount(pageMap);
		List list=getPrintTempletSubjectMapper().getPrintTempletSubjectPageList(pageMap);
		PageData pageData=new PageData(total, list, pageMap);
		return pageData;
	}
	@Override
	public PrintTempletSubject showPrintTempletSubjectInfo(String code) throws Exception{
		PrintTempletSubject printTempletSubject=getPrintTempletSubjectMapper().getPrintTempletSubject(code);
		return printTempletSubject;
	} 
	@Override
	public Map addPrintTempletSubject(PrintTempletSubject printTempletSubject) throws Exception{
		Map resultMap=new HashMap();
		if(null==printTempletSubject){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关数据");
			return resultMap;
		}
		Map paramMap=new HashMap();
		paramMap.put("code", printTempletSubject.getCode());
		int icount=getPrintTempletSubjectMapper().getPrintTempletSubjectCountBy(paramMap);
		if(icount>0){
			resultMap.put("flag", false);
			resultMap.put("msg", "代码："+printTempletSubject.getCode()+"已经存在");
			return resultMap;			
		}
		SysUser sysUser=getSysUser();
		printTempletSubject.setAdduserid(sysUser.getUserid());
		printTempletSubject.setAddusername(sysUser.getName());
		printTempletSubject.setAddtime(new Date());
		icount=getPrintTempletSubjectMapper().addPrintTempletSubject(printTempletSubject);
		resultMap.put("flag", icount>0);
		return resultMap;
	}
	@Override
	public Map editPrintTempletSubject(PrintTempletSubject printTempletSubject) throws Exception{
		Map resultMap=new HashMap();
		if(null==printTempletSubject){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关数据");
			return resultMap;
		}
		
		SysUser sysUser=getSysUser();
		printTempletSubject.setModifyuserid(sysUser.getUserid());
		printTempletSubject.setModifyusername(sysUser.getName());
		printTempletSubject.setModifytime(new Date());
		int icount = getPrintTempletSubjectMapper().editPrintTempletSubject(printTempletSubject);
		resultMap.put("flag", icount>0);
		return resultMap;
	}
	@Override
	public Map disablePrintTempletSubject(String code) throws Exception{	
		Map resultMap=new HashMap();
		if(null== code ||"".equals(code.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关数据");
			return resultMap;			
		}
		PrintTempletSubject oldModel=getPrintTempletSubjectMapper().getPrintTempletSubject(code.trim());
	
		
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
		PrintTempletSubject uPrintTempletSubject=new PrintTempletSubject();
		SysUser sysUser=getSysUser();
		uPrintTempletSubject.setCode(code);
		uPrintTempletSubject.setClosetime(new Date());
		uPrintTempletSubject.setCloseuserid(sysUser.getUserid());
		uPrintTempletSubject.setCloseusername(sysUser.getName());
		int icount=getPrintTempletSubjectMapper().disablePrintTempletSubject(uPrintTempletSubject);
		resultMap.put("flag", icount>0);
		return resultMap;
	}
	
	public Map enablePrintTempletSubject(String code) throws Exception{
		
		Map resultMap=new HashMap();

		if(null==code || "".equals(code.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关数据");
			return resultMap;
			
		}
		PrintTempletSubject oldModel=getPrintTempletSubjectMapper().getPrintTempletSubject(code.trim());
		
		if(null==oldModel){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关数据");
			return resultMap;			
		}
		if(StringUtils.isEmpty(oldModel.getState()) || !"0".equals(oldModel.getState())){
			resultMap.put("flag", false);
			resultMap.put("msg", "禁用的数据才能被启用");
			return resultMap;					
		}

		PrintTempletSubject uPrintTempletSubject=new PrintTempletSubject();
		SysUser sysUser=getSysUser();
		uPrintTempletSubject.setCode(code);
		uPrintTempletSubject.setOpentime(new Date());
		uPrintTempletSubject.setOpenuserid(sysUser.getUserid());
		uPrintTempletSubject.setOpenusername(sysUser.getName());
		int icount=getPrintTempletSubjectMapper().enablePrintTempletSubject(uPrintTempletSubject);
		resultMap.put("flag", icount>0);
		return resultMap;
	}
	@Override
	public Map deletePrintTempletSubject(String code)throws Exception{
		Map resultMap=new HashMap();
		if(null==code || "".equals(code.trim())){
			resultMap.put("flag", false);
			resultMap.put("msg", "未找到相关数据");
			return resultMap;
			
		}
		boolean delFlag=canTableDataDictDelete("t_print_templet_subject", code.trim());
		if(delFlag){
			PrintTempletSubject oldModel=getPrintTempletSubjectMapper().getPrintTempletSubject(code.trim());			
			
			if(null==oldModel){
				resultMap.put("flag", false);
				resultMap.put("msg", "未找到相关数据");
				return resultMap;			
			}
			if(!"0".equals(oldModel.getState())){
				resultMap.put("flag", false);
				resultMap.put("msg", "禁用的数据才能被删除");
				return resultMap;					
			}

			int icount=getPrintTempletSubjectMapper().deletePrintTempletSubject(code.trim());
			resultMap.put("flag", icount>0);
			return resultMap;
		}else{
			resultMap.put("flag", false);
			resultMap.put("msg", "被引用的数据不能被删除");
			return resultMap;				
		}
	}
	@Override
	public Map deletePrintTempletSubjectMore(String codearrs)throws Exception{
		Map map=new HashMap();
		int iSuccess=0;
		int iFailure=0;
		int iNohandle=0;
		
		if(null==codearrs || "".equals(codearrs.trim())){

			map.put("flag", false);
			map.put("isuccess", iSuccess);
			map.put("ifailure", iFailure);
			return map;
		}
		String[] codearr=codearrs.trim().split(",");
		for(String code : codearr){
			if(null==code || "".equals(code.trim())){
				continue;
			}
			Map resultMap=deletePrintTempletSubject(code);
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
	public boolean isUsedPrintTempletSubjectCode(String code) throws Exception{
		if(null==code || "".equals(code.trim())){
			return false;
		}
		Map paramMap=new HashMap();
		paramMap.put("code", code.trim());
		int icount=getPrintTempletSubjectMapper().getPrintTempletSubjectCountBy(paramMap);
		return icount>0;
	}



	@Override
	public String getPrintTempletSubjectListJsonCache() throws Exception {
		//获取缓存数据
		String objcet = (String) EhcacheUtils.getCacheData("PrintTempletListJsonCache");
		if(null==objcet){
			List<PrintTempletSubject> list=getPrintTempletSubjectMapper().getPrintTempletSubjectList();
			Map<String,PrintTempletSubject> datamap = new HashMap<String,PrintTempletSubject>();
			for(PrintTempletSubject item : list){
				/*
				if(datamap.containsKey(item.getCode())){
					List codeList = (List) datamap.get(item.getCode());
					codeList.add(item);
				}else{
					List codeList = new ArrayList();
					codeList.add(item);
					datamap.put(item.getCode(), codeList);
				}
				*/
				datamap.put(item.getCode(), item);
			}
			String codes = JSONUtils.mapToJsonStr(datamap);
			EhcacheUtils.addCache("PrintTempletListJsonCache", codes);
			objcet = codes;
		}
		return objcet;
	}

}

