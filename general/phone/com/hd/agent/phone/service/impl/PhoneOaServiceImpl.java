/**
 * @(#)PhoneOaServiceImpl.java
 *
 * @author zhengziyong
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * Dec 30, 2013 zhengziyong 创建版本
 */
package com.hd.agent.phone.service.impl;

import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.basefiles.service.impl.BaseFilesServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.hr.dao.SigninMapper;
import com.hd.agent.hr.model.Signin;
import com.hd.agent.message.dao.EmailMapper;
import com.hd.agent.message.dao.NoticeMapper;
import com.hd.agent.message.model.EmailReceive;
import com.hd.agent.message.model.MsgNotice;
import com.hd.agent.message.model.MsgNoticeread;
import com.hd.agent.phone.service.IPhoneOaService;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 
 * 手机OAservice
 * @author zhengziyong
 */
public class PhoneOaServiceImpl extends BaseFilesServiceImpl implements IPhoneOaService {
	
	private EmailMapper emailMapper;
	private NoticeMapper noticeMapper;
	private SigninMapper signinMapper;
	
	public EmailMapper getEmailMapper() {
		return emailMapper;
	}

	public void setEmailMapper(EmailMapper emailMapper) {
		this.emailMapper = emailMapper;
	}

	public NoticeMapper getNoticeMapper() {
		return noticeMapper;
	}

	public void setNoticeMapper(NoticeMapper noticeMapper) {
		this.noticeMapper = noticeMapper;
	}

	public SigninMapper getSigninMapper() {
		return signinMapper;
	}

	public void setSigninMapper(SigninMapper signinMapper) {
		this.signinMapper = signinMapper;
	}

	@Override
	public PageData getEmailData(PageMap pageMap) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int count = emailMapper.getEmailReceiveCount(pageMap);
		List<EmailReceive> list = emailMapper.getEmailReceivePageList(pageMap);
		List<Map> result = new ArrayList<Map>();
		for(EmailReceive email : list){
			Map map = new HashMap();
			map.put("id", email.getEmailContent().getId() );
			map.put("title", email.getEmailContent().getTitle());
			map.put("adduser", email.getEmailContent().getAddusername());
			map.put("addtime", dateFormat.format(email.getEmailContent().getAddtime()));
			result.add(map);
		}
		PageData pageData = new PageData(count, result, pageMap);
		return pageData;
	}

	@Override
	public MsgNotice getNoticeNoread(String userId) throws Exception {
		return null;
	}

	@Override
	public PageData getNoticeData(PageMap pageMap) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<MsgNotice> list = noticeMapper.getMsgNoticePageList(pageMap);
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		for(MsgNotice notice: list){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", notice.getId());
			map.put("title", notice.getTitle());
			map.put("intro", notice.getIntro());
			map.put("adduser", notice.getAddusername());
			map.put("addtime", dateFormat.format(notice.getAddtime()));
			result.add(map);
		}
		PageData pageData = new PageData(noticeMapper.getMsgNoticeCount(pageMap), result, pageMap);
		return pageData;
	}

	@Override
	public MsgNotice getNoticeDetail(String id, String userId) throws Exception {
		MsgNotice notice = noticeMapper.showMsgNotice(id);
		if(notice != null){
			try {
				if(noticeMapper.getMsgNoticereadCountBy(id, userId)<1){
					MsgNoticeread read = new MsgNoticeread();
					read.setNoticeid(Integer.parseInt(id));
					read.setReceiveuserid(userId);
					read.setReceivetime(new Date());
					noticeMapper.insertMsgNoticeread(read);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			SysUser sysUser = getSysUserById(notice.getAdduserid());
			if(sysUser != null){
				notice.setAddusername(sysUser.getName());
				notice.setAdddeptname(sysUser.getDepartmentname());
			}
		}
		return notice;
	}

	@Override
	public boolean addOrUpdateSingIn(String remark, String x, String y,
			String file, int num) throws Exception {
		boolean flag = false;
		SysUser sysUser = getSysUser();
		Signin oldSignIn = signinMapper.getSigninByUseridAndDate(sysUser.getUserid(), CommonUtils.getTodayDataStr());
		Signin oldSignIn1 = null;
		if(null!=oldSignIn){
			oldSignIn1 = new Signin();
			oldSignIn1.setUserid(oldSignIn.getUserid());
			oldSignIn1.setBusinessdate(oldSignIn.getBusinessdate());
			oldSignIn1.setId(oldSignIn.getId());
		}
		Signin signin = new Signin();
		signin.setUserid(sysUser.getUserid());
		signin.setPersonid(sysUser.getPersonnelid());
		if(StringUtils.isNotEmpty(remark)){
			if(num==1){
				remark = "上午上班:"+remark+"  ";
			}else if(num==2){
				remark = "上午下班:"+remark+"  ";
			}else if(num==3){
				remark = "下午上班:"+remark+"  ";
			}else if(num==4){
				remark = "下午下班:"+remark+"  ";
			}else if(num==5){
				remark = "外出:"+remark+"  ";
			}
		}else{
			remark = "";
		}
		switch (num) {
		case 1:
			if(null==oldSignIn){
				signin.setBusinessdate(CommonUtils.getTodayDataStr());
				signin.setAmbeginfile(file);
				signin.setAmbeginx(x);
				signin.setAmbeginy(y);
				signin.setAmbegin(new Date());
				signin.setRemark(remark);
				signin.setAdduserid(sysUser.getUserid());
				signin.setAddusername(sysUser.getName());
				signin.setAdddeptid(sysUser.getDepartmentid());
				signin.setAdddeptname(sysUser.getDepartmentname());
				int i = signinMapper.addSignin(signin);
				if(i>0){
					flag = true;
				}
			}else{
				if(null==oldSignIn.getAmbegin()){
					oldSignIn1.setAmbeginfile(file);
					oldSignIn1.setAmbeginx(x);
					oldSignIn1.setAmbeginy(y);
					oldSignIn1.setAmbegin(new Date());; 
					String oldremark = oldSignIn.getRemark();
					if(null==oldremark){
						oldremark = remark;
					}else{
						oldremark += "\n"+remark;
					}
					oldSignIn1.setRemark(oldremark);
					int i = signinMapper.updateSignin(oldSignIn1);
					if(i>0){
						flag = true;
					}
				}else{
                    if(StringUtils.isNotEmpty(oldSignIn.getAmbeginfile())){
                        flag  = true;
                    }
                }
			}
			break;
		case 2:
			if(null==oldSignIn){
				signin.setBusinessdate(CommonUtils.getTodayDataStr());
				signin.setAmendfile(file);
				signin.setAmendx(x);
				signin.setAmendy(y);
				signin.setAmend(new Date());
				signin.setRemark(remark);
				signin.setAdduserid(sysUser.getUserid());
				signin.setAddusername(sysUser.getName());
				signin.setAdddeptid(sysUser.getDepartmentid());
				signin.setAdddeptname(sysUser.getDepartmentname());
				int i = signinMapper.addSignin(signin);
				if(i>0){
					flag = true;
				}
			}else{
				if(null==oldSignIn.getAmend()){
					oldSignIn1.setAmendfile(file);
					oldSignIn1.setAmendx(x);
					oldSignIn1.setAmendy(y);
					oldSignIn1.setAmend(new Date());
					String oldremark = oldSignIn.getRemark();
					if(null==oldremark){
						oldremark = remark;
					}else{
						oldremark += "\n"+remark;
					}
					oldSignIn1.setRemark(oldremark);
					int i = signinMapper.updateSignin(oldSignIn1);
					if(i>0){
						flag = true;
					}
				}else{
                    if(StringUtils.isNotEmpty(oldSignIn.getAmendfile())){
                        flag  = true;
                    }
                }
			}
			break;
		case 3:
			if(null==oldSignIn){
				signin.setBusinessdate(CommonUtils.getTodayDataStr());
				signin.setPmbeginfile(file);
				signin.setPmbeginx(x);
				signin.setPmbeginy(y);
				signin.setPmbegin(new Date());
				signin.setRemark(remark);
				signin.setAdduserid(sysUser.getUserid());
				signin.setAddusername(sysUser.getName());
				signin.setAdddeptid(sysUser.getDepartmentid());
				signin.setAdddeptname(sysUser.getDepartmentname());
				int i = signinMapper.addSignin(signin);
				if(i>0){
					flag = true;
				}
			}else{
				if(null==oldSignIn.getPmbegin()){
					oldSignIn1.setPmbeginfile(file);
					oldSignIn1.setPmbeginx(x);
					oldSignIn1.setPmbeginy(y);
					oldSignIn1.setPmbegin(new Date());
					String oldremark = oldSignIn.getRemark();
					if(null==oldremark){
						oldremark = remark;
					}else{
						oldremark += "\n"+remark;
					}
					oldSignIn1.setRemark(oldremark);
					int i = signinMapper.updateSignin(oldSignIn1);
					if(i>0){
						flag = true;
					}
				}else{
                    if(StringUtils.isNotEmpty(oldSignIn.getPmbeginfile())){
                        flag  = true;
                    }
                }
			}
			break;
		case 4:
			if(null==oldSignIn){
				signin.setBusinessdate(CommonUtils.getTodayDataStr());
				signin.setPmendfile(file);
				signin.setPmendx(x);
				signin.setPmendy(y);
				signin.setPmend(new Date());
				signin.setRemark(remark);
				signin.setAdduserid(sysUser.getUserid());
				signin.setAddusername(sysUser.getName());
				signin.setAdddeptid(sysUser.getDepartmentid());
				signin.setAdddeptname(sysUser.getDepartmentname());
				int i = signinMapper.addSignin(signin);
				if(i>0){
					flag = true;
				}
			}else{
				if(null==oldSignIn.getPmend()){
					oldSignIn1.setPmendfile(file);
					oldSignIn1.setPmendx(x);
					oldSignIn1.setPmendy(y);
					oldSignIn1.setPmend(new Date());
					String oldremark = oldSignIn.getRemark();
					if(null==oldremark){
						oldremark = remark;
					}else{
						oldremark += "\n"+remark;
					}
					oldSignIn1.setRemark(oldremark);
					int i = signinMapper.updateSignin(oldSignIn1);
					if(i>0){
						flag = true;
					}
				}else{
                    if(StringUtils.isNotEmpty(oldSignIn.getPmendfile())){
                        flag  = true;
                    }
                }
			}
			break;
		case 5:
			if(null==oldSignIn){
				signin.setBusinessdate(CommonUtils.getTodayDataStr());
				signin.setOutpic(file);
				signin.setOutx(x);
				signin.setOuty(y);
				signin.setOuttime(new Date());
				signin.setRemark(remark);
				signin.setAdduserid(sysUser.getUserid());
				signin.setAddusername(sysUser.getName());
				signin.setAdddeptid(sysUser.getDepartmentid());
				signin.setAdddeptname(sysUser.getDepartmentname());
				int i = signinMapper.addSignin(signin);
				if(i>0){
					flag = true;
				}
			}else{
				if(null==oldSignIn.getOuttime()){
					oldSignIn1.setOutpic(file);
					oldSignIn1.setOutx(x);
					oldSignIn1.setOuty(y);
					oldSignIn1.setOuttime(new Date());
					String oldremark = oldSignIn.getRemark();
					if(null==oldremark){
						oldremark = remark;
					}else{
						oldremark += "\n"+remark;
					}
					oldSignIn1.setRemark(oldremark);
					int i = signinMapper.updateSignin(oldSignIn1);
					if(i>0){
						flag = true;
					}
				}else{
                    if(StringUtils.isNotEmpty(oldSignIn.getOutpic())){
                        flag  = true;
                    }
                }
			}
			break;
		default:
			break;
		}
		return flag;
	}

	@Override
	public List showSigninList(String begindate, String enddate)
			throws Exception {
		SysUser sysUser = getSysUser();
		List<Map> list = signinMapper.showSigninList(begindate, enddate, sysUser.getUserid());
		for(Map map : list){
			String date = (String) map.get("businessdate");
			if(StringUtils.isNotEmpty(date)){
				String dateStr = CommonUtils.dayForWeek(date);
				map.put("businessdate", date+" "+dateStr);
			}
		}
		return list;
	}

}

