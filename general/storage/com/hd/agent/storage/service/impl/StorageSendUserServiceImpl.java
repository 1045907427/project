package com.hd.agent.storage.service.impl;

import com.hd.agent.basefiles.model.Personnel;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.dao.PurchaseEnterMapper;
import com.hd.agent.storage.dao.SaleoutMapper;
import com.hd.agent.storage.dao.StorageSendUserDetailMapper;
import com.hd.agent.storage.dao.StorageSendUserOrderMapper;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IStorageSendUserService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanghongteng on 2016/10/26.
 */
public class StorageSendUserServiceImpl extends BaseStorageServiceImpl  implements IStorageSendUserService {

    private StorageSendUserDetailMapper storageSendUserDetailMapper;

    private StorageSendUserOrderMapper storageSendUserOrderMapper;

    private SaleoutMapper saleoutMapper;

    private PurchaseEnterMapper purchaseEnterMapper;

    public StorageSendUserDetailMapper getStorageSendUserDetailMapper() {
        return storageSendUserDetailMapper;
    }

    public void setStorageSendUserDetailMapper(StorageSendUserDetailMapper storageSendUserDetailMapper) {
        this.storageSendUserDetailMapper = storageSendUserDetailMapper;
    }

    public StorageSendUserOrderMapper getStorageSendUserOrderMapper() {
        return storageSendUserOrderMapper;
    }

    public void setStorageSendUserOrderMapper(StorageSendUserOrderMapper storageSendUserOrderMapper) {
        this.storageSendUserOrderMapper = storageSendUserOrderMapper;
    }

    public SaleoutMapper getSaleoutMapper() {
        return saleoutMapper;
    }

    public void setSaleoutMapper(SaleoutMapper saleoutMapper) {
        this.saleoutMapper = saleoutMapper;
    }

    public PurchaseEnterMapper getPurchaseEnterMapper() {
        return purchaseEnterMapper;
    }

    public void setPurchaseEnterMapper(PurchaseEnterMapper purchaseEnterMapper) {
        this.purchaseEnterMapper = purchaseEnterMapper;
    }

    /*** 发货单据分配    **/
    /**
     * 获取发货人单据列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    @Override
    public PageData showSendUserOrderList(PageMap pageMap) throws Exception {
        String dataSql = getDataAccessRule("t_storage_senduser_order", "t");
        pageMap.setDataSql(dataSql);

        List<StorageSendUserOrder> list = storageSendUserOrderMapper.getSendUserOrderList(pageMap);
        for (StorageSendUserOrder storageSendUserOrder : list) {
            String storageid=storageSendUserOrder.getStorageid();
            if(StringUtils.isNotEmpty(storageid)){
                StorageInfo storageInfo=getStorageInfoByID(storageid);
                if(null!=storageInfo){
                    storageSendUserOrder.setStoragename(storageInfo.getName());
                }
            }
            String senduserids=storageSendUserOrder.getSenduserid();
            if(StringUtils.isNotEmpty(senduserids)){
                if("zjsc".equals(senduserids)){
                    storageSendUserOrder.setLoadedusername("直接发货");
                }else {
                    String[] senduseridArr=senduserids.split(",");
                    String senduername="";
                    for(String senduserid : senduseridArr){
                        Personnel personnel = getPersonnelById(senduserid);
                        if(null!=personnel){
                            if(StringUtils.isNotEmpty(senduername)){
                                senduername=senduername+","+personnel.getName();
                            }else{
                                senduername=personnel.getName();
                            }
                        }
                    }
                    if("1".equals(storageSendUserOrder.getBilltype())){
                        storageSendUserOrder.setCheckusername(senduername);
                    }else if("2".equals(storageSendUserOrder.getBilltype())){
                        storageSendUserOrder.setLoadedusername(senduername);
                    }else if("3".equals(storageSendUserOrder.getBilltype())){
                        storageSendUserOrder.setUnloadedusername(senduername);
                    }
                }
            }
        }
        int count = storageSendUserOrderMapper.getSendUserOrderListCount(pageMap);
        PageData pageData = new PageData(count, list, pageMap);
        return pageData;
    }

    /**
     * 获取待分配核对发货单据列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    @Override
    public PageData showOrderList(PageMap pageMap,String billtype) throws Exception {
        if("1".equals(billtype)){
            List<Map> list = storageSendUserOrderMapper.getCheckOrderList(pageMap);
            for(Map map : list){
                String storageid=(String) map.get("storageid");
                if(StringUtils.isNotEmpty(storageid)){
                    StorageInfo storageInfo=getStorageInfoByID(storageid);
                    if(null!=storageInfo){
                        map.put("storagename",storageInfo.getName());
                    }
                }
            }
            PageData pageData = new PageData(storageSendUserOrderMapper.getCheckOrderListCount(pageMap),list,pageMap);
            return pageData;
        }else    if("2".equals(billtype)){
            List<Map> list = storageSendUserOrderMapper.getLoadedOrderList(pageMap);
            for(Map map : list){
                String storageid=(String) map.get("storageid");
                if(StringUtils.isNotEmpty(storageid)){
                    StorageInfo storageInfo=getStorageInfoByID(storageid);
                    if(null!=storageInfo){
                        map.put("storagename",storageInfo.getName());
                    }
                }
            }
            PageData pageData = new PageData(storageSendUserOrderMapper.getLoadedOrderListCount(pageMap),list,pageMap);
            return pageData;
        }else    if("3".equals(billtype)){
            List<Map> list = storageSendUserOrderMapper.getUnloadedOrderList(pageMap);
            for(Map map : list){
                String storageid=(String) map.get("storageid");
                if(StringUtils.isNotEmpty(storageid)){
                    StorageInfo storageInfo=getStorageInfoByID(storageid);
                    if(null!=storageInfo){
                        map.put("storagename",storageInfo.getName());
                    }
                }
            }
            PageData pageData = new PageData(storageSendUserOrderMapper.getUnloadedOrderListCount(pageMap),list,pageMap);
            return pageData;
        }else{
            return null;
        }

    }

    /**
     * 发货人分配
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    @Override
    public Map SendUserOrderAssigned(StorageSendUserOrder storageSendUserOrder,String storager,String billtype) throws Exception {
        boolean flag=false;
        if(null!=storageSendUserOrder){
            storageSendUserOrder.setBilltype(billtype);
            storageSendUserOrder.setSenduserid(storager);
            flag=storageSendUserOrderMapper.addSendUserOrder(storageSendUserOrder)>0;
        }
        if(flag){
            if(StringUtils.isNotEmpty(storager)){
                String[] senduserids=storager.split(",");
                int usernum=senduserids.length;
                for(String senduserid : senduserids){
                    StorageSendUserDetail storageSendUserDetail = new StorageSendUserDetail();
                    storageSendUserDetail.setBusinessdate(storageSendUserOrder.getBusinessdate());
                    storageSendUserDetail.setSourceid(storageSendUserOrder.getSourceid());
                    storageSendUserDetail.setStorageid(storageSendUserOrder.getStorageid());
                    storageSendUserDetail.setSenduserid(senduserid);
                    storageSendUserDetail.setBilltype(storageSendUserOrder.getBilltype());
                    storageSendUserDetail.setRemark(storageSendUserOrder.getRemark());
                    BigDecimal totalamount= new BigDecimal("0");
                    BigDecimal totalbox= new BigDecimal("0");
                    BigDecimal num= new BigDecimal("0");
                    BigDecimal totalvolume= new BigDecimal("0");
                    BigDecimal totalweight= new BigDecimal("0");
                    if(null!=storageSendUserOrder.getTotalamount()){
                        totalamount=storageSendUserOrder.getTotalamount();
                    }
                    if(null!=storageSendUserOrder.getTotalbox()){
                        totalbox=storageSendUserOrder.getTotalbox();
                    }
                    if(null!=storageSendUserOrder.getNum()){
                        num=storageSendUserOrder.getNum();
                    }
                    if(null!=storageSendUserOrder.getTotalvolume()){
                        totalvolume=storageSendUserOrder.getTotalvolume();
                    }
                    if(null!=storageSendUserOrder.getTotalweight()){
                        totalweight=storageSendUserOrder.getTotalweight();
                    }
                    if("1".equals(billtype)){
                        storageSendUserDetail.setCheckamount(totalamount.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                        storageSendUserDetail.setCheckbox(totalbox.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                        storageSendUserDetail.setChecknum(num.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                        storageSendUserDetail.setCheckvolume(totalvolume.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                        storageSendUserDetail.setCheckweight(totalweight.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                    }else if("2".equals(billtype)){
                        storageSendUserDetail.setLoadedamount(totalamount.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                        storageSendUserDetail.setLoadedbox(totalbox.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                        storageSendUserDetail.setLoadednum(num.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                        storageSendUserDetail.setLoadedvolume(totalvolume.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                        storageSendUserDetail.setLoadedweight(totalweight.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                    }else if("3".equals(billtype)){
                        storageSendUserDetail.setUnloadedamount(totalamount.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                        storageSendUserDetail.setUnloadedbox(totalbox.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                        storageSendUserDetail.setUnloadednum(num.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                        storageSendUserDetail.setUnloadedvolume(totalvolume.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                        storageSendUserDetail.setUnloadedweight(totalweight.divide(new BigDecimal(usernum),  2, BigDecimal.ROUND_HALF_UP));
                    }
                    storageSendUserDetailMapper.addSendUserDetail(storageSendUserDetail);
                }
            }
        }
        Map resultMap = new HashMap();
        resultMap.put("flag",flag);
        return resultMap;
    }



    /*** 发货人报表    **/


    /**
     * 获取发货人报表列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    @Override
    public PageData showSendUserDetailList(PageMap pageMap) throws Exception{
        String dataSql = getDataAccessRule("t_storage_senduser_detail", "t");
        pageMap.setDataSql(dataSql);

        List<StorageSendUserDetail> list = storageSendUserDetailMapper.getSendUserDetailList(pageMap);
        for (StorageSendUserDetail storageSendUserDetail : list) {
            String storageid=storageSendUserDetail.getStorageid();
            if(StringUtils.isNotEmpty(storageid)){
                StorageInfo storageInfo=getStorageInfoByID(storageid);
                if(null!=storageInfo){
                    storageSendUserDetail.setStoragename(storageInfo.getName());
                }
            }
            String senduserid=storageSendUserDetail.getSenduserid();
            if(StringUtils.isNotEmpty(senduserid)){
                if("zjsc".equals(senduserid)){
                    storageSendUserDetail.setSendusername("直接发货");
                }else{
                    Personnel personnel = getPersonnelById(senduserid);
                    if(null!=personnel){
                        storageSendUserDetail.setSendusername(personnel.getName());
                    }
                }

            }
        }
        int count = storageSendUserDetailMapper.getSendUserDetailListCount(pageMap);
        PageData pageData = new PageData(count, list, pageMap);
        List<StorageSendUserDetail> footer = storageSendUserDetailMapper.getSendUserDetailListSum(pageMap);
        for(StorageSendUserDetail storageSendUserDetail : footer){
            storageSendUserDetail.setSenduserid("合计");
        }
        pageData.setFooter(footer);
        return pageData;
    }


    /**
     * 获取发货人报表日志列表
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/10/26
     */
    @Override
    public PageData getSendUserDetailLogBySendUser(PageMap pageMap) throws Exception{
        String dataSql = getDataAccessRule("t_storage_senduser_detail", "t");
        pageMap.setDataSql(dataSql);
        List<StorageSendUserDetail> list = storageSendUserDetailMapper.getSendUserDetailListBySenduser(pageMap);
        int count = storageSendUserDetailMapper.getSendUserDetailListCountBySenduser(pageMap);
        PageData pageData = new PageData(count, list, pageMap);
        pageMap.getCondition().put("showUserData", "user");
        List<StorageSendUserDetail> footer = storageSendUserDetailMapper.getSendUserDetailListSum(pageMap);
        for(StorageSendUserDetail storageSendUserDetail : footer){
            storageSendUserDetail.setSourceid("合计");
        }
        pageData.setFooter(footer);
        return pageData;
    }

    /**
     * 删除发货人报表数据
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2016/9/13
     */
    public Map deleteSendUserOrder(Map map) throws  Exception {
        String sourceid = (String) map.get("sourceid");
        String billtype = (String) map.get("billtype");
        String senduserid = (String) map.get("senduserid");
        boolean flag = false;
        StorageSendUserOrder storageSendUserOrder=storageSendUserOrderMapper.getStorageSendUserOrderBySourceidAndBilltype(sourceid,billtype);
        if(null!=storageSendUserOrder){
            flag = storageSendUserOrderMapper.deleteSendUserOrder(sourceid, billtype) > 0;
            if (flag) {
                flag = storageSendUserDetailMapper.deleteSendUserDetail(sourceid, billtype) > 0;
            }
        }
        Map returnMap = new HashMap();
        returnMap.put("flag", flag);
        return returnMap;
    }
}
