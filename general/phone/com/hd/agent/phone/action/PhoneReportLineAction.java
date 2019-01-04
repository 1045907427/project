package com.hd.agent.phone.action;

import com.hd.agent.account.model.BankAmountLog;
import com.hd.agent.account.model.BankAmountOthers;
import com.hd.agent.account.model.CollectionOrder;
import com.hd.agent.account.model.Payorder;
import com.hd.agent.account.service.IBankAmountService;
import com.hd.agent.account.service.ICollectionOrderService;
import com.hd.agent.account.service.IPayorderService;
import com.hd.agent.basefiles.action.BaseFilesAction;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.journalsheet.model.DeptCostsSubject;
import com.hd.agent.journalsheet.model.DeptDailyCost;
import com.hd.agent.journalsheet.model.MatcostsInput;
import com.hd.agent.journalsheet.service.ICostsFeeService;
import com.hd.agent.journalsheet.service.IDeptDailyCostService;
import com.hd.agent.journalsheet.service.IJournalSheetService;
import com.hd.agent.phone.service.IPhoneReportLineService;
import com.hd.agent.report.service.IExceptionReportService;
import com.hd.agent.report.service.IFinanceFundsReturnService;
import com.hd.agent.report.service.ISalesReportService;
import com.hd.agent.sales.model.OrderDetail;
import com.hd.agent.sales.model.OrderGoodsDetail;
import com.hd.agent.sales.service.IOrderGoodsService;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.annotations.JSON;

import javax.servlet.http.Cookie;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by luoq on 2016/11/8.
 * 手机端在线报表
 */
public class PhoneReportLineAction extends BaseFilesAction {
    private IPhoneReportLineService phoneReportLineService;
    /**
     * 销售报表service
     */
    private ISalesReportService salesReportService;
    /**
     * 资金回笼service
     */
    private IFinanceFundsReturnService financeFundsReturnService;

    private IExceptionReportService exceptionReportService;

    private IBankAmountService bankAmountService;

    private ICollectionOrderService collectionOrderService;

    private IPayorderService payorderService;

    private IDeptDailyCostService deptDailyCostService;

    private ICostsFeeService costsFeeService;

    private IJournalSheetService journalSheetService;

    private IOrderGoodsService orderGoodsService;

    public IExceptionReportService getExceptionReportService() {
        return exceptionReportService;
    }

    public void setExceptionReportService(IExceptionReportService exceptionReportService) {
        this.exceptionReportService = exceptionReportService;
    }

    public IFinanceFundsReturnService getFinanceFundsReturnService() {
        return financeFundsReturnService;
    }

    public void setFinanceFundsReturnService(IFinanceFundsReturnService financeFundsReturnService) {
        this.financeFundsReturnService = financeFundsReturnService;
    }

    public ISalesReportService getSalesReportService() {
        return salesReportService;
    }

    public void setSalesReportService(ISalesReportService salesReportService) {
        this.salesReportService = salesReportService;
    }

    public IPhoneReportLineService getPhoneReportLineService() {
        return phoneReportLineService;
    }

    public void setPhoneReportLineService(IPhoneReportLineService phoneReportLineService) {
        this.phoneReportLineService = phoneReportLineService;
    }

    public IBankAmountService getBankAmountService() {
        return bankAmountService;
    }

    public void setBankAmountService(IBankAmountService bankAmountService) {
        this.bankAmountService = bankAmountService;
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

    public IDeptDailyCostService getDeptDailyCostService() {
        return deptDailyCostService;
    }

    public void setDeptDailyCostService(IDeptDailyCostService deptDailyCostService) {
        this.deptDailyCostService = deptDailyCostService;
    }

    public ICostsFeeService getCostsFeeService() {
        return costsFeeService;
    }

    public void setCostsFeeService(ICostsFeeService costsFeeService) {
        this.costsFeeService = costsFeeService;
    }

    public IJournalSheetService getJournalSheetService() {
        return journalSheetService;
    }

    public void setJournalSheetService(IJournalSheetService journalSheetService) {
        this.journalSheetService = journalSheetService;
    }

    public IOrderGoodsService getOrderGoodsService() {
        return orderGoodsService;
    }

    public void setOrderGoodsService(IOrderGoodsService orderGoodsService) {
        this.orderGoodsService = orderGoodsService;
    }

    private Integer pageSizeNum = 50;//手机报表每页数量

    /**
     * 订单明细查询页面
     * @return
     * @throws Exception
     */
    public String showSalesOrderTrackReportPage() throws Exception {
        String today = CommonUtils.getTodayDataStr();
        request.setAttribute("today", today);
        return SUCCESS;
    }
    /**
     * 订单明细查询数据展示页面
     *
     * @return
     * @throws Exception
     * luoq 2016/11/8
     */
    public String showSalesOrderTrackReportDataPage() throws Exception{
        Map paramterMap=CommonUtils.changeMap(request.getParameterMap());
        String businessdate1 = request.getParameter("businessdate1");
        String businessdate2 = request.getParameter("businessdate2");

        if(null==businessdate1){
            businessdate1 = "";
        }
        if(null==businessdate2){
            businessdate2 = "";
        }
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("paramterMap",paramterMap);
        request.setAttribute("map", map);

        return SUCCESS;
    }

    /**
     * 订单明细查询数据
     *
     * @return
     * @throws Exception
     * luoq 2016/11/8
     */
    public String showBuyOrderTrackReportData() throws Exception {
        Map colmap=getAccessColumn("t_report_sales_base");
        Map map = CommonUtils.changeMap(request.getParameterMap());
        Integer startNum;
        String page = request.getParameter("page");
        startNum = (Integer.parseInt(page) - 1) * pageSizeNum;
        pageMap.setCondition(map);
        pageMap.setRows(pageSizeNum);
        pageMap.setStartNum(startNum);
        PageData pageData = salesReportService.showSalesOrderTrackReportData(pageMap);
        Map listmap = new HashMap();
        listmap.put("list", pageData.getList());
        listmap.put("footer", pageData.getFooter());
        listmap.put("colmap", colmap);
        addJSONObject(listmap);
        request.setAttribute("listmap", listmap);
        return SUCCESS;
    }

    /**
     * 显示销售情况基础统计报表查询页面
     *
     * @return
     * @throws Exception
     * @author luoq
     * @date 2016/11/8
     */
    public String showBaseSalesReportPage() throws Exception {
        String today = CommonUtils.getTodayDataStr();
        String firstDay = CommonUtils.getMonthDateStr();
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("map", map);
        request.setAttribute("firstDay", firstDay);
        request.setAttribute("today", today);
        List goodstypeList = getBaseSysCodeService().showSysCodeListByType("goodstype");
        request.setAttribute("goodstypeList", goodstypeList);
        String groupcols=getGroupcol("baseSalesReportPage");//获取groupcolscookie
        if(StringUtils.isNotEmpty(groupcols)){
            String []a=groupcols.split(",");
            if(a!=null&&a.length>0){
                List collist= Arrays.asList(a);
                request.setAttribute("collist",collist);
            }
        }
        else{
            request.setAttribute("collist",null);
        }
        return "success";
    }
    /**
     * 获取销售情况基础统计数据页面
     *
     * @return
     * @throws Exception
     * @author luoq
     * @date 2016/11/8
     */
    public String showBaseSalesReportDataPage() throws Exception{
        Map paramterMap=CommonUtils.changeMap(request.getParameterMap());
        String businessdate1 = request.getParameter("businessdate1");
        String businessdate2 = request.getParameter("businessdate2");
        String groupcolsnum=request.getParameter("groupcolsnum");

        if(null==businessdate1){
            businessdate1 = "";
        }
        if(null==businessdate2){
            businessdate2 = "";
        }
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("paramterMap",paramterMap);
        request.setAttribute("map", map);

        saveGroupcol(groupcolsnum,"baseSalesReportPage");//保存cookie

        return SUCCESS;
    }

    /**
     * 获取销售情况基础统计数据
     *
     * @return
     * @throws Exception
     * @author luoq
     * @date 2016/11/8
     */
    public String showBaseSalesReportData() throws Exception {
        Map colmap=getAccessColumn("t_report_sales_base");
        Map map = CommonUtils.changeMap(request.getParameterMap());
        Integer startNum;
        String page = request.getParameter("page");
        startNum = (Integer.parseInt(page) - 1) * pageSizeNum;
        pageMap.setCondition(map);
        pageMap.setRows(pageSizeNum);
        pageMap.setStartNum(startNum);
        PageData pageData = salesReportService.showBaseSalesReportData(pageMap);
        Map listmap = new HashMap();
        listmap.put("list", pageData.getList());
        listmap.put("footer", pageData.getFooter());
        listmap.put("colmap",colmap);
        request.setAttribute("listmap", listmap);
        addJSONObject(listmap);
        return "success";
    }

    /**
     * 报表菜单列表
     *
     * @return
     */
    public String showPhoneReportListPage() {
        return SUCCESS;
    }

    /**
     * 资金回笼报表查询页面
     *2016/11/8
     * @return
     */
    public String showBaseFinanceDrawnPage() throws Exception {
        String today = CommonUtils.getTodayDataStr();
        String firstDay = CommonUtils.getMonthDateStr();
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("map", map);
        request.setAttribute("firstDay", firstDay);
        request.setAttribute("today", today);

        String groupcols=getGroupcol("baseFinanceDrawnPage");
        if(StringUtils.isNotEmpty(groupcols)){
            String []a=groupcols.split(",");
            if(a!=null&&a.length>0){
                List collist= Arrays.asList(a);
                request.setAttribute("collist",collist);
            }
        }
        else{
            request.setAttribute("collist",null);
        }
//        request.setAttribute("groupcols",groupcols);
        return "success";
    }
    /**
     * 手机端资金回笼数据页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Dec 23, 2016
     */
    public String showBaseFinanceDrawnDataPage() throws Exception{
        Map paramterMap=CommonUtils.changeMap(request.getParameterMap());
        String businessdate1 = request.getParameter("businessdate1");
        String businessdate2 = request.getParameter("businessdate2");
        String groupcolsnum=request.getParameter("groupcolsnum");

        if(null==businessdate1){
            businessdate1 = "";
        }
        if(null==businessdate2){
            businessdate2 = "";
        }
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("paramterMap",paramterMap);
        request.setAttribute("map", map);

        saveGroupcol(groupcolsnum,"baseFinanceDrawnPage");//保存cookie

        return SUCCESS;
    }

    /**
     * 资金回笼报表数据
     *
     * @return luoq 2016/11/8
     * @throws Exception
     */
    public String showBaseFinanceDrawnData() throws Exception {
        Map colmap=getAccessColumn("t_report_sales_base");
        Map map = CommonUtils.changeMap(request.getParameterMap());
        Integer startNum;
        String page = request.getParameter("page");
        startNum = (Integer.parseInt(page) - 1) * pageSizeNum;
        pageMap.setCondition(map);
        pageMap.setRows(pageSizeNum);
        pageMap.setStartNum(startNum);
        PageData pageData = financeFundsReturnService.showBaseFinanceDrawnData(pageMap);
        Map listmap = new HashMap();
        listmap.put("list", pageData.getList());
        listmap.put("footer", pageData.getFooter());
        listmap.put("colmap",colmap);
        request.setAttribute("listmap", listmap);
        addJSONObject(listmap);
        return SUCCESS;
    }

    /**
     * 显示要货金额报表
     *
     * @return
     * @throws Exception
     * @author luoq
     * @date 2016/11/8
     */
    public String showSalesDemandReportListPage() throws Exception {
        String today = CommonUtils.getTodayDataStr();
        String firstDay = CommonUtils.getMonthDateStr();
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("map", map);
        request.setAttribute("firstDay", firstDay);
        request.setAttribute("today", today);
        String groupcols=getGroupcol("salesDemandReportListPage");
        if(StringUtils.isNotEmpty(groupcols)){
            String []a=groupcols.split(",");
            if(a!=null&&a.length>0){
                List collist= Arrays.asList(a);
                request.setAttribute("collist",collist);
            }
        }
        else{
            request.setAttribute("collist",null);
        }
        return SUCCESS;
    }
    /**
     * 显示要货金额报表数据显示页面
     *
     * @return
     * @throws Exception
     * @author luoq
     * @date 2016/12/26
     */
    public String showSalesDemandReportDataPage() throws Exception{
        Map paramterMap=CommonUtils.changeMap(request.getParameterMap());
        String businessdate1 = request.getParameter("businessdate1");
        String businessdate2 = request.getParameter("businessdate2");
        String groupcolsnum=request.getParameter("groupcolsnum");

        if(null==businessdate1){
            businessdate1 = "";
        }
        if(null==businessdate2){
            businessdate2 = "";
        }
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("paramterMap",paramterMap);
        request.setAttribute("map", map);

        saveGroupcol(groupcolsnum,"salesDemandReportListPage");//保存cookie

        return SUCCESS;
    }

    /**
     * 获取要货金额报表数据
     *
     * @return
     * @throws Exception
     * @author luoq
     * @date 2016/11/8
     */
    public String getSalesDemandReportData() throws Exception {
        Map colmap=getAccessColumn("t_report_sales_base");
        Map map = CommonUtils.changeMap(request.getParameterMap());
        Integer startNum;
        String page = request.getParameter("page");
        startNum = (Integer.parseInt(page) - 1) * pageSizeNum;
        pageMap.setCondition(map);
        pageMap.setRows(pageSizeNum);
        pageMap.setStartNum(startNum);
        PageData pageData = salesReportService.getSalesDemandReportList(pageMap);
        Map listmap = new HashMap();
        listmap.put("list", pageData.getList());
        listmap.put("footer", pageData.getFooter());
        listmap.put("colmap",colmap);
        request.setAttribute("listmap", listmap);
        addJSONObject(listmap);
        return SUCCESS;
    }

    /**
     * 单据追踪查询页面
     * @return
     * @throws Exception
     * @author luoq
     * @date 2016/11/4
     */
    public String showSalesDemandBillReportPage() {
        String userId = request.getParameter("uid");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = dateFormat.format(new Date());
        request.setAttribute("today", today);
        request.setAttribute("uid", userId);
        return SUCCESS;
    }

    /**
     * 单据查询信息列表
     *
     * @return
     * @throws Exception
     * @author luoqiang
     * @date 2016/11/10
     */
    public String getSalesDemandBillReportData() throws Exception {
        Map colmap=getAccessColumn("t_report_sales_base");
        Map map = CommonUtils.changeMap(request.getParameterMap());
        Integer startNum;
        String page = request.getParameter("page");
        startNum = (Integer.parseInt(page) - 1) * pageSizeNum;
        pageMap.setCondition(map);
        pageMap.setRows(pageSizeNum);
        pageMap.setStartNum(startNum);
        List list = phoneReportLineService.getSalesDemandBillReportData(pageMap);
        Map res = new HashMap();
        res.put("list", list);
        res.put("colmap",colmap);
        addJSONObject(res);
        return SUCCESS;
    }
    /**
     * 单据追踪
     * @return
     * @throws Exception
     * @author luoqiang
     * @date 2016/11
     */
    public String showSalesDemandBillOrderTrackReportPage() throws Exception{
        String id = request.getParameter("id");
        String orderid = request.getParameter("orderid");
        Map parammap=new HashMap();
        parammap.put("id",id);
        parammap.put("orderid",orderid);
        Map map = phoneReportLineService.getDemandBillOrderTrack(parammap);
        request.setAttribute("track", map);
        addJSONObject(map);
        return SUCCESS;
    }
    /**
     * 显示按客户销售情况报表页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Dec 22, 2016
     */
    public String showSalesCustomerReportPage() throws Exception{
        String today = CommonUtils.getTodayDataStr();
        String firstDay = CommonUtils.getMonthDateStr();
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("map", map);
        request.setAttribute("firstDay", firstDay);
        request.setAttribute("today", today);
        return "success";
    }
    /**
     * 手机按客户销售情况表按商品明细
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Dec 23, 2016
     */
    public String showSalesCustomerReportDataPage() throws Exception{
        String customerid= request.getParameter("customerid");
        String businessdate1 = request.getParameter("businessdate1");
        String businessdate2 = request.getParameter("businessdate2");
        String pcustomerid = request.getParameter("pcustomerid");
        String salesarea = request.getParameter("salesarea");
        String salesdept = request.getParameter("salesdept");
        if(null==businessdate1){
            businessdate1 = "";
        }
        if(null==businessdate2){
            businessdate2 = "";
        }
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("map", map);
        request.setAttribute("customerid", customerid);
        request.setAttribute("businessdate1", businessdate1);
        request.setAttribute("businessdate2", businessdate2);
        request.setAttribute("pcustomerid", pcustomerid);
        request.setAttribute("salesarea", salesarea);
        request.setAttribute("salesdept", salesdept);
        return SUCCESS;
    }
    /**
     * 手机按客户销售情况表按商品明细
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Dec 23, 2016
     */
    public String showSalesCustomerDetailPage() throws Exception{
        String customerid= request.getParameter("customerid");
        String customername = request.getParameter("customername");
        String businessdate1 = request.getParameter("businessdate1");
        String businessdate2 = request.getParameter("businessdate2");
        if(null==businessdate1){
            businessdate1 = "";
        }
        if(null==businessdate2){
            businessdate2 = "";
        }
        Map map = getAccessColumn("t_report_sales_base");
        request.setAttribute("map", map);
        request.setAttribute("customerid", customerid);
        request.setAttribute("customername", customername);
        request.setAttribute("businessdate1", businessdate1);
        request.setAttribute("businessdate2", businessdate2);
        return SUCCESS;
    }
    private void saveGroupcol(String cols,String page){
        Cookie[] cookies = request.getCookies();
        boolean hasgroupcol = false;
        if (null!=cookies) {
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(page+"groupcolsnum")){
                    hasgroupcol = true;
                    cookie.setValue(cols);
                    cookie.setPath("/");
                    cookie.setMaxAge(3600*24*7);
                    response.addCookie(cookie);
                }
            }
            if(!hasgroupcol){
                Cookie cookie = new Cookie(page+"groupcolsnum", cols);
                cookie.setPath("/");
                cookie.setMaxAge(3600*24*7);
                response.addCookie(cookie);
            }
        }
    }
    private String getGroupcol(String page){
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(page+"groupcolsnum")){
                String col=cookie.getValue();
                return col;
            }
        }
        return " ";
    }
    /**
     * 缺货商品统计页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Nov 23, 2013
     */
    public String showGoodsOutReportPage() throws Exception{
        String firstday = CommonUtils.getMonthDateStr();
        request.setAttribute("date",firstday);
        return SUCCESS;
    }
    /**
     * 缺货商品统计数据显示页面
     */
    public String showGoodsOutReportDataPage() throws Exception{
        Map paramterMap=CommonUtils.changeMap(request.getParameterMap());
        request.setAttribute("paramterMap",paramterMap);
        return SUCCESS;
    }
    /**
     * 缺货商品统计数据查询
     */
    public String getGoodsOutReportData() throws Exception{
//        Map colmap=getAccessColumn("t_report_sales_base");
        Map map = CommonUtils.changeMap(request.getParameterMap());
        Integer startNum;
        String page = request.getParameter("page");
        startNum = (Integer.parseInt(page) - 1) * pageSizeNum;
        pageMap.setCondition(map);
        pageMap.setRows(pageSizeNum);
        pageMap.setStartNum(startNum);
        PageData pageData=exceptionReportService.getGoodsOutReport(pageMap);
//        List list = phoneReportLineService.getSalesDemandBillReportData(pageMap);
        Map res = new HashMap();
        res.put("list", pageData.getList());
        res.put("footer",pageData.getFooter());
//        res.put("colmap",colmap);
        addJSONObject(res);
        return SUCCESS;
    }


    /**
     * 订货单报表显示
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Mar 09, 2018
     */
    public String showOrderGoodsReportPage() throws Exception {
        String firstday = CommonUtils.getMonthDateStr();
        request.setAttribute("date",firstday);
        return SUCCESS;
    }

    /**
     * 订货单报表数据显示页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Mar 09, 2018
     */
    public String showOrderGoodsReportDataPage() throws Exception{
        Map paramterMap=CommonUtils.changeMap(request.getParameterMap());
        request.setAttribute("paramterMap",paramterMap);
        return SUCCESS;
    }

    /**
     * 订货单报表数据获取数据
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Mar 09, 2018
     */
    public String getOrderGoodsReportData() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        map.put("isphone",true);
        Integer startNum;
        String page = request.getParameter("page");
        startNum = (Integer.parseInt(page) - 1) * pageSizeNum;
        pageMap.setCondition(map);
        pageMap.setRows(pageSizeNum);
        pageMap.setStartNum(startNum);
        PageData pageData = salesReportService.getOrderGoodsReportData(pageMap);
        Map res = new HashMap();
        res.put("list", pageData.getList());
        res.put("footer",pageData.getFooter());
        addJSONObject(res);
        return SUCCESS;
    }

    /**
     * 订货单报表数据显示页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Mar 09, 2018
     */
    public String showOrderGoodsDetailPage() throws Exception{
        Map map=CommonUtils.changeMap(request.getParameterMap());
        String ordergoodsid=(String)map.get("ordergoodsid");
        lockData("t_sales_goodsorder", ordergoodsid);
        map.put("isflag", "true");
        pageMap.setCondition(map);
        PageData pageData=salesReportService.getOrderGoodsReportData(pageMap);
        Map resMap=new HashMap();
        resMap.put("list", JSONUtils.listToJsonStr(pageData.getList()));
//        resMap.put("footer",pageData.getFooter());
        request.setAttribute("res",resMap);
        request.setAttribute("ordergoodsid",ordergoodsid);
        return SUCCESS;
    }

    /**
     * 手机销售订货单在线报表直接生成销售订单
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Mar 29, 2018
     */
    public String saveOrderByOrderGoodsReport() throws Exception{
        String datalist=request.getParameter("datalist");
        List<OrderGoodsDetail> orderGoodsDetailList = JSONUtils.jsonStrToList(datalist, new OrderGoodsDetail());
        List<OrderDetail> orderDetailList=new ArrayList<OrderDetail>();
        String ordergoodsid="";
        for(OrderGoodsDetail orderGoodsDetail:orderGoodsDetailList){
            if(orderGoodsDetail.getIsorderunitnum()==null||orderGoodsDetail.getIsorderunitnum().compareTo(BigDecimal.ZERO)==0){
                continue;
            }
            OrderGoodsDetail oldOrderGoodsDetail=orderGoodsService.getOrderGoodsDetail(orderGoodsDetail.getId());
            //判断本次要生成订单的订货单未生成数量是否和数据库里的数据一样，为了防止页面没有及时刷新导致的错误
            if(oldOrderGoodsDetail.getNotorderunitnum().compareTo(oldOrderGoodsDetail.getNotorderunitnum())!=0){
                Map map=new HashMap();
                map.put("msg","数据已经更改，请刷新页面");
                map.put("flag",false);
                addJSONObject(map);
                return SUCCESS;
            }
            orderGoodsDetail.setUnitnum(orderGoodsDetail.getIsorderunitnum());
            orderGoodsDetail.setTaxamount(orderGoodsDetail.getUnitnum().multiply(orderGoodsDetail.getTaxprice()));
            orderGoodsDetail.setNotaxamount(orderGoodsDetail.getUnitnum().multiply(orderGoodsDetail.getNotaxprice()));
            OrderDetail orderDetail= (OrderDetail)JSONUtils.jsonStrToObject(JSONUtils.objectToJsonStr(orderGoodsDetail),new OrderDetail());
            orderDetailList.add(orderDetail);
            ordergoodsid=orderGoodsDetail.getOrderid();
        }
        if(orderDetailList.size()==0){
            Map map=new HashMap();
            map.put("msg","没有可以生成订单的数据");
            map.put("flag",false);
            addJSONObject(map);
            return SUCCESS;
        }
        Map map=orderGoodsService.addOrderByOrderGoodsBill(ordergoodsid,orderDetailList);
        addJSONObject(map);
        addLog("根据订货单"+ordergoodsid+"生成销售订单", map);
        return SUCCESS;
    }

    /**
     * 银行账户余额查询页面
     * @return
     * @throws Exception
     * @author zhengziyong
     * @date Nov 23, 2013
     */
    public String showBankAmountPage() throws Exception{
        return SUCCESS;
    }

    /**
     * 银行账户余额数据显示页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Feb 28, 2018
     */
    public String showBankAmountDataPage() throws Exception {
        Map paramterMap=CommonUtils.changeMap(request.getParameterMap());
        request.setAttribute("paramterMap",paramterMap);
        return SUCCESS;
    }

    /**
     * 获取银行账户余额查询数据
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Feb 28, 2018
     */
    public String getBankAmountData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        Integer startNum;
        String page = request.getParameter("page");
        startNum = (Integer.parseInt(page) - 1) * pageSizeNum;
        pageMap.setCondition(map);
        pageMap.setRows(pageSizeNum);
        pageMap.setStartNum(startNum);
        PageData pageData=bankAmountService.showBankAmountList(pageMap);
        Map res = new HashMap();
        res.put("list", pageData.getList());
        res.put("footer",pageData.getFooter());
        addJSONObject(res);
        return SUCCESS;
    }

    /**
     * 显示银行账户日志页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Feb 28, 2018
     */
    public String showBankAmountLogPage() throws Exception{
        String bankid=request.getParameter("bankid");
        request.setAttribute("bankid",bankid);
        List list=getBaseSysCodeService().showSysCodeListByType("bankAmountOthersBilltype");
        request.setAttribute("billtypeList",list);
        request.setAttribute("today",CommonUtils.getTodayDateStr());
        return SUCCESS;
    }

    /**
     * 获取银行余额日志数据
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Feb 28, 2018
     */
    public String getBankAmountLogData() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        Integer startNum;
        String page = request.getParameter("page");
        startNum = (Integer.parseInt(page) - 1) * pageSizeNum;
        pageMap.setCondition(map);
        pageMap.setRows(pageSizeNum);
        pageMap.setStartNum(startNum);
        PageData pageData = bankAmountService.showBankAmountLogList(pageMap);

        List<BankAmountLog> list = pageData.getList();
        for(BankAmountLog log : list) {
            BankAmountOthers bankAmountOthers = bankAmountService.getBankAmountOthersByID(log.getBillid());
            //依据单据类型加入对应的对象到备注
            String userName = showUserInfo(bankAmountOthers);
            log.setRemark(log.getRemark()+""+userName);
        }
        Map res = new HashMap();
        res.put("list", pageData.getList());
        res.put("footer",pageData.getFooter());
        addJSONObject(res);
        return SUCCESS;
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

}
