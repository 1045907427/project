/**
 * @(#)StockInitAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 7, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.service.IExcelService;
import com.hd.agent.common.util.*;
import com.hd.agent.storage.model.StockInit;
import com.hd.agent.storage.service.IStockInitService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 *
 * 库存初始化Action
 * @author chenwei
 */
public class StockInitAction extends BaseFilesAction {

	/**
	 * 库存初始化
	 */
	private StockInit stockInit;
	/**
	 * 库存初始化service
	 */
	private IStockInitService stockInitService;

	private IExcelService excelService;

	public IExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(IExcelService excelService) {
		this.excelService = excelService;
	}

	public IStockInitService getStockInitService() {
		return stockInitService;
	}
	public void setStockInitService(IStockInitService stockInitService) {
		this.stockInitService = stockInitService;
	}

	public StockInit getStockInit() {
		return stockInit;
	}
	public void setStockInit(StockInit stockInit) {
		this.stockInit = stockInit;
	}
	/**
	 * 显示库存初始化页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 7, 2013
	 */
	public String showStorageStockInitPage() throws Exception{
		//获取商品表自定义列名
		request.setAttribute("fieldmap", getRowDescFromDataDict("t_base_goods_info"));
		return "success";
	}
	/**
	 * 显示库存初始化新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 8, 2013
	 */
	public String showStockInitAddPage() throws Exception{
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 添加库存初始化数据
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 14, 2013
	 */
	@UserOperateLog(key="StockInit",type=2)
	public String addStockInit() throws Exception{
		boolean flag = stockInitService.addStockInit(stockInit);
		addJSONObject("flag", flag);
		addLog("库存初始化新增 商品编码："+stockInit.getGoodsid()+",仓库编码:"+stockInit.getStorageid(), flag);
		return "success";
	}
	/**
	 * 获取库存初始化数据列表
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 9, 2013
	 */
	public String showStockInitList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = stockInitService.showStockInitList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 计算库存初始化数据
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 11, 2013
	 */
	public String computeStockInitData() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String auxunitid = request.getParameter("auxunitid");
		String priceStr = request.getParameter("price");
		String unitnumStr = request.getParameter("unitnum");
		BigDecimal price = new BigDecimal(0);
		if(null!=priceStr && !"".equals(priceStr)){
			price = new BigDecimal(priceStr);
		}
		BigDecimal unitnum = new BigDecimal(0);
		if(null!=unitnumStr && !"".equals(unitnumStr)){
			unitnum = new BigDecimal(unitnumStr);
		}
		//获取金额 辅单位数量 辅单位数量描述
		Map map = countGoodsInfoNumber(goodsid, auxunitid, price, unitnum);
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		if(null != goodsInfo){
			BigDecimal boxnum = (null != goodsInfo.getBoxnum()) ? goodsInfo.getBoxnum() : BigDecimal.ZERO;
			BigDecimal unitnumaux = BigDecimal.ZERO;
			BigDecimal auxremainder = BigDecimal.ZERO;
			if(!(boxnum.compareTo(BigDecimal.ZERO) == 0)){
				unitnumaux = unitnum.divideAndRemainder(boxnum)[0];
				auxremainder = unitnum.divideAndRemainder(boxnum)[1];
			}
			map.put("unitnumaux", unitnumaux);
			map.put("auxremainder", auxremainder);

            BigDecimal unitamount = unitnum.multiply(price);
            BigDecimal notaxamount = getNotaxAmountByTaxAmount(unitamount,goodsInfo.getDefaulttaxtype());
            BigDecimal tax = unitamount.subtract(notaxamount);
            map.put("notaxamount", notaxamount);
            map.put("tax", tax);
            map.put("taxtype", goodsInfo.getDefaulttaxtype());
            map.put("taxtypename", goodsInfo.getDefaulttaxtypeName());
		}
		addJSONObject(map);
		return "success";
	}

	/**
	 * 根据辅数量计算主数量
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Sep 1, 2014
	 */
	public String computStockInitNumByAux()throws Exception{
		String goodsid = request.getParameter("goodsid");
		String unitnumauxStr = request.getParameter("unitnumaux");
		String auxremainderStr = request.getParameter("auxremainder");
		String priceStr = request.getParameter("price");
		BigDecimal price = new BigDecimal(0);
		if(null!=priceStr && !"".equals(priceStr)){
			price = new BigDecimal(priceStr);
		}
		BigDecimal unitnumaux = new BigDecimal(0);
		if(null!=unitnumauxStr && !"".equals(unitnumauxStr)){
			unitnumaux = new BigDecimal(unitnumauxStr);
		}
		BigDecimal auxremainder = new BigDecimal(0);
		if(null!=auxremainderStr && !"".equals(auxremainderStr)){
			auxremainder = new BigDecimal(auxremainderStr);
		}
		Map map = new HashMap();
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		if(null != goodsInfo){
			BigDecimal boxnum = (null != goodsInfo.getBoxnum()) ? goodsInfo.getBoxnum() : BigDecimal.ZERO;
			BigDecimal unitnum = unitnumaux.multiply(boxnum).add(auxremainder);
			map.put("unitnum", unitnum);
			BigDecimal auxnum = BigDecimal.ZERO;
			if(!(boxnum.compareTo(BigDecimal.ZERO) == 0)){
				auxnum = unitnum.divideAndRemainder(boxnum)[0];
			}
			auxremainder = unitnum.divideAndRemainder(boxnum)[1];
			map.put("auxremainder",auxremainder);
			map.put("auxnum", auxnum);
			BigDecimal unitamount = unitnum.multiply(price);
			map.put("unitamount", unitamount);

			BigDecimal notaxamount = getNotaxAmountByTaxAmount(unitamount,goodsInfo.getDefaulttaxtype());
			BigDecimal tax = unitamount.subtract(notaxamount);
            map.put("notaxamount", notaxamount);
            map.put("tax", tax);
            map.put("taxtype", goodsInfo.getDefaulttaxtype());
            map.put("taxtypename", goodsInfo.getDefaulttaxtypeName());
		}
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 删除库存初始化数据
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 13, 2013
	 */
	@UserOperateLog(key="StockInit",type=4)
	public String deleteStockInit() throws Exception{
		String ids = request.getParameter("ids");
		int delSuccess = 0;
		int delFail = 0;
		if(null!=ids){
			String[] idArray = ids.split(",");
			for(String id : idArray){
				boolean flag = stockInitService.deleteStockInit(id);
				if(flag){
					delSuccess = delSuccess +1;
				}else{
					delFail = delFail +1;
				}
			}
		}
		Map map = new HashMap();
		map.put("delSuccess", delSuccess);
		map.put("delFail", delFail);
		addJSONObject(map);
		addLog("库存初始化删除 编号："+ids, true);
		return "success";
	}
	/**
	 * 根据仓库编号获取该仓库下的库存初始化列表
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 13, 2013
	 */
	public String showStockInitListByStorageid() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = stockInitService.showStockInitListByStorageid(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 显示仓库初始化数据修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 14, 2013
	 */
	public String showStockInitEditPage() throws Exception{
		String id = request.getParameter("id");
		StockInit stockInit = stockInitService.showStockInitInfo(id);
		String isbatch = "0";
		if(null!=stockInit && null!=stockInit.getGoodsInfo() && "1".equals(stockInit.getGoodsInfo().getIsbatch())){
			isbatch = "1";
		}
		request.setAttribute("isbatch", isbatch);
		request.setAttribute("stockInit", stockInit);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 修改仓库初始化数据
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 14, 2013
	 */
	@UserOperateLog(key="StockInit",type=3)
	public String editStockInit() throws Exception{
		SysUser sysUser = getSysUser();
		boolean flag = false;
		if(null!=stockInit){
			stockInit.setModifyuserid(sysUser.getUserid());
			stockInit.setModifyusername(sysUser.getName());
			flag = stockInitService.editStockInit(stockInit);
		}
		addJSONObject("flag", flag);
		addLog("库存初始化修改 编号："+stockInit.getId(), flag);
		return "success";
	}
	/**
	 * 审核库存初始化数据
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 14, 2013
	 */
	@UserOperateLog(key="StockInit",type=3)
	public String auditStockInit() throws Exception{
		String ids = request.getParameter("ids");
		int auditSuccess = 0;
		int auditFail = 0;
		if(null!=ids){
			String[] idArray = ids.split(",");
			for(String id : idArray){
				boolean flag = stockInitService.auditStockInit(id);
				if(flag){
					auditSuccess = auditSuccess +1;
				}else{
					auditFail = auditFail +1;
				}
			}
		}
		Map map = new HashMap();
		map.put("auditSuccess", auditSuccess);
		map.put("auditFail", auditFail);
		addJSONObject(map);
		addLog("库存初始化审核,成功条数："+auditSuccess+",失败条数:"+auditFail+",编号："+ids, true);
		return "success";
	}
	/**
	 * 反审库存初始化数据
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 15, 2013
	 */
	@UserOperateLog(key="StockInit",type=3)
	public String oppauditStockInit() throws Exception{
		String ids = request.getParameter("ids");
		int oppauditSuccess = 0;
		int oppauditFail = 0;
		if(null!=ids){
			String[] idArray = ids.split(",");
			for(String id : idArray){
				boolean flag = stockInitService.oppauditStockInit(id);
				if(flag){
					oppauditSuccess = oppauditSuccess +1;
				}else{
					oppauditFail = oppauditFail +1;
				}
			}
		}
		Map map = new HashMap();
		map.put("oppauditSuccess", oppauditSuccess);
		map.put("oppauditFail", oppauditFail);
		addJSONObject(map);
		addLog("库存初始化反审,成功条数："+oppauditSuccess+",失败条数:"+oppauditFail+",编号："+ids, true);
		return "success";
	}
	/**
	 * 验证库存初始化批次号是否存在
	 * true 不存在 false存在
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 15, 2013
	 */
	public String checkStockInitBatchno() throws Exception{
		String id = request.getParameter("id");
		boolean flag = stockInitService.checkStockInitBatchno(id);
		addJSONObject("flag", flag);
		return "success";
	}

	/**
	 * 导入库存初始化
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2015-09-07
	 */
	public String importStockInit()throws Exception{
		Map<String,Object> retMap = new HashMap<String,Object>();
		try{
			String clazz = "stockInitService",meth = "addStockInitByImport",module = "storage",pojo = "StockInit";
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
				if("仓库名称".equals(str)){
					paramList2.add("storagename");
				}
				else if("商品编码".equals(str)){
					paramList2.add("goodsid");
				}
				else if("商品名称".equals(str)){
					paramList2.add("goodsname");
				}
				else if("库存单价".equals(str)){
					paramList2.add("price");
				}
				else if("主单位".equals(str)){
					paramList2.add("unitname");
				}
				else if("数量".equals(str)){
					paramList2.add("unitnum");
				}
				else if("助记符".equals(str)){
					paramList2.add("spellcode");
				}
				else if("库存金额".equals(str)){
					paramList2.add("unitamount");
				}
				else if("辅单位".equals(str)){
					paramList2.add("auxunitname");
				}
				else if("辅单位数量".equals(str)){
					paramList2.add("auxnumdetail");
				}else if("批次号".equals(str)){
					paramList2.add("batchno");
				}else if("生产日期".equals(str)){
					paramList2.add("produceddate");
				}else if("截止日期".equals(str)){
					paramList2.add("deadline");
				}else{
					paramList2.add("null");
				}
			}

			if(paramList.size() == paramList2.size()){
				List result = new ArrayList();
				List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
				if(list.size() != 0){
					Map detialMap = new HashMap();
					for(Map<String, Object> map4 : list){
						Object object = entity.newInstance();
						Field[] fields = entity.getDeclaredFields();
						//获取的导入数据格式转换
						DRCastToStockInit(map4,fields);
						//BeanUtils.populate(object, map4);
						PropertyUtils.copyProperties(object, map4);
						result.add(object);
					}
					if(result.size()  != 0){
						retMap = stockInitService.addStockInitByImport(result);
//                        retMap = excelService.insertSalesOrder(object2, result, method);
					}
				}else{
					retMap.put("excelempty", true);
				}
			}
			else{
				retMap.put("versionerror", true);
			}
		}catch (Exception e){
			e.printStackTrace();
			retMap.put("error", true);
		}
		addJSONObject(retMap);
		return SUCCESS;
	}

	/**
	 * 库存初始化导出
	 * @throws Exception
	 */
	public void exportStockInit()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		map.put("isflag",true);
		map.put("ordersql","storageid");
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
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		//根据定义类型获取显示的字段
		firstMap.put("storagename", "仓库名称");
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("price", "库存单价");
		firstMap.put("unitname", "主单位");
		firstMap.put("unitnum", "数量");
		firstMap.put("spellcode", "助记符");
		firstMap.put("unitamount", "库存金额");
		firstMap.put("auxunitname", "辅单位");
		firstMap.put("auxnumdetail", "辅单位数量");
		firstMap.put("batchno", "批次号");
		firstMap.put("produceddate", "生产日期");
		firstMap.put("deadline", "截止日期");
		firstMap.put("audittime","审核日期");
		result.add(firstMap);
		PageData pageData = stockInitService.showStockInitListByStorageid(pageMap);
		List<StockInit> list = pageData.getList();
		if(list.size()==0){
			list.add(new StockInit());
		}else{
			list.addAll(pageData.getFooter());
		}
		for(StockInit stockInit1 : list){
			Map<String, Object> retMap = new LinkedHashMap<String, Object>();
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2 = PropertyUtils.describe(stockInit1);
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
	}
}

