package com.hd.agent.storage.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.model.Brand;
import com.hd.agent.basefiles.model.GoodsInfo;
import com.hd.agent.basefiles.model.StorageInfo;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.dao.SplitMergeMapper;
import com.hd.agent.storage.model.*;
import com.hd.agent.storage.service.IPurchaseEnterService;
import com.hd.agent.storage.service.ISplitMergeService;
import com.hd.agent.storage.service.IStorageOtherEnterService;
import com.hd.agent.storage.service.IStorageOtherOutService;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 拆分单Service实现类
 * @author limin
 * @date Sep 16, 2015
 */
public class SplitMergeServiceImpl extends BaseStorageServiceImpl implements ISplitMergeService {

    /**
     * 拆分单Mapper
     */
    private SplitMergeMapper splitMergeMapper;

    private IStorageOtherEnterService storageOtherEnterService;

    private IStorageOtherOutService storageOtherOutService;

    private IPurchaseEnterService purchaseEnterService;

    public SplitMergeMapper getSplitMergeMapper() {
        return splitMergeMapper;
    }

    public void setSplitMergeMapper(SplitMergeMapper splitMergeMapper) {
        this.splitMergeMapper = splitMergeMapper;
    }

    public IStorageOtherEnterService getStorageOtherEnterService() {
        return storageOtherEnterService;
    }

    public void setStorageOtherEnterService(IStorageOtherEnterService storageOtherEnterService) {
        this.storageOtherEnterService = storageOtherEnterService;
    }

    public IStorageOtherOutService getStorageOtherOutService() {
        return storageOtherOutService;
    }

    public void setStorageOtherOutService(IStorageOtherOutService storageOtherOutService) {
        this.storageOtherOutService = storageOtherOutService;
    }

    public IPurchaseEnterService getPurchaseEnterService() {
        return purchaseEnterService;
    }

    public void setPurchaseEnterService(IPurchaseEnterService purchaseEnterService) {
        this.purchaseEnterService = purchaseEnterService;
    }

    @Override
    public PageData selectSplitMergeList(PageMap pageMap) throws Exception {

        String dataSql = getDataAccessRule("t_storage_splitmerge", "t1");
        pageMap.setDataSql(dataSql);

        List<SplitMerge> list = splitMergeMapper.selectSplitMergeList(pageMap);
        for(SplitMerge sm : list) {

            if(StringUtils.isNotEmpty(sm.getStorageid())) {

                StorageInfo storage = getStorageInfoByID(sm.getStorageid());
                if(storage != null) {

                    sm.setStoragename(storage.getName());
                }
            }
        }

        int count = splitMergeMapper.selectSplitMergeListCount(pageMap);

        PageData data = new PageData(count, list, pageMap);

        return data;
    }

    @Override
    public int insertSplitMerge(SplitMerge splitMerge, List<SplitMergeDetail> list) {

        int ret = splitMergeMapper.insertSplitMerge(splitMerge);

        for(SplitMergeDetail detail : list) {

            if(StringUtils.isEmpty(detail.getGoodsid())) {

                continue;
            }

            detail.setBillid(splitMerge.getId());
            splitMergeMapper.insertSplitMergeDetail(detail);
        }

        return ret;
    }

    @Override
    public int updateSplitMerge(SplitMerge splitMerge, List<SplitMergeDetail> list) {

        int ret = splitMergeMapper.updateSplitMerge(splitMerge);

        splitMergeMapper.deleteSplitMergeDetail(splitMerge.getId());
        for(SplitMergeDetail detail : list) {

            if(StringUtils.isEmpty(detail.getGoodsid())) {

                continue;
            }

            detail.setBillid(splitMerge.getId());
            splitMergeMapper.insertSplitMergeDetail(detail);
        }

        return ret;
    }

    @Override
    public SplitMerge selectSplitMerge(String id) {

        return splitMergeMapper.selectSplitMerge(id);
    }

    @Override
    public PageData selectSplitMergeDetailList(PageMap pageMap) {

        List list = splitMergeMapper.selectSplitMergeDetailList(pageMap);
        int count = splitMergeMapper.selectSplitMergeDetailListCount(pageMap);

        PageData data = new PageData(count, list, pageMap);

        return data;
    }

    @Override
    public int auditSplitMerge(SplitMerge splitMerge, Map enters, Map outs) throws Exception {

        // 入库单 保存&审核
        {
            List keys = new ArrayList();
            keys.addAll(enters.keySet());
            Collections.sort(keys);
            Iterator<String> iterator = keys.iterator();
            while(iterator.hasNext()) {

                String key = iterator.next();
                StorageOtherEnter enter = (StorageOtherEnter) enters.get(key);
                key = iterator.next();
                List<StorageOtherEnterDetail> enterDetailList = (List<StorageOtherEnterDetail>) enters.get(key);

                // 批次管理时，对明细中的商品设置批次号和截止日期
                for(StorageOtherEnterDetail detail : enterDetailList) {

                    GoodsInfo goods = getGoodsInfoByID(detail.getGoodsid());
                    StorageInfo storage = getStorageInfoByID(detail.getStorageid());

                    if(goods != null && storage != null && "1".equals(goods.getIsbatch()) && "1".equals(storage.getIsbatch())) {

                        detail.setProduceddate(StringUtils.isEmpty(splitMerge.getProduceddate()) ? CommonUtils.getTodayDataStr() : splitMerge.getProduceddate());
                        Map map = getBatchno(detail.getGoodsid(), detail.getProduceddate());
                        detail.setBatchno((String) map.get("batchno"));
                        detail.setDeadline((String) map.get("deadline"));
                    }
                }

                storageOtherEnterService.addStorageOtherEnter(enter, enterDetailList);
                storageOtherEnterService.auditStorageOtherEnter(enter.getId());
            }
        }

        // 出库单 保存&审核
        {
            List keys = new ArrayList();
            keys.addAll(outs.keySet());
            Collections.sort(keys);
            Iterator<String> iterator = keys.iterator();
            while(iterator.hasNext()) {

                String key = iterator.next();
                StorageOtherOut out = (StorageOtherOut) outs.get(key);
                key = iterator.next();
                List<StorageOtherOutDetail> outDetailList = (List<StorageOtherOutDetail>) outs.get(key);

                storageOtherOutService.addStorageOtherOut(out, outDetailList);
                storageOtherOutService.auditStorageOtherOut(out.getId());
            }
        }

        int ret = splitMergeMapper.auditSplitMerge(splitMerge);
        return ret;
    }

    @Override
    public int deleteSplitMerge(String id) {

        splitMergeMapper.deleteSplitMergeDetail(id);
        return splitMergeMapper.deleteSplitMerge(id);
    }

    @Override
    public List<SplitMergeDetail> selectSplitMergeDetailListByBillid(String billid) throws Exception {

        List<SplitMergeDetail> list = splitMergeMapper.selectSplitMergeDetailListByBillid(billid);
        for(SplitMergeDetail detail : list) {

            if(StringUtils.isNotEmpty(detail.getGoodsid())) {

                GoodsInfo goods = getGoodsInfoByID(detail.getGoodsid());
                if(goods != null) {

                    detail.setGoodsname(goods.getName());
                }
            }

            if(StringUtils.isNotEmpty(detail.getStorageid())) {

                StorageInfo storage = getStorageInfoByID(detail.getStorageid());
                if(storage != null) {

                    detail.setStoragename(storage.getName());
                }
            }
        }

        return list;
    }

    @Override
    public int updateSplitMergePrinttimes(String id) throws Exception {

        return splitMergeMapper.updateSplitMergePrinttimes(id);
    }
}
