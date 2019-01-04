/**
 * @(#)JournalSheetAction.java
 * @author panxiaoxiao
 * <p/>
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Jun 14, 2013 panxiaoxiao 创建版本
 */
package com.hd.agent.journalsheet.action;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.hd.agent.common.service.IExcelService;
import com.hd.agent.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.action.FilesLevelAction;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.DepartMent;
import com.hd.agent.basefiles.service.IBuyService;
import com.hd.agent.basefiles.service.IDepartMentService;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.journalsheet.model.ApprovalPrice;
import com.hd.agent.journalsheet.model.CapitalInput;
import com.hd.agent.journalsheet.model.ExpensesEntering;
import com.hd.agent.journalsheet.model.FundInput;
import com.hd.agent.journalsheet.model.MatcostsInput;
import com.hd.agent.journalsheet.model.ReimburseInput;
import com.hd.agent.journalsheet.service.IJournalSheetService;
import com.hd.agent.system.model.SysCode;

/**
 * @author panxiaoxiao
 */
public class JournalSheetAction extends FilesLevelAction {

    private IJournalSheetService journalSheetService;
    private IBuyService buyService;
    private IDepartMentService departMentService;
    private IExcelService excelService;

    private ExpensesEntering expensesEntering;

    private ApprovalPrice approvalPrice;

    private CapitalInput capitalInput;

    private FundInput fundInput;

    private ReimburseInput reimburseInput;

    private MatcostsInput matcostsInput;

    public FundInput getFundInput() {
        return fundInput;
    }

    public void setFundInput(FundInput fundInput) {
        this.fundInput = fundInput;
    }

    public ApprovalPrice getApprovalPrice() {
        return approvalPrice;
    }

    public void setApprovalPrice(ApprovalPrice approvalPrice) {
        this.approvalPrice = approvalPrice;
    }

    public ReimburseInput getReimburseInput() {
        return reimburseInput;
    }

    public void setReimburseInput(ReimburseInput reimburseInput) {
        this.reimburseInput = reimburseInput;
    }

    public MatcostsInput getMatcostsInput() {
        return matcostsInput;
    }

    public void setMatcostsInput(MatcostsInput matcostsInput) {
        this.matcostsInput = matcostsInput;
    }

    public CapitalInput getCapitalInput() {
        return capitalInput;
    }

    public void setCapitalInput(CapitalInput capitalInput) {
        this.capitalInput = capitalInput;
    }

    public IJournalSheetService getJournalSheetService() {
        return journalSheetService;
    }

    public void setJournalSheetService(IJournalSheetService journalSheetService) {
        this.journalSheetService = journalSheetService;
    }

    public IExcelService getExcelService() {
        return excelService;
    }

    public void setExcelService(IExcelService excelService) {
        this.excelService = excelService;
    }
    /*----------------------------------资金录入---------------------------------------------*/

    /**
     * 显示资金录入页面
     */
    public String capitalInputPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 显示资金录入新增页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 14, 2013
     */
    public String capitalInputAddPage() throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(new Date());
        request.setAttribute("date", date);
        String mindate = CommonUtils.getBeforeDateInDays(7);
        request.setAttribute("mindate", mindate);
        return SUCCESS;
    }

    /**
     * 显示资金录入修改页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 14, 2013
     */
    public String capitalInputEditPage() throws Exception {
        String id = request.getParameter("id");
        CapitalInput capitalInput = journalSheetService.getCapitalInputDetail(id);
        request.setAttribute("capitalInput", capitalInput);
        return SUCCESS;
    }

    /**
     * 显示资金录入详情页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 14, 2013
     */
    public String capitalInputViewPage() throws Exception {
        String id = request.getParameter("id");
        Map colMap = getAccessColumn("t_js_capitalinput");
        CapitalInput capitalInput = journalSheetService.getCapitalInputDetail(id);
        if (null != capitalInput) {
            request.setAttribute("showMap", colMap);
            request.setAttribute("capitalInput", capitalInput);
        }
        return SUCCESS;
    }

    /**
     * 获取资金录入列表
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 14, 2013
     */
    public String getCapitalInputListPage() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.getCapitalInputListPage(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 新增资金录入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 14, 2013
     */
    @UserOperateLog(key = "CapitalInput", type = 2, value = "")
    public String addCapitalInput() throws Exception {
        SysUser sysUser = getSysUser();
        capitalInput.setAdduserid(sysUser.getUserid());
        capitalInput.setAddusername(sysUser.getName());
        boolean flag = journalSheetService.addCapitalInput(capitalInput);
        addJSONObject("flag", flag);
        addLog("新增资金录入 编号:" + capitalInput.getId(), flag);
        return SUCCESS;
    }

    /**
     * 修改资金录入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 14, 2013
     */
    public String editCapitalInput() throws Exception {
        boolean flag = journalSheetService.editCapitalInput(capitalInput);
        addJSONObject("flag", flag);
        addLog("修改资金录入 编号:" + capitalInput.getId(), flag);
        return SUCCESS;
    }

    /**
     * 删除资金录入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 14, 2013
     */
    public String deleteCapitalInput() throws Exception {
        String id = request.getParameter("id");
        boolean delFlag = canTableDataDelete("t_js_capitalinput", id);
        if (!delFlag) {
            addJSONObject("delFlag", true);
            return SUCCESS;
        }
        boolean flag = journalSheetService.deleteCapitalInput(id);
        addLog("删除资金录入 编号:" + id, flag);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

	/*---------------------------------资金录入(t_js_fundinput)---------------------------*/

    /**
     * 根据供应商编码获取该供应商资金录入最大业务日期做最小业务日期
     *
     * @return
     * @throws Exception
     */
    public String getMaxDateFundInput() throws Exception {
        String supplierid = request.getParameter("supplierid");
        String maxdate = journalSheetService.getMaxDateFundInput(supplierid);
        addJSONObject("mindate", maxdate);
        return SUCCESS;
    }

    /**
     * 显示资金录入页面
     */
    public String fundInputPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 显示资金录入新增页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 2, 2013
     */
    public String fundInputAddPage() throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(new Date());
        request.setAttribute("date", date);
        String mindate = CommonUtils.getBeforeDateInDays(7);
        request.setAttribute("mindate", mindate);
        return SUCCESS;
    }

    /**
     * 显示资金录入修改页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 2, 2013
     */
    public String fundInputEditPage() throws Exception {
        String id = request.getParameter("id");
        FundInput fundInput = journalSheetService.getFundInputDetail(id);
        request.setAttribute("fundInput", fundInput);
        String mindate = journalSheetService.getMaxDateFundInput(fundInput.getSupplierid());
        request.setAttribute("mindate", mindate);
        return SUCCESS;
    }

    /**
     * 显示资金录入详情页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 2, 2013
     */
    public String fundInputViewPage() throws Exception {
        String id = request.getParameter("id");
        Map colMap = getAccessColumn("t_js_fundinput");
        FundInput fundInput = journalSheetService.getFundInputDetail(id);
        if (null != fundInput) {
            request.setAttribute("showMap", colMap);
            request.setAttribute("fundInput", fundInput);
        }
        return SUCCESS;
    }

    /**
     * 获取资金录入列表
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 2, 2013
     */
    public String getFundInputListPage() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.getFundInputListPage(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 判断是否存在资金录入，且输入的日期是否大于最大日期
     *
     * @return
     * @throws Exception
     */
    public String checkFundInputByMap() throws Exception {
        String supplierid = request.getParameter("supplierid");
        String businessdate = request.getParameter("businessdate");
        boolean flag = journalSheetService.checkFundInputByMap(supplierid, businessdate);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 新增资金录入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 2, 2013
     */
    @UserOperateLog(key = "fundInput", type = 2, value = "")
    public String addFundInput() throws Exception {
        boolean flag = journalSheetService.addFundInput(fundInput);
        addJSONObject("flag", flag);
        addLog("新增资金录入 编号:" + fundInput.getId(), flag);
        return SUCCESS;
    }

    /**
     * 修改资金录入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 2, 2013
     */
    @UserOperateLog(key = "fundInput", type = 3, value = "")
    public String editFundInput() throws Exception {
        boolean flag = journalSheetService.editFundInput(fundInput);
        addJSONObject("flag", flag);
        addLog("修改资金录入 编号:" + fundInput.getId(), flag);
        return SUCCESS;
    }

    /**
     * 删除资金录入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 2, 2013
     */
    @UserOperateLog(key = "fundInput", type = 4, value = "")
    public String deleteFundInput() throws Exception {
        String id = request.getParameter("id");
        boolean delFlag = canTableDataDelete("t_js_fundinput", id);
        if (!delFlag) {
            addJSONObject("delFlag", true);
            return SUCCESS;
        }
        boolean flag = journalSheetService.deleteFundInput(id);
        addLog("删除资金录入 编号:" + id, flag);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 导入资金录入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-03
     */
    @UserOperateLog(key = "FundInput", type = 2, value = "资金录入导入")
    public String addDRFundInput() throws Exception {
        Map<String, Object> retMap = new HashMap<String, Object>();
        try {
            Object object2 = SpringContextUtils.getBean("journalSheetService");
            Class entity = Class.forName("com.hd.agent.journalsheet.model.FundInput");
            Method[] methods = object2.getClass().getMethods();
            Method method = null;
            for (Method m : methods) {
                if (m.getName().equals("addDRFundInput")) {
                    method = m;
                }
            }
            List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
            List<String> paramList2 = new ArrayList<String>();
            for (String str : paramList) {
                if ("业务日期".equals(str)) {
                    paramList2.add("businessdate");
                } else if ("供应商编码".equals(str)) {
                    paramList2.add("supplierid");
                } else if ("供应商名称".equals(str)) {
                    paramList2.add("supplierName");
                } else if ("所属部门".equals(str)) {
                    paramList2.add("supplierdeptName");
                } else if ("预付金额".equals(str)) {
                    paramList2.add("advanceamount");
                } else if ("库存金额".equals(str)) {
                    paramList2.add("stockamount");
                } else if ("应收金额".equals(str)) {
                    paramList2.add("receivableamount");
                } else if ("代垫金额".equals(str)) {
                    paramList2.add("actingmatamount");
                } else if ("应付金额".equals(str)) {
                    paramList2.add("payableamount");
                } else if ("代垫未收".equals(str)) {
                    paramList2.add("norecactingmat");
                } else if ("费用未付".equals(str)) {
                    paramList2.add("norecexpenses");
                } else if ("库存折差".equals(str)) {
                    paramList2.add("stockdiscount");
                } else if ("本期代垫".equals(str)) {
                    paramList2.add("currentactingmat");
                } else if ("汇款收回".equals(str)) {
                    paramList2.add("remittancerecovery");
                } else if ("货补收回".equals(str)) {
                    paramList2.add("goodsrecovery");
                } else {
                    paramList2.add("null");
                }
            }

            if (paramList.size() == paramList2.size()) {
                List result = new ArrayList();
                List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
                if (list.size() != 0) {
                    Map detialMap = new HashMap();
                    for (Map<String, Object> map4 : list) {

                        String supplierid = (String) map4.get("supplierid");
                        BuySupplier buySupplier = getBuyService().showBuySupplier(supplierid);
                        map4.put("supplierdeptid", buySupplier.getBuydeptid());

                        Object object = entity.newInstance();
                        Field[] fields = entity.getDeclaredFields();
                        //获取的导入数据格式转换
                        DRCastTo(map4, fields);
                        //BeanUtils.populate(object, map4);
                        PropertyUtils.copyProperties(object, map4);
                        result.add(object);
                    }
                    if (result.size() != 0) {
                        retMap = excelService.insertSalesOrder(object2, result, method);
                    }
                } else {
                    retMap.put("excelempty", true);
                }
            } else {
                retMap.put("versionerror", true);
            }

        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("error", true);
        }
        addJSONObject(retMap);
        return SUCCESS;
    }

    /**
     * 导出资金录入
     *
     * @throws Exception
     * @author panxiaoxiao
     * @date 2015-12-03
     */
    public void exportFundInputList() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", true);
        pageMap.setCondition(map);
        String title = "";
        if (map.containsKey("excelTitle")) {
            title = map.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("businessdate", "业务日期");
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("supplierName", "供应商名称");
        firstMap.put("supplierdeptName", "所属部门");
        firstMap.put("advanceamount", "预付金额");
        firstMap.put("stockamount", "库存金额");
        firstMap.put("receivableamount", "应收金额");
        firstMap.put("actingmatamount", "代垫金额");
        firstMap.put("payableamount", "应付金额");
        firstMap.put("totalamount", "合计金额");
        firstMap.put("norecactingmat", "代垫未收");
        firstMap.put("norecexpenses", "费用未付");
        firstMap.put("stockdiscount", "库存折差");
        firstMap.put("currentactingmat", "本期代垫");
        firstMap.put("remittancerecovery", "汇款收回");
        firstMap.put("goodsrecovery", "货补收回");
        result.add(firstMap);

        PageData pageData = journalSheetService.getFundInputListPage(pageMap);
        List<FundInput> list = pageData.getList();

        //使用内部类重写sort方法
        Collections.sort(list, new Comparator<FundInput>() {
            @Override
            public int compare(FundInput o1, FundInput o2) {
                return o1.getSupplierid().compareTo(o2.getSupplierid());//根据供应商编码排序（从小到大）
            }
        });
        list.addAll(pageData.getFooter());

        for (FundInput fundInput1 : list) {
            Map<String, Object> retMap = new LinkedHashMap<String, Object>();
            Map<String, Object> map2 = new HashMap<String, Object>();
            map2 = PropertyUtils.describe(fundInput1);
            for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
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
        ExcelUtils.exportExcel(result, title);
    }

	/*---------------------------------资金统计报表------------------------------------------------------*/

    public String fundStatisticsSheetPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 获取资金录入列表
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jul 2, 2013
     */
    public String getFundStatisticsSheetList() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.getFundStatisticsSheetList(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    public String capitalStatisticsSheetPage() throws Exception {
        List list = getBaseSysCodeService().showSysCodeListByType("pricesubject");
        request.setAttribute("codeList", list);
        return SUCCESS;
    }

    /**
     * 获取资金统计报表
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 14, 2013
     */
    public String getCapitalStatisticsSheetList() throws Exception {
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            Map map = CommonUtils.changeMap(request.getParameterMap());
            pageMap.setCondition(map);
            PageData pageData = journalSheetService.getCapitalStatisticsSheetList(pageMap);
            if (pageData.getList().size() > 0) {
                addJSONObjectWithFooter(pageData);
            } else {
                addJSONObject(pageData);
            }
            return SUCCESS;
        } else {
            String[] idArr = id.split("_");
            String supplierid = idArr[0];
            String supplierdeptid = idArr[1];
            String begintime = idArr[2];
            String endtime = idArr[3];
            Map map = new HashMap();
            map.put("supplierid", supplierid);
            if (!"null".equals(supplierdeptid)) {
                map.put("supplierdeptid", supplierdeptid);
            }
            if (!"null".equals(begintime)) {
                map.put("begintime", begintime);
            }
            if (!"null".equals(endtime)) {
                map.put("endtime", endtime);
            }
            List list = journalSheetService.getCapitalStatisticsDetailList(map);
            addJSONArray(list);
            return "treeSuccess";
        }
    }

	/*---------------------资金平均金额统计报表----------------------------------------------*/

    /**
     * 显示资金平均金额页面
     */
    public String fundAverageStatisticsSheetPage() throws Exception {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-01");
        Date firstDay = new Date();
        request.setAttribute("firstDay", format1.format(firstDay));
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-" + maxDay);
        request.setAttribute("lastDay", format2.format(cal.getTime()));
        return SUCCESS;
    }

    /**
     * 获取资金平均金额列表
     */
    public String getFundAverageStatisticsSheetList() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.getFundAverageStatisticsParentList(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 显示资金平均统计详情报表页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 21, 2014
     */
    public String fundAverageStatisticsSheetDetailPage() throws Exception {
        String supplierid = request.getParameter("supplierid");
        String suppliername = request.getParameter("suppliername");
        String begintime = request.getParameter("begintime");
        String endtime = request.getParameter("endtime");
        request.setAttribute("supplierid", supplierid);
        request.setAttribute("suppliername", suppliername);
        request.setAttribute("begintime", begintime);
        request.setAttribute("endtime", endtime);
        return SUCCESS;
    }

    /**
     * 获取资金平均统计详情报表数据
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 21, 2014
     */
    public String getFundAverageStatisticsSheetDetailList() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.getFundAverageStatisticsDetailList(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

	/*-------------------------------------货款录入--------------------------------------------------*/

    /**
     * 显示货款录入页面
     */
    public String expensesEnteringPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 显示货款录入新增页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 30, 2013
     */
    public String expensesEnteringAddPage() throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(new Date());
        request.setAttribute("date", date);
        return SUCCESS;
    }

    /**
     * 显示货款录入修改页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 30, 2013
     */
    public String expensesEnteringEditPage() throws Exception {
        String id = request.getParameter("id");
        ExpensesEntering expensesEntering = journalSheetService.getExpensesEnteringDetail(id);
        Map map = new HashMap();
        map.put("id", expensesEntering.getSupplierid());
        BuySupplier supplier = getBaseBuyService().getBuySupplierBy(map);
        request.setAttribute("expensesEntering", expensesEntering);
        request.setAttribute("supplier", supplier);
        return SUCCESS;
    }

    /**
     * 显示货款录入详情页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 30, 2013
     */
    public String expensesEnteringViewPage() throws Exception {
        String id = request.getParameter("id");
        Map colMap = getAccessColumn("t_finance_expensesentering");
        ExpensesEntering expensesEntering = journalSheetService.getExpensesEnteringDetail(id);
        if (expensesEntering != null) {
            request.setAttribute("showMap", colMap);
            request.setAttribute("expensesEntering", expensesEntering);
        }
        return SUCCESS;
    }

    /**
     * 获取货款录入列表
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 30, 2013
     */
    public String getExpensesEnteringListPage() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.getExpensesEnteringListPage(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 新增货款录入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 30, 2013
     */
    @UserOperateLog(key = "ExpensesEntering", type = 2, value = "")
    public String addExpensesEntering() throws Exception {
        SysUser sysUser = getSysUser();
        expensesEntering.setAdduserid(sysUser.getUserid());
        expensesEntering.setAddusername(sysUser.getName());
        boolean flag = journalSheetService.addExpensesEntering(expensesEntering);
        addJSONObject("flag", flag);
        addLog("新增货款录入 编号:" + expensesEntering.getId(), flag);
        return SUCCESS;
    }

    /**
     * 修改货款录入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 30, 2013
     */
    @UserOperateLog(key = "ExpensesEntering", type = 3, value = "")
    public String editExpensesEntering() throws Exception {
        boolean flag = journalSheetService.editExpensesEntering(expensesEntering);
        addJSONObject("flag", flag);
        addLog("修改货款录入 编号:" + expensesEntering.getId(), flag);
        return SUCCESS;
    }

    /**
     * 删除货款录入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 30, 2013
     */
    @UserOperateLog(key = "ExpensesEntering", type = 4, value = "")
    public String deleteExpensesEntering() throws Exception {
        String ids = request.getParameter("ids");
        String sucids = "";
        boolean flag = true;
        if (StringUtils.isNotEmpty(ids)) {
            String[] idsArr = ids.split(",");
            for (String id : idsArr) {
                boolean delFlag = canTableDataDelete("t_finance_expensesentering", id);
                if (!delFlag) {
                    addJSONObject("delFlag", true);
                    return SUCCESS;
                }
                boolean retflag = journalSheetService.deleteExpensesEntering(id);
                flag = flag && retflag;
                if (flag) {
                    if (StringUtils.isEmpty(sucids)) {
                        sucids = id;
                    } else {
                        sucids += "," + id;
                    }
                } else {

                }
            }
            addLog("删除货款录入 编号:" + sucids, true);
        }
        addJSONObject("flag", flag);
        return SUCCESS;
    }

	/*-------------------------------------分科目开单流水账--------------------------------------------------*/

    /**
     * 显示分科目开单流水账页面
     */
    public String subjectdayaccountPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 获取分科目开单流水账列表
     *
     * @return
     * @throws Excepion
     * @author panxiaoxiao
     * @date May 31, 2013
     */
    public String getSubjectDayAccountListPage() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.getSubjectDayAccountList(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 显示分客户开单流水账页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 31, 2013
     */
    public String clienteledayaccountPage() throws Exception {
        List list = getBaseSysCodeService().showSysCodeListByType("subjectid");
        request.setAttribute("codeList", list);
        return SUCCESS;
    }

    /**
     * 获取分客户开单流水账列表
     *
     * @return
     * @throws Excepion
     * @author panxiaoxiao
     * @date May 31, 2013
     */
    public String getClienteleDayAccountList() throws Exception {
        String id = request.getParameter("id");
        if (null == id || "".equals(id)) {
            Map map = CommonUtils.changeMap(request.getParameterMap());
            if (map.containsKey("subjectid") && map.get("subjectid") != null) {
                String[] array = map.get("subjectid").toString().split(",");
                map.put("array", array);
            }
            pageMap.setCondition(map);
            Map returnMap = journalSheetService.getClienteleDayAccountList(pageMap);
            addJSONObject(returnMap);
            return SUCCESS;
        } else {
            String[] idArr = id.split("_");
            String supplierid = idArr[0];
            String begintime = idArr[1];
            String endtime = idArr[2];
            String subjectid = idArr[3];
            Map map = new HashMap();
            map.put("supplierid", supplierid);
            if (!"null".equals(begintime)) {
                map.put("begintime", begintime);
            }
            if (!"null".equals(endtime)) {
                map.put("endtime", endtime);
            }
            if (!"null".equals(subjectid)) {
                map.put("subjectid", subjectid);
            }
            List list = journalSheetService.getClienteleDayAccountDetailList(map);
            addJSONArray(list);
            return "treeSuccess";

        }
    }

    /**
     * 显示统计报表页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 31, 2013
     */
    public String statisticalstatementPage() throws Exception {
        List list = getBaseSysCodeService().showSysCodeListByType("subjectid");
        request.setAttribute("codeList", list);
        return SUCCESS;
    }

    /**
     * 货款统计报表列表
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date May 31, 2013
     */
    public String getStatisticslist() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.getStatisticslist(pageMap);
        if (pageData.getList().size() > 0) {
            addJSONObjectWithFooter(pageData);
        } else {
            addJSONObject(pageData);
        }
        return SUCCESS;
    }

    /**
     * 显示货款统计明细列表页面
     *
     * @return
     * @throws Exception
     * @author chenwei
     * @date Dec 10, 2013
     */
    public String showStatisticsDetaillistPage() throws Exception {
        String id = request.getParameter("id");
        String version = request.getParameter("version");
        String[] idArr = id.split("_");
        String supplierid = idArr[0];
        String supplierdeptid = idArr[1];
        String begintime = idArr[2];
        String endtime = idArr[3];
        request.setAttribute("supplierid", supplierid);
        request.setAttribute("version", version);
        if (!"null".equals(supplierdeptid)) {
            request.setAttribute("supplierdeptid", supplierdeptid);
        }
        if (!"null".equals(begintime)) {
            request.setAttribute("begintime", begintime);
        }
        if (!"null".equals(endtime)) {
            request.setAttribute("endtime", endtime);
        }
        return "success";
    }

    /**
     * 获取货款明细列表数据
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Mar 5, 2014
     */
    public String getStatisticsDetaillist() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.getStatisticsDetailList(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

	/*-----------------------------核准金额--------------------------------------------------------------*/

    /**
     * 显示核准金额页面
     */
    public String approvalPricePage() throws Exception {
        return SUCCESS;
    }

    /**
     * 获取核准金额列表
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 1, 2013
     */
    public String getApprovalPriceList() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.getApprovalPriceList(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 显示核准金额新增页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 1, 2013
     */
    public String supplierListAddPage() throws Exception {
        String idsStr = request.getParameter("idsStr");
        request.setAttribute("idsStr", idsStr);
        return SUCCESS;
    }

    /**
     * 为核准金额获取供应商列表，供应商不重复
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 1, 2013
     */
    public String getSupplierListForApproval() throws Exception {
        List list = journalSheetService.getSupplierListForApproval();
        addJSONArray(list);
        return SUCCESS;
    }

    /**
     * 新增核准金额
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 1, 2013
     */
    public String addApprovalPrice() throws Exception {
        String idsArr = request.getParameter("idarrs");
        if (StringUtils.isNotEmpty(idsArr)) {
            boolean flag = journalSheetService.addApprovalPrice(idsArr);
            addJSONObject("flag", flag);
        }
        return SUCCESS;
    }

    /**
     * 显示金额修改页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 3, 2013
     */
    public String apPriceEditPage() throws Exception {
        String id = request.getParameter("id");
        ApprovalPrice approvalPrice = journalSheetService.getApprovalPriceDetail(id);
        if (null != approvalPrice) {
            if (StringUtils.isNotEmpty(approvalPrice.getSupplierid())) {
                BuySupplier buySupplier = buyService.showBuySupplier(approvalPrice.getSupplierid());
                if (buySupplier != null) {
                    request.setAttribute("supplierName", buySupplier.getName());
                }
            }
            if (StringUtils.isNotEmpty(approvalPrice.getSupplierdeptid())) {
                DepartMent departMent = departMentService.showDepartMentInfo(approvalPrice.getSupplierdeptid());
                if (departMent != null) {
                    request.setAttribute("supplierdeptName", departMent.getName());
                }
            }
            request.setAttribute("editFlag", canTableDataDelete("t_finance_approvalprice", null));
            request.setAttribute("approvalPrice", approvalPrice);
        }
        request.setAttribute("id", id);
        return SUCCESS;
    }

    /**
     * 修改核准金额
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 3, 2013
     */
    public String editApprovalPrice() throws Exception {
        boolean flag = journalSheetService.editApprovalPrice(approvalPrice);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 删除核准金额
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 3, 2013
     */
    public String deleteapprovalPrice() throws Exception {
        String id = request.getParameter("id");
        boolean delFlag = canTableDataDelete("t_finance_approvalprice", id);
        if (!delFlag) {
            addJSONObject("delFlag", true);
            return SUCCESS;
        }
        boolean flag = journalSheetService.deleteApprovalPrice(id);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    public IBuyService getBuyService() {
        return buyService;
    }

    public void setBuyService(IBuyService buyService) {
        this.buyService = buyService;
    }

    public IDepartMentService getDepartMentService() {
        return departMentService;
    }

    public void setDepartMentService(IDepartMentService departMentService) {
        this.departMentService = departMentService;
    }

    public ExpensesEntering getExpensesEntering() {
        return expensesEntering;
    }

    public void setExpensesEntering(ExpensesEntering expensesEntering) {
        this.expensesEntering = expensesEntering;
    }

	/*-----------------------------代垫录入----------------------------------------------------*/

    /**
     * 显示代垫录入页面
     */
    public String reimburseInputPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 显示代垫录入新增页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 26, 2013
     */
    public String showReimburseInputAddPage() throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(new Date());
        request.setAttribute("businessdate", date);
        return SUCCESS;
    }

    /**
     * 显示代垫录入修改页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 26, 2013
     */
    public String showReimburseInputEditPage() throws Exception {
        String id = request.getParameter("id");
        ReimburseInput reimburseInput = journalSheetService.getReimberseInputDetail(id);
        request.setAttribute("reimburseInput", reimburseInput);
        return SUCCESS;
    }

    /**
     * 显示代垫录入详情页面
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 26, 2013
     */
    public String showReimburseInputViewPage() throws Exception {
        String id = request.getParameter("id");
        Map colMap = getAccessColumn("t_js_reimburseinput");
        ReimburseInput reimburseInput = journalSheetService.getReimberseInputDetail(id);
        if (null != reimburseInput) {
            request.setAttribute("showMap", colMap);
            request.setAttribute("reimburseInput", reimburseInput);
        }
        return SUCCESS;
    }

    /**
     * 删除代垫录入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 26, 2013
     */
    @UserOperateLog(key = "ReimburseInput", type = 4, value = "")
    public String deleteReimburseInput() throws Exception {
        String id = request.getParameter("id");
        boolean delFlag = canTableDataDelete("t_js_reimburseinput", id);
        if (!delFlag) {
            addJSONObject("delFlag", true);
            return SUCCESS;
        }
        boolean flag = journalSheetService.deleteReimburseInput(id);
        addLog("删除代垫录入 编号:" + id, flag);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 获取代垫录入列表分页
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 26, 2013
     */
    public String getReimburseInputListPage() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.getReimburseInputListPage(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 新增代垫录入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 26, 2013
     */
    @UserOperateLog(key = "ReimburseInput", type = 2, value = "")
    public String addReimburseInput() throws Exception {
        SysUser sysUser = getSysUser();
        reimburseInput.setAdduserid(sysUser.getUserid());
        reimburseInput.setAddusername(sysUser.getName());
        boolean flag = journalSheetService.addReimburseInput(reimburseInput);
        addJSONObject("flag", flag);
        addLog("新增代垫录入 编号:" + reimburseInput.getId(), flag);
        return SUCCESS;
    }

    /**
     * 修改代垫录入
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 26, 2013
     */
    @UserOperateLog(key = "ReimburseInput", type = 3, value = "")
    public String editReimburseInput() throws Exception {
        boolean flag = journalSheetService.editReimburseInput(reimburseInput);
        addJSONObject("flag", flag);
        addLog("修改代垫录入 编号:" + reimburseInput.getId(), flag);
        return SUCCESS;
    }

	/*-----------------------------代垫统计报表-----------------------------------------*/

    /**
     * 显示代垫统计报表页面
     */
    public String reimburseStatisticsSheetPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 获取代垫统计报表
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Jun 14, 2013
     */
    public String getreimburseStatisticsSheetList() throws Exception {
        String id = request.getParameter("id");
        if (StringUtils.isEmpty(id)) {
            Map map = CommonUtils.changeMap(request.getParameterMap());
            pageMap.setCondition(map);
            PageData pageData = journalSheetService.getRIStatisticsSheetFirstList(pageMap);
            if (pageData.getList().size() > 0) {
                addJSONObjectWithFooter(pageData);
            } else {
                addJSONObject(pageData);
            }
            return SUCCESS;
        } else if (id.indexOf("First") != -1) {
            //第二层展开，品牌
            String[] idArr = id.split("_");
            String supplierid = idArr[0];
            String supplierdeptid = idArr[1];
            String brandid = idArr[2];
            String subjectid = idArr[3];
            String begintime = idArr[4];
            String endtime = idArr[5];
            Map map2 = new HashMap();
            map2.put("supplierid", supplierid);
            map2.put("secondPid", id);
            if (!"null".equals(supplierdeptid)) {
                map2.put("supplierdeptid", supplierdeptid);
            }
            if (!"null".equals(brandid)) {
                map2.put("brandid", brandid);
            }
            if (!"null".equals(subjectid)) {
                map2.put("subjectid", subjectid);
            }
            if (!"null".equals(begintime)) {
                map2.put("begintime", begintime);
            }
            if (!"null".equals(endtime)) {
                map2.put("endtime", endtime);
            }
            pageMap.setCondition(map2);
            List secondList = journalSheetService.getRIStatisticsSheetSecondList(pageMap);
            addJSONArray(secondList);
            return "treeSuccess";
        }//第三层展开 ,科目
        else if (id.indexOf("Second") != -1) {
            String[] idArr = id.split("_");
            String supplierid = idArr[0];
            String supplierdeptid = idArr[1];
            String brandid = idArr[2];
            String subjectid = idArr[3];
            String begintime = idArr[4];
            String endtime = idArr[5];
            Map map2 = new HashMap();
            map2.put("supplierid", supplierid);
            map2.put("brandid", brandid);
            map2.put("thirdPid", id);
            if (!"null".equals(supplierdeptid)) {
                map2.put("supplierdeptid", supplierdeptid);
            }
            if (!"null".equals(subjectid)) {
                map2.put("subjectid", subjectid);
            }
            if (!"null".equals(begintime)) {
                map2.put("begintime", begintime);
            }
            if (!"null".equals(endtime)) {
                map2.put("endtime", endtime);
            }
            pageMap.setCondition(map2);
            List thirdList = journalSheetService.getRIStatisticsSheetThirdList(pageMap);
            addJSONArray(thirdList);
            return "treeSuccess";
        } else {
            String[] idArr = id.split("_");
            String supplierid = idArr[0];
            String supplierdeptid = idArr[1];
            String brandid = idArr[2];
            String subjectid = idArr[3];
            String begintime = idArr[4];
            String endtime = idArr[5];
            Map map2 = new HashMap();
            map2.put("supplierid", supplierid);
            map2.put("brandid", brandid);
            map2.put("subjectid", subjectid);
            map2.put("endPid", id);
            if (!"null".equals(supplierdeptid)) {
                map2.put("supplierdeptid", supplierdeptid);
            }
            if (!"null".equals(begintime)) {
                map2.put("begintime", begintime);
            }
            if (!"null".equals(endtime)) {
                map2.put("endtime", endtime);
            }
            List list = journalSheetService.getRIStatisticsSheetDetailList(map2);
            addJSONArray(list);
            return "treeSuccess";
        }
    }

    /**
     * 导出-货款统计报表
     *
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 30, 2013
     */
    public void exportStatisticsData() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", "true");
        pageMap.setCondition(map);
        String title = "";
        if (map.containsKey("excelTitle")) {
            title = map.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        PageData pageData = journalSheetService.getStatisticslist(pageMap);
        ExcelUtils.exportExcel(exportStatisticsDataFilter(pageData.getList(), pageData.getFooter()), title);
    }

    /**
     * 数据转换，list专程符合excel导出的数据格式(货款统计报表)
     *
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 29, 2013
     */
    private List<Map<String, Object>> exportStatisticsDataFilter(List<Map<String, Object>> list, List<Map<String, Object>> footerList) throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("suppliername", "供应商名称");
        firstMap.put("supplierdeptName", "所属部门");
        List<SysCode> codelist = getBaseSysCodeService().showSysCodeListByType("subjectid");
        if (codelist.size() != 0) {
            for (SysCode sysCode : codelist) {
                firstMap.put("subjectid" + sysCode.getCode(), sysCode.getCodename());
            }
        }
        firstMap.put("lastAmount", "合计金额");
        firstMap.put("approvalAmount", "核准金额");
        firstMap.put("realatamount", "实际占用金额");
        firstMap.put("settleamount", "结算金额");
        result.add(firstMap);

        if (list.size() != 0) {
            for (Map<String, Object> map : list) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        if (footerList.size() != 0) {
            for (Map<String, Object> map : footerList) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        return result;
    }

    /**
     * 导出-资金统计报表
     *
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 30, 2013
     */
    public void exportFundStatisticsData() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("state", "1");
        map.put("isflag", "true");
        pageMap.setCondition(map);
        pageMap.setOrderSql("businessdate,supplierdeptid asc");
        String title = "";
        if (map.containsKey("excelTitle")) {
            title = map.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        PageData pageData = journalSheetService.getFundStatisticsSheetList(pageMap);
        List<FundInput> list = pageData.getList();
        list.addAll(pageData.getFooter());
        ExcelUtils.exportExcel(exportFundStatisticsDataFilter(list), title);
    }

    /**
     * 数据转换，list专程符合excel导出的数据格式(资金统计报表)
     *
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 29, 2013
     */
    private List<Map<String, Object>> exportFundStatisticsDataFilter(List<FundInput> list) throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("businessdate", "业务日期");
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("supplierName", "供应商名称");
        firstMap.put("supplierdeptName", "所属部门");
        firstMap.put("advanceamount", "预付金额");
        firstMap.put("stockamount", "库存金额");
        firstMap.put("receivableamount", "应收金额");
        firstMap.put("actingmatamount", "代垫金额");
        firstMap.put("payableamount", "应付金额");
        firstMap.put("totalamount", "合计金额");
        firstMap.put("stockdiscount", "库存折差");
        firstMap.put("norecactingmat", "代垫未收");
        firstMap.put("norecexpenses", "费用未付");
        firstMap.put("sumamount", "累计金额");
        firstMap.put("currentactingmat", "本期代垫");
        firstMap.put("sumactingmat", "累计代垫");
        firstMap.put("remittancerecovery", "汇款收回");
        firstMap.put("goodsrecovery", "货补收回");
        firstMap.put("sumreceivable", "累计已收");
        firstMap.put("sumnorec", "累计未收");
        result.add(firstMap);

        //去掉最后的合计项
        FundInput count = list.remove(list.size() - 1);

        //使用内部类重写sort方法
        Collections.sort(list, new Comparator<FundInput>() {
            @Override
            public int compare(FundInput o1, FundInput o2) {
                return o1.getSupplierid().compareTo(o2.getSupplierid());//根据供应商编码排序（从小到大）
            }
        });

        list.add(count);

        if (list.size() != 0) {
            for (FundInput fundInput : list) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map = new HashMap<String, Object>();
                map = PropertyUtils.describe(fundInput);
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        return result;
    }

    /**
     * 重新生成报表
     *
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date 2016-01-17
     */
    public String doResetFundStatisticsSheetList() throws Exception {
        boolean flag = journalSheetService.doResetFundStatisticsSheetList();
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 导出-代垫统计报表
     *
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 30, 2013
     */
    public void exportReimburseStatisticsData() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", "true");
        pageMap.setCondition(map);
        String title = "";
        if (map.containsKey("excelTitle")) {
            title = map.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        PageData pageData = journalSheetService.getRIStatisticsSheetFirstList(pageMap);
        ExcelUtils.exportExcel(exportRIStatisticsDataFilter(pageData.getList(), pageData.getFooter()), title);
    }

    /**
     * 数据转换，list专程符合excel导出的数据格式(代垫统计报表)
     *
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 29, 2013
     */
    private List<Map<String, Object>> exportRIStatisticsDataFilter(List<Map<String, Object>> list, List<Map<String, Object>> footerList) throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("suppliername", "供应商名称");
        firstMap.put("planamount", "计划金额");
        firstMap.put("takebackamount", "收回金额");
        firstMap.put("untakebackamount", "未收回金额");
        firstMap.put("actingmatamount", "代垫金额");
        firstMap.put("surplusamount", "盈余金额");
        result.add(firstMap);

        if (list.size() != 0) {
            for (Map<String, Object> map : list) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        if (footerList.size() != 0) {
            for (Map<String, Object> map : footerList) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        return result;
    }

    /**
     * 导出-资金平均统计报表
     *
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 30, 2013
     */
    public void exportFundAverageStatisticsData() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", "true");
        pageMap.setCondition(map);
        String title = "";
        if (map.containsKey("excelTitle")) {
            title = map.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        PageData pageData = journalSheetService.getFundAverageStatisticsParentList(pageMap);
        List<FundInput> list = new ArrayList<FundInput>();
        list.addAll(pageData.getList());
        list.addAll(pageData.getFooter());
        ExcelUtils.exportExcel(exportFundAverageStatisticsDataFilter(list, 0), title);
    }

    /**
     * 导出某供应商资金平均统计报表
     *
     * @throws Exception
     * @author panxiaoxiao
     * @date Feb 21, 2014
     */
    public void exportFundAverageStatisticsDetailData() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isflag", "true");
        pageMap.setCondition(map);
        String title = "";
        if (map.containsKey("excelTitle")) {
            title = map.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        PageData pageData = journalSheetService.getFundAverageStatisticsDetailList(pageMap);
        List<FundInput> list = new ArrayList<FundInput>();
        list.addAll(pageData.getList());
        list.addAll(pageData.getFooter());
        ExcelUtils.exportExcel(exportFundAverageStatisticsDataFilter(list, 1), title);
    }

    public List<Map<String, Object>> exportFundAverageStatisticsDataFilter(List<FundInput> list, int type) throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("supplierName", "供应商名称");
        firstMap.put("supplierdeptName", "所属部门");
        if (1 == type) {
            firstMap.put("businessdate", "业务日期");
        }
        firstMap.put("advanceamount", "预付金额");
        firstMap.put("stockamount", "库存金额");
        firstMap.put("receivableamount", "应收金额");
        firstMap.put("actingmatamount", "代垫金额");
        firstMap.put("payableamount", "应付金额");
        firstMap.put("totalamount", "合计金额");
        firstMap.put("stockdiscount", "库存折差");
        firstMap.put("norecactingmat", "代垫未收");
        firstMap.put("norecexpenses", "费用未付");
        firstMap.put("sumamount", "累计金额");
        firstMap.put("currentactingmat", "本期代垫");
        firstMap.put("sumactingmat", "累计代垫");
        firstMap.put("remittancerecovery", "汇款收回");
        firstMap.put("goodsrecovery", "货补收回");
        firstMap.put("sumreceivable", "累计已收");
        firstMap.put("sumnorec", "累计未收");
        result.add(firstMap);
        if (list.size() != 0) {
            for (FundInput fundInput : list) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(fundInput);
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
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
        }
        return result;
    }
/*-----------------------------代垫录入----------------------------------------------------*/

    /**
     * 显示代垫录入页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    public String matcostsInputPage() throws Exception {
        request.setAttribute("firstday", CommonUtils.getMonthDateStr());
        request.setAttribute("today", CommonUtils.getTodayDataStr());
        List<SysCode> reimbursetypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        request.setAttribute("reimbursetypeList", reimbursetypeList);
        return SUCCESS;
    }

    /**
     * 显示代垫录入新增页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    public String showMatcostsInputAddPage() throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(new Date());
        request.setAttribute("businessdate", date);
        return SUCCESS;
    }

    /**
     * 显示代垫录入修改页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    public String showMatcostsInputEditPage() throws Exception {
        String id = request.getParameter("id");
        MatcostsInput matcostsInput = journalSheetService.getMatcostsInputDetail(id);
        if (null != matcostsInput) {
            request.setAttribute("matcostsInput", matcostsInput);
            if ("1".equals(matcostsInput.getHcflag())
                    || "2".equals(matcostsInput.getHcflag())
                    || "1".equals(matcostsInput.getIswriteoff())
                    || "2".equals(matcostsInput.getIswriteoff())) {
                return "ViewSuccess";
            }
        } else {
            return "AddSuccess";
        }
        return SUCCESS;
    }

    /**
     * 显示代垫录入红冲新增页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    public String showMatcostsInputHCAddPage() throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(new Date());
        request.setAttribute("businessdate", date);

        String id = request.getParameter("id");
        MatcostsInput matcostsInput = journalSheetService.getMatcostsInputDetail(id);
        if (null != matcostsInput) {
            request.setAttribute("matcostsInput", matcostsInput);

            if ("1".equals(matcostsInput.getHcflag())
                    || "2".equals(matcostsInput.getHcflag())
                    || "1".equals(matcostsInput.getIswriteoff())
                    || "2".equals(matcostsInput.getIswriteoff())) {
                return "ViewSuccess";
            }

            BigDecimal factory = matcostsInput.getFactoryamount();
            BigDecimal expense = matcostsInput.getExpense();

            if (null != factory) {
                factory = factory.negate();
            } else {
                factory = BigDecimal.ZERO;
            }
            request.setAttribute("factory", factory);
            if (null != expense) {
                expense = expense.negate();
            } else {
                expense = BigDecimal.ZERO;
            }
            request.setAttribute("expense", expense);
        }
        return SUCCESS;
    }

    /**
     * 显示代垫录入详情页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    public String showMatcostsInputViewPage() throws Exception {
        String id = request.getParameter("id");
        Map colMap = getAccessColumn("t_js_matcostsinput");
        MatcostsInput matcostsInput = journalSheetService.getMatcostsInputDetail(id);
        if (null != matcostsInput) {
            request.setAttribute("showMap", colMap);
            request.setAttribute("matcostsInput", matcostsInput);
        }
        return SUCCESS;
    }

    /**
     * 删除代垫录入
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    @UserOperateLog(key = "MatcostsInput", type = 4)
    public String deleteMatcostsInput() throws Exception {
        String id = request.getParameter("id");
        boolean delFlag = canTableDataDelete("t_js_matcostsinput", id);
        if (!delFlag) {
            addJSONObject("delFlag", true);
            return SUCCESS;
        }
        boolean flag = journalSheetService.deleteMatcostsInput(id);
        addLog("删除代垫录入 编号:" + id, flag);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 删除代垫录入
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    @UserOperateLog(key = "MatcostsInput", type = 4)
    public String deleteMatcostsInputMore() throws Exception {
        String idarrs = request.getParameter("idarrs");
        Map map = journalSheetService.deleteMatcostsInputMore(idarrs);
        Boolean flag = false;
        if (null != map) {
            flag = (Boolean) map.get("flag");
            if (null == flag) {
                flag = false;
            }
            addLog("批量删除代垫录入 编号:" + idarrs, flag);
        } else {
            addLog("批量删除代垫录入 编号失败:" + idarrs);
        }
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 获取代垫录入列表分页
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    public String getMatcostsInputPageList() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("showDyncReimburse", "false");    //显示收回方式动态列表
        pageMap.setCondition(map);
        String showAllData = (String) map.get("showAllData");
        if ("true".equals(showAllData)) {
            map.put("isPageflag", "true");
        } else {
            map.put("isPageflag", "false");
        }
        if (map.containsKey("isExportData")) {
            map.remove("isExportData");
        }
        PageData pageData = journalSheetService.getMatcostsInputPageList(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 新增代垫录入
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    @UserOperateLog(key = "MatcostsInput", type = 2, value = "")
    public String addMatcostsInput() throws Exception {
        SysUser sysUser = getSysUser();
        Map map = new HashMap();
        if (BigDecimal.ZERO.compareTo(matcostsInput.getFactoryamount()) > 0) {
            map.put("flag", "false");
            map.put("msg", "工厂投入为负数，请在代垫收回处新增");
            return SUCCESS;
        }
        matcostsInput.setAdduserid(sysUser.getUserid());
        matcostsInput.setAddusername(sysUser.getName());
        matcostsInput.setReimburseamount(BigDecimal.ZERO);
        matcostsInput.setReimbursetype("");
        matcostsInput.setSourcefrome("0");
        boolean flag = journalSheetService.addMatcostsInput(matcostsInput);
        addJSONObject("flag", flag);
        StringBuffer logSb = new StringBuffer();
        logSb.append("新增代垫录入");
        if (StringUtils.isNotEmpty(matcostsInput.getId())) {
            logSb.append("编号：");
            logSb.append(matcostsInput.getId());
        } else {
            if (StringUtils.isNotEmpty(matcostsInput.getBusinessdate())) {
                logSb.append("业务日期:" + matcostsInput.getBusinessdate());
            }
            if (StringUtils.isNotEmpty(matcostsInput.getOaid())) {
                logSb.append("OA编号:" + matcostsInput.getOaid());
            }
            if (StringUtils.isNotEmpty(matcostsInput.getSupplierid())) {
                logSb.append("供应商编号:" + matcostsInput.getSupplierid());
            }
        }
        addLog(logSb.toString(), flag);
        return SUCCESS;
    }

    /**
     * 修改代垫录入
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    @UserOperateLog(key = "MatcostsInput", type = 3, value = "")
    public String editMatcostsInput() throws Exception {
        Map map = new HashMap();
        if (BigDecimal.ZERO.compareTo(matcostsInput.getFactoryamount()) > 0) {
            map.put("flag", "false");
            map.put("msg", "工厂投入不能为负数");
            return SUCCESS;
        }
        matcostsInput.setReimburseamount(BigDecimal.ZERO);
        matcostsInput.setReimbursetype("");
        boolean flag = journalSheetService.editMatcostsInput(matcostsInput);
        addJSONObject("flag", flag);
        addLog("修改代垫录入 编号:" + matcostsInput.getId(), flag);
        return SUCCESS;
    }

    /**
     * 新增代垫红冲
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    @UserOperateLog(key = "MatcostsInput", type = 2, value = "")
    public String addMatcostsInputHC() throws Exception {
        Map map = new HashMap();
        if (StringUtils.isEmpty(matcostsInput.getHcreferid())) {
            map.put("flag", "false");
            map.put("msg", "抱歉，未能找到关联的代垫");
            return SUCCESS;
        }
        Map resultMap = journalSheetService.addMatcostsInputHC(matcostsInput);
        Boolean flag = false;
        if (null == resultMap) {
            resultMap = new HashMap();
            resultMap.put("flag", false);
            flag = false;
        } else {
            flag = (Boolean) resultMap.get("flag");
            if (null == flag) {
                flag = false;
                resultMap.put("flag", false);
            }
        }
        addJSONObject(resultMap);
        StringBuffer logSb = new StringBuffer();
        logSb.append("新增代垫红冲");
        if (StringUtils.isNotEmpty(matcostsInput.getId())) {
            logSb.append("编号：");
            logSb.append(matcostsInput.getId());
        } else {
            if (StringUtils.isNotEmpty(matcostsInput.getBusinessdate())) {
                logSb.append("业务日期:" + matcostsInput.getBusinessdate());
            }
            if (StringUtils.isNotEmpty(matcostsInput.getOaid())) {
                logSb.append("OA编号:" + matcostsInput.getOaid());
            }
            if (StringUtils.isNotEmpty(matcostsInput.getSupplierid())) {
                logSb.append("供应商编号:" + matcostsInput.getSupplierid());
            }
        }
        addLog(logSb.toString(), flag);
        return SUCCESS;
    }

    /**
     * 撤销代垫红冲
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    @UserOperateLog(key = "MatcostsInput", type = 0, value = "")
    public String removeMatcostsInputHC() throws Exception {
        String id = request.getParameter("id");
        Map resultMap = new HashMap();
        if (null == id || "".equals(id.trim())) {
            resultMap.put("flag", "false");
            resultMap.put("msg", "抱歉，未能找到关联的代垫");
            addLog("抱歉，未能找到关联的代垫");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        StringBuffer logSb = new StringBuffer();
        logSb.append("撤销代垫红冲");
        resultMap = journalSheetService.removeMatcostsInputHC(id);
        Boolean flag = false;
        if (null == resultMap) {
            resultMap = new HashMap();
            resultMap.put("flag", false);
            flag = false;
        } else {
            flag = (Boolean) resultMap.get("flag");
            if (null == flag) {
                flag = false;
                resultMap.put("flag", false);
            }
        }
        addLog(logSb.toString(), flag);
        addJSONObject(resultMap);
        return SUCCESS;
    }

    /**
     * 导出-代垫统计报表
     *
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 30, 2013
     */
    public void exportMatcostsInputData() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isPageflag", "true");
        map.put("showDyncReimburse", "false");    //显示收回方式动态列表
        map.put("isExportData", "true");    //是否导出数据
        pageMap.setCondition(map);
        String title = "";
        if (map.containsKey("excelTitle")) {
            title = map.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        PageData pageData = journalSheetService.getMatcostsInputPageList(pageMap);
        ExcelUtils.exportExcel(exportMatcostsInputDataFilter(pageData.getList(), pageData.getFooter()), title);
    }

    /**
     * 数据转换，list专程符合excel导出的数据格式(代垫表)
     *
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 29, 2013
     */
    private List<Map<String, Object>> exportMatcostsInputDataFilter(List<Map> list, List<Map> footerList) throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id", "编码");
        firstMap.put("businessdate", "业务日期");
        firstMap.put("paydate", "支付日期");
        firstMap.put("takebackdate", "收回日期");
        firstMap.put("oaid", "OA编号");
        firstMap.put("brandid", "品牌编码");
        firstMap.put("brandname", "品牌名称");
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("suppliername", "供应商名称");
        firstMap.put("supplierdeptname", "供应商所属部门");
        firstMap.put("customerid", "客户编码");
        firstMap.put("customername", "客户名称");
        firstMap.put("customersort", "客户分类编码");
        firstMap.put("customersortname", "客户分类名称");
        firstMap.put("transactorid", "经办人编码");
        firstMap.put("transactorname", "经办人名称");
        firstMap.put("subjectname", "科目名称");
        firstMap.put("hcflagname", "是否红冲");
        firstMap.put("factoryamount", "工厂投入");
        firstMap.put("expense", "费用金额");
        firstMap.put("remark", "备注");
		/*
		 * 通用版不用
		firstMap.put("htcompdiscount", "电脑折让");
		firstMap.put("htpayamount", "支付金额");
		firstMap.put("branchaccount", "转入分公司");
		*/
		/*
		List<SysCode> reimbursetypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
		if(null!=reimbursetypeList && reimbursetypeList.size()>0){
			for(SysCode item : reimbursetypeList){
				if(null!=item && StringUtils.isNotEmpty(item.getCode()) && StringUtils.isNotEmpty(item.getCodename())){
					firstMap.put("reimburse_"+item.getCode(), item.getCodename());
				}
			}
		}
		*/
        //firstMap.put("reimbursetypename", "收回方式");
        //firstMap.put("reimburseamount", "收回金额");
        firstMap.put("actingmatamount", "代垫金额");
        firstMap.put("iswriteoffname", "核销状态");
        firstMap.put("writeoffamount", "核销金额(收回金额)");
        firstMap.put("writeoffdate", "核销日期");
        firstMap.put("writeoffername", "核销人");
        result.add(firstMap);

        if (list.size() != 0) {
            for (Map<String, Object> map : list) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        if (footerList.size() != 0) {
            for (Map<String, Object> map : footerList) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        return result;
    }

    /**
     * 导入代垫录入
     *
     * @return
     * @throws Exception
     * @author chenwei
     * @date Sep 28, 2013
     */
    @UserOperateLog(key = "MatcostsInput", type = 2)
    public String importMatcostsInputListData() throws Exception {
        List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
        List<String> paramList2 = new ArrayList<String>();
        Map reimburseMap = new HashMap();
        List<SysCode> reimbursetypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        if (null != reimbursetypeList && reimbursetypeList.size() > 0) {
            for (SysCode item : reimbursetypeList) {
                if (null != item && StringUtils.isNotEmpty(item.getCode()) && StringUtils.isNotEmpty(item.getCodename())) {
                    reimburseMap.put(item.getCodename(), "reimburse_" + item.getCode());
                }
            }
        }
        for (String str : paramList) {
            if (null == str || "".equals(str.trim())) {
                continue;
            }
            if ("业务日期".equals(str.trim())) {
                paramList2.add("businessdate");
            }else if ("支付日期".equals(str.trim())) {
                paramList2.add("paydate");
            }else if ("收回日期".equals(str.trim())) {
                paramList2.add("takebackdate");
            } else if ("OA编号".equals(str.trim())) {
                paramList2.add("oaid");
            } else if ("品牌编码".equals(str.trim())) {
                paramList2.add("brandid");
            } else if ("品牌名称".equals(str.trim())) {
                paramList2.add("brandname");
            } else if ("供应商编码".equals(str.trim())) {
                paramList2.add("supplierid");
            } else if ("供应商名称".equals(str.trim())) {
                paramList2.add("suppliername");
            } else if ("供应商所属部门".equals(str.trim())) {
                paramList2.add("supplierdeptname");
            } else if ("客户编码".equals(str.trim())) {
                paramList2.add("customerid");
            } else if ("客户名称".equals(str.trim())) {
                paramList2.add("customername");
            } else if ("经办人编码".equals(str.trim())) {
                paramList2.add("transactorid");
            } else if ("经办人名称".equals(str.trim())) {
                paramList2.add("transactorname");
            } else if ("科目名称".equals(str.trim())) {
                paramList2.add("subjectname");
            } else if ("是否红冲".equals(str.trim())) {
                paramList2.add("hcflag");
            } else if ("工厂投入".equals(str.trim())) {
                paramList2.add("factoryamount");
            } else if ("费用金额".equals(str.trim())) {
                paramList2.add("expense");
            } else if ("电脑折让".equals(str.trim())) {
                paramList2.add("htcompdiscount");
            } else if ("支付金额".equals(str.trim())) {
                paramList2.add("htpayamount");
            } else if ("转入分公司".equals(str.trim())) {
                paramList2.add("branchaccount");
            }
            //if("收回方式".equals(str)){
            //	paramList2.add("reimbursetypename");
            //}
            //if("收回金额".equals(str)){
            //	paramList2.add("reimburseamount");
            //}
            else if (reimburseMap.containsKey(str.trim())) {
                paramList2.add((String) reimburseMap.get(str));
            } else if ("代垫金额".equals(str.trim())) {
                paramList2.add("actingmatamount");
            } else if ("备注".equals(str.trim())) {
                paramList2.add("remark");
            } else {
                paramList2.add("null");
            }
        }
        Map map = new HashMap();
        List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
        if (list.size() != 0) {
            try {
                map = journalSheetService.addDRMatcostsInput(list);
            } catch (RuntimeException re) {
                Map result = new HashMap();
                result.put("exception", "编号溢出，导入失败！");
                addJSONObject(result);
                return SUCCESS;
            }
        } else {
            map.put("excelempty", true);
        }
        addJSONObject(map);
        Boolean flag = false;
        if (null != map) {
            flag = (Boolean) map.get("flag");
            if (null == flag) {
                flag = false;
            }
        }
        addLog("批量导入代垫录入 ", flag);

        return "success";
    }

    /**
     * 显示代垫统计报表页面
     *
     * @return
     * @throws Exception
     * @author chenwei
     * @date Feb 28, 2014
     */
    public String showMatcostsReportPage() throws Exception {
        request.setAttribute("firstday", CommonUtils.getMonthDateStr());
        request.setAttribute("today", CommonUtils.getTodayDataStr());
        List list = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        request.setAttribute("reimbursetypeList", list);
        return "success";
    }

    /**
     * 获取代垫统计报表数据
     *
     * @return
     * @throws Exception
     * @author chenwei
     * @date Feb 28, 2014
     */
    public String showMatcostsReportData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.showMatcostsReportData(pageMap);
        addJSONObjectWithFooter(pageData);
        return "success";
    }

    /**
     * 显示供应商代垫统计报表数据列表
     *
     * @return
     * @throws Exception
     * @author chenwei
     * @date Feb 28, 2014
     */
    public String showMatcostsReportDetailPage() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        String supplierid = request.getParameter("supplierid");
        String deptid = request.getParameter("deptid");
        List list = journalSheetService.showMatcostsReportDetail(map);
        if(null==list){
            list=new ArrayList();
        }
        String jsonStr = JSONUtils.listToJsonStr(list);
        request.setAttribute("dataList", jsonStr);
        List reimbursetypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        request.setAttribute("reimbursetypeList", reimbursetypeList);
        request.setAttribute("supplierid", supplierid);
        BuySupplier buySupplier = getBaseBuyService().showBuySupplier(supplierid);
        if (null != buySupplier) {
            request.setAttribute("suppliername", buySupplier.getId() + ":" + buySupplier.getName());
        }
        if(null!=deptid) {
            DepartMent departMent = getBaseDepartMentService().showDepartMentInfo(deptid);
            if(null!=departMent){
                request.setAttribute("deptname", deptid+ ":" + departMent.getName());
            }
        }
        return SUCCESS;
    }

    /**
     * 导出代垫汇总统计数据
     *
     * @throws Exception
     * @author chenwei
     * @date Feb 28, 2014
     */
    public void exportMatcostsReportData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isPageflag", "true");
        pageMap.setCondition(map);
        String title = "";
        if (map.containsKey("excelTitle")) {
            title = map.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        PageData pageData = journalSheetService.showMatcostsReportData(pageMap);
        ExcelUtils.exportExcel(exportMatcostsReportDataFilter(pageData.getList(), pageData.getFooter()), title);
    }

    /**
     * 导出excel格式定义
     *
     * @param list
     * @param footerList
     * @return
     * @throws Exception
     * @author chenwei
     * @date Feb 28, 2014
     */
    private List<Map<String, Object>> exportMatcostsReportDataFilter(List<Map> list, List<Map> footerList) throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("supplierid", "供应商编号");
        firstMap.put("suppliername", "供应商名称");
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("supplierdeptname", "部门名称");
        firstMap.put("beginamount", "期初代垫金额");
        firstMap.put("factoryamount", "工厂投入");
        firstMap.put("hcflag", "是否红冲");
        /** 通用版不用 **/
        //firstMap.put("htcompdiscount", "电脑折让");
        //firstMap.put("htpayamount", "支付金额");
        //firstMap.put("branchaccount", "转入分公司");
        List<SysCode> codelist = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        for (SysCode code : codelist) {
            firstMap.put("amount" + code.getCode(), code.getCodename());
        }
        firstMap.put("endamount", "期末金额");
        firstMap.put("remark", "备注");
        result.add(firstMap);

        if (list.size() != 0) {
            for (Map<String, Object> map : list) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        if (footerList.size() != 0) {
            for (Map<String, Object> map : footerList) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        return result;
    }

    /**
     * 导出代垫明细数据
     *
     * @throws Exception
     * @author chenwei
     * @date Mar 3, 2014
     */
    public void exportMatcostsReportDetailData() throws Exception {
        String supplierid = request.getParameter("supplierid");
        String businessdate1 = request.getParameter("businessdate1");
        String businessdate2 = request.getParameter("businessdate2");
        String writeoffstatus = request.getParameter("writeoffstatus");
        String title = request.getParameter("excelTitle");
        Map map = new HashMap();
        map.put("supplierid", supplierid);
        map.put("businessdate1", businessdate1);
        map.put("businessdate2", businessdate2);
        map.put("writeoffstatus", writeoffstatus);
        List list = journalSheetService.showMatcostsReportDetail(map);
        ExcelUtils.exportExcel(exportMatcostsReportDetailDataFilter(list), title);
    }

    /**
     * 导出代垫明细数据excel格式定义
     *
     * @param list
     * @return
     * @throws Exception
     * @author chenwei
     * @date Mar 3, 2014
     */
    private List<Map<String, Object>> exportMatcostsReportDetailDataFilter(List<Map> list) throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("businessdate", "业务日期");
        firstMap.put("oaid", "OA编号");
        firstMap.put("customerid", "客户编号");
        firstMap.put("customername", "客户名称");
        firstMap.put("subjectname", "科目名称");
        firstMap.put("beginamount", "期初代垫金额");
        firstMap.put("factoryamount", "工厂投入");
        firstMap.put("hcflag", "是否红冲");
        /** 通用版不用 **/
        //firstMap.put("htcompdiscount", "电脑折让");
        //firstMap.put("htpayamount", "支付金额");
        //firstMap.put("branchaccount", "转入分公司");
        List<SysCode> codelist = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        for (SysCode code : codelist) {
            firstMap.put("amount" + code.getCode(), code.getCodename());
        }
        firstMap.put("endamount", "期末金额");
        firstMap.put("remark","备注");
        result.add(firstMap);

        Map<String, Object> footerMap = new LinkedHashMap<String, Object>();
        footerMap.put("businessdate", "合计");
        footerMap.put("oaid", "");
        footerMap.put("customerid", "");
        footerMap.put("customername", "");
        footerMap.put("subjectname", "");
        BigDecimal beginamount = null;
        BigDecimal factoryamount = BigDecimal.ZERO;
        BigDecimal htcompdiscount = BigDecimal.ZERO;
        BigDecimal htpayamount = BigDecimal.ZERO;
        BigDecimal branchaccount = BigDecimal.ZERO;
        BigDecimal endamount = BigDecimal.ZERO;
        footerMap.put("beginamount", beginamount);
        footerMap.put("factoryamount", factoryamount);
        footerMap.put("hcflag", "");
        /** 通用版不用 **/
        //footerMap.put("htcompdiscount", htcompdiscount);
        //footerMap.put("htpayamount", htpayamount);
        //footerMap.put("branchaccount", branchaccount);
        if (list.size() != 0) {
            for (Map<String, Object> map : list) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (fentry.getKey().equals(entry.getKey())) {
                                objectCastToRetMap(retMap, entry);
                            }
                        }
                    } else {
                        retMap.put(fentry.getKey(), "");
                    }
                }
                if (null == beginamount && map.containsKey("beginamount")) {
                    beginamount = (BigDecimal) map.get("beginamount");
                }
                if (map.containsKey("endamount")) {
                    endamount = (BigDecimal) map.get("endamount");
                }
                if (map.containsKey("factoryamount")) {
                    factoryamount = factoryamount.add((BigDecimal) map.get("factoryamount"));
                }
                if (map.containsKey("htcompdiscount")) {
                    htcompdiscount = htcompdiscount.add((BigDecimal) map.get("htcompdiscount"));
                }
                if (map.containsKey("htpayamount")) {
                    htpayamount = htpayamount.add((BigDecimal) map.get("htpayamount"));
                }
                if (map.containsKey("branchaccount")) {
                    branchaccount = branchaccount.add((BigDecimal) map.get("branchaccount"));
                }
                for (SysCode code : codelist) {
                    if (map.containsKey("amount" + code.getCode())) {
                        if (footerMap.containsKey("amount" + code.getCode())) {
                            BigDecimal amount = (BigDecimal) footerMap.get("amount" + code.getCode());
                            amount = amount.add((BigDecimal) map.get("amount" + code.getCode()));
                            footerMap.put("amount" + code.getCode(), amount);
                        } else {
                            BigDecimal amount = (BigDecimal) map.get("amount" + code.getCode());
                            footerMap.put("amount" + code.getCode(), amount);
                        }
                    } else {
                        if (!footerMap.containsKey("amount" + code.getCode())) {
                            footerMap.put("amount" + code.getCode(), BigDecimal.ZERO);
                        } else {
                            BigDecimal tmpd = (BigDecimal) footerMap.get("amount" + code.getCode());
                            if (null == tmpd) {
                                footerMap.put("amount" + code.getCode(), BigDecimal.ZERO);
                            }
                        }
                    }
                }
                result.add(retMap);
            }
        }
        footerMap.put("beginamount", beginamount);
        footerMap.put("factoryamount", factoryamount);
        /** 通用版不用 **/
        //footerMap.put("htcompdiscount", htcompdiscount);
        //footerMap.put("htpayamount", htpayamount);
        //footerMap.put("branchaccount", branchaccount);
        footerMap.put("endamount", endamount);
        footerMap.put("remark","");
        result.add(footerMap);
        return result;
    }

    /**
     * 反核销列表页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-5-24
     */
    public String matcostsInputRewriteoffPage() throws Exception {
        List<SysCode> reimbursetypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        request.setAttribute("reimbursetypeList", reimbursetypeList);
        String id = request.getParameter("id");
        MatcostsInput matcostsInput = journalSheetService.getMatcostsInputDetail(id);
        if (null == matcostsInput || !"1".equals(matcostsInput.getIswriteoff())) {
            request.setAttribute("noDataFound", "true");
            request.setAttribute("matcostsInput", new MatcostsInput());
            return SUCCESS;
        }
        request.setAttribute("matcostsInput", matcostsInput);
        return SUCCESS;
    }

    /**
     * 反核销
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-5-24
     */
    @UserOperateLog(key = "MatcostsInput", type = 0)
    public String matcostsInputRewriteoff() throws Exception {
        String idarrs = request.getParameter("id");
        Map map = journalSheetService.updateMatcostsInputRewriteoff(idarrs);
        Boolean flag = false;
        if (null != map) {
            flag = (Boolean) map.get("flag");
            if (null == flag) {
                flag = false;
            }
            addLog("反核销代垫 编号:" + idarrs, flag);
        } else {
            addLog("反核销代垫 编号失败:" + idarrs);
        }
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 显示代垫收回页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    public String matcostsReimbursePage() throws Exception {
        request.setAttribute("firstday", CommonUtils.getMonthDateStr());
        request.setAttribute("today", CommonUtils.getTodayDataStr());
        List<SysCode> reimbursetypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        request.setAttribute("reimbursetypeList", reimbursetypeList);
        return SUCCESS;
    }

    /**
     * 显示代垫收回新增页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    public String showMatcostsReimburseAddPage() throws Exception {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sf.format(new Date());
        request.setAttribute("businessdate", date);
        request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
        return SUCCESS;
    }

    /**
     * 显示代垫收回修改页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    public String showMatcostsReimburseEditPage() throws Exception {
        String id = request.getParameter("id");
        MatcostsInput matcostsInput = journalSheetService.getMatcostsReimburseDetail(id);
        if (null != matcostsInput) {
            if ("1".equals(matcostsInput.getIswriteoff()) || "2".equals(matcostsInput.getIswriteoff())) {
                return "ViewSuccess";
            }
        } else {
            return "AddSuccess";
        }
        request.setAttribute("matcostsInput", matcostsInput);
        request.setAttribute("decimallen",BillGoodsNumDecimalLenUtils.decimalLen);
        return SUCCESS;
    }

    /**
     * 显示代垫收回详情页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    public String showMatcostsReimburseViewPage() throws Exception {
        String id = request.getParameter("id");
        MatcostsInput matcostsInput = journalSheetService.getMatcostsReimburseDetail(id);
        request.setAttribute("matcostsInput", matcostsInput);
        return SUCCESS;
    }

    /**
     * 删除代垫收回
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    @UserOperateLog(key = "MatcostsReimburse", type = 4)
    public String deleteMatcostsReimburse() throws Exception {
        String id = request.getParameter("id");
        boolean delFlag = canTableDataDelete("t_js_matcostsinput", id);
        if (!delFlag) {
            addJSONObject("delFlag", true);
            return SUCCESS;
        }
        boolean flag = journalSheetService.deleteMatcostsReimburse(id);
        addLog("删除代垫收回 编号:" + id, flag);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 删除代垫收回
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    @UserOperateLog(key = "MatcostsReimburse", type = 4)
    public String deleteMatcostsReimburseMore() throws Exception {
        String idarrs = request.getParameter("idarrs");
        Map map = new HashMap();
        int iSuccess = 0;
        int iFailure = 0;
        String errid = "";
        String suc = "";
        if (StringUtils.isNotEmpty(idarrs)) {
            String[] idArr = idarrs.split(",");
            for (String id : idArr) {
                boolean flag = journalSheetService.deleteMatcostsReimburse(id);
                if (flag) {
                    iSuccess++;
                    suc += id + ",";
                } else {
                    iFailure++;
                    if (StringUtils.isEmpty(errid)) {
                        errid = "失败编号：" + id + ",";
                    } else {
                        errid += "," + id;
                    }
                }
            }
        }
        if (iSuccess > 0) {
            map.put("flag", true);
        } else {
            map.put("flag", false);
        }
        map.put("isuccess", iSuccess);
        map.put("ifailure", iFailure);
        addLog("批量删除代垫收回 成功编号:" + suc + " " + errid, map);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 获取代垫收回列表分页
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    public String getMatcostsReimbursePageList() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("showDyncReimburse", "true");    //显示收回方式动态列表
        pageMap.setCondition(map);
        String showAllData = (String) map.get("showAllData");
        if ("true".equals(showAllData)) {
            map.put("isPageflag", "true");
        } else {
            map.put("isPageflag", "false");
        }
        PageData pageData = journalSheetService.getMatcostsReimbursePageList(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 新增代垫收回
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    @UserOperateLog(key = "MatcostsReimburse", type = 2, value = "")
    public String addMatcostsReimburse() throws Exception {
        SysUser sysUser = getSysUser();
        matcostsInput.setAdduserid(sysUser.getUserid());
        matcostsInput.setAddusername(sysUser.getName());
        matcostsInput.setSourcefrome("0");
        boolean flag = true;
        Map resultMap = new HashMap();
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isEmpty(matcostsInput.getSupplierid())) {
            if (!flag) {
                sb.append("<br/>");
            } else {
                flag = false;
            }
            sb.append("供应商不能为空");
        }
        if (StringUtils.isEmpty(matcostsInput.getSupplierdeptid())) {
            if (!flag) {
                sb.append("<br/>");
            } else {
                flag = false;
            }
            sb.append("供应商所属部门不能为空");
        }
        if (StringUtils.isEmpty(matcostsInput.getReimbursetype())) {
            if (!flag) {
                sb.append("<br/>");
            } else {
                flag = false;
            }
            sb.append("收回方式不能为空");
        }
        if (null == matcostsInput.getReimburseamount()) {
            if (!flag) {
                sb.append("<br/>");
            } else {
                flag = false;
            }
            sb.append("收回金额不能为空");
        }
        if (!flag) {
            resultMap.put("flag", flag);
            resultMap.put("msg", sb.toString());
            addJSONObject(resultMap);
        }
        flag = journalSheetService.addMatcostsReimburse(matcostsInput);
        addJSONObject("flag", flag);
        StringBuffer logSb = new StringBuffer();
        logSb.append("新增代垫收回");
        if (StringUtils.isNotEmpty(matcostsInput.getId())) {
            logSb.append("编号：");
            logSb.append(matcostsInput.getId());
        } else {
            logSb.append("业务日期:" + matcostsInput.getBusinessdate());
            logSb.append("OA编号:" + matcostsInput.getOaid());
        }
        addLog(logSb.toString(), flag);
        return SUCCESS;
    }

    /**
     * 修改代垫录入
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-2-26
     */
    @UserOperateLog(key = "MatcostsReimburse", type = 3, value = "")
    public String editMatcostsReimburse() throws Exception {
        boolean flag = true;
        Map resultMap = new HashMap();
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isEmpty(matcostsInput.getSupplierid())) {
            if (!flag) {
                sb.append("<br/>");
            } else {
                flag = false;
            }
            sb.append("供应商不能为空");
        }
        if (StringUtils.isEmpty(matcostsInput.getSupplierdeptid())) {
            if (!flag) {
                sb.append("<br/>");
            } else {
                flag = false;
            }
            sb.append("供应商所属部门不能为空");
        }
        if (StringUtils.isEmpty(matcostsInput.getReimbursetype())) {
            if (!flag) {
                sb.append("<br/>");
            } else {
                flag = false;
            }
            sb.append("收回方式不能为空");
        }
        if (null == matcostsInput.getReimburseamount()) {
            if (!flag) {
                sb.append("<br/>");
            } else {
                flag = false;
            }
            sb.append("收回金额不能为空");
        }
        if (!flag) {
            resultMap.put("flag", flag);
            resultMap.put("msg", sb.toString());
            addJSONObject(resultMap);
        }
        flag = journalSheetService.editMatcostsReimburse(matcostsInput);
        addJSONObject("flag", flag);
        addLog("修改代垫收回 编号:" + matcostsInput.getId(), flag);
        return SUCCESS;
    }

    /**
     * 导出-代垫收回报表
     *
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 30, 2013
     */
    public void exportMatcostsReimburseData() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isPageflag", "true");
        map.put("showDyncReimburse", "true");    //显示收回方式动态列表
        pageMap.setCondition(map);
        String title = "";
        if (map.containsKey("excelTitle")) {
            title = map.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        PageData pageData = journalSheetService.getMatcostsReimbursePageList(pageMap);
        ExcelUtils.exportExcel(exportMatcostsReimburseDataFilter(pageData.getList(), pageData.getFooter()), title);
    }

    /**
     * 数据转换，list专程符合excel导出的数据格式(代垫收回列表)
     *
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 29, 2013
     */
    private List<Map<String, Object>> exportMatcostsReimburseDataFilter(List<Map> list, List<Map> footerList) throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("businessdate", "业务日期");
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("suppliername", "供应商名称");
        firstMap.put("supplierdeptname", "供应商所属部门");
        firstMap.put("bankname", "银行名称");
        firstMap.put("shsubjectid", "收回科目编号");
        firstMap.put("shsubjectname", "收回科目");
        List<SysCode> reimbursetypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        if (null != reimbursetypeList && reimbursetypeList.size() > 0) {
            for (SysCode item : reimbursetypeList) {
                if (null != item && StringUtils.isNotEmpty(item.getCode()) && StringUtils.isNotEmpty(item.getCodename())) {
                    firstMap.put("reimburse_" + item.getCode(), item.getCodename());
                }
            }
        }
        //firstMap.put("reimbursetypename", "收回方式");
        firstMap.put("unitname", "单位");
        firstMap.put("unitnum", "数量");
        firstMap.put("taxprice", "单价");
        firstMap.put("reimburseamount", "收回金额");
        firstMap.put("writeoffamount", "核销金额");
        firstMap.put("remainderamount", "未核销金额");
        firstMap.put("iswriteoffname", "核销状态");
        firstMap.put("writeoffdate", "核销日期");
        firstMap.put("writeoffername", "核销人");
        result.add(firstMap);

        if (list.size() != 0) {
            for (Map<String, Object> map : list) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        if (footerList.size() != 0) {
            for (Map<String, Object> map : footerList) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        return result;
    }

    /**
     * 导入代垫收回
     *
     * @return
     * @throws Exception
     * @author chenwei
     * @date Sep 28, 2013
     */
    @UserOperateLog(key = "MatcostsInput", type = 2)
    public String importMatcostsReimburseListData() throws Exception {
        List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
        List<String> paramList2 = new ArrayList<String>();
        Map reimburseMap = new HashMap();
        List<SysCode> reimbursetypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        if (null != reimbursetypeList && reimbursetypeList.size() > 0) {
            for (SysCode item : reimbursetypeList) {
                if (null != item && StringUtils.isNotEmpty(item.getCode()) && StringUtils.isNotEmpty(item.getCodename())) {
                    reimburseMap.put(item.getCodename(), "reimburse_" + item.getCode());
                }
            }
        }
        for (String str : paramList) {
            if (null == str || "".equals(str.trim())) {
                continue;
            }
            if ("业务日期".equals(str.trim())) {
                paramList2.add("businessdate");
            } else if ("供应商编码".equals(str.trim())) {
                paramList2.add("supplierid");
            } else if ("供应商名称".equals(str.trim())) {
                paramList2.add("suppliername");
            } else if ("供应商所属部门".equals(str.trim())) {
                paramList2.add("supplierdeptname");
            } else if ("收回科目编号".equals(str.trim())) {
                paramList2.add("shsubjectid");
            }  else if ("单位".equals(str.trim())) {
                paramList2.add("unitname");
            }else if ("数量".equals(str.trim())) {
                paramList2.add("unitnum");
            } else if ("单价".equals(str.trim())) {
                paramList2.add("taxprice");
            }
            //if("收回方式".equals(str)){
            //	paramList2.add("reimbursetypename");
            //}
            //if("收回金额".equals(str)){
            //	paramList2.add("reimburseamount");
            //}
            else if (reimburseMap.containsKey(str.trim())) {
                paramList2.add((String) reimburseMap.get(str));
            } else if ("备注".equals(str.trim())) {
                paramList2.add("remark");
            } else {
                paramList2.add("null");
            }
        }
        Map map = new HashMap();
        List list = ExcelUtils.importExcel(excelFile, paramList2); //获取导入数据
        if (list.size() != 0) {
            map = journalSheetService.addDRMatcostsReimburse(list);
        } else {
            map.put("excelempty", true);
        }
        addJSONObject(map);
        Boolean flag = false;
        if (null != map) {
            flag = (Boolean) map.get("flag");
            if (null == flag) {
                flag = false;
            }
        }
        addLog("批量导入代垫录入 ", flag);

        return "success";
    }

    /**
     * 代垫收回核销-收回查询选择
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-5-30
     */
    public String matcostsReimburseWriteoffQueryPage() throws Exception {
        String tmp = request.getParameter("begintime");
        if (tmp == null) {
            tmp = CommonUtils.getMonthDateStr();
        }
        request.setAttribute("begintime", tmp);
        tmp = request.getParameter("endtime");
        if (tmp == null) {
            tmp = CommonUtils.getTodayDataStr();
        }
        request.setAttribute("endtime", tmp);
        request.setAttribute("today", CommonUtils.getTodayDataStr());

        List<SysCode> reimbursetypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        request.setAttribute("reimbursetypeList", reimbursetypeList);

        return SUCCESS;
    }

    /**
     * 代垫收回核销-代垫查询选择
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-5-30
     */
    public String matcostsReimburseWriteoffInputQueryPage() throws Exception {
        request.setAttribute("firstday", CommonUtils.getMonthDateStr());
        request.setAttribute("today", CommonUtils.getTodayDataStr());

        return SUCCESS;
    }

    /**
     * 代垫收回核销
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-5-30
     */
    @UserOperateLog(key = "MatcostsReimburse", type = 0, value = "")
    public String matcostsReimburseWriteoff() throws Exception {
        String inputIdarrs = request.getParameter("inputIdarrs");
        String reimburseIdarrs = request.getParameter("reimburseIdarrs");
        String businessdate = request.getParameter("businessdate");
        Map resultMap = new HashMap();
        boolean flag = true;
        StringBuffer sb = new StringBuffer();
        if (null == inputIdarrs || "".equals(inputIdarrs.trim())) {
            flag = false;
            sb.append("未能找到要核销的代垫");
        }
        if (null == reimburseIdarrs || "".equals(reimburseIdarrs.trim())) {
            if (!flag) {
                sb.append("<br/>");
            } else {
                flag = false;
            }
            sb.append("未能找到要核销的代垫收回");
        }
        if (null == businessdate || "".equals(businessdate.trim())) {
            if (!flag) {
                sb.append("<br/>");
            } else {
                flag = false;
            }
            sb.append("核销日期不能为空");
        }
        if (!flag) {
            resultMap.put("flag", flag);
            resultMap.put("msg", sb.toString());
            addJSONObject(resultMap);
            return SUCCESS;
        }
        Map map = new HashMap();
        map.put("inputIdarrs", inputIdarrs);
        map.put("reimburseIdarrs", reimburseIdarrs);
        map.put("businessdate", businessdate);
        flag = journalSheetService.addMatcostsReimburseWriteoff(map);
        String logString = "代垫核销- 核销日期:" + businessdate + "代垫编号(字符串组)：" + inputIdarrs + " 收回编号(字符串组)：" + reimburseIdarrs;
        addLog(logString, flag);
        resultMap.put("flag", flag);
        addJSONObject(resultMap);
        return SUCCESS;
    }

    /**
     * 显示
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-6-6
     */
    public String showMatcostsStatementListPage() throws Exception {
        request.setAttribute("firstday", CommonUtils.getMonthDateStr());
        request.setAttribute("today", CommonUtils.getTodayDataStr());
        List<SysCode> reimbursetypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        request.setAttribute("reimbursetypeList", reimbursetypeList);
        return SUCCESS;
    }

    /**
     * 显示
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-6-6
     */
    public String getMatcostsStatementPageListData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("showDyncReimburse", "true");    //显示收回方式动态列表
        pageMap.setCondition(map);
        String showAllData = (String) map.get("showAllData");
        if ("true".equals(showAllData)) {
            map.put("isPageflag", "true");
        } else {
            map.put("isPageflag", "false");
        }
        PageData pageData = journalSheetService.getMatcostsStatementPageListData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 代垫核销报表
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-6-13
     */
    public String showMatcostsInputWriteoffReportPage() throws Exception {
        request.setAttribute("firstday", CommonUtils.getMonthDateStr());
        request.setAttribute("today", CommonUtils.getTodayDataStr());
        List list = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        request.setAttribute("reimbursetypeList", list);
        return SUCCESS;
    }

    /**
     * 代垫核销报表
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-6-13
     */
    public String getMatcostsInputWriteoffReportData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        if (map.containsKey("isPageflag")) {
            map.remove("isPageflag");
        }
        if (map.containsKey("isExportData")) {
            map.remove("isExportData");
        }
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.getMatcostsInputWriteoffReportData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 导出-代垫核销情况报表
     *
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 30, 2013
     */
    public void exportMatcostsInputWriteoffReportData() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isPageflag", "true");
        map.put("isExportData", "true");    //是否导出数据
        pageMap.setCondition(map);
        String title = "";
        if (map.containsKey("excelTitle")) {
            title = map.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        PageData pageData = journalSheetService.getMatcostsInputWriteoffReportData(pageMap);
        ExcelUtils.exportExcel(exportMatcostsInputWriteoffReportFilter(pageData.getList(), pageData.getFooter()), title);
    }

    /**
     * 数据转换，list专程符合excel导出的数据格式(代垫核销情况)
     *
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 29, 2013
     */
    private List<Map<String, Object>> exportMatcostsInputWriteoffReportFilter(List<Map> list, List<Map> footerList) throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        String groupcols = request.getParameter("groupcols");
        if (null == groupcols || "".equals(groupcols)) {
            groupcols = "id";
        }
        String iswriteoff = request.getParameter("iswriteoff");
        if (null == iswriteoff || !"1".equals(iswriteoff.trim())) {
            iswriteoff = "";
        }
        iswriteoff = iswriteoff.trim();
        if ("id".equals(groupcols)) {
            firstMap.put("businessdate", "业务日期");
            //firstMap.put("oaid", "OA编号");
            firstMap.put("brandid", "品牌编码");
            firstMap.put("brandname", "品牌名称");
        }
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("suppliername", "供应商名称");
        firstMap.put("supplierdeptname", "供应商所属部门");
        if ("id".equals(groupcols)) {
            firstMap.put("customerid", "客户编码");
            firstMap.put("customername", "客户名称");
            firstMap.put("subjectname", "科目名称");
        }
        firstMap.put("factoryamount", "工厂投入");
        /** 通用版不用 **/
        //firstMap.put("htcompdiscount", "电脑折让");
        //firstMap.put("htpayamount", "支付金额");
        //firstMap.put("branchaccount", "转入分公司");
        if (!"0".equals(iswriteoff)) {
            List<SysCode> reimbursetypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
            if (null != reimbursetypeList && reimbursetypeList.size() > 0) {
                for (SysCode item : reimbursetypeList) {
                    if (null != item && StringUtils.isNotEmpty(item.getCode()) && StringUtils.isNotEmpty(item.getCodename())) {
                        firstMap.put("reimburse_" + item.getCode(), item.getCodename());
                    }
                }
            }
        }
        firstMap.put("writeoffamount", "核销金额");
        if ("id".equals(groupcols)) {
            firstMap.put("iswriteoffname", "核销状态");
        }
        firstMap.put("unwriteoffamount", "未核销金额");
        if ("id".equals(groupcols) && "1".equals(iswriteoff)) {
            firstMap.put("writeoffdate", "核销日期");
            firstMap.put("writeoffername", "核销人");
        }
        result.add(firstMap);

        if (list.size() != 0) {
            for (Map<String, Object> map : list) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        if (footerList.size() != 0) {
            for (Map<String, Object> map : footerList) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        return result;
    }

    /**
     * 代垫核销报表
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-6-13
     */
    public String showMatcostsReimburseWriteoffReportPage() throws Exception {
        request.setAttribute("firstday", CommonUtils.getMonthDateStr());
        request.setAttribute("today", CommonUtils.getTodayDataStr());
        List list = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        request.setAttribute("reimbursetypeList", list);
        return SUCCESS;
    }

    /**
     * 代垫核销报表
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-6-13
     */
    public String getMatcostsReimburseWriteoffReportData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        if (map.containsKey("isPageflag")) {
            map.remove("isPageflag");
        }
        if (map.containsKey("isExportData")) {
            map.remove("isExportData");
        }
        pageMap.setCondition(map);
        PageData pageData = journalSheetService.getMatcostsReimburseWriteoffReportData(pageMap);
        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 导出-收回核销情况报表
     *
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 30, 2013
     */
    public void exportMatcostsReimburseWriteoffReportData() throws Exception {
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isPageflag", "true");
        map.put("isExportData", "true");    //是否导出数据
        pageMap.setCondition(map);
        String title = "";
        if (map.containsKey("excelTitle")) {
            title = map.get("excelTitle").toString();
        } else {
            title = "list";
        }
        if (StringUtils.isEmpty(title)) {
            title = "list";
        }
        PageData pageData = journalSheetService.getMatcostsReimburseWriteoffReportData(pageMap);
        ExcelUtils.exportExcel(exportMatcostsReimburseWriteoffReportFilter(pageData.getList(), pageData.getFooter()), title);
    }

    /**
     * 数据转换，list专程符合excel导出的数据格式(收回核销情况)
     *
     * @param list
     * @return
     * @throws Exception
     * @author panxiaoxiao
     * @date Sep 29, 2013
     */
    private List<Map<String, Object>> exportMatcostsReimburseWriteoffReportFilter(List<Map> list, List<Map> footerList) throws Exception {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        String groupcols = request.getParameter("groupcols");
        if (null == groupcols || "".equals(groupcols)) {
            groupcols = "id";
        }
        String iswriteoff = request.getParameter("iswriteoff");
        if (null == iswriteoff || !"1".equals(iswriteoff.trim())) {
            iswriteoff = "";
        }
        iswriteoff = iswriteoff.trim();
        if ("id".equals(groupcols)) {
            firstMap.put("businessdate", "业务日期");
        }
        firstMap.put("supplierid", "供应商编码");
        firstMap.put("suppliername", "供应商名称");
        firstMap.put("supplierdeptname", "供应商所属部门");
        firstMap.put("reimburseamount", "收回金额");
        if (!"0".equals(iswriteoff)) {
            List<SysCode> reimbursetypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
            if (null != reimbursetypeList && reimbursetypeList.size() > 0) {
                for (SysCode item : reimbursetypeList) {
                    if (null != item && StringUtils.isNotEmpty(item.getCode()) && StringUtils.isNotEmpty(item.getCodename())) {
                        firstMap.put("reimburse_" + item.getCode(), item.getCodename());
                    }
                }
            }
        }
        firstMap.put("reimburseamount", "核销金额");
        if ("id".equals(groupcols)) {
            //firstMap.put("iswriteoffname", "核销状态");
        }
        firstMap.put("unwriteoffamount", "未核销金额");
        if ("id".equals(groupcols) && "1".equals(iswriteoff)) {
            firstMap.put("writeoffdate", "核销日期");
            firstMap.put("writeoffername", "核销人");
        }
        result.add(firstMap);

        if (list.size() != 0) {
            for (Map<String, Object> map : list) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        if (footerList.size() != 0) {
            for (Map<String, Object> map : footerList) {
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                for (Map.Entry<String, Object> fentry : firstMap.entrySet()) {
                    if (map.containsKey(fentry.getKey())) { //如果记录中包含该Key，则取该Key的Value
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
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
        }
        return result;
    }

    /**
     * 收回方式变更页面
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-5-24
     */
    public String matcostsReimburseTypeChangePage() throws Exception {
        List<SysCode> reimbursetypeList = getBaseSysCodeService().showSysCodeListByType("matcreimbursetype");
        request.setAttribute("reimbursetypeList", reimbursetypeList);
        String id = request.getParameter("id");
        MatcostsInput matcostsReimburse = journalSheetService.getMatcostsReimburseDetail(id);
        if (null == matcostsReimburse) {
            request.setAttribute("noDataFound", "true");
            request.setAttribute("matcostsReimburse", new MatcostsInput());
            return SUCCESS;
        }
        request.setAttribute("matcostsReimburse", matcostsReimburse);
        return SUCCESS;
    }

    /**
     * 收回方式变更
     *
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date 2014-5-24
     */
    @UserOperateLog(key = "matcostsReimburse", type = 0)
    public String matcostsReimburseTypeChange() throws Exception {
        String id = request.getParameter("id");
        String reimbursetype = request.getParameter("reimburseType");
        Map queryMap = new HashMap();
        queryMap.put("id", id);
        queryMap.put("reimburseType", reimbursetype);
        Map map = journalSheetService.updateMatcostsReimburseTypeChange(queryMap);
        Boolean flag = false;
        if (null != map) {
            flag = (Boolean) map.get("flag");
            if (null == flag) {
                flag = false;
            }
            addLog("收回方式变更 编号:" + id, flag);
        } else {
            addLog("收回方式变更 编号失败:" + id);
        }
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 代垫收支情况页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-29
     */
    public String matcostsBalancePage() throws Exception {

        return SUCCESS;
    }

    /**
     * 代垫收支情况一览列表页面
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-29
     */
    public String matcostsBalanceListPage() throws Exception {

        return SUCCESS;
    }

    /**
     * 查询代垫收支情况一览数据
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-29
     */
    public String selectMatcostsBalanceList() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);

        PageData pageData = journalSheetService.selectMatcostsBalanceList(pageMap);

        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }

    /**
     * 查询代垫收支情况一览数据
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-29
     */
    public void exportMatcostsBalanceListData() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        pageMap.setRows(9999);

        PageData pageData = journalSheetService.selectMatcostsBalanceList(pageMap);

        map.put("isflag", "true");
        pageMap.setCondition(map);
        String title = null;

        if (map.containsKey("excelTitle")) {

            title = map.get("excelTitle").toString();

        } else {

            title = "list";
        }

        if (StringUtils.isEmpty(title)) {
            title = "list";
        }

        ExcelUtils.exportExcel(exportMatcostsBalanceListDataFilter(pageData), title);

    }

    /**
     * 数据转换，list专程符合excel导出的数据格式(代垫收支情况表)
     *
     * @param data
     * @return
     * @throws Exception
     * @author limin
     * @date Jun 9, 2015
     */
    private List<Map<String, Object>> exportMatcostsBalanceListDataFilter(PageData data) throws Exception {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

        Map<String, Object> header = new LinkedHashMap<String, Object>();
        header.put("supplierid", "供应商编码");
        header.put("suppliername", "供应商名称");
        header.put("deptid", "品牌部门编码");
        header.put("deptname", "品牌部门名称");
        header.put("income", "收入");
        header.put("outcome", "支出");
        header.put("balance", "结余");

        result.add(header);

        List<HashMap<String, Object>> list = (List<HashMap<String, Object>>) data.getList();

        for (HashMap<String, Object> item2 : list) {

            Map<String, Object> content = new LinkedHashMap<String, Object>();
            content.put("supplierid", item2.get("supplierid"));
            content.put("suppliername", item2.get("suppliername"));
            content.put("deptid", item2.get("deptid"));
            content.put("deptname", item2.get("deptname"));
            content.put("income", item2.get("income"));
            content.put("outcome", item2.get("outcome"));
            content.put("balance", item2.get("balance"));
            result.add(content);
        }

        //按品牌合计库存
        Map<String, Object> total = new LinkedHashMap<String, Object>();
        total.put("supplierid", "");
        total.put("suppliername", "合计");
        total.put("deptid", "");
        total.put("deptname", "");
        total.put("income", ((Map<String, String>) data.getFooter().get(0)).get("income"));
        total.put("outcome", ((Map<String, String>) data.getFooter().get(0)).get("outcome"));
        total.put("balance", ((Map<String, String>) data.getFooter().get(0)).get("balance"));
        result.add(total);

        return result;
    }

    /**
     * 代垫收支详情页面
     *
     * @return
     * @author limin
     * @date 2015-1-30
     */
    public String matcostsBalanceViewPage() throws Exception {

        return SUCCESS;
    }

    /**
     * 查询代垫收支明细情况一览数据
     *
     * @return
     * @throws Exception
     * @author limin
     * @date 2015-1-30
     */
    public String selectMatcostsBalanceDetailList() throws Exception {

        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);

        PageData pageData = journalSheetService.selectMatcostsBalanceDetailList(pageMap);

        addJSONObjectWithFooter(pageData);
        return SUCCESS;
    }


    /**
     * 查询代垫收支明细情况一览数据导出
     * @param
     * @return void
     * @throws
     * @author zhang_honghui
     * @date Jan 24, 2017
     */
    public void exportMatcostsBalanceDetailListData() throws Exception{
        Map<String, String> map = CommonUtils.changeMap(request.getParameterMap());
        Map queryMap = new HashMap();
        String query = (String) map.get("param");
        JSONObject object = JSONObject.fromObject(query);
        for (Object k : object.keySet()) {
            Object v = object.get(k);
            if(org.apache.commons.lang3.StringUtils.isNotEmpty((String) v)){
                queryMap.put(k.toString(), (String) v);
            }
        }
        queryMap.put("isPageflag", "true");
        pageMap.setCondition(queryMap);
        PageData pageData = journalSheetService.selectMatcostsBalanceDetailList(pageMap);
        List list = pageData.getList();
        if(null != pageData.getFooter()){
            List footer = pageData.getFooter();
            list.addAll(footer);
        }
        ExcelUtils.exportAnalysExcel(map,list);
    }

    /**
     * 代垫收支详情列表
     *
     * @return
     * @author limin
     * @date 2015-1-30
     */
    public String matcostsBalanceViewListPage() throws Exception {

        return SUCCESS;
    }
}

