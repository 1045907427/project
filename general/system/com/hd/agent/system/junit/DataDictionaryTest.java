/**
 * @(#)TableInfoTest.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-14 zhanghonghui 创建版本
 */
package com.hd.agent.system.junit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hd.agent.common.util.PageMap;
import com.hd.agent.system.model.TableInfo;
import com.hd.agent.system.model.TableRelation;
import com.hd.agent.system.service.IDataDictionaryService;

/**
 * 数据字典-表描述测试
 * 
 * @author zhanghonghui
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:WebRoot/WEB-INF/applicationContext.xml")
public class DataDictionaryTest {
	@Autowired
	public IDataDictionaryService dataDictionaryService;

	/**
	 * 查看表描述查询是否正常
	 * 
	 * @author zhanghonghui
	 * @date 2012-12-14
	 */
	@Test
	public void showTableInfoList() {
		List list = null;
		try {
			list = dataDictionaryService.showTableInfoList();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(list);
	}

	@Test
	public void showTableInfoListBy() {
		List list = null;
		try {
			Map queryMap = new HashMap();
			list = dataDictionaryService.getTableInfoListBy(queryMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list);
	}

	/**
	 * 获取表描述信息
	 * 
	 * @author zhanghonghui
	 * @date 2012-12-18
	 */
	@Test
	public void showTableInfo() {
		String tablename = "t_sys_tableinfo";
		TableInfo tableInfo = null;
		try {
			tableInfo = dataDictionaryService.showTableInfo(tablename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(tableInfo);
	}

	/**
	 * 更新表描述
	 * 
	 * @author zhanghonghui
	 * @date 2012-12-19
	 */
	@Test
	public void editTableInfo() {

		String tablename = "t_sys_tableinfo";
		TableInfo tableInfo = null;
		try {
			tableInfo = dataDictionaryService.showTableInfo(tablename);
			System.out.println("旧数据-描述：" + tableInfo.getTabledescname());
			String description = tableInfo.getTabledescname();
			tableInfo.setTabledescname("测试表");
			System.out.println("新数据-描述： " + tableInfo.getTabledescname());
			boolean flag = dataDictionaryService.editTableInfo(tableInfo);
			System.out.println("插入是否成功：" + flag);
			tableInfo.setTabledescname(description);
			dataDictionaryService.editTableInfo(tableInfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 删除表描述
	 * 
	 * @author zhanghonghui
	 * @date 2012-12-19
	 */
	@Test
	public void deleteTableInfo() {
		String tablename = "t_sys_tablecolumn1";
		try {
			boolean flag = dataDictionaryService.deleteTableInfo(tablename);
			System.out.print("删除是否成功：" + flag);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void showTableColumnList() {
		List list = null;
		try {
			list = dataDictionaryService.showTableColumnList();
			System.out.println(list);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	@Test
	public void showTableColumnBy() {
		List list = null;
		try {
			Map queryMap = new HashMap();
			list = dataDictionaryService.getTableColumnListBy(queryMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list);
	}

	@Test
	public void editTableRelation() {
		String id = "3";
		TableRelation tableRelation = new TableRelation();
		tableRelation.setMaintablename("t_sys_user1");
		try {

			boolean flag = dataDictionaryService
					.editTableRelation(tableRelation);
			System.out.println("更新表关联：" + flag);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getTableRelationMap() {
		try {
			Map queryMap = new HashMap();
			queryMap.put("maintablename", "t_sys_user");
			queryMap.put("maincolumnname", "mobilephone");
			queryMap.put("tablename", "t_ac_authdata");
			queryMap.put("columnname", "dataid");
			List<TableRelation> list = dataDictionaryService.getTableRelationListBy(queryMap);
			List jsonList = new ArrayList();
			for (TableRelation tableRelation : list) {
				Map map = new HashMap();
				map.put("maintablename", tableRelation.getMaintablename());
				map.put("maincolumnname", tableRelation.getMaincolumnname());
				map.put("tablename", tableRelation.getTablename());
				map.put("columnname", tableRelation.getColumnname());
				map.put("deleteverify", tableRelation.getDeleteverify());
				map.put("cascadechange", tableRelation.getCascadechange());
				jsonList.add(map);
			}
			System.out.println(jsonList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTableDataDictList(){
		try{
			Map queryMap=new HashMap();
			//queryMap.put("refertreecol", "pid");
			queryMap.put("tablename", "t_ac_authoper");
			queryMap.put("columnname", "operateid");
			//queryMap.put("coltitlename", "operatename");
			PageMap pageMap=new PageMap();
			pageMap.setCondition(queryMap);
			Map dataMap=dataDictionaryService.getTableDataDictHandleResult(pageMap);
	 		List list = (List) dataMap.get("data");
			JSONArray array = JSONArray.fromObject(list);
			System.out.println(array.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
