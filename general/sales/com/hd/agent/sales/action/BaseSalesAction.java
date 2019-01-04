/**
 * @(#)BaseSalesAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 28, 2013 zhengziyong 创建版本
 */
package com.hd.agent.sales.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.BeginAmount;
import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.service.ICustomerPushBanlanceService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.service.IExcelService;
import com.hd.agent.common.util.*;
import com.hd.agent.purchase.service.ext.IPurchaseForSalesService;
import com.hd.agent.sales.model.*;
import com.hd.agent.sales.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 *
 * @author zhengziyong
 */
public class BaseSalesAction extends BaseFilesAction {

	protected IOffpriceService salesOffpriceService;
	protected IOrderService salesOrderService;
	protected IDispatchBillService salesDispatchBillService;
	protected IReceiptService salesReceiptService;
	protected IRejectBillService salesRejectBillService;
	protected IOrderCarService salesOrderCarService;
	protected IDemandService salesDemandService;
	protected IExcelService excelService;
	protected IPromotionService salesPromotionService;
	protected IOweOrderService salesOweOrderService;
	protected ImportService importService;
	protected IOrderGoodsService orderGoodsService;

    protected IPurchaseForSalesService purchaseForSalesService;


	private IAttachFileService attachFileService;

	public IAttachFileService getAttachFileService() {
		return attachFileService;
	}

	public void setAttachFileService(IAttachFileService attachFileService) {
		this.attachFileService = attachFileService;
	}

	public ImportService getImportService() {
		return importService;
	}

	public void setImportService(ImportService importService) {
		this.importService = importService;
	}

	public IOweOrderService getSalesOweOrderService() {
		return salesOweOrderService;
	}

	public void setSalesOweOrderService(IOweOrderService salesOweOrderService) {
		this.salesOweOrderService = salesOweOrderService;
	}

	public IPromotionService getSalesPromotionService() {
		return salesPromotionService;
	}

	public void setSalesPromotionService(IPromotionService salesPromotionService) {
		this.salesPromotionService = salesPromotionService;
	}

	public IExcelService getExcelService() {
		return excelService;
	}

	public void setExcelService(IExcelService excelService) {
		this.excelService = excelService;
	}

	public IOffpriceService getSalesOffpriceService() {
		return salesOffpriceService;
	}

	public void setSalesOffpriceService(IOffpriceService salesOffpriceService) {
		this.salesOffpriceService = salesOffpriceService;
	}

	public IOrderService getSalesOrderService() {
		return salesOrderService;
	}

	public void setSalesOrderService(IOrderService salesOrderService) {
		this.salesOrderService = salesOrderService;
	}

	public IDispatchBillService getSalesDispatchBillService() {
		return salesDispatchBillService;
	}

	public void setSalesDispatchBillService(
			IDispatchBillService salesDispatchBillService) {
		this.salesDispatchBillService = salesDispatchBillService;
	}

	public IReceiptService getSalesReceiptService() {
		return salesReceiptService;
	}

	public void setSalesReceiptService(IReceiptService salesReceiptService) {
		this.salesReceiptService = salesReceiptService;
	}

	public IRejectBillService getSalesRejectBillService() {
		return salesRejectBillService;
	}

	public void setSalesRejectBillService(IRejectBillService salesRejectBillService) {
		this.salesRejectBillService = salesRejectBillService;
	}

	public IOrderCarService getSalesOrderCarService() {
		return salesOrderCarService;
	}

	public void setSalesOrderCarService(IOrderCarService salesOrderCarService) {
		this.salesOrderCarService = salesOrderCarService;
	}

	public IDemandService getSalesDemandService() {
		return salesDemandService;
	}

	public void setSalesDemandService(IDemandService salesDemandService) {
		this.salesDemandService = salesDemandService;
	}

    public IPurchaseForSalesService getPurchaseForSalesService() {
        return purchaseForSalesService;
    }

    public void setPurchaseForSalesService(IPurchaseForSalesService purchaseForSalesService) {
        this.purchaseForSalesService = purchaseForSalesService;
    }

	public IOrderGoodsService getOrderGoodsService() {
		return orderGoodsService;
	}

	public void setOrderGoodsService(IOrderGoodsService orderGoodsService) {
		this.orderGoodsService = orderGoodsService;
	}

	/**
	 * 改变主单位数量时计算辅单位、促销价及金额
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date Nov 28, 2013
	 */
	public String getAuxUnitNumAndPrice() throws Exception{
		String goodsId = request.getParameter("id");
		String customerId = request.getParameter("cid");
		String unitNum = request.getParameter("unitnum");
		String date = request.getParameter("date");
		//是否获取满赠信息
		String isShowFullFree = request.getParameter("free");
		String orderid = request.getParameter("orderid");
		String price = request.getParameter("price");
		String taxpricechange = request.getParameter("taxpricechange");
		if(StringUtils.isEmpty(orderid)){
			orderid = null;
		}
		if(StringUtils.isEmpty(unitNum)){
			unitNum = "0";
		}
		BigDecimal bUnitNum = new BigDecimal(unitNum);
		Map result = getNumChangeResult(goodsId, customerId, bUnitNum, date,price,taxpricechange);
		if("1".equals(isShowFullFree)){
			boolean hasFree = salesOrderService.getFullFreeDetailByCustomeridAndGoodsid(orderid,customerId, goodsId, bUnitNum);
			if(hasFree){
				result.put("hasFree", "1");
			}else{
				result.put("hasFree", "0");
			}
		}else{
			result.put("hasFree", "0");
		}
		addJSONObject(result);
		return SUCCESS;
	}

	/**
	 * 改变辅单位数量时计算主单位、促销价及金额
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date Nov 28, 2013
	 */
	public String getUnitNumAndPrice() throws Exception{
		String goodsId = request.getParameter("id");
		String customerId = request.getParameter("cid");
		String auxnum = request.getParameter("auxnum"); //辅计量单位数量
		String overnum = request.getParameter("overnum"); //主单位余数
		String date = request.getParameter("date");
		//是否获取满赠信息
		String isShowFullFree = request.getParameter("free");
		String orderid = request.getParameter("orderid");
		String price = request.getParameter("price");
		String taxpricechange = request.getParameter("taxpricechange");
		if(StringUtils.isEmpty(orderid)){
			orderid = null;
		}
		if(StringUtils.isEmpty(auxnum)){
			auxnum = "0";
		}
		if(StringUtils.isEmpty(overnum)){
			overnum = "0";
		}

		BigDecimal bAuxNum = new BigDecimal(auxnum);
		BigDecimal bOverNum = new BigDecimal(overnum);
		BigDecimal bUnitNum = new BigDecimal(0);
		Map retMap = retMainUnitByUnitAndGoodid(bAuxNum, goodsId);
		BigDecimal mainUnitNum = new BigDecimal(0);
		if(retMap.containsKey("mainUnitNum")){
			mainUnitNum = new BigDecimal(retMap.get("mainUnitNum").toString());
		}
		bUnitNum = bOverNum.add(mainUnitNum);
		Map result = getNumChangeResult(goodsId, customerId, bUnitNum, date,price,taxpricechange);
		if("1".equals(isShowFullFree)){
			boolean hasFree = salesOrderService.getFullFreeDetailByCustomeridAndGoodsid(orderid,customerId, goodsId, bUnitNum);
			if(hasFree){
				result.put("hasFree", "1");
			}else{
				result.put("hasFree", "0");
			}
		}else{
			result.put("hasFree", "0");
		}
		addJSONObject(result);
		return SUCCESS;
	}

	private Map getNumChangeResult(String goodsId, String customerId, BigDecimal bUnitNum, String date,String price,String taxpricechange) throws Exception{
		//商品明细中含税单价是否改动，0未改动1改动，若改动过，则无论商品数量怎么变化，不根据取价方式获取含税单价，若未改动过，则根据取价方式取价（如果数量是在特价数量区间外的 就取正常商品单价 在特价数量区间内的 取特价）
		if(StringUtils.isEmpty(taxpricechange)){
			taxpricechange = "0";
		}
		BigDecimal taxprice = null;
		if(StringUtils.isNotEmpty(price)){
			taxprice = new BigDecimal(price);
		}
		Map result = new HashMap();
		GoodsInfo_MteringUnitInfo mteringUnitInfo = getDefaultGoodsAuxMeterUnitInfo(goodsId);
		String auxUnitId = "";
		if(mteringUnitInfo != null){
			auxUnitId = mteringUnitInfo.getMeteringunitid();
		}
		OrderDetail orderDetail = salesOrderService.getGoodsDetail(goodsId, customerId, date, bUnitNum, null);
//		if(null !=orderDetail.getOffprice() || null==taxprice){
//			taxprice = orderDetail.getTaxprice();
//		}
		if("0".equals(taxpricechange) || null==taxprice){
			taxprice = orderDetail.getTaxprice();
			result.put("remark", orderDetail.getRemark());

			if(StringUtils.isNotEmpty(orderDetail.getGroupid())){
				result.put("groupid", orderDetail.getGroupid());
			}else{
				result.put("groupid", "");
			}
		}
		result.put("taxprice", taxprice);
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsId);
		if(null!=goodsInfo){
			result.put("boxprice", taxprice.multiply(goodsInfo.getBoxnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
			if(null != goodsInfo.getGrossweight()){
				result.put("totalboxweight", bUnitNum.multiply(goodsInfo.getGrossweight()).setScale(6, BigDecimal.ROUND_HALF_UP));
			}else{
				result.put("totalboxweight","0.000000");
			}
			if(null != goodsInfo.getSinglevolume()){
				result.put("totalboxvolume", bUnitNum.multiply(goodsInfo.getSinglevolume()).setScale(6, BigDecimal.ROUND_HALF_UP));
			}else{
				result.put("totalboxvolume","0.000000");
			}

		}
		result.put("totalbox", orderDetail.getTotalbox());
		result.put("auxnum", orderDetail.getAuxnum());
		result.put("overnum", orderDetail.getOvernum());
		result.put("auxnumdetail", orderDetail.getAuxnumdetail());
		result.put("unitnum", bUnitNum);

		TaxType taxType = getTaxType(orderDetail.getTaxtype());

		BigDecimal bTaxPrice = taxprice;
		if(null==bTaxPrice){
			bTaxPrice = BigDecimal.ZERO;
		}
		if(null==bUnitNum){
			bUnitNum = BigDecimal.ZERO;
		}
		BigDecimal bTaxAmount = new BigDecimal(0); //含税金额
		BigDecimal bNoTaxAmount = new BigDecimal(0); //无税金额
		BigDecimal bTax = new BigDecimal(0); //税额
		//获取小数位
		int decimalLen = getAmountDecimalsLength();
		bTaxAmount = bTaxPrice.multiply(bUnitNum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
		BigDecimal bTax1 = new BigDecimal(0);
		BigDecimal rate = BigDecimal.ZERO;
		if(taxType != null){
			rate = taxType.getRate();
			bTax1 = computeTax(1, bTaxPrice, taxType.getRate().divide(new BigDecimal(100)));
			bTax = bTax1.multiply(bUnitNum);
		}
		result.put("rate",rate);
		bNoTaxAmount = getNotaxAmountByTaxAmount(bTaxAmount, orderDetail.getTaxtype());
		BigDecimal notaxprice = BigDecimal.ZERO;
		if(null!=bNoTaxAmount && bNoTaxAmount.compareTo(BigDecimal.ZERO)!=0){
			notaxprice = bNoTaxAmount.divide(bUnitNum, 6,BigDecimal.ROUND_HALF_UP);
		}
		result.put("taxamount", bTaxAmount);
		result.put("notaxamount", bNoTaxAmount);
		result.put("notaxprice", notaxprice);
		result.put("tax", bTax.setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
		return result;
	}

	/**
	 * 通过主计量单位数量获取辅计量单位数量
	 * @return auxnum:辅单位数量、auxNumDetail:辅单位数量描述信息
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 14, 2013
	 */
	public String getAuxUnitNum() throws Exception{
		String goodsId = request.getParameter("id"); //商品编码
		String unitnum = StringUtils.isEmpty(request.getParameter("unitnum")) ? "0" : request.getParameter("unitnum"); //主计量单位数量
		String auxnum = StringUtils.isEmpty(request.getParameter("auxnum")) ? "0" : request.getParameter("auxnum"); //辅计量单位数量
		String overnum = StringUtils.isEmpty(request.getParameter("overnum")) ? "0" : request.getParameter("overnum"); //主单位余数
		String auxUnitId = request.getParameter("aid"); //辅计量单位编码
		String taxPrice = StringUtils.isEmpty(request.getParameter("tp")) ? "0" : request.getParameter("tp"); //含税单价
		String tax = request.getParameter("taxtype"); //税种
		String type = request.getParameter("type");
		BigDecimal bNum = new BigDecimal(unitnum);
		BigDecimal bNum2 = new BigDecimal(auxnum);
		BigDecimal bNum3 = new BigDecimal(overnum);
		BigDecimal totalbox = BigDecimal.ZERO;
		Map map = new HashMap();
		if("1".equals(type)){
			Map retMap = countGoodsInfoNumber(goodsId, auxUnitId, bNum);
			BigDecimal remainder = new BigDecimal(0);
			BigDecimal auxnumInteger = new BigDecimal(0);
			if(retMap.containsKey("auxremainder")){
				remainder = new BigDecimal(retMap.get("auxremainder").toString());
			}
			if(retMap.containsKey("auxInteger")){
				auxnumInteger = new BigDecimal(retMap.get("auxInteger").toString());
			}
			if (retMap.containsKey("auxnum")) {
				totalbox = (BigDecimal) retMap.get("auxnum");//箱数
			}
			bNum2 = auxnumInteger;
			bNum3 = remainder;
			map.put("totalbox", totalbox);
			map.put("auxnum", auxnumInteger);
			map.put("overnum", remainder);
			map.put("auxnumdetail", retMap.get("auxnumdetail"));
		}
		if("2".equals(type)){
			Map retMap = retMainUnitByUnitAndGoodid(bNum2, goodsId);
			BigDecimal mainUnitNum = new BigDecimal(0);
			if(retMap.containsKey("mainUnitNum")){
				mainUnitNum = new BigDecimal(retMap.get("mainUnitNum").toString());
			}
			map.put("unitnum", bNum3.add(mainUnitNum));
			bNum = bNum3.add(mainUnitNum);
			String auxunitname = (String) retMap.get("auxunitname");
			String unitname = (String) retMap.get("unitname");
			String auxnumdetail = "";

			GoodsInfo goodsInfo = getGoodsInfoByID(goodsId);
			//辅数量大于箱装量
			if(bNum3.compareTo(goodsInfo.getBoxnum()) > 0){
				BigDecimal num = bNum3.divideAndRemainder(goodsInfo.getBoxnum())[0];
				bNum2 = bNum2.add(num);
				//主单位余数数量= （主单位数量/换算比率-辅单位整数数量）*换算比率
				bNum3 = bNum.divideAndRemainder(goodsInfo.getBoxnum())[1];
			}
			if(bNum3.compareTo(BigDecimal.ZERO)!=0){
				auxnumdetail = bNum2 + auxunitname+bNum3+unitname;
			}else{
				auxnumdetail = bNum2 + auxunitname;
			}
			map.put("auxnumdetail", CommonUtils.strDigitNumDeal(auxnumdetail));
			Map retMap2 = countGoodsInfoNumber(goodsId, auxUnitId, bNum);
			if (retMap2.containsKey("auxnum")) {
				totalbox = (BigDecimal) retMap2.get("auxnum");//箱数
			}
			map.put("totalbox", totalbox);
		}
		BigDecimal bTaxPrice = new BigDecimal(taxPrice);
		BigDecimal bTaxAmount = bNum.multiply(bTaxPrice).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
		BigDecimal bNoTaxAmount = getNotaxAmountByTaxAmount(bTaxAmount, tax);
		BigDecimal bTax = bTaxAmount.subtract(bNoTaxAmount);
		map.put("auxnum",bNum2);
		map.put("overnum",bNum3);
		map.put("taxAmount", bTaxAmount);
		map.put("noTaxAmount", bNoTaxAmount);
		map.put("tax", bTax);
		addJSONObject(map);
		return SUCCESS;
	}

	/**
	 * 修改含税单价或无税单价时，获取对应单价、金额和税额改变的结果。
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 14, 2013
	 */
	public String getAmountChanger() throws Exception{
		String type = request.getParameter("type"); //1含税单价改变2无税单价改变
		String goodsId = request.getParameter("id"); //商品编码
		String price = StringUtils.isEmpty(request.getParameter("price")) ? "0" : request.getParameter("price"); //改变后的无税或含税单价
		String tax = request.getParameter("taxtype"); //税种
		String unitnum = StringUtils.isEmpty(request.getParameter("unitnum")) ? "0" : request.getParameter("unitnum"); //主计量单位数量;
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsId);
		//税种为空时，默认为商品档案默认税种
		if((null==tax || "".equals(tax.trim())) && null!=goodsInfo){
			tax=goodsInfo.getDefaulttaxtype();
		}
		TaxType taxType = getTaxType(tax);
		BigDecimal bPrice = new BigDecimal(price);
		BigDecimal bNum = new BigDecimal(unitnum);
		BigDecimal bTaxPrice = new BigDecimal(0); //含税单价
		BigDecimal bNoTaxPrice = new BigDecimal(0); //无税单价
		BigDecimal bTaxAmount = new BigDecimal(0); //含税金额
		BigDecimal bNoTaxAmount = new BigDecimal(0); //无税金额
		BigDecimal bTax = new BigDecimal(0); //税额
		if("1".equals(type)){
			bTaxPrice = bPrice;
			bTaxAmount = bTaxPrice.multiply(bNum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
			BigDecimal bTax1 = new BigDecimal(0);
			if(taxType != null){
				bTax1 = computeTax(1, bTaxPrice, taxType.getRate().divide(new BigDecimal(100)));
				bTax = bTax1.multiply(bNum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
			}
			bNoTaxPrice = bTaxPrice.subtract(bTax1);
			bNoTaxAmount = getNotaxAmountByTaxAmount(bTaxAmount, tax);
		}
		else if("2".equals(type)){
			bNoTaxPrice = bPrice;
			bNoTaxAmount = bNoTaxPrice.multiply(bNum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
			BigDecimal bTax1 = new BigDecimal(0);
			if(taxType != null){
				bTax1 = computeTax(2, bNoTaxPrice, taxType.getRate().divide(new BigDecimal(100)));
				bTax = bTax1.multiply(bNum);
			}
			bTaxPrice = bNoTaxPrice.add(bTax1);
			bTaxAmount = bTaxPrice.multiply(bNum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
		}
		Map map = new HashMap();
		map.put("taxPrice", bTaxPrice);
		if(null!=goodsInfo){
			map.put("boxPrice", bTaxPrice.multiply(goodsInfo.getBoxnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
		}else{
			map.put("boxPrice",0);
		}
		map.put("taxAmount", bTaxAmount);
		map.put("noTaxPrice", bNoTaxPrice);
		map.put("noTaxAmount", bNoTaxAmount);
		map.put("tax", bTax);
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 修改含税箱价，获取对应单价、金额和税额改变的结果。
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date 2014年11月18日
	 */
	public String getAmountChangerByBoxprice() throws Exception{
		String goodsId = request.getParameter("id"); //商品编码
		String boxpriceStr = StringUtils.isEmpty(request.getParameter("boxprice")) ? "0" : request.getParameter("boxprice"); //改变后的无税或含税单价
		String tax = request.getParameter("taxtype"); //税种
		String unitnum = StringUtils.isEmpty(request.getParameter("unitnum")) ? "0" : request.getParameter("unitnum"); //主计量单位数量;

		GoodsInfo goodsInfo = getGoodsInfoByID(goodsId);
		//税种为空时，默认为商品档案默认税种
		if((null==tax || "".equals(tax.trim())) && null!=goodsInfo){
			tax=goodsInfo.getDefaulttaxtype();
		}
		TaxType taxType = getTaxType(tax);
		BigDecimal boxPrice = new BigDecimal(boxpriceStr);
		BigDecimal bPrice = BigDecimal.ZERO;
		BigDecimal bNum = new BigDecimal(unitnum);
		BigDecimal bTaxPrice = new BigDecimal(0); //含税单价
		BigDecimal bNoTaxPrice = new BigDecimal(0); //无税单价
		BigDecimal bTaxAmount = new BigDecimal(0); //含税金额
		BigDecimal bNoTaxAmount = new BigDecimal(0); //无税金额
		BigDecimal bTax = new BigDecimal(0); //税额
		if(null!=goodsInfo){
			bPrice = boxPrice.divide(goodsInfo.getBoxnum(), 6, BigDecimal.ROUND_HALF_UP);
		}
		bTaxPrice = bPrice;
		bTaxAmount = bTaxPrice.multiply(bNum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
		BigDecimal bTax1 = new BigDecimal(0);
		if(taxType != null){
			bTax1 = computeTax(1, bTaxPrice, taxType.getRate().divide(new BigDecimal(100)));
			bTax = bTax1.multiply(bNum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
		}
		bNoTaxPrice = bTaxPrice.subtract(bTax1);
		bNoTaxAmount = getNotaxAmountByTaxAmount(bTaxAmount, tax);
		Map map = new HashMap();
		map.put("taxPrice", bTaxPrice);
		map.put("boxPrice", boxPrice);
		map.put("taxAmount", bTaxAmount);
		map.put("noTaxPrice", bNoTaxPrice);
		map.put("noTaxAmount", bNoTaxAmount);
		map.put("tax", bTax);
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 修改含税金额时，获取对应单价、金额和税额改变的结果。
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Apr 8, 2014
	 */
	public String getTaxAmountChange() throws Exception{
		String goodsId = request.getParameter("id"); //商品编码
		String taxamount = StringUtils.isEmpty(request.getParameter("taxamount")) ? "0" : request.getParameter("taxamount"); //改变后的无税或含税单价
		String tax = request.getParameter("taxtype"); //税种
		String unitnum = StringUtils.isEmpty(request.getParameter("unitnum")) ? "0" : request.getParameter("unitnum"); //主计量单位数量;
		BigDecimal bNum = new BigDecimal(unitnum);
		BigDecimal bTaxPrice = new BigDecimal(0); //含税单价
		BigDecimal bNoTaxPrice = new BigDecimal(0); //无税单价
		BigDecimal bTaxAmount = new BigDecimal(taxamount); //含税金额
		BigDecimal bNoTaxAmount = new BigDecimal(0); //无税金额
		BigDecimal bTax = new BigDecimal(0); //税额
		bNoTaxAmount = getNotaxAmountByTaxAmount(bTaxAmount, tax);
		if(bTaxAmount.compareTo(BigDecimal.ZERO)!=0 && bNum.compareTo(BigDecimal.ZERO)!=0){
			bTaxPrice = bTaxAmount.divide(bNum,6,BigDecimal.ROUND_HALF_UP);
			bTax = bTaxAmount.subtract(bNoTaxAmount);
			bNoTaxPrice = bNoTaxAmount.divide(bNum,6,BigDecimal.ROUND_HALF_UP);
		}
		Map map = new HashMap();
		map.put("taxPrice", bTaxPrice);
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsId);
		if(null!=goodsInfo){
			BigDecimal boxprice = bTaxPrice.multiply(goodsInfo.getBoxnum()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
			BigDecimal noboxprice = bNoTaxPrice.multiply(goodsInfo.getBoxnum()).setScale(6, BigDecimal.ROUND_HALF_UP);
			map.put("boxPrice", boxprice);
			map.put("noboxPrice", noboxprice);
		}else{
			map.put("boxPrice",0);
			map.put("noboxPrice", 0);
		}
		map.put("taxAmount", bTaxAmount);
		map.put("noTaxPrice", bNoTaxPrice);
		map.put("noTaxAmount", bNoTaxAmount);
		map.put("tax", bTax);
		addJSONObject(map);
		return SUCCESS;
	}


	/**
	 * 修改金额时，获取对应单价、金额和税额改变的结果。
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Apr 8, 2014
	 */
	public String getAmountChangeByType() throws Exception{
		String type = request.getParameter("type");//1含税金额，2未税金额
		String goodsId = request.getParameter("id"); //商品编码
		String amount = StringUtils.isEmpty(request.getParameter("amount")) ? "0" : request.getParameter("amount"); //
		String tax = request.getParameter("taxtype"); //税种
		String unitnum = StringUtils.isEmpty(request.getParameter("unitnum")) ? "0" : request.getParameter("unitnum"); //主计量单位数量;

		BigDecimal bAmount = new BigDecimal(amount);
		BigDecimal bNum = new BigDecimal(unitnum);
		BigDecimal bTaxPrice = new BigDecimal(0); //含税单价
		BigDecimal bNoTaxPrice = new BigDecimal(0); //无税单价
		BigDecimal bTaxAmount = new BigDecimal(0); //含税金额
		BigDecimal bNoTaxAmount = new BigDecimal(0); //无税金额
		BigDecimal bTax = new BigDecimal(0); //税额

		if("1".equals(type)){
			bTaxAmount = bAmount;
			bTaxPrice = bTaxAmount.divide(bNum, 6, BigDecimal.ROUND_HALF_UP);

			bNoTaxAmount = getNotaxAmountByTaxAmount(bTaxAmount, tax);
			bTax=bTaxAmount.subtract(bNoTaxAmount);
			bNoTaxPrice = bNoTaxAmount.divide(bNum, 6, BigDecimal.ROUND_HALF_UP);

		}else if("2".equals(type)){
			bNoTaxAmount = bAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
			bNoTaxPrice = bNoTaxAmount.divide(bNum, 6, BigDecimal.ROUND_HALF_UP);
			TaxType taxType = getTaxType(tax);
			bTaxAmount = bNoTaxAmount.multiply(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1))).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
			bTax=bTaxAmount.subtract(bNoTaxAmount);
			bTaxPrice=bTaxAmount.divide(bNum, 6, BigDecimal.ROUND_HALF_UP);
		}
		Map map = new HashMap();
		map.put("taxPrice", bTaxPrice);
		map.put("taxAmount", bTaxAmount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
		map.put("noTaxPrice", bNoTaxPrice);
		map.put("noTaxAmount", bNoTaxAmount.setScale(decimalLen,BigDecimal.ROUND_HALF_UP));
		map.put("tax", bTax);

		GoodsInfo goodsInfo = getGoodsInfoByID(goodsId);
		if(null!=goodsInfo){
			BigDecimal boxprice = goodsInfo.getBoxnum().multiply(bTaxPrice).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
			BigDecimal noboxprice = goodsInfo.getBoxnum().multiply(bNoTaxPrice).setScale(6, BigDecimal.ROUND_HALF_UP);
			map.put("boxPrice", boxprice);
			map.put("noboxPrice", noboxprice);
		}

		addJSONObject(map);
		return SUCCESS;
	}



	/**
	 * 计算单价税额
	 * @param type 1为含税单价，2为无税单价
	 * @param price 含税单价或无税单价
	 * @param rate 税率
	 * @return 单价税额
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 28, 2013
	 */
	protected BigDecimal computeTax(int type, BigDecimal price, BigDecimal rate) throws Exception{
		BigDecimal result = new BigDecimal(0);
		if(type == 1){
			BigDecimal noTaxPrice = price.divide(rate.add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
			result = price.subtract(noTaxPrice);
		}
		else if(type == 2){
			BigDecimal taxPrice = price.multiply(rate.add(new BigDecimal(1)));
			result = taxPrice.subtract(price);
		}
		return result;
	}

	/**
	 * 订单审核自动生成发货通知单
	 * @param order
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 28, 2013
	 */
	protected String addDispatchBillAuto(Order order) throws Exception{
		String result = null;
		SysUser sysUser = getSysUser();
		DispatchBill dispatchBill = new DispatchBill();
		dispatchBill.setAdddeptid(sysUser.getDepartmentid());
		dispatchBill.setAdddeptname(sysUser.getDepartmentname());
		dispatchBill.setAdduserid(sysUser.getUserid());
		dispatchBill.setAddusername(sysUser.getName());
		dispatchBill.setBillno(order.getId());
		dispatchBill.setBusinessdate(order.getBusinessdate());
		dispatchBill.setCustomerid(order.getCustomerid());
		dispatchBill.setRemark(order.getRemark());
		dispatchBill.setPaytype(order.getPaytype());
		dispatchBill.setHandlerid(order.getHandlerid());
		dispatchBill.setSalesdept(order.getSalesdept());
		dispatchBill.setSalesuser(order.getSalesuser());
		dispatchBill.setSettletype(order.getSettletype());
		dispatchBill.setSource("1");
		dispatchBill.setStatus("2");
		dispatchBill.setField01(order.getField01());
		dispatchBill.setField02(order.getField02());
		dispatchBill.setField03(order.getField03());
		dispatchBill.setField04(order.getField04());
		dispatchBill.setField05(order.getField05());
		dispatchBill.setField06(order.getField06());
		dispatchBill.setField07(order.getField07());
		dispatchBill.setField08(order.getField08());
		dispatchBill.setBillDetailList(orderDetailToBillDetail(order.getOrderDetailList(), ""));
		if (isAutoCreate("t_sales_dispatchbill")) {
			// 获取自动编号
			String id = getAutoCreateSysNumbderForeign(dispatchBill, "t_sales_dispatchbill");
			dispatchBill.setId(id);
		}
		else{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");
			Random random = new Random();
			String rand = random.nextInt(99999999) + "";
			String id = dateFormat.format(new Date()) + rand;
			dispatchBill.setId(id);
		}
		if(salesDispatchBillService.addDispatchBill(dispatchBill)){
			result = dispatchBill.getId();
		}
		return result;
	}

	/**
	 * 订单明细转为发货通知单明细
	 * @param orderDetailList 订单明细
	 * @param billId 发货单编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 28, 2013
	 */
	protected List<DispatchBillDetail> orderDetailToBillDetail(List<OrderDetail> orderDetailList, String billId) throws Exception{
		List<DispatchBillDetail> billDetailList = new ArrayList<DispatchBillDetail>();
		for(OrderDetail orderDetail : orderDetailList){
			DispatchBillDetail dispatchBillDetail = new DispatchBillDetail();
			dispatchBillDetail.setGoodsid(orderDetail.getGoodsid());
			dispatchBillDetail.setUnitid(orderDetail.getUnitid());
			dispatchBillDetail.setUnitname(orderDetail.getUnitname());
			dispatchBillDetail.setUnitnum(orderDetail.getUnitnum());
			dispatchBillDetail.setAuxnum(orderDetail.getAuxnum());
			dispatchBillDetail.setAuxnumdetail(orderDetail.getAuxnumdetail());
			dispatchBillDetail.setAuxunitid(orderDetail.getAuxunitid());
			dispatchBillDetail.setAuxunitname(orderDetail.getAuxunitname());
			dispatchBillDetail.setBatchno(orderDetail.getBatchno());
			dispatchBillDetail.setDeliverydate(orderDetail.getDeliverydate());
			dispatchBillDetail.setExpirationdate(orderDetail.getExpirationdate());
			dispatchBillDetail.setRemark(orderDetail.getRemark());
			dispatchBillDetail.setTax(orderDetail.getTax());
			dispatchBillDetail.setTaxamount(orderDetail.getTaxamount());
			dispatchBillDetail.setTaxprice(orderDetail.getTaxprice());
			dispatchBillDetail.setTaxtype(orderDetail.getTaxtype());
			dispatchBillDetail.setTaxtypename(orderDetail.getTaxtypename());
			dispatchBillDetail.setUnitid(orderDetail.getUnitid());
			dispatchBillDetail.setUnitname(orderDetail.getUnitname());
			dispatchBillDetail.setUnitnum(orderDetail.getUnitnum());
			dispatchBillDetail.setBillid(billId);
			dispatchBillDetail.setBillno(orderDetail.getOrderid());
			dispatchBillDetail.setBilldetailno(orderDetail.getId());
			dispatchBillDetail.setField01(orderDetail.getField01());
			dispatchBillDetail.setField02(orderDetail.getField02());
			dispatchBillDetail.setField03(orderDetail.getField03());
			dispatchBillDetail.setField04(orderDetail.getField04());
			dispatchBillDetail.setField05(orderDetail.getField05());
			dispatchBillDetail.setField06(orderDetail.getField06());
			dispatchBillDetail.setField07(orderDetail.getField07());
			dispatchBillDetail.setField08(orderDetail.getField08());
			billDetailList.add(dispatchBillDetail);
		}
		return billDetailList;
	}

	/**
	 * 订单反审时检查订单的下游发货通知单是否审核，未审核则删除，审核则不可反审
	 * @param id 订单编号
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date May 28, 2013
	 */
	protected String oppauditOrderCheckDispatchBill(String id) throws Exception{
		String result = "";
		DispatchBill dispatchBill = salesDispatchBillService.getDispatchBillByOrder(id);
		if(dispatchBill != null){
			if("1".equals(dispatchBill.getStatus()) || "2".equals(dispatchBill.getStatus())){
				salesDispatchBillService.deleteDispatchBill(dispatchBill.getId());
				result = "1";
			}
			else{
				result = "0";
			}
		}
		else{
			result = "1";
		}
		return result;
	}
	/**
	 * 获取回单销售退货通知单 合计列表（申请抽单列表）
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Oct 9, 2013
	 */
	public String getReceiptAndRejectBillList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReceiptService.getReceiptAndRejectBillList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}

	/**
	 * 回单销售退货通知单 合计列表（申请开票列表）
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Feb 14, 2015
	 */
	public String getReceiptAndRejectBillListForInvoiceBill()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = salesReceiptService.getReceiptAndRejectBillListForInvoiceBill(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}


	/**
	 * 导出回单销售退货通知单 合计列表（申请开票列表）
	 * @throws Exception
	 * @author chenwei
	 * @date Feb 26, 2014
	 */
	public void exportReceiptAndRejectBillListForInvoiceBill() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("exportflag", "true");
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
		PageData pageData = salesReceiptService.getReceiptAndRejectBillListForInvoiceBill(pageMap);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		//根据定义类型获取显示的字段
		firstMap.put("id", "编号");
		firstMap.put("billtypename", "单据类型");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("orderid", "订单编号");
		firstMap.put("orderdate", "订单日期");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("headcustomername", "总店名称");
		firstMap.put("handlerid", "对方经手人");
		firstMap.put("totaltaxamount", "总金额");
		firstMap.put("uninvoiceamount", "未开票金额");
		firstMap.put("duefromdate", "应收日期");
		firstMap.put("salesdept", "销售部门");
		firstMap.put("salesuser", "客户业务员");
		firstMap.put("addusername", "制单人");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		List<Map<String, Object>> list = objectToMapList(pageData,firstMap);
		result.addAll(list);

		ExcelUtils.exportExcel(result, title);
	}

	/**
	 * 导出回单销售退货通知单 合计列表（申请抽单列表）
	 * @throws Exception
	 * @author chenwei
	 * @date Feb 26, 2014
	 */
	public void exportReceiptAndRejectBillList() throws Exception{
		Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
		map.put("exportflag", "true");
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
		PageData pageData = salesReceiptService.getReceiptAndRejectBillList(pageMap);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		//根据定义类型获取显示的字段
		firstMap.put("id", "编号");
		firstMap.put("billtypename", "单据类型");
		firstMap.put("businessdate", "业务日期");
		firstMap.put("orderid", "订单编号");
		firstMap.put("sourceid", "客户单号");
		firstMap.put("orderdate", "订单日期");
		firstMap.put("customerid", "客户编号");
		firstMap.put("customername", "客户名称");
		firstMap.put("headcustomername", "总店名称");
		firstMap.put("handlerid", "对方经手人");
		firstMap.put("totaltaxamount", "总金额");
		firstMap.put("uninvoiceamount", "未开票金额");
		firstMap.put("duefromdate", "应收日期");
		firstMap.put("salesdept", "销售部门");
		firstMap.put("salesuser", "客户业务员");
		firstMap.put("addusername", "制单人");
		firstMap.put("remark", "备注");
		result.add(firstMap);
		List<Map<String, Object>> list = objectToMapList(pageData,firstMap);
		result.addAll(list);

		ExcelUtils.exportExcel(result, title);
	}
	/**
	 * 将类对象转化为List<Map<String, Object>>
	 * @param pageData
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Nov 26, 2013
	 */
	private List<Map<String, Object>> objectToMapList(PageData pageData,Map<String, Object> firstMap)throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		if(null != pageData.getList() && pageData.getList().size() != 0){
			for(Map<String, Object> map : new ArrayList<Map>(pageData.getList())){
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
		if(null != pageData.getFooter() && pageData.getFooter().size() != 0){
			for(Map<String, Object> map : new ArrayList<Map>(pageData.getFooter())){
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
	 * 获取回单销售退货通知单 明细合计列表
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Dec 23, 2013
	 */
	public String getReceiptAndRejectBillDetailList() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		List list = salesReceiptService.getReceiptAndRejectBillDetailList(pageMap);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 获取回单销售退货通知单明细合计列表
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Oct 9, 2013
	 */
	public String showReceiptAndRejectBillDetailList() throws Exception{
		String ids = request.getParameter("id");
		String customerid = request.getParameter("customerid");
		String customername = request.getParameter("customername");
		String brandids = request.getParameter("brandid");
		//未选中的单据与明细
		String uncheckedjson = request.getParameter("uncheckedjson");
		Map unCheckMap = new HashMap();
		if(null!=uncheckedjson && !"".equals(uncheckedjson)){
			JSONArray array = JSONArray.fromObject(uncheckedjson);
			for(int i=0; i<array.size(); i++){
				JSONObject jsonObject = (JSONObject)array.get(i);
				if(!jsonObject.isEmpty()){
					String detailid = jsonObject.getString("id");
					String billid = jsonObject.getString("billid");
					if(!unCheckMap.containsKey(detailid)){
						Map billMap = new HashMap();
						billMap.put(billid, billid);
						unCheckMap.put(detailid, billMap);
					}else{
						Map billMap = (Map) unCheckMap.get(detailid);
						billMap.put(billid, billid);
						unCheckMap.put(detailid, billMap);
					}
				}
			}
		}
		Map customerMap = new HashMap();
		Customer customer = getCustomerById(customerid);
		if(null!=customer){
			customerMap.put(customerid, customer.getName());
		}
		if(null!=ids){
			String[] idArr =ids.split(",");
			List list = new ArrayList();
			BigDecimal totalamount = BigDecimal.ZERO;
			for(String id : idArr){
				Receipt receipt = salesReceiptService.getReceipt(id);
				if(null!=receipt){
					Customer rcustomer = getCustomerById(receipt.getCustomerid());
					if(null!=rcustomer){
						customerMap.put(receipt.getCustomerid(), rcustomer.getName());
					}
					List<ReceiptDetail> detailList = receipt.getReceiptDetailList();
					for(ReceiptDetail receiptDetail : detailList){
						Map map = BeanUtils.describe(receiptDetail);
						map.put("billtype", "1");
						map.put("goodsInfo", receiptDetail.getGoodsInfo());
						map.put("taxamount", receiptDetail.getReceipttaxamount());
						map.put("notaxamount", receiptDetail.getReceiptnotaxamount());
						map.put("unitnum", receiptDetail.getReceiptnum());
						map.put("isdiscount", receiptDetail.getIsdiscount());
						Map auxMap = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), receiptDetail.getReceiptnum());
						String auxnumdetail = (String) auxMap.get("auxnumdetail");
						map.put("auxnumdetail", auxnumdetail);
						String isinvoice = (String) map.get("isinvoice");
						if("0".equals(isinvoice)){
							boolean addflag = true;
							if(unCheckMap.containsKey(receiptDetail.getId())){
								Map billMap = (Map) unCheckMap.get(receiptDetail.getId());
								if(billMap.containsKey(receiptDetail.getBillid())){
									addflag = false;
								}
							}
							if(addflag){
								if(StringUtils.isEmpty(brandids)){
									list.add(map);
									totalamount = totalamount.add(receiptDetail.getReceipttaxamount());
								}else if(brandids.indexOf(receiptDetail.getBrandid()) != -1){
									list.add(map);
									totalamount = totalamount.add(receiptDetail.getReceipttaxamount());
								}
							}
						}
					}
				}else{
					RejectBill rejectBill = salesRejectBillService.getRejectBill(id);
					if(null!=rejectBill){
						Customer rcustomer = getCustomerById(rejectBill.getCustomerid());
						if(null!=rcustomer){
							customerMap.put(rejectBill.getCustomerid(), rcustomer.getName());
						}
						List<RejectBillDetail> detaillist = rejectBill.getBillDetailList();
						for(RejectBillDetail rejectBillDetail : detaillist){
							Map map = BeanUtils.describe(rejectBillDetail);
							map.put("billtype", "2");
							map.put("goodsInfo", rejectBillDetail.getGoodsInfo());
							map.put("taxamount", rejectBillDetail.getTaxamount().negate());
							map.put("notaxamount", rejectBillDetail.getNotaxamount().negate());
							map.put("unitnum", rejectBillDetail.getUnitnum().negate());
							map.put("isdiscount", "0");
							Map auxMap = countGoodsInfoNumber(rejectBillDetail.getGoodsid(),rejectBillDetail.getAuxunitid(), rejectBillDetail.getUnitnum());
							String auxnumdetail = (String) auxMap.get("auxnumdetail");
							map.put("auxnumdetail", auxnumdetail);
							String isinvoice = (String) map.get("isinvoice");
							if("0".equals(isinvoice)){
								boolean addflag = true;
								if(unCheckMap.containsKey(rejectBillDetail.getId())){
									Map billMap = (Map) unCheckMap.get(rejectBillDetail.getId());
									if(billMap.containsKey(rejectBillDetail.getBillid())){
										addflag = false;
									}
								}
								if(addflag){
									if(StringUtils.isEmpty(brandids)){
										list.add(map);
										totalamount = totalamount.add(rejectBillDetail.getTaxamount().negate());
									}else if(brandids.indexOf(rejectBillDetail.getBrandid()) != -1){
										list.add(map);
										totalamount = totalamount.add(rejectBillDetail.getTaxamount().negate());
									}
								}
							}
						}
					}else{
						CustomerPushBalance customerPushBalance = salesReceiptService.getCustomerPushBalanceByID(id);
						if(null!=customerPushBalance && "3".equals(customerPushBalance.getStatus())){
							Customer cpcustomer = getCustomerById(customerPushBalance.getCustomerid());
							if(null!=cpcustomer){
								customerMap.put(customerPushBalance.getCustomerid(), cpcustomer.getName());
							}
							Map map = new HashMap();
							map.put("id", customerPushBalance.getId());
							map.put("billid", customerPushBalance.getId());
							map.put("billtype", "3");
							GoodsInfo goodsInfo = new GoodsInfo();
							Brand brand = getBaseGoodsService().getBrandInfo(customerPushBalance.getBrandid());
							if(null!=brand){
								map.put("goodsid", customerPushBalance.getBrandid());
								goodsInfo.setId(customerPushBalance.getBrandid());
								goodsInfo.setName(brand.getName()+"折扣");
							}
							map.put("goodsInfo", goodsInfo);
							map.put("taxamount", customerPushBalance.getAmount());
							map.put("notaxamount", BigDecimal.ZERO);
							map.put("isdiscount", "0");
							map.put("auxnumdetail", "");
							map.put("taxtype", customerPushBalance.getDefaulttaxtype());
							map.put("taxtypename", customerPushBalance.getDefaulttaxtypename());
							if(StringUtils.isEmpty(brandids)){
								list.add(map);
								totalamount = totalamount.add(customerPushBalance.getAmount());
							}else if(brandids.indexOf(customerPushBalance.getBrandid()) != -1){
								list.add(map);
								totalamount = totalamount.add(customerPushBalance.getAmount());
							}
						}else{
							BeginAmount beginAmount = salesReceiptService.getBeginAmountByID(id);
							if(null!=beginAmount && "3".equals(beginAmount.getStatus())){
								Customer cpcustomer = getCustomerById(beginAmount.getCustomerid());
								if(null!=cpcustomer){
									customerMap.put(beginAmount.getCustomerid(), cpcustomer.getName());
								}
								Map map = new HashMap();
								map.put("id", beginAmount.getId());
								map.put("billid", beginAmount.getId());
								map.put("billtype", "4");
								GoodsInfo goodsInfo = new GoodsInfo();
								map.put("goodsid", "QC");
								goodsInfo.setId("");
								goodsInfo.setName("客户应收款期初");
								map.put("goodsInfo", goodsInfo);
								map.put("taxamount", beginAmount.getAmount());
								map.put("notaxamount", BigDecimal.ZERO);
								map.put("isdiscount", "0");
								map.put("auxnumdetail", "");
								list.add(map);
								totalamount = totalamount.add(beginAmount.getAmount());
							}
						}
					}
				}
			}
			//addJSONArray(list);
			request.setAttribute("list", list);
			request.setAttribute("totalamount", totalamount);
			request.setAttribute("customername", customername);
			request.setAttribute("customerid", customerid);
			request.setAttribute("customerMap", customerMap);

			request.setAttribute("pattern", CommonUtils.getFormatNumberType());
		}
		return "success";
	}

	/**
	 * 获取回单销售退货通知单明细合计列表(申请开票)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Feb 14, 2015
	 */
	public String showReceiptAndRejectBillDetailListForInvoiceBill()throws Exception{
		String ids = request.getParameter("id");
		String customerid = request.getParameter("customerid");
		String customername = request.getParameter("customername");
		String brandid = request.getParameter("brandid");
		String iswriteoff = request.getParameter("iswriteoff");
		if(StringUtils.isEmpty(iswriteoff)){
			iswriteoff = "0,1";
		}
		String taxtype = request.getParameter("taxtype");
		if(null == taxtype || "".equals(taxtype.trim())){
			taxtype=null;
		}
		//未选中的单据与明细
		String uncheckedjson = request.getParameter("uncheckedjson");
		Map unCheckMap = new HashMap();
		if(null!=uncheckedjson && !"".equals(uncheckedjson)){
			JSONArray array = JSONArray.fromObject(uncheckedjson);
			for(int i=0; i<array.size(); i++){
				JSONObject jsonObject = (JSONObject)array.get(i);
				if(!jsonObject.isEmpty()){
					String detailid = jsonObject.getString("id");
					String billid = jsonObject.getString("billid");
					if(!unCheckMap.containsKey(detailid)){
						Map billMap = new HashMap();
						billMap.put(billid, billid);
						unCheckMap.put(detailid, billMap);
					}else{
						Map billMap = (Map) unCheckMap.get(detailid);
						billMap.put(billid, billid);
						unCheckMap.put(detailid, billMap);
					}
				}
			}
		}
		Map customerMap = new HashMap();
		Customer customer = getCustomerById(customerid);
		if(null!=customer){
			customerMap.put(customerid, customer.getName());
		}
		if(null!=ids){
			String[] idArr =ids.split(",");
			List list = new ArrayList();
			BigDecimal totalamount = BigDecimal.ZERO;
			for(String id : idArr){
				Receipt receipt = salesReceiptService.getReceiptInfoById(id);
				if(null!=receipt){
					Customer rcustomer = getCustomerById(receipt.getCustomerid());
					if(null!=rcustomer){
						customerMap.put(receipt.getCustomerid(), rcustomer.getName());
					}
					Map receiptDetailMap=new HashMap();
					receiptDetailMap.put("billid",receipt.getId());
					receiptDetailMap.put("taxtype",taxtype);
					List<ReceiptDetail> detailList = salesReceiptService.getReceiptDetailListSumDiscountByMap(receiptDetailMap);
					for(ReceiptDetail receiptDetail : detailList){
						if(iswriteoff.indexOf(receiptDetail.getIswriteoff()) != -1){
							Map map = BeanUtils.describe(receiptDetail);
							map.put("billtype", "1");
							map.put("goodsInfo", receiptDetail.getGoodsInfo());
							map.put("taxamount", receiptDetail.getReceipttaxamount());
							map.put("notaxamount", receiptDetail.getReceiptnotaxamount());
							map.put("unitnum", receiptDetail.getReceiptnum());
							map.put("isdiscount", receiptDetail.getIsdiscount());
							Map auxMap = countGoodsInfoNumber(receiptDetail.getGoodsid(), receiptDetail.getAuxunitid(), receiptDetail.getReceiptnum());
							String auxnumdetail = (String) auxMap.get("auxnumdetail");
							map.put("auxnumdetail", auxnumdetail);
							String isinvoicebill = (String) map.get("isinvoicebill");
							if("0".equals(isinvoicebill)){
								boolean addflag = true;
								if(unCheckMap.containsKey(receiptDetail.getId())){
									Map billMap = (Map) unCheckMap.get(receiptDetail.getId());
									if(billMap.containsKey(receiptDetail.getBillid())){
										addflag = false;
									}
								}
								if(addflag){
									if(null==brandid || "".equals(brandid)){
										list.add(map);
										totalamount = totalamount.add(receiptDetail.getReceipttaxamount());
									}else if(brandid.equals(receiptDetail.getBrandid())){
										list.add(map);
										totalamount = totalamount.add(receiptDetail.getReceipttaxamount());
									}else if(brandid.contains(",")){
										String[] bid = brandid.split(",");
										boolean flag = false ;
										for(String brand : bid) {
											if (brand.indexOf(receiptDetail.getBrandid()) != -1) {
												flag = true;
												break;
											}
										}
										if(flag){
											totalamount = totalamount.add(receiptDetail.getReceipttaxamount());
											list.add(map);
										}
									}
								}
							}
						}
					}
				}else{
					RejectBill rejectBill = salesRejectBillService.getRejectBillInfoById(id);
					if(null!=rejectBill){
						Customer rcustomer = getCustomerById(rejectBill.getCustomerid());
						if(null!=rcustomer){
							customerMap.put(rejectBill.getCustomerid(), rcustomer.getName());
						}
						Map rejectDetailMap=new HashMap();
						rejectDetailMap.put("billid",rejectBill.getId());
						rejectDetailMap.put("taxtype",taxtype);
						List<RejectBillDetail> detaillist = salesRejectBillService.getRejectBillDetailListByMap(rejectDetailMap);
						for(RejectBillDetail rejectBillDetail : detaillist){
							if(iswriteoff.indexOf(rejectBillDetail.getIswriteoff()) != -1){
								Map map = BeanUtils.describe(rejectBillDetail);
								map.put("billtype", "2");
								map.put("goodsInfo", rejectBillDetail.getGoodsInfo());
								map.put("taxamount", rejectBillDetail.getTaxamount().negate());
								map.put("notaxamount", rejectBillDetail.getNotaxamount().negate());
								map.put("unitnum", rejectBillDetail.getUnitnum().negate());
								map.put("isdiscount", "0");
								Map auxMap = countGoodsInfoNumber(rejectBillDetail.getGoodsid(),rejectBillDetail.getAuxunitid(), rejectBillDetail.getUnitnum());
								String auxnumdetail = (String) auxMap.get("auxnumdetail");
								map.put("auxnumdetail", auxnumdetail);
								String isinvoicebill = (String) map.get("isinvoicebill");
								if("0".equals(isinvoicebill)){
									boolean addflag = true;
									if(unCheckMap.containsKey(rejectBillDetail.getId())){
										Map billMap = (Map) unCheckMap.get(rejectBillDetail.getId());
										if(billMap.containsKey(rejectBillDetail.getBillid())){
											addflag = false;
										}
									}
									if(addflag){
										if(null==brandid || "".equals(brandid)){
											list.add(map);
											totalamount = totalamount.add(rejectBillDetail.getTaxamount().negate());
										}else if(brandid.equals(rejectBillDetail.getBrandid())){
											list.add(map);
											totalamount = totalamount.add(rejectBillDetail.getTaxamount().negate());
										}else if(brandid.contains(",")){
											String[] bid = brandid.split(",");
											boolean flag = false ;
											for(String brand : bid) {
												if (brand.indexOf(rejectBillDetail.getBrandid()) != -1) {
													flag = true;
													break;
												}
											}
											if(flag){
												totalamount = totalamount.add(rejectBillDetail.getTaxamount().negate());
												list.add(map);
											}
										}
									}
								}
							}
						}
					}else{
						CustomerPushBalance customerPushBalance = salesReceiptService.getCustomerPushBalanceByID(id);
						if(null!=customerPushBalance && ("3".equals(customerPushBalance.getStatus()) || "4".equals(customerPushBalance.getStatus())) && iswriteoff.indexOf(customerPushBalance.getIswriteoff()) != -1){
							Customer cpcustomer = getCustomerById(customerPushBalance.getCustomerid());
							if(null!=cpcustomer){
								customerMap.put(customerPushBalance.getCustomerid(), cpcustomer.getName());
							}
							Map map = new HashMap();
							map.put("id", customerPushBalance.getId());
							map.put("billid", customerPushBalance.getId());
							map.put("billtype", "3");
							GoodsInfo goodsInfo = new GoodsInfo();
							Brand brand = getBaseGoodsService().getBrandInfo(customerPushBalance.getBrandid());
							if(null!=brand){
								map.put("goodsid", customerPushBalance.getBrandid());
								goodsInfo.setId(customerPushBalance.getBrandid());
								goodsInfo.setName(brand.getName()+"折扣");
							}
							map.put("goodsInfo", goodsInfo);
							map.put("taxamount", customerPushBalance.getAmount());
							map.put("notaxamount", BigDecimal.ZERO);
							map.put("isdiscount", "0");
							map.put("auxnumdetail", "");
							map.put("taxtype", customerPushBalance.getDefaulttaxtype());
							map.put("taxtypename", customerPushBalance.getDefaulttaxtypename());
							if(null==brandid || "".equals(brandid)){
								list.add(map);
								totalamount = totalamount.add(customerPushBalance.getAmount());
							}else if(brandid.equals(customerPushBalance.getBrandid())){
								list.add(map);
								totalamount = totalamount.add(customerPushBalance.getAmount());
							}else if(brandid.contains(",")){
								String[] bid = brandid.split(",");
								boolean flag = false ;
								for(String bran : bid) {
									if (bran.indexOf(customerPushBalance.getBrandid()) != -1) {
										flag = true;
										break;
									}
								}
								if(flag){
									totalamount = customerPushBalance.getAmount();
									list.add(map);
								}
							}
						}
					}
				}
			}
			//addJSONArray(list);
			request.setAttribute("list", list);
			request.setAttribute("totalamount", totalamount);
			request.setAttribute("customername", customername);
			request.setAttribute("customerid", customerid);
			request.setAttribute("customerMap", customerMap);

			request.setAttribute("pattern", CommonUtils.getFormatNumberType());
		}
		return SUCCESS;
	}

	/**
	 * 根据单据编号 获取明细列表
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Dec 16, 2013
	 */
	public String showReceiptOrRejectBillDetailListPage() throws Exception{
		String id = request.getParameter("id");
		String billtype = request.getParameter("billtype");
		String brandids = request.getParameter("brandid");
		List list = new ArrayList();
		BigDecimal totalamount = BigDecimal.ZERO;
		if("1".equals(billtype)){
			List<ReceiptDetail> detailList = salesReceiptService.getReceiptDetailListByApplyWriteoff(id);
			for(ReceiptDetail receiptDetail : detailList){
				Map map = BeanUtils.describe(receiptDetail);
				map.put("billtype", "1");
				if(null!=receiptDetail.getGoodsInfo()){
					map.put("goodsname",receiptDetail.getGoodsInfo().getName() );
					map.put("boxnum",receiptDetail.getGoodsInfo().getBoxnum() );
					map.put("brandname", receiptDetail.getGoodsInfo().getBrandName());
					map.put("barcode", receiptDetail.getGoodsInfo().getBarcode());
				}
				map.put("taxamount", receiptDetail.getReceipttaxamount());
				map.put("notaxamount", receiptDetail.getReceiptnotaxamount());
				map.put("unitnum", receiptDetail.getReceiptnum());
				String isinvoice = (String) map.get("isinvoice");
				if("0".equals(isinvoice)){
					if(StringUtils.isEmpty(brandids)){
						list.add(map);
						totalamount = totalamount.add(receiptDetail.getReceipttaxamount());
					}else if(brandids.indexOf(receiptDetail.getBrandid()) != -1){
						list.add(map);
						totalamount = totalamount.add(receiptDetail.getReceipttaxamount());
					}
				}
			}
		}else if("2".equals(billtype)){
			RejectBill rejectBill = salesRejectBillService.getRejectBill(id);
			if(null!=rejectBill){
				List<RejectBillDetail> detaillist = rejectBill.getBillDetailList();
				for(RejectBillDetail rejectBillDetail : detaillist){
					Map map = BeanUtils.describe(rejectBillDetail);
					map.put("billtype", "2");
					map.put("isdiscount", "0");
					if(null!=rejectBillDetail.getGoodsInfo()){
						map.put("goodsname",rejectBillDetail.getGoodsInfo().getName() );
						map.put("boxnum",rejectBillDetail.getGoodsInfo().getBoxnum() );
						map.put("brandname", rejectBillDetail.getGoodsInfo().getBrandName());
						map.put("barcode", rejectBillDetail.getGoodsInfo().getBarcode());
					}
					map.put("taxamount", rejectBillDetail.getTaxamount().negate());
					map.put("notaxamount", rejectBillDetail.getNotaxamount().negate());
					map.put("unitnum", rejectBillDetail.getUnitnum().negate());
					String isinvoice = (String) map.get("isinvoice");
					if("0".equals(isinvoice)){
						if(StringUtils.isEmpty(brandids)){
							list.add(map);
							totalamount = totalamount.add(rejectBillDetail.getTaxamount().negate());
						}else if(brandids.indexOf(rejectBillDetail.getBrandid()) != -1){
							list.add(map);
							totalamount = totalamount.add(rejectBillDetail.getTaxamount().negate());
						}
					}
				}
			}
		}else if("3".equals(billtype)){
			ICustomerPushBanlanceService customerPushBanlanceService = (ICustomerPushBanlanceService) SpringContextUtils.getBean("customerPushBanlanceService");
			CustomerPushBalance customerPushBalance = customerPushBanlanceService.showCustomerPushBanlanceInfo(id);
			if(null != customerPushBalance){
				Map map = BeanUtils.describe(customerPushBalance);
				map.put("billid",customerPushBalance.getId());
				map.put("billtype", "3");
				map.put("isdiscount", "0");
				Brand brand = getBaseGoodsService().getBrandInfo(customerPushBalance.getBrandid());
				if(null != brand){
					map.put("goodsname",brand.getName()+"冲差");
				}else{
					map.put("goodsname","");
				}
				map.put("brandname", brand.getName());
				map.put("boxnum","");
				map.put("taxamount", customerPushBalance.getAmount());
				map.put("notaxamount", "");
				map.put("unitnum", "");
				map.put("taxtype", customerPushBalance.getDefaulttaxtype());
				map.put("taxtypename", customerPushBalance.getDefaulttaxtypename());
				String isrefer = (String) map.get("isrefer");
				if("0".equals(isrefer)){
					if(StringUtils.isEmpty(brandids)){
						list.add(map);
						totalamount = customerPushBalance.getAmount();
					}else if(brandids.indexOf(customerPushBalance.getBrandid()) != -1){
						list.add(map);
						totalamount = customerPushBalance.getAmount();
					}
				}
			}
		}
		String jsonStr = JSONUtils.listToJsonStr(list);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("totalamount", totalamount);
		request.setAttribute("id", id);
		return "success";
	}

	/**
	 * 根据单据编号 获取明细列表(申请开票)
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Feb 26, 2015
	 */
	public String showReceiptOrRejectBillDetailListForInvoiceBillPage() throws Exception{
		String id = request.getParameter("id");
		String billtype = request.getParameter("billtype");
		String brandid = request.getParameter("brandid");
		String iswriteoff = request.getParameter("iswriteoff");
		if(StringUtils.isEmpty(iswriteoff)){
			iswriteoff = "0,1";
		}
		List list = new ArrayList();
		BigDecimal totalamount = BigDecimal.ZERO;
		if("1".equals(billtype)){
			List<ReceiptDetail> detailList = salesReceiptService.getReceiptDetailListByApplyWriteoff(id);
			for(ReceiptDetail receiptDetail : detailList) {
				if(iswriteoff.indexOf(receiptDetail.getIswriteoff()) != -1){
					Map map = BeanUtils.describe(receiptDetail);
					map.put("billtype", "1");
					if(null!=receiptDetail.getGoodsInfo()){
						map.put("goodsname",receiptDetail.getGoodsInfo().getName() );
						map.put("boxnum",receiptDetail.getGoodsInfo().getBoxnum() );
					}
					map.put("taxamount", receiptDetail.getReceipttaxamount());
					map.put("notaxamount", receiptDetail.getReceiptnotaxamount());
					map.put("unitnum", receiptDetail.getReceiptnum());
					String isinvoicebill = (String) map.get("isinvoicebill");
					if("0".equals(isinvoicebill)){
						if(null==brandid || "".equals(brandid)){
							list.add(map);
						}else if(brandid.equals(receiptDetail.getBrandid())){
							list.add(map);
						}else if(brandid.contains(",")){
							String[] bid = brandid.split(",");
							boolean flag = false ;
							for(String brand : bid) {
								if (brand.equals(receiptDetail.getBrandid())) {
									flag = true;
									break;
								}
							}
							if(flag){
								list.add(map);
							}
						}
					}
					totalamount = totalamount.add(receiptDetail.getReceipttaxamount());
				}
			}
		}else if("2".equals(billtype)){
			RejectBill rejectBill = salesRejectBillService.getRejectBill(id);
			if(null!=rejectBill){
				List<RejectBillDetail> detaillist = rejectBill.getBillDetailList();
				for(RejectBillDetail rejectBillDetail : detaillist) {
					if(iswriteoff.indexOf(rejectBillDetail.getIswriteoff()) != -1){
						Map map = BeanUtils.describe(rejectBillDetail);
						map.put("billtype", "2");
						map.put("isdiscount", "0");
						if(null!=rejectBillDetail.getGoodsInfo()){
							map.put("goodsname",rejectBillDetail.getGoodsInfo().getName() );
							map.put("boxnum",rejectBillDetail.getGoodsInfo().getBoxnum() );
						}
						map.put("taxamount", rejectBillDetail.getTaxamount().negate());
						map.put("notaxamount", rejectBillDetail.getNotaxamount().negate());
						map.put("unitnum", rejectBillDetail.getUnitnum().negate());
						String isinvoicebill = (String) map.get("isinvoicebill");
						if("0".equals(isinvoicebill)){
							if(brandid.contains(",")){
								String[] brandsid = brandid.split(",");
							}
							if(null==brandid || "".equals(brandid)){
								list.add(map);
							}else if(brandid.equals(rejectBillDetail.getBrandid())){
								list.add(map);
							}else if(brandid.contains(",")){
								String[] bid = brandid.split(",");
								boolean flag = false ;
								for(String brand : bid) {
									if (brand.equals(rejectBillDetail.getBrandid())) {
										flag = true;
										break;
									}
								}
								if(flag){
									list.add(map);
								}
							}
						}
						totalamount = totalamount.add(rejectBillDetail.getTaxamount().negate());
					}
				}
			}
		}else if("3".equals(billtype)){
			ICustomerPushBanlanceService customerPushBanlanceService = (ICustomerPushBanlanceService) SpringContextUtils.getBean("customerPushBanlanceService");
			CustomerPushBalance customerPushBalance = customerPushBanlanceService.showCustomerPushBanlanceInfo(id);
			if(null != customerPushBalance && iswriteoff.indexOf(customerPushBalance.getIswriteoff()) != -1){
				Map map = BeanUtils.describe(customerPushBalance);
				map.put("billid",customerPushBalance.getId());
				map.put("billtype", "3");
				map.put("isdiscount", "0");
				Brand brand = getBaseGoodsService().getBrandInfo(customerPushBalance.getBrandid());
				if(null != brand){
					map.put("goodsname",brand.getName()+"冲差");
				}else{
					map.put("goodsname","");
				}
				map.put("boxnum","");
				map.put("taxamount", customerPushBalance.getAmount());
				map.put("notaxamount", "");
				map.put("unitnum", "");

				map.put("taxtype", customerPushBalance.getDefaulttaxtype());
				map.put("taxtypename", customerPushBalance.getDefaulttaxtypename());

				String isinvoicebill = (String) map.get("isinvoicebill");
				if("0".equals(isinvoicebill)){
					if(null==brandid || "".equals(brandid)){
						list.add(map);
					}else if(brandid.equals(customerPushBalance.getBrandid())){
						list.add(map);
					}else if(brandid.contains(",")){
						String[] bid = brandid.split(",");
						boolean flag = false ;
						for(String bra : bid) {
							if (bra.equals(customerPushBalance.getBrandid())) {
								flag = true;
								break;
							}
						}
						if(flag){
							list.add(map);
						}
					}
				}
				totalamount = customerPushBalance.getAmount();
			}
		}
		String jsonStr = JSONUtils.listToJsonStr(list);
		request.setAttribute("detailList", jsonStr);
		request.setAttribute("totalamount", totalamount);
		request.setAttribute("id", id);
		return "success";
	}

	/**
	 * 判断客户是否存在超账期应收款
	 * @return				true存在 false不存在
	 * @throws Exception
	 * @author chenwei
	 * @date Oct 25, 2013
	 */
	public boolean isReceivablePassDateByCustomerid(String customerid) throws Exception{
		return salesReceiptService.isReceivableAmountPassDateByCustomerid(customerid);
	}
	/**
	 * 根据客户编号 判断客户应收款是否在信用额度 信用期限内
	 * 信用期限为空时 信用额度一直有效
	 * 应收费 大于信用额度时 返回false
	 * 应收款 小于等于信用额度时 返回true
	 * 当信用额度为0时 表示一直有效
	 * @param customerid
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Oct 25, 2013
	 */
	public boolean isReceivableInCredit(String customerid) throws Exception{
		return salesReceiptService.isReceivableInCreditByCustomerid(customerid);
	}

	/**
	 * 根据来源编号获取单据类型1销售订单2退货通知单3发货回单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2015-08-18
	 */
	public String checkSourceBillType(String id)throws Exception{
		String billtype = "";
		Order order = salesOrderService.getOnlyOrder(id);
		if(null != order){
			billtype = "1";
		}else{
			RejectBill rejectBill = salesRejectBillService.getRejectBill(id);
			if(null != rejectBill){
				billtype = "2";
			}else{
				Receipt receipt = salesReceiptService.getReceipt(id);
				if(null != receipt){
					billtype = "3";
				}
			}
		}
		return billtype;
	}

	/**
	 * 根据系统参数isreapt 选择是否合并Excel模板导入的相同商品
	 * @author lin_xx
	 * @date 2017/3/14
	 */
	public List getModelOrderListByPram(String isreapt ,int beginRow,int numSheet,int goodsCol,int numCol,int priceCol,int boxnumCol,
										String pricetype,String gtype,String busid,String orderid) throws Exception{

		Map goodsMap = new LinkedHashMap();
		Map numMap = new LinkedHashMap();
		Map priceMap = new LinkedHashMap();
		if(goodsCol > -1){
			goodsMap = ExcelUtils.importExcelByGoodsGolumn(importFile,numSheet,beginRow,0,goodsCol);
		}
		if(numCol > -1){
			numMap =  ExcelUtils.importExcelByColumn(importFile,numSheet,beginRow,0,numCol);
		}
		if(priceCol > -1){
			priceMap =  ExcelUtils.importExcelByColumn(importFile,numSheet,beginRow,0,priceCol);
		}
		Map boxnumMap = new LinkedHashMap();
		if(boxnumCol > -1){
			boxnumMap =  ExcelUtils.importExcelByColumn(importFile,numSheet,beginRow,0,boxnumCol);
		}
		int endRowLine = beginRow + goodsMap.size();

		List<ModelOrder> wareList = new ArrayList<ModelOrder>();
		if("1".equals(isreapt)){
			for (int i = beginRow; i < endRowLine+beginRow; i++) {

				ModelOrder modelOrder = new ModelOrder();
				modelOrder.setUnitnum((String)numMap.get(i));
				if("1".equals(gtype)){
					modelOrder.setBarcode((String)goodsMap.get(i));
				}else if("2".equals(gtype)){
					modelOrder.setShopid((String)goodsMap.get(i));
				}else if("3".equals(gtype)){
					modelOrder.setSpell((String)goodsMap.get(i));
				}else if("4".equals(gtype)){
					modelOrder.setGoodsid((String)goodsMap.get(i));
				}
				modelOrder.setBusid(busid);
				modelOrder.setOrderId(orderid);
				if(priceMap.size() > 0 && "1".equals(pricetype)){
					modelOrder.setTaxprice((String)priceMap.get(i));
				}
				if(boxnumMap.size() > 0){
					modelOrder.setBoxnum((String) boxnumMap.get(i));
				}
				wareList.add(modelOrder);
			}

		}else{
			Map KeyMap = new LinkedHashMap();
			Map priceKeyMap = new LinkedHashMap();
			Map boxnumKeyMap = new LinkedHashMap();
			for(int i = beginRow; i< endRowLine+beginRow ; ++ i){
				if(KeyMap.containsKey(goodsMap.get(i)) && StringUtils.isNotEmpty((String)numMap.get(i))){
					String thisnum = (String) KeyMap.get(goodsMap.get(i));
					BigDecimal num = BigDecimal.ZERO;
					if(StringUtils.isNotEmpty(thisnum)){
						num = new BigDecimal(thisnum);
					}
					num = num.add(new BigDecimal((String)(numMap.get(i))));
					KeyMap.put(goodsMap.get(i),num.toString());
					if(boxnumMap.size() > 0){
						BigDecimal boxnum = new BigDecimal((String)boxnumKeyMap.get(goodsMap.get(i)));
						boxnum = boxnum.add(new BigDecimal((String)boxnumMap.get(i)));
						boxnumKeyMap.put(goodsMap.get(i),boxnum.toString());
					}
				}else{
					String num = (String) numMap.get(i);
					if(StringUtils.isNotEmpty(num)){
						KeyMap.put(goodsMap.get(i),numMap.get(i));
					}
					if(priceMap.size() > 0 && "1".equals(pricetype)){
						String price = (String) priceMap.get(i);
						if(StringUtils.isNotEmpty(price)){
							priceKeyMap.put(goodsMap.get(i),priceMap.get(i));
						}
					}
					if(boxnumMap.size() > 0){
						String boxnum = (String) boxnumMap.get(i);
						if(StringUtils.isNotEmpty(boxnum)){
							priceKeyMap.put(goodsMap.get(i),boxnumMap.get(i));
						}
					}
				}
			}
			for (Object goodsMark : KeyMap.keySet()) {
				ModelOrder modelOrder = new ModelOrder();
				if("1".equals(gtype)){
					modelOrder.setBarcode((String) goodsMark);
				}else if("2".equals(gtype)){
					modelOrder.setShopid((String) goodsMark);
				}else if("3".equals(gtype)){
					modelOrder.setSpell((String) goodsMark);
				}else if("4".equals(gtype)){
					modelOrder.setGoodsid((String) goodsMark);
				}
				modelOrder.setUnitnum((String) KeyMap.get(goodsMark));
				modelOrder.setOrderId(orderid);
				modelOrder.setBusid(busid);
				if(priceKeyMap.size()>0){
					modelOrder.setTaxprice((String) priceKeyMap.get(goodsMark));
				}
				if(boxnumKeyMap.size()>0){
					modelOrder.setBoxnum((String) boxnumKeyMap.get(goodsMark));
				}
				wareList.add(modelOrder);
			}
		}
		return wareList;
	}

	/**
	 * 根据拆分列获取多个订单数据(带折扣，为客户导入八九千上万条数据使用过)
	 * @author lin_xx
	 * @date 2017/6/1
	 */
	public Map<String, LinkedList> getModelOrderMapByPram(int beginRow, int divideCol, int goodsCol, int numCol, int priceCol, int boxnumCol,
													String pricetype, String gtype, String ctype, int otherCol) throws Exception {

		Map divideColMap = new LinkedHashMap();
		Map divideMap = ExcelUtils.importExcelByGoodsGolumn(importFile, 0, beginRow, 0, divideCol);
		Iterator<Map.Entry<Integer, String>> it = divideMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, String> entry = it.next();
			String divide = entry.getValue();
			if ("null" != divide && StringUtils.isNotEmpty(divide)) {
				int modelColumeValue = entry.getKey();
				if (divideColMap.containsKey(divide)) {
					String value = divideColMap.get(divide) + "";
					divideColMap.put(divide, value + "," + modelColumeValue);
				} else {
					divideColMap.put(divide, modelColumeValue+ "");
				}
			}
		}
		Map goodsMap = new LinkedHashMap();
		Map numMap = new LinkedHashMap();
		Map priceMap = new LinkedHashMap();
		Map remarkMap = new LinkedHashMap();
		Map otherMap = new LinkedHashMap();
		//拆分单 独有的折扣金、客户信息单元格、客户单号
		Map amountMap = new LinkedHashMap();
		Map customernameMap = new LinkedHashMap();
		Map orderidMap = new LinkedHashMap();
		if (goodsCol > -1) {
			goodsMap = ExcelUtils.importExcelByGoodsGolumn(importFile, 0, beginRow, 0, goodsCol);
			//拆分单的客户名称 取模板里的C列
			customernameMap = ExcelUtils.importExcelByGoodsGolumn(importFile, 0, beginRow, 0, 2);
			//拆分单的客户单号 取模板里的B列
			orderidMap = ExcelUtils.importExcelByGoodsGolumn(importFile, 0, beginRow, 0, 1);
		}
		if (numCol > -1) {
			numMap = ExcelUtils.importExcelByColumn(importFile, 0, beginRow, 0, numCol);
			//拆分单 可能有折扣，获取数量列后面那列的折扣金额
			amountMap = ExcelUtils.importExcelByColumn(importFile, 0, beginRow, 0, numCol+1);
		}
		if (priceCol > -1) {
			priceMap = ExcelUtils.importExcelByColumn(importFile, 0, beginRow, 0, priceCol);
		}
		if (otherCol > -1) {
			otherMap = ExcelUtils.importExcelByColumn(importFile, 0, beginRow, 0, otherCol);
		}
		Map boxnumMap = new LinkedHashMap();
		if (boxnumCol > -1) {
			boxnumMap = ExcelUtils.importExcelByColumn(importFile, 0, beginRow, 0, boxnumCol);
		}
		Map<String, LinkedList> resultMap = new LinkedHashMap<String, LinkedList>();
		Iterator<Map.Entry<Integer, String>> is = divideColMap.entrySet().iterator();

		while (is.hasNext()) {
			Map.Entry<Integer, String> entry = is.next();
			String cols = entry.getValue();
			//保证导入商品顺序，使用linkedList
			LinkedList<ModelOrder> modelList = new LinkedList<ModelOrder>();
			String[] colums = cols.split(",");
			Map<String,BigDecimal> brandDiscountMap = new HashMap<String, BigDecimal>();//每张单据的折扣信息
			Map brandDiscountOrderidMap = new HashMap<String, BigDecimal>();//折扣所在列的模板单据号
			for (int j = 0; j < colums.length; j++) {
				int c = Integer.parseInt(colums[j]);
				String goodsmark = (String) goodsMap.get(c);
				ModelOrder modelOrder = new ModelOrder();
				modelOrder.setUnitnum((String) numMap.get(c));
				if ("1".equals(gtype)) {
					modelOrder.setBarcode(goodsmark);
				} else if ("2".equals(gtype)) {
					modelOrder.setShopid(goodsmark);
				} else if ("3".equals(gtype)) {
					modelOrder.setSpell(goodsmark);
				} else if ("4".equals(gtype)) {
					modelOrder.setGoodsid(goodsmark);
				}
				modelOrder.setOrderId((String) orderidMap.get(c));
				String cust = (String) customernameMap.get(c);
				Map map1 = new HashMap();
				if ("7".equals(ctype)) {//客户编码
					map1.put("id", cust);
					cust = returnCustomerID(map1);
				} else if("4".equals(ctype)){//客户名称
					map1.put("name", cust);
					cust = returnCustomerID(map1);
				}
				modelOrder.setBusid(cust);

				GoodsInfo goodsInfo = getGoodsInfoByID(goodsmark);
				Brand brand = getBaseGoodsService().getBrandInfo(goodsmark);
				if(null == goodsInfo && null != brand){
					if(brandDiscountMap.size() == 0){
						brandDiscountMap.put(goodsmark,new BigDecimal(amountMap.get(c).toString()));
						brandDiscountOrderidMap.put(goodsmark, orderidMap.get(c));
					}else if(brandDiscountMap.containsKey(goodsmark)){
						BigDecimal discountamount = new BigDecimal(amountMap.get(c).toString());
						BigDecimal value = discountamount.add(brandDiscountMap.get(goodsmark));
						brandDiscountMap.put(goodsmark,value);
						String remarkid = (String) brandDiscountOrderidMap.get(goodsmark);//模板单据号
						brandDiscountOrderidMap.put(goodsmark, orderidMap.get(c)+","+remarkid);
					}else{
						brandDiscountMap.put(goodsmark,new BigDecimal(amountMap.get(c).toString()));
						brandDiscountOrderidMap.put(goodsmark, orderidMap.get(c));
					}
				}else{
					modelOrder.setBusid(cust);
					if (priceMap.size() > 0 && "1".equals(pricetype)) {
						modelOrder.setTaxprice((String) priceMap.get(c));
					}
					if (boxnumMap.size() > 0) {
						modelOrder.setBoxnum((String) boxnumMap.get(c));
					}
					if(otherMap.size() > 0){
						modelOrder.setOtherMsg((String) otherMap.get(c));
					}
					if (modelList.size() == 0) {
						modelOrder.setRemark((String) remarkMap.get(c));
					}
					modelList.add(modelOrder);
				}
			}
			//折扣组装 goodsid 品牌部门 taxprice 折扣金额
			if(brandDiscountMap.size()>0){
				Iterator<Map.Entry<String,BigDecimal>> entries = brandDiscountMap.entrySet().iterator();
				while (entries.hasNext()) {
					Map.Entry etr = entries.next();
					String brandid = (String) etr.getKey();
					BigDecimal amount = (BigDecimal) etr.getValue();
					ModelOrder modelOrder = new ModelOrder();
					//获取折扣那一列的单据号
					modelOrder.setOrderId((String) brandDiscountOrderidMap.get(etr.getKey()));
					modelOrder.setGoodsid(brandid);
					modelOrder.setTaxprice(amount.toString());
					modelList.add(modelOrder);
				}
			}
			resultMap.put(cols, modelList);
		}
		return resultMap;
	}

	/**
	 * 根据拆分列获取多个订单数据
	 * @author lin_xx
	 * @date 2017/6/1
	 */
	public Map<String, LinkedList> getModelOrderMapByDivideCol(int beginRow, int divideCol, int goodsCol, int numCol, int priceCol, int boxnumCol,
						   int customerCol,String pricetype, String gtype, String ctype, int otherCol , String orderid ,String busid,String custRegular ) throws Exception {

		Map divideColMap = new LinkedHashMap();
		Map divideMap = ExcelUtils.importExcelByGoodsGolumn(importFile, 0, beginRow, 0, divideCol);
		Iterator<Map.Entry<Integer, String>> it = divideMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, String> entry = it.next();
			String divide = entry.getValue();
			if ("null" != divide && StringUtils.isNotEmpty(divide)) {
				int modelColumeValue = entry.getKey();
				if (divideColMap.containsKey(divide)) {
					String value = divideColMap.get(divide) + "";
					divideColMap.put(divide, value + "," + modelColumeValue);
				} else {
					divideColMap.put(divide, modelColumeValue+ "");
				}
			}
		}
		Map goodsMap = new LinkedHashMap();
		Map numMap = new LinkedHashMap();
		Map priceMap = new LinkedHashMap();
		Map remarkMap = new LinkedHashMap();
		Map otherMap = new LinkedHashMap();
		Map customerMap = new LinkedHashMap();
		if (goodsCol > -1) {
			goodsMap = ExcelUtils.importExcelByGoodsGolumn(importFile, 0, beginRow, 0, goodsCol);
		}
		if (numCol > -1) {
			numMap = ExcelUtils.importExcelByColumn(importFile, 0, beginRow, 0, numCol);
		}
		if (priceCol > -1) {
			priceMap = ExcelUtils.importExcelByColumn(importFile, 0, beginRow, 0, priceCol);
		}
		if (otherCol > -1) {
			otherMap = ExcelUtils.importExcelByColumn(importFile, 0, beginRow, 0, otherCol);
		}
		if(customerCol > -1){
			customerMap  = ExcelUtils.importExcelByColumn(importFile, 0, beginRow, 0, customerCol);
		}
		Map boxnumMap = new LinkedHashMap();
		if (boxnumCol > -1) {
			boxnumMap = ExcelUtils.importExcelByColumn(importFile, 0, beginRow, 0, boxnumCol);
		}
		Map<String, LinkedList> resultMap = new LinkedHashMap<String, LinkedList>();
		Iterator<Map.Entry<Integer, String>> is = divideColMap.entrySet().iterator();

		while (is.hasNext()) {
			Map.Entry<Integer, String> entry = is.next();
			String cols = entry.getValue();
			LinkedList<ModelOrder> modelList = new LinkedList<ModelOrder>();
			String[] colums = cols.split(",");
			for (int j = 0; j < colums.length; j++) {
				int c = Integer.parseInt(colums[j]);
				String goodsmark = (String) goodsMap.get(c);
				ModelOrder modelOrder = new ModelOrder();
				if(StringUtils.isNotEmpty(orderid)){
					modelOrder.setOrderId(orderid);
				}
				String custParam = (String) customerMap.get(c);
				//根据正则解析客户编号
				if(StringUtils.isNotEmpty(custRegular)){
					custParam = getCusidByCustRegular(custRegular,custParam);
				}
				String customerid = "";
				Map map1 = new HashMap();
				if(StringUtils.isNotEmpty(custParam) && "null" != custParam ){
					if("1".equals(ctype)){
						customerid = busid ;
					}else if ("2".equals(ctype)) {
						map1.put("pid", busid);//总店
						map1.put("shopno", custParam);//店号
						customerid = returnCustomerID(map1);
					} else if ("3".equals(ctype)) {
						map1.put("shortcode", custParam);
						customerid = returnCustomerID(map1);
					} else if ("4".equals(ctype)) {
						map1.put("name", custParam);
						customerid = returnCustomerID(map1);
					} else if ("5".equals(ctype)) {
						map1.put("shortname", custParam);
						customerid = returnCustomerID(map1);
					} else if ("6".equals(ctype)) {
						map1.put("address", custParam);
						customerid = returnCustomerID(map1);
					} else if ("7".equals(ctype)) {
						customerid = custParam;
					}
				}
				modelOrder.setBusid(customerid);
				//先去拆分列的值判断是否是客户编码，如果不是客户编码，则取参数：客户单元格里的值
//				if(StringUtils.isNotEmpty(customerid)){
//					Customer customer = getCustomerById(customerid);
//					if(null == customer){
//						modelOrder.setBusid(customerValue);
//					}else{
//						modelOrder.setBusid(customerid);
//					}
//				}else{
//					Customer customer = getCustomerById(custParam);
//					if(null == customer){
//						modelOrder.setBusid(customerValue);
//					}else{
//						modelOrder.setBusid(custParam);
//					}
//				}
				modelOrder.setUnitnum((String) numMap.get(c));
				if ("1".equals(gtype)) {
					modelOrder.setBarcode(goodsmark);
				} else if ("2".equals(gtype)) {
					modelOrder.setShopid(goodsmark);
				} else if ("3".equals(gtype)) {
					modelOrder.setSpell(goodsmark);
				} else if ("4".equals(gtype)) {
					modelOrder.setGoodsid(goodsmark);
				}

				if (priceMap.size() > 0 && "1".equals(pricetype)) {
					modelOrder.setTaxprice((String) priceMap.get(c));
				}
				if (boxnumMap.size() > 0) {
					modelOrder.setBoxnum((String) boxnumMap.get(c));
				}
				if(otherMap.size() > 0){
					modelOrder.setOtherMsg((String) otherMap.get(c));
				}
				if (modelList.size() == 0) {
					modelOrder.setRemark((String) remarkMap.get(c));
				}
				modelList.add(modelOrder);
			}
			resultMap.put(cols, modelList);
		}
		return resultMap;
	}

	/**
	 * 根据正则解析出客户编号
	 * @param custRegular
	 * @param content
	 * @return
	 */
	public String getCusidByCustRegular(String custRegular, String content){
		String cust = "";
		//读取客户信息
		Pattern pattern = Pattern.compile(custRegular);
		Matcher matcher = pattern.matcher(content);
		while(matcher.find()){
			cust += matcher.group(0);
		}
		return cust;
	}

	/**
	 * 导出导入失败的商品明细
	 * @author lin_xx
	 * @date 2017/6/26
	 */
	public String createErrorDetailFile(List<Map<String, Object>> errorList) throws  Exception{

		//模板文件路径
		String tempFilePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/importFalseDetail.xls");
		List<String> dataListCell = new ArrayList<String>();
		dataListCell.add("orderid");
		dataListCell.add("goodsid");
		dataListCell.add("unitnum");
		dataListCell.add("price");

		ExcelFileUtils handle = new ExcelFileUtils();
		handle.writeListData(tempFilePath, dataListCell, errorList, 0);

		String dateSubPath= CommonUtils.getYearMonthDayDirPath();
		String phyDirPath=CommonUtils.getUploadFilePhysicalDir("errorimportfile", dateSubPath);

		String fileName = CommonUtils.getDateTimeUUID();
		fileName= fileName+ ".xls";

		File errorFile = new File(phyDirPath, fileName);

		if(!errorFile.exists()){
			errorFile.createNewFile();
		}
		OutputStream os = new FileOutputStream(errorFile);
		//写到输出流并关闭资源
		handle.writeAndClose(tempFilePath, os);
		os.flush();
		os.close();
		handle.readClose(tempFilePath);

		String fullPath = "upload/errorimportfile/"+ dateSubPath + "/" + fileName;
		fullPath=CommonUtils.filterFilePathSaparator(fullPath);
		AttachFile attachFile = new AttachFile();
		attachFile.setExt(".xls");
		attachFile.setFilename(fileName);
		attachFile.setFullpath(fullPath);
		attachFile.setOldfilename(fileName);
		//将临时文件信息插入数据库
		attachFileService.addAttachFile(attachFile);
		String id = "";
		if(null!=attachFile){
			id=attachFile.getId();
		}
		return id;
	}

	/**
	 * 根据 customerCol，customerRow 两个参数 获取模板中制定单元格的值
	 * @author lin_xx
	 * @date 2017/5/15
	 */
	public String getCellValueByParam(File importFile,int customerCol,int customerRow,List<String> readerInformation) throws Exception {
		String cellValue = "";
		String customerParam = "";//读取到的客户参数
		//txt格式 获取单元格值
		if(customerCol != -1 && customerRow >= 1 && readerInformation.size() >= customerRow){
			String[] lineValue = readerInformation.get(customerRow-1).split("\\t");
			customerParam = lineValue[customerCol];
			if(StringUtils.isEmpty(customerParam)){
				lineValue = readerInformation.get(customerRow-1).split("\\t");
				cellValue = lineValue[customerCol];
			}else{
				cellValue = customerParam;
			}
		}
		//excel格式 获取单元格值
		if(StringUtils.isEmpty(customerParam) && customerRow > 0){
			List<String> customerRowInfo = ExcelUtils.importFirstRowByIndex(importFile,customerRow-1, 0);
			if(customerRowInfo.size() > 0){
				cellValue = (String) customerRowInfo.get(customerCol);
			}
		}
		return cellValue;

	}

	/**
	 *
	 * @param insertMap 数据导入返回的结果集
	 * @param numSheet excel工作表
	 * @param emptySheet 空工作表
	 * @param count 工作表的技术
	 * @param map 导入参数
	 * @param barcodeMap 导入不成功的结果集
	 */
	public List<Map> insertMapReturn(Map insertMap , int numSheet , String emptySheet ,int count , Map map , Map barcodeMap) {

		List<Map> list = new ArrayList<Map>();
		String flag = "";
		try{
			flag = (String) insertMap.get("flag");
		}catch (Exception e){
			boolean f = (Boolean) insertMap.get("flag");
			if(f){
				flag = "true";
			}else{
				flag = "false";
			}
		}
		if (insertMap.containsKey("disablemsg")) {
			map.put("disablemsg", insertMap.get("disablemsg"));
		}
		if ("true".equals(flag)) {
			if (insertMap.containsKey("barcode")) {
				String barcode = insertMap.get("barcode").toString();
				barcode = barcode.replace("[", "");
				barcode = barcode.replace("]", "");
				barcodeMap.put(Integer.valueOf(numSheet) + 1, barcode);
			} else if (insertMap.containsKey("shopid")) {
				String barcode = insertMap.get("shopid").toString();
				barcode = barcode.replace("[", "");
				barcode = barcode.replace("]", "");
				barcodeMap.put(Integer.valueOf(numSheet) + 1, barcode);
			} else if (insertMap.containsKey("spell")) {
				String barcode = insertMap.get("spell").toString();
				barcode = barcode.replace("[", "");
				barcode = barcode.replace("]", "");
				barcodeMap.put(Integer.valueOf(numSheet) + 1, barcode);
			} else if (insertMap.containsKey("goodsid")) {
				String barcode = insertMap.get("goodsid").toString();
				barcode = barcode.replace("[", "");
				barcode = barcode.replace("]", "");
				barcodeMap.put(Integer.valueOf(numSheet) + 1, barcode);
			} else {
				map.put("sheet", true);
			}
		}else if ("false".equals(flag)) {
			if(map.containsKey("emptysheet")){
				String value = (String) map.get("emptysheet");
				map.put("emptysheet", value+","+(numSheet + 1));
			}else{
				map.put("emptysheet",(numSheet + 1)+"");
			}
		}else{
			map.put("emptysheet",emptySheet+1);
		}
		if(insertMap.containsKey("errorList")){
			if(map.containsKey("errorList")){
				List errorList = (List) map.get("errorList");
				List failList = (List) insertMap.get("errorList");
				errorList.addAll(failList);
				map.put("errorList",errorList);
			}else{
				map.put("errorList",insertMap.get("errorList"));
			}
		}
		list.add(map);
		list.add(barcodeMap);
		return list;
	}

	/**
	 * 工作表导入提醒
	 *
	 * @return
	 * @throws Exception
	 * @author lin_xx
	 * @date 2015年10月10日
	 */
	public void sheetModelMsg(Map barcodeMap, Map map) throws Exception {
		String barcodeMSG = "";
		if (map.containsKey("info")) {
			request.setAttribute("msg", "客户编号不存在,请检查相关配置或联系管理员.");
		} else if (map.containsKey("num")) {
			request.setAttribute("msg", "模板中读取数量为空,请检查模板和方法参数是否匹配.");
		} else if (barcodeMap.size() > 0) {
			if(barcodeMap.containsKey("count")){
				barcodeMSG = barcodeMap.get("count")+"条数据导入成功;";
				barcodeMap.remove("count");
			}else{
				barcodeMSG = "数据导入成功;";
			}
			Iterator<Map.Entry> entries = barcodeMap.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry entry = entries.next();
				if ("1".equals(map.get("gtype"))) {
					barcodeMSG = barcodeMSG + "第" + entry.getKey() + "张订单没有对应条形码:" + entry.getValue() + ";";
				} else if ("2".equals(map.get("gtype"))) {
					barcodeMSG = barcodeMSG + "第" + entry.getKey() + "张订单没有对应店内码:" + entry.getValue() + ";";
				} else if ("3".equals(map.get("gtype"))) {
					barcodeMSG = barcodeMSG + "第" + entry.getKey() + "张订单没有对应助记符:" + entry.getValue() + ";";
				} else if(("0".equals(map.get("gtype")) || "4".equals(map.get("gtype"))) ){
					barcodeMSG = barcodeMSG + "第" + entry.getKey() + "张订单没有对应编码:" + entry.getValue() + ";";
				}
			}
			if (map.containsKey("errorsheet")) {
				barcodeMSG = barcodeMSG + "第" + map.get("errorsheet") + "张工作表客户编号不存在;";
			}
			if (map.containsKey("emptysheet")) {
				barcodeMSG = barcodeMSG + "第" + map.get("emptysheet") + "张工作表导入不成功;";
			}
			if (map.containsKey("disablemsg")) {
				barcodeMSG += map.get("disablemsg");
			}
			if (barcodeMSG == "") {
				barcodeMSG = "数据导入成功;";
			}else{
				List<Map<String, Object>> errorList = (List<Map<String, Object>>) map.get("errorList");
				if(null != errorList && errorList.size() > 0){
					String fileid = createErrorDetailFile(errorList);
					barcodeMSG += "&"+fileid ;
				}
			}
			request.setAttribute("msg", barcodeMSG);
		} else if (map.containsKey("info")) {
			request.setAttribute("msg", "没有符合的导入数据,请检查导入模板或数据是否有误");
		} else if (map.containsKey("emptysheet")) {
			//有工作表商品全部倒入成功时加入键sheet
			if (map.containsKey("sheet")) {
				barcodeMSG = barcodeMSG + "导入成功!";
			}
			barcodeMSG = barcodeMSG + "第" + map.get("emptysheet") + "张工作表导入不成功;";
			if (map.containsKey("errorsheet")) {
				barcodeMSG = barcodeMSG + "第" + map.get("errorsheet") + "张工作表客户编号不存在;";
			}
			if (map.containsKey("disablemsg")) {
				barcodeMSG += map.get("disablemsg");
			}
			request.setAttribute("msg", barcodeMSG);
		} else if (map.containsKey("disablemsg")) {
			request.setAttribute("msg", "数据导入成功" + map.get("disablemsg"));
		} else {
			request.setAttribute("msg", "数据导入成功");
		}
	}


}

