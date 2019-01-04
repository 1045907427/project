/**
 * @(#)SysParamService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-20 panxiaoxiao 创建版本
 */
package com.hd.agent.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.dao.SysParamMapper;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysParamService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 处理系统参数业务逻辑功能的实现类
 * @author panxiaoxiao
 */
public class SysParamService implements ISysParamService{
	/**
	 * 操作SysParam表的dao接口
	 */
	private SysParamMapper sysParamMapper;
	
	public SysParamMapper getSysParamMapper() {
		return sysParamMapper;
	}

	public void setSysParamMapper(SysParamMapper sysParamMapper) {
		this.sysParamMapper = sysParamMapper;
	}

	/**
	 * 实现方法
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-20
	 */
	public List showSysParamList() throws Exception{
		List list=sysParamMapper.getSysParamList();
		return list;
	}
	
	public PageData showSysParamPageList(PageMap pageMap) throws Exception{
		int total=sysParamMapper.getSysParamCount(pageMap);
		List list=sysParamMapper.getSysParamPageList(pageMap);
		PageData pageData=new PageData(total, list, pageMap);
		return pageData;
	}
	
	public SysParam showSysParamInfo(String paramid) throws Exception{
		SysParam sysParam=sysParamMapper.getSysParamInfo(paramid);
		return sysParam;
	}
	
	public boolean addSysParam(SysParam sysParam) throws Exception{
		int i=sysParamMapper.insertSysParam(sysParam);
		return i>0;
	}
	
	public boolean editSysParam(SysParam sysParam) throws Exception{
		int i=sysParamMapper.updateSysParam(sysParam);
		return i>0;
	}
	
	public boolean disableSysParam(String paramid) throws Exception{
		int i=sysParamMapper.disableSysParam(paramid);
		return i>0;
	}
	
	public boolean enableSysParam(String paramid) throws Exception{
		int i=sysParamMapper.enableSysParam(paramid);
		return i>0;
	}
	
	public boolean searchPname(String pname) throws Exception{
		String str=sysParamMapper.searchPname(pname);
		if(str==null){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public SysParam getSysParam(String name) throws Exception {
		SysParam sysParam = sysParamMapper.getSysParam(name);
		return sysParam;
	}

	@Override
	public List<SysParam> showSysParamListByModualId(String id) throws Exception {
		return sysParamMapper.showSysParamListByModualId(id);
	}

	@Override
	public Map updateSysCodeByModual(Map map) throws Exception{
		Map rsMap= new HashMap();
		for (Object key : map.keySet()) {
			sysParamMapper.updateSysParamBypName((String)key,(String)map.get(key));
		}
		rsMap.put("msg", "保存成功");
		rsMap.put("flag", true);
		return rsMap;
	}

	@Override
	public String getPrintToolTypeJsonCache() throws Exception{
		Map jsonResultMap=new HashMap();
		jsonResultMap.put("AgPrintToolType","");
		jsonResultMap.put("AgPrintViewToolType","");
		SysParam printSysParam=sysParamMapper.getSysParam("AgPrintToolType");
		if(null!=printSysParam && StringUtils.isNotEmpty(printSysParam.getPvalue())){
			jsonResultMap.put("AgPrintToolType",printSysParam.getPvalue());
		}
		SysParam printViewSysParam=sysParamMapper.getSysParam("AgPrintViewToolType");
		if(null!=printViewSysParam){
			jsonResultMap.put("AgPrintViewToolType",printViewSysParam.getPvalue());
		}
		JSONObject jsonObject = JSONObject.fromObject(jsonResultMap);
		String jsonStr = jsonObject.toString();
		return jsonStr;
	}

}

