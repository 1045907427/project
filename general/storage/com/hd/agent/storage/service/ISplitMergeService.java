package com.hd.agent.storage.service;

import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.SplitMerge;
import com.hd.agent.storage.model.SplitMergeDetail;

import java.util.List;
import java.util.Map;

/**
 * 拆分单Service
 * @author limin
 * @date Sep 16, 2015
 */
public interface ISplitMergeService {

    /**
     * 查询拆分单List
     * @param pageMap
     * @return
     * @author limin
     * @date Sep 17, 2015
     */
    public PageData selectSplitMergeList(PageMap pageMap) throws Exception;

    /**
     * 新增拆装单
     * @param splitMerge
     * @param list
     * @return
     * @author limin
     * @date Oct 22, 2015
     */
    public int insertSplitMerge(SplitMerge splitMerge, List<SplitMergeDetail> list);

    /**
     * 修改拆装单
     * @param splitMerge
     * @param list
     * @return
     * @author limin
     * @date Oct 22, 2015
     */
    public int updateSplitMerge(SplitMerge splitMerge, List<SplitMergeDetail> list);

    /**
     * 查询拆装单
     * @param id
     * @return
     * @author limin
     * @date Oct 22, 2015
     */
    public SplitMerge selectSplitMerge(String id);

    /**
     * 查询拆装单明细
     * @param pageMap
     * @return
     * @author limin
     * @date Sep 22, 2015
     */
    public PageData selectSplitMergeDetailList(PageMap pageMap);

    /**
     * 审核拆装单
     * @param splitMerge
     * @param enters
     * @param outs
     * @return
     * @author limin
     * @date Oct 23, 2015
     */
    public int auditSplitMerge(SplitMerge splitMerge, Map enters, Map outs) throws Exception;

    /**
     * 删除拆装单
     * @param id
     * @return
     * @author limin
     * @date Oct 23, 2015
     */
    public int deleteSplitMerge(String id);

    /**
     * 根据billid查询明细list
     * @param billid
     * @return
     * @author limin
     * @date Oct 27, 2015
     */
    public List<SplitMergeDetail> selectSplitMergeDetailListByBillid(String billid) throws Exception ;

    /**
     * 更新打印次数
     * @param id
     * @return
     * @throws Exception
     * @author limin
     * @date Jan 12, 2016
     */
    public int updateSplitMergePrinttimes(String id) throws Exception;
}
