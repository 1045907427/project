/**
 * @(#)SysNumberAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-26 panxiaoxiao 创建版本
 */
package com.hd.agent.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.SysNumber;
import com.hd.agent.system.model.SysNumberRule;
import com.hd.agent.system.model.TableColumn;
import com.hd.agent.system.service.ISysCodeService;
import com.hd.agent.system.service.ISysNumberService;

/**
 * 单据编号
 * 对映struts-system配置
 * @author panxiaoxiao
 */
public class SysNumberAction extends BaseAction{
	
	private SysNumber sysNumber;
	private SysNumberRule sysNumberRule;
	private ISysNumberService sysNumberService;
	private ISysCodeService sysCodeService;
	private TableColumn tableColumn;
	
	public TableColumn getTableColumn() {
		return tableColumn;
	}
	public void setTableColumn(TableColumn tableColumn) {
		this.tableColumn = tableColumn;
	}
	public ISysCodeService getSysCodeService() {
		return sysCodeService;
	}
	public void setSysCodeService(ISysCodeService sysCodeService) {
		this.sysCodeService = sysCodeService;
	}
	public SysNumber getSysNumber() {
		return sysNumber;
	}
	public void setSysNumber(SysNumber sysNumber) {
		this.sysNumber = sysNumber;
	}
	public ISysNumberService getSysNumberService() {
		return sysNumberService;
	}
	public void setSysNumberService(ISysNumberService sysNumberService) {
		this.sysNumberService = sysNumberService;
	}
	
	public SysNumberRule getSysNumberRule() {
		return sysNumberRule;
	}
	public void setSysNumberRule(SysNumberRule sysNumberRule) {
		this.sysNumberRule = sysNumberRule;
	}
	/**
	 * 显示单据名称列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-28
	 */
	public String showSysNumberBillNameList()throws Exception{
		String type=request.getParameter("type");
		List billNameList=sysCodeService.showSysCodeListByType(type);
		addJSONArray(billNameList);
		return "success";
	}
	/**
	 * 显示单据列表页
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public String showSysNumberListPage()throws Exception{
		return "success";
	}
	/**
	 * 显示单据编码列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public String showSysNumberListByBcode() throws Exception{
		Map mapMap=request.getParameterMap();
		Map map=CommonUtils.changeMap(mapMap);
		pageMap.setCondition(map);
		PageData pageData=sysNumberService.showSysNumberPageListByBcode(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	
	/**
	 * 根据单据名称类型获取自动生成值去重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-3-23
	 */
	public String getAutoCreateByBillCode()throws Exception{
		boolean flag =true;
		Map retMap = new HashMap();
		String billcode=request.getParameter("billcode");
		List<SysNumber> list = sysNumberService.getAutoCreateByBillCode(billcode);
		if(list.size() == 1){
			retMap.put("autoCreate", list.get(0).getAutocreate().toString());
		}
		else{
			flag = false;
		}
		retMap.put("flag", flag);
		addJSONObject(retMap);
		return "success";
	}
	
	/**
	 * 添加单据编号
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	@UserOperateLog(key="sysNumber",type=2)
	public String addSysNumber() throws Exception{
		String inserted=request.getParameter("inserted");
		JSONArray jsonArray = JSONArray.fromObject(inserted);
		JSONObject json = jsonArray.getJSONObject(0);
		SysNumber sysNumber= (SysNumber) JSONObject.toBean(json, SysNumber.class);
 		boolean flag=sysNumberService.addSysNumber(sysNumber);
		addJSONObject("flag",flag);
		return "success";
	}
	
	/**
	 * 修改单据编号
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	@UserOperateLog(key="sysNumber",type=3)
	public String editSysNumber() throws Exception{
		String updated=request.getParameter("updated");
		JSONArray jsonArray = JSONArray.fromObject(updated);
		JSONObject json = jsonArray.getJSONObject(0);
		SysNumber sysNumber= (SysNumber) JSONObject.toBean(json, SysNumber.class);
		boolean flag=sysNumberService.editSysNumber(sysNumber);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 编辑流水号信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-3
	 */
	public String editSerial() throws Exception{
		String strSuf="",serialnumber = "";
		Map retMap = new HashMap();
		String oldPreview=request.getParameter("preview");//原先预览效果 
		for(int i=0;i<sysNumber.getSeriallength()-sysNumber.getSerialstart().toString().length();i++){
			strSuf +="0";
		}
		serialnumber=strSuf+sysNumber.getSerialstart();//serialnumber当前流水号
		sysNumber.setSerialnumber(serialnumber);
		if(!"".equals(oldPreview) && null != oldPreview){
			String strPre = "";
			if(oldPreview.indexOf("0") != -1){
				strPre=oldPreview.substring(0, oldPreview.indexOf("0"));//流水号前编号字符串
			}
			sysNumber.setPreview(strPre+serialnumber);
		}
		else{
			sysNumber.setPreview("");
		}
		sysNumber.setAfterserialstart(null);
		boolean flag=sysNumberService.editSysNumber(sysNumber);
		retMap.put("serialnumber", serialnumber);
		retMap.put("flag",flag);
		addJSONObject(retMap);
		return "success";
	}
	
	/**
	 * 停用单据编号
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public String disableSysNumber() throws Exception{
		String numberid=request.getParameter("numberid");
		boolean flag=sysNumberService.disableSysNumber(numberid);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 启用单据编号
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-26
	 */
	public String enableSysNumber() throws Exception{
		String numberid=request.getParameter("numberid");
		String tablename = request.getParameter("tablename");
		String type = request.getParameter("type");
		//boolean flag=sysNumberService.enableSysNumber(numberid,tablename,type);
		boolean flag = sysNumberService.enableSysNumber(numberid);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 删除单据编号
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-30
	 */
	public String deleteSysNumber() throws Exception{
		String numberid=request.getParameter("numberid");
		boolean flag=sysNumberService.deleteSysNumber(numberid);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 根据代码类型显示添加单据名称页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-31
	 */
	public String showSysCodeAddPageByType() throws Exception{	
		String type=request.getParameter("type");
		String typename=request.getParameter("typename");
		request.setAttribute("type", type);
		request.setAttribute("typename", typename);
		return "success";
	}
	
	/**
	 * 根据代码类型显示编辑单据名称页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-31
	 */
	public String showSysCodeEditPageByType() throws Exception{
		String code=request.getParameter("code");
		String type=request.getParameter("type");
		SysCode sysCode=sysCodeService.showSysCodeInfo(code,type);
		request.setAttribute("sysCode", sysCode);
		return "success";
	}
	
	/**
	 * 删除单据名称
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2012-12-31
	 */
	public String deleteBillName() throws Exception{
		String code=request.getParameter("code");
		String type=request.getParameter("type");
		boolean flag=sysCodeService.deleteSCBillName(code,type);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 批量修改单据编号是否自动生成
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-15
	 */
	public String editSysNumbersAutoCreate()throws Exception{
		String billcode = request.getParameter("billcode");
		String autoCreate = request.getParameter("autocreate");
		boolean flag=sysNumberService.editSysNumbersAutoCreate(billcode,autoCreate);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 批量修改单据编号是否允许修改
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-16
	 */
	public String editSysNumbersModifyFlag()throws Exception{
		String numberid = request.getParameter("numberid");
		String modifyflag = request.getParameter("modifyflag");
		boolean flag=sysNumberService.editSysNumbersModifyFlag(numberid,modifyflag);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/**
	 * 禁用所有的单据编号
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date 2013-1-16
	 */
	public String disableAllSysNumbers() throws Exception{
		Map mapMap=request.getParameterMap();
		Map map=CommonUtils.changeMap(mapMap);
		pageMap.setCondition(map);
		boolean flag=sysNumberService.disableAllSysNumbers(pageMap);
		addJSONObject("flag", flag);
		return "success";
	}
	
	/*--------------------------------单据编码设置------------------------------------------------------------------*/
	
	/**
	 * 显示单据编号页面
	 */
	public String sysNumberPage()throws Exception{
		return SUCCESS;
	}
	
	/**
	 * 获取单据编号列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public String getSysNumberList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = sysNumberService.getSysNumberList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示单据编号新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 1, 2013
	 */
	public String sysNumberAddPage()throws Exception{
		return SUCCESS;
	}
	
	/*--------------------------------单据编码规则------------------------------------------------------------------*/
	
	/**
	 * 显示单据编码规则新增页面
	 */
	public String sysNumberRuleAdd()throws Exception{
		String numberid = request.getParameter("numberid");
		String tablename = request.getParameter("tablename");
		request.setAttribute("tablename", tablename);
		request.setAttribute("numberid", numberid);
		return SUCCESS;
	}
	
	/**
	 * 显示单据编码规则修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public String sysNumberRuleEdit()throws Exception{
		String tablename = request.getParameter("tablename");
		request.setAttribute("tablename",tablename);
		return SUCCESS;
	}
	
	/**
	 * 将单据编码规则添加到列表中，虚拟新增,
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 6, 2013
	 */
	public String addSysNumberRuleToList()throws Exception{
		String tablename = request.getParameter("tablename");
		String sysNumberRuleRows = request.getParameter("sysNumberRuleRows");
		List<SysNumberRule> rowslist = new ArrayList<SysNumberRule>();
		List<SysNumberRule> list = new ArrayList<SysNumberRule>();
		Map map = new HashMap();
		if(null != sysNumberRuleRows){
			//把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(sysNumberRuleRows);
			rowslist = JSONArray.toList(json, SysNumberRule.class);
			//TODO 下面就可以根据转换后的对象进行相应的操作了
			if(rowslist.size() != 0){
				for(SysNumberRule sysNumberRule : rowslist){
					if(StringUtils.isNotEmpty(tablename) && StringUtils.isNotEmpty(sysNumberRule.getVal())){
						TableColumn tableColumn = getDictTableColumnBy(tablename,sysNumberRule.getVal());
						if(null != tableColumn){
							sysNumberRule.setValName(tableColumn.getColchnname());
						}else{
							sysNumberRule.setValName(sysNumberRule.getVal());
						}
					}
					list.add(sysNumberRule);
				}
			}
		}
		if(sysNumberRule != null){
			if(StringUtils.isNotEmpty(tablename) && StringUtils.isNotEmpty(sysNumberRule.getVal())){
				TableColumn tableColumn = getDictTableColumnBy(tablename,sysNumberRule.getVal());
				if(null != tableColumn){
					sysNumberRule.setValName(tableColumn.getColchnname());
				}else{
					sysNumberRule.setValName(sysNumberRule.getVal());
				}
			}
			list.add(sysNumberRule);
		}
		map.put("list", list);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 将单据编码规则修改到列表中，虚拟修改,
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public String editSysNumberRuleToList()throws Exception{
		String tablename = request.getParameter("tablename");
		List<SysNumberRule> list = new ArrayList<SysNumberRule>();
		Map map = new HashMap();
		if(null != sysNumberRule){
			if(StringUtils.isNotEmpty(tablename) && StringUtils.isNotEmpty(sysNumberRule.getVal())){
				TableColumn tableColumn = getDictTableColumnBy(tablename,sysNumberRule.getVal());
				if(null != tableColumn){
					sysNumberRule.setValName(tableColumn.getColchnname());
				}else{
					sysNumberRule.setValName(sysNumberRule.getVal());
				}
			}
			list.add(sysNumberRule);
		}
		map.put("sysNumberRule", sysNumberRule);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 获取预览效果
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 7, 2013
	 */
	public String getPreViewSysNumber()throws Exception{
		String preView = request.getParameter("preView");
		List<SysNumberRule> preViewlist = new ArrayList<SysNumberRule>();
		String serial = "",preViewNo = "";
		Map map = new HashMap();
		//获取流水号
		int sLength = sysNumber.getSeriallength();//流水号长度
		int sStep = sysNumber.getSerialstep();//流水号步长
		int sStart = sysNumber.getSerialstart();//流水号起始值
		for(int i=0;i<sLength-Integer.toString(sStart).length();i++){
			serial +="0";
		}
		serial = serial + Integer.toString(sStart);
		if(null != preView){
			//把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(preView);
			preViewlist = JSONArray.toList(json, SysNumberRule.class);
			for(SysNumberRule sysNumberRule:preViewlist){
				//获取长度，且将值用*代替
				String valStr = "";
				if("1".equals(sysNumberRule.getColtype())){//固定值
					preViewNo = sysNumberRule.getPrefix() + sysNumberRule.getVal() + sysNumberRule.getSuffix();
				}
				else{
					if(sysNumberRule.getLength() != null){
						for(int j=0;j<sysNumberRule.getLength();j++){
							valStr += "*";
						}
					}
					preViewNo += sysNumberRule.getPrefix() + valStr + sysNumberRule.getSuffix();
				}
			}
		}
		preViewNo = preViewNo + serial;
		map.put("preViewNo", preViewNo);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 新增单据编号设置
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 7, 2013
	 */
	public String addSysNumberBill()throws Exception{
		String sysNumberRuleRows = request.getParameter("sysNumberRuleRows");
		List<SysNumberRule> sysNumberRulelist = new ArrayList<SysNumberRule>();
		Map map = new HashMap();
		if(null != sysNumberRuleRows){
			//把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(sysNumberRuleRows);
			sysNumberRulelist = JSONArray.toList(json, SysNumberRule.class);
			sysNumber.setState("2");
			boolean flag = sysNumberService.addSysNumber(sysNumber, sysNumberRulelist);
			map.put("numberid", sysNumber.getNumberid());
			map.put("flag", flag);
			addJSONObject(map);
		}
		return SUCCESS;
	}
	
	/**
	 * 修改单据编号设置
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 10, 2013
	 */
	public String editSysNumberBill()throws Exception{
		String sysNumberRuleRows = request.getParameter("sysNumberRuleRows");
		String delStr = request.getParameter("delStr");
		List<SysNumberRule> ruleList = new ArrayList<SysNumberRule>();
		Map map = new HashMap();
		if(null != sysNumberRuleRows){
			//把json字符串转换成对象
			JSONArray json = JSONArray.fromObject(sysNumberRuleRows);
			ruleList = JSONArray.toList(json, SysNumberRule.class);
		}
		boolean flag = sysNumberService.editSysNumber(sysNumber, ruleList, delStr);
		map.put("numberid", sysNumber.getNumberid());
		map.put("flag", flag);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 显示单据编号详情
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public String sysNumberViewPage()throws Exception{
		String numberid = request.getParameter("numberid");
		SysNumber sysNumber = sysNumberService.getSysNumberInfo(numberid);
		if(null != sysNumber){
			if(StringUtils.isNotEmpty(sysNumber.getTestvalue()) && StringUtils.isNotEmpty(sysNumber.getTablename())){
				TableColumn tableColumn = super.getDictTableColumnBy(sysNumber.getTablename(), sysNumber.getTestvalue());
				if(null != tableColumn){
					request.setAttribute("testValueName", tableColumn.getColchnname());
				}
				else{
					request.setAttribute("testValueName", sysNumber.getTestvalue());
				}
			}
			request.setAttribute("numberid", numberid);
			request.setAttribute("sysNumber", sysNumber);
		}
		return SUCCESS;
	}
	
	/**
	 * 根据单据编号获取单据编号规则列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public String getSysNumberRuleList()throws Exception{
		String numberid = request.getParameter("numberid");
		SysNumber sysNumber = sysNumberService.getSysNumberInfo(numberid);
		List<SysNumberRule> list = sysNumberService.getSysNumberRuleList(numberid);
		for(SysNumberRule sysNumberRule:list){
			if(null != sysNumber){
				if(StringUtils.isNotEmpty(sysNumberRule.getVal()) && StringUtils.isNotEmpty(sysNumber.getTablename())){
					TableColumn tableColumn = getDictTableColumnBy(sysNumber.getTablename(),sysNumberRule.getVal());
					if(null != tableColumn){
						sysNumberRule.setValName(tableColumn.getColchnname());
					}else{
						sysNumberRule.setValName(sysNumberRule.getVal());
					}
				}
			}
		}
		request.setAttribute("sysNumber", sysNumber);
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 显示单据编码修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 8, 2013
	 */
	public String sysNumberEditPage()throws Exception{
		String numberid = request.getParameter("numberid");
		SysNumber sysNumber = sysNumberService.getSysNumberInfo(numberid);
		if(null != sysNumber){
			if(StringUtils.isNotEmpty(sysNumber.getTestvalue()) && StringUtils.isNotEmpty(sysNumber.getTablename())){
				TableColumn tableColumn = super.getDictTableColumnBy(sysNumber.getTablename(), sysNumber.getTestvalue());
				if(null != tableColumn){
					request.setAttribute("testValueName", tableColumn.getColchnname());
				}
				else{
					request.setAttribute("testValueName", sysNumber.getTestvalue());
				}
			}
			request.setAttribute("sysNumber", sysNumber);
		}
		return SUCCESS;
	}

    /**
     * 显示单据编码复制页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-15
     */
    public String sysNumberCopyPage()throws Exception{
        String numberid = request.getParameter("numberid");
        SysNumber sysNumber = sysNumberService.getSysNumberInfo(numberid);
        request.setAttribute("sysNumber", sysNumber);
        return SUCCESS;
    }

	/**
	 * 验证固定值是否重复
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 4, 2013
	 */
	public String isRepeatFixedVal()throws Exception{
		String val = request.getParameter("val");
		boolean flag = sysNumberService.isRepeatFixedVal(val);
		addJSONObject("flag", flag);
		return SUCCESS;
	}

    /**
     * 判断单据编码是否重复(true重复false不重复)
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-13
     */
    public String isRepeatBillCode()throws Exception{
        String billcode = request.getParameter("billcode");
        boolean flag = sysNumberService.isRepeatBillCode(billcode);
        addJSONObject("flag",flag);
        return SUCCESS;
    }

    /**
     * 判断单据名称是否重复(true重复false不重复)
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-10-14
     */
    public String isRepeatBillName()throws Exception{
        String billname = request.getParameter("billname");
        boolean flag = sysNumberService.isRepeatBillName(billname);
        addJSONObject("flag",flag);
        return SUCCESS;
    }
}

