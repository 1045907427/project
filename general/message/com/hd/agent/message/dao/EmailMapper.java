/**
 * @(#)EmailMapper.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-2 zhanghonghui 创建版本
 */
package com.hd.agent.message.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.EmailBox;
import com.hd.agent.message.model.EmailContent;
import com.hd.agent.message.model.EmailReceive;
import com.hd.agent.message.model.WebMailConfig;

/**
 * 
 * 
 * @author zhanghonghui
 */
public interface EmailMapper {
	/**
	 * 获取邮件内容列表<br/>
	 * queryMap中参数：<br/>
	 * 显示列参数：<br/>
	 * showcontentcol : 显示内容列<br/>
	 * showreceivecol : 显示接收人列:接收人，抄送人，密送人<br/>
	 * showtowebmailcol : 显示外部邮件接收人列<br/>
	 * 条件参数：<br/>
	 * adduserid : 发布者编号<br/>
	 * title : 标题<br/>
	 * ismsg : 内部短信提醒,1提醒，0不提醒<br/>
	 * isreceipt : 收条，1回收条，0不回收条 <br/>
	 * importantflag : 重要标志:0一般邮件，1重要邮件，2非常重要<br/>
	 * sendflag : 发送标志:1发送成功，0未发送(草稿)<br/>
	 * webmailflag : 外部邮件发送标志：1等待发送，2发送成功，3发送失败，0默认状态<br/>
	 * mailtype : 邮件类型：0内部邮件,1内部和外部邮件,2外部邮件<br/>
	 * delflag : 删除标志：1未删除，2进入废纸喽，0删除<br/>
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-23
	 */
	public List<EmailContent> showEmailContentList(Map queryMap);
	/**
	 * 添加邮件内容
	 * @param emailContent
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public int insertEmailContent(EmailContent emailContent);
	/**
	 * 更新邮件内容
	 * @param emailContent
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public int updateEmailContent(EmailContent emailContent);
	/**
	 * 更新删除标志 <br/>
 	 * queryMap中参数：<br/>
	 * 更新参数：<br/>
	 * delflag: 删除标志<br/>
	 * deltime:	删除时间<br/>
	 * 条件参数：<br/>
	 * id:	编号<br/>
	 * adduserid: 发布者<br/>
	 * wdelflag:  删除标志<br/>
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public int updateEmailContentBy(Map queryMap);
	/**
	 * 删除邮件内容
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public int deleteEmailContent(@Param("id")String id);
	/**
	 * 获取邮件内容
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public EmailContent showEmailContent(@Param("id")String id);
	/**
	 * 显示邮件内容
	 * 更新删除标志 <br/>
 	 * map中参数：<br/>
 	 * certaincols: 指定取出的列名<br/>
	 * id:	编号<br/>
	 * adduserid: 发布者<br/>
	 * wdelflag:  删除标志<br/>
	 * cureceiverid : 当前接收人<br/>
	 * @param map
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public EmailContent showEmailContentBy(Map map);
	/*
	 * --------------------------------------------
	 * 邮件接收人
	 * --------------------------------------------
	 */
	/**
	 * 获取接件信息列表<br/>
	 * queryMap中条件参数：<br/>
	 * emailid : 邮件编号<br/>
	 * recvuserid : 接收人编号 <br/>
	 * viewflag : 查看标志<br/>
	 * delflag : 删除标志<br/>
	 * receiptflag : 收条标志<br/>
	 * signflag : 星号标志<br/>
	 * boxid : 邮箱编号<br/>
	 * notdelflag : 删除标志（不等于此标志值）<br/>
	 * idarr : id编号列表<br/>
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-23
	 */
	public List<EmailReceive> showEmailReceiveList(Map queryMap);
	/**
	 * 获取接件信息统计<br/>
	 * queryMap中条件参数：<br/>
	 * emailid : 邮件编号<br/>
	 * recvuserid : 接收人编号 <br/>
	 * viewflag : 查看标志<br/>
	 * notviewflag : 不等于查看标志<br/>
	 * delflag : 删除标志<br/>
	 * receiptflag : 收条标志<br/>
	 * signflag : 星号标志<br/>
	 * boxid : 邮箱编号<br/>
	 * notdelflag : 删除标志（不等于此标志值）<br/>
	 * idarr : id编号列表<br/>
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-23
	 */
	public int getEmailReceiveCountBy(Map queryMap);
	/**
	 * 添加邮件接收人
	 * @param emailReceive
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public int insertEmailReceive(EmailReceive emailReceive);
	/**
	 * 更新邮件接收人
	 * @param emailReceive
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public int updateEmailReceive(EmailReceive emailReceive);
	/**
	 * 更新邮件接收人<br/>
	 * queryMap中参数：<br/>
	 * 更新参数：<br/>
	 * recvtime:邮件编号<br/>
	 * viewflag:阅读标志<br/>
	 * viewtime:阅读时间<br/>
	 * delfalg:删除标志<br/>
	 * deltime:删除时间<br/>
	 * receiptflag:收条标志<br/>
	 * signflag:星标志<br/>
	 * boxid:邮箱编号<br/>
	 * 条件参数：<br/>
	 * id:编号<br/>
	 * widarrs:多编号字符串，格式：1,2,3<br/>
	 * wemailid:邮件编号<br/>
	 * wemlidarrs:多邮件编号字符串，格式：1,2,3<br/>
	 * wrecvuserid:接收用户编号<br/>
	 * wsenduserid:发送用户编号<br/>
	 * wdelfalg:删除标志<br/>
	 * wnotdelflag: 不等于该删除标志的值<br/>
	 * wboxid:邮箱标志<br/>
	 * wviewflag:阅读标志<br/>
	 * wnotviewflag : 不等于该阅读标志的值<br/>
	 * wreceiptflag : 收条标志,1未回复收条，0回复收条<br/>
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public int updateEmailReceiveBy(Map queryMap);
	/**
	 * 删除邮件接收人
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public int deleteEmailReceive(@Param("id")String id);
	/**
	 * 更新邮件接收人删除标志
	 * @param id
	 * @param delflag
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public int updateEmailReceiveDelflag(@Param("id")String id,@Param("delflag")String delflag);

	/**
	 * 显示邮件接收人
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-22
	 */
	public EmailReceive showEmailReceive(@Param("id")String id);
	/**
	 * 显示邮件接收人
	 * map中参数：<br/>
	 * id:编号<br/>
	 * emailid:邮件编号<br/>
	 * recvuserid:接收用户编号<br/>
	 * senduserid:发送用户编号<br/>
	 * delfalg:删除标志<br/>
	 * notdelflag: 不等于该删除标志的值<br/>
	 * boxid:邮箱标志<br/>
	 * viewflag:阅读标志<br/>
	 * notviewflag : 不等于该阅读标志的值<br/>
	 * receiptflag : 是否收条<br/>
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-22
	 */
	public EmailReceive showEmailReceiveBy(Map map);
	
	/*
	 * --------------------------------------------
	 * 邮件接收
	 * --------------------------------------------
	 */
	/**
	 * 邮件接收分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-7
	 */
	public List getEmailReceivePageList(PageMap pageMap);
	/**
	 * 邮件接收统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-7
	 */
	public int getEmailReceiveCount(PageMap pageMap);
	/**
	 * 邮件发送分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-7
	 */
	public List getEmailSendPageList(PageMap pageMap);
	/**
	 * 邮件发送统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-7
	 */
	public int getEmailSendCount(PageMap pageMap);
	
	/**
	 * 废纸篓中的邮件分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public List getEmailDropPageList(PageMap pageMap);
	/**
	 * 废纸篓查询统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public int getEmailDropCount(PageMap pageMap);
	/**
	 * 接收邮件阅读状态分页列表 <br/>
	 * pageMap中参数：<br/>
	 * emailid: 邮件编号<br/>
	 * recvuserid: 接收人编号 <br/>
	 * delflag: 删除标志<br/>
	 * notdelflag : 不等该值 <br/>
	 * recvuseridarrs : 接收用户编号组字符串，格式1,2,3,4<br/>
	 * @param pageMap
	 * @return List<EmailReceive>
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public List<EmailReceive> getEmailUserReadPageList(PageMap pageMap);
	/**
	 * 接收邮件阅读状态统计
	 * pageMap 中condition 参数：<br/>
	 * emailid: 邮件编号<br/>
	 * recvuserid: 接收人编号 <br/>
	 * delflag: 删除标志，1未删除，2进入废纸喽，3发件删除，0删除<br/>
	 * viewflag ： 阅读标志,1未阅读，0阅读<br/> 
	 * notdelflag : 不等该值 <br/>
	 * recvuseridarrs : 接收用户编号组字符串，格式1,2,3,4<br/>
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public int getEmailUserReadCount(PageMap pageMap);
	/*
	 * --------------------------------------------
	 * 邮件信箱
	 * --------------------------------------------
	 */
	
	/**
	 * 添加信箱
	 * @param emailBox
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public int insertEmailBox(EmailBox emailBox);
	/**
	 * 更新信箱
	 * @param emailBox
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public int updateEmailBox(EmailBox emailBox);
	/**
	 * 删除信箱
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public int deleteEmailBox(@Param("id")String id);
	
	/**
	 * 显示信箱
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public EmailBox showEmailBox(@Param("id")String id);
	
	/**
	 * 根据用户编号显示信箱
	 * @param adduserid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public List showEmailBoxList(@Param("adduserid")String adduserid);
	
	/**
	 * 邮件箱分页列表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public List getEmailBoxPageList(PageMap pageMap);
	/**
	 * 分页统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-6
	 */
	public int getEmailBoxCount(PageMap pageMap);
	
	/*
	 * --------------------------------------------
	 * 外部邮件设置
	 * --------------------------------------------
	 */
	/**
	 * 添加外部邮件设置
	 * @param webMailConfig
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-5
	 */
	public int insertWebMailConfig(WebMailConfig webMailConfig);
	/**
	 * 更新外部邮件设置
	 * @param webMailConfig
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-5
	 */
	public int updateWebMailConfig(WebMailConfig webMailConfig);
	/**
	 * 删除外部邮件设置
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-5
	 */
	public int deleteWebMailConfig(@Param("id")String id);
	/**
	 * 显示外部邮件设置
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-5
	 */
	public WebMailConfig showWebMailConfig(@Param("id")String id);
	/**
	 * 显示外部邮件设置<br/>
	 * queryMap参数：<br/>
	 * emailmh: 邮件地址like查询<br/>
	 * email: 邮件地址<br/>
	 * ispopssl:pop是否加密,1是，0否<br/>
	 * issmtpssl:smtp是否加密,1是，0否<br/>
	 * emailuser：邮件用户名<br/>
	 * issmtppass:smtp验证<br/>
	 * autorecvflag:自动接收标志，1是，0否<br/>
	 * isdefault:是否默认，1是，0否<br/>
	 * isrecvdel:是否接收后删除服务器邮件，1是，0否<br/>
	 * isrecvmsg:收取新邮件短信提醒，1提醒，0不提醒<br/>
	 * adduserid:添加用户编号<br/>
	 * emailuid:邮件的uid<br/>
	 * orderbysql:排序，例如isdefault desc,id desc<br/>
	 * showmailpass:是否显示密码字段,1显示，其他值不显示<br/>
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-5
	 */
	public List showWebMailConfigList(Map queryMap);
	/**
	 * 分页显示数据
	 * 其中分页参数pageMap中showmailpasscol=1，显示邮件密码
	 * @param pageMap
	 * @author zhanghonghui 
	 * @date 2013-2-6
	 */
	public List getWebMailConfigPageList(PageMap pageMap);
	/**
	 * 分页统计
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-6
	 */
	public int getWebMailConfigCount(PageMap pageMap);
	/**
	 * 手机端，统计邮件箱，已发送条件，已收未阅读条数，已经删除条数
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2015年7月14日
	 */
	public Map getMailItemCountForPhone(Map map);
	

	/**
	 * 邮件发送分页列表,只查询发送邮件表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-7
	 */
	public List<EmailContent> getEmailContentSimplePageList(PageMap pageMap);
	/**
	 * 邮件发送统计,只查询发送邮件表
	 * @param pageMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-7
	 */
	public int getEmailContentSimplePageCount(PageMap pageMap);
}

