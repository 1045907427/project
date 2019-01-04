package com.hd.agent.phone.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.basefiles.model.StorageLocation;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.phone.service.IPhoneAllocateService;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IAllocateService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 手机调拨申请service实现类
 * Created by chenwei on 2016-07-23.
 */
public class PhoneAllocateServiceImpl extends BaseFilesServiceImpl implements IPhoneAllocateService {

    private IAllocateService allocateService;

    public IAllocateService getAllocateService() {
        return allocateService;
    }

    public void setAllocateService(IAllocateService allocateService) {
        this.allocateService = allocateService;
    }

    /**
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public PageData searchStorageGoodsList(PageMap pageMap) throws Exception {
        String con = (String) pageMap.getCondition().get("con");
        String storageid = (String) pageMap.getCondition().get("storageid");
        String brandids = (String) pageMap.getCondition().get("brandids");
        Map map = new HashMap();
        if(StringUtils.isNotEmpty(con)){
            map.put("goodscon", con);
            //多条件模糊查询 以空格为标识
            if(con.indexOf(" ")>=0){
                String[] conArr = con.split(" ");
                List<String> conList  = new ArrayList<String>();
                for(String constr : conArr){
                    constr = constr.trim();
                    if(StringUtils.isNotEmpty(constr)){
                        conList.add(constr);
                    }
                }
                map.put("conarr",conList);
            }
        }
        map.put("storageid", storageid);
        map.put("brandids", brandids);
        String dataSql = getDataAccessRule("t_base_goods_info", "g");
        map.put("datasql", dataSql);
        SysUser sysUser = getSysUser();
        //品牌业务员与厂家业务员不能同时存在
        //判断是否品牌业务员
        String brandUserRoleName = getSysParamValue("BrandUserRoleName");
        boolean isBrandUser = isSysUserHaveRole(brandUserRoleName);
        if(isBrandUser){
            map.put("isBrandUser", true);
            map.put("personnelid", sysUser.getPersonnelid());
        }else{
            //判断是否厂家业务员
            String supplierUserRoleName = getSysParamValue("SupplierUserRoleName");
            boolean isSupplierUser = isSysUserHaveRole(supplierUserRoleName);
            if(isSupplierUser){
                map.put("isSupplierUser", true);
                map.put("personnelid", sysUser.getPersonnelid());
            }
        }
        map.put("startNum",pageMap.getStartNum());
        map.put("rows",pageMap.getRows());
        List<GoodsInfo> list = getBaseFilesGoodsMapper().getStorageGoodsInfoListFroPhone(map);
        int totalCount = getBaseFilesGoodsMapper().gettStorageGoodsInfoListFroPhoneCount(map);

        List<Map> result = new ArrayList<Map>();
        if(null!=list){
            for(GoodsInfo goodsInfo : list){
                Map datamap = new HashMap();
                datamap.put("id", goodsInfo.getId());
                String name  = goodsInfo.getName();
                datamap.put("name", name);
                datamap.put("spell", goodsInfo.getSpell());
                datamap.put("barcode", goodsInfo.getBarcode());
                datamap.put("boxbarcode", goodsInfo.getBoxbarcode());
                datamap.put("brand", goodsInfo.getBrand());
                datamap.put("defaultsort", goodsInfo.getDefaultsort());
                GoodsInfo goods = getAllGoodsInfoByID(goodsInfo.getId());
                if(null!=goods){
                    datamap.put("auxunitname", goods.getAuxunitname());
                    datamap.put("unitname", goods.getMainunitName());
                    datamap.put("boxnum", goods.getBoxnum());
                }else{
                    datamap.put("auxunitname", "箱");
                    datamap.put("unitname", "");
                    datamap.put("boxnum", "1");
                }
                result.add(datamap);
            }
        }
        PageData pageData = new PageData(totalCount,result,pageMap);
        return pageData;
    }

    /**
     * 根据仓库编号和商品编号 获取仓库商品的相关信息
     *
     * @param storageid
     * @param goodsid
     * @return
     * @throws Exception
     */
    @Override
    public Map getStorageGoodsInfo(String storageid, String goodsid) throws Exception {
        Map map = new HashMap();
        String isbatch = "0";
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        StorageInfo storageInfo = getStorageInfoByID(storageid);
        if(null!=goodsInfo && null!=storageInfo){
            //判断该仓库中 商品是否批次管理
            if("1".equals(goodsInfo.getIsbatch()) && "1".equals(storageInfo.getIsbatch())){
                isbatch = "1";
                List<StorageSummaryBatch> list = getBaseStorageSummaryService().getStorageBatchListByStorageidAndGoodsid(storageid,goodsid);
                if(null!=list && list.size()>0){
                    List batchList = new ArrayList();
                    for(StorageSummaryBatch storageSummaryBatch : list){
                        Map bdata = new HashMap();
                        bdata.put("id",storageSummaryBatch.getId());
                        bdata.put("barcode",storageSummaryBatch.getBarcode());
                        bdata.put("storageid",storageSummaryBatch.getStorageid());
                        bdata.put("existingnum",storageSummaryBatch.getExistingnum());
                        bdata.put("usablenum",storageSummaryBatch.getUsablenum());
                        Map unitMap = countGoodsInfoNumber(goodsInfo.getId(),goodsInfo.getAuxunitid(),storageSummaryBatch.getUsablenum());
                        if(null!=unitMap){
                            String auxnumdetail = (String) unitMap.get("auxnumdetail");
                            bdata.put("usablenumdetail",auxnumdetail);
                        }
                        if(StringUtils.isNotEmpty(storageSummaryBatch.getBatchno())){
                            bdata.put("batchno",storageSummaryBatch.getBatchno());
                        }else{
                            bdata.put("batchno",storageSummaryBatch.getGoodsid());
                        }
                        bdata.put("produceddate",storageSummaryBatch.getProduceddate());
                        bdata.put("deadline",storageSummaryBatch.getDeadline());
                        StorageLocation storageLocation = getStorageLocation(storageSummaryBatch.getStoragelocationid());
                        if(null!=storageLocation){
                            bdata.put("storagelocationid",storageSummaryBatch.getStoragelocationid());
                            bdata.put("storagelocationname",storageLocation.getName());
                        }else{
                            bdata.put("storagelocationid","");
                            bdata.put("storagelocationname","");
                        }
                        batchList.add(bdata);
                    }
                    map.put("batchList",batchList);
                }
            }
            Map gMap = new HashMap();
            gMap.put("goodsid",goodsInfo.getId());
            gMap.put("goodsname",goodsInfo.getName());
            gMap.put("barcode",goodsInfo.getBarcode());
            gMap.put("boxbarcode",goodsInfo.getBoxbarcode());
            gMap.put("model",goodsInfo.getModel());
            gMap.put("spell",goodsInfo.getSpell());
            gMap.put("boxnum",goodsInfo.getBoxnum());
            gMap.put("price",goodsInfo.getNewstorageprice());

            gMap.put("unitid",goodsInfo.getMainunit());
            gMap.put("unitname",goodsInfo.getMainunitName());
            gMap.put("auxunitid",goodsInfo.getAuxunitid());
            gMap.put("auxunitname",goodsInfo.getAuxunitname());
            StorageSummary storageSummary = getBaseStorageSummaryService().getStorageSummaryByStorageAndGoods(storageid,goodsid);
            if(null!=storageSummary){
                gMap.put("existingnum",storageSummary.getExistingnum());
                gMap.put("usablenum",storageSummary.getUsablenum());
                Map unitMap = countGoodsInfoNumber(goodsInfo.getId(),goodsInfo.getAuxunitid(),storageSummary.getUsablenum());
                if(null!=unitMap){
                   String auxnumdetail = (String) unitMap.get("auxnumdetail");
                    gMap.put("usablenumdetail",auxnumdetail);
                }
            }else{
                gMap.put("existingnum","0");
                gMap.put("usablenum","0");
            }

            map.put("goods",gMap);
            map.put("isbatch",isbatch);
            map.put("flag",true);
            return map;
        }
        return null;
    }

    /**
     * 添加调拨申请单
     *
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public Map addAllocateNotice(JSONObject jsonObject) throws Exception {
        boolean flag = false;
        String msg = "";
        String id = "";
        SysUser sysUser = getSysUser();
        if(null!=jsonObject && jsonObject.has("list")){
            //手机上传的单据编号 防止重复上传
            String billid = jsonObject.getString("billid");
            String oId = allocateService.hasPhoneBillByAllocateNotice(billid);
            if(null!=oId){
                Map map = new HashMap();
                map.put("flag",flag);
                map.put("msg","该单据已上传,交款单编号："+oId);
                map.put("id",oId);
                return map;
            }
            AllocateNotice allocateNotice = new AllocateNotice();
            allocateNotice.setField01(billid);
            if(jsonObject.has("remark")){
                String remark = jsonObject.getString("remark");
                allocateNotice.setRemark(remark);
            }else{
                allocateNotice.setRemark("");
            }
            if(jsonObject.has("date")){
                String date = jsonObject.getString("date");
                allocateNotice.setBusinessdate(date);
            }else{
                allocateNotice.setBusinessdate(CommonUtils.getTodayDataStr());
            }
            String outstorageid = jsonObject.getString("outstorageid");
            String instorageid = jsonObject.getString("instorageid");
            allocateNotice.setOutstorageid(outstorageid);
            allocateNotice.setEnterstorageid(instorageid);

            allocateNotice.setStatus("2");
            allocateNotice.setAdduserid(sysUser.getUserid());
            allocateNotice.setAddusername(sysUser.getName());
            allocateNotice.setAdddeptid(sysUser.getDepartmentid());
            allocateNotice.setAdddeptname(sysUser.getDepartmentname());
            allocateNotice.setAddtime(new Date());

            List<AllocateNoticeDetail> list = new ArrayList<AllocateNoticeDetail>();
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            if(null!=jsonArray && jsonArray.size()>0){
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject detailObject = jsonArray.getJSONObject(i);
                    AllocateNoticeDetail allocateNoticeDetail = new AllocateNoticeDetail();
                    String goodsid = detailObject.getString("goodsid");
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
                    String remark = "";
                    if(detailObject.has("remark")){
                        remark = detailObject.getString("remark");
                    }
                    allocateNoticeDetail.setStorageid(outstorageid);
                    allocateNoticeDetail.setGoodsid(goodsid);
                    allocateNoticeDetail.setRemark(remark);
                    if(detailObject.has("summarybatchid")){
                        String summarybatchid = detailObject.getString("summarybatchid");
                        if(null!=summarybatchid){
                            allocateNoticeDetail.setSummarybatchid(summarybatchid);
                        }
                    }
                    if(detailObject.has("batchno")){
                        String batchno = detailObject.getString("batchno");
                        if(null!=batchno){
                            allocateNoticeDetail.setBatchno(batchno);
                        }
                    }
                    if(detailObject.has("produceddate")){
                        String produceddate = detailObject.getString("produceddate");
                        if(null!=produceddate){
                            allocateNoticeDetail.setProduceddate(produceddate);
                        }
                    }
                    if(detailObject.has("deadline")){
                        String deadline = detailObject.getString("deadline");
                        allocateNoticeDetail.setDeadline(deadline);
                    }
                    if(detailObject.has("storagelocationid")){
                        String storagelocationid = detailObject.getString("storagelocationid");
                        if(null!=storagelocationid){
                            allocateNoticeDetail.setStoragelocationid(storagelocationid);
                        }
                    }
                    if(detailObject.has("enterbatchno")){
                        String enterbatchno = detailObject.getString("enterbatchno");
                        if(null!=enterbatchno){
                            allocateNoticeDetail.setEnterbatchno(enterbatchno);
                        }
                    }
                    if(detailObject.has("enterproduceddate")){
                        String enterproduceddate = detailObject.getString("enterproduceddate");
                        if(null!=enterproduceddate){
                            allocateNoticeDetail.setEnterproduceddate(enterproduceddate);
                        }
                    }
                    if(detailObject.has("enterdeadline")){
                        String enterdeadline = detailObject.getString("enterdeadline");
                        if(null!=enterdeadline){
                            allocateNoticeDetail.setEnterdeadline(enterdeadline);
                        }
                    }
                    if(detailObject.has("enterstoragelocationid")){
                        String enterstoragelocationid = detailObject.getString("enterstoragelocationid");
                        if(null!=enterstoragelocationid){
                            allocateNoticeDetail.setEnterstoragelocationid(enterstoragelocationid);
                        }
                    }
                    if(detailObject.has("unitid")){
                        String unitid = detailObject.getString("unitid");
                        if(null!=unitid){
                            allocateNoticeDetail.setUnitid(unitid);
                        }
                    }
                    if(detailObject.has("unitname")){
                        String unitname = detailObject.getString("unitname");
                        if(null!=unitname){
                            allocateNoticeDetail.setUnitname(unitname);
                        }
                    }

                    if(detailObject.has("auxunitid")){
                        String auxunitid = detailObject.getString("auxunitid");
                        if(null!=auxunitid){
                            allocateNoticeDetail.setAuxunitid(auxunitid);
                        }
                    }
                    if(detailObject.has("auxunitname")){
                        String auxunitname = detailObject.getString("auxunitname");
                        if(null!=auxunitname){
                            allocateNoticeDetail.setAuxunitname(auxunitname);
                        }
                    }
                    String priceStr = "0";
                    if(detailObject.has("price")){
                        priceStr = detailObject.getString("price");
                    }
                    String unitnumStr = "0";
                    if(detailObject.has("unitnum")){
                        unitnumStr = detailObject.getString("unitnum");
                    }
                    BigDecimal price = new BigDecimal(priceStr);
                    BigDecimal unitnum = new BigDecimal(unitnumStr);
                    BigDecimal amount = price.multiply(unitnum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);

                    //计算辅单位数量
                    Map auxmap = countGoodsInfoNumber(allocateNoticeDetail.getGoodsid(), allocateNoticeDetail.getAuxunitid(), unitnum);
                    if(auxmap.containsKey("auxnum")){
                        allocateNoticeDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
                    }
                    if(auxmap.containsKey("auxInteger")){
                        String auxnumStr = (String) auxmap.get("auxInteger");
                        allocateNoticeDetail.setAuxnum(new BigDecimal(auxnumStr));
                    }
                    if(auxmap.containsKey("auxremainder")){
                        String auxremainderStr = (String) auxmap.get("auxremainder");
                        allocateNoticeDetail.setAuxremainder(new BigDecimal(auxremainderStr));
                    }
                    if(auxmap.containsKey("auxnumdetail")){
                        String auxnumdetail = (String) auxmap.get("auxnumdetail");
                        allocateNoticeDetail.setAuxnumdetail(auxnumdetail);
                    }

                    allocateNoticeDetail.setTaxprice(price);
                    allocateNoticeDetail.setUnitnum(unitnum);


                    BigDecimal notaxamount = getNotaxAmountByTaxAmount(amount, goodsInfo.getDefaulttaxtype());
                    allocateNoticeDetail.setTaxamount(amount);
                    allocateNoticeDetail.setNotaxamount(notaxamount);
                    if(unitnum.compareTo(BigDecimal.ZERO)!=0){
                        BigDecimal notaxprice = notaxamount.divide(unitnum,6,BigDecimal.ROUND_HALF_UP);
                        allocateNoticeDetail.setNotaxprice(notaxprice);
                    }
                    BigDecimal tax = allocateNoticeDetail.getTaxamount().subtract(allocateNoticeDetail.getNotaxamount());
                    allocateNoticeDetail.setTax(tax);
                    allocateNoticeDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                    allocateNoticeDetail.setBillno(allocateNotice.getId());
                    list.add(allocateNoticeDetail);
                }
            }

            Map rMap = allocateService.addallocateNotice(allocateNotice, list);
            if(null!=rMap && rMap.containsKey("flag")){
                flag = (Boolean)rMap.get("flag");
            }
            if(flag){
                id = allocateNotice.getId();
                msg += "生成调拨通知单单："+allocateNotice.getId();
            }
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        map.put("id",id);
        return map;
    }

    /**
     * 获取调拨通知单列表
     *
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public List getAllocateNoticeList(PageMap pageMap) throws Exception {
        List<AllocateNotice> list = allocateService.showAllocateNoticeListForPhone(pageMap);
        List dataList = new ArrayList();
        for(AllocateNotice allocateNotice : list){
            Map map = new HashMap();
            map.put("id",allocateNotice.getId());
            map.put("businessdate",allocateNotice.getBusinessdate());
            map.put("outstorageid",allocateNotice.getOutstorageid());
            StorageInfo outstorageInfo = getStorageInfoByID(allocateNotice.getOutstorageid());
            if(null!=outstorageInfo){
                map.put("outstoragename",outstorageInfo.getName());
            }else{
                map.put("outstoragename","");
            }
            map.put("instorageid",allocateNotice.getEnterstorageid());
            StorageInfo instorageInfo = getStorageInfoByID(allocateNotice.getEnterstorageid());
            if(null!=instorageInfo){
                map.put("instoragename",instorageInfo.getName());
            }else{
                map.put("instoragename","");
            }
            String statusname = "";
            if("2".equals(allocateNotice.getStatus() )){
                statusname = "保存";
            }else if("3".equals(allocateNotice.getStatus())){
                statusname = "审核通过";
            }else if("4".equals(allocateNotice.getStatus())){
                statusname = "关闭";
            }
            map.put("statusname",statusname);
            map.put("remark",allocateNotice.getRemark());

            dataList.add(map);
        }
        return dataList;
    }

    /**
     * 根据单据编号 获取调拨通知单详细信息
     *
     * @param billid
     * @return
     * @throws Exception
     */
    @Override
    public Map getAllocateNoticeInfo(String billid) throws Exception {
        Map returnMap = new HashMap();
        Map map = allocateService.getAllocateNoticeInfo(billid);
        if(null!=map){
            AllocateNotice allocateNotice = (AllocateNotice) map.get("allocateNotice");
            Map billMap = new HashMap();
            billMap.put("id",allocateNotice.getId());
            billMap.put("businessdate",allocateNotice.getBusinessdate());
            billMap.put("outstorageid",allocateNotice.getOutstorageid());
            StorageInfo outstorageInfo = getStorageInfoByID(allocateNotice.getOutstorageid());
            if(null!=outstorageInfo){
                billMap.put("outstoragename",outstorageInfo.getName());
            }else{
                billMap.put("outstoragename","");
            }
            billMap.put("instorageid",allocateNotice.getEnterstorageid());
            StorageInfo instorageInfo = getStorageInfoByID(allocateNotice.getEnterstorageid());
            if(null!=instorageInfo){
                billMap.put("instoragename",instorageInfo.getName());
            }else{
                billMap.put("instoragename","");
            }
            String statusname = "";
            if("2".equals(allocateNotice.getStatus() )){
                statusname = "保存";
            }else if("3".equals(allocateNotice.getStatus())){
                statusname = "审核通过";
            }else if("4".equals(allocateNotice.getStatus())){
                statusname = "关闭";
            }
            billMap.put("statusname",statusname);
            billMap.put("remark",allocateNotice.getRemark());
            List<AllocateNoticeDetail> dataList = (List<AllocateNoticeDetail>) map.get("detailList");
            List dList = new ArrayList();
            for(AllocateNoticeDetail allocateNoticeDetail : dataList){
                Map detail = new HashMap();
                detail.put("goodsid",allocateNoticeDetail.getGoodsid());
                if(null!=allocateNoticeDetail.getGoodsInfo()){
                    detail.put("goodsname",allocateNoticeDetail.getGoodsInfo().getName());
                    detail.put("barcode",allocateNoticeDetail.getGoodsInfo().getBarcode());
                    detail.put("boxbarcode",allocateNoticeDetail.getGoodsInfo().getBoxbarcode());
                    detail.put("boxnum",allocateNoticeDetail.getGoodsInfo().getBoxnum().doubleValue());
                }else{
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(allocateNoticeDetail.getGoodsid());
                    if(null!=goodsInfo){
                        detail.put("goodsname",goodsInfo.getName());
                        detail.put("barcode",goodsInfo.getBarcode());
                        detail.put("boxbarcode",goodsInfo.getBoxbarcode());
                        detail.put("boxnum",goodsInfo.getBoxnum().doubleValue());
                    }
                }
                if(null!=allocateNoticeDetail.getRemark()){
                    detail.put("remark",allocateNoticeDetail.getRemark());
                }
                if(null!=allocateNoticeDetail.getUnitname()){
                    detail.put("unitname",allocateNoticeDetail.getUnitname());
                }
                if(null!=allocateNoticeDetail.getAuxunitname()){
                    detail.put("auxunitname",allocateNoticeDetail.getAuxunitname());
                }
                detail.put("auxnum",allocateNoticeDetail.getAuxnum().toString());
                detail.put("num",allocateNoticeDetail.getAuxremainder().toString());
                detail.put("unitnum",allocateNoticeDetail.getUnitnum().toString());
                detail.put("price",allocateNoticeDetail.getTaxprice().toString());
                detail.put("amount", allocateNoticeDetail.getTaxamount().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                if(null!=allocateNoticeDetail.getBatchno()){
                    detail.put("batchno",allocateNoticeDetail.getBatchno());
                }
                if(null!=allocateNoticeDetail.getProduceddate()){
                    detail.put("produceddate",allocateNoticeDetail.getProduceddate());
                }
                if(null!=allocateNoticeDetail.getDeadline()){
                    detail.put("deadline",allocateNoticeDetail.getDeadline());
                }
                if(null!=allocateNoticeDetail.getStoragelocationname()){
                    detail.put("storagelocationname",allocateNoticeDetail.getStoragelocationname());
                }
                if(null!=allocateNoticeDetail.getEnterbatchno()){
                    detail.put("enterbatchno",allocateNoticeDetail.getEnterbatchno());
                }
                if(null!=allocateNoticeDetail.getEnterproduceddate()){
                    detail.put("enterproduceddate",allocateNoticeDetail.getEnterproduceddate());
                }
                if(null!=allocateNoticeDetail.getEnterdeadline()){
                    detail.put("enterdeadline",allocateNoticeDetail.getEnterdeadline());
                }
                if(null!=allocateNoticeDetail.getEnterstoragelocationname()){
                    detail.put("enterstoragelocationname",allocateNoticeDetail.getEnterstoragelocationname());
                }
                dList.add(detail);
            }
            returnMap.put("allocate",billMap);
            returnMap.put("dataList", dList);
        }
        return returnMap;
    }

    /**
     * 根据仓库编号获取该仓库的商品库存列表
     *
     * @param storageid
     * @return
     * @throws Exception
     */
    @Override
    public List getStorageGoodsByAllAllocate(String storageid) throws Exception {
        StorageInfo storageInfo = getStorageInfoByID(storageid);
        List dataList = new ArrayList();
        if(null!=storageInfo){
            List<StorageSummaryBatch> list = getBaseStorageSummaryService().getStorageBatchListHasUsenumByStorageid(storageid);
            for(StorageSummaryBatch storageSummaryBatch : list){
                GoodsInfo goodsInfo = getAllGoodsInfoByID(storageSummaryBatch.getGoodsid());
                if(null!=goodsInfo){
                    Map map = new HashMap();
                    map.put("goodsid",storageSummaryBatch.getGoodsid());
                    map.put("goodsname",goodsInfo.getName());
                    map.put("barcode",goodsInfo.getBarcode());
                    map.put("boxbarcode",goodsInfo.getBoxbarcode());
                    map.put("boxnum",goodsInfo.getBoxnum().doubleValue());

                    map.put("unitid",goodsInfo.getMainunit());
                    map.put("unitname",goodsInfo.getMainunitName());
                    map.put("auxunitid",goodsInfo.getAuxunitid());
                    map.put("auxunitname",goodsInfo.getAuxunitname());

                    map.put("price",goodsInfo.getNewstorageprice());
                    map.put("unitnum",storageSummaryBatch.getUsablenum());
                    map.put("amount",storageSummaryBatch.getUsablenum().multiply(goodsInfo.getNewstorageprice()).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                    Map auxmap = countGoodsInfoNumber(storageSummaryBatch.getGoodsid(),goodsInfo.getAuxunitid(),storageSummaryBatch.getUsablenum());
                    if(auxmap.containsKey("auxInteger")){
                        String auxnumStr = (String) auxmap.get("auxInteger");
                        map.put("auxnum",auxnumStr);
                    }
                    if(auxmap.containsKey("auxremainder")){
                        String auxremainderStr = (String) auxmap.get("auxremainder");
                        map.put("num",auxremainderStr);
                    }
                    map.put("summarybatchid",storageSummaryBatch.getId());
                    if("1".equals(storageInfo.getIsbatch()) && "1".equals(goodsInfo.getIsbatch())) {
                        if (StringUtils.isNotEmpty(storageSummaryBatch.getBatchno())) {
                            map.put("batchno", storageSummaryBatch.getBatchno());
                            map.put("enterbatchno", storageSummaryBatch.getBatchno());
                        } else {
                            map.put("batchno", storageSummaryBatch.getGoodsid());
                            map.put("enterbatchno", storageSummaryBatch.getGoodsid());
                        }
                        if (StringUtils.isNotEmpty(storageSummaryBatch.getProduceddate())) {
                            map.put("produceddate", storageSummaryBatch.getProduceddate());
                            map.put("enterproduceddate", storageSummaryBatch.getProduceddate());
                        } else {
                            map.put("produceddate", "");
                            map.put("enterproduceddate", "");
                        }
                        if (StringUtils.isNotEmpty(storageSummaryBatch.getDeadline())) {
                            map.put("deadline", storageSummaryBatch.getDeadline());
                            map.put("enterdeadline", storageSummaryBatch.getDeadline());
                        } else {
                            map.put("deadline", "");
                            map.put("enterdeadline", "");
                        }
                        StorageLocation storageLocation = getStorageLocation(storageSummaryBatch.getStoragelocationid());
                        if (null != storageLocation) {
                            map.put("storagelocationid", storageSummaryBatch.getStoragelocationid());
                            map.put("storagelocationname", storageLocation.getName());
                        } else {
                            map.put("storagelocationid", "");
                            map.put("storagelocationname", "");
                        }
                    }
                    dataList.add(map);
                }
            }
        }

        return dataList;
    }

    /**
     * 调拨单审核出库
     * @param id
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Nov 08, 2017
     */
    @Override
    public Map auditAllocateStorageOut(String id) throws Exception{
        Map map=allocateService.auditAllocateStorageOut(id);
        return map;
    }
    /**
     * 调拨单审核出库
     * @param id
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Nov 08, 2017
     */
    @Override
    public Map auditAllocatStorageEnter(String id) throws Exception{
        Map map=allocateService.auditAllocatStorageEnter(id);
        return map;
    }


    /**
     * 添加出库单
     *
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @Override
    public Map addAllocateOut(JSONObject jsonObject,String addtype) throws Exception {
        boolean isexist=false;
        boolean flag = false;
        String msg = "";
        String id = "";
        SysUser sysUser = getSysUser();
        if(null!=jsonObject && jsonObject.has("list")){
            //手机上传的单据编号 防止重复上传
            AllocateOut allocateOut = new AllocateOut();
            String billid = jsonObject.getString("billid");
            AllocateOut oldAllocateOut = allocateService.getAllocateOutPureInfo(billid);
            if(null!=oldAllocateOut){
                isexist=true;
                allocateOut.setId(billid);
            }
            allocateOut.setField01(billid);
            if(jsonObject.has("remark")){
                String remark = jsonObject.getString("remark");
                allocateOut.setRemark(remark);
            }else{
                allocateOut.setRemark("");
            }
            if(jsonObject.has("date")){
                String date = jsonObject.getString("date");
                allocateOut.setBusinessdate(date);
            }else{
                allocateOut.setBusinessdate(CommonUtils.getTodayDataStr());
            }
            String outstorageid = jsonObject.getString("outstorageid");
            String instorageid = jsonObject.getString("instorageid");
            allocateOut.setOutstorageid(outstorageid);
            allocateOut.setEnterstorageid(instorageid);

            allocateOut.setStatus("2");
            allocateOut.setAdduserid(sysUser.getUserid());
            allocateOut.setAddusername(sysUser.getName());
            allocateOut.setAdddeptid(sysUser.getDepartmentid());
            allocateOut.setAdddeptname(sysUser.getDepartmentname());
            allocateOut.setAddtime(new Date());

            String sourcetype = jsonObject.getString("sourcetype");
            allocateOut.setSourcetype(sourcetype);

            List<AllocateOutDetail> list = new ArrayList<AllocateOutDetail>();
            JSONArray jsonArray = jsonObject.getJSONArray("list");
            if(null!=jsonArray && jsonArray.size()>0){
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject detailObject = jsonArray.getJSONObject(i);
                    AllocateOutDetail allocateOutDetail = new AllocateOutDetail();
                    String goodsid = detailObject.getString("goodsid");
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
                    String remark = "";
                    if(detailObject.has("remark")){
                        remark = detailObject.getString("remark");
                    }
                    allocateOutDetail.setStorageid(outstorageid);
                    allocateOutDetail.setGoodsid(goodsid);
                    allocateOutDetail.setRemark(remark);
                    if(detailObject.has("summarybatchid")){
                        String summarybatchid = detailObject.getString("summarybatchid");
                        if(null!=summarybatchid){
                            allocateOutDetail.setSummarybatchid(summarybatchid);
                        }
                    }
                    if(detailObject.has("batchno")){
                        String batchno = detailObject.getString("batchno");
                        if(null!=batchno){
                            allocateOutDetail.setBatchno(batchno);
                        }
                    }
                    if(detailObject.has("produceddate")){
                        String produceddate = detailObject.getString("produceddate");
                        if(null!=produceddate){
                            allocateOutDetail.setProduceddate(produceddate);
                        }
                    }
                    if(detailObject.has("deadline")){
                        String deadline = detailObject.getString("deadline");
                        allocateOutDetail.setDeadline(deadline);
                    }
                    if(detailObject.has("storagelocationid")){
                        String storagelocationid = detailObject.getString("storagelocationid");
                        if(null!=storagelocationid){
                            allocateOutDetail.setStoragelocationid(storagelocationid);
                        }
                    }
                    if(detailObject.has("enterbatchno")){
                        String enterbatchno = detailObject.getString("enterbatchno");
                        if(null!=enterbatchno){
                            allocateOutDetail.setEnterbatchno(enterbatchno);
                        }
                    }
                    if(detailObject.has("enterproduceddate")){
                        String enterproduceddate = detailObject.getString("enterproduceddate");
                        if(null!=enterproduceddate){
                            allocateOutDetail.setEnterproduceddate(enterproduceddate);
                        }
                    }
                    if(detailObject.has("enterdeadline")){
                        String enterdeadline = detailObject.getString("enterdeadline");
                        if(null!=enterdeadline){
                            allocateOutDetail.setEnterdeadline(enterdeadline);
                        }
                    }
                    if(detailObject.has("enterstoragelocationid")){
                        String enterstoragelocationid = detailObject.getString("enterstoragelocationid");
                        if(null!=enterstoragelocationid){
                            allocateOutDetail.setEnterstoragelocationid(enterstoragelocationid);
                        }
                    }
                    if(detailObject.has("unitid")){
                        String unitid = detailObject.getString("unitid");
                        if(null!=unitid){
                            allocateOutDetail.setUnitid(unitid);
                        }
                    }
                    if(detailObject.has("unitname")){
                        String unitname = detailObject.getString("unitname");
                        if(null!=unitname){
                            allocateOutDetail.setUnitname(unitname);
                        }
                    }

                    if(detailObject.has("auxunitid")){
                        String auxunitid = detailObject.getString("auxunitid");
                        if(null!=auxunitid){
                            allocateOutDetail.setAuxunitid(auxunitid);
                        }
                    }
                    if(detailObject.has("auxunitname")){
                        String auxunitname = detailObject.getString("auxunitname");
                        if(null!=auxunitname){
                            allocateOutDetail.setAuxunitname(auxunitname);
                        }
                    }
                    String priceStr = "0";
                    if(detailObject.has("price")){
                        priceStr = detailObject.getString("price");
                    }
                    String unitnumStr = "0";
                    if(detailObject.has("unitnum")){
                        unitnumStr = detailObject.getString("unitnum");
                    }
                    BigDecimal price = new BigDecimal(priceStr);
                    BigDecimal unitnum = new BigDecimal(unitnumStr);
                    BigDecimal amount = price.multiply(unitnum).setScale(decimalLen,BigDecimal.ROUND_HALF_UP);

                    //计算辅单位数量
                    Map auxmap = countGoodsInfoNumber(allocateOutDetail.getGoodsid(), allocateOutDetail.getAuxunitid(), unitnum);
                    if(auxmap.containsKey("auxnum")){
                        allocateOutDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
                    }
                    if(auxmap.containsKey("auxInteger")){
                        String auxnumStr = (String) auxmap.get("auxInteger");
                        allocateOutDetail.setAuxnum(new BigDecimal(auxnumStr));
                    }
                    if(auxmap.containsKey("auxremainder")){
                        String auxremainderStr = (String) auxmap.get("auxremainder");
                        allocateOutDetail.setAuxremainder(new BigDecimal(auxremainderStr));
                    }
                    if(auxmap.containsKey("auxnumdetail")){
                        String auxnumdetail = (String) auxmap.get("auxnumdetail");
                        allocateOutDetail.setAuxnumdetail(auxnumdetail);
                    }

                    allocateOutDetail.setTaxprice(price);
                    allocateOutDetail.setUnitnum(unitnum);


                    BigDecimal notaxamount = getNotaxAmountByTaxAmount(amount, goodsInfo.getDefaulttaxtype());
                    allocateOutDetail.setTaxamount(amount);
                    allocateOutDetail.setNotaxamount(notaxamount);
                    if(unitnum.compareTo(BigDecimal.ZERO)!=0){
                        BigDecimal notaxprice = notaxamount.divide(unitnum,6,BigDecimal.ROUND_HALF_UP);
                        allocateOutDetail.setNotaxprice(notaxprice);
                    }
                    BigDecimal tax = allocateOutDetail.getTaxamount().subtract(allocateOutDetail.getNotaxamount());
                    allocateOutDetail.setTax(tax);
                    allocateOutDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
                    allocateOutDetail.setBillno(allocateOut.getId());
                    if(detailObject.containsKey("detailid")) {
                        allocateOutDetail.setId(Integer.parseInt((String) detailObject.get("detailid")));
                    }
                    list.add(allocateOutDetail);
                }
            }
            Map rMap = new HashMap();
            if(isexist){
                rMap = allocateService.editAllocateOut(allocateOut,list);
            }else{
                rMap = allocateService.addAllocateOut(allocateOut, list);
            }

            if(null!=rMap && rMap.containsKey("flag")){
                flag = (Boolean)rMap.get("flag");
            }
            if(flag){
                id = allocateOut.getId();
                msg += "生成调拨通知单单："+allocateOut.getId();
                if("0".equals(addtype)){
                    Map returnmap = allocateService.auditAllocateStorageOut(id);
                    flag = (Boolean)returnmap.get("flag");
                    msg = (String) returnmap.get("msg");
                }else if("1".equals(addtype)){
                    Map returnmap = allocateService.auditAllocateOut(id);
                    flag = (Boolean)returnmap.get("flag");
                    msg = (String) returnmap.get("msg");
                }

            }else{
                msg = (String) rMap.get("msg");
            }
        }
        Map map = new HashMap();
        map.put("flag",flag);
        map.put("msg",msg);
        map.put("id",id);
        return map;
    }
    /**
     * 获取调拨出库单列表
     *
     * @param pageMap
     * @return
     * @throws Exception
     */
    @Override
    public List getAllocateOutList(PageMap pageMap) throws Exception {
        List<AllocateOut> list = allocateService.showAllocateOutListForPhone(pageMap);
        List dataList = new ArrayList();
        for(AllocateOut allocateOut : list){
            List<AllocateOutDetail> allocateOutDetailList = allocateService.getAllocateOutDetailList(allocateOut.getId());
            if(null!=allocateOutDetailList && allocateOutDetailList.size()>0){
                Map map = new HashMap();
                map.put("id",allocateOut.getId());
                map.put("businessdate",allocateOut.getBusinessdate());
                map.put("outstorageid",allocateOut.getOutstorageid());
                StorageInfo outstorageInfo = getStorageInfoByID(allocateOut.getOutstorageid());
                if(null!=outstorageInfo){
                    map.put("outstoragename",outstorageInfo.getName());
                }else{
                    map.put("outstoragename","");
                }
                map.put("instorageid",allocateOut.getEnterstorageid());
                StorageInfo instorageInfo = getStorageInfoByID(allocateOut.getEnterstorageid());
                if(null!=instorageInfo){
                    map.put("instoragename",instorageInfo.getName());
                }else{
                    map.put("instoragename","");
                }
                String statusname = "";
                if("2".equals(allocateOut.getStatus() )){
                    statusname = "保存";
                }else if("3".equals(allocateOut.getStatus())){
                    statusname = "审核通过";
                }else if("4".equals(allocateOut.getStatus())){
                    statusname = "关闭";
                }else if("7".equals(allocateOut.getStatus())){
                    statusname = "审核出库";
                }
                map.put("statusname",statusname);
                map.put("remark",allocateOut.getRemark());

                dataList.add(map);
            }
        }
        return dataList;
    }

    /**
     * 根据单据编号 获取调拨出库单详细信息
     *
     * @param billid
     * @return
     * @throws Exception
     */
    @Override
    public Map getAllocateOutInfo(String billid) throws Exception {
        Map returnMap = new HashMap();
        Map map = allocateService.getAllocateOutInfo(billid);
        if(null!=map){
            AllocateOut allocateOut = (AllocateOut) map.get("allocateOut");
            Map billMap = new HashMap();
            billMap.put("id",allocateOut.getId());
            billMap.put("businessdate",allocateOut.getBusinessdate());
            billMap.put("outstorageid",allocateOut.getOutstorageid());
            StorageInfo outstorageInfo = getStorageInfoByID(allocateOut.getOutstorageid());
            if(null!=outstorageInfo){
                billMap.put("outstoragename",outstorageInfo.getName());
            }else{
                billMap.put("outstoragename","");
            }
            billMap.put("instorageid",allocateOut.getEnterstorageid());
            StorageInfo instorageInfo = getStorageInfoByID(allocateOut.getEnterstorageid());
            if(null!=instorageInfo){
                billMap.put("instoragename",instorageInfo.getName());
            }else{
                billMap.put("instoragename","");
            }
            String statusname = "";
            if("2".equals(allocateOut.getStatus() )){
                statusname = "保存";
            }else if("3".equals(allocateOut.getStatus())){
                statusname = "审核通过";
            }else if("4".equals(allocateOut.getStatus())){
                statusname = "关闭";
            }
            billMap.put("statusname",statusname);
            billMap.put("remark",allocateOut.getRemark());
            List<AllocateOutDetail> dataList = (List<AllocateOutDetail>) map.get("detailList");
            List dList = new ArrayList();
            for(AllocateOutDetail allocateOutDetail : dataList){
                Map detail = new HashMap();
                detail.put("goodsid",allocateOutDetail.getGoodsid());
                if(null!=allocateOutDetail.getGoodsInfo()){
                    detail.put("goodsname",allocateOutDetail.getGoodsInfo().getName());
                    detail.put("barcode",allocateOutDetail.getGoodsInfo().getBarcode());
                    detail.put("boxbarcode",allocateOutDetail.getGoodsInfo().getBoxbarcode());
                    detail.put("boxnum",allocateOutDetail.getGoodsInfo().getBoxnum().doubleValue());
                }else{
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(allocateOutDetail.getGoodsid());
                    if(null!=goodsInfo){
                        detail.put("goodsname",goodsInfo.getName());
                        detail.put("barcode",goodsInfo.getBarcode());
                        detail.put("boxbarcode",goodsInfo.getBoxbarcode());
                        detail.put("boxnum",goodsInfo.getBoxnum().doubleValue());
                    }
                }
                if(null!=allocateOutDetail.getRemark()){
                    detail.put("remark",allocateOutDetail.getRemark());
                }
                if(null!=allocateOutDetail.getUnitname()){
                    detail.put("unitname",allocateOutDetail.getUnitname());
                }
                if(null!=allocateOutDetail.getAuxunitname()){
                    detail.put("auxunitname",allocateOutDetail.getAuxunitname());
                }
                detail.put("auxnum",allocateOutDetail.getAuxnum().toString());
                detail.put("num",allocateOutDetail.getAuxremainder().toString());
                detail.put("unitnum",allocateOutDetail.getUnitnum().toString());
                detail.put("price",allocateOutDetail.getTaxprice().toString());
                detail.put("amount", allocateOutDetail.getTaxamount().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                if(null!=allocateOutDetail.getBatchno()){
                    detail.put("batchno",allocateOutDetail.getBatchno());
                }
                if(null!=allocateOutDetail.getProduceddate()){
                    detail.put("produceddate",allocateOutDetail.getProduceddate());
                }
                if(null!=allocateOutDetail.getDeadline()){
                    detail.put("deadline",allocateOutDetail.getDeadline());
                }
                if(null!=allocateOutDetail.getStoragelocationname()){
                    detail.put("storagelocationname",allocateOutDetail.getStoragelocationname());
                }
                if(null!=allocateOutDetail.getEnterbatchno()){
                    detail.put("enterbatchno",allocateOutDetail.getEnterbatchno());
                }
                if(null!=allocateOutDetail.getEnterproduceddate()){
                    detail.put("enterproduceddate",allocateOutDetail.getEnterproduceddate());
                }
                if(null!=allocateOutDetail.getEnterdeadline()){
                    detail.put("enterdeadline",allocateOutDetail.getEnterdeadline());
                }
                if(null!=allocateOutDetail.getEnterstoragelocationname()){
                    detail.put("enterstoragelocationname",allocateOutDetail.getEnterstoragelocationname());
                }
                dList.add(detail);
            }
            returnMap.put("allocate",billMap);
            returnMap.put("dataList", dList);
        }
        return returnMap;
    }

    @Override
    public List getAllocateOutDetail(String billid) throws Exception {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map allocateOut = allocateService.getAllocateOutInfo(billid);
        if (null != allocateOut) {
            List<AllocateOutDetail> dataList = (List<AllocateOutDetail>) allocateOut.get("detailList");
            List dList = new ArrayList();
            for(AllocateOutDetail allocateOutDetail : dataList){
                Map detail = new HashMap();
                detail.put("goodsid",allocateOutDetail.getGoodsid());
                if(null!=allocateOutDetail.getGoodsInfo()){
                    detail.put("goodsname",allocateOutDetail.getGoodsInfo().getName());
                    detail.put("barcode",allocateOutDetail.getGoodsInfo().getBarcode());
                    detail.put("boxbarcode",allocateOutDetail.getGoodsInfo().getBoxbarcode());
                    detail.put("boxnum",allocateOutDetail.getGoodsInfo().getBoxnum().doubleValue());
                }else{
                    GoodsInfo goodsInfo = getAllGoodsInfoByID(allocateOutDetail.getGoodsid());
                    if(null!=goodsInfo){
                        detail.put("goodsname",goodsInfo.getName());
                        detail.put("barcode",goodsInfo.getBarcode());
                        detail.put("boxbarcode",goodsInfo.getBoxbarcode());
                        detail.put("boxnum",goodsInfo.getBoxnum().doubleValue());
                    }
                }
                if(null!=allocateOutDetail.getRemark()){
                    detail.put("remark",allocateOutDetail.getRemark());
                }
                if(null!=allocateOutDetail.getUnitname()){
                    detail.put("unitname",allocateOutDetail.getUnitname());
                }
                if(null!=allocateOutDetail.getUnitid()){
                    detail.put("unitid",allocateOutDetail.getUnitid());
                }
                if(null!=allocateOutDetail.getAuxunitname()){
                    detail.put("auxunitname",allocateOutDetail.getAuxunitname());
                }
                if(null!=allocateOutDetail.getAuxunitid()){
                    detail.put("auxunitid",allocateOutDetail.getAuxunitid());
                }
                detail.put("auxnum",allocateOutDetail.getAuxnum().toString());
                detail.put("num",allocateOutDetail.getAuxremainder().toString());
                detail.put("unitnum",allocateOutDetail.getUnitnum().toString());
                detail.put("price",allocateOutDetail.getTaxprice().toString());
                detail.put("amount", allocateOutDetail.getTaxamount().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
                if(null!=allocateOutDetail.getBatchno()){
                    detail.put("batchno",allocateOutDetail.getBatchno());
                }
                if(null!=allocateOutDetail.getProduceddate()){
                    detail.put("produceddate",allocateOutDetail.getProduceddate());
                }
                if(null!=allocateOutDetail.getDeadline()){
                    detail.put("deadline",allocateOutDetail.getDeadline());
                }
                if(null!=allocateOutDetail.getStoragelocationname()){
                    detail.put("storagelocationname",allocateOutDetail.getStoragelocationname());
                }
                if(null!=allocateOutDetail.getEnterbatchno()){
                    detail.put("enterbatchno",allocateOutDetail.getEnterbatchno());
                }
                if(null!=allocateOutDetail.getEnterproduceddate()){
                    detail.put("enterproduceddate",allocateOutDetail.getEnterproduceddate());
                }
                if(null!=allocateOutDetail.getEnterdeadline()){
                    detail.put("enterdeadline",allocateOutDetail.getEnterdeadline());
                }
                if(null!=allocateOutDetail.getEnterstoragelocationname()){
                    detail.put("enterstoragelocationname",allocateOutDetail.getEnterstoragelocationname());
                }
                if(null!=allocateOutDetail.getSummarybatchid()){
                    detail.put("summarybatchid",allocateOutDetail.getSummarybatchid());
                }
                if(null!=allocateOutDetail.getBatchno()){
                    detail.put("batchno",allocateOutDetail.getBatchno());
                }
                if(null!=allocateOutDetail.getStorageid()){
                    detail.put("storageid",allocateOutDetail.getStorageid());
                }
                detail.put("detailid",allocateOutDetail.getId());
                detail.put("billid",allocateOutDetail.getBillno());
                result.add(detail);
            }
        }
        return result;
    }
}
