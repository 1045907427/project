/**
 * @(#)IEmailService.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-2 zhanghonghui 创建版本
 */
package com.hd.agent.message.service;

import java.util.List;
import java.util.Map;

import com.hd.agent.common.util.PageData;
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
public interface IEmailService {
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
	public List showEmailContentList(Map queryMap) throws Exception;
	/**
	 * 发送邮件
	 * @param emailContent
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-4
	 */
	public boolean addEmail(EmailContent emailContent) throws Exception;
	/**
	 * 添加邮件<br/>
	 * map中对象<br/>	 
	 * 
	 * delAttachIdarrs：要删除附件编号字符串，格式：1,2,3<br/>
	 * sysUser: 用户对象 <br/>
	 * authList : 权限列表<br/>
	 * @param emailContent 邮件内容
	 * @return map 参数对象
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-4
	 */
	public boolean addEmail(EmailContent emailContent,Map map) throws Exception;
	/**
	 * 添加邮件内容
	 * @param emailContent
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public boolean addEmailContent(EmailContent emailContent) throws Exception;
	/**
	 * 更新邮件内容
	 * @param emailContent
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public boolean updateEmailContent(EmailContent emailContent) throws Exception;
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
	public boolean updateEmailContentBy(Map queryMap) throws Exception;
	/**
	 * 删除邮件内容
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public boolean deleteEmailContent(String id) throws Exception;
	/**
	 * 显示邮件内容
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public EmailContent showEmailContent(String id) throws Exception;
	
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
	public EmailContent showEmailContentBy(Map map) throws Exception;
	
	/**
	 *------------------------------------------------------
	 *	邮件接收人
	 *------------------------------------------------------ 
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
	public List showEmailReceiveList(Map queryMap) throws Exception;
	
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
	public int getEmailReceiveCountBy(Map queryMap) throws Exception;
	/**
	 * 添加邮件接收人
	 * @param emailReceive
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public boolean addEmailReceive(EmailReceive emailReceive) throws Exception;
	
	/**
	 * 更新邮件接收人
	 * @param emailReceive
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public boolean updateEmailReceive(EmailReceive emailReceive) throws Exception;
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
	public boolean updateEmailReceiveBy(Map queryMap) throws Exception;
	/**
	 * 删除邮件接收人
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public boolean deleteEmailReceive(String id) throws Exception;
	/**
	 * 显示邮件接收人
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-22
	 */
	public EmailReceive showEmailReceive(String id) throws Exception;
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
	public EmailReceive showEmailReceiveBy(Map map) throws Exception;
	
	/**
	 * ------------------------------------------------
	 * 邮件收发
	 * ------------------------------------------------
	 */
	/**
	 * 邮件接收分页列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-7
	 */
	public PageData showEmailReceivePageList(PageMap pageMap) throws Exception;
	/**
	 * 邮件发送分页列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-7
	 */
	public PageData showEmailSendPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 邮件废纸篓分页列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public PageData showEmailDropPageList(PageMap pageMap) throws Exception;
	
	/**
	 * 显示接收邮件用户阅读情况 <br/>
	 * pageMap中参数：<br/>
	 * emailid: 邮件编号<br/>
	 * recvuserid: 接收人编号 <br/>
	 * delflag: 删除标志<br/>
	 * notdelflag : 不等该值 <br/>
	 * recvuseridarrs : 接收用户编号组字符串，格式1,2,3,4<br/>
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public PageData showEmailUserReadPageList(PageMap pageMap) throws Exception;
	/**
	 * 接收邮件阅读状态统计
	 * map 中参数：<br/>
	 * emailid: 邮件编号<br/>
	 * recvuserid: 接收人编号 <br/>
	 * delflag: 删除标志，1未删除，2进入废纸喽，3发件删除，0删除<br/>
	 * viewflag ： 阅读标志,1未阅读，0阅读<br/> 
	 * notdelflag : 不等该值 <br/>
	 * recvuseridarrs : 接收用户编号组字符串，格式1,2,3,4<br/>
	 * @param map
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public int getEmailUserReadCount(PageMap pageMap) throws Exception;
	
	/**
	 * ------------------------------------------------
	 * 邮箱
	 * ------------------------------------------------
	 */
	/**
	 * 添加邮箱
	 * @param emailBox
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public boolean addEmailBox(EmailBox emailBox) throws Exception;
	/**
	 * 更新邮箱
	 * @param emailBox
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public boolean updateEmailBox(EmailBox emailBox) throws Exception;
	
	/**
	 * 删除邮箱
	 * @param id
	 * @param adduserid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public boolean deleteEmailBox(String id,String adduserid) throws Exception;
	
	/**
	 * 显示信箱
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public EmailBox showEmailBox(String id) throws Exception;
	
	/**
	 * 根据用户编号显示信箱
	 * @param adduserid
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public List showEmailBoxList(String adduserid) throws Exception;
	
	/**
	 * 邮箱分页列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-16
	 */
	public PageData showEmailBoxPageList(PageMap pageMap) throws Exception;
	
	/**
	 * ------------------------------------------------
	 * 外部邮箱设置
	 * ------------------------------------------------
	 */
	/**
	 * 添加外部邮件设置
	 * @param webMailConfig
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-5
	 */
	public boolean addWebMailConfig(WebMailConfig webMailConfig) throws Exception;
	/**
	 * 更新外部邮件设置
	 * @param webMailConfig
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-5
	 */
	public boolean updateWebMailConfig(WebMailConfig webMailConfig) throws Exception;
	/**
	 * 删除外部邮件设置
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-5
	 */
	public boolean deleteWebMailConfig(String id) throws Exception;
	/**
	 * 显示外部邮件设置
	 * @param id
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-5
	 */
	public WebMailConfig showWebMailConfig(String id) throws Exception;
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
	public List showWebMailConfigList(Map queryMap) throws Exception;
	/**
	 * 分页显示数据
	 * 其中分页参数pageMap中showmailpasscol=1，显示邮件密码
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-6
	 */
	public PageData showWebMailConfigPageList(PageMap pageMap) throws Exception;
	/**
	 * 只显示发送邮件内容列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月14日
	 */
	public PageData showEmailContentSimplePageList(PageMap pageMap) throws Exception;
	/**
	 * 邮箱条数合计
	 * @param queryMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2015年7月22日
	 */
	public Map getMailItemCountForPhone(Map queryMap) throws Exception;
}

