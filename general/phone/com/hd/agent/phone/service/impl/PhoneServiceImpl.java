/**
 * @(#)PhoneServiceImpl.java
 * @author zhengziyong
 * <p>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 7, 2013 zhengziyong 创建版本
 */
package com.hd.agent.phone.service.impl;

import com.hd.agent.accesscontrol.dao.SysUserMapper;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.basefiles.service.ISalesService;
import com.hd.agent.common.dao.AttachFileMapper;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.phone.dao.LocationMapper;
import com.hd.agent.phone.dao.PhoneMapper;
import com.hd.agent.phone.dao.RouteDistanceMapper;
import com.hd.agent.phone.model.Location;
import com.hd.agent.phone.model.RouteDistance;
import com.hd.agent.phone.service.IPhoneService;
import com.hd.agent.sales.model.*;
import com.hd.agent.sales.service.IDemandService;
import com.hd.agent.sales.service.IOrderCarService;
import com.hd.agent.sales.service.IOrderService;
import com.hd.agent.sales.service.IRejectBillService;
import com.hd.agent.sales.service.impl.BaseSalesServiceImpl;
import com.hd.agent.storage.dao.CheckListMapper;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IPurchaseEnterService;
import com.hd.agent.storage.service.IStorageSaleOutService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 *
 *
 * @author zhengziyong
 */
public class PhoneServiceImpl extends BaseSalesServiceImpl implements IPhoneService {

    private LocationMapper locationMapper;
    private PhoneMapper phoneMapper;
    private CheckListMapper checkListMapper;
    private RouteDistanceMapper distanceMapper;
    private AttachFileMapper attachFileMapper;

    private IStorageSaleOutService storageSaleOutService;
    private IPurchaseEnterService purchaseEnterService;

    private IOrderService orderService;
    private IDemandService demandService;

    private ISalesService salesService;

    private static CloseableHttpClient httpclient = HttpClients.custom().build();


    public LocationMapper getLocationMapper() {
        return locationMapper;
    }

    public void setLocationMapper(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    public PhoneMapper getPhoneMapper() {
        return phoneMapper;
    }

    public void setPhoneMapper(PhoneMapper phoneMapper) {
        this.phoneMapper = phoneMapper;
    }

    public CheckListMapper getCheckListMapper() {
        return checkListMapper;
    }

    public void setCheckListMapper(CheckListMapper checkListMapper) {
        this.checkListMapper = checkListMapper;
    }

    public RouteDistanceMapper getDistanceMapper() {
        return distanceMapper;
    }

    public void setDistanceMapper(RouteDistanceMapper distanceMapper) {
        this.distanceMapper = distanceMapper;
    }

    public IStorageSaleOutService getStorageSaleOutService() {
        return storageSaleOutService;
    }

    public void setStorageSaleOutService(
            IStorageSaleOutService storageSaleOutService) {
        this.storageSaleOutService = storageSaleOutService;
    }

    public AttachFileMapper getAttachFileMapper() {
        return attachFileMapper;
    }

    public void setAttachFileMapper(AttachFileMapper attachFileMapper) {
        this.attachFileMapper = attachFileMapper;
    }

    @Override
    public SysUser getSysUser(String userId) throws Exception {
        return getSysUserById(userId);
    }

    public IOrderService getOrderService() {
        return orderService;
    }

    public IDemandService getDemandService() {
        return demandService;
    }

    public void setOrderService(IOrderService orderService) {
        this.orderService = orderService;
    }

    public void setDemandService(IDemandService demandService) {
        this.demandService = demandService;
    }

    public IPurchaseEnterService getPurchaseEnterService() {
        return purchaseEnterService;
    }

    public void setPurchaseEnterService(IPurchaseEnterService purchaseEnterService) {
        this.purchaseEnterService = purchaseEnterService;
    }

    public ISalesService getSalesService() {
        return salesService;
    }

    public void setSalesService(ISalesService salesService) {
        this.salesService = salesService;
    }

    @Override
    public List<String> getUserAuthorityList(String userId) throws Exception {
        SysUserMapper sysUserMapper = (SysUserMapper) SpringContextUtils.getBean("sysUserMapper");
        return sysUserMapper.getUserAuthorityListById(userId);
    }

    @Override
    public PageData getGoodsInfoListBySysUser(PageMap pageMap) throws Exception {
        Map map = new HashMap();
        map.put("startNum", pageMap.getStartNum());
        map.put("rows", pageMap.getRows());
        map.put("islimit", "true");
        String dataSql = getDataAccessRule("t_base_goods_info", "a");
        map.put("datasql", dataSql);
        SysUser sysUser = getSysUser();
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if (isBrandUser) {
            map.put("isBrandUser", true);
            map.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                map.put("isSupplierUser", true);
                map.put("personnelid", sysUser.getPersonnelid());
            }
        }
        List<PGoodsInfo> list = getBaseFilesGoodsMapper().getAllGoodsInfoPhone(map);
        List<Map> result = new ArrayList<Map>();
        for (PGoodsInfo goodsInfo : list) {
            Map datamap = new HashMap();
            datamap.put("id", goodsInfo.getId());
            datamap.put("name", goodsInfo.getName());
            datamap.put("price", goodsInfo.getPrice());
            datamap.put("inprice", goodsInfo.getInprice());
            datamap.put("spell", goodsInfo.getSpell());
            datamap.put("barcode", goodsInfo.getBarcode());
            datamap.put("boxbarcode", goodsInfo.getBoxbarcode());
            datamap.put("model", goodsInfo.getModel() == null ? "" : goodsInfo.getModel());
            datamap.put("isbatch", goodsInfo.getIsbatch());
            datamap.put("sbarcode", goodsInfo.getSbarcode());
            datamap.put("brand", goodsInfo.getBrand() == null ? "" : goodsInfo.getBrand());
            datamap.put("defaultsort", goodsInfo.getDefaultsort() == null ? "" : goodsInfo.getDefaultsort());
            datamap.put("goodstype", goodsInfo.getGoodstype() == null ? "" : goodsInfo.getGoodstype());
            datamap.put("supplierid", goodsInfo.getSupplierid() == null ? "" : goodsInfo.getSupplierid());
            datamap.put("unitid", goodsInfo.getUnitid());
            datamap.put("unitname", goodsInfo.getUnitname());
            datamap.put("auxunitid", goodsInfo.getAuxunitid());
            datamap.put("auxunitname", goodsInfo.getAuxunitname());
            datamap.put("rate", goodsInfo.getRate());
            datamap.put("minimum", goodsInfo.getMinimum().intValue()+"");
            result.add(datamap);
        }
        PageData pageData = new PageData(getBaseFilesGoodsMapper().getAllGoodsInfoPhoneCount(map), result, pageMap);
        return pageData;
    }

    @Override
    public List getAllGoodsInfoList() throws Exception {
        List list = getBaseFilesGoodsMapper().getAllGoodsInfoPhone(null);
        return list;
    }

    @Override
    public List getAllBrandList() throws Exception {
        List list = getBrandlSyncList(null);
        return list;
    }

    @Override
    public PageData getAllPriceInfoList(PageMap pageMap) throws Exception {
        Map map = new HashMap();
        map.put("startNum", pageMap.getStartNum());
        map.put("rows", pageMap.getRows());
        String dataSql = getDataAccessRule("t_base_goods_info", "a");
        map.put("datasql", dataSql);
        SysUser sysUser = getSysUser();
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if (isBrandUser) {
            map.put("isBrandUser", true);
            map.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                map.put("isSupplierUser", true);
                map.put("personnelid", sysUser.getPersonnelid());
            }
        }
        List<GoodsInfo_PriceInfo> priceList = getBaseFilesGoodsMapper().getAllPriceInfoList(map);
        List<Map> result = new ArrayList<Map>();
        for (GoodsInfo_PriceInfo priceInfo : priceList) {
            Map datamap = new HashMap();
            datamap.put("goodsid", priceInfo.getGoodsid());
            datamap.put("sortid", priceInfo.getCode());
            datamap.put("taxprice", priceInfo.getTaxprice());
            datamap.put("taxtype", priceInfo.getTaxtype());
            result.add(datamap);
        }
        PageData pageData = new PageData(getBaseFilesGoodsMapper().getAllPriceInfoListCount(map), result, pageMap);
        return pageData;
    }

    @Override
    public List getCustomerBySalesman(String username) throws Exception {
        return getBaseFilesCustomerMapper().getCustomerBySalesman(username);
    }

    @Override
    public List getCustomerBySalesmanId() throws Exception {
        List list = getCustomerSyncList(null);
        return list;
    }

    @Override
    public PageData getCustomerPriceList(PageMap pageMap) throws Exception {
        Map map = new HashMap();
        map.put("startNum", pageMap.getStartNum());
        map.put("rows", pageMap.getRows());
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        SysUser sysUser = getSysUser();
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if (isBrandUser) {
            map.put("isBrandUser", true);
            map.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                map.put("isSupplierUser", true);
                map.put("personnelid", sysUser.getPersonnelid());
            }
        }
        String dataSql = getDataAccessRule("t_base_sales_customer", "t");
        map.put("datasql", dataSql);
        //设置最大长度
        List<CustomerPrice> priceList = getBaseFilesCustomerMapper().getCustomerPriceList(map);
        List<Map> result = new ArrayList<Map>();
        for (CustomerPrice price : priceList) {
            Map datamap = new HashMap();
            datamap.put("customerid", price.getCustomerid());
            datamap.put("goodsid", price.getGoodsid());
            datamap.put("price", price.getPrice());
            result.add(datamap);
        }
        PageData pageData = new PageData(getBaseFilesCustomerMapper().getCustomerPriceListCount(map), result, pageMap);
        return pageData;
    }

    @Override
    public int getCustomerPriceCount(String userId) throws Exception {
        int count = 0;
        Personnel personnel = getBasePersonnelMapper().getPersonnelByUserId(userId);
        if (personnel != null) {
            //3表示品牌业务员 5表示车销人员 1或者其他表示客户业务员
            if ("3".equals(personnel.getEmployetype())) {
                count = getBaseFilesCustomerMapper().getBrandSalerCustomerPriceCount(userId);
            } else if ("5".equals(personnel.getEmployetype())) {
                count = getBaseFilesCustomerMapper().getAllCustomerPriceCount();
            } else {
                count = getBaseFilesCustomerMapper().getCustomerPriceCount(userId);
            }
        }
//		list = getBaseFilesCustomerMapper().getAllCustomerPriceList(userId);
        return count;
    }

    @Override
    public PageData getCustomerGoodsList(PageMap pageMap) throws Exception {
        Map map = new HashMap();
        map.put("startNum", pageMap.getStartNum());
        map.put("rows", pageMap.getRows());
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        SysUser sysUser = getSysUser();
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if (isBrandUser) {
            map.put("isBrandUser", true);
            map.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                map.put("isSupplierUser", true);
                map.put("personnelid", sysUser.getPersonnelid());
            }
        }
        String dataSql = getDataAccessRule("t_base_sales_customer", "t");
        map.put("datasql", dataSql);
        List<CustomerGoods> list = getBaseFilesCustomerMapper().getSalerCustomerGoodsList(map);
        List result = new ArrayList();
        for (CustomerGoods goods : list) {
            Map datamap = new HashMap();
            datamap.put("goodsid", goods.getGoodsid());
            datamap.put("customerid", goods.getCustomerid());
            result.add(datamap);
        }
        PageData pageData = new PageData(getBaseFilesCustomerMapper().getSalerCustomerGoodsListCount(map), result, pageMap);
        return pageData;
    }

    @Override
    public List getSameFinanceList() throws Exception {
        return getBaseFilesFinanceMapper().getSameFinanceList();
    }

    @Override
    public Map getSyncFlag(String syncdate) throws Exception {
        Map map = new HashMap();
        boolean syncflag = false;
        boolean syncAllFlag = false;
        //判断商品档案是否需要更新
        List goodsList = getGoodsSyncList(syncdate);
        if (null != goodsList && goodsList.size() > 0) {
            if (goodsList.size() > 10) {
                syncAllFlag = true;
            } else {
                syncflag = true;
            }
        }
        if (!syncAllFlag) {
            //判断客户档案是否需要更新
            List customerList = getCustomerSyncList(syncdate);
            if (null != customerList && customerList.size() > 0) {
                if (customerList.size() > 10) {
                    syncAllFlag = true;
                } else {
                    syncflag = true;
                }
            }
            //判断客户档案是否需要更新
            List dirList = getDistributionRuleList(syncdate);
            if (null != dirList && dirList.size() > 0) {
                if (dirList.size() > 50) {
                    syncAllFlag = true;
                } else {
                    syncflag = true;
                }
            }
        }
        if (!syncAllFlag) {
            //判断品牌档案是否需要更新
            List brandList = getBrandlSyncList(syncdate);
            if (null != brandList && brandList.size() > 0) {
                if (brandList.size() > 10) {
                    syncAllFlag = true;
                } else {
                    syncflag = true;
                }
            }
        }
        //判断商品价格套是否需要更新
        List goodsPriceList = getGoodsPriceSyncList(syncdate);
        if (null != goodsPriceList && goodsPriceList.size() > 0) {
            if (goodsPriceList.size() < 2000) {
                syncflag = true;
            } else {
                syncAllFlag = true;
            }
        }
        if (!syncflag) {
            List customerPriceList = getCustomerPriceSyncList(syncdate);
            if (null != customerPriceList && customerPriceList.size() > 0) {
                if (customerPriceList.size() < 5000) {
                    syncflag = true;
                } else {
                    syncAllFlag = true;
                }
            }
        }
        if (!syncflag) {
            List customerGoodsList = getCustomerGoodsSyncList(syncdate);
            if (null != customerGoodsList && customerGoodsList.size() > 0) {
                if (customerGoodsList.size() < 5000) {
                    syncflag = true;
                } else {
                    syncAllFlag = true;
                }
            }
        }
        map.put("syncFlag", syncflag);
        map.put("syncAllFlag", syncAllFlag);
        return map;
    }

    @Override
    public Map getSyncBaseData(String syncdate) throws Exception {
        Map map = new HashMap();
        //获取商品档案需要更新列表
        List goodsList = getGoodsSyncList(syncdate);
        if (null != goodsList && goodsList.size() > 0) {
            map.put("goodsList", goodsList);
        }
        //获取客户档案需要更新列表
        List customerList = getCustomerSyncList(syncdate);
        if (null != customerList && customerList.size() > 0) {
            map.put("customerList", customerList);
        }
        //获取客户档案需要更新列表
        List distributionRuleList = getDistributionRuleList(syncdate);
        if (null != distributionRuleList && distributionRuleList.size() > 0) {
            map.put("distributionRuleList", distributionRuleList);
        }
        //获取妻品牌档案需要更新列表
        List brandList = getBrandlSyncList(syncdate);
        if (null != brandList && brandList.size() > 0) {
            map.put("brandList", brandList);
        }
        //获取商品价格套需要更新列表
        List goodsPriceList = getGoodsPriceSyncList(syncdate);
        if (null != goodsPriceList && goodsPriceList.size() > 0) {
            map.put("goodsPriceList", goodsPriceList);
        }
        //获取客户合同价需要更新列表
        List customerPriceList = getCustomerPriceSyncList(syncdate);
        if (null != customerPriceList && customerPriceList.size() > 0) {
            map.put("customerPriceList", customerPriceList);
        }
        //获取客户产品需要更新列表
        List customerGoodsList = getCustomerGoodsSyncList(syncdate);
        if (null != customerGoodsList && customerGoodsList.size() > 0) {
            map.put("customerGoodsList", customerGoodsList);
        }
        return map;
    }

    @Override
    public PageData searchCustomerGoodsList(PageMap pageMap) throws Exception {
        String con = (String) pageMap.getCondition().get("con");
        String brandids = (String) pageMap.getCondition().get("brandids");
        String ispromotion = (String) pageMap.getCondition().get("ispromotion");
        String customerid = (String) pageMap.getCondition().get("customerid");
        String type = (String) pageMap.getCondition().get("type");
        List<PGoodsInfo> list = null;
        Map map = new HashMap();
        if (StringUtils.isNotEmpty(con)) {
            map.put("goodscon", con);
            //多条件模糊查询 以空格为标识
            if (con.indexOf(" ") >= 0) {
                String[] conArr = con.split(" ");
                List<String> conList = new ArrayList<String>();
                for (String constr : conArr) {
                    constr = constr.trim();
                    if (StringUtils.isNotEmpty(constr)) {
                        conList.add(constr);
                    }
                }
                map.put("conarr", conList);
            }
        }
        map.put("brandids", brandids);
        String dataSql = getDataAccessRule("t_base_goods_info", "g");
        map.put("datasql", dataSql);
        map.put("islimit", "1");
        map.put("ispromotion", ispromotion);
        SysUser sysUser = getSysUser();
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if (isBrandUser) {
            map.put("isBrandUser", true);
            map.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                map.put("isSupplierUser", true);
                map.put("personnelid", sysUser.getPersonnelid());
            }
        }
        //买赠捆绑
        boolean isPromotion = true;
        if (isPromotion) {
            Customer customer = getCustomerByID(customerid);
            //1单一客户2促销分类3分类客户4价格套5销售区域7信用等级6总店客户
            if (customer != null) {
                Map priceMap = new HashMap();
                //单一客户
                priceMap.put("type", "1");
                priceMap.put("customerid", customerid);
                //促销分类
                String csort = customer.getPromotionsort();
                if (StringUtils.isNotEmpty(csort)) {
                    priceMap.put("promotionsort", csort);
                }
                //取客户的默认分类
                String customerSort = customer.getCustomersort();
                if (StringUtils.isNotEmpty(customerSort)) {
                    priceMap.put("customersort", customerSort);
                }
                //取客户价格套
                String priceSort = customer.getPricesort();
                if (StringUtils.isNotEmpty(priceSort)) {
                    priceMap.put("pricesort", priceSort);
                }
                //取客户所属销售区域
                String saleArea = customer.getSalesarea();
                if (StringUtils.isNotEmpty(saleArea)) {
                    priceMap.put("salesarea", saleArea);
                }
                //取客户 总店
                String pcustomerid = customer.getPid();
                if (StringUtils.isNotEmpty(pcustomerid)) {
                    priceMap.put("pcustomerid", pcustomerid);
                }
                //取信用等级
                String credit = customer.getCreditrating();
                if (StringUtils.isNotEmpty(credit)) {
                    priceMap.put("credit", credit);
                }
                //取核销方式
                String canceltype = customer.getCanceltype();
                if (StringUtils.isNotEmpty(canceltype)) {
                    priceMap.put("canceltype", canceltype);
                }
                map.put("promotionMap", priceMap);
            }
        }
        map.put("startNum", pageMap.getStartNum());
        map.put("rows", pageMap.getRows());

        if ("true".equals(pageMap.getCondition().get("distribution"))) {
            List okDistributions = salesService.selectDistributionRuleIdByCustomer(customerid, "1");
            if(okDistributions.size() > 0) {

                map.put("okDistributions", okDistributions);
            }
            List ngDistributions = salesService.selectDistributionRuleIdByCustomer(customerid, "0");
            if(ngDistributions.size() > 0) {

                map.put("ngDistributions", ngDistributions);
            }
        }

        int totalCount = 0;
        if (StringUtils.isEmpty(type) || "0".equals(type)) {
            list = getBaseFilesGoodsMapper().getGoodsInfoListFroPhone(map);
            totalCount = getBaseFilesGoodsMapper().getGoodsInfoListFroPhoneCount(map);
        } else if ("1".equals(type)) {
            map.put("customerid", customerid);
            list = getBaseFilesGoodsMapper().getCustomerGoodsInfoPhone(map);
            totalCount = getBaseFilesGoodsMapper().getCustomerGoodsInfoPhoneCount(map);
        } else if ("2".equals(type)) {
            map.put("customerid", customerid);
            list = getBaseFilesGoodsMapper().getCustomerNoSalesGoodsInfoPhone(map);
            totalCount = getBaseFilesGoodsMapper().getCustomerNoSalesGoodsInfoPhoneCount(map);
        }
        List<Map> result = new ArrayList<Map>();
        if (null != list) {
            for (PGoodsInfo goodsInfo : list) {
                Map datamap = new HashMap();
                datamap.put("id", goodsInfo.getId());
                datamap.put("ptype", goodsInfo.getPtype());
                String name = "";
                if ("1".equals(goodsInfo.getPtype())) {
                    name = "(买赠)" + goodsInfo.getName();
                } else if ("2".equals(goodsInfo.getPtype())) {
                    name = "(捆绑)" + goodsInfo.getName();
                } else {
                    name = goodsInfo.getName();
                }
                datamap.put("name", name);
                datamap.put("spell", goodsInfo.getSpell());
                datamap.put("barcode", goodsInfo.getBarcode());
                datamap.put("boxbarcode", goodsInfo.getBoxbarcode());
                datamap.put("brand", goodsInfo.getBrand());
                datamap.put("defaultsort", goodsInfo.getDefaultsort());
                GoodsInfo goods = getAllGoodsInfoByID(goodsInfo.getId());
                if (null != goods) {
                    datamap.put("auxunitname", goods.getAuxunitname());
                    datamap.put("unitname", goods.getMainunitName());
                    datamap.put("boxnum", goods.getBoxnum());
                } else {
                    datamap.put("auxunitname", "箱");
                    datamap.put("unitname", "");
                    datamap.put("boxnum", "1");
                }

//                //获取客户 该商品的价格
//                OrderDetail orderDetail = orderService.getGoodsDetail(goodsInfo.getId(), customerid, CommonUtils.getTodayDataStr(), 0, null);
//                if(null!=orderDetail){
//                	datamap.put("price", orderDetail.getTaxprice());
//                }else{
//                	datamap.put("price", goodsInfo.getPrice());
//                }
                result.add(datamap);
            }
        }
        PageData pageData = new PageData(totalCount, result, pageMap);
        return pageData;
    }

    @Override
    public Map getCustomerGoodsInfo(String customerid, String goodsid, String type) throws Exception {
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        if (null != goodsInfo) {
            Map map = new HashMap();
            map.put("goodsid", goodsid);
            map.put("name", goodsInfo.getName());
            map.put("barcode", goodsInfo.getBarcode());
            map.put("spell", goodsInfo.getSpell()==null?"":goodsInfo.getSpell());
            map.put("model", goodsInfo.getModel()==null?"":goodsInfo.getModel());
            map.put("unitid", goodsInfo.getMainunit());
            map.put("unitname", goodsInfo.getMainunitName());
            if (StringUtils.isNotEmpty(goodsInfo.getAuxunitid())) {
                map.put("auxunitid", goodsInfo.getAuxunitid());
                map.put("auxunitname", goodsInfo.getAuxunitname());
                map.put("rate", goodsInfo.getBoxnum());
            } else {
                map.put("auxunitid", goodsInfo.getMainunit());
                map.put("auxunitname", goodsInfo.getMainunitName());
                map.put("rate", "1");
            }
            OrderDetail orderDetail = orderService.getGoodsDetail(goodsInfo.getId(), customerid, CommonUtils.getTodayDataStr(), BigDecimal.ZERO, type);
            if (null != orderDetail) {
                map.put("price", orderDetail.getTaxprice());
                map.put("remark", orderDetail.getRemark());
            } else {
                map.put("price", goodsInfo.getBasesaleprice());
                map.put("remark", "");
            }

             map.put("mininum", goodsInfo.getMinimum().intValue()+"");
            return map;
        }
        return null;
    }

    @Override
    public PageData showGoodsImageInfoList(PageMap pageMap) throws Exception {
        //判断商品档案是否需要同步
        String brandIds = (String) pageMap.getCondition().get("brandIds");
        String condition = (String) pageMap.getCondition().get("condition");
        Map map = new HashMap();
        if (StringUtils.isNotEmpty(brandIds)) {
            map.put("brandids", brandIds);
        }
        if (StringUtils.isNotEmpty(condition)) {
            map.put("goodscon", condition);
        }
        map.put("islimit", true);
        String dataSql = getDataAccessRule("t_base_goods_info", "a");
        map.put("datasql", dataSql);
        String IsUseStorageDatarule = getSysParamValue("IsUseStorageDatarule");
        if ("1".equals(IsUseStorageDatarule)) {
            String dataSqlStorage = getDataAccessRule("t_storage_summary", "t");
            map.put("dataSqlStorage", dataSqlStorage);
        }
        SysUser sysUser = getSysUser();
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if (isBrandUser) {
            map.put("isBrandUser", true);
            map.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                map.put("isSupplierUser", true);
                map.put("personnelid", sysUser.getPersonnelid());
            }
        }
        map.put("startNum", pageMap.getStartNum());
        map.put("rows", pageMap.getRows());
        List<PGoodsInfo> list = getBaseFilesGoodsMapper().getAllGoodsInfoPhone(map);
        List<Map> result = new ArrayList<Map>();
        for (PGoodsInfo goods : list) {
            Map datamap = new HashMap();
            String goodsid = goods.getId();
            GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
            if (null != goodsInfo) {
                datamap.put("id", goodsid);
                datamap.put("name", "[" + goodsid + "]" + goodsInfo.getName());
                datamap.put("content", goodsInfo.getBarcode());
                String mainImage = goodsInfo.getImage();
                String otherImages = goodsInfo.getImageids();
                if (StringUtils.isNotEmpty(mainImage)) {
                    datamap.put("avator", mainImage);
                } else {
                    datamap.put("avator", "");
                }
                if (StringUtils.isNotEmpty(otherImages)) {
                    String[] imagesArr = otherImages.split(",");
                    int len = imagesArr.length;
                    String[] urls = new String[len];
                    for (int i = 0; i < imagesArr.length; i++) {
                        AttachFile attachFile = attachFileMapper.getAttachFile(imagesArr[i]);
                        if (null != attachFile) {
                            urls[i] = attachFile.getFullpath();
                        }
                    }
                    datamap.put("urls", urls);
                } else {
                    if (StringUtils.isNotEmpty(mainImage)) {
                        String[] urls = {mainImage};
                        datamap.put("urls", urls);
                    }
                }
                if (StringUtils.isNotEmpty(goods.getRemark())) {
                    String remark = CommonUtils.strDigitNumDeal(goods.getRemark());
                    datamap.put("remark", remark);
                } else {
                    datamap.put("remark", "库存为空");
                }
            }
            result.add(datamap);
        }
        PageData pageData = new PageData(getBaseFilesGoodsMapper().getAllGoodsInfoPhoneCount(map), result, pageMap);
        return pageData;
    }

    /**
     * 根据同步日期 获取商品档案需要更新的数据
     * @param syncdate
     * @return
     * @throws Exception
     */
    public List getGoodsSyncList(String syncdate) throws Exception {
        //判断商品档案是否需要同步
        Map map = new HashMap();
        if (StringUtils.isNotEmpty(syncdate)) {
            map.put("syncdate", syncdate);
        }
        String dataSql = getDataAccessRule("t_base_goods_info", "a");
        map.put("datasql", dataSql);
        SysUser sysUser = getSysUser();
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if (isBrandUser) {
            map.put("isBrandUser", true);
            map.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                map.put("isSupplierUser", true);
                map.put("personnelid", sysUser.getPersonnelid());
            }
        }
        List<PGoodsInfo> list = getBaseFilesGoodsMapper().getAllGoodsInfoPhone(map);
        List<Map> result = new ArrayList<Map>();
        for (PGoodsInfo goodsInfo : list) {
            Map datamap = new HashMap();
            datamap.put("id", goodsInfo.getId());
            datamap.put("name", goodsInfo.getName());
            datamap.put("price", goodsInfo.getPrice());
            datamap.put("inprice", goodsInfo.getInprice());
            datamap.put("spell", goodsInfo.getSpell());
            datamap.put("barcode", goodsInfo.getBarcode());
            datamap.put("boxbarcode", goodsInfo.getBoxbarcode());
            datamap.put("model", goodsInfo.getModel() == null ? "" : goodsInfo.getModel());
            datamap.put("isbatch", goodsInfo.getIsbatch());
            datamap.put("sbarcode", goodsInfo.getSbarcode());
            datamap.put("brand", goodsInfo.getBrand() == null ? "" : goodsInfo.getBrand());
            datamap.put("defaultsort", goodsInfo.getDefaultsort() == null ? "" : goodsInfo.getDefaultsort());
            datamap.put("goodstype", goodsInfo.getGoodstype() == null ? "" : goodsInfo.getGoodstype());
            datamap.put("supplierid", goodsInfo.getSupplierid() == null ? "" : goodsInfo.getSupplierid());
            datamap.put("unitid", goodsInfo.getUnitid());
            datamap.put("unitname", goodsInfo.getUnitname());
            datamap.put("auxunitid", goodsInfo.getAuxunitid());
            datamap.put("auxunitname", goodsInfo.getAuxunitname());
            datamap.put("rate", goodsInfo.getRate());
            result.add(datamap);
        }
        return result;
    }

    /**
     * 根据同步日期 获取客户档案需要同步的数据
     * @param syncdate
     * @return
     * @throws Exception
     */
    public List getCustomerSyncList(String syncdate) throws Exception {
        //判断客户档案是否需要同步
        Map customerMap = new HashMap();
        SysUser sysUser = getSysUser();
        if (StringUtils.isNotEmpty(syncdate)) {
            customerMap.put("syncdate", syncdate);
        }
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        if (isBrandUser) {
            customerMap.put("isBrandUser", true);
            customerMap.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                customerMap.put("isSupplierUser", true);
                customerMap.put("personnelid", sysUser.getPersonnelid());
            }
        }
        String customerDataSql = getDataAccessRule("t_base_sales_customer", "t");
        customerMap.put("datasql", customerDataSql);
        List<Customer> customerList = getBaseFilesCustomerMapper().getAllCustomerWithoutPcustomer(customerMap);
        List<Map> result = new ArrayList<Map>();
        for (Customer customer : customerList) {
            Map datamap = new HashMap();
            datamap.put("id", customer.getId());
            datamap.put("pid", customer.getPid());
            datamap.put("name", customer.getName());
            datamap.put("spell", customer.getShortcode());
            datamap.put("pinyin", customer.getPinyin());
            datamap.put("settletype", customer.getSettletype());
            datamap.put("paytype", customer.getPaytype());
            datamap.put("billtype", customer.getBilltype());
            datamap.put("pricesort", customer.getPricesort());
            datamap.put("islast", customer.getIslast());
            datamap.put("addr", customer.getAddress());

            datamap.put("customersort", customer.getCustomersort() == null ? "" : customer.getCustomersort());
            datamap.put("promotionsort", customer.getPromotionsort() == null ? "" : customer.getPromotionsort());
            datamap.put("salesarea", customer.getSalesarea() == null ? "" : customer.getSalesarea());
            datamap.put("creditrating", customer.getCreditrating() == null ? "" : customer.getCreditrating());
            datamap.put("canceltype", customer.getCanceltype() == null ? "" : customer.getCanceltype());
            result.add(datamap);
        }
        return result;
    }

    /**
     * 根据同步日期 获取品牌需要更新的列表
     * @param syncdate
     * @return
     * @throws Exception
     */
    public List getBrandlSyncList(String syncdate) throws Exception {
        Map map = new HashMap();
        if (StringUtils.isNotEmpty(syncdate)) {
            map.put("syncdate", syncdate);
        }
        String dataSql = getDataAccessRule("t_base_goods_brand", "t");
        map.put("datasql", dataSql);
        SysUser sysUser = getSysUser();
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if (isBrandUser) {
            map.put("isBrandUser", true);
            map.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                map.put("isSupplierUser", true);
                map.put("personnelid", sysUser.getPersonnelid());
            }
        }
        List<Brand> brandList = getBaseFilesGoodsMapper().getAllBrandPhone(map);
        List<Map> result = new ArrayList<Map>();
        for (Brand brand : brandList) {
            Map datamap = new HashMap();
            datamap.put("id", brand.getId());
            datamap.put("name", brand.getName());
            result.add(datamap);
        }
        return result;
    }

    /**
     * 根据同步日期 获取商品价格套需要更新的数据
     * @param syncdate
     * @return
     * @throws Exception
     */
    public List getGoodsPriceSyncList(String syncdate) throws Exception {
        Map map = new HashMap();
        if (StringUtils.isNotEmpty(syncdate)) {
            map.put("syncdate", syncdate);
        }
        String dataSql = getDataAccessRule("t_base_goods_info", "a");
        map.put("datasql", dataSql);
        SysUser sysUser = getSysUser();
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if (isBrandUser) {
            map.put("isBrandUser", true);
            map.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                map.put("isSupplierUser", true);
                map.put("personnelid", sysUser.getPersonnelid());
            }
        }
        List<GoodsInfo_PriceInfo> priceList = getBaseFilesGoodsMapper().getAllPriceInfoList(map);
        List<Map> result = new ArrayList<Map>();
        for (GoodsInfo_PriceInfo priceInfo : priceList) {
            Map datamap = new HashMap();
            datamap.put("goodsid", priceInfo.getGoodsid());
            datamap.put("sortid", priceInfo.getCode());
            datamap.put("taxprice", priceInfo.getTaxprice());
            datamap.put("taxtype", priceInfo.getTaxtype());
            result.add(datamap);
        }
        return result;
    }

    /**
     * 根据同步日期 获取客户合同价需要更新的列表
     * @param syncdate
     * @return
     * @throws Exception
     */
    public List getCustomerPriceSyncList(String syncdate) throws Exception {
        Map map = new HashMap();
        map.put("syncdate", syncdate);
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        SysUser sysUser = getSysUser();
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if (isBrandUser) {
            map.put("isBrandUser", true);
            map.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                map.put("isSupplierUser", true);
                map.put("personnelid", sysUser.getPersonnelid());
            }
        }
        String dataSql = getDataAccessRule("t_base_sales_customer", "t");
        map.put("datasql", dataSql);
        List<CustomerPrice> priceList = getBaseFilesCustomerMapper().getCustomerPriceList(map);
        List<Map> result = new ArrayList<Map>();
        for (CustomerPrice price : priceList) {
            Map datamap = new HashMap();
            datamap.put("customerid", price.getCustomerid());
            datamap.put("goodsid", price.getGoodsid());
            datamap.put("price", price.getPrice());
            result.add(datamap);
        }
        return result;
    }

    /**
     * 根据同步日期 获取客户产品需要更新列表
     * @param syncdate
     * @return
     * @throws Exception
     */
    public List getCustomerGoodsSyncList(String syncdate) throws Exception {
        Map map = new HashMap();
        if (StringUtils.isNotEmpty(syncdate)) {
            map.put("syncdate", syncdate);
        }
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        SysUser sysUser = getSysUser();
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if (isBrandUser) {
            map.put("isBrandUser", true);
            map.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                map.put("isSupplierUser", true);
                map.put("personnelid", sysUser.getPersonnelid());
            }
        }
        String dataSql = getDataAccessRule("t_base_sales_customer", "t");
        map.put("datasql", dataSql);
        List<CustomerGoods> list = getBaseFilesCustomerMapper().getSalerCustomerGoodsList(map);
        List result = new ArrayList();
        for (CustomerGoods goods : list) {
            Map datamap = new HashMap();
            datamap.put("goodsid", goods.getGoodsid());
            datamap.put("customerid", goods.getCustomerid());
            result.add(datamap);
        }
        return result;
    }

    @Override
    public Map updateOrder(JSONObject jsonObject) throws Exception {
        Map returnMap = new HashMap();
        boolean flag = false;
        String keyid = "";
        if (jsonObject.has("keyid")) {
            keyid = jsonObject.getString("keyid");
        } else {
            keyid = jsonObject.getString("orderid");
        }
        boolean isHaveFlag = demandService.isDemandHaveByKeyid(keyid);
        if (isHaveFlag) {
            returnMap.put("flag", flag);
            returnMap.put("isupload", true);
            returnMap.put("msg", "");
            return returnMap;
        }
        returnMap.put("isupload", false);
        String customerId = jsonObject.getString("customerid");
        Customer customer = getCustomerByID(customerId);
        //判断客户是否在系统中存在
        if (null == customer) {
            returnMap.put("flag", false);
            returnMap.put("msg", "客户编号:" + customerId + "，在系统中不存在。");
            return returnMap;
        } else {
            BigDecimal totalAmount = BigDecimal.ZERO;
            int listCount = 0;
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            Demand demand = new Demand();
            demand.setId(jsonObject.getString("orderid"));
            demand.setKeyid(keyid);
            demand.setBusinessdate(CommonUtils.getTodayDataStr());
            demand.setStatus("0");
            demand.setCustomerid(jsonObject.getString("customerid"));
            demand.setPaytype(jsonObject.getString("paytype"));
            SysUser sysUser = getSysUser();
            if (customer != null) {
                demand.setCustomersort(customer.getCustomersort());
                demand.setHandlerid(customer.getContact());
                demand.setSalesarea(customer.getSalesarea());
                demand.setSalesdept(customer.getSalesdeptid());
                demand.setSalesuser(customer.getSalesuserid());
                demand.setSettletype(customer.getSettletype());
            }
            //要货通知单客户业务员获取方式 1取客户默认业务员2取制单人为客户业务员
            String salesUserDemandType = getSysParamValue("SalesUserDemandType");
            if("2".equals(salesUserDemandType)){
                Personnel personnel = getPersonnelById(sysUser.getPersonnelid());
                if(null!=personnel){
                    demand.setSalesdept(personnel.getBelongdeptid());
                    demand.setSalesuser(personnel.getId());
                }
            }
            if (sysUser != null) {
                demand.setAdddeptid(sysUser.getDepartmentid());
                demand.setAdddeptname(sysUser.getDepartmentname());
                demand.setAdduserid(sysUser.getUserid());
                demand.setAddusername(sysUser.getName());
            }
            if (isAutoCreate("t_sales_demand")) {
                // 获取自动编号
                String id = getAutoCreateSysNumbderForeign(demand, "t_sales_demand");
                demand.setId(id);
            } else {
                demand.setId("SJXD-" + CommonUtils.getDataNumberSeconds());
            }
            List<DemandDetail> detailList = new ArrayList<DemandDetail>();
            if (jsonArray != null && !jsonArray.isEmpty()) {
                int seq = 1;
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    String num = "0";
                    if (json.has("num")) {
                        num = json.getString("num");
                        if ("null".equals(num)) {
                            num = "0";
                        }
                    }
                    if (StringUtils.isNotEmpty(num)) {

                        String gtype = json.getString("gtype");
                        String goodsId = json.getString("goodsid");
                        String groupid = json.getString("groupid");

                        DemandDetail detail = new DemandDetail();
                        detail.setOrderid(json.getString("orderid"));
                        detail.setDeliverytype(gtype);
                        detail.setGroupid(groupid);
                        detail.setGoodsid(json.getString("goodsid"));
                        GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
                        detail.setGoodsInfo(goodsInfo);
                        if (null != goodsInfo) {
                            //品牌部门
                            Brand brand = getGoodsBrandByID(goodsInfo.getBrand());
                            if (null != brand) {
                                detail.setBranddept(brand.getDeptid());
                            }
                            //根据客户编号和品牌编号 获取品牌业务员
                            detail.setBranduser(getBrandUseridByCustomeridAndBrand(goodsInfo.getBrand(), demand.getCustomerid()));
                            //厂家业务员
                            detail.setSupplieruser(getSupplieruserByCustomeridAndBrand(goodsInfo.getBrand(), demand.getCustomerid()));
                            String price = json.getString("price");
                            String amount = json.getString("amount");
                            detail.setUnitnum(new BigDecimal(num));
                            detail.setUnitid(goodsInfo.getMainunit());
                            detail.setUnitname(goodsInfo.getMainunitName());
                            MeteringUnit meteringUnit = getGoodsAuxUnitInfoByGoodsid(detail.getGoodsid());
                            if (null != meteringUnit) {
                                detail.setAuxunitid(meteringUnit.getId());
                                detail.setAuxunitname(meteringUnit.getName());
                            }
                            Map map = countGoodsInfoNumber(detail.getGoodsid(), detail.getAuxunitid(), detail.getUnitnum());
                            if (map.containsKey("auxInteger")) {
                                detail.setAuxnum(new BigDecimal(map.get("auxInteger").toString()));
                            }
                            if (map.containsKey("auxremainder")) {
                                detail.setOvernum(new BigDecimal(map.get("auxremainder").toString()));
                            }
                            if (map.containsKey("auxnumdetail")) {
                                detail.setAuxnumdetail(map.get("auxnumdetail").toString());
                            }
                            if (map.containsKey("auxnum")) {
                                detail.setTotalbox(new BigDecimal(map.get("auxnum").toString()));
                            }
                            //gtype 0是正常商品1是买赠2是捆绑
                            //正常商品需要重新获取最新价格
                            BigDecimal bnum = new BigDecimal(num);
                            OrderDetail orderDetail = orderService.getGoodsDetail(goodsId, customerId, CommonUtils.getTodayDataStr(), bnum, null);
                            detail.setOldprice(orderDetail.getTaxprice());
                            detail.setTaxprice(new BigDecimal(price));
                            detail.setTaxtype(goodsInfo.getDefaulttaxtype());
                            BigDecimal taxamount=detail.getUnitnum().multiply(detail.getTaxprice());
                            detail.setTaxamount(taxamount);
                            BigDecimal noTaxAmount = getNotaxAmountByTaxAmount(taxamount, detail.getTaxtype());
                            detail.setNotaxamount(noTaxAmount);
                            BigDecimal tax = detail.getTaxamount().subtract(detail.getNotaxamount());
                            detail.setTax(tax);
                            TaxType taxType = getTaxType(detail.getTaxtype());
                            BigDecimal noTaxPrice = detail.getTaxprice().divide(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
                            detail.setNotaxprice(noTaxPrice);
                            String remark = json.getString("remark") == null ? "" : json.getString("remark");
                            remark = CommonUtils.escapeStr(remark);
                            detail.setRemark(remark);

                            detail.setSeq(seq);
                            seq++;
                            detailList.add(detail);
                            //总金额
                            totalAmount = totalAmount.add(detail.getTaxamount());
                            listCount++;
                        }
                    }

                }
            }
            demand.setDetailList(detailList);
            flag = demandService.addDemand(demand);
            returnMap.put("flag", flag);
            returnMap.put("id", demand.getId());
            if (flag) {
                totalAmount = totalAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
                returnMap.put("msg", "生成要货单:" + demand.getId() + "。总金额：" + totalAmount.toString() + ",商品条数：" + listCount + "条。");
            } else {
                returnMap.put("msg", "");
            }
            return returnMap;
        }
    }

    @Override
    public Map addRejectOrder(JSONObject jsonObject) throws Exception {
        IOrderService orderService = (IOrderService) SpringContextUtils.getBean("salesOrderService");
        IRejectBillService rejectBillService = (IRejectBillService) SpringContextUtils.getBean("salesRejectBillService");
        String customerId = jsonObject.getString("customerid");
        Customer customer = getCustomerByID(customerId);

        Map returnMap = new HashMap();
        boolean flag = false;
        //根据手机上传的keyid 判断单据是否上传过
        String keyid = "";
        if (jsonObject.has("keyid")) {
            keyid = jsonObject.getString("keyid");
        } else {
            keyid = jsonObject.getString("orderid");
        }
        boolean isHaveFlag = rejectBillService.isHaveRejectBillByKeyid(keyid);
        if (isHaveFlag) {
            returnMap.put("flag", flag);
            returnMap.put("isupload", true);
            returnMap.put("msg", "客户编号：" + customerId);
            return returnMap;
        }
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        RejectBill bill = new RejectBill();
        bill.setBusinessdate(CommonUtils.getTodayDataStr());
        bill.setStatus("1");
        bill.setSource("8");
        bill.setCustomerid(jsonObject.getString("customerid"));
        bill.setKeyid(keyid);
        bill.setPaytype(jsonObject.getString("paytype"));
        bill.setBilltype("2");
        if (customer != null) {
            bill.setCustomersort(customer.getCustomersort());
            bill.setHandlerid(customer.getContact());
            bill.setSalesdept(customer.getSalesdeptid());
            bill.setSalesuser(customer.getSalesuserid());
            bill.setSettletype(customer.getSettletype());
            bill.setSalesarea(customer.getSalesarea());
        }
        SysUser sysUser = getSysUser();
        //要货通知单客户业务员获取方式 1取客户默认业务员2取制单人为客户业务员
        String salesUserDemandType = getSysParamValue("SalesUserDemandType");
        if("2".equals(salesUserDemandType)){
            Personnel personnel = getPersonnelById(sysUser.getPersonnelid());
            if(null!=personnel){
                bill.setSalesdept(personnel.getBelongdeptid());
                bill.setSalesuser(personnel.getId());
            }
        }
        if (sysUser != null) {
            bill.setAdddeptid(sysUser.getDepartmentid());
            bill.setAdddeptname(sysUser.getDepartmentname());
            bill.setAdduserid(sysUser.getUserid());
            bill.setAddusername(sysUser.getName());
        }
        //启用部门与仓库关联 获取关联仓库
        String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
        DepartMent departMent = getDepartmentByDeptid(sysUser.getDepartmentid());
        if (null != departMent) {
            bill.setStorageid(departMent.getStorageid());
        }
        if (isAutoCreate("t_sales_rejectbill")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(bill, "t_sales_rejectbill");
            id = id.replace("THTZD", "SJTH");
            bill.setId(id);
        } else {
            bill.setId("SJTH-" + CommonUtils.getDataNumberSeconds());
        }
        List<RejectBillDetail> billDetailList = new ArrayList<RejectBillDetail>();
        if (jsonArray != null && !jsonArray.isEmpty()) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String num = "0";
                if (json.has("num")) {
                    num = json.getString("num");
                }
                if (StringUtils.isNotEmpty(num) && !"0".equals(num) && !"null".equals(num)) {
                    RejectBillDetail billDetail = new RejectBillDetail();
                    billDetail.setBillid(bill.getId());
                    String goodsId = json.getString("goodsid");
                    billDetail.setGoodsid(json.getString("goodsid"));
                    GoodsInfo goodsInfo = getGoodsInfoByID(billDetail.getGoodsid());
                    if (null != goodsInfo) {
                        billDetail.setGoodsInfo(goodsInfo);
                        String price = json.getString("price");
                        //退货属性
                        String rejectcategory = "";
                        if (json.has("rejectcategory")) {
                            rejectcategory = json.getString("rejectcategory");

                        }
                        String amount = json.getString("amount");
                        billDetail.setUnitnum(new BigDecimal(num));
                        billDetail.setUnitid(goodsInfo.getMainunit());
                        billDetail.setUnitname(goodsInfo.getMainunitName());
                        MeteringUnit meteringUnit = getGoodsAuxUnitInfoByGoodsid(billDetail.getGoodsid());
                        if (null != meteringUnit) {
                            billDetail.setAuxunitid(meteringUnit.getId());
                            billDetail.setAuxunitname(meteringUnit.getName());
                        }
                        Map map = countGoodsInfoNumber(billDetail.getGoodsid(), billDetail.getAuxunitid(), billDetail.getUnitnum());
                        if (map.containsKey("auxInteger")) {
                            billDetail.setAuxnum(new BigDecimal(map.get("auxInteger").toString()));
                        }
                        if (map.containsKey("auxremainder")) {
                            billDetail.setAuxremainder(new BigDecimal(map.get("auxremainder").toString()));
                        }
                        if (map.containsKey("auxnumdetail")) {
                            billDetail.setAuxnumdetail(map.get("auxnumdetail").toString());
                        }
                        if (map.containsKey("auxnum")) {
                            billDetail.setTotalbox((BigDecimal) map.get("auxnum"));
                        }
                        OrderDetail orderDetail = orderService.getGoodsDetail(goodsId, customerId, CommonUtils.getTodayDataStr(), BigDecimal.ZERO, "reject");
                        billDetail.setTaxprice(orderDetail.getTaxprice());
                        billDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                        billDetail.setTaxamount(billDetail.getUnitnum().multiply(billDetail.getTaxprice()).setScale(decimalLen, BigDecimal.ROUND_HALF_UP));
                        BigDecimal noTaxAmount = getNotaxAmountByTaxAmount(billDetail.getUnitnum().multiply(billDetail.getTaxprice()), billDetail.getTaxtype());
                        BigDecimal tax = billDetail.getTaxamount().subtract(noTaxAmount);
                        billDetail.setNotaxamount(noTaxAmount);
                        billDetail.setTax(tax);
                        TaxType taxType = getTaxType(billDetail.getTaxtype());
                        BigDecimal noTaxPrice = billDetail.getTaxprice().divide(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
                        billDetail.setNotaxprice(noTaxPrice);
                        String remark = json.getString("remark") == null ? "" : json.getString("remark");
                        remark = CommonUtils.escapeStr(remark);
                        billDetail.setRemark(remark);
                        billDetail.setRejectcategory(rejectcategory);
                        billDetailList.add(billDetail);
                    }
                }
            }
        }
        if (null != billDetailList && billDetailList.size() > 0) {
            bill.setBillDetailList(billDetailList);
            flag = rejectBillService.addRejectBillForPhone(bill);
        }
        returnMap.put("flag", flag);
        returnMap.put("id", bill.getId());
        if (flag) {
            returnMap.put("msg", "生成手机退货申请单:" + bill.getId());
        } else {
            returnMap.put("msg", "");
        }
        return returnMap;
    }

    /**
     * 验证销售退货的商品 是否销售过
     *
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public Map checkRejectOrder(JSONObject jsonObject) throws Exception {
        String customerId = jsonObject.getString("customerid");
        Customer customer = getCustomerByID(customerId);
        boolean flag = true;
        String msg = "";
        if (null != customer) {
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            String ungoodsids = "";
            if (jsonArray != null && !jsonArray.isEmpty()) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    String goodsId = json.getString("goodsid");
                    boolean flag2 = checkCustomerGoods(customerId, goodsId);
                    if (!flag2) {//未买过
                        flag = false;
                        if (StringUtils.isEmpty(ungoodsids)) {
                            ungoodsids = goodsId;
                        } else {
                            ungoodsids += "," + goodsId;
                        }
                    }
                }
            }
            if (StringUtils.isNotEmpty(ungoodsids)) {
                msg = "商品：" + ungoodsids + ";未销售过。";
            }
        }
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("msg", msg);
        return map;
    }

    @Override
    public Map updateOrderCar(JSONObject jsonObject) throws Exception {
//		IOrderService orderService = (IOrderService)SpringContextUtils.getBean("salesOrderService");
        IOrderCarService orderCarService = (IOrderCarService) SpringContextUtils.getBean("salesOrderCarService");
        boolean flag = false;
        Map returnMap = new HashMap();
        //根据手机上传的keyid 判断该单据是否上传过
        String keyid = "";
        if (jsonObject.has("keyid")) {
            keyid = jsonObject.getString("keyid");
        } else {
            keyid = jsonObject.getString("orderid");
        }
        boolean isHaveFlag = orderCarService.isHaveOrderCarByKeyid(keyid);
        if (isHaveFlag) {
            returnMap.put("id", orderCarService.getOrderCarBillidByKeyid(keyid));
            returnMap.put("flag", flag);
            returnMap.put("isupload", true);
            returnMap.put("msg", "重复上传");
            return returnMap;
        }
        returnMap.put("isupload", false);
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        OrderCar order = new OrderCar();
        order.setBusinessdate(CommonUtils.getTodayDataStr());
        order.setStatus("2");
        String customerId = jsonObject.getString("customerid");
        String storageid = null;
        if (jsonObject.has("storageid")) {
            storageid = jsonObject.getString("storageid");
        }
        order.setStorageid(storageid);
        String billremark = null;
        if (jsonObject.has("remark")) {
            billremark = jsonObject.getString("remark");
        }
        order.setRemark(billremark);
        //单据类型1零售2车销
        String billtype = "2";
        if (jsonObject.has("billtype")) {
            billtype = jsonObject.getString("billtype");
        }
        order.setBilltype(billtype);
        order.setCustomerid(customerId);
        order.setKeyid(keyid);

        List<OrderCarDetail> detailList = new ArrayList<OrderCarDetail>();
        if (jsonArray != null && !jsonArray.isEmpty()) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String num = "0";
                if (json.has("num")) {
                    num = json.getString("num");
                }
                if (StringUtils.isNotEmpty(num) && !"0".equals(num) && !"null".equals(num)) {
                    String gtype = json.getString("gtype");
                    String groupid = json.getString("groupid");
                    String goodsId = json.getString("goodsid");

                    OrderCarDetail detail = new OrderCarDetail();
                    detail.setOrderid(order.getId());

                    detail.setGoodsid(json.getString("goodsid"));
                    GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
                    if (null != goodsInfo) {
                        detail.setGoodsInfo(goodsInfo);
                        String price = json.getString("price");
                        detail.setUnitnum(new BigDecimal(num));
                        detail.setUnitid(goodsInfo.getMainunit());
                        detail.setUnitname(goodsInfo.getMainunitName());
                        MeteringUnit meteringUnit = getGoodsAuxUnitInfoByGoodsid(detail.getGoodsid());
                        if (null != meteringUnit) {
                            detail.setAuxunitid(meteringUnit.getId());
                            detail.setAuxunitname(meteringUnit.getName());
                        }
                        Map map = countGoodsInfoNumber(detail.getGoodsid(), detail.getAuxunitid(), detail.getUnitnum());
                        if (map.containsKey("auxInteger")) {
                            detail.setAuxnum(new BigDecimal(map.get("auxInteger").toString()));
                        }
                        if (map.containsKey("auxremainder")) {
                            detail.setOvernum(new BigDecimal(map.get("auxremainder").toString()));
                        }
                        if (map.containsKey("auxnumdetail")) {
                            detail.setAuxnumdetail(map.get("auxnumdetail").toString());
                        }
                        if (map.containsKey("auxnum")) {
                            detail.setTotalbox((BigDecimal) map.get("auxnum"));
                        }
                        BigDecimal snum = new BigDecimal(num);
//						OrderDetail orderDetail = orderService.getGoodsDetail(goodsId, customerId, CommonUtils.getTodayDataStr(), snum.intValue(), null);
                        detail.setTaxprice(new BigDecimal(price));
                        detail.setTaxtype(goodsInfo.getDefaulttaxtype());
                        BigDecimal taxamount=detail.getUnitnum().multiply(detail.getTaxprice());
                        detail.setTaxamount(taxamount);
                        BigDecimal noTaxAmount = getNotaxAmountByTaxAmount(taxamount, detail.getTaxtype());
                        detail.setNotaxamount(noTaxAmount);
                        BigDecimal tax = detail.getTaxamount().subtract(detail.getNotaxamount());
                        detail.setTax(tax);
                        TaxType taxType = getTaxType(detail.getTaxtype());
                        BigDecimal noTaxPrice = detail.getTaxprice().divide(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
                        detail.setNotaxprice(noTaxPrice);
                        String remark = json.getString("remark") == null ? "" : json.getString("remark");
                        remark = CommonUtils.escapeStr(remark);
                        if ("1".equals(gtype)) {
                            detail.setRemark("赠品 " + remark);
                        } else {
                            detail.setRemark(remark);
                        }
                        detailList.add(detail);
                    }
                }
            }
        }
        order.setOrderDetailList(detailList);
        String msg = "";
        if (null != detailList && detailList.size() > 0) {
            Map map = orderCarService.addOrderCar(order);
            flag = (Boolean) map.get("flag");
            order.setId((String) map.get("id"));
            boolean auditflag = (Boolean) map.get("auditflag");
            if (!auditflag) {
                msg = "未审核通过。";
            }
        }
        returnMap.put("flag", flag);
        returnMap.put("id", order.getId());
        if (flag) {
            returnMap.put("msg", "生成零售订单:" + order.getId() + "。" + msg);
        } else {
            returnMap.put("msg", "");
        }
        return returnMap;
    }

    @Override
    public Map addCarRejectOrder(JSONObject jsonObject) throws Exception {
        IOrderService orderService = (IOrderService) SpringContextUtils.getBean("salesOrderService");
        IRejectBillService rejectBillService = (IRejectBillService) SpringContextUtils.getBean("salesRejectBillService");
        boolean flag = false;
        Map returnMap = new HashMap();
        //根据手机上传的keyid 判断单据是否上传过
        String keyid = "";
        if (jsonObject.has("keyid")) {
            keyid = jsonObject.getString("keyid");
        } else {
            keyid = jsonObject.getString("orderid");
        }
        boolean isHaveFlag = rejectBillService.isHaveRejectBillByKeyid(keyid);
        if (isHaveFlag) {
            returnMap.put("flag", flag);
            returnMap.put("isupload", true);
            returnMap.put("msg", "");
            return returnMap;
        }
        returnMap.put("isupload", false);
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        RejectBill bill = new RejectBill();
        String customerId = jsonObject.getString("customerid");
        Customer customer = getCustomerByID(customerId);
        bill.setBusinessdate(CommonUtils.getTodayDataStr());
        bill.setStatus("2");
        bill.setKeyid(keyid);
        bill.setCustomerid(jsonObject.getString("customerid"));
        bill.setPaytype(jsonObject.getString("paytype"));
        bill.setBilltype("2");
        if (customer != null) {
            bill.setCustomersort(customer.getCustomersort());
            bill.setHandlerid(customer.getContact());
            bill.setSalesdept(customer.getSalesdeptid());
            bill.setSalesuser(customer.getSalesuserid());
            bill.setSettletype(customer.getSettletype());
            bill.setSalesarea(customer.getSalesarea());
        }
        SysUser sysUser = getSysUser();
        //要货通知单客户业务员获取方式 1取客户默认业务员2取制单人为客户业务员
        String salesUserDemandType = getSysParamValue("SalesUserDemandType");
        if("2".equals(salesUserDemandType)){
            Personnel personnel = getPersonnelById(sysUser.getPersonnelid());
            if(null!=personnel){
                bill.setSalesdept(personnel.getBelongdeptid());
                bill.setSalesuser(personnel.getId());
            }
        }
        if (sysUser != null) {
            StorageInfo storageInfo = getStorageInfoByCarsaleuser(sysUser.getUserid());
            if (storageInfo != null) {
                bill.setStorageid(storageInfo.getId());
            }
            bill.setAdddeptid(sysUser.getDepartmentid());
            bill.setAdddeptname(sysUser.getDepartmentname());
            bill.setAdduserid(sysUser.getUserid());
            bill.setAddusername(sysUser.getName());
        }

        List<RejectBillDetail> billDetailList = new ArrayList<RejectBillDetail>();
        if (jsonArray != null && !jsonArray.isEmpty()) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String num = "0";
                if (json.has("num")) {
                    num = json.getString("num");
                }
                if (StringUtils.isNotEmpty(num) && !"0".equals(num) && !"null".equals(num)) {
                    RejectBillDetail billDetail = new RejectBillDetail();
                    billDetail.setBillid(bill.getId());
                    String goodsId = json.getString("goodsid");
                    billDetail.setGoodsid(json.getString("goodsid"));
                    GoodsInfo goodsInfo = getGoodsInfoByID(billDetail.getGoodsid());
                    billDetail.setGoodsInfo(goodsInfo);
                    if (null != goodsInfo) {
                        String price = json.getString("price");
                        billDetail.setUnitnum(new BigDecimal(num));
                        billDetail.setUnitid(goodsInfo.getMainunit());
                        billDetail.setUnitname(goodsInfo.getMainunitName());
                        MeteringUnit meteringUnit = getGoodsAuxUnitInfoByGoodsid(billDetail.getGoodsid());
                        if (null != meteringUnit) {
                            billDetail.setAuxunitid(meteringUnit.getId());
                            billDetail.setAuxunitname(meteringUnit.getName());
                        }
                        Map map = countGoodsInfoNumber(billDetail.getGoodsid(), billDetail.getAuxunitid(), billDetail.getUnitnum());
                        if (map.containsKey("auxInteger")) {
                            billDetail.setAuxnum(new BigDecimal(map.get("auxInteger").toString()));
                        }
                        if (map.containsKey("auxremainder")) {
                            billDetail.setAuxremainder(new BigDecimal(map.get("auxremainder").toString()));
                        }
                        if (map.containsKey("auxnumdetail")) {
                            billDetail.setAuxnumdetail(map.get("auxnumdetail").toString());
                        }
                        if (map.containsKey("auxnum")) {
                            billDetail.setTotalbox((BigDecimal) map.get("auxnum"));
                        }
                        OrderDetail orderDetail = orderService.getGoodsDetail(goodsId, customerId, CommonUtils.getTodayDataStr(), BigDecimal.ZERO, "reject");
                        billDetail.setTaxprice(orderDetail.getTaxprice());
                        billDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                        BigDecimal taxamount=billDetail.getUnitnum().multiply(billDetail.getTaxprice());
                        billDetail.setTaxamount(taxamount);
                        BigDecimal noTaxAmount = getNotaxAmountByTaxAmount(taxamount, billDetail.getTaxtype());
                        billDetail.setNotaxamount(noTaxAmount);
                        BigDecimal tax = billDetail.getTaxamount().subtract(billDetail.getNotaxamount());

                        billDetail.setTax(tax);
                        TaxType taxType = getTaxType(billDetail.getTaxtype());
                        BigDecimal noTaxPrice = billDetail.getTaxprice().divide(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
                        billDetail.setNotaxprice(noTaxPrice);
                        String remark = json.getString("remark") == null ? "" : json.getString("remark");
                        remark = CommonUtils.escapeStr(remark);
                        billDetail.setRemark(remark);
                        billDetailList.add(billDetail);
                    }
                }
            }
        }
        String msg = "";
        //不是绑定仓库的车销用户 现场交易不能退货
        if (null != bill.getStorageid() && !"".equals(bill.getStorageid()) && billDetailList.size() > 0) {
            bill.setBillDetailList(billDetailList);
            flag = rejectBillService.addRejectBillForCar(bill);
        } else {
            msg = "用户未绑定车销仓库！";
        }
        returnMap.put("flag", flag);
        returnMap.put("id", bill.getId());
        if (flag) {
            returnMap.put("msg", "生成手机车销退货单:" + bill.getId());
        } else {
            returnMap.put("msg", msg);
        }
        return returnMap;
    }

    @Override
    public String uploadOrderMsg(String customerid, String demandid) throws Exception {
        boolean passDateflag = false;
        boolean creditflag = true;
        Customer customer = getCustomerByID(customerid);
        String msg = "";
        if (null != customer) {
            Map pMap = isReceivablePassDateByCustomeridAndDemand(customerid, demandid);
            if (null != pMap) {
                passDateflag = (Boolean) pMap.get("flag");
                if (passDateflag) {
                    msg = (String) pMap.get("msg");
                }
            }
            if (!passDateflag) {
                creditflag = isReceivableInCredit(customerid);
            }
            if (!creditflag) {
                BigDecimal creditamount = BigDecimal.ZERO;
                if (null != customer.getCredit()) {
                    creditamount = customer.getCredit().setScale(decimalLen, BigDecimal.ROUND_HALF_UP);
                }
                msg = "客户：" + customer.getId() + "," + customer.getName() + ",应收款超过信用额度!信用额度:" + creditamount.setScale(2, BigDecimal.ROUND_HALF_UP);
            }
        }
        return msg;
    }

    @Override
    public boolean addLocation(Location location) throws Exception {
        SysUser sysUser = getSysUserById(location.getUserid());
        if (sysUser != null && "1".equals(sysUser.getIsuploadlocation())) {
            location.setName(sysUser.getName());
            location.setUsername(sysUser.getUsername());
            location.setPersonnelid(sysUser.getPersonnelid());
        } else {
            return true;
        }
        Location l = locationMapper.getLocationByUserId(location.getUserid());
        if (null == location.getUpdatetime()) {
            location.setUpdatetime(new Date());
        }
        //防止重复上传
        String time = CommonUtils.dataToStr(location.getUpdatetime(), "yyyy-MM-dd HH:mm:ss");
        int count = locationMapper.checkLocationByUseridAndTime(location.getUserid(), time);
        if (count == 0) {
            locationMapper.addLocationHistory(location);
        }
        if (l == null || StringUtils.isEmpty(l.getId())) {
            return locationMapper.addLocation(location) > 0;
        } else {
            return locationMapper.updateLocation(location) > 0;
        }
    }

    @Override
    public List getLocationList() throws Exception {
        String date = CommonUtils.getTodayDataStr();
        String sql = getDataAccessRule("t_base_personnel", "p");
        List list = locationMapper.getLocationList(date, sql);
        return list;
    }

    @Override
    public List getLocationHistoryList(Map map) throws Exception {
        List<Location> list = locationMapper.getLocationHistoryList(map);
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Location location : list) {
            Map<String, String> m = new HashMap<String, String>();
            m.put("name", location.getName());
            m.put("updatetime", dateFormat.format(location.getUpdatetime()));
            m.put("x", location.getX());
            m.put("y", location.getY());
            result.add(m);
        }
        return result;
    }

    public List getNewLocationByInfo(Map map) throws Exception {
        List<Location> list = locationMapper.getLocationHistoryListByInfo(map);
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map checkMap = new HashMap();
        for (Location location : list) {
            Map<String, String> m = new HashMap<String, String>();
            String name = location.getName();
            if (checkMap.size() > 0 && checkMap.containsKey(name)) {
                continue;
            }
            checkMap.put(name, name);
            m.put("name", name);
            m.put("updatetime", dateFormat.format(location.getUpdatetime()));
            m.put("x", location.getX());
            m.put("y", location.getY());
            result.add(m);
        }
        return result;
    }

    @Override
    public List getAllCustomer() throws Exception {
        return getBaseFilesCustomerMapper().getAllCustomer();
    }

    @Override
    public List getAllChildCustomer() throws Exception {
        Map map = new HashMap();
        return getBaseFilesCustomerMapper().getAllCustomerWithoutPcustomer(map);
    }

    @Override
    public Map uploadReject(JSONObject jsonObject) throws Exception {
        boolean flag = false;
        String ids = "";
        IOrderService orderService = (IOrderService) SpringContextUtils.getBean("salesOrderService");
        IRejectBillService rejectBillService = (IRejectBillService) SpringContextUtils.getBean("salesRejectBillService");
        Map returnMap = new HashMap();
        //根据手机上传的keyid 判断单据是否上传过
        String keyid = "";
        if (jsonObject.has("keyid")) {
            keyid = jsonObject.getString("keyid");
        } else {
            keyid = CommonUtils.getDataNumberSendsWithRand();
        }
        boolean isHaveFlag = rejectBillService.isHaveRejectBillByKeyid(keyid);
        if (isHaveFlag) {
            returnMap.put("flag", false);
            returnMap.put("isupload", true);
            returnMap.put("msg", "");
            return returnMap;
        }

        JSONArray jsonArray = jsonObject.getJSONArray("list");
        String storageid = jsonObject.getString("storageid");
        String customerid = jsonObject.getString("customerid");
        String billtype = jsonObject.getString("type");
        String billno = jsonObject.getString("id");
        String businessDate = jsonObject.getString("businessdate");
        String billRemark = "";
        if (jsonObject.has("remark")) {
            billRemark = jsonObject.getString("remark");
        }
        SysUser sysUser = getSysUser();
        //是否启用了部门关联仓库 启用后获取当前用户部门关联仓库
        String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
        if ("1".equals(OpenDeptStorage)) {
            DepartMent departMent = getDepartmentByDeptid(sysUser.getDepartmentid());
            if (null != departMent && (StringUtils.isEmpty(storageid) || "defaultstorage".equals(storageid))) {
                if (StringUtils.isNotEmpty(departMent.getStorageid())) {
                    storageid = departMent.getStorageid();
                }
            }
        }
        if (null == storageid) {
            storageid = "";
        }
        //指定了仓库
        if (!"defaultstorage".equals(storageid)) {
            RejectBill bill = new RejectBill();
            Customer customer = getCustomerByID(customerid);
            bill.setKeyid(keyid);
            bill.setBusinessdate(CommonUtils.getTodayDataStr());
            bill.setStatus("2");
            bill.setCustomerid(customerid);
            bill.setBillno(jsonObject.getString("id"));
            bill.setSource("9");
            bill.setBilltype(billtype);
            bill.setStorageid(storageid);
            bill.setRemark(billRemark);
            if (customer != null) {
                bill.setPaytype(customer.getPaytype());
                bill.setHandlerid(customer.getContact());
                bill.setSalesdept(customer.getSalesdeptid());
                bill.setSalesuser(customer.getSalesuserid());
                bill.setSettletype(customer.getSettletype());
                bill.setSalesarea(customer.getSalesarea());
                bill.setPcustomerid(customer.getPid());
                bill.setCustomersort(customer.getCustomersort());
            }

            if (sysUser != null) {
                bill.setAdddeptid(sysUser.getDepartmentid());
                bill.setAdddeptname(sysUser.getDepartmentname());
                bill.setAdduserid(sysUser.getUserid());
                bill.setAddusername(sysUser.getName());
            }
            if (isAutoCreate("t_sales_rejectbill")) {
                // 获取自动编号
                String id = getAutoCreateSysNumbderForeign(bill, "t_sales_rejectbill");
                bill.setId(id);
            } else {
                bill.setId(CommonUtils.getDataNumberSendsWithRand());
            }
            List<RejectBillDetail> billDetailList = new ArrayList<RejectBillDetail>();
            if (jsonArray != null && !jsonArray.isEmpty()) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    String auxNum = json.getString("auxnum");
                    String unitNum = json.getString("unitnum");
                    String barcode = "";
                    String producedate = "";
                    if (json.has("barcode")) {
                        barcode = json.getString("barcode");
                    }
                    if (json.has("producedate")) {
                        producedate = json.getString("producedate");
                    }
                    String remark = "";
                    if (json.has("remark")) {
                        remark = json.getString("remark");
                    }
                    if (StringUtils.isEmpty(auxNum)) {
                        auxNum = "0";
                    }
                    if (StringUtils.isEmpty(unitNum)) {
                        unitNum = "0";
                    }
                    //退货属性
                    String rejectcategory = "1";
                    if (json.has("rejectcategory")) {
                        rejectcategory = json.getString("rejectcategory");
                    }

                    RejectBillDetail billDetail = new RejectBillDetail();
                    billDetail.setBillid(bill.getId());
                    String goodsId = json.getString("goodsid");
                    billDetail.setGoodsid(goodsId);
                    billDetail.setProduceddate(producedate);
                    GoodsInfo goodsInfo = getGoodsInfoByID(billDetail.getGoodsid());
                    if (null != goodsInfo) {
                        billDetail.setGoodsInfo(goodsInfo);
                        billDetail.setUnitid(goodsInfo.getMainunit());
                        billDetail.setUnitname(goodsInfo.getMainunitName());
                        MeteringUnit meteringUnit = getGoodsAuxUnitInfoByGoodsid(billDetail.getGoodsid());
                        GoodsInfo_MteringUnitInfo unitInfo = getDefaultGoodsAuxMeterUnitInfo(goodsId);
                        billDetail.setUnitnum(new BigDecimal(auxNum).multiply(unitInfo.getRate()).add(new BigDecimal(unitNum)));
                        if (null != meteringUnit) {
                            billDetail.setAuxunitid(meteringUnit.getId());
                            billDetail.setAuxunitname(meteringUnit.getName());
                        }
                        Map map = countGoodsInfoNumber(billDetail.getGoodsid(), billDetail.getAuxunitid(), billDetail.getUnitnum());
                        if (map.containsKey("auxInteger")) {
                            billDetail.setAuxnum(new BigDecimal(map.get("auxInteger").toString()));
                        }
                        if (map.containsKey("auxremainder")) {
                            billDetail.setAuxremainder(new BigDecimal(map.get("auxremainder").toString()));
                        }
                        if (map.containsKey("auxnumdetail")) {
                            billDetail.setAuxnumdetail(map.get("auxnumdetail").toString());
                        }
                        OrderDetail orderDetail = orderService.getGoodsDetail(goodsId, customerid, businessDate, BigDecimal.ZERO, "reject"); //获取商品的客户价格
                        billDetail.setTaxprice(orderDetail.getTaxprice());
                        billDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                        BigDecimal taxamount=billDetail.getUnitnum().multiply(billDetail.getTaxprice());
                        billDetail.setTaxamount(taxamount);
                        BigDecimal noTaxAmount = getNotaxAmountByTaxAmount(taxamount, billDetail.getTaxtype());
                        billDetail.setNotaxamount(noTaxAmount);
                        BigDecimal tax = billDetail.getTaxamount().subtract(billDetail.getNotaxamount());
                        billDetail.setTax(tax);
                        TaxType taxType = getTaxType(billDetail.getTaxtype());
                        BigDecimal noTaxPrice = billDetail.getTaxprice().divide(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
                        billDetail.setNotaxprice(noTaxPrice);
                        billDetail.setRemark(remark);
                        //退货属性
                        billDetail.setRejectcategory(rejectcategory);
                        billDetailList.add(billDetail);
                    }
                }
            }
            bill.setBillDetailList(billDetailList);
            flag = rejectBillService.addRejectBillForPhone(bill);
            if (StringUtils.isEmpty(ids)) {
                ids = bill.getId();
            } else {
                ids += "," + bill.getId();
            }
        } else {
            //未指定仓库，或者是指定默认仓库，按仓库生成不同的退货通知单
            Map storageMap = new HashMap();
            if (jsonArray != null && !jsonArray.isEmpty()) {
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject json = jsonArray.getJSONObject(i);
                    String auxNum = json.getString("auxnum");
                    String unitNum = json.getString("unitnum");
                    String barcode = "";
                    String producedate = "";
                    if (json.has("barcode")) {
                        barcode = json.getString("barcode");
                    }
                    if (json.has("producedate")) {
                        producedate = json.getString("producedate");
                    }
                    if (StringUtils.isEmpty(auxNum)) {
                        auxNum = "0";
                    }
                    if (StringUtils.isEmpty(unitNum)) {
                        unitNum = "0";
                    }
                    String remark = "";
                    if (json.has("remark")) {
                        remark = json.getString("remark");
                    }
                    //退货属性
                    String rejectcategory = "";
                    if (json.has("rejectcategory")) {
                        rejectcategory = json.getString("rejectcategory");
                    }
                    RejectBillDetail billDetail = new RejectBillDetail();
                    String goodsId = json.getString("goodsid");
                    billDetail.setGoodsid(goodsId);
                    billDetail.setProduceddate(producedate);
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(billDetail.getGoodsid());
                    if (null != goodsInfo) {
                        billDetail.setGoodsInfo(goodsInfo);
                        billDetail.setUnitid(goodsInfo.getMainunit());
                        billDetail.setUnitname(goodsInfo.getMainunitName());
                        MeteringUnit meteringUnit = getGoodsAuxUnitInfoByGoodsid(billDetail.getGoodsid());
                        GoodsInfo_MteringUnitInfo unitInfo = getDefaultGoodsAuxMeterUnitInfo(goodsId);
                        billDetail.setUnitnum(new BigDecimal(auxNum).multiply(unitInfo.getRate()).add(new BigDecimal(unitNum)));
                        if (null != meteringUnit) {
                            billDetail.setAuxunitid(meteringUnit.getId());
                            billDetail.setAuxunitname(meteringUnit.getName());
                        }
                        Map map = countGoodsInfoNumber(billDetail.getGoodsid(), billDetail.getAuxunitid(), billDetail.getUnitnum());
                        if (map.containsKey("auxInteger")) {
                            billDetail.setAuxnum(new BigDecimal(map.get("auxInteger").toString()));
                        }
                        if (map.containsKey("auxremainder")) {
                            billDetail.setAuxremainder(new BigDecimal(map.get("auxremainder").toString()));
                        }
                        if (map.containsKey("auxnumdetail")) {
                            billDetail.setAuxnumdetail(map.get("auxnumdetail").toString());
                        }
                        OrderDetail orderDetail = orderService.getGoodsDetail(goodsId, customerid, businessDate, BigDecimal.ZERO, "reject"); //获取商品的客户价格
                        billDetail.setTaxprice(orderDetail.getTaxprice());
                        billDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                        BigDecimal taxamount=billDetail.getUnitnum().multiply(billDetail.getTaxprice());
                        billDetail.setTaxamount(taxamount);
                        BigDecimal noTaxAmount = getNotaxAmountByTaxAmount(taxamount, billDetail.getTaxtype());
                        billDetail.setNotaxamount(noTaxAmount);
                        BigDecimal tax = billDetail.getTaxamount().subtract(billDetail.getNotaxamount());
                        billDetail.setTax(tax);
                        TaxType taxType = getTaxType(billDetail.getTaxtype());
                        BigDecimal noTaxPrice = billDetail.getTaxprice().divide(taxType.getRate().divide(new BigDecimal(100)).add(new BigDecimal(1)), 6, BigDecimal.ROUND_HALF_UP);
                        billDetail.setNotaxprice(noTaxPrice);
                        billDetail.setRemark(remark);
                        //退货属性
                        billDetail.setRejectcategory(rejectcategory);

                        if (storageMap.containsKey(goodsInfo.getStorageid())) {
                            List<RejectBillDetail> datalist = (List) storageMap.get(goodsInfo.getStorageid());
                            datalist.add(billDetail);
                            storageMap.put(goodsInfo.getStorageid(), datalist);
                        } else {
                            List<RejectBillDetail> datalist = new ArrayList<RejectBillDetail>();
                            datalist.add(billDetail);
                            storageMap.put(goodsInfo.getStorageid(), datalist);
                        }
                    }
                }
            }
            Set set = storageMap.entrySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, List<RejectBillDetail>> entry = (Entry<String, List<RejectBillDetail>>) it.next();
                String defaultstorageid = entry.getKey();
                List<RejectBillDetail> detailList = entry.getValue();
                if (null != detailList && detailList.size() > 0) {
                    RejectBill bill = new RejectBill();
                    Customer customer = getCustomerByID(customerid);
                    bill.setKeyid(keyid);
                    bill.setBusinessdate(CommonUtils.getTodayDataStr());
                    bill.setStatus("2");
                    bill.setCustomerid(customerid);
                    bill.setBillno(jsonObject.getString("id"));
                    bill.setSource("9");
                    bill.setBilltype(billtype);
                    bill.setStorageid(defaultstorageid);
                    bill.setRemark(billRemark);
                    if (customer != null) {
                        bill.setPaytype(customer.getPaytype());
                        bill.setHandlerid(customer.getContact());
                        bill.setSalesdept(customer.getSalesdeptid());
                        bill.setSalesuser(customer.getSalesuserid());
                        bill.setSettletype(customer.getSettletype());
                        bill.setSalesarea(customer.getSalesarea());
                        bill.setPcustomerid(customer.getPid());
                        bill.setCustomersort(customer.getCustomersort());
                    }

                    if (sysUser != null) {
                        bill.setAdddeptid(sysUser.getDepartmentid());
                        bill.setAdddeptname(sysUser.getDepartmentname());
                        bill.setAdduserid(sysUser.getUserid());
                        bill.setAddusername(sysUser.getName());
                    }
                    if (isAutoCreate("t_sales_rejectbill")) {
                        // 获取自动编号
                        String id = getAutoCreateSysNumbderForeign(bill, "t_sales_rejectbill");
                        bill.setId(id);
                    } else {
                        bill.setId(CommonUtils.getDataNumberSendsWithRand());
                    }
                    for (RejectBillDetail rejectBillDetail : detailList) {
                        rejectBillDetail.setBillid(bill.getId());
                    }
                    bill.setBillDetailList(detailList);
                    flag = rejectBillService.addRejectBillForPhone(bill);
                    if (StringUtils.isEmpty(ids)) {
                        ids = bill.getId();
                    } else {
                        ids += "," + bill.getId();
                    }
                }
            }
        }
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("ids", ids);
        return map;
    }

    @Override
    public List getAllStorage() throws Exception {
        PageMap pageMap = new PageMap();
        List list = getBaseFilesStorageMapper().getStorageInfoAllList(pageMap);
        return list;
    }

    /**
     * 获取客户分销规则列表
     *
     * @return
     * @throws Exception
     */
    @Override
    public List getDistributionRuleList(String date) throws Exception {
        List<DistributionRule> list = getBaseFilesDistributionRuleMapper().getAllDistributionRuleList(date);
        List dataList = new ArrayList();
        for (DistributionRule distributionRule : list) {
            List detailList = getBaseFilesDistributionRuleMapper().selectDistributionRuleDetailListByRuleid(distributionRule.getId());
            Map map = new HashMap();
            map.put("dtype", "drule");
            map.put("billid", distributionRule.getId());
            map.put("customertype", distributionRule.getCustomertype() == null ? "" : distributionRule.getCustomertype());
            map.put("goodsruletype", distributionRule.getGoodsruletype() == null ? "" : distributionRule.getGoodsruletype());
            map.put("customerid", distributionRule.getCustomerid() == null ? "" : distributionRule.getCustomerid());
            map.put("pcustomerid", distributionRule.getPcustomerid() == null ? "" : distributionRule.getPcustomerid());
            map.put("customersort", distributionRule.getCustomersort() == null ? "" : distributionRule.getCustomersort());
            map.put("promotionsort", distributionRule.getPromotionsort() == null ? "" : distributionRule.getPromotionsort());
            map.put("salesarea", distributionRule.getSalesarea() == null ? "" : distributionRule.getSalesarea());
            map.put("creditrating", distributionRule.getCreditrating() == null ? "" : distributionRule.getCreditrating());
            map.put("canceltype", distributionRule.getCanceltype() == null ? "" : distributionRule.getCanceltype());
            map.put("canbuy", distributionRule.getCanbuy() == null ? "" : distributionRule.getCanbuy());
            map.put("list", detailList);
            dataList.add(map);
        }
        return dataList;
    }

    /**
     * 获取客户分销规则列表
     *
     * @return
     * @throws Exception
     */
    @Override
    public List getOffPriceList(PageMap pageMap) throws Exception {
        Map queryMap = new HashMap();
        queryMap.put("status","3");
        pageMap.setCondition(queryMap);
        List<Offprice> list = getSalesOffpriceMapper().getOffpriceList(pageMap);
        List dataList = new ArrayList();
        for (Offprice offprice : list) {
            List<OffpriceDetail> detailList = getSalesOffpriceMapper().getDetailListByOffprice(offprice.getId());
            Map map = new HashMap();
            map.put("otype", "offprice");
            map.put("billid", offprice.getId());
            map.put("customertype", offprice.getCustomertype() == null ? "" : offprice.getCustomertype());
            map.put("customerid", offprice.getCustomerid() == null ? "" : offprice.getCustomerid());
            map.put("begindate", offprice.getBegindate() == null ? "" : offprice.getBegindate());
            map.put("enddate", offprice.getEnddate() == null ? "" : offprice.getEnddate());
            map.put("oaid", offprice.getOaid() == null ? "" : offprice.getOaid());
            map.put("schedule", offprice.getSchedule() == null ? "" : offprice.getSchedule());
            map.put("list", detailList);
            dataList.add(map);
        }
        return dataList;
    }

    @Override
    public List getCheckListUnfinish(Map map) throws Exception {
        String dataSql = getDataAccessRule("t_storage_checklist", "t");
        map.put("dataSql", dataSql);
        List<CheckList> list = checkListMapper.getCheckListUnfinishForPhone(map);
        for (CheckList check : list) {
            StorageInfo storageInfo = getStorageInfoByID(check.getStorageid());
            if (storageInfo != null) {
                check.setStoragename(storageInfo.getName());
            }
            Personnel personnel = getPersonnelById(check.getCheckuserid());
            if (personnel != null) {
                check.setCheckuserid(personnel.getName());
            }
        }
        return list;
    }

    @Override
    public List getCheckListDetail(String id) throws Exception {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        List<CheckListDetail> list = checkListMapper.getCheckListDetailListByCheckListid(id);
        for (CheckListDetail detail : list) {
            Map<String, String> map = new HashMap<String, String>();
            GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
            map.put("cid", detail.getId() + "");
            map.put("goodsid", detail.getGoodsid());
            if (goodsInfo != null) {
                map.put("goodsname", goodsInfo.getName());
                map.put("barcode", goodsInfo.getBarcode());
            }
            map.put("unitid", detail.getUnitid());
            map.put("unitname", detail.getUnitname());
            map.put("auxunitid", detail.getAuxunitid());
            map.put("auxunitname", detail.getAuxunitname());
            result.add(map);
        }
        return result;
    }

    @Override
    public boolean updateCheckList(JSONObject jsonObject) throws Exception {
        String checkId = jsonObject.getString("checkid");
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        CheckList checkList = checkListMapper.getCheckListInfo(checkId);
        boolean flag = false;
        if ("2".equals(checkList.getStatus())) {
            int j = 0;
            int falsenum = 0;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String cid = json.getString("cid");
                String goodsId = json.getString("goodsid");
                String num = json.getString("num");
                String auxnum = json.getString("auxnum");
                GoodsInfo_MteringUnitInfo unitInfo = getDefaultGoodsAuxMeterUnitInfo(goodsId);
                BigDecimal bNum = new BigDecimal(auxnum).multiply(unitInfo.getRate()).add(new BigDecimal(num));
                CheckListDetail detail = new CheckListDetail();
                detail.setId(Integer.valueOf(cid));
                detail.setChecklistid(checkId);
                ;
                detail.setRealnum(bNum);
                detail.setGoodsid(goodsId);
                Map map = countGoodsInfoNumber(goodsId, unitInfo.getMeteringunitid(), bNum);
                if (map.containsKey("auxnumdetail")) {
                    detail.setAuxrealnumdetail(map.get("auxnumdetail").toString()); //辅单位数量描述(辅单位数量+辅单位+主单位余数+主单位)
                }
                if (map.containsKey("auxInteger")) {
                    detail.setAuxrealnum(new BigDecimal(map.get("auxInteger").toString())); //辅单位数量
                }
                if (map.containsKey("auxremainder")) {
                    detail.setAuxrealremainder(new BigDecimal(map.get("auxremainder").toString())); //主单位余数
                }
                CheckListDetail checkListDetail = checkListMapper.getCheckListDetailInfo(cid);
                BigDecimal bookNum = new BigDecimal(0);
                if (null != checkListDetail) {
                    bookNum = checkListDetail.getBooknum();
                    //盈亏数量=实际数量-账面数量
                    BigDecimal num1 = bNum.subtract(bookNum);
                    detail.setProfitlossnum(num1);
                    Map map1 = countGoodsInfoNumber(goodsId, unitInfo.getMeteringunitid(), num1);
                    if (map1.containsKey("auxnumdetail")) {
                        detail.setAuxprofitlossnumdetail(map1.get("auxnumdetail").toString()); //辅单位数量描述(辅单位数量+辅单位+主单位余数+主单位)
                    }
                    j++;
                    if (bookNum.compareTo(detail.getRealnum()) == 0) {
                        detail.setIstrue("1");
                    } else {
                        falsenum++;
                        detail.setIstrue("0");
                    }
                    int z = checkListMapper.updateCheckListDetail(detail);
                } else {
                    checkListDetail = checkListMapper.getCheckListDetailInfoByGoodsid(checkId, goodsId);
                    if (checkListDetail != null) {
                        bookNum = checkListDetail.getBooknum();
                        //盈亏数量=实际数量-账面数量
                        BigDecimal num1 = bNum.subtract(bookNum);
                        detail.setProfitlossnum(num1);
                        Map map1 = countGoodsInfoNumber(goodsId, unitInfo.getMeteringunitid(), num1);
                        if (map1.containsKey("auxnumdetail")) {
                            detail.setAuxprofitlossnumdetail(map1.get("auxnumdetail").toString()); //辅单位数量描述(辅单位数量+辅单位+主单位余数+主单位)
                        }
                        j++;
                        if (bookNum.compareTo(detail.getRealnum()) == 0) {
                            detail.setIstrue("1");
                        } else {
                            falsenum++;
                            detail.setIstrue("0");
                        }
                        int z = checkListMapper.updateCheckListDetailByGoodsid(detail);
                    } else {
                        falsenum++;
                    }
                }
            }
            if (1 == checkList.getCheckno()) {
                checkList.setChecknum(checkList.getChecknum());
                checkList.setTruenum(j - falsenum);
            } else {
                CheckList oldCheckList = checkListMapper.getCheckListInfo(checkList.getSourceid());
                if (null != oldCheckList) {
                    checkList.setChecknum(oldCheckList.getChecknum());
                    checkList.setTruenum(oldCheckList.getChecknum() - falsenum);
                }
            }
            if (checkList.getChecknum() == checkList.getTruenum()) {
                checkList.setIstrue("1");
            } else {
                checkList.setIstrue("0");
            }
            checkListMapper.editCheckList(checkList);
            return checkListMapper.updateCheckListFinish(checkId) > 0;
        }
        return flag;
    }

    @Override
    public Map getLoginUserInfo(String userid) throws Exception {
        return phoneMapper.getLoginUserInfo(userid);
    }

    @Override
    public String getManagerUserDepartment(String uid) throws Exception {
        return phoneMapper.getManagerUserDepartment(uid);
    }

    @Override
    public List<Map> getDepartmentUserList(String uid, String con) throws Exception {
        List<Map> list = new ArrayList<Map>();
        String deptid = getManagerUserDepartment(uid);
        if (StringUtils.isNotEmpty(deptid)) {
            list = phoneMapper.getDepartmentUserList(deptid, con);
        } else {
            Map map = new HashMap();
            SysUser user = getSysUser();
            map.put("userid", user.getUserid());
            map.put("name", user.getName());
            list.add(map);
        }
        return list;
    }

    /**
     * 抄单汇总报表
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public List<Map> getBaseOrderQueryReport(Map map) throws Exception {
        String datasql = getDataAccessRule("t_report_sales_base", "z");
        map.put("datasql", datasql);
        String groupcol = "";
        if (map.containsKey("groupcol")) {
            groupcol = (String) map.get("groupcol");
        }
        List<Map> list = phoneMapper.getBaseOrderQueryReprot(map);
        if (StringUtils.isNotEmpty(groupcol)) {
            for (Map m : list) {
                BigDecimal bNum1 = new BigDecimal(0);
                BigDecimal bNum2 = new BigDecimal(0);
                BigDecimal bNum3 = new BigDecimal(0);
                if (m.containsKey("amount1")) {
                    BigDecimal amount1 = (BigDecimal) m.get("amount1");
                    amount1 = amount1.setScale(2, BigDecimal.ROUND_HALF_UP);
                    m.put("amount1", amount1.toString());
                }
                if (m.containsKey("amount1")) {
                    BigDecimal amount2 = (BigDecimal) m.get("amount2");
                    amount2 = amount2.setScale(2, BigDecimal.ROUND_HALF_UP);
                    m.put("amount2", amount2.toString());
                }
                String auxUnitId = m.get("auxunitid").toString();
                String goodsId = m.get("goodsid").toString();
                if (groupcol.indexOf("salesuser") >= 0) {
                    String salesuser = (String) m.get("salesuser");
                    if (StringUtils.isNotEmpty(salesuser)) {
                        Personnel personnel = getPersonnelById(salesuser);
                        if (null != personnel) {
                            m.put("salesusername", personnel.getName());
                        } else {
                            m.put("salesusername", "其他");
                        }
                    } else {
                        m.put("salesusername", "其他");
                    }
                }
                if (groupcol.indexOf("goodsid") >= 0) {
                    if (m.get("num1") != null) {
                        bNum1 = new BigDecimal(m.get("num1").toString());
                    }
                    if (m.get("num2") != null) {
                        bNum2 = new BigDecimal(m.get("num2").toString());
                    }
                    if (m.get("num3") != null) {
                        bNum3 = new BigDecimal(m.get("num3").toString());
                    }
                    if (bNum1.compareTo(new BigDecimal(0)) != 0) {
                        Map map2 = countGoodsInfoNumber(goodsId, auxUnitId, bNum1);
                        m.put("auxnum1", map2.get("auxnumdetail"));
                    } else {
                        m.put("auxnum1", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
                    }
                    if (bNum2.compareTo(new BigDecimal(0)) != 0) {
                        Map map3 = countGoodsInfoNumber(goodsId, auxUnitId, bNum2);
                        m.put("auxnum2", map3.get("auxnumdetail"));
                    } else {
                        m.put("auxnum2", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
                    }
                    if (bNum3.compareTo(new BigDecimal(0)) != 0) {
                        Map map4 = countGoodsInfoNumber(goodsId, auxUnitId, bNum3);
                        m.put("auxnum3", map4.get("auxnumdetail"));
                    } else {
                        m.put("auxnum3", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<Map> getCustomerOrderQueryReport(String ids, String beginDate, String endDate) throws Exception {
        //判断客户档案是否需要同步
        Map queryMap = new HashMap();
        SysUser sysUser = getSysUser();
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        if (isBrandUser) {
            queryMap.put("isBrandUser", true);
            queryMap.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                queryMap.put("isSupplierUser", true);
                queryMap.put("personnelid", sysUser.getPersonnelid());
            }
        }
        String customerDataSql = getDataAccessRule("t_base_sales_customer", "c");
        queryMap.put("datasql", customerDataSql);
        if (StringUtils.isNotEmpty(ids)) {
            queryMap.put("ids", ids);
        }
        queryMap.put("beginDate", beginDate);
        queryMap.put("endDate", endDate);
        return phoneMapper.getCustomerOrderQueryReport(queryMap);
    }

    @Override
    public List<Map> getCustomerGoodsOrderQueryReport(Map map) throws Exception {
        List<Map> list = phoneMapper.getCustomerGoodsOrderQueryReport(map);
        for (Map m : list) {
            BigDecimal bNum1 = new BigDecimal(0);
            BigDecimal bNum2 = new BigDecimal(0);
            BigDecimal bNum3 = new BigDecimal(0);
            String auxUnitId = m.get("auxunitid").toString();
            String goodsId = m.get("goodsid").toString();
            if (m.get("num1") != null) {
                bNum1 = new BigDecimal(m.get("num1").toString());
            }
            if (m.get("num2") != null) {
                bNum2 = new BigDecimal(m.get("num2").toString());
            }
            if (m.get("num3") != null) {
                bNum3 = new BigDecimal(m.get("num3").toString());
            }
            if (bNum1.compareTo(new BigDecimal(0)) != 0) {
                Map map2 = countGoodsInfoNumber(goodsId, auxUnitId, bNum1);
                m.put("auxnum1", map2.get("auxnumdetail"));
            } else {
                m.put("auxnum1", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
            }
            if (bNum2.compareTo(new BigDecimal(0)) != 0) {
                Map map3 = countGoodsInfoNumber(goodsId, auxUnitId, bNum2);
                m.put("auxnum2", map3.get("auxnumdetail"));
            } else {
                m.put("auxnum2", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
            }
            if (bNum3.compareTo(new BigDecimal(0)) != 0) {
                Map map4 = countGoodsInfoNumber(goodsId, auxUnitId, bNum3);
                m.put("auxnum3", map4.get("auxnumdetail"));
            } else {
                m.put("auxnum3", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
            }
        }
        return list;
    }

    @Override
    public List<Map> getGoodsOrderQueryReport(Map map) throws Exception {
        String dataSql = getDataAccessRule("t_base_goods_info", "a");
        map.put("datasql", dataSql);
        SysUser sysUser = getSysUser();
        // 品牌业务员与厂家业务员不能同时存在
        // 判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if (isBrandUser) {
            map.put("isBrandUser", true);
            map.put("personnelid", sysUser.getPersonnelid());
        } else {
            // 判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                map.put("isSupplierUser", true);
                map.put("personnelid", sysUser.getPersonnelid());
            }
        }
        return phoneMapper.getGoodsOrderQueryReport(map);
    }

    @Override
    public List<Map> getGoodsCustomerOrderQueryReport(Map map) throws Exception {
        List<Map> list = phoneMapper.getGoodsCustomerOrderQueryReport(map);
        for (Map m : list) {
            BigDecimal bNum1 = new BigDecimal(0);
            BigDecimal bNum2 = new BigDecimal(0);
            BigDecimal bNum3 = new BigDecimal(0);
            String auxUnitId = m.get("auxunitid").toString();
            String goodsId = m.get("goodsid").toString();
            if (m.get("num1") != null) {
                bNum1 = new BigDecimal(m.get("num1").toString());
            }
            if (m.get("num2") != null) {
                bNum2 = new BigDecimal(m.get("num2").toString());
            }
            if (m.get("num3") != null) {
                bNum3 = new BigDecimal(m.get("num3").toString());
            }
            if (bNum1.compareTo(new BigDecimal(0)) != 0) {
                Map map2 = countGoodsInfoNumber(goodsId, auxUnitId, bNum1);
                m.put("auxnum1", map2.get("auxnumdetail"));
            } else {
                m.put("auxnum1", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
            }
            if (bNum2.compareTo(new BigDecimal(0)) != 0) {
                Map map3 = countGoodsInfoNumber(goodsId, auxUnitId, bNum2);
                m.put("auxnum2", map3.get("auxnumdetail"));
            } else {
                m.put("auxnum2", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
            }
            if (bNum3.compareTo(new BigDecimal(0)) != 0) {
                Map map4 = countGoodsInfoNumber(goodsId, auxUnitId, bNum3);
                m.put("auxnum3", map4.get("auxnumdetail"));
            } else {
                m.put("auxnum3", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
            }
        }
        return list;
    }

    @Override
    public List<Map> getSalerOrderQueryReport(Map map) throws Exception {

        return phoneMapper.getSalerOrderQueryReport(map);
    }

    @Override
    public List<Map> getSalerCustomerOrderQueryReport(Map map) throws Exception {
        return phoneMapper.getSalerCustomerOrderQueryReport(map);
    }

    @Override
    public List<Map> getSalerCustomerGoodsOrderQueryReport(Map map) throws Exception {
        List<Map> list = phoneMapper.getSalerCustomerGoodsOrderQueryReport(map);
        for (Map m : list) {
            BigDecimal bNum1 = new BigDecimal(0);
            BigDecimal bNum2 = new BigDecimal(0);
            BigDecimal bNum3 = new BigDecimal(0);
            String auxUnitId = m.get("auxunitid").toString();
            String goodsId = m.get("goodsid").toString();
            if (m.get("num1") != null) {
                bNum1 = new BigDecimal(m.get("num1").toString());
            }
            if (m.get("num2") != null) {
                bNum2 = new BigDecimal(m.get("num2").toString());
            }
            if (m.get("num3") != null) {
                bNum3 = new BigDecimal(m.get("num3").toString());
            }
            if (bNum1.compareTo(new BigDecimal(0)) != 0) {
                Map map2 = countGoodsInfoNumber(goodsId, auxUnitId, bNum1);
                m.put("auxnum1", map2.get("auxnumdetail"));
            } else {
                m.put("auxnum1", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
            }
            if (bNum2.compareTo(new BigDecimal(0)) != 0) {
                Map map3 = countGoodsInfoNumber(goodsId, auxUnitId, bNum2);
                m.put("auxnum2", map3.get("auxnumdetail"));
            } else {
                m.put("auxnum2", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
            }
            if (bNum3.compareTo(new BigDecimal(0)) != 0) {
                Map map4 = countGoodsInfoNumber(goodsId, auxUnitId, bNum3);
                m.put("auxnum3", map4.get("auxnumdetail"));
            } else {
                m.put("auxnum3", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
            }
        }
        return list;
    }

    @Override
    public List<Map> getCustomerSaleQueryReport(Map map) throws Exception {
        return phoneMapper.getCustomerSaleQueryReport(map);
    }

    @Override
    public List<Map> getCustomerGoodsSaleQueryReport(Map map) throws Exception {
        List<Map> list = phoneMapper.getCustomerGoodsSaleQueryReport(map);
        for (Map m : list) {
            BigDecimal bNum1 = new BigDecimal(0);
            BigDecimal bNum2 = new BigDecimal(0);
            String auxUnitId = m.get("auxunitid").toString();
            String goodsId = m.get("goodsid").toString();
            if (m.get("num1") != null) {
                bNum1 = new BigDecimal(m.get("num1").toString());
            }
            if (m.get("num2") != null) {
                bNum2 = new BigDecimal(m.get("num2").toString());
            }
            if (bNum1.compareTo(new BigDecimal(0)) != 0) {
                Map map2 = countGoodsInfoNumber(goodsId, auxUnitId, bNum1);
                m.put("auxnum1", map2.get("auxnumdetail"));
            } else {
                m.put("auxnum1", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
            }
            if (bNum2.compareTo(new BigDecimal(0)) != 0) {
                Map map3 = countGoodsInfoNumber(goodsId, auxUnitId, bNum2);
                m.put("auxnum2", map3.get("auxnumdetail"));
            } else {
                m.put("auxnum2", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
            }
        }
        return list;
    }

    @Override
    public List<Map> getSalerSaleQueryReport(PageMap pageMap) throws Exception {
        String userid = (String) pageMap.getCondition().get("userid");
        String dataSql = getDataAccessRule("t_report_sales_base", null, userid);
        pageMap.setDataSql(dataSql);
        String query_sql = " 1=1 ";
        if (pageMap.getCondition().containsKey("salesarea")) {
            String str = (String) pageMap.getCondition().get("salesarea");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.salesarea = '" + str + "' ";
        }
        if (pageMap.getCondition().containsKey("salesdept")) {
            String str = (String) pageMap.getCondition().get("salesdept");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.salesdept = '" + str + "' ";
        }
        if (pageMap.getCondition().containsKey("customerid")) {
            String str = (String) pageMap.getCondition().get("customerid");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and FIND_IN_SET(b.adduserid, '" + str + "') ";
        }
        if (pageMap.getCondition().containsKey("pcustomerid")) {
            String str = (String) pageMap.getCondition().get("pcustomerid");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.pcustomerid = '" + str + "' ";
        }
        if (pageMap.getCondition().containsKey("branduser")) {
            String str = (String) pageMap.getCondition().get("branduser");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t1.branduser = '" + str + "' ";
        }
        if (pageMap.getCondition().containsKey("businessdate1")) {
            String str = (String) pageMap.getCondition().get("businessdate1");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.businessdate >= '" + str + "'";
        }
        if (pageMap.getCondition().containsKey("businessdate2")) {
            String str = (String) pageMap.getCondition().get("businessdate2");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.businessdate <= '" + str + "'";
        }
        if (!pageMap.getCondition().containsKey("groupcols")) {
            //小计列
            pageMap.getCondition().put("groupcols", "customerid");
        }
        pageMap.getCondition().put("query_sql", query_sql);
        //排序
        String orderstr = "";
        if (pageMap.getCondition().containsKey("sort")) {
            String sort = (String) pageMap.getCondition().get("sort");
            String order = (String) pageMap.getCondition().get("order");
            if (null == order) {
                order = "asc";
            }
            orderstr = sort + " " + order;
        }
        pageMap.getCondition().put("orderstr", orderstr);
        List<Map> list = phoneMapper.getBaseSaleReport(pageMap);
        for (Map map : list) {
            if (map.containsKey("customerid")) {
                String customerid = (String) map.get("customerid");
                Customer customer = getCustomerByID(customerid);
                if (null != customer) {
                    map.put("customername", customer.getName());
                }
            }
            if (map.containsKey("brandid")) {
                String brandid = (String) map.get("brandid");
                Brand brand = getGoodsBrandByID(brandid);
                if (null != brand) {
                    map.put("brandname", brand.getName());
                }
            }
            if (map.containsKey("salesuser")) {
                String salesuser = (String) map.get("salesuser");
                Personnel personnel = getPersonnelById(salesuser);
                if (null != personnel) {
                    map.put("salesusername", personnel.getName());
                }
            }
            if (map.containsKey("branduser")) {
                String salesuser = (String) map.get("branduser");
                Personnel personnel = getPersonnelById(salesuser);
                if (null != personnel) {
                    map.put("brandusername", personnel.getName());
                }
            }
        }
        return list;
    }

    @Override
    public List<Map> getSalerGoodsSaleQueryReport(Map map) throws Exception {
        List<Map> list = phoneMapper.getSalerGoodsSaleQueryReport(map);
        for (Map m : list) {
            BigDecimal bNum1 = new BigDecimal(0);
            BigDecimal bNum2 = new BigDecimal(0);
            String auxUnitId = m.get("auxunitid").toString();
            String goodsId = m.get("goodsid").toString();
            if (m.get("num1") != null) {
                bNum1 = new BigDecimal(m.get("num1").toString());
            }
            if (m.get("num2") != null) {
                bNum2 = new BigDecimal(m.get("num2").toString());
            }
            if (bNum1.compareTo(new BigDecimal(0)) != 0) {
                Map map2 = countGoodsInfoNumber(goodsId, auxUnitId, bNum1);
                m.put("auxnum1", map2.get("auxnumdetail"));
            } else {
                m.put("auxnum1", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
            }
            if (bNum2.compareTo(new BigDecimal(0)) != 0) {
                Map map3 = countGoodsInfoNumber(goodsId, auxUnitId, bNum2);
                m.put("auxnum2", map3.get("auxnumdetail"));
            } else {
                m.put("auxnum2", "0" + m.get("auxunitname") + "0" + m.get("unitname"));
            }
        }
        return list;
    }

    @Override
    public boolean addFeed(Map map) throws Exception {
        return phoneMapper.addFeed(map) > 0;
    }

    @Override
    public boolean addRouteDistance(RouteDistance distance) throws Exception {
        SysUser sysUser = getSysUserById(distance.getUserid());
        //判断是否系统用户 并且是否上传行程
        if (null != sysUser && "1".equals(sysUser.getIsuploadlocation())) {
        } else {
            return true;
        }
        boolean flag = false;
        int count = distanceMapper.getDistanceDetailCount(distance.getUserid(), distance.getAdddate());
        if (count > 0) {
            flag = true;
        } else {
            flag = distanceMapper.addDistance(distance) > 0;
        }
        return flag;
    }

    /**
     * 删除相关的行程记录
     * @param dateStr 日期
     * @param userId 用户编号
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteRouteDistance(String dateStr, String userId) throws Exception {
        return locationMapper.deleteRouteDistance(dateStr, userId) > 0;
    }

    public PageData getRouteDistanceData(PageMap pageMap) throws Exception {
        //数据权限
        String sql = getDataAccessRule("t_base_personnel", "p");
        pageMap.setDataSql(sql);
        List<RouteDistance> list = distanceMapper.getDistanceList(pageMap);
        for (RouteDistance distance : list) {
            SysUser user = getSysUser(distance.getUserid());
            if (user != null) {
                distance.setUsername(user.getName());
            }
            int dis = distance.getDistance();
            if (dis < 1000) {
                distance.setDistancedesc("大约" + dis + "米");
            } else {
                double d = (double) distance.getDistance() / 1000;
                DecimalFormat df = new DecimalFormat("#.00");
                distance.setDistancedesc("大约" + df.format(d) + "千米");
            }
        }
        PageData pageData = new PageData(distanceMapper.getDistanceCount(pageMap), list, pageMap);
        return pageData;
    }

    @Override
    public Map getBaseSalesReport(PageMap pageMap) throws Exception {
        String userid = (String) pageMap.getCondition().get("userid");
        String querytype = (String) pageMap.getCondition().get("querytype");
        String groupcols = (String) pageMap.getCondition().get("groupcols");
        if (!pageMap.getCondition().containsKey("groupcols")) {
            //小计列
            pageMap.getCondition().put("groupcols", "customerid");
            groupcols = "customerid";
        }
        if ("branduserdept".equals(groupcols)) {
            String dataSql = getDataAccessRule("t_report_branduser_dept", "z");
            pageMap.setDataSql(dataSql);
        } else {
            String dataSql = getDataAccessRule("t_report_sales_base", "z");
            pageMap.setDataSql(dataSql);
        }
        String query_sql = " 1=1 ";
        if (StringUtils.isNotEmpty(querytype) && "adduserid".equals(querytype)) {
            pageMap.getCondition().put("adduserid", userid);
            userid = StringEscapeUtils.escapeSql(userid);
            query_sql += " and t.adduserid = '" + userid + "' ";
        }
        if (pageMap.getCondition().containsKey("brandid")) {
            String str = (String) pageMap.getCondition().get("brandid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                query_sql += " and FIND_IN_SET(t1.brandid, '" + str + "') ";
            } else {
                query_sql += " and t1.brandid = '" + str + "' ";
            }
        }
        if (pageMap.getCondition().containsKey("goodsid")) {
            String str = (String) pageMap.getCondition().get("goodsid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                query_sql += " and FIND_IN_SET(t1.goodsid, '" + str + "') ";
            } else {
                query_sql += " and t1.goodsid = '" + str + "' ";
            }
        }
        if (pageMap.getCondition().containsKey("salesarea")) {
            String str = (String) pageMap.getCondition().get("salesarea");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                String[] strArr = str.split(",");
                query_sql += " and ( ";
                int i = 0;
                for (String salearea : strArr) {
                    if (i == 0) {
                        query_sql += " t.salesarea like '" + salearea + "%' ";
                    } else {
                        query_sql += " or t.salesarea like '" + salearea + "%' ";
                    }
                    i++;
                }
                query_sql += ")";
            } else {
                query_sql += " and t.salesarea like '" + str + "%' ";
            }
        }
        if (pageMap.getCondition().containsKey("customersort")) {
            String str = (String) pageMap.getCondition().get("customersort");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                String[] strArr = str.split(",");
                query_sql += " and ( ";
                int i = 0;
                for (String customersort : strArr) {
                    if (i == 0) {
                        query_sql += " t.customersort like '" + customersort + "%' ";
                    } else {
                        query_sql += " or t.customersort like '" + customersort + "%' ";
                    }
                    i++;
                }
                query_sql += ")";
            } else {
                query_sql += " and t.customersort like '" + str + "%' ";
            }
        }
        if (pageMap.getCondition().containsKey("salesuser")) {
            String str = (String) pageMap.getCondition().get("salesuser");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                query_sql += " and FIND_IN_SET(t.salesuser, '" + str + "') ";
            } else {
                query_sql += " and t.salesuser = '" + str + "' ";
            }
        }
        if (pageMap.getCondition().containsKey("branduser")) {
            String str = (String) pageMap.getCondition().get("branduser");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                query_sql += " and FIND_IN_SET(t1.branduser, '" + str + "') ";
            } else {
                query_sql += " and t1.branduser = '" + str + "' ";
            }
        }
        if (pageMap.getCondition().containsKey("branduserdept")) {
            String str = (String) pageMap.getCondition().get("branduserdept");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                String[] strArr = str.split(",");
                query_sql += " and ( ";
                int i = 0;
                for (String branduserdept : strArr) {
                    if (i == 0) {
                        query_sql += " p.belongdeptid like '" + branduserdept + "%' ";
                    } else {
                        query_sql += " or p.belongdeptid like '" + branduserdept + "%' ";
                    }
                    i++;
                }
                query_sql += ")";
            } else {
                query_sql += " and p.belongdeptid like '" + str + "%' ";
            }
        }
        if (pageMap.getCondition().containsKey("salesdept")) {
            String str = (String) pageMap.getCondition().get("salesdept");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.salesdept = '" + str + "' ";
        }
        if (pageMap.getCondition().containsKey("customerid")) {
            String str = (String) pageMap.getCondition().get("customerid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                query_sql += " and FIND_IN_SET(t.customerid, '" + str + "') ";
            } else {
                query_sql += " and t.customerid = '" + str + "' ";
            }
        }
        if (pageMap.getCondition().containsKey("pcustomerid")) {
            String str = (String) pageMap.getCondition().get("pcustomerid");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.pcustomerid = '" + str + "' ";
        }
        if (pageMap.getCondition().containsKey("businessdate1")) {
            String str = (String) pageMap.getCondition().get("businessdate1");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.businessdate >= '" + str + "'";
        }
        if (pageMap.getCondition().containsKey("businessdate2")) {
            String str = (String) pageMap.getCondition().get("businessdate2");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.businessdate <= '" + str + "'";
        }

        pageMap.getCondition().put("query_sql", query_sql);
        String query_sql_push = query_sql.replaceAll("t1.", "t.");
        query_sql_push = query_sql_push.replaceAll("t.goodsid", "t.brandid");
        pageMap.getCondition().put("query_sql_push", query_sql_push);
        //排序
        String orderstr = null;
        if (pageMap.getCondition().containsKey("sort")) {
            String sort = (String) pageMap.getCondition().get("sort");
            String order = (String) pageMap.getCondition().get("order");
            if (null == order) {
                order = "asc";
            }
            orderstr = sort + " " + order;
        }
        pageMap.getCondition().put("orderstr", orderstr);
        List<Map> list = phoneMapper.getBaseSaleReport(pageMap);
        Map footerMap = new HashMap();
        BigDecimal totalsendamount = BigDecimal.ZERO;
        BigDecimal totalreturnamount = BigDecimal.ZERO;
        BigDecimal totalpushbalanceamount = BigDecimal.ZERO;
        BigDecimal totalsalesamount = BigDecimal.ZERO;
        List companyList = new ArrayList();
        Map map2 = new HashMap();
        map2.put("leaf", "0");
        List conpanyList = new ArrayList();
        String grouptype = (String) pageMap.getCondition().get("grouptype");
        if ("8".equals(grouptype)) {
            List<DepartMent> deptList = getBaseDepartMentMapper().getDeptListByParam(map2);
            Map dataMap = new HashMap();
            if (null != deptList && deptList.size() != 0) {
                for (DepartMent dept : deptList) {
                    for (Map map : list) {
                        String branddept = (String) map.get("branddept");
                        BigDecimal sendamount = BigDecimal.ZERO;
                        BigDecimal returnamount = BigDecimal.ZERO;
                        BigDecimal pushamount = BigDecimal.ZERO;
                        BigDecimal salesamount = BigDecimal.ZERO;
                        if (map.containsKey("sendamount")) {
                            sendamount = (BigDecimal) map.get("sendamount");
                        }
                        if (map.containsKey("returnamount")) {
                            returnamount = (BigDecimal) map.get("returnamount");
                        }
                        if (map.containsKey("pushamount")) {
                            pushamount = (BigDecimal) map.get("pushamount");
                        }
                        if (map.containsKey("salesamount")) {
                            salesamount = (BigDecimal) map.get("salesamount");
                        }
                        if (dataMap.containsKey(dept.getId())) {
                            if (branddept.indexOf(dept.getId()) == 0) {
                                Map data = (Map) dataMap.get(dept.getId());
                                BigDecimal oldsendamount = BigDecimal.ZERO;
                                BigDecimal oldreturnamount = BigDecimal.ZERO;
                                BigDecimal oldpushamount = BigDecimal.ZERO;
                                BigDecimal oldsalesamount = BigDecimal.ZERO;
                                if (data.containsKey("sendamount")) {
                                    oldsendamount = (BigDecimal) data.get("sendamount");
                                }
                                if (data.containsKey("returnamount")) {
                                    oldreturnamount = (BigDecimal) data.get("returnamount");
                                }
                                if (data.containsKey("pushamount")) {
                                    oldpushamount = (BigDecimal) data.get("pushamount");
                                }
                                if (data.containsKey("salesamount")) {
                                    oldsalesamount = (BigDecimal) data.get("salesamount");
                                }
                                sendamount = oldsendamount.add(sendamount);
                                returnamount = oldreturnamount.add(returnamount);
                                pushamount = oldpushamount.add(pushamount);
                                salesamount = oldsalesamount.add(salesamount);
                                data.put("sendamount", sendamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                                data.put("returnamount", returnamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                                data.put("pushamount", pushamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                                data.put("salesamount", salesamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                                dataMap.put(dept.getId(), data);
                            }
                        } else {
                            if (branddept.indexOf(dept.getId()) == 0) {
                                Map data = new HashMap();
                                data.put("name", dept.getName());
                                data.put("id", dept.getId());
                                data.put("sendamount", sendamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                                data.put("returnamount", returnamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                                data.put("pushamount", pushamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                                data.put("salesamount", salesamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                                dataMap.put(dept.getId(), data);
                            }
                        }
                    }
                }
            }
            for (Object data : dataMap.keySet()) {
                Map dataobjcet = (Map) dataMap.get(data);
                conpanyList.add(dataobjcet);
            }
        }
        for (Map map : list) {
            if (map.containsKey("customerid")) {
                String customerid = (String) map.get("customerid");
                Customer customer = getCustomerByID(customerid);
                if (null != customer) {
                    map.put("customername", customer.getName());
                }
                if ("customerid".equals(groupcols)) {
                    map.put("id", customerid);
                    map.put("name", "[" + customerid + "]" + customer.getName());
                }
            }
            if (map.containsKey("brandid")) {
                String brandid = (String) map.get("brandid");
                Brand brand = getGoodsBrandByID(brandid);
                if (null != brand) {
                    map.put("brandname", brand.getName());
                    if ("brandid".equals(groupcols)) {
                        map.put("id", brandid);
                        map.put("name", brand.getName());
                    }
                } else {
                    if ("brandid".equals(groupcols)) {
                        map.put("id", brandid);
                        map.put("name", "其他");
                    }
                }
            }
            if (map.containsKey("branddept")) {
                String branddept = (String) map.get("branddept");
                DepartMent departMent = getDepartmentByDeptid(branddept);
                if (null != departMent) {
                    map.put("departMentname", departMent.getName());
                    if ("branddept".equals(groupcols)) {
                        map.put("id", branddept);
                        map.put("name", departMent.getName());
                    }
                } else {
                    if ("branddept".equals(groupcols)) {
                        map.put("id", branddept);
                        map.put("name", "其他");
                    }
                }
            }
            if (map.containsKey("salesuser")) {
                String salesuser = (String) map.get("salesuser");
                Personnel personnel = getPersonnelById(salesuser);
                if (null != personnel) {
                    map.put("salesusername", personnel.getName());
                    if ("salesuser".equals(groupcols)) {
                        map.put("id", salesuser);
                        map.put("name", personnel.getName());
                    }
                } else {
                    if ("salesuser".equals(groupcols)) {
                        map.put("id", salesuser);
                        map.put("name", "其他");
                    }
                }

            }
            if (map.containsKey("salesarea")) {
                String salesarea = (String) map.get("salesarea");
                SalesArea salesArea = getSalesareaByID(salesarea);
                if (null != salesArea) {
                    map.put("salesareaname", salesArea.getName());
                    if ("salesarea".equals(groupcols)) {
                        map.put("id", salesarea);
                        map.put("name", salesArea.getName());
                    }
                } else {
                    if ("salesarea".equals(groupcols)) {
                        map.put("id", salesarea);
                        map.put("name", "其他");
                    }
                }
            }
            if (map.containsKey("customersort")) {
                String customersort = (String) map.get("customersort");
                CustomerSort customerSort = getCustomerSortByID(customersort);
                if (null != customerSort) {
                    map.put("customersortname", customerSort.getName());
                    if ("customersort".equals(groupcols)) {
                        map.put("id", customersort);
                        map.put("name", customerSort.getName());
                    }
                } else {
                    if ("customersort".equals(groupcols)) {
                        map.put("id", customersort);
                        map.put("name", "其他");
                    }
                }
            }
            if (map.containsKey("branduser")) {
                String branduser = (String) map.get("branduser");
                Personnel personnel = getPersonnelById(branduser);
                if (null != personnel) {
                    map.put("brandusername", personnel.getName());
                    if ("branduser".equals(groupcols)) {
                        map.put("id", branduser);
                        map.put("name", personnel.getName());
                    }
                } else {
                    if ("branduser".equals(groupcols)) {
                        map.put("id", branduser);
                        map.put("name", "其他");
                    }
                }
            }
            if (map.containsKey("goodsid")) {
                String goodsid = (String) map.get("goodsid");
                GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
                if (null != goodsInfo) {
                    map.put("goodsname", goodsInfo.getName());
                    if ("goodsid".equals(groupcols)) {
                        map.put("id", goodsid);
                        map.put("name", "[" + goodsid + "]" + goodsInfo.getName());
                    }
                } else {
                    Brand brand = getGoodsBrandByID(goodsid);
                    if (null != brand) {
                        map.put("goodsname", brand.getName() + "(折扣)");
                        if ("goodsid".equals(groupcols)) {
                            map.put("id", goodsid);
                            map.put("name", brand.getName());
                        }
                    }
                }
            }
            if (map.containsKey("branduserdept")) {
                String branduserdept = (String) map.get("branduserdept");
                DepartMent departMent = getDepartMentById(branduserdept);
                if (null != departMent) {
                    map.put("branduserdeptname", departMent.getName());
                    if ("branduserdept".equals(groupcols)) {
                        map.put("id", branduserdept);
                        map.put("name", departMent.getName());
                    }
                } else {
                    if ("branduserdept".equals(groupcols)) {
                        map.put("id", branduserdept);
                        map.put("name", "其他");
                    }
                }
            } else if ("branduserdept".equals(groupcols)) {
                map.put("id", "其他");
                map.put("name", "其他");
            }

            BigDecimal sendamount = BigDecimal.ZERO;
            BigDecimal returnamount = BigDecimal.ZERO;
            BigDecimal pushamount = BigDecimal.ZERO;
            BigDecimal salesamount = BigDecimal.ZERO;
            if (map.containsKey("sendamount")) {
                sendamount = (BigDecimal) map.get("sendamount");
                map.put("sendamount", sendamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
            if (map.containsKey("returnamount")) {
                returnamount = (BigDecimal) map.get("returnamount");
                map.put("returnamount", returnamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
            if (map.containsKey("pushamount")) {
                pushamount = (BigDecimal) map.get("pushamount");
                map.put("pushamount", pushamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
            if (map.containsKey("salesamount")) {
                salesamount = (BigDecimal) map.get("salesamount");
                map.put("salesamount", salesamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
            }
            totalsendamount = totalsendamount.add(sendamount);
            totalreturnamount = totalreturnamount.add(returnamount);
            totalpushbalanceamount = totalpushbalanceamount.add(pushamount);
            totalsalesamount = totalsalesamount.add(salesamount);
        }
        footerMap.put("sendamount", totalsendamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        footerMap.put("returnamount", totalreturnamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        footerMap.put("pushamount", totalpushbalanceamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        footerMap.put("salesamount", totalsalesamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        footerMap.put("name", "合计");
        Map map = new HashMap();
        if ("8".equals(grouptype)) {
            map.put("list", conpanyList);
        } else {
            map.put("list", list);
        }
        map.put("footer", footerMap);
        return map;
    }

    @Override
    public Map getBaseWithdrawReport(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_report_sales_base", "z");
        pageMap.setDataSql(dataSql);
        String query_sql = " 1=1 ";
        if (pageMap.getCondition().containsKey("pcustomerid")) {
            String str = (String) pageMap.getCondition().get("pcustomerid");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.pcustomerid = '" + str + "' ";
        }
        if (pageMap.getCondition().containsKey("brandid")) {
            String str = (String) pageMap.getCondition().get("brandid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                query_sql += " and FIND_IN_SET(t1.brandid, '" + str + "') ";
            } else {
                query_sql += " and t1.brandid = '" + str + "' ";
            }
        }
        if (pageMap.getCondition().containsKey("goodsid")) {
            String str = (String) pageMap.getCondition().get("goodsid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                query_sql += " and FIND_IN_SET(t1.goodsid, '" + str + "') ";
            } else {
                query_sql += " and t1.goodsid = '" + str + "' ";
            }
        }
        if (pageMap.getCondition().containsKey("branduser")) {
            String str = (String) pageMap.getCondition().get("branduser");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                query_sql += " and FIND_IN_SET(t1.branduser, '" + str + "') ";
            } else {
                query_sql += " and t1.branduser = '" + str + "' ";
            }
        }
        if (pageMap.getCondition().containsKey("salesarea")) {
            String str = (String) pageMap.getCondition().get("salesarea");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                String[] strArr = str.split(",");
                query_sql += " and ( ";
                int i = 0;
                for (String salearea : strArr) {
                    if (i == 0) {
                        query_sql += " t.salesarea like '" + salearea + "%' ";
                    } else {
                        query_sql += " or t.salesarea like '" + salearea + "%' ";
                    }
                    i++;
                }
                query_sql += ")";
            } else {
                query_sql += " and t.salesarea like '" + str + "%' ";
            }
        }
        if (pageMap.getCondition().containsKey("customersort")) {
            String str = (String) pageMap.getCondition().get("customersort");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                String[] strArr = str.split(",");
                query_sql += " and ( ";
                int i = 0;
                for (String customersort : strArr) {
                    if (i == 0) {
                        query_sql += " t.customersort like '" + customersort + "%' ";
                    } else {
                        query_sql += " or t.customersort like '" + customersort + "%' ";
                    }
                    i++;
                }
                query_sql += ")";
            } else {
                query_sql += " and t.customersort like '" + str + "%' ";
            }
        }
        if (pageMap.getCondition().containsKey("salesuser")) {
            String str = (String) pageMap.getCondition().get("salesuser");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                query_sql += " and FIND_IN_SET(t.salesuser, '" + str + "') ";
            } else {
                query_sql += " and t.salesuser = '" + str + "' ";
            }
        }
        if (pageMap.getCondition().containsKey("salesdept")) {
            String str = (String) pageMap.getCondition().get("salesdept");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t.salesdept = '" + str + "' ";
        }
        if (pageMap.getCondition().containsKey("customerid")) {
            String str = (String) pageMap.getCondition().get("customerid");
            str = StringEscapeUtils.escapeSql(str);
            if (str.indexOf(",") > 0) {
                query_sql += " and FIND_IN_SET(t.customerid, '" + str + "') ";
            } else {
                query_sql += " and t.customerid = '" + str + "' ";
            }
        }
        if (pageMap.getCondition().containsKey("businessdate1")) {
            String str = (String) pageMap.getCondition().get("businessdate1");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t1.writeoffdate >= '" + str + "'";
        }
        if (pageMap.getCondition().containsKey("businessdate2")) {
            String str = (String) pageMap.getCondition().get("businessdate2");
            str = StringEscapeUtils.escapeSql(str);
            query_sql += " and t1.writeoffdate <= '" + str + "'";
        }

        String groupcols = (String) pageMap.getCondition().get("groupcols");
        if (!pageMap.getCondition().containsKey("groupcols")) {
            //小计列
            pageMap.getCondition().put("groupcols", "customerid");
            groupcols = "customerid";
        }
        pageMap.getCondition().put("query_sql", query_sql);
        String query_sql_push = query_sql.replaceAll("t1.", "t.");
        query_sql_push = query_sql_push.replaceAll("t.goodsid", "t.brandid");
        query_sql_push = query_sql_push.replaceAll("t1.writeoffdate", "t.writeoffdate");
        pageMap.getCondition().put("query_sql_push", query_sql_push);
        //排序
        String orderstr = null;
        if (pageMap.getCondition().containsKey("sort")) {
            String sort = (String) pageMap.getCondition().get("sort");
            String order = (String) pageMap.getCondition().get("order");
            if (null == order) {
                order = "asc";
            }
            orderstr = sort + " " + order;
        }
        pageMap.getCondition().put("orderstr", orderstr);
        List<Map> list = phoneMapper.getBaseWithdrawReport(pageMap);
        Map footerMap = new HashMap();
        BigDecimal totalamount = BigDecimal.ZERO;
        List companyList = new ArrayList();
        Map map2 = new HashMap();
        map2.put("leaf", "0");
        List dataList = new ArrayList();
        String grouptype = (String) pageMap.getCondition().get("grouptype");
        if ("8".equals(grouptype)) {
            List<DepartMent> deptList = getBaseDepartMentMapper().getDeptListByParam(map2);
            Map dataMap = new HashMap();
            if (null != deptList && deptList.size() != 0) {
                for (DepartMent dept : deptList) {
                    for (Map map : list) {
                        String branddept = (String) map.get("branddept");
                        BigDecimal amount = BigDecimal.ZERO;
                        if (map.containsKey("withdrawnamount")) {
                            amount = (BigDecimal) map.get("withdrawnamount");
                        }
                        if (dataMap.containsKey(dept.getId())) {
                            if (branddept.indexOf(dept.getId()) == 0) {
                                Map data = (Map) dataMap.get(dept.getId());
                                BigDecimal oldamount = BigDecimal.ZERO;
                                if (data.containsKey("amount")) {
                                    oldamount = (BigDecimal) data.get("amount");
                                }
                                oldamount = oldamount.add(amount);
                                data.put("amount", oldamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                                dataMap.put(dept.getId(), data);
                            }
                        } else {
                            if (branddept.indexOf(dept.getId()) == 0) {
                                Map data = new HashMap();
                                data.put("name", dept.getName());
                                data.put("id", dept.getId());
                                data.put("amount", amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                                dataMap.put(dept.getId(), data);
                            }
                        }
                    }
                }
            }
            for (Object data : dataMap.keySet()) {
                Map dataobjcet = (Map) dataMap.get(data);
                BigDecimal amount = BigDecimal.ZERO;
                if (dataobjcet.containsKey("amount")) {
                    amount = (BigDecimal) dataobjcet.get("amount");
                }
                totalamount = totalamount.add(amount);
                dataList.add(dataobjcet);
            }
        } else {
            for (Map map : list) {
                Map dataMap = new HashMap();
                if ("customerid".equals(groupcols)) {
                    String customerid = (String) map.get("customerid");
                    Customer customer = getCustomerByID(customerid);
                    if (null != customer) {
                        dataMap.put("id", customerid);
                        dataMap.put("name", "[" + customerid + "]" + customer.getName());
                    }
                }
                if ("brandid".equals(groupcols)) {
                    String brandid = (String) map.get("brandid");
                    Brand brand = getGoodsBrandByID(brandid);
                    if (null != brand) {
                        dataMap.put("id", brandid);
                        dataMap.put("name", brand.getName());
                    } else {
                        dataMap.put("id", brandid);
                        dataMap.put("name", "其他");
                    }
                }
                if ("branddept".equals(groupcols)) {
                    String branddept = (String) map.get("branddept");
                    DepartMent departMent = getDepartmentByDeptid(branddept);
                    if (null != departMent) {
                        dataMap.put("id", branddept);
                        dataMap.put("name", departMent.getName());
                    } else {
                        dataMap.put("id", branddept);
                        dataMap.put("name", "其他");
                    }
                }
                if ("salesuser".equals(groupcols)) {
                    String salesuser = (String) map.get("salesuser");
                    Personnel personnel = getPersonnelById(salesuser);
                    if (null != personnel) {
                        dataMap.put("id", salesuser);
                        dataMap.put("name", personnel.getName());
                    } else {
                        dataMap.put("id", salesuser);
                        dataMap.put("name", "其他");
                    }

                }
                if ("salesarea".equals(groupcols)) {
                    String salesarea = (String) map.get("salesarea");
                    SalesArea salesArea = getSalesareaByID(salesarea);
                    if (null != salesArea) {
                        dataMap.put("id", salesarea);
                        dataMap.put("name", salesArea.getName());
                    } else {
                        dataMap.put("id", salesarea);
                        dataMap.put("name", "其他");
                    }
                }
                if ("customersort".equals(groupcols)) {
                    String customersort = (String) map.get("customersort");
                    CustomerSort customerSort = getCustomerSortByID(customersort);
                    if (null != customerSort) {
                        dataMap.put("id", customersort);
                        dataMap.put("name", customerSort.getName());
                    } else {
                        dataMap.put("id", customersort);
                        dataMap.put("name", "其他");
                    }
                }
                if ("branduser".equals(groupcols)) {
                    String branduser = (String) map.get("branduser");
                    Personnel personnel = getPersonnelById(branduser);
                    if (null != personnel) {
                        dataMap.put("id", branduser);
                        dataMap.put("name", personnel.getName());
                    } else {
                        dataMap.put("id", branduser);
                        dataMap.put("name", "其他");
                    }
                }
                if ("goodsid".equals(groupcols)) {
                    String goodsid = (String) map.get("goodsid");
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
                    if (null != goodsInfo) {
                        dataMap.put("id", goodsid);
                        dataMap.put("name", "[" + goodsid + "]" + goodsInfo.getName());
                    } else {
                        Brand brand = getGoodsBrandByID(goodsid);
                        if (null != brand) {
                            dataMap.put("goodsname", brand.getName() + "(折扣)");
                            dataMap.put("id", goodsid);
                            dataMap.put("name", "(折扣)" + brand.getName());
                        } else {
                            dataMap.put("id", goodsid);
                            dataMap.put("name", "折扣");
                        }
                    }
                }
                BigDecimal amount = BigDecimal.ZERO;
                if (map.containsKey("withdrawnamount")) {
                    amount = (BigDecimal) map.get("withdrawnamount");
                }
                dataMap.put("amount", amount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                totalamount = totalamount.add(amount);
                dataList.add(dataMap);
            }
        }
        footerMap.put("totalamount", totalamount.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        footerMap.put("name", "合计");
        Map map = new HashMap();
        map.put("list", dataList);
        map.put("footer", footerMap);
        return map;
    }

    @Override
    public List getSaleoutUnCheckList(String storageid, String date)
            throws Exception {
        String status = "3";
        String scanSaleOutType = getSysParamValue("ScanSaleOutType");
        if ("1".equals(scanSaleOutType)) {
            status = "2";
        } else if ("2".equals(scanSaleOutType)) {
            status = "3";
        }

        List<Saleout> list = storageSaleOutService.getSaleoutUnCheckList(storageid, date, status);
        List dataList = new ArrayList();
        for (Saleout saleout : list) {
            Map map = new HashMap();
            map.put("billid", saleout.getId());
            map.put("orderid", saleout.getSaleorderid());
            map.put("customerid", saleout.getCustomerid());
            Customer customer = getCustomerByID(saleout.getCustomerid());
            if (null != customer) {
                map.put("customername", customer.getName());
            }
            map.put("storageid", saleout.getStorageid());
            StorageInfo storageInfo = getStorageInfoByID(saleout.getStorageid());
            if (null != storageInfo) {
                map.put("storagename", storageInfo.getName());
            }
            map.put("businessdate", saleout.getBusinessdate());
            dataList.add(map);
        }
        return dataList;
    }

    /**
     * 扫描枪获取发货单列表
     *
     * @return
     * @throws Exception
     */
    @Override
    public List getSaleoutForScanList(Map map) throws Exception {
        List<Saleout> list = storageSaleOutService.getSaleoutForScanList("2", map);
        List dataList = new ArrayList();
        for (Saleout saleout : list) {
            Map dMap = new HashMap();
            dMap.put("billid", saleout.getId());
            dMap.put("orderid", saleout.getSaleorderid());
            dMap.put("customerid", saleout.getCustomerid());
            Customer customer = getCustomerByID(saleout.getCustomerid());
            if (null != customer) {
                dMap.put("customername", customer.getName());
            }
            dMap.put("storageid", saleout.getStorageid());
            StorageInfo storageInfo = getStorageInfoByID(saleout.getStorageid());
            if (null != storageInfo) {
                dMap.put("storagename", storageInfo.getName());
            }
            dMap.put("businessdate", saleout.getBusinessdate());
            dataList.add(dMap);
        }
        return dataList;
    }

    /**
     * 根据发货单编号 获取相关发货单信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Saleout getSaleoutInfoByID(String id) throws Exception {
        Saleout saleout = storageSaleOutService.getSaleOutInfoWithDataSql(id);
        if (null != saleout) {
            Customer customer = getCustomerByID(saleout.getCustomerid());
            if (null != customer) {
                saleout.setCustomername(customer.getName());
            }
            StorageInfo storageInfo = getStorageInfoByID(saleout.getStorageid());
            if (null != storageInfo) {
                saleout.setStoragename(storageInfo.getName());
            }
        }
        return saleout;
    }

    @Override
    public List getSaleoutDetail(String billid) throws Exception {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Saleout saleout = storageSaleOutService.getBaseSaleOutInfo(billid);
        if (null != saleout) {
            SysUser sysUser = getSysUser();
            //发货单核对人为空 或者核对人是同步的用户时 可以获取数据
            if (StringUtils.isEmpty(saleout.getCheckuserid())
                    || (StringUtils.isNotEmpty(saleout.getCheckuserid()) && saleout.getCheckuserid().equals(sysUser.getUserid()))) {
                List<SaleoutDetail> list = storageSaleOutService.getSaleoutDetailWithoutDiscount(billid);

                for (SaleoutDetail detail : list) {
                    Map<String, String> map = new HashMap<String, String>();
                    GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
                    map.put("cid", detail.getId() + "");
                    map.put("goodsid", detail.getGoodsid());
                    if (goodsInfo != null) {
                        map.put("goodsname", goodsInfo.getName());
                        map.put("barcode", goodsInfo.getBarcode());
                    }
                    map.put("batchno", StringUtils.isNotEmpty(detail.getBatchno()) ? detail.getBatchno() : "");
                    map.put("produceddate", StringUtils.isNotEmpty(detail.getProduceddate()) ? detail.getProduceddate() : "");
                    map.put("deadline", StringUtils.isNotEmpty(detail.getDeadline()) ? detail.getDeadline() : "");
                    map.put("unitid", detail.getUnitid());
                    map.put("unitname", detail.getUnitname());
                    map.put("auxunitid", detail.getAuxunitid());
                    map.put("auxunitname", detail.getAuxunitname());
                    map.put("auxnum", CommonUtils.subZeroAndDot(detail.getAuxnum() + ""));
                    map.put("num", CommonUtils.subZeroAndDot(detail.getAuxremainder() + ""));
                    map.put("initnum", CommonUtils.subZeroAndDot(detail.getUnitnum() + ""));
                    map.put("initnumdetail", detail.getAuxnumdetail());
                    if (!"1".equals(detail.getIsdiscount())) {
                        result.add(map);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Map updateSaleoutCheckFlag(String billid) throws Exception {
        String scanSaleOutType = getSysParamValue("ScanSaleOutType");
        boolean flag = false;
        String msg = "";
        if ("2".equals(scanSaleOutType)) {
            flag = storageSaleOutService.updateSaleoutCheckFlag(billid);
        } else if ("1".equals(scanSaleOutType)) {
            Map returnMap = storageSaleOutService.auditSaleOut(billid);
            if (null != returnMap && returnMap.containsKey("flag")) {
                flag = (Boolean) returnMap.get("flag");
                msg = (String) returnMap.get("msg");
            }
        }
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("msg", msg);
        return map;
    }

    /**
     * 扫描枪同步发货单后 更新发货单核对人
     *
     * @param billid
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年6月30日
     */
    @Override
    public Map updateSaleoutCheckFlagByScan(String billid) throws Exception {
        String msg = "";
        boolean flag = storageSaleOutService.updateSaleoutCheckFlag(billid);
        Map map = new HashMap();
        map.put("flag", flag);
        map.put("msg", msg);
        return map;
    }

    /**
     * 更新发货单明细数量 并且审核
     *
     * @param json
     * @return
     * @throws Exception
     */
    @Override
    public Map updateSaleoutAndAudit(String json) throws Exception {
        Map map = new HashMap();
        boolean flag = false;
        String msg = "";
        if (StringUtils.isNotEmpty(json)) {
            map = storageSaleOutService.updateSaleoutAndAudit(json);
        } else {
            map.put("flag", flag);
            map.put("msg", "上传数据有问题");
        }
        return map;
    }

    @Override
    public List getRouteReportList(PageMap pageMap) throws Exception {
        //数据权限
        String sql = getDataAccessRule("t_base_personnel", "p");
        pageMap.setDataSql(sql);
        List<RouteDistance> list = distanceMapper.getRouteReportList(pageMap);
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        Map<String, Map<String, Object>> retMap = new LinkedHashMap<String, Map<String, Object>>();
        for (RouteDistance routeDistance : list) {
            //判断是否存在业务员
            if (retMap.containsKey(routeDistance.getUserid())) {
                Map<String, Object> map = retMap.get(routeDistance.getUserid());
                //大约多少米，精确3为小数
                BigDecimal lastDistance = new BigDecimal(null != routeDistance.getDistance() ? routeDistance.getDistance() : 0).divide(new BigDecimal("1000"), 3, BigDecimal.ROUND_HALF_UP);
                map.put(routeDistance.getAdddate(), lastDistance);
                retMap.put(routeDistance.getUserid(), map);
            } else {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userid", routeDistance.getUserid());
                SysUser sysUser = getSysUser(routeDistance.getUserid());
                if (null != sysUser) {
                    map.put("username", sysUser.getName());
                }
                //大约多少米，精确3为小数
                BigDecimal lastDistance = new BigDecimal(null != routeDistance.getDistance() ? routeDistance.getDistance() : 0).divide(new BigDecimal("1000"), 3, BigDecimal.ROUND_HALF_UP);
                map.put(routeDistance.getAdddate(), lastDistance);
                retMap.put(routeDistance.getUserid(), map);
            }
        }
        if (!retMap.isEmpty()) {
            Iterator iter = retMap.entrySet().iterator();  //获得map的Iterator
            while (iter.hasNext()) {
                Entry entry = (Entry) iter.next();
                Map<String, Object> map = (Map<String, Object>) entry.getValue();
                retList.add(map);
            }
        }
        return retList;
    }

    @Override
    public List getCustomerHisGoodsSalesList(String customerid, String goodsid)
            throws Exception {
        List list = storageSaleOutService.getCustomerHisGoodsSalesList(customerid, goodsid);
        return list;
    }

    @Override
    public Map getPromotionBundData(String id, String customerid)
            throws Exception {
        Map map = new HashMap();
        PromotionPackageGroup packageGroup = orderService.getOrderGoodsPromotionDetailInfo(id);
        if (null != packageGroup) {
            String name = packageGroup.getGroupname();
            BigDecimal price = packageGroup.getPrice();
            BigDecimal oldPrice = packageGroup.getOldprice();
            BigDecimal remainnum = packageGroup.getRemainnum();
            BigDecimal limitnum = packageGroup.getLimitnum();
            Map gmap = new HashMap();
            gmap.put("groupid", id);
            gmap.put("groupname", name);
            gmap.put("price", price);
            gmap.put("oldprice", oldPrice);
            gmap.put("remainnum", remainnum);
            gmap.put("limitnum", limitnum);
            map.put("group", gmap);

            List<PromotionPackageGroupDetail> list = packageGroup.getGroupDetails();
            for (PromotionPackageGroupDetail detail : list) {
                GoodsInfo goodsInfo = getAllGoodsInfoByID(detail.getGoodsid());
                detail.setBoxnum(goodsInfo.getBoxnum());
                detail.setBarcode(goodsInfo.getBarcode());
            }
            map.put("detail", list);

        }
        return map;
    }

    @Override
    public PageData searchCustomerList(PageMap pageMap) throws Exception {
        String con = (String) pageMap.getCondition().get("con");
        //判断客户档案是否需要同步
        Map customerMap = new HashMap();
        SysUser sysUser = getSysUser();
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        if (isBrandUser) {
            customerMap.put("isBrandUser", true);
            customerMap.put("personnelid", sysUser.getPersonnelid());
        } else {
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if (isSupplierUser) {
                customerMap.put("isSupplierUser", true);
                customerMap.put("personnelid", sysUser.getPersonnelid());
            }
        }
        String customerDataSql = getDataAccessRule("t_base_sales_customer", "t");
        customerMap.put("datasql", customerDataSql);
        customerMap.put("con", con);
        //多条件模糊查询 以空格为标识
        if (StringUtils.isNotEmpty(con) && con.indexOf(" ") >= 0) {
            String[] conArr = con.split(" ");
            List<String> conList = new ArrayList<String>();
            for (String constr : conArr) {
                constr = constr.trim();
                if (StringUtils.isNotEmpty(constr)) {
                    conList.add(constr);
                }
            }
            customerMap.put("conarr", conList);
        }
        pageMap.setCondition(customerMap);
        List<Customer> customerList = getBaseFilesCustomerMapper().getCustomerListForPhone(pageMap);
        List<Map> result = new ArrayList<Map>();
        for (Customer customer : customerList) {
            Map datamap = new HashMap();
            datamap.put("customerid", customer.getId());
            datamap.put("customername", customer.getName());
            datamap.put("addr", customer.getAddress());
            result.add(datamap);
        }
        int total = getBaseFilesCustomerMapper().getCustomerListForPhoneCount(pageMap);
        PageData pageData = new PageData(total, result, pageMap);
        return pageData;
    }

    /**
     * 为扫描枪提供采购入库单列表
     * @param map               查询条件
     * @return
     * @throws Exception
     */
    @Override
    public List getPurchaseEnterForScanList(Map map) throws Exception {
        List<PurchaseEnter> list = purchaseEnterService.getPurchaseEnterForScanList(map);
        List dataList = new ArrayList();
        for (PurchaseEnter purchaseEnter : list) {
            Map dMmap = new HashMap();
            dMmap.put("billid", purchaseEnter.getId());
            String orderid = purchaseEnter.getSourceid();
            if (StringUtils.isEmpty(orderid)) {
                orderid = "";
            }
            dMmap.put("orderid", orderid);
            dMmap.put("supplierid", purchaseEnter.getSupplierid());
            BuySupplier supplier = getSupplierInfoById(purchaseEnter.getSupplierid());
            if (null != supplier) {
                dMmap.put("suppliername", supplier.getName());
            } else {
                dMmap.put("suppliername", "");
            }
            dMmap.put("storageid", purchaseEnter.getStorageid());
            StorageInfo storageInfo = getStorageInfoByID(purchaseEnter.getStorageid());
            if (null != storageInfo) {
                dMmap.put("storagename", storageInfo.getName());
            } else {
                dMmap.put("storagename", "");
            }
            dMmap.put("businessdate", purchaseEnter.getBusinessdate());
            dMmap.put("remark", purchaseEnter.getRemark() == null ? "" : purchaseEnter.getRemark());
            dataList.add(dMmap);
        }
        return dataList;

    }

    /**
     * 获取采购入库单明细列表
     *
     * @return
     * @throws Exception
     */
    @Override
    public List getPurchaseEnterDetail(String id) throws Exception {
        List<PurchaseEnterDetail> list = purchaseEnterService.getPurchaseEnterDetail(id);
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        for (PurchaseEnterDetail detail : list) {
            Map<String, String> map = new HashMap<String, String>();
            GoodsInfo goodsInfo = getGoodsInfoByID(detail.getGoodsid());
            map.put("cid", detail.getId() + "");
            map.put("goodsid", detail.getGoodsid());
            if (goodsInfo != null) {
                map.put("goodsname", goodsInfo.getName());
                map.put("barcode", goodsInfo.getBarcode());
            }
            map.put("unitid", detail.getUnitid());
            map.put("unitname", detail.getUnitname());
            map.put("auxunitid", detail.getAuxunitid());
            map.put("auxunitname", detail.getAuxunitname());
            map.put("auxnum", CommonUtils.subZeroAndDot(detail.getAuxnum() + ""));
            map.put("num", CommonUtils.subZeroAndDot(detail.getAuxremainder() + ""));
            map.put("remark", detail.getRemark() == null ? "" : detail.getRemark());
            map.put("goodstype", detail.getGoodstype());
            result.add(map);
        }
        return result;
    }

    /**
     * 扫描枪上传采购入库单 并且审核
     *
     * @param json
     * @return
     * @throws Exception
     */
    @Override
    public Map uploadPurchaseEnterAndAudit(String json) throws Exception {
        Map map = new HashMap();
        boolean flag = false;
        String msg = "";
        if (StringUtils.isNotEmpty(json)) {
            map = purchaseEnterService.uploadPurchaseEnterAndAudit(json);
        } else {
            map.put("flag", flag);
            map.put("msg", "上传数据有问题");
        }
        return map;
    }

    @Override
    public Map addRootByDateAndUserId(String startDate, String endDate, String userid) throws Exception {
        Map paramMap = new HashMap();
        paramMap.put("begindate", startDate);
        paramMap.put("enddate", endDate);

        //所有业务员
        if (StringUtils.isEmpty(userid)) {
            paramMap.put("ifgroup", 1);
            List<Location> locations = locationMapper.getLocationListByDateAndUserId(paramMap);
            for (Location location : locations) {
                String addId = location.getUserid();//获取出所有userId
                addRootForOneUser(startDate, endDate, addId);
            }
        } else {
            addRootForOneUser(startDate, endDate, userid);
        }

        Map rsMap = new HashMap();
        rsMap.put("flag", true);
        rsMap.put("msg", "生成成功");
        return rsMap;
    }

    /**
     * 获取客户列表数据
     *
     * @param pageMap
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2015年9月10日
     */
    @Override
    public PageData searchOrderList(PageMap pageMap) throws Exception {
        PageData pageData = orderService.getOrderData(pageMap);
        return pageData;
    }

    /**
     * 根据订单编号 获取销售订单明细
     *
     * @param orderid
     * @return
     * @throws Exception
     */
    @Override
    public List getOrderDetail(String orderid) throws Exception {
        Order order = orderService.getOrder(orderid);
        if (null != order) {
            List list = order.getOrderDetailList();
            return list;
        }
        return null;
    }

    /**
     * 单个用户
     * @param begindate
     * @param enddate
     * @param userId
     * @return
     * @throws Exception
     * @author huangzhiqian
     * @date 2016年1月20日
     */
    private synchronized boolean addRootForOneUser(String begindate, String enddate, String userId) throws Exception {
        List<String> dateList = getAllDate(begindate, enddate);
        String baiduWebAK = getSysParamValue("baiduWebAK");
        if(StringUtils.isEmpty(baiduWebAK) || "123456789".equals(baiduWebAK)){
            baiduWebAK = "i5TlrzbmwDbygpliitdF2Fal";
        }
        for (String dateStr : dateList) {
            locationMapper.deleteRouteDistance(dateStr, userId);
            //查看该业务员每天行程记录表 这天是否已经有数据 t_phone_route_distance
            int count = locationMapper.getRouteDistanceCount(dateStr, userId);
            //这天此业务员行程记录表没有
            if (count == 0) {
                //t_phone_location
                List<Location> locationList = locationMapper.getLocationsByexactDateAndUserId(dateStr, userId);
                if (locationList != null && locationList.size() > 0) {
                    List<List<Location>> avaiLableLocations = transFerResultToUpdate(locationList, dateStr);
                    if (avaiLableLocations.size() > 0) {

                        Integer routeLength = 0;//总路长
                        for (int i = 0; i < avaiLableLocations.size(); i++) {
                            //获取每条线路的url,发送http请求,得到routelength 加上去
                            List<Location> route = new ArrayList(avaiLableLocations.get(i));
                            Location destination = route.remove(route.size() - 1);
                            Location origin = route.remove(0);
                            String origin_region = getCityForLocation(origin);
                            origin_region = URLEncoder.encode(origin_region, "utf-8");
                            String destination_region = getCityForLocation(destination);
                            destination_region = URLEncoder.encode(destination_region, "utf-8");
                            String url = "http://api.map.baidu.com/direction/v1?mode=driving&origin=" + origin.getLatAndLot()+"&destination=" + destination.getLatAndLot() +"&region="+ origin_region + "&origin_region=" + origin_region + "&destination_region=" + destination_region + "&tactics=11&output=json&ak="+baiduWebAK;
                            String waypoints = "";
                            for(int j=0;j<route.size();j++){
                                Location wlocation = route.get(j);
                                if(j==0){
                                    waypoints = "waypoints="+wlocation.getLatAndLot();
                                }else{
                                    waypoints += URLEncoder.encode("|", "utf-8")+ wlocation.getLatAndLot() ;
                                }

                            }
                            url = url + "&" + waypoints;
                            Integer length = getRouteLength(url);
                            routeLength = routeLength + length;
                        }
                        RouteDistance distance = new RouteDistance();
                        distance.setAdddate(dateStr);
                        distance.setUserid(userId);
                        distance.setDistance(routeLength);
                        distanceMapper.addDistance(distance);

                    }

                }
            }
        }

        return true;
    }

    /**
     * 获取路线长度
     * @param url
     * @return
     * @throws Exception
     * @author huangzhiqian
     * @date 2016年1月21日
     */
    private Integer getRouteLength(String url) throws Exception {
        CloseableHttpResponse response = null;
        ObjectMapper mapper = new ObjectMapper();
        Integer length = 0;
        try {
            HttpGet httpget = new HttpGet(url);
            httpget.setConfig(RequestConfig.custom().setConnectTimeout(40000).setConnectionRequestTimeout(40000).setSocketTimeout(40000).build());
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                Map map = mapper.readValue(entity.getContent(), Map.class);
                if ((Integer) map.get("status") == 0) {
                    length = (Integer) ((Map) ((List) ((Map) map.get("result")).get("routes")).get(0)).get("distance");
                } else {
                    //返回状态码不为0
                    throw new RuntimeException("百度API返回状态码为" + map.get("status") + " .url : " + url);
                }
            }
            EntityUtils.consume(entity);
            return length;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 根据Location获取城市
     * @param location
     * @return
     * @throws Exception
     * @author huangzhiqian
     * @date 2016年1月21日
     */
    private String getCityForLocation(Location location) throws Exception {
        String baiduWebAK = getSysParamValue("baiduWebAK");
        if(StringUtils.isEmpty(baiduWebAK) || "123456789".equals(baiduWebAK)){
            baiduWebAK = "i5TlrzbmwDbygpliitdF2Fal";
        }
        CloseableHttpResponse response = null;
        ObjectMapper mapper = new ObjectMapper();
        String city = "";
        try {
            String url = "http://api.map.baidu.com/geocoder/v2/?ak="+baiduWebAK+"&location=" + location.getX() + "," + location.getY() + "&output=json";
            HttpGet httpget = new HttpGet(url);
            httpget.setConfig(RequestConfig.custom().setConnectTimeout(40000).setConnectionRequestTimeout(40000).setSocketTimeout(40000).build());
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                Map map = mapper.readValue(entity.getContent(), Map.class);
                if ((Integer) map.get("status") == 0) {
                    city = (String) ((Map) ((Map) map.get("result")).get("addressComponent")).get("city");
                } else {
                    //返回状态码不为0
                    throw new RuntimeException("百度API返回状态码为" + map.get("status") + " .Location : " + location.getLatAndLot());
                }
            }
            EntityUtils.consume(entity);
            return city;
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 筛选出系统参数时间范围内距离大于300米的所有点,并转换成能发送Http请求的List
     * @param locationList
     * @return
     * @author huangzhiqian
     * @throws Exception
     * @date 2016年1月20日
     */
    private List<List<Location>> transFerResultToUpdate(List<Location> locationList, String dateStr) throws Exception {

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //筛选 系统参数时间
        List<Location> filterdLocations = new ArrayList<Location>();//返回值
        String[] sysCodeTime = getSysParamValue("PhoneRouteTime").split("~");
        //locationList中的date日期都是一样的
        Date time1 = fmt.parse(dateStr + " " + sysCodeTime[0]);
        Date time2 = fmt.parse(dateStr + " " + sysCodeTime[1]);
        for (Location lc : locationList) {
            Date d = lc.getUpdatetime();
            if (d.after(time1) && d.before(time2)) {
                filterdLocations.add(lc);
            }
        }
        List<Location> filterdResult = new ArrayList<Location>();
        //筛选 距离
        Location tmpLocation = null;
        int size = filterdLocations.size();
        boolean hasAdd = false;
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                tmpLocation = filterdLocations.get(0);
                filterdResult.add(tmpLocation);
                continue;
            }
            if (further300(tmpLocation, filterdLocations.get(i))) {//如果距离大于300,添加到rsLocation中,并把tmpLocation替换掉
                hasAdd = true;
                filterdResult.add(filterdLocations.get(i));
                tmpLocation = filterdLocations.get(i);
            } else if (i == size - 1 && hasAdd) {//最后一条,而且中间有点
                filterdResult.add(filterdLocations.get(i));
            }
        }

        List<List<Location>> rsList = new ArrayList<List<Location>>();
        if (filterdResult.size() >= 2) {
            //waypoints = waypoints +1;
            //最大5个点
            int waypoints =1;
            //筛选后的结果拆分List,可以用于http
            //分成的组数
            int divideNum = (int) Math.ceil((filterdResult.size() - 1) / waypoints);
            List<Location> locations = null;
            for (int i = 0; i < divideNum; i++) {
                int subBeginIndex = i * waypoints;
                int subEndIndex = (i + 1) * waypoints > filterdResult.size() - 1 ? filterdResult.size() - 1 : (i + 1) * waypoints;
                locations = filterdResult.subList(subBeginIndex, subEndIndex + 1);
                rsList.add(locations);
            }
        }
        return rsList;

    }

    /**
     * 判断两个点的距离是否大于300m
     * @param location1
     * @param location2
     * @return
     * @author huangzhiqian
     * @date 2016年1月20日
     */
    private boolean further300(Location location1, Location location2) {
//        Double long1 = new Double(location1.getX());
//        Double long2 = new Double(location2.getX());
//        Double lat1 = new Double(location1.getY());
//        Double lat2 = new Double(location2.getY());
//        double R = 6378137; // 地球半径
//        lat1 = lat1 * Math.PI / 180.0;
//        lat2 = lat2 * Math.PI / 180.0;
//        double a = lat1 - lat2;
//        double b = (long1 - long2) * Math.PI / 180.0;
//        double d;
//        double sa2, sb2;
//        sa2 = Math.sin(a / 2.0);
//        sb2 = Math.sin(b / 2.0);
//        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
//        if (d > 300) {
//            return true;
//        }
        Double x = new Double(location1.getX());
        Double y = new Double(location1.getY());
        double nx = Double.parseDouble(location2.getX());
        double ny = Double.parseDouble(location2.getY());

        double xrate = (x-nx)*10000;
        double yrate = (y-ny)*10000;

        if((Math.abs(xrate)>=3) && Math.abs(yrate)>=3){
            return  true;
        }
        return false;

    }


    /**
     * 获取两个日期字符串之间所有的日期
     * @param begindate
     * @param enddate
     * @return
     * @throws ParseException
     * @author huangzhiqian
     * @date 2016年1月20日
     */
    private List<String> getAllDate(String begindate, String enddate) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String> dateList = new ArrayList<String>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fmt.parse(begindate));

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(fmt.parse(enddate));
        //enddate>=begindate
        while (!calendar2.before(calendar)) {
            dateList.add(fmt.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
        return dateList;
    }


    @Override
    public List getAllBank() throws Exception {
        List list = getBaseFilesFinanceMapper().getBankList();
        return list;
    }

    @Override
    public List<Map> getSaleUser() throws Exception {
        List<Map> list = getBaseFilesPersonnelMapper().getSaleUser();
        return list;
    }
}


