/**
 * @(#)RejectBillAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 5, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.sales.model.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 销售退货通知单
 * @author zhengziyong
 */
public class RejectBillAction extends BaseSalesAction {

	private RejectBill rejectBill;
	
	public RejectBill getRejectBill() {
		return rejectBill;
	}

	public void setRejectBill(RejectBill rejectBill) {
		this.rejectBill = rejectBill;
	}
	/**
	 * 显示销售退货通知单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 17, 2013
	 */
	public String showRejectBillAddPage() throws Exception{
		return "success";
	}
	 /**
	  * 显示模板导入页面
	  * @author lin_xx
	  * @date 2016/12/6
	  */
	 public String showRejectModelPage() throws Exception{
		 return SUCCESS;
	 }

	  /**
	   * 模板导入
	   * @author lin_xx
	   * @date 2016/12/6
	   */
	  public String importRejectModel() throws Exception{
		  String driverid = request.getParameter("driverid");
		  String storageid = request.getParameter("storageid");
		  String billtype = request.getParameter("billtype");
		  String busid = request.getParameter("busid");
		  String gtype = request.getParameter("gtype");
          String ctype = request.getParameter("ctype");
		  String pricetype = request.getParameter("pricetype");
		  String fileparam = request.getParameter("fileparam");
		  String[] info = fileparam.split(";");

		  //读取导入文件
		  InputStream is = new FileInputStream(importFile);
		  Workbook hssfWorkbook = null;
		  String type = ExcelUtils.getFileByFile(importFile);
		  if("xls".equals(type)){
			  hssfWorkbook = new HSSFWorkbook(is);
		  }else if("xlsx".equals(type)){
			  hssfWorkbook = new XSSFWorkbook(importFile.getPath());
		  }else{
			  hssfWorkbook = new XSSFWorkbook(importFile.getPath());
		  }
		  String msg  = "";
		  int beginRow = 0; //开始行
		  int customerTypeCol = -1;//客户类型读取位置
		  int customerTypeRow = -1;
		  int goodsCol = 0;
		  int divideCol = -1;
		  int numCol = 0 ;
		  int priceCol = 0;
		  int boxnumCol = -1;
		  int otherCol = -1 ;//和拆分所在列一起用
		  int detailcount = 0 ;//拆分时导入多少条商品明细统计
		  List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();//导入失败的商品明细
		  for(int i = 0 ; i< info.length ; ++ i) {
			  String value = info[i].split("=")[1];//获取参数值
			  if (info[i].contains("开始单元格")) {
				  String beginRowStr = value.replaceAll("[a-zA-Z]+","");
				  beginRow = Integer.parseInt(beginRowStr) - 1;
			  }else if(info[i].contains("商品条形码")){
				  goodsCol = ExcelUtils.chagneCellColtoNumber(value);
			  }else if (info[i].contains("拆分所在列")) {
				  divideCol = ExcelUtils.chagneCellColtoNumber(value);
			  }else if(info[i].contains("商品数量")){
				  numCol = ExcelUtils.chagneCellColtoNumber(value);
			  }else if(info[i].contains("商品箱数")){
				  boxnumCol = ExcelUtils.chagneCellColtoNumber(value);
			  }else if(info[i].contains("商品单价") && "1".equals(pricetype)){
				  priceCol = ExcelUtils.chagneCellColtoNumber(value);
			  }else if(info[i].contains("商品助记符")){
				  goodsCol = ExcelUtils.chagneCellColtoNumber(value);
			  }else if(info[i].contains("商品店内码")){
				  goodsCol = ExcelUtils.chagneCellColtoNumber(value);
			  }else if(info[i].contains("商品编码")){
				  goodsCol = ExcelUtils.chagneCellColtoNumber(value);
			  }else if (info[i].contains("客户单元格")) {
				  String Column = value.replaceAll("\\d+", "");
				  customerTypeCol = ExcelUtils.chagneCellColtoNumber(Column);
				  String customerRowStr = value.replaceAll("[a-zA-Z]+", "");
				  customerTypeRow = Integer.parseInt(customerRowStr);
			  } else if (info[i].contains("日期/其它列")) {
				  otherCol = ExcelUtils.chagneCellColtoNumber(value);
			  }
		  }

          String customerParam = "";//读取到的客户参数值
          Map map1 = new HashMap();

          //获取客户参数具体信息
		  List<String> customerRowInfo = ExcelUtils.importFirstRowByIndex(importFile,customerTypeRow-1, 0);
		  if(customerRowInfo.size()>= customerTypeCol && customerTypeCol > -1){
			  customerParam = customerRowInfo.get(customerTypeCol);
		  }
          //根据客户类型 获取导入的客户编码
		  if("null" != customerParam){
			  if("1".equals(ctype)){
				  busid = request.getParameter("busid");
			  }else if("2".equals(ctype)){
				  map1.put("pid",busid);//总店
				  map1.put("shopno",customerParam);//店号
				  busid = returnCustomerID(map1);
			  }else if("3".equals(ctype)){
				  map1.put("shortcode",customerParam);
				  busid = returnCustomerID(map1);
			  }else if("4".equals(ctype)){
				  map1.put("name",customerParam);
				  busid = returnCustomerID(map1);
			  }else if("5".equals(ctype)){
				  map1.put("shortname",customerParam);
				  busid = returnCustomerID(map1);
			  }else if("6".equals(ctype)){
				  map1.put("address",customerParam);
				  busid = returnCustomerID(map1);
			  } else if ("7".equals(ctype)) {
				  map1.put("id", customerParam);
				  busid = returnCustomerID(map1);
			  }
		  }
		  //根据分隔列导入多张订单
		  if (divideCol > -1) {
			  Map modelResultMap = getModelOrderMapByDivideCol(beginRow, divideCol, goodsCol, numCol, priceCol, boxnumCol,
					  customerTypeCol,pricetype, gtype,ctype ,otherCol,"",busid,"");
			  Iterator<Map.Entry<String, List>> it = modelResultMap.entrySet().iterator();
			  while (it.hasNext()) {
				  //退货通知单单据数据
				  RejectBill rejectBill = new RejectBill();
				  rejectBill.setStatus("2");

				  rejectBill.setStorageid(storageid);
				  rejectBill.setDriverid(driverid);
				  rejectBill.setBilltype(billtype);

				  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

				  if (isAutoCreate("t_sales_rejectbill")) {
					  // 获取自动编号
					  String id = getAutoCreateSysNumbderForeign(rejectBill, "t_sales_rejectbill");
					  rejectBill.setId(id);
				  } else {
					  rejectBill.setId("THTZD-" + CommonUtils.getDataNumberSendsWithRand());
				  }
				  //组装退货单明细
				  Map.Entry<String, List> entry = it.next();
				  List wareList = entry.getValue();
				  if (wareList.size() > 0) {
					  ModelOrder modelOrder = (ModelOrder)wareList.get(0);
					  String othermsg = modelOrder.getOtherMsg();
					  //模板导入中的业务日期或其它除备注以外的信息，如果该信息是日期，则作为订单日期导入
					  if(StringUtils.isNotEmpty(othermsg)){
						  rejectBill.setField07(othermsg);
						  try{
							  Date date = format.parse(othermsg);
							  rejectBill.setBusinessdate(othermsg);
						  }catch (Exception e){
							  rejectBill.setBusinessdate(format.format(new Date()));
						  }
					  }
					  if(StringUtils.isEmpty(rejectBill.getBusinessdate())){
						  rejectBill.setBusinessdate(CommonUtils.getTodayDataStr());
					  }
					  if(StringUtils.isNotEmpty(modelOrder.getOrderId())){
						  rejectBill.setSourceid(modelOrder.getOrderId());
					  }
					  rejectBill.setCustomerid(modelOrder.getBusid());
					  Customer customer = getCustomerById(rejectBill.getCustomerid());
					  if(null != customer){
						  rejectBill.setCustomername(customer.getName());
						  rejectBill.setSalesuser(customer.getSalesuserid());
						  rejectBill.setSalesdept(customer.getSalesdeptid());
					  }else{
						  rejectBill.setCustomerid("");
					  }
					  Map detailMap = salesRejectBillService.changeModelForDetail(wareList,gtype);
					  if(detailMap.containsKey("errorList")){
						  List<Map<String, Object>> failList = (List<Map<String, Object>>) detailMap.get("errorList");
						  for(Map fm : failList){
							  errorList.add(fm);
						  }
					  }
					  List<RejectBillDetail> detailList = (List<RejectBillDetail>) detailMap.get("detailList");
					  if(detailList.size() > 0) {
						  //模板导入中的业务日期或其它除备注以外的信息
						  if(StringUtils.isNotEmpty(modelOrder.getOtherMsg())){
							  rejectBill.setField07(modelOrder.getOtherMsg());
						  }
						  //插入数据
						  rejectBill.setBillDetailList(detailList);
						  boolean flag = salesRejectBillService.addRejectBill(rejectBill);
						  if (flag) {
							  detailcount += detailList.size();
						  }
					  }
				  }
			  }
			  if(detailcount>0){
				  msg = "导入成功"+detailcount+"条数据。";
			  }else{
				  msg = "导入失败！";
			  }
			  if(null != errorList && errorList.size() > 0){
				  String fileid = createErrorDetailFile(errorList);
				  msg += "&"+fileid ;
			  }
		  }else{
			  //分别读取对应列值
			  for(int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++){
				  String custParam = ExcelUtils.getFileValueByParam(importFile,numSheet,customerTypeRow,customerTypeCol);
				  if (StringUtils.isNotEmpty(custParam)) {
				  	  if ("2".equals(ctype)) {
						  Customer c = getCustomerById(busid);
						  map1.put("pid", c.getPid());//总店
						  map1.put("shopno", custParam);//店号
						  busid = returnCustomerID(map1);
					  } else if ("3".equals(ctype)) {
						  map1.put("shortcode", custParam);
						  busid = returnCustomerID(map1);
					  } else if ("4".equals(ctype)) {
						  map1.put("name", custParam);
						  busid = returnCustomerID(map1);
					  } else if ("5".equals(ctype)) {
						  map1.put("shortname", custParam);
						  busid = returnCustomerID(map1);
					  } else if ("6".equals(ctype)) {
						  map1.put("address", custParam);
						  busid = returnCustomerID(map1);
					  } else if ("7".equals(ctype)) {
						  map1.put("id", custParam);
						  busid = returnCustomerID(map1);
					  }
				  }
				  //根据系统参数（是否允许重复商品）组装最终的商品信息
				  String isreapt = getSysParamValue("isRepeatRejectBillDetailGoodsid");
				  List<ModelOrder> wareList = getModelOrderListByPram(isreapt,beginRow,numSheet,goodsCol,numCol,priceCol,boxnumCol,pricetype,gtype,busid,"");
				  String emptySheet = "";
				  int count = 0;
				  //退货通知单 数据
				  RejectBill rejectBill = new RejectBill();
				  rejectBill.setStatus("2");
				  rejectBill.setStorageid(storageid);
				  rejectBill.setDriverid(driverid);
				  rejectBill.setBilltype(billtype);
				  rejectBill.setBusinessdate(CommonUtils.getTodayDataStr());
				  if (isAutoCreate("t_sales_rejectbill")) {
					  // 获取自动编号
					  String id = getAutoCreateSysNumbderForeign(rejectBill, "t_sales_rejectbill");
					  rejectBill.setId(id);
				  } else {
					  rejectBill.setId("THTZD-" + CommonUtils.getDataNumberSendsWithRand());
				  }
				  custParam = ExcelUtils.getFileValueByParam(importFile,numSheet,customerTypeRow,customerTypeCol);
				  if (StringUtils.isNotEmpty(custParam)) {
					  if ("2".equals(ctype)) {
						  Customer c = getCustomerById(busid);
						  map1.put("pid", c.getPid());//总店
						  map1.put("shopno", custParam);//店号
						  busid = returnCustomerID(map1);
					  } else if ("3".equals(ctype)) {
						  map1.put("shortcode", custParam);
						  busid = returnCustomerID(map1);
					  } else if ("4".equals(ctype)) {
						  map1.put("name", custParam);
						  busid = returnCustomerID(map1);
					  } else if ("5".equals(ctype)) {
						  map1.put("shortname", custParam);
						  busid = returnCustomerID(map1);
					  } else if ("6".equals(ctype)) {
						  map1.put("address", custParam);
						  busid = returnCustomerID(map1);
					  } else if ("7".equals(ctype)) {
						  map1.put("id", custParam);
						  busid = returnCustomerID(map1);
					  }
				  }
				  if("1".equals(ctype)){
				  	rejectBill.setCustomerid(busid);
				  }
				  Customer customer = getCustomerById(busid);
				  if(null != customer){
					  rejectBill.setCustomername(customer.getName());
					  rejectBill.setSalesuser(customer.getSalesuserid());
					  rejectBill.setSalesdept(customer.getSalesdeptid());
				  }else{
				  	wareList = null;
				  }
				  if(null != wareList &&wareList.size()> 0) {
					  Map detailMap = salesRejectBillService.changeModelForDetail(wareList,gtype);
					  List<RejectBillDetail> detailList = (List<RejectBillDetail>) detailMap.get("detailList");
					  if(detailList.size() > 0){
						  //插入数据
						  rejectBill.setBillDetailList(detailList);
						  boolean flag = salesRejectBillService.addRejectBill(rejectBill);
						  if(flag){
							  if(StringUtils.isEmpty(msg)){
								  msg = "导入成功!";
							  }
							  String unimportGoods = (String) detailMap.get("unimportGoods");
							  String disableGoods = (String) detailMap.get("disableGoods");
							  if(StringUtils.isNotEmpty(unimportGoods)){
								  msg +="第"+(numSheet+1)+"张工作表没有对应商品："+unimportGoods+",";
							  }
							  if(StringUtils.isNotEmpty(disableGoods)){
								  msg +="第"+(numSheet+1)+"张工作表禁用商品："+disableGoods+";";
							  }
						  }else{
							  msg += "导入失败";
						  }

					  }else{
						  emptySheet += (numSheet + 1) + "";
					  }
				  }else{
					  emptySheet += (numSheet + 1) + "";
				  }
				  if(emptySheet !="" && count != hssfWorkbook.getNumberOfSheets()){
					  msg += "第"+(numSheet+1)+"张工作表导入不成功;";
				  }else if(count == hssfWorkbook.getNumberOfSheets()){
					  msg = "导入失败!";
				  }
			  }

		  }
		  request.setAttribute("msg",msg);

		  return SUCCESS;
	  }


	/**
	 * 销售退货通知单页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	public String rejectBill() throws Exception{
		String  id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		

		//打印是否显示打印选项
		String showPrintDailogOptions=getSysParamValue("showSaleTHTZDPrintOptions");		
		if(null==showPrintDailogOptions || "".equals(showPrintDailogOptions.trim())){
			showPrintDailogOptions="0";
		}
		request.setAttribute("showPrintDailogOptions", showPrintDailogOptions.trim());
		//销售退货通知单是否允许商品重复
		String isRepeatRejectBillDetailGoodsid=getSysParamValue("isRepeatRejectBillDetailGoodsid");
		if(StringUtils.isEmpty(isRepeatRejectBillDetailGoodsid)){
			isRepeatRejectBillDetailGoodsid = "0";
		}
		request.setAttribute("isRepeatRejectBillDetailGoodsid", isRepeatRejectBillDetailGoodsid);
		return SUCCESS;
	}
	
	/**
	 * 根据销售退货通知单编码获取该通知单单据状态
	 * @return false，不允许操作，true,允许操作
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jun 11, 2014
	 */
	public String getRejectBillOperation()throws Exception{
		String id = request.getParameter("id");
		Boolean flag = true;
		if(StringUtils.isNotEmpty(id)){
			RejectBill rejectBill = salesRejectBillService.getRejectBill(id);
			if("3".equals(rejectBill.getStatus())){//审核通过状态
				flag = false;
			}
		}
		addJSONObject("flag",flag);
		return SUCCESS;
	}
	
	/**
	 * 销售退货通知单参照回单查询页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	public String rejectBillSourceQueryPage() throws Exception{
		
		return SUCCESS;
	}
	
	public String rejectBillSourcePage() throws Exception{
		
		return SUCCESS;
	}
	
	/**
	 * 销售退货通知单页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	public String rejectBillAddPage() throws Exception{
		request.setAttribute("date", CommonUtils.getTodayDataStr());
		request.setAttribute("autoCreate", isAutoCreate("t_sales_rejectbill"));
		
		SysUser sysUser = getSysUser();
		request.setAttribute("userid", sysUser.getUserid());
		String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
		if("1".equals(OpenDeptStorage)){
			DepartMent departMent = getBaseDepartMentService().showDepartMentInfo(sysUser.getDepartmentid());
            if (null != departMent) {
            	request.setAttribute("defaultStorageid", departMent.getStorageid());
            }else{
            	request.setAttribute("defaultStorageid", "");
            }
		}else{
			request.setAttribute("defaultStorageid", "");
		}
		return SUCCESS;
	}
	
	/**
	 * 销售退货通知单参照回单页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	public String rejectBillReferPage() throws Exception{
		String id = request.getParameter("id");
		if(StringUtils.isEmpty(id)){
			return INPUT;
		}
		Receipt receipt = salesReceiptService.getReceipt(id);
		List<RejectBillDetail> detailList = receiptDetailToBillDetail(receipt.getReceiptDetailList());
		String jsonStr = JSONUtils.listToJsonStr(detailList);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("receipt", receipt);
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_sales_rejectbill"));
		request.setAttribute("userName", getSysUser().getName());
		return SUCCESS;
	}
	
	/**
	 * 销售退货通知单商品详细信息添加页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 27, 2013
	 */
	public String rejectBillDetailAddPage() throws Exception{
		String customerId = request.getParameter("cid");
		List list = getBaseSysCodeService().showSysCodeListByType("rejecttype");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.add(calendar.DATE, 2);
		request.setAttribute("customerId", customerId);
		request.setAttribute("list", list);
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		//字段权限
		Map colMap = getEditAccessColumn("t_sales_rejectbill_detail");
		request.setAttribute("colMap", colMap);
        request.setAttribute("presentByZero",getSysParamValue("presentByZero"));  //赠品单价是否为0系统参数
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		request.setAttribute("rejectCategory", getBaseSysCodeService().showSysCodeListByType("rejectCategory"));
		return SUCCESS;
	}
	
	/**
	 * 销售退货通知单商品详细信息修改页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 27, 2013
	 */
	public String rejectBillDetailEditPage() throws Exception{
		List list = getBaseSysCodeService().showSysCodeListByType("rejecttype");
		request.setAttribute("list", list);
		//字段权限
		Map colMap = getEditAccessColumn("t_sales_rejectbill_detail");
		request.setAttribute("colMap", colMap);
		
		String goodsid = request.getParameter("goodsid");
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		if(null!=goodsInfo){
			request.setAttribute("isbatch", goodsInfo.getIsbatch());
		}else{
			request.setAttribute("isbatch", "0");
		}
		request.setAttribute("goodsid", goodsid);
        request.setAttribute("presentByZero",getSysParamValue("presentByZero"));
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		request.setAttribute("rejectCategory", getBaseSysCodeService().showSysCodeListByType("rejectCategory"));
		return SUCCESS;
	}
	
	/**
	 * 添加销售退货通知单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	@UserOperateLog(key="RejectBill",type=2)
	public String addRejectBill() throws Exception{
		if (isAutoCreate("t_sales_rejectbill")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(rejectBill, "t_sales_rejectbill");
			rejectBill.setId(id);
		}
		String addType = request.getParameter("addType");
		String saveaudit = request.getParameter("saveaudit");
		String storager = request.getParameter("storager");
		if("temp".equals(addType)){ //暂存
			rejectBill.setStatus("1");
		}
		else if("real".equals(addType)){ //保存
			rejectBill.setStatus("2");
		}
		String billDetailJson = request.getParameter("goodsjson");
		List<RejectBillDetail> billDetailList = JSONUtils.jsonStrToList(billDetailJson, new RejectBillDetail());
		rejectBill.setBillDetailList(billDetailList);
		boolean flag = salesRejectBillService.addRejectBill(rejectBill);
		if(flag && "saveaudit".equals(saveaudit)){
			Map map = salesRejectBillService.auditRejectBill("1", rejectBill.getId(),storager);
			addLog("销售退货通知单 新增保存审核 编号："+rejectBill.getId(), map);
			map.put("backid", rejectBill.getId());
			if(map.containsKey("flag")){
				map.put("auditflag", map.get("flag"));
			}
			map.put("type", "add");
			addJSONObject(map);
		}else{
			Map map = new HashMap();
			map.put("flag", flag);
			map.put("backid", rejectBill.getId());
			map.put("type", "add");
			addJSONObject(map);
			addLog("销售退货通知单新增 编号："+rejectBill.getId(), flag);
		}
		return SUCCESS;
	}
	
	/**
	 * 销售退货通知单修改页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	public String rejectBillEditPage() throws Exception{
		String id = request.getParameter("id");
		RejectBill rejectBill = salesRejectBillService.getRejectBill(id);

		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);
		
		if(null==rejectBill){
			return "viewSuccess";
		}else{
			List statusList = getBaseSysCodeService().showSysCodeListByType("status");
			String jsonStr = JSONUtils.listToJsonStr(rejectBill.getBillDetailList());
			request.setAttribute("goodsJson", jsonStr);
			request.setAttribute("rejectBill", rejectBill);
			request.setAttribute("status", statusList);
			request.setAttribute("settletype", getSettlementListData());
			request.setAttribute("paytype", getPaymentListData());
			request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
			//仓库列表
			List storageList = getStorageInfoAllList();
			request.setAttribute("storageList", storageList);
			List userList = getPersListByOperationTypeAndDeptid("1", rejectBill.getSalesdept());
			request.setAttribute("userList", userList);
			
			//获取默认退货仓库 RETURNSTORAGE退货仓 INCOMPLETESTORAGE残次仓库
			String storageid = getSysParamValue("INCOMPLETESTORAGE");
			
			if(null!=storageid){
				request.setAttribute("storageid", storageid);
			}
			if("1".equals(rejectBill.getStatus()) || "2".equals(rejectBill.getStatus()) || "6".equals(rejectBill.getStatus())){
				return "editSuccess";
			}else{
				return "viewSuccess";
			}
		}
	}
	
	/**
	 * 修改退货通知单信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String updateRejectBill() throws Exception{
		String addType = request.getParameter("addType");
		if("1".equals(rejectBill.getStatus())){
			if("real".equals(addType)){
				rejectBill.setStatus("2");
			}
		}
		String saveaudit = request.getParameter("saveaudit");
		String storager = request.getParameter("storager");
		SysUser sysUser = getSysUser();
		rejectBill.setModifyuserid(sysUser.getUserid());
		rejectBill.setModifyusername(sysUser.getName());
		String billDetailJson = request.getParameter("goodsjson");
		List<RejectBillDetail> billDetailList = JSONUtils.jsonStrToList(billDetailJson, new RejectBillDetail());
		rejectBill.setBillDetailList(billDetailList);
		boolean flag = salesRejectBillService.updateRejectBill(rejectBill);
		if(flag && "saveaudit".equals(saveaudit)){
			Map map = salesRejectBillService.auditRejectBill("1", rejectBill.getId(),storager);
			addLog("销售退货通知单保存审核 编号："+rejectBill.getId(), map);
			map.put("backid", rejectBill.getId());
			if(map.containsKey("flag")){
				map.put("auditflag", map.get("flag"));
			}
			addJSONObject(map);
		}else{
			Map map = new HashMap();
			map.put("flag", flag);
			map.put("backid", rejectBill.getId());
			addJSONObject(map);
			addLog("销售退货通知单修改 编号："+rejectBill.getId(), flag);
		}
		return SUCCESS;
	}
	
	/**
	 * 销售退货通知单查看页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	public String rejectBillViewPage() throws Exception{
		String id = request.getParameter("id");
		RejectBill rejectBill = salesRejectBillService.getRejectBill(id);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		String jsonStr = JSONUtils.listToJsonStr(rejectBill.getBillDetailList());
		request.setAttribute("goodsJson", jsonStr);
		request.setAttribute("rejectBill", rejectBill);
		request.setAttribute("status", statusList);
		
		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);
		return SUCCESS;
	}
	
	/**
	 * 销售退货通知单列表页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	public String rejectBillListPage() throws Exception{
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		//打印是否显示打印选项
		String showPrintDailogOptions=getSysParamValue("showSaleTHTZDPrintOptions");		
		if(null==showPrintDailogOptions || "".equals(showPrintDailogOptions.trim())){
			showPrintDailogOptions="0";
		}
		request.setAttribute("showPrintDailogOptions", showPrintDailogOptions.trim());
		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);
		return SUCCESS;
	}
	
	/**
	 * 获取销售退货通知单列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	public String getRejectBillList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());		
		String printsign=(String)map.get("printsign");
		if(null!=printsign && !"".equals(printsign.trim())){
			String printtimes=(String)map.get("queryprinttimes");
			if(!(StringUtils.isNotEmpty(printtimes) && StringUtils.isNumeric(printtimes))){
				map.remove("printsign");
				map.remove("queryprinttimes");
			}
		}
		pageMap.setCondition(map);
		PageData pageData = salesRejectBillService.getRejectBillData(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 删除销售退货通知单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	@UserOperateLog(key="RejectBill",type=4)
	public String deleteRejectBill() throws Exception{
		String id = request.getParameter("id");
		boolean delFlag = canTableDataDelete("t_sales_rejectbill", id); //判断是否被引用，被引用则无法删除。
		if(!delFlag){
			addJSONObject("delFlag", true);
			return SUCCESS;
		}
		boolean flag = salesRejectBillService.deleteRejectBill(id);
		addJSONObject("flag", flag);
		addLog("销售退货通知单删除 编号："+id, flag);
		return SUCCESS;
	}
	
	/**
	 * 审核或反审退货通知单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 6, 2013
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String auditRejectBill() throws Exception{
		String type = request.getParameter("type"); //1为审核2为反审
		String id = request.getParameter("id");
		String storager = request.getParameter("storager");
		String check = request.getParameter("check");
		boolean checkflag=true;
		if("1".equals(check)){
			checkflag = salesRejectBillService.checkRejectRepeat(id);
		}
		Map checkStorageMap = salesRejectBillService.checkRejectStorage(id);
		if(checkflag&&(Boolean)checkStorageMap.get("flag")){
			Map result = salesRejectBillService.auditRejectBill(type, id,storager);
			addJSONObject(result);
			if("1".equals(type)){
				addLog("销售退货通知单审核 编号："+id, result);
			}else{
				addLog("销售退货通知单反审 编号："+id, result);
			}
		}else{
			if(!checkflag){
			    Map map = new HashMap();
			    map.put("flag", false);
			    map.put("checkflag", "0");
			    addJSONObject(map);
			    addLog("销售退货通知单审核 编号："+id, false);
			}
			if(!(Boolean)checkStorageMap.get("flag")){
			    Map map = new HashMap();
			    if(null!=checkStorageMap.get("msg"))
			    	map.put("msg", checkStorageMap.get("msg"));
			    map.put("flag", false);
			    map.put("checkstorage", "0");
			    addJSONObject(map);
			    addLog("销售退货通知单审核 编号："+id, false);
			}
		}
		return SUCCESS;
	}
	/**
	 * 批量审核退货通知单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年8月12日
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String auditMultiRejectBill() throws Exception{
		String ids = request.getParameter("ids");
		String storager = request.getParameter("storager");
		int failure = 0;
		int success = 0;
		boolean sFlag = true;
		String msg = "";
		String failstr = "";
		String successstr = "";
		if(StringUtils.isNotEmpty(ids)){
			if(ids.endsWith(",")){
				ids = ids.substring(0, ids.length() - 1);
			}
			String[] idArr = ids.split(",");
			for(String id : idArr){
				boolean checkflag = salesRejectBillService.checkRejectRepeat(id);
				if(checkflag){
					Map map = salesRejectBillService.auditRejectBill("1", id,storager);
					boolean flag = false;
					if(map.containsKey("flag")){
						flag = (Boolean)map.get("flag");
					}
					if(flag){
						success++;
						successstr += id+",";
					}
					else{
						failure++;
						if(map.containsKey("msg")){
							msg += "<br/>"+map.get("msg");
						}else{
							msg += "<br/>单据:"+id+"已审核";
						}
						failstr += id+",";
					}
				}else{
					failure++;
					msg += "<br/>单据:"+id+"可能重复";
					failstr += id+",";
				}
			}
		}
		Map map = new HashMap();
		map.put("flag", sFlag);
		map.put("failure", failure);
		map.put("success", success);
		map.put("msg", msg);
		addJSONObject(map);
		addLog("销售退货通知单批量审核 编号"+ids+"，成功条数:"+success+",成功编号:"+successstr+",失败条数"+failure+",失败编号:"+failstr, sFlag);
		
		return "success";
	}
	/**
	 * 检验退货通知单审核前是否买过该商品
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Apr 7, 2014
	 */
	public String checkBuyGoodsRejectBill()throws Exception{
		String id = request.getParameter("id");
		Map map = salesRejectBillService.checkBuyGoodsRejectBill(id);
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 回单明细转为退货单明细
	 * @param receiptDetailList
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 7, 2013
	 */
	private List<RejectBillDetail> receiptDetailToBillDetail(List<ReceiptDetail> receiptDetailList) throws Exception{
		List<RejectBillDetail> billDetailList = new ArrayList<RejectBillDetail>();
		for(ReceiptDetail receiptDetail : receiptDetailList){
			if(receiptDetail.getUnitnum().compareTo(receiptDetail.getReceiptnum()) == 1){
				RejectBillDetail rejectBillDetail = new RejectBillDetail();
				rejectBillDetail.setGoodsInfo(receiptDetail.getGoodsInfo());
				BigDecimal unitnum = receiptDetail.getUnitnum().subtract(receiptDetail.getReceiptnum());
				rejectBillDetail.setGoodsid(receiptDetail.getGoodsid());
				rejectBillDetail.setUnitid(receiptDetail.getUnitid());
				rejectBillDetail.setUnitname(receiptDetail.getUnitname());
				rejectBillDetail.setUnitnum(unitnum);
				Map map = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), unitnum);
				if(map.containsKey("auxnum")){
					rejectBillDetail.setAuxnum(new BigDecimal(map.get("auxnum").toString()));
				}
				if(map.containsKey("auxnumdetail")){
					rejectBillDetail.setAuxnumdetail(map.get("auxnumdetail").toString());
				}
				rejectBillDetail.setAuxunitid(receiptDetail.getAuxunitid());
				rejectBillDetail.setAuxunitname(receiptDetail.getAuxunitname());
				rejectBillDetail.setBatchno(receiptDetail.getBatchno());
				rejectBillDetail.setDeliverydate(receiptDetail.getDeliverydate());
				rejectBillDetail.setExpirationdate(receiptDetail.getExpirationdate());
				rejectBillDetail.setRemark(receiptDetail.getRemark());
				rejectBillDetail.setTax(receiptDetail.getTax());
				rejectBillDetail.setTaxamount(receiptDetail.getTaxamount());
				rejectBillDetail.setTaxprice(receiptDetail.getTaxprice());
				rejectBillDetail.setNotaxamount(receiptDetail.getNotaxamount());
				rejectBillDetail.setNotaxprice(receiptDetail.getNotaxprice());
				rejectBillDetail.setTaxtype(receiptDetail.getTaxtype());
				rejectBillDetail.setTaxtypename(receiptDetail.getTaxtypename());
				rejectBillDetail.setBillno(receiptDetail.getBillid());
				rejectBillDetail.setBilldetailno(receiptDetail.getId());
				rejectBillDetail.setField01(receiptDetail.getField01());
				rejectBillDetail.setField02(receiptDetail.getField02());
				rejectBillDetail.setField03(receiptDetail.getField03());
				rejectBillDetail.setField04(receiptDetail.getField04());
				rejectBillDetail.setField05(receiptDetail.getField05());
				rejectBillDetail.setField06(receiptDetail.getField06());
				rejectBillDetail.setField07(receiptDetail.getField07());
				rejectBillDetail.setField08(receiptDetail.getField08());
				billDetailList.add(rejectBillDetail);
			}
		}
		return billDetailList;
	}
	
	/**
	 * 提交工作流
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 29, 2013
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String submitRejectBillProcess() throws Exception{
		String id = request.getParameter("id");
		RejectBill bill = salesRejectBillService.getRejectBill(id);
		if(!bill.getStatus().equals("2")){
			addJSONObject("flag", false);
			return SUCCESS;
		}
		SysUser user = getSysUser();
		Map<String, Object> variables = new HashMap<String, Object>();
		String title = "销售订单 "+bill.getId()+" (" + bill.getBusinessdate() + ")";
		boolean flag = salesRejectBillService.submitRejectBillProcess(title, user.getUserid(), "salesRejectBill", id, variables);
		addJSONObject("flag", flag);
		addLog("销售退货通知单提交工作流 编号："+id, flag);
		return SUCCESS;
	}
	/**
	 * 根据通知单编号查询明细
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-9-30
	 */
	public String showRejectBillDetailListByBill() throws Exception{
		String billid=request.getParameter("id");
		List<RejectBillDetail> list=salesRejectBillService.getRejectBillDetailListByBill(billid);
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 根据编号 获取直退的销售退货通知单 未与回单关联的明细列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Oct 28, 2013
	 */
	public String showDirectRejectBillDetailByIds() throws Exception{
		String ids = request.getParameter("ids");
		List list = salesRejectBillService.showDirectRejectBillDetailByIds(ids);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 显示销售退货通知单 拆分页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 14, 2014
	 */
	public String rejectBillSplitPage() throws Exception{
		String id = request.getParameter("id");
		RejectBill rejectBill = salesRejectBillService.getRejectBill(id);
		if(null!=rejectBill && (null==rejectBill.getReceiptid() || "".equals(rejectBill.getReceiptid()))){
			List<RejectBillDetail> list = rejectBill.getBillDetailList();
			for(RejectBillDetail rejectBillDetail:list){
				rejectBillDetail.setSplitnum(rejectBillDetail.getUnitnum());
			}
			String jsonStr = JSONUtils.listToJsonStr(list);
			request.setAttribute("goodsJson", jsonStr);
		}else{
			request.setAttribute("goodsJson", "");
			request.setAttribute("split", false);
		}
		request.setAttribute("id", id);
		request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 拆分销售退货通知单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 14, 2014
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String updateRejectBillSplit() throws Exception{
		String rejectid = request.getParameter("rejectid");
		String billDetailJson = request.getParameter("detailJson");
		List<RejectBillDetail> billDetailList = JSONUtils.jsonStrToList(billDetailJson, new RejectBillDetail());
		Map map = salesRejectBillService.updateRejectBillSplit(rejectid, billDetailList);
		if(null!=map){
			String id = (String) map.get("id");
			addJSONObject(map);
			addLog("销售退货通知单拆分 编号："+rejectid+"，生成新的退货通知单:"+id, map);
		}
		return "success";
	}
	/**
	 * 显示销售退货验收列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 14, 2014
	 */
	public String rejectBillCheckListPage() throws Exception{
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return "success";
	}
	/**
	 * 销售退货通知单页面
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Jun 5, 2013
	 */
	public String rejectBillCheckPage() throws Exception{
		String  id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
        request.setAttribute("rejectCategory", getBaseSysCodeService().showSysCodeListByType("rejectCategory"));
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);
		return SUCCESS;
	}
	/**
	 * 显示销售退货通知单验收页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 14, 2014
	 */
	public String rejectBillCheckEditPage() throws Exception{
		String id = request.getParameter("id");
		RejectBill rejectBill = salesRejectBillService.getRejectBill(id);
		request.setAttribute("salestypeList", getBaseSysCodeService().showSysCodeListByType("salestype"));	//销售类型

		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);
		if(null==rejectBill){
			return "viewSuccess";
		}else{
			List statusList = getBaseSysCodeService().showSysCodeListByType("status");
			String jsonStr = JSONUtils.listToJsonStr(rejectBill.getBillDetailList());
			request.setAttribute("goodsJson", jsonStr);
			request.setAttribute("rejectBill", rejectBill);
			request.setAttribute("status", statusList);
			request.setAttribute("settletype", getSettlementListData());
			request.setAttribute("paytype", getPaymentListData());
			request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
			//仓库列表
			List storageList = getStorageInfoAllList();
			request.setAttribute("storageList", storageList);
			List userList = getPersListByOperationTypeAndDeptid("1", rejectBill.getSalesdept());
			request.setAttribute("userList", userList);
			
			//获取默认退货仓库 RETURNSTORAGE退货仓 INCOMPLETESTORAGE残次仓库
			String storageid = getSysParamValue("INCOMPLETESTORAGE");
			
			if(null!=storageid){
				request.setAttribute("storageid", storageid);
			}
            //赠品单价是否为0系统参数
            request.setAttribute("presentByZero",getSysParamValue("presentByZero"));
			if("0".equals(rejectBill.getIsinvoice())){
				return "success";
			}else{
				return "checkView";
			}
		}
	}
	/**
	 * 更新销售退货通知单 保存并验收
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 15, 2014
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String updateRejectBillCheck() throws Exception{
		String addType = request.getParameter("addType");
		if("1".equals(rejectBill.getStatus())){
			if("real".equals(addType)){
				rejectBill.setStatus("2");
			}
		}
		SysUser sysUser = getSysUser();
		rejectBill.setModifyuserid(sysUser.getUserid());
		rejectBill.setModifyusername(sysUser.getName());
		String billDetailJson = request.getParameter("goodsjson");
		List<RejectBillDetail> billDetailList = JSONUtils.jsonStrToList(billDetailJson, new RejectBillDetail());
		rejectBill.setBillDetailList(billDetailList);
		Map map = salesRejectBillService.updateRejectBillCheck(rejectBill);
		boolean flag = (Boolean) map.get("flag");
		addJSONObject(map);
		addLog("销售退货通知单保存并验收 编号："+rejectBill.getId(), flag);
		return "success";
	}
	/**
	 * 取消销售退货通知单验收
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Apr 18, 2014
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String updateRejectBillCheckCancel() throws Exception{
		String id = request.getParameter("id");
		Map returnMap = salesRejectBillService.updateRejectBillCheckCancel(id);
		addJSONObject(returnMap);
		addLog("销售退货通知单取消验收 编号："+id, returnMap);
		return "success";
	}
	/**
	 * 显示手机退货申请单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 15, 2014
	 */
	public String rejectBillPhonePage() throws Exception{
		request.setAttribute("salesDept", getBaseDepartMentService().getDeptListByOperType("4"));
		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);		

		//打印是否显示打印选项
		String showPrintDailogOptions=getSysParamValue("showSaleTHTZDPrintOptions");		
		if(null==showPrintDailogOptions || "".equals(showPrintDailogOptions.trim())){
			showPrintDailogOptions="0";
		}
		request.setAttribute("showPrintDailogOptions", showPrintDailogOptions.trim());
		
		return "success";
	}
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 16, 2014
	 */
	public String rejectBillPhone() throws Exception{
		String  id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		

		String printlimit=getPrintLimitInfo();
		request.setAttribute("printlimit", printlimit);		

		//打印是否显示打印选项
		String showPrintDailogOptions=getSysParamValue("showSaleTHTZDPrintOptions");		
		if(null==showPrintDailogOptions || "".equals(showPrintDailogOptions.trim())){
			showPrintDailogOptions="0";
		}
		request.setAttribute("showPrintDailogOptions", showPrintDailogOptions.trim());
		
		
		return "success";
	}
	/**
	 * 启用手机上传过来的退货通知单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 16, 2014
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String auditRejectBillPhone() throws Exception{
		String id = request.getParameter("id");
		boolean flag = salesRejectBillService.auditRejectBillPhone(id);
		addJSONObject("flag", flag);
		addLog("手机退货通知单审核 编号："+id, flag);
		return "success";
	}
	/**
	 * 批量审核手机手机上传过来的退货通知单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 16, 2014
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String auditMutRejectBillPhone() throws Exception{
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		String successid = "";
		String errorid = "";
		for(String id : idArr){
			boolean flag = salesRejectBillService.auditRejectBillPhone(id);
			if(flag){
				successid += id+",";
			}else{
				errorid += id+",";
			}
		}
		String msg = "成功编号："+successid+"，失败编号:"+errorid;
		Map map = new HashMap();
		map.put("flag", true);
		map.put("msg", msg);
		addJSONObject(map);
		addLog("手机退货通知单审核 成功编号："+successid+",失败编号:"+errorid, true);
		return "success";
	}
	/**
	 * 删除手机退货通知单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 16, 2014
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String deleteRejectBillPhone() throws Exception{
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		String successid = "";
		String errorid = "";
		for(String id : idArr){
			boolean flag = salesRejectBillService.deleteRejectBill(id);
			if(flag){
				successid += id+",";
			}else{
				errorid += id+",";
			}
		}
		String msg = "成功编号："+successid+"，失败编号:"+errorid;
		Map map = new HashMap();
		map.put("flag", true);
		map.put("msg", msg);
		addJSONObject(map);
		addLog("手机退货通知单删除 成功编号："+successid+",失败编号:"+errorid, true);
		return "success";
	}
	/**
	 * 销售退货验收导出
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 11, 2014
	 */
	public String exportRejectBillList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());		
		String printsign=(String)map.get("printsign");
		if(null!=printsign && !"".equals(printsign.trim())){
			String printtimes=(String)map.get("queryprinttimes");
			if(!(StringUtils.isNotEmpty(printtimes) && StringUtils.isNumeric(printtimes))){
				map.remove("printsign");
				map.remove("queryprinttimes");
			}
		}
		map.put("isflag", "true");
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
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
		PageData pageData = salesRejectBillService.getRejectBillData(pageMap);
		ExcelUtils.exportExcel(exportRejectBillListFielter(pageData.getList()), title);
        return SUCCESS ;
	}
	/**
	 * 导出销售退货验收列表数据 excel格式
	 * @param list
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 3, 2014
	 */
	private List<Map<String, Object>> exportRejectBillListFielter(List<RejectBill> list) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("id", "编号");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("customerid", "客户编码");
		firstMap.put("customername", "客户名称");
		firstMap.put("handlerid", "对方经手人");
		firstMap.put("billtype", "退货类型");
		firstMap.put("salesdept", "销售部门");
		firstMap.put("salesuser", "客户业务员");
		firstMap.put("totaltaxamount", "金额");
		firstMap.put("indoorusername", "销售内勤");
		firstMap.put("status", "状态");
		firstMap.put("addusername", "制单人");
		firstMap.put("addtime", "制单时间");
        firstMap.put("checkdate","验收日期");
        firstMap.put("stopusername","验收人");
		firstMap.put("auditusername", "审核人");
		firstMap.put("audittime", "审核时间");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		if(null!=list && list.size() != 0){
			for(RejectBill rejectBill : list){
				if("2".equals(rejectBill.getStatus())){
					rejectBill.setStatus("保存");
				}else if("3".equals(rejectBill.getStatus())){
					rejectBill.setStatus("审核通过");
				}else if("4".equals(rejectBill.getStatus())){
					rejectBill.setStatus("关闭");
				}else if("1".equals(rejectBill.getStatus())){
					rejectBill.setStatus("暂存");
				}
				if("1".equals(rejectBill.getBilltype())){
					rejectBill.setBilltype("直退");
				}else if("2".equals(rejectBill.getBilltype())){
					rejectBill.setBilltype("售后退货");
				}
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map = new HashMap<String, Object>();
				map = PropertyUtils.describe(rejectBill);
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
	 * 车销退货申请单页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年5月13日
	 */
	public String rejectBillCarListPage() throws Exception{	

		//打印是否显示打印选项
		String showPrintDailogOptions=getSysParamValue("showSaleTHTZDPrintOptions");		
		if(null==showPrintDailogOptions || "".equals(showPrintDailogOptions.trim())){
			showPrintDailogOptions="0";
		}
		request.setAttribute("showPrintDailogOptions", showPrintDailogOptions.trim());
		
		return "success";
	}
	/**
	 * 删除手机退货通知单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 16, 2014
	 */
	@UserOperateLog(key="RejectBill",type=4)
	public String deleteRejectBillCar() throws Exception{
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		String successid = "";
		String errorid = "";
		for(String id : idArr){
			boolean flag = salesRejectBillService.deleteRejectBill(id);
			if(flag){
				successid += id+",";
			}else{
				errorid += id+",";
			}
		}
		String msg = "成功编号："+successid+"，失败编号:"+errorid;
		Map map = new HashMap();
		map.put("flag", true);
		map.put("msg", msg);
		addJSONObject(map);
		addLog("车销退货通知单删除 成功编号："+successid+",失败编号:"+errorid, true);
		return "success";
	}
	/**
	 * 批量审核车销退货通知单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年5月13日
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String auditMutRejectBillCar() throws Exception{
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		String successid = "";
		String errorid = "";
		for(String id : idArr){
			boolean flag = salesRejectBillService.auditRejectBillCar(id);
			if(flag){
				successid += id+",";
			}else{
				errorid += id+",";
			}
		}
		String msg = "成功编号："+successid+"，失败编号:"+errorid;
		Map map = new HashMap();
		map.put("flag", true);
		map.put("msg", msg);
		addJSONObject(map);
		addLog("车销退货通知单审核 成功编号："+successid+",失败编号:"+errorid, true);
		return "success";
	}
	/**
	 * 审核车销退货通知单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jan 16, 2014
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String auditRejectBillCar() throws Exception{
		String id = request.getParameter("id");
		boolean flag = salesRejectBillService.auditRejectBillCar(id);
		addJSONObject("flag", flag);
		addLog("车销退货通知单审核 编号："+id, flag);
		return "success";
	}
	/**
	 * 反审车销退货通知单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年5月13日
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String oppauditRejectBillCar() throws Exception{
		String id = request.getParameter("id");
		Map map = salesRejectBillService.auditRejectBill("2", id,"");
		addJSONObject(map);
		addLog("车销退货通知单反审 编号："+id, map);
		return "success";
	}
	/**
	 * 显示车销退货通知单页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年5月13日
	 */
	public String rejectBillCar() throws Exception{
		String  id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		
		String printlimit=getPrintLimitInfo();		
		request.setAttribute("printlimit", printlimit);
		

		//打印是否显示打印选项
		String showPrintDailogOptions=getSysParamValue("showSaleTHTZDPrintOptions");		
		if(null==showPrintDailogOptions || "".equals(showPrintDailogOptions.trim())){
			showPrintDailogOptions="0";
		}
		request.setAttribute("showPrintDailogOptions", showPrintDailogOptions.trim());
		
		return "success";
	}
	/**
	 * 保存并审核车销退货通知单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年5月13日
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String updateRejectBillCarAudit() throws Exception{
		SysUser sysUser = getSysUser();
		rejectBill.setModifyuserid(sysUser.getUserid());
		rejectBill.setModifyusername(sysUser.getName());
		String billDetailJson = request.getParameter("goodsjson");
		List<RejectBillDetail> billDetailList = JSONUtils.jsonStrToList(billDetailJson, new RejectBillDetail());
		rejectBill.setBillDetailList(billDetailList);
		boolean flag = salesRejectBillService.updateRejectBill(rejectBill);
		boolean auditflag = false;
		if(flag){
			auditflag = salesRejectBillService.auditRejectBillCar(rejectBill.getId());
		}
		addLog("车销退货通知单保存并审核 编号:"+rejectBill.getId(), auditflag);
		Map map = new HashMap();
		map.put("flag", flag);
		map.put("auditflag", auditflag);
		map.put("backid", rejectBill.getId());
		addJSONObject(map);
		return "success";
	}
	/**
	 * 批量验收退货通知单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年1月21日
	 */
	@UserOperateLog(key="RejectBill",type=3)
	public String checkMultiRejectBill() throws Exception{
		String ids = request.getParameter("ids");
		Map map1  = salesRejectBillService.auditCheckRejectBill(ids);
		String msg = (String)map1.get("msg");
		if(StringUtils.isEmpty(msg)){
			msg = "退货通知单编号："+ids+"验收失败";
		}
		Map map = new HashMap();
		map.put("msg", msg);
		addJSONObject(map);
		addLog(msg, true);
		return "success";
	}
    /**
    /**
     * 显示订单退货页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-29
     */
    public String showAddRejectBillCaseOrderPage()throws Exception{
        return SUCCESS;
    }

    /**
     * 根据单据（订单）编号 获取明细列表
     * @return
     * @throws Exception
     */
    public String showSalesOrderDetailListPage()throws Exception{
        String id = request.getParameter("id");
        List list = new ArrayList();
        BigDecimal totalamount = BigDecimal.ZERO;
        Order order = salesOrderService.getOrder(id);
        if(null != order){
            List<OrderDetail> detailList = order.getOrderDetailList();
            for(OrderDetail orderDetail : detailList){
                Map map = BeanUtils.describe(orderDetail);
                if(null!=orderDetail.getGoodsInfo()){
                    map.put("goodsname",orderDetail.getGoodsInfo().getName() );
                    map.put("boxnum",orderDetail.getGoodsInfo().getBoxnum() );
                }
                map.put("taxamount", orderDetail.getTaxamount());
                map.put("notaxamount", orderDetail.getNotaxamount());
                map.put("unitnum", orderDetail.getUnitnum());
                totalamount = totalamount.add(orderDetail.getTaxamount());
                list.add(map);
            }
        }
        String jsonStr = JSONUtils.listToJsonStr(list);
        request.setAttribute("detailList", jsonStr);
        request.setAttribute("totalamount", totalamount);
        request.setAttribute("id", id);
        return SUCCESS;
    }
    /**
     * 根据选中的订单明细显示退货通知单明细列表页面
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-04-30
     */
    public String showRejectBillDetailListPage()throws Exception{
        String ids = request.getParameter("id");
        String customerid = request.getParameter("customerid");
        //未选中的单据与明细
        String uncheckedjson = request.getParameter("uncheckedjson");
        Map unCheckMap = new HashMap();
        if(null!=uncheckedjson && !"".equals(uncheckedjson)){
            JSONArray array = JSONArray.fromObject(uncheckedjson);
            for(int i=0; i<array.size(); i++){
                JSONObject jsonObject = (JSONObject)array.get(i);
                if(!jsonObject.isEmpty()){
                    String detailid = jsonObject.getString("id");
                    String orderid = jsonObject.getString("orderid");
                    if(!unCheckMap.containsKey(detailid)){
                        Map billMap = new HashMap();
                        billMap.put(orderid, orderid);
                        unCheckMap.put(detailid, billMap);
                    }else{
                        Map billMap = (Map) unCheckMap.get(detailid);
                        billMap.put(orderid, orderid);
                        unCheckMap.put(detailid, billMap);
                    }
                }
            }
        }
        Map customerMap = new HashMap();
        Customer customer = getCustomerById(customerid);
        if(null!=customer){
            customerMap.put(customerid, customer.getName());
            customerMap.put("salesuser",customer.getSalesuserid());
            customerMap.put("salesdept",customer.getSalesdeptid());
        }
        if(null!=ids){
            String[] idArr =ids.split(",");
            List list = new ArrayList();
            BigDecimal totalamount = BigDecimal.ZERO;
            for(String id : idArr){
                Order order = salesOrderService.getOrder(id);
                if(null != order){
                    request.setAttribute("order", order);
                    Customer rcustomer = getCustomerById(order.getCustomerid());
                    if(null != rcustomer){
                        customerMap.put(order.getCustomerid(), customer.getName());
                    }
                    List<OrderDetail> detailList = order.getOrderDetailList();
                    for(OrderDetail orderDetail : detailList){
                        if("0".equals(orderDetail.getIsdiscount())){
                            Map map = new HashMap();
                            map.put("goodsid",orderDetail.getGoodsid());
                            map.put("id",orderDetail.getId());
                            map.put("orderid",orderDetail.getOrderid());
                            map.put("unitname",orderDetail.getUnitname());
                            map.put("unitid",orderDetail.getUnitid());
                            map.put("taxprice",orderDetail.getTaxprice());
                            map.put("boxprice",orderDetail.getBoxprice());
                            map.put("goodsInfo", orderDetail.getGoodsInfo());
                            map.put("taxamount", orderDetail.getTaxamount());
                            map.put("notaxamount", orderDetail.getNotaxamount());
                            map.put("unitnum", orderDetail.getUnitnum());
                            map.put("taxtype", orderDetail.getTaxtype());
                            map.put("notaxprice", orderDetail.getNotaxprice());
                            map.put("notaxamount", orderDetail.getNotaxamount());
                            map.put("tax", orderDetail.getTax());
                            map.put("remark",orderDetail.getRemark());
                            map.put("auxunitid", orderDetail.getAuxunitid());
                            map.put("auxunitname",orderDetail.getAuxunitname());
							map.put("deliverytype",orderDetail.getDeliverytype());
                            Map auxMap = countGoodsInfoNumber(orderDetail.getGoodsid(), orderDetail.getAuxunitid(), orderDetail.getUnitnum());
                            String auxnumdetail = (String) auxMap.get("auxnumdetail");
                            String auxnum = (String)auxMap.get("auxInteger");
                            String auxremainder = (String)auxMap.get("auxremainder");
                            map.put("auxnumdetail", auxnumdetail);
                            map.put("auxnum",auxnum);
                            map.put("auxremainder",auxremainder);
                            boolean addflag = true;
                            if(unCheckMap.containsKey(orderDetail.getId())){
                                Map billMap = (Map) unCheckMap.get(orderDetail.getId());
                                if(billMap.containsKey(orderDetail.getOrderid())){
                                    addflag = false;
                                }
                            }
                            if(addflag){
                                list.add(map);
                                totalamount = totalamount.add(orderDetail.getTaxamount());
                            }
                        }
                    }
                }
            }
            String jsonStr = JSONUtils.listToJsonStr(list);
            request.setAttribute("liststr", jsonStr);
            request.setAttribute("list", list);
            request.setAttribute("totalamount", totalamount);
            request.setAttribute("customerid", customerid);
            request.setAttribute("customerMap", customerMap);
            request.setAttribute("date", CommonUtils.getTodayDataStr());
        }
        return SUCCESS;
    }

    /**
     * 退货通知单导出
     * @return
     * @throws Exception
     * @author lin_xx
     * @date 2015年6月12日
     */
    public String exportRejectBill() throws Exception{

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }
        else{
            title = "list";
        }
        if(org.apache.commons.lang3.StringUtils.isEmpty(title)){
            title = "list";
        }

        List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id", "编号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("customerid", "客户编码");
        firstMap.put("customername", "客户");
        firstMap.put("state", "状态");
        firstMap.put("driver", "司机");
        firstMap.put("salesdeptname", "销售部门");
		firstMap.put("sourceid", "客户单号");
        firstMap.put("salesusername", "客户业务员");
        firstMap.put("billtypename", "退货类型");
        firstMap.put("storagename", "入库仓库");
        firstMap.put("goodsid", "商品编码");
        firstMap.put("goodsname", "商品名称");
        firstMap.put("barcode", "条形码");
        firstMap.put("itemno", "商品货位");
        firstMap.put("boxnum", "箱装量");
        firstMap.put("unitname", "单位");
        firstMap.put("unitnum", "数量");
        firstMap.put("taxprice", "单价");
        firstMap.put("boxprice", "箱价");
        firstMap.put("taxamount", "金额");
        firstMap.put("batchno","批次号");
        firstMap.put("produceddate","生产日期");
        firstMap.put("deadline","截止日期");
        firstMap.put("auxnumdetail", "辅数量");
        firstMap.put("remark", "备注");

        result.add(firstMap);

        List<Map<String, Object>> rejectBillList = salesRejectBillService.getRejectBillByExport(pageMap);
        for(Map<String, Object> map2 : rejectBillList){
            Map<String, Object> retMap = new LinkedHashMap<String, Object>();
            for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                    for(Map.Entry<String, Object> entry : map2.entrySet()){
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
        ExcelUtils.exportExcel(result, title);
        return SUCCESS;
    }

    /**
     * 根据客户编码、商品编码显示历史价格表数据页面
     * @return
     * @throws Exception
     * @author pan_xx
     * @date 2015年6月16日
     */
    public String showRejectBillHistoryGoodsPricePage()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        List<Map> list = salesRejectBillService.getRejectBillHistoryGoodsPriceList(map);
        String listjson = JSONUtils.listToJsonStr(list);
        request.setAttribute("listjson",listjson);
        return SUCCESS;
    }

    /**
     * 根据已税金额和税种获取未税金额
     * @return
     * @throws Exception
     */
    public String getNotaxAmountByTaxAmount() throws Exception{

        String goodsid = request.getParameter("goodsid");
        String taxamount = request.getParameter("taxamount");

        GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
        if(null != goodsInfo){
            String taxtype = goodsInfo.getDefaulttaxtype();
            BigDecimal notaxamount = getNotaxAmountByTaxAmount(new BigDecimal(taxamount),taxtype);
            addJSONObject("notaxamount",notaxamount);
        }else{
            addJSONObject("notaxamount","0");
        }

        return SUCCESS;
    }

	/**
	 * 导入销售退货通知单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2017-01-05
	 */
	@UserOperateLog(key = "Rejectbill", type = 2)
	public String importRejectBill()throws Exception{
		Map<String, Object> retMap = new HashMap<String, Object>();
		//获取第一行数据为字段的描述列表
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile);
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("业务日期".equals(str)){
				paramList2.add("businessdate");
			}else if("客户编码".equals(str)){
				paramList2.add("customerid");
			}else if("客户名称".equals(str)){
				paramList2.add("customername");
			}else if("司机".equals(str)){
				paramList2.add("driver");
			}else if("销售部门".equals(str)){
				paramList2.add("salesdeptname");
			}else if("客户业务员".equals(str)){
				paramList2.add("salesusername");
			}else if("退货类型".equals(str)){
				paramList2.add("billtypename");
			}else if("入库仓库".equals(str)){
				paramList2.add("storagename");
			}else if("商品编码".equals(str)){
				paramList2.add("goodsid");
			}else if("商品名称".equals(str)){
				paramList2.add("goodsname");
			}else if("条形码".equals(str)){
				paramList2.add("barcode");
			}else if("数量".equals(str)){
				paramList2.add("unitnum");
			}else if("单价".equals(str)){
				paramList2.add("taxprice");
			}else if("备注".equals(str)){
				paramList2.add("remark");
			}else{
				paramList2.add("null");
			}
		}
		List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
		List result = new ArrayList();
		if(null != list && list.size() != 0){
			Map map2 =  salesRejectBillService.importRejectBill(list);
			String sucbillid = (String)map2.get("sucbillid");
			if(StringUtils.isNotEmpty(sucbillid)){
				addLog("退货通知单导入 编号："+sucbillid , true);
			}
			retMap.putAll(map2);
		} else{
			retMap.put("excelempty", true);
		}
		addJSONObject(retMap);
		return SUCCESS;
	}

    /**
     * 根据单价计算金额
     * @param
     * @return
     * @throws Exception
     */
    public String getAmountByPrice() throws Exception{
        //价格类型 1含税单价2箱价
        String type = request.getParameter("type");
        String priceStr = request.getParameter("price");
        String unitnumStr = request.getParameter("unitnum");
        String goodsid = request.getParameter("goodsid");
        GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
        if(StringUtils.isNotEmpty(priceStr) && null!=goodsInfo){
            BigDecimal price = new BigDecimal(priceStr);
            BigDecimal unitnum = new BigDecimal(unitnumStr);
            BigDecimal taxamount = BigDecimal.ZERO;
            BigDecimal notaxamount = BigDecimal.ZERO;
            BigDecimal notaxprice = BigDecimal.ZERO;
            BigDecimal tax = BigDecimal.ZERO;
            BigDecimal taxprice = BigDecimal.ZERO;
            BigDecimal boxprice = BigDecimal.ZERO;
            if("1".equals(type)){
                taxprice = price;
                boxprice = goodsInfo.getBoxnum().multiply(taxprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);

            }else if("2".equals(type)){
                boxprice = price;
                if(goodsInfo.getBoxnum().compareTo(BigDecimal.ZERO)!=0){
                    taxprice = price.divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
                }else{
                    taxprice = price;
                }
            }
            taxamount = unitnum.multiply(taxprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
            notaxamount = getNotaxAmountByTaxAmount(taxamount,goodsInfo.getDefaulttaxtype());
            tax = taxamount.subtract(notaxamount);
            if(unitnum.compareTo(BigDecimal.ZERO)!=0){
                notaxprice = notaxamount.divide(unitnum,6,BigDecimal.ROUND_HALF_UP);
            }else{
                notaxprice = getNotaxAmountByTaxAmount(taxprice,goodsInfo.getDefaulttaxtype());
            }
            Map map = new HashMap();
            map.put("taxprice",taxprice);
            map.put("unitnum",unitnum);
            map.put("taxamount",taxamount);
            map.put("notaxamount",notaxamount);
            map.put("notaxprice",notaxprice);
            map.put("tax",tax);
            map.put("boxprice",boxprice);
            addJSONObject(map);
        }
        else{
            addJSONObject(new HashMap());
        }
        return "success";
    }

    /**
     * 根据金额计算单价
     * @param
     * @return
     * @throws Exception
     */
    public String getPriceByAmount() throws Exception{
        //价格类型 1含税单价2箱价
        String taxamountStr = request.getParameter("taxamount");
        String unitnumStr = request.getParameter("unitnum");
        String goodsid = request.getParameter("goodsid");
        GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
        if(StringUtils.isNotEmpty(taxamountStr) && null!=goodsInfo){
            BigDecimal unitnum = new BigDecimal(unitnumStr);
            BigDecimal taxamount = new BigDecimal(taxamountStr);
            BigDecimal notaxamount = BigDecimal.ZERO;
            BigDecimal notaxprice = BigDecimal.ZERO;
            BigDecimal tax = BigDecimal.ZERO;
            BigDecimal taxprice = BigDecimal.ZERO;
            BigDecimal boxprice = BigDecimal.ZERO;
            if(unitnum.compareTo(BigDecimal.ZERO)!=0){
                taxprice = taxamount.divide(unitnum,6,BigDecimal.ROUND_HALF_UP);
                boxprice = goodsInfo.getBoxnum().multiply(taxprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
                notaxamount = getNotaxAmountByTaxAmount(taxamount,goodsInfo.getDefaulttaxtype());
                tax = taxamount.subtract(notaxamount);
                notaxprice = notaxamount.divide(unitnum,6,BigDecimal.ROUND_HALF_UP);
            }
            Map map = new HashMap();
            map.put("taxprice",taxprice);
            map.put("unitnum",unitnum);
            map.put("taxamount",taxamount);
            map.put("notaxamount",notaxamount);
            map.put("notaxprice",notaxprice);
            map.put("tax",tax);
            map.put("boxprice",boxprice);
            addJSONObject(map);
        }else{
            addJSONObject(new HashMap());
        }
        return "success";

    }
}

