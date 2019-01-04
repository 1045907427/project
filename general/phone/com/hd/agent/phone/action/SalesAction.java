/**
 * @(#)SalesAction.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jul 13, 2013 zhengziyong 创建版本
 */
package com.hd.agent.phone.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.*;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.SpringContextUtils;
import com.hd.agent.phone.model.Location;
import com.hd.agent.phone.model.RouteDistance;
import com.hd.agent.phone.service.IPhoneService;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.sales.service.IOrderService;
import com.hd.agent.storage.model.CheckList;
import com.hd.agent.storage.model.Saleout;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.system.model.SysCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 手机订单相关
 * @author zhengziyong
 */
public class SalesAction extends BaseFilesAction {
	
	private IPhoneService phoneService;

	public IPhoneService getPhoneService() {
		return phoneService;
	}

	public void setPhoneService(IPhoneService phoneService) {
		this.phoneService = phoneService;
	}

	/**
	 * 返回用户商品数据列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Aug 14, 2013
	 */
	public String getGoodsList() throws Exception{
		PageData pageData  = phoneService.getGoodsInfoListBySysUser(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 获取商品及客户等数据（扫描枪）
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public String getGoodsAndCustomerList() throws Exception{
		List<PGoodsInfo> goodsList = phoneService.getAllGoodsInfoList();
		List<Customer> customerList = phoneService.getAllChildCustomer();
		List<StorageInfo> storageList = phoneService.getAllStorage();
		List<Map> result = new ArrayList<Map>();
		for(PGoodsInfo goodsInfo : goodsList){
			Map map = new HashMap();
			map.put("id", goodsInfo.getId());
			map.put("name", goodsInfo.getName());
			map.put("price", goodsInfo.getPrice());
			map.put("inprice", goodsInfo.getInprice());
			map.put("spell", goodsInfo.getSpell());
			map.put("barcode", goodsInfo.getBarcode());
			map.put("boxbarcode", goodsInfo.getBoxbarcode());
			map.put("brand", goodsInfo.getBrand());
			map.put("defaultsort", goodsInfo.getDefaultsort());
			map.put("unitid", goodsInfo.getUnitid());
			map.put("unitname", goodsInfo.getUnitname());
			map.put("auxunitid", goodsInfo.getAuxunitid());
			map.put("auxunitname", goodsInfo.getAuxunitname());
			map.put("rate", goodsInfo.getRate());
			map.put("type", "goods");
			result.add(map);
		}
		for(Customer customer: customerList){
			Map map = new HashMap();
			map.put("id", customer.getId());
			map.put("name", customer.getName());
			map.put("spell", customer.getShortcode());
			map.put("type", "customer");
			result.add(map);
		}
		Map storagemap = new HashMap();
		storagemap.put("id", "defaultstorage");
		storagemap.put("name", "默认仓库");
		storagemap.put("type", "storage");
		result.add(storagemap);
		for(StorageInfo storage: storageList){
			Map map = new HashMap();
			map.put("id", storage.getId());
			map.put("name", storage.getName());
			map.put("type", "storage");
			result.add(map);
		}
		addJSONArray(result);
		return SUCCESS;
	}

    /**
     * 获取仓库档案列表
     * @return
     * @throws Exception
     */
	public String getStorageList() throws Exception{
        List<Map> result = new ArrayList<Map>();
        List<StorageInfo> storageList = phoneService.getAllStorage();
        Map storagemap = new HashMap();
        storagemap.put("id", "defaultstorage");
        storagemap.put("name", "默认仓库");
        storagemap.put("type", "storage");
        result.add(storagemap);
        for(StorageInfo storage: storageList){
            Map map = new HashMap();
            map.put("id", storage.getId());
            map.put("name", storage.getName());
            map.put("type", "storage");
            result.add(map);
        }
        addJSONArray(result);
        return SUCCESS;
    }
	/**
	 * 返回所有品牌数据列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Aug 14, 2013
	 */
	public String getBrandList() throws Exception{
		List brandList = phoneService.getAllBrandList();
		addJSONArray(brandList);
		return SUCCESS;
	}
	
	/**
	 * 返回手机用户客户数据列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong
	 * @date Aug 14, 2013
	 */
	public String getCustomerList() throws Exception{
		List customerList = phoneService.getCustomerBySalesmanId();
        List customerDistributionList = phoneService.getDistributionRuleList(null);
		List offpriceList = phoneService.getOffPriceList(pageMap);
        customerList.addAll(customerDistributionList);
		customerList.addAll(offpriceList);
		addJSONArray(customerList);
		return SUCCESS;
	}
	
	/**
	 * 客户合同价列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public String getCustomerPriceList() throws Exception{
		PageData pageData = phoneService.getCustomerPriceList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	/**
	 * 获取客户合同价数量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Mar 17, 2014
	 */
	public String getCustomerPriceCount() throws Exception{
		SysUser sysUser = getSysUser();
		int count = phoneService.getCustomerPriceCount(sysUser.getUserid());
		addJSONObject("count", count);
		return SUCCESS;
	}
	/**
	 * 返回所有价格套数据列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Aug 14, 2013
	 */
	public String getPriceSortList() throws Exception{
		PageData pageData = phoneService.getAllPriceInfoList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
    /**
     * 返回扫描枪需要的编码表数据
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Aug 14, 2013
     */
    public String getScanSyscodeList() throws Exception{
        List<SysCode> list = getBaseSysCodeService().showSysCodeListByType("rejectCategory");
        List dlist = new ArrayList();
        for(SysCode sysCode : list){
            Map map = new HashedMap();
            map.put("id",sysCode.getCode());
            map.put("name",sysCode.getCodename());
            map.put("type",sysCode.getType());
            dlist.add(map);
        }
        addJSONArray(dlist);
        return SUCCESS;
    }
	/**
	 * 获取库存信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Aug 14, 2013
	 */
	public String getStorageInfo() throws Exception{
		String goodsId = request.getParameter("id");
		String storageid = request.getParameter("storageid");
        SysUser sysUser = getSysUser();
        if(StringUtils.isEmpty(storageid)){
            String OpenDeptStorage = getSysParamValue("OpenDeptStorage");
            if("1".equals(OpenDeptStorage)){
                DepartMent departMent = getBaseDepartMentService().showDepartMentInfo(sysUser.getDepartmentid());
                if (null != departMent) {
                    storageid = departMent.getStorageid();
                }
            }
        }
		if(StringUtils.isNotEmpty(storageid)){
			Map map = new HashMap();
			StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageid, goodsId);
			StorageInfo storageInfo = getStorageInfo(storageid);
			if(null!=storageSummary){
				Map auxstoragemap = countGoodsInfoNumber(goodsId, null, storageSummary.getUsablenum());
				String auxdetail = (String) auxstoragemap.get("auxnumdetail");
				map.put("totalnum", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum().toString()));
				map.put("storagenum", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum().toString()));
				map.put("total", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum()+storageSummary.getUnitname()+",箱数:"+auxdetail));
			}else{
				map.put("totalnum", 0);
				map.put("storagenum", 0);
				map.put("total", "0箱");
			}
			map.put("storagename", storageInfo.getName());
			
			addJSONObject(map);
		}else{
			StorageSummary storageSummary = getStorageSummarySumByGoodsidWithDatarule(goodsId);
			StorageInfo storageInfo = getStorageInfoByCarsaleuser(sysUser.getUserid());
			Map map = new HashMap();
			if(null!=storageSummary){
				Map auxmap = countGoodsInfoNumber(goodsId, null, storageSummary.getUsablenum());
				String auxdetail = (String) auxmap.get("auxnumdetail");
				if(null!=auxmap){
                    if(StringUtils.isNotEmpty(storageSummary.getRemark())){
                        map.put("total", CommonUtils.strDigitNumDeal(storageSummary.getRemark()));
                    }else{
                        map.put("total", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum()+storageSummary.getUnitname()+",箱数:"+auxdetail));
                    }
					map.put("totalnum", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum().toString()));
				}else{
					map.put("totalnum", 0);
					map.put("total", "0箱");
				}
			}
			if(null!=storageInfo){
				StorageSummary storageSummary1 = getStorageSummaryByStorageidAndGoodsid(storageInfo.getId(), goodsId);
				if(null!=storageSummary1){
					Map auxstoragemap = countGoodsInfoNumber(goodsId, null, storageSummary1.getUsablenum());
					String auxdetail = (String) auxstoragemap.get("auxnumdetail");
					map.put("storagenum", CommonUtils.strDigitNumDeal(storageSummary1.getUsablenum()+storageSummary.getUnitname()+",箱数:"+auxdetail));
				}else{
					map.put("totalnum", 0);
					map.put("total", "0箱");
				}
				map.put("storagename", storageInfo.getName());
			}
			addJSONObject(map);
		}
		
		return SUCCESS;
	}
	/**
	 * 获取车销仓库的库存信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Aug 14, 2013
	 */
	public String getCarStorageInfo() throws Exception{
		String goodsId = request.getParameter("id");
        String storageid = request.getParameter("storageid");
        StorageInfo storageInfo = null;
        if(StringUtils.isNotEmpty(storageid)){
            storageInfo = getStorageInfo(storageid);
        }else{
            SysUser sysUser = getSysUser();
            storageInfo = getStorageInfoByCarsaleuser(sysUser.getUserid());
        }
		Map map = new HashMap();
		if(null!=storageInfo){
			StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageInfo.getId(), goodsId);
			if(null!=storageSummary){
				Map auxstoragemap = countGoodsInfoNumber(goodsId, null, storageSummary.getUsablenum());
				String auxdetail = (String) auxstoragemap.get("auxnumdetail");
				
				map.put("totalnum", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum().toString()));
				map.put("total", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum()+storageSummary.getUnitname()+",箱数:"+auxdetail==null?"":auxdetail));
			}else{
				map.put("totalnum", 0);
				map.put("total", "0箱");
			}
			map.put("storagename", storageInfo.getName());
		}else{
			map.put("total","");
            map.put("totalnum", 0);
			map.put("storagename", "");
		}
		addJSONObject(map);
		return SUCCESS;
	}
	
	/**
	 * 在线查询价格，促销价
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 2, 2013
	 */
	public String getPriceOnline() throws Exception{
		String goodsId = request.getParameter("id"); //商品编码
		String customerId = request.getParameter("cid"); //客户编码
		String num = request.getParameter("num");
        String tradetype = request.getParameter("tradetype");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		BigDecimal unitNum = BigDecimal.ZERO;
		if(StringUtils.isNotEmpty(num)){
			unitNum = new BigDecimal(num);
		}
		Map map = new HashMap();
		IOrderService orderService = (IOrderService)SpringContextUtils.getBean("salesOrderService");
		OrderDetail orderDetail = orderService.getGoodsDetail(goodsId, customerId, date, unitNum, null);
		map.put("price", orderDetail.getTaxprice());
		map.put("fixprice", orderDetail.getFixprice());
		map.put("remark", orderDetail.getRemark());

        if("01".equals(tradetype)){
            SysUser sysUser = getSysUser();
            StorageInfo storageInfo = getStorageInfoByCarsaleuser(sysUser.getUserid());
            if(null!=storageInfo){
                StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageInfo.getId(), goodsId);
                if(null!=storageSummary){
                    Map auxstoragemap = countGoodsInfoNumber(goodsId, null, storageSummary.getUsablenum());
                    String auxdetail = (String) auxstoragemap.get("auxnumdetail");

                    map.put("totalnum", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum().toString()));
                    map.put("total", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum()+storageSummary.getUnitname()+",箱数:"+auxdetail==null?"":auxdetail));
                }else{
                    map.put("totalnum", 0);
                    map.put("total", "0箱");
                }
                map.put("storagename", storageInfo.getName());
            }else{
                map.put("total","");
                map.put("totalnum", 0);
                map.put("storagename", "");
            }
        }else{
            //获取库存信息
            StorageSummary storageSummary = getStorageSummarySumByGoodsidWithDatarule(goodsId);
            if(null!=storageSummary){
                Map auxmap = countGoodsInfoNumber(goodsId, null, storageSummary.getUsablenum());
                String auxdetail = (String) auxmap.get("auxnumdetail");
                if(null!=auxmap){
                    if(StringUtils.isNotEmpty(storageSummary.getRemark())){
                        map.put("total", CommonUtils.strDigitNumDeal(storageSummary.getRemark()));
                    }else{
                        map.put("total", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum()+storageSummary.getUnitname()+",箱数:"+auxdetail));
                    }
                    map.put("totalnum", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum().toString()));
                }
            }
        }
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 在线查询车销价格，促销价，库存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月14日
	 */
	public String getCarPriceOnline() throws Exception{
		String goodsId = request.getParameter("id"); //商品编码
		String customerId = request.getParameter("cid"); //客户编码
		String num = request.getParameter("num");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		BigDecimal unitNum = BigDecimal.ZERO;
		if(StringUtils.isNotEmpty(num)){
			unitNum = new BigDecimal(num);
		}
		Map map = new HashMap();
		IOrderService orderService = (IOrderService)SpringContextUtils.getBean("salesOrderService");
		OrderDetail orderDetail = orderService.getGoodsDetail(goodsId, customerId, date, unitNum, null);
		map.put("price", orderDetail.getTaxprice());
		map.put("fixprice", orderDetail.getFixprice());
		map.put("remark", orderDetail.getRemark()==null?"":orderDetail.getRemark());
		
		SysUser sysUser = getSysUser();
		StorageInfo storageInfo = getStorageInfoByCarsaleuser(sysUser.getUserid());
		if(null!=storageInfo){
			StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageInfo.getId(), goodsId);
			if(null!=storageSummary){
				Map auxstoragemap = countGoodsInfoNumber(goodsId, null, storageSummary.getUsablenum());
				String auxdetail = (String) auxstoragemap.get("auxnumdetail");
				map.put("totalnum", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum().toString()));
				map.put("total", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum()+storageSummary.getUnitname()+",箱数:"+auxdetail));
			}
			map.put("storagename", storageInfo.getName());
		}else{
			map.put("total","");
			map.put("storagename", "");
		}
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 获取客户商品退货价格
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月14日
	 */
	public String getRejectPrice() throws Exception{
		String goodsId = request.getParameter("id"); //商品编码
		String customerId = request.getParameter("cid"); //客户编码
		String num = request.getParameter("num");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(new Date());
		BigDecimal unitNum = BigDecimal.ZERO;
		if(StringUtils.isNotEmpty(num)){
			unitNum = new BigDecimal(num);
		}
		Map map = new HashMap();
		IOrderService orderService = (IOrderService)SpringContextUtils.getBean("salesOrderService");
		OrderDetail orderDetail = orderService.getGoodsDetail(goodsId, customerId, date, unitNum, "reject");
		map.put("price", orderDetail.getTaxprice());
		map.put("fixprice", orderDetail.getFixprice());
		map.put("remark", orderDetail.getRemark()==null?"":orderDetail.getRemark());
		
		addJSONObject(map);
		return SUCCESS;
	}
	/**
	 * 基础数据列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public String getSameFinanceList() throws Exception{
        Map rMap = new HashMap();
        //获取财务相关内容
		List<Map> list =  phoneService.getSameFinanceList();
		List fList = (List) CommonUtils.deepCopy(list);
		Map map = new HashMap();
		map.put("id", "01");
		map.put("name", "现场交易");
		map.put("type", "3");
        fList.add(map);
		Map map2 = new HashMap();
		map2.put("id", "02");
		map2.put("name", "事后送货");
		map2.put("type", "3");
        fList.add(map2);
        rMap.put("finance",fList);
        //获取仓库数据
        List<Map> sList = new ArrayList<Map>();
        List<StorageInfo> storageList = phoneService.getAllStorage();
        Map storagemap = new HashMap();
        storagemap.put("id", "defaultstorage");
        storagemap.put("name", "默认仓库");
        storagemap.put("type", "storage");
        sList.add(storagemap);
        for(StorageInfo storage: storageList){
            Map omap = new HashMap();
            omap.put("id", storage.getId());
            omap.put("name", storage.getName());
            omap.put("type", "storage");
            sList.add(omap);
        }
        rMap.put("storage",sList);
        //获取银行数据
		List<Map> bList = new ArrayList<Map>();
		List<Bank> bankList = phoneService.getAllBank();
		for(Bank bank: bankList){
			Map omap = new HashMap();
			omap.put("id", bank.getId());
			omap.put("name", bank.getName());
			omap.put("type", "bank");
			bList.add(omap);
		}
		rMap.put("bank",bList);
		//获取业务员数据
		List<Map> suList = new ArrayList<Map>();
		List<Map> salesuserList = phoneService.getSaleUser();
		for(Map salesuserMap: salesuserList){
			Map omap = new HashMap();
			omap.put("personnelid", (String)salesuserMap.get("id"));
			omap.put("name", (String)salesuserMap.get("name"));
			omap.put("deptid", (String)salesuserMap.get("deptid"));
			suList.add(omap);
		}
		rMap.put("salesuser",suList);

        addJSONObject(rMap);
		return SUCCESS;
	}

    /**
     * 判断手机客户端数据同步是否需要同步
     * @return
     * @throws Exception
     */
	public String getSyncFlag() throws Exception{
        String syncdate = request.getParameter("syncdate");
        if(StringUtils.isEmpty(syncdate)){
            syncdate = CommonUtils.getTodayDataStr();
        }
        Map returnMap = phoneService.getSyncFlag(syncdate);
        addJSONObject(returnMap);
        return SUCCESS;
    }

    /**
     * 获取用户 手机客户端需要更新的数据
     * @return
     * @throws Exception
     */
    public String getSyncBaseData() throws Exception{
        String syncdate = request.getParameter("syncdate");
        if(StringUtils.isEmpty(syncdate)){
            syncdate = CommonUtils.getTodayDataStr();
        }
        Map map = phoneService.getSyncBaseData(syncdate);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 获取商品基本信息图片列表
     * @return
     * @throws Exception
     */
    public String showGoodsImageInfoList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = phoneService.showGoodsImageInfoList(pageMap);
        Map returnMap = new HashMap();
        returnMap.put("code",200);
        returnMap.put("msg","ok");
        returnMap.put("page",pageData.getPage());
        returnMap.put("rows",pageData.getrow());
        returnMap.put("total",pageData.getTotal());
        returnMap.put("list",pageData.getList());
        addJSONObject(returnMap);
        return SUCCESS;
    }
	/**
	 * 上传要货订单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	@UserOperateLog(key="phone",type=2)
	public String uploadOrder() throws Exception{
		String json = request.getParameter("json");
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map map = phoneService.updateOrder(jsonObject);
		String customerid = jsonObject.getString("customerid");
        String billid = null;
        if(null!=map && map.containsKey("id")){
            billid = (String) map.get("id");
        }
		String msg = phoneService.uploadOrderMsg(customerid,billid);
		if(null==msg){
			msg = "";
		}else{
			String msgStr = (String) map.get("msg");
			msg = msgStr + msg;
		}
		map.put("msg",msg);
		addJSONObject(map);
		String id = jsonObject.getString("orderid");
		if(null!=map && map.containsKey("id")){
			id = (String) map.get("id");
		}
		addLog("手机上传要货申请单 编号："+id +";客户："+jsonObject.getString("customerid"), map);
		return SUCCESS;
	}
	/**
	 * 上传手机退货申请单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月11日
	 */
	@UserOperateLog(key="phone",type=2)
	public String uploadRejectOrder() throws Exception{
		String json = request.getParameter("json");
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map map = phoneService.addRejectOrder(jsonObject);
		String customerid = jsonObject.getString("customerid");
		addJSONObject(map);
		String id = jsonObject.getString("orderid");
		if(null!=map && map.containsKey("id")){
			id = (String) map.get("id");
		}
		addLog("手机上传退货申请单 编号："+id +";客户："+jsonObject.getString("customerid"), map);
		return SUCCESS;
	}

    /**
     * 验证退货申是否有销售过
     * @return
     * @throws Exception
     */
	public String checkRejectOrder() throws Exception{
        String json = request.getParameter("json");
        JSONObject jsonObject = JSONObject.fromObject(json);
        Map map = phoneService.checkRejectOrder(jsonObject);
        addJSONObject(map);
	    return SUCCESS;
    }
	/**
	 * 上传车销订单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月25日
	 */
	@UserOperateLog(key="phone",type=2)
	public String uploadOrderCar() throws Exception{
		String json = request.getParameter("json");
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map map = phoneService.updateOrderCar(jsonObject);
		String customerid = jsonObject.getString("customerid");
		String msg = phoneService.uploadOrderMsg(customerid,null);
		if(null==msg){
			msg = "";
		}else{
			String msgStr = (String) map.get("msg");
			msg = msgStr + msg;
		}
		map.put("msg",msg);
		addJSONObject(map);
		String id = jsonObject.getString("orderid");
		if(null!=map && map.containsKey("id")){
			id = (String) map.get("id");
		}
		addLog("手机上传车销单 编号："+id +";客户："+jsonObject.getString("customerid")+"."+msg, map);
		return "success";
	}
	/**
	 * 上传车销退货单
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月13日
	 */
	@UserOperateLog(key="phone",type=2)
	public String uploadCarRejectOrder() throws Exception{
		String json = request.getParameter("json");
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map map = phoneService.addCarRejectOrder(jsonObject);
		String customerid = jsonObject.getString("customerid");
		addJSONObject(map);
		String id = jsonObject.getString("orderid");
		if(null!=map && map.containsKey("id")){
			id = (String) map.get("id");
		}
		addLog("手机上传车销退货单 编号："+id +";客户："+jsonObject.getString("customerid"), map);
		return "success";
	}
	/**
	 * 上传位置
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Aug 27, 2013
	 */
	public String uploadLocation() throws Exception{
//		String userid = request.getParameter("userid");
//		String x = request.getParameter("x");
//		String y = request.getParameter("y");
//		Location location = new Location();
//		location.setUserid(userid);
//		location.setX(x);
//		location.setY(y);
		
		String locationStr = request.getParameter("location");
		boolean flag = false;
		if(StringUtils.isNotEmpty(locationStr)){
			JSONArray jsonArray = JSONArray.fromObject(locationStr);
            if(jsonArray.size()<5000){
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if(null!=jsonObject && jsonObject.has("userid")){
                        String userid =jsonObject.getString("userid");
                        String x = jsonObject.getString("x");
                        String y = jsonObject.getString("y");
                        String time = jsonObject.getString("time");

                        Location location = new Location();
                        location.setUserid(userid);
                        location.setX(x);
                        location.setY(y);
                        location.setUpdatetime(CommonUtils.stringToDate(time, "yyyyMMddHHmmss"));
                        flag = phoneService.addLocation(location);
                    }
                }
            }else{
                flag = true;
            }
		}
		
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 获取位置列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public String getLocationList() throws Exception{
		addJSONArray(phoneService.getLocationList());
		return SUCCESS;
	}
	
	/**
	 * 获取位置历史列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public String getLocationHistoryList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        List list = phoneService.getLocationHistoryList(map);
        addJSONArray(list);
        return SUCCESS;
    }

    /**
     * 获取位置历史列表
     * @return
     * @throws Exception
     * @author lin_xx
     * @date June 13, 2016
     */
    public String getNewLocationByInfo() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        List list = phoneService.getNewLocationByInfo(map);
        addJSONArray(list);
        return SUCCESS;
    }

	/**
	 * 客户产品列表
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public String getCustomerGoods() throws Exception{
        PageData pageData = phoneService.getCustomerGoodsList(pageMap);
        addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 扫描系统上传退货单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 7, 2014
	 */
	@UserOperateLog(key="phone",type=2)
	public String uploadReject() throws Exception{
		String json = request.getParameter("json");
		String source = request.getParameter("source");
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map map = phoneService.uploadReject(jsonObject);
		boolean flag = false;
		String ids = "";
		if(null!=map && map.containsKey("ids")){
			flag = (Boolean) map.get("flag");
			ids = (String) map.get("ids");
		}
		if(map.containsKey("isupload")){

			String msg = (String) map.get("msg");
            if("1".equals(source)){
                addJSONObject(map);
                addLog("手机上传退货通知单 重复上传，"+msg, flag);
            }else{
                addJSONObject("flag", true);
                addLog("扫描枪上传退货通知单 重复上传，"+msg, flag);
            }
		}else{
			String customerid = jsonObject.getString("customerid");
			addJSONObject("flag", flag);
            if("1".equals(source)) {
                addJSONObject(map);
                addLog("手机上传退货通知单  编号:" + ids + ";客户编号:" + customerid, flag);
            }else{
                addJSONObject("flag", true);
                addLog("扫描枪上传退货通知单  编号:" + ids + ";客户编号:" + customerid, flag);
            }
		}
		return SUCCESS;
	}
	
	/**
	 * 盘点单列表（扫描枪）
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public String getCheckListUnfinish() throws Exception{
        Map qmap = CommonUtils.changeMap(request.getParameterMap());
		List<CheckList> list = phoneService.getCheckListUnfinish(qmap);
		List<Map<String, String>> mapList = new ArrayList<Map<String,String>>();
		for(CheckList check: list){
			Map<String, String> map = new HashMap<String, String>();
			map.put("checkid", check.getId());
			map.put("checkuser", check.getCheckuserid());
			map.put("storageid", check.getStorageid());
			map.put("storagename", check.getStoragename());
			map.put("businessdate", check.getBusinessdate());
			mapList.add(map);
		}
		addJSONArray(mapList);
		return SUCCESS;
	}
	
	/**
	 * 盘点单明细
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	public String getCheckListDetail() throws Exception{
		String id = request.getParameter("id");
		addJSONArray(phoneService.getCheckListDetail(id));
		return SUCCESS;
	}
	
	/**
	 * 更新盘点单
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Mar 11, 2014
	 */
	@UserOperateLog(key="phone",type=2)
	public String updateCheckList() throws Exception{
		String json = request.getParameter("json");
		JSONObject jsonObject = JSONObject.fromObject(json);
		boolean flag = phoneService.updateCheckList(jsonObject);
		addJSONObject("flag", flag);
		String checkId = jsonObject.getString("checkid");
		addLog("扫描枪盘点 盘点单:"+checkId, flag);
		return SUCCESS;
	}
	
	/**
	 * 添加反馈信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Dec 31, 2013
	 */
	public String addFeed() throws Exception{
		Map map = new HashMap();
		String detail = request.getParameter("detail");
		String userId = request.getParameter("userid");
		map.put("detail", detail);
		map.put("userid", userId);
		boolean flag = phoneService.addFeed(map);
		addJSONObject("flag", flag);
		return SUCCESS;
	}
	
	/**
	 * 添加业务员行程信息
	 * @return
	 * @throws Exception
	 * @author zhengziyong 
	 * @date Feb 11, 2014
	 */
	public String addRouteDistance() throws Exception{
		String userid = request.getParameter("u");
		String d = request.getParameter("d");
		String date = request.getParameter("t");
		int dis = 0;
		if(StringUtils.isNotEmpty(d)){
			dis = Integer.valueOf(d);
		}
		if(StringUtils.isEmpty(date)){
			date = CommonUtils.getTodayDataStr();
		}
		RouteDistance distance = new RouteDistance();
		distance.setUserid(userid);
		distance.setDistance(dis);
		distance.setAdddate(date); 
		//distance.setAdddate(dateFormat.format(new Date()));
		addJSONObject("flag", phoneService.addRouteDistance(distance));
		return SUCCESS;
	}
	/**
	 * 发货单列表（扫描枪）
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月27日
	 */
	public String getSaleoutUnCheckList() throws Exception{
		String storageId = request.getParameter("id");
		String date = request.getParameter("date");
		if(StringUtils.isEmpty(date)){
			date = CommonUtils.getTodayDataStr();
		}
		List list = phoneService.getSaleoutUnCheckList(storageId,date);
		addJSONArray(list);
		return "success";
	}

    /**
     * 扫描枪获取发货单列表
     * @return
     * @throws Exception
     */
    public String getSaleoutForScanList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        List list = phoneService.getSaleoutForScanList(map);
        addJSONArray(list);
        return SUCCESS;
    }

    /**
     * 根据发货单编号获取发货单相关信息
     * @return
     * @throws Exception
     */
    public String getSaleoutInfoForScanByID() throws Exception{
        String id = request.getParameter("id");
        Saleout saleout = phoneService.getSaleoutInfoByID(id);
        Map map = new HashedMap();
        boolean flag = false;
        String msg = "";
        if(null!=saleout ){
            if("2".equals(saleout.getStatus())){
                flag = true;
                msg = "成功";
                map.put("id",saleout.getId());
                map.put("orderid",saleout.getSaleorderid());
                map.put("customerid",saleout.getCustomerid());
                map.put("customername",saleout.getCustomername());
                map.put("storageid",saleout.getStorageid());
                map.put("storagename",saleout.getStoragename());
                map.put("businessdate",saleout.getBusinessdate());
            }else{
                msg = "发货单已审核或者关闭";
            }
        }else{
            msg = "未找到发货单："+id;
        }
        map.put("flag",flag);
        map.put("msg",msg);
        addJSONObject(map);
        return SUCCESS;
    }
	/**
	 * 获取发货单明细数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月30日
	 */
    @UserOperateLog(key="phone",type=2)
	public String getSaleoutDetail() throws Exception{
		String id = request.getParameter("id");
		List list = phoneService.getSaleoutDetail(id);
		addJSONArray(list);
        if(list.size()>0){
            Map map = phoneService.updateSaleoutCheckFlagByScan(id);
            addLog("扫描枪发货单同步："+id,map);
        }else{
            addLog("扫描枪发货单同步："+id,false);
        }
		return "success";
	}
	/**
	 * 更新发货单核销确认状态
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年6月30日
	 */
	@UserOperateLog(key="phone",type=2)
	public String updateSaleoutCheckFlag() throws Exception{
		String id = request.getParameter("id");
		Map map = phoneService.updateSaleoutCheckFlag(id);
		addJSONObject(map);
		addLog("扫描枪核对发货单："+id,map);
		return "success";
	}

    /**
     * 更新发货单数量并且审核通过
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="phone",type=2)
    public String updateSaleoutAndAudit() throws Exception{
        String id = request.getParameter("id");
        String json = request.getParameter("json");
        Map map = phoneService.updateSaleoutAndAudit(json);
        addJSONObject(map);
        addLog("扫描枪发货单上传："+id,map);
        return SUCCESS;
    }
	/**
	 * 获取客户商品历史销售数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2014年10月5日
	 */
	public String getCustomerHisGoodsSalesList() throws Exception{
		String customerid = request.getParameter("customerid");
		String goodsid = request.getParameter("goodsid");
		List list = phoneService.getCustomerHisGoodsSalesList(customerid, goodsid);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 根据查询条件 获取商品列表和商品价格等信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年8月31日
	 */
	public String searchCustomerGoodsList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
		//0不显示买赠捆绑 1显示买赠捆绑
		String ispromotion = request.getParameter("ispromotion");
		if(StringUtils.isEmpty(ispromotion)){
			ispromotion = "0";
            map.put("ispromotion",ispromotion);
		}
        pageMap.setCondition(map);
        PageData pageData = phoneService.searchCustomerGoodsList(pageMap);
        addJSONObject(pageData);
		return "success";
	}
	/**
	 * 根据客户编号和商品编号 获取商品价格等下
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年9月1日
	 */
	public String getCustomerGoodsInfo() throws Exception{
		String customerid = request.getParameter("customerid");
		String goodsid = request.getParameter("goodsid");
		String type = request.getParameter("type");
        String tradetype = request.getParameter("tradetype");
		Map map = phoneService.getCustomerGoodsInfo(customerid, goodsid,type);
		if(!"reject".equals(type)){
            if("01".equals(tradetype)){
                SysUser sysUser = getSysUser();
                StorageInfo storageInfo = getStorageInfoByCarsaleuser(sysUser.getUserid());
                if(null!=storageInfo){
                    StorageSummary storageSummary = getStorageSummaryByStorageidAndGoodsid(storageInfo.getId(), goodsid);
                    if(null!=storageSummary){
                        Map auxstoragemap = countGoodsInfoNumber(goodsid, null, storageSummary.getUsablenum());
                        String auxdetail = (String) auxstoragemap.get("auxnumdetail");

                        map.put("totalnum", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum().toString()));
                        map.put("total", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum()+storageSummary.getUnitname()+",箱数:"+auxdetail==null?"":auxdetail));
                    }else{
                        map.put("totalnum", 0);
                        map.put("total", "0箱");
                    }
                    map.put("storagename", storageInfo.getName());
                }else{
                    map.put("total","");
                    map.put("totalnum", 0);
                    map.put("storagename", "");
                }
            }else{
                //获取库存信息
                StorageSummary storageSummary = getStorageSummarySumByGoodsidWithDatarule(goodsid);
                if(null!=storageSummary){
                    Map auxmap = countGoodsInfoNumber(goodsid, null, storageSummary.getUsablenum());
                    String auxdetail = (String) auxmap.get("auxnumdetail");
                    if(null!=auxmap){
                        if(StringUtils.isNotEmpty(storageSummary.getRemark())){
                            map.put("total", CommonUtils.strDigitNumDeal(storageSummary.getRemark()));
                        }else{
                            map.put("total", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum()+storageSummary.getUnitname()+",箱数:"+auxdetail));
                        }
                        map.put("totalnum", CommonUtils.strDigitNumDeal(storageSummary.getUsablenum().toString()));
                    }
                }
            }
		}
		
		addJSONObject(map);
		return "success";
	}
	/**
	 * 获取捆绑信息规则
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年9月2日
	 */
	public String getPromotionBundData() throws Exception{
		String id = request.getParameter("id");
		String customerid = request.getParameter("customerid");
		Map map = phoneService.getPromotionBundData(id, customerid);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 获取客户列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年9月10日
	 */
	public String searchCustomerList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
		String con = StringEscapeUtils.escapeSql(request.getParameter("con"));
        map.put("con",con);
        pageMap.setCondition(map);
        PageData pageData = phoneService.searchCustomerList(pageMap);
        addJSONObject(pageData);
		return "success";
	}

    /**
     * 获取订单列表数据
     * @return
     * @throws Exception
     */
	public String searchOrderList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        String con = StringEscapeUtils.escapeSql(request.getParameter("con"));
        map.put("con",con);
        map.put("isClose","1");
        pageMap.setCondition(map);
        PageData pageData = phoneService.searchOrderList(pageMap);
        addJSONObject(pageData);
        return "success";
    }

    /**
     * 根据订单编号 获取订单的明细数据
     * @return
     * @throws Exception
     */
    public String getOrderDetail() throws Exception{
        String orderid = request.getParameter("id");
        List list = phoneService.getOrderDetail(orderid);
        if(null!=list){
            addJSONArray(list);
        }else{
            addJSONArray(new ArrayList());
        }
	    return "success";
    }
}

