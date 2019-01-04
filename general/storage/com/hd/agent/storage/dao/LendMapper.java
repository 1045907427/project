package com.hd.agent.storage.dao;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.storage.model.Lend;
import com.hd.agent.storage.model.LendDetail;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface LendMapper {
    /**
     * 借货还货单明细添加
     * @param lendDetail
     * @return
     * @author chenwei
     * @date Aug 6, 2013
     */
    public int addLendDetail(LendDetail lendDetail);
    /**
     * 借货还货单添加
     * @param storageOtherEnter
     * @return
     * @author chenwei
     * @date Aug 6, 2013
     */
    public int addLend(Lend storageOtherEnter);
    /**
     * 获取借货还货单详细信息
     * @param id
     * @return
     * @author chenwei
     * @date Aug 6, 2013
     */
    public Lend getLendInfo(@Param("id") String id);
    /**
     * 根据借货还货单编号获取明细列表
     * @param billid
     * @return
     * @author chenwei
     * @date Aug 6, 2013
     */
    public List getLendDetailListByID(@Param("billid") String billid);
    /**
     * 获取借货还货单列表数据
     * @param pageMap
     * @return
     * @author chenwei
     * @date Aug 6, 2013
     */
    public List showLendList(PageMap pageMap);
    /**
     * 获取借货还货单列表数量
     * @param pageMap
     * @return
     * @author chenwei
     * @date Aug 6, 2013
     */
    public int showLendListCount(PageMap pageMap);
    /**
     * 删除借货还货单明细
     * @param billid
     * @return
     * @author chenwei
     * @date Aug 6, 2013
     */
    public int deleteLendDetailListByBillid(@Param("billid") String billid);
    /**
     * 借货还货单修改
     * @param storageOtherEnter
     * @return
     * @author chenwei
     * @date Aug 6, 2013
     */
    public int editLend(Lend storageOtherEnter);
    /**
     * 借货还货单删除
     * @param id
     * @return
     * @author chenwei
     * @date Aug 6, 2013
     */
    public int deleteLend(@Param("id") String id);
    /**
     * 借货还货单审核
     * @param id
     * @return
     * @author chenwei
     * @date Aug 6, 2013
     */
    public int auditLend(@Param("id") String id, @Param("userid") String userid, @Param("username") String username, @Param("businessdate") String businessdate);
    /**
     * 借货还货单反审
     * @param id
     * @param userid
     * @param username
     * @return
     * @author chenwei
     * @date Feb 14, 2014
     */
    public int oppauditLend(@Param("id") String id, @Param("userid") String userid, @Param("username") String username);
    /**
     * 根据编号获取借货还货单明细详细信息
     * @param id
     * @return
     * @author chenwei
     * @date Aug 7, 2013
     */
    public LendDetail getLendDetail(@Param("id") String id);

    /**
     * 获取借货还货单列表<br/>
     * map中参数:<br/>
     * dataSql：数据权限<br/>
     * idarrs: 编号字符串组，类似 1,2,3<br/>
     * status: 表示状态3<br/>
     * statusarr: 表示状态，类似 1,2,3<br/>
     * @param map
     * @return
     * @author zhanghonghui
     * @date 2013-10-16
     */
    public List showLendListBy(Map map);
    /**
     * 更新打印次数
     * @param lend
     * @return
     * @author zhanghonghui
     * @date 2015年1月9日
     */
    public int updateOrderPrinttimes(Lend lend);
}