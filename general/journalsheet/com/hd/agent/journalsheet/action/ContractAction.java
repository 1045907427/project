package com.hd.agent.journalsheet.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.FilesLevelAction;
import com.hd.agent.basefiles.model.CustomerPrice;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.delivery.model.DeliveryAogorder;
import com.hd.agent.delivery.model.DeliveryAogorderDetail;
import com.hd.agent.journalsheet.model.Contract;
import com.hd.agent.journalsheet.model.ContractCaluse;
import com.hd.agent.journalsheet.model.ContractDetail;
import com.hd.agent.journalsheet.model.ContractReport;
import com.hd.agent.journalsheet.service.IContractService;
import com.hd.agent.sales.model.OrderDetail;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghongteng on 2017/11/8.
 */
public class ContractAction extends FilesLevelAction {

    private IContractService contractService;

    public IContractService getContractService() {
        return contractService;
    }

    public void setContractService(IContractService contractService) {
        this.contractService = contractService;
    }

    private ContractCaluse contractCaluse;

    public ContractCaluse getContractCaluse() {
        return contractCaluse;
    }

    public void setContractCaluse(ContractCaluse contractCaluse) {
        this.contractCaluse = contractCaluse;
    }

    private Contract contract;

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    /**
     * 显示客户费用合同条款列表界面
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractCaluseListPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 显示客户费用合同条款列表数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractCaluseListData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = contractService.getContractCaluseListData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 显示客户费用合同条款新增界面
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractCaluseAddPage() throws Exception {
        request.setAttribute("autoCreate", isAutoCreate("t_finance_customer_contract_caluse"));
        List calusetypeList = getBaseSysCodeService().showSysCodeListByType("caluseType");
        request.setAttribute("calusetypeList",calusetypeList);
        return SUCCESS;
    }

    /**
     * 显示客户费用合同条款修改界面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractCaluseEditPage() throws Exception {
        String id = request.getParameter("id");
        ContractCaluse contractCaluse = contractService.getContractCaluseById(id);
        List stateList = getBaseSysCodeService().showSysCodeListByType("state");
        List calusetypeList = getBaseSysCodeService().showSysCodeListByType("caluseType");
        request.setAttribute("calusetypeList",calusetypeList);
        request.setAttribute("contractCaluse",contractCaluse);
        request.setAttribute("stateList",stateList);
        if(!"1".equals(contractCaluse.getState())){
            return "success";
        }else{
            return "successView";
        }
    }

    /**
     * 新增客户费用合同
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @UserOperateLog(key = "contractCaluse", type = 2)
    public String addContractCaluse() throws Exception {
        contractCaluse.setState("2");
        Map map = contractService.addContractCaluse(contractCaluse);
        addLog("客户费用合同条款新增 编号：" + contractCaluse.getId(), (Boolean)map.get("flag"));
        addJSONObject(map);
        return "success";
    }

    /**
     * 修改客户费用合同
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @UserOperateLog(key = "contractCaluse", type = 3)
    public String editContractCaluse() throws Exception {
        Map map = contractService.editContractCaluse(contractCaluse);
        addLog("客户费用合同条款修改 编号：" + contractCaluse.getId(), (Boolean)map.get("flag"));
        addJSONObject(map);
        return "success";
    }

    /**
     * 删除客户费用合同
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @UserOperateLog(key = "contractCaluse", type = 3)
    public String deleteContractCaluse() throws Exception {
        int fnum=0,snum=0;
        String fids="",sids="",msg="",lmsg="";
        boolean flag = false;
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        for(String id : idArr){
            Map resultMap = contractService.deleteContractCaluse(id);
            if((Boolean)resultMap.get("flag")){
                if(StringUtils.isEmpty(sids)){
                    sids="";
                }else {
                    sids = sids+",";
                }
                snum++;
            }else {
                if(StringUtils.isEmpty(fids)){
                    fids="";
                }else {
                    fids = fids+",";
                }
                fnum++;
                if(StringUtils.isEmpty(msg)){
                    msg = "编号："+id+(String)resultMap.get("msg")+"删除失败";
                }else{
                    msg += ",编号："+id+(String)resultMap.get("msg")+"删除失败";
                }
            }
        }

        lmsg = "客户费用合同条款 ";
        if(snum>0){
            lmsg = lmsg+"编号为："+sids+"的数据删除成功,共"+snum+"条。";
        }
        if(fnum>0){
            flag=false;
            msg = msg+",共"+fnum+"条";
            lmsg = lmsg+"编号为："+sids+"的数据删除失败,共"+fnum+"条。";
        }else {
            msg = "所选数据删除成功";
            flag=true;
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        addLog(lmsg, flag);
        addJSONObject(map);
        return "success";
    }

    /**
     * 启用客户费用合同
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @UserOperateLog(key = "contractCaluse", type = 3)
    public String openContractCaluse() throws Exception {
        int fnum=0,snum=0;
        String fids="",sids="",msg="",lmsg="";
        boolean flag = false;
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        for(String id : idArr){
            Map resultMap = contractService.openContractCaluse(id);
            if((Boolean)resultMap.get("flag")){
                if(StringUtils.isEmpty(sids)){
                    sids=id;
                }else {
                    sids = sids+","+id;
                }
                snum++;
            }else {
                if(StringUtils.isEmpty(fids)){
                    fids=id;
                }else {
                    fids = fids+","+id;
                }
                fnum++;
                if(StringUtils.isEmpty(msg)){
                    msg = "编号："+id+(String)resultMap.get("msg")+"启用失败";
                }else{
                    msg += ",编号："+id+(String)resultMap.get("msg")+"启用失败";
                }
            }
        }
        lmsg = "客户费用合同条款 ";
        if(snum>0){
            lmsg = lmsg+"编号为："+sids+"的数据启用成功,共"+snum+"条。";
        }
        if(fnum>0){
            flag=false;
            msg = msg+",共"+fnum+"条";
            lmsg = lmsg+"编号为："+sids+"的数据启用失败,共"+fnum+"条。";
        }else {
            msg = "所选数据启用成功";
            flag=true;
        }

        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        addLog(lmsg, flag);
        addJSONObject(map);
        return "success";
    }

    /**
     * 禁用客户费用合同
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @UserOperateLog(key = "contractCaluse", type = 3)
    public String closeContractCaluse() throws Exception {
        int fnum=0,snum=0;
        String fids="",sids="",msg="",lmsg="";
        boolean flag = false;
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        for(String id : idArr){
            Map resultMap = contractService.closeContractCaluse(id);
            if((Boolean)resultMap.get("flag")){
                if(StringUtils.isEmpty(sids)){
                    sids="";
                }else {
                    sids = sids+",";
                }
                snum++;
            }else {
                if(StringUtils.isEmpty(fids)){
                    fids="";
                }else {
                    fids = fids+",";
                }
                fnum++;
                if(StringUtils.isEmpty(msg)){
                    msg = "编号："+id+(String)resultMap.get("msg")+"禁用失败";
                }else{
                    msg += ",编号："+id+(String)resultMap.get("msg")+"禁用失败";
                }
            }
        }

        lmsg = "客户费用合同条款 ";
        if(snum>0){
            lmsg = lmsg+"编号为："+sids+"的数据禁用成功,共"+snum+"条。";
        }
        if(fnum>0){
            flag=false;
            msg = msg+",共"+fnum+"条";
            lmsg = lmsg+"编号为："+sids+"的数据禁用失败,共"+fnum+"条。";
        }else {
            msg = "所选数据禁用成功";
            flag=true;
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        addLog(lmsg, flag);
        addJSONObject(map);
        return "success";
    }



//    费用合同
    /**
     * 显示费用合同列表页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractListPage() throws Exception {
        return "success";
    }


    /**
     * 显示客户费用合同列表数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractListData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = contractService.getContractListData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }
    /**
     * 显示费用合同新增页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractPage() throws Exception {
        String type = request.getParameter("type");
        String id = request.getParameter("id");
        request.setAttribute("type",type);
        request.setAttribute("id",id);
        return "success";
    }
    /**
     * 费用合同新增页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractAddPage() throws Exception {
        request.setAttribute("autoCreate", isAutoCreate("t_finance_customer_contract"));
        return "success";
    }

    /**
     * 费用合同修改页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractEditPage() throws Exception {
        String id = request.getParameter("id");
        Map map = contractService.getContractById(id);
        Contract contract = (Contract) map.get("contract");
        List<ContractDetail> list = (List) map.get("detailList");
        String jsonStr = JSONUtils.listToJsonStr(list);
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);
        request.setAttribute("contract", contract);
        request.setAttribute("detailList", jsonStr);
        request.setAttribute("listSize", list.size());

        return "success";
    }
    /**
     * 费用合同查看页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractViewPage() throws Exception {
        String id = request.getParameter("id");
        Map map = contractService.getContractById(id);
        Contract contract = (Contract) map.get("contract");
        List<ContractDetail> list = (List) map.get("detailList");
        String jsonStr = JSONUtils.listToJsonStr(list);
        List statusList = getBaseSysCodeService().showSysCodeListByType("status");
        request.setAttribute("statusList", statusList);
        request.setAttribute("contract", contract);
        request.setAttribute("detailList", jsonStr);
        request.setAttribute("listSize", list.size());
        return "success";
    }

    /**
     * 费用合同明细新增页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractDetailAddPage() throws Exception {
        return "success";
    }

    /**
     * 费用合同明细修改页面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractDetailEditPage() throws Exception {
        return "success";
    }
    /**
     * 保存新增的客户费用合同
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @UserOperateLog(key = "Contract", type = 2)
    public String addContractSave() throws Exception {
        contract.setStatus("2");
        String detailJson = request.getParameter("detailJson");
        List detailList = JSONUtils.jsonStrToList(detailJson, new ContractDetail());
        Map map = contractService.addContract(contract, detailList);
        String id = null != map.get("id") ? (String) map.get("id") : "";
        String saveaudit = request.getParameter("saveaudit");
        boolean flag = (Boolean) map.get("flag");
        String msg = "";
        if (flag && "saveaudit".equals(saveaudit)) {
            Map returnmap = contractService.auditContract(contract.getId());
            map.put("auditflag", returnmap.get("flag"));
            msg = (String) returnmap.get("msg");
            map.put("msg", msg);
            addLog("客户费用合同保存审核 编号：" + id, returnmap);
        } else {
            addLog("客户费用合同新增编号"+id, flag);
        }
        addJSONObject(map);
        return "success";
    }
    /**
     * 保存修改的客户费用合同
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @UserOperateLog(key = "Contract", type = 2)
    public String editContractSave() throws Exception {
        contract.setStatus("2");
        String detailJson = request.getParameter("detailJson");
        List detailList = JSONUtils.jsonStrToList(detailJson, new ContractDetail());
        Map map = contractService.editContract(contract, detailList);
        String id = null != map.get("id") ? (String) map.get("id") : "";
        String saveaudit = request.getParameter("saveaudit");
        boolean flag = (Boolean) map.get("flag");
        String msg = "";
        if (flag && "saveaudit".equals(saveaudit)) {
            Map returnmap = contractService.auditContract(contract.getId());
            map.put("auditflag", returnmap.get("flag"));
            msg = (String) returnmap.get("msg");
            map.put("msg", msg);
            addLog("客户费用合同保存审核 编号：" + id, returnmap);
        } else {
            addLog("客户费用合同修改 编号"+id, flag);
        }
        addJSONObject(map);
        return "success";
    }
    /**
     * 删除客户费用合同
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @UserOperateLog(key = "Contract", type = 2)
    public String deleteContract() throws Exception {
        boolean flag = false;
        String msg = "", fmsg = "", smsg = "";
        int fnum = 0, snum = 0;
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        for (String id : idArr) {

                Map map = contractService.deleteContract(id);
                if((Boolean)map.get("flag")){
                    if (org.apache.commons.lang3.StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
                        smsg = id;
                    } else {
                        smsg += "," + id;
                    }
                    snum++;
                }else{
                    if (org.apache.commons.lang3.StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
                        fmsg = id;
                    } else {
                        fmsg += "," + id;
                    }
                    fnum++;
                    if(StringUtils.isEmpty(msg)){
                        msg = "编号："+id+(String)map.get("msg")+"删除失败";
                    }else{
                        msg += ",编号："+id+(String)map.get("msg")+"删除失败";
                    }
                }
        }
        String lmsg = "客户费用合同删除 ";
        if (snum != 0) {
            flag=true;
            lmsg += "编号：" + smsg + "删除成功。共" + snum + "条数据。";
        }
        if (fnum != 0) {
            msg += ",共" + fnum + "条数据<br>";
            lmsg += "编号：" + fmsg + "删除失败。共" + fnum + "条数据。";
        }else {
            msg = "所选数据删除成功";
            flag=true;
        }
        addLog(lmsg, flag);
        Map returnMap = new HashMap();
        returnMap.put("flag", flag);
        returnMap.put("msg", msg);
        addJSONObject(returnMap);
        return "success";
    }

    /**
     * 审核客户费用合同
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @UserOperateLog(key = "Contract", type = 2)
    public String auditContract() throws Exception {
        boolean flag = false;
        String msg = "", fmsg = "", smsg = "";
        int fnum = 0, snum = 0;
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        for (String id : idArr) {

            Map map = contractService.auditContract(id);
            if((Boolean)map.get("flag")){
                if (org.apache.commons.lang3.StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
                    smsg = id;
                } else {
                    smsg += "," + id;
                }
                snum++;

            }else{
                if (org.apache.commons.lang3.StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
                    fmsg = id;
                } else {
                    fmsg += "," + id;
                }
                fnum++;
                if(StringUtils.isEmpty(msg)){
                    msg = "编号："+id+(String)map.get("msg")+"审核失败";
                }else{
                    msg += ",编号："+id+(String)map.get("msg")+"审核失败";
                }
            }
        }
        String lmsg = "客户费用合同审核 ";
        if (snum != 0) {
            flag=true;
            lmsg += "编号：" + smsg + "审核成功。共" + snum + "条数据。";
        }
        if (fnum != 0) {
            msg += ",共" + fnum + "条数据<br>";
            lmsg += "编号：" + fmsg + "审核失败。共" + fnum + "条数据。";
        }else {
            msg = "所选数据审核成功";
            flag=true;
        }
        addLog(lmsg, flag);
        Map returnMap = new HashMap();
        returnMap.put("flag", flag);
        returnMap.put("msg", msg);
        addJSONObject(returnMap);
        return "success";
    }

    /**
     * 反审客户费用合同
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @UserOperateLog(key = "Contract", type = 2)
    public String oppauditContract() throws Exception {
        boolean flag = false;
        String msg = "", fmsg = "", smsg = "";
        int fnum = 0, snum = 0;
        String ids = request.getParameter("ids");
        String[] idArr = ids.split(",");
        for (String id : idArr) {

            Map map = contractService.oppauditContract(id);
            if((Boolean)map.get("flag")){
                if (org.apache.commons.lang3.StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
                    smsg = id;
                } else {
                    smsg += "," + id;
                }
                snum++;
            }else{
                if (org.apache.commons.lang3.StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
                    fmsg = id;
                } else {
                    fmsg += "," + id;
                }
                fnum++;
                if(StringUtils.isEmpty(msg)){
                    msg = "编号："+id+(String)map.get("msg")+"反审失败";
                }else{
                    msg += ",编号："+id+(String)map.get("msg")+"反审失败";
                }
            }
        }
        String lmsg = "客户费用合同反审 ";
        if (snum != 0) {
            flag=true;
            lmsg += "编号：" + smsg + "反审成功。共" + snum + "条数据。";
        }
        if (fnum != 0) {
            msg += ",共" + fnum + "条数据<br>";
            lmsg += "编号：" + fmsg + "反审失败。共" + fnum + "条数据。";
        }else {
            msg = "所选数据反审成功";
            flag=true;
        }
        addLog(lmsg, flag);
        Map returnMap = new HashMap();
        returnMap.put("flag", flag);
        returnMap.put("msg", msg);
        addJSONObject(returnMap);
        return "success";
    }



    /**
     * 检测是否存在相关联的门店或者总店
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String checkCorrelationCustomer() throws Exception {
        String customerid = request.getParameter("customerid");
        Map map = contractService.checkCorrelationCustomer(customerid);
        addJSONObject(map);
        return "success";
    }




    /**
     * 显示客户费用汇总统计报表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractReportListPage() throws Exception {
        String month = CommonUtils.getTodayMonStr();
        request.setAttribute("month",month);
        return "success";
    }

    /**
     * 显示合同费用生成界面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractReportAddPage() throws Exception {
        String month = CommonUtils.getTodayMonStr();
        request.setAttribute("month",month);
        return "success";
    }
    /**
     * 显示合同费用统计明细界面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractReportDetailPage() throws Exception {
        String month = request.getParameter("month");
        String customerid = request.getParameter("customerid");
        String state = request.getParameter("state");
        request.setAttribute("month",month);
        request.setAttribute("customerid",customerid);
        request.setAttribute("state",state);
        return "success";
    }

    /**
     * 根据月份客户编号获取合同费用统计明细数据
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String getContractReportDetailDataByMonthAndCustomerid() throws Exception {
        String month = request.getParameter("month");
        String customerid = request.getParameter("customerid");
        Map map = contractService.getContractReportDetailDataByMonthAndCustomerid(month,customerid);
        addJSONObject(map);
        return "success";
    }
    /**
     * 生成客户合同费用
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    @UserOperateLog(key = "ContractReport", type = 2)
    public String addContractReport() throws Exception {
        boolean flag = false;
        String lmsg = "";
        String month = request.getParameter("month");
        String customerid = request.getParameter("customerid");
        String deptid= request.getParameter("deptid");
        String type= request.getParameter("type");
        Map map = contractService.addContractReport(month,type,customerid);
        flag=(Boolean)map.get("flag");
        if(flag){
            lmsg = "客户费用合同生成统计数据 客户编号为"+customerid+"的"+month+"数据 生成成功！ ";
        }else{
            lmsg = "客户费用合同生成统计数据 客户编号为"+customerid+"的"+month+"数据 生成失败！ ";
        }
        addLog(lmsg, flag);
        map.put("month",month);
        map.put("customerid",customerid);
        addJSONObject(map);
        return "success";
    }
    /**
     * 保存客户合同费用统计报表明细
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String editContractReportDataList() throws Exception {
        boolean flag = false;
        String msg = "", fmsg = "", smsg = "";
        int fnum = 0, snum = 0;
        String contractReportListJson = request.getParameter("contractReportListJson");
        List<ContractReport> contractReportList = JSONUtils.jsonStrToList(contractReportListJson, new ContractReport());
        for (ContractReport contractReport : contractReportList) {
            Map map = contractService.editContractReportDetail(contractReport);
            if((Boolean)map.get("flag")){
                snum++;
            }else{
                fnum++;
            }
        }
        if(snum>0){
            flag=true;
        }

        Map resultMap = new HashMap();
        resultMap.put("flag",flag);
        addJSONObject(resultMap);

        return "success";
    }


    /**
     * 显示客户费用合同列表数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showAddContractReportData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = contractService.getAddContractReportData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }


    /**
     * 显示客户费用合同列表数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractReportData() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = contractService.getContractReportData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 保存行修改的客户合同费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-24
     */
    @UserOperateLog(key="ContractReport",type=3,value="")
    public String editContractReport() throws Exception{
        String rowsjsonstr = request.getParameter("rowsjsonstr");
        boolean retflag = true;
        String sucgoodsids = "",unsucgoodsids = "",customerid = "";
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(rowsjsonstr)){
            JSONArray array = JSONArray.fromObject(rowsjsonstr);
            List<ContractReport> list = array.toList(array, ContractReport.class);
            for(ContractReport contractReport : list){
                Map map = contractService.editContractReport(contractReport);
                if(org.apache.commons.lang3.StringUtils.isNotEmpty(sucgoodsids)){
                    addLog("修改客户合同费用 合同编号:"+contractReport.getContractid()+"条款编码:"+contractReport.getContractcaluseid(),true);
                }else{
                    addLog("修改客户合同费用 合同编号:"+contractReport.getContractid()+"条款编码:"+contractReport.getContractcaluseid(),false);
                }
            }

        }
        addJSONObject("flag", retflag);
        return SUCCESS;
    }


    /**
     * 确认客户合同费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-24
     */
    @UserOperateLog(key="ContractReport",type=3,value="")
    public String auditContractReport() throws Exception{
        boolean flag = false;
        String msg = "", fmsg = "", smsg = "";
        int fnum = 0, snum = 0;
        String month = request.getParameter("month");
        String customerids = request.getParameter("customerids");

        String sucgoodsids = "",unsucgoodsids = "";
        if(StringUtils.isNotEmpty(customerids)){
            String customeridArr[] = customerids.split(",");
            for(String  customerid : customeridArr){
                Map map = contractService.auditContractReport(month,customerid);
                if((Boolean)map.get("flag")){
                    if (org.apache.commons.lang3.StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
                        smsg = customerid;
                    } else {
                        smsg += "," + customerid;
                    }
                    snum++;
                }else{
                    if (org.apache.commons.lang3.StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
                        fmsg = customerid;
                    } else {
                        fmsg += "," + customerid;
                    }
                    fnum++;
                }
            }

        }
        String lmsg = "确认客户合同费用统计数据 月份:"+month;
        if (snum != 0) {
            flag=true;
            lmsg += " 客户编号：" + smsg + "确认成功。共" + snum + "条数据。";
        }
        if (fnum != 0) {
            msg = " 客户编号：" + fmsg + "确认失败。共" + fnum + "条数据<br>";
            lmsg += " 客户编号：" + fmsg + "确认失败。共" + fnum + "条数据。";
        }
        addLog(lmsg, flag);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 取消确认客户合同费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-24
     */
    @UserOperateLog(key="ContractReport",type=3,value="")
    public String oppauditContractReport() throws Exception{
        boolean flag = false;
        String msg = "", fmsg = "", smsg = "";
        int fnum = 0, snum = 0;
        String customerids = request.getParameter("customerids");
        String month = request.getParameter("month");
        boolean retflag = true;
        String sucgoodsids = "",unsucgoodsids = "";
        if(StringUtils.isNotEmpty(customerids)){
            String customeridArr[] = customerids.split(",");
            for(String  customerid : customeridArr){
                Map map = contractService.oppauditContractReport(month,customerid);
                if((Boolean)map.get("flag")){
                    if (org.apache.commons.lang3.StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
                        smsg = customerid;
                    } else {
                        smsg += "," + customerid;
                    }
                    snum++;
                }else{
                    if (org.apache.commons.lang3.StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
                        fmsg = customerid;
                    } else {
                        fmsg += "," + customerid;
                    }
                    fnum++;
                }
            }
        }
        String lmsg = "取消确认客户合同费用统计数据 月份:"+month;
        if (snum != 0) {
            flag=true;
            lmsg += "客户编号：" + smsg + "确认成功。共" + snum + "条数据。";
        }
        if (fnum != 0) {
            msg = "客户编号：" + fmsg + "确认失败。共" + fnum + "条数据<br>";
            lmsg += "客户编号：" + fmsg + "确认失败。共" + fnum + "条数据。";
        }
        addLog(lmsg, flag);
        addJSONObject("flag", flag);
        return SUCCESS;
    }
    /**
     * 生成客户应付费用和代垫录入费用
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-24
     */
    @UserOperateLog(key="ContractReport",type=3,value="")
    public String CreatPayableAndMatcost() throws Exception{
        boolean flag = false;
        String msg = "", fmsg = "", smsg = "";
        int fnum = 0, snum = 0;
        String customerids = request.getParameter("customerids");
        String month = request.getParameter("month");
        String sucgoodsids = "",unsucgoodsids = "";
        if(StringUtils.isNotEmpty(customerids)){
            String customeridArr[] = customerids.split(",");
            for(String  customerid : customeridArr){
                Map map = contractService.CreatPayableAndMatcost(month,customerid);
                if((Boolean)map.get("flag")){
                    if (org.apache.commons.lang3.StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
                        smsg = customerid;
                    } else {
                        smsg += "," + customerid;
                    }
                    snum++;
                }else{
                    if (org.apache.commons.lang3.StringUtils.isEmpty(smsg) && (Boolean) map.get("flag")) {
                        fmsg = customerid;
                    } else {
                        fmsg += "," + customerid;
                    }
                    fnum++;
                }
            }

        }
        String lmsg = "客户合同费用统计数据生成代垫借贷数据 月份:"+month ;
        if (snum != 0) {
            flag=true;
            lmsg += "客户编号：" + smsg + "生成成功。共" + snum + "条数据。";
        }
        if (fnum != 0) {
            msg = "客户编号：" + fmsg + "生成失败。共" + fnum + "条数据<br>";
            lmsg += "客户编号：" + fmsg + "生成失败。共" + fnum + "条数据。";
        }
        addLog(lmsg, flag);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

//客户费用权责金额


    /**
     * 显示客户费用合同权责统计报表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractLiabilityPage() throws Exception {
        String month =CommonUtils.getTodayMonStr();
        request.setAttribute("month",month);
        return "success";
    }

    /**
     * 显示客户费用权责金额数据
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017-11-8
     */
    public String showContractLiabilityData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = contractService.getContractLiabilityData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }
}
