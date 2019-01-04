/**
 * @(#)MessageServiceTest.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-1-19 zhanghonghui 创建版本
 */
package com.hd.agent.message.junit;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.message.model.MsgContent;
import com.hd.agent.message.model.MsgReceive;
import com.hd.agent.message.service.IInnerMessageService;

/**
 * 
 * 
 * @author zhanghonghui
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebRoot/WEB-INF/applicationContext.xml")
public class InnerMessageTest {
	@Autowired
	public IInnerMessageService innerMessageService;

	@Test
	public void addMsgContent() {
		try {
			MsgContent msgContent = new MsgContent();
			msgContent.setTitle("title");
			msgContent.setContent("sssss");
			msgContent.setMsgtype("1");
			boolean isok = innerMessageService.addMsgContent(msgContent);
			if (isok) {
				System.out.println(msgContent.getId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateMsgContent(){
		try{
			MsgContent msgContent=new MsgContent();
			msgContent.setTitle("title1");
			msgContent.setContent("ssss222s");
			msgContent.setMsgtype("1");
			msgContent.setId(3);
			boolean isok=innerMessageService.updateMsgContent(msgContent);
			System.out.println(isok);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void showMsgContentPageList(){
		try{
			PageMap pageMap=new PageMap();
			pageMap.setCondition(new HashMap());
			PageData pageData=innerMessageService.showMsgContentPageList(pageMap);
			List<MsgContent> list=pageData.getList();
			
			if(list!=null){
				BaseAction baseAction =new BaseAction();
				baseAction.addJSONObject(pageData);
				System.out.println(baseAction.getJsonResult());
			}else{
				System.out.println("未找到相关数据");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void addMsgReceive(){
		try{
			MsgReceive msgReceive=new MsgReceive();
			msgReceive.setMsgid(2);
			msgReceive.setSenduserid("1");
			msgReceive.setRecvuserid("2");
			msgReceive.setViewflag("1");
			boolean isok=innerMessageService.addMsgReceive(msgReceive);
			System.out.println(isok+" id:"+msgReceive.getId());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateMsgReceive(){
		try{
			MsgReceive msgReceive=new MsgReceive();
			msgReceive.setId(1);
			msgReceive.setViewflag("0");
			msgReceive.setRecvtime(new Date());
			boolean isok=innerMessageService.updateMsgReceive(msgReceive);
			System.out.println(isok);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
