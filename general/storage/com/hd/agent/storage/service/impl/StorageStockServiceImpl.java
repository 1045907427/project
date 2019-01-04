package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.dao.GoodsMapper;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.dao.StorageOtherEnterMapper;
import com.hd.agent.storage.dao.StorageOtherOutMapper;
import com.hd.agent.storage.dao.StorageStockMapper;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IStorageOtherEnterService;
import com.hd.agent.storage.service.IStorageOtherOutService;
import com.hd.agent.storage.service.IStorageStockService;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by luoq on 2018/1/26.
 */
public class StorageStockServiceImpl extends BaseStorageServiceImpl implements IStorageStockService {
    //入库类型为成本调整
    public static final String STORAGE_COST_ENTERTYPE="99";
    //出库类型为成本调整
    public static final String STORAGE_COST_OUTTYPE="99";
    private StorageStockMapper storageStockMapper;

    private IStorageOtherEnterService storageOtherEnterService;

    private GoodsMapper goodsMapper;

    private StorageOtherEnterMapper storageOtherEnterMapper;

    private StorageOtherOutMapper storageOtherOutMapper;

    private IStorageOtherOutService storageOtherOutService;

    private StorageStockMapper getStorageStockMapper() {
        return storageStockMapper;
    }

    public void setStorageStockMapper(StorageStockMapper storageStockMapper) {
        this.storageStockMapper = storageStockMapper;
    }

    public IStorageOtherEnterService getStorageOtherEnterService() {
        return storageOtherEnterService;
    }

    public void setStorageOtherEnterService(IStorageOtherEnterService storageOtherEnterService) {
        this.storageOtherEnterService = storageOtherEnterService;
    }

    public GoodsMapper getGoodsMapper() {
        return goodsMapper;
    }

    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    public StorageOtherEnterMapper getStorageOtherEnterMapper() {
        return storageOtherEnterMapper;
    }

    public void setStorageOtherEnterMapper(StorageOtherEnterMapper storageOtherEnterMapper) {
        this.storageOtherEnterMapper = storageOtherEnterMapper;
    }

    public StorageOtherOutMapper getStorageOtherOutMapper() {
        return storageOtherOutMapper;
    }

    public void setStorageOtherOutMapper(StorageOtherOutMapper storageOtherOutMapper) {
        this.storageOtherOutMapper = storageOtherOutMapper;
    }

    public IStorageOtherOutService getStorageOtherOutService() {
        return storageOtherOutService;
    }

    public void setStorageOtherOutService(IStorageOtherOutService storageOtherOutService) {
        this.storageOtherOutService = storageOtherOutService;
    }

    /**
     * 获取成本异常报表数据
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Jan 26, 2018
     */
    @Override
    public PageData getAbnormalBillReportData(PageMap pageMap) throws Exception{
        String accounttype=(String)pageMap.getCondition().get("accounttype");
        if(StringUtils.isEmpty(accounttype)){
            accounttype="2";
        }
        List list=storageStockMapper.getAbnormalBillReportList(pageMap);
        getCostAccountDataListByAccountType(list,accounttype);
        int count=storageStockMapper.getAbnormalBillReportCount(pageMap);
        PageData pageData=new PageData(count,list,pageMap);
        return pageData;
    }

    /**
     * 商品成本结算
     * @param map
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    @Override
    public Map insertCostReportData(Map map) throws Exception{
        //判断日期范围内相关全部单据，是否审核通过，有未审核的单据就不允许结算。
        Map checkMap=storageStockMapper.checkBillForAccount(map);
        String errormsg=checkBillForAccount(checkMap);
        if(StringUtils.isNotEmpty(errormsg)){
            map.put("flag",false);
            map.put("msg",errormsg);
            return map;
        }
        Map resMap=new HashMap();
        Map addMap=addAccountCostSumAndDetailData(map);
        Boolean flag=(Boolean)addMap.get("flag");
        //如果有插入数据，回写单据成本价
        if(flag){
            String accountid=addMap.get("accountid").toString();
            //回写来源单据成本价
            updateSourceBillCostPrice(accountid);
            //结算完之后，更新结算日期之后做的单据的成本价
            updateLaterBillCostPrice(map);

        }
        resMap.put("flag",true);
        return resMap;
    }

    /**
     * 添加存货合计以及明细信息
     * @param map
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    private Map addAccountCostSumAndDetailData(Map map) throws Exception{
        String accounttype=map.containsKey("accounttype")?(String)map.get("accounttype"):"";
        Map resMap=new HashMap();
        PageMap pageMap=new PageMap();
        if(!map.containsKey("storageid")){
            map.put("storageid","");
        }
        pageMap.setCondition(map);
        List<StorageStockSum> list=storageStockMapper.getAbnormalBillReportList(pageMap);
        getCostAccountDataListByAccountType(list,accounttype);
        if(list==null||list.size()!=1){
          resMap.put("flag",false);
          return resMap;
        }
        StorageStockSum storageStockSum=list.get(0);
        storageStockSum.setAccounttype(accounttype);
        SysUser sysUser=getSysUser();
        if(sysUser!=null){
            storageStockSum.setAdduserid(sysUser.getUserid());
            storageStockSum.setAdddeptname(sysUser.getName());
            storageStockSum.setAdddeptid(sysUser.getDepartmentid());
            storageStockSum.setAdddeptname(sysUser.getDepartmentname());
        }
        //获取存货合计结算的id作为存货明细的结算编码
        storageStockMapper.insertCostAccountSum(storageStockSum);
        map.put("accountid",list.get(0).getId());

        storageStockMapper.insertStorageStockDetailList(pageMap);

        resMap.put("flag",true);
        resMap.put("accountid",storageStockSum.getId());
        return resMap;
    }

    /**
     * 修改来源单据成本价
     * @param accountid
     * @return void
     * @throws
     * @author luoqiang
     * @date Jan 29, 2018
     */
    private void updateSourceBillCostPrice(String accountid) {
        //更新存货明细的结算成本价
        storageStockMapper.updateStorageAccountDetailCostPrice(accountid);
        storageStockMapper.updateSourceBillCostPrice(accountid);
        //更新库存成本价
        storageStockMapper.updateStorageSummaryCostprice(accountid);
        //更新商品成本价
        goodsMapper.updateCostGoodsCostPrice(accountid);
        //生成成本变更记录
        storageStockMapper.insertGoodsCostPriceChange(accountid);
    }

    /**
     * 结算完之后，更新结算日期之后做的单据的成本价
     * @param map
     * @return void
     * @throws
     * @author luoqiang
     * @date Jan 22, 2018
     */
    private void updateLaterBillCostPrice(Map map) throws Exception{
        Integer accountid=Integer.parseInt(map.get("accountid").toString());

        Map pmap=(Map) CommonUtils.deepCopy(map);;
        accountid=accountid+1;
        pmap.put("accountid",accountid);
        //从本次结算结束日期开始到当前日期
        pmap.put("businessdate", CommonUtils.getNextDayByDate(map.get("businessdate1").toString()));
        pmap.put("businessdate1", CommonUtils.getTodayDataStr());
        pmap.put("goodsid",map.get("goodsid"));
        pmap.put("storageid",map.get("storageid"));
        //更新结算日期之后的单据：保存存货明细和存货合计数据，回写销售成本之后，删除掉存货明细，存货合计数据，以及结算记录
        PageMap pageMap=new PageMap();
        pageMap.setCondition(pmap);
        //保存存货明细和存货合计数据
        addAccountCostSumAndDetailData(pmap);

        //更新来源单据成本价
        updateSourceBillCostPrice(accountid+"");
        //删除新增的存货明细数据
        storageStockMapper.deleteCostAccountDetailByAccountid(accountid+"");
        //删除新增的存货合计数据
        storageStockMapper.deleteCostAccountSumByAccountid(accountid+"");

    }

    /**
     * 获取存货明细数据
     * @param pageMap
     * @return com.hd.agent.common.util.PageData
     * @throws
     * @author luoqiang
     * @date Jan 30, 2018
     */
    public PageData showCostAccountDetailData(PageMap pageMap) throws Exception{
        Map condition=pageMap.getCondition();
        String accounttype=(String)pageMap.getCondition().get("accounttype");
        BigDecimal costprice=BigDecimal.ZERO;
        if(condition.containsKey("costprice")){
            costprice=new BigDecimal(condition.get("costprice").toString());
        }
        List<StorageStockDetail> list=storageStockMapper.getCostAccountDetailList(pageMap);
        for(StorageStockDetail storageStockDetail:list){
            GoodsInfo goodsInfo=getGoodsInfoByID(storageStockDetail.getGoodsid());
            if(goodsInfo!=null){
                storageStockDetail.setGoodsname(goodsInfo.getName());
            }
            StorageInfo storageInfo=getStorageInfoByID(storageStockDetail.getStorageid());
            if(storageInfo!=null){
                storageStockDetail.setStoragename(storageInfo.getName());
            }
            if("2".equals(accounttype)){
//                BigDecimal outnum=storageStockDetail.getOutnum();
//                //出库金额用数量*成本价计算
//                storageStockDetail.setOuttaxamount(outnum.multiply(costprice));
            }
        }
        int count=storageStockMapper.getCostAccountDetailCount(pageMap);

        PageData pageData=new PageData(count,list,pageMap);
        Map total=storageStockMapper.getCostAccountDetailSum(pageMap);
//        BigDecimal totaloutnum=BigDecimal.ZERO;
//        if(total.containsKey("outnum")){
//            totaloutnum=(BigDecimal)total.get("outnum");
//        }
//        if("2".equals(accounttype)){
//            total.put("outtaxamount",costprice.multiply(totaloutnum));
//        }
        List footlist=new ArrayList();
        footlist.add(total);
        pageData.setFooter(footlist);
        return pageData;
    }

    private void getCostAccountDataListByAccountType(List<StorageStockSum> list,String accounttype) throws Exception{
        if("2".equals(accounttype)){
            for(StorageStockSum storageStockSum:list){
                BigDecimal endnum=storageStockSum.getEndnum();
                //加权平均单价
                BigDecimal costprice=storageStockSum.getCostprice();
                //加权平均未税单价
                BigDecimal costnotaxprice=storageStockSum.getCostnotaxprice();
                BigDecimal outnum=storageStockSum.getOutnum();

                BigDecimal initnum=storageStockSum.getInitnum();
                BigDecimal innum=storageStockSum.getInnum();
                //没有期初数量和入库数量，有出库数量的时候，取商品最新库存价格
                if(initnum.compareTo(BigDecimal.ZERO)==0&&innum.compareTo(BigDecimal.ZERO)==0
                        &&outnum.compareTo(BigDecimal.ZERO)!=0){
                    GoodsInfo goodsInfo=goodsMapper.getGoodsInfo(storageStockSum.getGoodsid());
                    if(goodsInfo!=null){
                        costprice=goodsInfo.getNewstorageprice();
                        storageStockSum.setCostprice(costprice);
                    }
                }

//                storageStockSum.setEndtaxamount(endnum.multiply(costprice));
//                storageStockSum.setEndnotaxamount(endnum.multiply(costnotaxprice));

                BigDecimal nowCostPrice=getGoodsCostprice(storageStockSum.getStorageid(),storageStockSum.getGoodsid());
                storageStockSum.setNowCostPrice(nowCostPrice);
            }
        }
    }

    private String checkBillForAccount(Map checkMap){
        String errormsg="";
        if(checkMap.containsKey("purchasearrivalordernum") && Integer.parseInt(checkMap.get("purchasearrivalordernum").toString())>0){
            errormsg+="采购进货单"+Integer.parseInt(checkMap.get("purchasearrivalordernum").toString())+"条单据未审核<br>";
        }
        if(checkMap.containsKey("purchasereturnordernum") && Integer.parseInt(checkMap.get("purchasereturnordernum").toString())>0){
            errormsg+="采购退货通知单"+Integer.parseInt(checkMap.get("purchasereturnordernum").toString())+"条单据未审核<br>";
        }
        if(checkMap.containsKey("storageotheroutnum") && Integer.parseInt(checkMap.get("storageotheroutnum").toString())>0){
            errormsg+="其它出库单"+Integer.parseInt(checkMap.get("storageotheroutnum").toString())+"条单据未审核<br>";
        }
        if(checkMap.containsKey("storageotherenternum") && Integer.parseInt(checkMap.get("storageotherenternum").toString())>0){
            errormsg+="其它入库单"+Integer.parseInt(checkMap.get("storageotherenternum").toString())+"条单据未审核<br>";
        }
        if(checkMap.containsKey("storagesaleoutnum") && Integer.parseInt(checkMap.get("storagesaleoutnum").toString())>0){
            errormsg+="发货单"+Integer.parseInt(checkMap.get("storagesaleoutnum").toString())+"条单据未审核<br>";
        }
        if(checkMap.containsKey("salesrejectbillnum") && Integer.parseInt(checkMap.get("salesrejectbillnum").toString())>0){
            errormsg+="销售退货通知单"+Integer.parseInt(checkMap.get("salesrejectbillnum").toString())+"条单据未审核<br>";
        }
        if(checkMap.containsKey("adjustmentsnum") && Integer.parseInt(checkMap.get("adjustmentsnum").toString())>0){
            errormsg+="报溢单"+Integer.parseInt(checkMap.get("adjustmentsnum").toString())+"条单据未审核<br>";
        }
        if(checkMap.containsKey("lossadjustmentsnum") && Integer.parseInt(checkMap.get("lossadjustmentsnum").toString())>0){
            errormsg+="报损单"+Integer.parseInt(checkMap.get("lossadjustmentsnum").toString())+"条单据未审核";
        }
        if(checkMap.containsKey("storageallocateoutnum") && Integer.parseInt(checkMap.get("storageallocateoutnum").toString())>0){
            errormsg+="调拨单"+Integer.parseInt(checkMap.get("storageallocateoutnum").toString())+"条单据未审核";
        }
        return errormsg;
    }

    /**
     * 获取库存成本调整后的单价
     * @param pageMap
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Mar 22, 2018
     */
    public Map getStorageAfterChangePrice(PageMap pageMap) throws Exception{
        String storageid=(String) pageMap.getCondition().get("storageid");
        String goodsid=(String) pageMap.getCondition().get("goodsid");
        Map resMap=new HashMap();
        String accounttype=(String)pageMap.getCondition().get("accounttype");
        if(StringUtils.isEmpty(accounttype)){
            accounttype="2";
        }
        List<StorageStockSum> list=storageStockMapper.getAbnormalBillReportList(pageMap);
        getCostAccountDataListByAccountType(list,accounttype);
        StorageStockSum storageStockSum=list.get(0);
        BigDecimal endnum=storageStockSum.getRealendnum();
        BigDecimal endamount=storageStockSum.getRealendamount();
        BigDecimal changeamount=new BigDecimal(pageMap.getCondition().get("changeamount").toString());
        BigDecimal afterendprice=BigDecimal.ZERO;
        if(endnum.compareTo(BigDecimal.ZERO)!=0){
            afterendprice=endamount.add(changeamount).divide(endnum,6,BigDecimal.ROUND_HALF_UP);
        }


        BigDecimal realendnum=BigDecimal.ZERO;
        BigDecimal costamount=BigDecimal.ZERO;
        BigDecimal afterstorageprice=BigDecimal.ZERO;
        BigDecimal costDiffAmount=BigDecimal.ZERO;
        //库存成本金额+未分摊金额+调整金额/数量
        if("".equals(storageid)){
            costDiffAmount=goodsMapper.getCostDiffAmountByGoodsid(null,goodsid);
            costDiffAmount=costDiffAmount==null?BigDecimal.ZERO:costDiffAmount;
            StorageSummary storageSummary=getStorageSummaryMapper().getStorageSummarySumByGoodsid(goodsid);
            if(storageSummary!=null){
                realendnum=storageSummary.getExistingnum();
            }
            GoodsInfo goodsInfo=getGoodsInfoByID(goodsid);
            if(goodsInfo!=null){
                costamount=realendnum.multiply(goodsInfo.getNewstorageprice());
            }
        }else{
            StorageSummary storageSummary=getStorageSummaryByStorageidAndGoodsid(storageid,goodsid);
            if(storageSummary!=null){
                realendnum=storageSummary.getExistingnum();
                costDiffAmount=storageSummary.getStorageamount()==null?BigDecimal.ZERO:storageSummary.getStorageamount();
                costamount=storageSummary.getCostprice().multiply(storageSummary.getExistingnum());
            }
        }

        if(endnum.compareTo(BigDecimal.ZERO)!=0){
            afterstorageprice=(costamount.add(costDiffAmount).add(changeamount).subtract(costDiffAmount)).divide(realendnum,6,BigDecimal.ROUND_HALF_UP);
        }
        resMap.put("afterstorageprice",afterstorageprice);
        resMap.put("afterendprice",afterendprice);
        return resMap;
    }

    @Override
    public Map addChangeBill(String storageid, String goodsid, String changedate, BigDecimal changeamount,String isAcountCostPrice,Map map) throws Exception{
        Map resMap=new HashMap();
        //需要重新结算的时候判断单据是否审核过
        if("1".equals(isAcountCostPrice)){
            //判断日期范围内相关全部单据，是否审核通过，有未审核的单据就不允许结算。
            Map checkMap=storageStockMapper.checkBillForAccount(map);
            String errormsg=checkBillForAccount(checkMap);
            if(StringUtils.isNotEmpty(errormsg)){
                map.put("flag",false);
                map.put("msg",errormsg);
                return map;
            }
        }

        //生成其它入库单
        if(changeamount.compareTo(BigDecimal.ZERO)>0){
            resMap=addStorageOtherEnterChangeBill(storageid,goodsid,changedate,changeamount);
        }
        //生成其它出库单
        else if(changeamount.compareTo(BigDecimal.ZERO)<0){
            resMap=addStorageOtherOutChangeBill(storageid,goodsid,changedate,changeamount.negate());
        }
        Boolean flag=(Boolean) resMap.get("flag");
        //需要重新结算
        if(flag&&"1".equals(isAcountCostPrice)){
            Map accountMap=insertCostReportData(map);
            Boolean accountflag=(Boolean) accountMap.get("flag");
            if(!accountflag){
                throw new Exception("成本调整结算失败");
            }
        }
        return resMap;
    }

    /**
     * 生成其它入库单
     * @param storageid
     * @param goodsid
     * @param changedate
     * @param changeamount
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Mar 22, 2018
     */
    private Map addStorageOtherEnterChangeBill(String storageid, String goodsid, String changedate, BigDecimal changeamount) throws Exception{
        Map resMap=new HashMap();
        StorageOtherEnter storageOtherEnter=new StorageOtherEnter();
        if (isAutoCreate("t_storage_other_enter")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(storageOtherEnter, "t_storage_other_enter");
            storageOtherEnter.setId(id);
        }else{
            storageOtherEnter.setId("QTRK-"+CommonUtils.getDataNumberSendsWithRand());
        }
        storageOtherEnter.setStorageid(storageid);
        storageOtherEnter.setEntertype(STORAGE_COST_ENTERTYPE);
        storageOtherEnter.setRemark("库存成本调整");
        storageOtherEnter.setBusinessdate(changedate);
        storageOtherEnter.setStatus("2");

        StorageOtherEnterDetail storageOtherEnterDetail=new StorageOtherEnterDetail();
        storageOtherEnterDetail.setBillid(storageOtherEnter.getId());
        storageOtherEnterDetail.setGoodsid(goodsid);
        GoodsInfo goodsInfo = getAllGoodsInfoByID(goodsid);
        if (null != goodsInfo) {
            storageOtherEnterDetail.setBrandid(goodsInfo.getBrand());
            storageOtherEnterDetail.setCostprice(getGoodsCostprice(storageid,goodsInfo));
            //实际成本价 不包括核算成本价
            storageOtherEnterDetail.setRealcostprice(getGoodsCostprice(storageid,goodsInfo));
            storageOtherEnterDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
            storageOtherEnterDetail.setAuxunitid(goodsInfo.getAuxunitid());
            storageOtherEnterDetail.setUnitnum(BigDecimal.ZERO);
            storageOtherEnterDetail.setTaxamount(changeamount);
            //计算辅单位数量
            Map auxmap = countGoodsInfoNumber(storageOtherEnterDetail.getGoodsid(), storageOtherEnterDetail.getAuxunitid(), storageOtherEnterDetail.getUnitnum());
            if (auxmap.containsKey("auxnum")) {
                storageOtherEnterDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
            }
            BigDecimal notaxamount=getNotaxAmountByTaxAmount(changeamount,goodsInfo.getDefaulttaxtype());
            storageOtherEnterDetail.setNotaxamount(notaxamount);
            storageOtherEnterDetail.setTax(changeamount.subtract(notaxamount));

            StorageSummaryBatch storageSummaryBatch = null;
            //批次商品根据生产日期获取批次号
            if("1".equals(goodsInfo.getIsbatch())){
                if(StringUtils.isEmpty(storageOtherEnterDetail.getBatchno())){
                    storageOtherEnterDetail.setProduceddate(changedate);
                    storageSummaryBatch = addOrGetStorageSummaryBatchByStorageidAndProduceddate(storageOtherEnter.getStorageid(), storageOtherEnterDetail.getGoodsid(), storageOtherEnterDetail.getProduceddate());
                    storageOtherEnterDetail.setSummarybatchid(storageSummaryBatch.getId());
                    storageOtherEnterDetail.setDeadline(storageSummaryBatch.getDeadline());
                    storageOtherEnterDetail.setProduceddate(storageSummaryBatch.getProduceddate());

                }else{
                    storageSummaryBatch = getStorageSummaryBatchByStorageidAndBatchno(storageOtherEnter.getStorageid(), storageOtherEnterDetail.getBatchno(), storageOtherEnterDetail.getGoodsid());
                    storageOtherEnterDetail.setSummarybatchid(storageSummaryBatch.getId());
                    storageOtherEnterDetail.setDeadline(storageSummaryBatch.getDeadline());
                    storageOtherEnterDetail.setProduceddate(storageSummaryBatch.getProduceddate());
                }
            }
            //非批次商品获取库存
            if(null == storageSummaryBatch){
                storageSummaryBatch = getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageOtherEnter.getStorageid(), storageOtherEnterDetail.getGoodsid());
                storageOtherEnterDetail.setSummarybatchid(storageSummaryBatch.getId());
            }
            //对批次管理的商品进行批次号添加
            if("1".equals(goodsInfo.getIsbatch()) &&  StringUtils.isEmpty(storageOtherEnterDetail.getBatchno())){
                if(StringUtils.isNotEmpty(storageSummaryBatch.getBatchno())){
                    storageOtherEnterDetail.setBatchno(storageSummaryBatch.getBatchno());
                }else {
                    storageOtherEnterDetail.setBatchno(goodsInfo.getId());
                }
            }
        }
        storageOtherEnterDetail.setStorageid(storageOtherEnter.getStorageid());
        storageOtherEnterMapper.addStorageOtherEnterDetail(storageOtherEnterDetail);
        SysUser sysUser = getSysUser();

        storageOtherEnter.setAdddeptid(sysUser.getDepartmentid());
        storageOtherEnter.setAdddeptname(sysUser.getDepartmentname());
        storageOtherEnter.setAdduserid(sysUser.getUserid());
        storageOtherEnter.setAddusername(sysUser.getName());
        int i = storageOtherEnterMapper.addStorageOtherEnter(storageOtherEnter);
        resMap.put("id",storageOtherEnter.getId());
        resMap.put("flag",i>0);
        if(i>0){
            Boolean flag=storageOtherEnterService.auditStorageOtherEnter(storageOtherEnter.getId());
            if(!flag){
                throw new Exception("审核其它入库单失败");
            }
            resMap.put("flag",flag);
        }else{
            throw new Exception("生成其它入库单失败");
        }
        return resMap;
    }
    /**
     * 生成其它出库单
     * @param storageid
     * @param goodsid
     * @param changedate
     * @param changeamount
     * @return java.util.Map
     * @throws
     * @author luoqiang
     * @date Mar 22, 2018
     */
    private Map addStorageOtherOutChangeBill(String storageid, String goodsid, String changedate, BigDecimal changeamount) throws Exception{
        Map resMap=new HashMap();
        StorageOtherOut storageOtherOut=new StorageOtherOut();
        if (isAutoCreate("t_storage_other_out")) {
            // 获取自动编号
            String id = getAutoCreateSysNumbderForeign(storageOtherOut, "t_storage_other_out");
            storageOtherOut.setId(id);
        }else{
            storageOtherOut.setId("QTCK-"+CommonUtils.getDataNumberSendsWithRand());
        }
        storageOtherOut.setStatus("2");
        storageOtherOut.setOuttype(STORAGE_COST_OUTTYPE);
        storageOtherOut.setStorageid(storageid);
        storageOtherOut.setBusinessdate(changedate);
        storageOtherOut.setRemark("库存成本调整");

        StorageOtherOutDetail storageOtherOutDetail=new StorageOtherOutDetail();
        storageOtherOutDetail.setBillid(storageOtherOut.getId());
        storageOtherOutDetail.setGoodsid(goodsid);
        storageOtherOutDetail.setUnitnum(BigDecimal.ZERO);
        storageOtherOutDetail.setTaxamount(changeamount);
        GoodsInfo goodsInfo = getAllGoodsInfoByID(storageOtherOutDetail.getGoodsid());
        if (null != goodsInfo) {
            storageOtherOutDetail.setBrandid(goodsInfo.getBrand());
            storageOtherOutDetail.setCostprice(storageOtherOutDetail.getTaxprice());
            //实际成本价 不包括核算成本价
            storageOtherOutDetail.setRealcostprice(storageOtherOutDetail.getTaxprice());
            storageOtherOutDetail.setTaxtype(goodsInfo.getDefaulttaxtype());
            //计算辅单位数量
            Map auxmap = countGoodsInfoNumber(storageOtherOutDetail.getGoodsid(), storageOtherOutDetail.getAuxunitid(), storageOtherOutDetail.getUnitnum());
            if (auxmap.containsKey("auxnum")) {
                storageOtherOutDetail.setTotalbox((BigDecimal) auxmap.get("auxnum"));
            }

            BigDecimal notaxamount=getNotaxAmountByTaxAmount(changeamount,goodsInfo.getDefaulttaxtype());
            storageOtherOutDetail.setNotaxamount(notaxamount);
            storageOtherOutDetail.setTax(changeamount.subtract(notaxamount));
        }
        storageOtherOutDetail.setStorageid(storageOtherOut.getStorageid());
        StorageSummaryBatch storageSummaryBatch = null;
        if("1".equals(goodsInfo.getIsbatch()) || StringUtils.isNotEmpty(storageOtherOutDetail.getSummarybatchid())){
            storageSummaryBatch = getStorageSummaryBatchById(storageOtherOutDetail.getSummarybatchid());
        }else{
            storageSummaryBatch=getStorageSummaryBatchNoBatchnoByStorageidAndGoodsid(storageOtherOut.getStorageid(), storageOtherOutDetail.getGoodsid());
        }

        //关联相关商品批次
        storageOtherOutDetail.setSummarybatchid(storageSummaryBatch.getId());
        storageOtherOutMapper.addStorageOtherOutDetail(storageOtherOutDetail);

        SysUser sysUser = getSysUser();

        storageOtherOut.setAdddeptid(sysUser.getDepartmentid());
        storageOtherOut.setAdddeptname(sysUser.getDepartmentname());
        storageOtherOut.setAdduserid(sysUser.getUserid());
        storageOtherOut.setAddusername(sysUser.getName());
        int i = storageOtherOutMapper.addStorageOtherOut(storageOtherOut);


        if(i>0){
            Boolean flag=storageOtherOutService.auditStorageOtherOut(storageOtherOut.getId());
            if(!flag){
                throw new Exception("审核其它出库单失败");
            }
            resMap.put("flag",flag);
        }else{
            throw new Exception("生成其它出库单失败");
        }
        return resMap;
    }
}
