/**
 * @(#)showOffPricePageAction.java
 *
 * @author huangzhiqian
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年12月1日 huangzhiqian 创建版本
 */
package com.hd.agent.client.action;

import com.hd.agent.activiti.action.BaseAction;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.client.model.ClientOffprice;
import com.hd.agent.client.service.IOffPriceLogService;
import com.hd.agent.client.service.IOffPriceService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 门店特价
 * @author huangzhiqian
 */
public class ClientOffPriceAction extends BaseAction {

	private ClientOffprice clientOffprice;
	
	public ClientOffprice getClientOffprice() {
		return clientOffprice;
	}

	public void setClientOffprice(ClientOffprice clientOffprice) {
		this.clientOffprice = clientOffprice;
	}

	private IOffPriceService offPriceService;

	private IOffPriceLogService offPriceLogService;
	
	public IOffPriceLogService getOffPriceLogService() {
		return offPriceLogService;
	}

	public void setOffPriceLogService(IOffPriceLogService offPriceLogService) {
		this.offPriceLogService = offPriceLogService;
	}

	public IOffPriceService getOffPriceService() {
		return offPriceService;
	}

	public void setOffPriceService(IOffPriceService offPriceService) {
		this.offPriceService = offPriceService;
	}

	/**
	 * 特价页面
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月1日
	 */
	public String offpriceListPage(){
		return SUCCESS;
	}

	/**
	 * 特价商品新增对话框
	 * @return
	 * @author huangzhiqian
	 * @date 2016年2月3日
	 */
	public String offpriceDetailAddPage() throws Exception{
		Personnel personnel = getPersonnelInfoById(getSysUser().getPersonnelid());
		String deptid="";
		if(null!=personnel){
			deptid=personnel.getBelongdeptid();
		}
		request.setAttribute("deptid", deptid);
		return SUCCESS;
	}
	
	/**
	 * 特价商品新增对话框
	 * @return
	 * @author huangzhiqian
	 * @throws Exception 
	 * @date 2016年2月3日
	 */
	public String addOffPrice() throws Exception{

		boolean expired = isOffPriceExpired(clientOffprice);
		if(expired) {

			Map map = new HashMap();
			map.put("flag", false);
			map.put("msg","该门店特价信息已过期！");
			addJSONObject(map);
			return SUCCESS;
		}

		List<ClientOffprice> list = offPriceService.selectOffPriceGoodsByGoodsIdAndDeptId(clientOffprice.getDeptid(),clientOffprice.getGoodsid());

		// 判断时间段是否重叠
		for(ClientOffprice off : list) {

			boolean dateConflicted = isDatetimeSpanConflicted(
					clientOffprice.getBegindate(),
					clientOffprice.getEnddate(),
					off.getBegindate(),
					off.getEnddate()
			);
			boolean timeConflicted = isDatetimeSpanConflicted(
					clientOffprice.getBegintime(),
					clientOffprice.getEndtime(),
					off.getBegintime(),
					off.getEndtime()
			);

			if(dateConflicted && timeConflicted) {

				Map map = new HashMap();
				map.put("flag", false);
				map.put("msg","商品价格设定冲突，添加失败！请重新检查后再添加。");
				addJSONObject(map);
				return SUCCESS;
			}
		}

		clientOffprice.setAddtime(new Date());
		boolean ret = offPriceService.addOffPriceGoods(clientOffprice);

		Map map = new HashMap();
		map.put("flag", ret);
		map.put("msg","添加失败！");
		if(ret){
			map.put("msg","添加成功。");
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * datagrid
	 * @return
	 * @author huangzhiqian
	 * @throws Exception 
	 * @date 2015年12月1日
	 */
	public String getOffPriceList() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String today = CommonUtils.getTodayDataStr();
		String time = CommonUtils.getNowTime().substring(0, 5);
		map.put("today", today);
		map.put("time", time);
		map.put("now", today + " " + time);
		pageMap.setCondition(map);
		PageData pageData = offPriceService.getOffPriceList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 更新特价商品
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月1日
	 */
	public String updateOffPriceInfo()throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
		Pattern p = Pattern.compile("(0\\d{1}|1\\d{1}|2[0-3]):[0-5]\\d{1}");
		//校验
		try{
			Matcher matcher = p.matcher(clientOffprice.getBegintime());
			if(!matcher.matches()){
				throw new Exception();
			}
			matcher = p.matcher(clientOffprice.getEndtime());
			if(!matcher.matches()){
				throw new Exception();
			}
			fmt.parse(clientOffprice.getBegindate());
			fmt.parse(clientOffprice.getEnddate());
		} catch (Exception e) {
			map.put("flag", false);
			map.put("msg", "格式有误");
			addJSONObject(map);
			return SUCCESS;
		}

//		offPriceService.selectClientOffPriceById(clientOffprice.getId())

		if(clientOffprice.getBegindate().compareTo(clientOffprice.getEnddate()) > 0) {

			map.put("flag", false);
			map.put("msg","终止日期不能小于起始日期！");
			addJSONObject(map);
			return SUCCESS;
		}

		if(clientOffprice.getBegintime().compareTo(clientOffprice.getEndtime()) > 0) {

			map.put("flag", false);
			map.put("msg","终止时间不能小于起始时间！");
			addJSONObject(map);
			return SUCCESS;
		}

		boolean expired = isOffPriceExpired(clientOffprice);
		if(expired) {

			map.put("flag", false);
			map.put("msg","该门店特价信息已过期！");
			addJSONObject(map);
			return SUCCESS;
		}

		boolean flag = offPriceService.updateSaleOffGoods(clientOffprice);
		map.put("flag", flag);
		if(flag){
			map.put("msg", "更新成功");
		}else{
			map.put("msg", "更新失败");
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 日志页面
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月7日
	 */
	public String offpriceLogPage(){
		return SUCCESS;
	}
	
	/**
	 * 查询日志
	 * @return
	 * @author huangzhiqian
	 * @date 2015年12月7日
	 */
	public String getOffPriceLogList()throws Exception{

		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		pageMap.setOrderSql("  id desc ");
		
		PageData pageData = offPriceLogService.getOffPriceLogList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 判断日期或事件是否重叠
	 * @return	false: 不重叠；true: 重叠
	 * @author limin
	 * @date Apr 5, 2016
	 */
	public void exportClientOffPrice() throws Exception {

		String title = "";

		Map<String, String> condition = CommonUtils.changeMap(request.getParameterMap());

		if(condition.containsKey("excelTitle")){
			title = condition.get("excelTitle").toString();
		}
		else{
			title = "门店特价";
		}

		if(StringUtils.isEmpty(title)){
			title = "门店特价";
		}

		String today = CommonUtils.getTodayDataStr();
		String time = CommonUtils.getNowTime().substring(0, 5);
		condition.put("today", today);
		condition.put("time", time);
		condition.put("now", today + " " + time);
		pageMap.setCondition(condition);
		pageMap.setRows(99999999);

		PageData data = offPriceService.getOffPriceList(pageMap);

		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> headCols = new LinkedHashMap<String, Object>();
		headCols.put("storename", "门店名称");
		headCols.put("goodsid", "商品编码");
		headCols.put("goodsname", "商品名称");
		headCols.put("barcode", "条形码");
		headCols.put("begindate", "起始日期");
		headCols.put("enddate", "终止日期");
		headCols.put("begintime", "起始时间");
		headCols.put("endtime", "结束时间");
		headCols.put("basesaleprice", "基准销售价");
		headCols.put("retailprice", "零售价格");
		headCols.put("status", "状态");
		result.add(headCols);

		List<ClientOffprice> list = data.getList();
		for (ClientOffprice off : list) {
			BigDecimal basesaleprice = off.getBasesaleprice();
			off.setBasesaleprice(basesaleprice.setScale(6, BigDecimal.ROUND_HALF_UP));
			Map<String, Object> map = (Map<String, Object>) JSONUtils.jsonStrToMap(JSONUtils.objectToJsonStr(off));
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			for (Map.Entry<String, Object> fentry : headCols.entrySet()) {
				if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
					for (Map.Entry<String, Object> entry : map.entrySet()) {
						if (fentry.getKey().equals(entry.getKey())) {
							objectCastToRetMap(retMap, entry);
						}
					}
				} else {
					retMap.put(fentry.getKey(), "");
				}
			}
			result.add(retMap);
		}
		ExcelUtils.exportExcel(result, title);
	}


	/**
	 * 判断日期或事件是否重叠
	 * @return	false: 不重叠；true: 重叠
	 * @author limin
	 * @date Apr 5, 2016
	 */
	public String importClientOffPrice() throws Exception {
		try {
			List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
			List<String> paramList2 = new ArrayList<String>();
			for(String str : paramList){
				if("部门名称".equals(str)){
					paramList2.add("deptname");
				}else if("商品编码".equals(str)){
					paramList2.add("goodsid");
				}else if("商品名称".equals(str)){
					paramList2.add("goodsname");
				}else if("条形码".equals(str)){
					paramList2.add("barcode");
				}else if("起始日期".equals(str)){
					paramList2.add("begindate");
				}else if("终止日期".equals(str)){
					paramList2.add("enddate");
				}else if("起始时间".equals(str)){
					paramList2.add("begintime");
				}else if("结束时间".equals(str)){
					paramList2.add("endtime");
				}else if("基准销售价".equals(str)){
					paramList2.add("basesaleprice");
				}else if("零售价格".equals(str)){
					paramList2.add("retailprice");
				}else if("状态".equals(str)){
					paramList2.add("status");
				}
			}
			Map map = new HashMap();
			List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
			if(list.size() != 0){
				map = offPriceService.importClientOffPrice(list);
			}else{
				map.put("excelempty", true);
			}
			addJSONObject(map);
			addLog("导入门店特价", map);
		} catch (Exception e) {
			Map map = new HashMap();
			map.put("dataerror", true);
			addJSONObject(map);
		}

		return SUCCESS;
	}

	/**
	 * 判断日期或事件是否重叠
	 * @param startDate1
	 * @param endDate1
	 * @param startDate2
	 * @param endDate2
     * @return	false: 不重叠；true: 重叠
	 * @author limin
	 * @date Apr 3, 2016
     */
	private boolean isDatetimeSpanConflicted(
			String startDate1,
			String endDate1,
			String startDate2,
			String endDate2
	) {

		if(endDate1.compareTo(startDate2) < 0 || startDate1.compareTo(endDate2) > 0) {
			return false;
		}

		return true;
	}

	/**
	 * 判断特价是否过期
	 * @param off
	 * @return
	 * @throws Exception
	 * @author limin
	 * @date Apr 5, 2016
     */
	public boolean isOffPriceExpired(ClientOffprice off) throws Exception {

		String now = CommonUtils.getTodayDataStr() + " " + CommonUtils.getNowTime().substring(0, 5);

		if(now.compareTo(off.getEnddate() + off.getEndtime()) > 0) {
			return true;
		}

		return false;
	}
}

