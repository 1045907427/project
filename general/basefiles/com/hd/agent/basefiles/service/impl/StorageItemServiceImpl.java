package com.hd.agent.basefiles.service.impl;

import com.hd.agent.basefiles.dao.StorageItemMapper;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.basefiles.model.StorageItemGoods;
import com.hd.agent.basefiles.service.IStorageItemService;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.SysCode;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luoq on 2017/11/21.
 */
public class StorageItemServiceImpl extends BaseFilesServiceImpl implements IStorageItemService {
    private StorageItemMapper storageItemMapper;

    public StorageItemMapper getStorageItemMapper() {
        return storageItemMapper;
    }

    public void setStorageItemMapper(StorageItemMapper storageItemMapper) {
        this.storageItemMapper = storageItemMapper;
    }

//    /**
//     * 获取仓库货位
//     * @param pageMap
//     * @return com.hd.agent.common.util.PageData
//     * @throws
//     * @author luoqiang
//     * @date Nov 22, 2017
//     */
//    @Override
//    public PageData getStorageItemList(PageMap pageMap) throws Exception{
//        List<StorageItem> list=storageItemMapper.getStorageItemList(pageMap);
//        for(StorageItem storageItem:list){
//            String storageid=storageItem.getStorageid();
//            StorageInfo storageInfo=getStorageInfoByID(storageid);
//            if(storageInfo!=null){
//                storageItem.setStoragename(storageInfo.getName());
//            }
//        }
//        int count=storageItemMapper.getStorageItemCount(pageMap);
//        PageData pageData=new PageData(count,list,pageMap);
//        return null;
//    }

    /**
     * 保存仓库货位
     * @param storageItemGoods
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    @Override
    public Map addStorageItemGoods(StorageItemGoods storageItemGoods){
        Map resMap=new HashMap();
        StorageItemGoods oldstorageItemGoods=getStorageItemGoods(storageItemGoods.getGoodsid(),storageItemGoods.getStorageid());
        int i=0;
        //如果商品货位不存在，新增，存在就修改商品货位
        if(oldstorageItemGoods==null){
           i=storageItemMapper.addStorageItemGoods(storageItemGoods);
        }else{
            oldstorageItemGoods.setItemno(storageItemGoods.getItemno());
            i=storageItemMapper.editStorageItemGoods(oldstorageItemGoods);
        }
        resMap.put("flag",i>0);
        return resMap;
    }

    /**
     * 根据编码和仓库获取仓库货位档案
     * @param goodsid
     * @param storageid
     * @return com.hd.agent.basefiles.model.StorageItem
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    @Override
    public StorageItemGoods getStorageItemGoods(String goodsid,String storageid){
        StorageItemGoods storageItemGoods=storageItemMapper.getStorageItemGoods(goodsid.trim(),storageid);
        return storageItemGoods;
    }

    /**
     * 保存仓库货位
     * @param storageItemGoods
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Nov 22, 2017
     */
    @Override
    public Map editStorageItemGoods(StorageItemGoods storageItemGoods){
        Map resMap=new HashMap();
        int i=storageItemMapper.editStorageItemGoods(storageItemGoods);
        resMap.put("flag",i>0);
        return resMap;
    }
//    /**
//     * 判断仓库货位编码是否重复
//     * @param id
//     * @return java.lang.Boolean
//     * @throws
//     * @author luoqiang
//     * @date Nov 22, 2017
//     */
//    @Override
//    public Boolean isRepeatStorageItemID(String id){
//        StorageItem storageItem=storageItemMapper.getStorageItem(id);
//        if(storageItem==null){
//            return false;
//        }else{
//            return true;
//        }
//    }

//    /**
//     * 判断名称是否在仓库中重复
//     * @param name
//     * @param storageid
//     * @return java.lang.Boolean
//     * @throws
//     * @author luoqiang
//     * @date Nov 22, 2017
//     */
//    @Override
//    public Boolean isRepeatStorageItemName(String name,String storageid){
//        List<StorageItem> storageItemList=storageItemMapper.getStorageItemByNameAndStorage(name,storageid);
//        if(storageItemList.size()==0){
//            return false;
//        }else{
//            return true;
//        }
//    }

    /**
     * 获取仓库下的商品货位
     * @param
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Nov 23, 2017
     */
    @Override
    public PageData showStorageItemGoodsList(PageMap pageMap) throws Exception{
        List<StorageItemGoods> storageItemGoodsList=storageItemMapper.getStorageItemGoodsList(pageMap);
        for(StorageItemGoods storageItemGoods:storageItemGoodsList){
            String goodsid=storageItemGoods.getGoodsid();
            GoodsInfo goodsInfo=getGoodsInfoByID(goodsid);
            if(goodsInfo!=null){
                storageItemGoods.setGoodsname(goodsInfo.getName());
            }

            String storageid=storageItemGoods.getStorageid();
            StorageInfo storageInfo=getStorageInfoByID(storageid);
            if(storageInfo!=null){
                storageItemGoods.setStoragename(storageInfo.getName());
            }
        }
        int count=storageItemMapper.getStorageItemGoodsCount(pageMap);
        PageData pageData=new PageData(count,storageItemGoodsList,pageMap);
        return pageData;
    }

    /**
     * 删除仓库商品货位
     * @param storageid
     * @param idstr
     * @return Map
     * @throws
     * @author luoqiang
     * @date Nov 23, 2017
     */
    @Override
    public Map deleteStorageItemGoods(String storageid,String idstr){
        int i=storageItemMapper.deleteStorageItemGoods(storageid,idstr);
        Map resMap=new HashMap();
        resMap.put("flag",i>0);
        return resMap;
    }

    /**
     * 导入仓库商品货位
     * @param list
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Nov 23, 2017
     */
    public List<Map<String, Object>> importStorageItemGoods(List list) throws Exception{
        List<Map<String, Object>> errorList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> temp =  (Map<String, Object>)list.get(i);
            String goodsid = (String) temp.get("goodsid");
            String storageid = (String) temp.get("storageid");
            String itemno = (String) temp.get("itemno");
            StringBuilder msgSb = new StringBuilder();

            //验证仓库
            if (StringUtils.isEmpty(storageid)) {
                Map errorMap = new HashMap(temp);
                errorMap.put("lineno", (i + 2));
                errorMap.put("errors", "仓库编码为空。");
                errorList.add(errorMap);
                continue;
            } else {
                StorageInfo storageInfo = getStorageInfoByID(storageid);
                if (storageInfo == null) {
                    Map errorMap = new HashMap(temp);
                    errorMap.put("lineno", (i + 2));
                    errorMap.put("errors","仓库"+storageid+"不存在；");
                    errorList.add(errorMap);
                    continue;
                }
            }

            // 验证商品
            if (StringUtils.isEmpty(goodsid)) {
                Map errorMap = new HashMap(temp);
                errorMap.put("lineno", (i + 2));
                errorMap.put("errors","商品编码为空；");
                errorList.add(errorMap);
                continue;
            } else {
                GoodsInfo goodsInfo= getGoodsInfoByID(goodsid);
                if (goodsInfo == null) {
                    Map errorMap = new HashMap(temp);
                    errorMap.put("lineno", (i + 2));
                    errorMap.put("errors","商品"+goodsid+"不存在；");
                    errorList.add(errorMap);
                    continue;
                }
            }

            StorageItemGoods storageItemGoods=new StorageItemGoods();
            storageItemGoods.setGoodsid(goodsid);
            storageItemGoods.setStorageid(storageid);
            storageItemGoods.setItemno(itemno);
            addStorageItemGoods(storageItemGoods);

            if (msgSb.length() > 0) {
                Map errorMap = new HashMap(temp);
                errorMap.put("lineno", (i + 2));
                errorMap.put("errors", new String(msgSb));
                errorList.add(errorMap);
            }
        }
        return errorList;
    }

    @Override
    public PageData showStorageInfoList(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_base_storage_info", null);
        pageMap.setDataSql(dataSql);
        List<StorageInfo> list = storageItemMapper.showStorageInfoList(pageMap);
        for(StorageInfo storageInfo : list){
            SysCode storagetype = getBaseSysCodeMapper().getSysCodeInfo(storageInfo.getStoragetype(), "storagetype");
            if(null != storagetype){
                storageInfo.setStoragetypename(storagetype.getCodename());
            }
        }
        PageData pageData = new PageData(storageItemMapper.showStorageInfoCount(pageMap),list,pageMap);
        return pageData;
    }

    /**
     * 根据商品和仓库获取商品货位
     * @param goodsid
     * @param storageid
     * @return java.lang.String
     * @throws
     * @author luoqiang
     * @date Nov 28, 2017
     */
    @Override
    public String getStorageGoodsItem(String goodsid,String storageid) throws Exception{
        StorageItemGoods storageItemGoods=storageItemMapper.getStorageItemGoods(goodsid,storageid);
        if(storageItemGoods!=null){
            return storageItemGoods.getItemno();
        }else{
            GoodsInfo goodsInfo=getGoodsInfoByID(goodsid);
            return goodsInfo.getItemno();
        }
    }
}
