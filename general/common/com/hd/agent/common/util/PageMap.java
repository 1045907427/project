/**
 * @(#)PageMap.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-8 chenwei 创建版本
 */
package com.hd.agent.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页查询类
 * 处理查询分类，查询条件等
 * @author chenwei
 */
public class PageMap {
	
	/**
	 * 查询条件
	 */
	private Map condition = new HashMap();
	/**
	 * 分页查询开始序号
	 */
	private int startNum;
	
	/**
	 * 分页查询结束序号
	 */
	private int endNum;
	/**
	 * 当前第几页
	 */
	private int page = 1;
	/**
	 * 每页显示条数
	 */
	private int rows = 10;
	/**
	 * 可以查询的列字段(字段权限)
	 */
	private String cols;
	/**
	 * 数据权限sql语句
	 */
	private String dataSql;
	/**
	 * 通用查询规则
	 */
	private String queryRules;
	/**
	 * 通用查询排序规则
	 */
	private String orderRules;
	/**
	 * 通用查询sql语句
	 */
	private String querySql;
	/**
	 * 通用查询排序语句
	 */
	private String orderSql;
	
	/**
	 * 左右关联
	 */
	private String joinTable;
	
	public String getJoinTable() {
		return joinTable;
	}
	public void setJoinTable(String joinTable) {
		this.joinTable = joinTable;
	}
	/**
	 * 分页类 显示第一页，每页显示10条数据
	 */
	public PageMap(){
		this.startNum = 0;
		this.endNum = rows;
	}
	/**
	 * 分页类
	 * @param page 第几页
	 * @param rows	每页显示数量
	 */
	public PageMap(int page,int rows){
		if(page>1){
			this.startNum = (page-1)*rows;
			this.endNum = page*rows;
		}else{
			this.startNum = 0;
			this.endNum = rows;
		}
		this.page = page;
		this.rows = rows;
	}
	/**
	 * 分页类
	 * @param page 第几页
	 * @param rows	每页显示数量
	 * @param map 查询条件
	 */
	public PageMap(int page,int rows,Map map){
		if(page>1){
			this.startNum = (page-1)*rows;
			this.endNum = page*rows;
		}else{
			this.startNum = 0;
			this.endNum = rows;
		}
		this.page = page;
		this.rows = rows;
		this.condition = map;
	}
	public Map getCondition() {
		return condition;
	}
	public void setCondition(Map condition) {
		this.condition = condition;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getEndNum() {
		return endNum;
	}
	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		if(page>1){
			this.startNum = (page-1)*rows ;
			this.endNum = page*rows;
		}else{
			this.startNum = 0;
			this.endNum = rows;
		}
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		if(page>1){
			this.startNum = (page-1)*rows ;
			this.endNum = page*rows;
		}else{
			this.startNum = 0;
			this.endNum = rows;
		}
		this.rows= rows;
	}
	public String getCols() {
		return cols;
	}
	public void setCols(String cols) {
		this.cols = cols;
	}
	public String getDataSql() {
		return dataSql;
	}
	public void setDataSql(String dataSql) {
		this.dataSql = dataSql;
	}
	public String getQuerySql() {
		return querySql;
	}
	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}
	public String getQueryRules() {
		return queryRules;
	}
	public void setQueryRules(String queryRules) {
		this.queryRules = queryRules;
	}
	
	public String getOrderRules() {
		return orderRules;
	}
	public void setOrderRules(String orderRules) {
		this.orderRules = orderRules;
	}
	public String getOrderSql() {
		return orderSql;
	}
	public void setOrderSql(String orderSql) {
		this.orderSql = orderSql;
	}
	/**
	 * 设置通用查询的别名
	 * @param alias
	 * @author chenwei 
	 * @date Mar 7, 2013
	 */
	public void setQueryAlias(String alias){
		Map map = new HashMap();
		map.put("alias", alias);
		String sql = RuleJSONUtils.dataRuleToSQLString(this.queryRules,map);
		String ordersql = RuleJSONUtils.orderRulesToSql(this.orderRules, alias);
		this.querySql = sql;
		this.orderSql = ordersql;
	}

	/**
	 * 只是给通用查询设置别名
	 * @param alias
	 * @return void
	 * @throws
	 * @author zhanghonghui
	 * @date Dec 06, 2017
	 */
	public void setQuerySqlOnlyAlias(String alias){
		Map map = new HashMap();
		map.put("alias", alias);
		String sql = RuleJSONUtils.dataRuleToSQLString(this.queryRules,map);
		this.querySql = sql;
	}
}

