/**
 * @(#)AdjustPriceAction.java
 *
 * @author wanghongteng
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2015年10月9日 wanghongteng 创建版本
 */
package com.hd.agent.basefiles.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.basefiles.model.AdjustPrice;
import com.hd.agent.basefiles.model.AdjustPriceDetail;
import com.hd.agent.basefiles.model.AdjustPriceExport;
import com.hd.agent.basefiles.model.CustomerPrice;
import com.hd.agent.basefiles.model.GoodsInfo_PriceInfo;
import com.hd.agent.basefiles.service.IAdjustPriceService;
import com.hd.agent.basefiles.service.IGoodsService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;

/**
 * 
 * 
 * @author wanghongteng
 */
public class AdjustPriceAction extends FilesLevelAction{
   private AdjustPrice adjustPrice;
    
   private IAdjustPriceService adjustPriceService;
   
   private IGoodsService goodsService;
	
   public AdjustPrice getAdjustPrice(){
		return adjustPrice;
	}

	public void setAdjustPrice(AdjustPrice adjustPrice){
		this.adjustPrice = adjustPrice;
	}
	
	public IAdjustPriceService getAdjustPriceService(){
		return adjustPriceService;
	}

	public void setAdjustPriceService(IAdjustPriceService adjustPriceService){
		this.adjustPriceService = adjustPriceService;
	}
	
	public IGoodsService getGoodsService(){
		return goodsService;
	}

	public void setGoodsService(IGoodsService goodsService){
		this.goodsService = goodsService;
	}
   
	
	
	
	
	/**
	 * 获取调价单列表
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String showAdjustPriceListPage() throws Exception {
		return "success";
	}

	/**
	 * 获取调价单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */

	public String showAdjustPriceList() throws Exception {
		// 获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		// map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = adjustPriceService.showAdjustPriceList(pageMap);
		addJSONObject(pageData);
		return "success";
	}

	/**
	 * 显示调价单新增页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String showAdjustPriceAddPage() throws Exception {
		request.setAttribute("type", "add");
		return "success";
	}

	/**
	 * 显示调价单修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String showAdjustPriceEditPage() throws Exception {
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		return "success";
	}

	/**
	 * 调价单新增页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */

	public String adjustPriceAddPage() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		request.setAttribute("date", dateFormat.format(calendar.getTime()));
		request.setAttribute("autoCreate", isAutoCreate("t_base_adjust_price"));
		request.setAttribute("userName", getSysUser().getName());
		return "success";
	}

	/**
	 * 调价单修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String adjustPriceEditPage() throws Exception {
		String id = request.getParameter("id");
		Map map = adjustPriceService.getAdjustPriceInfo(id);
		AdjustPrice adjustPrice = (AdjustPrice) map.get("adjustPrice");
		List<AdjustPriceDetail> list = (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("adjustPrice", adjustPrice);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("listSize", list.size());
		if ("1".equals(adjustPrice.getStatus()) || "2".equals(adjustPrice.getStatus()) || "6".equals(adjustPrice.getStatus())) {
			// 加锁
			lockData("t_base_adjust_price", adjustPrice.getId());
			return "success";
		} else {
			return "viewSuccess";
		}
	}

	/**
	 * 显示调价商品添加页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String showAdjustPriceDetailAddPage() throws Exception {
		String type = request.getParameter("type");
		String typeCode = request.getParameter("typeCode");
		List<String> goodsidlist = new ArrayList<String>();
		if (type.equals("4")) {
			List<CustomerPrice> customerPricelist = adjustPriceService.getCustomerPriceListByTypeCode(typeCode);
			for (CustomerPrice customerPrice : customerPricelist) {
				goodsidlist.add(customerPrice.getGoodsid());
			}
		}
		else if(type.equals("3")) {
			List<GoodsInfo_PriceInfo> goodsInfo_PriceInfolist = adjustPriceService.getPriceListByTypeCode(typeCode);
			for (GoodsInfo_PriceInfo goodsInfo_PriceInfo : goodsInfo_PriceInfolist) {
				goodsidlist.add(goodsInfo_PriceInfo.getGoodsid());
			}
		}
		String goodsidsStr = "";
		for (String goodsid : goodsidlist) {
			if (goodsidsStr == "") {
				goodsidsStr = goodsid;
			} else {
				goodsidsStr += "," + goodsid;
			}
		}
	
		request.setAttribute("type", type);
		request.setAttribute("goodsidlist", goodsidsStr);
		return "success";
	}

	/**
	 * 显示调价商品修改页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 5, 2015
	 */
	public String showAdjustPriceDetailEditPage() throws Exception {
		Map fieldMap = getAccessColumn("t_base_goods_info");
		request.setAttribute("fieldMap", fieldMap);
		return "success";
	}

	/**
	 * 获取价格套价格
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String getPriceDataByGoodsidAndCode() throws Exception {
		String goodsid = request.getParameter("goodsid");
		String code = request.getParameter("code");
		GoodsInfo_PriceInfo goodsPrice = goodsService.getPriceDataByGoodsidAndCode(goodsid, code);
		Map returnMap = new HashMap();
		if(null!=goodsPrice)
		    returnMap.put("price", goodsPrice.getTaxprice());
		else
			returnMap.put("price", 0);
		addJSONObject(returnMap);
		return "success";
	}

	/**
	 * 根据商品编码，客户编码获取含税单价
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String getPriceDataByCustomerid() throws Exception {
		String goodsid = request.getParameter("goodsid");
		String customerid = request.getParameter("customerid");
		CustomerPrice customerPrice = adjustPriceService.getPriceDataByCustomerid(goodsid, customerid);
		Map returnMap = new HashMap();
		if(null!=customerPrice)
		    returnMap.put("price", customerPrice.getPrice());
		else
			returnMap.put("price", 0);
		addJSONObject(returnMap);
		return "success";
	}

	// 通过品牌自动生成
	/**
	 * 显示通过品牌添加调价商品页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String showAdjustPriceDetailAddPageByBrand() throws Exception {
		List list = adjustPriceService.getBrandList();
		String jsonStr = JSONUtils.listToJsonStr(list);
		request.setAttribute("detailList", jsonStr);
		return "success";
	}

	/**
	 * 通过品牌和涨幅，获取调价商品列表
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String getAdjustPriceDetailByBrand() throws Exception {
		String rate = request.getParameter("rate");
		String brands = request.getParameter("brands");
		String type = request.getParameter("type");
		String busid = request.getParameter("busid");
		List list = adjustPriceService.getAdjustPriceDetailByBrand(rate, brands, type, busid);
		addJSONArray(list);
		return "success";
	}

	/**
	 * 显示通过客户添加调价商品页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String showAdjustCustomerPriceDetailAddPageByBrand() throws Exception {
		String customerid = request.getParameter("customerid");
		List list = adjustPriceService.getBrandListByCustomerid(customerid);
		String jsonStr = JSONUtils.listToJsonStr(list);
		request.setAttribute("detailList", jsonStr);
		return "success";
	}

	/**
	 * 通过客户和涨幅，获取调价商品列表
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String getAdjustCustomerPriceDetailByBrand() throws Exception {
		String rate = request.getParameter("rate");
		String brands = request.getParameter("brands");
		String customerid = request.getParameter("customerid");
		List list = adjustPriceService.getAdjustCustomerPriceDetailByBrand(rate, brands, customerid);
		addJSONArray(list);
		return "success";
	}
	// 通过分类自动生成
	/**
	 * 显示通过分类添加调价商品页面
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String showAdjustPriceDetailAddPageByDefaultSort() throws Exception {
		return "success";
	}
	
	/**
	 * 通过品牌和涨幅，获取调价商品列表
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	public String getAdjustPriceDetailByDefaultSort() throws Exception {
		String rate = request.getParameter("rate");
		String goodssorts = request.getParameter("goodssorts");
		String type = request.getParameter("type");
		String busid = request.getParameter("busid");
		List list = adjustPriceService.getAdjustPriceDetailByDefaultSort(rate, goodssorts, type, busid);
		addJSONArray(list);
		return "success";
	}
	
	/**
	 * 保存新增的商品调价单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	@UserOperateLog(key = "AdjustPrice", type = 2)
	public String addAdjustPriceSave() throws Exception {
		adjustPrice.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new AdjustPriceDetail());
		Map map = adjustPriceService.addAdjustPrice(adjustPrice, detailList);
		String id = null != map.get("id") ? (String) map.get("id") : "";
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean) map.get("flag");
		String msg = "";
		if (flag && "saveaudit".equals(saveaudit)) {
			Map returnmap = adjustPriceService.auditAdjustPrice(adjustPrice.getId());
			map.put("auditflag", returnmap.get("flag"));
			msg = (String) returnmap.get("msg");
			map.put("msg", msg);
			addLog("调价单保存审核 编号：" + id, returnmap);
		} else {
			addLog("调价单新增编号" + id, flag);
		}
		addJSONObject(map);
		return "success";
	}
	
	/**
	 * 保存修改的商品调价单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 4, 2015
	 */
	@UserOperateLog(key = "AdjustPrice", type = 3)
	public String editAdjustPriceSave() throws Exception {
		// 加锁
		boolean lock = isLock("t_base_adjust_price", adjustPrice.getId());
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("商品调价单 编码：" + adjustPrice.getId() + "互斥，操作", false);
			return "success";
		}
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new AdjustPriceDetail());
		Map map = adjustPriceService.editAdjustPrice(adjustPrice, detailList);
		String id = null != map.get("id") ? (String) map.get("id") : "";
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean) map.get("flag");
		String msg = "";
		if (flag && "saveaudit".equals(saveaudit)) {
			Map returnmap = adjustPriceService.auditAdjustPrice(adjustPrice.getId());

			map.put("auditflag", returnmap.get("flag"));
			msg = (String) returnmap.get("msg");
			map.put("msg", msg);
			addLog("商品调价单保存审核 编号：" + id, returnmap);
		} else {
			addLog("商品调价单修改保存编号:" + id, flag);
		}
		addJSONObject(map);
		// 解锁数据
		isLockEdit("t_base_adjust_price", adjustPrice.getId()); // 判断锁定并解锁
		return "success";
	}
	
	/**
	 * 删除商品调价单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "AdjustPrice", type = 4)
	public String deleteAdjustPrice() throws Exception {
		String id = request.getParameter("id");
		// 加锁
		boolean lock = isLock("t_base_adjust_price", id);
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("商品调价单编码：" + id + "互斥，操作", false);
			return "success";
		}
		boolean flag = adjustPriceService.deleteAdjustPrice(id);
		addJSONObject("flag", flag);
		addLog("商品调价单删除 编号：" + id, flag);
		return "success";
	}
	
	/**
	 * 反审商品调价单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "AdjustPrice", type = 3)
	public String oppauditAdjustPrice() throws Exception {
		String id = request.getParameter("id");
		// 加锁
		boolean lock = isLock("t_base_adjust_price", id);
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("商品调价单 编码：" + id + "互斥，操作", false);
			return "success";
		}
		Map map = adjustPriceService.oppauditAdjustPrice(id);
		addJSONObject(map);
		addLog("商品调价单反审 编号：" + id, map);
		return "success";
	}
	
	/**
	 * 审核商品调价单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "AdjustPrice", type = 3)
	public String auditAdjustPrice() throws Exception {
		String id = request.getParameter("id");
		// 加锁
		boolean lock = isLock("t_base_adjust_price", id);
		if (lock) { // 被锁定不能进行修改
			addJSONObject("lock", true);
			addLog("商品调价单 编码：" + id + "互斥，操作", false);
			return "success";
		}
		Map map = adjustPriceService.auditAdjustPrice(id);
		addJSONObject(map);
		addLog("商品调价单审核 编号：" + id, map);
		return "success";
	}
	/**
	 * 批量审核商品调价单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "AdjustPrice", type = 3)
	public String auditsAdjustPrice() throws Exception {
		String msg = "", fmsg = "", smsg = "";
		int fnum = 0, snum = 0;
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			if (isLock("t_base_adjust_price", id)) {
				if (StringUtils.isEmpty(fmsg)) {
					fmsg = id;
				} else {
					fmsg += "," + id;
				}
				fnum++;
			} else {
				Map map = adjustPriceService.auditAdjustPrice(id);
				if (StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
					smsg = id;
				} else {
					smsg += "," + id;
				}
				snum++;
				addLog("商品调价单审核编号：" + id, map);
			}
		}
		if (fnum != 0) {

			msg = "编号：" + fmsg + "被上锁，审核失败。共" + fnum + "条数据<br>";

		}
		if (snum != 0) {
			if (StringUtils.isEmpty(msg))
				msg = "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
			else
				msg += "编号：" + smsg + "审核成功。共" + snum + "条数据<br>";
		}

		Map returnMap = new HashMap();
		returnMap.put("msg", msg);
		addJSONObject(returnMap);
		return "success";
	}
	/**
	 * 批量删除商品调价单
	 * 
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "AdjustPrice", type = 4)
	public String deletesAdjustPrice() throws Exception {
		String msg = "", fmsg = "", smsg = "";
		int fnum = 0, snum = 0;
		String ids = request.getParameter("ids");
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			if (isLock("t_base_adjust_price", id)) {
				if (StringUtils.isEmpty(fmsg)) {
					fmsg = id;
				} else {
					fmsg += "," + id;
				}
				fnum++;
			} else {
				boolean flag = adjustPriceService.deleteAdjustPrice(id);
				if (StringUtils.isEmpty(smsg) && flag) {
					smsg = id;
				} else {
					smsg += "," + id;
				}
				snum++;
				addLog("商品调价单删除编号：" + id, flag);
			}
		}
		if (fnum != 0) {

			msg = "编号：" + fmsg + "被上锁，删除失败。共" + fnum + "条数据<br>";

		}
		if (snum != 0) {
			if (StringUtils.isEmpty(msg))
				msg = "编号：" + smsg + "删除成功。共" + snum + "条数据<br>";
			else
				msg += "编号：" + smsg + "删除成功。共" + snum + "条数据<br>";
		}
		Map returnMap = new HashMap();
		returnMap.put("msg", msg);
		addJSONObject(returnMap);
		return "success";
	}
 
	/**
	 * 调价单导出
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key="AdjustPrice",type=2,value="调价单导出")
	public void exportAdjustPrice()throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		String state = request.getParameter("state");
		if(StringUtils.isNotEmpty(state)){
			map.put("state", state);
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
		pageMap.setCondition(map);
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		firstMap.put("name", "调价单名称");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("typename", "调价类型名称");
		firstMap.put("busid", "对应项目");
		firstMap.put("goodsid", "商品编号");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("oldprice", "原价");
		firstMap.put("oldboxprice", "原箱价");
		firstMap.put("boxnum", "箱装量");
		firstMap.put("nowprice", "现价");
		firstMap.put("nowboxprice", "现箱价");
		firstMap.put("rate", "涨幅﹪");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		List<AdjustPriceExport> list = adjustPriceService.getAdjustPriceExportList(pageMap);
		if(list.size() != 0){
			for(AdjustPriceExport adjustPriceExport : list){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2 = PropertyUtils.describe(adjustPriceExport);
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
		}
		ExcelUtils.exportExcel(result, title);
	}
	/**
	 * 导入代配送采购单
	 * @return
	 * @throws Exception
	 * @author wanghongteng
	 * @date Aug 7, 2015
	 */
	@UserOperateLog(key = "AdjustPrice", type = 2)
	public String importAdjustPrice()throws Exception{
		Map<String, Object> retMap = new HashMap<String, Object>();
		//获取第一行数据为字段的描述列表
		List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile);
		List<String> paramList2 = new ArrayList<String>();
		for(String str : paramList){
			if("业务日期".equals(str)){
				paramList2.add("businessdate");
			}else if("调价单名称".equals(str)){
				paramList2.add("name");
			}else if("调价类型编号".equals(str)){
				paramList2.add("type");
			}else if("调价类型名称".equals(str)){
				paramList2.add("typename");
			}else if("对应项目".equals(str)){
				paramList2.add("busid");
			}else if("商品编号".equals(str)){
				paramList2.add("goodsid");
			}else if("商品名称".equals(str)){
				paramList2.add("goodsname");
			}else if("原价".equals(str)){
				paramList2.add("oldprice");
			}else if("原箱价".equals(str)){
				paramList2.add("oldboxprice");
			}else if("现价".equals(str)){
				paramList2.add("nowprice");
			}else if("现箱价".equals(str)){
				paramList2.add("nowboxprice");
			}else if("涨幅﹪".equals(str)){
				paramList2.add("rate");
			}else if("备注".equals(str)){
				paramList2.add("remark");
			}
			else{
				paramList2.add("null");
			}
		}
	 	List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
	 	List result = new ArrayList();
	 	if(null != list && list.size() != 0){
			Map map2 =  adjustPriceService.importAdjustPrice(list);
			List<String> idlist=(List<String>)map2.get("idlist");
			String logmsg="";
			for(String id : idlist){
				if(logmsg==""){
					logmsg=id;
				}
				else{
					logmsg=logmsg+","+id;
				}
				
			}
			addLog("商品调价单导入 编号："+logmsg , map2);
			retMap.putAll(map2);
		}else{
			retMap.put("excelempty", true);
		}
	 	addJSONObject(retMap);
		return SUCCESS;
	}
}

