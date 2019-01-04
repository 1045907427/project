/**
 * @(#)AttachFileTest.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-11-13 zhanghonghui 创建版本
 */
package com.hd.agent.common.junit;
/**
 * 
 * 
 * @author zhanghonghui
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.model.AttachFile;
import com.hd.agent.common.service.IAttachFileService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:WebRoot/WEB-INF/applicationContext.xml"})
public class AttachFileTest  extends BaseAction{
	@Autowired
	IAttachFileService attachFileService;
	
	@Test
	public void covertPDF()throws Exception{
		try{
			AttachFile attachFile=attachFileService.getAttachFile("355");
			//attachFileService.updateAttachConvertByJob(attachFile);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}

