package com.hd.agent.basefiles.action;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.StorageItemGoods;
import com.hd.agent.basefiles.service.IStorageItemService;
import com.hd.agent.basefiles.service.IStorageService;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.ExcelUtils;
import com.hd.agent.common.util.PageData;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by luoq on 2017/11/21.
 */
public class StorageItemAction extends BaseFilesAction {
    private IStorageItemService storageItemService;

    /**
     * 仓库档案service
     */
    private IStorageService storageService;

    private StorageItemGoods storageItemGoods;

    public IStorageItemService getStorageItemService() {
        return storageItemService;
    }

    public void setStorageItemService(IStorageItemService storageItemService) {
        this.storageItemService = storageItemService;
    }

    public StorageItemGoods getStorageItemGoods() {
        return storageItemGoods;
    }

    public void setStorageItemGoods(StorageItemGoods storageItemGoods) {
        this.storageItemGoods = storageItemGoods;
    }

    public IStorageService getStorageService() {
        return storageService;
    }

    public void setStorageService(IStorageService storageService) {
        this.storageService = storageService;
    }

    /**
     * 显示货位管理页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public String showStorageItemListPage() {
        return SUCCESS;
    }

//    /**
//     * 获取货位管理数据
//     * @param
//     * @return java.lang.String
//     * @throws
//     * @author luoqiang
//     * @date Nov 22, 2017
//     */
//    public String getStorageItemList() throws Exception{
//        Map map= CommonUtils.changeMap(request.getParameterMap());
//        pageMap.setCondition(map);
//        PageData pageData=storageItemService.getStorageItemList(pageMap);
//        addJSONObject(pageData);
//        return SUCCESS;
//    }

    /**
     * 仓库货位新增页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public String showStorageItemGoodsAddPage() {
        return SUCCESS;
    }

    /**
     * 仓库货位修改页面
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public String showStorageItemGoodsEditPage() {
        String goodsid=request.getParameter("goodsid");
        String storageid=request.getParameter("storageid");
        StorageItemGoods storageItemGoods=storageItemService.getStorageItemGoods(goodsid, storageid);
        request.setAttribute("storageItemGoods",storageItemGoods);
        return SUCCESS;
    }

    /**
     * 添加商品货位
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public String addStorageItemGoods() throws Exception {
        SysUser sysUser=getSysUser();
//        storageItem.setAdduserid(sysUser.getUserid());
//        storageItem.setAddusername(sysUser.getUsername());
//        storageItem.setAdddeptid(sysUser.getDeptid());
        Map map=storageItemService.addStorageItemGoods(storageItemGoods);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 修改仓库货位
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public String editStorageItemGoods() throws Exception {
        Map map=storageItemService.editStorageItemGoods(storageItemGoods);
        addJSONObject(map);
        return SUCCESS;
    }

    /**
     * 获取所有的仓库
     * @param
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    public String getStorageList() throws Exception{
        Map map= CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData=storageItemService.showStorageInfoList(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 获取仓库下的货位商品
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Nov 23, 2017
     */
    public String showStorageItemGoodsList() throws Exception{
        Map map= CommonUtils.changeMap(request.getParameterMap());
        pageMap.setCondition(map);
        PageData pageData=storageItemService.showStorageItemGoodsList(pageMap);
        addJSONObject(pageData);
        return SUCCESS;
    }

    /**
     * 删除仓库商品货位
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Nov 23, 2017
     */
    public String deleteStorageItemGoods() throws Exception {
        String storageid=request.getParameter("storageid");
        String idstr=request.getParameter("idstr");
        Map map=storageItemService.deleteStorageItemGoods(storageid,idstr);
        addJSONObject(map);
        return SUCCESS;
    }
    /**
     * 导出仓库商品货位
     * @param
     * @return void
     * @throws
     * @author luoqiang
     * @date Nov 23, 2017
     */
    public void exportStorageItemGoods() throws Exception{
        Map map = CommonUtils.changeMap(request.getParameterMap());
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
        PageData pageData=storageItemService.showStorageItemGoodsList(pageMap);
        List<StorageItemGoods> list = pageData.getList();

        List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
        Map<String, Object> firstMap = new LinkedHashMap<String, Object>();

        firstMap.put("storageid", "仓库编码");
        firstMap.put("storagename", "仓库名称");
        firstMap.put("goodsid", "商品编码");
        firstMap.put("goodsname", "商品名称");
        firstMap.put("itemno", "货位");
        result.add(firstMap);

        if(null!=list && list.size() != 0){
            for(StorageItemGoods storageItemGoods : list){
                Map<String, Object> retMap = new LinkedHashMap<String, Object>();
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2 = PropertyUtils.describe(storageItemGoods);
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
     * 导入仓库商品货位
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Nov 23, 2017
     */
    public String importStorageItemGoods() throws Exception{
        Map<String,Object> retMap = new HashMap<String,Object>();
        try{
            //获取第一行数据为字段的描述列表
            List<String> firstLine = ExcelUtils.importExcelFirstRow(excelFile);
            List<String> titleList = new ArrayList<String>();

            Map titleMap = new HashMap();
            titleMap.put("仓库编码", "storageid");
            titleMap.put("商品编码", "goodsid");
            titleMap.put("货位", "itemno");

            for(String str : firstLine){

                titleList.add((String) titleMap.get(str));
            }

            //获取导入数据
            List<Map<String, Object>> list = ExcelUtils.importExcel(excelFile, titleList);
            if(list.size() != 0){

                List<Map<String, Object>> errorList =storageItemService.importStorageItemGoods(list);
                if(errorList.size() > 0){

                    Map errorTitleMap = new LinkedHashMap();
                    errorTitleMap.put("storageid", "仓库编码");
                    errorTitleMap.put("goodsid", "商品编码");
                    errorTitleMap.put("itemno", "货位");
                    errorTitleMap.put("lineno", "行号");
                    errorTitleMap.put("errors", "错误信息");

                    String fileid = createErrorFile(errorTitleMap, errorList, request);
                    retMap.put("msg", "导入失败" + errorList.size() + "条");
                    retMap.put("errorid",fileid);
                } else {
                    retMap.put("flag", true);
                }
            }else{
                retMap.put("excelempty", true);
            }

        }catch (Exception e) {
            e.printStackTrace();
            retMap.put("exception", true);
        }
        addJSONObject(retMap);
        return SUCCESS;
    }

    /**
     * 根据商品和仓库获取商品货位
     * @param
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Nov 28, 2017
     */
    public String getItemByGoodsAndStorage() throws Exception{
        String goodsid=request.getParameter("goodsid");
        String storageid=request.getParameter("storageid");
        String itemno=storageItemService.getStorageGoodsItem(goodsid, storageid);
        addJSONObject("itemno",itemno);
        return SUCCESS;
    }
}
