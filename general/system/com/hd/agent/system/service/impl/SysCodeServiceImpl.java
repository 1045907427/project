/**
 * @(#)SysCodeServiceImpl.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-15 panxiaoxiao 创建版本
 */
package com.hd.agent.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.EhcacheUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.dao.SysCodeMapper;
import com.hd.agent.system.dao.SysNumberMapper;
import com.hd.agent.system.dao.SysNumberRuleMapper;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.SysNumber;
import com.hd.agent.system.service.ISysCodeService;

/**
 * 
 * 处理公共代码的业务逻辑的实现类
 * @author panxiaoxiao
 */
public class SysCodeServiceImpl extends BaseServiceImpl implements ISysCodeService {
	/**
	 * 操作SysCode表的dao接口
	 */
	private SysCodeMapper sysCodeMapper;
	private SysNumberMapper sysNumberMapper;
	private SysNumberRuleMapper sysNumberRuleMapper;
	private SysNumberService sysNumberService;
	private SysNumber sysNumber;
	public SysNumberService getSysNumberService() {
		return sysNumberService;
	}

	public void setSysNumberService(SysNumberService sysNumberService) {
		this.sysNumberService = sysNumberService;
	}

	public SysNumber getSysNumber() {
		return sysNumber;
	}

	public void setSysNumber(SysNumber sysNumber) {
		this.sysNumber = sysNumber;
	}

	public SysNumberMapper getSysNumberMapper() {
		return sysNumberMapper;
	}

	public void setSysNumberMapper(SysNumberMapper sysNumberMapper) {
		this.sysNumberMapper = sysNumberMapper;
	}

	public SysNumberRuleMapper getSysNumberRuleMapper() {
		return sysNumberRuleMapper;
	}

	public void setSysNumberRuleMapper(SysNumberRuleMapper sysNumberRuleMapper) {
		this.sysNumberRuleMapper = sysNumberRuleMapper;
	}

	public SysCodeMapper getSysCodeMapper() {
		return sysCodeMapper;
	}

	public void setSysCodeMapper(SysCodeMapper sysCodeMapper) {
		this.sysCodeMapper = sysCodeMapper;
	}
	
	/**
	 * 业务逻辑实现方法
	 */
	
	/**
	 * 显示公共代码列表
	 */
	public List showSysCodeList() throws Exception{
		List list=sysCodeMapper.getSysCodeList();
		return list;
	}
	
	public List showSysCodeTypes() throws Exception{
		List typeList=sysCodeMapper.getSysCodeTypes();
		return typeList;
	}
	
	public List showSysCodeCodes() throws Exception{
		List codesList=sysCodeMapper.getSysCodeCodes();
		return codesList;
	}
	
	public PageData showSysCodePageList(PageMap pageMap) throws Exception{
		int total=sysCodeMapper.getSysCodeCount(pageMap);
		List list=sysCodeMapper.getSysCodePageList(pageMap);
		PageData pageData=new PageData(total, list, pageMap);
		return pageData;
	}
	
	public SysCode showSysCodeInfo(String code,String type) throws Exception{
		SysCode sysCode=sysCodeMapper.getSysCodeInfo(code, type);
		return sysCode;
	} 
	
	/**
	 * 新增代码
	 */
	public boolean addSysCode(SysCode sysCode) throws Exception{
		List<SysCode> codeList = sysCodeMapper.checkSysCodeList(sysCode.getType());
		if(codeList.size() != 0){
			for(SysCode sysCodeInfo : codeList){
				if(sysCodeInfo.getCode().equals(sysCode.getCode())){//数据库中代码与将要新增的代码重复
					return false;
				}
			}
		}
		int i=sysCodeMapper.addSysCode(sysCode);
		return i>0;
	}
	
	/**
	 * 修改代码信息
	 */
	public boolean editSysCode(SysCode sysCode) throws Exception{
		List<SysCode> codeList = sysCodeMapper.checkSysCodeList(sysCode.getType());
//		if(codeList.size() != 0){
//			for(SysCode sysCodeInfo : codeList){
//				if(sysCodeInfo.getCode().equals(sysCode.getCode())){//数据库中代码与将要新增的代码重复
//					return false;
//				}
//			}
//		}
		boolean flag = sysCodeMapper.editSysCode(sysCode) > 0;
		if(flag){
			//若为价格套，修改代码名称则需修改商品价格套中的代码名称
			if("price_list".equals(sysCode.getType())){
				getBaseGoodsMapper().updateGoodsPriceListByCode(sysCode);
			}
		}
		return flag;
	}
	
	public boolean disableSysCode(String code,String type) throws Exception{
		int i=sysCodeMapper.disableSysCode(code,type);
		return i>0;
	}
	
	public boolean enableSysCode(String code,String type) throws Exception{
		int i=sysCodeMapper.enableSysCode(code,type);
		return i>0;
	}
	
	public String searchCodeName(String code,String type) throws Exception{
		String sCodeName=sysCodeMapper.searchCodename(code,type);
		return sCodeName;
	}
	
	public boolean searchCode(String code,String type) throws Exception{
		String sCode=sysCodeMapper.searchCode(code,type);
		if(sCode==null){
			return false;
		}else{
			return true;
		}
	}
	
	public String codenametocode(String codeName,String type)throws Exception{
		String code=sysCodeMapper.codenametocode(codeName,type);
		return code;
	}
	
	public List showSysCodeListByType(String type) throws Exception{
		List listByType=sysCodeMapper.getSysCodeListForeign(type);
		return listByType;
	}
	
	public boolean deleteSCBillName(String code,String type) throws Exception{
		int i=0,t=0,r=0,n;
		boolean nflag[],countFlag=true;
		String numberID[];
		if(code == null && type==null ){
			return false;
		}
		else{
			List<SysNumber> numberidsList= new ArrayList<SysNumber>();
			numberidsList=sysNumberMapper.getNumIdByBcode(code);
			if(numberidsList.size()>0){
				for(int j=0;j<numberidsList.size();j++){
					numberID = new String[numberidsList.size()];
					nflag = new boolean[numberidsList.size()];
					numberID[j] =numberidsList.get(j).getNumberid().toString();
					List vList=sysNumberRuleMapper.getSysNumberRuleList(numberID[j]);
					if(vList.size() > 0){
						r=sysNumberRuleMapper.deleteSysNumberRuleByNumID(numberID[j]);
						n=sysNumberMapper.deleteSysNumber(numberID[j]);
						if(r>0 && n>0){
							nflag[j]=true;
						}
					}
					else{
						n=sysNumberMapper.deleteSysNumber(numberID[j]);
						if(n>0){nflag[j]=true;}
					}
					countFlag=countFlag && nflag[j];
				}
				if(countFlag == true){
					i=sysCodeMapper.deleteSCBillName(code, type);
				}
			}
			else{
				i=sysCodeMapper.deleteSCBillName(code, type);
			}
			if(i>0){
				return true;
			}
			return false;
		}
	}
	
	/**
	 * 返回代码类型，代码名称，对外调用
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-14
	 */
	public List backSysCodeType() throws Exception{
		List sysCodeTypeList=sysCodeMapper.getSysCodeTypeForeign();
		return sysCodeTypeList;
	}
	
	/**
	 * 删除代码
	 */
	public boolean deleteSysCode(Map paramMap)throws Exception{
		if(null != paramMap.get("state") && null != paramMap.get("code") && null != paramMap.get("type")){
			if(!"1".equals(paramMap.get("state").toString())){
				int i = sysCodeMapper.deleteSysCode(paramMap);
				return i>0;
			}
		}
		return false;
	}

	@Override
	public String getAllSysCodeList() throws Exception {
		//获取缓存数据
		String objcet = (String) EhcacheUtils.getCacheData("SysCodeCache");
		if(null==objcet){
			List<SysCode> list = sysCodeMapper.getSysCodeList();
			Map datamap = new HashMap();
			for(SysCode sysCode : list){
				if(datamap.containsKey(sysCode.getType())){
					List codeList = (List) datamap.get(sysCode.getType());
					codeList.add(sysCode);
				}else{
					List codeList = new ArrayList();
					codeList.add(sysCode);
					datamap.put(sysCode.getType(), codeList);
				}
			}
			JSONObject jsonObject = JSONObject.fromObject(datamap);
			String codes = jsonObject.toString();
			EhcacheUtils.addCache("SysCodeCache", codes);
			objcet = codes;
		}
		return objcet;
	}
	@Override
	public SysCode getEnableSysCodeFirstInfoByType(String type) throws Exception{
		return sysCodeMapper.getEnableSysCodeFirstInfoByType(type);
	}
}

