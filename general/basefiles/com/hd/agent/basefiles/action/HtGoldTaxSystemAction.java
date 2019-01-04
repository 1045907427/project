package com.hd.agent.basefiles.action;

import com.hd.agent.basefiles.model.JsTaxTypeCode;
import com.hd.agent.basefiles.model.JsTaxTypeCodeBarcode;
import com.hd.agent.basefiles.service.IHtGoldTaxSystemService;
import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.SpringContextUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 航天金税
 */
public class HtGoldTaxSystemAction extends BaseAction {
    private IHtGoldTaxSystemService htGoldTaxSystemService;

    public IHtGoldTaxSystemService getHtGoldTaxSystemService() {
        return htGoldTaxSystemService;
    }

    public void setHtGoldTaxSystemService(IHtGoldTaxSystemService htGoldTaxSystemService) {
        this.htGoldTaxSystemService = htGoldTaxSystemService;
    }

    private JsTaxTypeCode jsTaxTypeCode;

    public JsTaxTypeCode getJsTaxTypeCode() {
        return jsTaxTypeCode;
    }

    public void setJsTaxTypeCode(JsTaxTypeCode jsTaxTypeCode) {
        this.jsTaxTypeCode = jsTaxTypeCode;
    }

    private JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode;

    public JsTaxTypeCodeBarcode getJsTaxTypeCodeBarcode() {
        return jsTaxTypeCodeBarcode;
    }

    public void setJsTaxTypeCodeBarcode(JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode) {
        this.jsTaxTypeCodeBarcode = jsTaxTypeCodeBarcode;
    }

    /**
     * 金税税收分类分页列表
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String showJsTaxTypeCodeListPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 金税税收分类分页列表数据
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String getJsTaxTypeCodePageListData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = htGoldTaxSystemService.getJsTaxTypeCodePageListData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 添加金税税收分类页面
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String showJsTaxTypeCodeAddPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 添加金税税收分类
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    @UserOperateLog(key = "JsTaxTypeCode", type = 2, value = "")
    public String addJsTaxTypeCode() throws Exception {
        Map resultMap = new HashMap();
        if (StringUtils.isEmpty(jsTaxTypeCode.getId())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "请填写相关编码");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if (StringUtils.isEmpty(jsTaxTypeCode.getGoodsname())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "请填写名称");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        resultMap = htGoldTaxSystemService.addJsTaxTypeCode(jsTaxTypeCode);
        Boolean flag=false;
        if(null!=resultMap){
            flag=(Boolean)resultMap.get("flag");
            if(null==flag){
                flag=false;
            }
        }
        addLog("新增金税税收分类 编号:" + jsTaxTypeCode.getId(), flag);
        addJSONObject("flag", flag);
        return SUCCESS;
    }

    /**
     * 编辑金税税收分类
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String showJsTaxTypeCodeEditPage() throws Exception {
        String id = request.getParameter("id");
        JsTaxTypeCode jsTaxTypeCode = htGoldTaxSystemService.getJsTaxTypeCodeById(id);
        request.setAttribute("jsTaxTypeCode", jsTaxTypeCode);
        return SUCCESS;
    }
    /**
     * 复制金税税收分类
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String showJsTaxTypeCodeCopyPage() throws Exception {
        String id = request.getParameter("id");
        JsTaxTypeCode jsTaxTypeCode = htGoldTaxSystemService.getJsTaxTypeCodeById(id);
        request.setAttribute("jsTaxTypeCode", jsTaxTypeCode);
        return SUCCESS;
    }
    /**
     * 编辑金税税收分类
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    @UserOperateLog(key = "JsTaxTypeCode", type = 3, value = "")
    public String editJsTaxTypeCode() throws Exception {
        Map resultMap = new HashMap();
        if (StringUtils.isEmpty(jsTaxTypeCode.getId())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "请填写相关编码");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if (StringUtils.isEmpty(jsTaxTypeCode.getGoodsname())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "请填写名称");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        boolean flag = htGoldTaxSystemService.editJsTaxTypeCode(jsTaxTypeCode);
        addLog("编辑金税税收分类 编号:" + jsTaxTypeCode.getId(), flag);
        addJSONObject("flag", flag);

        return SUCCESS;
    }

    /**
     * 删除金税税收分类
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    @UserOperateLog(key = "JsTaxTypeCode", type = 4, value = "")
    public String deleteJsTaxTypeCode() throws Exception {
        Map resultMap = new HashMap();
        String id=request.getParameter("id");
        if (null==id || "".equals(id.trim())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "请填写相关编码");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if (canTableDataDelete("t_base_jstaxtypecode", id)) {// 判断是否被引用
            // true可以删除，false不可以删除
            resultMap = htGoldTaxSystemService.deleteJsTaxTypeCode(id);
        } else {
            resultMap.put("flag",false);
            resultMap.put("msg", "该编码被引用,无法删除。");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        Boolean flag=false;
        if(null!=resultMap){
            flag=(Boolean)resultMap.get("flag");
            if(null==flag){
                flag=false;
                resultMap.put("flag",false);
            }
        }else{
            resultMap=new HashMap();
            resultMap.put("flag",false);
        }
        String modifygoodsid=(String)resultMap.get("modifygoodsid");
        if(null==modifygoodsid){
            modifygoodsid="";
        }
        String logs="删除金税税收分类 编号:" + id.trim();
        if(flag){
            logs=logs+" 成功;";
            if(!"".equals(modifygoodsid.trim())){
                logs=logs+"并将商品编号："+modifygoodsid.trim()+"所对应的税收分类编码字段设置为空。";
            }

        }
        addLog(logs);
        addJSONObject(resultMap);

        return SUCCESS;
    }

    /**
     * 金税税收分类查看页面
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String showJsTaxTypeCodeViewPage() throws Exception {
        String id = request.getParameter("id");
        JsTaxTypeCode jsTaxTypeCode = htGoldTaxSystemService.getJsTaxTypeCodeById(id);
        request.setAttribute("jsTaxTypeCode", jsTaxTypeCode);
        return SUCCESS;
    }
    /**
     * 判断金税税收分类编码是否存在
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String isUsedJsTaxTypeCodeById() throws Exception {
        String id = (String) request.getParameter("id");
        boolean isused = htGoldTaxSystemService.isUsedJsTaxTypeCodeById(id);
        addJSONObject("flag",isused);
        return SUCCESS;
    }

    /**
     * 导出金税税收分类数据
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String exportJsTaxTypeCodeData() throws Exception {
        Map map=CommonUtils.changeMap(request.getParameterMap());
        map.put("isNoPageflag", "true");
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
        PageData pageData=htGoldTaxSystemService.getJsTaxTypeCodePageListData(pageMap);
        ExcelUtils.exportExcel(exportJsTaxTypeCodeDataExcelFilter(pageData.getList()), title);
        return null;
    }

    /**
     * 导出金税税收分类数据数据转换，list专程符合excel导出的数据格式
     * @param list
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    private List<Map<String, Object>> exportJsTaxTypeCodeDataExcelFilter(List<JsTaxTypeCode> list) throws Exception{
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("id","编码");
        firstMap.put("mergeid","合并编码");
        firstMap.put("goodsname","货物和劳务名称");
        firstMap.put("description", "说明");
        firstMap.put("keyword", "关键字");
        result.add(firstMap);

        if(list.size() != 0){
            for(JsTaxTypeCode item : list){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map = new HashMap<String, Object>();
                map = PropertyUtils.describe(item);
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

    /**
     * 导入金税税收分类数据
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String importJsTaxTypeCodeData() throws Exception {
        Map resultMap=new HashMap();
        //默认消息
        resultMap.put("msg","导入商品和服务税收分类与编码失败");
        String importversion=request.getParameter("importversion");

        List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
        if(null==paramList){
            resultMap.put("msg","无法读取上传文件内容");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        List<String> paramList2 = new ArrayList<String>();
        for(String str : paramList){
            if("2".equals(importversion)) {
                if ("篇".equals(str)) {
                    paramList2.add("idpart");
                } else if ("类".equals(str)) {
                    paramList2.add("idcategory");
                } else if ("章".equals(str)) {
                    paramList2.add("idchapter");
                } else if ("节".equals(str)) {
                    paramList2.add("idsection");
                } else if ("条".equals(str)) {
                    paramList2.add("idarticle");
                } else if ("款".equals(str)) {
                    paramList2.add("idparagraph");
                } else if ("项".equals(str)) {
                    paramList2.add("idsubparagraph");
                } else if ("目".equals(str)) {
                    paramList2.add("iditem");
                } else if ("子目".equals(str)) {
                    paramList2.add("idsubitem");
                } else if ("细目".equals(str)) {
                    paramList2.add("iddetailitem");
                }else if("货物和劳务名称".equals(str)){
                    paramList2.add("goodsname");
                }else if("说明".equals(str)){
                    paramList2.add("description");
                }
                else if("关键字".equals(str)){
                    paramList2.add("keyword");
                }
                else{
                    paramList2.add("null");
                }
            }else{
                if("编码".equals(str)){
                    paramList2.add("id");
                }else if("货物和劳务名称".equals(str)){
                    paramList2.add("goodsname");
                }else if("说明".equals(str)){
                    paramList2.add("description");
                }
                else if("关键字".equals(str)){
                    paramList2.add("keyword");
                }
                else{
                    paramList2.add("null");
                }
            }
        }
        List<String> dataCellList = new ArrayList<String>();
        dataCellList.add("id");
        if("2".equals(importversion)) {
            dataCellList.add("idpart");
            dataCellList.add("idcategory");
            dataCellList.add("idchapter");
            dataCellList.add("idsection");
            dataCellList.add("idarticle");
            dataCellList.add("idparagraph");
            dataCellList.add("idsubparagraph");
            dataCellList.add("iditem");
            dataCellList.add("idsubitem");
            dataCellList.add("iddetailitem");
        }
        dataCellList.add("goodsname");
        dataCellList.add("description");
        dataCellList.add("keyword");
        dataCellList.add("errormessage");

        if(paramList.size() == paramList2.size()){
            List<Map<String, Object>> list = ExcelUtils.importExcelMoreSheet(excelFile, paramList2); //获取导入数据
            Map optionMap=new HashMap();
            optionMap.put("importversion",importversion);

            resultMap=htGoldTaxSystemService.importJsTaxTypeCodeData(list,optionMap);

            if(resultMap.containsKey("errorDataList") && null!=resultMap.get("errorDataList")){
                List<Map<String,Object>> errorDataList=(List<Map<String,Object>>)resultMap.get("errorDataList");
                resultMap.remove("errorDataList");
                if(errorDataList.size() > 0){
                    //模板文件路径
                    String tplfile="";
                    if("2".equals(importversion)){
                        tplfile="/basefiles/exceltemplet/htGoldTaxTypeCodeTemplet2.xls";
                    }else{
                        tplfile="/basefiles/exceltemplet/htGoldTaxTypeCodeTemplet.xls";
                    }
                    String outTplFilePath = request.getSession().getServletContext().getRealPath(tplfile);
                    IAttachFileService attachFileService=(IAttachFileService) SpringContextUtils.getBean("attachFileService");
                    String fileid=attachFileService.createExcelAndAttachFile(errorDataList, dataCellList, outTplFilePath,"导入商品和服务税收分类与编码失败");
                    resultMap.put("msg","导入失败"+errorDataList.size()+"条");
                    resultMap.put("errorid",fileid);
                }
            }
            Boolean flag=(Boolean)resultMap.get("flag");
            if(flag==true){
                String logs=(String)resultMap.get("importLogs");
                if(null!=logs && !"".equals(logs.trim())){
                    addLog("导入商品和服务税收分类与编码"+logs);
                }
            }
        }else{
            resultMap.put("flag",false);
            resultMap.put("msg","导入文件格式不正确");
        }
        addJSONObject(resultMap);
        return SUCCESS;
    }


    /**
     * 未关联金税税收分类商品信息分页列表
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String showUnLinkJsTypeCodeGoodsListPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 未关联金税税收分类商品信息分页列表数据
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String getUnLinkJsTypeCodeGoodsPageListData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = htGoldTaxSystemService.getUnLinkJsTypeCodeGoodsPageListData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 关联金税税收分类商品信息分页列表
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String showLinkJsTypeCodeGoodsListPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 关联金税税收分类商品信息分页列表数据
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String getLinkJsTypeCodeGoodsPageListData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = htGoldTaxSystemService.getLinkJsTypeCodeGoodsPageListData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 关联金税税收分类
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    @UserOperateLog(key = "JsTaxTypeCode", type = 4, value = "")
    public String addLinkJsTypeCode() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
        Map resultMap=htGoldTaxSystemService.addLinkJsTypeCode(map);
        String jstypeid=request.getParameter("jstypeid");
        if(null==jstypeid || "".equals(jstypeid)){
            resultMap.put("flag",false);
            resultMap.put("msg","请填写税收分类编码");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        Boolean flag=false;
        if(null!=resultMap){
            flag=(Boolean)resultMap.get("flag");
            if(null==flag){
                flag=false;
                resultMap.put("flag",false);
            }
        }else{
            resultMap=new HashMap();
            resultMap.put("flag",false);
        }
        String updategoodslog=(String)resultMap.get("updategoodslog");
        if(null==updategoodslog){
            updategoodslog="";
        }
        String logs="关联金税税收分类 编号:" + jstypeid.trim();
        if(flag){
            logs=logs+" 成功;";
            if(!"".equals(updategoodslog.trim())){
                logs=logs+"并将商品编号："+updategoodslog.trim()+"所对应的税收分类编码“"+jstypeid+"”字段设置成功。";
            }
        }
        addLog(logs);
        addJSONObject(resultMap);
        return SUCCESS;
    }

    /*=======================================================================================*/

    /**
     * 税收分类关联商品条形码分页列表
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String showJsTaxTypeCodeBarcodeListPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 税收分类关联商品条形码分页列表数据
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String getJsTaxTypeCodeBarcodePageListData() throws Exception {
        Map map = CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData = htGoldTaxSystemService.getJsTaxTypeCodeBarcodePageListData(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 添加税收分类关联商品条形码页面
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String showJsTaxTypeCodeBarcodeAddPage() throws Exception {
        return SUCCESS;
    }

    /**
     * 添加税收分类关联商品条形码
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    @UserOperateLog(key = "jsTaxTypeCodeBarcode", type = 2, value = "")
    public String addJsTaxTypeCodeBarcode() throws Exception {
        Map resultMap = new HashMap();
        if (StringUtils.isEmpty(jsTaxTypeCodeBarcode.getJstypeid())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "请填写金税税收分类编码");
            addJSONObject(resultMap);
        }
        if (StringUtils.isEmpty(jsTaxTypeCodeBarcode.getBarcode())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "请填写条形码");
            addJSONObject(resultMap);
        }
        resultMap = htGoldTaxSystemService.addJsTaxTypeCodeBarcode(jsTaxTypeCodeBarcode);
        Boolean flag=false;
        if(null!=resultMap){
            flag=(Boolean)resultMap.get("flag");
            if(null==flag){
                flag=false;
                resultMap.put("flag",flag);
            }
        }
        addLog("新增税收分类关联商品条形码 编号:" + jsTaxTypeCodeBarcode.getId(), flag);
        addJSONObject(resultMap);
        return SUCCESS;
    }

    /**
     * 编辑税收分类关联商品条形码
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String showJsTaxTypeCodeBarcodeEditPage() throws Exception {
        String id = request.getParameter("id");
        JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode = htGoldTaxSystemService.getJsTaxTypeCodeBarcodeById(id);
        request.setAttribute("jsTaxTypeCodeBarcode", jsTaxTypeCodeBarcode);
        return SUCCESS;
    }
    /**
     * 复制税收分类关联商品条形码
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String showJsTaxTypeCodeBarcodeCopyPage() throws Exception {
        String id = request.getParameter("id");
        JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode = htGoldTaxSystemService.getJsTaxTypeCodeBarcodeById(id);
        request.setAttribute("jsTaxTypeCodeBarcode", jsTaxTypeCodeBarcode);
        return SUCCESS;
    }
    /**
     * 编辑税收分类关联商品条形码
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    @UserOperateLog(key = "jsTaxTypeCodeBarcode", type = 3, value = "")
    public String editJsTaxTypeCodeBarcode() throws Exception {
        Map resultMap = new HashMap();

        if (StringUtils.isEmpty(jsTaxTypeCodeBarcode.getId())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "未找到相关要修改的信息");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if (StringUtils.isEmpty(jsTaxTypeCodeBarcode.getJstypeid())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "请填写相关编码");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        if (StringUtils.isEmpty(jsTaxTypeCodeBarcode.getBarcode())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "请填写名称");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        resultMap = htGoldTaxSystemService.editJsTaxTypeCodeBarcode(jsTaxTypeCodeBarcode);
        Boolean flag=false;
        if(null!=resultMap){
            flag=(Boolean) resultMap.get("flag");
            if(null==flag){
                flag=false;
            }
        }
        addLog("编辑税收分类关联商品条形码 编号:" +jsTaxTypeCodeBarcode.getId(), flag);
        addJSONObject("flag", flag);

        return SUCCESS;
    }

    /**
     * 税收分类关联商品条形码查看页面
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String showJsTaxTypeCodeBarcodeViewPage() throws Exception {
        String id = request.getParameter("id");
        JsTaxTypeCodeBarcode jsTaxTypeCodeBarcode = htGoldTaxSystemService.getJsTaxTypeCodeBarcodeById(id);
        request.setAttribute("jsTaxTypeCodeBarcode", jsTaxTypeCodeBarcode);
        return SUCCESS;
    }

    /**
     * 判断税收分类关联商品条形码编码是否存在
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String isUsedJsTaxTypeCodeBarcode() throws Exception {
        Map queryMap=CommonUtils.changeMap(request.getParameterMap());
        String notCurId=(String)queryMap.get("notCurId");
        if(null==notCurId || "".equals(notCurId.trim())){
            queryMap.remove("notCurId");
        }
        boolean isused = htGoldTaxSystemService.getJsTaxTypeCodeBarcodeCountBy(queryMap)>0;
        addJSONObject("flag",isused);
        return SUCCESS;
    }

    /**
     * 导出税收分类关联商品条形码数据
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String exportJsTaxTypeCodeBarcodeData() throws Exception {
        Map map=CommonUtils.changeMap(request.getParameterMap());
        map.put("isNoPageflag", "true");
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
        PageData pageData=htGoldTaxSystemService.getJsTaxTypeCodeBarcodePageListData(pageMap);
        ExcelUtils.exportExcel(exportJsTaxTypeCodeBarcodeDataExcelFilter(pageData.getList()), title);
        return null;
    }

    /**
     * 导出税收分类关联商品条形码数据数据转换，list专程符合excel导出的数据格式
     * @param list
     * @return
     * @throws Exception
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    private List<Map<String, Object>> exportJsTaxTypeCodeBarcodeDataExcelFilter(List<Map> list) throws Exception{
        String exportversion=request.getParameter("exportversion");
        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();
        firstMap.put("jstypeid","金税税收分类编码");
        firstMap.put("barcode","商品条形码");
        if("2".equals(exportversion)) {
            firstMap.put("goodsname", "货物和劳务名称");
            firstMap.put("description", "说明");
            firstMap.put("keyword", "关键字");
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

    /**
     * 导入税收分类关联商品条形码数据
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    public String importJsTaxTypeCodeBarcodeData() throws Exception {
        Map resultMap=new HashMap();
        //默认消息
        resultMap.put("msg","导入税收分类对应商品条形码关联信息失败");
        String existsidtip=request.getParameter("existsidtip");

        List<String> paramList = ExcelUtils.importExcelFirstRow(excelFile); //获取第一行数据为字段的描述列表
        if(null==paramList){
            resultMap.put("msg","无法读取上传文件内容");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        List<String> paramList2 = new ArrayList<String>();
        for(String str : paramList){
            if("金税税收分类编码".equals(str)){
                paramList2.add("jstypeid");
            }else if("商品条形码".equals(str)){
                paramList2.add("barcode");
            }else if("货物和劳务名称".equals(str)) {
                paramList2.add("goodsname");
            }else if("说明".equals(str)) {
                paramList2.add("description");
            }else if("关键字".equals(str)) {
                paramList2.add("keyword");
            }else{
                paramList2.add("null");
            }
        }
        List<String> dataCellList = new ArrayList<String>();
        dataCellList.add("jstypeid");
        dataCellList.add("barcode");
        dataCellList.add("errormessage");

        if(paramList.size() == paramList2.size()){
            List<Map<String, Object>> list = ExcelUtils.importExcelMoreSheet(excelFile, paramList2); //获取导入数据
            Map optionMap=new HashMap();
            optionMap.put("existsidtip",existsidtip);

            resultMap=htGoldTaxSystemService.importJsTaxTypeCodeBarcodeData(list,optionMap);

            if(resultMap.containsKey("errorDataList") && null!=resultMap.get("errorDataList")){
                List<Map<String,Object>> errorDataList=(List<Map<String,Object>>)resultMap.get("errorDataList");
                resultMap.remove("errorDataList");
                if(errorDataList.size() > 0){
                    //模板文件路径
                    String tplfile="/basefiles/exceltemplet/htGoldTaxTypeCodeBarcodeTemplet.xls";
                    String outTplFilePath = request.getSession().getServletContext().getRealPath(tplfile);
                    IAttachFileService attachFileService=(IAttachFileService) SpringContextUtils.getBean("attachFileService");
                    String fileid=attachFileService.createExcelAndAttachFile(errorDataList, dataCellList, outTplFilePath,"导入税收分类对应商品条形码关联信息失败");
                    resultMap.put("msg","导入失败"+errorDataList.size()+"条");
                    resultMap.put("errorid",fileid);
                }
            }
            Boolean flag=(Boolean)resultMap.get("flag");
            if(flag==true){
                String logs=(String)resultMap.get("importLogs");
                if(null!=logs && !"".equals(logs.trim())){
                    addLog("导入税收分类对应商品条形码关联信息"+logs);
                }
            }
        }else{
            resultMap.put("flag",false);
            resultMap.put("msg","导入文件格式不正确");
        }
        addJSONObject(resultMap);
        return SUCCESS;
    }
    /**
     * 删除金税税收分类对应商品条形码关联信息
     *
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Nov 27, 2017
     */
    @UserOperateLog(key = "jsTaxTypeCodeBarcode", type = 4, value = "")
    public String deleteJsTaxTypeCodeBarcode() throws Exception {
        Map resultMap = new HashMap();
        String id=request.getParameter("id");
        if (null==id || !"".equals(id.trim())) {
            resultMap.put("flag", false);
            resultMap.put("msg", "请填写相关编码");
            addJSONObject(resultMap);
            return SUCCESS;
        }
        boolean flag = htGoldTaxSystemService.deleteJsTaxTypeCodeBarcode(id.trim());
        addLog("编辑金税税收分类对应商品条形码关联信息 编号:" + id.trim(), flag);
        addJSONObject("flag", flag);

        return SUCCESS;
    }

    /**
     * 匹配商品档案
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Dec 01, 2017
     */
    @UserOperateLog(key = "jsTaxTypeCodeBarcode", type = 0, value = "")
    public String updateAndLinkGoodsInfoJsCodeByBarcode() throws Exception{
        Map resultMap=htGoldTaxSystemService.updateAndLinkGoodsInfoJsCode();
        Boolean flag=false;
        if(null!=resultMap){
            flag=(Boolean)resultMap.get("flag");
            if(null==flag){
                flag=false;
            }
        }
        String modifygoodsid=(String)resultMap.get("opGoodsid");
        if(null==modifygoodsid){
            modifygoodsid="";
        }
        String logs="根据商品条形码匹配金税税收分类";
        Integer itotal=(Integer)resultMap.get("itotal");
        Integer isuccess=(Integer)resultMap.get("isuccess");
        if(flag){
            logs=logs+" 成功;";
            logs=logs+";待匹配条数:"+itotal+",成功匹配："+isuccess;
            if(!"".equals(modifygoodsid.trim())){
                logs=logs+";影响商品档案中金税分类编码,商品编码："+modifygoodsid.trim()+"。";
            }
        }else{
            logs=logs+"失败";
            logs=logs+";待匹配条数:"+itotal;
        }

        addLog(logs);
        addJSONObject(resultMap);
        return SUCCESS;
    }
    /**
     * 系统将根据商品档案中金税分类编码和条形码生成关联数据
     * @param
     * @return java.lang.String
     * @throws
     * @author zhanghonghui
     * @date Dec 01, 2017
     */
    @UserOperateLog(key = "jsTaxTypeCodeBarcode", type = 0, value = "")
    public String updateAndCreateJsBarcodeData() throws Exception{
        Map resultMap=htGoldTaxSystemService.updateAndCreateJsBarcodeData();
        Boolean flag=false;
        if(null!=resultMap){
            flag=(Boolean)resultMap.get("flag");
            if(null==flag){
                flag=false;
            }
        }
        String modifygoodsid=(String)resultMap.get("opGoodsid");
        if(null==modifygoodsid){
            modifygoodsid="";
        }
        String logs="系统将根据商品档案中金税分类编码和条形码生成关联数据";
        Integer itotal=(Integer)resultMap.get("itotal");
        Integer isuccess=(Integer)resultMap.get("isuccess");
        if(flag){
            logs=logs+" 成功;";
            logs=logs+";待生成条数:"+itotal+",成功匹配："+isuccess;
        }else{
            logs=logs+"失败";
            logs=logs+";待生成条数:"+itotal;
        }

        addLog(logs);
        addJSONObject(resultMap);
        return SUCCESS;
    }
}
