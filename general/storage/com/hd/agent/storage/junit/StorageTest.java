/**
 * @author chenwei
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-7 chenwei 创建版本
 */
package com.hd.agent.storage.junit;


import com.hd.agent.common.action.BaseAction;
import com.hd.agent.storage.service.IStorageSummaryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:WebRoot/WEB-INF/applicationContext.xml"})
public class StorageTest extends BaseAction{
	@Autowired
	private IStorageSummaryService storageSummaryService;

	@Test
	public void addInventoryAge(){
        try {
            storageSummaryService.addInventoryAge(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	

}

