/**
 * @(#)FinanceFundsReturnServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 19, 2013 chenwei 创建版本
 */
package com.hd.agent.report.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.CollectionOrderMapper;
import com.hd.agent.account.dao.CustomerCapitalMapper;
import com.hd.agent.account.model.CustomerCapital;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.*;
import com.hd.agent.message.model.MsgContent;
import com.hd.agent.message.service.IInnerMessageService;
import com.hd.agent.report.dao.FinanceFundsReturnMapper;
import com.hd.agent.report.dao.PaymentdaysSetMapper;
import com.hd.agent.report.model.*;
import com.hd.agent.report.service.IFinanceFundsReturnService;
import com.hd.agent.sales.dao.ReceiptMapper;
import com.hd.agent.sales.model.Receipt;
import com.hd.agent.storage.dao.SaleRejectEnterMapper;
import com.hd.agent.storage.model.SaleRejectEnter;
import com.hd.agent.system.model.SysCode;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.model.TaskSchedule;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 资金回笼报表service实现类
 * @author chenwei
 */
public class FinanceFundsReturnServiceImpl extends BaseFilesServiceImpl implements IFinanceFundsReturnService{
	
	private FinanceFundsReturnMapper financeFundsReturnMapper;
	/**
	 * 超账期设置dao
	 */
	private PaymentdaysSetMapper paymentdaysSetMapper;
	
	private CustomerCapitalMapper customerCapitalMapper;
	
	private CollectionOrderMapper collectionOrderMapper;
	
	private ReceiptMapper receiptMapper;

    private SaleRejectEnterMapper rejectEnterMapper;

    public SaleRejectEnterMapper getRejectEnterMapper() {
        return rejectEnterMapper;
    }

    public void setRejectEnterMapper(SaleRejectEnterMapper rejectEnterMapper) {
        this.rejectEnterMapper = rejectEnterMapper;
    }

    public ReceiptMapper getReceiptMapper() {
		return receiptMapper;
	}

	public void setReceiptMapper(ReceiptMapper receiptMapper) {
		this.receiptMapper = receiptMapper;
	}

	public CollectionOrderMapper getCollectionOrderMapper() {
		return collectionOrderMapper;
	}

	public void setCollectionOrderMapper(CollectionOrderMapper collectionOrderMapper) {
		this.collectionOrderMapper = collectionOrderMapper;
	}

	public CustomerCapitalMapper getCustomerCapitalMapper() {
		return customerCapitalMapper;
	}

	public void setCustomerCapitalMapper(CustomerCapitalMapper customerCapitalMapper) {
		this.customerCapitalMapper = customerCapitalMapper;
	}

	public FinanceFundsReturnMapper getFinanceFundsReturnMapper() {
		return financeFundsReturnMapper;
	}

	public void setFinanceFundsReturnMapper(
			FinanceFundsReturnMapper financeFundsReturnMapper) {
		this.financeFundsReturnMapper = financeFundsReturnMapper;
	}
	
	public PaymentdaysSetMapper getPaymentdaysSetMapper() {
		return paymentdaysSetMapper;
	}

	public void setPaymentdaysSetMapper(PaymentdaysSetMapper paymentdaysSetMapper) {
		this.paymentdaysSetMapper = paymentdaysSetMapper;
	}

	@Override
	public PageData showCustomerSalesFlowList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_salesflow_base", "z");
		pageMap.setDataSql(dataSql);
		if(null != pageMap.getCondition().get("orderid")){
			String id = (String) pageMap.getCondition().get("orderid");
			id = StringEscapeUtils.escapeSql(id);
			Map map = new HashMap();
			map.put("id", id);
			Receipt receipt = receiptMapper.getReceipt(map);
			if(null != receipt){//发货回单
				pageMap.getCondition().put("orderid", receipt.getSaleorderid());
			}
		}
		List<CustomerSalesFlow> list = financeFundsReturnMapper.showCustomerSalesFlowList(pageMap);
		for(CustomerSalesFlow customerSalesFlow : list){
			if("9".equals(customerSalesFlow.getBilltype())){
				customerSalesFlow.setIsbitsum("1");
				customerSalesFlow.setCustomerid("");
				customerSalesFlow.setId(customerSalesFlow.getBusinessdate()+" 小计");
				customerSalesFlow.setBusinessdate("");
				customerSalesFlow.setGoodsid("");
				customerSalesFlow.setPrice(null);
                customerSalesFlow.setCostprice(null);
				customerSalesFlow.setIsinvoicebill(null);
                customerSalesFlow.setDeliveryStorage("");
				customerSalesFlow.setOrderid("");
				customerSalesFlow.setAuxnumdetail(CommonUtils.strDigitNumDeal(customerSalesFlow.getTotalbox().setScale(3,BigDecimal.ROUND_HALF_UP).toString()+"箱"));
			}else{
				GoodsInfo goodsInfo = getGoodsInfoByID(customerSalesFlow.getGoodsid());
				if(null!=goodsInfo){
					customerSalesFlow.setBarcode(goodsInfo.getBarcode());
					customerSalesFlow.setGoodsname(goodsInfo.getName());
					customerSalesFlow.setSpell(goodsInfo.getSpell());
					customerSalesFlow.setUnitid(goodsInfo.getMainunit());
					customerSalesFlow.setBoxnum(goodsInfo.getBoxnum());
					MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
					if(null!=meteringUnit){
						customerSalesFlow.setUnitname(meteringUnit.getName());
					}
                    if(null != customerSalesFlow.getPrice() && null != goodsInfo.getBoxnum()){
                        customerSalesFlow.setBoxprice(customerSalesFlow.getPrice().multiply(goodsInfo.getBoxnum()));
                    }
				}
				if(customerSalesFlow.getAuxnum().compareTo(BigDecimal.ZERO)!=0){
					customerSalesFlow.setAuxnumdetail(customerSalesFlow.getAuxnum().intValue()+"箱");
				}else{
					customerSalesFlow.setAuxnumdetail("");
				}
				if(customerSalesFlow.getAuxremainder().compareTo(BigDecimal.ZERO)!=0){
					customerSalesFlow.setAuxnumdetail(CommonUtils.strDigitNumDeal(customerSalesFlow.getAuxnumdetail()+customerSalesFlow.getAuxremainder()+customerSalesFlow.getUnitname()));
				}
				Customer customer = getCustomerByID(customerSalesFlow.getCustomerid());
				if(null!=customer){
					customerSalesFlow.setCustomername(customer.getName());
				}
				CustomerSort customerSort = getCustomerSortByID(customerSalesFlow.getCustomersort());
				if(null != customerSort){
					customerSalesFlow.setCustomersortname(customerSort.getThisname());
				}
				Brand brand2 = getGoodsBrandByID(customerSalesFlow.getBrandid());
				if(null != brand2){
					customerSalesFlow.setBrandname(brand2.getName());
				}
				Personnel personnel = getPersonnelById(customerSalesFlow.getSalesuser());
				if(null != personnel){
					customerSalesFlow.setSalesusername(personnel.getName());
				}
				SalesArea salesArea = getSalesareaByID(customerSalesFlow.getSalesarea());
				if(null!=salesArea){
					customerSalesFlow.setSalesareaname(salesArea.getName());
				}
				StorageInfo storageInfo = getStorageInfoByID(customerSalesFlow.getDeliveryStorage());
				if(null != storageInfo){
					customerSalesFlow.setDeliverystoragename(storageInfo.getName());
				}
				//获取客户 商品的合同价 或者价格套
				BigDecimal initprice = getGoodsPriceByCustomer(customerSalesFlow.getGoodsid(), customerSalesFlow.getCustomerid());
				customerSalesFlow.setInitprice(initprice);
				//单据类型
				if("1".equals(customerSalesFlow.getBilltype())){
					customerSalesFlow.setBilltypename("发货单");
//					//获取销售订单编号
//					Saleout saleout = getBaseSaleoutMapper().getSaleOutInfo(customerSalesFlow.getId());
//					if(null != saleout){
//						customerSalesFlow.setOrderid(saleout.getSaleorderid());
//					}
				}
				else if("2".equals(customerSalesFlow.getBilltype())){
					customerSalesFlow.setBilltypename("直退退货单");
//					//直退，获取销售订单编码或发货回单编号
//					SaleRejectEnter saleRejectEnter = getBaseSaleRejectEnterMapper().getSaleRejectEnterByID(customerSalesFlow.getId());
//					if(null != saleRejectEnter){
//						customerSalesFlow.setOrderid(saleRejectEnter.getSourceid());
//					}
				}
				else if("3".equals(customerSalesFlow.getBilltype())){
					customerSalesFlow.setBilltypename("售后退货单");
//					//售后退货，获取退货通知单编号
//					SaleRejectEnter saleRejectEnter = getBaseSaleRejectEnterMapper().getSaleRejectEnterByID(customerSalesFlow.getId());
//					if(null != saleRejectEnter){
//						customerSalesFlow.setOrderid(saleRejectEnter.getSourceid());
//					}
				}
				else if("4".equals(customerSalesFlow.getBilltype())){
					customerSalesFlow.setBilltypename("冲差单");
				}
				//折扣商品
				if("1".equals(customerSalesFlow.getBilltype()) && "1".equals(customerSalesFlow.getIsdiscount())){
					if(null!=goodsInfo){
						customerSalesFlow.setGoodsname("品牌："+goodsInfo.getBrandName()+"，折扣");
					}
				}else if("4".equals(customerSalesFlow.getBilltype()) && null!=customerSalesFlow.getGoodsid() && !"".equals(customerSalesFlow.getGoodsid())){
					Brand brand = getBaseFilesGoodsMapper().getBrandInfo(customerSalesFlow.getGoodsid());
					if(null!=brand){
						customerSalesFlow.setGoodsname("品牌："+brand.getName()+"，折扣");
					}
				}
				if(null != customerSalesFlow.getIsinvoicebill()){
					if("0".equals(customerSalesFlow.getIsinvoicebill())){
						customerSalesFlow.setIsinvoicebillname("未开票");
					}
					else if("1".equals(customerSalesFlow.getIsinvoicebill())){
						customerSalesFlow.setIsinvoicebillname("已开票");
					}
					if("1".equals(customerSalesFlow.getIswriteoff())){
						customerSalesFlow.setWriteoffname("已核销");
					}
					else if("0".equals(customerSalesFlow.getIswriteoff())){
						customerSalesFlow.setWriteoffname("未核销");
						Calendar c = Calendar.getInstance();
						Date date = c.getTime();
						SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
						Date now = smf.parse(smf.format(date));
						if(StringUtils.isNotEmpty(customerSalesFlow.getDuefromdate())){
							Date duefromdate = smf.parse(customerSalesFlow.getDuefromdate());
							if(duefromdate.before(now)){
								customerSalesFlow.setIsultra("1");
							}else{
								customerSalesFlow.setIsultra("0");
							}
						}
					}
				}
				else{
					customerSalesFlow.setIsinvoicebillname("");
				}
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.showCustomerSalesFlowCount(pageMap),list,pageMap);
		List<CustomerSalesFlow> footerList = new ArrayList<CustomerSalesFlow>();
		CustomerSalesFlow customerSalesFlow = new CustomerSalesFlow();
		customerSalesFlow.setId("合计");
		Map totalmap = financeFundsReturnMapper.showCustomerSalesFlowSum(pageMap);
		if(null!=totalmap){
			BigDecimal totalamount = (BigDecimal) totalmap.get("taxamount");
			BigDecimal totalcostamount = (BigDecimal) totalmap.get("costamount");
			BigDecimal auxnum = (BigDecimal) totalmap.get("auxnum");
			BigDecimal auxremainder = (BigDecimal) totalmap.get("auxremainder");
			BigDecimal unitnum = (BigDecimal) totalmap.get("unitnum");
			BigDecimal totalbox = (BigDecimal) totalmap.get("totalbox");
			BigDecimal marginamount = (BigDecimal)totalmap.get("marginamount");
			BigDecimal marginamountrate = (BigDecimal)totalmap.get("marginamountrate");
			customerSalesFlow.setUnitnum(unitnum);
			customerSalesFlow.setTaxamount(totalamount);
			customerSalesFlow.setCostamount(totalcostamount);
			customerSalesFlow.setAuxnum(auxnum);
			customerSalesFlow.setAuxremainder(auxremainder);
			customerSalesFlow.setAuxnumdetail(CommonUtils.strDigitNumDeal(totalbox.setScale(3,BigDecimal.ROUND_HALF_UP)+"箱"));
			customerSalesFlow.setTotalbox(totalbox);
			customerSalesFlow.setMarginamount(marginamount);
			customerSalesFlow.setMarginamountrate(marginamountrate);
			footerList.add(customerSalesFlow);
			pageData.setFooter(footerList);
		}
		return pageData;
	}

    @Override
    public List showCustomerSalesFlowListForExport(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_report_salesflow_base", "z");
//        String dataSql2 = dataSql;
//        dataSql = dataSql.replaceAll("salesarea", "y.salesarea");
//        dataSql2 = dataSql2.replaceAll("salesarea", "z.salesarea");
        pageMap.setDataSql(dataSql);
//        pageMap.getCondition().put("data_sql", dataSql2);
        if(null != pageMap.getCondition().get("orderid")){
            String id = (String) pageMap.getCondition().get("orderid");
            id = StringEscapeUtils.escapeSql(id);
            Map map = new HashMap();
            map.put("id", id);
            Receipt receipt = receiptMapper.getReceipt(map);
            if(null != receipt){//发货回单
                pageMap.getCondition().put("orderid", receipt.getSaleorderid());
            }
        }
        List<Map> list = financeFundsReturnMapper.showCustomerSalesFlowListForExport(pageMap);
//        for(CustomerSalesFlow customerSalesFlow : list){
//            if("9".equals(customerSalesFlow.getBilltype())){
//                customerSalesFlow.setIsbitsum("1");
//                customerSalesFlow.setCustomerid("");
//                customerSalesFlow.setId(customerSalesFlow.getBusinessdate()+" 小计");
//                customerSalesFlow.setBusinessdate("");
//                customerSalesFlow.setGoodsid("");
//                customerSalesFlow.setPrice(null);
//                customerSalesFlow.setCostprice(null);
//                customerSalesFlow.setIsinvoicebill(null);
//                customerSalesFlow.setDeliveryStorage("");
//                customerSalesFlow.setOrderid("");
//                customerSalesFlow.setAuxnumdetail(CommonUtils.strDigitNumDeal(customerSalesFlow.getTotalbox().setScale(3,BigDecimal.ROUND_HALF_UP).toString()+"箱"));
//            }else{
//                // GoodsInfo goodsInfo = getGoodsInfoByID(customerSalesFlow.getGoodsid());
////                 if(null!=goodsInfo){
//// //                    customerSalesFlow.setBarcode(goodsInfo.getBarcode());
//// //                    customerSalesFlow.setGoodsname(goodsInfo.getName());
//// //                    customerSalesFlow.setSpell(goodsInfo.getSpell());
//// //                    customerSalesFlow.setUnitid(goodsInfo.getMainunit());
//// //                    customerSalesFlow.setBoxnum(goodsInfo.getBoxnum());
//// //                    MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
//// //                    if(null!=meteringUnit){
//// //                        customerSalesFlow.setUnitname(meteringUnit.getName());
//// //                    }
////                     if(null != customerSalesFlow.getPrice() && null != goodsInfo.getBoxnum()){
////                         customerSalesFlow.setBoxprice(customerSalesFlow.getPrice().multiply(goodsInfo.getBoxnum()));
////                     }
////                 }
//                // if(customerSalesFlow.getAuxnum().compareTo(BigDecimal.ZERO)!=0){
//                //     customerSalesFlow.setAuxnumdetail(customerSalesFlow.getAuxnum().intValue()+"箱");
//                // }else{
//                //     customerSalesFlow.setAuxnumdetail("");
//                // }
//                // if(customerSalesFlow.getAuxremainder().compareTo(BigDecimal.ZERO)!=0){
//                //     customerSalesFlow.setAuxnumdetail(CommonUtils.strDigitNumDeal(customerSalesFlow.getAuxnumdetail()+customerSalesFlow.getAuxremainder()+customerSalesFlow.getUnitname()));
//                // }
////                Customer customer = getCustomerByID(customerSalesFlow.getCustomerid());
////                if(null!=customer){
////                    customerSalesFlow.setCustomername(customer.getName());
////                }
//                // CustomerSort customerSort = getCustomerSortByID(customerSalesFlow.getCustomersort());
//                // if(null != customerSort){
//                //     customerSalesFlow.setCustomersortname(customerSort.getThisname());
//                // }
//                // Brand brand2 = getGoodsBrandByID(customerSalesFlow.getBrandid());
//                // if(null != brand2){
//                //     customerSalesFlow.setBrandname(brand2.getName());
//                // }
//                // Personnel personnel = getPersonnelById(customerSalesFlow.getSalesuser());
//                // if(null != personnel){
//                //     customerSalesFlow.setSalesusername(personnel.getName());
//                // }
//                // SalesArea salesArea = getSalesareaByID(customerSalesFlow.getSalesarea());
//                // if(null!=salesArea){
//                //     customerSalesFlow.setSalesareaname(salesArea.getName());
//                // }
//                // StorageInfo storageInfo = getStorageInfoByID(customerSalesFlow.getDeliveryStorage());
//                // if(null != storageInfo){
//                //     customerSalesFlow.setDeliverystoragename(storageInfo.getName());
//                // }
//                //获取客户 商品的合同价 或者价格套
//                BigDecimal initprice = getGoodsPriceByCustomer(customerSalesFlow.getGoodsid(), customerSalesFlow.getCustomerid());
//                customerSalesFlow.setInitprice(initprice);
//                // //单据类型
//                // if("1".equals(customerSalesFlow.getBilltype())){
//                //     customerSalesFlow.setBilltypename("发货单");
//                // }
//                // else if("2".equals(customerSalesFlow.getBilltype())){
//                //     customerSalesFlow.setBilltypename("直退退货单");
//                // }
//                // else if("3".equals(customerSalesFlow.getBilltype())){
//                //     customerSalesFlow.setBilltypename("售后退货单");
//                // }
//                // else if("4".equals(customerSalesFlow.getBilltype())){
//                //     customerSalesFlow.setBilltypename("冲差单");
//                // }
//                //折扣商品
//                // if("1".equals(customerSalesFlow.getBilltype()) && "1".equals(customerSalesFlow.getIsdiscount())){
//                //     if(null!=goodsInfo){
//                //         customerSalesFlow.setGoodsname("品牌："+goodsInfo.getBrandName()+"，折扣");
//                //     }
//                // }else if("4".equals(customerSalesFlow.getBilltype()) && null!=customerSalesFlow.getGoodsid() && !"".equals(customerSalesFlow.getGoodsid())){
//                //     Brand brand = getBaseFilesGoodsMapper().getBrandInfo(customerSalesFlow.getGoodsid());
//                //     if(null!=brand){
//                //         customerSalesFlow.setGoodsname("品牌："+brand.getName()+"，折扣");
//                //     }
//                // }
//                // if(null != customerSalesFlow.getIsinvoicebill()){
//                //     if("0".equals(customerSalesFlow.getIsinvoicebill())){
//                //         customerSalesFlow.setIsinvoicebillname("未开票");
//                //     }
//                //     else if("1".equals(customerSalesFlow.getIsinvoicebill())){
//                //         customerSalesFlow.setIsinvoicebillname("已开票");
//                //     }
//                //     if("1".equals(customerSalesFlow.getIswriteoff())){
//                //         customerSalesFlow.setWriteoffname("已核销");
//                //     }
//                //     else if("0".equals(customerSalesFlow.getIswriteoff())){
//                //         customerSalesFlow.setWriteoffname("未核销");
//                //         Calendar c = Calendar.getInstance();
//                //         Date date = c.getTime();
//                //         SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
//                //         Date now = smf.parse(smf.format(date));
//                //         if(StringUtils.isNotEmpty(customerSalesFlow.getDuefromdate())){
//                //             Date duefromdate = smf.parse(customerSalesFlow.getDuefromdate());
//                //             if(duefromdate.before(now)){
//                //                 customerSalesFlow.setIsultra("1");
//                //             }else{
//                //                 customerSalesFlow.setIsultra("0");
//                //             }
//                //         }
//                //     }
//                // }
//                // else{
//                //     customerSalesFlow.setIsinvoicebillname("");
//                // }
//            }
//        }
//        PageData pageData = new PageData(financeFundsReturnMapper.showCustomerSalesFlowCount(pageMap),list,pageMap);
//        List<CustomerSalesFlow> footerList = new ArrayList<CustomerSalesFlow>();
//        CustomerSalesFlow customerSalesFlow = new CustomerSalesFlow();
//        customerSalesFlow.setId("合计");
//        Map totalmap = financeFundsReturnMapper.showCustomerSalesFlowSum(pageMap);
//        if(null!=totalmap){
//            BigDecimal totalamount = (BigDecimal) totalmap.get("taxamount");
//            BigDecimal totalcostamount = (BigDecimal) totalmap.get("costamount");
//            BigDecimal auxnum = (BigDecimal) totalmap.get("auxnum");
//            BigDecimal auxremainder = (BigDecimal) totalmap.get("auxremainder");
//            BigDecimal unitnum = (BigDecimal) totalmap.get("unitnum");
//            BigDecimal totalbox = (BigDecimal) totalmap.get("totalbox");
//            BigDecimal marginamount = (BigDecimal)totalmap.get("marginamount");
//            BigDecimal marginamountrate = (BigDecimal)totalmap.get("marginamountrate");
//            customerSalesFlow.setUnitnum(unitnum);
//            customerSalesFlow.setTaxamount(totalamount);
//            customerSalesFlow.setCostamount(totalcostamount);
//            customerSalesFlow.setAuxnum(auxnum);
//            customerSalesFlow.setAuxremainder(auxremainder);
//            customerSalesFlow.setAuxnumdetail(CommonUtils.strDigitNumDeal(totalbox.setScale(3,BigDecimal.ROUND_HALF_UP)+"箱"));
//            customerSalesFlow.setTotalbox(totalbox);
//            customerSalesFlow.setMarginamount(marginamount);
//            customerSalesFlow.setMarginamountrate(marginamountrate);
//            footerList.add(customerSalesFlow);
//            pageData.setFooter(footerList);
//        }
        return list;
    }

	@Override
	public File exportCustomerFlowListData(String title, PageMap pageMap) throws Exception {

		int page = 1;
		int total = 0;
		OutputStream out = null;
		SXSSFWorkbook book = null;
		File file = null;

		try {

			String dataSql = getDataAccessRule("t_report_salesflow_base", "z");
			pageMap.setDataSql(dataSql);

			//文件存放路径
			String path = OfficeUtils.getFilepath() + "/export/";
			File file2 = new File(path);
			//判断文件夹是否存在,如果不存在则创建文件夹
			if (!file2.exists()) {
				file2.mkdir();
			}
			String fileName = CommonUtils.getDataNumberWithRand() + ".xlsx";
			file = new File(path, fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			book = new SXSSFWorkbook(1000);
//			SXSSFSheet sheet = book.createSheet("sheet1");
			Sheet sheet = book.createSheet("sheet1");
			Row firstRow = sheet.createRow(0);

			DataFormat format = book.createDataFormat();
			CellStyle cellStyle0 = book.createCellStyle();
			cellStyle0.setDataFormat(format.getFormat("0"));
			CellStyle cellStyle1 = book.createCellStyle();
			cellStyle1.setDataFormat(format.getFormat("0.0"));
			CellStyle cellStyle2 = book.createCellStyle();
			cellStyle2.setDataFormat(format.getFormat("0.00"));
			CellStyle cellStyle3 = book.createCellStyle();
			cellStyle3.setDataFormat(format.getFormat("0.000"));

			CellStyle[] cellStyles = {cellStyle0, cellStyle1, cellStyle2, cellStyle3};

			//首行
			Cell cell = null;
			int cellCount = 0;
			String columnsStr = (String) pageMap.getCondition().get("columnsStr");
			List<Map> columnMapList = JSONUtils.jsonStrToList(columnsStr, new HashMap());
			{
				Row row = sheet.createRow(0);
				for (int i = 0; i < columnMapList.size(); i++) {
					Map columnMap = columnMapList.get(i);
					if (columnMap.containsKey("hidden") && ((Boolean) columnMap.get("hidden") || "true".equals(columnMap.get("hidden")))) {
//                        sheet.setColumnHidden((short) i, true);
						continue;
					}

					cell = row.createCell(cellCount++);
					cell.setCellValue((String) columnMap.get("title"));
				}
			}

			List list = null;

			out = new FileOutputStream(file);
			do {

				list = null;
				System.gc();

				pageMap.setPage(page);
				list = showCustomerSalesFlowListForExport(pageMap);
				page++;

				for (int i = 0; i < list.size(); i++) {

					Row row = sheet.createRow(total + i + 1);
					cellCount = 0;
					Map dataMap = (Map) list.get(i);

					for (int j = 0; j < columnMapList.size(); j++) {
						Map columnMap = columnMapList.get(j);
						if (columnMap.containsKey("hidden") && ((Boolean) columnMap.get("hidden") || "true".equals(columnMap.get("hidden")))) {
							continue;
						}

						cell = row.createCell(cellCount++);
						Object value = dataMap.get(columnMap.get("field"));
						if (value == null) {
							continue;
						}
						if (value instanceof BigDecimal) {
							cell.setCellValue(((BigDecimal) value).doubleValue());
							if(columnMap.containsKey("precision")) {
								cell.setCellStyle(cellStyles[(Integer) columnMap.get("precision")]);
							}
//                            String precision = (String) columnMap.get("precision");
//                            if(StringUtils.isNotEmpty(precision)) {
//                                cell.setCellStyle(cellStyles[Integer.parseInt(precision)]);
//                            }
						} else {
							cell.setCellValue(value.toString());
						}
					}
				}

				total = total + list.size();
				System.gc();
			} while (list.size() > 0);

			Map totalmap = financeFundsReturnMapper.showCustomerSalesFlowSum(pageMap);
			List totalList = new ArrayList();
			for (int i = 0; i < totalList.size(); i++) {

				Row row = sheet.createRow(total + i + 1);
				cellCount = 0;
				Map dataMap = (Map) totalList.get(i);

				for (int j = 0; j < columnMapList.size(); j++) {
					Map columnMap = columnMapList.get(j);
					if (columnMap.containsKey("hidden") && ((Boolean) columnMap.get("hidden") || "true".equals(columnMap.get("hidden")))) {
						continue;
					}

					cell = row.createCell(cellCount++);
					Object value = dataMap.get(columnMap.get("field"));
					if (value == null) {
						continue;
					}
					if (value instanceof BigDecimal) {
						cell.setCellValue(((BigDecimal) value).doubleValue());
						if(columnMap.containsKey("precision")) {
							cell.setCellStyle(cellStyles[(Integer) columnMap.get("precision")]);
						}
					} else {
						cell.setCellValue(value.toString());
					}
				}
			}

//			// 合计
//			List<CustomerSalesFlow> footerList = new ArrayList<CustomerSalesFlow>();
//			CustomerSalesFlow customerSalesFlow = new CustomerSalesFlow();
//			customerSalesFlow.setId("合计");
//			Map totalmap = financeReportMapper.showCustomerSalesFlowSum(pageMap);
//			if (null != totalmap) {
//
//				BigDecimal totalamount = (BigDecimal) totalmap.get("taxamount");
//				BigDecimal totalcostamount = (BigDecimal) totalmap.get("costamount");
//				BigDecimal auxnum = (BigDecimal) totalmap.get("auxnum");
//				BigDecimal auxremainder = (BigDecimal) totalmap.get("auxremainder");
//				BigDecimal unitnum = (BigDecimal) totalmap.get("unitnum");
//				BigDecimal totalbox = (BigDecimal) totalmap.get("totalbox");
//				BigDecimal marginamount = (BigDecimal) totalmap.get("marginamount");
//				BigDecimal marginamountrate = (BigDecimal) totalmap.get("marginamountrate");
//				BigDecimal boxamount = (BigDecimal) totalmap.get("boxamount");
//				BigDecimal goodsamount = (BigDecimal) totalmap.get("goodsamount");
//				BigDecimal allamount = boxamount.add(goodsamount);
//
//				customerSalesFlow.setUnitnum(unitnum);
//				customerSalesFlow.setTaxamount(totalamount);
//				customerSalesFlow.setCostamount(totalcostamount);
//				customerSalesFlow.setAuxnum(auxnum);
//				customerSalesFlow.setAuxremainder(auxremainder);
//				customerSalesFlow.setAuxnumdetail(totalbox.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
//				customerSalesFlow.setTotalbox(totalbox);
//				customerSalesFlow.setMarginamount(marginamount);
//				customerSalesFlow.setMarginamountrate(marginamountrate);
//			}
//
//			Map totalMap = PropertyUtils.describe(customerSalesFlow);
//			Row row = sheet.createRow(total + 2);
//			{
//				for (int j = 0; j < columnMapList.size(); j++) {
//					Map columnMap = columnMapList.get(j);
//					if (columnMap.containsKey("hidden") && ((Boolean) columnMap.get("hidden") || "true".equals(columnMap.get("hidden")))) {
//						// sheet.setColumnHidden((short)i,true);
//						continue;
//					}
//
//					cell = row.createCell(cellCount++);
//					Object value = totalMap.get(columnMap.get("field"));
//					if (value == null) {
//						continue;
//					}
//					if (value instanceof BigDecimal) {
//						cell.setCellValue(((BigDecimal) value).doubleValue());
//						if(columnMap.containsKey("precision")) {
//							cell.setCellStyle(cellStyles[(Integer) columnMap.get("precision")]);
//						}
////                            String precision = (String) columnMap.get("precision");
////                            if(StringUtils.isNotEmpty(precision)) {
////                                cell.setCellStyle(cellStyles[Integer.parseInt(precision)]);
////                            }
//					} else {
//						cell.setCellValue(value.toString());
//					}
//				}
//			}

//			sheet.trackAllColumnsForAutoSizing();
//			for (int i = 0; i <= 46; i++) {
//				sheet.autoSizeColumn(i, true);
//			}

//            //设置自适应列宽
//            if(null!=dataList && dataList.size()>0){
//                Object dataObject = dataList.get(0);
//                Map<String, Object> rowMap1 = null;
//                if(dataObject instanceof Map){
//                    rowMap1 = (Map) dataObject;
//                }else{
//                    rowMap1 = PropertyUtils.describe(dataList.get(0));
//                }
//                int i = 0;
//                for(Map.Entry<String, Object> entry : rowMap1.entrySet()){
//                    sheet.autoSizeColumn(i);
//                    int maxColumnWidth = sheet.getColumnWidth(i);
//                    if(maxColumnWidth == 65280){
//                        sheet.setColumnWidth(i, 5000);//数据过长时设置最大的列宽
//                    }else{
//                        sheet.setColumnWidth(i, maxColumnWidth+500);
//                    }
//                    i++;
//                }
//            }
//
//			sheet.flushRows();
			book.write(out);

		} finally {

//			if (book != null) {
//				book.close();
//			}
			if (out != null) {
				out.close();
			}
			System.gc();
		}

		return file;
	}

	@Override
	public List showCustomerSalesFlowDetailList(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_report_salesflow_base", "z");
		pageMap.setDataSql(dataSql);
		if(null != pageMap.getCondition().get("orderid")){
			String id = (String) pageMap.getCondition().get("orderid");
			id = StringEscapeUtils.escapeSql(id);
			Map map = new HashMap();
			map.put("id", id);
			Receipt receipt = receiptMapper.getReceipt(map);
			if(null != receipt){//发货回单
				pageMap.getCondition().put("orderid", receipt.getSaleorderid());
			}
		}
		List<CustomerSalesFlow> list = financeFundsReturnMapper.showCustomerSalesFlowDetailList(pageMap);
		for(CustomerSalesFlow customerSalesFlow : list){
			if("4".equals(customerSalesFlow.getBilltype())){
				BigDecimal taxrate = new BigDecimal("1");
				SysParam taxtaypeParam = getBaseSysParamMapper().getSysParam("DEFAULTAXTYPE");
				customerSalesFlow.setUnitnum(new BigDecimal("1"));
				if(null != taxtaypeParam){
					customerSalesFlow.setTaxtype(taxtaypeParam.getPvalue());
					TaxType taxType = getBaseFilesFinanceMapper().getTaxTypeInfo(taxtaypeParam.getPvalue());
					if(null != taxType){
						customerSalesFlow.setTaxtypename(taxType.getName());
						taxrate = taxType.getRate().divide(new BigDecimal("100")).add(new BigDecimal("1"));
					}
				}
				customerSalesFlow.setNotaxamount(customerSalesFlow.getTaxamount().divide(taxrate, 6, BigDecimal.ROUND_HALF_UP));
				customerSalesFlow.setNoprice(customerSalesFlow.getNotaxamount().divide(customerSalesFlow.getUnitnum(), 6, BigDecimal.ROUND_HALF_UP));
				customerSalesFlow.setTax(customerSalesFlow.getTaxamount().subtract(customerSalesFlow.getNotaxamount()));
				
				Brand brand = getGoodsBrandByID(customerSalesFlow.getGoodsid());
				if(null != brand){
					customerSalesFlow.setGoodsname("品牌："+brand.getName()+"，折扣");
				}
			}else if("1".equals(customerSalesFlow.getBilltype()) && "1".equals(customerSalesFlow.getIsdiscount())){
				GoodsInfo goodsInfo = getGoodsInfoByID(customerSalesFlow.getGoodsid());
				if(null!=goodsInfo){
					customerSalesFlow.setGoodsname("品牌："+goodsInfo.getBrandName()+"，折扣");
				}
			}else{
				GoodsInfo goodsInfo = getGoodsInfoByID(customerSalesFlow.getGoodsid());
				if(null!=goodsInfo){
					customerSalesFlow.setGoodsname(goodsInfo.getName());
					customerSalesFlow.setModel(goodsInfo.getModel());
					TaxType taxType = getBaseFilesFinanceMapper().getTaxTypeInfo(goodsInfo.getDefaulttaxtype());
					if(null != taxType){
						customerSalesFlow.setTaxtypename(taxType.getName());
					}
				}
			}
		}
		return list;
	}

	@Override
	public PageData showCustomerReceivablePastDueListData(PageMap pageMap)
			throws Exception {
		//未回笼资金 
		String iswithdrawal = "0";
		//未回笼资金
		pageMap.getCondition().put("iswithdrawal", iswithdrawal);
		SysUser sysUser = getSysUser();
		List<PaymentdaysSet> paymentdaysList = paymentdaysSetMapper.getPaymentdaysSetByUserid(sysUser.getUserid(),"1");
		List<Map<String,Object>> list = financeFundsReturnMapper.showCustomerReceivablePastDueListData(pageMap);
		for(Map<String,Object> dataMap : list){
			String customerid = (String) dataMap.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				dataMap.put("customername", customer.getName());
			}
			for(PaymentdaysSet paymentdaysSet : paymentdaysList){
				Map queryMap = new HashMap();
				String endDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getBeginday());
				if(paymentdaysSet.getEndday()>0){
					String beginDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getEndday());
					queryMap.put("beginDate", beginDate);
				}
				queryMap.put("endDate", endDate);
				queryMap.put("thiscustomerid", customerid);
				queryMap.put("iswithdrawal", 0);
				BigDecimal passamount = financeFundsReturnMapper.getCustomerPastDueDataByDays(queryMap);
				if(null!=passamount){
					dataMap.put("passamount"+paymentdaysSet.getSeq(), passamount);
				}else{
					dataMap.put("passamount"+paymentdaysSet.getSeq(), BigDecimal.ZERO);
				}
			}
			//获取客户应收款未回笼 未超账期总金额
			BigDecimal unpassdayamount = financeFundsReturnMapper.getCustomerNoPastDueDataAmount(customerid);
			////获取客户应收款未回笼 超账期总金额
			BigDecimal passdayamount = financeFundsReturnMapper.getCustomrPastDueDataAmount(customerid);
			if(null!=unpassdayamount){
				dataMap.put("unpassdayamount", unpassdayamount);
			}else{
				dataMap.put("unpassdayamount", BigDecimal.ZERO);
			}
			if(null!=passdayamount){
				dataMap.put("passdayamount", passdayamount);
			}else{
				dataMap.put("passdayamount", BigDecimal.ZERO);
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.showCustomerReceivablePastDueListDataCount(pageMap),list,pageMap);
		//合计数据
		Map<String,Object> totalDataMap  = financeFundsReturnMapper.getCustomerReceivablePastDueSumData(pageMap);
		totalDataMap.put("customerid", "");
		totalDataMap.put("customername", "合计");
		BigDecimal totalunpassdayamount = financeFundsReturnMapper.getCustomerNoPastDueSumDataAmount(pageMap);
		BigDecimal totalpassdayamount = financeFundsReturnMapper.getCustomrPastDueSumDataAmount(pageMap);
		if(null!=totalunpassdayamount){
			totalDataMap.put("unpassdayamount", totalunpassdayamount);
		}else{
			totalDataMap.put("unpassdayamount", BigDecimal.ZERO);
		}
		if(null!=totalpassdayamount){
			totalDataMap.put("passdayamount", totalpassdayamount);
		}else{
			totalDataMap.put("passdayamount", BigDecimal.ZERO);
		}
		for(PaymentdaysSet paymentdaysSet : paymentdaysList){
			pageMap.getCondition().remove("endDate");
			pageMap.getCondition().remove("beginDate");
			String endDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getBeginday());
			pageMap.getCondition().put("endDate", endDate);
			if(paymentdaysSet.getEndday()>0){
				String beginDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getEndday());
				pageMap.getCondition().put("beginDate", beginDate);
			}
			
			BigDecimal passamount = financeFundsReturnMapper.getCustomerPastDueSumDataByDays(pageMap);
			if(null!=passamount){
				totalDataMap.put("passamount"+paymentdaysSet.getSeq(), passamount);
			}else{
				totalDataMap.put("passamount"+paymentdaysSet.getSeq(), BigDecimal.ZERO);
			}
		}
		List<Map<String,Object>> footerList = new ArrayList<Map<String,Object>>();
		footerList.add(totalDataMap);
		pageData.setFooter(footerList);
		return pageData;
	}

	@Override
	public PageData showCustomerWithdrawalPastDueListData(PageMap pageMap)
			throws Exception {
		//已回笼资金 
		String iswithdrawal = "1";
		
		pageMap.getCondition().put("iswithdrawal", iswithdrawal);
		SysUser sysUser = getSysUser();
		List<PaymentdaysSet> paymentdaysList = paymentdaysSetMapper.getPaymentdaysSetByUserid(sysUser.getUserid(),"1");
		List<Map<String,Object>> list = financeFundsReturnMapper.showCustomerReceivablePastDueListData(pageMap);
		for(Map<String,Object> dataMap : list){
			String customerid = (String) dataMap.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				dataMap.put("customername", customer.getName());
			}
			for(PaymentdaysSet paymentdaysSet : paymentdaysList){
				Map queryMap = new HashMap();
				queryMap.put("businessdate1", pageMap.getCondition().get("businessdate1"));
				queryMap.put("businessdate2", pageMap.getCondition().get("businessdate2"));
				String endDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getBeginday());
				if(paymentdaysSet.getEndday()>0){
					String beginDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getEndday());
					queryMap.put("beginDate", beginDate);
				}
				queryMap.put("endDate", endDate);
				queryMap.put("thiscustomerid", customerid);
				queryMap.put("iswithdrawal", iswithdrawal);
				BigDecimal passamount = financeFundsReturnMapper.getCustomerPastDueDataByDays(queryMap);
				if(null!=passamount){
					dataMap.put("passamount"+paymentdaysSet.getSeq(), passamount);
				}else{
					dataMap.put("passamount"+paymentdaysSet.getSeq(), BigDecimal.ZERO);
				}
			}
			pageMap.getCondition().put("thiscustomerid", customerid);
			BigDecimal unpassdayamount = financeFundsReturnMapper.getCustomerWithdrawalNoPastDueDataAmount(pageMap);
			BigDecimal passdayamount = financeFundsReturnMapper.getCustomrWithdrawalPastDueDataAmount(pageMap);
			if(null!=unpassdayamount){
				dataMap.put("unpassdayamount", unpassdayamount);
			}else{
				dataMap.put("unpassdayamount", BigDecimal.ZERO);
			}
			if(null!=passdayamount){
				dataMap.put("passdayamount", passdayamount);
			}else{
				dataMap.put("passdayamount", BigDecimal.ZERO);
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.showCustomerReceivablePastDueListDataCount(pageMap),list,pageMap);
		//合计数据
		Map<String,Object> totalDataMap  = financeFundsReturnMapper.getCustomerReceivablePastDueSumData(pageMap);
		if(null==totalDataMap){
			totalDataMap = new HashMap();
			totalDataMap.put("amount", BigDecimal.ZERO);
		}
		totalDataMap.put("customerid", "");
		totalDataMap.put("customername", "合计");
		BigDecimal totalunpassdayamount = financeFundsReturnMapper.getCustomerNoPastDueSumDataAmount(pageMap);
		BigDecimal totalpassdayamount = financeFundsReturnMapper.getCustomrPastDueSumDataAmount(pageMap);
		if(null!=totalunpassdayamount){
			totalDataMap.put("unpassdayamount", totalunpassdayamount);
		}else{
			totalDataMap.put("unpassdayamount", BigDecimal.ZERO);
		}
		if(null!=totalpassdayamount){
			totalDataMap.put("passdayamount", totalpassdayamount);
		}else{
			totalDataMap.put("passdayamount", BigDecimal.ZERO);
		}
		for(PaymentdaysSet paymentdaysSet : paymentdaysList){
			pageMap.getCondition().remove("endDate");
			pageMap.getCondition().remove("beginDate");
			String endDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getBeginday());
			pageMap.getCondition().put("endDate", endDate);
			if(paymentdaysSet.getEndday()>0){
				String beginDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getEndday());
				pageMap.getCondition().put("beginDate", beginDate);
			}
			
			BigDecimal passamount = financeFundsReturnMapper.getCustomerPastDueSumDataByDays(pageMap);
			if(null!=passamount){
				totalDataMap.put("passamount"+paymentdaysSet.getSeq(), passamount);
			}else{
				totalDataMap.put("passamount"+paymentdaysSet.getSeq(), BigDecimal.ZERO);
			}
		}
		List<Map<String,Object>> footerList = new ArrayList<Map<String,Object>>();
		footerList.add(totalDataMap);
		pageData.setFooter(footerList);
		return pageData;
	}
	@Override
	public PageData showBankReportData(PageMap pageMap) throws Exception {
		List<BankReport> list = financeFundsReturnMapper.showBankReportDataList(pageMap);
		BigDecimal totalReceiptamount = BigDecimal.ZERO;
		BigDecimal totalPayamount = BigDecimal.ZERO;
		BigDecimal totalEndamount = BigDecimal.ZERO;
		BigDecimal totalReceiptwriteamount = BigDecimal.ZERO;
		BigDecimal totalUnReceiptwriteamount = BigDecimal.ZERO;
		BigDecimal totalPaywriteamount = BigDecimal.ZERO;
		BigDecimal totalUnPaywriteamount = BigDecimal.ZERO;
		for(BankReport bankReport : list){
			Bank bank = getBankInfoByID(bankReport.getBank());
			if(null!=bank){
				bankReport.setBankname(bank.getName());
			}
			else{
				bankReport.setBankname("现金");
			}
			bankReport.setEndamount(bankReport.getReceiptamount().subtract(bankReport.getPayamount()));
			totalReceiptamount = totalReceiptamount.add(bankReport.getReceiptamount());
			totalPayamount = totalPayamount.add(bankReport.getPayamount());
			totalEndamount = totalEndamount.add(bankReport.getEndamount());
		}
		PageData pageData = new PageData(list.size(),list,pageMap);
		List footer = new ArrayList();
		BankReport bankReportSum = new BankReport();
		bankReportSum.setBankname("合计");
		bankReportSum.setReceiptamount(totalReceiptamount);
		bankReportSum.setPayamount(totalPayamount);
		bankReportSum.setEndamount(totalEndamount);
		bankReportSum.setReceiptwriteamount(totalReceiptwriteamount);
		footer.add(bankReportSum);
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData showCustomerReceiptBankListData(PageMap pageMap)
			throws Exception {
		List<Map<String,Object>> list = financeFundsReturnMapper.showCustomerReceiptData(pageMap);
		int totalcount = financeFundsReturnMapper.showCustomerReceiptCount(pageMap);
		List<Bank> bankList = getBaseFilesFinanceMapper().getBankList();
		for(Map<String,Object> map : list){
			String customerid = (String) map.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				map.put("customername", customer.getName());
                DepartMent saleDept = getDepartmentByDeptid(customer.getSalesdeptid());
                if(null!=saleDept){
                    map.put("salesdeptname", saleDept.getName());
                }
			}
			if(null==customerid || "".equals(customerid)){
				map.put("customername", "未指定客户");
			}
			pageMap.getCondition().put("thiscustomerid", customerid);
			pageMap.getCondition().put("bank", null);
			//获取该客户现金金额
			//BigDecimal cashamount = financeFundsReturnMapper.getCustomerReceiptBankSumByCustomeridAndBank(pageMap);
			//map.put("cashamount", cashamount);
			//获取该金额互各银行金额
			for(Bank bank : bankList){
				pageMap.getCondition().put("bank", bank.getId());
				BigDecimal bankamount = financeFundsReturnMapper.getCustomerReceiptBankSumByCustomeridAndBank(pageMap);
				map.put("bank"+bank.getId(), bankamount);
			}
		}
		PageData pageData = new PageData(totalcount,list,pageMap);
		Map<String,Object> footmap = new HashMap<String,Object>();
		pageMap.getCondition().put("bank", null);
		//合计数据
		BigDecimal totalamount = financeFundsReturnMapper.getCustomerReceiptSum(pageMap);
		footmap.put("amount", totalamount);
		footmap.put("customername", "合计");
		BigDecimal totalcashamount = financeFundsReturnMapper.getCustomerReceiptBankSumByBank(pageMap);
		footmap.put("cashamount", totalcashamount);
		//获取该金额互各银行金额
		for(Bank bank : bankList){
			pageMap.getCondition().put("bank", bank.getId());
			BigDecimal bankamount = financeFundsReturnMapper.getCustomerReceiptBankSumByBank(pageMap);
			footmap.put("bank"+bank.getId(), bankamount);
		}
		List<Map<String,Object>> footerList = new ArrayList<Map<String,Object>>();
		footerList.add(footmap);
		pageData.setFooter(footerList);
		return pageData;
	}

	@Override
	public PageData showCustomerPastDueListPage(PageMap pageMap)
			throws Exception {
		SysUser sysUser = getSysUser();
		String seq = (String) pageMap.getCondition().get("seq");
		if(null!=seq && !"".equals(seq)){
			PaymentdaysSet paymentdaysSet = paymentdaysSetMapper.getPaymentdaysSetByUseridAndSeq(sysUser.getUserid(), seq,"1");
			String endDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getBeginday());
			if(paymentdaysSet.getEndday()>0){
				String beginDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getEndday());
				pageMap.getCondition().put("beginDate", beginDate);
			}
			pageMap.getCondition().put("endDate", endDate);
		}else{
			pageMap.getCondition().put("endDate", CommonUtils.getTodayDataStr());
		}
		List<Receipt> list = financeFundsReturnMapper.getCustomerPastDueDataListByDays(pageMap);
		for(Receipt receipt : list){
			DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(receipt.getSalesdept());
			if(departMent != null){
				receipt.setSalesdept(departMent.getName());
			}
			Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(receipt.getSalesuser());
			if(personnel != null){
				receipt.setSalesuser(personnel.getName());
			}
			Customer customer = getCustomerByID(receipt.getCustomerid());
			if(customer != null){
				receipt.setCustomerid(customer.getId());
				receipt.setCustomername(customer.getShortname());
			}
			Contacter contacter = getContacterById(receipt.getHandlerid());
			if(contacter != null){
				receipt.setHandlerid(contacter.getName());
			}
			Map total = financeFundsReturnMapper.getReceiptDetailTotal(receipt.getId());
			if(total != null){
				if(total.containsKey("taxamount")){
					receipt.setField01(total.get("taxamount").toString());
				}
				if(total.containsKey("notaxamount")){
					receipt.setField02(total.get("notaxamount").toString());
				}
				if(total.containsKey("tax")){
					receipt.setField03(total.get("tax").toString());
				}
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.getCustomerPastDueDataCountByDays(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public PageData showSalesUserReceivablePastDueListData(PageMap pageMap)
			throws Exception {
		//未回笼资金 
		String iswithdrawal = "0";
		//未回笼资金
		pageMap.getCondition().put("iswithdrawal", iswithdrawal);
		SysUser sysUser = getSysUser();
		List<PaymentdaysSet> paymentdaysList = paymentdaysSetMapper.getPaymentdaysSetByUserid(sysUser.getUserid(),"1");
		List<Map<String,Object>> list = financeFundsReturnMapper.showSalesUserReceivablePastDueListData(pageMap);
		for(Map<String,Object> dataMap : list){
			String salesuserid = (String) dataMap.get("salesuserid");
			Personnel personnel = getPersonnelById(salesuserid);
			if(null!=personnel){
				dataMap.put("salesusername", personnel.getName());
			}
			for(PaymentdaysSet paymentdaysSet : paymentdaysList){
				Map queryMap = new HashMap();
				String endDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getBeginday());
				if(paymentdaysSet.getEndday()>0){
					String beginDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getEndday());
					queryMap.put("beginDate", beginDate);
				}
				queryMap.put("endDate", endDate);
				queryMap.put("thissalesuserid", salesuserid);
				queryMap.put("iswithdrawal", 0);
				BigDecimal passamount = financeFundsReturnMapper.getSalesUserPastDueDataByDays(queryMap);
				if(null!=passamount){
					dataMap.put("passamount"+paymentdaysSet.getSeq(), passamount);
				}else{
					dataMap.put("passamount"+paymentdaysSet.getSeq(), BigDecimal.ZERO);
				}
			}
			//获取客户应收款未回笼 未超账期总金额
			BigDecimal unpassdayamount = financeFundsReturnMapper.getSalesUserNoPastDueDataAmount(salesuserid);
			////获取客户应收款未回笼 超账期总金额
			BigDecimal passdayamount = financeFundsReturnMapper.getSalesUserPastDueDataAmount(salesuserid);
			if(null!=unpassdayamount){
				dataMap.put("unpassdayamount", unpassdayamount);
			}else{
				dataMap.put("unpassdayamount", BigDecimal.ZERO);
			}
			if(null!=passdayamount){
				dataMap.put("passdayamount", passdayamount);
			}else{
				dataMap.put("passdayamount", BigDecimal.ZERO);
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.showSalesUserReceivablePastDueListDataCount(pageMap),list,pageMap);
		//合计数据
		Map<String,Object> totalDataMap  = financeFundsReturnMapper.getSalesUserReceivablePastDueSumData(pageMap);
		totalDataMap.put("salesuserid", "");
		totalDataMap.put("salesusername", "合计");
		BigDecimal totalunpassdayamount = financeFundsReturnMapper.getSalesUserNoPastDueSumDataAmount(pageMap);
		BigDecimal totalpassdayamount = financeFundsReturnMapper.getSalesUserPastDueSumDataAmount(pageMap);
		if(null!=totalunpassdayamount){
			totalDataMap.put("unpassdayamount", totalunpassdayamount);
		}else{
			totalDataMap.put("unpassdayamount", BigDecimal.ZERO);
		}
		if(null!=totalpassdayamount){
			totalDataMap.put("passdayamount", totalpassdayamount);
		}else{
			totalDataMap.put("passdayamount", BigDecimal.ZERO);
		}
		for(PaymentdaysSet paymentdaysSet : paymentdaysList){
			pageMap.getCondition().remove("endDate");
			pageMap.getCondition().remove("beginDate");
			String endDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getBeginday());
			pageMap.getCondition().put("endDate", endDate);
			if(paymentdaysSet.getEndday()>0){
				String beginDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getEndday());
				pageMap.getCondition().put("beginDate", beginDate);
			}
			
			BigDecimal passamount = financeFundsReturnMapper.getCustomerPastDueSumDataByDays(pageMap);
			if(null!=passamount){
				totalDataMap.put("passamount"+paymentdaysSet.getSeq(), passamount);
			}else{
				totalDataMap.put("passamount"+paymentdaysSet.getSeq(), BigDecimal.ZERO);
			}
		}
		List<Map<String,Object>> footerList = new ArrayList<Map<String,Object>>();
		footerList.add(totalDataMap);
		pageData.setFooter(footerList);
		return pageData;
	}

	@Override
	public PageData showSalesUserPastDueListPage(PageMap pageMap) throws Exception {
		SysUser sysUser = getSysUser();
		String seq = (String) pageMap.getCondition().get("seq");
		if(null!=seq && !"".equals(seq)){
			PaymentdaysSet paymentdaysSet = paymentdaysSetMapper.getPaymentdaysSetByUseridAndSeq(sysUser.getUserid(), seq,"1");
			String endDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getBeginday());
			if(paymentdaysSet.getEndday()>0){
				String beginDate = CommonUtils.getBeforeDateInDays(paymentdaysSet.getEndday());
				pageMap.getCondition().put("beginDate", beginDate);
			}
			pageMap.getCondition().put("endDate", endDate);
		}else{
			pageMap.getCondition().put("endDate", CommonUtils.getTodayDataStr());
		}
		List<Receipt> list = financeFundsReturnMapper.getSalesUserPastDueDataListByDays(pageMap);
		for(Receipt receipt : list){
			DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(receipt.getSalesdept());
			if(departMent != null){
				receipt.setSalesdept(departMent.getName());
			}
			Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(receipt.getSalesuser());
			if(personnel != null){
				receipt.setSalesuser(personnel.getName());
			}
			Customer customer = getCustomerByID(receipt.getCustomerid());
			if(customer != null){
				receipt.setCustomerid(customer.getShortname());
			}
			Contacter contacter = getContacterById(receipt.getHandlerid());
			if(contacter != null){
				receipt.setHandlerid(contacter.getName());
			}
			Map total = financeFundsReturnMapper.getReceiptDetailTotal(receipt.getId());
			if(total != null){
				if(total.containsKey("taxamount")){
					receipt.setField01(total.get("taxamount").toString());
				}
				if(total.containsKey("notaxamount")){
					receipt.setField02(total.get("notaxamount").toString());
				}
				if(total.containsKey("tax")){
					receipt.setField03(total.get("tax").toString());
				}
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.getSalesUserPastDueDataCountByDays(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public PageData showUnauditamountDataList(PageMap pageMap) throws Exception {
        if(pageMap.getCondition().containsKey("salesuser")){
            String str = (String) pageMap.getCondition().get("salesuser");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("salesuser",str);
        }
        if(pageMap.getCondition().containsKey("customerid")){
            String str = (String) pageMap.getCondition().get("customerid");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("customerid",str);
        }
        if(pageMap.getCondition().containsKey("deptid")){
            String str = (String) pageMap.getCondition().get("deptid");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("deptid",str);
        }
        if(pageMap.getCondition().containsKey("brand")){
            String str = (String) pageMap.getCondition().get("brand");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("brand",str);
        }
		List<Receipt> list = financeFundsReturnMapper.getUnauditamountDataList(pageMap);
		for(Receipt receipt : list){
			DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(receipt.getSalesdept());
			if(departMent != null){
				receipt.setSalesdept(departMent.getName());
			}
			Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(receipt.getSalesuser());
			if(personnel != null){
				receipt.setSalesuser(personnel.getName());
			}
			Customer customer = getCustomerByID(receipt.getCustomerid());
			if(customer != null){
				receipt.setCustomername(customer.getName());
			}
			Contacter contacter = getContacterById(receipt.getHandlerid());
			if(contacter != null){
				receipt.setHandlerid(contacter.getName());
			}
			Map total = financeFundsReturnMapper.getReceiptDetailTotal(receipt.getId());
			if(total != null){
				if(total.containsKey("taxamount")){
					receipt.setField01(total.get("taxamount").toString());
				}
				if(total.containsKey("notaxamount")){
					receipt.setField02(total.get("notaxamount").toString());
				}
				if(total.containsKey("tax")){
					receipt.setField03(total.get("tax").toString());
				}
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.getUnauditamountDataListCount(pageMap),list,pageMap);
		
		List<Receipt> footlist = new ArrayList<Receipt>();
		Map totalAmount = financeFundsReturnMapper.getUnauditamountReceiptDetailTotalSum(pageMap);
		if(totalAmount != null){
			Receipt totalReceipt = new Receipt();
			totalReceipt.setCustomername("合计");
			if(totalAmount.containsKey("taxamount")){
				totalReceipt.setField01(totalAmount.get("taxamount").toString());
			}
			if(totalAmount.containsKey("notaxamount")){
				totalReceipt.setField02(totalAmount.get("notaxamount").toString());
			}
			if(totalAmount.containsKey("tax")){
				totalReceipt.setField03(totalAmount.get("tax").toString());
			}
			footlist.add(totalReceipt);
		}
		pageData.setFooter(footlist);
		return pageData;
	}

	@Override
	public PageData showAuditamountDataList(PageMap pageMap) throws Exception {
        if(pageMap.getCondition().containsKey("salesuser")){
            String str = (String) pageMap.getCondition().get("salesuser");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("salesuser",str);
        }
        if(pageMap.getCondition().containsKey("customerid")){
            String str = (String) pageMap.getCondition().get("customerid");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("customerid",str);
        }
        if(pageMap.getCondition().containsKey("deptid")){
            String str = (String) pageMap.getCondition().get("deptid");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("deptid",str);
        }
        if(pageMap.getCondition().containsKey("brand")){
            String str = (String) pageMap.getCondition().get("brand");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("brand",str);
        }
        List<Receipt> list = financeFundsReturnMapper.getAuditamountDataList(pageMap);
		for(Receipt receipt : list){
			DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(receipt.getSalesdept());
			if(departMent != null){
				receipt.setSalesdept(departMent.getName());
			}
			Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(receipt.getSalesuser());
			if(personnel != null){
				receipt.setSalesuser(personnel.getName());
			}
			Customer customer = getCustomerByID(receipt.getCustomerid());
			if(customer != null){
				receipt.setCustomername(customer.getName());
			}
			Contacter contacter = getContacterById(receipt.getHandlerid());
			if(contacter != null){
				receipt.setHandlerid(contacter.getName());
			}
			Map total = financeFundsReturnMapper.getCustomerAuditamountReceiptDetailTotal(receipt.getId());
			if(total != null){
				if(total.containsKey("receipttaxamount")){
					receipt.setField01(total.get("receipttaxamount").toString());
				}
				if(total.containsKey("receiptnotaxamount")){
					receipt.setField02(total.get("receiptnotaxamount").toString());
				}
				if(null != total.get("receiptnotaxamount")){
					BigDecimal b1 = (BigDecimal)total.get("receipttaxamount");
					BigDecimal b2 = (BigDecimal)total.get("receiptnotaxamount");
					receipt.setField03(b1.subtract(b2).toString());
				}
			}
			//回单金额
			Map nowriteoff = financeFundsReturnMapper.getAuditamountReceiptDetailTotal(receipt.getId());
			if(nowriteoff != null){
				if(nowriteoff.containsKey("taxamount")){
					receipt.setField04(nowriteoff.get("taxamount").toString());
				}
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.getAuditamountDataListCount(pageMap),list,pageMap);
		
		Map  rTotalAmount = financeFundsReturnMapper.getAuditamountReceiptDetailReceipttaxamountSum(pageMap);
		Receipt totalReceipt = new Receipt();
		if(rTotalAmount != null){
			totalReceipt.setCustomername("合计");
			if(rTotalAmount.containsKey("receipttaxamount")){
				totalReceipt.setField01(rTotalAmount.get("receipttaxamount").toString());
			}
		}
		Map totalAmount = financeFundsReturnMapper.getAuditamountReceiptDetailTotalSum(pageMap);
		if(totalAmount != null){
			totalReceipt.setCustomername("合计");
			if(totalAmount.containsKey("taxamount")){
				totalReceipt.setField04(totalAmount.get("taxamount").toString());
			}
		}
		List<Receipt> footlist = new ArrayList<Receipt>();
		footlist.add(totalReceipt);
		pageData.setFooter(footlist);
		return pageData;
	}

	@Override
	public PageData showRejectamountDataList(PageMap pageMap) throws Exception {
        if(pageMap.getCondition().containsKey("salesuser")){
            String str = (String) pageMap.getCondition().get("salesuser");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("salesuser",str);
        }
        if(pageMap.getCondition().containsKey("customerid")){
            String str = (String) pageMap.getCondition().get("customerid");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("customerid",str);
        }
        if(pageMap.getCondition().containsKey("deptid")){
            String str = (String) pageMap.getCondition().get("deptid");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("deptid",str);
        }
        if(pageMap.getCondition().containsKey("brand")){
            String str = (String) pageMap.getCondition().get("brand");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("brand",str);
        }
        List<SaleRejectEnter> list = financeFundsReturnMapper.getRejectamountDataList(pageMap);
		for(SaleRejectEnter saleRejectEnter : list){
			DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(saleRejectEnter.getSalesdept());
			if(departMent != null){
				saleRejectEnter.setSalesdept(departMent.getName());
			}
			Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(saleRejectEnter.getSalesuser());
			if(personnel != null){
				saleRejectEnter.setSalesuser(personnel.getName());
			}
			Customer customer = getCustomerByID(saleRejectEnter.getCustomerid());
			if(customer != null){
				saleRejectEnter.setCustomername(customer.getName());
			}
			Contacter contacter = getContacterById(saleRejectEnter.getHandlerid());
			if(contacter != null){
				saleRejectEnter.setHandlerid(contacter.getName());
			}
			Map total = financeFundsReturnMapper.getRejectamountDetailTotal(saleRejectEnter.getId());
			if(total != null){
				if(total.containsKey("taxamount")){
					saleRejectEnter.setField01(total.get("taxamount").toString());
				}
				if(total.containsKey("notaxamount")){
					saleRejectEnter.setField02(total.get("notaxamount").toString());
				}
				if(total.containsKey("tax")){
					saleRejectEnter.setField03(total.get("tax").toString());
				}
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.getRejectamountDataListCount(pageMap),list,pageMap);
		
		List<SaleRejectEnter> footlist = new ArrayList<SaleRejectEnter>();
		Map totalAmount = financeFundsReturnMapper.getRejectamountReceiptDetailTotalSum(pageMap);
		if(totalAmount != null){
			SaleRejectEnter totalRejectEnter = new SaleRejectEnter();
			totalRejectEnter.setCustomername("合计");
			if(totalAmount.containsKey("taxamount")){
				totalRejectEnter.setField01(totalAmount.get("taxamount").toString());
			}
			if(totalAmount.containsKey("notaxamount")){
				totalRejectEnter.setField02(totalAmount.get("notaxamount").toString());
			}
			if(totalAmount.containsKey("tax")){
				totalRejectEnter.setField03(totalAmount.get("tax").toString());
			}
			footlist.add(totalRejectEnter);
		}
		pageData.setFooter(footlist);
		return pageData;
	}

	@Override
	public PageData showSalesGoodsFlowDetailDataList(PageMap pageMap)
			throws Exception {
        if(pageMap.getCondition().containsKey("salesuser")){
            String str = (String) pageMap.getCondition().get("salesuser");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("salesuser",str);
        }
        if(pageMap.getCondition().containsKey("customerid")){
            String str = (String) pageMap.getCondition().get("customerid");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("customerid",str);
        }
        if(pageMap.getCondition().containsKey("deptid")){
            String str = (String) pageMap.getCondition().get("deptid");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("deptid",str);
        }
        if(pageMap.getCondition().containsKey("brand")){
            String str = (String) pageMap.getCondition().get("brand");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("brand",str);
        }
        List<SalesGoodsFlowDetail> list = financeFundsReturnMapper.getSalesGoodsFlowDetailList(pageMap);
		for(SalesGoodsFlowDetail salesGoodsFlowDetail : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(salesGoodsFlowDetail.getGoodsid());
			if(null!=goodsInfo){
				salesGoodsFlowDetail.setGoodsname(goodsInfo.getName());
				salesGoodsFlowDetail.setUnitid(goodsInfo.getMainunit());
				MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
				if(null!=meteringUnit){
					salesGoodsFlowDetail.setUnitname(meteringUnit.getName());
				}
			}
			Customer customer = getCustomerByID(salesGoodsFlowDetail.getCustomerid());
			if(null!=customer){
				salesGoodsFlowDetail.setCustomername(customer.getName());
			}
			//单据类型
			if("1".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("发货单");
			}
			else if("2".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("直退退货单");
			}
			else if("3".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("售后退货单");
			}
			else if("4".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("冲差单");
			}
			//折扣商品
			if("1".equals(salesGoodsFlowDetail.getBilltype()) && "1".equals(salesGoodsFlowDetail.getIsdiscount())){
				salesGoodsFlowDetail.setGoodsname("品牌："+goodsInfo.getBrandName()+"，折扣");
			}else if("4".equals(salesGoodsFlowDetail.getBilltype()) && null!=salesGoodsFlowDetail.getGoodsid() && !"".equals(salesGoodsFlowDetail.getGoodsid())){
				Brand brand = getBaseFilesGoodsMapper().getBrandInfo(salesGoodsFlowDetail.getGoodsid());
				if(null!=brand){
					salesGoodsFlowDetail.setGoodsname("品牌："+brand.getName()+"，折扣");
				}
			}
			if(null != salesGoodsFlowDetail.getIsinvoice()){
				if("0".equals(salesGoodsFlowDetail.getIsinvoice())){
					salesGoodsFlowDetail.setIsinvoicename("未开票");
				}
				else if("1".equals(salesGoodsFlowDetail.getIsinvoice())){
					salesGoodsFlowDetail.setIsinvoicename("已开票");
				}
				if("1".equals(salesGoodsFlowDetail.getIswriteoff())){
					salesGoodsFlowDetail.setWriteoffname("已核销");
				}
				else if("0".equals(salesGoodsFlowDetail.getIswriteoff())){
					salesGoodsFlowDetail.setWriteoffname("未核销");
				}
			}
			else{
				salesGoodsFlowDetail.setIsinvoicename("");
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.getSalesGoodsFlowDetailListCount(pageMap),list,pageMap);
		List<SalesGoodsFlowDetail> footerList = new ArrayList<SalesGoodsFlowDetail>();
		SalesGoodsFlowDetail salesGoodsFlowDetail = new SalesGoodsFlowDetail();
		salesGoodsFlowDetail.setId("合计");
		BigDecimal totalamount = financeFundsReturnMapper.getSalesGoodsFlowDetailSum(pageMap);
		salesGoodsFlowDetail.setTaxamount(totalamount);
		footerList.add(salesGoodsFlowDetail);
		pageData.setFooter(footerList);
		return pageData;
	}

	@Override
	public PageData showSalesGoodsFlowDetailDataListByBrand(PageMap pageMap)
			throws Exception {
        if(pageMap.getCondition().containsKey("supplierid")){
            String str = (String) pageMap.getCondition().get("supplierid");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("supplierid",str);
        }
		List<SalesGoodsFlowDetail> list = financeFundsReturnMapper.getSalesGoodsFlowDetailListByBrand(pageMap);
		for(SalesGoodsFlowDetail salesGoodsFlowDetail : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(salesGoodsFlowDetail.getGoodsid());
			if(null!=goodsInfo){
				salesGoodsFlowDetail.setGoodsname(goodsInfo.getName());
				salesGoodsFlowDetail.setUnitid(goodsInfo.getMainunit());
				MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
				if(null!=meteringUnit){
					salesGoodsFlowDetail.setUnitname(meteringUnit.getName());
				}
			}
			Customer customer = getCustomerByID(salesGoodsFlowDetail.getCustomerid());
			if(null!=customer){
				salesGoodsFlowDetail.setCustomername(customer.getName());
			}
			Brand brand2 = getGoodsBrandByID(salesGoodsFlowDetail.getBrandid());
			if(null != brand2){
				salesGoodsFlowDetail.setBrandname(brand2.getName());
			}
			else{
				salesGoodsFlowDetail.setBrandname("其他");
			}
			//单据类型
			if("1".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("发货单");
			}
			else if("2".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("直退退货单");
			}
			else if("3".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("售后退货单");
			}
			else if("4".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("冲差单");
			}
			//折扣商品
			if("1".equals(salesGoodsFlowDetail.getBilltype()) && "1".equals(salesGoodsFlowDetail.getIsdiscount())){
				salesGoodsFlowDetail.setGoodsname("品牌："+goodsInfo.getBrandName()+"，折扣");
			}else if("4".equals(salesGoodsFlowDetail.getBilltype()) && null!=salesGoodsFlowDetail.getGoodsid() && !"".equals(salesGoodsFlowDetail.getGoodsid())){
				Brand brand = getBaseFilesGoodsMapper().getBrandInfo(salesGoodsFlowDetail.getGoodsid());
				if(null!=brand){
					salesGoodsFlowDetail.setGoodsname("品牌："+brand.getName()+"，折扣");
				}
			}
			if(null != salesGoodsFlowDetail.getIsinvoice()){
				if("0".equals(salesGoodsFlowDetail.getIsinvoice())){
					salesGoodsFlowDetail.setIsinvoicename("未开票");
				}
				else if("1".equals(salesGoodsFlowDetail.getIsinvoice())){
					salesGoodsFlowDetail.setIsinvoicename("已开票");
				}
				if("1".equals(salesGoodsFlowDetail.getIswriteoff())){
					salesGoodsFlowDetail.setWriteoffname("已核销");
				}
				else if("0".equals(salesGoodsFlowDetail.getIswriteoff())){
					salesGoodsFlowDetail.setWriteoffname("未核销");
				}
			}
			else{
				salesGoodsFlowDetail.setIsinvoicename("");
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.getSalesGoodsFlowDetailCountByBrand(pageMap),list,pageMap);
		List<SalesGoodsFlowDetail> footerList = new ArrayList<SalesGoodsFlowDetail>();
		SalesGoodsFlowDetail salesGoodsFlowDetail = new SalesGoodsFlowDetail();
		salesGoodsFlowDetail.setId("合计");
		BigDecimal totalamount = financeFundsReturnMapper.getSalesGoodsFlowDetailByBrandSum(pageMap);
		salesGoodsFlowDetail.setTaxamount(totalamount);
		footerList.add(salesGoodsFlowDetail);
		pageData.setFooter(footerList);
		return pageData;
	}

	@Override
	public PageData showSalesGoodsFlowDetailDataListByBrandDept(PageMap pageMap)
			throws Exception {
        if(pageMap.getCondition().containsKey("supplierid")){
            String str = (String) pageMap.getCondition().get("supplierid");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("supplierid",str);
        }
		List<SalesGoodsFlowDetail> list = financeFundsReturnMapper.getSalesGoodsFlowDetailListByBrandDept(pageMap);
		for(SalesGoodsFlowDetail salesGoodsFlowDetail : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(salesGoodsFlowDetail.getGoodsid());
			if(null!=goodsInfo){
				salesGoodsFlowDetail.setGoodsname(goodsInfo.getName());
				salesGoodsFlowDetail.setUnitid(goodsInfo.getMainunit());
				MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
				if(null!=meteringUnit){
					salesGoodsFlowDetail.setUnitname(meteringUnit.getName());
				}
			}
			Customer customer = getCustomerByID(salesGoodsFlowDetail.getCustomerid());
			if(null!=customer){
				salesGoodsFlowDetail.setCustomername(customer.getName());
			}
			Brand brand2 = getGoodsBrandByID(salesGoodsFlowDetail.getBrandid());
			if(null != brand2){
				salesGoodsFlowDetail.setBrandname(brand2.getName());
			}
			else{
				salesGoodsFlowDetail.setBrandname("其他");
			}
			DepartMent departMent = getDepartmentByDeptid(salesGoodsFlowDetail.getBranddept());
			if(null != departMent){
				salesGoodsFlowDetail.setBranddeptname(departMent.getName());
			}
			else{
				salesGoodsFlowDetail.setBranddeptname("其他");
			}
			//单据类型
			if("1".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("发货单");
			}
			else if("2".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("直退退货单");
			}
			else if("3".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("售后退货单");
			}
			else if("4".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("冲差单");
			}
			//折扣商品
			if("1".equals(salesGoodsFlowDetail.getBilltype()) && "1".equals(salesGoodsFlowDetail.getIsdiscount())){
				salesGoodsFlowDetail.setGoodsname("品牌："+goodsInfo.getBrandName()+"，折扣");
			}else if("4".equals(salesGoodsFlowDetail.getBilltype()) && null!=salesGoodsFlowDetail.getGoodsid() && !"".equals(salesGoodsFlowDetail.getGoodsid())){
				Brand brand = getBaseFilesGoodsMapper().getBrandInfo(salesGoodsFlowDetail.getGoodsid());
				if(null!=brand){
					salesGoodsFlowDetail.setGoodsname("品牌："+brand.getName()+"，折扣");
				}
			}
			if(null != salesGoodsFlowDetail.getIsinvoice()){
				if("0".equals(salesGoodsFlowDetail.getIsinvoice())){
					salesGoodsFlowDetail.setIsinvoicename("未开票");
				}
				else if("1".equals(salesGoodsFlowDetail.getIsinvoice())){
					salesGoodsFlowDetail.setIsinvoicename("已开票");
				}
				if("1".equals(salesGoodsFlowDetail.getIswriteoff())){
					salesGoodsFlowDetail.setWriteoffname("已核销");
				}
				else if("0".equals(salesGoodsFlowDetail.getIswriteoff())){
					salesGoodsFlowDetail.setWriteoffname("未核销");
				}
			}
			else{
				salesGoodsFlowDetail.setIsinvoicename("");
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.getSalesGoodsFlowDetailCountByBrandDept(pageMap),list,pageMap);
		List<SalesGoodsFlowDetail> footerList = new ArrayList<SalesGoodsFlowDetail>();
		SalesGoodsFlowDetail salesGoodsFlowDetail = new SalesGoodsFlowDetail();
		salesGoodsFlowDetail.setId("合计");
		BigDecimal totalamount = financeFundsReturnMapper.getSalesGoodsFlowDetailByBrandDeptSum(pageMap);
		salesGoodsFlowDetail.setTaxamount(totalamount);
		footerList.add(salesGoodsFlowDetail);
		pageData.setFooter(footerList);
		return pageData;
	}

	@Override
	public PageData showSalesGoodsFlowDetailDataListByBrandUser(PageMap pageMap)
			throws Exception {
        if(pageMap.getCondition().containsKey("supplierid")){
            String str = (String) pageMap.getCondition().get("supplierid");
            str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                str = "";
            }
            pageMap.getCondition().put("supplierid",str);
        }
		List<SalesGoodsFlowDetail> list = financeFundsReturnMapper.getSalesGoodsFlowDetailListByBrandUser(pageMap);
		for(SalesGoodsFlowDetail salesGoodsFlowDetail : list){
			GoodsInfo goodsInfo = getGoodsInfoByID(salesGoodsFlowDetail.getGoodsid());
			if(null!=goodsInfo){
				salesGoodsFlowDetail.setGoodsname(goodsInfo.getName());
				salesGoodsFlowDetail.setUnitid(goodsInfo.getMainunit());
				MeteringUnit meteringUnit = getMeteringUnitById(goodsInfo.getMainunit());
				if(null!=meteringUnit){
					salesGoodsFlowDetail.setUnitname(meteringUnit.getName());
				}
			}
			Customer customer = getCustomerByID(salesGoodsFlowDetail.getCustomerid());
			if(null!=customer){
				salesGoodsFlowDetail.setCustomername(customer.getName());
			}
			Personnel personnel = getPersonnelById(salesGoodsFlowDetail.getBranduser());
			if(null != personnel){
				salesGoodsFlowDetail.setBrandusername(personnel.getName());
			}
			else{
				salesGoodsFlowDetail.setBrandusername("其他");
			}
			//单据类型
			if("1".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("发货单");
			}
			else if("2".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("直退退货单");
			}
			else if("3".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("售后退货单");
			}
			else if("4".equals(salesGoodsFlowDetail.getBilltype())){
				salesGoodsFlowDetail.setBilltypename("冲差单");
			}
			//折扣商品
			if("1".equals(salesGoodsFlowDetail.getBilltype()) && "1".equals(salesGoodsFlowDetail.getIsdiscount())){
				salesGoodsFlowDetail.setGoodsname("品牌："+goodsInfo.getBrandName()+"，折扣");
			}else if("4".equals(salesGoodsFlowDetail.getBilltype()) && null!=salesGoodsFlowDetail.getGoodsid() && !"".equals(salesGoodsFlowDetail.getGoodsid())){
				Brand brand = getBaseFilesGoodsMapper().getBrandInfo(salesGoodsFlowDetail.getGoodsid());
				if(null!=brand){
					salesGoodsFlowDetail.setGoodsname("品牌："+brand.getName()+"，折扣");
				}
			}
			if(null != salesGoodsFlowDetail.getIsinvoice()){
				if("0".equals(salesGoodsFlowDetail.getIsinvoice())){
					salesGoodsFlowDetail.setIsinvoicename("未开票");
				}
				else if("1".equals(salesGoodsFlowDetail.getIsinvoice())){
					salesGoodsFlowDetail.setIsinvoicename("已开票");
				}
				if("1".equals(salesGoodsFlowDetail.getIswriteoff())){
					salesGoodsFlowDetail.setWriteoffname("已核销");
				}
				else if("0".equals(salesGoodsFlowDetail.getIswriteoff())){
					salesGoodsFlowDetail.setWriteoffname("未核销");
				}
			}
			else{
				salesGoodsFlowDetail.setIsinvoicename("");
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.getSalesGoodsFlowDetailCountByBrandUser(pageMap),list,pageMap);
		List<SalesGoodsFlowDetail> footerList = new ArrayList<SalesGoodsFlowDetail>();
		SalesGoodsFlowDetail salesGoodsFlowDetail = new SalesGoodsFlowDetail();
		salesGoodsFlowDetail.setId("合计");
		BigDecimal totalamount = financeFundsReturnMapper.getSalesGoodsFlowDetailByBrandUserSum(pageMap);
		salesGoodsFlowDetail.setTaxamount(totalamount);
		footerList.add(salesGoodsFlowDetail);
		pageData.setFooter(footerList);
		return pageData;
	}

	@Override
	public PageData showBaseSalesWithdrawnData(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		String query_sql = " 1=1 ";
		String query_sql_all = " 1=1 ";
		if(pageMap.getCondition().containsKey("goodsid")){
			String str = (String) pageMap.getCondition().get("goodsid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.goodsid = '"+str+"' ";
				query_sql_all += " and t1.goodsid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.goodsid,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t1.goodsid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("brandid")){
			String str = (String) pageMap.getCondition().get("brandid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.brandid = '"+str+"' ";
				query_sql_all += " and t1.brandid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.brandid,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t1.brandid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("branduser")){
			String str = (String) pageMap.getCondition().get("branduser");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.branduser = '"+str+"' ";
				query_sql_all += " and t1.branduser = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.branduser,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t1.branduser,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.branddept = '"+str+"' ";
				query_sql_all += " and t1.branddept = '"+str+"' ";
			}else{
				query_sql += " and FIND_IN_SET(t1.branddept,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t1.branddept,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesdept")){
			String str = (String) pageMap.getCondition().get("salesdept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.salesdept = '"+str+"' ";
				query_sql_all += " and t.salesdept = '"+str+"' ";
			}else{
				query_sql += " and FIND_IN_SET(t.salesdept,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t.salesdept,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesuser")){
			String str = (String) pageMap.getCondition().get("salesuser");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.salesuser = '"+str+"' ";
				query_sql_all += " and t.salesuser = '"+str+"' ";
			}else{
				query_sql += " and FIND_IN_SET(t.salesuser,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t.salesuser,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesarea")){
			String str = (String) pageMap.getCondition().get("salesarea");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.salesarea = '"+str+"' ";
				query_sql_all += " and t.salesarea = '"+str+"' ";
			}else{
				query_sql += " and FIND_IN_SET(t.salesarea,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t.salesarea,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("customerid")){
			String str = (String) pageMap.getCondition().get("customerid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.customerid = '"+str+"' ";
				query_sql_all += " and t.customerid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t.customerid,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t.customerid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("pcustomerid")){
			String str = (String) pageMap.getCondition().get("pcustomerid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.pcustomerid = '"+str+"' ";
				query_sql_all += " and t.pcustomerid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t.pcustomerid,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t.pcustomerid,'"+str+"')";
			}
		}  
		if(pageMap.getCondition().containsKey("businessdate1")){
			String str = (String) pageMap.getCondition().get("businessdate1");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate >= '"+str+"'";
		}
		if(pageMap.getCondition().containsKey("businessdate2")){
			String str = (String) pageMap.getCondition().get("businessdate2");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate <= '"+str+"'";
		}
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "");
			groupcols = "customerid,goodsid";
		}
		pageMap.getCondition().put("query_sql", query_sql);
		pageMap.getCondition().put("query_sql_all", query_sql_all);
		//排序
		String orderstr = "";
		if(pageMap.getCondition().containsKey("sort")){
			String sort = (String) pageMap.getCondition().get("sort");
			String order = (String) pageMap.getCondition().get("order");
			if(null==order){
				order = "asc";
			}
			orderstr = sort +" " +order;
		}
		pageMap.getCondition().put("orderstr", orderstr);
		
		List<BaseSalesWithdrawnReport> list = financeFundsReturnMapper.showBaseSalesWithdrawnData(pageMap);
		for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : list){
			if(null!=baseSalesWithdrawnReport.getSalecostamount() && null != baseSalesWithdrawnReport.getSaleamount()){
				//毛利额 = 销售总金额 - 成本金额
				baseSalesWithdrawnReport.setSalemarginamount(baseSalesWithdrawnReport.getSaleamount().subtract(baseSalesWithdrawnReport.getSalecostamount()));
				//实际毛利率 = （销售金额 - 成本金额）/销售金额*100
				if(null != baseSalesWithdrawnReport.getSaleamount() && baseSalesWithdrawnReport.getSaleamount().compareTo(BigDecimal.ZERO)!=0){
					BigDecimal realrate = baseSalesWithdrawnReport.getSaleamount().subtract(baseSalesWithdrawnReport.getSalecostamount()).divide(baseSalesWithdrawnReport.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
					realrate = realrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
					if(baseSalesWithdrawnReport.getSaleamount().compareTo(BigDecimal.ZERO)==-1){
						baseSalesWithdrawnReport.setSalerate(realrate.negate());
					}else{
						baseSalesWithdrawnReport.setSalerate(realrate);
					}
				}
				
			}else if(baseSalesWithdrawnReport.getSalecostamount().compareTo(BigDecimal.ZERO)==1){
				baseSalesWithdrawnReport.setSalerate(new BigDecimal(100).negate());
			}
			//回笼毛利额
			if(null != baseSalesWithdrawnReport.getWithdrawnamount() && null != baseSalesWithdrawnReport.getCostwriteoffamount()){
				baseSalesWithdrawnReport.setWriteoffmarginamount(baseSalesWithdrawnReport.getWithdrawnamount().subtract(baseSalesWithdrawnReport.getCostwriteoffamount()));
			}
			//回笼毛利率 = （回笼金额-回笼成本金额）/回笼金额
			if(null != baseSalesWithdrawnReport.getWithdrawnamount() && baseSalesWithdrawnReport.getWithdrawnamount().compareTo(BigDecimal.ZERO)==1){
				BigDecimal writeoffrate = baseSalesWithdrawnReport.getWithdrawnamount().subtract(baseSalesWithdrawnReport.getCostwriteoffamount()).divide(baseSalesWithdrawnReport.getWithdrawnamount(), 6, BigDecimal.ROUND_HALF_UP);
				writeoffrate = writeoffrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
				baseSalesWithdrawnReport.setWriteoffrate(writeoffrate);
			}else if(baseSalesWithdrawnReport.getWithdrawnamount().compareTo(BigDecimal.ZERO)==-1){
				baseSalesWithdrawnReport.setWriteoffrate(new BigDecimal(100).negate());
			}
			//条形码
			if(StringUtils.isNotEmpty(baseSalesWithdrawnReport.getGoodsid())){
				GoodsInfo goodsInfo = getGoodsInfoByID(baseSalesWithdrawnReport.getGoodsid());
				if(null != goodsInfo){
					baseSalesWithdrawnReport.setBarcode(goodsInfo.getBarcode());
				}
			}
			if(groupcols.indexOf("customerid")!=-1){
				Customer customer = getCustomerByID(baseSalesWithdrawnReport.getCustomerid());
				if(null!=customer){
					baseSalesWithdrawnReport.setCustomername(customer.getName());
				}
				if(groupcols.indexOf("pcustomerid")==-1){
					Customer pcustomer = getCustomerByID(baseSalesWithdrawnReport.getPcustomerid());
					if(null!=pcustomer ){
						baseSalesWithdrawnReport.setPcustomername(pcustomer.getName());
					}
				}
				if(groupcols.indexOf("salesdept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getSalesdept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setSalesdeptname(departMent.getName());
					}else{
						baseSalesWithdrawnReport.setSalesdeptname("其他(未指定销售部门)");
					}
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(baseSalesWithdrawnReport.getSalesarea());
					if(null!=salesArea){
						baseSalesWithdrawnReport.setSalesareaname(salesArea.getName());
					}else{
						baseSalesWithdrawnReport.setSalesareaname("其他（未指定销售区域）");
					}
				}
				if(groupcols.indexOf("salesuser")==-1){
					Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getSalesuser());
					if(null!=personnel){
						baseSalesWithdrawnReport.setSalesusername(personnel.getName());
					}else{
						baseSalesWithdrawnReport.setSalesusername("其他（未指定客户业务员）");
					}
				}
				Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getBranduser());
				if(null!=personnel){
					baseSalesWithdrawnReport.setBrandusername(personnel.getName());
				}else{
					baseSalesWithdrawnReport.setBrandusername("其他(未指定品牌业务员)");
				}
			}else{
				baseSalesWithdrawnReport.setCustomerid("");
				baseSalesWithdrawnReport.setCustomername("");
				if(groupcols.indexOf("salesuserid")==-1 && groupcols.indexOf("deptid")==-1){
					baseSalesWithdrawnReport.setSalesdeptname("");
				}
				
			}
			if(groupcols.indexOf("pcustomerid")!=-1){
				Customer pcustomer = getCustomerByID(baseSalesWithdrawnReport.getPcustomerid());
				if(null!=pcustomer ){
					baseSalesWithdrawnReport.setPcustomername(pcustomer.getName());
				}else{
					baseSalesWithdrawnReport.setPcustomername("其他客户总和");
				}
				if(groupcols.indexOf("customerid")==1){
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setCustomername("");
					
				}
				
			}
			if(groupcols.indexOf("salesarea")!=-1){
				SalesArea salesArea = getSalesareaByID(baseSalesWithdrawnReport.getSalesarea());
				if(null!=salesArea){
					baseSalesWithdrawnReport.setSalesareaname(salesArea.getName());
				}else{
					baseSalesWithdrawnReport.setSalesareaname("其他（未指定销售区域）");
				}
			}
			if(groupcols.indexOf("salesdept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getSalesdept());
				if(null!=departMent){
					baseSalesWithdrawnReport.setSalesdeptname(departMent.getName());
				}else{
					baseSalesWithdrawnReport.setSalesdeptname("其他（未指定销售部门）");
				}
			}
			if(groupcols.indexOf("salesuser")!=-1){
				Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getSalesuser());
				if(null!=personnel){
					baseSalesWithdrawnReport.setSalesusername(personnel.getName());
				}else{
					baseSalesWithdrawnReport.setSalesusername("其他（未指定客户业务员）");
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(baseSalesWithdrawnReport.getSalesarea());
					if(null!=salesArea){
						baseSalesWithdrawnReport.setSalesareaname(salesArea.getName());
					}
				}
				if(groupcols.indexOf("salesdept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getSalesdept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setSalesdeptname(departMent.getName());
					}else{
						baseSalesWithdrawnReport.setSalesdeptname("其他（未指定销售部门）");
					}
				}
			}
			if(groupcols.indexOf("goodsid")!=-1){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(baseSalesWithdrawnReport.getGoodsid());
				if(null!=goodsInfo){
					baseSalesWithdrawnReport.setGoodsname(goodsInfo.getName());
				}else{
					Brand brand = getGoodsBrandByID(baseSalesWithdrawnReport.getGoodsid());
					if(null!=brand){
						baseSalesWithdrawnReport.setGoodsname("（折扣）"+brand.getName());
					}else{
						baseSalesWithdrawnReport.setGoodsname("（折扣）其他");
					}
				}
				if(groupcols.indexOf("brandid")==-1){
					Brand brand = getGoodsBrandByID(baseSalesWithdrawnReport.getBrandid());
					if(null!=brand){
						baseSalesWithdrawnReport.setBrandname(brand.getName());
					}
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
					}else{
						baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
					}
				}
			}else{
				baseSalesWithdrawnReport.setGoodsid("");
			}
			if(groupcols.indexOf("brandid")!=-1){
				Brand brand = getGoodsBrandByID(baseSalesWithdrawnReport.getBrandid());
				if(null!=brand){
					baseSalesWithdrawnReport.setBrandname(brand.getName());
				}else{
					baseSalesWithdrawnReport.setBrandname("其他");
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
					}else{
						baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
					}
					Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getBranduser());
					if(null!=personnel){
						baseSalesWithdrawnReport.setBrandusername(personnel.getName());
					}else{
						baseSalesWithdrawnReport.setBrandusername("其他(未指定品牌业务员)");
					}
				}
			}
			if(groupcols.indexOf("branduser")!=-1){
				Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getBranduser());
				if(null!=personnel){
					baseSalesWithdrawnReport.setBrandusername(personnel.getName());
				}else{
					baseSalesWithdrawnReport.setBrandusername("其他(未指定品牌业务员)");
				}
				if(groupcols.indexOf("brandid")==-1){
					baseSalesWithdrawnReport.setBrandname("");
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
					}else{
						baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
					}
				}
			}
			if(groupcols.indexOf("branddept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
				if(null!=departMent){
					baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
				}else{
					baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
				}
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.showBaseSalesWithdrawnDataCount(pageMap),list,pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<BaseSalesWithdrawnReport> footer = financeFundsReturnMapper.showBaseSalesWithdrawnData(pageMap);
		for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : footer){
			if(null!=baseSalesWithdrawnReport){
				if(null!=baseSalesWithdrawnReport.getSalecostamount() && null != baseSalesWithdrawnReport.getSaleamount()){
					//毛利额 = 销售总金额 - 成本金额
					if(null != baseSalesWithdrawnReport.getSaleamount() && null != baseSalesWithdrawnReport.getSalecostamount()){
						baseSalesWithdrawnReport.setSalemarginamount(baseSalesWithdrawnReport.getSaleamount().subtract(baseSalesWithdrawnReport.getSalecostamount()));
					}
					//实际毛利率 = （销售金额 - 成本金额）/销售金额*100
					if(null != baseSalesWithdrawnReport.getSaleamount() && null != baseSalesWithdrawnReport.getSalecostamount() &&
						baseSalesWithdrawnReport.getSaleamount().compareTo(BigDecimal.ZERO)!=0){
						BigDecimal realrate = baseSalesWithdrawnReport.getSaleamount().subtract(baseSalesWithdrawnReport.getSalecostamount()).divide(baseSalesWithdrawnReport.getSaleamount(), 6, BigDecimal.ROUND_HALF_UP);
						realrate = realrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
						if(baseSalesWithdrawnReport.getSaleamount().compareTo(BigDecimal.ZERO)==-1){
							baseSalesWithdrawnReport.setSalerate(realrate.negate());
						}else{
							baseSalesWithdrawnReport.setSalerate(realrate);
						}
					}
				}else if(baseSalesWithdrawnReport.getSalecostamount().compareTo(BigDecimal.ZERO)==1){
					baseSalesWithdrawnReport.setSalerate(new BigDecimal(100).negate());
				}
				//回笼毛利额
				if(null != baseSalesWithdrawnReport.getWithdrawnamount() && null != baseSalesWithdrawnReport.getCostwriteoffamount()){
					baseSalesWithdrawnReport.setWriteoffmarginamount(baseSalesWithdrawnReport.getWithdrawnamount().subtract(baseSalesWithdrawnReport.getCostwriteoffamount()));
				}
				//回笼毛利率 = （回笼金额-回笼成本金额）/回笼金额
				if(null != baseSalesWithdrawnReport.getWithdrawnamount() && baseSalesWithdrawnReport.getWithdrawnamount().compareTo(BigDecimal.ZERO)!=0){
					BigDecimal writeoffrate = baseSalesWithdrawnReport.getWithdrawnamount().subtract(baseSalesWithdrawnReport.getCostwriteoffamount()).divide(baseSalesWithdrawnReport.getWithdrawnamount(), 6, BigDecimal.ROUND_HALF_UP);
					writeoffrate = writeoffrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
					baseSalesWithdrawnReport.setWriteoffrate(writeoffrate);
				}else if(baseSalesWithdrawnReport.getWithdrawnamount().compareTo(BigDecimal.ZERO)==-1){
					baseSalesWithdrawnReport.setWriteoffrate(new BigDecimal(100).negate());
				}
				if(groupcols.indexOf(",") == -1){
					if(groupcols.indexOf("branddept")!=-1){
						baseSalesWithdrawnReport.setBranddept("");
						baseSalesWithdrawnReport.setBranddeptname("合计");
					}
					else if(groupcols.indexOf("brandid")!=-1){
						baseSalesWithdrawnReport.setBrandid("");
						baseSalesWithdrawnReport.setBrandname("合计");
					}
					else if(groupcols.indexOf("salesdept")!=-1){
						baseSalesWithdrawnReport.setSalesdept("");
						baseSalesWithdrawnReport.setSalesdeptname("合计");
					}
					else if(groupcols.indexOf("salesuser")!=-1){
						baseSalesWithdrawnReport.setSalesuser("");
						baseSalesWithdrawnReport.setSalesusername("合计");
					}
					else if(groupcols.indexOf("branduser")!=-1){
						baseSalesWithdrawnReport.setBranduser("");
						baseSalesWithdrawnReport.setBrandusername("合计");
					}
					else if(groupcols.indexOf("goodsid")!=-1){
						baseSalesWithdrawnReport.setGoodsid("");
						baseSalesWithdrawnReport.setGoodsname("合计");
					}
					else if(groupcols.indexOf("customerid")!=-1){
						baseSalesWithdrawnReport.setCustomerid("");
						baseSalesWithdrawnReport.setCustomername("合计");
						baseSalesWithdrawnReport.setSalesdeptname("");
					}
					else if(groupcols.indexOf("salesarea")!=-1){
						baseSalesWithdrawnReport.setSalesarea("");
						baseSalesWithdrawnReport.setSalesareaname("合计");
					}
				}else{
					String[] groupArr = groupcols.split(",");
					for(String group : groupArr){
						if(group.indexOf("branddept")!=-1){
							baseSalesWithdrawnReport.setBranddeptname("合计");
							baseSalesWithdrawnReport.setBranddept("");
							baseSalesWithdrawnReport.setBrandid("");
							baseSalesWithdrawnReport.setSalesdept("");
							baseSalesWithdrawnReport.setSalesuser("");
							baseSalesWithdrawnReport.setBranduser("");
							baseSalesWithdrawnReport.setGoodsid("");
							baseSalesWithdrawnReport.setCustomerid("");
							baseSalesWithdrawnReport.setBrandname("");
							baseSalesWithdrawnReport.setSalesdeptname("");
							baseSalesWithdrawnReport.setSalesusername("");
							baseSalesWithdrawnReport.setBrandusername("");
							baseSalesWithdrawnReport.setGoodsname("");
							baseSalesWithdrawnReport.setCustomername("");
						}
						else if(group.indexOf("brandid")!=-1){
							baseSalesWithdrawnReport.setBrandname("合计");
							baseSalesWithdrawnReport.setBranddept("");
							baseSalesWithdrawnReport.setBrandid("");
							baseSalesWithdrawnReport.setSalesdept("");
							baseSalesWithdrawnReport.setSalesuser("");
							baseSalesWithdrawnReport.setBranduser("");
							baseSalesWithdrawnReport.setGoodsid("");
							baseSalesWithdrawnReport.setCustomerid("");
							baseSalesWithdrawnReport.setBranddeptname("");
							baseSalesWithdrawnReport.setSalesdeptname("");
							baseSalesWithdrawnReport.setSalesusername("");
							baseSalesWithdrawnReport.setBrandusername("");
							baseSalesWithdrawnReport.setGoodsname("");
							baseSalesWithdrawnReport.setCustomername("");
						}
						else if(group.indexOf("salesdept")!=-1){
							baseSalesWithdrawnReport.setSalesdeptname("合计");
							baseSalesWithdrawnReport.setBranddept("");
							baseSalesWithdrawnReport.setBrandid("");
							baseSalesWithdrawnReport.setSalesdept("");
							baseSalesWithdrawnReport.setSalesuser("");
							baseSalesWithdrawnReport.setBranduser("");
							baseSalesWithdrawnReport.setGoodsid("");
							baseSalesWithdrawnReport.setCustomerid("");
							baseSalesWithdrawnReport.setBrandname("");
							baseSalesWithdrawnReport.setBranddeptname("");
							baseSalesWithdrawnReport.setSalesusername("");
							baseSalesWithdrawnReport.setBrandusername("");
							baseSalesWithdrawnReport.setGoodsname("");
							baseSalesWithdrawnReport.setCustomername("");
						}
						else if(group.indexOf("salesuser")!=-1){
							baseSalesWithdrawnReport.setSalesusername("合计");
							baseSalesWithdrawnReport.setBranddept("");
							baseSalesWithdrawnReport.setBrandid("");
							baseSalesWithdrawnReport.setSalesdept("");
							baseSalesWithdrawnReport.setSalesuser("");
							baseSalesWithdrawnReport.setBranduser("");
							baseSalesWithdrawnReport.setGoodsid("");
							baseSalesWithdrawnReport.setCustomerid("");
							baseSalesWithdrawnReport.setBrandname("");
							baseSalesWithdrawnReport.setSalesdeptname("");
							baseSalesWithdrawnReport.setBranddeptname("");
							baseSalesWithdrawnReport.setBrandusername("");
							baseSalesWithdrawnReport.setGoodsname("");
							baseSalesWithdrawnReport.setCustomername("");
						}
						else if(group.indexOf("branduser")!=-1){
							baseSalesWithdrawnReport.setBrandusername("合计");
							baseSalesWithdrawnReport.setBranddept("");
							baseSalesWithdrawnReport.setBrandid("");
							baseSalesWithdrawnReport.setSalesdept("");
							baseSalesWithdrawnReport.setSalesuser("");
							baseSalesWithdrawnReport.setBranduser("");
							baseSalesWithdrawnReport.setGoodsid("");
							baseSalesWithdrawnReport.setCustomerid("");
							baseSalesWithdrawnReport.setBrandname("");
							baseSalesWithdrawnReport.setSalesdeptname("");
							baseSalesWithdrawnReport.setSalesusername("");
							baseSalesWithdrawnReport.setBranddeptname("");
							baseSalesWithdrawnReport.setGoodsname("");
							baseSalesWithdrawnReport.setCustomername("");
						}
						else if(group.indexOf("goodsid")!=-1){
							baseSalesWithdrawnReport.setGoodsname("合计");
							baseSalesWithdrawnReport.setBranddept("");
							baseSalesWithdrawnReport.setBrandid("");
							baseSalesWithdrawnReport.setSalesdept("");
							baseSalesWithdrawnReport.setSalesuser("");
							baseSalesWithdrawnReport.setBranduser("");
							baseSalesWithdrawnReport.setGoodsid("");
							baseSalesWithdrawnReport.setCustomerid("");
							baseSalesWithdrawnReport.setBrandname("");
							baseSalesWithdrawnReport.setSalesdeptname("");
							baseSalesWithdrawnReport.setSalesusername("");
							baseSalesWithdrawnReport.setBrandusername("");
							baseSalesWithdrawnReport.setBranddeptname("");
							baseSalesWithdrawnReport.setCustomername("");
						}
						else if(group.indexOf("customerid")!=-1){
							baseSalesWithdrawnReport.setCustomername("合计");
							baseSalesWithdrawnReport.setBranddept("");
							baseSalesWithdrawnReport.setBrandid("");
							baseSalesWithdrawnReport.setSalesdept("");
							baseSalesWithdrawnReport.setSalesuser("");
							baseSalesWithdrawnReport.setBranduser("");
							baseSalesWithdrawnReport.setGoodsid("");
							baseSalesWithdrawnReport.setCustomerid("");
							baseSalesWithdrawnReport.setBrandname("");
							baseSalesWithdrawnReport.setSalesdeptname("");
							baseSalesWithdrawnReport.setSalesusername("");
							baseSalesWithdrawnReport.setBrandusername("");
							baseSalesWithdrawnReport.setGoodsname("");
							baseSalesWithdrawnReport.setBranddeptname("");
						}
					}
				}
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData showBaseFinanceDrawnData(PageMap pageMap)throws Exception {
		String type = StringUtils.isNotEmpty((String) pageMap.getCondition().get("type")) ? (String)pageMap.getCondition().get("type") : "";
		String dataSql = "";
		if("branduserdept".equals(type)){
			dataSql = getDataAccessRule("t_report_branduser_dept", "z");
		}else{
			dataSql = getDataAccessRule("t_report_sales_base", "z");
		}
		pageMap.setDataSql(dataSql);
		String query_sql = " 1=1 ";
		Map map2 = pageMap.getCondition();
		if(map2.containsKey("branduser") && "0000".equals(map2.get("branduser"))){
			map2.put("branduser", "null");
		}
		if(map2.containsKey("supplieruser") && "0000".equals(map2.get("supplieruser"))){
			map2.put("supplieruser", "null");
		}
		if(map2.containsKey("branddept") && "0000".equals(map2.get("branddept"))){
			map2.put("branddept", "null");
		}
		if(map2.containsKey("salesdept") && "0000".equals(map2.get("salesdept"))){
			map2.put("salesdept", "null");
		}
		if(map2.containsKey("salesuser") && "0000".equals(map2.get("salesuser"))){
			map2.put("salesuser", "null");
		}

		if("branduserdept".equals(type)){
			if(pageMap.getCondition().containsKey("branduserdept")){
				String str = (String) pageMap.getCondition().get("branduserdept");
				str = StringEscapeUtils.escapeSql(str);
				if(str.indexOf(",") == -1){
					query_sql += " and p.belongdeptid = '"+str+"' ";
				}
				else{
					query_sql += " and FIND_IN_SET(p.belongdeptid,'"+str+"')";
				}
			}
		}
		if(pageMap.getCondition().containsKey("goodsid")){
			String str = (String) pageMap.getCondition().get("goodsid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.goodsid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.goodsid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("supplierid")){
			String str = (String) pageMap.getCondition().get("supplierid");
			str = StringEscapeUtils.escapeSql(str);
			if("null".equals(str)){
				query_sql += " and t1.supplierid = '' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.supplierid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("brandid")){
			String str = (String) pageMap.getCondition().get("brandid");
			str = StringEscapeUtils.escapeSql(str);
			if("null".equals(str)){
				query_sql += " and t1.brandid = '' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.brandid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("branduser")){
			String str = (String) pageMap.getCondition().get("branduser");
			str = StringEscapeUtils.escapeSql(str);
			if("null".equals(str)){
				query_sql += " and t1.branduser = '' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.branduser,'"+str+"')";
			}
		}
        if(pageMap.getCondition().containsKey("supplieruser")){
			String str = (String) pageMap.getCondition().get("supplieruser");
			str = StringEscapeUtils.escapeSql(str);
			if("null".equals(str)){
				query_sql += " and t1.supplieruser = '' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.supplieruser,'"+str+"')";
			}
        }
		if(pageMap.getCondition().containsKey("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				if("null".equals(str)){
					query_sql += " and t1.branddept = '' ";
				}else{
					query_sql += " and t1.branddept like '"+str+"%' ";
				}
			}else{
				String retStr = "";
				String[] branddeptArr = str.split(",");
				for(String branddept : branddeptArr){
					Map map = new HashMap();
					map.put("deptid", branddept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if(dList.size() != 0){
						for(DepartMent departMent : dList){
							if(StringUtils.isNotEmpty(retStr)){
								retStr += "," + departMent.getId();
							}else{
								retStr = departMent.getId();
							}
						}
					}
				}
				query_sql += " and FIND_IN_SET(t1.branddept,'"+retStr+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesdept")){
			String str = (String) pageMap.getCondition().get("salesdept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				if("null".equals(str)){
					query_sql += " and t.salesdept = '' ";
				}else{
					query_sql += " and t.salesdept = '"+str+"' ";
				}
			}else{
				String retStr = "";
				String[] salesdeptArr = str.split(",");
				for(String salesdept : salesdeptArr){
					Map map = new HashMap();
					map.put("deptid", salesdept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if(dList.size() != 0){
						for(DepartMent departMent : dList){
							if(StringUtils.isNotEmpty(retStr)){
								retStr += "," + departMent.getId();
							}else{
								retStr = departMent.getId();
							}
						}
					}
				}
				query_sql += " and FIND_IN_SET(t.salesdept,'"+retStr+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesuser")){
			String str = (String) pageMap.getCondition().get("salesuser");
			str = StringEscapeUtils.escapeSql(str);
			if("null".equals(str)){
				query_sql += " and t.salesuser = '' ";
			}else{
				query_sql += " and FIND_IN_SET(t.salesuser,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesarea")){
			String str = (String) pageMap.getCondition().get("salesarea");
			str = StringEscapeUtils.escapeSql(str);
			if("null".equals(str)){
				query_sql += " and t.salesarea = '' ";
			}else{
				query_sql += " and FIND_IN_SET(t.salesarea,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("customerid")){
			String str = (String) pageMap.getCondition().get("customerid");
			str = StringEscapeUtils.escapeSql(str);
			if("null".equals(str)){
				query_sql += " and t.customerid = '' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t.customerid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("pcustomerid")){
			String str = (String) pageMap.getCondition().get("pcustomerid");
			str = StringEscapeUtils.escapeSql(str);
			if("null".equals(str)){
				query_sql += " and t.pcustomerid = '' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t.pcustomerid,'"+str+"')";
			}
		}  
		if(pageMap.getCondition().containsKey("businessdate1")){
			String str = (String) pageMap.getCondition().get("businessdate1");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate >= '"+str+"'";
		}
		if(pageMap.getCondition().containsKey("businessdate2")){
			String str = (String) pageMap.getCondition().get("businessdate2");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate <= '"+str+"'";
		}
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "");
			groupcols = "customerid,goodsid";
		}
		pageMap.getCondition().put("query_sql", query_sql);
		//排序
		String orderstr = "";
		if(pageMap.getCondition().containsKey("sort")){
			String sort = (String) pageMap.getCondition().get("sort");
			String order = (String) pageMap.getCondition().get("order");
			if(null==order){
				order = "asc";
			}
			orderstr = sort +" " +order;
		}
		pageMap.getCondition().put("orderstr", orderstr);
		
		List<BaseSalesWithdrawnReport> list = financeFundsReturnMapper.showBaseFinanceDrawnData(pageMap);
		for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : list){
			if(null != baseSalesWithdrawnReport){
				//回笼毛利额
				BigDecimal withdrawnamount = null != baseSalesWithdrawnReport.getWithdrawnamount() ? baseSalesWithdrawnReport.getWithdrawnamount() : BigDecimal.ZERO;
				BigDecimal costwriteoffamount = null != baseSalesWithdrawnReport.getCostwriteoffamount() ? baseSalesWithdrawnReport.getCostwriteoffamount() : BigDecimal.ZERO;
//				baseSalesWithdrawnReport.setWriteoffmarginamount(withdrawnamount.subtract(costwriteoffamount));
				//回笼毛利率 = （回笼金额-回笼成本金额）/回笼金额
				if(withdrawnamount.compareTo(BigDecimal.ZERO)==1){
					BigDecimal writeoffrate = withdrawnamount.subtract(costwriteoffamount).divide(withdrawnamount, 6, BigDecimal.ROUND_HALF_UP);
					writeoffrate = writeoffrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
					baseSalesWithdrawnReport.setWriteoffrate(writeoffrate);
				}else if(withdrawnamount.compareTo(BigDecimal.ZERO)==-1){
					baseSalesWithdrawnReport.setWriteoffrate(new BigDecimal(100).negate());
				}
				//条形码
				if(StringUtils.isNotEmpty(baseSalesWithdrawnReport.getGoodsid())){
					GoodsInfo goodsInfo = getGoodsInfoByID(baseSalesWithdrawnReport.getGoodsid());
					if(null != goodsInfo){
						baseSalesWithdrawnReport.setBarcode(goodsInfo.getBarcode());
					}
				}
				if(groupcols.indexOf("customerid")!=-1){
					Customer customer = getCustomerByID(baseSalesWithdrawnReport.getCustomerid());
					if(null!=customer){
						baseSalesWithdrawnReport.setCustomername(customer.getName());
					}
					if(groupcols.indexOf("pcustomerid")==-1){
						Customer pcustomer = getCustomerByID(baseSalesWithdrawnReport.getPcustomerid());
						if(null!=pcustomer ){
							baseSalesWithdrawnReport.setPcustomername(pcustomer.getName());
						}
					}
                    if(groupcols.indexOf("supplierid")==-1){
                        BuySupplier buySupplier = getSupplierInfoById(baseSalesWithdrawnReport.getSupplierid());
                        if(null!=buySupplier ){
                            baseSalesWithdrawnReport.setSuppliername(buySupplier.getName());
                        }
                    }
					if(groupcols.indexOf("salesdept")==-1){
						DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getSalesdept());
						if(null!=departMent){
							baseSalesWithdrawnReport.setSalesdeptname(departMent.getName());
						}else{
							baseSalesWithdrawnReport.setSalesdeptname("其他(未指定销售部门)");
						}
					}
					if(groupcols.indexOf("salesarea")==-1){
						SalesArea salesArea = getSalesareaByID(baseSalesWithdrawnReport.getSalesarea());
						if(null!=salesArea){
							baseSalesWithdrawnReport.setSalesareaname(salesArea.getName());
						}else{
							baseSalesWithdrawnReport.setSalesareaname("其他（未指定销售区域）");
						}
					}
					if(groupcols.indexOf("salesuser")==-1){
						Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getSalesuser());
						if(null!=personnel){
							baseSalesWithdrawnReport.setSalesusername(personnel.getName());
						}else{
							baseSalesWithdrawnReport.setSalesusername("其他（未指定客户业务员）");
						}
					}
					Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getBranduser());
					if(null!=personnel){
						baseSalesWithdrawnReport.setBrandusername(personnel.getName());
					}else{
						baseSalesWithdrawnReport.setBrandusername("其他(未指定品牌业务员)");
					}
				}else{
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setCustomername("");
					if(groupcols.indexOf("salesuser")==-1 && groupcols.indexOf("salesdept")==-1){
						baseSalesWithdrawnReport.setSalesdeptname("");
					}

				}
				if(groupcols.indexOf("pcustomerid")!=-1){
					Customer pcustomer = getCustomerByID(baseSalesWithdrawnReport.getPcustomerid());
					if(null!=pcustomer ){
						baseSalesWithdrawnReport.setPcustomername(pcustomer.getName());
					}else{
						baseSalesWithdrawnReport.setPcustomername("其他客户总和");
					}
					if(groupcols.indexOf("customerid")==1){
						baseSalesWithdrawnReport.setCustomerid("");
						baseSalesWithdrawnReport.setCustomername("");

					}

				}
				if(groupcols.indexOf("salesarea")!=-1){
					SalesArea salesArea = getSalesareaByID(baseSalesWithdrawnReport.getSalesarea());
					if(null!=salesArea){
						baseSalesWithdrawnReport.setSalesareaname(salesArea.getName());
					}else{
						baseSalesWithdrawnReport.setSalesareaname("其他（未指定销售区域）");
					}
				}
				if(groupcols.indexOf("supplierid")!=-1){
					BuySupplier buySupplier = getSupplierInfoById(baseSalesWithdrawnReport.getSupplierid());
					if(null!=buySupplier){
						baseSalesWithdrawnReport.setSuppliername(buySupplier.getName());
					}else{
						if("QC".equals(baseSalesWithdrawnReport.getSupplierid())){
							baseSalesWithdrawnReport.setSuppliername("应收款期初");
						}else{
							baseSalesWithdrawnReport.setSuppliername("其他（未指定供应商）");
						}
					}
					//品牌部门
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
					if(null != departMent){
						baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
					}
				}
				if(groupcols.indexOf("salesdept")!=-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getSalesdept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setSalesdeptname(departMent.getName());
					}else{
						baseSalesWithdrawnReport.setSalesdeptname("其他（未指定销售部门）");
					}
				}
				if(groupcols.indexOf("salesuser")!=-1){
					Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getSalesuser());
					if(null!=personnel){
						baseSalesWithdrawnReport.setSalesusername(personnel.getName());
					}else{
						baseSalesWithdrawnReport.setSalesusername("其他（未指定客户业务员）");
					}
					if(groupcols.indexOf("salesarea")==-1){
						SalesArea salesArea = getSalesareaByID(baseSalesWithdrawnReport.getSalesarea());
						if(null!=salesArea){
							baseSalesWithdrawnReport.setSalesareaname(salesArea.getName());
						}
					}
					if(groupcols.indexOf("salesdept")==-1){
						DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getSalesdept());
						if(null!=departMent){
							baseSalesWithdrawnReport.setSalesdeptname(departMent.getName());
						}else{
							baseSalesWithdrawnReport.setSalesdeptname("其他（未指定销售部门）");
						}
					}
				}
				if(groupcols.indexOf("goodsid")!=-1){
					GoodsInfo goodsInfo = getAllGoodsInfoByID(baseSalesWithdrawnReport.getGoodsid());
					if(null!=goodsInfo){
						baseSalesWithdrawnReport.setGoodsname(goodsInfo.getName());

						String auxunitid = "";
						MeteringUnit auxunit = getGoodsDefaulAuxunit(baseSalesWithdrawnReport.getGoodsid());
						if(null != auxunit){
							auxunitid = auxunit.getId();
						}
						Map map = countGoodsInfoNumber(baseSalesWithdrawnReport.getGoodsid(),auxunitid,baseSalesWithdrawnReport.getUnitnum());
						BigDecimal auxnum = (BigDecimal)map.get("auxnum");
						String auxnumdetail = (String)map.get("auxnumdetail");
						baseSalesWithdrawnReport.setAuxnum(auxnum);
						baseSalesWithdrawnReport.setAuxnumdetail(auxnumdetail);
					}else{
						Brand brand = getGoodsBrandByID(baseSalesWithdrawnReport.getGoodsid());
						if(null!=brand){
							baseSalesWithdrawnReport.setGoodsname("（折扣）"+brand.getName());
						}else{
							if("QC".equals(baseSalesWithdrawnReport.getGoodsid())){
								baseSalesWithdrawnReport.setGoodsname("应收款期初");
							}else{
								baseSalesWithdrawnReport.setGoodsname("（折扣）其他");
							}
						}
					}
					if(groupcols.indexOf("brandid")==-1){
						Brand brand = getGoodsBrandByID(baseSalesWithdrawnReport.getBrandid());
						if(null!=brand){
							baseSalesWithdrawnReport.setBrandname(brand.getName());
						}
					}
					if(groupcols.indexOf("goodssort")==-1){
						if (StringUtils.isNotEmpty(baseSalesWithdrawnReport.getGoodssort())) {
							WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(baseSalesWithdrawnReport.getGoodssort());
							if (null != waresClass) {
								baseSalesWithdrawnReport.setGoodssortname(waresClass.getThisname());
							} else {
								baseSalesWithdrawnReport.setGoodssortname("其他未定义");
							}
						} else {
							baseSalesWithdrawnReport.setGoodssort("nodata");
							baseSalesWithdrawnReport.setGoodssortname("其他未指定");
						}
					}
					if(groupcols.indexOf("branddept")==-1){
						DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
						if(null!=departMent){
							baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
						}else{
							baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
						}
					}
				}else{
					baseSalesWithdrawnReport.setGoodsid("");
				}
				if(groupcols.indexOf("brandid")!=-1){
					Brand brand = getGoodsBrandByID(baseSalesWithdrawnReport.getBrandid());
					if(null!=brand){
						baseSalesWithdrawnReport.setBrandname(brand.getName());
					}else{
						if("QC".equals(baseSalesWithdrawnReport.getBrandid())){
							baseSalesWithdrawnReport.setBrandname("应收款期初");
						}else{
							baseSalesWithdrawnReport.setBrandname("其他");
						}
					}
					if(groupcols.indexOf("branddept")==-1){
						DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
						if(null!=departMent){
							baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
						}else{
							baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
						}
						Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getBranduser());
						if(null!=personnel){
							baseSalesWithdrawnReport.setBrandusername(personnel.getName());
						}else{
							baseSalesWithdrawnReport.setBrandusername("其他(未指定品牌业务员)");
						}
					}
				}
				if(groupcols.indexOf("goodssort")!=-1){
					if (StringUtils.isNotEmpty(baseSalesWithdrawnReport.getGoodssort())) {
						WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(baseSalesWithdrawnReport.getGoodssort());
						if (null != waresClass) {
							baseSalesWithdrawnReport.setGoodssortname(waresClass.getThisname());
						} else {
							baseSalesWithdrawnReport.setGoodssortname("其他未定义");
						}
					} else {
						if("QC".equals(baseSalesWithdrawnReport.getGoodssort())){
							baseSalesWithdrawnReport.setGoodssortname("应收款期初");
						}else{
							baseSalesWithdrawnReport.setGoodssortname("其他");
						}
					}
				}
				if(groupcols.indexOf("branduser")!=-1){
					Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getBranduser());
					if(null!=personnel){
						baseSalesWithdrawnReport.setBrandusername(personnel.getName());
					}else{
						if("QC".equals(baseSalesWithdrawnReport.getBranduser())){
							baseSalesWithdrawnReport.setBrandusername("应收款期初");
						}else{
							baseSalesWithdrawnReport.setBrandusername("其他(未指定品牌业务员)");
						}
					}
					if(groupcols.indexOf("brandid")==-1){
						baseSalesWithdrawnReport.setBrandname("");
					}
					if(groupcols.indexOf("branddept")==-1){
						DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
						if(null!=departMent){
							baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
						}else{
							baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
						}
					}
					if(groupcols.indexOf("branduserdept") == -1){
						DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
						if(null!=departMent){
							baseSalesWithdrawnReport.setBranduserdeptname(departMent.getName());
						}else{
							baseSalesWithdrawnReport.setBranduserdeptname("其他（未指定人员部门）");
						}
					}
				}
				if(groupcols.indexOf("supplieruser")!=-1){
					Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getSupplieruser());
					if(null!=personnel){
						baseSalesWithdrawnReport.setSupplierusername(personnel.getName());
					}else{
						if("QC".equals(baseSalesWithdrawnReport.getSupplieruser())){
							baseSalesWithdrawnReport.setSupplierusername("应收款期初");
						}else{
							baseSalesWithdrawnReport.setSupplierusername("其他(未指定厂家业务员)");
						}
					}
				}
				if(groupcols.indexOf("branddept")!=-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
					}else{
						if("QC".equals(baseSalesWithdrawnReport.getBranddept())){
							baseSalesWithdrawnReport.setBranddeptname("应收款期初");
						}else{
							baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
						}
					}
				}
				//人员部门
				if(groupcols.indexOf("branduserdept")!=-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranduserdept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setBranduserdeptname(departMent.getName());
					}else{
						if("QC".equals(baseSalesWithdrawnReport.getBranduserdept())){
							baseSalesWithdrawnReport.setBranduserdeptname("应收款期初");
						}else{
							baseSalesWithdrawnReport.setBranduserdeptname("其他（未指定人员部门）");
						}
					}
				}
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.showBaseFinanceDrawnDataCount(pageMap),list,pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<BaseSalesWithdrawnReport> footer = financeFundsReturnMapper.showBaseFinanceDrawnData(pageMap);
		for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : footer){
			if(null!=baseSalesWithdrawnReport){
				//回笼毛利额
				BigDecimal withdrawnamount = null != baseSalesWithdrawnReport.getWithdrawnamount() ? baseSalesWithdrawnReport.getWithdrawnamount() : BigDecimal.ZERO;
				BigDecimal costwriteoffamount = null != baseSalesWithdrawnReport.getCostwriteoffamount() ? baseSalesWithdrawnReport.getCostwriteoffamount() : BigDecimal.ZERO;
				baseSalesWithdrawnReport.setWriteoffmarginamount(withdrawnamount.subtract(costwriteoffamount));
				//回笼毛利率 = （回笼金额-回笼成本金额）/回笼金额
				if(withdrawnamount.compareTo(BigDecimal.ZERO)==1){
					BigDecimal writeoffrate = withdrawnamount.subtract(costwriteoffamount).divide(withdrawnamount, 6, BigDecimal.ROUND_HALF_UP);
					writeoffrate = writeoffrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
					baseSalesWithdrawnReport.setWriteoffrate(writeoffrate);
				}else if(withdrawnamount.compareTo(BigDecimal.ZERO)==-1){
					baseSalesWithdrawnReport.setWriteoffrate(new BigDecimal(100).negate());
				}

				String[] groupArr = groupcols.split(",");
				if(groupArr[0].indexOf("branddept")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBranddeptname("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
					baseSalesWithdrawnReport.setGoodssort("");
					baseSalesWithdrawnReport.setGoodssortname("");
				}
				else if(groupArr[0].indexOf("brandid")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBrandname("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
					baseSalesWithdrawnReport.setGoodssort("");
					baseSalesWithdrawnReport.setGoodssortname("");
				}
				else if(groupArr[0].indexOf("salesdept")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setSalesdeptname("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
					baseSalesWithdrawnReport.setGoodssort("");
					baseSalesWithdrawnReport.setGoodssortname("");
				}
				else if(groupArr[0].indexOf("salesuser")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setSalesusername("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
					baseSalesWithdrawnReport.setGoodssort("");
					baseSalesWithdrawnReport.setGoodssortname("");
				}
				else if(groupArr[0].indexOf("branduser")!=-1 && !groupArr[0].equals("branduserdept")){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBrandusername("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
					baseSalesWithdrawnReport.setGoodssort("");
					baseSalesWithdrawnReport.setGoodssortname("");
				}
				else if(groupArr[0].indexOf("goodsid")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setGoodsname("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
					baseSalesWithdrawnReport.setGoodssort("");
					baseSalesWithdrawnReport.setGoodssortname("");
				}
				else if(groupArr[0].indexOf("customerid")!=-1 && groupArr[0].equals("customerid")){
					baseSalesWithdrawnReport.setCustomername("合计");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
					baseSalesWithdrawnReport.setGoodssort("");
					baseSalesWithdrawnReport.setGoodssortname("");
				}else if(groupArr[0].indexOf("pcustomerid") !=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
					baseSalesWithdrawnReport.setGoodssort("");
					baseSalesWithdrawnReport.setGoodssortname("");
				}else if(groupArr[0].indexOf("supplierid")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("合计");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
					baseSalesWithdrawnReport.setGoodssort("");
					baseSalesWithdrawnReport.setGoodssortname("");
				}else if(groupArr[0].indexOf("supplieruser")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("合计");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
					baseSalesWithdrawnReport.setGoodssort("");
					baseSalesWithdrawnReport.setGoodssortname("");
				}else if(groupArr[0].indexOf("branduserdept")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("合计");
					baseSalesWithdrawnReport.setGoodssort("");
					baseSalesWithdrawnReport.setGoodssortname("");
				}else if(groupArr[0].indexOf("goodssort")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
					baseSalesWithdrawnReport.setGoodssort("");
					baseSalesWithdrawnReport.setGoodssortname("合计");
				}
			}else{
				 footer = new ArrayList<BaseSalesWithdrawnReport>();
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}
	
	@Override
	public Map getCompanyWithdrawnList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		String query_sql = " 1=1 ";
		if(pageMap.getCondition().containsKey("businessdate1")){
			String str = (String) pageMap.getCondition().get("businessdate1");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate >= '"+str+"'";
		}
		if(pageMap.getCondition().containsKey("businessdate2")){
			String str = (String) pageMap.getCondition().get("businessdate2");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate <= '"+str+"'";
		}
        if(pageMap.getCondition().containsKey("salesuser")){
            String str = (String) pageMap.getCondition().get("salesuser");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t.salesuser = '"+str+"' ";
            }else{
                query_sql += " and FIND_IN_SET(t.salesuser,'"+str+"')";
            }
        }
		//小计列
		pageMap.getCondition().put("groupcols", "branddept");
		pageMap.getCondition().put("query_sql", query_sql);
		//排序
		String orderstr = "";
		if(pageMap.getCondition().containsKey("sort")){
			String sort = (String) pageMap.getCondition().get("sort");
			String order = (String) pageMap.getCondition().get("order");
			if(null==order){
				order = "asc";
			}
			orderstr = sort +" " +order;
		}
		pageMap.getCondition().put("orderstr", orderstr);
		pageMap.getCondition().put("isflag", "true");
		
		//分公司单资金回笼情况列表
		List<BaseSalesWithdrawnReport> compnyList = new ArrayList<BaseSalesWithdrawnReport>();
		List<BaseSalesWithdrawnReport> footlist = new ArrayList<BaseSalesWithdrawnReport>();
		Map<String,List<BaseSalesWithdrawnReport>> map3 = new LinkedHashMap<String, List<BaseSalesWithdrawnReport>>();
		List<BaseSalesWithdrawnReport> list = financeFundsReturnMapper.showBaseFinanceDrawnData(pageMap);
		Map map2 = new HashMap();
		map2.put("pid", "");
		List<DepartMent> deptList = getBaseDepartMentMapper().getDeptListByParam(map2);
		if(null != deptList && deptList.size() != 0){
			for(DepartMent dept: deptList){
				for(BaseSalesWithdrawnReport brandDeptReport: list){
					if(StringUtils.isNotEmpty(brandDeptReport.getBranddept())){
						//判断获取的部门数据属于该公司
						if(map3.containsKey(dept.getId())){
							if(brandDeptReport.getBranddept().indexOf(dept.getId()) == 0){
								List<BaseSalesWithdrawnReport> list1 = map3.get(dept.getId());
								list1.add(brandDeptReport);
								map3.put(dept.getId(), list1);
							}
						}else{
							if("QC".equals(brandDeptReport.getBranddept())){
								List<BaseSalesWithdrawnReport> list1 = new ArrayList<BaseSalesWithdrawnReport>();
								list1.add(brandDeptReport);
								map3.put("QC", list1);
							}else{
								if(brandDeptReport.getBranddept().indexOf(dept.getId()) == 0){
									List<BaseSalesWithdrawnReport> list1 = new ArrayList<BaseSalesWithdrawnReport>();
									list1.add(brandDeptReport);
									map3.put(dept.getId(), list1);
								}
							}
						}
					}else{
						List<BaseSalesWithdrawnReport> list1 = new ArrayList<BaseSalesWithdrawnReport>();
						list1.add(brandDeptReport);
						map3.put("0000", list1);
					}
				}
			}
		}else{
			if(list.size() > 0){
				for(BaseSalesWithdrawnReport brandDeptReport: list){
					if(StringUtils.isNotEmpty(brandDeptReport.getBranddept())){
						List<BaseSalesWithdrawnReport> list1 = new ArrayList<BaseSalesWithdrawnReport>();
						list1.add(brandDeptReport);
						map3.put(brandDeptReport.getBranddept(), list1);
					}else{
						List<BaseSalesWithdrawnReport> list1 = new ArrayList<BaseSalesWithdrawnReport>();
						list1.add(brandDeptReport);
						map3.put("0000", list1);
					}
				}
			}
		}
		
		if(null != map3 && !map3.isEmpty()){
			BaseSalesWithdrawnReport salesWithdrawnSum = new BaseSalesWithdrawnReport();
			salesWithdrawnSum.setBranddeptname("合计");
			Iterator it = map3.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				BaseSalesWithdrawnReport cmpBaseWithdrawnReport = new BaseSalesWithdrawnReport();
				Object key = (String)entry.getKey();
				List<BaseSalesWithdrawnReport> list2 = (List<BaseSalesWithdrawnReport>)entry.getValue();
				if(null != list2 && list2.size() != 0){
					String deptid = (String)entry.getKey();
					DepartMent dept = getBaseDepartMentMapper().getDepartmentInfo(deptid);
					if(null != dept){
						cmpBaseWithdrawnReport.setBranddept(dept.getId());
						cmpBaseWithdrawnReport.setBranddeptname(dept.getName());
					}else{
						if("QC".equals(deptid)){
							cmpBaseWithdrawnReport.setBranddept(deptid);
							cmpBaseWithdrawnReport.setBranddeptname("应收款期初");
						}else{
							cmpBaseWithdrawnReport.setBranddept("0000");
							cmpBaseWithdrawnReport.setBranddeptname("其他");
						}
					}
					for(BaseSalesWithdrawnReport brandDeptReport : list2){
						//回笼金额
						if(null != brandDeptReport.getWithdrawnamount()){
							cmpBaseWithdrawnReport.setWithdrawnamount(brandDeptReport.getWithdrawnamount().add(null == cmpBaseWithdrawnReport.getWithdrawnamount() ? new BigDecimal(0) : cmpBaseWithdrawnReport.getWithdrawnamount()));
						}
						//回笼成本
						if(null != brandDeptReport.getCostwriteoffamount()){
							cmpBaseWithdrawnReport.setCostwriteoffamount(brandDeptReport.getCostwriteoffamount().add(null == cmpBaseWithdrawnReport.getCostwriteoffamount() ? new BigDecimal(0) : cmpBaseWithdrawnReport.getCostwriteoffamount()));
						}
					}
					//回笼毛利额
					BigDecimal withdrawnamount = null != cmpBaseWithdrawnReport.getWithdrawnamount() ? cmpBaseWithdrawnReport.getWithdrawnamount() : BigDecimal.ZERO;
					BigDecimal costwriteoffamount = null != cmpBaseWithdrawnReport.getCostwriteoffamount() ? cmpBaseWithdrawnReport.getCostwriteoffamount() : BigDecimal.ZERO;
					BigDecimal writeoffmarginamount = withdrawnamount.subtract(costwriteoffamount);
					cmpBaseWithdrawnReport.setWriteoffmarginamount(writeoffmarginamount);
					//回笼毛利率 = （回笼金额-回笼成本金额）/回笼金额
					if(withdrawnamount.compareTo(BigDecimal.ZERO)==1){
						BigDecimal writeoffrate = withdrawnamount.subtract(costwriteoffamount).divide(withdrawnamount, 6, BigDecimal.ROUND_HALF_UP);
						writeoffrate = writeoffrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
						cmpBaseWithdrawnReport.setWriteoffrate(writeoffrate);
					}else if(withdrawnamount.compareTo(BigDecimal.ZERO)==-1){
						cmpBaseWithdrawnReport.setWriteoffrate(new BigDecimal(100).negate());
					}
					compnyList.add(cmpBaseWithdrawnReport);
				}
				//合计
				//回笼金额
				if(null != cmpBaseWithdrawnReport.getWithdrawnamount()){
					salesWithdrawnSum.setWithdrawnamount(cmpBaseWithdrawnReport.getWithdrawnamount().add(null == salesWithdrawnSum.getWithdrawnamount() ? new BigDecimal(0) : salesWithdrawnSum.getWithdrawnamount()));
				}
				//回笼成本金额
				if(null != cmpBaseWithdrawnReport.getCostwriteoffamount()){
					salesWithdrawnSum.setCostwriteoffamount(cmpBaseWithdrawnReport.getCostwriteoffamount().add(null == salesWithdrawnSum.getCostwriteoffamount() ? new BigDecimal(0) : salesWithdrawnSum.getCostwriteoffamount()));
				}
			}
			//合计
			//回笼毛利额
			BigDecimal withdrawnamountSUM = null != salesWithdrawnSum.getWithdrawnamount() ? salesWithdrawnSum.getWithdrawnamount() : BigDecimal.ZERO;
			BigDecimal costwriteoffamountSUM = null != salesWithdrawnSum.getCostwriteoffamount() ? salesWithdrawnSum.getCostwriteoffamount() : BigDecimal.ZERO;
			BigDecimal writeoffmarginamountSUM = withdrawnamountSUM.subtract(costwriteoffamountSUM);
			salesWithdrawnSum.setWriteoffmarginamount(writeoffmarginamountSUM);
			//回笼毛利率 = （回笼金额-回笼成本金额）/回笼金额
			if(withdrawnamountSUM.compareTo(BigDecimal.ZERO)==1){
				BigDecimal writeoffrate = withdrawnamountSUM.subtract(costwriteoffamountSUM).divide(withdrawnamountSUM, 6, BigDecimal.ROUND_HALF_UP);
				writeoffrate = writeoffrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
				salesWithdrawnSum.setWriteoffrate(writeoffrate);
			}else if(withdrawnamountSUM.compareTo(BigDecimal.ZERO)==-1){
				salesWithdrawnSum.setWriteoffrate(new BigDecimal(100).negate());
			}
			footlist.add(salesWithdrawnSum);
		}
		Map map = new HashMap();
		map.put("list", compnyList);
		map.put("footer", footlist);
		return map;
	}

	@Override
	public List getExportSupplierDrawnData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		if(pageMap.getCondition().containsKey("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			String retStr = "";
			String[] branddeptArr = str.split(",");
			for(String branddept : branddeptArr){
				Map map = new HashMap();
				map.put("deptid", branddept);
				List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
				if(dList.size() != 0){
					for(DepartMent departMent : dList){
						if(StringUtils.isNotEmpty(retStr)){
							retStr += "," + departMent.getId();
						}else{
							retStr = departMent.getId();
						}
					}
				}
			}
			pageMap.getCondition().put("branddept", retStr);
		}
		List<BaseSalesWithdrawnReport> list = financeFundsReturnMapper.getExportSupplierDrawnData(pageMap);
		for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : list){
			//回笼毛利额
			if(null != baseSalesWithdrawnReport.getWithdrawnamount() && null != baseSalesWithdrawnReport.getCostwriteoffamount()){
				baseSalesWithdrawnReport.setWriteoffmarginamount(baseSalesWithdrawnReport.getWithdrawnamount().subtract(baseSalesWithdrawnReport.getCostwriteoffamount()));
			}
			//回笼毛利率 = （回笼金额-回笼成本金额）/回笼金额
			if(null != baseSalesWithdrawnReport.getWithdrawnamount() && baseSalesWithdrawnReport.getWithdrawnamount().compareTo(BigDecimal.ZERO)==1
					&& null != baseSalesWithdrawnReport.getCostwriteoffamount()
			){
				BigDecimal writeoffrate = baseSalesWithdrawnReport.getWithdrawnamount().subtract(baseSalesWithdrawnReport.getCostwriteoffamount()).divide(baseSalesWithdrawnReport.getWithdrawnamount(), 6, BigDecimal.ROUND_HALF_UP);
				writeoffrate = writeoffrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
				baseSalesWithdrawnReport.setWriteoffrate(writeoffrate);
			}else if(null != baseSalesWithdrawnReport.getWithdrawnamount() && baseSalesWithdrawnReport.getWithdrawnamount().compareTo(BigDecimal.ZERO)==-1){
				baseSalesWithdrawnReport.setWriteoffrate(new BigDecimal(100).negate());
			}
			//条形码
			if(StringUtils.isNotEmpty(baseSalesWithdrawnReport.getGoodsid())){
				GoodsInfo goodsInfo = getGoodsInfoByID(baseSalesWithdrawnReport.getGoodsid());
				if(null != goodsInfo){
					baseSalesWithdrawnReport.setBarcode(goodsInfo.getBarcode());
				}
			}
			BuySupplier buySupplier = getSupplierInfoById(baseSalesWithdrawnReport.getSupplierid());
			if(null!=buySupplier){
				baseSalesWithdrawnReport.setSuppliername(buySupplier.getName());
			}else{
				baseSalesWithdrawnReport.setSuppliername("其他（未指定供应商）");
			}
			DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
			if(null != departMent){
				baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
			}
		}
		
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<BaseSalesWithdrawnReport> footer = financeFundsReturnMapper.getExportSupplierDrawnData(pageMap);
		for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : footer){
			if(null!=baseSalesWithdrawnReport){
				//回笼毛利额
				if(null != baseSalesWithdrawnReport.getWithdrawnamount() && null != baseSalesWithdrawnReport.getCostwriteoffamount()){
					baseSalesWithdrawnReport.setWriteoffmarginamount(baseSalesWithdrawnReport.getWithdrawnamount().subtract(baseSalesWithdrawnReport.getCostwriteoffamount()));
				}
				//回笼毛利率 = （回笼金额-回笼成本金额）/回笼金额
				if(null != baseSalesWithdrawnReport.getWithdrawnamount() && baseSalesWithdrawnReport.getWithdrawnamount().compareTo(BigDecimal.ZERO)!=0
						&& null != baseSalesWithdrawnReport.getCostwriteoffamount()
				){
					BigDecimal writeoffrate = baseSalesWithdrawnReport.getWithdrawnamount().subtract(baseSalesWithdrawnReport.getCostwriteoffamount()).divide(baseSalesWithdrawnReport.getWithdrawnamount(), 6, BigDecimal.ROUND_HALF_UP);
					writeoffrate = writeoffrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
					baseSalesWithdrawnReport.setWriteoffrate(writeoffrate);
				}else if(null != baseSalesWithdrawnReport.getWithdrawnamount() && baseSalesWithdrawnReport.getWithdrawnamount().compareTo(BigDecimal.ZERO)==-1){
					baseSalesWithdrawnReport.setWriteoffrate(new BigDecimal(100).negate());
				}
				baseSalesWithdrawnReport.setSupplierid("");
				baseSalesWithdrawnReport.setSuppliername("合计");
			}
		}
		list.addAll(footer);
		return list;
	}

	@Override
	public PageData showBaseFinanceReceiptData(PageMap pageMap)
			throws Exception {
		String type = StringUtils.isNotEmpty((String) pageMap.getCondition().get("type")) ? (String)pageMap.getCondition().get("type") : "";
		String dataSql = "";
		if("branduserdept".equals(type)){
			dataSql = getDataAccessRule("t_report_branduser_dept", "z");
		}else{
			dataSql = getDataAccessRule("t_report_sales_base", "z");
		}
		pageMap.setDataSql(dataSql);
		String query_sql = " 1=1 ";
		String query_sql_all = " 1=1 ";
		if("branduserdept".equals(type)){
			if(pageMap.getCondition().containsKey("branduserdept")){
				String str = (String) pageMap.getCondition().get("branduserdept");
				str = StringEscapeUtils.escapeSql(str);
				if(str.indexOf(",") == -1){
					query_sql += " and p.belongdeptid = '"+str+"' ";
					query_sql_all += " and p.belongdeptid = '"+str+"' ";
				}
				else{
					query_sql += " and FIND_IN_SET(p.belongdeptid,'"+str+"')";
					query_sql_all += " and FIND_IN_SET(p.belongdeptid,'"+str+"')";
				}
			}
		}
		if(pageMap.getCondition().containsKey("goodsid")){
			String str = (String) pageMap.getCondition().get("goodsid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.goodsid = '"+str+"' ";
				query_sql_all += " and t1.goodsid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.goodsid,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t1.goodsid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("brandid")){
			String str = (String) pageMap.getCondition().get("brandid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.brandid = '"+str+"' ";
				query_sql_all += " and t1.brandid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.brandid,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t1.brandid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("branduser")){
			String str = (String) pageMap.getCondition().get("branduser");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.branduser = '"+str+"' ";
				query_sql_all += " and t1.branduser = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.branduser,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t1.branduser,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.branddept like concat( '"+str+"','%') ";
				query_sql_all += " and t1.branddept like concat( '"+str+"','%') ";
			}else{
				String retStr = "";
				String[] branddeptArr = str.split(",");
				for(String branddept : branddeptArr){
					Map map = new HashMap();
					map.put("deptid", branddept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if(dList.size() != 0){
						for(DepartMent departMent : dList){
							if(StringUtils.isNotEmpty(retStr)){
								retStr += "," + departMent.getId();
							}else{
								retStr = departMent.getId();
							}
						}
					}
				}
				query_sql += " and FIND_IN_SET(t1.branddept,'"+retStr+"')";
				query_sql_all += " and FIND_IN_SET(t1.branddept,'"+retStr+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesdept")){
			String str = (String) pageMap.getCondition().get("salesdept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.salesdept = '"+str+"' ";
				query_sql_all += " and t.salesdept = '"+str+"' ";
			}else{
				String retStr = "";
				String[] salesdeptArr = str.split(",");
				for(String salesdept : salesdeptArr){
					Map map = new HashMap();
					map.put("deptid", salesdept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if(dList.size() != 0){
						for(DepartMent departMent : dList){
							if(StringUtils.isNotEmpty(retStr)){
								retStr += "," + departMent.getId();
							}else{
								retStr = departMent.getId();
							}
						}
					}
				}
				query_sql += " and FIND_IN_SET(t.salesdept,'"+retStr+"')";
				query_sql_all += " and FIND_IN_SET(t.salesdept,'"+retStr+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesuser")){
			String str = (String) pageMap.getCondition().get("salesuser");
			str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t.salesuser = '"+str+"' ";
                query_sql_all += " and t.salesuser = '"+str+"' ";
            }else{
                query_sql += " and FIND_IN_SET(t.salesuser,'"+str+"')";
                query_sql_all += " and FIND_IN_SET(t.salesuser,'"+str+"')";
            }
		}
		if(pageMap.getCondition().containsKey("salesarea")){
			String str = (String) pageMap.getCondition().get("salesarea");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.salesarea = '"+str+"' ";
				query_sql_all += " and t.salesarea = '"+str+"' ";
			}else{
				query_sql += " and FIND_IN_SET(t.salesarea,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t.salesarea,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("customerid")){
			String str = (String) pageMap.getCondition().get("customerid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.customerid = '"+str+"' ";
				query_sql_all += " and t.customerid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t.customerid,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t.customerid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("pcustomerid")){
			String str = (String) pageMap.getCondition().get("pcustomerid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.pcustomerid = '"+str+"' ";
				query_sql_all += " and t.pcustomerid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t.pcustomerid,'"+str+"')";
				query_sql_all += " and FIND_IN_SET(t.pcustomerid,'"+str+"')";
			}
		}
        if(pageMap.getCondition().containsKey("supplierid")){
            String str = (String) pageMap.getCondition().get("supplierid");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t1.supplierid = '"+str+"' ";
                query_sql_all += " and t1.supplierid = '"+str+"' ";
            }
            else{
                query_sql += " and FIND_IN_SET(t1.supplierid,'"+str+"')";
                query_sql_all += " and FIND_IN_SET(t1.supplierid,'"+str+"')";
            }
        }

		if(pageMap.getCondition().containsKey("businessdate1")){
			String str = (String) pageMap.getCondition().get("businessdate1");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.addtime >= '"+str+"' ";
			query_sql_all += " and t.businessdate >= '"+str+"'";
		}
		if(pageMap.getCondition().containsKey("businessdate2")){
			String str = (String) pageMap.getCondition().get("businessdate2");
			str = StringEscapeUtils.escapeSql(str);
            Date date2 = CommonUtils.stringToDate(str);
            String nextDay = CommonUtils.getNextDateByDays(date2, 1);
            pageMap.getCondition().put("nextDay",nextDay);
			query_sql += " and t.addtime <= '"+nextDay+"'";
			query_sql_all += " and t.businessdate <= '"+str+"'";
		}
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "");
			groupcols = "customerid,goodsid";
		}
		pageMap.getCondition().put("query_sql_all", query_sql_all);
		pageMap.getCondition().put("query_sql", query_sql);
		//排序
		String orderstr = "";
		if(pageMap.getCondition().containsKey("sort")){
			String sort = (String) pageMap.getCondition().get("sort");
			String order = (String) pageMap.getCondition().get("order");
			if(null==order){
				order = "asc";
			}
			orderstr = sort +" " +order;
		}
		pageMap.getCondition().put("orderstr", orderstr);

		List<BaseSalesWithdrawnReport> list = financeFundsReturnMapper.showBaseFinanceReceiptData(pageMap);
		for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : list){
			//条形码
			if(StringUtils.isNotEmpty(baseSalesWithdrawnReport.getGoodsid())){
				GoodsInfo goodsInfo = getGoodsInfoByID(baseSalesWithdrawnReport.getGoodsid());
				if(null != goodsInfo){
					baseSalesWithdrawnReport.setBarcode(goodsInfo.getBarcode());
				}
			}
            CustomerCapital customerCapital = customerCapitalMapper.getCustomerCapital(baseSalesWithdrawnReport.getCustomerid());
            if(null != customerCapital){
                baseSalesWithdrawnReport.setCustomeramount(customerCapital.getAmount());
            }
			if(groupcols.indexOf("customerid")!=-1){
				Customer customer = getCustomerByID(baseSalesWithdrawnReport.getCustomerid());
				if(null!=customer){
					baseSalesWithdrawnReport.setCustomername(customer.getName());
				}
				if(groupcols.indexOf("pcustomerid")==-1){
					Customer pcustomer = getCustomerByID(baseSalesWithdrawnReport.getPcustomerid());
					if(null!=pcustomer ){
						baseSalesWithdrawnReport.setPcustomername(pcustomer.getName());
					}
				}
				if(groupcols.indexOf("salesdept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getSalesdept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setSalesdeptname(departMent.getName());
					}else{
						baseSalesWithdrawnReport.setSalesdeptname("其他(未指定销售部门)");
					}
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(baseSalesWithdrawnReport.getSalesarea());
					if(null!=salesArea){
						baseSalesWithdrawnReport.setSalesareaname(salesArea.getName());
					}else{
						baseSalesWithdrawnReport.setSalesareaname("其他（未指定销售区域）");
					}
				}
				if(groupcols.indexOf("salesuser")==-1){
					Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getSalesuser());
					if(null!=personnel){
						baseSalesWithdrawnReport.setSalesusername(personnel.getName());
					}else{
						baseSalesWithdrawnReport.setSalesusername("其他（未指定客户业务员）");
					}
				}
				Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getBranduser());
				if(null!=personnel){
					baseSalesWithdrawnReport.setBrandusername(personnel.getName());
				}else{
					baseSalesWithdrawnReport.setBrandusername("其他(未指定品牌业务员)");
				}
			}else{
				baseSalesWithdrawnReport.setCustomerid("0");
				baseSalesWithdrawnReport.setCustomername("其他(未指定客户)");
				if(groupcols.indexOf("salesuserid")==-1 && groupcols.indexOf("deptid")==-1){
					baseSalesWithdrawnReport.setSalesdeptname("");
				}
				
			}
			if(groupcols.indexOf("pcustomerid")!=-1){
				Customer pcustomer = getCustomerByID(baseSalesWithdrawnReport.getPcustomerid());
				if(null!=pcustomer ){
					baseSalesWithdrawnReport.setPcustomername(pcustomer.getName());
				}else{
					baseSalesWithdrawnReport.setPcustomername("其他客户总和");
				}
				if(groupcols.indexOf("customerid")==1){
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setCustomername("");
					
				}
				
			}
			if(groupcols.indexOf("salesarea")!=-1){
				SalesArea salesArea = getSalesareaByID(baseSalesWithdrawnReport.getSalesarea());
				if(null!=salesArea){
					baseSalesWithdrawnReport.setSalesareaname(salesArea.getName());
				}else{
					baseSalesWithdrawnReport.setSalesareaname("其他（未指定销售区域）");
				}
			}
			if(groupcols.indexOf("salesdept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getSalesdept());
				if(null!=departMent){
					baseSalesWithdrawnReport.setSalesdeptname(departMent.getName());
				}else{
					baseSalesWithdrawnReport.setSalesdeptname("其他（未指定销售部门）");
				}
			}
			if(groupcols.indexOf("salesuser")!=-1){
				Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getSalesuser());
				if(null!=personnel){
					baseSalesWithdrawnReport.setSalesusername(personnel.getName());
				}else{
                    baseSalesWithdrawnReport.setSalesuser("0");
					baseSalesWithdrawnReport.setSalesusername("其他（未指定客户业务员）");
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(baseSalesWithdrawnReport.getSalesarea());
					if(null!=salesArea){
						baseSalesWithdrawnReport.setSalesareaname(salesArea.getName());
					}
				}
				if(groupcols.indexOf("salesdept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getSalesdept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setSalesdeptname(departMent.getName());
					}else{
						baseSalesWithdrawnReport.setSalesdeptname("其他（未指定销售部门）");
					}
				}
			}
			if(groupcols.indexOf("goodsid")!=-1){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(baseSalesWithdrawnReport.getGoodsid());
				if(null!=goodsInfo){
					baseSalesWithdrawnReport.setGoodsname(goodsInfo.getName());
                    BuySupplier buySupplier = getSupplierInfoById(baseSalesWithdrawnReport.getSupplierid());
                    if(null!=buySupplier){
                        baseSalesWithdrawnReport.setSuppliername(buySupplier.getName());
                    }else{
                        baseSalesWithdrawnReport.setSuppliername("其他（未指定供应商）");
                    }
				}else{
					Brand brand = getGoodsBrandByID(baseSalesWithdrawnReport.getGoodsid());
					if(null!=brand){
						baseSalesWithdrawnReport.setGoodsname("（折扣）"+brand.getName());
					}else{
						if("QC".equals(baseSalesWithdrawnReport.getGoodsid())){
							baseSalesWithdrawnReport.setGoodsname("应收款期初");
						}else{
							baseSalesWithdrawnReport.setGoodsname("（折扣）其他");
						}
					}
				}
				if(groupcols.indexOf("brandid")==-1){
					Brand brand = getGoodsBrandByID(baseSalesWithdrawnReport.getBrandid());
					if(null!=brand){
						baseSalesWithdrawnReport.setBrandname(brand.getName());
					}
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
					}else{
						baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
					}
				}
			}else{
				baseSalesWithdrawnReport.setGoodsid("");
			}
			if(groupcols.indexOf("brandid")!=-1){
				Brand brand = getGoodsBrandByID(baseSalesWithdrawnReport.getBrandid());
				if(null!=brand){
					baseSalesWithdrawnReport.setBrandname(brand.getName());
				}else{
					if("QC".equals(baseSalesWithdrawnReport.getBrandid())){
						baseSalesWithdrawnReport.setBrandname("应收款期初");
					}else{
						baseSalesWithdrawnReport.setBrandid("0");
						baseSalesWithdrawnReport.setBrandname("其他");
					}
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
					}else{
						baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
					}
					Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getBranduser());
					if(null!=personnel){
						baseSalesWithdrawnReport.setBrandusername(personnel.getName());
					}else{
						baseSalesWithdrawnReport.setBrandusername("其他(未指定品牌业务员)");
					}
				}
			}
			if(groupcols.indexOf("branduser")!=-1){
				Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getBranduser());
				if(null!=personnel){
					baseSalesWithdrawnReport.setBrandusername(personnel.getName());
				}else{
					if("QC".equals(baseSalesWithdrawnReport.getBranduser())){
						baseSalesWithdrawnReport.setBrandusername("应收款期初");
					}else{
						baseSalesWithdrawnReport.setBrandusername("其他(未指定品牌业务员)");
					}
				}
				if(groupcols.indexOf("brandid")==-1){
					baseSalesWithdrawnReport.setBrandname("");
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
					}else{
						baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
					}
				}
				if(groupcols.indexOf("branduserdept") == -1){
					DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
					if(null!=departMent){
						baseSalesWithdrawnReport.setBranduserdeptname(departMent.getName());
					}else{
						baseSalesWithdrawnReport.setBranduserdeptname("其他（未指定人员部门）");
					}
				}
			}
			if(groupcols.indexOf("branddept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
				if(null!=departMent){
					baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
				}else{
					if("QC".equals(baseSalesWithdrawnReport.getBranddept())){
						baseSalesWithdrawnReport.setBranddeptname("应收款期初");
					}else{
						baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
					}
				}
			}
            if(groupcols.indexOf("supplierid")!=-1){
                BuySupplier buySupplier = getSupplierInfoById(baseSalesWithdrawnReport.getSupplierid());
                if(null!=buySupplier){
                    baseSalesWithdrawnReport.setSuppliername(buySupplier.getName());
                }else{
					if("QC".equals(baseSalesWithdrawnReport.getSupplierid())){
						baseSalesWithdrawnReport.setSuppliername("应收款期初");
					}else{
						baseSalesWithdrawnReport.setSupplierid("0");
						baseSalesWithdrawnReport.setSuppliername("其他（未指定供应商）");
					}
                }
                if(groupcols.indexOf("branddept")==-1){
                    DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
                    if(null!=departMent){
                        baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
                    }else{
                        baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
                    }
                }
            }
			//人员部门
			if(groupcols.indexOf("branduserdept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranduserdept());
				if(null!=departMent){
					baseSalesWithdrawnReport.setBranduserdeptname(departMent.getName());
				}else{
					if("QC".equals(baseSalesWithdrawnReport.getBranduserdept())){
						baseSalesWithdrawnReport.setBranduserdeptname("应收款期初");
					}else{
						baseSalesWithdrawnReport.setBranduserdeptname("其他（未指定人员部门）");
					}
				}
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.showBaseFinanceReceiptDataCount(pageMap),list,pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<BaseSalesWithdrawnReport> footer = financeFundsReturnMapper.showBaseFinanceReceiptData(pageMap);
		for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : footer){
			if(null!=baseSalesWithdrawnReport){
				String[] groupArr = groupcols.split(",");
				if(groupArr[0].indexOf("branddept")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBranddeptname("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
				}
				else if(groupArr[0].indexOf("brandid")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBrandname("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
				}
				else if(groupArr[0].indexOf("salesdept")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setSalesdeptname("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
				}
				else if(groupArr[0].indexOf("salesuser")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setSalesusername("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
				}
				else if(groupArr[0].indexOf("branduser")!=-1 && !groupArr[0].equals("branduserdept")){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBrandusername("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
				}
				else if(groupArr[0].indexOf("goodsid")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setGoodsname("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
				}
				else if(groupArr[0].indexOf("customerid")!=-1 && groupArr[0].equals("customerid")){
					baseSalesWithdrawnReport.setCustomername("合计");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
				}else if(groupArr[0].indexOf("pcustomerid") !=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
				}else if(groupArr[0].indexOf("supplierid")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("合计");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
				}else if(groupArr[0].indexOf("supplieruser")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("合计");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("");
				}else if(groupArr[0].indexOf("branduserdept")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setPcustomerid("");
					baseSalesWithdrawnReport.setPcustomername("");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
					baseSalesWithdrawnReport.setSupplierusername("");
					baseSalesWithdrawnReport.setBranduserdept("");
					baseSalesWithdrawnReport.setBranduserdeptname("合计");
				}
			}else{
				footer = new ArrayList<BaseSalesWithdrawnReport>();
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}
	

	@Override
	public List showSupplierFinanceReceiptData(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		if(pageMap.getCondition().containsKey("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			String retStr = "";
			String[] branddeptArr = str.split(",");
			for(String branddept : branddeptArr){
				Map map = new HashMap();
				map.put("deptid", branddept);
				List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
				if(dList.size() != 0){
					for(DepartMent departMent : dList){
						if(StringUtils.isNotEmpty(retStr)){
							retStr += "," + departMent.getId();
						}else{
							retStr = departMent.getId();
						}
					}
				}
			}
			pageMap.getCondition().put("branddept", retStr);
		}
        if(pageMap.getCondition().containsKey("businessdate2")){
            String businessdate2 = (String) pageMap.getCondition().get("businessdate2");
            String nextday = CommonUtils.getNextDayByDate(businessdate2);
            pageMap.getCondition().put("businessdate2",nextday);
        }
		List<BaseSalesWithdrawnReport> list = financeFundsReturnMapper.getSupplierFinanceReceiptList(pageMap);
		for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : list){
			//条形码
			if(StringUtils.isNotEmpty(baseSalesWithdrawnReport.getGoodsid())){
				GoodsInfo goodsInfo = getGoodsInfoByID(baseSalesWithdrawnReport.getGoodsid());
				if(null != goodsInfo){
					baseSalesWithdrawnReport.setBarcode(goodsInfo.getBarcode());
				}
			}
			//供应商
			BuySupplier buySupplier = getSupplierInfoById(baseSalesWithdrawnReport.getSupplierid());
			if(null != buySupplier){
				baseSalesWithdrawnReport.setSuppliername(buySupplier.getName());
			}else{
				baseSalesWithdrawnReport.setSuppliername("未指定供应商");
			}
			//品牌部门
			DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
			if(null != departMent){
				baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
			}
		}
		//PageData pageData = new PageData(financeFundsReturnMapper.getSupplierFinanceReceiptCount(pageMap),list,pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<BaseSalesWithdrawnReport> footer = financeFundsReturnMapper.getSupplierFinanceReceiptList(pageMap);
		for(BaseSalesWithdrawnReport baseSalesWithdrawnReport : footer){
			if(null!=baseSalesWithdrawnReport){
				baseSalesWithdrawnReport.setSupplierid("");
				baseSalesWithdrawnReport.setSuppliername("合计");
				list.add(baseSalesWithdrawnReport);
			}else{
				footer = new ArrayList<BaseSalesWithdrawnReport>();
			}
		}
		//pageData.setFooter(footer);
		return list;
	}

	@Override
	public PageData showCustomerReceivableDynamicData(PageMap pageMap)
			throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
        String query_dept = "",query_salesarea = "";
        if(pageMap.getCondition().containsKey("salesdept")){
            String str = (String) pageMap.getCondition().get("salesdept");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_dept += " and t.salesdept like '"+str+"%' ";
            }else{
                String retStr = "";
                String[] salesdeptArr = str.split(",");
                for(String salesdept : salesdeptArr){
                    Map map = new HashMap();
                    map.put("deptid", salesdept);
                    List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
                    if(dList.size() != 0){
                        for(DepartMent departMent : dList){
                            if(StringUtils.isNotEmpty(retStr)){
                                retStr += "," + departMent.getId();
                            }else{
                                retStr = departMent.getId();
                            }
                        }
                    }
                }
                query_dept = " and FIND_IN_SET(t.salesdept,'"+retStr+"')";
            }
            if(StringUtils.isNotEmpty(query_dept)){
                pageMap.getCondition().put("query_dept",query_dept);
            }
        }
        if(pageMap.getCondition().containsKey("salesarea")){
            String str = (String) pageMap.getCondition().get("salesarea");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_salesarea += " and t.salesarea like '"+str+"%' ";
            }else{
                String retStr = "";
                String[] salesareaArr = str.split(",");
                for(String salesarea : salesareaArr){
                    List<SalesArea> dList = getBaseSalesAreaMapper().getSalesAreaListById(salesarea);
                    if(dList.size() != 0){
                        for(SalesArea salesArea : dList){
                            if(StringUtils.isNotEmpty(retStr)){
                                retStr += "," + salesArea.getId();
                            }else{
                                retStr = salesArea.getId();
                            }
                        }
                    }
                }
                query_salesarea = " and FIND_IN_SET(t.salesarea,'"+retStr+"')";
            }
            if(StringUtils.isNotEmpty(query_salesarea)){
                pageMap.getCondition().put("query_salesarea",query_salesarea);
            }
        }
		List<CustomerReceivableDynamic> list = financeFundsReturnMapper.showCustomerReceivableDynamicData(pageMap);
		for(CustomerReceivableDynamic customerReceivableDynamic : list){
			//单据类型
			if("1".equals(customerReceivableDynamic.getBilltype())){
				customerReceivableDynamic.setBilltypename("发货单");
			}else if("2".equals(customerReceivableDynamic.getBilltype())){
				customerReceivableDynamic.setBilltypename("直退退货单");
			}else if("3".equals(customerReceivableDynamic.getBilltype())){
				customerReceivableDynamic.setBilltypename("售后退货单");
			}else if("4".equals(customerReceivableDynamic.getBilltype())){
				customerReceivableDynamic.setBilltypename("正常冲差单");
			}else if("5".equals(customerReceivableDynamic.getBilltype())){
				customerReceivableDynamic.setBilltypename("发票冲差单");
			}else if("6".equals(customerReceivableDynamic.getBilltype())){
				customerReceivableDynamic.setBilltypename("回单冲差单");
			}else if("7".equals(customerReceivableDynamic.getBilltype())){
				customerReceivableDynamic.setBilltypename("应收款期初");
			}
			Customer customer = getCustomerByID(customerReceivableDynamic.getCustomerid());
			if(null!=customer){
				customerReceivableDynamic.setCustomername(customer.getName());
			}
			Customer pcustomer = getCustomerByID(customerReceivableDynamic.getPcustomerid());
			if(null!=pcustomer){
				customerReceivableDynamic.setPcustomername(pcustomer.getName());
			}
			Personnel personnel = getPersonnelById(customerReceivableDynamic.getSalesuser());
			if(null!=personnel){
				customerReceivableDynamic.setSalesusername(personnel.getName());
			}
			SalesArea salesArea = getSalesareaByID(customerReceivableDynamic.getSalesarea());
			if(null!=salesArea){
				customerReceivableDynamic.setSalesareaname(salesArea.getName());
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.showCustomerReceivableDynamicDataCount(pageMap),list,pageMap);

		List footer = new ArrayList();
		CustomerReceivableDynamic customerReceivableDynamic = financeFundsReturnMapper.showCustomerReceivableDynamicSum(pageMap);
		if(null== customerReceivableDynamic){
			customerReceivableDynamic = new CustomerReceivableDynamic();
		}
		customerReceivableDynamic.setCustomername("合计");
		footer.add(customerReceivableDynamic);
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData showCustomerInvoiceAccountBillData(PageMap pageMap)
			throws Exception {
		List<Map<String,Object>> list = financeFundsReturnMapper.showCustomerInvoiceAccountBillData(pageMap);
		for(Map<String,Object> map : list){
			if("1".equals((String)map.get("billtype"))){
				map.put("billtypename", "销售发票");
			}else if("2".equals((String)map.get("billtype"))){
				map.put("billtypename", "正常冲差单");
			}
			String customerid = (String) map.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				map.put("customername", customer.getName());
			}
			String pcustomerid = (String) map.get("pcustomerid");
			Customer pcustomer = getCustomerByID(pcustomerid);
			if(null!=pcustomer){
				map.put("pcustomername", pcustomer.getName());
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.showCustomerInvoiceAccountBillCount(pageMap),list,pageMap);
		Map footermap = financeFundsReturnMapper.showCustomerInvoiceAccountBillSum(pageMap);
		if(null!=footermap){
			footermap.put("customername", "合计");
			List footer = new ArrayList();
			footer.add(footermap);
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public PageData showWriteAccountDetailData(PageMap pageMap)
			throws Exception {
		List<Map<String,Object>> list = financeFundsReturnMapper.showWriteAccountDetailData(pageMap);
		for(Map<String,Object> map : list){
			if("1".equals((String)map.get("billtype"))){
				map.put("billtypename", "销售发货回单");
			}else if("2".equals((String)map.get("billtype"))){
				map.put("billtypename", "销售退货通知单");
			}else if("3".equals((String)map.get("billtype"))){
				map.put("billtypename", "发票冲差单");
			}else if("4".equals((String)map.get("billtype"))){
				map.put("billtypename", "正常冲差单");
			}
			String customerid = (String) map.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				map.put("customername", customer.getName());
			}
			String pcustomerid = (String) map.get("pcustomerid");
			Customer pcustomer = getCustomerByID(pcustomerid);
			if(null!=pcustomer){
				map.put("pcustomername", pcustomer.getName());
			}
			String goodsid = (String) map.get("goodsid");
			GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
			if(null!=goodsInfo){
				map.put("goodsname", goodsInfo.getName());
				map.put("barcode", goodsInfo.getBarcode());
			}else{
				Brand brand = getGoodsBrandByID(goodsid);
				if(null!=brand){
					map.put("goodsname",brand.getName()+"折扣");
				}
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.showWriteAccountDetailCount(pageMap),list,pageMap);
		Map footermap = financeFundsReturnMapper.showWriteAccountDetailSum(pageMap);
		if(null!=footermap){
			footermap.put("customername", "合计");
			List footer = new ArrayList();
			footer.add(footermap);
			pageData.setFooter(footer);
		}
		return pageData;
	}

	@Override
	public PageData showBaseReceivablePassDueListData(PageMap pageMap)
			throws Exception {
		SysUser sysUser = getSysUser();
		pageMap.getCondition().put("sysuserid", sysUser.getUserid());
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		String query_sql = " 1=1 ";
		String query_sql_begin = " 1=1 ";
		String query_sql_z = " 1=1 ";
		if(pageMap.getCondition().containsKey("goodsid")){
			String str = (String) pageMap.getCondition().get("goodsid");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t1.goodsid = '"+str+"' ";
			query_sql_z += " and z.goodsid = '"+str+"' ";
		}
		if(pageMap.getCondition().containsKey("brandid")){
			String str = (String) pageMap.getCondition().get("brandid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.brandid = '"+str+"' ";
				query_sql_z += " and z.brandid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.brandid,'"+str+"')";
				query_sql_z += " and FIND_IN_SET(z.brandid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesuser")){
			String str = (String) pageMap.getCondition().get("salesuser");
			str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                query_sql += " and t.salesuser = '' ";
				query_sql_begin += " and t.salesuser = '' ";
            }else{
                if(str.indexOf(",") == -1){
                    query_sql += " and t.salesuser = '"+str+"' ";
					query_sql_begin += " and t.salesuser = '"+str+"' ";
                }else{
                    query_sql += " and FIND_IN_SET(t.salesuser,'"+str+"')";
					query_sql_begin += " and FIND_IN_SET(t.salesuser,'"+str+"')";
                }
            }
		}
		if(pageMap.getCondition().containsKey("branduser")){
			String str = (String) pageMap.getCondition().get("branduser");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.branduser = '"+str+"' ";
				query_sql_z += " and z.branduser = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.branduser,'"+str+"')";
				query_sql_z += " and FIND_IN_SET(z.branduser,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.branddept like '"+str+"%' ";
				query_sql_z += " and z.branddept like '"+str+"%' ";
			}else{
				query_sql += " and FIND_IN_SET(t1.branddept,'"+str+"')";
				query_sql_z += " and FIND_IN_SET(z.branddept,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("salesarea")){
			String str = (String) pageMap.getCondition().get("salesarea");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.salesarea like '"+str+"%' ";
				query_sql_begin += " and t.salesarea like '"+str+"%' ";
			}else{
				query_sql += " and FIND_IN_SET(t.salesarea,'"+str+"')";
				query_sql_begin += " and t.salesarea like '"+str+"%' ";
			}
		}
		if(pageMap.getCondition().containsKey("salesdept")){
			String str = (String) pageMap.getCondition().get("salesdept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.salesdept like '"+str+"%' ";
				query_sql_begin += " and t.salesdept like '"+str+"%' ";
			}else{
				query_sql += " and FIND_IN_SET(t.salesdept,'"+str+"')";
				query_sql_begin += " and FIND_IN_SET(t.salesdept,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("customerid")){
			String str = (String) pageMap.getCondition().get("customerid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.customerid = '"+str+"' ";
				query_sql_begin += " and t.customerid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t.customerid,'"+str+"')";
				query_sql_begin += " and FIND_IN_SET(t.customerid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("pcustomerid")) {
            String str = (String) pageMap.getCondition().get("pcustomerid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") == -1) {
                query_sql += " and t.pcustomerid = '" + str + "' ";
				query_sql_begin += " and t.pcustomerid = '" + str + "' ";
            } else {
                query_sql += " and FIND_IN_SET(t.pcustomerid,'" + str + "')";
				query_sql_begin += " and FIND_IN_SET(t.pcustomerid,'" + str + "')";
            }
        }
        if(pageMap.getCondition().containsKey("supplierid")){
            String str = (String) pageMap.getCondition().get("supplierid");
            str = StringEscapeUtils.escapeSql(str);
            if(str.indexOf(",") == -1){
                query_sql += " and t1.supplierid = '"+str+"' ";
				query_sql_z += " and z.supplierid = '"+str+"' ";
            }
            else{
                query_sql += " and FIND_IN_SET(t1.supplierid,'"+str+"')";
				query_sql_z += " and FIND_IN_SET(z.supplierid,'"+str+"')";
            }
        }
		if(pageMap.getCondition().containsKey("businessdate1")){
			String str = (String) pageMap.getCondition().get("businessdate1");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate >= '"+str+"'";
		}
		if(pageMap.getCondition().containsKey("businessdate2")){
			String str = (String) pageMap.getCondition().get("businessdate2");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t.businessdate <= '"+str+"'";
		}
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "");
			groupcols = "customerid,goodsid";
		}
		
		//排序
		String orderstr = "";
		if(pageMap.getCondition().containsKey("sort")){
			String sort = (String) pageMap.getCondition().get("sort");
			String order = (String) pageMap.getCondition().get("order");
			if(null==order){
				order = "asc";
			}
			orderstr = sort +" " +order;
		}
		pageMap.getCondition().put("orderstr", orderstr);
		String query_sql_push=query_sql.replace("t1", "t");
		query_sql_push=query_sql_push.replace("goodsid", "brandid");
		pageMap.getCondition().put("query_sql_push", query_sql_push);
		pageMap.getCondition().put("query_sql", query_sql);
		pageMap.getCondition().put("query_sql_begin", query_sql_begin);
		pageMap.getCondition().put("query_sql_z", query_sql_z);
		
	
		
		String pass_sql="";
		String column_sum="";
	    String column="";
		List<Map<String,Object>> RSlist = financeFundsReturnMapper.getBaseReceivablePassDueListDataRS(pageMap);
		int RSCount = financeFundsReturnMapper.getBaseReceivablePassDueListDataRSCount(pageMap);
		for(Map<String,Object> rsMap :RSlist){
			int beginday =(Integer)rsMap.get("beginday");
			int endday =(Integer)rsMap.get("endday");
			int seq =(Integer)rsMap.get("seq");
			column_sum=column_sum+",sum(z.passamount"+seq+") as passamount"+seq;
			column=column+",0 as passamount"+seq;
			pass_sql=pass_sql+"union all select t.customerid,t.pcustomerid,t.salesarea,t.salesdept,t.salesuser,t1.goodsid AS goodsid,t1.brandid,t1.branduser,t1.branddept,t1.supplierid,t1.supplieruser,0 AS saleamount, 0 AS salenotaxamount, 0 as returnamount, 0 as pushamount,0 as costamount,0 as unpassamount ,0 as totalpassamount";
		    int v=1;
		    while(v<RSCount+1){
		    	if(v!=seq){
		    		pass_sql=pass_sql+",0 as passamount"+v;
		    	}
		    	if(v==seq){
		    		pass_sql=pass_sql+",t1.taxamount as passamount"+v;
		    	}
		    	v=v+1;
		    }
            String beginDate = CommonUtils.getBeforeDateInDays(beginday);
		    pass_sql=pass_sql+" from t_storage_saleout t RIGHT JOIN t_storage_saleout_detail t1 on t.id=t1.saleoutid where  t1.duefromdate<= '"+beginDate+"' ";
		    if(endday!=0){
                String enddateStr = CommonUtils.getBeforeDateInDays(new Date(),endday);
		    	pass_sql=pass_sql+" and (t1.duefromdate='' or t1.duefromdate>= '"+enddateStr+"') ";
		    }
            String today = CommonUtils.getTodayDataStr();
		    pass_sql=pass_sql+" and "+query_sql+" and t1.duefromdate<>'' and t1.duefromdate < '"+today+"' and t1.iswriteoff='0' and t.status in('3','4') ";
			//退货入库单
            pass_sql=pass_sql+"union all select t.customerid,t.pcustomerid,t.salesarea,t.salesdept,t.salesuser,t1.goodsid AS goodsid,t1.brandid,t1.branduser,t1.branddept,t1.supplierid,t1.supplieruser,0 AS saleamount, 0 AS salenotaxamount, 0 as returnamount, 0 as pushamount,0 as costamount,0 as unpassamount ,0 as totalpassamount";
			v=1;
            while(v<RSCount+1){
                if(v!=seq){
                    pass_sql=pass_sql+",0 as passamount"+v;
                }
                if(v==seq){
                    pass_sql=pass_sql+",-t1.taxamount as passamount"+v;
                }
				v=v+1;
            }
            pass_sql=pass_sql+" from t_storage_salereject_enter t RIGHT JOIN t_storage_salereject_enter_detail t1 on t.id=t1.salerejectid where  t1.duefromdate<= '"+beginDate+"' ";
            if(endday!=0){
                String enddateStr = CommonUtils.getBeforeDateInDays(new Date(),endday);
                pass_sql=pass_sql+" and (t1.duefromdate='' or t1.duefromdate>= '"+enddateStr+"') ";
            }
            pass_sql=pass_sql+" and "+query_sql+" and t1.duefromdate<>'' and t1.duefromdate < '"+today+"' and t.ischeck='1' and t1.iswriteoff='0' and t.status in('3','4') ";
			//应收款期初
			pass_sql=pass_sql+"union all select t.customerid,t.pcustomerid,t.salesarea,t.salesdept,t.salesuser,'	QC' goodsid,'QC' brandid,'QC' branduser,'QC' branddept,'QC' supplierid,'QC' supplieruser,0 AS saleamount, 0 AS salenotaxamount, 0 as returnamount, 0 as pushamount,0 as costamount,0 as unpassamount ,0 as totalpassamount";
			v=1;
			while(v<RSCount+1){
				if(v!=seq){
					pass_sql=pass_sql+",0 as passamount"+v;
				}
				if(v==seq){
					pass_sql=pass_sql+",t.amount as passamount"+v;
				}
				v=v+1;
			}
			pass_sql=pass_sql+" from t_account_begin_amount t where t.duefromdate<= '"+beginDate+"' ";
			if(endday!=0){
				String enddateStr = CommonUtils.getBeforeDateInDays(new Date(),endday);
				pass_sql=pass_sql+" and (t.duefromdate='' or t.duefromdate>= '"+enddateStr+"') ";
			}
			pass_sql=pass_sql+" and "+query_sql_begin+" and t.duefromdate<>'' and t.duefromdate < '"+today+"' and t.iswriteoff='0' and t.status in('3','4') ";
		}
		pageMap.getCondition().put("pass_sql", pass_sql);
		pageMap.getCondition().put("column_sum", column_sum);
		pageMap.getCondition().put("column", column);
        pageMap.getCondition().put("today", CommonUtils.getTodayDataStr());
		List<Map<String,Object>> list = financeFundsReturnMapper.showBaseReceivablePassDueListData(pageMap);
		for(Map<String,Object> dataObject : list){
			if(null == dataObject){
				continue;
			}
			BigDecimal returnamount = (BigDecimal) dataObject.get("returnamount");
			BigDecimal saleamount = (BigDecimal) dataObject.get("saleamount");
			if(saleamount.compareTo(BigDecimal.ZERO)==1){
				returnamount = returnamount.negate();
				BigDecimal returnrate = returnamount.divide(saleamount,6,BigDecimal.ROUND_HALF_UP);
				returnrate = returnrate.multiply(new BigDecimal(100));
				dataObject.put("returnrate", returnrate);
			}else{
				if(null!=returnamount && returnamount.compareTo(BigDecimal.ZERO)!=0){
					dataObject.put("returnrate", 100);
				}
			}
			
			String customerid = (String) dataObject.get("customerid");
			String pcustomerid = (String) dataObject.get("pcustomerid");
			String salesarea = (String) dataObject.get("salesarea");
			String salesdept = (String) dataObject.get("salesdept");
			String salesuser = (String) dataObject.get("salesuser");
			String goodsid = (String) dataObject.get("goodsid");
			String brandid = (String) dataObject.get("brandid");
			String branduser = (String) dataObject.get("branduser");
			String branddept = (String) dataObject.get("branddept");
            String supplierid = (String) dataObject.get("supplierid");
			if(groupcols.indexOf("customerid")!=-1){
				//结算方式
				Customer customer = getCustomerByID(customerid);
				if(null!=customer){
					if(StringUtils.isNotEmpty(customer.getSettletype())){
						dataObject.put("settletype", customer.getSettletype());
						dataObject.put("settleday", customer.getSettleday());
						Settlement settlement = getSettlementByID(customer.getSettletype());
						if(null != settlement){
							dataObject.put("settletypename", settlement.getName());
						}
					}
					if(StringUtils.isNotEmpty(customer.getPayeeid())){
						Personnel personnel = getPersonnelById(customer.getPayeeid());
						if(null != personnel){
							dataObject.put("payeename", personnel.getName());
						}
					}
					dataObject.put("customername", customer.getName());
				}
				if(groupcols.indexOf("pcustomerid")==-1){
					Customer pcustomer = getCustomerByID(pcustomerid);
					if(null!=pcustomer ){
						dataObject.put("pcustomername", pcustomer.getName());
					}
				}
				if(groupcols.indexOf("salesdept")==-1){
					DepartMent departMent = getDepartmentByDeptid(salesdept);
					if(null!=departMent){
						dataObject.put("salesdeptname", departMent.getName());
					}else{
						dataObject.put("salesdeptname", "其他(未指定销售部门)");
					}
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(salesarea);
					if(null!=salesArea){
						dataObject.put("salesareaname", salesArea.getName());
					}else{
						dataObject.put("salesareaname","其他（未指定销售区域）");
					}
				}
				if(groupcols.indexOf("salesuser")==-1){
					Personnel personnel = getPersonnelById(salesuser);
					if(null!=personnel){
						dataObject.put("salesusername", personnel.getName());
					}else{
						dataObject.put("salesusername","其他（未指定客户业务员）");
					}
				}
			}
			
			if(groupcols.indexOf("pcustomerid")!=-1){
				Customer pcustomer = getCustomerByID(pcustomerid);
				if(null!=pcustomer ){
					dataObject.put("pcustomername", pcustomer.getName());
				}else{
					dataObject.put("pcustomername","其他客户总和");
				}
				if(groupcols.indexOf("customerid")==1){
					dataObject.put("customerid","");
					dataObject.put("customername","");
					
				}
				
			}
			if(groupcols.indexOf("salesarea")!=-1){
				SalesArea salesArea = getSalesareaByID(salesarea);
				if(null!=salesArea){
					dataObject.put("salesareaname",salesArea.getName());
				}else{
					dataObject.put("salesareaname","其他（未指定销售区域）");
				}
			}
			if(groupcols.indexOf("salesdept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(salesdept);
				if(null!=departMent){
					dataObject.put("salesdeptname",departMent.getName());
				}else{
					dataObject.put("salesdeptname","其他（未指定销售部门）");
				}
			}
			if(groupcols.indexOf("salesuser")!=-1){
				Personnel personnel = getPersonnelById(salesuser);
				if(null!=personnel){
					dataObject.put("salesusername",personnel.getName());
				}else{
                    dataObject.put("salesuser","0");
					dataObject.put("salesusername","其他（未指定客户业务员）");
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(salesarea);
					if(null!=salesArea){
						dataObject.put("salesareaname",salesArea.getName());
					}
				}
				if(groupcols.indexOf("salesdept")==-1){
					DepartMent departMent = getDepartmentByDeptid(salesdept);
					if(null!=departMent){
						dataObject.put("salesdeptname",departMent.getName());
					}else{
						dataObject.put("salesdeptname","其他（未指定销售部门）");
					}
				}
			}
			if(groupcols.indexOf("goodsid")!=-1){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
				if(null!=goodsInfo){
					dataObject.put("barcode", goodsInfo.getBarcode());
					dataObject.put("goodsname",goodsInfo.getName());
                    BuySupplier buySupplier = getSupplierInfoById(supplierid);
                    if(null!=buySupplier){
                        dataObject.put("suppliername",buySupplier.getName());
                    }else{
                        dataObject.put("suppliername","其他（未指定供应商）");
                    }
				}else{
					Brand brand = getGoodsBrandByID(goodsid);
					if(null!=brand){
						dataObject.put("goodsname","（折扣）"+brand.getName());
					}else{
						if("QC".equals(goodsid)){
							dataObject.put("goodsname","应收款期初");
						}else{
							dataObject.put("goodsname","（折扣）其他");
						}
					}
				}
				if(groupcols.indexOf("brandid")==-1){
					Brand brand = getGoodsBrandByID(brandid);
					if(null!=brand){
						dataObject.put("brandname",brand.getName());
					}
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(branddept);
					if(null!=departMent){
						dataObject.put("branddeptname",departMent.getName());
					}else{
						dataObject.put("branddeptname","其他（未指定品牌部）");
					}
				}
				if(groupcols.indexOf("branduser")==-1){
					Personnel personnel = getPersonnelById(branduser);
					if(null!=personnel){
						dataObject.put("brandusername",personnel.getName());
					}else{
						dataObject.put("brandusername","其他（未指定品牌业务员）");
					}
				}
			}
			if(groupcols.indexOf("brandid")!=-1){
				Brand brand = getGoodsBrandByID(brandid);
				if(null!=brand){
					dataObject.put("brandname",brand.getName());
				}else{
					if("QC".equals(brandid)){
						dataObject.put("brandname","应收款期初");
					}else{
						dataObject.put("brandname","其他");
					}
				}
				Personnel personnel = getPersonnelById(branduser);
				if(null!=personnel){
					dataObject.put("brandusername",personnel.getName());
				}else{
					dataObject.put("brandusername","其他(未指定品牌业务员)");
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(branddept);
					if(null!=departMent){
						dataObject.put("branddeptname",departMent.getName());
					}else{
						dataObject.put("branddeptname","其他（未指定品牌部）");
					}
				}
			}
			if(groupcols.indexOf("branduser")!=-1){
				Personnel personnel = getPersonnelById(branduser);
				if(null!=personnel){
					dataObject.put("brandusername",personnel.getName());
				}else{
					if("QC".equals(branduser)){
						dataObject.put("brandusername","应收款期初");
					}else{
						dataObject.put("brandusername","其他(未指定品牌业务员)");
					}
				}
				if(groupcols.indexOf("brandid")==-1){
					dataObject.put("brandname","");
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(branddept);
					if(null!=departMent){
						dataObject.put("branddeptname",departMent.getName());
					}else{
						dataObject.put("branddeptname","其他（未指定品牌部）");
					}
				}
			}
			if(groupcols.indexOf("branddept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(branddept);
				if(null!=departMent){
					dataObject.put("branddeptname",departMent.getName());
				}else{
					if("QC".equals(branddept)){
						dataObject.put("branddeptname","应收款期初");
					}else{
						dataObject.put("branddeptname","其他（未指定品牌部）");
					}
				}
			}
            if(groupcols.indexOf("supplierid")!=-1){
                BuySupplier buySupplier = getSupplierInfoById(supplierid);
                if(null!=buySupplier){
                    dataObject.put("suppliername",buySupplier.getName());
                }else{
					if("QC".equals(supplierid)){
						dataObject.put("suppliername","应收款期初");
					}else{
						dataObject.put("suppliername","其他（未指定供应商）");
					}
                }
            }
		}
		PageData pageData = new PageData(financeFundsReturnMapper.showBaseReceivablePassDueListCount(pageMap),list,pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<Map<String,Object>> footer = financeFundsReturnMapper.showBaseReceivablePassDueListData(pageMap);
		for(Map<String,Object> map : footer){
			if(null!=map){
				BigDecimal returnamount = (BigDecimal) map.get("returnamount");
				BigDecimal saleamount = (BigDecimal) map.get("saleamount");
				if(saleamount.compareTo(BigDecimal.ZERO)==1){
					returnamount = returnamount.negate();
					BigDecimal returnrate = returnamount.divide(saleamount,6,BigDecimal.ROUND_HALF_UP);
					returnrate = returnrate.multiply(new BigDecimal(100));
					map.put("returnrate", returnrate);
				}else{
					if(null!=returnamount && returnamount.compareTo(BigDecimal.ZERO)!=0){
						map.put("returnrate", 100);
					}
				}
				map.put("customerid", "");
				map.put("goodsid", "");
				if(groupcols.indexOf("customerid")!=-1){
					if(groupcols.indexOf("pcustomerid")!=-1){
						map.put("pcustomername", "合计");
					}else{
						map.put("customername", "合计");
					}
				}else if(groupcols.indexOf("pcustomerid")!=-1){
					map.put("pcustomername", "合计");
				}else if(groupcols.indexOf("salesarea")!=-1){
					map.put("salesareaname", "合计");
				}else if(groupcols.indexOf("salesdept")!=-1){
					map.put("salesdeptname", "合计");
				}else if(groupcols.indexOf("salesuser")!=-1){
					map.put("salesusername", "合计");
				}else if(groupcols.indexOf("goodsid")!=-1){
					map.put("goodsname", "合计");
				}else if(groupcols.indexOf("brandid")!=-1){
					map.put("brandname", "合计");
				}else if(groupcols.indexOf("branddept")!=-1){
					map.put("branddeptname", "合计");
				}else if(groupcols.indexOf("branduser")!=-1){
					map.put("brandusername", "合计");
				}else if(groupcols.indexOf("supplierid")!=-1){
                    map.put("suppliername", "合计");
                }
			}else{
				footer = new ArrayList<Map<String,Object>>();
			}
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData showBaseWithdrawnPastdueListData(PageMap pageMap)
			throws Exception {
		SysUser sysUser = getSysUser();
		pageMap.getCondition().put("sysuserid", sysUser.getUserid());
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		String query_sql = " 1=1 ";
		String query_sql_begin = " 1=1 ";
		String query_sql_z = " 1=1 ";
		if(pageMap.getCondition().containsKey("businessdate1")){
			String str = (String) pageMap.getCondition().get("businessdate1");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t1.writeoffdate >= '"+str+"' ";
			query_sql_begin += " and t.writeoffdate >= '"+str+"' ";
		}
		if(pageMap.getCondition().containsKey("businessdate2")){
			String str = (String) pageMap.getCondition().get("businessdate2");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t1.writeoffdate <= '"+str+"' ";
			query_sql_begin += " and t.writeoffdate <= '"+str+"' ";
		}
		if(pageMap.getCondition().containsKey("goodsid")){
			String str = (String) pageMap.getCondition().get("goodsid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.goodsid = '"+str+"' ";
				query_sql_z += " and z.goodsid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.goodsid,'"+str+"')";
				query_sql_z += " and FIND_IN_SET(z.goodsid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("brandid")){
			String str = (String) pageMap.getCondition().get("brandid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.brandid = '"+str+"' ";
				query_sql_z += " and z.brandid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.brandid,'"+str+"')";
				query_sql_z += " and FIND_IN_SET(z.brandid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("customerid")){
			String str = (String) pageMap.getCondition().get("customerid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.customerid = '"+str+"' ";
				query_sql_begin += " and t.customerid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t.customerid,'"+str+"')";
				query_sql_begin += " and FIND_IN_SET(t.customerid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("pcustomerid")){
			String str = (String) pageMap.getCondition().get("pcustomerid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.pcustomerid = '"+str+"' ";
				query_sql_begin += " and t.pcustomerid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t.pcustomerid,'"+str+"')";
				query_sql_begin += " and FIND_IN_SET(t.pcustomerid,'"+str+"')";
			}
		}  
		if(pageMap.getCondition().containsKey("salesuser")){
			String str = (String) pageMap.getCondition().get("salesuser");
			str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                query_sql += " and t.salesuser = '' ";
				query_sql_begin += " and t.salesuser = '' ";
            }else{
                if(str.indexOf(",") == -1){
                    query_sql += " and t.salesuser = '"+str+"' ";
					query_sql_begin += " and t.salesuser = '"+str+"' ";
                }else{
                    query_sql += " and FIND_IN_SET(t.salesuser,'"+str+"')";
					query_sql_begin += " and FIND_IN_SET(t.salesuser,'"+str+"')";
                }
            }
		}
		if(pageMap.getCondition().containsKey("salesdept")){
			String str = (String) pageMap.getCondition().get("salesdept");
			str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                query_sql += " and t.salesdept = '' ";
				query_sql_begin += " and t.salesdept = '' ";
            }else{
                if(str.indexOf(",") == -1){
                    query_sql += " and t.salesdept = '"+str+"' ";
					query_sql_begin += " and t.salesdept = '"+str+"' ";
                }else{
                    query_sql += " and FIND_IN_SET(t.salesdept,'"+str+"')";
					query_sql_begin += " and FIND_IN_SET(t.salesdept,'"+str+"')";
                }
            }
		}
		if(pageMap.getCondition().containsKey("salesarea")){
			String str = (String) pageMap.getCondition().get("salesarea");
			str = StringEscapeUtils.escapeSql(str);
            if("0".equals(str)){
                query_sql += " and t.salesarea = '' ";
				query_sql_begin += " and t.salesarea = '' ";
            }else{
                if(str.indexOf(",") == -1){
                    query_sql += " and t.salesarea = '"+str+"' ";
					query_sql_begin += " and t.salesarea = '"+str+"' ";
                }else{
                    query_sql += " and FIND_IN_SET(t.salesarea,'"+str+"')";
					query_sql_begin += " and FIND_IN_SET(t.salesarea,'"+str+"')";
                }
            }
		}
		if(pageMap.getCondition().containsKey("branduser")){
			String str = (String) pageMap.getCondition().get("branduser");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.branduser = '"+str+"' ";
				query_sql_z += " and z.branduser = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t1.branduser,'"+str+"')";
				query_sql_z += " and FIND_IN_SET(z.branduser,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t1.branddept = '"+str+"' ";
				query_sql_z += " and z.branddept = '"+str+"' ";
			}else{
				query_sql += " and FIND_IN_SET(t1.branddept,'"+str+"')";
				query_sql_z += " and FIND_IN_SET(z.branddept,'"+str+"')";
			}
		}
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
			pageMap.getCondition().put("groupcols", "");
			groupcols = "customerid,goodsid";
		}
		pageMap.getCondition().put("query_sql", query_sql);
		//排序
		String orderstr = "";
		if(pageMap.getCondition().containsKey("sort")){
			String sort = (String) pageMap.getCondition().get("sort");
			String order = (String) pageMap.getCondition().get("order");
			if(null==order){
				order = "asc";
			}
			orderstr = sort +" " +order;
		}
		pageMap.getCondition().put("orderstr", orderstr);
		String query_sql_push=query_sql.replace("t1", "t");
		pageMap.getCondition().put("query_sql_push", query_sql_push);
		pageMap.getCondition().put("query_sql_begin", query_sql_begin);
		pageMap.getCondition().put("query_sql_z", query_sql_z);
		
		String pass_sql="";
		String column_sum="";
	    String column="";
		List<Map<String,Object>> RSlist = financeFundsReturnMapper.getBaseReceivablePassDueListDataRS(pageMap);
		int RSCount = financeFundsReturnMapper.getBaseReceivablePassDueListDataRSCount(pageMap);
		for(Map<String,Object> rsMap :RSlist){
			int beginday =(Integer)rsMap.get("beginday");
			int endday =(Integer)rsMap.get("endday");
			int seq =(Integer)rsMap.get("seq");
			column_sum=column_sum+",sum(z.passamount"+seq+") as passamount"+seq;
			column=column+",0 as passamount"+seq;
			pass_sql=pass_sql+"union all select t.customerid,t.pcustomerid,t.salesarea,t.salesdept,t.salesuser,t1.goodsid AS goodsid,t1.brandid,t1.branduser,t1.branddept,t1.supplierid,t1.supplieruser,0 as unitnum,0 AS taxamount, 0 AS costamount, 0 as marginamount,0 as unpassamount, 0 as returnamount,0 as pushamount,0 as totalpassamount";
		    int v=1;
		    while(v<RSCount+1){
		    	if(v!=seq){
		    		pass_sql=pass_sql+",0 as passamount"+v;
		    	}
		    	if(v==seq){
		    		pass_sql=pass_sql+",t1.taxamount as passamount"+v;
		    	}
		    	v=v+1;
		    }
		    pass_sql=pass_sql+" from t_storage_saleout t RIGHT JOIN t_storage_saleout_detail t1 on t.id=t1.saleoutid where  DATE_ADD(STR_TO_DATE(t1.duefromdate, '%Y-%m-%d'),INTERVAL '"+ beginday +"' DAY)<=t1.writeoffdate";
		    if(endday!=0){
		    	pass_sql=pass_sql+" and DATE_ADD(STR_TO_DATE(t1.duefromdate, '%Y-%m-%d'),INTERVAL '"+ endday +"' DAY)>=t1.writeoffdate ";
		    }
		    pass_sql=pass_sql+" and "+query_sql+" and t1.duefromdate<>'' and t1.duefromdate < t1.writeoffdate and t1.iswriteoff='1' ";
            //退货的超账期
            pass_sql=pass_sql+"union all select t.customerid,t.pcustomerid,t.salesarea,t.salesdept,t.salesuser,t1.goodsid AS goodsid,t1.brandid,t1.branduser,t1.branddept,t1.supplierid,t1.supplieruser,0 as unitnum,0 AS taxamount, 0 AS costamount, 0 as marginamount,0 as unpassamount, 0 as returnamount,0 as pushamount,0 as totalpassamount";
            v=1;
            while(v<RSCount+1){
                if(v!=seq){
                    pass_sql=pass_sql+",0 as passamount"+v;
                }
                if(v==seq){
                    pass_sql=pass_sql+",-t1.taxamount as passamount"+v;
                }
                v=v+1;
            }
            pass_sql=pass_sql+" from t_storage_salereject_enter t RIGHT JOIN t_storage_salereject_enter_detail t1 on t.id=t1.salerejectid where  DATE_ADD(STR_TO_DATE(t1.duefromdate, '%Y-%m-%d'),INTERVAL '"+ beginday +"' DAY)<=t1.writeoffdate";
            if(endday!=0){
                pass_sql=pass_sql+" and DATE_ADD(STR_TO_DATE(t1.duefromdate, '%Y-%m-%d'),INTERVAL '"+ endday +"' DAY)>=t1.writeoffdate ";
            }
            pass_sql=pass_sql+" and "+query_sql+" and t1.duefromdate<>'' and t1.duefromdate < t1.writeoffdate and t1.iswriteoff='1' ";
			//应收款期初
			pass_sql=pass_sql+"union all select t.customerid,t.pcustomerid,t.salesarea,t.salesdept,t.salesuser,'QC' goodsid,'QC' brandid,'QC' branduser,'QC' branddept,'QC' supplierid,'QC' supplieruser,0 as unitnum,0 AS taxamount, 0 AS costamount, 0 as marginamount,0 as unpassamount, 0 as returnamount,0 as pushamount,0 as totalpassamount";
			v=1;
			while(v<RSCount+1){
				if(v!=seq){
					pass_sql=pass_sql+",0 as passamount"+v;
				}
				if(v==seq){
					pass_sql=pass_sql+",t.amount as passamount"+v;
				}
				v=v+1;
			}
			pass_sql=pass_sql+" from t_account_begin_amount t where DATE_ADD(STR_TO_DATE(t.duefromdate, '%Y-%m-%d'),INTERVAL '"+ beginday +"' DAY)<=t.writeoffdate";
			if(endday!=0){
				pass_sql=pass_sql+" and DATE_ADD(STR_TO_DATE(t.duefromdate, '%Y-%m-%d'),INTERVAL '"+ endday +"' DAY)>=t.writeoffdate ";
			}
			pass_sql=pass_sql+" and "+query_sql_begin+" and t.duefromdate<>'' and t.duefromdate < t.writeoffdate and t.iswriteoff='1' ";
        }
		pageMap.getCondition().put("pass_sql", pass_sql);
		pageMap.getCondition().put("column_sum", column_sum);
		pageMap.getCondition().put("column", column);
		
		
		
		List<Map<String,Object>> list = financeFundsReturnMapper.showBaseWithdrawnPastdueListData(pageMap);
		for(Map<String,Object> dataObject : list){
			BigDecimal taxamount = (BigDecimal) dataObject.get("taxamount");
			BigDecimal marginamount = (BigDecimal) dataObject.get("marginamount");
			BigDecimal returnamount = (BigDecimal) dataObject.get("returnamount");
            BigDecimal unitnum = (BigDecimal)dataObject.get("unitnum");
			if(null!=taxamount && taxamount.compareTo(BigDecimal.ZERO)!=0){
				BigDecimal marginrate = marginamount.divide(taxamount,6,BigDecimal.ROUND_HALF_UP);
				marginrate = marginrate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
				if(taxamount.compareTo(BigDecimal.ZERO)==-1 && marginrate.compareTo(BigDecimal.ZERO)==1){
					marginrate = marginrate.negate();
				}
				dataObject.put("marginrate", marginrate);
				
				returnamount = returnamount.negate();
				BigDecimal returnrate = returnamount.divide(taxamount,6,BigDecimal.ROUND_HALF_UP);
				returnrate = returnrate.multiply(new BigDecimal(100));
				if(returnrate.compareTo(BigDecimal.ZERO)==1){
					dataObject.put("returnrate", returnrate);
				}else{
					dataObject.put("returnrate", returnrate.negate());
				}
			}else{
				if(null!=returnamount && returnamount.compareTo(BigDecimal.ZERO)!=0){
					dataObject.put("returnrate", 100);
				}
			}
			
			String customerid = (String) dataObject.get("customerid");
			String pcustomerid = (String) dataObject.get("pcustomerid");
			String salesarea = (String) dataObject.get("salesarea");
			String salesdept = (String) dataObject.get("salesdept");
			String salesuser = (String) dataObject.get("salesuser");
			String goodsid = (String) dataObject.get("goodsid");
			String brandid = (String) dataObject.get("brandid");
			String branduser = (String) dataObject.get("branduser");
			String branddept = (String) dataObject.get("branddept");
			if(groupcols.indexOf("customerid")!=-1){
				Customer customer = getCustomerByID(customerid);
				if(null!=customer){
					if(StringUtils.isNotEmpty(customer.getSettletype())){
						dataObject.put("settletype", customer.getSettletype());
						Settlement settlement = getSettlementByID(customer.getSettletype());
						if(null != settlement){
							dataObject.put("settletypename", settlement.getName());
						}
					}
					dataObject.put("customername", customer.getName());
				}
				if(groupcols.indexOf("pcustomerid")==-1){
					Customer pcustomer = getCustomerByID(pcustomerid);
					if(null!=pcustomer ){
						dataObject.put("pcustomername", pcustomer.getName());
					}
				}
				if(groupcols.indexOf("salesdept")==-1){
					DepartMent departMent = getDepartmentByDeptid(salesdept);
					if(null!=departMent){
						dataObject.put("salesdeptname", departMent.getName());
					}else{
						dataObject.put("salesdeptname", "其他(未指定销售部门)");
					}
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(salesarea);
					if(null!=salesArea){
						dataObject.put("salesareaname", salesArea.getName());
					}else{
						dataObject.put("salesareaname","其他（未指定销售区域）");
					}
				}
				if(groupcols.indexOf("salesuser")==-1){
					Personnel personnel = getPersonnelById(salesuser);
					if(null!=personnel){
						dataObject.put("salesusername", personnel.getName());
					}else{
						dataObject.put("salesusername","其他（未指定客户业务员）");
					}
				}
			}
			
			if(groupcols.indexOf("pcustomerid")!=-1){
				Customer pcustomer = getCustomerByID(pcustomerid);
				if(null!=pcustomer ){
					dataObject.put("pcustomername", pcustomer.getName());
				}else{
					dataObject.put("pcustomername","其他客户总和");
				}
				if(groupcols.indexOf("customerid")==1){
					dataObject.put("customerid","");
					dataObject.put("customername","");
					
				}
				
			}
			if(groupcols.indexOf("salesarea")!=-1){
				SalesArea salesArea = getSalesareaByID(salesarea);
				if(null!=salesArea){
					dataObject.put("salesareaname",salesArea.getName());
				}else{
					dataObject.put("salesareaname","其他（未指定销售区域）");
				}
			}
			if(groupcols.indexOf("salesdept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(salesdept);
				if(null!=departMent){
					dataObject.put("salesdeptname",departMent.getName());
				}else{
					dataObject.put("salesdeptname","其他（未指定销售部门）");
				}
			}
			if(groupcols.indexOf("salesuser")!=-1){
				Personnel personnel = getPersonnelById(salesuser);
				if(null!=personnel){
					dataObject.put("salesusername",personnel.getName());
				}else{
					dataObject.put("salesusername","其他（未指定客户业务员）");
				}
				if(groupcols.indexOf("salesarea")==-1){
					SalesArea salesArea = getSalesareaByID(salesarea);
					if(null!=salesArea){
						dataObject.put("salesareaname",salesArea.getName());
					}
				}
				if(groupcols.indexOf("salesdept")==-1){
					DepartMent departMent = getDepartmentByDeptid(salesdept);
					if(null!=departMent){
						dataObject.put("salesdeptname",departMent.getName());
					}else{
						dataObject.put("salesdeptname","其他（未指定销售部门）");
					}
				}
			}
			if(groupcols.indexOf("goodsid")!=-1){
				GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
				if(null!=goodsInfo){
					dataObject.put("barcode", goodsInfo.getBarcode());
					dataObject.put("goodsname",goodsInfo.getName());

                    String auxunitid = "";
                    MeteringUnit auxunit = getGoodsDefaulAuxunit(goodsid);
                    if(null != auxunit){
                        auxunitid = auxunit.getId();
                    }
                    Map map = countGoodsInfoNumber(goodsid,auxunitid,unitnum);
                    String auxnumdetail = (String)map.get("auxnumdetail");
                    BigDecimal auxnum = (BigDecimal)map.get("auxnum");
                    dataObject.put("auxnumdetail",auxnumdetail);
                    dataObject.put("auxnum", auxnum); //辅单位数量
				}else{
					Brand brand = getGoodsBrandByID(goodsid);
					if(null!=brand){
						dataObject.put("goodsname","（折扣）"+brand.getName());
					}else{
						if("QC".equals(goodsid)){
							dataObject.put("goodsname","应收款期初");
						}else{
							dataObject.put("goodsname","（折扣）其他");
						}
					}
				}
				if(groupcols.indexOf("brandid")==-1){
					Brand brand = getGoodsBrandByID(brandid);
					if(null!=brand){
						dataObject.put("brandname",brand.getName());
					}
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(branddept);
					if(null!=departMent){
						dataObject.put("branddeptname",departMent.getName());
					}else{
						dataObject.put("branddeptname","其他（未指定品牌部）");
					}
				}
                if(groupcols.indexOf("branduser")==-1){
                    Personnel personnel = getPersonnelById(branduser);
                    if(null!=personnel){
                        dataObject.put("brandusername",personnel.getName());
                    }else{
                        dataObject.put("brandusername","其他（未指定品牌业务员）");
                    }
                }
			}
			if(groupcols.indexOf("brandid")!=-1){
				Brand brand = getGoodsBrandByID(brandid);
				if(null!=brand){
					dataObject.put("brandname",brand.getName());
				}else{
					if("QC".equals(brandid)){
						dataObject.put("brandname","应收款期初");
					}else{
						dataObject.put("brandname","其他");
					}
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(branddept);
					if(null!=departMent){
						dataObject.put("branddeptname",departMent.getName());
					}else{
						dataObject.put("branddeptname","其他（未指定品牌部）");
					}
				}
			}
			if(groupcols.indexOf("branduser")!=-1){
				Personnel personnel = getPersonnelById(branduser);
				if(null!=personnel){
					dataObject.put("brandusername",personnel.getName());
				}else{
					if("QC".equals(branduser)){
						dataObject.put("brandusername","应收款期初");
					}else{
						dataObject.put("brandusername","其他(未指定品牌业务员)");
					}
				}
				if(groupcols.indexOf("brandid")==-1){
					dataObject.put("brandname","");
				}
				if(groupcols.indexOf("branddept")==-1){
					DepartMent departMent = getDepartmentByDeptid(branddept);
					if(null!=departMent){
						dataObject.put("branddeptname",departMent.getName());
					}else{
						dataObject.put("branddeptname","其他（未指定品牌部）");
					}
				}
			}
			if(groupcols.indexOf("branddept")!=-1){
				DepartMent departMent = getDepartmentByDeptid(branddept);
				if(null!=departMent){
					dataObject.put("branddeptname",departMent.getName());
				}else{
					if("QC".equals(branddept)){
						dataObject.put("branddeptname","应收款期初");
					}else{
						dataObject.put("branddeptname","其他（未指定品牌部）");
					}
				}
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.showBaseWithdrawnPastdueListCount(pageMap),list,pageMap);
		//取合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<Map<String,Object>> footer = financeFundsReturnMapper.showBaseWithdrawnPastdueListData(pageMap);
		for(Map<String,Object> map : footer){
			if(null!=map){
				BigDecimal taxamount = (BigDecimal) map.get("taxamount");
				BigDecimal marginamount = (BigDecimal) map.get("marginamount");
				BigDecimal returnamount = (BigDecimal) map.get("returnamount");
				if(null!=taxamount && taxamount.compareTo(BigDecimal.ZERO)!=0){
					BigDecimal marginrate = marginamount.divide(taxamount,6,BigDecimal.ROUND_HALF_UP);
					marginrate = marginrate.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
					map.put("marginrate", marginrate);
					
					returnamount = returnamount.negate();
					BigDecimal returnrate = returnamount.divide(taxamount,6,BigDecimal.ROUND_HALF_UP);
					returnrate = returnrate.multiply(new BigDecimal(100));
					if(returnrate.compareTo(BigDecimal.ZERO)==1){
						map.put("returnrate", returnrate);
					}else{
						map.put("returnrate", returnrate.negate());
					}
				}else{
					if(null!=returnamount && returnamount.compareTo(BigDecimal.ZERO)!=0){
						map.put("returnrate", 100);
					}
				}
				map.put("customerid", "");
				map.put("goodsid", "");
				if(groupcols.indexOf("customerid")!=-1){
					if(groupcols.indexOf("pcustomerid")!=-1){
						map.put("pcustomername", "合计");
					}else{
						map.put("customername", "合计");
					}
				}else if(groupcols.indexOf("pcustomerid")!=-1){
					map.put("pcustomername", "合计");
				}else if(groupcols.indexOf("salesarea")!=-1){
					map.put("salesareaname", "合计");
				}else if(groupcols.indexOf("salesdept")!=-1){
					map.put("salesdeptname", "合计");
				}else if(groupcols.indexOf("salesuser")!=-1){
					map.put("salesusername", "合计");
				}else if(groupcols.indexOf("goodsid")!=-1){
					map.put("goodsname", "合计");
				}else if(groupcols.indexOf("brandid")!=-1){
					map.put("brandname", "合计");
				}else if(groupcols.indexOf("branddept")!=-1){
					map.put("branddeptname", "合计");
				}else if(groupcols.indexOf("branduser")!=-1){
					map.put("brandusername", "合计");
				}
			}else{
                footer = new ArrayList<Map<String, Object>>();
            }
		}
		pageData.setFooter(footer);
		return pageData;
	}
	
	@Override
	public PageData showBankWriteReportData(PageMap pageMap) throws Exception {
		List<Map> bankidList = financeFundsReturnMapper.getBankId(pageMap);
		String sql_text_sum="SELECT z.customerid,sum(z.totalamount) AS totalamount,sum(z.cashamount) AS cashamount";
		String sql_text_column="SELECT t.customerid,t1.relateamount AS totalamount,IF(t2.bank='',t1.relateamount,0) AS cashamount";
		for(Map bankidMap : bankidList){
			String bankid=(String)bankidMap.get("id");
			sql_text_sum=sql_text_sum+",sum(z.amount"+bankid+") AS amount"+bankid;
			sql_text_column=sql_text_column+",IF(t2.bank='"+bankid+"',t1.relateamount,0) AS amount"+bankid;
		}
		pageMap.getCondition().put("sql_text_sum", sql_text_sum);
		pageMap.getCondition().put("sql_text_column", sql_text_column);
		String query_sql = " and 1=1 ";
		if(pageMap.getCondition().containsKey("businessdate1")){
			String str = (String) pageMap.getCondition().get("businessdate1");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t1.writeoffdate >= STR_TO_DATE('"+str+"','%Y-%m-%d')";
		}
		if(pageMap.getCondition().containsKey("businessdate2")){
			String str = (String) pageMap.getCondition().get("businessdate2");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t1.writeoffdate <= STR_TO_DATE('"+str+"','%Y-%m-%d')";
		}
		if(pageMap.getCondition().containsKey("customerid")){
			String str = (String) pageMap.getCondition().get("customerid");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				query_sql += " and t.customerid = '"+str+"' ";
			}
			else{
				query_sql += " and FIND_IN_SET(t.customerid,'"+str+"')";
			}
		}
		if(pageMap.getCondition().containsKey("bank")){
			String str = (String) pageMap.getCondition().get("bank");
			str = StringEscapeUtils.escapeSql(str);
			query_sql += " and t2.bank = '"+str+"' ";
		}
		pageMap.getCondition().put("query_sql", query_sql);
		//小计列
		pageMap.getCondition().put("groupcols", "customerid");
		//排序
		String orderstr = "";
		if(pageMap.getCondition().containsKey("sort")){
			String sort = (String) pageMap.getCondition().get("sort");
			String order = (String) pageMap.getCondition().get("order");
			if(null==order){
				order = "asc";
			}
			orderstr = sort +" " +order;
		}
		pageMap.getCondition().put("orderstr", orderstr);
		List<Map> dataList = financeFundsReturnMapper.showBankWriteReportData(pageMap);
		for(Map map : dataList){
			String customerid = (String) map.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				map.put("customername", customer.getName());
				//是否门店
				if("0".equals(customer.getIslast())){
					map.put("pid", customerid);
					map.put("pname", customer.getName());
				}else{
					map.put("pid", customer.getPid());
					Customer pCustomer = getCustomerByID(customer.getPid());
					if(null != pCustomer){
						map.put("pname", pCustomer.getName());
					}
				}
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.showBankWriteReportCount(pageMap),dataList,pageMap);
		//合计数据
		pageMap.getCondition().put("groupcols", "all");
		List<Map> footer = financeFundsReturnMapper.showBankWriteReportData(pageMap);
		if(null!=footer &&footer.size()>0 && null==footer.get(0)){
			Map map = new HashMap();
			map.put("customerid", "");
			map.put("customername", "合计");
			List newfooter = new ArrayList();
			newfooter.add(map);
			pageData.setFooter(newfooter);
		}else{
			for(Map map : footer){
				if(null!=map){
					map.put("customerid", "");
					map.put("customername", "合计");
				}
			}
			pageData.setFooter(footer);
		}
		return pageData;
	}
	@Override
	public PageData showCustomerBankWriteReportData(PageMap pageMap)
			throws Exception {
		Map<String, Object> condition=pageMap.getCondition();
		String grouptype = (String) condition.get("grouptype");
		if(null==grouptype || "".equals(grouptype.trim()) || "1".equals(grouptype.trim())){
			condition.put("showOnlyPageOrder", "true");
		}
		List<Map> dataList = financeFundsReturnMapper.showCustomerBankWriteReportData(pageMap);
		PageData pageData = new PageData(financeFundsReturnMapper.showCustomerBankWriteReportDataCount(pageMap),dataList,pageMap);
		for(Map map : dataList){
			String customerid = (String) map.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				map.put("customername", customer.getName());
				//是否门店
				if("0".equals(customer.getIslast())){
					map.put("pid", customerid);
					map.put("pname", customer.getName());
				}else{
					map.put("pid", customer.getPid());
					Customer pCustomer = getCustomerByID(customer.getPid());
					if(null != pCustomer){
						map.put("pname", pCustomer.getName());
					}
				}
			}
			String bankid = (String) map.get("bank");
			Bank bank = getBankInfoByID(bankid);
			if(null!=bank && "2".equals(grouptype)){
				map.put("bankname", bank.getName());
			}
		}
		//合计数据
		condition.put("grouptype", null);
		if(condition.containsKey("showOnlyPageOrder")){
			condition.remove("showOnlyPageOrder");
		}
		List<Map> footer = financeFundsReturnMapper.showCustomerBankWriteReportData(pageMap);
		if(null!=footer &&footer.size()>0 && null==footer.get(0)){
			Map map = new HashMap();
			map.put("customerid", "");
			map.put("customername", "合计");
			List newfooter = new ArrayList();
			newfooter.add(map);
			pageData.setFooter(newfooter);
		}else{
			for(Map map : footer){
				if(null!=map){
					map.put("customerid", "");
					map.put("customername", "合计");
				}
			}
			pageData.setFooter(footer);
		}
		return pageData;
	}

    @Override
    public List<Map> getCustomerSalesAmountByQueryMap(Map map) throws Exception {

        List<Map> mapArrayList= financeFundsReturnMapper.getCustomerSalesAmountByQueryMap(map);
        Map sumMap = financeFundsReturnMapper.getCustomerSalesAmountSum(map);
        for(Map m : mapArrayList){
            m.put("amountsum",sumMap.get("amountsum"));
        }
        return mapArrayList ;
    }
	
	/*-----------------------品牌业务员考核表-----------------------------------*/
	@Override
	public boolean addBranduserAssess(BranduserAssess branduserAssess)
			throws Exception {
		SysUser sysUser = getSysUser();
		branduserAssess.setAdduserid(sysUser.getUserid());
		branduserAssess.setAddusername(sysUser.getName());
		return financeFundsReturnMapper.addBranduserAssess(branduserAssess) > 0;
	}
	
	@Override
	public boolean addBranduserAssessInfoExtend(
			BranduserAssessExtend branduserAssessExtend) throws Exception {
		SysUser sysUser = getSysUser();
		branduserAssessExtend.setAdduserid(sysUser.getUserid());
		branduserAssessExtend.setAddusername(sysUser.getName());
		return financeFundsReturnMapper.addBranduserAssessExtend(branduserAssessExtend) > 0;
	}

	@Override
	public Map addBranduserAssessExtend(
			BranduserAssessExtend branduserAssessExtend) throws Exception {
		Map map = new HashMap();
		SysUser sysUser = getSysUser();
		branduserAssessExtend.setAdduserid(sysUser.getUserid());
		branduserAssessExtend.setAddusername(sysUser.getName());
		boolean flag = financeFundsReturnMapper.addBranduserAssessExtend(branduserAssessExtend) > 0;
		if(flag){
			map = computeBranduserAssessExtend(branduserAssessExtend);
		}
		map.put("flag", flag);
		return map;
	}

	@Override
	public Map editBranduserAssessExtend(
			BranduserAssessExtend branduserAssessExtend) throws Exception {
		Map map = new HashMap();
		SysUser sysUser = getSysUser();
		branduserAssessExtend.setModifyuserid(sysUser.getUserid());
		branduserAssessExtend.setModifyusername(sysUser.getName());
		boolean flag = financeFundsReturnMapper.editBranduserAssessExtend(branduserAssessExtend) > 0;
		if(flag){
			map = computeBranduserAssessExtend(branduserAssessExtend);
		}
		map.put("flag", flag);
		return map;
	}

	/**
	 * 计算分月考核
	 * @param branduserAssessExtend
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Jul 2, 2014
	 */
	private Map computeBranduserAssessExtend(BranduserAssessExtend branduserAssessExtend)throws Exception{
		Map map = new HashMap();
		SysParam bonusParam = getBaseSysParamMapper().getSysParam("bonusnum");
		BigDecimal bonusnum = BigDecimal.ZERO;
		if(null != bonusParam){
			bonusnum = new BigDecimal(bonusParam.getPvalue());
		}
		SysParam returnParam = getBaseSysParamMapper().getSysParam("returnrate");
		BigDecimal returnrate = BigDecimal.ZERO;
		if(null != returnParam){
			returnrate = new BigDecimal(returnParam.getPvalue());
		}
		if(null != branduserAssessExtend){
			BigDecimal otherwdamount = (null != branduserAssessExtend.getOtherwdamount()) ? branduserAssessExtend.getOtherwdamount() : BigDecimal.ZERO;
			BigDecimal wdtargetamount = (null != branduserAssessExtend.getWdtargetamount()) ? branduserAssessExtend.getWdtargetamount() : BigDecimal.ZERO;
			BigDecimal kpitargetamount = (null != branduserAssessExtend.getKpitargetamount()) ? branduserAssessExtend.getKpitargetamount() : BigDecimal.ZERO;
			BigDecimal kpibonusamount = (null != branduserAssessExtend.getKpibonusamount()) ? branduserAssessExtend.getKpibonusamount() : BigDecimal.ZERO;
			BigDecimal wardenamount = (null != branduserAssessExtend.getWardenamount()) ? branduserAssessExtend.getWardenamount() : BigDecimal.ZERO;
			BigDecimal totalpassamount1 = (null != branduserAssessExtend.getTotalpassamount1()) ? branduserAssessExtend.getTotalpassamount1() : BigDecimal.ZERO;
			BigDecimal totalpasssubamount1 = (null != branduserAssessExtend.getTotalpasssubamount1()) ? branduserAssessExtend.getTotalpasssubamount1() : BigDecimal.ZERO;
			BigDecimal totalpassamount2 = (null != branduserAssessExtend.getTotalpassamount2()) ? branduserAssessExtend.getTotalpassamount2() : BigDecimal.ZERO;
			BigDecimal totalpasssubamount2 = (null != branduserAssessExtend.getTotalpasssubamount2()) ? branduserAssessExtend.getTotalpasssubamount2() : BigDecimal.ZERO;
			BigDecimal totalpassamount3 = (null != branduserAssessExtend.getTotalpassamount3()) ? branduserAssessExtend.getTotalpassamount3() : BigDecimal.ZERO;
			BigDecimal totalpasssubamount3 = (null != branduserAssessExtend.getTotalpasssubamount3()) ? branduserAssessExtend.getTotalpasssubamount3() : BigDecimal.ZERO;
			BigDecimal totalpassamount4 = (null != branduserAssessExtend.getTotalpassamount4()) ? branduserAssessExtend.getTotalpassamount4() : BigDecimal.ZERO;
			BigDecimal totalpasssubamount4 = (null != branduserAssessExtend.getTotalpasssubamount4()) ? branduserAssessExtend.getTotalpasssubamount4() : BigDecimal.ZERO;
			
			PageMap pageMap = new PageMap();
			pageMap.getCondition().put("businessdate", branduserAssessExtend.getBusinessdate());
			pageMap.getCondition().put("branduser", branduserAssessExtend.getBranduser());
			List<Map<String,Object>> list = financeFundsReturnMapper.getBranduserAssessReportExtendList(pageMap);
			
			Map<String,Object> dataObject = list.get(0);
			if(null != dataObject){
				String salesuser = (String)dataObject.get("salesuser");
				String branduser = (String)dataObject.get("branduser");
				Personnel salesuserPer = getPersonnelById(salesuser);
				if(null != salesuserPer){
					dataObject.put("salesusername", salesuserPer.getName());
				}else{
					dataObject.put("salesusername", "其他(未指定客户业务员)");
				}
				
				Personnel branduserPer = getPersonnelById(branduser);
				if(null!=branduserPer){
					dataObject.put("brandusername",branduserPer.getName());
				}else{
					dataObject.put("brandusername","其他(未指定品牌业务员)");
				}
				
				dataObject.put("otherwdamount", otherwdamount);
				dataObject.put("wdtargetamount", wdtargetamount);
				dataObject.put("kpitargetamount", kpitargetamount);
				dataObject.put("kpibonusamount", kpibonusamount);
				dataObject.put("wardenamount", wardenamount);
				dataObject.put("totalpassamount1", totalpassamount1);
				dataObject.put("totalpassamount2", totalpassamount2);
				dataObject.put("totalpassamount3", totalpassamount3);
				dataObject.put("totalpassamount4", totalpassamount4);
				dataObject.put("totalpasssubamount1", totalpasssubamount1);
				dataObject.put("totalpasssubamount2", totalpasssubamount2);
				dataObject.put("totalpasssubamount3", totalpasssubamount3);
				dataObject.put("totalpasssubamount4", totalpasssubamount4);
				//ERP回笼
				BigDecimal erpwdamount = (null != dataObject.get("taxamount")) ? (BigDecimal)dataObject.get("taxamount") : BigDecimal.ZERO;
				dataObject.put("erpwdamount", erpwdamount);
				
				//实际回笼 = 其他回笼 + ERP回笼
				BigDecimal realwdamount = otherwdamount.add(erpwdamount);
				dataObject.put("realwdamount", realwdamount);
				
				//得分 = 实际回笼/回笼指标*100
				BigDecimal totalscore = BigDecimal.ZERO;
				if(wdtargetamount.compareTo(BigDecimal.ZERO) != 0){
					totalscore = (realwdamount.divide(wdtargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}
				dataObject.put("totalscore", totalscore);
				
				//退货金额
				BigDecimal returnamount = (BigDecimal)dataObject.get("returnamount");
				dataObject.put("returnamount", returnamount);
				
				//退货扣减 = 退货金额*0.015（0.015根据系统参数表获取）
				BigDecimal returnsubamount = returnamount.multiply(returnrate);
				dataObject.put("returnsubamount", returnsubamount);
				
				//奖金 = 得分*19-退货扣减-超账扣减1-超账扣减2-超账扣减3-超账扣减4（19根据系统参数表获取）
				BigDecimal bonus = totalscore.multiply(bonusnum).subtract(returnsubamount).subtract(totalpasssubamount1).subtract(totalpasssubamount2).subtract(totalpasssubamount3).subtract(totalpasssubamount4);
				dataObject.put("bonus", bonus);
				
				//奖金总额 = 奖金+kpi奖金
				BigDecimal bonusamount = bonus.add(kpibonusamount);
				dataObject.put("bonusamount", bonusamount);
				
				//合计 = 奖金总额+区长
				BigDecimal amountsum = bonusamount.add(wardenamount);
				dataObject.put("amountsum", amountsum);
			}
			map.put("dataObject", dataObject);
		}
		
		//合计
		PageMap pageMap = branduserAssessExtend.getPageMap();
		if(null != pageMap){
			List<Map<String, Object>> footList = financeFundsReturnMapper.getBranduserAssessReportExtendSum(pageMap);
			Map<String,Object> footObject = footList.get(0);
			if(null != footObject){
				//其他回笼
				BigDecimal otherwdamount = BigDecimal.ZERO;
				//回笼指标
				BigDecimal wdtargetamount = BigDecimal.ZERO;
				//kpi目标
				BigDecimal kpitargetamount = BigDecimal.ZERO;
				//kpi奖金
				BigDecimal kpibonusamount = BigDecimal.ZERO;
				//区长
				BigDecimal wardenamount = BigDecimal.ZERO;
				//超账金额1
				BigDecimal totalpassamount1 = BigDecimal.ZERO;
				//超账扣减1
				BigDecimal totalpasssubamount1 = BigDecimal.ZERO;
				//超账金额2
				BigDecimal totalpassamount2 = BigDecimal.ZERO;
				//超账扣减2
				BigDecimal totalpasssubamount2 = BigDecimal.ZERO;
				//超账金额3
				BigDecimal totalpassamount3 = BigDecimal.ZERO;
				//超账扣减3
				BigDecimal totalpasssubamount3 = BigDecimal.ZERO;
				//超账金额4
				BigDecimal totalpassamount4 = BigDecimal.ZERO;
				//超账扣减4
				BigDecimal totalpasssubamount4 = BigDecimal.ZERO;
				//品牌业务员考核信息
				BranduserAssessExtend branduserAssessExtendSUM = financeFundsReturnMapper.getBranduserAssessExtendTotalSum(pageMap.getCondition());
				if(null != branduserAssessExtend){
					otherwdamount = (null != branduserAssessExtendSUM.getOtherwdamount()) ? branduserAssessExtendSUM.getOtherwdamount() : BigDecimal.ZERO;
					wdtargetamount = (null != branduserAssessExtendSUM.getWdtargetamount()) ? branduserAssessExtendSUM.getWdtargetamount() : BigDecimal.ZERO;
					kpitargetamount = (null != branduserAssessExtendSUM.getKpitargetamount()) ? branduserAssessExtendSUM.getKpitargetamount() : BigDecimal.ZERO;
					kpibonusamount = (null != branduserAssessExtendSUM.getKpibonusamount()) ? branduserAssessExtendSUM.getKpibonusamount() : BigDecimal.ZERO;
					wardenamount = (null != branduserAssessExtendSUM.getWardenamount()) ? branduserAssessExtendSUM.getWardenamount() : BigDecimal.ZERO;
					totalpassamount1 = (null != branduserAssessExtendSUM.getTotalpassamount1()) ? branduserAssessExtendSUM.getTotalpassamount1() : BigDecimal.ZERO;
					totalpasssubamount1 = (null != branduserAssessExtendSUM.getTotalpasssubamount1()) ? branduserAssessExtendSUM.getTotalpasssubamount1() : BigDecimal.ZERO;
					totalpassamount2 = (null != branduserAssessExtendSUM.getTotalpassamount2()) ? branduserAssessExtendSUM.getTotalpassamount2() : BigDecimal.ZERO;
					totalpasssubamount2 = (null != branduserAssessExtendSUM.getTotalpasssubamount2()) ? branduserAssessExtendSUM.getTotalpasssubamount2() : BigDecimal.ZERO;
					totalpassamount3 = (null != branduserAssessExtendSUM.getTotalpassamount3()) ? branduserAssessExtendSUM.getTotalpassamount3() : BigDecimal.ZERO;
					totalpasssubamount3 = (null != branduserAssessExtendSUM.getTotalpasssubamount3()) ? branduserAssessExtendSUM.getTotalpasssubamount3() : BigDecimal.ZERO;
					totalpassamount4 = (null != branduserAssessExtendSUM.getTotalpassamount4()) ? branduserAssessExtendSUM.getTotalpassamount4() : BigDecimal.ZERO;
					totalpasssubamount4 = (null != branduserAssessExtendSUM.getTotalpasssubamount4()) ? branduserAssessExtendSUM.getTotalpasssubamount4() : BigDecimal.ZERO;
				}
				footObject.put("otherwdamount", otherwdamount);
				footObject.put("wdtargetamount", wdtargetamount);
				footObject.put("kpitargetamount", kpitargetamount);
				footObject.put("kpibonusamount", kpibonusamount);
				footObject.put("wardenamount", wardenamount);
				footObject.put("totalpassamount1", totalpassamount1);
				footObject.put("totalpassamount2", totalpassamount2);
				footObject.put("totalpassamount3", totalpassamount3);
				footObject.put("totalpassamount4", totalpassamount4);
				footObject.put("totalpasssubamount1", totalpasssubamount1);
				footObject.put("totalpasssubamount2", totalpasssubamount2);
				footObject.put("totalpasssubamount3", totalpasssubamount3);
				footObject.put("totalpasssubamount4", totalpasssubamount4);
				footObject.put("brandusername","合计");
				
				//ERP回笼
				BigDecimal erpwdamount = (null != footObject.get("taxamount")) ? (BigDecimal)footObject.get("taxamount") : BigDecimal.ZERO;
				footObject.put("erpwdamount", erpwdamount);
				
				//实际回笼 = 其他回笼 + ERP回笼
				BigDecimal realwdamount = otherwdamount.add(erpwdamount);
				footObject.put("realwdamount", realwdamount);
				
				//得分 = 实际回笼/回笼指标*100
				BigDecimal totalscore = BigDecimal.ZERO;
				if(wdtargetamount.compareTo(BigDecimal.ZERO) != 0){
					totalscore = (realwdamount.divide(wdtargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}
				footObject.put("totalscore", totalscore);
				
				//退货金额
				BigDecimal returnamount = (BigDecimal)footObject.get("returnamount");
				footObject.put("returnamount", returnamount);
				
				//退货扣减 = 退货金额*0.015（0.015根据系统参数表获取）
				BigDecimal returnsubamount = returnamount.multiply(returnrate);
				footObject.put("returnsubamount", returnsubamount);
				
				//奖金 = 得分*19-退货扣减-超账扣减1-超账扣减2-超账扣减3-超账扣减4（19根据系统参数表获取）
				BigDecimal bonus = totalscore.multiply(bonusnum).subtract(returnsubamount).subtract(totalpasssubamount1).subtract(totalpasssubamount2).subtract(totalpasssubamount3).subtract(totalpasssubamount4);
				footObject.put("bonus", bonus);
				
				//奖金总额 = 奖金+kpi奖金
				BigDecimal bonusamount = bonus.add(kpibonusamount);
				footObject.put("bonusamount", bonusamount);
				
				//合计 = 奖金总额+区长
				BigDecimal amountsum = bonusamount.add(wardenamount);
				footObject.put("amountsum", amountsum);
			}
			map.put("footObject", footObject);
		}
		return map;
	}
	
	@Override
	public boolean deleteBranduserAssess(String id) throws Exception {
		return financeFundsReturnMapper.deleteBranduserAssess(id) > 0;
	}

	@Override
	public boolean editBranduserAssess(BranduserAssess branduserAssess)
			throws Exception {
		SysUser sysUser = getSysUser();
		branduserAssess.setModifyuserid(sysUser.getUserid());
		branduserAssess.setModifyusername(sysUser.getName());
		return financeFundsReturnMapper.editBranduserAssess(branduserAssess) > 0;
	}
	
	@Override
	public boolean editBranduserAssessInfoExtend(
			BranduserAssessExtend branduserAssessExtend) throws Exception {
		SysUser sysUser = getSysUser();
		branduserAssessExtend.setModifyuserid(sysUser.getUserid());
		branduserAssessExtend.setModifyusername(sysUser.getName());
		return financeFundsReturnMapper.editBranduserAssessExtend(branduserAssessExtend) > 0;
	}

	@Override
	public PageData getBranduserAssessData(PageMap pageMap) throws Exception {
		String sql = getDataAccessRule("t_report_branduser_assess",null); //数据权限
		pageMap.setDataSql(sql);
		List<BranduserAssess> list = financeFundsReturnMapper.getBranduserAssessList(pageMap);
		for(BranduserAssess branduserAssess : list){
			if(StringUtils.isNotEmpty(branduserAssess.getBranduser())){
				Personnel personnel = getPersonnelById(branduserAssess.getBranduser());
				if(null != personnel){
					branduserAssess.setBrandusername(personnel.getName());
				}
			}
			SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(branduserAssess.getWdbonusbase(), "wdbonusbase");
			if(null != sysCode){
				branduserAssess.setWdbonusbasename(sysCode.getCodename());
			}
		}
		
		PageData pageData = new PageData(financeFundsReturnMapper.getBranduserAssessCount(pageMap),list,pageMap);
		
		//合计
		List<BranduserAssess> footer = new ArrayList<BranduserAssess>(); 
		BranduserAssess branduserAssessSum = financeFundsReturnMapper.getBranduserAssessSum(pageMap);
		if(null != branduserAssessSum){
			branduserAssessSum.setBrandusername("合计");
			footer.add(branduserAssessSum);
		}
		pageData.setFooter(footer);
		return pageData;
	}
	
	@Override
	public PageData getBranduserAssessExtendData(PageMap pageMap)
			throws Exception {
		String sql = getDataAccessRule("t_report_branduser_assess_extend",null); //数据权限
		pageMap.setDataSql(sql);
		List<BranduserAssessExtend> list = financeFundsReturnMapper.getBranduserAssessExtendList(pageMap);
		for(BranduserAssessExtend branduserAssessExtend : list){
			if(StringUtils.isNotEmpty(branduserAssessExtend.getBranduser())){
				Personnel personnel = getPersonnelById(branduserAssessExtend.getBranduser());
				if(null != personnel){
					branduserAssessExtend.setBrandusername(personnel.getName());
				}
				SalesArea salesArea = getSalesareaByID(branduserAssessExtend.getSalesarea());
				if(null != salesArea){
					branduserAssessExtend.setSalesareaname(salesArea.getThisname());
				}
			}
		}
		
		PageData pageData = new PageData(financeFundsReturnMapper.getBranduserAssessExtendCount(pageMap),list,pageMap);
		return pageData;
	}

	@Override
	public BranduserAssess getBranduserAssessInfo(String id) throws Exception {
		return financeFundsReturnMapper.getBranduserAssessInfo(id);
	}

	@Override
	public Map addDRBranduserAssess(List<BranduserAssess> list)
			throws Exception {
		int success = 0,failure = 0,reptnum = 0;
		String failStr = "",msg = "",reptstr = "";
		for(BranduserAssess branduserAssess : list){
			Map map2 = new HashMap();
			map2.put("businessdate", branduserAssess.getBusinessdate());
			map2.put("branduser", branduserAssess.getBranduser());
			boolean check = financeFundsReturnMapper.checkBranduserAssess(map2) > 0;
			if(check){//存在，则覆盖
				BranduserAssess bAssess = financeFundsReturnMapper.getBranduserAssessByParam(map2);
				branduserAssess.setId(bAssess.getId());
				boolean flag = financeFundsReturnMapper.editBranduserAssess(branduserAssess) > 0;
				if(flag){
					reptnum++;
					if(StringUtils.isEmpty(reptstr)){
						reptstr = branduserAssess.getBranduser();
					}else{
						reptstr += "," + branduserAssess.getBranduser();
					}
				}else{
					failure++;
					if(StringUtils.isEmpty(failStr)){
						failStr = branduserAssess.getBranduser();
					}else{
						failStr += "," + branduserAssess.getBranduser();
					}
				}
			}else{
				boolean flag = financeFundsReturnMapper.addBranduserAssess(branduserAssess) > 0;
				if(flag){
					success++;
				}else{
					failure++;
					if(StringUtils.isEmpty(failStr)){
						failStr = branduserAssess.getBranduser();
					}else{
						failStr += "," + branduserAssess.getBranduser();
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(reptstr)){
			msg = "覆盖" + reptnum +"条数据;品牌业务员编码:"+reptstr + "覆盖;";
		}
		Map map = new HashMap();
		map.put("success", success);
		map.put("failure", failure);
		map.put("failStr", failStr);
		map.put("msg", msg);
		return map;
	}

	@Override
	public boolean checkBranduserAssess(Map map) throws Exception {
		return financeFundsReturnMapper.checkBranduserAssess(map) > 0;
	}

	@Override
	public BranduserAssess getBranduserAssessByParam(Map map) throws Exception {
		return financeFundsReturnMapper.getBranduserAssessByParam(map);
	}

	@Override
	public PageData getBranduserAssessReportData(PageMap pageMap)
			throws Exception {
		List<BranduserAssessReport> list = financeFundsReturnMapper.getBranduserAssessReportList(pageMap);
		//回笼奖金金额合计
		BigDecimal totalwdbonusamount = BigDecimal.ZERO;
		//退货标准
		BigDecimal totalretstandardamount = BigDecimal.ZERO;
		//超退货部分
		BigDecimal totalsuperretamount = BigDecimal.ZERO;
		//合计得分
		BigDecimal totaltotalscore = BigDecimal.ZERO;
		for(BranduserAssessReport branduserAssessReport : list){
			//品牌业务员
			Personnel personnel = getPersonnelById(branduserAssessReport.getBranduser());
			if(null != personnel){
				branduserAssessReport.setBrandusername(personnel.getName());
			}
			//退货标准=回笼实绩金额*3%
			if(null != branduserAssessReport.getWdaccomplishamount()){
				branduserAssessReport.setRetstandardamount(branduserAssessReport.getWdaccomplishamount().multiply(new BigDecimal("0.03")).setScale(2, BigDecimal.ROUND_HALF_UP));
				totalretstandardamount = totalretstandardamount.add(branduserAssessReport.getRetstandardamount());
			}
			//超退货部分=实绩退货-退货标准，若为负数显示为0，为正数显示差额
			if(null != branduserAssessReport.getRetaccomplishamount() && null != branduserAssessReport.getRetstandardamount()){
				BigDecimal superretamount = branduserAssessReport.getRetaccomplishamount().subtract(branduserAssessReport.getRetstandardamount());
				if(superretamount.compareTo(BigDecimal.ZERO) == -1){
					branduserAssessReport.setSuperretamount(BigDecimal.ZERO);
				}else if(superretamount.compareTo(BigDecimal.ZERO) >= 0){
					branduserAssessReport.setSuperretamount(superretamount);
				}
				totalsuperretamount = totalsuperretamount.add(branduserAssessReport.getSuperretamount());
			}
			//核算奖金基数=回笼实绩-超退货部分
			if(null != branduserAssessReport.getWdaccomplishamount() && null != branduserAssessReport.getSuperretamount()){
				branduserAssessReport.setCheckbonusbase(branduserAssessReport.getWdaccomplishamount().subtract(branduserAssessReport.getSuperretamount()));
			}
			//合计得分=核算奖金基数/回笼目标金额
			if(null != branduserAssessReport.getCheckbonusbase() && null != branduserAssessReport.getWdtargetamount()
					&& branduserAssessReport.getWdtargetamount().compareTo(BigDecimal.ZERO) != 0
			){
				branduserAssessReport.setTotalscore(branduserAssessReport.getCheckbonusbase().divide(branduserAssessReport.getWdtargetamount(), 2, BigDecimal.ROUND_HALF_UP));
				totaltotalscore = totaltotalscore.add(branduserAssessReport.getTotalscore());
			}
			//回笼奖金金额=合计得分*回笼奖金基数
			if(null != branduserAssessReport.getTotalscore() && null != branduserAssessReport.getWdbonusbase()){
				SysCode sysCode = getBaseSysCodeMapper().getSysCodeInfo(branduserAssessReport.getWdbonusbase(), "wdbonusbase");
				if(null != sysCode){
					BigDecimal wdbonusbase = new BigDecimal(sysCode.getCodename());
					branduserAssessReport.setWdbonusamount(branduserAssessReport.getTotalscore().multiply(wdbonusbase));
					totalwdbonusamount = totalwdbonusamount.add(branduserAssessReport.getWdbonusamount());
				}
			}
			//本月合计奖金=回笼奖金金额+kpi奖金金额
			if( null != branduserAssessReport.getWdbonusamount() && null != branduserAssessReport.getKpibonusamount()){
				branduserAssessReport.setMonthtotalamount(branduserAssessReport.getWdbonusamount().add(branduserAssessReport.getKpibonusamount()));
			}
		}
		
		PageData pageData = new PageData(financeFundsReturnMapper.getBranduserAssessReportCount(pageMap),list,pageMap);
		
		//合计
		List<BranduserAssessReport> footer = new ArrayList<BranduserAssessReport>();
		BranduserAssessReport bAssessReportSum = financeFundsReturnMapper.getBranduserAssessReportSum(pageMap);
		if(null != bAssessReportSum){
			//品牌业务员
			bAssessReportSum.setBrandusername("合计");
			//退货标准=回笼实绩金额*3%
			bAssessReportSum.setRetstandardamount(totalretstandardamount);
			//超退货部分=实绩退货-退货标准，若为负数显示为0，为正数显示差额
			bAssessReportSum.setSuperretamount(totalsuperretamount);
			//核算奖金基数=回笼实绩-超退货部分
			if(null != bAssessReportSum.getWdaccomplishamount() && null != bAssessReportSum.getSuperretamount()){
				bAssessReportSum.setCheckbonusbase(bAssessReportSum.getWdaccomplishamount().subtract(bAssessReportSum.getSuperretamount()));
			}
			//合计得分=核算奖金基数/回笼目标金额
			bAssessReportSum.setTotalscore(totaltotalscore);
			//回笼奖金金额=合计得分*回笼奖金基数
			bAssessReportSum.setWdbonusamount(totalwdbonusamount);
			//本月合计奖金=回笼奖金金额+kpi奖金金额
			if( null != bAssessReportSum.getWdbonusamount() && null != bAssessReportSum.getKpibonusamount()){
				bAssessReportSum.setMonthtotalamount(bAssessReportSum.getWdbonusamount().add(bAssessReportSum.getKpibonusamount()));
			}
			footer.add(bAssessReportSum);
		}
		pageData.setFooter(footer);
		return pageData;
	}

	
	@Override
	public PageData getBranduserAssessReportExtendData(PageMap pageMap)
			throws Exception {
		PageMap copyPageMap = new PageMap();
		Map copyConditonMap = new HashMap();
		copyConditonMap.putAll(pageMap.getCondition());
		copyPageMap.setCondition(copyConditonMap);
		copyPageMap.setDataSql(pageMap.getDataSql());
		
		SysParam bonusParam = getBaseSysParamMapper().getSysParam("bonusnum");
		BigDecimal bonusnum = BigDecimal.ZERO;
		if(null != bonusParam){
			bonusnum = new BigDecimal(bonusParam.getPvalue());
		}
		SysParam returnParam = getBaseSysParamMapper().getSysParam("returnrate");
		BigDecimal returnrate = BigDecimal.ZERO;
		if(null != returnParam){
			returnrate = new BigDecimal(returnParam.getPvalue());
		}
		String businessdate = (String)pageMap.getCondition().get("businessdate");
		
		List<Map<String,Object>> list2 = new ArrayList<Map<String,Object>>();
		
		List<SalesArea> areaList = getBaseSalesAreaMapper().getFirstLevelSalesAreaList();
		if(areaList.size() != 0){
			for(SalesArea salesArea : areaList){
				//小计品牌业务员考核表数据金额
				BigDecimal otherwdamountSub = BigDecimal.ZERO;
				BigDecimal wdtargetamountSub = BigDecimal.ZERO;
				BigDecimal kpitargetamountSub = BigDecimal.ZERO;
				BigDecimal kpibonusamountSub = BigDecimal.ZERO;
				BigDecimal wardenamountSub = BigDecimal.ZERO;
				BigDecimal totalpassamount1Sub = BigDecimal.ZERO;
				BigDecimal totalpasssubamount1Sub = BigDecimal.ZERO;
				BigDecimal totalpassamount2Sub = BigDecimal.ZERO;
				BigDecimal totalpasssubamount2Sub = BigDecimal.ZERO;
				BigDecimal totalpassamount3Sub = BigDecimal.ZERO;
				BigDecimal totalpasssubamount3Sub = BigDecimal.ZERO;
				BigDecimal totalpassamount4Sub = BigDecimal.ZERO;
				BigDecimal totalpasssubamount4Sub = BigDecimal.ZERO;
				
				copyPageMap.getCondition().put("salesarea", salesArea.getId());
				List<Map<String,Object>> partList = financeFundsReturnMapper.getBranduserAssessReportExtendList(copyPageMap);
				for(Map<String,Object> dataObject : partList){
					String salesarea = (String)dataObject.get("salesarea");
					String branduser = (String)dataObject.get("branduser");
					
					SalesArea saleArea = getSalesareaByID(salesarea);
					if(null != saleArea){
						dataObject.put("salesareaname", saleArea.getThisname());
					}else{
						dataObject.put("salesareaname", "其他(未指定区域)");
					}
					
					Personnel branduserPer = getPersonnelById(branduser);
					if(null!=branduserPer){
						dataObject.put("brandusername",branduserPer.getName());
					}else{
						dataObject.put("brandusername","其他(未指定品牌业务员)");
					}
					
					//其他回笼
					BigDecimal otherwdamount = BigDecimal.ZERO;
					//回笼指标
					BigDecimal wdtargetamount = BigDecimal.ZERO;
					//kpi目标
					BigDecimal kpitargetamount = BigDecimal.ZERO;
					//kpi奖金
					BigDecimal kpibonusamount = BigDecimal.ZERO;
					//区长
					BigDecimal wardenamount = BigDecimal.ZERO;
					//超账金额1
					BigDecimal totalpassamount1 = BigDecimal.ZERO;
					//超账扣减1
					BigDecimal totalpasssubamount1 = BigDecimal.ZERO;
					//超账金额2
					BigDecimal totalpassamount2 = BigDecimal.ZERO;
					//超账扣减2
					BigDecimal totalpasssubamount2 = BigDecimal.ZERO;
					//超账金额3
					BigDecimal totalpassamount3 = BigDecimal.ZERO;
					//超账扣减3
					BigDecimal totalpasssubamount3 = BigDecimal.ZERO;
					//超账金额4
					BigDecimal totalpassamount4 = BigDecimal.ZERO;
					//超账扣减4
					BigDecimal totalpasssubamount4 = BigDecimal.ZERO;
					
					//品牌业务员考核信息
					Map paramMap = new HashMap();
					paramMap.put("businessdate", businessdate);
					paramMap.put("branduser", branduser);
					paramMap.put("salesarea", salesarea);
					BranduserAssessExtend branduserAssessExtend = financeFundsReturnMapper.getBranduserAssessExtendByParam(paramMap);
					if(null != branduserAssessExtend){
						otherwdamount = (null != branduserAssessExtend.getOtherwdamount()) ? branduserAssessExtend.getOtherwdamount() : BigDecimal.ZERO;
						wdtargetamount = (null != branduserAssessExtend.getWdtargetamount()) ? branduserAssessExtend.getWdtargetamount() : BigDecimal.ZERO;
						kpitargetamount = (null != branduserAssessExtend.getKpitargetamount()) ? branduserAssessExtend.getKpitargetamount() : BigDecimal.ZERO;
						kpibonusamount = (null != branduserAssessExtend.getKpibonusamount()) ? branduserAssessExtend.getKpibonusamount() : BigDecimal.ZERO;
						wardenamount = (null != branduserAssessExtend.getWardenamount()) ? branduserAssessExtend.getWardenamount() : BigDecimal.ZERO;
						totalpassamount1 = (null != branduserAssessExtend.getTotalpassamount1()) ? branduserAssessExtend.getTotalpassamount1() : BigDecimal.ZERO;
						totalpasssubamount1 = (null != branduserAssessExtend.getTotalpasssubamount1()) ? branduserAssessExtend.getTotalpasssubamount1() : BigDecimal.ZERO;
						totalpassamount2 = (null != branduserAssessExtend.getTotalpassamount2()) ? branduserAssessExtend.getTotalpassamount2() : BigDecimal.ZERO;
						totalpasssubamount2 = (null != branduserAssessExtend.getTotalpasssubamount2()) ? branduserAssessExtend.getTotalpasssubamount2() : BigDecimal.ZERO;
						totalpassamount3 = (null != branduserAssessExtend.getTotalpassamount3()) ? branduserAssessExtend.getTotalpassamount3() : BigDecimal.ZERO;
						totalpasssubamount3 = (null != branduserAssessExtend.getTotalpasssubamount3()) ? branduserAssessExtend.getTotalpasssubamount3() : BigDecimal.ZERO;
						totalpassamount4 = (null != branduserAssessExtend.getTotalpassamount4()) ? branduserAssessExtend.getTotalpassamount4() : BigDecimal.ZERO;
						totalpasssubamount4 = (null != branduserAssessExtend.getTotalpasssubamount4()) ? branduserAssessExtend.getTotalpasssubamount4() : BigDecimal.ZERO;
						
						otherwdamountSub = otherwdamountSub.add(otherwdamount);
						wdtargetamountSub = wdtargetamountSub.add(wdtargetamount);
						kpitargetamountSub = kpitargetamountSub.add(kpitargetamount);
						kpibonusamountSub = kpibonusamountSub.add(kpibonusamount);
						wardenamountSub = wardenamountSub.add(wardenamount);
						totalpassamount1Sub = totalpassamount1Sub.add(totalpassamount1);
						totalpasssubamount1Sub = totalpasssubamount1Sub.add(totalpasssubamount1);
						totalpassamount2Sub = totalpassamount2Sub.add(totalpassamount2);
						totalpasssubamount2Sub = totalpasssubamount2Sub.add(totalpasssubamount2);
						totalpassamount3Sub = totalpassamount3Sub.add(totalpassamount3);
						totalpasssubamount3Sub = totalpasssubamount3Sub.add(totalpasssubamount3);
						totalpassamount4Sub = totalpassamount4Sub.add(totalpassamount4);
						totalpasssubamount4Sub = totalpasssubamount4Sub.add(totalpasssubamount4);
					}
					dataObject.put("otherwdamount", otherwdamount);
					dataObject.put("wdtargetamount", wdtargetamount);
					dataObject.put("kpitargetamount", kpitargetamount);
					dataObject.put("kpibonusamount", kpibonusamount);
					dataObject.put("wardenamount", wardenamount);
					dataObject.put("totalpassamount1", totalpassamount1);
					dataObject.put("totalpassamount2", totalpassamount2);
					dataObject.put("totalpassamount3", totalpassamount3);
					dataObject.put("totalpassamount4", totalpassamount4);
					dataObject.put("totalpasssubamount1", totalpasssubamount1);
					dataObject.put("totalpasssubamount2", totalpasssubamount2);
					dataObject.put("totalpasssubamount3", totalpasssubamount3);
					dataObject.put("totalpasssubamount4", totalpasssubamount4);
					
					//ERP回笼
					BigDecimal erpwdamount = (null != dataObject.get("taxamount")) ? (BigDecimal)dataObject.get("taxamount") : BigDecimal.ZERO;
					dataObject.put("erpwdamount", erpwdamount);
					
					//实际回笼 = 其他回笼 + ERP回笼
					BigDecimal realwdamount = otherwdamount.add(erpwdamount);
					dataObject.put("realwdamount", realwdamount);
					
					//得分 = 实际回笼/回笼指标*100
					BigDecimal totalscore = BigDecimal.ZERO;
					if(wdtargetamount.compareTo(BigDecimal.ZERO) != 0){
						totalscore = (realwdamount.divide(wdtargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					}
					dataObject.put("totalscore", totalscore);
					
					//退货金额
					BigDecimal returnamount = (BigDecimal)dataObject.get("returnamount");
					dataObject.put("returnamount", returnamount);
					
					//退货扣减 = 退货金额*0.015（0.015根据系统参数表获取）
					BigDecimal returnsubamount = returnamount.multiply(returnrate);
					dataObject.put("returnsubamount", returnsubamount);
					
					//奖金 = 得分*19-退货扣减-超账扣减1-超账扣减2-超账扣减3-超账扣减4（19根据系统参数表获取）
					BigDecimal bonus = totalscore.multiply(bonusnum).subtract(returnsubamount).subtract(totalpasssubamount1).subtract(totalpasssubamount2).subtract(totalpasssubamount3).subtract(totalpasssubamount4);
					dataObject.put("bonus", bonus);
					
					//奖金总额 = 奖金+kpi奖金
					BigDecimal bonusamount = bonus.add(kpibonusamount);
					dataObject.put("bonusamount", bonusamount);
					
					//合计 = 奖金总额+区长
					BigDecimal amountsum = bonusamount.add(wardenamount);
					dataObject.put("amountsum", amountsum);
				}
				list2.addAll(partList);
				
				//小计
				List<Map<String,Object>> subList = financeFundsReturnMapper.getBranduserAssessReportExtendSum(copyPageMap);
				for(Map<String,Object> dataObject : subList){
					if(null != dataObject){
						dataObject.put("branduser", "999999");
						dataObject.put("brandusername", salesArea.getThisname());
						
						dataObject.put("otherwdamount", otherwdamountSub);
						dataObject.put("wdtargetamount", wdtargetamountSub);
						dataObject.put("kpitargetamount", kpitargetamountSub);
						dataObject.put("kpibonusamount", kpibonusamountSub);
						dataObject.put("wardenamount", wardenamountSub);
						dataObject.put("totalpassamount1", totalpassamount1Sub);
						dataObject.put("totalpassamount2", totalpassamount2Sub);
						dataObject.put("totalpassamount3", totalpassamount3Sub);
						dataObject.put("totalpassamount4", totalpassamount4Sub);
						dataObject.put("totalpasssubamount1", totalpasssubamount1Sub);
						dataObject.put("totalpasssubamount2", totalpasssubamount2Sub);
						dataObject.put("totalpasssubamount3", totalpasssubamount3Sub);
						dataObject.put("totalpasssubamount4", totalpasssubamount4Sub);
						
						//ERP回笼
						BigDecimal erpwdamountSub = (null != dataObject.get("taxamount")) ? (BigDecimal)dataObject.get("taxamount") : BigDecimal.ZERO;
						dataObject.put("erpwdamount", erpwdamountSub);
						
						//实际回笼 = 其他回笼 + ERP回笼
						BigDecimal realwdamountSub = otherwdamountSub.add(erpwdamountSub);
						dataObject.put("realwdamount", realwdamountSub);
						
						//得分 = 实际回笼/回笼指标*100
						BigDecimal totalscoreSub = BigDecimal.ZERO;
						if(wdtargetamountSub.compareTo(BigDecimal.ZERO) != 0){
							totalscoreSub = (realwdamountSub.divide(wdtargetamountSub, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
						}
						dataObject.put("totalscore", totalscoreSub);
						
						//退货金额
						BigDecimal returnamountSub = (BigDecimal)dataObject.get("returnamount");
						dataObject.put("returnamount", returnamountSub);
						
						//退货扣减 = 退货金额*0.015（0.015根据系统参数表获取）
						BigDecimal returnsubamountSub = returnamountSub.multiply(returnrate);
						dataObject.put("returnsubamount", returnsubamountSub);
						
						//奖金 = 得分*19-退货扣减-超账扣减1-超账扣减2-超账扣减3-超账扣减4（19根据系统参数表获取）
						BigDecimal bonusSub = totalscoreSub.multiply(bonusnum).subtract(returnsubamountSub).subtract(totalpasssubamount1Sub).subtract(totalpasssubamount2Sub).subtract(totalpasssubamount3Sub).subtract(totalpasssubamount4Sub);
						dataObject.put("bonus", bonusSub);
						
						//奖金总额 = 奖金+kpi奖金
						BigDecimal bonusamountSub = bonusSub.add(kpibonusamountSub);
						dataObject.put("bonusamount", bonusamountSub);
						
						//合计 = 奖金总额+区长
						BigDecimal amountsumSub = bonusamountSub.add(wardenamountSub);
						dataObject.put("amountsum", amountsumSub);
					}
					else{
						subList = new ArrayList<Map<String,Object>>();
					}
				}
				list2.addAll(subList);
			}
		}
		PageData pageData = new PageData(list2.size(),list2,pageMap);
		
		//合计
		List<Map<String, Object>> footList = financeFundsReturnMapper.getBranduserAssessReportExtendSum(pageMap);
		for(Map<String, Object> objectSum : footList){
			if(null != objectSum){
				//其他回笼
				BigDecimal otherwdamount = BigDecimal.ZERO;
				//回笼指标
				BigDecimal wdtargetamount = BigDecimal.ZERO;
				//kpi目标
				BigDecimal kpitargetamount = BigDecimal.ZERO;
				//kpi奖金
				BigDecimal kpibonusamount = BigDecimal.ZERO;
				//区长
				BigDecimal wardenamount = BigDecimal.ZERO;
				//超账金额1
				BigDecimal totalpassamount1 = BigDecimal.ZERO;
				//超账扣减1
				BigDecimal totalpasssubamount1 = BigDecimal.ZERO;
				//超账金额2
				BigDecimal totalpassamount2 = BigDecimal.ZERO;
				//超账扣减2
				BigDecimal totalpasssubamount2 = BigDecimal.ZERO;
				//超账金额3
				BigDecimal totalpassamount3 = BigDecimal.ZERO;
				//超账扣减3
				BigDecimal totalpasssubamount3 = BigDecimal.ZERO;
				//超账金额4
				BigDecimal totalpassamount4 = BigDecimal.ZERO;
				//超账扣减4
				BigDecimal totalpasssubamount4 = BigDecimal.ZERO;
				//品牌业务员考核信息
				BranduserAssessExtend branduserAssessExtend = financeFundsReturnMapper.getBranduserAssessExtendTotalSum(pageMap.getCondition());
				if(null != branduserAssessExtend){
					otherwdamount = (null != branduserAssessExtend.getOtherwdamount()) ? branduserAssessExtend.getOtherwdamount() : BigDecimal.ZERO;
					wdtargetamount = (null != branduserAssessExtend.getWdtargetamount()) ? branduserAssessExtend.getWdtargetamount() : BigDecimal.ZERO;
					kpitargetamount = (null != branduserAssessExtend.getKpitargetamount()) ? branduserAssessExtend.getKpitargetamount() : BigDecimal.ZERO;
					kpibonusamount = (null != branduserAssessExtend.getKpibonusamount()) ? branduserAssessExtend.getKpibonusamount() : BigDecimal.ZERO;
					wardenamount = (null != branduserAssessExtend.getWardenamount()) ? branduserAssessExtend.getWardenamount() : BigDecimal.ZERO;
					totalpassamount1 = (null != branduserAssessExtend.getTotalpassamount1()) ? branduserAssessExtend.getTotalpassamount1() : BigDecimal.ZERO;
					totalpasssubamount1 = (null != branduserAssessExtend.getTotalpasssubamount1()) ? branduserAssessExtend.getTotalpasssubamount1() : BigDecimal.ZERO;
					totalpassamount2 = (null != branduserAssessExtend.getTotalpassamount2()) ? branduserAssessExtend.getTotalpassamount2() : BigDecimal.ZERO;
					totalpasssubamount2 = (null != branduserAssessExtend.getTotalpasssubamount2()) ? branduserAssessExtend.getTotalpasssubamount2() : BigDecimal.ZERO;
					totalpassamount3 = (null != branduserAssessExtend.getTotalpassamount3()) ? branduserAssessExtend.getTotalpassamount3() : BigDecimal.ZERO;
					totalpasssubamount3 = (null != branduserAssessExtend.getTotalpasssubamount3()) ? branduserAssessExtend.getTotalpasssubamount3() : BigDecimal.ZERO;
					totalpassamount4 = (null != branduserAssessExtend.getTotalpassamount4()) ? branduserAssessExtend.getTotalpassamount4() : BigDecimal.ZERO;
					totalpasssubamount4 = (null != branduserAssessExtend.getTotalpasssubamount4()) ? branduserAssessExtend.getTotalpasssubamount4() : BigDecimal.ZERO;
				}
				objectSum.put("otherwdamount", otherwdamount);
				objectSum.put("wdtargetamount", wdtargetamount);
				objectSum.put("kpitargetamount", kpitargetamount);
				objectSum.put("kpibonusamount", kpibonusamount);
				objectSum.put("wardenamount", wardenamount);
				objectSum.put("totalpassamount1", totalpassamount1);
				objectSum.put("totalpassamount2", totalpassamount2);
				objectSum.put("totalpassamount3", totalpassamount3);
				objectSum.put("totalpassamount4", totalpassamount4);
				objectSum.put("totalpasssubamount1", totalpasssubamount1);
				objectSum.put("totalpasssubamount2", totalpasssubamount2);
				objectSum.put("totalpasssubamount3", totalpasssubamount3);
				objectSum.put("totalpasssubamount4", totalpasssubamount4);
				objectSum.put("brandusername","合计");
				
				//ERP回笼
				BigDecimal erpwdamount = (null != objectSum.get("taxamount")) ? (BigDecimal)objectSum.get("taxamount") : BigDecimal.ZERO;
				objectSum.put("erpwdamount", erpwdamount);
				
				//实际回笼 = 其他回笼 + ERP回笼
				BigDecimal realwdamount = otherwdamount.add(erpwdamount);
				objectSum.put("realwdamount", realwdamount);
				
				//得分 = 实际回笼/回笼指标*100
				BigDecimal totalscore = BigDecimal.ZERO;
				if(wdtargetamount.compareTo(BigDecimal.ZERO) != 0){
					totalscore = (realwdamount.divide(wdtargetamount, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}
				objectSum.put("totalscore", totalscore);
				
				//退货金额
				BigDecimal returnamount = (BigDecimal)objectSum.get("returnamount");
				objectSum.put("returnamount", returnamount);
				
				//退货扣减 = 退货金额*0.015（0.015根据系统参数表获取）
				BigDecimal returnsubamount = returnamount.multiply(returnrate);
				objectSum.put("returnsubamount", returnsubamount);
				
				//奖金 = 得分*19-退货扣减-超账扣减1-超账扣减2-超账扣减3-超账扣减4（19根据系统参数表获取）
				BigDecimal bonus = totalscore.multiply(bonusnum).subtract(returnsubamount).subtract(totalpasssubamount1).subtract(totalpasssubamount2).subtract(totalpasssubamount3).subtract(totalpasssubamount4);
				objectSum.put("bonus", bonus);
				
				//奖金总额 = 奖金+kpi奖金
				BigDecimal bonusamount = bonus.add(kpibonusamount);
				objectSum.put("bonusamount", bonusamount);
				
				//合计 = 奖金总额+区长
				BigDecimal amountsum = bonusamount.add(wardenamount);
				objectSum.put("amountsum", amountsum);
			}
			else{
				footList = new ArrayList<Map<String,Object>>();
			}
		}
		pageData.setFooter(footList);
		return pageData;
	}
	
	@Override
	public BranduserAssessExtend getBranduserAssessExtendByParam(Map map)
			throws Exception {
		return financeFundsReturnMapper.getBranduserAssessExtendByParam(map);
	}

	@Override
	public boolean getTotalPassAmount(Map map) throws Exception {
		String businessdate = (null != map.get("businessdate")) ? (String)map.get("businessdate") : "";
		String passnum = (null != map.get("passnum")) ? (String)map.get("passnum") : "";
		//超账扣减率
		SysParam passParam = getBaseSysParamMapper().getSysParam("passrate");
		BigDecimal passrate = BigDecimal.ZERO;
		if(null != passParam){
			passrate = new BigDecimal(passParam.getPvalue());
		}
		SysUser sysUser = getSysUser();
		List<Map<String, Object>> list = financeFundsReturnMapper.getTotalPassAmount(map);
		for(Map<String, Object> obj : list){
			String branduser = (String)obj.get("branduser");
			String salesarea = (String)obj.get("salesarea");
			
			//超账金额 
			BigDecimal totalpassamount = (BigDecimal)obj.get("totalpassamount");
			//超账扣减 = 超账金额*0.002（0.002根据系统参数表获取）
			BigDecimal totalpasssubamount = totalpassamount.multiply(passrate);
			
			map.put("branduser", branduser);
			map.put("salesarea", salesarea);
			BranduserAssessExtend branduserAssessExtend = financeFundsReturnMapper.getBranduserAssessExtendByParam(map);
			//判断是否存在品牌业务员考核是否存在
			if(null != branduserAssessExtend){
				if("1".equals(passnum)){//超账金额1
					branduserAssessExtend.setTotalpassamount1(totalpassamount);
					branduserAssessExtend.setTotalpasssubamount1(totalpasssubamount);
				}else if("2".equals(passnum)){
					branduserAssessExtend.setTotalpassamount2(totalpassamount);
					branduserAssessExtend.setTotalpasssubamount2(totalpasssubamount);
				}else if("3".equals(passnum)){
					branduserAssessExtend.setTotalpassamount3(totalpassamount);
					branduserAssessExtend.setTotalpasssubamount3(totalpasssubamount);
				}else if("4".equals(passnum)){
					branduserAssessExtend.setTotalpassamount4(totalpassamount);
					branduserAssessExtend.setTotalpasssubamount4(totalpasssubamount);
				}
				branduserAssessExtend.setModifyuserid(sysUser.getUserid());
				branduserAssessExtend.setModifyusername(sysUser.getName());
				financeFundsReturnMapper.editBranduserAssessExtend(branduserAssessExtend);
			}else{
				branduserAssessExtend = new BranduserAssessExtend();
				branduserAssessExtend.setBranduser(branduser);
				branduserAssessExtend.setBusinessdate(businessdate);
				branduserAssessExtend.setSalesarea(salesarea);
				if("1".equals(passnum)){//超账金额1
					branduserAssessExtend.setTotalpassamount1(totalpassamount);
					branduserAssessExtend.setTotalpasssubamount1(totalpasssubamount);
				}else if("2".equals(passnum)){
					branduserAssessExtend.setTotalpassamount2(totalpassamount);
					branduserAssessExtend.setTotalpasssubamount2(totalpasssubamount);
				}else if("3".equals(passnum)){
					branduserAssessExtend.setTotalpassamount3(totalpassamount);
					branduserAssessExtend.setTotalpasssubamount3(totalpasssubamount);
				}else if("4".equals(passnum)){
					branduserAssessExtend.setTotalpassamount4(totalpassamount);
					branduserAssessExtend.setTotalpasssubamount4(totalpasssubamount);
				}
				branduserAssessExtend.setAdduserid(sysUser.getUserid());
				branduserAssessExtend.setAddusername(sysUser.getName());
				financeFundsReturnMapper.addBranduserAssessExtend(branduserAssessExtend);
			}
		}
		return true;
	}

	@Override
	public boolean checkBranduserAssessExtend(Map map) throws Exception {
		return financeFundsReturnMapper.checkBranduserAssessExtend(map) > 0;
	}

	@Override
	public boolean deleteBranduserAssessExtend(String id) throws Exception {
		return financeFundsReturnMapper.deleteBranduserAssessExtend(id) > 0;
	}

	@Override
	public Map addDRbranduserAssessExtend(List<BranduserAssessExtend> list)
			throws Exception {
		int success = 0,failure = 0,reptnum = 0;
		String failStr = "",msg = "",reptstr = "";
		for(BranduserAssessExtend branduserAssessExtend : list){
			SysUser sysUser = getSysUser();
			Map map2 = new HashMap();
			map2.put("businessdate", branduserAssessExtend.getBusinessdate());
			map2.put("branduser", branduserAssessExtend.getBranduser());
			map2.put("salesarea", branduserAssessExtend.getSalesarea());
			boolean check = financeFundsReturnMapper.checkBranduserAssessExtend(map2) > 0;
			if(check){//存在，则覆盖
				BranduserAssessExtend bAssessExtend = financeFundsReturnMapper.getBranduserAssessExtendByParam(map2);
				branduserAssessExtend.setId(bAssessExtend.getId());
				branduserAssessExtend.setModifyuserid(sysUser.getUserid());
				branduserAssessExtend.setModifyusername(sysUser.getName());
				boolean flag = financeFundsReturnMapper.editBranduserAssessExtend(branduserAssessExtend) > 0;
				if(flag){
					reptnum++;
					if(StringUtils.isEmpty(reptstr)){
						reptstr = branduserAssessExtend.getBranduser();
					}else{
						reptstr += "," + branduserAssessExtend.getBranduser();
					}
				}else{
					failure++;
					if(StringUtils.isEmpty(failStr)){
						failStr = branduserAssessExtend.getBranduser();
					}else{
						failStr += "," + branduserAssessExtend.getBranduser();
					}
				}
			}else{
				branduserAssessExtend.setAdduserid(sysUser.getUserid());
				branduserAssessExtend.setAddusername(sysUser.getName());
				boolean flag = financeFundsReturnMapper.addBranduserAssessExtend(branduserAssessExtend) > 0;
				if(flag){
					success++;
				}else{
					failure++;
					if(StringUtils.isEmpty(failStr)){
						failStr = branduserAssessExtend.getBranduser();
					}else{
						failStr += "," + branduserAssessExtend.getBranduser();
					}
				}
			}
		}
		if(StringUtils.isNotEmpty(reptstr)){
			msg = "覆盖" + reptnum +"条数据;品牌业务员编码:"+reptstr + "覆盖;";
		}
		Map map = new HashMap();
		map.put("success", success);
		map.put("failure", failure);
		map.put("failStr", failStr);
		map.put("msg", msg);
		return map;
	}

	@Override
	public PageData getCustomerExpectReceiptListData(PageMap pageMap)
			throws Exception {
		//小计列
        String groupcols = (String) pageMap.getCondition().get("groupcols");
        if(StringUtils.isEmpty(groupcols)){
        	groupcols = "customerid";
        	pageMap.getCondition().put("groupcols",groupcols);
		}
		String dataSql = getDataAccessRule("t_report_reason_base", "z");
		pageMap.setDataSql(dataSql);
		int count = financeFundsReturnMapper.getCustomerExpectReceiptCount(pageMap);
		List<Map<String, Object>> list = financeFundsReturnMapper.getCustomerExpectReceiptListData(pageMap);
		for(Map<String, Object> dataObject : list){
			String customerid = (String) dataObject.get("customerid");
			String pcustomerid = (String) dataObject.get("pcustomerid");
			String salesarea = (String) dataObject.get("salesarea");
			String salesdept = (String) dataObject.get("salesdept");
			String salesuser = (String) dataObject.get("salesuser");
			String customersort = (String) dataObject.get("customersort");
			String goodsid = (String) dataObject.get("goodsid");
			String goodssort = (String) dataObject.get("goodssort");
			String brandid = (String) dataObject.get("brandid");
			String branduser = (String) dataObject.get("branduser");
			String branddept = (String) dataObject.get("branddept");
			String supplierid = (String) dataObject.get("supplierid");
			//收款人
			if(null != dataObject.get("payeeid")){
				Personnel personnel = getPersonnelById(dataObject.get("payeeid").toString());
				if(null != personnel){
					dataObject.put("payeename", personnel.getName());
				}
			}
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				dataObject.put("customername", customer.getName());
			}else{
				dataObject.put("customername", "未指定客户");
			}
			Customer pcustomer = getCustomerByID(pcustomerid);
			if(null!=pcustomer ){
				dataObject.put("pcustomername", pcustomer.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(salesdept);
			if(null!=departMent){
				dataObject.put("salesdeptname", departMent.getName());
			}else{
				dataObject.put("salesdeptname", "其他(未指定销售部门)");
			}
			SalesArea salesArea = getSalesareaByID(salesarea);
			if(null!=salesArea){
				dataObject.put("salesareaname", salesArea.getName());
			}else{
				dataObject.put("salesareaname","其他（未指定销售区域）");
			}
			Personnel personnel = getPersonnelById(salesuser);
			if(null!=personnel){
				dataObject.put("salesusername", personnel.getName());
			}else{
				dataObject.put("salesusername","其他（未指定客户业务员）");
			}
			 CustomerSort customerSort = getCustomerSortByID(customersort);
			if(null!=customerSort){
				dataObject.put("customersortname", customerSort.getName());
			}else{
				dataObject.put("customersortname", "其他（未指定客户分类）");
			}

            if (groupcols.indexOf("goodsid") != -1) {
                GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
                if (null != goodsInfo) {
                	dataObject.put("goodsname", goodsInfo.getName());
                    WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(goodssort);
                    if (null != waresClass) {
                    	dataObject.put("goodssortname", waresClass.getName());
                    }
                } else {
                    Brand brand = getGoodsBrandByID(goodsid);
                    if (null != brand) {
                    	dataObject.put("goodsname","（折扣）" + brand.getName());
                    }
                }

            }
            if (groupcols.indexOf("brandid") != -1) {
				Brand brand = getGoodsBrandByID(brandid);
				if (null != brand) {
					dataObject.put("brandname",brand.getName());
				}
			}
			if (groupcols.indexOf("branddept") != -1) {
				DepartMent brandDept = getDepartmentByDeptid(branddept);
				if (null != brandDept) {
					dataObject.put("branddeptname",brandDept.getName());
				}
			}
			if (groupcols.indexOf("branduser") != -1) {
				Personnel brandUser = getPersonnelById(branduser);
				if (null != brandUser) {
					dataObject.put("brandusername",brandUser.getName());
				}
			}
            if (groupcols.indexOf("goodssort") != -1) {
                WaresClass waresClass = getBaseGoodsMapper().getWaresClassInfo(goodssort);
				if (null != waresClass) {
					dataObject.put("goodssortname", waresClass.getName());
				}
            }
            if (groupcols.indexOf("supplierid") != -1) {
                if (StringUtils.isNotEmpty(supplierid)) {
                    BuySupplier supplier = getSupplierInfoById(supplierid);
                    if (null != supplier) {
                    	dataObject.put("suppliername", supplier.getName());
                    }
                }
            }
		}
		PageData pageData = new PageData(count,list,pageMap);
		pageMap.getCondition().put("groupcols","all");
		//合计
		List<Map> footList = financeFundsReturnMapper.getCustomerExpectReceiptListData(pageMap);
		for(Map footMap : footList){
			if (groupcols.indexOf("customerid") != -1) {
				footMap.put("customername", "合计");
			}else if (groupcols.indexOf("pcustomerid") != -1) {
				footMap.put("pcustomername", "合计");
			}else if (groupcols.indexOf("salesarea") != -1) {
				footMap.put("salesareaname", "合计");
			}else if (groupcols.indexOf("salesdept") != -1) {
				footMap.put("salesdeptname", "合计");
			}else if (groupcols.indexOf("salesuser") != -1) {
				footMap.put("salesusername", "合计");
			}else if (groupcols.indexOf("payeeid") != -1) {
				footMap.put("payeename", "合计");
			}else if (groupcols.indexOf("customersort") != -1) {
				footMap.put("customersortname", "合计");
			}else if (groupcols.indexOf("goodsid") != -1) {
				footMap.put("goodsname", "合计");
			}else if (groupcols.indexOf("goodssort") != -1) {
				footMap.put("goodssortname", "合计");
			}else if (groupcols.indexOf("brandid") != -1) {
				footMap.put("brandname", "合计");
			}else if (groupcols.indexOf("branduser") != -1) {
				footMap.put("brandusername", "合计");
			}else if (groupcols.indexOf("branddept") != -1) {
				footMap.put("branddeptname", "合计");
			}else if (groupcols.indexOf("supplierid") != -1) {
				footMap.put("suppliername", "合计");
			}
		}
		pageData.setFooter(footList);
		return pageData;
	}

	@Override
	public PageData getCustomerTotalReceiptReportList(PageMap pageMap) throws Exception {
		if(!pageMap.getCondition().containsKey("businessdate1")){
			pageMap.getCondition().put("businessdate1", "1900-01-01");
		}
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		List<Map> list = financeFundsReturnMapper.getCustomerTotalReceiptReportList(pageMap);
		for(Map map : list){
			String customerid = null != map.get("customerid") ? (String)map.get("customerid") : "";
			String pcustomerid = null != map.get("pcustomerid") ? (String)map.get("pcustomerid") : "";
			String salesdept = null != map.get("salesdept") ? (String)map.get("salesdept") : "";
			String salesuser = null != map.get("salesuser") ? (String)map.get("salesuser") : "";
			String customersortid = null != map.get("customersort") ? (String)map.get("customersort") : "";
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				map.put("customername",customer.getName());
			}
			Customer pcustomer = getCustomerByID(pcustomerid);
			if(null != pcustomer){
				map.put("pcustomername",pcustomer.getName());
			}
			DepartMent departMent = getDepartmentByDeptid(salesdept);
			if(null!=departMent){
				map.put("salesdeptname", departMent.getName());
			}
			CustomerSort customersort = getCustomerSortByID(customersortid);
			if(null != customersort){
				map.put("customersortname", customersort.getThisname());
			}
		}
		PageData pageData = new PageData(financeFundsReturnMapper.getCustomerTotalReceiptReportCount(pageMap),list,pageMap);
		List<Map> footer = new ArrayList<Map>();
		Map footerMap = financeFundsReturnMapper.getCustomerTotalReceiptReportSum(pageMap);
		if(null !=footerMap){
			footerMap.put("customername","合计");
			footer.add(footerMap);
		}
		pageData.setFooter(footer);
		return pageData;
	}

	@Override
	public PageData getCustomerTotalReceiptReportDetailList(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		List<Map> list = financeFundsReturnMapper.getCustomerTotalReceiptReportDetailList(pageMap);
		BigDecimal totalreceiptamount = null;
		BigDecimal inittotalreceiptamount = null;
		for(Map map : list){
			String billtype = null != map.get("billtype") ? (String)map.get("billtype") : "";
			if(null == inittotalreceiptamount){
				String businessdate = null != map.get("businessdate") ? (String)map.get("businessdate") : "";
				pageMap.getCondition().put("businessdate1",businessdate);
				BigDecimal inittotalreceiptamount1 = financeFundsReturnMapper.getCustomerTotalReceiptReportInittotalreceiptamount(pageMap);
				if(null != inittotalreceiptamount1){
					inittotalreceiptamount = inittotalreceiptamount1;
				}else{
					inittotalreceiptamount = BigDecimal.ZERO;
				}
			}
			map.put("inittotalreceiptamount",inittotalreceiptamount);

			//销售金额
			BigDecimal saleamount = null != map.get("saleamount") ? (BigDecimal)map.get("saleamount") : BigDecimal.ZERO;
			//尾差金额
			BigDecimal tailamount = null != map.get("tailamount") ? (BigDecimal)map.get("tailamount") : BigDecimal.ZERO;
			//应收金额
			BigDecimal receiptamount = null != map.get("receiptamount") ? (BigDecimal)map.get("receiptamount") : BigDecimal.ZERO;
			//期末总应收 = 期初总应收 + 销售金额 + 尾差金额 - 应收金额
			totalreceiptamount = saleamount.add(tailamount).subtract(receiptamount).add(inittotalreceiptamount);
			map.put("totalreceiptamount",totalreceiptamount);
			inittotalreceiptamount = totalreceiptamount;

			if("1".equals(billtype)){
				map.put("billtypename","销售发货单");
			}else if("2".equals(billtype)){
				map.put("billtypename", "销售退货入库单");
			}else if("3".equals(billtype)){
				map.put("billtypename", "冲差单");
			}else if("4".equals(billtype)){
				map.put("billtypename","销售核销");
			}else if("5".equals(billtype)){
				map.put("billtypename","收款单");
			}else if("6".equals(billtype)){
				map.put("billtypename","客户应收款期初");
			}
		}

		int rows = pageMap.getRows();
		int endnum = 0;
		if(!pageMap.getCondition().containsKey("isflag") && pageMap.getCondition().get("isflag") != "true"){
			endnum = pageMap.getStartNum() + rows;
			if(endnum > list.size()){
				endnum = list.size();
			}
		}else{
			endnum = list.size();
		}
		PageData pageData = new PageData(list.size(),list.subList(pageMap.getStartNum(),endnum),pageMap);
		return pageData;
	}


	@Override
	public PageData showMonthfinanceFundsReturnData(PageMap pageMap) throws Exception {
		String dataSql = getDataAccessRule("t_report_sales_base", "z");
		pageMap.setDataSql(dataSql);
		
		//小计列
		String groupcols = (String) pageMap.getCondition().get("groupcols");
		if(!pageMap.getCondition().containsKey("groupcols")){
			groupcols="customerid,goodsid";
			pageMap.getCondition().put("groupcols", groupcols);
		}
		
		//品牌部门查询条件
		String tmpSql = "";
		if(pageMap.getCondition().containsKey("branddept")){
			String str = (String) pageMap.getCondition().get("branddept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				if(pageMap.getCondition().containsKey("branddept") && "null".equals(pageMap.getCondition().get("branddept"))){
					tmpSql += " and t1.branddept = '' ";
				}else{
					tmpSql += " and t1.branddept like '"+str+"%' ";
				}
			}else{
				String retStr = "";
				String[] branddeptArr = str.split(",");
				for(String branddept : branddeptArr){
					Map map = new HashMap();
					map.put("deptid", branddept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if(dList.size() != 0){
						for(DepartMent departMent : dList){
							if(StringUtils.isNotEmpty(retStr)){
								retStr += "," + departMent.getId();
							}else{
								retStr = departMent.getId();
							}
						}
					}
				}
				tmpSql += " and FIND_IN_SET(t1.branddept,'"+retStr+"')";
			}
			pageMap.getCondition().put("branddeptcondition", tmpSql);
			pageMap.getCondition().put("branddeptcondition2", tmpSql.replaceAll("t1.", "t."));
		}
		tmpSql = "";
		//销售部门查询条件
		if(pageMap.getCondition().containsKey("salesdept")){
			String str = (String) pageMap.getCondition().get("salesdept");
			str = StringEscapeUtils.escapeSql(str);
			if(str.indexOf(",") == -1){
				tmpSql += " and t.salesdept = '"+str+"' ";
			}else{
				String retStr = "";
				String[] salesdeptArr = str.split(",");
				for(String salesdept : salesdeptArr){
					Map map = new HashMap();
					map.put("deptid", salesdept);
					List<DepartMent> dList = getBaseDepartMentMapper().getDeptListByParam(map);
					if(dList.size() != 0){
						for(DepartMent departMent : dList){
							if(StringUtils.isNotEmpty(retStr)){
								retStr += "," + departMent.getId();
							}else{
								retStr = departMent.getId();
							}
						}
					}
				}
				tmpSql += " and FIND_IN_SET(t.salesdept,'"+retStr+"')";
			}
			pageMap.getCondition().put("saledeptcondition", tmpSql);
			pageMap.getCondition().put("saledeptcondition2", tmpSql.replaceAll("t1.", "t."));
		}
		List<MonthSaleWithdrawnReport> list = financeFundsReturnMapper.showMonthFinanceDrawnData(pageMap);
		for(MonthSaleWithdrawnReport monthReport : list){
			setWriteoffRate(monthReport);//设置每个月的回笼毛利率
			BindName(groupcols, monthReport);//前台需要的一些name
		}
		int count = financeFundsReturnMapper.showMonthFinanceDrawnDataCount(pageMap);
		PageData pageData = new PageData(count,list,pageMap);
		//取合计数据
		pageMap.getCondition().put("group", "false");
		pageMap.getCondition().put("limit", "false");
		List<MonthSaleWithdrawnReport> result =null;
		result = financeFundsReturnMapper.showMonthFinanceDrawnData(pageMap);
		if(result!=null&&result.size()>0&&result.get(0)!=null){
			setWriteoffRate(result.get(0));//设置每个月的回笼毛利率
			bindHejiData(groupcols, result.get(0));//合计的位置
		}else{
			result=new ArrayList<MonthSaleWithdrawnReport>();
		}
		pageData.setFooter(result);
		return pageData;
		
	}
	/**
	 * 设置回笼毛利率
	 * @param monthReport
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @author huangzhiqian
	 * @date 2016年1月15日
	 */
	private void setWriteoffRate(MonthSaleWithdrawnReport monthReport) throws Exception {
		for(int i = 1 ; i <13;i++){
			String monthStr = i+"";
			if(i<10){
				monthStr="0"+monthStr;
			}
			Method getMethodWithdrawnamount = MonthSaleWithdrawnReport.class.getMethod("getWithdrawnamount"+monthStr);
			Method getMethodCostwriteoffamount = MonthSaleWithdrawnReport.class.getMethod("getCostwriteoffamount"+monthStr);
			
			Method setMethodWriteoffrate = MonthSaleWithdrawnReport.class.getMethod("setWriteoffrate"+monthStr,new Class[]{java.math.BigDecimal.class});
			
			BigDecimal withdrawnamount =(BigDecimal) getMethodWithdrawnamount.invoke(monthReport);//回笼金额
			BigDecimal costwriteoffamount = (BigDecimal) getMethodCostwriteoffamount.invoke(monthReport);//回笼成本金额
			
			if(withdrawnamount.compareTo(BigDecimal.ZERO)==1){
				BigDecimal writeoffrate = withdrawnamount.subtract(costwriteoffamount).divide(withdrawnamount, 6, BigDecimal.ROUND_HALF_UP);
				writeoffrate = writeoffrate.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
				setMethodWriteoffrate.invoke(monthReport, writeoffrate);
			}else if(withdrawnamount.compareTo(BigDecimal.ZERO)==-1){
				setMethodWriteoffrate.invoke(monthReport, new BigDecimal(100).negate());
			}
		}	
	}
	/**
	 * 数据中的name
	 * @param groupcols
	 * @param baseSalesWithdrawnReport
	 * @throws Exception
	 * @author huangzhiqian
	 * @date 2016年1月14日
	 */
	private void BindName(String groupcols, MonthSaleWithdrawnReport baseSalesWithdrawnReport) throws Exception {
		//条形码
		if(StringUtils.isNotEmpty(baseSalesWithdrawnReport.getGoodsid())){
			GoodsInfo goodsInfo = getGoodsInfoByID(baseSalesWithdrawnReport.getGoodsid());
			if(null != goodsInfo){
				baseSalesWithdrawnReport.setBarcode(goodsInfo.getBarcode());
			}
		}
		if(groupcols.indexOf("customerid")!=-1){
			Customer customer = getCustomerByID(baseSalesWithdrawnReport.getCustomerid());
			if(null!=customer){
				baseSalesWithdrawnReport.setCustomername(customer.getName());
			}
			if(groupcols.indexOf("pcustomerid")==-1){
				Customer pcustomer = getCustomerByID(baseSalesWithdrawnReport.getPcustomerid());
				if(null!=pcustomer ){
					baseSalesWithdrawnReport.setPcustomername(pcustomer.getName());
				}
			}
			if(groupcols.indexOf("salesdept")==-1){
				DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getSalesdept());
				if(null!=departMent){
					baseSalesWithdrawnReport.setSalesdeptname(departMent.getName());
				}else{
					baseSalesWithdrawnReport.setSalesdeptname("其他(未指定销售部门)");
				}
			}
			if(groupcols.indexOf("salesarea")==-1){
				SalesArea salesArea = getSalesareaByID(baseSalesWithdrawnReport.getSalesarea());
				if(null!=salesArea){
					baseSalesWithdrawnReport.setSalesareaname(salesArea.getName());
				}else{
					baseSalesWithdrawnReport.setSalesareaname("其他（未指定销售区域）");
				}
			}
			if(groupcols.indexOf("salesuser")==-1){
				Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getSalesuser());
				if(null!=personnel){
					baseSalesWithdrawnReport.setSalesusername(personnel.getName());
				}else{
					baseSalesWithdrawnReport.setSalesusername("其他（未指定客户业务员）");
				}
			}
			Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getBranduser());
			if(null!=personnel){
				baseSalesWithdrawnReport.setBrandusername(personnel.getName());
			}else{
				baseSalesWithdrawnReport.setBrandusername("其他(未指定品牌业务员)");
			}
		}else{
			baseSalesWithdrawnReport.setCustomerid("");
			baseSalesWithdrawnReport.setCustomername("");
			if(groupcols.indexOf("salesuser")==-1 && groupcols.indexOf("salesdept")==-1){
				baseSalesWithdrawnReport.setSalesdeptname("");
			}

		}
		if(groupcols.indexOf("pcustomerid")!=-1){
			Customer pcustomer = getCustomerByID(baseSalesWithdrawnReport.getPcustomerid());
			if(null!=pcustomer ){
				baseSalesWithdrawnReport.setPcustomername(pcustomer.getName());
			}else{
				baseSalesWithdrawnReport.setPcustomername("其他客户总和");
			}
			if(groupcols.indexOf("customerid")==1){
				baseSalesWithdrawnReport.setCustomerid("");
				baseSalesWithdrawnReport.setCustomername("");

			}

		}
		if(groupcols.indexOf("salesarea")!=-1){
			SalesArea salesArea = getSalesareaByID(baseSalesWithdrawnReport.getSalesarea());
			if(null!=salesArea){
				baseSalesWithdrawnReport.setSalesareaname(salesArea.getName());
			}else{
				baseSalesWithdrawnReport.setSalesareaname("其他（未指定销售区域）");
			}
		}
		if(groupcols.indexOf("supplierid")!=-1){
			BuySupplier buySupplier = getSupplierInfoById(baseSalesWithdrawnReport.getSupplierid());
			if(null!=buySupplier){
				baseSalesWithdrawnReport.setSuppliername(buySupplier.getName());
			}else{
				if("QC".equals(baseSalesWithdrawnReport.getSupplierid())){
					baseSalesWithdrawnReport.setSuppliername("应收款期初");
				}else{
					baseSalesWithdrawnReport.setSuppliername("其他（未指定供应商）");
				}
			}
			//品牌部门
			DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
			if(null != departMent){
				baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
			}
		}
		if(groupcols.indexOf("salesdept")!=-1){
			DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getSalesdept());
			if(null!=departMent){
				baseSalesWithdrawnReport.setSalesdeptname(departMent.getName());
			}else{
				baseSalesWithdrawnReport.setSalesdeptname("其他（未指定销售部门）");
			}
		}
		if(groupcols.indexOf("salesuser")!=-1){
			Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getSalesuser());
			if(null!=personnel){
				baseSalesWithdrawnReport.setSalesusername(personnel.getName());
			}else{
				baseSalesWithdrawnReport.setSalesusername("其他（未指定客户业务员）");
			}
			if(groupcols.indexOf("salesarea")==-1){
				SalesArea salesArea = getSalesareaByID(baseSalesWithdrawnReport.getSalesarea());
				if(null!=salesArea){
					baseSalesWithdrawnReport.setSalesareaname(salesArea.getName());
				}
			}
			if(groupcols.indexOf("salesdept")==-1){
				DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getSalesdept());
				if(null!=departMent){
					baseSalesWithdrawnReport.setSalesdeptname(departMent.getName());
				}else{
					baseSalesWithdrawnReport.setSalesdeptname("其他（未指定销售部门）");
				}
			}
		}
		if(groupcols.indexOf("goodsid")!=-1){
			GoodsInfo goodsInfo = getAllGoodsInfoByID(baseSalesWithdrawnReport.getGoodsid());
			if(null!=goodsInfo){
				baseSalesWithdrawnReport.setGoodsname(goodsInfo.getName());

				String auxunitid = "";
				MeteringUnit auxunit = getGoodsDefaulAuxunit(baseSalesWithdrawnReport.getGoodsid());
				if(null != auxunit){
					auxunitid = auxunit.getId();
				}
				Map map = countGoodsInfoNumber(baseSalesWithdrawnReport.getGoodsid(),auxunitid,baseSalesWithdrawnReport.getUnitnum());
				BigDecimal auxnum = (BigDecimal)map.get("auxnum");
				String auxnumdetail = (String)map.get("auxnumdetail");
				baseSalesWithdrawnReport.setAuxnum(auxnum);
				baseSalesWithdrawnReport.setAuxnumdetail(auxnumdetail);
			}else{
				Brand brand = getGoodsBrandByID(baseSalesWithdrawnReport.getGoodsid());
				if(null!=brand){
					baseSalesWithdrawnReport.setGoodsname("（折扣）"+brand.getName());
				}else{
					if("QC".equals(baseSalesWithdrawnReport.getGoodsid())){
						baseSalesWithdrawnReport.setGoodsname("应收款期初");
					}else{
						baseSalesWithdrawnReport.setGoodsname("（折扣）其他");
					}
				}
			}
			if(groupcols.indexOf("brandid")==-1){
				Brand brand = getGoodsBrandByID(baseSalesWithdrawnReport.getBrandid());
				if(null!=brand){
					baseSalesWithdrawnReport.setBrandname(brand.getName());
				}
			}
			if(groupcols.indexOf("branddept")==-1){
				DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
				if(null!=departMent){
					baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
				}else{
					baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
				}
			}
		}else{
			baseSalesWithdrawnReport.setGoodsid("");
		}
		if(groupcols.indexOf("brandid")!=-1){
			Brand brand = getGoodsBrandByID(baseSalesWithdrawnReport.getBrandid());
			if(null!=brand){
				baseSalesWithdrawnReport.setBrandname(brand.getName());
			}else{
				if("QC".equals(baseSalesWithdrawnReport.getBrandid())){
					baseSalesWithdrawnReport.setBrandname("应收款期初");
				}else{
					baseSalesWithdrawnReport.setBrandname("其他");
				}
			}
			if(groupcols.indexOf("branddept")==-1){
				DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
				if(null!=departMent){
					baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
				}else{
					baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
				}
				Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getBranduser());
				if(null!=personnel){
					baseSalesWithdrawnReport.setBrandusername(personnel.getName());
				}else{
					baseSalesWithdrawnReport.setBrandusername("其他(未指定品牌业务员)");
				}
			}
		}
		if(groupcols.indexOf("branduser")!=-1){
			Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getBranduser());
			if(null!=personnel){
				baseSalesWithdrawnReport.setBrandusername(personnel.getName());
			}else{
				if("QC".equals(baseSalesWithdrawnReport.getBranduser())){
					baseSalesWithdrawnReport.setBrandusername("应收款期初");
				}else{
					baseSalesWithdrawnReport.setBrandusername("其他(未指定品牌业务员)");
				}
			}
			if(groupcols.indexOf("brandid")==-1){
				baseSalesWithdrawnReport.setBrandname("");
			}
			if(groupcols.indexOf("branddept")==-1){
				DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
				if(null!=departMent){
					baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
				}else{
					baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
				}
			}
		}
		if(groupcols.indexOf("supplieruser")!=-1){
			Personnel personnel = getPersonnelById(baseSalesWithdrawnReport.getSupplieruser());
			if(null!=personnel){
				baseSalesWithdrawnReport.setSupplierusername(personnel.getName());
			}else{
				if("QC".equals(baseSalesWithdrawnReport.getSupplieruser())){
					baseSalesWithdrawnReport.setSupplierusername("应收款期初");
				}else{
					baseSalesWithdrawnReport.setSupplierusername("其他(未指定厂家业务员)");
				}
			}
		}
		if(groupcols.indexOf("branddept")!=-1){
			DepartMent departMent = getDepartmentByDeptid(baseSalesWithdrawnReport.getBranddept());
			if(null!=departMent){
				baseSalesWithdrawnReport.setBranddeptname(departMent.getName());
			}else{
				if("QC".equals(baseSalesWithdrawnReport.getBranddept())){
					baseSalesWithdrawnReport.setBranddeptname("应收款期初");
				}else{
					baseSalesWithdrawnReport.setBranddeptname("其他（未指定品牌部）");
				}
			}
		}
	}
	
	/**
	 * 合计
	 * @param groupcols
	 * @param baseSalesWithdrawnReport
	 * @author huangzhiqian
	 * @date 2016年1月14日
	 */
	private void bindHejiData(String groupcols,MonthSaleWithdrawnReport baseSalesWithdrawnReport) {
		if(groupcols.indexOf(",") == -1){
			if(groupcols.indexOf("branddept")!=-1){
				baseSalesWithdrawnReport.setBranddept("");
				baseSalesWithdrawnReport.setBranddeptname("合计");
			}
			else if(groupcols.indexOf("brandid")!=-1){
				baseSalesWithdrawnReport.setBrandid("");
				baseSalesWithdrawnReport.setBrandname("合计");
			}
			else if(groupcols.indexOf("salesdept")!=-1){
				baseSalesWithdrawnReport.setSalesdept("");
				baseSalesWithdrawnReport.setSalesdeptname("合计");
			}
			else if(groupcols.indexOf("supplierid")!=-1){
				baseSalesWithdrawnReport.setSupplierid("");
				baseSalesWithdrawnReport.setSuppliername("合计");
			}
			else if(groupcols.indexOf("salesuser")!=-1){
				baseSalesWithdrawnReport.setSalesuser("");
				baseSalesWithdrawnReport.setSalesusername("合计");
			}
			else if(groupcols.indexOf("branduser")!=-1){
				baseSalesWithdrawnReport.setBranduser("");
				baseSalesWithdrawnReport.setBrandusername("合计");
			}
			else if(groupcols.indexOf("goodsid")!=-1){
				baseSalesWithdrawnReport.setGoodsid("");
				baseSalesWithdrawnReport.setGoodsname("合计");
			}
			else if(groupcols.indexOf("customerid")!=-1){
				baseSalesWithdrawnReport.setCustomerid("");
				baseSalesWithdrawnReport.setCustomername("合计");
				baseSalesWithdrawnReport.setSalesdeptname("");
			}
			else if(groupcols.indexOf("salesarea")!=-1){
				baseSalesWithdrawnReport.setSalesarea("");
				baseSalesWithdrawnReport.setSalesareaname("合计");
			}else if(groupcols.indexOf("supplieruser")!=-1){
		        baseSalesWithdrawnReport.setSupplieruser("");
		        baseSalesWithdrawnReport.setSupplierusername("合计");
		    }
		}else{
			String[] groupArr = groupcols.split(",");
			for(String group : groupArr){
				if(group.indexOf("branddept")!=-1){
					baseSalesWithdrawnReport.setBranddeptname("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
		            baseSalesWithdrawnReport.setSupplierusername("");
				}
				else if(group.indexOf("brandid")!=-1){
					baseSalesWithdrawnReport.setBrandname("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
		            baseSalesWithdrawnReport.setSupplierusername("");
				}
				else if(group.indexOf("salesdept")!=-1){
					baseSalesWithdrawnReport.setSalesdeptname("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
		            baseSalesWithdrawnReport.setSupplierusername("");
				}
				else if(group.indexOf("salesuser")!=-1){
					baseSalesWithdrawnReport.setSalesusername("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
		            baseSalesWithdrawnReport.setSupplierusername("");
				}
				else if(group.indexOf("branduser")!=-1){
					baseSalesWithdrawnReport.setBrandusername("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
		            baseSalesWithdrawnReport.setSupplierusername("");
				}
				else if(group.indexOf("goodsid")!=-1){
					baseSalesWithdrawnReport.setGoodsname("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
		            baseSalesWithdrawnReport.setSupplierusername("");
				}
				else if(group.indexOf("customerid")!=-1){
					baseSalesWithdrawnReport.setCustomername("合计");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("");
		            baseSalesWithdrawnReport.setSupplierusername("");
				}
				else if(group.indexOf("supplierid")!=-1){
					baseSalesWithdrawnReport.setCustomername("");
					baseSalesWithdrawnReport.setBranddept("");
					baseSalesWithdrawnReport.setBrandid("");
					baseSalesWithdrawnReport.setSalesdept("");
					baseSalesWithdrawnReport.setSalesuser("");
					baseSalesWithdrawnReport.setBranduser("");
					baseSalesWithdrawnReport.setGoodsid("");
					baseSalesWithdrawnReport.setCustomerid("");
					baseSalesWithdrawnReport.setBrandname("");
					baseSalesWithdrawnReport.setSalesdeptname("");
					baseSalesWithdrawnReport.setSalesusername("");
					baseSalesWithdrawnReport.setBrandusername("");
					baseSalesWithdrawnReport.setGoodsname("");
					baseSalesWithdrawnReport.setBranddeptname("");
					baseSalesWithdrawnReport.setSupplierid("");
					baseSalesWithdrawnReport.setSuppliername("合计");
		            baseSalesWithdrawnReport.setSupplierusername("");
				}else if(group.indexOf("supplieruser")!=-1){
		            baseSalesWithdrawnReport.setCustomername("");
		            baseSalesWithdrawnReport.setBranddept("");
		            baseSalesWithdrawnReport.setBrandid("");
		            baseSalesWithdrawnReport.setSalesdept("");
		            baseSalesWithdrawnReport.setSalesuser("");
		            baseSalesWithdrawnReport.setBranduser("");
		            baseSalesWithdrawnReport.setGoodsid("");
		            baseSalesWithdrawnReport.setCustomerid("");
		            baseSalesWithdrawnReport.setBrandname("");
		            baseSalesWithdrawnReport.setSalesdeptname("");
		            baseSalesWithdrawnReport.setSalesusername("");
		            baseSalesWithdrawnReport.setBrandusername("");
		            baseSalesWithdrawnReport.setGoodsname("");
		            baseSalesWithdrawnReport.setBranddeptname("");
		            baseSalesWithdrawnReport.setSupplierid("");
		            baseSalesWithdrawnReport.setSuppliername("");
		            baseSalesWithdrawnReport.setSupplierusername("合计");
		        }
			}
		}
	}
	/**
	 * 如果有超账期数据，发送代垫应收超账期链接
	 * @param
	 * @return void
	 * @throws
	 * @author luoqiang
	 * @date Oct 31, 2017
	 */
	@Override
	public void sendSupplierPassDudData(TaskSchedule taskSchedule) throws Exception{
		if("0".equals(taskSchedule.getIsalert())){
			return;
		}
		//操作标志
		boolean flag=false;
		MsgContent msgContent=new MsgContent();
		List<SysUser> sysUserList=getSysUserListByRoleidAndUserid(taskSchedule.getSendroleid(), taskSchedule.getSenduserid());
		StringBuilder result = new StringBuilder();
		for(SysUser sysUser:sysUserList){
			if(StringUtils.isEmpty(result)){
				result=result.append(sysUser.getUserid());
			}else{
				result=result.append(","+sysUser.getUserid());
			}
		}
		msgContent.setReceivers(result.toString());
		msgContent.setContent("供应商代垫应收超账期提醒");
		msgContent.setTitle("供应商代垫应收超账期提醒");

		msgContent.setClocktype("2");

		msgContent.setAdduserid("system");
		msgContent.setMsgtype("2");	//消息类型为内部短信
		msgContent.setUrl(taskSchedule.getAlerturl()); //内部消息详细地址为空

		IInnerMessageService innerMessageService = (IInnerMessageService) SpringContextUtils.getBean("innerMessageService");
		innerMessageService.addSendMessage(msgContent);
	}
	/**
	 * 分客户分银行回笼情况表 获取客户的回笼金额数据 按单据生成
	 * @param map
	 * @return java.util.List<java.util.Map>
	 * @throws
	 * @author luoqiang
	 * @date Dec 20, 2017
	 */
	public List<Map> getCustomerSalesAmountByQueryMapForThird(Map map) throws Exception{
		List<Map> mapArrayList= financeFundsReturnMapper.getCustomerSalesAmountByQueryMapForThird(map);
		return mapArrayList ;
	}

}

