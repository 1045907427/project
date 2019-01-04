package com.hd.agent.storage.action;

import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.MeteringUnit;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.*;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.ILendService;
import com.hd.agent.storage.model.Lend;
import com.hd.agent.storage.service.IStorageOtherEnterService;
import com.hd.agent.storage.service.IStorageOtherOutService;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jqq on 2017/10/19.
 */
public class LendAction extends BaseFilesAction {

    private ILendService lendService;

    private IAttachFileService attachFileService;

    private Lend lend;

    public IAttachFileService getAttachFileService() {
        return attachFileService;
    }

    public void setAttachFileService(IAttachFileService attachFileService) {
        this.attachFileService = attachFileService;
    }

    public Lend getLend() {
        return lend;
    }

    public void setLend(Lend lend) {
        this.lend = lend;
    }

    public ILendService getLendService() {
        return lendService;
    }

    public void setLendService(ILendService lendService) {
        this.lendService = lendService;
    }


    /**
     * 显示借货还货单新增页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    public String showLendAddPage() throws Exception{
        String printlimit=getPrintLimitInfo();
        request.setAttribute("printlimit", printlimit);
        String type = request.getParameter("billtype");
        if ("2".equals(type)) {
            return "enterSuccess";
        } else {
            return "outSuccess";
        }
    }

    /**
     * 显示借货还货单新增详情页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    public String lendAddPage() throws Exception{

        request.setAttribute("date", CommonUtils.getTodayDataStr());
        request.setAttribute("autoCreate", isAutoCreate("t_storage_other_out"));
        request.setAttribute("userName", getSysUser().getName());
        String type = request.getParameter("billtype");
        if ("2".equals(type)) {
            return "enterSuccess";
        } else {
            return "outSuccess";
        }
    }

    /**
     * 显示借货还货明细添加页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    public String showLendDetailAddPage() throws Exception{
        request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
        String type = request.getParameter("billtype");
        if ("2".equals(type)) {
            return "enterSuccess";
        } else {
            return "outSuccess";
        }
    }

    /**
     * 显示借货还货明细修改页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    public String showLendDetailEditPage() throws Exception{
        String goodsid = request.getParameter("goodsid");
        String type = request.getParameter("billtype");
        GoodsInfo goodsInfo = getGoodsInfoByID(goodsid);
        if(null!=goodsInfo){
            request.setAttribute("isbatch", goodsInfo.getIsbatch());
        }else{
            request.setAttribute("isbatch", "0");
        }
        request.setAttribute("goodsid", goodsid);
        request.setAttribute("decimallen", BillGoodsNumDecimalLenUtils.decimalLen);
        if ("2".equals(type)) {
            return "enterSuccess";
        } else {
            return "outSuccess";
        }
    }

    /**
     * 借货还货单添加暂存
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    @UserOperateLog(key="Lend",type=2)
    public String addLendHold() throws Exception{
        lend.setStatus("1");
        String detailJson = request.getParameter("detailJson");
        List detailList = JSONUtils.jsonStrToList(detailJson, new LendDetail());
        Map map = lendService.addLend(lend, detailList);
        map.put("id", lend.getId());
        addJSONObject(map);
        addLog("借货还货单新增暂存 编号："+lend.getId(), map);
        return "success";
    }
    /**
     * 借货还货单添加保存
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    @UserOperateLog(key="Lend",type=2)
    public String addLendSave() throws Exception{
        lend.setStatus("2");
        String detailJson = request.getParameter("detailJson");
        String type = request.getParameter("billtype");
        if ("2".equals(type)) {
            lend.setBilltype("2");
        } else {
            lend.setBilltype("1");
        }
        List detailList = JSONUtils.jsonStrToList(detailJson, new LendDetail());
        Map map = lendService.addLend(lend, detailList);
        String saveaudit = request.getParameter("saveaudit");
        boolean flag = (Boolean)map.get("flag");
        if (flag) {
            String msg = "保存成功";
            map.put("msg", msg);
        } else {
            String msg = "保存失败";
            map.put("msg", msg);
        }
        if(flag && "saveaudit".equals(saveaudit)){
            Map auditMap = new HashMap();
            if ("2".equals(type)) {
                List storageEnterList = JSONUtils.jsonStrToList(detailJson,new StorageOtherEnterDetail());
                StorageOtherEnter storageOtherEnter = PojoConvertUtil.convertPojo(lend,StorageOtherEnter.class);
                storageOtherEnter.setId(null);
                storageOtherEnter.setSourceid(lend.getId());
                storageOtherEnter.setSourcetype("2");
                auditMap = lendService.auditLend(lend.getId(),storageOtherEnter,storageEnterList);
            } else {
                List storageOutList = JSONUtils.jsonStrToList(detailJson,new StorageOtherOutDetail());
                StorageOtherOut storageOtherOut = PojoConvertUtil.convertPojo(lend,StorageOtherOut.class);
                storageOtherOut.setId(null);
                storageOtherOut.setSourceid(lend.getId());
                storageOtherOut.setSourcetype("2");
                auditMap = lendService.auditLend(lend.getId(),storageOtherOut,storageOutList);
            }
            if((Boolean) auditMap.get("auditflag")) {
                if ("2".equals(type)) {
                    String msg = "审核成功，生成其他入库单据，编号："+(String) auditMap.get("otherId");
                    map.put("msg", msg);
                } else {
                    String msg = "审核成功，生成其他出货单据，编号："+(String) auditMap.get("otherId");
                    map.put("msg", msg);
                }
            } else {
                String msg = "保存成功，审核失败";
                map.put("msg", msg);
                map.put("flag",false);
            }
            addLog("借货还货单保存审核 编号："+lend.getId(), (Boolean) auditMap.get("auditflag"));
        }else{
            addLog("借货还货单新增保存 编号："+lend.getId(), flag);
        }
        map.put("id", lend.getId());
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 显示借货还货单查看页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    public String showLendViewPage() throws Exception{
        String printlimit=getPrintLimitInfo();
        request.setAttribute("printlimit", printlimit);
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
     * 显示借货还货单查看详情页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    public String lendViewPage() throws Exception{
        String id = request.getParameter("id");
        String type = request.getParameter("billtype");

        Map map = lendService.getLendInfo(id);
        Lend lend = (Lend) map.get("lend");
        List list =  (List) map.get("detailList");
        String jsonStr = JSONUtils.listToJsonStr(list);
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);
        request.setAttribute("lend", lend);
        request.setAttribute("detailList", jsonStr);

        if ("2".equals(type)) {
            return "enterSuccess";
        } else  {
            return "outSuccess";
        }
    }
    /**
     * 显示借货还货单列表页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    public String showLendListPage() throws Exception{
        String printlimit=getPrintLimitInfo();
        request.setAttribute("printlimit", printlimit);
        String type = request.getParameter("billtype");
        if ("2".equals(type)) {
            return "enterSuccess";
        } else  {
            return "outSuccess";
        }
    }
    /**
     * 获取借货还货单列表数据
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    public String showLendList() throws Exception{
        //获取页面传过来的参数 封装到map里面
        Map map = CommonUtils.changeMap(request.getParameterMap());
        //map赋值到pageMap中作为查询条件
        pageMap.setCondition(map);
        PageData pageData = lendService.showLendList(pageMap);
        addJSONObject(pageData);
        return "success";
    }
    /**
     * 显示借货还货单修改页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    public String showLendEditPage() throws Exception{
        String printlimit=getPrintLimitInfo();
        request.setAttribute("printlimit", printlimit);
        Boolean flag=true;
        String id = request.getParameter("id");
        String type = request.getParameter("billtype");
        request.setAttribute("id", id);
        request.setAttribute("type", "edit");
        Lend lend=lendService.showPureLend(id);
        if(null==lend){
            flag=false;
        }
        request.setAttribute("flag", flag);
        if ("2".equals(type)) {
            return "enterSuccess";
        } else  {
            return "outSuccess";
        }
    }
    /**
     * 显示借货还货单详细修改页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    public String lendEditPage() throws Exception{
        String id = request.getParameter("id");
        String type = request.getParameter("billtype");
        Map map = lendService.getLendInfo(id);
        Lend lend = (Lend) map.get("lend");
        List list =  (List) map.get("detailList");
        String jsonStr = JSONUtils.listToJsonStr(list);
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);
        request.setAttribute("lend", lend);
        request.setAttribute("detailList", jsonStr);

        if ("2".equals(type)) {
            if(null==lend){
                return "addEnterSuccess";
            }else{
                if("1".equals(lend.getStatus()) || "2".equals(lend.getStatus()) || "6".equals(lend.getStatus())){
                    return "editEnterSuccess";
                }else{
                    return "viewEnterSuccess";
                }
            }
        } else  {
            if(null==lend){
                return "addOutSuccess";
            }else{
                if("1".equals(lend.getStatus()) || "2".equals(lend.getStatus()) || "6".equals(lend.getStatus())){
                    return "editOutSuccess";
                }else{
                    return "viewOutSuccess";
                }
            }
        }
    }
    /**
     * 借货还货单修改暂存
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    @UserOperateLog(key="Lend",type=3)
    public String editLendHold() throws Exception{
        String detailJson = request.getParameter("detailJson");
        List detailList = JSONUtils.jsonStrToList(detailJson, new LendDetail());
        Map map = lendService.editLend(lend, detailList);
        map.put("id", lend.getId());
        addJSONObject(map);
        addLog("借货还货单修改暂存 编号："+lend.getId(), map);
        return "success";
    }
    /**
     * 借货还货单修改保存
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    @UserOperateLog(key="Lend",type=3)
    public String editLendSave() throws Exception{
        lend.setStatus("2");
        String detailJson = request.getParameter("detailJson");
        String type = request.getParameter("billtype");
        if ("2".equals(type)) {
            lend.setBilltype("2");
        } else {
            lend.setBilltype("1");
        }
        List detailList = JSONUtils.jsonStrToList(detailJson, new LendDetail());
        Map map = lendService.editLend(lend, detailList);
        String saveaudit = request.getParameter("saveaudit");
        boolean flag = (Boolean) map.get("flag");
        //判断是否保存并审核
        if(flag && "saveaudit".equals(saveaudit)){
            Map auditMap = new HashMap();
            if ("2".equals(type)) {
                List<StorageOtherEnterDetail> storageEnterList = JSONUtils.jsonStrToList(detailJson,new StorageOtherEnterDetail());
                StorageOtherEnter storageOtherEnter = PojoConvertUtil.convertPojo(lend,StorageOtherEnter.class);
                storageOtherEnter.setId(null);
                storageOtherEnter.setSourceid(lend.getId());
                storageOtherEnter.setSourcetype("2");
                for(StorageOtherEnterDetail enter : storageEnterList) {
                    enter.setId(null);
                }
                auditMap = lendService.auditLend(lend.getId(),storageOtherEnter,storageEnterList);
            } else {
                List<StorageOtherOutDetail> storageOutList = JSONUtils.jsonStrToList(detailJson,new StorageOtherOutDetail());
                StorageOtherOut storageOtherOut = PojoConvertUtil.convertPojo(lend,StorageOtherOut.class);
                storageOtherOut.setId(null);
                storageOtherOut.setSourceid(lend.getId());
                storageOtherOut.setSourcetype("2");
                for(StorageOtherOutDetail out : storageOutList) {
                    out.setId(null);
                }
                auditMap = lendService.auditLend(lend.getId(),storageOtherOut,storageOutList);
            }
            if (flag && (Boolean) auditMap.get("auditflag")) {
                map.put("auditflag", true);
                if ("2".equals(type)) {
                    String msg = "审核成功，生成其他入库单据，编号："+(String) auditMap.get("otherId");
                    map.put("msg", msg);
                } else {
                    String msg = "审核成功，生成其他出货单据，编号："+(String) auditMap.get("otherId");
                    map.put("msg", msg);
                }
            } else {
                map.put("auditflag", false);
            }
            addLog("借货还货单保存审核 编号："+lend.getId(), (Boolean) auditMap.get("auditflag"));
        }else{
            addLog("借货还货单修改保存 编号："+lend.getId(), flag);
        }
        map.put("id", lend.getId());
        addJSONObject(map);
        addLog("借货还货单修改保存 编号："+lend.getId(), map);
        return "success";
    }
    /**
     * 借货还货单删除
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    @UserOperateLog(key="Lend",type=3)
    public String deleteLend() throws Exception{
        String id = request.getParameter("id");
        boolean flag = lendService.deleteLend(id);
        addJSONObject("flag", flag);
        addLog("借货还货单删除 编号："+id, flag);
        return "success";
    }
    /**
     * 借货还货单审核
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    @UserOperateLog(key="Lend",type=3)
    public String auditLend() throws Exception{
        String id = request.getParameter("id");
     //   String type = request.getParameter("billtype");
     //   String detailJson = request.getParameter("detailJson");
      //  List detailList = JSONUtils.jsonStrToList(detailJson, new LendDetail());
        boolean flag = true;
        Map lendMap =lendService.getLendInfo(id);
        Lend lend = (Lend) lendMap.get("lend");
        List<LendDetail> lendDetails = (List<LendDetail>)lendMap.get("detailList");
        Map auditMap = new HashMap();
        if ("2".equals(lend.getBilltype())) {
            StorageOtherEnter storageOtherEnter = PojoConvertUtil.convertPojo(lend,StorageOtherEnter.class);
            storageOtherEnter.setId(null);
            storageOtherEnter.setSourceid(lend.getId());
            storageOtherEnter.setSourcetype("2");
            List<StorageOtherEnterDetail> storageEnterList = PojoConvertUtil.convertPojos(lendDetails,StorageOtherEnterDetail.class);
            for(StorageOtherEnterDetail enter : storageEnterList) {
                enter.setId(null);
            }
            auditMap = lendService.auditLend(lend.getId(),storageOtherEnter,storageEnterList);
        } else {
            StorageOtherOut storageOtherOut = PojoConvertUtil.convertPojo(lend,StorageOtherOut.class);
            storageOtherOut.setId(null);
            storageOtherOut.setSourceid(lend.getId());
            storageOtherOut.setSourcetype("2");
            List<StorageOtherOutDetail> storageOutList = PojoConvertUtil.convertPojos(lendDetails,StorageOtherOutDetail.class);
            for(StorageOtherOutDetail out : storageOutList) {
                out.setId(null);
            }
            auditMap = lendService.auditLend(lend.getId(),storageOtherOut,storageOutList);
        }
        if (flag && (Boolean) auditMap.get("auditflag")) {
            lendMap.put("flag", true);
            if ("2".equals(lend.getBilltype())) {
                String msg = "审核成功，生成其他入库单据，编号："+(String) auditMap.get("otherId");
                lendMap.put("msg", msg);
            } else {
                String msg = "审核成功，生成其他出货单据，编号："+(String) auditMap.get("otherId");
                lendMap.put("msg", msg);
            }
        } else {
            lendMap.put("flag", false);
            lendMap.put("msg",auditMap.get("msg"));
        }
        addJSONObject(lendMap);
        addLog("借货还货单审核 编号："+id, flag);
        return "success";
    }
    /**
     * 借货还货单反审
     * @return
     * @throws Exception
     * @author chenwei
     * @date Feb 14, 2014
     */
    @UserOperateLog(key="Lend",type=3)
    public String oppauditLend() throws Exception{
        String id = request.getParameter("id");
       // String type = request.getParameter("billtype");
        Map oppauditMap = lendService.oppauditLend(id);
        addJSONObject(oppauditMap);
        addLog("借货还货单反审 编号："+id,(Boolean)oppauditMap.get("flag"));
        return "success";
    }

    /**
     * 借货还货单提交工作流
     * @return
     * @throws Exception
     * @author chenwei
     * @date Aug 6, 2013
     */
    public String submitLendProcess() throws Exception{
        String id = request.getParameter("id");
        Map map = lendService.submitLendProcess(id);
        addJSONObject(map);
        return "success";
    }

    /**
     * 获取借货还货单明细信息
     * @return
     * @throws Exception
     * @author chenwei
     * @date Jun 21, 2013
     */
    public String getLendDetailInfo() throws Exception{
        String id = request.getParameter("id");
        LendDetail lendDetail = lendService.getLendDetailInfo(id);
        addJSONObject("detail", lendDetail);
        return "success";
    }

    /**
     * 借货还货单导出
     * @author lin_xx
     * @date Feb 6, 2016
     * @throws Exception
     */
    @UserOperateLog(key="Lend",type=0,value = "借货还货单导出")
    public void lendExport() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        String type = (String)map.get("billtype");
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
        if ("2".equals(type)) {
            firstMap.put("storageid", "入货仓编码");
            firstMap.put("storagename", "入货仓库");
            firstMap.put("lendtypename","还货人类型");
            firstMap.put("lendid","还货人编号");
            firstMap.put("lendname","还货人");
        } else {
            firstMap.put("storageid", "出货仓编码");
            firstMap.put("storagename", "出货仓库");
            firstMap.put("lendtypename","借货人类型");
            firstMap.put("lendid","借货人编号");
            firstMap.put("lendname","借货人");
        }
        firstMap.put("deptid", "部门编码");
        firstMap.put("deptname", "部门名称");
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

        List<ExportLend> list = excelService.getLendList(pageMap);

        if(list.size()!=0){
            for(ExportLend lend :list){

                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(lend);
                for(Map.Entry<String, Object> fentry : firstMap.entrySet()) {
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
     * 借货还货单导入
     * @Date Feb 18,2016
     * @author lin_xx
     * @return
     * @throws Exception
     */
    @UserOperateLog(key = "Lend", type = 2, value = "借货还货单导入")
    public String lendImport() throws Exception{
        String type = request.getParameter("billtype");
        List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile);
        List<String> paramList2 = new ArrayList<String>();
        Map returnMap = new HashMap();
        if (null != paramList) {
            for (String str : paramList) {
                if ("2".equals(type)) {
                    if ("入货仓编码".equals(str)) {
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
                    } else if ("还货人类型".equals(str)) {
                        paramList2.add("lendtype");
                    } else if("还货人编号".equals(str)) {
                        paramList2.add("lendid");
                    } else {
                        paramList2.add("null");
                    }
                } else {
                    if ("出货仓编码".equals(str)) {
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
                    } else if ("借货人类型".equals(str)) {
                        paramList2.add("lendtype");
                    } else if("借货人编号".equals(str)) {
                        paramList2.add("lendid");
                    }  else {
                        paramList2.add("null");
                    }
                }
            }
        }
        List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); // 获取导入数据
        List<Map<String, Object>> errorList = lendService.lendImport(list,type);
        if (errorList.size() > 0) {
            Map titleMap = new LinkedHashMap();
            titleMap.put("storageid","还货仓编码");
            titleMap.put("goodsid","商品编码");
            titleMap.put("unitnum","数量");
            titleMap.put("lendtype","还货人类型");
            titleMap.put("lendid","还货人编号");
            titleMap.put("errors", "错误信息");

            String fileid = createErrorFile(titleMap,errorList);
            returnMap.put("msg", "导入失败" + errorList.size() + "条");
            returnMap.put("errorid", fileid);
        } else {
            returnMap.put("msg","导入成功");
        }
        addJSONObject(returnMap);
        return SUCCESS;
    }

    /**
     * 根据仓库对商品明细进行组装
     * @param detailList
     * @return
     * @throws Exception
     */
    public List<LendDetail> getLendDetailList(List<LendDetail> detailList) throws Exception {

        for(LendDetail outDetail : detailList){
            GoodsInfo goodsInfo = getGoodsInfoByID(outDetail.getGoodsid());

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
          //  outDetail.setRealcostprice(getGoodsCostPrice(outDetail.getStorageid(),outDetail.getGoodsid()));
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
        return detailList;

    }

    public String createErrorFile(Map<String, Object> titleMap, List<Map<String, Object>> errorList) throws Exception {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        result.add(titleMap);

        for (Map error : errorList) {
            Map<String, Object> retMap = new LinkedHashMap<String, Object>();
            Map<String, Object> map2 = error;
            for (Map.Entry<String, Object> fentry : titleMap.entrySet()) {
                if (map2.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                    for (Map.Entry<String, Object> entry : map2.entrySet()) {
                        if (fentry.getKey().equals(entry.getKey())) {
                            objectCastToRetMap(retMap, entry);
                        }
                    }
                } else {
                    retMap.put(fentry.getKey(), "");
                }
            }
            result.add(retMap);
        }
        return  createErrorExcelFile(result, "错误-" + getSysUser().getUserid() + "-" + CommonUtils.getDataNumberSendsWithRand());

    }

    public String createErrorExcelFile(List<Map<String, Object>> errorList, String title) throws Exception {

        //文件存放路径
        String path = OfficeUtils.getFilepath() + "/error/" + CommonUtils.getTodayDateStr() + "/";
        File file2 = new File(path);
        //判断文件夹是否存在,如果不存在则创建文件夹
        if (!file2.exists()) {
            file2.mkdir();
        }
        String fileName = title + ".xls";
        File pathdir = new File(path);
        if (!pathdir.exists()) {
            pathdir.mkdirs();
        }
        File file = new File(path, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("sheet1");
        CellStyle intStyle = book.createCellStyle();
        CellStyle decimalStyle = book.createCellStyle();
        CellStyle decimalStyle2 = book.createCellStyle();
        DataFormat format = book.createDataFormat();
        for (int rowNum = 0; rowNum < errorList.size(); rowNum++) {
            HashMap<String, Object> rowMap = (HashMap<String, Object>) errorList.get(rowNum);
            Row row = sheet.createRow(rowNum);
            int cellNum = 0;
            for (Map.Entry<String, Object> entry : rowMap.entrySet()) {
                Cell cell = row.createCell(cellNum++);
                doSetExportExcelCellTypeAndValue(entry.getValue(), cell, entry.getKey(), book, intStyle, decimalStyle, decimalStyle2, format);
            }
        }
        //设置自适应列宽
        if (null != errorList && errorList.size() > 0) {
            HashMap<String, Object> rowMap1 = (HashMap<String, Object>) errorList.get(0);
            int i = 0;
            for (Map.Entry<String, Object> entry : rowMap1.entrySet()) {
                sheet.autoSizeColumn(i);
                int maxColumnWidth = sheet.getColumnWidth(i);
                if (maxColumnWidth == 65280) {
                    sheet.setColumnWidth(i, 5000);//数据过长时设置最大的列宽
                } else {
                    sheet.setColumnWidth(i, maxColumnWidth + 500);
                }

                i++;
            }
        }
        OutputStream out = new FileOutputStream(file);
        book.write(out);
        out.close();

        AttachFile attachFile = new AttachFile();
        attachFile.setExt(".xls");
        attachFile.setFilename(file.getName());
        attachFile.setFullpath("upload/error/" + CommonUtils.getTodayDateStr() + "/" + file.getName());
        attachFile.setOldfilename(file.getName());
        //将临时文件信息插入数据库
        attachFileService.addAttachFile(attachFile);

        return attachFile.getId();
    }


    /**
     * @param object
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-06-13
     */
    private static boolean doSetExportExcelCellTypeAndValue(Object object, Cell cell, String key, Workbook book, CellStyle intStyle, CellStyle decimalStyle, CellStyle decimalStyle2, DataFormat format) throws Exception {
        if (null != object) {
            if (object instanceof String) {
                String result = (String) object;
                cell.setCellValue(result);

            } else if (object instanceof BigDecimal) {
                BigDecimal bignum = (BigDecimal) object;
//                cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                if ("taxprice".equals(key)) {
                    if (null != bignum) {
                        double bignumstr = bignum.doubleValue();
                        String[] strarr = (bignumstr + "").split("\\.");
                        if (strarr.length > 1 && strarr[1].length() <= 2) {
                            decimalStyle.setDataFormat(format.getFormat("0.00"));
                            cell.setCellStyle(decimalStyle);
                        }
                    }
                } else if ("totalweight".equals(key) || "totalvolume".equals(key) || "singlevolume".equals(key) || "glength".equals(key) || "volume".equals(key) ||
                        "ghight".equals(key) || "gwidth".equals(key) || "gdiameter".equals(key) || "grossweight".equals(key) || "netweight".equals(key) || "nowprice".equals(key) || "nowboxprice".equals(key) || "oldprice".equals(key) || "oldboxprice".equals(key)) {
                    decimalStyle2.setDataFormat(format.getFormat("0.000000"));
                    cell.setCellStyle(decimalStyle2);
                } else if (key.contains("num") || key.contains("auxremainder") || key.equals("rate") || key.equals("highestinventory") || key.equals("lowestinventory")) {
                    int decimalScale = BillGoodsNumDecimalLenUtils.decimalLen;
                    if (decimalScale != 0) {
                        bignum = bignum.setScale(decimalScale, BigDecimal.ROUND_HALF_UP);
                    }
                } else if (key.contains("totalbox")) {
                    bignum = bignum.setScale(3, BigDecimal.ROUND_HALF_UP);
                } else {
                    decimalStyle.setDataFormat(format.getFormat("0.00"));
                    cell.setCellStyle(decimalStyle);
                }
                if (null != bignum) {
                    cell.setCellValue(bignum.doubleValue());
                }
            } else if (object instanceof Timestamp) {
                Timestamp timestamp = (Timestamp) object;
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cell.setCellValue(sdf.format(timestamp));
            } else if (object instanceof Date) {
                Date date = (Date) object;
                DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                cell.setCellValue(sdf.format(date));
            } else if (object instanceof Integer) {
                Integer intnum = (Integer) object;
                intStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                cell.setCellStyle(intStyle);
                cell.setCellValue(intnum);
            } else if (object instanceof Long) {
                Long longval = (Long) object;
                intStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
                cell.setCellStyle(intStyle);
                cell.setCellValue(longval);
            }
        } else {
            cell.setCellValue("");
        }
        return true;
    }
}
