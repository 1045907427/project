/**
 * @(#)MobileSmsServiceImpl.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-3-7 zhanghonghui 创建版本
 */
package com.hd.agent.message.service.impl;

import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.dao.MobileSmsMapper;
import com.hd.agent.message.model.MobileSms;
import com.hd.agent.message.service.IMobileSmsService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class MobileSmsServiceImpl extends BaseServiceImpl implements IMobileSmsService {
	
	private MobileSmsMapper mobileSmsMapper;
	
	public MobileSmsMapper getMobileSmsMapper() {
		return mobileSmsMapper;
	}

	public void setMobileSmsMapper(MobileSmsMapper mobileSmsMapper) {
		this.mobileSmsMapper = mobileSmsMapper;
	}
	/**
	 * 添加手机短信
	 * @param mobileSms
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-7
	 */
	public boolean addMobileSms(MobileSms mobileSms) throws Exception{
		int irows= mobileSmsMapper.insertMobileSms(mobileSms);
		return irows>0;
	}
	/**
	 * 添加多个手机短信
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public boolean addMobileSmsList(List<MobileSms> list) throws Exception{
		if(list==null || list.size()==0){
			return false;
		}
		boolean flag=false;
		for(MobileSms item : list ){
			flag=addMobileSms(item)||flag;
		}
		return true;
	}
	
	/**
	 * 更新手机短信
	 * @param mobileSms
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-7
	 */
	public boolean updateMobileSms(MobileSms mobileSms) throws Exception{
		int irows=mobileSmsMapper.updateMobileSms(mobileSms);
		return irows>0;
	}
	
	/**
	 * 更新手机短信
	 */
	public boolean updateMobileSmsBy(Map map) throws Exception{
		if(!map.containsKey("isdataauth") || !"0".equals(map.get("isdataauth").toString())){
			String sql = getDataAccessRule("t_msg_mobilesms",null);
			map.put("authDataSql", sql);
		}
		int irows=mobileSmsMapper.updateMobileSmsBy(map);
		return irows>0;
	}

	/**
	 * 获取手机短信发送分页列表数据 <br/>
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-9
	 */
	public PageData showSendMobileSmsPageList(PageMap pageMap) throws Exception{
		String cols = getAccessColumnList("t_msg_mobilesms",null);
		pageMap.setCols(cols);
		String sql = getDataAccessRule("t_msg_mobilesms",null);
		pageMap.setDataSql(sql);
		PageData pageData = new PageData(mobileSmsMapper
				.getSendMobileSmsCount(pageMap), mobileSmsMapper
				.getSendMobileSmsPageList(pageMap), pageMap);
		return pageData;
	}
	
	/**
	 * 获取手机短信接收分页列表数据 <br/>
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-9
	 */
	public PageData showReceiveMobileSmsPageList(PageMap pageMap) throws Exception{
		PageData pageData = new PageData(mobileSmsMapper
				.getReceiveMobileSmsCount(pageMap), mobileSmsMapper
				.getReceiveMobileSmsPageList(pageMap), pageMap);
		return pageData;
	}
	/**
	 * 获取手机短信信息列表
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public List getMobileSmsListBy(Map map) throws Exception{
		return mobileSmsMapper.getMobileSmsListBy(map);
	}
	/**
	 * 获取serialno mysql为 uuid_short
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-11
	 */
	public String getSerialno() throws Exception{
		return mobileSmsMapper.getSerialno();
	}
}

