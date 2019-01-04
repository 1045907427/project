/**
 * @(#)IClientOffPriceLogService.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月7日 huangzhiqian 创建版本
 */
package com.hd.agent.client.service.impl;

import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.client.dao.ClientOffpriceLogMapper;
import com.hd.agent.client.model.ClientOffpriceLog;
import com.hd.agent.client.service.IOffPriceLogService;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;

import java.math.RoundingMode;
import java.util.List;

/**
 * 
 * 
 * @author huangzhiqian
 */
public class OffPriceLogServiceImpl extends BaseFilesServiceImpl implements IOffPriceLogService{
	
	private ClientOffpriceLogMapper clientOffpriceLogMapper;

	public ClientOffpriceLogMapper getClientOffpriceLogMapper() {
		return clientOffpriceLogMapper;
	}

	public void setClientOffpriceLogMapper(ClientOffpriceLogMapper clientOffpriceLogMapper) {
		this.clientOffpriceLogMapper = clientOffpriceLogMapper;
	}

	@Override
	public PageData getOffPriceLogList(PageMap pageMap) throws Exception {
		String seperator= "->";
		List<ClientOffpriceLog> rsList = clientOffpriceLogMapper.getOffPriceLogList(pageMap);
		for(ClientOffpriceLog info:rsList){
			info.setRetailprice(info.getRetailpriceafter().setScale(2,RoundingMode.HALF_UP)+"");
			
			if("1".equals(info.getOperatetype())){
				
				if(!(info.getRetailpricebefore().compareTo(info.getRetailpriceafter())==0)){
					info.setRetailprice(info.getRetailpricebefore().setScale(2,RoundingMode.HALF_UP)+seperator+info.getRetailpriceafter().setScale(2,RoundingMode.HALF_UP));
				}
				
				if(!info.getBegintimeafter().equals(info.getBegintimebefore())){
					info.setBegintimeafter(info.getBegintimebefore()+seperator+info.getBegintimeafter());
				}
				if(!info.getEndtimebefore().equals(info.getEndtimeafter())){
					info.setEndtimeafter(info.getEndtimebefore()+seperator+ info.getEndtimeafter());
				}
				if(!info.getBegindatebefore().equals(info.getBegindateafter())){
					info.setBegindateafter(info.getBegindatebefore()+seperator+info.getBegindateafter());
				}
				if(!info.getEnddatebefore().equals(info.getEnddateafter())){
					info.setEnddateafter(info.getEnddatebefore()+seperator+info.getEnddateafter());
				}
			}
			//商品名称
			GoodsInfo goodsinfo=getGoodsInfoByID(info.getGoodsid());
			if(goodsinfo!=null){
				info.setGoodsname(goodsinfo.getName());
			}
			//门店名称
			DepartMent departMent=getDepartmentByDeptid(info.getDeptid());
			if(departMent!=null){
				info.setDeptname(departMent.getName());
			}
			
		}
		int count = clientOffpriceLogMapper.getOffPriceLogCount(pageMap);
		return new PageData(count, rsList, pageMap);
	}

}

