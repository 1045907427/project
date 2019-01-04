/**
 * @(#)EmailServiceImpl.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-2-2 zhanghonghui 创建版本
 */
package com.hd.agent.message.service.impl;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.service.IAttachFileService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.dao.EmailMapper;
import com.hd.agent.message.model.EmailBox;
import com.hd.agent.message.model.EmailContent;
import com.hd.agent.message.model.EmailReceive;
import com.hd.agent.message.model.WebMailConfig;
import com.hd.agent.message.service.IEmailService;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class EmailServiceImpl extends BaseServiceImpl implements IEmailService {
	/**
	 * 邮件数据操作层
	 */
	private EmailMapper emailMapper;

	/**
	 * 附件
	 */
	private IAttachFileService attachFileService;

	public EmailMapper getEmailMapper() {
		return emailMapper;
	}

	public void setEmailMapper(EmailMapper emailMapper) {
		this.emailMapper = emailMapper;
	}
	public IAttachFileService getAttachFileService() {
		return attachFileService;
	}

	public void setAttachFileService(IAttachFileService attachFileService) {
		this.attachFileService = attachFileService;
	}

	/**
	 * 获取邮件内容列表<br/>
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-23
	 */
	public List showEmailContentList(Map queryMap) throws Exception{
		List list=emailMapper.showEmailContentList(queryMap);
		return list;
	}

	/**
	 * 添加邮件
	 * 
	 * @param emailContent
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-4
	 */
	public boolean addEmail(EmailContent emailContent) throws Exception {
		boolean isok = false;
		if (StringUtils.isNotEmpty(emailContent.getReceiveuser())) {
			emailContent.setMailtype("0"); // 内部邮件
		} else {
			emailContent.setMailtype("1"); // 外部邮件
		}
		if (StringUtils.isNotEmpty(emailContent.getFromwebmail())
				|| StringUtils.isNotEmpty(emailContent.getTowebmail())) {
			emailContent.setWebmailflag("1"); // 外部邮件等待发送
		} else {
			emailContent.setWebmailflag("0");
		}
		emailContent.setDelflag("1"); // 未删除
		if(emailContent.getId()!=null && emailContent.getId()>0){
			// 更新邮箱
			
			isok = emailMapper.updateEmailContent(emailContent)>0;
		}else{
			// 添加邮箱
			isok = emailMapper.insertEmailContent(emailContent)>0;
		}
		
		if (isok) {
			if(StringUtils.isNotEmpty(emailContent.getAttach())){
			//添加附件权限
				//attachFileService.updateAttachFileAuth(emailContent.getAttach(),emailContent.getReceiveuser(),null,null);
			}
			
			// 是否要发送
			if ("1".equals(emailContent.getSendflag())) {
                List<String> userList = CommonUtils.stringsToList(",", emailContent.getReceiveuser(), emailContent.getCopytouser(), emailContent.getSecrettouser());
                if (userList.size() > 0) {
                    EmailReceive emailReceive = new EmailReceive();
                    emailReceive.setEmailid(emailContent.getId());
                    emailReceive.setViewflag("1"); // 默认未阅读
                    emailReceive.setDelflag("1"); // 默认未删除
                    emailReceive.setReceiptflag(emailContent.getIsreceipt()); // 不需要收条
                    emailReceive.setBoxid(0); // 默认收件邮箱
                    emailReceive.setSenduserid(emailContent.getAdduserid());    //添加发送人，方便查询
                    //接收时间
                    if (emailContent.getAddtime() != null) {
                        emailReceive.setRecvtime(emailContent.getAddtime());
                    } else {
                        emailReceive.setRecvtime(new Date());
                    }
                    for (String item : userList) {
                        emailReceive.setRecvuserid(item); // 接收用户编号
                        emailMapper.insertEmailReceive(emailReceive); // 添加接收人
                    }
                }
            }

		}
        return isok;
	}
	
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
	@Override
	public boolean addEmail(EmailContent emailContent,Map map) throws Exception {
		boolean isok=addEmail(emailContent);
//		if(isok){
//			if(map.containsKey("delAttachIdarrs") && null!=map.get("delAttachIdarrs") && !"".equals(map.get("delAttachIdarrs").toString())){
//				map.put("idarrs", map.get("delAttachIdarrs"));
//				map.remove("delAttachIdarrs");
//				attachFileService.deleteAttachWithPhysical(map);
//			}
//		}
		return isok;
	}

	/**
	 * 添加邮件内容
	 * 
	 * @param emailContent
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-2
	 */
	public boolean addEmailContent(EmailContent emailContent) throws Exception {
		if (StringUtils.isNotEmpty(emailContent.getReceiveuser())) {
			emailContent.setMailtype("0"); // 内部邮件
		} else {
			emailContent.setMailtype("1"); // 外部邮件
		}
		if (StringUtils.isNotEmpty(emailContent.getFromwebmail())
				|| !"".equals(emailContent.getTowebmail())) {
			emailContent.setWebmailflag("1"); // 外部邮件等待发送
		} else {
			emailContent.setWebmailflag("0");
		}
		emailContent.setDelflag("1"); // 未删除
		int irows = emailMapper.insertEmailContent(emailContent);
		return irows > 0;
	}

	/**
	 * 更新邮件内容
	 * 
	 * @param emailContent
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-2
	 */
    public boolean updateEmailContent(EmailContent emailContent) throws Exception {
		if (StringUtils.isNotEmpty(emailContent.getReceiveuser())) {
			emailContent.setMailtype("0"); // 内部邮件
		} else {
			emailContent.setMailtype("1"); // 外部邮件
		}
		if (StringUtils.isNotEmpty(emailContent.getFromwebmail())
				|| !"".equals(emailContent.getTowebmail())) {
			emailContent.setWebmailflag("1"); // 外部邮件等待发送
		} else {
			emailContent.setWebmailflag("0");
		}
		emailContent.setDelflag("1"); // 未删除
		int irows = emailMapper.updateEmailContent(emailContent);
		return irows > 0;
	}
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
	public boolean updateEmailContentBy(Map queryMap) throws Exception{
		int irows =emailMapper.updateEmailContentBy(queryMap);
		return irows>0;
	}

	/**
	 * 删除邮件内容
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-2
	 */
	public boolean deleteEmailContent(String id) throws Exception {
		int irows = emailMapper.deleteEmailContent(id);
		return irows > 0;
	}

	/**
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-2
	 */
	public EmailContent showEmailContent(String id) throws Exception {
		EmailContent emailContent = emailMapper.showEmailContent(id);
		return emailContent;
	}
	
	/**
	 * 显示邮件内容
	 * @param map
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-2
	 */
	public EmailContent showEmailContentBy(Map map) throws Exception{
		EmailContent emailContent=emailMapper.showEmailContentBy(map);
		return emailContent;
	}

	/**
	 *------------------------------------------------------ 邮件接收人
	 * ------------------------------------------------------
	 */
	/**
	 * 获取接件信息列表<br/>
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-23
	 */
	public List showEmailReceiveList(Map queryMap) throws Exception{
		List list=emailMapper.showEmailReceiveList(queryMap);
		return list;
	}
	/**
	 * 获取接件信息统计<br/>
	 * @param queryMap
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-2-23
	 */
	public int getEmailReceiveCountBy(Map queryMap) throws Exception{
		int irows=emailMapper.getEmailReceiveCountBy(queryMap);
		return irows;
	}
	/**
	 * 添加邮件接收人
	 * 
	 * @param emailReceive
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-2
	 */
	public boolean addEmailReceive(EmailReceive emailReceive) throws Exception {
		int irows = emailMapper.insertEmailReceive(emailReceive);
		return irows > 0;
	}

	/**
	 * 更新邮件接收人
	 * 
	 * @param emailReceive
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-2
	 */
	public boolean updateEmailReceive(EmailReceive emailReceive)
			throws Exception {
		int irows = emailMapper.updateEmailReceive(emailReceive);
		return irows > 0;
	}

	/**
	 * 更新邮件接收人<br/>
	 * 
	 * @param queryMap
	 * @return
	 * @author zhanghonghui
	 * @date 2013-2-2
	 */
	public boolean updateEmailReceiveBy(Map queryMap) throws Exception {
		int irows = emailMapper.updateEmailReceiveBy(queryMap);
		return irows > 0;
	}

	/**
	 * 删除邮件接收人
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-2
	 */
	public boolean deleteEmailReceive(String id) throws Exception {
		int irows = emailMapper.deleteEmailReceive(id);
		return irows > 0;
	}
	/**
	 * 显示邮件接收人
	 * @param id
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-22
	 */
	public EmailReceive showEmailReceive(String id) throws Exception{
		EmailReceive emailReceive=emailMapper.showEmailReceive(id);
		return emailReceive;
	}
	
	/**
	 * 显示邮件接收人
	 * @param map
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-2-22
	 */
	public EmailReceive showEmailReceiveBy(Map map) throws Exception{
		EmailReceive emailReceive=emailMapper.showEmailReceiveBy(map);
		return emailReceive;
		
	}

	/**
	 * ------------------------------------------------ 
	 * 邮件收发
	 * ------------------------------------------------
	 */
	/**
	 * 邮件接收分页列表
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-7
	 */
	public PageData showEmailReceivePageList(PageMap pageMap) throws Exception {
		/*
		String emcDataAuthSQL = getDataAccessRule("t_msg_emailcontent","emc");
		Map condition=pageMap.getCondition();
		condition.put("emcDataAuthSQL",emcDataAuthSQL);
		*/
        pageMap.setQuerySqlOnlyAlias("emlc");
        PageData pageData = new PageData(emailMapper
                .getEmailReceiveCount(pageMap), emailMapper
                .getEmailReceivePageList(pageMap), pageMap);
        return pageData;
    }

	/**
	 * 邮件发送分页列表
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-7
	 */
	public PageData showEmailSendPageList(PageMap pageMap) throws Exception {
		/*
        String emcDataAuthSQL = getDataAccessRule("t_msg_emailcontent","emc");
		Map condition=pageMap.getCondition();
		condition.put("emcDataAuthSQL",emcDataAuthSQL);
		*/

        Map condition=pageMap.getCondition();
        String queryreceiver=(String)condition.get("queryreceiver");
        if(null!=queryreceiver && !"".equals(queryreceiver.trim())){
            String[] queryArr=StringUtils.split(queryreceiver.trim(),",");
            condition.put("queryreceiverArr",queryArr);
        }
        pageMap.setQuerySqlOnlyAlias("emlc");
        int itotal=emailMapper.getEmailSendCount(pageMap);
        List<EmailContent> list=emailMapper.getEmailSendPageList(pageMap);

        for(EmailContent item:list){
            if(StringUtils.isNotEmpty(item.getAdduserid())){
                SysUser sysUser=getSysUserById(item.getAdduserid());
                item.setAddusername(sysUser.getName());
            }
            List<EmailReceive> receiveList=new ArrayList<EmailReceive>();
            if(StringUtils.isNotEmpty(item.getReceiveuser())){
                String[] receiverArr=StringUtils.split(item.getReceiveuser(),",");
                item.setReceivernum(receiverArr.length);
                if(receiverArr.length==1) {
                    SysUser receiverUser = getSysUserById(receiverArr[0]);
                    if(null!=receiverUser){
                        item.setRecvusername(receiverUser.getName());
                    }
                    Map recvMap=new HashMap();
                    recvMap.put("emailid",item.getId());
                    receiveList=emailMapper.showEmailReceiveList(recvMap);
                    item.setReceivernum(1);
                }else{
                    Map recvCondMap=new HashMap();
                    recvCondMap.put("emailid",item.getId());
                    int recvcount=emailMapper.getEmailReceiveCountBy(recvCondMap);
                    item.setReceivernum(recvcount);
                }
            }
            item.setEmailReceiveList(receiveList);
        }

        PageData pageData = new PageData(itotal, list, pageMap);
        return pageData;
	}
	
	/**
	 * 邮件废纸篓分页列表
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public PageData showEmailDropPageList(PageMap pageMap) throws Exception{
        Map condition=pageMap.getCondition();
        String queryreceiver=(String)condition.get("queryreceiver");
        if(null!=queryreceiver && !"".equals(queryreceiver.trim())){
            String[] queryArr=StringUtils.split(queryreceiver.trim(),",");
            condition.put("queryreceiverArr",queryArr);
        }
        pageMap.setQuerySqlOnlyAlias("emlc");
		int rows=emailMapper.getEmailDropCount(pageMap);
		List list=emailMapper.getEmailDropPageList(pageMap);
		PageData pageData = new PageData(
				emailMapper.getEmailDropCount(pageMap), emailMapper
						.getEmailDropPageList(pageMap), pageMap);
		return pageData;		
	}
	/**
	 * 显示接收邮件用户阅读情况
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui 
	 * @date 2013-3-1
	 */
	public PageData showEmailUserReadPageList(PageMap pageMap) throws Exception{
		PageData pageData = new PageData(
				emailMapper.getEmailUserReadCount(pageMap), emailMapper
						.getEmailUserReadPageList(pageMap), pageMap);
		return pageData;			
	}
	
	/**
	 * 接收邮件阅读状态统计
	 * map 中参数：<br/>
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
	public int getEmailUserReadCount(PageMap pageMap) throws Exception{
		return emailMapper.getEmailUserReadCount(pageMap);
	}
	
	/**
	 * 邮件内容分页列表
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-7
	 */
	public PageData showEmailContentSimplePageList(PageMap pageMap) throws Exception {
		Map condition=pageMap.getCondition();
		/*
		String emcDataAuthSQL = getDataAccessRule("t_msg_emailcontent","emc");
		condition.put("emcDataAuthSQL",emcDataAuthSQL);
		*/
		boolean isContentFilterHtmlShort=false;
		if(condition.containsKey("contentFilterHtmlShort") && "true".equals(condition.get("contentFilterHtmlShort"))){
			isContentFilterHtmlShort=true;
		}
		int iCount=emailMapper.getEmailContentSimplePageCount(pageMap);
		List<EmailContent> list=emailMapper.getEmailContentSimplePageList(pageMap);
		for(EmailContent item : list){
			if(null!=item){
				if(StringUtils.isNotEmpty(item.getReceiveuser())){
					if(item.getReceiveuser().indexOf(',')==-1){
						SysUser sysUser=getSysUserById(item.getReceiveuser());
						if(null!=sysUser){
							item.setRecvusername(sysUser.getName());
						}
					}else if(item.getReceiveuser().indexOf(',')>=0){
						item.setRecvusername("群发");
					}
				}
				if(isContentFilterHtmlShort && StringUtils.isNotEmpty(item.getContent())){
					String txtcontent=CommonUtils.htmlFilter(item.getContent());
					if(txtcontent.length()>300){
						item.setKeyword(txtcontent.substring(0, 300));
					}else{
						item.setKeyword(txtcontent);
					}
				}
			}
		}
		PageData pageData = new PageData(iCount,list , pageMap);
		return pageData;
	}

	/**
	 * ------------------------------------------------ 邮箱
	 * ------------------------------------------------
	 */
	/**
	 * 添加邮箱
	 * 
	 * @param emailBox
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-2
	 */
	public boolean addEmailBox(EmailBox emailBox) throws Exception {
		int irows = emailMapper.insertEmailBox(emailBox);
		return irows > 0;
	}

	/**
	 * 更新邮箱
	 * 
	 * @param emailBox
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-2
	 */
	public boolean updateEmailBox(EmailBox emailBox) throws Exception {
		int irows = emailMapper.updateEmailBox(emailBox);
		return irows > 0;
	}

	/**
	 * 删除邮箱
	 * 
	 * @param id
	 * @param adduserid
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-2
	 */
	public boolean deleteEmailBox(String id, String adduserid) throws Exception {
		Map queryMap = new HashMap();
		//收件箱
		queryMap.put("boxid", "0");
		//接收人
		queryMap.put("wrecvuserid", adduserid);
		//要删除的邮件箱
		queryMap.put("wboxid", id);
		int irows = emailMapper.deleteEmailBox(id);
		if (irows > 0) {
			emailMapper.updateEmailReceiveBy(queryMap);
		}
		return irows > 0;
	}

	/**
	 * 显示信箱
	 * 
	 * @param id
	 * @return
	 * @author zhanghonghui
	 * @date 2013-2-16
	 */
	public EmailBox showEmailBox(String id) throws Exception {
		EmailBox emailBox = emailMapper.showEmailBox(id);
		return emailBox;
	}

	/**
	 * 根据用户编号显示信箱
	 * 
	 * @param adduserid
	 * @return
	 * @author zhanghonghui
	 * @date 2013-2-16
	 */
	public List showEmailBoxList(String adduserid) throws Exception {
		List list = emailMapper.showEmailBoxList(adduserid);
		return list;
	}

	/**
	 * 邮箱分页列表
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-16
	 */
	public PageData showEmailBoxPageList(PageMap pageMap) throws Exception {
		PageData pageData = new PageData(emailMapper.getEmailBoxCount(pageMap),
				emailMapper.getEmailBoxPageList(pageMap), pageMap);
		return pageData;
	}

	/**
	 * ------------------------------------------------ 外部邮箱设置
	 * ------------------------------------------------
	 */
	/**
	 * 添加外部邮件设置
	 * 
	 * @param webMailConfig
	 * @return
	 * @author zhanghonghui
	 * @date 2013-2-5
	 */
	public boolean addWebMailConfig(WebMailConfig webMailConfig)
			throws Exception {
		int irows = emailMapper.insertWebMailConfig(webMailConfig);
		return irows > 0;
	}

	/**
	 * 更新外部邮件设置
	 * 
	 * @param webMailConfig
	 * @return
	 * @author zhanghonghui
	 * @date 2013-2-5
	 */
	public boolean updateWebMailConfig(WebMailConfig webMailConfig)
			throws Exception {
		int irows = emailMapper.updateWebMailConfig(webMailConfig);
		return irows > 0;
	}

	/**
	 * 删除外部邮件设置
	 * 
	 * @param id
	 * @return
	 * @author zhanghonghui
	 * @date 2013-2-5
	 */
	public boolean deleteWebMailConfig(String id) throws Exception {
		int irows = emailMapper.deleteWebMailConfig(id);
		return irows > 0;
	}

	/**
	 * 显示外部邮件设置
	 * 
	 * @param id
	 * @return
	 * @author zhanghonghui
	 * @date 2013-2-5
	 */
	public WebMailConfig showWebMailConfig(String id) throws Exception {
		WebMailConfig webMailConfig = emailMapper.showWebMailConfig(id);
		return webMailConfig;
	}

	/**
	 * 显示外部邮件设置<br/>
	 * 
	 * @param queryMap
	 * @return
	 * @author zhanghonghui
	 * @date 2013-2-5
	 */
	public List showWebMailConfigList(Map queryMap) throws Exception {
		List list = emailMapper.showWebMailConfigList(queryMap);
		return list;
	}

	/**
	 * 分页显示数据 其中分页参数pageMap中showmailpass=1，显示邮件密码
	 * 
	 * @param pageMap
	 * @return
	 * @throws Exception
	 * @author zhanghonghui
	 * @date 2013-2-6
	 */

	public PageData showWebMailConfigPageList(PageMap pageMap) throws Exception {
		PageData pageData = new PageData(emailMapper
				.getWebMailConfigCount(pageMap), emailMapper
				.getWebMailConfigPageList(pageMap), pageMap);
		return pageData;
	}
	


	@Override
	public Map getMailItemCountForPhone(Map queryMap) throws Exception{
		Map resultMap=new HashMap();
		if(null==queryMap || queryMap.size()==0){
			resultMap.put("sendcount", 0);
			resultMap.put("recvcount", 0);
			resultMap.put("dropcount", 0);
			return resultMap;
		}
		resultMap=emailMapper.getMailItemCountForPhone(queryMap);
		return resultMap;
	}
}
