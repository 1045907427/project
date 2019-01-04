/**
 * @(#)largeSingleAction.java
 *
 * @author panxiaoxiao
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Sep 11, 2014 panxiaoxiao 创建版本
 */
package com.hd.agent.storage.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.BillGoodsNumDecimalLenUtils;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.storage.model.BigSaleOut;
import com.hd.agent.storage.model.BigSaleOutDetail;
import com.hd.agent.storage.model.SaleoutDetail;
import com.hd.agent.storage.service.IBigSaleOutService;
import com.hd.agent.storage.service.IStorageSaleOutPrintService;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 大单发货 action
 * @author panxiaoxiao
 */
public class BigSaleOutAction extends BaseFilesAction{
	
	private IBigSaleOutService bigSaleOutService;
	
	private BigSaleOut bigSaleOut;
	
	public BigSaleOut getBigSaleOut() {
		return bigSaleOut;
	}

	/**
	 * 打印
	 */
	private IStorageSaleOutPrintService storageSaleOutPrintService;
	
	public IStorageSaleOutPrintService getStorageSaleOutPrintService() {
		return storageSaleOutPrintService;
	}

	public void setStorageSaleOutPrintService(
			IStorageSaleOutPrintService storageSaleOutPrintService) {
		this.storageSaleOutPrintService = storageSaleOutPrintService;
	}
	
	public void setBigSaleOut(BigSaleOut bigSaleOut) {
		this.bigSaleOut = bigSaleOut;
	}


	public IBigSaleOutService getBigSaleOutService() {
		return bigSaleOutService;
	}


	public void setBigSaleOutService(IBigSaleOutService bigSaleOutService) {
		this.bigSaleOutService = bigSaleOutService;
	}
	
	/**
	 * 显示大单发货单列表页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public String showBigSaleOutListPage()throws Exception{
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		return SUCCESS;
	}
	
	/**
	 * 获取大单发货单列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public String getBigSaleOutList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = bigSaleOutService.getBigSaleOutList(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}
	
	/**
	 * 显示大单发货单页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public String showBigSaleOutPage()throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		request.setAttribute("id", id);
		request.setAttribute("type", type);
		return SUCCESS;
	}
	
	/**
	 * 显示大单发货单新增页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 11, 2014
	 */
	public String showBigSaleOutAddPage()throws Exception{
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		return SUCCESS;
	}
	
	/**
	 * 获取发货单列表数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public String getSaleOutListForBigSaleOut()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		if(map.containsKey("addsaleoutids") && null != map.get("addsaleoutids")){
			String addsaleoutids = (String)map.get("addsaleoutids");
			map.put("array", addsaleoutids.split(","));
		}
		pageMap.setCondition(map);
		PageData pageData = bigSaleOutService.getSaleOutListForBigSaleOut(pageMap);
		addJSONObject(pageData);
		return SUCCESS;
	}

	/**
	 * 新增大单发货单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	@UserOperateLog(key="bigSaleOut",type=2)
	public String addBigSaleOut()throws Exception{
		String ids = request.getParameter("ids");
		String storageid = request.getParameter("storageid");
		Map map = bigSaleOutService.addBigSaleOut(ids,storageid);
		addJSONObject(map);
		addLog("大单发货单新增 编号："+(String)map.get("id"), map.get("flag").equals(true));
		return SUCCESS;
	}
	
	/**
	 * 显示大单发货单修改页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 12, 2014
	 */
	public String showBigSaleOutEditPage()throws Exception{
		String id = request.getParameter("id");
		BigSaleOut bigSaleOut = bigSaleOutService.getBigSaleOutInfo(id);
		request.setAttribute("bigSaleOut", bigSaleOut);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		return SUCCESS;
	}
	
	/**
	 * 获取大单发货单商品明细列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 13, 2014
	 */
	public String showBigSaleOutGoodsList()throws Exception{
		String ids = request.getParameter("ids");
		String initpage = request.getParameter("initpage");
		Map map = CommonUtils.changeMap(request.getParameterMap());
		 pageMap.setCondition(map);
		if("true".equals(initpage)){
		   PageData pageData = bigSaleOutService.getBigSaleOutGoodsList(pageMap);
		   addJSONObjectWithFooter(pageData);
		}
		else{
		   PageData	pageData = bigSaleOutService.getUpdateBigSaleOutSourceBillList(pageMap,ids);
		   addJSONObjectWithFooter(pageData);
		}
		return SUCCESS;
	}
	
	/**
	 * 获取大单发货单单据明细列表
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 13, 2014
	 */
	public String showBigSaleOutSourceBillList()throws Exception{
		String id = request.getParameter("id");
		List list = bigSaleOutService.getBigSaleOutSourceBillList(id);
		addJSONArray(list);
		return SUCCESS;
	}
	
	/**
	 * 显示大单发货单查看页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 13, 2014
	 */
	public String showBigSaleOutViewPage()throws Exception{
		String id = request.getParameter("id");
		BigSaleOut bigSaleOut = bigSaleOutService.getBigSaleOutInfo(id);
		request.setAttribute("bigSaleOut", bigSaleOut);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		//仓库列表
		List storageList = getStorageInfoAllList();
		request.setAttribute("storageList", storageList);
		return SUCCESS;
	}
	
	/**
	 * 审核大单发货单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 13, 2014
	 */
	@UserOperateLog(key="bigSaleOut",type=3)
	public String auditBigSaleOuts()throws Exception{
		String ids = request.getParameter("ids");
		Map map = bigSaleOutService.auditBigSaleOuts(ids,bigSaleOut);
		addJSONObject(map);
		addLog("大单发货单审核 编号："+ids, true);
		return SUCCESS;
	}

	/**
	 * 反审大单发货单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 14, 2014
	 */
	@UserOperateLog(key="bigSaleOut",type=3)
	public String oppauditBigSaleOut()throws Exception{
		String id = request.getParameter("id");
		Map map = bigSaleOutService.oppauditBigSaleOut(id);
		addJSONObject(map);
		addLog("大单发货单反审 编号："+id, map.get("flag").equals(true));
		return SUCCESS;
	}

	/**
	 * 确认发货
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date Sep 20, 2014
	 */
	@UserOperateLog(key="bigSaleOut",type=0)
	public String doSaleoutBigSaleOuts()throws Exception{
		String ids = request.getParameter("ids");
		Map map = bigSaleOutService.doSaleoutBigSaleOuts(ids);
		addJSONObject(map);
		addLog("大单发货单确认发货 编号："+ids+",来源单据"+(String)map.get("msg"), true);
		return SUCCESS;
	}

	/**
	 * 取消确认发货
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2015-09-14
	 */
	@UserOperateLog(key="bigSaleOut",type=0)
	public String cancelDoSaleoutBigSaleOuts()throws Exception{
		String ids = request.getParameter("ids");
		Map map = bigSaleOutService.cancelDoSaleoutBigSaleOuts(ids);
		addJSONObject(map);
		addLog("大单发货单取消确认发货 编号："+ids, map.get("flag").equals(true));
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 14, 2014
	 */
	@UserOperateLog(key="bigSaleOut",type=4)
	public String deleteBigSaleOuts()throws Exception{
		String ids = request.getParameter("ids");
		Map map = bigSaleOutService.deleteBigSaleOuts(ids);
		addJSONObject(map);
		String msg = map.get("msg") != null ? (String)map.get("msg") : "";
		if(StringUtils.isNotEmpty(msg)){
			addLog((String)map.get("msg"), true);
		}else{
			addLog("大单发货单删除 编号："+ids, false);
		}
		return SUCCESS;
	}
	
	/**
	 * 大单发货发货单添加页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 19, 2014
	 */
	public String showBigSaleOutAddSaleoutPage()throws Exception{
		String storageid = request.getParameter("storageid");
		request.setAttribute("storageid", storageid);
		return SUCCESS;
	}
	
	/**
	 * 保存大单发货单
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 20, 2014
	 */
	@UserOperateLog(key="bigSaleOut",type=3)
	public String saveBigSaleOut()throws Exception{
		String billid = request.getParameter("billid");
		String editsaleoutid = request.getParameter("editsaleoutid");
		String remark = request.getParameter("remark");
		String isdel = request.getParameter("isdel");
		boolean flag = bigSaleOutService.saveBigSaleOut(billid,editsaleoutid,remark,isdel);
		addJSONObject("flag", flag);
		addLog("大单发货单修改 编号："+billid, flag);
		return SUCCESS;
	}

	/**
	 * 获取大单发货打印页面相同要显示的参数
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-09-26
	 */
	private void getBigSaleOutCommonPrint(String id)throws Exception{
		BigSaleOut bigSaleOut = bigSaleOutService.getBigSaleOutInfo(id);
		if(null != bigSaleOut){
			StorageInfo storageInfo = getStorageInfo(bigSaleOut.getStorageid());
			if(null != storageInfo){
				Personnel personnel = getPersonnelInfoById(storageInfo.getManageruserid());
				if(null != personnel){
					request.setAttribute("P_StorageManager", personnel.getName());
				}
			}
			request.setAttribute("remark", bigSaleOut.getRemark());
		}
		request.setAttribute("id", id);
		request.setAttribute("pattern",CommonUtils.getFormatNumberType());
		List<BigSaleOutDetail> sourceList = bigSaleOutService.getBigSaleOutSourceBillList(id);
		request.setAttribute("billnum", sourceList.size());
	}

	/**
	 * 整件分拣预览
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-09-26
	 */
	public String showBigSaleOutTotalGoodsPrintViewPage()throws Exception{
		String id = request.getParameter("id");
        String zjpsize=request.getParameter("zjpsize");
        Integer zjpagesize=15;
        if(null!=zjpsize && StringUtils.isNumeric(zjpsize)){
            zjpagesize=Integer.parseInt(zjpsize);
        }
        request.setAttribute("zjpagesize",zjpagesize);
        //是否显示拣单
        String showFJBill=request.getParameter("showfjbill");
        if(!"0".equals(showFJBill) && !"false".equals(showFJBill)){
            showFJBill="1";
        }
        request.setAttribute("showFJBill",showFJBill);
        getBigSaleOutCommonPrint(id);
        Map map = new HashMap();
        map.put("id", id);
        Map retMap = null;//storageSaleOutPrintService.getBigSaleOutGoodsListForPrint(map);
        List<SaleoutDetail> goodsList = (List<SaleoutDetail>)retMap.get("goodsList");
        if(null != goodsList && goodsList.size() != 0){
            request.setAttribute("goodsList", goodsList);
        }
			Map billmap = (Map)retMap.get("billmap");
		if(!billmap.isEmpty()){
			request.setAttribute("billmap", billmap);
		}
		return SUCCESS;
	}

	/**
	 * 按商品分客户数预览
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-09-26
	 */
	public String showBigSaleOutGoodsCustomerPrintViewPage()throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		getBigSaleOutCommonPrint(id);
		Map map = new HashMap();
		map.put("id", id);
		List<SaleoutDetail> goodsCustomerList = null ;//storageSaleOutPrintService.getBigSaleOutGoodsCustomerListForPrint(map);
		if(null != goodsCustomerList && goodsCustomerList.size() != 0){
			request.setAttribute("goodsCustomerList", goodsCustomerList);
		}
		if(StringUtils.isNotEmpty(type) && type.equals("nomodel")){
			return "nomodelsuccess";
		}else{
			return SUCCESS;
		}
	}

	/**
	 * 按商品分客户区块预览
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-09-26
	 */
	public String showBigSaleOutGoodsCustomerDivPrintViewPage()throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		getBigSaleOutCommonPrint(id);
		Map map = new HashMap();
		map.put("id", id);
		Map<String,List<SaleoutDetail>> customerDivMap = null; //storageSaleOutPrintService.getBigOutGoodsCustomerDivMapForPrint(map);
		if(null != customerDivMap && !customerDivMap.isEmpty()){
			request.setAttribute("customerDivMap", customerDivMap);
		}
		if(StringUtils.isNotEmpty(type) && type.equals("nomodel")){
			return "nomodelsuccess";
		}else{
			return SUCCESS;
		}
	}

	/**
	 * 按品牌分商品预览
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao
	 * @date 2016-09-26
	 */
	public String showBigSaleOutBrandPrintViewPage()throws Exception{
		String id = request.getParameter("id");
		getBigSaleOutCommonPrint(id);
		Map map = new HashMap();
		map.put("id", id);
		Map<SaleoutDetail,List<SaleoutDetail>> brandDivMap = null;// storageSaleOutPrintService.getBigOutBrandGoodsDivMapForPrint(map);
		if(null != brandDivMap && !brandDivMap.isEmpty()){
			request.setAttribute("brandDivMap", brandDivMap);
		}
		return SUCCESS;
	}

	/**
	 * 更新大单发货单打印数量同步更新发货单打印数量
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 28, 2014
	 */
	public String updateBigSaleoutPrintNum()throws Exception{
		String id = request.getParameter("id");
		storageSaleOutPrintService.updatePrinttimesSyncUpBigSalout(id);
		return null;
	}
	
	/**
	 * 显示客户对应商品数页面
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 23, 2014
	 */
	public String showBigSaleOutCustomerGoodsNumPage()throws Exception{
		String goodsid = request.getParameter("goodsid");
		String bigsaleoutid = request.getParameter("bigsaleoutid");
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("bigsaleoutid", bigsaleoutid);
		return SUCCESS;
	}
	
	/**
	 * 获取按商品获取分客户对应该商品数量的数据
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Oct 23, 2014
	 */
	public String getBigSaleOutCustomerGoodsNumList()throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = bigSaleOutService.getBigSaleOutCustomerGoodsNumList(pageMap);
		addJSONObjectWithFooter(pageData);
		return SUCCESS;
	}
}

