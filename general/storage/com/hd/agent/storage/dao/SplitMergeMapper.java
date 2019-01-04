package com.hd.agent.storage.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.SplitMerge;
import com.hd.agent.storage.model.SplitMergeDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 拆分单Mapper
 * @author limin
 */
public interface SplitMergeMapper {

    /**
     * 删除拆装单
     * @param id
     * @return
     * @author limin
     * @date Sep 16, 2015
     */
    public int deleteSplitMerge(@Param("id")String id);

    /**
     * 删除拆装单明细
     * @param billid
     * @return
     * @author limin
     * @date Sep 16, 2015
     */
    public int deleteSplitMergeDetail(@Param("billid")String billid);

    /**
     * 新增拆装单
     * @param splitMerge
     * @return
     * @author limin
     * @date Sep 16, 2015
     */
    public int insertSplitMerge(SplitMerge splitMerge);

    /**
     * 新增拆装单明细
     * @param detail
     * @return
     * @author limin
     * @date Sep 16, 2015
     */
    public int insertSplitMergeDetail(SplitMergeDetail detail);

    /**
     * 查询拆装单List
     * @param pageMap
     * @return
     * @author limin
     * @date Sep 16, 2015
     */
    public List<SplitMerge> selectSplitMergeList(PageMap pageMap);

    /**
     * 查询拆装单ListCount
     * @param pageMap
     * @return
     * @author limin
     * @date Sep 16, 2015
     */
    public int selectSplitMergeListCount(PageMap pageMap);

    /**
     * 更新拆装单信息
     * @param splitMerge
     * @return
     * @author limin
     * @date Sep 16, 2015
     */
    public int updateSplitMerge(SplitMerge splitMerge);

    /**
     * 查询拆装单
     * @param id
     * @return
     * @author limin
     * @date Sep 22, 2015
     */
    public SplitMerge selectSplitMerge(String id);

    /**
     * 查询拆装单明细
     * @param pageMap
     * @return
     * @author limin
     * @date Sep 22, 2015
     */
    public List<SplitMerge> selectSplitMergeDetailList(PageMap pageMap);

    /**
     * 查询拆装单明细count
     * @param pageMap
     * @return
     * @author limin
     * @date Sep 22, 2015
     */
    public int selectSplitMergeDetailListCount(PageMap pageMap);

    /**
     * 审核拆装单
     * @param splitMerge
     * @return
     * @author limin
     * @date Oct 23, 2015
     */
    public int auditSplitMerge(SplitMerge splitMerge);

    /**
     * 根据billid查询明细list
     * @param billid
     * @return
     * @author limin
     * @date Oct 26, 2015
     */
    public List<SplitMergeDetail> selectSplitMergeDetailListByBillid(String billid);

    /**
     * 更新打印次数
     * @param id
     * @return
     * @author limin
     * @date Oct 26, 2015
     */
    public int updateSplitMergePrinttimes(@Param("id")String id);
}