/**
 * @(#)PurchaseAction.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-6-13 zhanghonghui 创建版本
 */
package com.hd.agent.purchase.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.GoodsInfo_MteringUnitInfo;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.purchase.model.LimitPriceOrderDetail;
import com.hd.agent.purchase.service.ILimitPriceOrderService;

/**
 * 采购公共 Action
 * 
 * @author zhanghonghui
 */
public class PurchaseAction extends BaseFilesAction {
	private ILimitPriceOrderService limitPriceOrderService;

	public ILimitPriceOrderService getLimitPriceOrderService() {
		return limitPriceOrderService;
	}

	public void setLimitPriceOrderService(
			ILimitPriceOrderService limitPriceOrderService) {
		this.limitPriceOrderService = limitPriceOrderService;
	}
	/**
	 * 采购商品价格计算方式
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-13
	 */
	public String completeBuyDetailGoodsOrder() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String auxunitid = request.getParameter("auxunitid");
		String taxpriceStr = request.getParameter("taxprice");
		String unitnumStr = request.getParameter("unitnum");
		String taxtypeStr=request.getParameter("taxtype");
		String businessdate=request.getParameter("businessdate");
		
		if(null==auxunitid || "".equals(auxunitid.trim())){
			auxunitid="";
		}else{
			auxunitid=auxunitid.trim();
		}
		
		//获取金额 辅单位数量 辅单位数量描述
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("auxunitid", ""); //辅单位编号
		map.put("auxnum", ""); //辅单位数量
		map.put("auxnumdetail", ""); //辅单位数量描述
		map.put("auxunitname", ""); //辅单位名称
		map.put("taxprice", "");	//含税单价
		map.put("notaxprice", "");	//无税单价
		map.put("taxtypename", "");	//税种名称
		map.put("taxamount", "");	//含税金额
		map.put("notaxamount", "");	//无税金额
		map.put("tax", "");	//税额
		map.put("lpstartdate", "");
		map.put("lpenddate", "");
		map.put("limitprice", "0");
		map.put("auxremainder", "0");
		map.put("auxInteger","0");
		
		if(null==taxpriceStr || "".equals(taxpriceStr.trim())){
			taxpriceStr="0";
		}
		taxpriceStr=taxpriceStr.trim();
		if(null==unitnumStr || "".equals(unitnumStr.trim())){
			unitnumStr="0";
		}
		unitnumStr=unitnumStr.trim();
		
		
		BigDecimal taxprice= BigDecimal.ZERO;
		if(null==goodsid || "".equals(goodsid.trim())){
			taxprice=new BigDecimal(taxpriceStr);
		}else{
			LimitPriceOrderDetail limitPriceOrderDetail=limitPriceOrderService.getLimitPriceOrderDetailValid(goodsid,businessdate);
			if(null!=limitPriceOrderDetail){
				if(null !=limitPriceOrderDetail.getPriceasleft() && limitPriceOrderDetail.getPriceasleft().compareTo(BigDecimal.ZERO)>0){
					taxprice=limitPriceOrderDetail.getPriceasleft();
					map.put("limitprice", "1");
					map.put("lpstartdate", limitPriceOrderDetail.getEffectstartdate());
					map.put("lpenddate", limitPriceOrderDetail.getEffectenddate());
				}else{
					taxprice=new BigDecimal(taxpriceStr);
				}
			}else{
				taxprice=new BigDecimal(taxpriceStr);
			}
		}
		map.put("taxprice", taxprice);
		
		BigDecimal unitnum = new BigDecimal(unitnumStr);
		
		Map<String,Object> tmpMap=countGoodsInfoNumber(goodsid, auxunitid, unitnum);
		if(null!=tmpMap){
			map.putAll(tmpMap);			
		}
		tmpMap=getTaxInfosByTaxpriceAndTaxtype(taxprice,taxtypeStr,unitnum);
		if(null!=tmpMap){
			map.putAll(tmpMap);			
		}
		BigDecimal notaxprice = BigDecimal.ZERO;
		if(map.containsKey("notaxprice")){
			notaxprice = (BigDecimal) map.get("notaxprice");
		}
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		if(null!=goodsInfo){
			BigDecimal boxprice = goodsInfo.getBoxnum().multiply(taxprice).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);
			BigDecimal noboxprice = goodsInfo.getBoxnum().multiply(notaxprice).setScale(6,BigDecimal.ROUND_HALF_UP);
			map.put("boxprice", boxprice);
			map.put("noboxprice", noboxprice);
		}
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 采购商品价格计算方式,不通过根据限价单算
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-13
	 */
	public String completeBuyDetailGoodsNoLimit() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String auxunitid = request.getParameter("auxunitid");
		String taxpriceStr = request.getParameter("taxprice");
		String unitnumStr = request.getParameter("unitnum");
		String taxtypeStr=request.getParameter("taxtype");
		
		if(null==auxunitid || "".equals(auxunitid.trim())){
			auxunitid="";
		}else{
			auxunitid=auxunitid.trim();
		}
		
		//获取金额 辅单位数量 辅单位数量描述
		Map<String,Object> map =new HashMap<String, Object>();
		map.put("auxunitid", ""); //辅单位编号
		map.put("auxnum", ""); //辅单位数量
		map.put("auxnumdetail", ""); //辅单位数量描述
		map.put("auxunitname", ""); //辅单位名称
		map.put("notaxprice", "");	//无税单价
		map.put("taxtypename", "");	//税种名称
		map.put("taxamount", "");	//含税金额
		map.put("notaxamount", "");	//无税金额
		map.put("tax", "");	//税额
		
		if(null==taxpriceStr || "".equals(taxpriceStr.trim())){
			taxpriceStr="0";
		}
		taxpriceStr=taxpriceStr.trim();
		if(null==unitnumStr || "".equals(unitnumStr.trim())){
			unitnumStr="0";
		}
		unitnumStr=unitnumStr.trim();
		
		
		BigDecimal taxprice = new BigDecimal(taxpriceStr);
		
		BigDecimal unitnum = new BigDecimal(unitnumStr);
		
		Map<String,Object> tmpMap=countGoodsInfoNumber(goodsid, auxunitid, unitnum);
		if(null!=tmpMap){
			map.putAll(tmpMap);			
		}
		tmpMap=getTaxInfosByTaxpriceAndTaxtype(taxprice,taxtypeStr,unitnum);
		if(null!=tmpMap){
			map.putAll(tmpMap);			
		}
		addJSONObject(map);
		
		return SUCCESS;
	}
	
	/**
	 * 显示辅计量名称信息
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-6-13
	 */
	public String showAuxAndUnitNameInfo() throws Exception{
		String goodsid=request.getParameter("goodsid");
		String auxid=request.getParameter("auxunitid");
		String unitid=request.getParameter("unitid");
		GoodsInfo_MteringUnitInfo mteringUnitInfo= null;
		Map map=new HashMap();
		map.put("auxunitid", "");
		map.put("auxunitname", "");
		if(null==auxid || "".equals(auxid.trim())){
			mteringUnitInfo=getDefaultGoodsAuxMeterUnitInfo(goodsid);
 		}else{
			mteringUnitInfo=getGoodsAuxMeterUnitInfo(goodsid,auxid);
		}
		if(null !=mteringUnitInfo){
			map.put("auxunitid", mteringUnitInfo.getMeteringunitid());
			map.put("auxunitname", mteringUnitInfo.getMeteringunitName());
			
		}
		if(null!=unitid && !"".equals(unitid.trim())){
			MeteringUnit meteringUnit=getBaseGoodsService().showMeteringUnitInfo(unitid);
			if(null!=meteringUnit){
				map.put("unitname", meteringUnit.getName());
			}
		}
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
				bTax = bTax1.multiply(bNum);
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
		map.put("taxAmount", bTaxAmount);
		map.put("noTaxPrice", bNoTaxPrice);
		map.put("noTaxAmount", bNoTaxAmount);
		map.put("tax", bTax);
		if(null!=goodsInfo){
			BigDecimal boxprice = goodsInfo.getBoxnum().multiply(bTaxPrice).setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
			BigDecimal noboxprice = goodsInfo.getBoxnum().multiply(bNoTaxPrice).setScale(6, BigDecimal.ROUND_HALF_UP);
			map.put("boxprice", boxprice);
			map.put("noboxprice", noboxprice);
		}
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 箱价修改后，调整金额 与含税单价 未税单价
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年12月3日
	 */
	public String getAmountChangerByBoxprice() throws Exception{
		String goodsId = request.getParameter("id"); //商品编码
		String type = request.getParameter("type"); //type:1含税箱价 2未税箱价
		String price = StringUtils.isEmpty(request.getParameter("price")) ? "0" : request.getParameter("price"); //改变后的箱价

		String tax = request.getParameter("taxtype"); //税种
		String unitnum = StringUtils.isEmpty(request.getParameter("unitnum")) ? "0" : request.getParameter("unitnum"); //主计量单位数量;
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsId);
		//税种为空时，默认为商品档案默认税种
		if((null==tax || "".equals(tax.trim())) && null!=goodsInfo){
			tax=goodsInfo.getDefaulttaxtype();
		}
		TaxType taxType = getTaxType(tax);
		BigDecimal bTaxPrice = new BigDecimal(0); //含税单价
		BigDecimal bTax = new BigDecimal(0); //税额
		BigDecimal bNum = new BigDecimal(unitnum);
		BigDecimal boxprice = new BigDecimal(0);
		BigDecimal noboxprice = new BigDecimal(0);
		if(null != taxType){
			Map map = new HashMap();
			//根据箱价 获取价格信息
			if("1".equals(type)){
				boxprice = new BigDecimal(price);
				bTaxPrice = boxprice.divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
				map = getTaxInfosByTaxpriceAndTaxtype(bTaxPrice,tax,bNum);
				BigDecimal notaxprice = (BigDecimal) map.get("notaxprice");
				map.put("noboxprice", notaxprice.multiply(goodsInfo.getBoxnum()));
				map.put("taxprice", bTaxPrice);
				map.put("boxprice", boxprice);
			}else if("2".equals(type)){
				noboxprice = new BigDecimal(price);
				BigDecimal bNoTaxPrice = noboxprice.divide(goodsInfo.getBoxnum(),6,BigDecimal.ROUND_HALF_UP);
				if(taxType != null){
					bTax = computeTax(2, bNoTaxPrice, taxType.getRate().divide(new BigDecimal(100)));
				}
				bTaxPrice = bNoTaxPrice.add(bTax);
				map = getTaxInfosByTaxpriceAndTaxtype(bTaxPrice,tax,bNum);
				map.put("taxprice", bTaxPrice);
				map.put("noboxprice", noboxprice);
				map.put("boxprice", bTaxPrice.multiply(goodsInfo.getBoxnum()));
			}
			addJSONObject(map);
		}
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
	 * 修改含税金额时，获取对应含税单价，税单价和税额改变的结果
	 * @return 
	 * @throws Exception
	 * @author zhengziyong 
	 * @date May 14, 2013
	 */
	public String getPriceChanger() throws Exception{
		String type = request.getParameter("type"); //1含税金额2无税金额
		String goodsId = request.getParameter("id"); //商品编码
		String amount = StringUtils.isEmpty(request.getParameter("amount")) ? "0" : request.getParameter("amount"); //改变后的无税或含税单价
		String tax = request.getParameter("taxtype"); //税种
		String unitnum = StringUtils.isEmpty(request.getParameter("unitnum")) ? "0" : request.getParameter("unitnum"); //主计量单位数量;
		TaxType taxType = getTaxType(tax);
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
		}
		else if("2".equals(type)){
			bNoTaxAmount = bAmount;
			bNoTaxPrice = bNoTaxAmount.divide(bNum, 6, BigDecimal.ROUND_HALF_UP);
			
			bTaxAmount = bNoTaxAmount.multiply(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1)));
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
			map.put("boxprice", boxprice);
			map.put("noboxprice", noboxprice);
		}
		addJSONObject(map);
		return SUCCESS;
	}
}

