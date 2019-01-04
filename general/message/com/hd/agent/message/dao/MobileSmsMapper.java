/**
 * @(#)MobileSmsMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-3-6 zhanghonghui 创建版本
 */
package com.hd.agent.message.dao;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.MobileSms;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface MobileSmsMapper {
	/**
	 * 添加手机短信
	 * @param smsContent
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-6
	 */
	public int insertMobileSms(MobileSms mobileSms);
	/**
	 * 更新手机短信
	 * @param smsContent
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-6
	 */
	public int updateMobileSms(MobileSms mobileSms);
	/**
	 * 更新手机短信<br/>
	 * map中参数sendtime<br/>
	 * 可更新参数：<br/>
	 * sendtime : 发送时间<br/>
	 * sendflag : 发送状态: 1未发送，2发送失败，0已经发送<br/>
	 * smstype : 短信性质: 1内部，2外发<br/>
	 * delflag ： 删除标志：1未删除，0已删除<br/>
	 * deltime : 删除时间<br/>
	 * sendnum : 发送次数<br/>
	 * dealtime : 处理时间<br/>
	 * 条件参数：<br/>
	 * id : 编号<br/>
	 * adduserid : 添加用户编号<br/>
	 * recvuserid : 接收人编号 <br/>
	 * wdelflag : 删除标志<br/>
	 * wsendflag: 发送状态<br/>
	 * wnotsendflag: 发送状态<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-7
	 */
	public int updateMobileSmsBy(Map map);
	/**
	 * 获取手机短信已经发送分页列表 <br/>
	 * pageMap参数：<br/>
	 * adduserid : 不为空时，显示与发信息人相关数据<br/>
	 * recvuserid : 不为空时，显示与收信息相关数据<br/>
	 * stopqueryflag: 设置查询返回空数据<br/>
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-9
	 */
	public List getSendMobileSmsPageList(PageMap pageMap);
	/**
	 * 手机短信已经发送统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-9
	 */
	public int getSendMobileSmsCount(PageMap pageMap);

	/**
	 * 获取手机短信已接收分页列表 <br/>
	 * pageMap参数：<br/>
	 * recvuserid : 不为空时，显示与收信息相关数据<br/>
	 * stopqueryflag: 设置查询返回空数据<br/>
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-9
	 */
	public List getReceiveMobileSmsPageList(PageMap pageMap);
	/**
	 * 手机短信已接收统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-9
	 */
	public int getReceiveMobileSmsCount(PageMap pageMap);
	/**
	 * 获取手机短信信息列表<br/>
	 * map中的参数：<br/>
	 * adduserid ： 添加用户编号<br/>
	 * recvuserid : 接收用户编号 <br/>
	 * delflag : 删除标志，1未删除，0已经删除<br/>
	 * notdelflag : 不等于该删除标志<br/>
	 * sendflag : 发送标志,1未发送，2发送失败，0已经发送<br/>
	 * notsendflag : 不等于该发送标志<br/>
	 * serialno : 短信发送序列<br/>
	 * mobile : 手机号码<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public List getMobileSmsListBy(Map map);
	/**
	 * 获取serialno mysql为 uuid_short
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-11
	 */
	public String getSerialno();
	
}

