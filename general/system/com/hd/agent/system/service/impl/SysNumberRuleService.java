/**
 * @(#)SysNumberRuleService.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-26 panxiaoxiao 创建版本
 */
package com.hd.agent.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.hd.agent.system.dao.SysNumberMapper;
import com.hd.agent.system.dao.SysNumberRuleMapper;
import com.hd.agent.system.model.SysNumber;
import com.hd.agent.system.model.SysNumberRule;
import com.hd.agent.system.service.ISysNumberRuleService;

/**
 * 单据编号规则的业务逻辑的实现类
 * 
 * @author panxiaoxiao
 */
public class SysNumberRuleService implements ISysNumberRuleService{
	private SysNumber sysNumber;
	
	public SysNumber getSysNumber() {
		return sysNumber;
	}

	public void setSysNumber(SysNumber sysNumber) {
		this.sysNumber = sysNumber;
	}
	private SysNumberRuleMapper sysNumberRuleMapper;
	private SysNumberMapper sysNumberMapper;
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

	/**
	 * 根据numberid显示单据编号规则列表
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public List showSysNumberRuleList(String numberid) throws Exception{
		List sysNumberRuleList=sysNumberRuleMapper.getSysNumberRuleList(numberid);
		return sysNumberRuleList;
	}
	
	/**
	 * 显示单据编号规则详情信息 
	 * @param numberruleid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-11
	 */
	public SysNumberRule showSysNumberRuleInfo(String numberruleid)throws Exception{
		SysNumberRule sysNumberRule=sysNumberRuleMapper.getSysNumberRuleInfo(numberruleid);
		return sysNumberRule;
	}
	
	/**
	 * 添加单据编号规则
	 * @param sysNumber
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public boolean addSysNumberRule(SysNumberRule sysNumberRule) throws Exception{
		int i=sysNumberRuleMapper.addSysNumberRule(sysNumberRule);
		return i>0;
	}
	
	/**
	 * 修改单据编号规则
	 * @param sysNumber
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public boolean editSysNumberRule(SysNumberRule sysNumberRule) throws Exception{
		int i=sysNumberRuleMapper.editSysNumberRule(sysNumberRule);
		return i>0;
	}
	
	/**
	 * 删除单据编号规则
	 * @param numberruleid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public boolean deleteSysNumberRule(String numberruleid) throws Exception{
		int i=sysNumberRuleMapper.deleteSysNumberRule(numberruleid);
		return i>0;
	}
	
	/**
	 * 获取sysNumber预览效果
	 * @param numberid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-14
	 */
	public String getSysNumberPreview(String numberid)throws Exception{
		String sysNumberPreviewRet="",sSubtype=null,sMiddle="";	//单据编号预览效果sysNumberPreviewRet
		List<SysNumberRule> sysNumberRuleList=new ArrayList<SysNumberRule>();
		sysNumberRuleList=sysNumberRuleMapper.getSysNumberRuleList(numberid);
		if(0 == sysNumberRuleList.size()){
			sysNumberPreviewRet=null;
		}
		else{
			//获取单据编号规则的值，并生成单据编号预览效果
			for(int i=0;i<sysNumberRuleList.size();i++){
				String sSysNumberPreview[]= new String[sysNumberRuleList.size()];
				String sVal=sysNumberRuleList.get(i).getVal();
				String sPrefix=sysNumberRuleList.get(i).getPrefix();
				String sSuffix=sysNumberRuleList.get(i).getSuffix();
				int iSubstart=sysNumberRuleList.get(i).getSubstart();
				sSubtype=sysNumberRuleList.get(i).getSubtype();
				String sCover=sysNumberRuleList.get(i).getCover();
				String sLength=String.valueOf(sysNumberRuleList.get(i).getLength());
				//为固定值时，获取全值
				if("1".equals(sysNumberRuleList.get(i).getColtype())){
					sSysNumberPreview[i]=sPrefix+sVal.substring(iSubstart)+sSuffix;
				}
				else{
					if("null".equals(sLength) || "".equals(sLength)){
						sSysNumberPreview[i]=sPrefix+sSuffix;
					}
					else{
						int iLength = sysNumberRuleList.get(i).getLength();
						for(int j=0;j<iLength;j++){
							sMiddle="*"+sMiddle;
						}
						sSysNumberPreview[i]=sPrefix+sMiddle+sSuffix;
					}
				}
				sysNumberPreviewRet=sysNumberPreviewRet+sSysNumberPreview[i];
			}
		}
		
		return sysNumberPreviewRet;
	}
	
	/**
	 * 提供自动生成方法
	 * 以map形式返回结果<br/>
	 * map返回结果格式:<br/>
	 * {'sysNumberCode':billno数值,'autoCreate':'1是或者2否','modifyFlag':'1允许或者2不允许'}<br/>
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-17
	 */
	public Map setAutoCreateSysNumbderForeign(Object obj,String tablename)throws Exception{
		String billno = "";
		Map retMap = new HashMap();
		SysNumber sysNumber=sysNumberMapper.getSysNumberAutoCreate(tablename);//单据编号详情
		if(null != sysNumber){//是否存在编号
			if(null != obj){
				String mapVal="",sSerial="",sZero="";
				//对象转化为map	
				Map map1 = BeanUtils.describe(obj);
				if("1".equals(sysNumber.getAutocreate())){
					//获取单据编号流水详情
					SysNumber sysNumberInfo=sysNumberMapper.getSysNumberSerail(sysNumber.getNumberid());
					//流水，是否为第一次自动生成编号
					if(null == sysNumberInfo.getAfterserialstart()){
						for(int l=0;l<sysNumberInfo.getSeriallength()-String.valueOf(sysNumberInfo.getSerialstart()).length();l++){
							sZero="0"+sZero;
						}
					}
					else{
						for(int l=0;l<sysNumberInfo.getSeriallength()-String.valueOf(sysNumberInfo.getAfterserialstart()+sysNumberInfo.getSerialstep()).length();l++){
							sZero="0"+sZero;
						}
					}
					boolean isSerialFlag = false;
					List<SysNumberRule> sysNumberRuleList = sysNumberRuleMapper.getSysNumberRuleList(sysNumber.getNumberid());
					//获取单据编号规则的值，并生成单据编号Billno
					for(SysNumberRule sysNumberRule : sysNumberRuleList){
						//判断是否流水依据,“是”则判断流水号依据字段值是否相同，是否以该数据作为流水依据:1是0否。字段为时间，且为流水依据时，添加时间为调用接口的系统当前时间
						if("1".equals(sysNumberRule.getState())){
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");//系统当前时间
							String systime = sdf.format(new Date());
							map1.put(sysNumberRule.getVal(),systime);
							String dateStr =getNumberRule(systime,sysNumberRule);
							//判断流水依据字段值是否相同，相同则流水号持续新增
							if(dateStr.equals(sysNumber.getTestvalue())){
								isSerialFlag = true;
							}
							else{//判断流水依据字段值是否相同，不同,则更新流水号依据字段值，当前流水号为当前流水号初始值
								sysNumber.setTestvalue(dateStr);
							}
						}
						else{//判断是否流水依据,不是则持续新增,流水依据字段值为空
							isSerialFlag = true;
							sysNumber.setTestvalue(null);
						}
						//判断编号规则字段类型：1固定值2字段3系统日期
						if("1".equals(sysNumberRule.getColtype())){
							billno += sysNumberRule.getPrefix()+sysNumberRule.getVal()+sysNumberRule.getSuffix();
							
						}else if("2".equals(sysNumberRule.getColtype())){
							if(map1.containsKey(sysNumberRule.getVal()) && null != map1.get(sysNumberRule.getVal())){
								mapVal = map1.get(sysNumberRule.getVal()).toString();
								billno += getNumberRule(mapVal,sysNumberRule);
							}
						}else if("3".equals(sysNumberRule.getColtype())){
							mapVal = map1.get(sysNumberRule.getVal()).toString();
							billno += getNumberRule(mapVal,sysNumberRule);
						}
					}
					if(isSerialFlag){
						if(null == sysNumberInfo.getAfterserialstart()){
							sSerial=sZero+String.valueOf(sysNumberInfo.getSerialstart());
							if(sSerial.length() <= sysNumberInfo.getSeriallength()){
								sysNumber.setAfterserialstart(sysNumberInfo.getSerialstart());
							}
							else{
								sSerial = sysNumberInfo.getSerialnumber();
								retMap.put("spill", "spill");
							}
						}
						else{
							sSerial=sZero+String.valueOf(sysNumberInfo.getAfterserialstart()+sysNumberInfo.getSerialstep());
							if(sSerial.length() <= sysNumberInfo.getSeriallength()){
								sysNumber.setAfterserialstart(sysNumberInfo.getAfterserialstart()+sysNumberInfo.getSerialstep());
							}
							else{
								sSerial = sysNumberInfo.getSerialnumber();
								retMap.put("spill", "spill");
							}
						}
					}
					else{
						sSerial=sZero+String.valueOf(sysNumberInfo.getSerialstart());
						if(sSerial.length() <= sysNumberInfo.getSeriallength()){
							sysNumber.setAfterserialstart(sysNumberInfo.getSerialstart());
						}
						else{
							sSerial = sysNumberInfo.getSerialnumber();
							retMap.put("spill", "spill");
						}
					}
					//当前流水号赋值
					sysNumber.setSerialnumber(sSerial);
					int iEditSerial= sysNumberMapper.editSysNumber(sysNumber);//更新当前流水依据 
					if(iEditSerial > 0 ){
						billno += sSerial;
					}
				}
				else{
					billno = null;
				}
			}
			retMap.put("autoCreate", sysNumber.getAutocreate());
			retMap.put("modifyFlag", sysNumber.getModifyflag());
		}
		else{
			retMap.put("autoCreate", "2");
			retMap.put("modifyFlag", "1");
			billno = null;
			retMap.put("autoCreate", "2");
			retMap.put("modifyFlag", "1");
		}
		retMap.put("billno", billno);
		return retMap;
	}
	
	/**
	 * 根据表名tablename获取是否允许修改(接口)
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-2-22
	 */
	public String isModifyFlagForeign(String tablename)throws Exception{
		SysNumber sysNumberBill=sysNumberMapper.getSysNumberAutoCreate(tablename);
		if(null != sysNumberBill){
			return sysNumberBill.getModifyflag();
		}
		return null;
	}
	
	/**
	 * 根据单据规则截取生成字符串
	 * @param str
	 * @param sysNumberRule
	 * @return
	 * @author panxiaoxiao 
	 * @date 2013-1-19
	 */
	public String getNumberRule(String str,SysNumberRule sysNumberRule){
		String retStr="",sCover="";
		if("1".equals(sysNumberRule.getSubtype())){//截取方向1:从前向后2从后向前
			//判断长度是否足够
			if(str.length()>=sysNumberRule.getLength()+sysNumberRule.getSubstart()){
				retStr= str.substring(sysNumberRule.getSubstart(), sysNumberRule.getLength()+sysNumberRule.getSubstart());
			}else{
				//长度不够 需要补位
				retStr= str.substring(sysNumberRule.getSubstart(), str.length());
			}
		}else{
			//当截取位置大于字符串总长度时
			if(sysNumberRule.getSubstart()>str.length()){
				if(str.length()>=sysNumberRule.getLength()){
					retStr= str.substring(str.length()-sysNumberRule.getLength(), str.length());
				}else{
					retStr= str.substring(0, str.length());
				}
			}else{
				//截取开始位置 大于需要长度时
				if(sysNumberRule.getSubstart()>=sysNumberRule.getLength()){
					retStr=str.substring(sysNumberRule.getSubstart()-sysNumberRule.getLength(), str.length());
				}else{
					//截取开始位置 小于需要长度时 
					//表示需要补位
					retStr=str.substring(0, sysNumberRule.getSubstart());
				}
			}
		}
		//截取后长度不够 补位
		if(retStr.length()<sysNumberRule.getLength()){
			String cover = "";
			for(int i=0;i<sysNumberRule.getLength()-retStr.length();i++){
				cover += sysNumberRule.getCover();
			}
			retStr += cover;
		}
		//补上前缀后缀
		retStr = sysNumberRule.getPrefix()+retStr+sysNumberRule.getSuffix();
		return retStr;
	}
}

