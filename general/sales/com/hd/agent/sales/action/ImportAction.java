package com.hd.agent.sales.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.BuySupplier;
import com.hd.agent.basefiles.model.Customer;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.sales.model.ImportSet;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LINXX on 2015/9/22.
 */
public class ImportAction extends BaseSalesAction{

    private ImportSet importSet;

    private IAttachFileService attachFileService;

    public IAttachFileService getAttachFileService() {
        return attachFileService;
    }

    public void setAttachFileService(IAttachFileService attachFileService) {
        this.attachFileService = attachFileService;
    }

    public ImportSet getImportSet() {
        return importSet;
    }

    public void setImportSet(ImportSet importSet) {
        this.importSet = importSet;
    }

    public String importModelList() throws Exception{
        return SUCCESS ;
    }

    public String showImportModelData() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData=getImportService().showImportModelData(pageMap);
        addJSONObject(pageData);
        return  SUCCESS ;
    }

    /**
     * 跳转至模板新增页
     * @return
     * @throws Exception
     */
    public String showImportModelAddPage() throws Exception{
        return  SUCCESS ;
    }
    /**
     * 跳转至模板新增页（改造的新增页面）
     * @return
     * @throws Exception
     */
    public String showImportAddPage() throws Exception{
        String filePath = request.getSession().getServletContext().getRealPath("/basefiles/exceltemplet/importModelURL.txt");
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
        StringBuilder urlStr = new StringBuilder();
        String line = null ;
        while((line = br.readLine()) != null){
            urlStr.append(line);
        }
        request.setAttribute("modelJSON",urlStr);
        return  SUCCESS ;
    }
     /**
      * 跳转至URL方法参数配置页面
      * @author lin_xx
      * @date 2016/12/1
      */
    public String showImportParamAddPage() throws Exception{
        String id = request.getParameter("id");
        Map colMap = new HashMap();
        if("1".equals(id)){
            colMap.put("param","param");
            colMap.put("ctype","2");
            colMap.put("price","price");
            colMap.put("adaptive","adaptive");
            colMap.put("word","word");
        }else if("2".equals(id)){
            colMap.put("param","param");
            colMap.put("ctype","2");
            colMap.put("price","price");
            colMap.put("digital","digital");
            colMap.put("divide","divide");
            colMap.put("dateother","dateother");
        }else if("3".equals(id)){
            colMap.put("param","param");
            colMap.put("ctype","1");
            colMap.put("digitalHtml","digitalHtml");
        }else if("4".equals(id)){
            colMap.put("param","param");
            colMap.put("supplierid","supplierid");
            colMap.put("digital","digital");
        }else if("5".equals(id)){
            colMap.put("param","param");
            colMap.put("ctype","2");
            colMap.put("price","price");
            colMap.put("digital","digital");
            colMap.put("dateother","dateother");
        }else if("6".equals(id) ){
            colMap.put("param","param");
            colMap.put("ctype","2");
            colMap.put("retailprice","retailprice");
            colMap.put("digital","digital");
            colMap.put("dateother","dateother");
        }else if("7".equals(id)){
            colMap.put("param","param");
            colMap.put("ctype","2");
            colMap.put("price","price");
            colMap.put("digital","digital");
            colMap.put("divide","divide");
            colMap.put("dateother","dateother");
        }else if("8".equals(id)){
            colMap.put("param","param");
            colMap.put("price","price");
            colMap.put("goodsid","goodsid");
            colMap.put("suppliername","suppliername");
            colMap.put("storage","storage");
            colMap.put("digital","digital");
            colMap.put("word","word");
        }else if("10".equals(id)){//南京版本 账面退货用了9 规避版本间不兼容问题 以后添加模板方法请用10
            colMap.put("param","param");
            colMap.put("ctype","2");
            colMap.put("price","price");
            colMap.put("digital","digital");
            colMap.put("divide","divide");
            colMap.put("dateother","dateother");
        }
        request.setAttribute("colMap",colMap);
        request.setAttribute("url",request.getParameter("url"));
        request.setAttribute("urltype",request.getParameter("urltype"));
        return SUCCESS;
    }

     /**
      * URL参数方法保存
      * @author lin_xx
      * @date 2016/12/2
      */
     public String addModelParam() throws Exception{
         SysUser sysUser = getSysUser();
         importSet.setAdddeptid(sysUser.getDepartmentid());
         importSet.setAdddeptname(sysUser.getDepartmentname());
         importSet.setAddtime(new Date());
         importSet.setAdduserid(sysUser.getUserid());
         importSet.setAddusername(sysUser.getName());

         if("指定客户编号".equals(importSet.getCtype())){
             importSet.setCtype("1");
         }
         String fileparam = "";
         if(StringUtils.isNotEmpty(importSet.getFirstcol())){
             fileparam += "首行="+importSet.getFirstcol()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getGoodscol())){
             fileparam += "商品行="+importSet.getGoodscol()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getBeginrow())){
             fileparam += "开始列="+importSet.getBeginrow()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getGtype())){
             if("1".equals(importSet.getGtype())){
                 fileparam += "商品条形码="+importSet.getProductcol()+";";
             }else if("2".equals(importSet.getGtype())){
                 fileparam += "商品店内码="+importSet.getProductcol()+";";
             }else if("3".equals(importSet.getGtype())){
                 fileparam += "商品助记符="+importSet.getProductcol()+";";
             }else if("4".equals(importSet.getGtype())){
                 fileparam += "商品编码="+importSet.getProductcol()+";";
             }
         }
         if(StringUtils.isNotEmpty(importSet.getNumid())){
             if("0".equals(importSet.getNumid())){
                 fileparam += "商品数量="+importSet.getNumcol()+";";
             }else if("1".equals(importSet.getNumid())){
                 fileparam += "商品箱数="+importSet.getNumcol()+";";
             }
         }

         if(StringUtils.isNotEmpty(importSet.getCustomercol())){
             if(importSet.getUrl().contains("Html")){
                 fileparam += "数据去空="+importSet.getCustomercol()+";";
             }else{
                 fileparam += "客户单号位置="+importSet.getCustomercol()+";";
             }
         }
         if(StringUtils.isNotEmpty(importSet.getPricecol()) && !"crm/customerStorageOrder/importCustStorageModel.do".equals(importSet.getUrl())){
             fileparam += "商品单价="+importSet.getPricecol()+";";
             importSet.setPricetype("1");
         }
         if("crm/customerStorageOrder/importCustStorageModel.do".equals(importSet.getUrl()) && StringUtils.isNotEmpty(importSet.getPricecol()) ){
             fileparam += "商品零售价="+importSet.getPricecol()+";";
             importSet.setPricetype("1");
         }
         if(StringUtils.isNotEmpty(importSet.getCostpricecol())){
             fileparam += "商品成本价="+importSet.getPricecol()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getSuppliercol())){
             fileparam += "供应商编码="+importSet.getSuppliercol()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getBegincell())){
             fileparam += "开始单元格="+importSet.getBegincell()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getValidcol())){
             fileparam += "有效列="+importSet.getValidcol()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getDataposition())){
             fileparam += "数据位置="+importSet.getDataposition()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getHtmlFirstRow())){
             fileparam += "模板首行="+importSet.getHtmlFirstRow()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getCustomercell())){
             fileparam += "客户单元格="+importSet.getCustomercell()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getStoragecol())){
             fileparam += "仓库名称="+importSet.getStoragecol()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getRemarkcell())){
             fileparam += "备注="+importSet.getRemarkcell()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getDividecell())){
             fileparam += "拆分所在列="+importSet.getDividecell()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getOthercol())){
             fileparam += "日期/其它列="+importSet.getOthercol()+";";
         }
         if(StringUtils.isNotEmpty(importSet.getRegularcustomer())){
             fileparam += "客户正则="+importSet.getRegularcustomer()+";";
         }
         if(fileparam.endsWith(";")){
             fileparam = fileparam.substring(0,fileparam.length()-1);
         }
         importSet.setFileparam(fileparam);

         Map map = new HashMap();
         boolean flag = getImportService().addImportSet(importSet);
         map.put("flag",flag);
         addJSONObject(map);

         return SUCCESS;
     }

    /**
     * 保存新增模板信息
     * @return
     * @throws Exception
     */
    public String addImportModel() throws Exception{
        SysUser sysUser = getSysUser();
        importSet.setAdddeptid(sysUser.getDepartmentid());
        importSet.setAdddeptname(sysUser.getDepartmentname());
        importSet.setAddtime(new Date());
        importSet.setAdduserid(sysUser.getUserid());
        importSet.setAddusername(sysUser.getName());

        boolean flag = false ;
        Map map = new HashMap();
        String fileparam = importSet.getFileparam();
        if(StringUtils.isNotEmpty(fileparam)){
            if(fileparam.contains("商品单价")){
                importSet.setPricetype("1");
            }else{
                importSet.setPricetype("0");
            }
            if(fileparam.contains("商品条形码")){
                importSet.setGtype("1");
            }else if(fileparam.contains("商品店内码")){
                importSet.setGtype("2");
            }else if(fileparam.contains("商品助记符")){
                importSet.setGtype("3");
            }else if(fileparam.contains("商品编码")){
                importSet.setGtype("4");
            }
        }
        //html自适应导入 新增
        if("指定客户编号".equals(importSet.getCtype())){
            importSet.setCtype("1");
        }
        flag = getImportService().addImportSet(importSet);
        map.put("flag",flag);
        addJSONObject(map);
        return SUCCESS ;
    }

    @UserOperateLog(key="ImportSet",type=4)
    public String deleteImportModel() throws  Exception{
        Map resultMap=new HashMap();
        String id=request.getParameter("id");
        if(null==id || "".equals(id.trim())){
            resultMap.put("flag", false);
            resultMap.put("msg", "未能找到相关打印模板信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        resultMap=getImportService().deleteImportModel(id);
        if(null!=resultMap){
            boolean flag=(Boolean)resultMap.get("flag");
            addLog("删除编号："+id+"的模板数据", flag);
        }else{
            addLog("删除编号："+id+"模板数据");
        }
        addJSONObject(resultMap);

        return SUCCESS;
    }

    public String showImportModelEditPage() throws Exception{

        String id=request.getParameter("id");
        ImportSet importSet = getImportService().showImportModelById(id);
        if( !"".equals(importSet.getBusid())){
            Customer cu = getCustomerById(importSet.getBusid());
            if(null != cu){
                importSet.setBusname(cu.getName());
            }
        }
        boolean flag = true;
        if("sales/model/importAdaptiveExcelByColumn.do".equals(importSet.getUrl())
                || "sales/model/importByDigitalParam.do".equals(importSet.getUrl())
                || "sales/model/importHtmlBydigitalParam.do".equals(importSet.getUrl())
                || "crm/terminal/importCrmOrderModel.do".equals(importSet.getUrl())
                || "sales/importRejectModel.do".equals(importSet.getUrl())
                || "purchase/returnorder/importReturnModel.do".equals(importSet.getUrl())
                ){
            flag = false;
        }
        //供应商
        String supplierid = importSet.getSupplierid();
        if(StringUtils.isNotEmpty(supplierid)){
            BuySupplier buySupplier = getBaseBuySupplierById(supplierid);
            if(null != buySupplier){
                request.setAttribute("suppliername",buySupplier.getName());
            }
        }
        String filepath = importSet.getFilepath();
        String filename = filepath.substring(filepath.lastIndexOf("/")+1,filepath.length());
        request.setAttribute("filename",filename);
        request.setAttribute("importSet",importSet);
        request.setAttribute("flag",flag);
        return  SUCCESS;
    }
    
    @UserOperateLog(key="importSet",type=3)
    public String editImportModel() throws Exception{
        Map resultMap=new HashMap();
        if(null==importSet.getId()){
            resultMap.put("flag", false);
            resultMap.put("msg","抱歉，未能找到要修改的信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        ImportSet oldimportSet=getImportService().showImportModelById(importSet.getId().toString());
        if(null==oldimportSet){
            resultMap.put("flag", false);
            resultMap.put("msg", "抱歉，未能找到要修改的信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        String fileparam = importSet.getFileparam();
        if(StringUtils.isNotEmpty(fileparam)){
            if(fileparam.contains("商品条形码")){
                importSet.setGtype("1");
            }else if(fileparam.contains("商品店内码")){
                importSet.setGtype("2");
            }else if(fileparam.contains("商品助记符")){
                importSet.setGtype("3");
            }else if(fileparam.contains("商品编码")){
                importSet.setGtype("4");
            }
            if(fileparam.contains("商品单价") || fileparam.contains("零售价")){
                importSet.setPricetype("1");
            }else{
                importSet.setPricetype("0");
            }
        }
        if("3".equals(importSet.getCtype()) || "4".equals(importSet.getCtype())
                || "5".equals(importSet.getCtype()) || "6".equals(importSet.getCtype())){
            importSet.setBusid("");
        }
        SysUser sysUser = getSysUser();
        importSet.setModifytime(new Date());
        importSet.setModifyuserid(sysUser.getUserid());
        importSet.setModifyusername(sysUser.getName());
        boolean flag=getImportService().updateImportSet(importSet);
        addLog("编辑编号为"+importSet.getId()+"模板数据", flag);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    public String showImportModelViewPage() throws Exception{
        String id=request.getParameter("id");
        ImportSet importSet = getImportService().showImportModelById(id);
        if( !"".equals(importSet.getBusid())){
            Customer cu = getCustomerById(importSet.getBusid());
            if(null != cu){
                importSet.setBusname(cu.getName());
            }
        }
        //供应商
        String supplierid = importSet.getSupplierid();
        if(StringUtils.isNotEmpty(supplierid)){
            BuySupplier buySupplier = getBaseBuySupplierById(supplierid);
            if(null != buySupplier){
                request.setAttribute("suppliername",buySupplier.getName());
            }
        }
        boolean flag = true;
        if("sales/model/importAdaptiveExcelByColumn.do".equals(importSet.getUrl())
                || "sales/model/importByDigitalParam.do".equals(importSet.getUrl())
                || "sales/model/importHtmlBydigitalParam.do".equals(importSet.getUrl())
                || "crm/terminal/importCrmOrderModel.do".equals(importSet.getUrl())
                || "crm/customerStorageOrder/importCustStorageModel.do".equals(importSet.getUrl())){
            flag = false;
        }
        String filepath = importSet.getFilepath();
        String filename = filepath.substring(filepath.lastIndexOf("/")+1,filepath.length());
        request.setAttribute("filename",filename);
        request.setAttribute("importSet",importSet);
        request.setAttribute("flag",flag);
        return  SUCCESS;
    }

    public String enableImportModel() throws Exception{

        String id = request.getParameter("id");
        if(null==id || "".equals(id.trim())){
            addJSONObject("flag", false);
            addJSONObject("msg", "未找到要启用的数据");
            return SUCCESS;
        }
        Map resultMap=getImportService().enableImportModel(id);
        if(null!=resultMap){
            boolean flag=(Boolean)resultMap.get("flag");
            addLog("启用编号为"+id+"模板数据", flag);
        }else{
            addLog("启用编号为"+id+"模板数据");
        }
        addJSONObject(resultMap);

        return  SUCCESS;
    }

    public String disableImportModel() throws Exception{

        String id = request.getParameter("id");
        if(null==id || "".equals(id.trim())){
            addJSONObject("flag", false);
            addJSONObject("msg", "未找到要禁用的数据");
            return SUCCESS;
        }
        Map resultMap=getImportService().disableImportModel(id);
        if(null!=resultMap){
            boolean flag=(Boolean)resultMap.get("flag");
            addLog("禁用编号为"+id+"模板数据", flag);
        }else{
            addLog("禁用编号为"+id+"模板数据");
        }
        addJSONObject(resultMap);

        return  SUCCESS;
    }

}
