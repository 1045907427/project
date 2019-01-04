/**
 * @(#)StorageSummaryAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * May 15, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.storage.model.StorageSummary;
import com.hd.agent.storage.model.StorageSummaryBatch;
import com.hd.agent.storage.service.IStorageSummaryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 
 * 库存现存量action
 * @author chenwei
 */
public class StorageSummaryAction extends BaseFilesAction {
	
	private IStorageSummaryService storageSummaryService;

	public IStorageSummaryService getStorageSummaryService() {
		return storageSummaryService;
	}

	public void setStorageSummaryService(
			IStorageSummaryService storageSummaryService) {
		this.storageSummaryService = storageSummaryService;
	}
	/**
	 * 显示现存量查询页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 15, 2013
	 */
	public String showStorageSummaryPage() throws Exception{
		//根据表名从数据字典中获取自定义描述 
		request.setAttribute("fieldmap", getRowDescFromDataDict("t_base_goods_info"));
		// 购销类型
		List bstypeList = super.getBaseSysCodeService().showSysCodeListByType("bstype");
		request.setAttribute("bstypeList", bstypeList);
		return "success";
	}
	/**
	 * 获取商品现存量数据列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 16, 2013
	 */
	public String showStorageSummaryList() throws Exception{
		//获取页面传过来的参数 封装到map里面
        Map map = CommonUtils.changeMap(request.getParameterMap());
        //map.put("istotalcontrol", "1");
        //map赋值到pageMap中作为查询条件
        pageMap.setCondition(map);
        pageMap.setCols("id");
        PageData pageData = storageSummaryService.showStorageSummarySumList(pageMap);
        addJSONObjectWithFooter(pageData);
        return "summary";
	}
	/**
	 * 显示分仓库库存总量查询页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 28, 2013
	 */
	public String showStorageSummaryPageByStorage() throws Exception{
		//根据表名从数据字典中获取自定义描述 
		request.setAttribute("fieldmap", getRowDescFromDataDict("t_base_goods_info"));
		return "success";
	}
	/**
	 * 显示分仓库库存总量数据列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 28, 2013
	 */
	public String showStorageSummaryByStorageList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = storageSummaryService.showStorageSummaryByStorageList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示仓库中商品个批次的明细情况
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date 2015年10月29日
	 */
	public String showStorageSummaryBatchList() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String storageid = request.getParameter("storageid");
		String showZero=request.getParameter("existingnum");
		List list = storageSummaryService.showStorageSummaryBatchList(storageid, goodsid,showZero);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 显示商品库存追踪日志页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 30, 2013
	 */
	public String showStorageSummaryLogPage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		String goodsid = request.getParameter("goodsid");
		String storageid = request.getParameter("storageid");
		String storagelocationid = request.getParameter("storagelocationid");
		String batchno = request.getParameter("batchno");
		
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("storageid", storageid);
		request.setAttribute("storagelocationid", storagelocationid);
		request.setAttribute("batchno", batchno);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 显示商品库存追踪日志页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 30, 2013
	 */
	public String showStorageSummaryLogPageByStorage() throws Exception{
		String today = CommonUtils.getTodayDataStr();
		String firstDay = CommonUtils.getMonthDateStr();
		String goodsid = request.getParameter("goodsid");
		String storageid = request.getParameter("storageid");
		String storagelocationid = request.getParameter("storagelocationid");
		String batchno = request.getParameter("batchno");
		
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("storageid", storageid);
		request.setAttribute("storagelocationid", storagelocationid);
		request.setAttribute("batchno", batchno);
		request.setAttribute("firstDay", firstDay);
		request.setAttribute("today", today);
		return "success";
	}
	/**
	 * 获取库存汇总数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 30, 2013
	 */
	public String showStorageSummaryLogList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = storageSummaryService.showStorageSummaryLogList(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 通过仓库获取库存汇总数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date May 30, 2013
	 */
	public String showStorageSummaryLogListByStorage() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = storageSummaryService.showStorageSummaryLogListByStorage(pageMap);
		addJSONObjectWithFooter(pageData);
		return "success";
	}
	/**
	 * 根据仓库编码和商品编码 获取该仓库下的商品批次列表
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 20, 2013
	 */
	public String getStorageBatchListByStorageidAndGoodsid() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String storageid = request.getParameter("storageid");
		List list = storageSummaryService.getStorageBatchListByStorageidAndGoodsid(storageid, goodsid);
		addJSONArray(list);
		return "success";
	}
	/**
	 * 根据批次现存量编号 获取批次现存量详细信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public String getStorageSummaryBatchInfo() throws Exception{
		String id = request.getParameter("summarybatchid");
		StorageSummaryBatch storageSummaryBatch = storageSummaryService.getStorageSummaryBatchInfo(id);
		addJSONObject("storageSummaryBatch", storageSummaryBatch);
		return "success";
	}
	/**
	 * 根据商品编号获取库存中商品的总量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	public String getStorageSummarySumByGoodsid() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String storageid = request.getParameter("storageid");
		String summarybatchid = request.getParameter("summarybatchid");
		StorageSummary storageSummary = new StorageSummary();
		if(StringUtils.isNotEmpty(summarybatchid)){
			StorageSummaryBatch storageSummaryBatch = storageSummaryService.getStorageSummaryBatchInfo(summarybatchid);
			if(null!=storageSummaryBatch){
				storageSummary.setUsablenum(storageSummaryBatch.getUsablenum());
				storageSummary.setOutuseablenum(storageSummaryBatch.getUsablenum());
				storageSummary.setUnitname(storageSummaryBatch.getUnitname());
			}
		}else if(StringUtils.isNotEmpty(storageid)){
			storageSummary = storageSummaryService.getStorageSummaryByStorageAndGoods(storageid, goodsid);
		}else{
			storageSummary = storageSummaryService.getStorageSummarySumByGoodsid(goodsid);
		}
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		Map map = new HashMap();
		if(null!=goodsInfo){
			map.put("isbatch", goodsInfo.getIsbatch());
		}
		map.put("storageSummary", storageSummary);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 根据仓库编号和商品编号获取该仓库下的商品数量
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 25, 2013
	 */
	public String getStorageSummaryByStorageidAndGoodsid() throws Exception{
		String goodsid = request.getParameter("goodsid");
		String storageid = request.getParameter("storageid");
		StorageSummary storageSummary = storageSummaryService.getStorageSummaryByStorageAndGoods(storageid, goodsid);
		addJSONObject("storageSummary",storageSummary);
		return "success";
	}
	/**
	 * 显示仓库拥有的商品列表选择页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 7, 2013
	 */
	public String showStorageGoodsSelectPage() throws Exception{
		String id = request.getParameter("id");
		String divid = request.getParameter("divid");
		String paramRule = request.getParameter("paramRule");
		String dialog = request.getParameter("dialog");
		request.setAttribute("paramRule", paramRule);
		request.setAttribute("divid", divid);
		request.setAttribute("id", id);
		request.setAttribute("dialog", dialog);
		//字段权限
		Map fieldMap = getAccessColumn("t_storage_summary");
		request.setAttribute("fieldMap", fieldMap);
		return "success";
	}
	/**
	 * 根据id值获取仓库下的商品列表
	 * id查询商品编码 条形码 商品助记码
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 2, 2013
	 */
	public String getStorageGoodsSelectListData() throws Exception{
		Map map = CommonUtils.changeMap(request.getParameterMap());
		pageMap.setCondition(map);
		PageData pageData = storageSummaryService.getStorageGoodsSelectListData(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	
	/**
	 * 全局导出 库存明细表
	 * @throws Exception
	 * @author lin_xx
	 * @date June 30, 2016
	 */
	public void exportSummaryByStorageData()throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            String name = v.getClass().toString();
            if(null != v && name.contains("String")){
                queryMap.put(k.toString(), (String) v);
            }else if(null != v && name.contains("JSONArray")){
                queryMap.put(k.toString(),"storageid,goodsid");
            }
        }
        pageMap.setCondition(queryMap);
        pageMap.setCols("id");
		PageData pageData = storageSummaryService.showStorageSummarySumList(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
	}

    /**
     * 库存明细表 明细导出
     * @throws Exception
     * @author lin_xx
     * @date July 22, 2016
     */
    public void exportSummaryByStorageDetailData() throws Exception {

        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        String groupcols = (String) map.get("groupcols");
        String storageid = null;
        if(groupcols.contains(",")){
            groupcols = "goodsid";//小计列勾选仓库时 导出忽略按仓库统计
        }
        map.put("groupcols",groupcols);
        map.put("isflag", "true");
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
        pageMap.setCols("");
        PageData pageData = storageSummaryService.showStorageSummarySumList(pageMap);
        List<Map<String, Object>> list = pageData.getList();
        List<Map<String, Object>> exportList = new ArrayList<Map<String, Object>>();
        for(Map<String, Object> data : list ){
            Map m = new HashMap();
            m.put("basesaleprice",data.get("basesaleprice"));
			m.put("basesaleamount",data.get("basesaleamount"));
            m.put("suppliername",data.get("suppliername"));
            m.put("spell",data.get("spell"));
            m.put("brandname",data.get("brandname"));
            m.put("waresclassname",data.get("waresclassname"));
            m.put("model",data.get("model"));
            m.put("unitname",data.get("unitname"));
            m.put("price",data.get("price"));
            // m.put("auxwaitdetail",data.get("auxwaitdetail"));
            //m.put("auxallotwaitdetail",data.get("auxallotwaitdetail"));
            // m.put("auxallotenterdetail",data.get("auxallotenterdetail"));
            m.put("projectedusablenum",data.get("projectedusablenum"));
            m.put("auxprojectedusabledetail",data.get("auxprojectedusabledetail"));
            m.put("safenum",data.get("safenum"));
            m.put("auxsafedetail",data.get("auxsafedetail"));

            String goodsid = (String) data.get("goodsid");
            //小计列勾选仓库时
            if(groupcols.contains(",")){
                storageid = (String) data.get("storageid");
            }
            //查询条件选择仓库时
            if(map.containsKey("storageid")){
                storageid = (String) map.get("storageid");
            }
			String showZero=request.getParameter("existingnum");
            List<Map<String, Object>> detailList = storageSummaryService.showStorageSummaryBatchList(storageid, goodsid,showZero);
            GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
            for(Map<String, Object> detail : detailList ){

                BigDecimal existingnum = (BigDecimal) detail.get("existingnum");
                if(existingnum.compareTo(BigDecimal.ZERO) > 0){
                    String costprice = data.get("costprice").toString();//成本价
                    if(StringUtils.isNotEmpty(costprice)){
                        BigDecimal price = new BigDecimal(costprice);
                        BigDecimal costamount = existingnum.multiply(price).setScale(2,BigDecimal.ROUND_HALF_UP);
                        m.put("costprice",price.setScale(2,BigDecimal.ROUND_HALF_UP));
                        m.put("costamount",costamount.toString());
                    }
                }else {
                    continue;//现存量为0或负数的 不导出
                }

                BigDecimal allotwaitnum = (BigDecimal) detail.get("allotwaitnum");//调拨待发箱数
                if(allotwaitnum.compareTo(BigDecimal.ZERO) == 0){
                    String auxallotwaitdetail = "0" + goodsInfo.getAuxunitname() + "0" + goodsInfo.getMainunitName();
                    m.put("auxallotwaitdetail",auxallotwaitdetail);
                }else if(null != goodsInfo && null != goodsInfo.getBoxnum()){
                    BigDecimal boxnum = goodsInfo.getBoxnum();
                    if(boxnum.compareTo(BigDecimal.ZERO) > 0){
                        int auxnum = allotwaitnum.intValue() / boxnum.intValue();
                        int remainnum = allotwaitnum.intValue() % boxnum.intValue();
                        String auxallotwaitdetail = auxnum + goodsInfo.getAuxunitname() + remainnum + goodsInfo.getMainunitName();
                        m.put("auxallotwaitdetail",auxallotwaitdetail);
                    }
                }

                BigDecimal allotenternum = (BigDecimal) detail.get("allotenternum");//调拨待入箱数
                if(allotenternum.compareTo(BigDecimal.ZERO) == 0){
                    String auxallotenterdetail = "0" + goodsInfo.getAuxunitname() + "0" + goodsInfo.getMainunitName();
                    m.put("auxallotenterdetail",auxallotenterdetail);
                }else if(null != goodsInfo && null != goodsInfo.getBoxnum()){
                    BigDecimal boxnum = goodsInfo.getBoxnum();
                    if(boxnum.compareTo(BigDecimal.ZERO) > 0){
                        int auxnum = allotenternum.intValue() / boxnum.intValue();
                        int remainnum = allotenternum.intValue() % boxnum.intValue();
                        String auxallotenterdetail = auxnum + goodsInfo.getAuxunitname() + remainnum + goodsInfo.getMainunitName();
                        m.put("auxallotenterdetail",auxallotenterdetail);
                    }
                }

                BigDecimal waitnum = (BigDecimal) detail.get("waitnum");//待发量
                if(waitnum.compareTo(BigDecimal.ZERO) == 0){
                    String auxwaitdetail = "0" + goodsInfo.getAuxunitname() + "0" + goodsInfo.getMainunitName();
                    m.put("auxwaitdetail",auxwaitdetail);
                }else if(null != goodsInfo && null != goodsInfo.getBoxnum()){
                    BigDecimal boxnum = goodsInfo.getBoxnum();
                    if(boxnum.compareTo(BigDecimal.ZERO) > 0){
                        int auxnum = waitnum.intValue() / boxnum.intValue();
                        int remainnum = waitnum.intValue() % boxnum.intValue();
                        String auxwaitdetail = auxnum + goodsInfo.getAuxunitname() + remainnum + goodsInfo.getMainunitName();
                        m.put("auxwaitdetail",auxwaitdetail);
                    }
                }
                detail.putAll(m);
                exportList.add(detail);
            }
        }

        if(null!=pageData.getFooter()){
            exportList.addAll(pageData.getFooter());
        }
        map.put("detail","export");//map中传入detail参数 导出所属仓库
        ExcelUtils.exportExcel(exportSummaryByStorageDataFilter(exportList,map), title);

    }

    /**
     * 导出分仓库库存总量查询
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 30, 2013
     */
    public void exportSummaryStorageData()throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", "true");
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
        pageMap.setCols("");
        PageData pageData = storageSummaryService.showStorageSummarySumList(pageMap);
        if(null!=pageData.getFooter()){
            pageData.getList().addAll(pageData.getFooter());
        }
        ExcelUtils.exportExcel(exportSummaryByStorageDataFilter(pageData.getList(),map), title);
    }
	
	/**
	 * 数据转换，list专程符合excel导出的数据格式(分仓库库存总量查询)
	 * @param list
	 * @return
	 * @throws Exception
	 * @author panxiaoxiao 
	 * @date Sep 29, 2013
	 */
	private List<Map<String, Object>> exportSummaryByStorageDataFilter(List<Map<String, Object>> list,Map queryMap) throws Exception{
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
		String groupcols = (String) queryMap.get("groupcols");
		if((null!=groupcols && groupcols.indexOf("storageid")!=-1) || queryMap.containsKey("detail")){
			firstMap.put("storagename", "所属仓库");
		}
		String cols = getAccessColunmnList("t_storage_summary",null);
		firstMap.put("goodsid", "商品编码");
		firstMap.put("goodsname", "商品名称");
		firstMap.put("supplierid", "供应商编号");
		firstMap.put("suppliername", "供应商名称");
		firstMap.put("spell", "助记符");
		firstMap.put("barcode", "条形码");
		firstMap.put("brandname", "商品品牌");
        firstMap.put("waresclassname", "商品分类");
        firstMap.put("model", "规格型号");
		firstMap.put("boxnum", "箱装量");
		firstMap.put("unitname", "单位");
		if(isSysUserHaveUrl("/storage/showStorageSummaryAmount.do")){
			firstMap.put("price", "单价");
			firstMap.put("basesaleprice","基准价");
		}
        //判断用户是否拥有 查看成本的权限
        if(isSysUserHaveUrl("/storage/showStorageSummaryCostAmountView.do")){
            firstMap.put("costprice", "成本价");
        }
		firstMap.put("existingnum", "现存量");
		firstMap.put("auxexistingdetail", "现存箱数");
		if(isSysUserHaveUrl("/storage/showStorageSummaryAmount.do")){
			firstMap.put("existingamount", "现存金额");
			firstMap.put("basesaleamount", "基准金额");
		}
        if(isSysUserHaveUrl("/storage/showStorageSummaryCostAmountView.do")){
            firstMap.put("costamount", "成本金额");
        }
		firstMap.put("usablenum", "可用量");
		firstMap.put("auxusabledetail", "可用箱数");
		if(isSysUserHaveUrl("/storage/showStorageSummaryAmount.do")){
			firstMap.put("usableamount", "可用金额");
		}
        firstMap.put("waitnum", "待发量");
        firstMap.put("auxwaitdetail", "待发箱数");
		if(isSysUserHaveUrl("/storage/showStorageSummaryAmount.do")){
			firstMap.put("waitamount", "待发金额");
		}
		firstMap.put("allotwaitnum", "调拨待发量");
		firstMap.put("auxallotwaitdetail", "调拨待发箱数");
		firstMap.put("allotenternum", "调拨待入量");
		firstMap.put("auxallotenterdetail", "调拨待入箱数");
        //列表导出和明细导出 分别导出不同的列
        if(queryMap.containsKey("detail")){
            firstMap.put("batchno","批次号");
            firstMap.put("produceddate","生产日期");
            firstMap.put("deadline","截止日期");
        }else{
            firstMap.put("transitnum", "在途量");
            firstMap.put("auxtransitdetail", "在途箱数");
            firstMap.put("projectedusablenum", "预计可用量");
            firstMap.put("auxprojectedusabledetail", "预计可用箱数");
            firstMap.put("safenum", "安全库存");
            firstMap.put("auxsafedetail", "安全箱数");
        }

		result.add(firstMap);
		if(list.size() != 0){
			for(Map<String,Object> map : list){
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
}

