/**
 * @(#)SigninMapper.java
 *
 * @author limin
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2014-9-18 limin 创建版本
 */
package com.hd.agent.hr.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.hr.model.Signin;

/**
 * 签到Mapper
 * 
 * @author limin
 */
public interface SigninMapper {

	public int selectSigninListCount(PageMap map);
	
	/**
	 * 查询签到List
	 * @param map
	 * @return
	 * @author limin 
	 * @date 2014-9-20
	 */
	public List selectSigninList(PageMap map);
	
	/**
	 * 查询签到详情
	 * @param id
	 * @return
	 * @author limin 
	 * @date 2014-9-20
	 */
	public Signin selectSigninInfo(String id);

	/**
	 * 新增签到
	 * @param signin
	 * @return
	 * @author chenwei 
	 * @date 2014年9月19日
	 */
	public int addSignin(Signin signin);

	/**
	 * 根据用户编号和业务日期获取签到信息
	 * @param userid
	 * @param businessdate
	 * @return
	 * @author chenwei 
	 * @date 2014年9月19日
	 */
	public Signin getSigninByUseridAndDate(@Param("userid")String userid,@Param("businessdate")String businessdate);

	/**
	 * 更新签到信息
	 * @param signin
	 * @return
	 * @author chenwei 
	 * @date 2014年9月19日
	 */
	public int updateSignin(Signin signin);

	/**
	 * 获取用户的考勤信息
	 * @param begindate
	 * @param enddate
	 * @return
	 * @author chenwei 
	 * @date 2014年9月26日
	 */
	public List showSigninList(@Param("begindate")String begindate,@Param("enddate")String enddate,@Param("userid")String userid);

    /**
     * 删除签到
     * @param id
     * @return
     * @author limin
     * @date 2015-03-12
     */
    public int deleteSignin(String id);
}

