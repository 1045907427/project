/**
 * @(#)StorageOtherOutAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 3, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.action;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.basefiles.model.TaxType;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IStorageOtherOutService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 其他出库单action
 * @author chenwei
 */
public class StorageOtherOutAction extends BaseFilesAction {

	private StorageOtherOut storageOtherOut;
	
	private IStorageOtherOutService storageOtherOutService;

	public IStorageOtherOutService getStorageOtherOutService() {
		return storageOtherOutService;
	}

	public void setStorageOtherOutService(
			IStorageOtherOutService storageOtherOutService) {
		this.storageOtherOutService = storageOtherOutService;
	}
	
	public StorageOtherOut getStorageOtherOut() {
		return storageOtherOut;
	}

	public void setStorageOtherOut(StorageOtherOut storageOtherOut) {
		this.storageOtherOut = storageOtherOut;
	}

	/**
	 * 显示其他出库单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public String showStorageOtherOutAddPage() throws Exception{
		return "success";
	}
	/**
	 * 显示其他出库单新增详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public String storageOtherOutAddPage() throws Exception{
		//仓库列表
		List storageList = getStorageInfoAllList();
		//出库类型
		List storageOutList = getBaseSysCodeService().showSysCodeListByType("storageOtherOutType");
		List deptList = getBaseDepartMentService().showDepartmentOpenList();
		request.setAttribute("storageList", storageList);
		request.setAttribute("storageOutList", storageOutList);
		request.setAttribute("deptList", deptList);
		request.setAttribute("date", CommonUtils.getTodayDataStr());
		request.setAttribute("autoCreate", isAutoCreate("t_storage_other_out"));
		request.setAttribute("userName", getSysUser().getName());
		return "success";
	}
	/**
	 * 显示其他出库明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public String showStorageOtherOutDetailAddPage() throws Exception{
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

	/**
	 * 显示其他出库明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	public String showStorageOtherOutDetailAddPageForCost() throws Exception{
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

	/**
	 * 显示其他出库明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public String showStorageOtherOutDetailEditPage() throws Exception{
		String goodsid = request.getParameter("goodsid");
		GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
		if(null!=goodsInfo){
			request.setAttribute("isbatch", goodsInfo.getIsbatch());
		}else{
			request.setAttribute("isbatch", "0");
		}
		request.setAttribute("goodsid", goodsid);
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

    /**
     * 显示其他出库明细修改页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    public String showStorageOtherOutDetailEditPageForCost() throws Exception{
        String goodsid = request.getParameter("goodsid");
        GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
        if(null!=goodsInfo){
            request.setAttribute("isbatch", goodsInfo.getIsbatch());
        }else{
            request.setAttribute("isbatch", "0");
        }
        request.setAttribute("goodsid", goodsid);
        request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
        return "success";
    }

	/**
	 * 其他出库单添加暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	@UserOperateLog(key="StorageOtherOut",type=2)
	public String addStorageOtherOutHold() throws Exception{
		storageOtherOut.setStatus("1");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new StorageOtherOutDetail());
		Map map = storageOtherOutService.addStorageOtherOut(storageOtherOut, detailList);
		map.put("id", storageOtherOut.getId());
		addJSONObject(map);
		addLog("其他出库单新增暂存 编号："+storageOtherOut.getId(), map);
		return "success";
	}
	/**
	 * 其他出库单添加保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	@UserOperateLog(key="StorageOtherOut",type=2)
	public String addStorageOtherOutSave() throws Exception{
		storageOtherOut.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new StorageOtherOutDetail());
		Map map = storageOtherOutService.addStorageOtherOut(storageOtherOut, detailList);
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean)map.get("flag");
		if(flag && "saveaudit".equals(saveaudit)){
			boolean auditflag = storageOtherOutService.auditStorageOtherOut(storageOtherOut.getId());
			map.put("auditflag", auditflag);
			addLog("其他出库单保存审核 编号："+storageOtherOut.getId(), auditflag);
		}else{
			addLog("其他出库单新增保存 编号："+storageOtherOut.getId(), flag);
		}
		map.put("id", storageOtherOut.getId());
		addJSONObject(map);
		return "success";
	}
	/**
	 * 显示其他出库单查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public String showStorageOtherOutViewPage() throws Exception{
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if("handle".equals(type)){
			request.setAttribute("type", type);
		}else{
			request.setAttribute("type", "view");
		}
		request.setAttribute("id", id);
		return "success";
	}
	/**
	 * 显示其他出库单查看详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public String storageOtherOutViewPage() throws Exception{
		String id = request.getParameter("id");
		Map map = storageOtherOutService.getStorageOtherOutInfo(id);
		StorageOtherOut storageOtherOut = (StorageOtherOut) map.get("storageOtherOut");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("storageOtherOut", storageOtherOut);
		request.setAttribute("detailList", jsonStr);
		
		//仓库列表
		List storageList = getStorageInfoAllList();
		List storageOutList = getStorageInOutAllList("2");
		List deptList = getBaseDepartMentService().showDepartmentOpenList();
		request.setAttribute("storageList", storageList);
		request.setAttribute("storageOutList", storageOutList);
		request.setAttribute("deptList", deptList);
		return "success";
	}
	/**
	 * 显示其他出库单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public String showStorageOtherOutListPage() throws Exception{
		//仓库列表
		List storageList = getStorageInfoAllList();
//		List storageOutList = getStorageInOutAllList("2");
		List deptList = getBaseDepartMentService().showDepartmentOpenList();
		request.setAttribute("storageList", storageList);
//		request.setAttribute("storageOutList", storageOutList);
		request.setAttribute("deptList", deptList);
		return "success";
	}
	/**
	 * 获取其他出库单列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public String showStorageOtherOutList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = storageOtherOutService.showStorageOtherOutList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示其他出库单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public String showStorageOtherOutEditPage() throws Exception{
		Boolean flag=true;
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		StorageOtherOut storageOtherOut=storageOtherOutService.showPureStorageOtherOut(id);
		if(null==storageOtherOut){
			flag=false;
		}
		request.setAttribute("flag", flag);
		return "success";
	}
	/**
	 * 显示其他出库单详细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public String storageOtherOutEditPage() throws Exception{
		String id = request.getParameter("id");
		Map map = storageOtherOutService.getStorageOtherOutInfo(id);
		StorageOtherOut storageOtherOut = (StorageOtherOut) map.get("storageOtherOut");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("storageOtherOut", storageOtherOut);
		request.setAttribute("detailList", jsonStr);
		
		//仓库列表
		List storageList = getStorageInfoAllList();
//		List storageOutList = getStorageInOutAllList("2");
        List storageOutList = getBaseSysCodeService().showSysCodeListByType("storageOtherOutType");
        List deptList = getBaseDepartMentService().showDepartmentOpenList();
		request.setAttribute("storageList", storageList);
		request.setAttribute("storageOutList", storageOutList);
		request.setAttribute("deptList", deptList);
		
		if(null==storageOtherOut){
			return "addSuccess";
		}else{
			if("1".equals(storageOtherOut.getStatus()) || "2".equals(storageOtherOut.getStatus()) || "6".equals(storageOtherOut.getStatus())){
				return "editSuccess";
			}else{
				return "viewSuccess";
			}
		}
	}
	/**
	 * 其他出库单修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	@UserOperateLog(key="StorageOtherOut",type=3)
	public String editStorageOtherOutHold() throws Exception{
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new StorageOtherOutDetail());
		Map map = storageOtherOutService.editStorageOtherOut(storageOtherOut, detailList);
		map.put("id", storageOtherOut.getId());
		addJSONObject(map);
		addLog("其他出库单修改暂存 编号："+storageOtherOut.getId(), map);
		return "success";
	}
	/**
	 * 其他出库单修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	@UserOperateLog(key="StorageOtherOut",type=3)
	public String editStorageOtherOutSave() throws Exception{
		storageOtherOut.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new StorageOtherOutDetail());
		Map map = storageOtherOutService.editStorageOtherOut(storageOtherOut, detailList);
		String saveaudit = request.getParameter("saveaudit");
		boolean flag = (Boolean) map.get("flag");
		//判断是否保存并审核
		if(flag && "saveaudit".equals(saveaudit)){
			boolean auditflag = storageOtherOutService.auditStorageOtherOut(storageOtherOut.getId());
			map.put("auditflag", auditflag);
			addLog("其他出库单保存审核 编号："+storageOtherOut.getId(), auditflag);
		}else{
			addLog("其他出库单修改保存 编号："+storageOtherOut.getId(), flag);
		}
		map.put("id", storageOtherOut.getId());
		addJSONObject(map);
		addLog("其他出库单修改保存 编号："+storageOtherOut.getId(), map);
		return "success";
	}
	/**
	 * 其他出库单删除
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	@UserOperateLog(key="StorageOtherOut",type=3)
	public String deleteStorageOtherOut() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageOtherOutService.deleteStorageOtherOut(id);
		addJSONObject("flag", flag);
		addLog("其他出库单删除 编号："+id, flag);
		return "success";
	}
	/**
	 * 其他出库单审核
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	@UserOperateLog(key="StorageOtherOut",type=3)
	public String auditStorageOtherOut() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageOtherOutService.auditStorageOtherOut(id);
		addJSONObject("flag", flag);
		addLog("其他出库单审核 编号："+id, flag);
		return "success";
	}
	/**
	 * 其他出库单反审
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Feb 14, 2014
	 */
	@UserOperateLog(key="StorageOtherOut",type=3)
	public String oppauditStorageOtherOut() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageOtherOutService.oppauditStorageOtherOut(id);
		addJSONObject("flag", flag);
		addLog("其他出库单反审 编号："+id, flag);
		return "success";
	}
	/**
	 * 其他出库单提交工作流
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Aug 6, 2013
	 */
	public String submitStorageOtherOutProcess() throws Exception{
		String id = request.getParameter("id");
		Map map = storageOtherOutService.submitStorageOtherOutProcess(id);
		addJSONObject(map);
		return "success";
	}
	
	/**
	 * 获取其他出库单明细信息
	 * @return
	 * @throws Exception
	 * @author chenwei 
	 * @date Jun 21, 2013
	 */
	public String getStorageOtherOutDetailInfo() throws Exception{
		String id = request.getParameter("id");
		StorageOtherOutDetail storageOtherOutDetail = storageOtherOutService.getStorageOtherOutDetailInfo(id);
		addJSONObject("detail", storageOtherOutDetail);
		return "success";
	}

    /**
     * 其它出库单导出
     * @author lin_xx
     * @date Feb 6, 2016
     * @throws Exception
     */
    @UserOperateLog(key="StorageOtherOut",type=0,value = "其它出库单导出")
    public void storageOtherOutExport() throws Exception{

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);

        String title = "";
        if(map.containsKey("excelTitle")){
            title = map.get("excelTitle").toString();
        }else{
            title = "list";
        }
        if(StringUtils.isEmpty(title)){
            title = "list";
        }

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        //根据定义类型获取显示的字段
        firstMap.put("id","编号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("storageid", "出库仓编码");
        firstMap.put("storagename", "出库仓库");
        firstMap.put("goodsid", "商品编码");
        firstMap.put("goodsname", "商品名称");
        firstMap.put("spell", "商品助记符");
        firstMap.put("barcode", "商品条形码");
        firstMap.put("unitnum", "数量");
        firstMap.put("auxnumdetail", "辅数量");
        firstMap.put("taxprice", "单价");
        firstMap.put("taxamount","金额");
        firstMap.put("storagelocationname","所属库位");
        firstMap.put("batchno","批次号");
        firstMap.put("produceddate","生产日期");
        firstMap.put("deadline","有效截止日期");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        List<ExportStorageOtherEnterAndOut> list = excelService.getStorageOtherEnterAndOutList(pageMap, "out");

        if(list.size()!=0){
            for(ExportStorageOtherEnterAndOut storageOtherOut :list){

                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(storageOtherOut);
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()){
                    if(map2.containsKey(fentry.getKey())){ //如果记录中包含该Key，则取该Key的Value
                        for(Map.Entry<String, Object> entry : map2.entrySet()){
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
        ExcelUtils.exportExcel(result, title);
    }

    /**
     * 其它出库单导入
     * @Date Feb 18,2016
     * @author lin_xx
     * @return
     * @throws Exception
     */
    @UserOperateLog(key = "StorageOtherOut", type = 2, value = "其它出库单导入")
    public String storageOtherOutImport() throws Exception{
        Map returnMap = new HashMap();
        List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); // 获取第一行数据为字段的描述列表
        List<String> paramList2 = new ArrayList<String>();
        if (null != paramList) {
            for (String str : paramList) {
                if ("出库仓编码".equals(str)) {
                    paramList2.add("storageid");
                }  else if ("商品编码".equals(str)) {
                    paramList2.add("goodsid");
                } else if ("数量".equals(str)) {
                    paramList2.add("unitnum");
                } else if ("单价".equals(str)) {
                    paramList2.add("taxprice");
                } else if ("金额".equals(str)) {
                    paramList2.add("taxamount");
                } else if ("所属库位".equals(str)) {
                    paramList2.add("storagelocationname");
                } else if ("批次号".equals(str)) {
                    paramList2.add("batchno");
                } else if ("生产日期".equals(str)) {
                    paramList2.add("produceddate");
                } else if ("有效截止日期".equals(str)) {
                    paramList2.add("deadline");
                } else if ("备注".equals(str)) {
                    paramList2.add("remark");
                } else {
                    paramList2.add("null");
                }
            }
        }
        String goodsids = "",disablegoodsids = "",msg="";
        List storageidList = new ArrayList();
        List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); // 获取导入数据
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
		List<StorageOtherOutDetail> exportList = new ArrayList<StorageOtherOutDetail>();
        for(Map m : list){
            StorageOtherOutDetail other = new StorageOtherOutDetail();
            GoodsInfo goodsInfo = null;
            String goodid = (String)m.get("goodsid");
            String storageid = (String) m.get("storageid");
			BigDecimal unitnum = new BigDecimal((String) m.get("unitnum"));
			if(decimalScale == 0){
				unitnum = unitnum.setScale(decimalScale,BigDecimal.ROUND_DOWN);
			}else{
				unitnum = unitnum.setScale(decimalScale,BigDecimal.ROUND_HALF_UP);
			}
            other.setUnitnum(unitnum);

            if(storageidList.size() == 0 || !storageidList.contains(storageid)){
                storageidList.add(storageid);
            }
            if(StringUtils.isNotEmpty(goodid)){
                goodsInfo = getGoodsInfoByID(goodid);
                if (null == goodsInfo) {
                    goodsids += goodid + ",";
                }else if(!"1".equals(goodsInfo.getState())){
                    disablegoodsids += goodid +",";
                }
            }
            other.setGoodsid(goodid);
            other.setStorageid(storageid);

            if(m.containsKey("batchno")){
                other.setBatchno((String) m.get("batchno"));
            }
            if(m.containsKey("produceddate")){
                other.setProduceddate((String) m.get("produceddate"));
            }
            if(m.containsKey("deadline")){
                other.setDeadline((String) m.get("deadline"));
            }
            if(m.containsKey("remark")){
                other.setRemark((String) m.get("remark"));
            }
            exportList.add(other);
        }

        Map storageMap = mergeExportStorageOther(exportList);
		List<StorageOtherOutDetail> addList = new ArrayList<StorageOtherOutDetail>();
		for(int i = 0 ;i<storageidList.size(); ++ i){
            String storageid = (String) storageidList.get(i);
            if(storageMap.containsKey(storageid)){
                List<StorageOtherOutDetail> detailList = (List<StorageOtherOutDetail>) storageMap.get(storageid);
                //验证仓库是否存在
                StorageInfo storageInfo = getStorageInfo(storageid);
                if(null == storageInfo){
                    msg += "仓库编码："+storageid+"不存在，"+detailList.size()+"条商品导入失败";
                    continue;
                }
                StorageOtherOut StorageOther = new StorageOtherOut();
                StorageOther.setStorageid(storageid);
                StorageOther.setStatus("2");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                StorageOther.setBusinessdate(dateFormat.format(new Date()));
                String nogoodsid = "",noMsg = "";//不存在的商品编码
                List<StorageOtherOutDetail> notExistDetail = new ArrayList();
                for(StorageOtherOutDetail outDetail : detailList) {
                    GoodsInfo goodsInfo = getGoodsInfoByID(outDetail.getGoodsid());
                    if(null != goodsInfo){
                        addList.add(outDetail);
                    }else{
                    	nogoodsid = outDetail.getGoodsid()+",";
					}
                }
                noMsg += "不存在商品：" +nogoodsid;
                if(addList.size() > 0){
					addList = getStorageOtherOutDetailList(addList);
                    Map map = storageOtherOutService.addImportStorageOtherOut(StorageOther, addList);
                    String disUsableMsg = (String) map.get("disUsableMsg");
                    if(map.containsKey("flag") && (Boolean)map.get("flag")){
                        if(msg == ""){
                            msg = "仓库编码："+storageid +".成功导入"+addList.size()+"条记录;"+disUsableMsg+noMsg;
                        }else{
                            msg = msg +","+"仓库编码："+storageid +".成功导入"+addList.size()+"条记录;"+disUsableMsg+noMsg;
                        }
                    }else{
                        if(msg == "" ){
                            msg = "仓库编码："+storageid +"," +disUsableMsg+noMsg;
                        }else {
                            msg = "仓库编码："+storageid +"," +msg  +"," +disUsableMsg+noMsg;
                        }
                    }
                }else {
                    msg = "仓库编码："+storageid+","+noMsg;

                }

            }
        }
        returnMap.put("goodsids",goodsids);
        returnMap.put("disablegoodsids",disablegoodsids);
        returnMap.put("msg",msg);

        addJSONObject(returnMap);
        return SUCCESS;
    }

    /**
     * 对导入的出库单数据进行相同仓库合并
     * @param list
     * @return
     * @date 2016-4-7
     * @author lin_xx
     * @throws Exception
     */
    public Map mergeExportStorageOther(List<StorageOtherOutDetail> list) throws Exception{

        Map storageMap = new HashMap();
        String sameGoodsidIndex = "";
        Map goodsidMap = new HashMap();

        for(StorageOtherOutDetail storageOther : list ){
            if(goodsidMap.containsKey(storageOther.getGoodsid())){
                StorageOtherOutDetail sameGoods = (StorageOtherOutDetail) goodsidMap.get(storageOther.getGoodsid());
                String goodsid = storageOther.getGoodsid();
                String produceddate = storageOther.getProduceddate() ;
                GoodsInfo goodsInfo1 = getGoodsInfoByID(goodsid);
                if("0".equals(goodsInfo1.getIsbatch()) ||
                        ("1".equals(goodsInfo1.getIsbatch()) && org.apache.commons.lang3.StringUtils.isNotEmpty(produceddate) && produceddate.equals(sameGoods.getProduceddate()))){
                    sameGoodsidIndex = String.valueOf(list.indexOf(storageOther));
                    BigDecimal unitnum = storageOther.getUnitnum().add(sameGoods.getUnitnum());
                    sameGoods.setUnitnum(unitnum);
                }
            }else{
                goodsidMap.put(storageOther.getGoodsid(),storageOther);
            }
        }
        //删除商品编码重复的记录
        String[] indexStr = sameGoodsidIndex.split(",");
        for(String index : indexStr){
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(index)){
                list.remove(Integer.parseInt(index));
            }
        }
        for(StorageOtherOutDetail storageOther : list ){
            //将相同仓库的商品记录归为一类
            if (storageMap.containsKey(storageOther.getStorageid())){
                List<StorageOtherOutDetail> detailList = (List<StorageOtherOutDetail>) storageMap.get(storageOther.getStorageid());
                detailList.add(storageOther);
                storageMap.put(storageOther.getStorageid(), detailList);
            }else{
                List<StorageOtherOutDetail> detailList = new ArrayList<StorageOtherOutDetail>();
                detailList.add(storageOther);
                storageMap.put(storageOther.getStorageid(), detailList);
            }
        }
        return storageMap;
    }

    /**
     * 根据仓库对商品明细进行组装
     * @param detailList
     * @return
     * @throws Exception
     */
    public List<StorageOtherOutDetail> getStorageOtherOutDetailList(List<StorageOtherOutDetail> detailList) throws Exception {
		Map goodsidMap = new HashMap();
		List<StorageOtherOutDetail> addList = new ArrayList<StorageOtherOutDetail>();
        for(StorageOtherOutDetail outDetail : detailList){
        	if(!goodsidMap.containsKey(outDetail.getGoodsid())){
        		goodsidMap.put(outDetail.getGoodsid(),outDetail);
				GoodsInfo goodsInfo = getGoodsInfoByID(outDetail.getGoodsid());
				outDetail.setGoodsInfo(goodsInfo);
				outDetail.setGoodsid(goodsInfo.getId());
				outDetail.setBrandid(goodsInfo.getBrand());
				outDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
				outDetail.setUnitid(goodsInfo.getMainunit());
				if(null != outDetail.getTaxprice() && outDetail.getTaxprice().compareTo(BigDecimal.ZERO) != 0){
					outDetail.setTaxprice(outDetail.getTaxprice());
				}else{
					outDetail.setTaxprice(goodsInfo.getNewbuyprice());
				}
				//实际成本价 不包括核算成本价
				outDetail.setRealcostprice(getGoodsCostPrice(outDetail.getStorageid(),outDetail.getGoodsid()));
				if (org.apache.commons.lang3.StringUtils.isEmpty(goodsInfo.getMainunitName())) {
					if (org.apache.commons.lang3.StringUtils.isNotEmpty(goodsInfo.getMainunit())) {
						MeteringUnit meteringUnit = getBaseFilesService().getMeteringUnitById(goodsInfo.getMainunit());
						if (null != meteringUnit) {
							outDetail.setUnitname(meteringUnit.getName());
						}
					}
				} else {
					outDetail.setUnitname(goodsInfo.getMainunitName());
				}
				Map auxMap = countGoodsInfoNumber(goodsInfo.getId(), "", outDetail.getUnitnum());
				if(null != auxMap.get("auxunitid")) {
					outDetail.setAuxunitid((String) auxMap.get("auxunitid"));
				}
				if(null != auxMap.get("auxunitname")){
					outDetail.setAuxunitname((String) auxMap.get("auxunitname"));
				}
				if(null != auxMap.get("auxInteger")){
					outDetail.setAuxnum(new BigDecimal((String) auxMap.get("auxInteger")));
				}
				if(null != auxMap.get("auxnumdetail")){
					outDetail.setAuxnumdetail((String) auxMap.get("auxnumdetail"));
				}
				if (null != auxMap.get("auxremainder")) {
					outDetail.setAuxremainder(new BigDecimal((String) auxMap.get("auxremainder")));
				}
				Map taxMap = getTaxInfosByTaxpriceAndTaxtype(outDetail.getTaxprice(),goodsInfo.getDefaulttaxtype(),outDetail.getUnitnum());
				outDetail.setNotaxprice((BigDecimal) taxMap.get("notaxprice"));
				outDetail.setTaxamount((BigDecimal) taxMap.get("taxamount"));
				outDetail.setNotaxamount((BigDecimal) taxMap.get("notaxamount"));
				outDetail.setTax((BigDecimal) taxMap.get("tax"));
				addList.add(outDetail);
			}

        }
        return addList;

    }


}

