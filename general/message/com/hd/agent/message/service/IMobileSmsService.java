/**
 * @(#)IMobileSmsService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-3-7 zhanghonghui 创建版本
 */
package com.hd.agent.message.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.MobileSms;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface IMobileSmsService {
	/**
	 * 添加手机短信
	 * @param mobileSms
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-7
	 */
	public boolean addMobileSms(MobileSms mobileSms) throws Exception;
	/**
	 * 添加多个手机短信
	 * @param mobileSms
	 * @return
	 * @throws Exception
	 */
	public boolean addMobileSmsList(List<MobileSms> list) throws Exception;
	
	/**
	 * 添加手机短信
	 * @param mobileSms
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-7
	 */
	public boolean updateMobileSms(MobileSms mobileSms) throws Exception;
	
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
	 * isdataauth : 是否有数据权限判断，0为不需要，其他值为需要，默认为需要。<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-7
	 */
	public boolean updateMobileSmsBy(Map map) throws Exception;
	/**
	 * 获取手机短信发送分页列表数据 <br/>
	 * pageMap参数：<br/>
	 * adduserid : 不为空时，显示与发信息人相关数据<br/>
	 * recvuserid : 不为空时，显示与收信息相关数据<br/>
	 * stopqueryflag: 设置查询返回空数据<br/>
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-9
	 */
	public PageData showSendMobileSmsPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 获取手机短信接收分页列表数据 <br/>
	 * pageMap参数：<br/>
	 * adduserid : 不为空时，显示与发信息人相关数据<br/>
	 * recvuserid : 不为空时，显示与收信息相关数据<br/>
	 * stopqueryflag: 设置查询返回空数据<br/>
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-9
	 */
	public PageData showReceiveMobileSmsPageList(PageMap pageMap) throws Exception;
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
	public List getMobileSmsListBy(Map map) throws Exception;
	/**
	 * 获取serialno mysql为 uuid_short
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-11
	 */
	public String getSerialno() throws Exception;

    /**
     * 往手机发送信息
     * @param userid            用户编号
     * @param type              1 发送公告短信等内容包含url地址 2上传定位 3 强制启用程序
     * @param title             标题
     * @param msg               内容
     * @param url               url地址
     * @return
     * @throws Exception
     */
    public boolean sendPhoneMsg(String userid,String type,String title,String msg,String url) throws Exception;
}

