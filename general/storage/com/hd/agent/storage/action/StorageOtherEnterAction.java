/**
 * @(#)StorageOtherEnterAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Aug 3, 2013 chenwei 创建版本
 */
package com.hd.agent.storage.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.*;
import com.hd.agent.storage.model.ExportStorageOtherEnterAndOut;
import com.hd.agent.storage.model.StorageOtherEnter;
import com.hd.agent.storage.model.StorageOtherEnterDetail;
import com.hd.agent.storage.model.StorageOtherEnterDetail;
import com.hd.agent.storage.service.IStorageOtherEnterService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 其他入库单action
 * @author chenwei
 */
public class StorageOtherEnterAction extends BaseFilesAction {

	private StorageOtherEnter storageOtherEnter;

	private IStorageOtherEnterService storageOtherEnterService;

	public IStorageOtherEnterService getStorageOtherEnterService() {
		return storageOtherEnterService;
	}

	public void setStorageOtherEnterService(
			IStorageOtherEnterService storageOtherEnterService) {
		this.storageOtherEnterService = storageOtherEnterService;
	}

	public StorageOtherEnter getStorageOtherEnter() {
		return storageOtherEnter;
	}

	public void setStorageOtherEnter(StorageOtherEnter storageOtherEnter) {
		this.storageOtherEnter = storageOtherEnter;
	}

	/**
	 * 显示其他入库单新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 5, 2013
	 */
	public String showStorageOtherEnterAddPage() throws Exception{
		return "success";
	}
	/**
	 * 显示其他入库单详细新增页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 5, 2013
	 */
	public String storageOtherEnterAddPage() throws Exception{
		//仓库列表
		List storageList = getStorageInfoAllList();
		List storageInList = getBaseSysCodeService().showSysCodeListByType("storageOtherEnterType");
		List deptList = getBaseDepartMentService().showDepartmentOpenList();
		request.setAttribute("storageList", storageList);
		request.setAttribute("storageInList", storageInList);
		request.setAttribute("deptList", deptList);
		request.setAttribute("date", CommonUtils.getTodayDataStr());
		request.setAttribute("autoCreate", isAutoCreate("t_storage_other_enter"));
		request.setAttribute("userName", getSysUser().getName());
		return "success";
	}
	/**
	 * 显示其他入库单明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 5, 2013
	 */
	public String showStorageOtherEnterDetailAddPage() throws Exception{
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}
	/**
	 * 显示其他入库单明细添加页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 5, 2013
	 */
	public String showStorageOtherEnterDetailAddPageForCost() throws Exception{
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

	/**
	 * 显示其他入库单明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 5, 2013
	 */
	public String showStorageOtherEnterDetailEditPage() throws Exception{
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

	/**
	 * 显示其他入库单明细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 5, 2013
	 */
	public String showStorageOtherEnterDetailEditPageForCost() throws Exception{
		request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
		return "success";
	}

	/**
	 * 其他入库单添加暂存
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	@UserOperateLog(key="StorageOtherEnter",type=2)
	public String addStorageOtherEnterHold() throws Exception{
		storageOtherEnter.setStatus("1");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new StorageOtherEnterDetail());
		boolean flag = storageOtherEnterService.addStorageOtherEnter(storageOtherEnter, detailList);
		Map map = new HashMap();
		map.put("id", storageOtherEnter.getId());
		map.put("flag", flag);
		addJSONObject(map);
		addLog("其他入库单新增暂存 编号："+storageOtherEnter.getId(), flag);
		return "success";
	}
	/**
	 * 其他入库单添加保存
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	@UserOperateLog(key="StorageOtherEnter",type=2)
	public String addStorageOtherEnterSave() throws Exception{
		storageOtherEnter.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new StorageOtherEnterDetail());
		boolean flag = storageOtherEnterService.addStorageOtherEnter(storageOtherEnter, detailList);
		Map map = new HashMap();
		String saveaudit = request.getParameter("saveaudit");
		//判断是否保存并审核
		if(flag && "saveaudit".equals(saveaudit)){
			boolean auditflag = storageOtherEnterService.auditStorageOtherEnter(storageOtherEnter.getId());
			map.put("auditflag", auditflag);
			addLog("其他入库单保存审核 编号："+storageOtherEnter.getId(), auditflag);
		}else{
			addLog("其他入库单新增保存 编号："+storageOtherEnter.getId(), flag);
		}
		map.put("id", storageOtherEnter.getId());
		map.put("flag", flag);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 显示其他入库单查看页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	public String showStorageOtherEnterViewPage() throws Exception{
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
	 * 显示其他入库单查看详情页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	public String storageOtherEnterViewPage() throws Exception{
		String id = request.getParameter("id");
        final Map storageOtherEnterInfo = storageOtherEnterService.getStorageOtherEnterInfo(id);
        Map map = storageOtherEnterInfo;
		StorageOtherEnter storageOtherEnter = (StorageOtherEnter) map.get("storageOtherEnter");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("storageOtherEnter", storageOtherEnter);
		request.setAttribute("detailList", jsonStr);

		//仓库列表
		List storageList = getStorageInfoAllList();
		List storageInList = getStorageInOutAllList("1");
		List deptList = getBaseDepartMentService().showDepartmentOpenList();
		request.setAttribute("storageList", storageList);
		request.setAttribute("storageInList", storageInList);
		request.setAttribute("deptList", deptList);
		return "success";
	}
	/**
	 * 显示其他入库单列表页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	public String showStorageOtherEnterListPage() throws Exception{
		//仓库列表
		List storageList = getStorageInfoAllList();
//		List storageInList = getStorageInOutAllList("1");
		List deptList = getBaseDepartMentService().showDepartmentOpenList();
		request.setAttribute("storageList", storageList);
//		request.setAttribute("storageInList", storageInList);
		request.setAttribute("deptList", deptList);
		return "success";
	}
	/**
	 * 获取其他入库单列表数据
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	public String showStorageOtherEnterList() throws Exception{
		//获取页面传过来的参数 封装到map里面
		Map map = CommonUtils.changeMap(request.getParameterMap());
		//map赋值到pageMap中作为查询条件
		pageMap.setCondition(map);
		PageData pageData = storageOtherEnterService.showStorageOtherEnterList(pageMap);
		addJSONObject(pageData);
		return "success";
	}
	/**
	 * 显示其他入库单修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	public String showStorageOtherEnterEditPage() throws Exception{
		Boolean flag=true;
		String id = request.getParameter("id");
		request.setAttribute("id", id);
		request.setAttribute("type", "edit");
		StorageOtherEnter storageOtherEnter=storageOtherEnterService.showPureStorageOtherEnter(id);
		if(null==storageOtherEnter){
			flag=false;
		}
		request.setAttribute("flag", flag);
		return "success";
	}
	/**
	 * 显示其他入库单详细修改页面
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	public String storageOtherEnterEditPage() throws Exception{
		String id = request.getParameter("id");
		Map map = storageOtherEnterService.getStorageOtherEnterInfo(id);
		StorageOtherEnter storageOtherEnter = (StorageOtherEnter) map.get("storageOtherEnter");
		List list =  (List) map.get("detailList");
		String jsonStr = JSONUtils.listToJsonStr(list);
		List statusList = getBaseSysCodeService().showSysCodeListByType("status");
		request.setAttribute("statusList", statusList);
		request.setAttribute("storageOtherEnter", storageOtherEnter);
		request.setAttribute("detailList", jsonStr);

		//仓库列表
		List storageList = getStorageInfoAllList();
//		List storageInList = getStorageInOutAllList("1");
        List storageInList = getBaseSysCodeService().showSysCodeListByType("storageOtherEnterType");
		List deptList = getBaseDepartMentService().showDepartmentOpenList();
		request.setAttribute("storageList", storageList);
		request.setAttribute("storageInList", storageInList);
		request.setAttribute("deptList", deptList);

		if(null==storageOtherEnter){
			return "addSuccess";
		}else{
			if("1".equals(storageOtherEnter.getStatus()) || "2".equals(storageOtherEnter.getStatus()) || "6".equals(storageOtherEnter.getStatus())){
				return "editSuccess";
			}else{
				return "viewSuccess";
			}
		}
	}
	/**
	 * 其他入库单修改暂存
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	@UserOperateLog(key="StorageOtherEnter",type=3)
	public String editStorageOtherEnterHold() throws Exception{
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new StorageOtherEnterDetail());
		boolean flag = storageOtherEnterService.editStorageOtherEnter(storageOtherEnter, detailList);
		Map map = new HashMap();
		map.put("id", storageOtherEnter.getId());
		map.put("flag", flag);
		addJSONObject(map);
		addLog("其他入库单修改暂存 编号："+storageOtherEnter.getId(), flag);
		return "success";
	}
	/**
	 * 其他入库单修改保存
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	@UserOperateLog(key="StorageOtherEnter",type=3)
	public String editStorageOtherEnterSave() throws Exception{
		storageOtherEnter.setStatus("2");
		String detailJson = request.getParameter("detailJson");
		List detailList = JSONUtils.jsonStrToList(detailJson, new StorageOtherEnterDetail());
		boolean flag = storageOtherEnterService.editStorageOtherEnter(storageOtherEnter, detailList);
		Map map = new HashMap();
		String saveaudit = request.getParameter("saveaudit");
		//判断是否保存并审核
		if(flag && "saveaudit".equals(saveaudit)){
			boolean auditflag = storageOtherEnterService.auditStorageOtherEnter(storageOtherEnter.getId());
			map.put("auditflag", auditflag);
			addLog("其他入库单保存审核 编号："+storageOtherEnter.getId(), auditflag);
		}else{
			addLog("其他入库单修改保存 编号："+storageOtherEnter.getId(), flag);
		}
		map.put("id", storageOtherEnter.getId());
		map.put("flag", flag);
		addJSONObject(map);
		return "success";
	}
	/**
	 * 其他入库单删除
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	@UserOperateLog(key="StorageOtherEnter",type=4)
	public String deleteStorageOtherEnter() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageOtherEnterService.deleteStorageOtherEnter(id);
		addJSONObject("flag", flag);
		addLog("其他入库单删除 编号："+id, flag);
		return "success";
	}
	/**
	 * 其他入库单审核
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	@UserOperateLog(key="StorageOtherEnter",type=3)
	public String auditStorageOtherEnter() throws Exception{
		String id = request.getParameter("id");
		boolean flag = storageOtherEnterService.auditStorageOtherEnter(id);
		addJSONObject("flag", flag);
		addLog("其他入库单审核 编号："+id, flag);
		return "success";
	}
	/**
	 * 其他入库单反审
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Feb 14, 2014
	 */
	@UserOperateLog(key="StorageOtherEnter",type=3)
	public String oppauditStorageOtherEnter() throws Exception{
		String id = request.getParameter("id");
		Map map = storageOtherEnterService.oppauditStorageOtherEnter(id);
		addJSONObject(map);
		addLog("其他入库单反审 编号："+id, map);
		return "success";
	}
	/**
	 * 其他入库单提交工作流
	 * @return
	 * @throws Exception
	 * @author chenwei
	 * @date Aug 6, 2013
	 */
	public String submitStorageOtherEnterProcess() throws Exception{
		String id = request.getParameter("id");
		Map map = storageOtherEnterService.submitStorageOtherEnterProcess(id);
		addJSONObject(map);
		return "success";
	}

    /**
     * 其它入库单导出
     * @author lin_xx
     * @date Feb 6, 2016
     * @throws Exception
     */
    @UserOperateLog(key="StorageOtherEnter",type=0,value = "其它入库单导出")
    public void storageOtherEnterExport() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
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

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        //根据定义类型获取显示的字段
        firstMap.put("id","编号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("storageid", "入库仓编码");
        firstMap.put("storagename", "入库仓库");
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

        List<ExportStorageOtherEnterAndOut> list = excelService.getStorageOtherEnterAndOutList(pageMap,"enter");

        if(list.size()!=0){
            for(ExportStorageOtherEnterAndOut storageOtherEnter :list){

                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(storageOtherEnter);
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
     * 其它入库单导入
     * @Date Feb 18,2016
     * @author lin_xx
     * @return
     * @throws Exception
     */
    @UserOperateLog(key = "StorageOtherEnter", type = 2, value = "其它入库单导入")
    public String storageOtherEnterImport() throws Exception{
        Map returnMap = new HashMap();
        List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); // 获取第一行数据为字段的描述列表
        List<String> paramList2 = new ArrayList<String>();
        if (null != paramList) {
            for (String str : paramList) {
                if ("业务日期".equals(str)) {
                    paramList2.add("businessdate");
                } else if ("入库仓编码".equals(str)) {
                    paramList2.add("storageid");
                } else if ("入库仓库".equals(str)) {
                    paramList2.add("storagename");
                } else if ("商品编码".equals(str)) {
                    paramList2.add("goodsid");
                } else if ("商品名称".equals(str)) {
                    paramList2.add("goodsname");
                } else if ("商品助记符".equals(str)) {
                    paramList2.add("spell");
                } else if ("商品条形码".equals(str)) {
                    paramList2.add("barcode");
                } else if ("数量".equals(str)) {
                    paramList2.add("unitnum");
                } else if ("辅数量".equals(str)) {
                    paramList2.add("auxnumdetail");
                } else if ("单价".equals(str)) {
                    paramList2.add("taxprice");
                } else if ("金额".equals(str)) {
                    paramList2.add("taxamount");
                } else if("所属库位".equals(str)){
                    paramList2.add("storagelocationname");
                } else if("批次号".equals(str)){
                    paramList2.add("batchno");
                } else if("生产日期".equals(str)){
                    paramList2.add("produceddate");
                } else if("有效截止日期".equals(str)){
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
        List<StorageOtherEnterDetail> exportList = new ArrayList<StorageOtherEnterDetail>();
		int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
        for(Map m : list){
            StorageOtherEnterDetail other = new StorageOtherEnterDetail();
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
            if(m.containsKey("taxamount")){
			 	BigDecimal taxamount = new BigDecimal((String) m.get("taxamount"));
			 	if(taxamount.compareTo(BigDecimal.ZERO)!=0 &&unitnum.compareTo(BigDecimal.ZERO)!=0){
					BigDecimal taxprice = taxamount.divide(unitnum,6,BigDecimal.ROUND_HALF_UP);
					other.setTaxprice(taxprice);
				}
            }
            if(m.containsKey("taxprice")){
			 	BigDecimal taxprice = new BigDecimal((String) m.get("taxprice"));
                other.setTaxprice(taxprice);
            }
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
        Map storageMap = mergeExportStorageEnter(exportList);
        for(int i = 0 ;i<storageidList.size(); ++ i){
            String storageid = (String) storageidList.get(i);
            if(storageMap.containsKey(storageid)){
                List<StorageOtherEnterDetail> detailList = (List<StorageOtherEnterDetail>) storageMap.get(storageid);
                //验证仓库是否存在
                StorageInfo storageInfo = getStorageInfo(storageid);
                if(null == storageInfo){
                    msg += "仓库编码："+storageid+"不存在，"+detailList.size()+"条商品导入失败";
                    continue;
                }
                StorageOtherEnter storageOtherEnter = new StorageOtherEnter();
                storageOtherEnter.setStorageid(storageid);
                storageOtherEnter.setStatus("2");
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                storageOtherEnter.setBusinessdate(dateFormat.format(new Date()));
                detailList = getStorageOtherEnterDetailList(detailList);
                Map map= storageOtherEnterService.addImportStorageOtherEnter(storageOtherEnter, detailList);
                String disUsableMsg = (String) map.get("disUsableMsg");
                if(map.containsKey("flag") && (Boolean)map.get("flag")){
                    if(msg == ""){
                        msg = "仓库编码："+storageid +".成功导入"+detailList.size()+"条记录;"+disUsableMsg;
                    }else{
                        msg = msg +","+"仓库编码："+storageid +".成功导入"+detailList.size()+"条记录;"+disUsableMsg;
                    }
                }else{
                    if(msg == "" ){
                        msg = "仓库编码："+storageid+"," +disUsableMsg;
                    }else {
                        msg = "仓库编码："+storageid +"," +msg  +"," +disUsableMsg;
                    }
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
     * 对导入的入库单数据进行相同仓库合并
     * @param list
     * @return
     * @date 2016-4-7
     * @author lin_xx
     * @throws Exception
     */
    public Map mergeExportStorageEnter(List<StorageOtherEnterDetail> list) throws Exception{

        Map storageMap = new HashMap();
        Map goodsidMap = new HashMap();
		List<StorageOtherEnterDetail> addList = new ArrayList<StorageOtherEnterDetail>();

        for(StorageOtherEnterDetail storageOther : list ){
            if(goodsidMap.containsKey(storageOther.getGoodsid())){
                StorageOtherEnterDetail sameGoods = (StorageOtherEnterDetail) goodsidMap.get(storageOther.getGoodsid());
                String goodsid = storageOther.getGoodsid();
                String produceddate = storageOther.getProduceddate() ;
                GoodsInfo goodsInfo1 = getGoodsInfoByID(goodsid);
                if("0".equals(goodsInfo1.getIsbatch()) ||
                        ("1".equals(goodsInfo1.getIsbatch()) && StringUtils.isNotEmpty(produceddate) && produceddate.equals(sameGoods.getProduceddate()))){
                    BigDecimal unitnum = storageOther.getUnitnum().add(sameGoods.getUnitnum());
                    sameGoods.setUnitnum(unitnum);
                }
            }else{
                goodsidMap.put(storageOther.getGoodsid(),storageOther);
				addList.add(storageOther);
            }
        }
        for(StorageOtherEnterDetail storageOther : addList ){
            //将相同仓库的商品记录归为一类
            if (storageMap.containsKey(storageOther.getStorageid())){
                List detailList = (List) storageMap.get(storageOther.getStorageid());
                detailList.add(storageOther);
                storageMap.put(storageOther.getStorageid(), detailList);
            }else{
                List detailList = new ArrayList();
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
    public List<StorageOtherEnterDetail> getStorageOtherEnterDetailList(List<StorageOtherEnterDetail> detailList) throws Exception {

        for(StorageOtherEnterDetail outDetail : detailList){
            GoodsInfo goodsInfo = getGoodsInfoByID(outDetail.getGoodsid());
			if(null != goodsInfo){
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
				outDetail.setRealcostprice(goodsInfo.getNewstorageprice());
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
			}
        }
        return detailList;
    }


}


