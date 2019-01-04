/**
 * @(#)BankAmountAction.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014年11月24日 chenwei 创建版本
 */
package com.hd.agent.account.action;

import com.hd.agent.account.model.*;
import com.hd.agent.account.service.IBankAmountService;
import com.hd.agent.account.service.ICollectionOrderService;
import com.hd.agent.account.service.IPayorderService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.journalsheet.model.DeptCostsSubject;
import com.hd.agent.journalsheet.model.DeptDailyCost;
import com.hd.agent.journalsheet.model.MatcostsInput;
import com.hd.agent.journalsheet.service.ICostsFeeService;
import com.hd.agent.journalsheet.service.IDeptDailyCostService;
import com.hd.agent.journalsheet.service.IJournalSheetService;
import com.hd.agent.system.model.SysCode;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * 银行账户金额相关action
 * @author chenwei
 */
public class BankAmountAction extends BaseFilesAction {
    /**
     *
     */
    private static final long serialVersionUID = 969172294402041770L;
    /**
     * 银行账户期初
     */
    private BankAmountBegin bankAmountBegin;
    /**
     * 银行账户借贷单
     */
    private BankAmountOthers bankAmountOthers;
    /**
     * 银行账户service
     */
    private IBankAmountService bankAmountService;
    /**
     * 付款单service
     */
    private IPayorderService payorderService;
    /**
     * 收款单service
     */
    private ICollectionOrderService collectionOrderService;
    /**
     * 部门日常费用service
     */
    private IDeptDailyCostService deptDailyCostService;
    /**
     * 代垫收回service
     */
    private IJournalSheetService journalSheetService;

    public IJournalSheetService getJournalSheetService() {
        return journalSheetService;
    }

    public void setJournalSheetService(IJournalSheetService journalSheetService) {
        this.journalSheetService = journalSheetService;
    }

    /**
     * 部门费用科目
     */
    private ICostsFeeService costsFeeService ;

    public ICostsFeeService getCostsFeeService() {
        return costsFeeService;
    }

    public void setCostsFeeService(ICostsFeeService costsFeeService) {
        this.costsFeeService = costsFeeService;
    }

    public IDeptDailyCostService getDeptDailyCostService() {
        return deptDailyCostService;
    }

    public void setDeptDailyCostService(IDeptDailyCostService deptDailyCostService) {
        this.deptDailyCostService = deptDailyCostService;
    }

    public ICollectionOrderService getCollectionOrderService() {
        return collectionOrderService;
    }

    public void setCollectionOrderService(ICollectionOrderService collectionOrderService) {
        this.collectionOrderService = collectionOrderService;
    }

    public IPayorderService getPayorderService() {
        return payorderService;
    }

    public void setPayorderService(IPayorderService payorderService) {
        this.payorderService = payorderService;
    }

    public IBankAmountService getBankAmountService() {
        return bankAmountService;
    }

    public void setBankAmountService(IBankAmountService bankAmountService) {
        this.bankAmountService = bankAmountService;
    }

    public BankAmountBegin getBankAmountBegin() {
        return bankAmountBegin;
    }

    public void setBankAmountBegin(BankAmountBegin bankAmountBegin) {
        this.bankAmountBegin = bankAmountBegin;
    }

    public BankAmountOthers getBankAmountOthers() {
        return bankAmountOthers;
    }

    public void setBankAmountOthers(BankAmountOthers bankAmountOthers) {
        this.bankAmountOthers = bankAmountOthers;
    }

    /**
     * 显示银行账户期初列表页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    public String showBankAmountBeginListPage() throws Exception{
        return "success";
    }
    /**
     * 显示银行账户期初添加页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    public String showBankAmountBeginAddPage() throws Exception{
        request.setAttribute("autoCreate", isAutoCreate("t_account_bankamount_begin"));
        request.setAttribute("date", CommonUtils.getTodayDataStr());
        return "success";
    }
    /**
     * 添加银行账户期初
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    @UserOperateLog(key="BankAmountBegin",type=2)
    public String addBankAmountBegin() throws Exception{
        boolean flag = bankAmountService.addBankAmountBegin(bankAmountBegin);
        addJSONObject("flag", flag);
        addLog("添加银行账户期初  编号:"+bankAmountBegin.getId(), flag);
        return "success";
    }
    /**
     * 修改银行账户期初
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    @UserOperateLog(key="BankAmountBegin",type=3)
    public String editBankAmountBegin() throws Exception{
        boolean flag = bankAmountService.editBankAmountBegin(bankAmountBegin);
        addJSONObject("flag", flag);
        addLog("修改银行账户期初  编号:"+bankAmountBegin.getId(), flag);
        return "success";
    }
    /**
     * 显示银行账号期初修改页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    public String showBankAmountBeginEditPage() throws Exception{
        String id = request.getParameter("id");
        BankAmountBegin bankAmountBegin = bankAmountService.getBankAmountBeginById(id);
        request.setAttribute("bankAmountBegin", bankAmountBegin);
        if(null==bankAmountBegin){
            return "addSuccess";
        }else if("2".equals(bankAmountBegin.getStatus()) || "1".equals(bankAmountBegin.getStatus())){
            return "editSuccess";
        }else if("3".equals(bankAmountBegin.getStatus()) || "4".equals(bankAmountBegin.getStatus())){
            return "viewSuccess";
        }
        return "addSuccess";
    }
    /**
     * 显示银行账号期初修改页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    public String showBankAmountBeginViewPage() throws Exception{
        String id = request.getParameter("id");
        BankAmountBegin bankAmountBegin = bankAmountService.getBankAmountBeginById(id);
        request.setAttribute("bankAmountBegin", bankAmountBegin);
        return "success";
    }
    /**
     * 删除银行账户期初
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    @UserOperateLog(key="BankAmountBegin",type=3)
    public String deleteBankAmountBegin() throws Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            for(String id : idArr){
                boolean flag = bankAmountService.deleteBankAmountBegin(id);
                if(flag){
                    succssids += id+",";
                }else{
                    errorids += id+",";
                }

            }
            Map map = new HashMap();
            map.put("flag", true);
            if(!"".equals(succssids)){
                map.put("succssids", "删除成功编号:"+succssids);
            }else{
                map.put("succssids", "");
            }
            if(!"".equals(errorids)){
                map.put("errorids", "删除失败编号:"+errorids);
            }else{
                map.put("errorids", "");
            }
            addJSONObject(map);
            addLog("删除银行账户期初   成功编号："+succssids+";失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return "success";
    }
    /**
     * 获取银行账户期初列表数据
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    public String showBankAmountBeginList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = bankAmountService.showBankAmountBeginList(pageMap);
        addJSONObject(pageData);
        return "success";
    }
    /**
     * 审核银行账户期初
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    @UserOperateLog(key="BankAmountBegin",type=3)
    public String auditBankAmountBegin() throws Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            for(String id : idArr){
                boolean flag = bankAmountService.auditBankAmountBegin(id);
                if(flag){
                    succssids += id+",";
                }else{
                    errorids += id+",";
                }

            }
            Map map = new HashMap();
            map.put("flag", true);
            if(!"".equals(succssids)){
                map.put("succssids", "审核成功编号:"+succssids);
            }else{
                map.put("succssids", "");
            }
            if(!"".equals(errorids)){
                map.put("errorids", "审核失败编号:"+errorids);
            }else{
                map.put("errorids", "");
            }
            addJSONObject(map);
            addLog("审核银行账户期初   成功编号："+succssids+";失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return "success";
    }

    /**
     * 反审银行账户期初
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    @UserOperateLog(key="BankAmountBegin",type=3)
    public String oppauditBankAmountBegin() throws Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            for(String id : idArr){
                boolean flag = bankAmountService.oppauditBankAmountBegin(id);
                if(flag){
                    succssids += id+",";
                }else{
                    errorids += id+",";
                }

            }
            Map map = new HashMap();
            map.put("flag", true);
            if(!"".equals(succssids)){
                map.put("succssids", "反审成功编号:"+succssids);
            }else{
                map.put("succssids", "");
            }
            if(!"".equals(errorids)){
                map.put("errorids", "反审失败编号:"+errorids);
            }else{
                map.put("errorids", "");
            }
            addJSONObject(map);
            addLog("审核银行账户期初   成功编号："+succssids+";失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return "success";
    }
    /**
     * 关闭银行账户期初
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月24日
     */
    @UserOperateLog(key="BankAmountBegin",type=3)
    public String closeBankAmountBegin() throws Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            for(String id : idArr){
                boolean flag = bankAmountService.closeBankAmountBegin(id);
                if(flag){
                    succssids += id+",";
                }else{
                    errorids += id+",";
                }

            }
            Map map = new HashMap();
            map.put("flag", true);
            if(!"".equals(succssids)){
                map.put("succssids", "关闭成功编号:"+succssids);
            }else{
                map.put("succssids", "");
            }
            if(!"".equals(errorids)){
                map.put("errorids", "关闭失败编号:"+errorids);
            }else{
                map.put("errorids", "");
            }
            addJSONObject(map);
            addLog("关闭银行账户期初   成功编号："+succssids+";失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return "success";
    }
    /**
     * 显示银行账户借贷列表页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月26日
     */
    public String showBankAmountOthersListPage() throws Exception{
        request.setAttribute("day1", CommonUtils.getMonthDateStr());
        request.setAttribute("day2", CommonUtils.getTodayDataStr());
        return "success";
    }
    /**
     * 获取银行账户借贷列表数据
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月26日
     */
    public String showBankAmountOthersList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = bankAmountService.showBankAmountOthersList(pageMap);

        List<BankAmountOthers> list = pageData.getList();
        for(BankAmountOthers bankAmountOthers : list){
            //依据单据类型加入对应的对象到备注
            String userName = showUserInfo(bankAmountOthers);
            if(StringUtils.isNotEmpty(userName)){
                bankAmountOthers.setOppname(userName);
            }
        }

        addJSONObjectWithFooter(pageData);
        return "success";
    }
    /**
     * 显示银行账户借贷新增页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月26日
     */
    public String showBankAmountOthersAddPage() throws Exception{
        request.setAttribute("autoCreate", isAutoCreate("t_account_bankamount_others"));
        request.setAttribute("date", CommonUtils.getTodayDataStr());
        return "success";
    }
    /**
     * 显示银行账户借贷修改页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月26日
     */
    public String showBankAmountOthersEditPage() throws Exception{
        String id = request.getParameter("id");
        BankAmountOthers bankAmountOthers = bankAmountService.getBankAmountOthersByID(id);
        request.setAttribute("bankAmountOthers", bankAmountOthers);
        if(null==bankAmountOthers){
            return "addSuccess";
        }else if("2".equals(bankAmountOthers.getStatus()) || "1".equals(bankAmountOthers.getStatus())){
            return "editSuccess";
        }else if("3".equals(bankAmountOthers.getStatus()) || "4".equals(bankAmountOthers.getStatus())){
            return "viewSuccess";
        }
        return "addSuccess";
    }
    /**
     * 显示银行账户借贷查看页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月26日
     */
    public String showBankAmountOthersViewPage() throws Exception{
        String id = request.getParameter("id");
        BankAmountOthers bankAmountOthers = bankAmountService.getBankAmountOthersByID(id);
        request.setAttribute("bankAmountOthers", bankAmountOthers);
        return "success";
    }

    /**
     * 导出银行账户借贷单
     * @throws Exception
     */
    public void exportBankAmountOthers() throws Exception {
        Map queryMap = CommonUtils.changeMap(request.getParameterMap());
        String title = "";
        if(queryMap.containsKey("excelTitle")){
            title = queryMap.get("excelTitle").toString();
        }
        else{
            title = "list";
        }
        queryMap.put("isPageLimit",true);
        pageMap.setCondition(queryMap);
        PageData pageData = bankAmountService.showBankAmountOthersList(pageMap);

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id", "编号");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("bankname", "银行名称");
        firstMap.put("deptname", "所属部门");
        firstMap.put("billtype", "单据类型");
        firstMap.put("billid","单据编号");
        firstMap.put("oaid", "OA编号");
        firstMap.put("oppname", "对方名称");
        firstMap.put("amount1", "借");
        firstMap.put("amount2", "贷");
        firstMap.put("amount", "余额");
        firstMap.put("status", "状态");
        firstMap.put("addusername", "制单人");
        firstMap.put("addtime", "制单时间");
        firstMap.put("auditusername", "审核人");
        firstMap.put("audittime", "审核时间");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        //数据转换，list专程符合excel导出的数据格式
        List<BankAmountOthers> list = pageData.getList();
        for(BankAmountOthers bankAmountOthers : list){
            //依据单据类型加入对应的对象到备注
            String userName = showUserInfo(bankAmountOthers);
            if(StringUtils.isNotEmpty(userName)){
                bankAmountOthers.setOppname(userName);
            }
        }

        if(list.size() != 0){
            for(BankAmountOthers bankAmountOthers : list){
                if("2".equals(bankAmountOthers.getStatus())){
                    bankAmountOthers.setStatus("保存");
                }else if("3".equals(bankAmountOthers.getStatus())){
                    bankAmountOthers.setStatus("审核通过");
                }else if("4".equals(bankAmountOthers.getStatus())){
                    bankAmountOthers.setStatus("关闭");
                }
                if("0".equals(bankAmountOthers.getBilltype())){
                    bankAmountOthers.setBilltype("期初");
                }else if("1".equals(bankAmountOthers.getBilltype())){
                    bankAmountOthers.setBilltype("收款单");
                }else if("2".equals(bankAmountOthers.getBilltype())){
                    bankAmountOthers.setBilltype("付款单");
                }else if("3".equals(bankAmountOthers.getBilltype())){
                    bankAmountOthers.setBilltype("日常费用支付单");
                }else if("4".equals(bankAmountOthers.getBilltype())){
                    bankAmountOthers.setBilltype("客户费用支付");
                }else if("5".equals(bankAmountOthers.getBilltype())){
                    bankAmountOthers.setBilltype("个人借款");
                }else if("6".equals(bankAmountOthers.getBilltype())){
                    bankAmountOthers.setBilltype("预付款");
                }else if("7".equals(bankAmountOthers.getBilltype())){
                    bankAmountOthers.setBilltype("转账");
                }else if("8".equals(bankAmountOthers.getBilltype())){
                    bankAmountOthers.setBilltype("代垫收回");
                }else if("9".equals(bankAmountOthers.getBilltype())){
                    bankAmountOthers.setBilltype("利息");
                }else if("31".equals(bankAmountOthers.getBilltype())){
                    bankAmountOthers.setBilltype("行政采购付款单");
                }else{
                    bankAmountOthers.setBilltype("");
                }

                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map = new HashMap<String, Object>();
                map = PropertyUtils.describe(bankAmountOthers);
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
                if("1".equals(bankAmountOthers.getLendtype())){
                    retMap.put("amount1",bankAmountOthers.getAmount());
                    retMap.put("amount2","");
                }else if("2".equals(bankAmountOthers.getLendtype())){
                    retMap.put("amount1","");
                    retMap.put("amount2",bankAmountOthers.getAmount());
                    retMap.put("amount",bankAmountOthers.getAmount().multiply(new BigDecimal(-1)));
                }
                result.add(retMap);
            }
        }
        ExcelUtils.exportExcel(result, title);
    }

    /**
     * 添加银行账户借贷单
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月27日
     */
    @UserOperateLog(key="BankAmountOthers",type=2)
    public String addBankAmountOthers() throws Exception{
        boolean flag = bankAmountService.addBankAmountOthers(bankAmountOthers);
        addJSONObject("flag", flag);
        addLog("添加银行账户借贷单  编号:"+bankAmountOthers.getId(), flag);
        return "success";
    }
    /**
     * 修改银行账户借贷单
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月27日
     */
    @UserOperateLog(key="BankAmountOthers",type=3)
    public String editBankAmountOthers() throws Exception{
        boolean flag = bankAmountService.editBankAmountOthers(bankAmountOthers);
        addJSONObject("flag", flag);
        addLog("修改银行账户借贷单  编号:"+bankAmountOthers.getId(), flag);
        return "success";
    }
    /**
     * 审核银行账户借贷单
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月27日
     */
    @UserOperateLog(key="BankAmountOthers",type=3)
    public String auditBankAmountOthers() throws Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            for(String id : idArr){
                boolean flag = bankAmountService.auditBankAmountOthers(id);
                if(flag){
                    succssids += id+",";
                }else{
                    errorids += id+",";
                }

            }
            Map map = new HashMap();
            map.put("flag", true);
            if(!"".equals(succssids)){
                map.put("succssids", "审核成功编号:"+succssids);
            }else{
                map.put("succssids", "");
            }
            if(!"".equals(errorids)){
                map.put("errorids", "审核失败编号:"+errorids);
            }else{
                map.put("errorids", "");
            }
            addJSONObject(map);
            addLog("审核银行账户借贷单   成功编号："+succssids+";失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return "success";
    }
    /**
     * 反审银行账户借贷单
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月27日
     */
    @UserOperateLog(key="BankAmountOthers",type=3)
    public String oppauditBankAmountOthers() throws Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            for(String id : idArr){
                boolean flag = bankAmountService.oppauditBankAmountOthers(id);
                if(flag){
                    succssids += id+",";
                }else{
                    errorids += id+",";
                }

            }
            Map map = new HashMap();
            map.put("flag", true);
            if(!"".equals(succssids)){
                map.put("succssids", "反审成功编号:"+succssids);
            }else{
                map.put("succssids", "");
            }
            if(!"".equals(errorids)){
                map.put("errorids", "反审失败编号:"+errorids);
            }else{
                map.put("errorids", "");
            }
            addJSONObject(map);
            addLog("反审银行账户借贷单   成功编号："+succssids+";失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return "success";
    }

    /**
     * 删除银行账户借贷单
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月27日
     */
    @UserOperateLog(key="BankAmountOthers",type=4)
    public String deleteBankAmountOthers() throws Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            for(String id : idArr){
                boolean flag = bankAmountService.deleteBankAmountOthers(id);
                if(flag){
                    succssids += id+",";
                }else{
                    errorids += id+",";
                }

            }
            Map map = new HashMap();
            map.put("flag", true);
            if(!"".equals(succssids)){
                map.put("succssids", "删除成功编号:"+succssids);
            }else{
                map.put("succssids", "");
            }
            if(!"".equals(errorids)){
                map.put("errorids", "删除失败编号:"+errorids);
            }else{
                map.put("errorids", "");
            }
            addJSONObject(map);
            addLog("删除银行账户借贷单   成功编号："+succssids+";失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return "success";
    }
    /**
     * 关闭银行账户借贷单
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月27日
     */
    @UserOperateLog(key="BankAmountOthers",type=3)
    public String closeBankAmountOthers() throws Exception{
        String ids = request.getParameter("ids");
        if(null!=ids){
            String[] idArr = ids.split(",");
            String succssids = "";
            String errorids = "";
            for(String id : idArr){
                boolean flag = bankAmountService.closeBankAmountOthers(id);
                if(flag){
                    succssids += id+",";
                }else{
                    errorids += id+",";
                }

            }
            Map map = new HashMap();
            map.put("flag", true);
            if(!"".equals(succssids)){
                map.put("succssids", "关闭成功编号:"+succssids);
            }else{
                map.put("succssids", "");
            }
            if(!"".equals(errorids)){
                map.put("errorids", "关闭失败编号:"+errorids);
            }else{
                map.put("errorids", "");
            }
            addJSONObject(map);
            addLog("关闭银行账户借贷单   成功编号："+succssids+";失败编号："+errorids, true);
        }else{
            addJSONObject("flag", false);
        }
        return "success";
    }

    /**
     * 显示银行账户余额
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月27日
     */
    public String showBankAmountListPage() throws Exception{
        return "success";
    }
    /**
     * 获取银行账户余额列表
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月27日
     */
    public String showBankAmountList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        addJSONObjectWithFooter(bankAmountService.showBankAmountList(pageMap)) ;
        return "success";
    }
    /**
     * 显示银行账户日志列表页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月27日
     */
    public String showBankAmountLogListPage() throws Exception{
        String id = request.getParameter("id");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        request.setAttribute("date",sdf.format(date));
        request.setAttribute("id", id);

        return "success";
    }
    /**
     * 获取银行账户日志
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年11月27日
     */
    public String showBankAmountLogList() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = bankAmountService.showBankAmountLogList(pageMap);

        List<BankAmountLog> list = pageData.getList();
        for(BankAmountLog log : list) {
            BankAmountOthers bankAmountOthers = bankAmountService.getBankAmountOthersByID(log.getBillid());
            //依据单据类型加入对应的对象到备注
            String userName = showUserInfo(bankAmountOthers);
            log.setRemark(log.getRemark()+""+userName);
        }
        addJSONObject(pageData);
        return "success";
    }

     /**
      *
      * @author lin_xx
      * @date 2016/9/27
      */
    public void exportBankAmountLogList() throws Exception {

        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        queryMap.put("isflag", "true");
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(org.apache.commons.lang3.StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        pageMap.setCondition(queryMap);
        PageData pageData = bankAmountService.showBankAmountLogList(pageMap);

        List<BankAmountLog> list = pageData.getList();
        for(BankAmountLog log : list) {
            SysCode sysCode=getBaseSysCodeService().showSysCodeInfo(log.getBilltype(), "bankAmountOthersBilltype");
            if(null != sysCode){
                String name = sysCode.getCodename();
                log.setBilltype(name);
            }else{
                log.setBilltype("");
            }
            BankAmountOthers bankAmountOthers = bankAmountService.getBankAmountOthersByID(log.getBillid());
            //依据单据类型加入对应的对象到备注
            String userName = showUserInfo(bankAmountOthers);
            log.setRemark(log.getRemark() + "" + userName);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }

    /**
     * 根据单据类型返回里面对应的用户信息
     * @return
     * @throws Exception
     */
    public String showUserInfo(BankAmountOthers bankAmountOthers) throws Exception{

        String userName = "";

        if(null != bankAmountOthers && org.apache.commons.lang3.StringUtils.isNotEmpty(bankAmountOthers.getBillid())){

            String billid = bankAmountOthers.getBillid();

            if(StringUtils.isEmpty(billid)){
                return "";
            }

            if("1".equals(bankAmountOthers.getBilltype())){//收款单
                CollectionOrder collectionOrder = collectionOrderService.getCollectionOrderInfo(billid);
                if(null != collectionOrder){
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(collectionOrder.getCustomername())){
                        userName = collectionOrder.getCustomername();
                    }else{
                        Customer c = getCustomerById(collectionOrder.getCustomerid());
                        if(null != c){
                            userName = c.getName();
                        }
                    }
                }
            }else if("2".equals(bankAmountOthers.getBilltype())){//付款单
                Payorder payorder = payorderService.getPayorderInfo(billid);
                if(null != payorder){
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(payorder.getSuppliername())){
                        userName = payorder.getSuppliername();
                    }else{
                        BuySupplier supplier = getBaseBuySupplierById(payorder.getSupplierid());
                        if(null != supplier)
                            userName = supplier.getName();
                    }
                }
            }else if("3".equals(bankAmountOthers.getBilltype())){//日常费用支付单
                DeptDailyCost deptDailyCost = deptDailyCostService.getDeptDailyCostInfo(billid);
                if(null != deptDailyCost){
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(deptDailyCost.getCostsortname())){
                        userName = deptDailyCost.getCostsortname();
                    }else{
                        DeptCostsSubject costsSubject =costsFeeService.showDeptCostsSubjectById(deptDailyCost.getCostsort());
                        if(null != costsSubject)
                            userName = costsSubject.getName();
                    }
                }
            }else if("8".equals(bankAmountOthers.getBilltype())){//代垫收回

                MatcostsInput matcostsInput = journalSheetService.getMatcostsReimburseDetail(billid);
                if(null != matcostsInput){
                    if(org.apache.commons.lang3.StringUtils.isNotEmpty(matcostsInput.getSuppliername())){
                        userName = matcostsInput.getSuppliername();
                    }else{
                        BuySupplier supplier = getBaseBuySupplierById(matcostsInput.getSupplierid());
                        if(null != supplier)
                            userName = supplier.getName();
                    }
                }
            }
        }
        return userName ;
    }


    /**
     * 显示银行账户转账页面
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年12月16日
     */
    public String showBankAmountTransferPage() throws Exception{
        return "success";
    }
    /**
     * 银行账户转账功能
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年12月16日
     */
    @UserOperateLog(key="BankAmount",type=0)
    public String addBankAmountTransfer() throws Exception{
        String outbankid = request.getParameter("outbankid");
        String inbankid = request.getParameter("inbankid");
        String amountstr = request.getParameter("amount");
        BigDecimal amount = BigDecimal.ZERO;
        if(StringUtils.isNotEmpty(amountstr)){
            amount = new BigDecimal(amountstr);
        }
        boolean flag = bankAmountService.addBankAmountTransfer(outbankid, inbankid, amount);
        addJSONObject("flag", flag);
        addLog("银行账户转账 从银行："+outbankid+"转账到银行："+inbankid+"，金额："+amount, flag);
        return "success";
    }
    /**
     * 获取银行账号信息
     * @return
     * @throws Exception
     * @author chenwei
     * @date 2014年12月17日
     */
    public String getBankAmountInfo() throws Exception{
        String id = request.getParameter("id");
        BankAmount bankAmount = bankAmountService.getBankAmountInfo(id);
        if(null!=bankAmount){
            addJSONObject("amount", bankAmount.getAmount());
        }else{
            addJSONObject("amount", 0);
        }
        return "success";
    }
}

