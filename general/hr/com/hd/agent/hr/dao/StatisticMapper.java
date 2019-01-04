package com.hd.agent.hr.dao;

import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StatisticMapper {

    /**
     *
     * @param businessdate
     * @return
     * @author limin
     * @date Jun 29, 2015
     */
    public int deleteRestStatisticByDate(@Param("businessdate") String businessdate);

    /**
     *
     * @param id
     * @return
     * @author limin
     * @date Jul 21, 2016
     */
    public int deleteRestStatisticById(@Param("id") String id);

    /**
     *
     * @param map
     * @return
     * @author limin
     * @date Jun 29, 2015
     */
    public int insertRestStatistic(Map map);

    /**
     *
     * @param list
     * @return
     * @author limin
     * @date Jul 22, 2016
     */
    public int insertRestStatisticList(@Param("list") List list);

    /**
     *
     * @param processid
     * @return
     * @author limin
     * @date Jul 21, 2016
     */
    public Map selectRestStatisticByProcessid(@Param("processid") String processid, @Param("businessdate") String businessdate);

    /**
     *
     * @param map
     * @return
     * @author limin
     * @date Jun 30, 2015
     */
    public List<Map> selectRestList(PageMap map);

    /**
     *
     * @param map
     * @return
     * @author limin
     * @date Jun 30, 2015
     */
    public int selectRestListCnt(PageMap map);

    /**
     *
     * @param businessdate
     * @return
     * @author limin
     * @date Jun 29, 2015
     */
    public int deleteOvertimeStatisticByDate(@Param("businessdate") String businessdate);

    /**
     *
     * @param map
     * @return
     * @author limin
     * @date Jun 29, 2015
     */
    public int insertOvertimeStatistic(Map map);

    /**
     *
     * @param map
     * @return
     * @author limin
     * @date Jun 30, 2015
     */
    public List<Map> selectOvertimeList(PageMap map);

    /**
     *
     * @param map
     * @return
     * @author limin
     * @date Jun 30, 2015
     */
    public int selectOvertimeListCnt(PageMap map);
}
