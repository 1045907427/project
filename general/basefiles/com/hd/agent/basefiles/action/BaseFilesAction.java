/**
 * @(#)BaseFilesAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 11, 2013 chenwei 创建版本
 */
package com.hd.agent.basefiles.action;

import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.IBaseFilesService;
import com.hd.agent.basefiles.service.ICMRService;
import com.hd.agent.basefiles.service.IFinanceService;
import com.hd.agent.basefiles.service.IGoodsService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.service.IExcelService;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.service.IStorageSummaryService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 *
 * 基础档案公共action。
 * 提供接口方法给业务模块使用
 * @author chenwei
 */
public class BaseFilesAction extends BaseAction {

	/**
	 * 基础档案公共service。提供接口方法给业务模块使用
	 */
	private IBaseFilesService baseFilesService;

	/**
	 * 财务service
	 */
	protected IFinanceService baseFinanceService;

	/**
	 * 商品档案
	 */
	private IGoodsService baseGoodsService;
	/**
	 * 库存现存量service
	 */
	private IStorageSummaryService baseStorageSummaryService;

	/**
	 * 获取客户service
	 */
	private ICMRService baseCMRService;

	/**
	 * 导出明细service
	 */
	public IExcelService excelService;

	public IExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(IExcelService excelService) {
		this.excelService = excelService;
	}

	public IGoodsService getBaseGoodsService() {
		return baseGoodsService;
	}
	public void setBaseGoodsService(IGoodsService baseGoodsService) {
		this.baseGoodsService = baseGoodsService;
	}

	public IBaseFilesService getBaseFilesService() {
		return baseFilesService;
	}

	public void setBaseFilesService(IBaseFilesService baseFilesService) {
		this.baseFilesService = baseFilesService;
	}
	public IFinanceService getBaseFinanceService() {
		return baseFinanceService;
	}

	public void setBaseFinanceService(IFinanceService baseFinanceService) {
		this.baseFinanceService = baseFinanceService;
	}

	public IStorageSummaryService getBaseStorageSummaryService() {
		return baseStorageSummaryService;
	}
	public void setBaseStorageSummaryService(
			IStorageSummaryService baseStorageSummaryService) {
		this.baseStorageSummaryService = baseStorageSummaryService;
	}
	public ICMRService getBaseCMRService() {
		return baseCMRService;
	}
	public void setBaseCMRService(ICMRService baseCMRService) {
		this.baseCMRService = baseCMRService;
	}

	/**
	 * 根据商品编码获取商品详细信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 10, 2013
	 */
	public GoodsInfo getGoodsInfoByID(String id) throws Exception{
		return baseFilesService.getGoodsInfoByID(id);
	}
    /**
     * 获取商品成本价
     * @param storageid         仓库编号
     * @param goodsid           商品编号
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date May 10, 2013
     */
    public BigDecimal getGoodsCostPrice(String storageid,String goodsid) throws Exception{
        return baseFilesService.getGoodsCostprice(storageid,goodsid);
    }
	/**
	 * 根据商品编码获取商品详细信息(含商品状态）
	 * @param id
	 * @return
	 * @throws Exception
	 * @author lin_xx
	 * @date  2015-8-6
	 */
	public GoodsInfo getGoodsStatesInfoByID(String id) throws Exception{
		return baseFilesService.getGoodsStatesInfoByID(id);
	}

	/**
	 * 根据商品编码,主单位数量,辅单位编码
	 * 计算得到金额,辅单位数量,辅单位数量描述信息等
	 * unitamount:金额，auxnum: 辅单位数量,auxnumDetail:辅单位数量描述信息
	 * @param goodsid		商品编码
	 * @param auxunitid		辅单位编码
	 * @param price 		主单位单价
	 * @param unitNum		主单位数量
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date May 11, 2013
	 */
	public Map countGoodsInfoNumber(String goodsid,String auxunitid,BigDecimal price,BigDecimal unitNum) throws Exception{
		Map map = baseFilesService.countGoodsInfoNumber(goodsid, auxunitid, price, unitNum);
		return map;
	}

	/**
	 * 通过商品编码、主单位数量、辅单位编码计算得到辅单位数量,辅单位数量描述信息
	 * 返回：auxnum：辅单位数量 auxnumdetail：辅单位数量描述 auxunitname辅单位名称 unitname主单位名称
	 * auxremainder辅单位整数数量 auxInteger辅单位余数（换算后剩余的主单位数量）（主比辅有效）
	 * @param goodsId 商品编码
	 * @param auxUnitId 辅单位编码
	 * @param unitNum 主单位数量
	 * @return auxnum:辅单位数量、auxNumDetail:辅单位数量描述信息
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 14, 2013
	 */
	public Map countGoodsInfoNumber(String goodsId, String auxUnitId, BigDecimal unitNum) throws Exception{
		Map map = baseFilesService.countGoodsInfoNumber(goodsId, auxUnitId, unitNum);
		return map;
	}

	/**
	 * 获取税种详细信息
	 * @param type
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 15, 2013
	 */
	public TaxType getTaxType(String type) throws Exception{
		return baseFinanceService.getTaxTypeInfo(type);
	}

	/**
	 * 获取商品辅助单位信息
	 * @param goodsId
	 * @param auxUnitId
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-5-15
	 */
	public GoodsInfo_MteringUnitInfo getGoodsAuxMeterUnitInfo(String goodsId,String auxUnitId) throws Exception{
		return baseFilesService.getGoodsAuxMeterUnitInfo(goodsId, auxUnitId);
	}
	/**
	 * 获取商品默认辅助单位信息
	 * @param goodsId
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-5-15
	 */
	public GoodsInfo_MteringUnitInfo getDefaultGoodsAuxMeterUnitInfo(String goodsId) throws Exception{
		List<GoodsInfo_MteringUnitInfo> list = baseGoodsService.getMUListByGoodsId(goodsId); //获取商品的辅助计量单位列表
		GoodsInfo_MteringUnitInfo goodsInfo_MteringUnitInfo = null;
		for(GoodsInfo_MteringUnitInfo mteringUnitInfo : list){ //获取对应的辅单位换算信息
			if("1".equals(mteringUnitInfo.getIsdefault())){
				goodsInfo_MteringUnitInfo = mteringUnitInfo;
				MeteringUnit meteringUnit=baseFilesService.getMeteringUnitById(goodsInfo_MteringUnitInfo.getMeteringunitid());
				if(null!=meteringUnit && StringUtils.isNotEmpty(meteringUnit.getName())){
					goodsInfo_MteringUnitInfo.setMeteringunitName(meteringUnit.getName());
				}
				break;
			}
		}
		return goodsInfo_MteringUnitInfo;
	}

	/**
	 * 根据含税单价和税种,数量 获取无税单价 税种名称 含税金额 无税金额
	 * notaxprice：无税单价 taxtypename税种名称 taxamount：含税金额 notaxamount无税金额
	 * @param taxprice
	 * @param taxtype
	 * @param unitnum
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 11, 2013
	 */
	public Map getTaxInfosByTaxpriceAndTaxtype(BigDecimal taxprice,String taxtype,BigDecimal unitnum) throws Exception{
		if(null==unitnum){
			unitnum = new BigDecimal(0);
		}
		TaxType taxTypeInfo = getTaxType(taxtype);
		if(null!=taxTypeInfo){
			//税率  保存6位小数
			BigDecimal taxrate = taxTypeInfo.getRate().divide(new BigDecimal(100),6, BigDecimal.ROUND_HALF_UP);
			//无税单价 = 含税单价/（1+税率） 保存6位小数
			BigDecimal notaxprice = taxprice.divide(taxrate.add(new BigDecimal(1)),6,BigDecimal.ROUND_HALF_UP);
			//含税金额 = 含税单价*数量
			BigDecimal taxamount = taxprice.multiply(unitnum).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
			//无税金额 = 含税金额/（1+税率） 保存6位小数
			BigDecimal notaxamount = taxamount.divide(taxrate.add(new BigDecimal(1)),decimalLen,BigDecimal.ROUND_HALF_UP);
			//税额
			BigDecimal tax=taxamount.subtract(notaxamount);
			Map map = new HashMap();
			map.put("notaxprice", notaxprice);
			map.put("taxtypename", taxTypeInfo.getName());
			map.put("taxamount", taxamount);
			map.put("notaxamount", notaxamount);
			map.put("tax", tax);
			return map;
		}else{
			//税率  保存6位小数
			BigDecimal taxrate = BigDecimal.ZERO;
			//无税单价 = 含税单价/（1+税率） 保存6位小数
			BigDecimal notaxprice = BigDecimal.ZERO;
			//含税金额 = 含税单价*数量
			BigDecimal taxamount = taxprice.multiply(unitnum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
			//无税金额 = 含税金额/（1+税率） 保存6位小数
			BigDecimal notaxamount = BigDecimal.ZERO;
			//税额
			BigDecimal tax=BigDecimal.ZERO;
			Map map = new HashMap();
			map.put("notaxprice", notaxprice);
			map.put("taxtypename", "");
			map.put("taxamount", taxamount);
			map.put("notaxamount", notaxamount);
			map.put("tax", tax);
			return map;
		}
	}
	/**
	 * 根据含税金额和税种 获取无税金额
	 * @param taxamount
	 * @param taxtype
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 11, 2013
	 */
	public BigDecimal getNotaxAmountByTaxAmount(BigDecimal taxamount,String taxtype) throws Exception{

		TaxType taxTypeInfo = getTaxType(taxtype);
		if(null== taxTypeInfo){
			//获取默认税种
			String defaultTaxtype = getSysParamValue("DEFAULTAXTYPE");
			taxTypeInfo = getTaxType(defaultTaxtype);
		}
		if(null==taxamount){
			taxamount = new BigDecimal(0);
		}
		if(null!=taxTypeInfo){
			//税率  保存6位小数
			BigDecimal taxrate = taxTypeInfo.getRate().divide(new BigDecimal(100),6, BigDecimal.ROUND_HALF_UP);
			//无税金额 = 含税金额/（1+税率） 保存6位小数
			BigDecimal notaxamount = taxamount.divide(taxrate.add(new BigDecimal(1)),decimalLen,BigDecimal.ROUND_HALF_UP);
			return notaxamount;
		}else{
			return new BigDecimal(0);
		}
	}
	/**
	 * 根据默认供应商获取商品列表
	 * @param defaultsupplier
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 24, 2013
	 */
	public List<GoodsInfo> getGoodsInfoBySupplier(String defaultsupplier)throws Exception{
		PageMap pageMap = new PageMap();
		Map map = new HashMap();
		map.put("defaultsupplier", defaultsupplier);
		map.put("state", "1");
		pageMap.setCondition(map);
		return baseGoodsService.getGoodsInfoByCondition(pageMap);
	}
	/**
	 * 获取供应商信息
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-8-7
	 */
	public BuySupplier getBaseBuySupplierById(String id) throws Exception{
		return baseFilesService.getSupplierInfoById(id);
	}
	/**
	 * 根据商品编号获取库存中商品的总量
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 24, 2013
	 */
	public StorageSummary getStorageSummarySumByGoodsid(String goodsid) throws Exception{
		StorageSummary storageSummary = baseStorageSummaryService.getStorageSummarySumByGoodsid(goodsid);
		return storageSummary;
	}
    /**
     * 根据商品编号获取库存中商品的总量
     * @param goodsid
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jun 24, 2013
     */
    public StorageSummary getStorageSummarySumByGoodsidWithDatarule(String goodsid) throws Exception{
        StorageSummary storageSummary = baseStorageSummaryService.getStorageSummarySumByGoodsidWithDatarule(goodsid);
        return storageSummary;
    }
	/**
	 * 根据仓库编号和商品编号获取该仓库下的商品数量
	 * @param storageid
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 24, 2013
	 */
	public StorageSummary getStorageSummaryByStorageidAndGoodsid(String storageid,String goodsid) throws Exception{
		StorageSummary storageSummary =  baseStorageSummaryService.getStorageSummaryByStorageAndGoods(storageid, goodsid);
		return storageSummary;
	}
	/**
	 * 获取税种档案所有列表数据
	 * @return
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getTaxTypeListData()throws Exception{
		return baseFinanceService.getTaxTypeListData();
	}

	/**
	 * 获取结算方式所有列表数据
	 * @return
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getSettlementListData()throws Exception{
		return baseFinanceService.getSettlementListData();
	}

	/**
	 * 获取支付方式所有列表数据
	 * @return
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getPaymentListData()throws Exception{
		return baseFinanceService.getPaymentListData();
	}

	/**
	 * 获取费用分类所有列表数据
	 * @return
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getExpensesSortListData()throws Exception{
		return baseFinanceService.getExpensesSortListData();
	}

	/**
	 * 获取销售方式所有列表数据
	 * @return
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getSaleModeListData()throws Exception{
		return baseCMRService.getSaleModeListData();
	}

	/**
	 * 获取销售机会来源分了所有列表数据
	 * @return
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getSaleChanceSortListData()throws Exception{
		return baseCMRService.getSaleChanceSortListData();
	}

	/**
	 * 获取任务分类所有列表数据
	 * @return
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getTaskSortListData()throws Exception{
		return baseCMRService.getTaskSortListData();
	}

	/**
	 * 获取市场活动分类所有列表数据
	 * @return
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public List getmarketActivitySortListData()throws Exception{
		return baseCMRService.getmarketActivitySortListData();
	}

	/**
	 * 根据辅单位，商品编码获取主单位
	 * @param unitNum
	 * @param goodsid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Aug 2, 2013
	 */
	public Map retMainUnitByUnitAndGoodid(BigDecimal unitNum,String goodsid)throws Exception{
		Map map = baseFilesService.retMainUnitByUnitAndGoodid(unitNum,goodsid);
		return map;
	}
	/**
	 * 获取仓库全部启用的列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 5, 2013
	 */
	public List getStorageInfoAllList() throws Exception{
		List list = baseFilesService.getStorageInfoAllList();
		return list;
	}
	/**
	 * 获取启用的仓库档案列表
	 * @param
	 * @return java.util.List
	 * @throws
	 * @author luoqiang
	 * @date Mar 05, 2018
	 */
	public List getStorageInfoOpenList() throws Exception {
		List list = baseFilesService.getStorageInfoOpenList();
		return list;
	}
	/**
	 * 根据类型获取 出入库类型列表
	 * @param type	1入库 2出库
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 5, 2013
	 */
	public List getStorageInOutAllList(String type) throws Exception{
		List list = baseFilesService.getStorageInOutAllList(type);
		return list;
	}
	/**
	 * 通过总数量计算商品含税 未税金额 以及辅单位数量等
	 * goodsid商品编码 unitnum商品数量 auxunitid辅单位编码（不是必填） taxprice：含税单价 taxtype税种编号
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 5, 2013
	 */
	public String computeGoodsByUnitnum() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String auxunitid = request.getParameter("auxunitid");
		String unitnumStr = request.getParameter("unitnum");
		String taxpriceStr = request.getParameter("taxprice");
		String taxtypeid = request.getParameter("taxtype");

		BigDecimal unitnum = null;
		if(null==unitnumStr || "".equals(unitnumStr)){
			unitnum = new BigDecimal(0);
		}else{
			unitnum = new BigDecimal(unitnumStr);
		}
		GoodsInfo goodsInfo = baseGoodsService.showGoodsInfo(goodsid);
		BigDecimal taxprice = null;
		if((null==taxpriceStr || "".equals(taxpriceStr)) && null!=goodsInfo){
			taxprice = goodsInfo.getNewbuyprice();
		}else{
			taxprice = new BigDecimal(taxpriceStr);
		}
		if(null==taxtypeid || "".equals(taxtypeid)){
			taxtypeid = goodsInfo.getDefaulttaxtype();
		}
		Map taxinfoMap = getTaxInfosByTaxpriceAndTaxtype(taxprice, taxtypeid,unitnum);
		//无税单价
		BigDecimal notaxprice = (BigDecimal) taxinfoMap.get("notaxprice");
		//税种名称
		String taxtypename = (String) taxinfoMap.get("taxtypename");
		//含税金额
		BigDecimal taxamount = (BigDecimal) taxinfoMap.get("taxamount");
		//无税金额
		BigDecimal notaxamount = (BigDecimal) taxinfoMap.get("notaxamount");
		//税额
		BigDecimal tax = taxamount.subtract(notaxamount);

		Map auxnumMap = countGoodsInfoNumber(goodsid, auxunitid, unitnum);
		BigDecimal auxnum = (BigDecimal) auxnumMap.get("auxnum");
		BigDecimal auxrate = (BigDecimal) auxnumMap.get("auxrate");
		String auxnumdetail = (String) auxnumMap.get("auxnumdetail");
		String auxunitname = (String) auxnumMap.get("auxunitname");
		String unitname = (String) auxnumMap.get("unitname");
		String auxremainder = (String) auxnumMap.get("auxremainder");
		String auxInteger = (String) auxnumMap.get("auxInteger");
		BigDecimal totalboxweight = new BigDecimal(auxnumMap.get("totalboxweight").toString());
		BigDecimal totalboxvolume = new BigDecimal(auxnumMap.get("totalboxvolume").toString());

		Map returnMap = new HashMap();
		returnMap.put("taxprice", taxprice);
		returnMap.put("notaxprice", notaxprice);
		returnMap.put("taxtypename", taxtypename);
		returnMap.put("taxamount", taxamount);
		returnMap.put("notaxamount", notaxamount);
		returnMap.put("tax", tax);
		returnMap.put("auxnum", auxnum);
		returnMap.put("auxnumdetail", auxnumdetail);
		returnMap.put("auxunitname", auxunitname);
		returnMap.put("unitname", unitname);
		returnMap.put("auxremainder", auxremainder);
		returnMap.put("auxInteger", auxInteger);
		returnMap.put("totalboxweight",totalboxweight);
		returnMap.put("totalboxvolume",totalboxvolume);
		if(null!=goodsInfo){
			BigDecimal boxprice = goodsInfo.getBoxnum().multiply(taxprice).setScale(6, BigDecimal.ROUND_HALF_UP);
			BigDecimal noboxprice = goodsInfo.getBoxnum().multiply(notaxprice).setScale(6, BigDecimal.ROUND_HALF_UP);
			returnMap.put("boxprice", boxprice);
			returnMap.put("noboxprice", noboxprice);
		}
		if(null!=auxrate){
			returnMap.put("auxrate", auxrate);
		}
		addJSONObject(returnMap);
		return "success";
	}
	/**
	 * 通过辅单位数量 计算商品含税 未税金额 主单位数量
	 * goodsid商品编码 auxInterger辅单位整数 auxremainder辅单位余数 taxprice：含税单价 taxtype税种编号
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 5, 2013
	 */
	public String computeGoodsByAuxnum() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String auxIntergerStr = request.getParameter("auxInterger");
		String auxremainderStr = request.getParameter("auxremainder");
		String taxpriceStr = request.getParameter("taxprice");
		String taxtypeid = request.getParameter("taxtype");
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		BigDecimal auxInterger = null;
		if(null==auxIntergerStr || "".equals(auxIntergerStr)){
			auxInterger = new BigDecimal(0);
		}else{
			auxInterger = new BigDecimal(auxIntergerStr);
		}
		BigDecimal auxremainder = null;
		if(null==auxremainderStr || "".equals(auxremainderStr)){
			auxremainder = new BigDecimal(0);
		}else{
			auxremainder = new BigDecimal(auxremainderStr);
		}

		BigDecimal taxprice = null;
		if(null==taxpriceStr || "".equals(taxpriceStr)){
			taxprice = goodsInfo.getNewbuyprice();
		}else{
			taxprice = new BigDecimal(taxpriceStr);
		}
		Map mainMap = retMainUnitByUnitAndGoodid(auxInterger, goodsid);
		BigDecimal mainnum = (BigDecimal) mainMap.get("mainUnitNum");
		mainnum = mainnum.add(auxremainder);
		if(null==taxtypeid || "".equals(taxtypeid)){
			taxtypeid = goodsInfo.getDefaulttaxtype();
		}
		Map taxinfoMap = getTaxInfosByTaxpriceAndTaxtype(taxprice, taxtypeid,mainnum);
		//无税单价
		BigDecimal notaxprice = (BigDecimal) taxinfoMap.get("notaxprice");
		//税种名称
		String taxtypename = (String) taxinfoMap.get("taxtypename");
		//含税金额
		BigDecimal taxamount = (BigDecimal) taxinfoMap.get("taxamount");
		//无税金额
		BigDecimal notaxamount = (BigDecimal) taxinfoMap.get("notaxamount");
		//税额
		BigDecimal tax = taxamount.subtract(notaxamount);

		String auxnumdetail = "";
		//辅数量大于等于箱装量
		if(auxremainder.compareTo(goodsInfo.getBoxnum()) > 0){
			BigDecimal num = auxremainder.divideAndRemainder(goodsInfo.getBoxnum())[0];
			auxInterger = auxInterger.add(num);
			//主单位余数数量= （主单位数量/换算比率-辅单位整数数量）*换算比率
			auxremainder = mainnum.divideAndRemainder(goodsInfo.getBoxnum())[1];
		}

		GoodsInfo_MteringUnitInfo meteringUnit = getDefaultGoodsAuxMeterUnitInfo(goodsid);
		if(null!=meteringUnit){
			auxnumdetail += auxInterger.abs().intValue()+meteringUnit.getMeteringunitName();
		}else{
			auxnumdetail += auxInterger.abs().intValue()+"箱";
		}
		if(auxremainder.compareTo(BigDecimal.ZERO) != 0){
			auxnumdetail += auxremainder.abs()+goodsInfo.getMainunitName();
		}

		Map returnMap = new HashMap();
		if(null != goodsInfo.getGrossweight()){
			returnMap.put("totalboxweight", mainnum.multiply(goodsInfo.getGrossweight()).setScale(6, BigDecimal.ROUND_HALF_UP));
		}else{
			returnMap.put("totalboxweight","0.000000");
		}
		if(null != goodsInfo.getSinglevolume()){
			returnMap.put("totalboxvolume", mainnum.multiply(goodsInfo.getSinglevolume()).setScale(6, BigDecimal.ROUND_HALF_UP));
		}else{
			returnMap.put("totalboxvolume","0.000000");
		}
		returnMap.put("taxprice", taxprice);
		returnMap.put("notaxprice", notaxprice);
		returnMap.put("taxtypename", taxtypename);
		returnMap.put("taxamount", taxamount);
		returnMap.put("notaxamount", notaxamount);
		returnMap.put("tax", tax);
		returnMap.put("mainnum", mainnum);
		returnMap.put("auxremainder",auxremainder);
		returnMap.put("auxInterger",auxInterger);
		if(mainnum.compareTo(BigDecimal.ZERO)==-1){
			auxnumdetail ="-"+auxnumdetail;
		}
		returnMap.put("auxnumdetail", CommonUtils.strDigitNumDeal(auxnumdetail));
		addJSONObject(returnMap);
		return "success";
	}

	public Customer getCustomerById(String id) throws Exception{
		Customer customer = baseFilesService.getCustomerByID(id);
		return customer;
	}

	/**
	 * 根据表名获取当前用户访问该表的字段权限
	 * @param tablename
	 * @param alias
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Jan 16, 2014
	 */
	public String getAccessColunmnList(String tablename,String alias)throws Exception{
		String cols = baseFilesService.getAccessColunmnList(tablename, alias);
		return cols;
	}

	public StorageInfo getStorageInfoByCarsaleuser(String userid) throws Exception{
		StorageInfo storageInfo = baseFilesService.getStorageInfoByCarsaleuser(userid);
		return storageInfo;
	}

	/**
	 * 根据仓库编码获取仓库档案详情
	 * @param storageid
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Sep 25, 2014
	 */
	public StorageInfo getStorageInfo(String storageid)throws Exception{
		StorageInfo storageInfo = getBaseStorageService().showStorageInfo(storageid);
		return storageInfo;
	}

	/**
	 * 将Map对象转化为List<Map<String, Object>>
	 * @param pageData
	 * @param firstMap
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Nov 28, 2013
	 */
	public List<Map<String, Object>> mapToMapList(PageData pageData,Map<String, Object> firstMap)throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		if(pageData.getList().size() != 0){
			for(Map<String,Object> map : new ArrayList<Map<String,Object>>(pageData.getList())){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
		if(pageData.getFooter().size() != 0){
			for(Map<String,Object> map : new ArrayList<Map<String,Object>>(pageData.getFooter())){
				Map<String, Object> retMap = new LinkedHashMap<String, Object>();
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
	 * 金额、单价、数量变动后，相互计算
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2016年1月21日
	 */
	public String computeUnitumPriceAmountChange() throws Exception{
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		String type=request.getParameter("type");
		String taxpriceStr = request.getParameter("taxprice");
		String unitnumStr = request.getParameter("unitnum");
		String amountStr = request.getParameter("amount");

		if(null==taxpriceStr || "".equals(taxpriceStr.trim())){
			taxpriceStr="0";
		}
		if(null==unitnumStr || "".equals(unitnumStr.trim())){
			unitnumStr="0";
		}
		if(null==amountStr || "".equals(amountStr.trim())){
			amountStr="0";
		}

		BigDecimal taxprice=new BigDecimal(taxpriceStr.trim());
		BigDecimal unitnum=new BigDecimal(unitnumStr.trim());
		BigDecimal amount=new BigDecimal(amountStr.trim());
		//金额变动
		if("1".equals(type)){
			if(amount.compareTo(BigDecimal.ZERO)==0){
				taxprice=BigDecimal.ZERO;
			}else{
				amount=amount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
				//如果数量为零
				if(unitnum.compareTo(BigDecimal.ZERO)==0){
					//单价也为零
					taxprice=BigDecimal.ZERO;
				}else{
					unitnum=unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);

					taxprice=amount.divide(unitnum, 6, BigDecimal.ROUND_HALF_UP);
				}

			}

		}else if("2".equals(type)){
			//数量变动
			if(unitnum.compareTo(BigDecimal.ZERO)==0){
				taxprice=BigDecimal.ZERO;
			}else{
				unitnum=unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);

				if(amount.compareTo(BigDecimal.ZERO)==0){
					if(taxprice.compareTo(BigDecimal.ZERO)>0){
						amount=unitnum.multiply(taxprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
					}
				}else{
					amount=amount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP);

					taxprice=amount.divide(unitnum, 6, BigDecimal.ROUND_HALF_UP);
				}

			}
		}else if("3".equals(type)){
			//单价变动 
			if(taxprice.compareTo(BigDecimal.ZERO)==0){
				if(amount.compareTo(BigDecimal.ZERO)>0 && unitnum.compareTo(BigDecimal.ZERO)>0){
					taxprice=amount.divide(unitnum, 6, BigDecimal.ROUND_HALF_UP);
				}else{
					taxprice=BigDecimal.ZERO;
				}
			}else{
				taxprice=taxprice.setScale(6,BigDecimal.ROUND_HALF_UP);

				if(unitnum.compareTo(BigDecimal.ZERO)==0){
					amount=BigDecimal.ONE;
				}else{
					unitnum=unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);

					amount=unitnum.multiply(taxprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
				}

			}
		}
		Map resultMap=new HashMap();
		resultMap.put("taxprice", taxprice);
		resultMap.put("unitnum", unitnum);
		resultMap.put("amount", amount);
		addJSONObject(resultMap);
		return SUCCESS;
	}

	public Map showCustomerReceivableInfoData(String customerid) throws Exception {
		Map map = baseFilesService.showCustomerReceivableInfoData(customerid);
		return map;
	}

	 /**
	  * 根据查询条件寻找对应的客户
	  * @author lin_xx
	  * @date 2016/12/7
	  */
	public String returnCustomerID(Map map ) throws Exception{
		String busid = "";
		Customer customer = new Customer();
		List customerList = getBaseSalesService().getCustomerByConditon(map);
		if(customerList.size()>0){
			customer = (Customer)customerList.get(0);
		}
		if(!StringUtils.isEmpty(customer.getId())){
			busid = customer.getId();
		}
		return busid;
	}

	/**
	 * 根据含税金额和税种 获取无税金额,或者根据无税金额和税种获取含税金额
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Jun 11, 2013
	 */
	public String getAmountMapByTaxAndAmount() throws Exception{
		Map resMap=new HashMap();
		String taxamountstr=request.getParameter("taxamount");
		String notaxamountstr=request.getParameter("notaxamount");

		String taxtype = request.getParameter("taxtype");

		TaxType taxTypeInfo = getTaxType(taxtype);
		if(null== taxTypeInfo){
			//获取默认税种
			String defaultTaxtype = getSysParamValue("DEFAULTAXTYPE");
			taxTypeInfo = getTaxType(defaultTaxtype);
		}
		if(null!=taxTypeInfo){
			//税率  保存6位小数
			BigDecimal taxrate = taxTypeInfo.getRate().divide(new BigDecimal(100),6, BigDecimal.ROUND_HALF_UP);
			if(StringUtils.isNotEmpty(taxamountstr)){
				BigDecimal taxamount=new BigDecimal(taxamountstr);
				//无税金额 = 含税金额/（1+税率） 保存6位小数
				BigDecimal notaxamount = taxamount.divide(taxrate.add(new BigDecimal(1)),6,BigDecimal.ROUND_HALF_UP);
				resMap.put("notaxamount",notaxamount);
				BigDecimal tax=taxamount.subtract(notaxamount);
				resMap.put("tax",tax);
			}else if(StringUtils.isNotEmpty(notaxamountstr)){
				BigDecimal notaxamount=new BigDecimal(notaxamountstr);
				BigDecimal taxamount = notaxamount.multiply(taxrate.add(new BigDecimal(1)));
				resMap.put("taxamount",taxamount);
				BigDecimal tax=taxamount.subtract(notaxamount);
				resMap.put("tax",tax);
			}
		}
		addJSONObject(resMap);
		return SUCCESS;
	}
}

