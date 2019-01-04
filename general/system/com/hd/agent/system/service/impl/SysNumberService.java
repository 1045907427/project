/**
 * @(#)SysNumberService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-25 panxiaoxiao 创建版本
 */
package com.hd.agent.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.dao.SysNumberMapper;
import com.hd.agent.system.dao.SysNumberRuleMapper;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.SysNumber;
import com.hd.agent.system.model.SysNumberRule;
import com.hd.agent.system.service.ISysNumberService;

/**
 * 处理单据编号的业务逻辑的实现类
 * 
 * @author panxiaoxiao
 */
public class SysNumberService extends BaseServiceImpl implements ISysNumberService {
	
	private SysNumber sysNumber;
	public SysNumber getSysNumber() {
		return sysNumber;
	}

	public void setSysNumber(SysNumber sysNumber) {
		this.sysNumber = sysNumber;
	}

	private SysNumberMapper sysNumberMapper;
	private SysNumberRuleMapper sysNumberRuleMapper;
	public SysNumberRuleMapper getSysNumberRuleMapper() {
		return sysNumberRuleMapper;
	}

	public void setSysNumberRuleMapper(SysNumberRuleMapper sysNumberRuleMapper) {
		this.sysNumberRuleMapper = sysNumberRuleMapper;
	}

	public SysNumberMapper getSysNumberMapper() {
		return sysNumberMapper;
	}

	public void setSysNumberMapper(SysNumberMapper sysNumberMapper) {
		this.sysNumberMapper = sysNumberMapper;
	}

	/**
	 * 获取单据名称列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-28
	 */
	public List showSysNumberBillNameList()throws Exception{
		List billNameList=sysNumberMapper.getSysNumberBillNameList();
		return billNameList;
	}
	/**
	 * 显示单据编号列表，实现分页
	 * @return
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public PageData showSysNumberPageListByBcode(PageMap pageMap) throws Exception{
		int total=sysNumberMapper.getSysNumberCount(pageMap);
		List list=sysNumberMapper.getSysNumberPageListByBcode(pageMap);
		PageData pageData=new PageData(total,list,pageMap);
		return pageData;
	}
	
	/**
	 * 获取单据编号详情信息
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-21
	 */
	public SysNumber getSysNumberInfo(String numberid)throws Exception{
		SysNumber sysNumber=sysNumberMapper.getSysNumberInfo(numberid);
		return sysNumber;
	}
	
	/**
	 * 根据启用的单据编号billcode，判断是否自动生成
	 * @param billcode
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-19
	 */
	public SysNumber getSysNumberAutoCreate(String billcode) throws Exception{
		SysNumber sysNumberAutoCreate=sysNumberMapper.getSysNumberAutoCreate(billcode);
		return sysNumberAutoCreate;
	}
	
	/**
	 * 添加单据编号
	 * @param sysNumber
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public boolean addSysNumber(SysNumber sysNumber) throws Exception{
		int i=sysNumberMapper.insertSysNumber(sysNumber);
		return i>0;
	}
	
	/**
	 * 修改单据编码
	 * @param sysNumber
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public boolean editSysNumber(SysNumber sysNumber) throws Exception{
		int i=sysNumberMapper.editSysNumber(sysNumber);
		return i>0;
	}
	
	/**
	 * 禁用单据编码
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public boolean disableSysNumber(String numberid) throws Exception{
		SysNumber sysNumberInfo = sysNumberMapper.getSysNumberInfo(numberid);
		sysNumberInfo.setModifyflag("1");
		int e = sysNumberMapper.editSysNumber(sysNumberInfo);
		int i=sysNumberMapper.disableSysNumber(numberid);
		return i>0 && e>0;
	}
	
	/**
	 * 启用单据编码
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-25
	 */
	public boolean enableSysNumber(String numberid,String tablename,String type) throws Exception{
		List sysNumberRuleList = sysNumberRuleMapper.getSysNumberRuleList(numberid);
		if(sysNumberRuleList.size() != 0){
			boolean flagCount = true;
			if(!"".equals(type) && null != type){
				if("notonly".equals(type)){
					if(!"".equals(tablename) && null != tablename){
						List<SysNumber> sysNumberList = sysNumberMapper.getSysNumberListByCode(tablename);
						for(SysNumber sysNumber:sysNumberList){
							//当单据编号状态为启用时,则禁用该单据编号 1:启用
							if("1".equals(sysNumber.getState())){
								sysNumber.setState("0");//禁用
								sysNumber.setModifyflag("1");//允许修改
							}
							else if("0".equals(sysNumber.getState())){
								sysNumber.setModifyflag("2");//不允许修改
								sysNumber.setState("1");//启用
							}
							else{
								sysNumber.setModifyflag("1");//允许修改
							}
							int e = sysNumberMapper.editSysNumber(sysNumber);
							flagCount = flagCount && e>0;
						}
						return flagCount;
					}
				}else{
					if(!"".equals(numberid) && null != numberid){
						int i=sysNumberMapper.enableSysNumber(numberid);
						if(i> 0){
							SysNumber sysNumberInfo = sysNumberMapper.getSysNumberInfo(numberid);
							sysNumberInfo.setModifyflag("2");
				 			int e = sysNumberMapper.editSysNumber(sysNumberInfo);
				 			if(e > 0){
				 				return true;
				 			}
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 删除单据编码
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-30
	 */
	public boolean deleteSysNumber(String numberid) throws Exception{
		int j = 0;
		if(StringUtils.isNotEmpty(numberid)){
			int i = sysNumberMapper.deleteSysNumber(numberid);
			if(i > 0){
				if(sysNumberMapper.getSysNumberRuleCountByNumid(numberid) > 0){
					j = sysNumberMapper.deleteSysNumberRuleByNumid(numberid);
				}
				else{
					j = 1;
				}
				return j > 0;
			}
		}
		return false;
	}
	
	/**
	 * 批量修改单据编号是否自动生成
	 * @param billcode
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-15
	 */
	public boolean editSysNumbersAutoCreate(String billcode,String autoCreate)throws Exception{
		Map paramMap = new HashMap();
		paramMap.put("billcode", billcode);
		paramMap.put("autocreate", autoCreate);
		int i=sysNumberMapper.editSysNumbersAutoCreate(paramMap);
		return i>0;
	}
	
	/**
	 * 批量修改单据是否允许修改
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-16
	 */
	public boolean editSysNumbersModifyFlag(String numberid,String modifyflag)throws Exception{
		SysNumber sysNumberInfo = sysNumberMapper.getSysNumberInfo(numberid);
		sysNumberInfo.setModifyflag(modifyflag);
		int i=sysNumberMapper.editSysNumbersModifyFlag(sysNumberInfo);
		return i>0;
	}
	
	/**
	 * 禁用所有单据编号
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-16
	 */
	public boolean disableAllSysNumbers(PageMap pageMap)throws Exception{
		return sysNumberMapper.disableAllSysNumbers(pageMap) > 0;
	}
	
	/**
	 * 根据单据名称类型获取自动生成值去重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-23
	 */
	public List getAutoCreateByBillCode(String billcode)throws Exception{
		List list = sysNumberMapper.getAutoCreateByBillCode(billcode);
		return list;
	}

	/*--------------------------------单据编号设置-------------------------------------------*/
	@Override
	public boolean addSysNumber(SysNumber sysNumber,List<SysNumberRule> list) throws Exception {
		if(null != sysNumber){
			int count = 0;
            String numberid = Integer.toString((int)(Math.random()*1000000000));
            sysNumber.setNumberid(numberid);
			int i = sysNumberMapper.addSysNumber(sysNumber);
			if(i > 0 && (list.size() != 0)){
				for(SysNumberRule sysNumberRule:list){
                    sysNumberRule.setNumberid(numberid);
					int j = sysNumberMapper.addSysNumberRule(sysNumberRule);
					if(j > 0){
						count++;
					}
				}
				return (count == list.size());
			}
		}
		return false;
	}

	@Override
	public PageData getSysNumberList(PageMap pageMap) throws Exception {
		List<SysNumber> list = sysNumberMapper.getSysNumberListPage(pageMap);
		if(list.size() != 0){
			for(SysNumber sysNumber :list){
				if(StringUtils.isNotEmpty(sysNumber.getState())){ //状态
					SysCode sysCode = super.getBaseSysCodeMapper().getSysCodeInfo(sysNumber.getState(), "state");
					if(sysCode != null){
						sysNumber.setStateName(sysCode.getCodename());
					}
				}
			}
		}
		PageData pageData = new PageData(sysNumberMapper.getSysNumberListCount(pageMap),
				list,pageMap);
		return pageData;
	}

	@Override
	public List getSysNumberRuleList(String numberid) throws Exception {
		return sysNumberMapper.getSysNumberRuleList(numberid);
	}

	@Override
	public SysNumberRule getSysNumberRuleInfo(String numberruleid)
			throws Exception {
		return sysNumberMapper.getSysNumberRuleInfo(numberruleid);
	}

	@Override
	public boolean editSysNumber(SysNumber sysNumber,List<SysNumberRule> ruleList, String delStr)
			throws Exception {
		int i = sysNumberMapper.editSysNumber(sysNumber);
		int count = 0;
		if(i > 0){
			sysNumberMapper.deleteSysNumberRuleByNumid(sysNumber.getNumberid());

			if(ruleList.size() != 0){
				for(SysNumberRule sysNumberRule : ruleList){
					sysNumberRule.setNumberid(sysNumber.getNumberid());
					int ar = sysNumberMapper.addSysNumberRule(sysNumberRule);
					if(ar > 0){
						count++;
					}
				}
			}
			return ruleList.size() == count;
		}
		return false;
	}

	@Override
	public boolean enableSysNumber(String numberid) throws Exception {
		return sysNumberMapper.enableSysNumber(numberid) > 0;
	}


	@Override
	public boolean isRepeatFixedVal(String val) throws Exception {
		return sysNumberMapper.isRepeatFixedVal(val) > 0;
	}

    @Override
    public boolean isRepeatBillCode(String billcode) throws Exception {
        boolean flag = sysNumberMapper.isRepeatBillCode(billcode) > 0;
        return flag;
    }

    @Override
    public boolean isRepeatBillName(String billname) throws Exception {
        boolean flag = sysNumberMapper.isRepeatBillName(billname) > 0;
        return flag;
    }
}

