/**
 * @(#)ExcelAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Mar 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.common.action;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.service.IExcelService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.TableColumn;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;


/**
 * 
 * 
 * @author zhengziyong
 */
public class ExcelAction extends BaseAction {
	
	private IExcelService excelService;
	public IExcelService getExcelService() {
		return excelService;
	}
	public void setExcelService(IExcelService excelService) {
		this.excelService = excelService;
	}
	
	public String importPage() throws Exception{
		String version = request.getParameter("version");
		request.setAttribute("version", version);
		return SUCCESS;
	}
	
	public String exportPage() throws Exception{
		String version = request.getParameter("version");
		request.setAttribute("version", version);
		return SUCCESS;
	}
	
	/**
	 * 显示导入成功后显示信息
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Dec 28, 2013
	 */
	public String returnExcelMsgPage()throws Exception{
		//String data = request.getParameter("data");
		//JSONObject jsonObj = JSONObject.fromObject(data);
		//Map<String, Object> map = (Map)jsonObj;
		//request.setAttribute("map", map);
		return SUCCESS;
	}
	public String importExcel() throws Exception{
		Map<String, Object> retmap = new HashMap<String, Object>();
		try{
			String clazz = request.getParameter("clazz");
			String meth = request.getParameter("method");
			String tn = request.getParameter("tn");
			String module = request.getParameter("module");
			String pojo = request.getParameter("pojo");
			String majorkey = request.getParameter("majorkey");
			String repeatVal = "",closeVal = "",failStr = "";
			List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
			List<String> paramList2 = desToColumn(paramList, tn); //将描述名改为字段名并判断数据权限
			List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
			if(list.size() != 0){
				Object object = SpringContextUtils.getBean(clazz);
				Class entity = Class.forName("com.hd.agent." + module + ".model." +pojo);
				Method[] methods = object.getClass().getMethods();
				Method method = null;
				for(Method m : methods){
					if(m.getName().equals(meth)){
						method = m;
					}
				}
				//返回值
				List resultList = importMapToList(list, entity, tn);
				retmap = excelService.comInsertMethod(object, resultList, method);
			}else{
				retmap.put("excelempty", true);
			}
			addJSONObject(retmap);
		}
		catch (Exception e) {
			e.printStackTrace();
			addJSONObject("error", true);
		}
		return SUCCESS;
	}

	/**
	 * 导入多表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 18, 2013
	 */
	public String importMoreExcel() throws Exception{
		Map<String,Object> retMap = new HashMap<String,Object>();
		try{
			Map<String, String> map3 = CommonUtils.changeMap(request.getParameterMap());
			//version：显示版本1：不显示单选框，2：简单版or合同版，3：excel格式或txt格式
			//versiontype：导出版本:简单版or合同版
			String version = "",versiontype = "";
			if(map3.containsKey("version")){
				version = map3.get("version").toString();
			}
			if(map3.containsKey("versiontype")){
				versiontype = map3.get("versiontype").toString();
			}
			if(!"1".equals(version)){
				if("1".equals(versiontype)){//简单版
					if(map3.containsKey("shortcutname")){
						String shortcutname = map3.get("shortcutname").toString();
						if("goods".equals(shortcutname)){//商品快捷导入
							retMap = importShortcutGoodsExcel(map3);
						}
						else if("customer".equals(shortcutname)){//客户快捷导入
							retMap = importShortcutCustomerExcel(map3);
						}
						else if("supplier".equals(shortcutname)){//供应商快捷导入
							retMap = importShortcutSupplierExcel(map3);
						}
					}
				}
				else if("2".equals(versiontype)){//复杂版
					retMap = importMoreComplexType(map3);
				}
			}
			else{
				retMap = importMoreComplexType(map3);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			retMap.put("error", true);
		}
		addJSONObject(retMap);
		return SUCCESS;
	}
	
	/**
	 * 商品快捷导入
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 27, 2013
	 */
	@UserOperateLog(key="GoodsInfo",type=2,value="商品简化版导入")
	public Map<String,Object> importShortcutGoodsExcel(Map<String, String> map)throws Exception{
		String clazz = (String)map.get("clazz");
		String meth = "addShortcutGoodsExcel";
		String module = (String)map.get("module");
		String pojo = "GoodsInfo";
		Object object2 = SpringContextUtils.getBean(clazz);
		Class entity = Class.forName("com.hd.agent." + module + ".model." +pojo);
		Method[] methods = object2.getClass().getMethods();
		Method method = null;
		for(Method m : methods){
			if(m.getName().equals(meth)){
				method = m;
			}
		}
		
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("商品编码".equals(str)){
				paramList2.add("id");
			}
			else if("商品名称".equals(str)){
				paramList2.add("name");
			}
			else if("规格型号".equals(str)){
				paramList2.add("model");
			}
			else if("助记符".equals(str)){
				paramList2.add("spell");
			}
			else if("条形码".equals(str)){
				paramList2.add("barcode");
			}
			else if("箱装条形码".equals(str)){
				paramList2.add("boxbarcode");
			}
			else if("商品货位".equals(str)){
				paramList2.add("itemno");
			}
			else if("商品分类".equals(str)){
				paramList2.add("defaultsortName");
			}
			else if("商品品牌".equals(str)){
				paramList2.add("brandName");
			}
			else if("所属部门".equals(str)){
				paramList2.add("deptname");
			}
			else if("默认供应商".equals(str)){
				paramList2.add("defaultsupplierName");
			}
			else if("第二供应商".equals(str)){
				paramList2.add("secondsuppliername");
			}
			else if("长度".equals(str)){
				paramList2.add("glength");
			}
			else if("高度".equals(str)){
				paramList2.add("ghight");
			}
			else if("宽度".equals(str)){
				paramList2.add("gwidth");
			}
			else if("箱重".equals(str)){
				paramList2.add("totalweight");
			}
			else if("箱体积".equals(str)){
				paramList2.add("totalvolume");
			}
			else if("主单位".equals(str)){
				paramList2.add("mainunitName");
			}
			else if("辅单位".equals(str)){
				paramList2.add("auxunitname");
			}
			else if("箱装量".equals(str)){
				paramList2.add("boxnum");
			}
			else if("商品类型".equals(str)){
				paramList2.add("goodstypeName");			
			}
			else if("默认仓库".equals(str)){
				paramList2.add("storageName");
			}
			else if("库位管理".equals(str)){
				paramList2.add("isstoragelocationname");
			}
			else if("保质期管理".equals(str)){
				paramList2.add("isshelflifename");
			}
			else if("保质期".equals(str)){
				paramList2.add("shelflifedetail");
			}
			else if("批次管理".equals(str)){
				paramList2.add("isbatchname");
			}
			else if("采购价".equals(str)){
				paramList2.add("highestbuyprice");
			}
			else if("最低销售价".equals(str)){
				paramList2.add("lowestsaleprice");
			}
			else if("基准销售价".equals(str)){
				paramList2.add("basesaleprice");
			}
			else if("最新采购价".equals(str)){
				paramList2.add("newbuyprice");
			}
			else if("最新销售价".equals(str)){
				paramList2.add("newsaleprice");
			}
			else if("默认税种".equals(str)){
				paramList2.add("defaulttaxtypeName");
			}
			else if("最小发货单位".equals(str)){
				paramList2.add("minimum");
			}
			else if(isExistPriceCodeName(str)){
				String code = getBaseSysCodeService().codenametocode(str, "price_list");
				paramList2.add("price"+code);
			}
			else if("备注".equals(str)){
				paramList2.add("remark");
			}
			else if("状态".equals(str)){
				paramList2.add("stateName");
			}else{
				paramList2.add("null");
			}
		}
		Map resultMap2 = new HashMap();
		if(paramList.size() == paramList2.size()){
			List result = new ArrayList();
			Map<String, Object> map2 = new HashMap<String, Object>();
			List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
			if(list.size() != 0){
				Map detialMap = new HashMap();
				for(Map<String, Object> map4 : list){
					Object object = entity.newInstance();
					Field[] fields = entity.getDeclaredFields();
					//获取的导入数据格式转换
					DRCastTo(map4,fields);
					//BeanUtils.populate(object, map4);
					PropertyUtils.copyProperties(object, map4);
					result.add(object);
				}
				if(result.size() != 0){
					resultMap2 = excelService.insertSalesOrder(object2, result, method);
				}
			}else{
				resultMap2.put("excelempty", true);
			}
		}
		else{
			resultMap2.put("versionerror", true);
		}
		return resultMap2;
	}
	
	/**
	 * 客户快捷导入
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 28, 2013
	 */
	@UserOperateLog(key="Customer",type=2,value="客户简化版导入")
	public Map<String,Object> importShortcutCustomerExcel(Map<String, String> map)throws Exception{
		String clazz = (String)map.get("clazz");
		String meth = "addShortcutCustomerExcel";
		String module = (String)map.get("module");
		String pojo = "Customer";
		Object object2 = SpringContextUtils.getBean(clazz);
		Class entity = Class.forName("com.hd.agent." + module + ".model." +pojo);
		Method[] methods = object2.getClass().getMethods();
		Method method = null;
		for(Method m : methods){
			if(m.getName().equals(meth)){
				method = m;
			}
		}
		
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("编码".equals(str)){
				paramList2.add("id");
			}
			else if("客户名称".equals(str)){
				paramList2.add("name");
			}
			else if("上级客户".equals(str)){
				paramList2.add("pname");
			}
			else if("是否总店".equals(str)){
				paramList2.add("islastname");
			}
			else if("助记符".equals(str)){
				paramList2.add("shortcode");
			}
			else if("简称".equals(str)){
				paramList2.add("shortname");
			}
			else if("税号".equals(str)){
				paramList2.add("taxno");
			}
			else if("开户银行".equals(str)){
				paramList2.add("bank");
			}
			else if("开户账号".equals(str)){
				paramList2.add("cardno");
			}
			else if("开户名".equals(str)){
				paramList2.add("caraccount");
			}
			else if("注册资金".equals(str)){
				paramList2.add("fund");
			}
			else if("门店面积m^2".equals(str)){
				paramList2.add("storearea");
			}
			else if("联系人".equals(str)){
				paramList2.add("contactname");
			}
			else if("联系人电话".equals(str)){
				paramList2.add("mobile");
			}
			else if("收款人".equals(str)){
				paramList2.add("payeename");
			}
			else if("传真".equals(str)){
				paramList2.add("faxno");
			}
			else if("公司属性".equals(str)){
				paramList2.add("naturename");
			}
			else if("是否连锁".equals(str)){
				paramList2.add("ischainname");
			}
			else if("县级市".equals(str)){
				paramList2.add("countylevel");
			}
			else if("乡镇".equals(str)){
				paramList2.add("villagetown");
			}
			else if("详细地址".equals(str)){
				paramList2.add("address");
			}
			else if("结算方式".equals(str)){
				paramList2.add("settletypename");
			}
			else if("结算日".equals(str)){
				paramList2.add("settleday");
			}
			else if("支付方式".equals(str)){
				paramList2.add("paytypename");
			}
			else if("现款".equals(str)){
				paramList2.add("iscashname");
			}
			else if("账期".equals(str)){
				paramList2.add("islongtermname");
			}
			else if("超账期宽限天数".equals(str)){
				paramList2.add("overgracedate");
			}
			else if("信用额度".equals(str)){
				paramList2.add("credit");
			}
			else if("对账日期".equals(str)){
				paramList2.add("reconciliationdate");
			}
			else if("开票日期".equals(str)){
				paramList2.add("billingdate");
			}
			else if("到款日期".equals(str)){
				paramList2.add("arrivalamountdate");
			}
			else if("票种".equals(str)){
				paramList2.add("tickettypename");
			}
			else if("核销方式".equals(str)){
				paramList2.add("canceltypename");
			}
			else if("价格套".equals(str)){
				paramList2.add("pricesortname");
			}
			else if("促销分类".equals(str)){
				paramList2.add("promotionsortname");
			}
			else if("所属区域".equals(str)){
				paramList2.add("salesareaname");
			}
			else if("所属分类".equals(str)){
				paramList2.add("customersortname");			
			}
			else if("ABCD等级".equals(str)){
				paramList2.add("abclevelname");			
			}
			else if("默认销售部门".equals(str)){
				paramList2.add("salesdeptname");			
			}
			else if("客户业务员".equals(str)){
				paramList2.add("salesusername");			
			}
			else if("默认理货员".equals(str)){
				paramList2.add("tallyusername");
			}
			else if("默认内勤".equals(str)){
				paramList2.add("indoorstaffname");
			}
			else if("收款人".equals(str)){
				paramList2.add("payeename");
			}
			else if("对账人".equals(str)){
				paramList2.add("checker");
			}
			else if("对账人电话".equals(str)){
				paramList2.add("checkmobile");
			}
			else if("对账人邮箱".equals(str)){
				paramList2.add("checkemail");
			}
			else if("付款人".equals(str)){
				paramList2.add("payer");			
			}
			else if("付款人电话".equals(str)){
				paramList2.add("payermobile");			
			}
			else if("付款人邮箱".equals(str)){
				paramList2.add("payeremail");			
			}
			else if("店长".equals(str)){
				paramList2.add("shopmanager");			
			}
			else if("店长联系电话".equals(str)){
				paramList2.add("shopmanagermobile");			
			}
			else if("收货人".equals(str)){
				paramList2.add("gsreceipter");			
			}
			else if("收货人联系电话".equals(str)){
				paramList2.add("gsreceiptermobile");
			}
			else if("状态".equals(str)){
				paramList2.add("statename");
			}
			else if("录入人".equals(str)){
				paramList2.add("addusername");
			}
			else if("备注".equals(str)){
				paramList2.add("remark");
			}
			else if("法人代表".equals(str)){
				paramList2.add("person");
			}
			else if("法人代表电话".equals(str)){
				paramList2.add("personmobile");
			}
			else if("法人身份证号码".equals(str)){
				paramList2.add("personcard");
			}
			else if("客户开户日期".equals(str)){
				paramList2.add("setupdate");
			}
			else if("超账期控制".equals(str)){
				paramList2.add("overcontrolname");
			}
			else if("信用等级".equals(str)){
				paramList2.add("creditratingname");
			}
			else if("信用期限".equals(str)){
				paramList2.add("creditdate");
			}
			else if("目标销售".equals(str)){
				paramList2.add("targetsales");
			}
			else if("年返".equals(str)){
				paramList2.add("yearback");
			}
			else if("月返".equals(str)){
				paramList2.add("monthback");
			}
			else if("配送费".equals(str)){
				paramList2.add("dispatchingamount");
			}
			else if("六节一庆".equals(str)){
				paramList2.add("sixone");
			}else{
				paramList2.add("null");
			}
		}
		
		Map resultMap2 = new HashMap();
		if(paramList.size() == paramList2.size()){
			List result = new ArrayList();
			Map<String, Object> map2 = new HashMap<String, Object>();
			List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
			if(list.size() != 0){
				Map detialMap = new HashMap();
				for(Map<String, Object> map4 : list){
					Object object = entity.newInstance();
					Field[] fields = entity.getDeclaredFields();
					//获取的导入数据格式转换
					DRCastTo(map4,fields);
					//BeanUtils.populate(object, map4);
					PropertyUtils.copyProperties(object, map4);
					result.add(object);
				}
				if(result.size() != 0){
					resultMap2 = excelService.insertSalesOrder(object2, result, method);
				}
			}else{
				resultMap2.put("excelempty", true);
			}
		}
		else{
			resultMap2.put("versionerror", true);
		}
		return resultMap2;
	}
	
	/**
	 * 供应商快捷导入
	 * @param map
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 28, 2013
	 */
	@UserOperateLog(key="BuySupplier",type=2,value="供应商简化版导入")
	public Map<String,Object> importShortcutSupplierExcel(Map<String, String> map)throws Exception{
		String clazz = (String)map.get("clazz");
		String meth = "addShortcutSupplierExcel";
		String module = (String)map.get("module");
		String pojo = "BuySupplier";
		Object object2 = SpringContextUtils.getBean(clazz);
		Class entity = Class.forName("com.hd.agent." + module + ".model." +pojo);
		Method[] methods = object2.getClass().getMethods();
		Method method = null;
		for(Method m : methods){
			if(m.getName().equals(meth)){
				method = m;
			}
		}
		
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("编码".equals(str)){
				paramList2.add("id");
			}
			else if("供应商名称".equals(str)){
				paramList2.add("name");
			}
			else if("助记符".equals(str)){
				paramList2.add("spell");
			}
			else if("简称".equals(str)){
				paramList2.add("shortname");
			}
			else if("税号".equals(str)){
				paramList2.add("taxno");
			}
			else if("开户银行".equals(str)){
				paramList2.add("bank");
			}
			else if("开户账号".equals(str)){
				paramList2.add("cardno");
			}
			else if("供应商电话".equals(str)){
				paramList2.add("telphone");
			}
			else if("供应商传真".equals(str)){
				paramList2.add("faxno");
			}
			else if("详细地址".equals(str)){
				paramList2.add("address");
			}
			else if("所属区域".equals(str)){
				paramList2.add("buyareaname");
			}
			else if("所属分类".equals(str)){
				paramList2.add("suppliersortname");
			}
			else if("所属部门".equals(str)){
				paramList2.add("filialename");
			}
			else if("采购部门".equals(str)){
				paramList2.add("buydeptname");
			}
			else if("采购员".equals(str)){
				paramList2.add("buyusername");
			}
			else if("采购员联系电话".equals(str)){
				paramList2.add("buyusermobile");
			}
			else if("结算方式".equals(str)){
				paramList2.add("settletypename");
			}
			else if("核销方式".equals(str)){
				paramList2.add("canceltypename");
			}
			else if("对应仓库".equals(str)){
				paramList2.add("storagename");
			}
			else if("订单追加".equals(str)){
				paramList2.add("orderappendname");
			}
			else if("状态".equals(str)){
				paramList2.add("statename");
			}
			else if("录入人".equals(str)){
				paramList2.add("addusername");
			}
			else if("备注".equals(str)){
				paramList2.add("remark");
			}
			else if("财务联系人".equals(str)){
				paramList2.add("financiallink");
			}
			else if("财务联系电话".equals(str)){
				paramList2.add("financialmobile");
			}
			else if("财务邮箱".equals(str)){
				paramList2.add("financialemail");			
			}
			else if("业务联系人".equals(str)){
				paramList2.add("contactname");			
			}
			else if("业务联系人电话".equals(str)){
				paramList2.add("contactmobile");
			}
			else if("业务联系人邮箱".equals(str)){
				paramList2.add("contactemail");
			}
			else if("区域主管".equals(str)){
				paramList2.add("contactarea");
			}
			else if("区域主管联系电话".equals(str)){
				paramList2.add("contactareamobile");
			}
			else if("区域主管邮箱".equals(str)){
				paramList2.add("contactareaemail");
			}
			else if("大区/省区经理".equals(str)){
				paramList2.add("region");
			}
			else if("大区/省区经理联系电话".equals(str)){
				paramList2.add("regionmobile");
			}
			else if("大区/省区经理邮箱".equals(str)){
				paramList2.add("regionemail");
			}
			else if("年度目标".equals(str)){
				paramList2.add("annualobjectives");
			}
			else if("年度返利%".equals(str)){
				paramList2.add("annualrebate");
			}
			else if("半年度返利%".equals(str)){
				paramList2.add("semiannualrebate");
			}
			else if("季度返利%".equals(str)){
				paramList2.add("quarterlyrebate");
			}
			else if("月度返利%".equals(str)){
				paramList2.add("monthlyrebate");
			}
			else if("破损补贴".equals(str)){
				paramList2.add("breakagesubsidies");
			}
			else if("其他费用补贴".equals(str)){
				paramList2.add("othersubsidies");
			}
			else if("收回方式".equals(str)){
				paramList2.add("recoverymodename");
			}
			else if("供价折扣率%".equals(str)){
				paramList2.add("pricediscount");
			}
			else if("其他条件".equals(str)){
				paramList2.add("otherconditions");
			}
			else if("促销员名额".equals(str)){
				paramList2.add("promotersplaces");
			}
			else if("促销员工资".equals(str)){
				paramList2.add("promoterssalary");
			}
			else if("业务员名额".equals(str)){
				paramList2.add("salesmanplaces");
			}
			else if("业务员工资".equals(str)){
				paramList2.add("salesmansalary");
			}
			else if("法人代表".equals(str)){
				paramList2.add("person");
			}
			else if("法人代表电话".equals(str)){
				paramList2.add("personmobile");
			}
			else if("法人身份证号码".equals(str)){
				paramList2.add("personcard");
			}
			else if("注册资金".equals(str)){
				paramList2.add("fund");
			}else{
				paramList2.add("null");
			}
		}
		Map resultMap2 = new HashMap();
		if(paramList.size() == paramList2.size()){
			List result = new ArrayList();
			Map<String, Object> map2 = new HashMap<String, Object>();
			List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
			if(list.size() != 0){
				Map detialMap = new HashMap();
				for(Map<String, Object> map4 : list){
					Object object = entity.newInstance();
					Field[] fields = entity.getDeclaredFields();
					//获取的导入数据格式转换
					DRCastTo(map4,fields);
					//BeanUtils.populate(object, map4);
					PropertyUtils.copyProperties(object, map4);
					result.add(object);
				}
				if(result.size() != 0){
					resultMap2 = excelService.insertSalesOrder(object2, result, method);
				}
			}else{
				resultMap2.put("excelempty", true);
			}
		}
		else{
			resultMap2.put("versionerror", true);
		}
		return resultMap2;
	}
	
	/**
	 * 合同商品导入
	 * @return
	 * @author panxiaoxiao 
	 * @date Nov 8, 2013
	 */
	@UserOperateLog(key="CustomerPrice",type=2,value="合同商品导入")
	public Map<String,Object> importShortcutPriceCustomerExcel(Map<String,String> map)throws Exception{
		String clazz = (String)map.get("clazz");
		String meth = "addDRPriceCustomer";
		String module = (String)map.get("module");
		String pojo = "CustomerPrice";
		Object object2 = SpringContextUtils.getBean(clazz);
		Class entity = Class.forName("com.hd.agent." + module + ".model." +pojo);
		Method[] methods = object2.getClass().getMethods();
		Method method = null;
		for(Method m : methods){
			if(m.getName().equals(meth)){
				method = m;
			}
		}
		
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("客户编码".equals(str)){
				paramList2.add("customerid");
			}
			else if("客户名称".equals(str)){
				paramList2.add("customername");
			}
			else if("上级客户".equals(str)){
				paramList2.add("pcustomername");
			}
			else if("商品编码".equals(str)){
				paramList2.add("goodsid");
			}
			else if("商品名称".equals(str)){
				paramList2.add("goodsname");
			}
			else if("店内码".equals(str)){
				paramList2.add("shopid");
			}
			else if("合同价".equals(str)){
				paramList2.add("price");			
			}
			else if("备注".equals(str)){
				paramList2.add("remark");			
			}else{
				paramList2.add("null");
			}
		}
		Map resultMap2 = new HashMap();
		if(paramList.size() == paramList2.size()){
			List result = new ArrayList();
			Map<String, Object> map2 = new HashMap<String, Object>();
			List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
			if(list.size() != 0){
				Map detialMap = new HashMap();
				for(Map<String, Object> map4 : list){
					Object object = entity.newInstance();
					Field[] fields = entity.getDeclaredFields();
					//获取的导入数据格式转换
					DRCastTo(map4,fields);
					//BeanUtils.populate(object, map4);
					PropertyUtils.copyProperties(object, map4);
					result.add(object);
				}
				if(result.size() != 0){
					resultMap2 = excelService.insertSalesOrder(object2, result, method);
				}
			}else{
				resultMap2.put("excelempty", true);
			}
		}
		else{
			resultMap2.put("versionerror", true);
		}
		return resultMap2;
	}
	/**
	 * 导入复杂版
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 27, 2013
	 */
	public Map<String, Object> importMoreComplexType(Map<String, String> map3)throws Exception{
		String clazz = (String)map3.get("clazz");
		String tnstr = (String)map3.get("tnstr");
		String module = (String)map3.get("module");
		String methodstr = (String)map3.get("methodstr");
		String pojostr = (String)map3.get("pojostr");//实体类json组集合
		String maintn = (String)map3.get("maintn");
		String majorkey = (String)map3.get("majorkey");
		String childkey = (String)map3.get("childkey");
		String childsamemethod = (String)map3.get("childsamemethod");
		String repeatVal = "",closeVal = "",failStr = "",errorVal = "",errorChildVal = "",msg = "";//主表导入失败的主键值
		//返回值
		Map<String, Object> retMap = new HashMap<String,Object>();
		int successNum = 0,failureNum = 0,closeNum = 0,repeatNum = 0,errorNum = 0,errorChildNum = 0;
		Map<String,List<String>> paramListMap = ExcelUtils.importExcelMoreFirstRow(excelFile,tnstr); //获取各个sheet第一行数据为字段的描述列表
		if(paramListMap.size() != 0){
			Map<String,List<String>> paramListMap2 = desToColumnMore(paramListMap, tnstr); //将描述名改为字段名并判断数据权限
			Map<String,List<Map<String, Object>>> listMap = ExcelUtils.importExcelMore(excelFile, paramListMap2,tnstr); //获取导入数据{t_base_personnle:List...}
			Object object = SpringContextUtils.getBean(clazz);
			//类名
			JSONObject pojojson = JSONObject.fromObject(pojostr);//{t_base_personnle:Personnel}
			
			//读取表名tnkey和表描述名tnval
			JSONObject json = JSONObject.fromObject(tnstr);
			Iterator<String> tnit = json.keys();
		    while(tnit.hasNext()) {
		    	String key = (String)tnit.next();
		    	String tn = json.getString(key);
		    	String pojo = pojojson.getString(tn);//表名对应的类名Personnel
	    		Class entity = Class.forName("com.hd.agent." + module + ".model." +pojo);
	    		//将数据格式化类列表List<Class>
				List<Map<String, Object>> list = listMap.get(tn);
				List modelList = importMapToList(list, entity, tn);
				JSONObject methodjson = JSONObject.fromObject(methodstr);//导入方法{t_base_personnle:addDRPersonnelInfo}
				if(maintn.equals(tn)){//若为主表
					if(modelList.size() != 0){
						if(methodjson.containsKey(tn)){
							String meth = methodjson.getString(tn);//表名对应的导入方法
							Method method2 = null;
							Method[] methods = object.getClass().getMethods();
							for(Method m : methods){
								if(m.getName().equals(meth)){
									method2 = m;
								}
							}
							for(int i=0;i<modelList.size();i++){
								//主表导入新增方法执行，返回是否成功
								Map resultMap = excelService.insertMain(object, modelList.get(i), method2);
								Map map= new HashMap();
								map = BeanUtils.describe(modelList.get(i));
								if(null != resultMap){
									if(resultMap.get("flag").equals(false)){
										if((Integer)resultMap.get("repeatNum") > 0){
											repeatVal += (String)resultMap.get("repeatVal") + ",";
											repeatNum += (Integer)resultMap.get("repeatNum");
										}
										else if((Integer)resultMap.get("closeNum") > 0){
											closeVal += (String)resultMap.get("closeVal") + ",";
											closeNum += (Integer)resultMap.get("closeNum");
										}
										else if((Integer)resultMap.get("failure") > 0){
											failStr += (String)resultMap.get("failStr") + ",";
											failureNum += (Integer)resultMap.get("failure");
										}else if ((Integer)resultMap.get("errorNum") > 0) {
											errorVal += (String)resultMap.get("errorVal") + ",";
											errorNum += (Integer)resultMap.get("errorNum");
										}
										if(StringUtils.isNotEmpty((String)resultMap.get("msg"))){
											msg = (String)resultMap.get("msg");
										}
									}else{
										if((Integer)resultMap.get("repeatNum") > 0){
											repeatVal += (String)resultMap.get("repeatVal") + ",";
											repeatNum += (Integer)resultMap.get("repeatNum");
										}
										successNum += (Integer)resultMap.get("success");
										Map samemothedMap = new HashMap();
										Map childmap = jsonObjectToMap(json);//读取子表表名和描述名
										childmap.remove(key);
										JSONObject json2 = JSONObject.fromObject(childmap); 
										Iterator<String> tnit2 = json2.keys();
										while (tnit2.hasNext()) {
											String tn2 = json2.getString((String)tnit2.next());
											String meth2 = methodjson.getString(tn2);//表名对应的导入方法
											String pojo2 = pojojson.getString(tn2);//表名对应的类名Personnel
								    		Class entity2 = Class.forName("com.hd.agent." + module + ".model." +pojo2);
											List<Map<String, Object>> list2 = listMap.get(tn2);
											List modelList2 = importMapToList(list2, entity2, tn2);
											List childList = new ArrayList();
											for(int j=0;j<modelList2.size();j++){
												Map map2= new HashMap();
												map2 = BeanUtils.describe(modelList2.get(j));
												if(null != map.get(majorkey) && null != map2.get(childkey)){
													if(map.get(majorkey).toString().equals(map2.get(childkey).toString())){
														childList.add(modelList2.get(j));
													}
												}
											}
											if(StringUtils.isEmpty(childsamemethod) || !meth2.equals(childsamemethod)){//判断是否可调用同一方法
												Method[] methods2 = object.getClass().getMethods();
												Method method3 = null;
												for(Method m : methods2){
													if(m.getName().equals(meth2)){
														method3 = m;
													}
												}
												//主表导入新增方法执行，返回是否成功
												if(childList.size() != 0){
													Map resultMap2 = excelService.comInsertMethod(object, childList, method3);
													if(resultMap2.containsKey("flag") && resultMap2.get("flag").equals(false)){
														errorChildVal += map.get(majorkey).toString() + ",";
														errorChildNum += 1;
													}
												}
											}else{
												samemothedMap.put(pojo2, childList);
											}
										}
										if(!samemothedMap.isEmpty()){
											Method[] methods2 = object.getClass().getMethods();
											Method method3 = null;
											for(Method m : methods2){
												if(m.getName().equals(childsamemethod)){
													method3 = m;
												}
											}
											Map resultMap2 = excelService.insertCaseBySameMethod(object, samemothedMap, method3);
											if(resultMap2.containsKey("flag") && resultMap2.get("flag").equals(false)){
												errorChildVal += map.get(majorkey).toString() + ",";
												errorChildNum += 1;
											}
										}
									}
								}
							}
						}
					}else{
						retMap.put("excelempty", true);
					}
				}
		    }
		}
		else{
			retMap.put("versionerror", true);
		}
		retMap.put("success", successNum);
		retMap.put("failure", failureNum);
		retMap.put("repeatNum", repeatNum);
		retMap.put("closeNum", closeNum);
		retMap.put("errorNum", errorNum);
		retMap.put("errorChildNum", errorChildNum);
		if(StringUtils.isNotEmpty(repeatVal)){
			repeatVal = repeatVal.substring(0, repeatVal.lastIndexOf(","));
		}
		if(StringUtils.isNotEmpty(closeVal)){
			closeVal = closeVal.substring(0, closeVal.lastIndexOf(","));
		}
		if(StringUtils.isNotEmpty(errorVal)){
			errorVal = errorVal.substring(0, errorVal.lastIndexOf(","));
		}
		if(StringUtils.isNotEmpty(errorChildVal)){
			errorChildVal = errorChildVal.substring(0, errorChildVal.lastIndexOf(","));
		}
		retMap.put("repeatVal", repeatVal);
		retMap.put("closeVal", closeVal);
		retMap.put("errorVal", errorVal);
		retMap.put("errorChildVal", errorChildVal);
		retMap.put("msg",msg);
		return retMap;
	}
	
	/**
	 * 导出数据
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 30, 2013
	 */
	public void exportExcel() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String state = request.getParameter("state");
		if(StringUtils.isNotEmpty(state)){
			map.put("state", state);
		}
		String tn = "";
		String title = "";
		if(map.containsKey("tn")){
			tn = map.get("tn").toString();
		}
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		if(map.containsKey("ordersql") && null != map.get("ordersql")){
			pageMap.setOrderSql((String)map.get("ordersql"));
		}
		pageMap.setCondition(map);
		List list = excelService.getList(pageMap);
		ExcelUtils.exportExcel(exportDataFilter(list, tn), title);
	}
	
	/**
	 * 多表导出
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jun 17, 2013
	 */
	public void exportMoreExcel() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String version = "",versiontype = "";
		if(map.containsKey("version")){
			version = map.get("version").toString();
		}
		if(map.containsKey("versiontype")){
			versiontype = map.get("versiontype").toString();
		}
		String title = "";
		if(map.containsKey("excelTitle")){
			title = map.get("excelTitle").toString();
		}
		else{
			title = "list";
		}
		if(StringUtils.isEmpty(title)){
			title = "list";
		}
		if(!"1".equals(version)){
			if("1".equals(versiontype)){//简单版
				title = title + "简化版";
				if(map.containsKey("shortcutname")){
					String shortcutname = map.get("shortcutname").toString();
					pageMap.setCondition(map);
					if("goods".equals(shortcutname)){//商品快捷导出
						List list = excelService.getGoodsList(pageMap);
						ExcelUtils.exportExcel(exportDataFilterGoods(list), title);
					}
					else if("customer".equals(shortcutname)){//客户快捷导出
						List list = excelService.getCustomerList(pageMap);
						ExcelUtils.exportExcel(exportDataFilterCustomer(list), title);
					}
					else if("supplier".equals(shortcutname)){//供应商快捷导出
						List list = excelService.getSupplierList(pageMap);
						ExcelUtils.exportExcel(exportDataFilterSupplier(list), title);
					}
				}
			}
			else if("2".equals(versiontype)){
				title = title + "合同版";
				exportMoreComplexType(map,title);
			}
		}
		else{
			exportMoreComplexType(map,title);
		}
		
	}
	
	/**
	 * 商品快捷导出
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 26, 2013
	 */
	@UserOperateLog(key="GoodsInfo",type=2,value="商品档案简化版导出")
	public List<Map<String, Object>> exportDataFilterGoods(List<GoodsInfo> list)throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "商品编码");
		firstMap.put("name", "商品名称");
		firstMap.put("model", "规格型号");
		firstMap.put("spell", "助记符");
		firstMap.put("barcode", "条形码");
		firstMap.put("boxbarcode", "箱装条形码");
		firstMap.put("itemno", "商品货位");
		firstMap.put("defaultsortName", "商品分类");
		firstMap.put("brandName", "商品品牌");
		firstMap.put("deptname", "所属部门");
		firstMap.put("defaultsupplierName", "默认供应商");
		firstMap.put("secondsuppliername", "第二供应商");
		firstMap.put("glength", "长度");
		firstMap.put("ghight", "高度");
		firstMap.put("gwidth", "宽度");
		firstMap.put("totalweight", "箱重");
		firstMap.put("totalvolume", "箱体积");
		firstMap.put("mainunitName", "主单位");
		firstMap.put("auxunitname", "辅单位");
		firstMap.put("boxnum", "箱装量");
		firstMap.put("goodstypeName", "商品类型");
		firstMap.put("storageName", "默认仓库");
		firstMap.put("isstoragelocationname", "库位管理");
		firstMap.put("isshelflifename", "保质期管理");
		firstMap.put("shelflifedetail", "保质期");
		firstMap.put("isbatchname", "批次管理");
		firstMap.put("highestbuyprice", "采购价");
		firstMap.put("lowestsaleprice", "最低销售价");
		firstMap.put("basesaleprice", "基准销售价");
		firstMap.put("newbuyprice", "最新采购价");
		firstMap.put("newsaleprice", "最新销售价");
		firstMap.put("defaulttaxtypeName", "默认税种");
		firstMap.put("minimum", "最小发货单位");
		//价格套列表
		List<SysCode> priceList = getGoodsPriceList();
		for(SysCode sysCode : priceList){
			firstMap.put("price"+sysCode.getCode(), sysCode.getCodename());
		}
		firstMap.put("remark", "备注");
		firstMap.put("stateName", "状态");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(GoodsInfo goodsInfo : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(goodsInfo);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		return result;
	}
	
	/**
	 * 客户快捷导出
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 26, 2013
	 */
	@UserOperateLog(key="Customer",type=2,value="客户档案简化版导出")
	public List<Map<String, Object>> exportDataFilterCustomer(List<Customer> list)throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "编码");
		firstMap.put("name", "客户名称");
		firstMap.put("pname", "上级客户");
		firstMap.put("islastname", "是否总店");
		firstMap.put("shortcode", "助记符");
		firstMap.put("shortname", "简称");
		firstMap.put("taxno", "税号");
		firstMap.put("bank", "开户银行");
		firstMap.put("cardno", "开户账号");
		firstMap.put("caraccount", "开户名");
		firstMap.put("fund", "注册资金");
		firstMap.put("storearea", "门店面积m^2");
		firstMap.put("contactname", "联系人");
		firstMap.put("mobile", "联系人电话");
		firstMap.put("payeename", "收款人");
		firstMap.put("faxno", "传真");
		firstMap.put("naturename", "公司属性");
		firstMap.put("ischainname", "是否连锁");
		firstMap.put("countylevel", "县级市");
		firstMap.put("villagetown", "乡镇");
		firstMap.put("address", "详细地址");
		firstMap.put("settletypename", "结算方式");
		firstMap.put("settleday", "结算日");
		firstMap.put("paytypename", "支付方式");
		firstMap.put("iscashname", "现款");
		firstMap.put("islongtermname", "账期");
		firstMap.put("overgracedate", "超账期宽限天数");
		firstMap.put("credit", "信用额度");
		firstMap.put("reconciliationdate", "对账日期");
		firstMap.put("billingdate", "开票日期");
		firstMap.put("arrivalamountdate", "到款日期");
		firstMap.put("tickettypename", "票种");
		firstMap.put("canceltypename", "核销方式");
		firstMap.put("pricesortname", "价格套");
		firstMap.put("promotionsortname", "促销分类");
		firstMap.put("salesareaname", "所属区域");
		firstMap.put("customersortname", "所属分类");
		firstMap.put("abclevelname", "ABCD等级");
		firstMap.put("salesdeptname", "默认销售部门");
		firstMap.put("salesusername", "客户业务员");
		firstMap.put("tallyusername", "默认理货员");
		firstMap.put("indoorstaffname", "默认内勤");
		firstMap.put("payeename", "收款人");
		firstMap.put("checker", "对账人");
		firstMap.put("checkmobile", "对账人电话");
		firstMap.put("checkemail", "对账人邮箱");
		firstMap.put("payer", "付款人");
		firstMap.put("payermobile", "付款人电话");
		firstMap.put("payeremail", "付款人邮箱");
		firstMap.put("shopmanager", "店长");
		firstMap.put("shopmanagermobile", "店长联系电话");
		firstMap.put("gsreceipter", "收货人");
		firstMap.put("gsreceiptermobile", "收货人联系电话");
		firstMap.put("statename", "状态");
		firstMap.put("addusername", "录入人");
		firstMap.put("remark", "备注");
		firstMap.put("person", "法人代表");
		firstMap.put("personmobile", "法人代表电话");
		firstMap.put("personcard", "法人身份证号码");
		firstMap.put("setupdate", "客户开户日期");
		firstMap.put("overcontrolname", "超账期控制");
		firstMap.put("creditratingname", "信用等级");
		firstMap.put("creditdate", "信用期限");
		firstMap.put("targetsales", "目标销售");
		firstMap.put("yearback", "年返");
		firstMap.put("monthback", "月返");
		firstMap.put("dispatchingamount", "配送费");
		firstMap.put("sixone", "六节一庆");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(Customer customer : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(customer);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		return result;
	}

	/**
	 * 供应商快捷导出
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 26, 2013
	 */
	@UserOperateLog(key="BuySupplier",type=2,value="供应商档案简化版导出")
	public List<Map<String, Object>> exportDataFilterSupplier(List<BuySupplier> list)throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "供应商编码");
		firstMap.put("name", "供应商名称");
		firstMap.put("spell", "助记符");
		firstMap.put("shortname", "简称");
		firstMap.put("taxno", "税号");
		firstMap.put("bank", "开户银行");
		firstMap.put("cardno", "开户账号");
		firstMap.put("telphone", "供应商电话");
		firstMap.put("faxno", "供应商传真");
		firstMap.put("address", "详细地址");
		firstMap.put("buyareaname", "所属区域");
		firstMap.put("suppliersortname", "所属分类");
		firstMap.put("filialename", "所属部门");
		firstMap.put("buydeptname", "采购部门");
		firstMap.put("buyusername", "采购员");
		firstMap.put("buyusermobile", "采购员联系电话");
		firstMap.put("settletypename", "结算方式");
		firstMap.put("canceltypename", "核销方式");
		firstMap.put("storagename", "对应仓库");
		firstMap.put("orderappendname", "订单追加");
		firstMap.put("statename", "状态");
		firstMap.put("addusername", "录入人");
		firstMap.put("remark", "备注");
		firstMap.put("financiallink", "财务联系人");
		firstMap.put("financialmobile", "财务联系电话");
		firstMap.put("financialemail", "财务邮箱");
		firstMap.put("contactname", "业务联系人");
		firstMap.put("contactmobile", "业务联系人电话");
		firstMap.put("contactemail", "业务联系人邮箱");
		firstMap.put("contactarea", "区域主管");
		firstMap.put("contactareamobile", "区域主管联系电话");
		firstMap.put("contactareaemail", "区域主管邮箱");
		firstMap.put("region", "大区/省区经理");
		firstMap.put("regionmobile", "大区/省区经理联系电话");
		firstMap.put("regionemail", "大区/省区经理邮箱");
		firstMap.put("annualobjectives", "年度目标");
		firstMap.put("annualrebate", "年度返利%");
		firstMap.put("semiannualrebate", "半年度返利%");
		firstMap.put("quarterlyrebate", "季度返利%");
		firstMap.put("monthlyrebate", "月度返利%");
		firstMap.put("breakagesubsidies", "破损补贴");
		firstMap.put("othersubsidies", "其他费用补贴");
		firstMap.put("recoverymodename", "收回方式");
		firstMap.put("pricediscount", "供价折扣率%");
		firstMap.put("otherconditions", "其他条件");
		firstMap.put("promotersplaces", "促销员名额");
		firstMap.put("promoterssalary", "促销员工资");
		firstMap.put("salesmanplaces", "业务员名额");
		firstMap.put("salesmansalary", "业务员工资");
		firstMap.put("person", "法人代表");
		firstMap.put("personmobile", "法人代表电话");
		firstMap.put("personcard", "法人身份证号码");
		firstMap.put("fund", "注册资金");
		result.add(firstMap);
		
		if(list.size() != 0){
			for(BuySupplier buySupplier : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(buySupplier);
				for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
					if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
						for(Map.Entry<String, Object> entry : map.entrySet()){
							if(fentry.getKey().equals(entry.getKey())){
								objectCastToRetMap(retMap,entry);
							}
						}
					}
					else{
						retMap.put(fentry.getKey(), "");
					}
				}
				result.add(retMap);
			}
		}
		return result;
	}
	
	/**
	 * 导出复杂版
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 26, 2013
	 */
	public void exportMoreComplexType(Map<String, String> map,String title)throws Exception{
		String queryparam = null,id="", tnStr = "",tnjson = "",maintn="",childkey="",anotherkey = "",sort = null,uniontnjson = null;
		if(map.containsKey("tnstr")){
			tnStr = map.get("tnstr").toString();
		}
		if(map.containsKey("tnjson")){
			tnjson = map.get("tnjson").toString();
		}
		if(map.containsKey("maintn")){
			maintn = map.get("maintn").toString();
		}
		if(map.containsKey("childkey")){
			childkey = map.get("childkey").toString();
		}
		if(map.containsKey("id")){
			id = map.get("id").toString();
		}
		if(map.containsKey("queryparam")){
			queryparam = map.get("queryparam").toString();
		}
		if(map.containsKey("sort")){
			sort = map.get("sort").toString();
		}
		if(map.containsKey("uniontnjson")){
			uniontnjson = map.get("uniontnjson").toString();
		}
		if(map.containsKey("ordersql") && null != map.get("ordersql")){
			pageMap.setOrderSql((String)map.get("ordersql"));
		}
		String[] tnArr = tnStr.split(",");
		Map<String,	Map> map2 = new HashMap<String,Map>();
		for(String tn : tnArr){
			Map map3 = new HashMap();
			map3.put("tn", tn);
			map3.putAll(map);
			String uniontable = null;
			if(!maintn.equals(tn)){
				if(StringUtils.isNotEmpty(queryparam)){
					if(queryparam.indexOf(",") != -1){
						String[] paramArr = queryparam.split(",");
						for(String param : paramArr){
							if(map3.containsKey(param)){
								map3.put(param+"1", param);
							}
						}
					}
					else{
						map3.put(queryparam+"1", map3.get(queryparam));
						map3.remove(queryparam);
					}
				}
				if(StringUtils.isNotEmpty(sort)){
					if(sort.indexOf(",") != -1){
						String[] strArr = sort.split(",");
						int num = 1;
						for(String str :strArr){
							map3.put("sort"+num+"", str);
							num++;
						}
					}
					else{
						map3.put("sort1", sort);
					}
					map3.remove("sort");
				}
				//是否需两子表合并查询
				if(StringUtils.isNotEmpty(uniontnjson)){
					JSONObject jsonObject = JSONObject.fromObject(uniontnjson);
					if(jsonObject.containsKey(tn)){
						uniontable = jsonObject.get(tn).toString();
						if(StringUtils.isNotEmpty(uniontable)){
							map3.put("uniontable", uniontable);
						}
					}
				}
			}
			Map map5 = new HashMap();
			pageMap.setCondition(map3);
			List list = excelService.getList(pageMap);
			map5.put(tn, list);
			map2.put(""+tn+"_List", map5);
		}
		ExcelUtils.exportMoreExcel(exportDataFilter(map2,tnStr,anotherkey), title,tnjson);
	}
	
	/**
	 * Excel导出，转换编码，去除不可导出字段
	 * @param list
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date 2013-3-13
	 */
	private List<Map<String, Object>> exportDataFilter(List list, String tablename) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map queryMap = new HashMap();
		queryMap.put("tablename", tablename);
		List<TableColumn> dicList = getBaseDataDictionaryService().getTableColumnListBy(queryMap);
		Map colsMap = getAccessColumn(tablename);
		Map condition = new HashMap();
		Map referMap = new HashMap();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		for(TableColumn tableColumn : dicList){ //循环数据字典，取出支持数据导出且有权限查看的字段。
			if("1".equals(tableColumn.getUsedataexport()) && colsMap.containsKey(tableColumn.getColumnname())){
				if("1".equals(tableColumn.getUsecoded())){
					Map map = new HashMap();
					List<SysCode> sysCodes = getBaseSysCodeService().showSysCodeListByType(tableColumn.getCodedcoltype());
					for(SysCode sysCode : sysCodes){
						map.put(sysCode.getCode(), sysCode.getCodename());
					}
					condition.put(tableColumn.getColumnname(), map);
				}
				if("1".equals(tableColumn.getUsecolrefer())){//是否支持参照窗口
					if("1".equals(tableColumn.getReferwflag())){
						referMap.put(tableColumn.getColumnname(), tableColumn.getReferwid());
					}
				}
				firstMap.put(tableColumn.getColumnname(), tableColumn.getColchnname());
			}
		}
		result.add(firstMap);
		
		for(int i = 0; i<list.size(); i++){
			Map<String, Object> map = (HashMap<String, Object>)list.get(i);
			Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
			for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
				if(map.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
					for(Map.Entry<String, Object> entry : map.entrySet()){
						if(fentry.getKey().equals(entry.getKey())){
							if(condition.containsKey(entry.getKey())){
								Map conMap = (Map)condition.get(entry.getKey());
								String str = (String)entry.getValue();
								String retName = "";
								if(str.indexOf(",") != -1){
									String[] strArr = str.split(",");
									for(int s=0;s<strArr.length;s++){
										String valName = (String)conMap.get(strArr[s]);
										retName += valName + ",";
									}
									resultMap.put(entry.getKey(), retName.substring(0, retName.lastIndexOf(",")));
								}
								else{
									resultMap.put(entry.getKey(), conMap.get(entry.getValue()));
								}
							}
							else if(referMap.containsKey(entry.getKey())){
								String referWname = referMap.get(entry.getKey()).toString();
								String valName = getBaseReferWindowService().getReferWindowNameByValue(referWname, entry.getValue().toString());
								resultMap.put(entry.getKey(), valName);
							}
							else{
								objectCastToRetMap(resultMap,entry);
							}
						}
					}
				}
				else{ //否则直接赋值为空
					resultMap.put(fentry.getKey(), "");
				}
			}
			result.add(resultMap);
		}
		return result;
	}
	
	/**
	 * Excel多表导出，转换编码，去除不可导出字段
	 * @param map<String,List>
	 * @param tnstr 表名字符串数组
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 17, 2013
	 */
	private Map<String,List<Map<String, Object>>> exportDataFilter(Map<String,Map> map, String tnstr,String anotherkey) throws Exception{
		Map<String,List<Map<String, Object>>> map2 = new HashMap<String,List<Map<String, Object>>>();
		String[] tnArr = tnstr.split(",");
		for(int i=0;i<tnArr.length;i++){
			List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
			Map queryMap = new HashMap();
			queryMap.put("tablename", tnArr[i]);
			List<TableColumn> dicList = getBaseDataDictionaryService().getTableColumnListBy(queryMap);
			Map colsMap = getAccessColumn(tnArr[i]);
			Map condition = new HashMap();
			Map referMap = new HashMap();
			Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
			for(TableColumn tableColumn : dicList){ //循环数据字典，取出支持数据导出且有权限查看的字段。
				if("1".equals(tableColumn.getUsedataexport()) && colsMap.containsKey(tableColumn.getColumnname())){
					if("1".equals(tableColumn.getUsecoded())){
						Map map3 = new HashMap();
						List<SysCode> sysCodes = getBaseSysCodeService().showSysCodeListByType(tableColumn.getCodedcoltype());
						for(SysCode sysCode : sysCodes){
							map3.put(sysCode.getCode(), sysCode.getCodename());
						}
						condition.put(tableColumn.getColumnname(), map3);
					}
					if("1".equals(tableColumn.getUsecolrefer())){//是否支持参照窗口
						if("1".equals(tableColumn.getReferwflag())){
							referMap.put(tableColumn.getColumnname(), tableColumn.getReferwid());
						}
					}
					firstMap.put(tableColumn.getColumnname(), tableColumn.getColchnname());
				}
			}
			result.add(firstMap);
			
			String key = tnArr[i] + "_List";
			Map retMap = map.get(key);
			if(null == retMap){
				continue;
			}
			if(retMap.containsKey(tnArr[i])){
				List<Map<String, Object>> retList = (List)retMap.get(tnArr[i]);
				if(null != retList && retList.size() != 0){
					for(int j = 0; j<retList.size(); j++){
						Map<String, Object> resultMap = retunResultList(firstMap,retList.get(j),condition,referMap);
						result.add(resultMap);
					}
				}
				if(null != result){
					map2.put(key, result);
				}
			}
			else{
				if(StringUtils.isNotEmpty(anotherkey)){
					String[] strArr = anotherkey.split(",");
					for(String str : strArr){
						List<Map<String, Object>> result2 = new ArrayList<Map<String,Object>>();
						result2.add(firstMap);
						List<Map<String, Object>> retList = (List)retMap.get(str);
						if(null != retList && retList.size() != 0){
							for(int j = 0; j<retList.size(); j++){
								Map<String, Object> resultMap = retunResultList(firstMap,retList.get(j),condition,referMap);
								result2.add(resultMap);
							}
						}
						if(null != result2){
							map2.put(str+"_List", result2);
						}
					}
				}
			}
		}
		return map2;
	}
	
	public Map<String, Object> retunResultList(Map<String, Object> firstMap,Map<String, Object> map4,Map condition,Map referMap)throws Exception{
		//Map<String, Object> map4 = (HashMap<String, Object>)retList.get(j);
		Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
		for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
			if(map4.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
				for(Map.Entry<String, Object> entry : map4.entrySet()){
					if(fentry.getKey().equals(entry.getKey())){
						if(condition.containsKey(entry.getKey())){
							Map conMap = (Map)condition.get(entry.getKey());
							Object object = entry.getValue();
							if(null!=object){
								if (object instanceof String) {
									String retName = "";
									if(object.toString().indexOf(",") != -1){
										String[] strArr = object.toString().split(",");
										for(int s=0;s<strArr.length;s++){
											String valName = (String)conMap.get(strArr[s]);
											retName += valName + ",";
										}
										resultMap.put(entry.getKey(), retName.substring(0, retName.lastIndexOf(",")));
									}
									else{
										resultMap.put(entry.getKey(), conMap.get(entry.getValue()));
									}
								}else if(object instanceof BigDecimal){
									BigDecimal bignum = (BigDecimal) object;
									resultMap.put(entry.getKey(), conMap.get(bignum.setScale(decimalLen, BigDecimal.ROUND_HALF_UP)));
								}else if(object instanceof Timestamp){
									Timestamp timestamp = (Timestamp) object;
									resultMap.put(entry.getKey(), conMap.get(timestamp));
								}else if(object instanceof Date){
									Date date = (Timestamp) object;
									resultMap.put(entry.getKey(), conMap.get(date));
								}
								else if(object instanceof Integer){
									resultMap.put(entry.getKey(), conMap.get(object));
								}
							}
						}
						else if(referMap.containsKey(entry.getKey())){
							String referWname = referMap.get(entry.getKey()).toString();
							String valName = getBaseReferWindowService().getReferWindowNameByValue(referWname, entry.getValue().toString());
							resultMap.put(entry.getKey(), valName);
						}
						else{
							objectCastToRetMap(resultMap,entry);
						}
					}
				}
			}
			else{ //否则直接赋值为空
				resultMap.put(fentry.getKey(), "");
			}
		}
		return resultMap;
	}
	
	/**
	 * excel导入时将Map转为实体对象
	 * @param list
	 * @param bean
	 * @param tablename
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 30, 2013
	 */
	protected List importMapToList(List<Map<String, Object>> list, Class bean, String tablename) throws Exception{
		List result = new ArrayList();
		Map queryMap = new HashMap();
		queryMap.put("tablename", tablename);
		List<TableColumn> dicList = getBaseDataDictionaryService().getTableColumnListBy(queryMap);
		Map condition = new HashMap(); //对应编码表数据
		Map referMap = new HashMap();
		for(TableColumn tableColumn : dicList){
			if("1".equals(tableColumn.getUsecoded())){ //该字段数据来源编码表
				Map map = new HashMap();
				List<SysCode> sysCodes = getBaseSysCodeService().showSysCodeListByType(tableColumn.getCodedcoltype()); //获取字段对应编码类型的数据
				for(SysCode sysCode : sysCodes){
					map.put(sysCode.getCodename(), sysCode.getCode()); //key为编码名称，value为编号，导入时要将值转换为编码才可以存入数据库
				}
				condition.put(tableColumn.getColumnname(), map); //如：key为sex，value为{"女":"0","男":"1","未知":"2"}这样的Map，导入的时候数据中的女将转为0，男转为1
			}
			if("1".equals(tableColumn.getUsecolrefer())){//是否支持参照窗口
				if("1".equals(tableColumn.getReferwflag())){//导出是否引用参照窗口
					referMap.put(tableColumn.getColumnname(), tableColumn.getReferwid());
				}
			}
		}
		if(null != list && list.size() != 0){
			for(Map<String, Object> map : list){ //循环导入的数据
				Object object = bean.newInstance();
				for(Map.Entry<String, Object> entry : map.entrySet()){
					if(condition.containsKey(entry.getKey())){
						Map conMap = (Map)condition.get(entry.getKey());
						Object obj = entry.getValue();
						if(null!=obj){
							if (obj instanceof String) {
								String retName = "";
								if(obj.toString().indexOf(",") != -1){
									String[] strArr = obj.toString().split(",");
									for(int s=0;s<strArr.length;s++){
										String valName = (String)conMap.get(strArr[s]);
										retName += valName + ",";
									}
									map.put(entry.getKey(), retName.substring(0, retName.lastIndexOf(",")));
								}
								else{
									map.put(entry.getKey(), conMap.get(entry.getValue()));
								}
							}else if(obj instanceof BigDecimal){
								BigDecimal bignum = (BigDecimal) obj;
								map.put(entry.getKey(), conMap.get(bignum.setScale(decimalLen, BigDecimal.ROUND_HALF_UP)));
							}else if(obj instanceof Timestamp){
								Timestamp timestamp = (Timestamp) obj;
								map.put(entry.getKey(), conMap.get(timestamp));
							}else if(obj instanceof Date){
								Date date = (Timestamp) obj;
								map.put(entry.getKey(), conMap.get(date));
							}
							else if(obj instanceof Integer){
								map.put(entry.getKey(), conMap.get(obj));
							}
						}
					}
					else if(referMap.containsKey(entry.getKey()) && null != referMap.get(entry.getKey())){
						String referWname = referMap.get(entry.getKey()).toString();
						if(null != entry.getValue()){
							String val = getBaseReferWindowService().getReferWindowNameByName(referWname, entry.getValue().toString());
							if(StringUtils.isNotEmpty(val)){
								map.put(entry.getKey(), val);
							}
						}
						else{
							map.put(entry.getKey(), null);
						}
					}
				}
				Field[] fields = bean.getDeclaredFields();
				DRCastTo(map,fields);
				//BeanUtils.populate(object, map);
				PropertyUtils.copyProperties(object, map);
				result.add(object);
			}
		}
		return result;
	}
	
	/**
	 * 通过数据字典将字段描述转为字段名
	 * @param desList 描述列表，是excel文件中的第一行数据
	 * @param tn 表名
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 30, 2013
	 */
	private List<String> desToColumn(List<String> desList, String tn) throws Exception{
		List<String> result = new ArrayList<String>();
		Map queryMap = new HashMap();
		queryMap.put("tablename", tn);
		List<TableColumn> columns = getBaseDataDictionaryService().getTableColumnListBy(queryMap);
		Map fieldMap = getAccessColumn(tn);
		if(null != desList && desList.size() != 0){
			for(String string : desList){
				for(TableColumn column : columns){
					if(string.equals(column.getColchnname())){
						result.add(column.getColumnname());
					}
				}
			}
		}
		if(null != result && result.size() != 0){
			for(String string : result){
				if(!fieldMap.containsKey(string)){
					result.remove(string);
				}
			}
		}
		return result;
	}
	
	private Map<String,List<String>> desToColumnMore(Map<String,List<String>> desListMap, String tnstr) throws Exception{
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		Map queryMap = new HashMap();
		JSONObject tnjson = JSONObject.fromObject(tnstr);
		Iterator<String> it = tnjson.keys();
		while (it.hasNext()) {
			String tndescribe = (String) it.next();//表描述名:人员列表
			String tnkey = tnjson.getString(tndescribe);//表名t_base_personnel
			List<String> result = new ArrayList<String>();
			queryMap.put("tablename", tnkey);
			List<TableColumn> columns = getBaseDataDictionaryService().getTableColumnListBy(queryMap);
			Map fieldMap = getAccessColumn(tnkey);
			List<String> desList = desListMap.get(tnkey+"_First");
			if(null != desList && desList.size() != 0){
				for(String string : desList){
					for(TableColumn column : columns){
						if(string.equals(column.getColchnname())){
							result.add(column.getColumnname());
						}
					}
					
				}
			}
			if(null != result && result.size() != 0){
				for(String string : result){
					if(!fieldMap.containsKey(string)){
						result.remove(string);
					}
				}
			}
			map.put(tnkey+"_Field", result);
		}
		return map;
	}
	
	/** 
     * 将json对象转换成Map 
     * 
     * @param jsonObject json对象 
     * @return Map对象 
     */ 
    @SuppressWarnings("unchecked") 
    public static Map<String, String> jsonObjectToMap(JSONObject jsonObject) 
    { 
        Map<String, String> result = new HashMap<String, String>(); 
        Iterator<String> iterator = jsonObject.keys(); 
        String key = null; 
        String value = null; 
        while (iterator.hasNext()) 
        { 
            key = iterator.next(); 
            value = jsonObject.getString(key); 
            result.put(key, value); 
        } 
        return result; 
    }

    /**
     * 跳转至全局导出页面
     * @return
     * @throws Exception
     */
    public String exportAnalysPage() throws Exception {
    	String common = request.getParameter("commonCol");
    	if(common.contains(",,")){
			common = common.replaceAll(",,",",");
		}
        Map mapCol = JSONUtils.jsonStrToMap(common);

		String commonCol = "";
		List commonList = (List) mapCol.get("common");
		List commonArrList = (List) commonList.get(0);
		String title = new String();
		for (int i = 0; i < commonArrList.size(); i++) {
			Map map = (Map) commonArrList.get(i);
			if (null == map) {
				continue;
			} else {
				String field = (String) map.get("field");
				String titlestr = (String) map.get("title");
				if ("ck".equals(field)) {
					continue;
				} else {
					commonCol += field + ",";
					title += titlestr + ",";
				}
			}
		}

		if (commonCol.endsWith(",")) {
			commonCol = commonCol.substring(0, commonCol.length() - 1);
			title = title.substring(0, title.length() - 1);
		}

        String exportname = (String) mapCol.get("exportname");

        request.setAttribute("commonCol",commonCol);
        request.setAttribute("colName",title);
        request.setAttribute("exportname",exportname);

        return SUCCESS ;

    }

	//这个方法用于根据trs行数和sheet画出整个表格
	public static void mergeColRow(Elements trs, WritableSheet sheet) throws RowsExceededException, WriteException {
		int[][] rowhb=new int[300][50];
		for(int i=0;i<trs.size();i++){
			Element tr=trs.get(i);
			Elements tds=tr.getElementsByTag("td");
			if(tds.size() == 0){
				tds = tr.getElementsByTag("th");
			}
			int realColNum=0;
			for(int j=0;j<tds.size();j++){
				Element td=tds.get(j);
				try{
					if(rowhb[i][realColNum]!=0){
						realColNum=getRealColNum(rowhb,i,realColNum);
					}
				}catch (Exception e){
					break;
				}
				int rowspan=1;
				int colspan=1;
				if(td.attr("rowspan")!=""){
					rowspan = Integer.parseInt(td.attr("rowspan"));
				}
				if(td.attr("colspan")!=""){
					colspan = Integer.parseInt(td.attr("colspan"));
				}
				String text=td.text();
				drawMegerCell(rowspan,colspan,sheet,realColNum,i,text,rowhb);
				realColNum=realColNum+colspan;
			}

		}
	}
	///这个方法用于根据样式画出单元格，并且根据rowpan和colspan合并单元格
	public static void drawMegerCell(int rowspan,int colspan,WritableSheet sheet,int realColNum,int realRowNum,String text,int[][] rowhb) throws RowsExceededException, WriteException{

		for(int i=0;i<rowspan;i++){
			for(int j=0;j<colspan;j++){
				if(i!=0||j!=0){
					text="";
				}
				Label label = new Label(realColNum+j,realRowNum+i,text);
				WritableFont countents = new WritableFont(WritableFont.TIMES,10); // 设置单元格内容，字号12
				WritableCellFormat cellf = new WritableCellFormat(countents );
				cellf.setAlignment(jxl.format.Alignment.CENTRE);//把水平对齐方式指定为居中
				cellf.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);//把垂直对齐方式指定为居
				label.setCellFormat(cellf);
				sheet.addCell(label);
				rowhb[realRowNum+i][realColNum+j]=1;
			}
		}
		sheet.mergeCells(realColNum,realRowNum, realColNum+colspan-1,realRowNum+rowspan-1);
	}
	public static int getRealColNum(int[][] rowhb,int i,int realColNum){
		while(rowhb[i][realColNum]!=0){
			realColNum++;
		}
		return realColNum;
	}
	///根据colgroups设置表格的列宽
	public static void setColWidth(Elements colgroups,WritableSheet sheet){
		if(colgroups.size()>0){
			Element colgroup=colgroups.get(0);
			Elements cols=colgroup.getElementsByTag("col");
			for(int i=0;i<cols.size();i++){
				Element col=cols.get(i);
				String strwd=col.attr("width");
				if(col.attr("width")!=""){
					int wd=Integer.parseInt(strwd);
					sheet.setColumnView(i,wd/8);
				}

			}

		}
	}




}

