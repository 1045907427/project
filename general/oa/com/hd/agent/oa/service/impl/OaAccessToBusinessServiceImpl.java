/**
 * @(#)OAAccessToBusinessServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年10月22日 chenwei 创建版本
 */
package com.hd.agent.oa.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.service.ICustomerPushBanlanceService;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.journalsheet.model.CustomerCostPayable;
import com.hd.agent.journalsheet.model.MatcostsInput;
import com.hd.agent.journalsheet.service.IJournalSheetService;
import com.hd.agent.oa.model.OaAccess;
import com.hd.agent.oa.model.OaAccessBrandDiscount;
import com.hd.agent.oa.model.OaAccessGoodsPrice;
import com.hd.agent.oa.service.IOaAccessToBusinessService;
import com.hd.agent.report.service.ISalesReportService;
import com.hd.agent.sales.model.Offprice;
import com.hd.agent.sales.model.OffpriceDetail;
import com.hd.agent.sales.service.IOffpriceService;

/**
 * 
 * OA费用通路单接口实现类
 * @author chenwei
 */
public class OaAccessToBusinessServiceImpl extends BaseFilesServiceImpl implements
		IOaAccessToBusinessService {
	/**
	 * 客户应收款冲差单接口
	 */
	private ICustomerPushBanlanceService customerPushBanlanceService;
	/**
	 * 销售特价接口
	 */
	private IOffpriceService offpriceService;
	/**
	 * 资金相关接口
	 */
	private IJournalSheetService journalSheetService;
	/**
	 * 销售报表接口
	 */
	private ISalesReportService salesReportService;
	
	public ICustomerPushBanlanceService getCustomerPushBanlanceService() {
		return customerPushBanlanceService;
	}

	public void setCustomerPushBanlanceService(
			ICustomerPushBanlanceService customerPushBanlanceService) {
		this.customerPushBanlanceService = customerPushBanlanceService;
	}

	public IOffpriceService getOffpriceService() {
		return offpriceService;
	}

	public void setOffpriceService(IOffpriceService offpriceService) {
		this.offpriceService = offpriceService;
	}

	public IJournalSheetService getJournalSheetService() {
		return journalSheetService;
	}

	public void setJournalSheetService(IJournalSheetService journalSheetService) {
		this.journalSheetService = journalSheetService;
	}
	
	public ISalesReportService getSalesReportService() {
		return salesReportService;
	}

	public void setSalesReportService(ISalesReportService salesReportService) {
		this.salesReportService = salesReportService;
	}

	@Override
	public Map addBusinessBillByOaAccess(OaAccess oaAccess,
			List<OaAccessBrandDiscount> brandDiscountList) throws Exception {
		boolean flag = false;
		String msg = "";
		String ids = "";
		//判断通路单 不是降价特价 其他情况通路单接口一致
		//支付方式为折扣
		if(null!=oaAccess && "1".equals(oaAccess.getPaytype())){
			//通路单只有工厂投入金额时
			if(null!=oaAccess.getFactoryamount() && oaAccess.getFactoryamount().compareTo(BigDecimal.ZERO)==1
					&&(oaAccess.getCompdiscount()==null || oaAccess.getCompdiscount().compareTo(BigDecimal.ZERO)==0)
					&&(oaAccess.getPayamount()==null || oaAccess.getPayamount().compareTo(BigDecimal.ZERO)==0)){
				//只有工厂投入金额时
				Map returnMap = new HashMap();
				returnMap.put("flag", false);
				returnMap.put("msg", "只有工厂投入金额，不需要生成冲差单");
				returnMap.put("ids", "");
				return returnMap;
			}else{
				Customer customer = getCustomerByID(oaAccess.getCustomerid());
				//客户为门店时
				if("1".equals(customer.getIslast())){
					Map returnMap = addBusinessBillByCustomer(oaAccess, brandDiscountList);
					return returnMap;
				}else if("0".equals(customer.getIslast())){
					//客户为总店时  支付方式为折让时  需要按规则把电脑折让金额平摊到各门店去
					Map returnMap = addBusinessBillByPCustomer(oaAccess, brandDiscountList);
					return returnMap;
				}
			}
		}else{
			msg = "通路单不存在";
		}
		//客户应收款冲差暂不自动生成
		flag = true;
		Map returnMap = new HashMap();
		returnMap.put("flag", flag);
		returnMap.put("msg", msg);
		returnMap.put("ids", ids);
		return returnMap;
	}

	@Override
	public Map addOffPriceByOaAccess(OaAccess oaAccess, List<OaAccessGoodsPrice> goodsPricelist) throws Exception {
		boolean flag = false;
		String msg = "";
		String ids = "";

		Offprice off = offpriceService.selectOffPriceByOaid(oaAccess.getOaid());
		if(off != null) {
			return new HashMap();
		}

		//判断通路单是否降价特价
		if(null!=oaAccess &&null!=goodsPricelist && "2".equals(oaAccess.getPricetype())){
			Offprice offprice = new Offprice();
			Customer customer = getCustomerByID(oaAccess.getCustomerid());
			if(null!=customer){
				//客户类型 单一客户为1 总店客户6
				//判断客户是否为总店 1是门店 0是总店
				if("1".equals(customer.getIslast())){
					offprice.setCustomertype("1");
				}else{
					offprice.setCustomertype("6");
				}
				offprice.setCustomerid(oaAccess.getCustomerid());
				offprice.setBusinessdate(CommonUtils.getTodayDataStr());
				offprice.setBegindate(oaAccess.getCombegindate());
				offprice.setEnddate(oaAccess.getComenddate());
				SysUser applyUser = getSysUserById(oaAccess.getAdduserid());
				if(null!=applyUser){
					offprice.setApplyuserid(applyUser.getPersonnelid());
					offprice.setApplydeptid(applyUser.getDepartmentid());
				}
				offprice.setRemark("通过通路单生成,OA号:"+oaAccess.getOaid());
				offprice.setOaid(oaAccess.getOaid());
				List<OffpriceDetail> detailList = new ArrayList<OffpriceDetail>();
				for(OaAccessGoodsPrice oaAccessGoodsPrice : goodsPricelist){
					OffpriceDetail offpriceDetail = new OffpriceDetail();
					if(StringUtils.isNotEmpty(oaAccessGoodsPrice.getGoodsid())){
						offpriceDetail.setGoodsid(oaAccessGoodsPrice.getGoodsid());
						offpriceDetail.setOldprice(oaAccessGoodsPrice.getOldprice());
						offpriceDetail.setOffprice(oaAccessGoodsPrice.getNewprice());
						offpriceDetail.setLownum(BigDecimal.ZERO);
						offpriceDetail.setUpnum(new BigDecimal(9999));
						offpriceDetail.setRemark(oaAccessGoodsPrice.getRemark());
						detailList.add(offpriceDetail);
					}
				}
				String billid = offpriceService.addAndAuditOffpriceToOa(offprice, detailList);
				if(StringUtils.isNotEmpty(billid)){
					ids = billid;
					flag = true;
				}else{
					msg = "生成特价调整单失败";
				}
			}else{
				msg = "客户不存在！";
			}
		}else{
			msg = "通路单不是降价特价！";
		}
		Map returnMap = new HashMap();
		returnMap.put("flag", flag);
		returnMap.put("msg", msg);
		returnMap.put("ids", ids);
		return returnMap;
	}
	/**
	 * 当通路单只有工厂投入金额时，生成相关单据
	 * @param oaAccess
	 * @param brandDiscountList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月22日
	 */
	public Map addBusinessBillByOnlyFactoryAmount(OaAccess oaAccess,
			List<OaAccessBrandDiscount> brandDiscountList) throws Exception{

        if(oaAccess.getFactoryamount() == null || BigDecimal.ZERO.compareTo(oaAccess.getFactoryamount()) == 0) {

            return new HashMap();
        }

		List<MatcostsInput> list = journalSheetService.selectMatcostsInputByOaid(oaAccess.getOaid());
		if(list.size() % 2 == 1) {
			return new HashMap();
		}

		SysUser sysUser = getSysUser();
		boolean flag = false;
		String msg = "";
		String ids = "";
		//新增代垫数据
		MatcostsInput matcostsInput = new MatcostsInput();
		matcostsInput.setSupplierid(oaAccess.getSupplierid());
		//供应商
		BuySupplier buySupplier = getSupplierInfoById(oaAccess.getSupplierid());
		if(null!=buySupplier){
			matcostsInput.setSupplierdeptid(buySupplier.getBuydeptid());
		}
		matcostsInput.setCustomerid(oaAccess.getCustomerid());
		matcostsInput.setBilltype("1");
		matcostsInput.setOaid(oaAccess.getOaid());
		matcostsInput.setBusinessdate(CommonUtils.getTodayDataStr());
		//补差特价 费用类别
		if("1".equals(oaAccess.getPricetype())){
			matcostsInput.setSubjectid("");
		}else{
			matcostsInput.setSubjectid(oaAccess.getExpensesort());
		}
		//工厂投入金额
		matcostsInput.setFactoryamount(oaAccess.getFactoryamount());
		//电脑折让金额
		matcostsInput.setHtcompdiscount(BigDecimal.ZERO);
		//支付金额
		matcostsInput.setHtpayamount(BigDecimal.ZERO);
		matcostsInput.setAdduserid(sysUser.getUserid());
		matcostsInput.setAddusername(sysUser.getName());
        // 3239 瑞家&通用版：通路单生成代垫时，数据来源不对
        matcostsInput.setSourcefrome("2");
        // 3243 瑞家：等张红辉把代垫录入增加费用金额后，通路单生成代垫时要把费用金额带过去
        matcostsInput.setExpense(oaAccess.getTotalamount());
		// 需求 78 费用管理==》代垫录入修改
		matcostsInput.setPaydate(oaAccess.getPaydate());
		matcostsInput.setTakebackdate(oaAccess.getReimbursedate());
        // 5160 通用版：OA生成代垫的时候经办人取当前工作处理人，相关OA：通路单、品牌费用申请单（支付）、客户费用申请单（账扣）
        matcostsInput.setTransactorid(sysUser.getPersonnelid());
		boolean addMflag = journalSheetService.addMatcostsInput(matcostsInput, false);
		if(addMflag){
			flag = true;
			if(StringUtils.isEmpty(ids)){
				ids += matcostsInput.getId();
			}else{
				ids += ","+ matcostsInput.getId();
			}
		}else{
			flag = false;
			throw new Exception("生成代垫失败！");
		}
		
		Map returnMap = new HashMap();
		returnMap.put("flag", flag);
		returnMap.put("msg", msg);
		returnMap.put("ids", ids);
		return returnMap;
	}
	
	/**
	 * 门店客户 -- 生成相关单据
	 * @param oaAccess
	 * @param brandDiscountList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月22日
	 */
	public Map addBusinessBillByCustomer(OaAccess oaAccess,
			List<OaAccessBrandDiscount> brandDiscountList) throws Exception{
		SysUser sysUser = getSysUser();
		boolean flag = false;
		String msg = "";
		String ids = "";
		//工厂投入总额
		BigDecimal factoryamount = BigDecimal.ZERO;
		BigDecimal payamount = BigDecimal.ZERO;
		if(null!=oaAccess.getPayamount()){
			payamount = oaAccess.getPayamount();
		}
		BigDecimal compdiscountAmount = BigDecimal.ZERO; 
		if(null!=oaAccess.getCompdiscount()){
			compdiscountAmount = oaAccess.getCompdiscount();
		}
		//已分配品牌工厂投入金额
		BigDecimal totaluseBrandFAmount = BigDecimal.ZERO;
		//已分配电脑折让金额
		BigDecimal totaluseBrandCompAmount = BigDecimal.ZERO;
		//已分配支付金额
		BigDecimal totaluseBrandPayAmount = BigDecimal.ZERO;
		if(null!=oaAccess.getFactoryamount()){
			factoryamount = oaAccess.getFactoryamount();
		}
		//品牌合计总金额
		BigDecimal totalamount = BigDecimal.ZERO;
		for(OaAccessBrandDiscount accessBrandDiscount : brandDiscountList){
			totalamount = totalamount.add(accessBrandDiscount.getAmount()==null?BigDecimal.ZERO:accessBrandDiscount.getAmount());
		}
		if(totalamount.compareTo(compdiscountAmount)==0 || totalamount.compareTo(payamount)==0){
			int i = 0;
			for(OaAccessBrandDiscount accessBrandDiscount : brandDiscountList){
				i++;
				//品牌电脑折让金额
				BigDecimal brandCompAmount = BigDecimal.ZERO;
				//品牌支付金额
				BigDecimal brandPayAmount = BigDecimal.ZERO;
				//支付方式为1折扣时 生成冲差单和代垫电脑折让
				//支付方式为2支票时 生成代垫支付金额
				if("1".equals(oaAccess.getPaytype())){
					//品牌支付金额  = （品牌折让金额/品牌折让总金额）* 支付总金额
					brandPayAmount = accessBrandDiscount.getAmount().divide(totalamount,2,BigDecimal.ROUND_HALF_UP).multiply(payamount).setScale(2,BigDecimal.ROUND_HALF_UP);
					//最后一个品牌电脑折让金额= 电脑折让总额- 已分配电脑折让金额
					if(i==(brandDiscountList.size())){
						brandPayAmount = payamount.subtract(totaluseBrandPayAmount);
					}
					//合计品牌支付金额
					totaluseBrandPayAmount = totaluseBrandPayAmount.add(brandPayAmount);
					
					//品牌电脑折让金额
					brandCompAmount = accessBrandDiscount.getAmount();
					CustomerPushBalance customerPushBalance = new CustomerPushBalance();
					customerPushBalance.setCustomerid(oaAccess.getCustomerid());
					customerPushBalance.setPushtype("1");
					customerPushBalance.setBrandid(accessBrandDiscount.getBrandid());
					//生成的客户应收款冲差单应该为负的
					customerPushBalance.setAmount(brandCompAmount.negate());
					customerPushBalance.setRemark(oaAccess.getOaid());
					//补差特价对应的费用类别
					if("1".equals(oaAccess.getPricetype())){
//						customerPushBalance.setSubject(oaAccess.getPricetype());
					}else{
						customerPushBalance.setSubject(oaAccess.getExpensesort());
					}
					customerPushBalance.setBusinessdate(CommonUtils.getTodayDataStr());
					
					//冲差类型为正常冲差
					customerPushBalance.setIsinvoice("0");
					customerPushBalance.setOaid(oaAccess.getOaid());
					customerPushBalance.setSendamount(customerPushBalance.getAmount());
					//生成冲差单
					String cpid = customerPushBanlanceService.addAndAuditCustomerPushBanlanceByOA(customerPushBalance);
					if(StringUtils.isNotEmpty(cpid)){
						if(StringUtils.isEmpty(ids)){
							ids += cpid;
						}else{
							ids += ","+ cpid;
						}
					}else{
						flag = false;
						throw new Exception("生成客户应收款冲差单失败！");
					}
				}else if("2".equals(oaAccess.getPaytype())){
					brandPayAmount = accessBrandDiscount.getAmount();
					
					//支付方式为支票时，且电脑折让金额大于0 按比例对电脑折让金额进行分派
					//品牌工厂投入金额  = （品牌折让金额/品牌折让总金额）* 电脑折让总金额
					brandCompAmount = accessBrandDiscount.getAmount().divide(totalamount,2,BigDecimal.ROUND_HALF_UP).multiply(compdiscountAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
					//最后一个品牌电脑折让金额= 电脑折让总额- 已分配电脑折让金额
					if(i==(brandDiscountList.size())){
						brandCompAmount = compdiscountAmount.subtract(totaluseBrandCompAmount);
					}
					//合计品牌工厂投入金额
					totaluseBrandCompAmount = totaluseBrandCompAmount.add(brandCompAmount);
					
					if(brandCompAmount.compareTo(BigDecimal.ZERO)==1){
						CustomerPushBalance customerPushBalance = new CustomerPushBalance();
						customerPushBalance.setCustomerid(oaAccess.getCustomerid());
						customerPushBalance.setPushtype("1");
						customerPushBalance.setBrandid(accessBrandDiscount.getBrandid());
						//生成的客户应收款冲差单应该为负的
						customerPushBalance.setAmount(brandCompAmount.negate());
						customerPushBalance.setRemark(oaAccess.getOaid());
						//补差特价对应的费用类别
						if("1".equals(oaAccess.getPricetype())){
//							customerPushBalance.setSubject(oaAccess.getPricetype());
						}else{
							customerPushBalance.setSubject(oaAccess.getExpensesort());
						}
						customerPushBalance.setBusinessdate(CommonUtils.getTodayDataStr());
						//冲差类型为正常冲差
						customerPushBalance.setIsinvoice("0");
						customerPushBalance.setOaid(oaAccess.getOaid());
                        customerPushBalance.setSendamount(customerPushBalance.getAmount());
						//生成冲差单
						String cpid = customerPushBanlanceService.addAndAuditCustomerPushBanlanceByOA(customerPushBalance);
						if(StringUtils.isNotEmpty(cpid)){
							if(StringUtils.isEmpty(ids)){
								ids += cpid;
							}else{
								ids += ","+ cpid;
							}
						}else{
							flag = false;
							throw new Exception("生成客户应收款冲差单失败！");
						}
					}
				}
			}
		}else{
			msg = "分品牌金额与通路单金额不一致";
		}
		Map returnMap = new HashMap();
		returnMap.put("flag", flag);
		returnMap.put("msg", msg);
		returnMap.put("ids", ids);
		return returnMap;
	}
	/**
	 * 总店客户 -- 生成相关单据
	 * @param oaAccess
	 * @param brandDiscountList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月22日
	 */
	public Map addBusinessBillByPCustomer(OaAccess oaAccess,
			List<OaAccessBrandDiscount> brandDiscountList) throws Exception{
		SysUser sysUser = getSysUser();
		boolean flag = false;
		String msg = "";
		String ids = "";
		String begindate = null;
		String enddate = null;
		//取通路单确认日期  通路单确认日期不存在时 取上个月的日期
		if(null!=oaAccess.getConbegindate() && null!=oaAccess.getConenddate()){
			begindate = oaAccess.getConbegindate();
			enddate = oaAccess.getConenddate();
		}else{
			begindate = CommonUtils.getPreMonthFirstDay();
			enddate = CommonUtils.getPreMonthLastDay();
		}
		List<Map> salesAmountList = salesReportService.getSalesReportListByPcustomer(oaAccess.getCustomerid(), begindate, enddate);
		//当该日期没有销售金额时，取全部销售金额
		if(null==salesAmountList || salesAmountList.size()==0){
			salesAmountList = salesReportService.getSalesReportListByPcustomer(oaAccess.getCustomerid(), null, null);
		}
		if(null!=salesAmountList && salesAmountList.size()>0){
			//品牌折让总金额
			BigDecimal totalamount = BigDecimal.ZERO;
			for(OaAccessBrandDiscount accessBrandDiscount : brandDiscountList){
				totalamount = totalamount.add(accessBrandDiscount.getAmount()==null?BigDecimal.ZERO:accessBrandDiscount.getAmount());
			}
			//获取该总店客户的销售总额
			BigDecimal totalSalesAmount = BigDecimal.ZERO;
			//已分配工厂金额
			BigDecimal totalUseFactoryAmount = BigDecimal.ZERO;
			//已分配支付金额
			BigDecimal totalUsePayAmount = BigDecimal.ZERO;
			//已分配电脑折让金额
			BigDecimal totalUseCompdiscountAmount = BigDecimal.ZERO;
			//各品牌折让已分配金额
			Map totalUseBrandDidscountMap = new HashMap();
			//获取各门店销售总额
			for(Map map : salesAmountList){
				BigDecimal salesamount = BigDecimal.ZERO;
				if(map.containsKey("salesamount")){
					salesamount = (BigDecimal) map.get("salesamount");
				}
				if(salesamount.compareTo(BigDecimal.ZERO)==1){
					totalSalesAmount = totalSalesAmount.add(salesamount);
				}
			}
			int i = 0;
			//按比例平摊到各门店中去
			for(Map map : salesAmountList){
				i++;
				BigDecimal salesamount = BigDecimal.ZERO;
				if(map.containsKey("salesamount")){
					salesamount = (BigDecimal) map.get("salesamount");
				}
				//销售金额大于0的 平摊
				if(salesamount.compareTo(BigDecimal.ZERO)==1){
					String customerid = (String) map.get("customerid");
					//门店客户工厂投入金额
					BigDecimal customerFactoryAmount = BigDecimal.ZERO;
					//门店客户已分配工厂投入金额
					BigDecimal totaluseBrandFAmount = BigDecimal.ZERO;
					//门店客户支付金额
					BigDecimal customerPayAmount = BigDecimal.ZERO;
					//门店客户已分配支付金额
					BigDecimal customerUsePayAmount = BigDecimal.ZERO;
					//门店客户电脑折让金额
					BigDecimal customerCompdiscountAmount = BigDecimal.ZERO;
					//门店客户已分配电脑折让金额
					BigDecimal customerUseCompdiscountAmount = BigDecimal.ZERO;
					if(null!=oaAccess.getFactoryamount() && oaAccess.getFactoryamount().compareTo(BigDecimal.ZERO)==1){
						//门店工厂投入金额  = （门店销售额/各门店销售总额）*工厂投入总额
						customerFactoryAmount = salesamount.divide(totalSalesAmount,2,BigDecimal.ROUND_HALF_UP).multiply(oaAccess.getFactoryamount()).setScale(2,BigDecimal.ROUND_HALF_UP);
						//最后一个门店工厂投入金额= 工厂投入总额- 已分配工厂投入金额
						if(i==(salesAmountList.size())){
							customerFactoryAmount = oaAccess.getFactoryamount().subtract(totalUseFactoryAmount);
						}
						//合计已分配工厂投入金额
						totalUseFactoryAmount = totalUseFactoryAmount.add(customerFactoryAmount);
					}
					if(null!=oaAccess.getCompdiscount() && oaAccess.getCompdiscount().compareTo(BigDecimal.ZERO)==1){
						//门店电脑折让金额  = （门店销售额/各门店销售总额）*电脑折让总额
						customerCompdiscountAmount = salesamount.divide(totalSalesAmount,2,BigDecimal.ROUND_HALF_UP).multiply(oaAccess.getCompdiscount()).setScale(2,BigDecimal.ROUND_HALF_UP);
						//最后一个门店电脑折让金额= 电脑折让总额- 已分配电脑折让金额
						if(i==(salesAmountList.size())){
							customerCompdiscountAmount = oaAccess.getCompdiscount().subtract(totalUseCompdiscountAmount);
						}
						//合计已分配电脑折让金额
						totalUseCompdiscountAmount = totalUseCompdiscountAmount.add(customerCompdiscountAmount);
					}
					if(null!=oaAccess.getPayamount() && oaAccess.getPayamount().compareTo(BigDecimal.ZERO)==1){
						//门店支付金额  = （门店销售额/各门店销售总额）*支付总金额
						customerPayAmount = salesamount.divide(totalSalesAmount,2,BigDecimal.ROUND_HALF_UP).multiply(oaAccess.getPayamount()).setScale(2,BigDecimal.ROUND_HALF_UP);
						//最后一个门店支付金额= 支付总额- 已分配支付金额
						if(i==(salesAmountList.size())){
							customerPayAmount = oaAccess.getPayamount().subtract(totalUsePayAmount);
						}
						//合计已分配支付金额
						totalUsePayAmount = totalUsePayAmount.add(customerPayAmount);
					}
					int j = 0;
					//根据品牌折让明细 分配到各门店中去
					for(OaAccessBrandDiscount accessBrandDiscount : brandDiscountList){
						j++;
						//品牌电脑折让金额
						BigDecimal brandCompAmount = BigDecimal.ZERO;
						//品牌支付金额
						BigDecimal brandPayAmount = BigDecimal.ZERO;
						
						//按比例品牌折让金额平摊到各门店中去
						BigDecimal brandDiscountAmount = BigDecimal.ZERO;
						brandDiscountAmount = salesamount.divide(totalSalesAmount,2,BigDecimal.ROUND_HALF_UP).multiply(accessBrandDiscount.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP);
						if(i==(salesAmountList.size())){
							BigDecimal useBrandDiscountAmount = BigDecimal.ZERO;
							if(totalUseBrandDidscountMap.containsKey(accessBrandDiscount.getBrandid())){
								useBrandDiscountAmount = (BigDecimal) totalUseBrandDidscountMap.get(accessBrandDiscount.getBrandid());
							}
							brandDiscountAmount = accessBrandDiscount.getAmount().subtract(useBrandDiscountAmount);
						}
						if(totalUseBrandDidscountMap.containsKey(accessBrandDiscount.getBrandid())){
							BigDecimal amount = (BigDecimal) totalUseBrandDidscountMap.get(accessBrandDiscount.getBrandid());
							amount = amount.add(brandDiscountAmount);
							totalUseBrandDidscountMap.put(accessBrandDiscount.getBrandid(), amount);
						}else{
							BigDecimal amount = brandDiscountAmount;
							totalUseBrandDidscountMap.put(accessBrandDiscount.getBrandid(), amount);
						}
						//支付方式为1折扣时 生成冲差单和代垫电脑折让
						//支付方式为2支票时 生成代垫支付金额
						if("1".equals(oaAccess.getPaytype())){
							//品牌支付金额  = （品牌折让金额/品牌折让总金额）* 门店客户支付金额
							brandPayAmount = accessBrandDiscount.getAmount().divide(totalamount,2,BigDecimal.ROUND_HALF_UP).multiply(customerPayAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
							//最后一个品牌电脑折让金额= 电脑折让总额- 已分配电脑折让金额
							if(j==(brandDiscountList.size())){
								brandPayAmount = customerPayAmount.subtract(customerUsePayAmount);
							}
							//合计门店品牌支付金额
							customerUsePayAmount = customerUsePayAmount.add(brandPayAmount);
							
							brandCompAmount = brandDiscountAmount;
							CustomerPushBalance customerPushBalance = new CustomerPushBalance();
							customerPushBalance.setCustomerid(customerid);
							customerPushBalance.setPushtype("1");
							customerPushBalance.setBrandid(accessBrandDiscount.getBrandid());
							//生成的客户应收款冲差单应该为负的
							customerPushBalance.setAmount(brandCompAmount.negate());
							customerPushBalance.setRemark(oaAccess.getOaid());
							//补差特价对应的费用类别
							if("1".equals(oaAccess.getPricetype())){
//								customerPushBalance.setSubject(oaAccess.getPricetype());
							}else{
								customerPushBalance.setSubject(oaAccess.getExpensesort());
							}
							customerPushBalance.setBusinessdate(CommonUtils.getTodayDataStr());
							//冲差类型为正常冲差
							customerPushBalance.setIsinvoice("0");
							customerPushBalance.setOaid(oaAccess.getOaid());
							//生成冲差单
							String cpid = customerPushBanlanceService.addAndAuditCustomerPushBanlanceByOA(customerPushBalance);
							if(StringUtils.isNotEmpty(cpid)){
								if(StringUtils.isEmpty(ids)){
									ids += cpid;
								}else{
									ids += ","+ cpid;
								}
							}else{
								flag = false;
								throw new Exception("生成客户应收款冲差单失败！");
							}
						}else if("2".equals(oaAccess.getPaytype())){
							brandPayAmount = brandDiscountAmount;
							
							//支付方式为支票时，且电脑折让金额大于0 按比例对电脑折让金额进行分派
							//品牌工厂投入金额  = （品牌折让金额/品牌折让总金额）* 电脑折让总金额
							brandCompAmount = accessBrandDiscount.getAmount().divide(totalamount,2,BigDecimal.ROUND_HALF_UP).multiply(customerCompdiscountAmount).setScale(2,BigDecimal.ROUND_HALF_UP);
							//最后一个品牌电脑折让金额= 电脑折让总额- 已分配电脑折让金额
							if(j==(brandDiscountList.size())){
								brandCompAmount = customerCompdiscountAmount.subtract(customerUseCompdiscountAmount);
							}
							//合计品牌工厂投入金额
							customerUseCompdiscountAmount = customerUseCompdiscountAmount.add(brandCompAmount);
							if(brandCompAmount.compareTo(BigDecimal.ZERO)==1){
								CustomerPushBalance customerPushBalance = new CustomerPushBalance();
								customerPushBalance.setCustomerid(oaAccess.getCustomerid());
								customerPushBalance.setPushtype("1");
								customerPushBalance.setBrandid(accessBrandDiscount.getBrandid());
								//生成的客户应收款冲差单应该为负的
								customerPushBalance.setAmount(brandCompAmount.negate());
								customerPushBalance.setRemark(oaAccess.getOaid());
								//补差特价对应的费用类别
								if("1".equals(oaAccess.getPricetype())){
//									customerPushBalance.setSubject(oaAccess.getPricetype());
								}else{
									customerPushBalance.setSubject(oaAccess.getExpensesort());
								}
								customerPushBalance.setBusinessdate(CommonUtils.getTodayDataStr());
								//冲差类型为正常冲差
								customerPushBalance.setIsinvoice("0");
								customerPushBalance.setOaid(oaAccess.getOaid());
								//生成冲差单
								String cpid = customerPushBanlanceService.addAndAuditCustomerPushBanlanceByOA(customerPushBalance);
								if(StringUtils.isNotEmpty(cpid)){
									if(StringUtils.isEmpty(ids)){
										ids += cpid;
									}else{
										ids += ","+ cpid;
									}
								}else{
									flag = false;
									throw new Exception("生成客户应收款冲差单失败！");
								}
							}
						}
						
					}
				}
			}
		}else{
			//当该总店销售不存在时 取该总店第一家客户
			List list = getCustomerListByPid(oaAccess.getCustomerid());
			if(null!=list && list.size()>0){
				Customer customer = (Customer) list.get(0);
				for(OaAccessBrandDiscount accessBrandDiscount : brandDiscountList){
					//品牌电脑折让金额
					BigDecimal brandCompAmount = BigDecimal.ZERO;
					//品牌支付金额
					BigDecimal brandPayAmount = BigDecimal.ZERO;
					//支付方式为1折扣时 生成冲差单和代垫电脑折让
					//支付方式为2支票时 生成代垫支付金额
					if("1".equals(oaAccess.getPaytype())){
						//品牌电脑折让金额
						brandCompAmount = accessBrandDiscount.getAmount();
						CustomerPushBalance customerPushBalance = new CustomerPushBalance();
						customerPushBalance.setCustomerid(customer.getId());
						customerPushBalance.setPushtype("1");
						customerPushBalance.setBrandid(accessBrandDiscount.getBrandid());
						//生成的客户应收款冲差单应该为负的
						customerPushBalance.setAmount(brandCompAmount.negate());
						customerPushBalance.setRemark(oaAccess.getOaid());
						//补差特价对应的费用类别
						if("1".equals(oaAccess.getPricetype())){
//							customerPushBalance.setSubject(oaAccess.getPricetype());
						}else{
							customerPushBalance.setSubject(oaAccess.getExpensesort());
						}
						customerPushBalance.setBusinessdate(CommonUtils.getTodayDataStr());
						
						//冲差类型为正常冲差
						customerPushBalance.setIsinvoice("0");
						customerPushBalance.setOaid(oaAccess.getOaid());
						//生成冲差单
						String cpid = customerPushBanlanceService.addAndAuditCustomerPushBanlanceByOA(customerPushBalance);
						if(StringUtils.isNotEmpty(cpid)){
							if(StringUtils.isEmpty(ids)){
								ids += cpid;
							}else{
								ids += ","+ cpid;
							}
						}else{
							flag = false;
							throw new Exception("生成客户应收款冲差单失败！");
						}
					}
				}
			}
		}
		Map returnMap = new HashMap();
		returnMap.put("flag", flag);
		returnMap.put("msg", msg);
		returnMap.put("ids", ids);
		return returnMap;
	}

	@Override
	public boolean deleteBusinessBillByOaAccess(OaAccess oaAccess)
			throws Exception {
		boolean flag = false;
		if(null!=oaAccess && !"2".equals(oaAccess.getPricetype())){
			flag = customerPushBanlanceService.deleteCustomerPushBanlanceByOA(oaAccess.getOaid());
		}
		return flag;
	}

	@Override
	public Map addMatcostsByOaAccess(OaAccess oaAccess,
									 List<OaAccessBrandDiscount> brandDiscountList) throws Exception {
		boolean flag = false;
		String msg = "";
		String ids = "";
		//判断通路单 不是降价特价 其他情况通路单接口一致
		if(null!=oaAccess ){
			//通路单只有工厂投入金额时
			//if(null!=oaAccess.getFactoryamount() && oaAccess.getFactoryamount().compareTo(BigDecimal.ZERO)==1
			//		&&(oaAccess.getCompdiscount()==null || oaAccess.getCompdiscount().compareTo(BigDecimal.ZERO)==0)
			//		&&(oaAccess.getPayamount()==null || oaAccess.getPayamount().compareTo(BigDecimal.ZERO)==0)){
				//只有工厂投入金额时
				Map returnMap = addBusinessBillByOnlyFactoryAmount(oaAccess, brandDiscountList);
				return returnMap;
			//}else{
			//	Map returnMap = addBusinessMatcostsByCustomer(oaAccess, brandDiscountList);
			//	return returnMap;
			//}
		}else{
			msg = "通路单不存在！";
		}
		Map returnMap = new HashMap();
		returnMap.put("flag", flag);
		returnMap.put("msg", msg);
		returnMap.put("ids", ids);
		return returnMap;
	}
	
	/**
	 * 生成相关代垫数据
	 * @param oaAccess
	 * @param brandDiscountList
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月22日
	 */
	public Map addBusinessMatcostsByCustomer(OaAccess oaAccess,
			List<OaAccessBrandDiscount> brandDiscountList) throws Exception{
		SysUser sysUser = getSysUser();
		boolean flag = false;
		String msg = "";
		String ids = "";
		//工厂投入总额
		BigDecimal factoryamount = BigDecimal.ZERO;
		BigDecimal compdiscountAmount = BigDecimal.ZERO; 
		if(null!=oaAccess.getCompdiscount()){
			compdiscountAmount = oaAccess.getCompdiscount();
		}
		//已分配品牌工厂投入金额
		BigDecimal totaluseBrandFAmount = BigDecimal.ZERO;
		if(null!=oaAccess.getFactoryamount()){
			factoryamount = oaAccess.getFactoryamount();
		}
		//品牌合计总金额
		BigDecimal totalamount = BigDecimal.ZERO;
		for(OaAccessBrandDiscount accessBrandDiscount : brandDiscountList){
			totalamount = totalamount.add(accessBrandDiscount.getAmount()==null?BigDecimal.ZERO:accessBrandDiscount.getAmount());
		}
		if(!"2".equals(oaAccess.getPaytype())/* && totalamount.compareTo(compdiscountAmount)==0*/){
			// int i = 0;
			
			//新增代垫数据
			/*
			*/
			MatcostsInput matcostsInput = new MatcostsInput();
			matcostsInput.setSupplierid(oaAccess.getSupplierid());
			//供应商
			BuySupplier buySupplier = getSupplierInfoById(oaAccess.getSupplierid());
			if(null!=buySupplier){
				matcostsInput.setSupplierdeptid(buySupplier.getBuydeptid());
			}
//			matcostsInput.setBrandid(accessBrandDiscount.getBrandid());
			matcostsInput.setCustomerid(oaAccess.getCustomerid());
			matcostsInput.setBilltype("1");
			matcostsInput.setOaid(oaAccess.getOaid());
			matcostsInput.setBusinessdate(CommonUtils.getTodayDataStr());
			//补差特价 费用类别
			if("1".equals(oaAccess.getPricetype())){
				matcostsInput.setSubjectid("");
			}else{
				matcostsInput.setSubjectid(oaAccess.getExpensesort());
			}
			//工厂投入金额
			matcostsInput.setFactoryamount(oaAccess.getFactoryamount());
			//电脑折让金额
			matcostsInput.setHtcompdiscount(oaAccess.getCompdiscount());
			//支付金额
			// matcostsInput.setHtpayamount(BigDecimal.ZERO);
			matcostsInput.setHtpayamount(oaAccess.getPayamount());
			matcostsInput.setAdduserid(sysUser.getUserid());
			matcostsInput.setAddusername(sysUser.getName());
			matcostsInput.setBranchaccount(oaAccess.getBranchaccount());
			journalSheetService.addMatcostsInput(matcostsInput);
			/*
			for(OaAccessBrandDiscount accessBrandDiscount : brandDiscountList){
				i++;
				//生成代垫数据
				//如果有工厂投入金额  按品牌折让金额 平摊到个品牌上去
				BigDecimal brandFactoryAmount = BigDecimal.ZERO;
				//品牌工厂投入金额  = （品牌折让金额/品牌折让总金额）*工厂投入总额
				brandFactoryAmount = accessBrandDiscount.getAmount().divide(totalamount,2,BigDecimal.ROUND_HALF_UP).multiply(factoryamount).setScale(2,BigDecimal.ROUND_HALF_UP);
				//最后一个品牌工厂投入金额= 工厂投入总额- 已分配品牌工厂投入金额
				if(i==(brandDiscountList.size())){
					brandFactoryAmount = factoryamount.subtract(totaluseBrandFAmount);
				}
				//合计品牌工厂投入金额
				totaluseBrandFAmount = totaluseBrandFAmount.add(brandFactoryAmount);
				
				//新增代垫数据
				MatcostsInput matcostsInput = new MatcostsInput();
				matcostsInput.setSupplierid(oaAccess.getSupplierid());
				//供应商
				BuySupplier buySupplier = getSupplierInfoById(oaAccess.getSupplierid());
				if(null!=buySupplier){
					matcostsInput.setSupplierdeptid(buySupplier.getBuydeptid());
				}
				matcostsInput.setBrandid(accessBrandDiscount.getBrandid());
				matcostsInput.setCustomerid(oaAccess.getCustomerid());
				matcostsInput.setBilltype("1");
				matcostsInput.setOaid(oaAccess.getOaid());
				matcostsInput.setBusinessdate(CommonUtils.getTodayDataStr());
				//补差特价 费用类别
				if("1".equals(oaAccess.getPricetype())){
					matcostsInput.setSubjectid("");
				}else{
					matcostsInput.setSubjectid(oaAccess.getExpensesort());
				}
				//工厂投入金额
				matcostsInput.setFactoryamount(brandFactoryAmount);
				//电脑折让金额
				matcostsInput.setHtcompdiscount(accessBrandDiscount.getAmount());
				//支付金额
				matcostsInput.setHtpayamount(BigDecimal.ZERO);
				matcostsInput.setAdduserid(sysUser.getUserid());
				matcostsInput.setAddusername(sysUser.getName());
				boolean addMflag = journalSheetService.addMatcostsInput(matcostsInput);
				if(addMflag){
					flag = true;
					if(StringUtils.isEmpty(ids)){
						ids += matcostsInput.getId();
					}else{
						ids += ","+ matcostsInput.getId();
					}
				}else{
					flag = false;
					throw new Exception("生成代垫失败！");
				}
			}
			*/
		}else if("2".equals(oaAccess.getPaytype())){			//支付方式为支票
			//新增代垫数据
			MatcostsInput matcostsInput = new MatcostsInput();
			matcostsInput.setSupplierid(oaAccess.getSupplierid());
			//供应商
			BuySupplier buySupplier = getSupplierInfoById(oaAccess.getSupplierid());
			if(null!=buySupplier){
				matcostsInput.setSupplierdeptid(buySupplier.getBuydeptid());
			}
			matcostsInput.setBrandid("");
			matcostsInput.setCustomerid(oaAccess.getCustomerid());
			matcostsInput.setBilltype("1");
			matcostsInput.setOaid(oaAccess.getOaid());
			matcostsInput.setBusinessdate(CommonUtils.getTodayDataStr());
			//补差特价 费用类别
			if("1".equals(oaAccess.getPricetype())){
				matcostsInput.setSubjectid("");
			}else{
				matcostsInput.setSubjectid(oaAccess.getExpensesort());
			}
			//工厂投入金额
			matcostsInput.setFactoryamount(oaAccess.getFactoryamount());
			//电脑折让金额
			matcostsInput.setHtcompdiscount(BigDecimal.ZERO);
			//支付金额
			matcostsInput.setHtpayamount(oaAccess.getPayamount());
			matcostsInput.setAdduserid(sysUser.getUserid());
			matcostsInput.setAddusername(sysUser.getName());
			boolean addMflag = journalSheetService.addMatcostsInput(matcostsInput);
			if(addMflag){
				flag = true;
				if(StringUtils.isEmpty(ids)){
					ids += matcostsInput.getId();
				}else{
					ids += ","+ matcostsInput.getId();
				}
			}else{
				flag = false;
				throw new Exception("生成代垫失败！");
			}
		}else{
			msg = "电脑冲差金额与票扣金额不一致";
		}
		Map returnMap = new HashMap();
		returnMap.put("flag", flag);
		returnMap.put("msg", msg);
		returnMap.put("ids", ids);
		return returnMap;
	}
	
	@Override
	public boolean deleteMatcostsByOaAccess(OaAccess oaAccess) throws Exception {
		boolean flag = false;
		if(null != oaAccess/* && !"2".equals(oaAccess.getPricetype())*/ && (oaAccess.getFactoryamount() != null && BigDecimal.ZERO.compareTo(oaAccess.getFactoryamount()) != 0)){

            List list = journalSheetService.selectMatcostsInputByOaid(oaAccess.getOaid());
            if(list.size() % 2 == 0) {

                return true;
            }

            MatcostsInput oldMatcostsInput = (MatcostsInput) list.get(0);

            SysUser sysUser = getSysUser();

            //新增代垫数据
            MatcostsInput matcostsInput = new MatcostsInput();
            matcostsInput.setSupplierid(oldMatcostsInput.getSupplierid());
            matcostsInput.setSupplierdeptid(oldMatcostsInput.getSupplierdeptid());
            matcostsInput.setCustomerid(oldMatcostsInput.getCustomerid());
            matcostsInput.setBilltype("1");
            matcostsInput.setOaid(oldMatcostsInput.getOaid());
            matcostsInput.setBusinessdate(CommonUtils.getTodayDataStr());
            matcostsInput.setSubjectid(oldMatcostsInput.getSubjectid());
            //工厂投入金额
            matcostsInput.setFactoryamount(oldMatcostsInput.getFactoryamount().negate());
            //电脑折让金额
            matcostsInput.setHtcompdiscount(BigDecimal.ZERO);
            //支付金额
            matcostsInput.setHtpayamount(BigDecimal.ZERO);
            matcostsInput.setAdduserid(sysUser.getUserid());
            matcostsInput.setAddusername(sysUser.getName());
            matcostsInput.setRemark("OA驳回，OA编号：" + oaAccess.getOaid());
            // 3239 瑞家&通用版：通路单生成代垫时，数据来源不对
            matcostsInput.setSourcefrome("2");
			matcostsInput.setExpense(oldMatcostsInput.getExpense().negate());
			matcostsInput.setPaydate(oldMatcostsInput.getPaydate());
			matcostsInput.setTakebackdate(oldMatcostsInput.getTakebackdate());
            // 5160 通用版：OA生成代垫的时候经办人取当前工作处理人，相关OA：通路单、品牌费用申请单（支付）、客户费用申请单（账扣）
            matcostsInput.setTransactorid(sysUser.getPersonnelid());
			flag = journalSheetService.addMatcostsInput(matcostsInput, false);

//			flag = journalSheetService.deleteMatcostsInputByOA(oaAccess.getOaid());
		}
		return flag;
	}

	@Override
	public boolean deleteOffpriceByOaAccess(OaAccess oaAccess) throws Exception {
		boolean flag = false;
		if(null!=oaAccess && "2".equals(oaAccess.getPricetype())){
			flag = offpriceService.deleteOffpriceByOA(oaAccess.getOaid());
		}
		return flag;
	}

	@Override
	public boolean addCustomerCostPayableByOaAccess(OaAccess oaAccess) throws Exception {
		if(null!=oaAccess){

			List<CustomerCostPayable> list = (List<CustomerCostPayable>)journalSheetService.selectCustomerCostPayablByOaid(oaAccess.getOaid());

			int payCount = 0;
			for(CustomerCostPayable pay : list) {
				if("1".equals(pay.getBilltype())) {
					payCount++;
				}
			}

			if(payCount % 2 == 1) {
				return false;
			}

			CustomerCostPayable customerCostPayable = new CustomerCostPayable();
			customerCostPayable.setCustomerid(oaAccess.getCustomerid());
			customerCostPayable.setSupplierid(oaAccess.getSupplierid());
			Customer customer = getCustomerByID(oaAccess.getCustomerid());
			if(null!=customer){
				customerCostPayable.setPcustomerid(customer.getPid());
				if("0".equals(customer.getIslast())){
					customerCostPayable.setPcustomerid(oaAccess.getCustomerid());
				}
			}
			customerCostPayable.setBusinessdate(CommonUtils.getTodayDataStr());
			customerCostPayable.setIspay("0");
			customerCostPayable.setExpensesort(oaAccess.getExpensesort());
			customerCostPayable.setOaid(oaAccess.getOaid());
			customerCostPayable.setBilltype("1");
			customerCostPayable.setRelateoaid(oaAccess.getOaid());
            customerCostPayable.setApplyuserid(oaAccess.getAdduserid());
            customerCostPayable.setApplydeptid(getSysUserById(oaAccess.getAdduserid()).getDepartmentid());
            // 部门设定
            BuySupplier supplier = getSupplierInfoById(oaAccess.getSupplierid());
            customerCostPayable.setDeptid(supplier == null ? null : supplier.getBuydeptid());
			//费用金额
			customerCostPayable.setAmount(oaAccess.getTotalamount()==null?BigDecimal.ZERO:oaAccess.getTotalamount());
            customerCostPayable.setSourcefrom("12");    // 12:通路单
			boolean flag1 = journalSheetService.addCustomerCostPayable(customerCostPayable);
			boolean flag2 = journalSheetService.updateCustomerCostPayableOpen(oaAccess.getOaid());
			return flag1 && flag2;
		}
		return false;
	}

	@Override
	public boolean rollbackCustomerCostPayableByOaAccess(OaAccess oaAccess)
			throws Exception {
		if(null!=oaAccess){

			List<CustomerCostPayable> list = journalSheetService.selectCustomerCostPayablByOaid(oaAccess.getOaid());

			int payCount = 0;
			CustomerCostPayable pre = null;
			for(CustomerCostPayable pay : list) {
				if("1".equals(pay.getBilltype())) {
					payCount++;
					pre = ((pre == null) ? pay : pre);
				}
			}

			if(payCount % 2 == 0) {
				return false;
			}

			CustomerCostPayable customerCostPayable = new CustomerCostPayable();
			customerCostPayable.setCustomerid(pre.getCustomerid());
			customerCostPayable.setSupplierid(pre.getSupplierid());
			customerCostPayable.setPcustomerid(pre.getCustomerid());
			customerCostPayable.setBusinessdate(CommonUtils.getTodayDataStr());
			customerCostPayable.setIspay("0");
			customerCostPayable.setExpensesort(pre.getExpensesort());
			customerCostPayable.setOaid(pre.getOaid());
			customerCostPayable.setBilltype("1");
			customerCostPayable.setRelateoaid(pre.getRelateoaid());
			customerCostPayable.setApplyuserid(oaAccess.getAdduserid());
			customerCostPayable.setApplydeptid(getSysUserById(oaAccess.getAdduserid()).getDepartmentid());
			customerCostPayable.setDeptid(pre.getDeptid());
			//费用金额
			customerCostPayable.setAmount(pre.getAmount() == null ? BigDecimal.ZERO : pre.getAmount().negate());
			customerCostPayable.setSourcefrom("12");    // 12:通路单
            customerCostPayable.setRemark("OA驳回：" + oaAccess.getOaid());
			boolean flag1 = journalSheetService.addCustomerCostPayable(customerCostPayable);
			boolean flag2 = journalSheetService.updateCustomerCostPayableOpen(oaAccess.getOaid());
			return flag1 && flag2;

//			boolean flag = journalSheetService.deleteCustomerCostPayableByOaid(oaAccess.getOaid());
//			return flag;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean updateCustomerCostPayableColse(OaAccess oaAccess)
			throws Exception {
		if(null!=oaAccess){
			boolean flag = journalSheetService.updateCustomerCostPayableColse(oaAccess.getOaid());
			return flag;
		}else{
			return false;
		}
	}

	@Override
	public boolean updateCustomerCostPayableOpen(OaAccess oaAccess)
			throws Exception {
		if(null!=oaAccess){
			boolean flag = journalSheetService.updateCustomerCostPayableOpen(oaAccess.getOaid());
			return flag;
		}else{
			return false;
		}
	}

	@Override
	public List selectCustomerCostPayableByOaid(String oaid) throws Exception {

		return journalSheetService.selectCustomerCostPayablByOaid(oaid);
	}

	@Override
	public List selectMatcostsByOaid(String oaid) throws Exception {

		return journalSheetService.selectMatcostsInputByOaid(oaid);
	}

	@Override
	public List selectOffPrice(String oaid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addAuthedCustomerFeeByOaAccess(OaAccess access) throws Exception {

		if(access == null) {

			return false;
		}

		List<CustomerCostPayable> list = journalSheetService.selectCustomerCostPayablByOaid(access.getOaid());

		int payCount = 0;
		for(CustomerCostPayable payable : list) {
			if("3".equals(payable.getBilltype())) {
				payCount++;
			}
		}
		if(payCount % 2 == 1) {
			return false;
		}

		CustomerCostPayable customerCostPayable = new CustomerCostPayable();
		customerCostPayable.setCustomerid(access.getCustomerid());
		customerCostPayable.setSupplierid(access.getSupplierid());
		Customer customer = getCustomerByID(access.getCustomerid());
		if(null!=customer){

			customerCostPayable.setPcustomerid(customer.getPid());
			if("0".equals(customer.getIslast())){

				customerCostPayable.setPcustomerid(access.getCustomerid());
			}
		}
		customerCostPayable.setBusinessdate(CommonUtils.getTodayDataStr());
		customerCostPayable.setIspay("0");
		customerCostPayable.setExpensesort(access.getExpensesort());
		customerCostPayable.setOaid(access.getOaid());
		customerCostPayable.setBilltype("3");       // 3：客户应付费用(未批准)
		customerCostPayable.setRelateoaid(access.getOaid());
		customerCostPayable.setApplyuserid(access.getAdduserid());
		customerCostPayable.setApplydeptid(getSysUserById(access.getAdduserid()).getDepartmentid());
		//费用金额
		customerCostPayable.setAmount(access.getMyamount() == null ? BigDecimal.ZERO : access.getMyamount());
		boolean flag = journalSheetService.addCustomerCostPayable(customerCostPayable);
		return flag;
	}

	@Override
	public boolean deleteAuthedCustomerFeeByOaAccess(OaAccess access) throws Exception {

		int ret =  journalSheetService.deleteCustomerFee(access.getOaid(), "3");
		return ret > 0;
	}

	@Override
	public boolean updateAuthedCustomerFeeByOaAccess(OaAccess access) throws Exception {

		CustomerCostPayable payable = journalSheetService.selectCustomerFee(access.getOaid(), "3");
		payable.setAmount(access.getTotalamount());

		int ret = journalSheetService.updateCustomerFee(payable);
		return ret > 0;
	}}

