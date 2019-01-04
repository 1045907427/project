package com.hd.agent.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.oa.model.OaCustomerBrand;

public interface OaCustomerBrandMapper {

	public int selectBarcodenum(@Param("brand") String brand);
	
	public List selectCustomerBrandList(PageMap pageMap);
	
	public int selectCustomerBrandListCount(PageMap pageMap);
	
	public void insertCustomerBrand(OaCustomerBrand brand);
	
	public int deleteBrandByBillid(@Param("billid") String billid);
}