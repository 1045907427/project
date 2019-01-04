/**
 * @(#)PhoneOrderServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jan 17, 2014 zhengziyong 创建版本
 */
package com.hd.agent.phone.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.account.dao.CustomerPushBalanceMapper;
import com.hd.agent.account.dao.SalesFreeOrderMapper;
import com.hd.agent.account.model.CustomerPushBalance;
import com.hd.agent.account.model.ReceivablePastDueReason;
import com.hd.agent.account.service.ISalesInvoiceBillService;
import com.hd.agent.account.service.ISalesInvoiceService;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.phone.dao.PhoneMapper;
import com.hd.agent.phone.service.IPhoneOrderService;
import com.hd.agent.sales.dao.DemandMapper;
import com.hd.agent.sales.dao.ReceiptMapper;
import com.hd.agent.sales.dao.RejectBillMapper;
import com.hd.agent.sales.model.Demand;
import com.hd.agent.sales.model.ReceiptDetail;
import com.hd.agent.sales.model.RejectBillDetail;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author zhengziyong
 */
public class PhoneOrderServiceImpl extends BaseFilesServiceImpl implements IPhoneOrderService {

	private PhoneMapper phoneMapper;
	private ISalesInvoiceService salesInvoiceService;
    private ISalesInvoiceBillService salesInvoiceBillService;
	private SalesFreeOrderMapper salesFreeOrderMapper;
	
	public PhoneMapper getPhoneMapper() {
		return phoneMapper;
	}

	public void setPhoneMapper(PhoneMapper phoneMapper) {
		this.phoneMapper = phoneMapper;
	}
	
	public ISalesInvoiceService getSalesInvoiceService() {
		return salesInvoiceService;
	}

	public void setSalesInvoiceService(ISalesInvoiceService salesInvoiceService) {
		this.salesInvoiceService = salesInvoiceService;
	}
	
	public SalesFreeOrderMapper getSalesFreeOrderMapper() {
		return salesFreeOrderMapper;
	}

	public void setSalesFreeOrderMapper(SalesFreeOrderMapper salesFreeOrderMapper) {
		this.salesFreeOrderMapper = salesFreeOrderMapper;
	}

    public ISalesInvoiceBillService getSalesInvoiceBillService() {
        return salesInvoiceBillService;
    }

    public void setSalesInvoiceBillService(ISalesInvoiceBillService salesInvoiceBillService) {
        this.salesInvoiceBillService = salesInvoiceBillService;
    }

    @Override
	public List getCustomerInvoiceList(PageMap pageMap) throws Exception {
		ReceiptMapper receiptMapper = (ReceiptMapper)SpringContextUtils.getBean("salesReceiptMapper");

        String dataSql = getDataAccessRule("t_report_sales_invoicebill","t");
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(dataSql)){
            dataSql = dataSql.replaceAll("t.payeeid","c.payeeid");
        }
        pageMap.setDataSql(dataSql);
		SysUser sysUser = getSysUser();
		//判断是否品牌业务员
		String brandUserRoleName = getSysParamValue("BrandUserRoleName");
		boolean isBrandUser = isSysUserHaveRole(brandUserRoleName,sysUser.getUserid());
		if(isBrandUser){
			pageMap.getCondition().put("isBrandUser", true);
			pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
		}else{
//			pageMap.getCondition().put("salesuser", sysUser.getPersonnelid());
		}
		List<Map> list = receiptMapper.getCustomerInvoiceSumData(pageMap);
		for(Map map:list){
			if(null!=map){
				String customerid = (String) map.get("customerid");
				Customer customer = getCustomerByID(customerid);
				if(null!=customer){
					map.put("customername", "["+customer.getId()+"]"+customer.getName());
				}
				BigDecimal banlanceamount = BigDecimal.ZERO;
				BigDecimal invoiceamount = BigDecimal.ZERO;
				if(map.containsKey("banlanceamount")){
					banlanceamount = (BigDecimal) map.get("banlanceamount");
				}
				if(map.containsKey("invoiceamount")){
					invoiceamount = (BigDecimal) map.get("invoiceamount");
				}
				BigDecimal useableamount = BigDecimal.ZERO;
				if(banlanceamount.compareTo(invoiceamount)==1){
					useableamount= banlanceamount.subtract(invoiceamount);
				}
				map.put("useableamount", useableamount);
			}
			
		}
		return list;
	}
	@Override
	public List getCustomerInvoiceBillList(PageMap pageMap) throws Exception {
		ReceiptMapper receiptMapper = (ReceiptMapper)SpringContextUtils.getBean("salesReceiptMapper");

        String dataSql = getDataAccessRule("t_report_sales_invoicebill","t");
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(dataSql)){
            dataSql = dataSql.replaceAll("t.payeeid","c.payeeid");
        }
        pageMap.setDataSql(dataSql);
		SysUser sysUser = getSysUser();
		//判断是否品牌业务员
		String brandUserRoleName = getSysParamValue("BrandUserRoleName");
		boolean isBrandUser = isSysUserHaveRole(brandUserRoleName,sysUser.getUserid());
		if(isBrandUser){
			pageMap.getCondition().put("isBrandUser", true);
			pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
		}else{
//			pageMap.getCondition().put("salesuser", sysUser.getPersonnelid());
		}
		pageMap.getCondition().put("exportflag", "true");
		List<Map> list = receiptMapper.getReceiptAndRejectBillList(pageMap);
		List<Map> result = new ArrayList<Map>();
		for(Map map : list){
			Map m = new HashMap();
			m.put("orderid", map.get("orderid"));
			m.put("id", map.get("id"));
			m.put("billtype", map.get("billtype"));
			String billtype = (String) map.get("billtype");
			if("1".equals(billtype)){
				m.put("billtypename", "销售发货回单");
    		}else if("2".equals(billtype)){
    			m.put("billtypename", "销售退货通知单");
    		}else if("3".equals(billtype)){
    			m.put("orderid", map.get("remark"));
    			m.put("billtypename", "冲差单");
    		}else if("4".equals(billtype)){
                m.put("billtypename", "应收款期初");
                m.put("orderid", map.get("remark"));
            }
			m.put("businessdate", map.get("businessdate"));
			m.put("addusername", map.get("addusername"));
			String id = (String) map.get("id");
			String salesuser = (String) map.get("salesuser");
			Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(salesuser);
			if(personnel != null){
				m.put("salesuser", personnel.getName());
			}
			m.put("customerid", map.get("customerid"));
			String customerid = (String) map.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(customer != null){
				m.put("customername", customer.getName());
			}
			if(map.containsKey("totaltaxamount")){
				m.put("totaltaxamount", ((BigDecimal) map.get("totaltaxamount")).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}
			if(map.containsKey("uninvoiceamount")){
				m.put("uninvoiceamount", ((BigDecimal) map.get("uninvoiceamount")).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			}
            if(map.containsKey("billamount")){
                m.put("billamount", ((BigDecimal) map.get("billamount")).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
			result.add(m);
		}
		return result;
	}
	@Override
	public PageData getReceiptAndRejectBillList(PageMap pageMap) throws Exception {
		ReceiptMapper receiptMapper = (ReceiptMapper)SpringContextUtils.getBean("salesReceiptMapper");
        String dataSql = getDataAccessRule("t_report_sales_invoicebill","t");
        if(StringUtils.isNotEmpty(dataSql)){
            dataSql = dataSql.replaceAll("t.payeeid","c.payeeid");
        }
        pageMap.setDataSql(dataSql);
		SysUser sysUser = getSysUser();
		//判断是否品牌业务员
		String brandUserRoleName = getSysParamValue("BrandUserRoleName");
		boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
		if(isBrandUser){
			pageMap.getCondition().put("isBrandUser", true);
			pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
		}
		List<Map> list = receiptMapper.getReceiptAndRejectBillList(pageMap);
		List<Map> result = new ArrayList<Map>();
		for(Map map : list){
			Map m = new HashMap();
			m.put("id", map.get("id"));
			m.put("billtype", map.get("billtype"));
			m.put("businessdate", map.get("businessdate"));
			m.put("addusername", map.get("addusername"));
			String id = (String) map.get("id");
			String billtype = (String) map.get("billtype");
			String salesuser = (String) map.get("salesuser");
			Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(salesuser);
			if(personnel != null){
				m.put("salesuser", personnel.getName());
			}
			m.put("customerid", map.get("customerid"));
			String customerid = (String) map.get("customerid");
			Customer customer = getCustomerByID(customerid);
			if(customer != null){
				m.put("customername", customer.getName());
			}
			result.add(m);
		}
		PageData pageData = new PageData(receiptMapper.getReceiptAndRejectBillCount(pageMap), result, pageMap);
		return pageData;
	}

	@Override
	public Map getReceiptAndRejectBillDetailList(String id, String cid, String type) throws Exception {
		Map result = new HashMap();
		ReceiptMapper receiptMapper = (ReceiptMapper)SpringContextUtils.getBean("salesReceiptMapper");
		RejectBillMapper rejectBillMapper = (RejectBillMapper)SpringContextUtils.getBean("salesRejectBillMapper");
		CustomerPushBalanceMapper customerPushBalanceMapper = (CustomerPushBalanceMapper)SpringContextUtils.getBean("customerPushBalanceMapper");
		List list = new ArrayList();
		BigDecimal totalamount = BigDecimal.ZERO;
		List cList = new ArrayList();
		Customer customer = getCustomerByID(cid);
		if(customer != null){
			Map cMap = new HashMap();
			cMap.put("id", customer.getId());
			cMap.put("name", customer.getName());
			cList.add(cMap);
			Customer pCustomer = getCustomerByID(customer.getPid());
			if(pCustomer != null){
				Map pMap = new HashMap();
				pMap.put("id", pCustomer.getId());
				pMap.put("name", pCustomer.getName());
				cList.add(pMap);
			}
		}
		if("1".equals(type)){
			List<ReceiptDetail> detailList = receiptMapper.getReceiptDetailListSumDiscount(id);
			if(null != detailList){
				for(ReceiptDetail receiptDetail : detailList){
					Map map = new HashMap();
					map.put("goodsid", receiptDetail.getGoodsid());
					map.put("billtype", "1");
					GoodsInfo goodsInfo = getGoodsInfoByID(receiptDetail.getGoodsid());
					if(null != goodsInfo){
						map.put("goodsname",goodsInfo.getName() );
						map.put("boxnum",goodsInfo.getBoxnum() );
					}
					map.put("taxprice", receiptDetail.getTaxprice());
					map.put("taxamount", receiptDetail.getReceipttaxamount());
					map.put("notaxamount", receiptDetail.getReceiptnotaxamount());
					map.put("unitnum", receiptDetail.getReceiptnum());
					map.put("isinvoice", receiptDetail.getIsinvoice());
					map.put("unitname", receiptDetail.getUnitname());
					list.add(map);
					totalamount = totalamount.add(receiptDetail.getReceipttaxamount());
				}
			}
		}else if("2".equals(type)){
			List<RejectBillDetail> detaillist = rejectBillMapper.getRejectBillDetailListByBill(id);
			if(null != detaillist){
				for(RejectBillDetail rejectBillDetail : detaillist){
					Map map = new HashMap();
					map.put("goodsid", rejectBillDetail.getGoodsid());
					map.put("billtype", "2");
					GoodsInfo goodsInfo = getGoodsInfoByID(rejectBillDetail.getGoodsid());
					if(null != goodsInfo){
						map.put("goodsname",goodsInfo.getName() );
						map.put("boxnum",goodsInfo.getBoxnum() );
					}
					map.put("taxprice", rejectBillDetail.getTaxprice());
					map.put("taxamount", rejectBillDetail.getInamounttax().negate());
					map.put("notaxamount", rejectBillDetail.getInamountnotax().negate());
					map.put("unitnum", rejectBillDetail.getUnitnum().negate());
					map.put("isinvoice", rejectBillDetail.getIsinvoice());
					map.put("unitname", rejectBillDetail.getUnitname());
					list.add(map);
					totalamount = totalamount.add(rejectBillDetail.getTaxamount().negate());
				}
			}
		}else if("3".equals(type)){
			CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
			if(null!=customerPushBalance){
				Map map = new HashMap();
				map.put("goodsid", customerPushBalance.getBrandid());
				map.put("billtype", "2");
				Brand brand = getGoodsBrandByID(customerPushBalance.getBrandid());
				if(null != brand){
					map.put("goodsname",brand.getName() );
					map.put("boxnum",BigDecimal.ZERO );
				}
				map.put("taxprice", BigDecimal.ZERO);
				map.put("taxamount",customerPushBalance.getAmount());
				map.put("notaxamount", getNotaxAmountByTaxAmount(customerPushBalance.getAmount(), null));
				map.put("unitnum", BigDecimal.ZERO);
				map.put("isinvoice", customerPushBalance.getIsrefer());
				map.put("unitname", "");
				list.add(map);
				totalamount = totalamount.add(customerPushBalance.getAmount());
			}
		}
		result.put("id", id);
		result.put("type", type);
		result.put("rows", list);
		result.put("size", list.size());
		result.put("total", totalamount);
		result.put("customer", cList);
		return result;
	}

	@Override
	public Map makeInvoice(String uid, String id, String cid, String type) throws Exception {
		ReceiptMapper receiptMapper = (ReceiptMapper)SpringContextUtils.getBean("salesReceiptMapper");
		RejectBillMapper rejectBillMapper = (RejectBillMapper)SpringContextUtils.getBean("salesRejectBillMapper");
		CustomerPushBalanceMapper customerPushBalanceMapper = (CustomerPushBalanceMapper)SpringContextUtils.getBean("customerPushBalanceMapper");
		String ids = "[";
		BigDecimal totalamount = BigDecimal.ZERO;
		if("1".equals(type)){
			List<ReceiptDetail> detailList = receiptMapper.getReceiptDetailListSumDiscount(id);
			if(null != detailList){
				for(ReceiptDetail receiptDetail : detailList){
					ids += "{\"billtype\":\""+type+"\",\"billid\":\""+id+"\",\"detailid\":\""+receiptDetail.getId()+"\"},"; 
				}
			}
			if(ids.endsWith(",")){
				ids = ids.substring(0, ids.length() - 1);
			}
			ids += "]";
		}else if("2".equals(type)){
			List<RejectBillDetail> detaillist = rejectBillMapper.getRejectBillDetailListByBill(id);
			if(null != detaillist){
				for(RejectBillDetail rejectBillDetail : detaillist){
					ids += "{\"billtype\":\""+type+"\",\"billid\":\""+id+"\",\"detailid\":\""+rejectBillDetail.getId()+"\"},";
				}
			}
			if(ids.endsWith(",")){
				ids = ids.substring(0, ids.length() - 1);
			}
			ids += "]";
		}else if("3".equals(type)){
			CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(id);
			if(null != customerPushBalance){
					ids += "{\"billtype\":\""+type+"\",\"billid\":\""+id+"\",\"detailid\":\""+customerPushBalance.getId()+"\"},";
			}
			if(ids.endsWith(",")){
				ids = ids.substring(0, ids.length() - 1);
			}
			ids += "]";
		}else if("4".equals(type)){
            ids += "{\"billtype\":\""+type+"\",\"billid\":\""+id+"\",\"detailid\":\""+id+"\"},";
            if(ids.endsWith(",")){
                ids = ids.substring(0, ids.length() - 1);
            }
            ids += "]";
        }
		Map map = salesInvoiceService.addSalesInvoiceByReceiptAndRejectbillForPhone(uid, ids, cid);
		return map;
	}

    /**
     * 获取客户开票数据列表
     *
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public List getCustomerInvoiceBillInfoList(PageMap pageMap) throws Exception {
        ReceiptMapper receiptMapper = (ReceiptMapper)SpringContextUtils.getBean("salesReceiptMapper");

        String dataSql = getDataAccessRule("t_report_sales_invoicebill","t");
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(dataSql)){
            dataSql = dataSql.replaceAll("t.payeeid","c.payeeid");
        }
        //申请开票时来源单据是否取未核销状态的1已核销可取0只取未核销
        String addSalesInvoiceBillSourceBillUnWriteoff = getSysParamValue("addSalesInvoiceBillSourceBillUnWriteoff");
        if(StringUtils.isEmpty(addSalesInvoiceBillSourceBillUnWriteoff)){
            addSalesInvoiceBillSourceBillUnWriteoff = "0";
        }
        if("0".equals(addSalesInvoiceBillSourceBillUnWriteoff)){
            pageMap.getCondition().put("iswriteoff","0");
        }
        pageMap.setDataSql(dataSql);
        SysUser sysUser = getSysUser();
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName,sysUser.getUserid());
        if(isBrandUser){
            pageMap.getCondition().put("isBrandUser", true);
            pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
        }else{
//			pageMap.getCondition().put("salesuser", sysUser.getPersonnelid());
        }
        List<Map> list = receiptMapper.getCustomerInvoiceBillInfoSumData(pageMap);
        for(Map map:list){
            if(null!=map){
                String customerid = (String) map.get("customerid");
                String customername = (String) map.get("customername");
                map.put("customername", "["+customerid+"]"+customername);
            }
        }
        return list;
    }

    /**
     * 获取客户可以申请开票的单据列表
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date Apr 29, 2014
     */
    @Override
    public List getCustomerInvoiceBillApplayList(PageMap pageMap) throws Exception {
        ReceiptMapper receiptMapper = (ReceiptMapper)SpringContextUtils.getBean("salesReceiptMapper");
        String dataSql = getDataAccessRule("t_report_sales_invoicebill","t");
        if(StringUtils.isNotEmpty(dataSql)){
            dataSql = dataSql.replaceAll("t.payeeid","c.payeeid");
        }
        pageMap.setDataSql(dataSql);
        pageMap.getCondition().put("exportflag", "true");
        //申请开票时来源单据是否取未核销状态的1已核销可取0只取未核销
        String addSalesInvoiceBillSourceBillUnWriteoff = getSysParamValue("addSalesInvoiceBillSourceBillUnWriteoff");
        if(StringUtils.isEmpty(addSalesInvoiceBillSourceBillUnWriteoff)){
            addSalesInvoiceBillSourceBillUnWriteoff = "0";
        }
        if("0".equals(addSalesInvoiceBillSourceBillUnWriteoff)){
            pageMap.getCondition().put("iswriteoff","0");
        }
        SysUser sysUser = getSysUser();
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if(isBrandUser){
            pageMap.getCondition().put("isBrandUser", true);
            pageMap.getCondition().put("personnelid", sysUser.getPersonnelid());
        }
        List<Map> list = receiptMapper.getReceiptAndRejectBillListForInvoiceBill(pageMap);
        for(Map map : list){
            String id = (String) map.get("id");
            String billtype = (String) map.get("billtype");
            if("1".equals(billtype)){
                map.put("billtypename", "销售发货回单");
            }else if("2".equals(billtype)){
                map.put("billtypename", "销售退货通知单");
            }else if("3".equals(billtype)){
                map.put("billtypename", "冲差单");
            }
            String salesdept = (String) map.get("salesdept");
            DepartMent departMent = getBaseFilesDepartmentMapper().getDepartmentInfo(salesdept);
            if(departMent != null){
                map.put("salesdept", departMent.getName());
            }
            String salesuser = (String) map.get("salesuser");
            Personnel personnel = getBaseFilesPersonnelMapper().getPersonnelInfo(salesuser);
            if(personnel != null){
                map.put("salesuser", personnel.getName());
            }
            String customerid = (String) map.get("customerid");

            Customer customer = getCustomerByID(customerid);
            if(customer != null){
                map.put("customername", customer.getName());
                map.put("customerInfo", customer);

                Customer headCustomer = getCustomerByID(customer.getPid());
                if(null!=headCustomer){
                    map.put("headcustomername", headCustomer.getName());
                    map.put("headcustomerid", customer.getPid());
                }else{
                    map.put("headcustomerid", customer.getId());
                }
            }
        }
        return list;
    }

    /**
     * 上传申请开票
     *
     * @param map
     * @return
     * @throws Exception
     * @author chenwei
     * @date Apr 30, 2014
     */
    @Override
    public Map addCustomerInvoiceBillApplayList(Map map) throws Exception {
        SysUser sysUser = getSysUser();
        String customerid = (String) map.get("customerid");
        String billJson = (String) map.get("json");
        ReceiptMapper receiptMapper = (ReceiptMapper)SpringContextUtils.getBean("salesReceiptMapper");
        RejectBillMapper rejectBillMapper = (RejectBillMapper)SpringContextUtils.getBean("salesRejectBillMapper");
        CustomerPushBalanceMapper customerPushBalanceMapper = (CustomerPushBalanceMapper)SpringContextUtils.getBean("customerPushBalanceMapper");
        JSONArray jsonArray = JSONArray.fromObject(billJson);
        String ids = "[";
        BigDecimal totalamount = BigDecimal.ZERO;
        for(int i=0;i<jsonArray.size();i++){
            JSONObject object = jsonArray.getJSONObject(i);
            String billtype = object.getString("billtype");
            String billid = object.getString("billid");
            String cid = object.getString("customerid");
            if("1".equals(billtype)){
                List<ReceiptDetail> detailList = receiptMapper.getReceiptDetailListSumDiscountNoInvoicebill(billid);
                if(null != detailList){
                    for(ReceiptDetail receiptDetail : detailList){
                        ids += "{\"billtype\":\""+billtype+"\",\"billid\":\""+billid+"\",\"detailid\":\""+receiptDetail.getId()+"\"},";
                    }
                }
            }else if("2".equals(billtype)){
                List<RejectBillDetail> detaillist = rejectBillMapper.getRejectBillDetailListNoInvoiceBillByBill(billid);
                if(null != detaillist){
                    for(RejectBillDetail rejectBillDetail : detaillist){
                        ids += "{\"billtype\":\""+billtype+"\",\"billid\":\""+billid+"\",\"detailid\":\""+rejectBillDetail.getId()+"\"},";
                    }
                }

            }else if("3".equals(billtype)){
                CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(billid);
                if(null != customerPushBalance){
                    ids += "{\"billtype\":\""+billtype+"\",\"billid\":\""+billid+"\",\"detailid\":\""+customerPushBalance.getId()+"\"},";
                }
            }
        }
        if(ids.endsWith(",")){
            ids = ids.substring(0, ids.length() - 1);
        }
        ids += "]";
        Map returnMap = salesInvoiceBillService.addSalesInvoiceBillByReceiptAndRejectbill(ids, customerid,"0");
        return returnMap;
    }

    @Override
	public PageData getDemandData(PageMap pageMap) throws Exception {
		DemandMapper demandMapper = (DemandMapper)SpringContextUtils.getBean("salesDemandMapper");
		List<Demand> demandList = demandMapper.getDemandList(pageMap);
		for(Demand demand : demandList){
			Map map = new HashMap();
			map.put("id", demand.getCustomerid());
			Customer customer = getBaseFilesCustomerMapper().getCustomerDetail(map);
			if(customer != null){
				demand.setCustomername(customer.getName());
			}
			Map total = demandMapper.getDemandDetailTotal(demand.getId());
			if(total != null){
				if(total.containsKey("taxamount")){
					BigDecimal taxamount = (BigDecimal) total.get("taxamount");
					demand.setField01(taxamount.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
				}
				if(total.containsKey("notaxamount")){
					BigDecimal notaxamount = (BigDecimal) total.get("notaxamount");
					demand.setField02(notaxamount.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
				}
				if(total.containsKey("tax")){
					BigDecimal tax = (BigDecimal) total.get("tax");
					demand.setField03(tax.setScale(2,BigDecimal.ROUND_HALF_UP).toString());
				}
			}
		}
		return new PageData(demandMapper.getDemandCount(pageMap), demandList, pageMap);
	}

	@Override
	public Map getOrderTrack(String id) throws Exception {
		Map map = phoneMapper.getOrderTrack(id);
		if(null==map){
			map = phoneMapper.getOrderCarTrack(id);
		}
		if(null!=map && map.containsKey("outid")){
			String outids = (String) map.get("outid");
			String[] outidarr = outids.split(",");
			String psid = "";
			String psmsg = "";
			List<Map> list = phoneMapper.getLogisticsBillBySaleoutid(outids);
			for(Map psmap : list){
				String psbillid = (String) psmap.get("id");
				if(!psid.equals(psbillid)){
					String driverid = (String) psmap.get("driverid");
					String followid = (String) psmap.get("followid");
					Personnel drpersonnel = getPersonnelById(driverid);
					Personnel flpersonnel = getPersonnelById(followid);
					if(null!=drpersonnel){
						psmsg +="<br/>配送单："+psbillid+";<br/>司机："+drpersonnel.getName()+",联系电话:<a href='javascript:callAndroid(\""+drpersonnel.getName()+"\",\""+drpersonnel.getTelphone()+"\");'>"+drpersonnel.getTelphone()+"</a>;";
					}else{
						psmsg +="<br/>配送单："+psbillid+";";
					}
					if(null!=flpersonnel){
						psmsg +="<br/>跟车："+flpersonnel.getName()+",联系电话:<a href=\'javascript:callAndroid(\""+flpersonnel.getName()+"\",\""+flpersonnel.getTelphone()+"\");'>"+flpersonnel.getTelphone()+"</a>;";
					}else{
						psmsg +=";";
					}
				}
			}
			if(StringUtils.isNotEmpty(psmsg)){
				map.put("showpsmsg", true);
				map.put("psmsg", psmsg);
			}else{
				map.put("showpsmsg", false);
				map.put("psmsg", "查询不到配送信息。");
			}
			
		}
		return map;
	}

	@Override
	public Map getCustomerInvoiceInfo(String customerid) throws Exception {
		ReceiptMapper receiptMapper = (ReceiptMapper)SpringContextUtils.getBean("salesReceiptMapper");
		Map map = receiptMapper.getCustomerInvoiceInfoData(customerid);
		if(null!=map){
			Customer customer = getCustomerByID(customerid);
			if(null!=customer){
				map.put("customername", "["+customer.getId()+"]"+customer.getName());
			}
			BigDecimal banlanceamount = BigDecimal.ZERO;
			BigDecimal invoiceamount = BigDecimal.ZERO;
			if(map.containsKey("banlanceamount")){
				banlanceamount = (BigDecimal) map.get("banlanceamount");
			}
			if(map.containsKey("invoiceamount")){
				invoiceamount = (BigDecimal) map.get("invoiceamount");
			}
			BigDecimal useableamount = BigDecimal.ZERO;
			if(banlanceamount.compareTo(invoiceamount)==1){
				useableamount= banlanceamount.subtract(invoiceamount);
			}
			map.put("useableamount", useableamount);
		}
		return map;
	}

	@Override
	public Map addCustomerInvoiceBillList(Map map) throws Exception {
		SysUser sysUser = getSysUser();
		String customerid = (String) map.get("customerid");
		String billJson = (String) map.get("json");
		ReceiptMapper receiptMapper = (ReceiptMapper)SpringContextUtils.getBean("salesReceiptMapper");
		RejectBillMapper rejectBillMapper = (RejectBillMapper)SpringContextUtils.getBean("salesRejectBillMapper");
		CustomerPushBalanceMapper customerPushBalanceMapper = (CustomerPushBalanceMapper)SpringContextUtils.getBean("customerPushBalanceMapper");
		JSONArray jsonArray = JSONArray.fromObject(billJson);
		String ids = "[";
		BigDecimal totalamount = BigDecimal.ZERO;
		for(int i=0;i<jsonArray.size();i++){
			JSONObject object = jsonArray.getJSONObject(i);
			String billtype = object.getString("billtype");
			String billid = object.getString("billid");
			String cid = object.getString("customerid");
			if("1".equals(billtype)){
				List<ReceiptDetail> detailList = receiptMapper.getReceiptDetailListSumDiscountNoInvoice(billid);
				if(null != detailList){
					for(ReceiptDetail receiptDetail : detailList){
						ids += "{\"billtype\":\""+billtype+"\",\"billid\":\""+billid+"\",\"detailid\":\""+receiptDetail.getId()+"\"},"; 
					}
				}
			}else if("2".equals(billtype)){
				List<RejectBillDetail> detaillist = rejectBillMapper.getRejectBillDetailListNoInvoiceByBill(billid);
				if(null != detaillist){
					for(RejectBillDetail rejectBillDetail : detaillist){
						ids += "{\"billtype\":\""+billtype+"\",\"billid\":\""+billid+"\",\"detailid\":\""+rejectBillDetail.getId()+"\"},";
					}
				}
				
			}else if("3".equals(billtype)){
				CustomerPushBalance customerPushBalance = customerPushBalanceMapper.showCustomerPushBanlanceInfo(billid);
				if(null != customerPushBalance){
						ids += "{\"billtype\":\""+billtype+"\",\"billid\":\""+billid+"\",\"detailid\":\""+customerPushBalance.getId()+"\"},";
				}
			}else if("4".equals(billtype)){
                ids += "{\"billtype\":\""+billtype+"\",\"billid\":\""+billid+"\",\"detailid\":\""+billid+"\"},";
                if(ids.endsWith(",")){
                    ids = ids.substring(0, ids.length() - 1);
                }
                ids += "]";
            }
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length() - 1);
		}
		ids += "]";
		Map returnMap = salesInvoiceService.addSalesInvoiceByReceiptAndRejectbill(ids, customerid,"0");
		return returnMap;
	}

	@Override
	public List getCustomerReceivablePastdueList(PageMap pageMap)
			throws Exception {
		SysUser sysUser = getSysUser();
		pageMap.getCondition().put("userId", sysUser.getUserid());
		pageMap.getCondition().put("today", CommonUtils.getTodayDataStr());
		String dataSql = getDataAccessRule("t_report_reason_base", "z");
		pageMap.setDataSql(dataSql);
		List<Map> list = salesInvoiceService.getCustomerReceivablePastdueList(pageMap);
		for(Map map : list){
			String customerid = (String) map.get("customerid");
			ReceivablePastDueReason receivablePastDueReason = salesFreeOrderMapper.getCustomerReceivablePastDueReason(customerid);
			if(null!=receivablePastDueReason){
				map.put("overreason", receivablePastDueReason.getOverreason());
				map.put("commitmentamount", receivablePastDueReason.getCommitmentamount());
				map.put("commitmentdate", receivablePastDueReason.getCommitmentdate());
			}
		}
		return list;
	}

	@Override
	public List getSalesInvoiceListByPhone(Map map) throws Exception {
		List list = salesInvoiceService.getSalesInvoiceListByPhone(map);
		return list;
	}

    /**
     * 获取销售发票列表
     *
     * @param map
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年7月16日
     */
    @Override
    public List getSalesInvoiceBillListByPhone(Map map) throws Exception {
        List list = salesInvoiceBillService.getSalesInvoiceBillListByPhone(map);
        return list;
    }

    @Override
	public boolean updateSalesInvoiceWrite(String billid) throws Exception {
		boolean flag = salesInvoiceService.updateSalesInvoiceApplyWriteOff(billid);
		return flag;
	}

	@Override
	public boolean deleteSalesInvoiceBack(String billid) throws Exception {
		boolean flag = salesInvoiceService.deletesalesInvoice(billid);
		return flag;
	}

    /**
     * 销售发票回退删除
     *
     * @param billid
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年7月17日
     */
    @Override
    public boolean deleteSalesInvoiceBillBack(String billid) throws Exception {
        boolean flag = salesInvoiceBillService.deleteSalesInvoiceBill(billid);
        return flag;
    }

}

