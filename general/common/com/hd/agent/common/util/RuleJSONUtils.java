/**
 * @(#)JsonUtils.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-24 chenwei 创建版本
 */
package com.hd.agent.common.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringEscapeUtils;

import com.hd.agent.accesscontrol.model.SysUser;

/**
 * 
 * 解析json字符串或者json对象
 * @author chenwei
 */
public class RuleJSONUtils {
	/**
	 * 数据权限控制规则,json格式的字符串转成sql条件语句
	 * @param jsonStr
	 * @param map 公共属性集合{alias:'别名',CurrentRoleID:当前角色编号集合,sysUser:当前用户}
	 * @return
	 * @author chenwei 
	 * @date 2012-12-24
	 */
	public static String dataRuleToSQLString(String jsonStr,Map map){
		if(null==jsonStr){
			return null;
		}
		String sql = "(";
		JSONObject jo = JSONObject.fromObject(jsonStr);
		String op = jo.getString("op");
		JSONArray jsonRules = null;
		if(jo.has("rules")){
			jsonRules = jo.getJSONArray("rules");
			for(int i=0;i<jsonRules.size();i++){
				JSONObject jsonRule = jsonRules.getJSONObject(i);
				String sqlRule = ruleToSql(jsonRule,map);
				if(i==0){
					sql +=  sqlRule+ " ";
				}else{
					sql += op+" "+ sqlRule+ " ";
				}
				
			}
		}else{
			//没有条件规则时 默认不允许
			sql +=" 1!=1 ";
		}
		JSONArray jsonGroup = null;
		if(jo.has("groups")){
			jsonGroup = jo.getJSONArray("groups");
			for(int i=0;i<jsonGroup.size();i++){
				//递归调用
				String sqlStr = dataRuleToSQLString(jsonGroup.getJSONObject(i).toString(),map);
				JSONObject group = JSONObject.fromObject(jsonStr);
				sql += op+" "+sqlStr +" ";
			}
		}
		sql += ")";
		//判断是否有条件规则 
		if("( 1!=1 )".equals(sql)){
			sql = null;
		}
		return sql;
	}
	/**
	 * rule规则转成sql条件
	 * @param jsonObject
	 * @return
	 * @author chenwei 
	 * @date 2012-12-24
	 */
	public static String ruleToSql(JSONObject jsonObject,Map map){
		String alias = (String) map.get("alias");
		if(null==alias||"".equals(alias)){
			alias = "";
		}else{
			alias += ".";
		}
		String sql = null;
		String field = null;
		
		String opRule = null;
		String value = null;
		if(jsonObject.containsKey("value") && jsonObject.containsKey("field") && jsonObject.containsKey("op")){
			field = jsonObject.getString("field");
			opRule = jsonObject.getString("op");
			value = jsonObject.getString("value");
		}else{
			return " 1=1 ";
		}
		//当值为空或者null值时，默认条件为true
		if(null == value || "".equals(value) || "null".equals(value)){
			return " 1=1 ";
		}
		//防止sql注入
		field = StringEscapeUtils.escapeSql(field);
		opRule = StringEscapeUtils.escapeSql(opRule);
		value = StringEscapeUtils.escapeSql(value);
		
		//当字段为当前角色id（CurrentRoleID）时，判断是否有权限访问 并且生成sql语句
		if("CurrentRoleID".equals(field)){
			List<String> authorityList = (List<String>) map.get("CurrentRoleID");
			if(null!=value){
				//不等于 与不包括 判断是否存在该角色编号
				//其他都判断是否存在该角色
				if("notequal".equals(opRule) || "notin".equals(opRule)){
					boolean flag = true;
					String[] values = value.split(",");
					for(String authorityid : authorityList){
						for(String val : values){
							if(authorityid.equals(val)){
								flag = false;
								break;
							}
						}
						if(!flag){
							break;
						}
					}
					if(flag){
						return " 1=1 ";
					}else{
						return " 1!=1 ";
					}
				}else{
					boolean flag = false;
					String[] values = value.split(",");
					for(String authorityid : authorityList){
						for(String val : values){
							if(authorityid.equals(val)){
								flag = true;
								break;
							}
						}
						if(flag){
							break;
						}
					}
					if(flag){
						return " 1=1 ";
					}else{
						return " 1!=1 ";
					}
				}
				
			}else{
				return " 1=1 ";
			}
		}else if("CurrentUserID".equals(field)){
			//当字段为当前用户id（CurrentUserID）时，判断是否有权限访问 并且生成sql语句
			SysUser sysUser = (SysUser) map.get("sysUser");
			if(null!=value){
				if("notequal".equals(opRule) || "notin".equals(opRule)){
					String[] values = value.split(",");
					boolean flag = true;
					for(String val : values){
						//判断编号是否与当前用户编号 或者用户相关的人员档案编号一致
						if(val.equals(sysUser.getUserid()) || val.equals(sysUser.getPersonnelid())){
							flag = false;
							break;
						}
					}
					if(flag){
						return " 1=1 ";
					}else{
						return " 1!=1 ";
					}
				}else{
					String[] values = value.split(",");
					boolean flag = false;
					for(String val : values){
						//判断编号是否与当前用户编号 或者用户相关的人员档案编号一致
						if(val.equals(sysUser.getUserid()) || val.equals(sysUser.getPersonnelid())){
							flag = true;
							break;
						}
					}
					if(flag){
						return " 1=1 ";
					}else{
						return " 1!=1 ";
					}
				}
			}else{
				return " 1=1 ";
			}
		}else if("CurrentDeptID".equals(field)){
			//当字段为当前部门id（CurrentDeptID）时，判断是否有权限访问 并且生成sql语句
			SysUser sysUser = (SysUser) map.get("sysUser");
			if(null!=value){
				if("notequal".equals(opRule) || "notin".equals(opRule)){
					String[] values = value.split(",");
					boolean flag = true;
					for(String val : values){
						if(val.equals(sysUser.getDepartmentid())){
							flag = false;
							break;
						}
					}
					if(flag){
						return " 1=1 ";
					}else{
						return " 1!=1 ";
					}
				}else if("startwith".equals(opRule)){
					//判断当前部门 是否 以。。。开始
					boolean flag = false;
					if(null!=sysUser.getDepartmentid() && sysUser.getDepartmentid().startsWith(value)){
						flag = true;
					}
					if(flag){
						return " 1=1 ";
					}else{
						return " 1!=1 ";
					}
				}else if("endwith".equals(opRule)){
					//以什么结束 判断
					boolean flag = false;
					if(null!=sysUser.getDepartmentid() && sysUser.getDepartmentid().endsWith(value)){
						flag = true;
					}
					if(flag){
						return " 1=1 ";
					}else{
						return " 1!=1 ";
					}
				}else{
					//其他情况就判断部门是否存在
					String[] values = value.split(",");
					boolean flag = false;
					for(String val : values){
						if(val.equals(sysUser.getDepartmentid())){
							flag = true;
							break;
						}
					}
					if(flag){
						return " 1=1 ";
					}else{
						return " 1!=1 ";
					}
				}
			}else{
				return " 1=1 ";
			}
		}
		//当值为当前用户编号值 生成特殊的sql语句
		if("CurrentUserID".equals(value)){
			SysUser sysUser = (SysUser) map.get("sysUser");
			String personnelid = sysUser.getPersonnelid();
			if(null==personnelid || "".equals(personnelid)){
				personnelid = sysUser.getUserid();
			}
			if("equalCurr".equals(opRule)){
				sql = "("+alias+field +" = '" + sysUser.getUserid() +"' or "+ alias+field +" = '" + personnelid +"')";
			}else if("notequalCurr".equals(opRule)){
				sql = "("+alias+field +" != '" + sysUser.getUserid() +"' or" + alias+field +" != '" + personnelid +"')";
			}else if("startwithCurr".equals(opRule)){
				sql = "("+alias+field +" like '" + sysUser.getUserid() +"%' or " + alias+field +" like '" + personnelid+"%' )";
			}else if("endwithCurr".equals(opRule)){
				sql = "("+alias+field +" like '%" + sysUser.getUserid() +"' or " + alias+field +" like '%" + personnelid +"')";
			}else if("likeCurr".equals(opRule)){
				sql = "("+alias+field +" like '%" + sysUser.getUserid() +"%' or " + alias+field +" like '%" + personnelid +"%')";
			}
			return sql;
		}else if("CurrentRoleID".equals(value)){ //当值为当前角色编号时  生成特殊的sql语句
			List<String> authorityList = (List<String>) map.get("CurrentRoleID");
			String authoritys = null;
			for(String authority : authorityList){
				if(null!=authoritys){
					authoritys += ",'"+authority+"'";
				}else{
					authoritys = "'"+authority+"'";
				}
			}
			if("equalCurr".equals(opRule)){
				sql = alias+field +" in (" + authoritys +")";
			}else if("notequalCurr".equals(opRule)){
				sql = alias+field +" not in (" + authoritys +")";
			}
			return sql;
		}else if("CurrentDeptID".equals(value)){ //当值为当前角色编号时  生成特殊的sql语句
			SysUser sysUser = (SysUser) map.get("sysUser");
			String departmentId = sysUser.getDepartmentid();
			if("equalCurr".equals(opRule)){
				sql = alias+field +" = '" + departmentId +"'";
			}else if("notequalCurr".equals(opRule)){
				sql = alias+field +" <> '" + departmentId +"'";
			}else if("startwithCurr".equals(opRule)){
				sql = alias+field +" like '" + departmentId +"%'";
			}else if("endwithCurr".equals(opRule)){
				sql = alias+field +" like '%" + departmentId +"'";
			}else if("likeCurr".equals(opRule)){
				sql = alias+field +" like '%" + departmentId +"%'";
			}
			return sql;
		}else {
			if(CommonUtils.isDataStr(value)){
				//如果值是日期格式，则生成日期格式的sql语句
				switch (OpEnum.getOpEnum(opRule)) {
					case greater:
						sql = alias+field +" > STR_TO_DATE('"+value+"','%Y-%m-%d')";
						break;
					case greaterorequal:
						sql = alias+field +" >= STR_TO_DATE('"+value+"','%Y-%m-%d')";
						break;
					case less:
						sql = alias+field +" < STR_TO_DATE('"+value+"','%Y-%m-%d')";
						break;
					case lessorequal:
						sql = alias+field +" <= STR_TO_DATE('"+value+"','%Y-%m-%d')";
						break;
				}
				return sql;
			}else if("nearday".equals(opRule)){ //最近多少天
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, -Integer.parseInt(value));
				String dataStr = CommonUtils.dataToStr(c.getTime(), "yyyy-MM-dd");
				sql = alias+field +" >= STR_TO_DATE('"+dataStr+"','%Y-%m-%d')";
				return sql;
			}else if("nearmonth".equals(opRule)){//最近多少月
				Calendar c = Calendar.getInstance();
				c.add(Calendar.MONTH, -Integer.parseInt(value));
				String dataStr = CommonUtils.dataToStr(c.getTime(), "yyyy-MM-dd");
				sql = alias+field +" >= STR_TO_DATE('"+dataStr+"','%Y-%m-%d')";
				return sql;
			}else if("thismonth".equals(opRule)){//当月
				String dataStr = CommonUtils.getNowMonthDay();
				sql = alias+field +" >= STR_TO_DATE('"+dataStr+"','%Y-%m-%d')";
				return sql;
			}else if("thisquarter".equals(opRule)){//当前季度
				String dataStr = CommonUtils.getQuarterBeginDate();
				sql = alias+field +" >= STR_TO_DATE('"+dataStr+"','%Y-%m-%d')";
				return sql;
			}
		}
		//普通情况下 根据不同的操作条件 封装不同的sql语句
		switch (OpEnum.getOpEnum(opRule)) {
			case equal:
				sql = alias+field +" = '" + value +"'";
				break;
			case notequal:
				sql = alias+field +" != '" + value +"'";
				break;
			case startwith:
				sql = alias+field + " like '" + value+"%'";
				break;
			case endwith:
				sql = alias+field + " like '%" + value+"'";
				break;
			case like:
				sql = alias+field + " like '%" + value+"%'";
				break;
			case greater:
				sql = alias+field + " > " + value;
				break;
			case greaterorequal:
				sql = alias+field + " >= " + value;
				break;
			case less:
				sql = alias+field + " < " + value;
				break;
			case lessorequal:
				sql = alias+field + " <= " + value;
				break;
			case in:
				if(null!=value && !"".equals(value)){
					String[] valarr = value.split(",");
					String values = "";
					for(String val : valarr){
						if("".equals(values)){
							values = "'"+val+"'";
						}else{
							values += ",'"+val+"'";
						}
					}
					sql = alias+field + " in (" + values+")";
				}
				break;
			case notin:
				if(null!=value && !"".equals(value)){
					String[] valarr = value.split(",");
					String values = "";
					for(String val : valarr){
						if("".equals(values)){
							values = "'"+val+"'";
						}else{
							values += ",'"+val+"'";
						}
					}
					sql = alias+field + " not in (" + values+")";
				}
				break;
		default:
				sql =  " 1=1 ";
			break;
		}
		return sql;
	}
	/**
	 * 控件中的参数规则解析成sql语句
	 * @param paramRule
	 * @return
	 * @author chenwei 
	 * @date Mar 4, 2013
	 */
	public static String widgetParamToSql(String paramRule,String alias){
		String sql = null;
		if(null!=paramRule&&!"".equals(paramRule)){
			JSONArray array = JSONArray.fromObject(paramRule);
			Map map = new HashMap();
			if(null!=alias){
				map.put("alias", alias);
			}
			for (int i = 0; i < array.size(); i++) {
				JSONObject object = array.getJSONObject(i);
				if(null !=sql){
					sql += " and " +ruleToSql(object, map);
				}else{
					sql = " " +ruleToSql(object, map);
				}
				
			}
		}else{
			sql = " 1=1 ";
		}
		return sql;
	}
	/**
	 * 排序规则转成sql排序语句
	 * @param orderRules
	 * @param alias
	 * @return
	 * @author chenwei 
	 * @date Mar 7, 2013
	 */
	public static String orderRulesToSql(String orderRules,String alias){
		if(null==orderRules){
			return null;
		}
		JSONArray rules = JSONArray.fromObject(orderRules);
		if(alias!=null&&!"".equals(alias)){
			alias = alias+".";
		}else{
			alias = "";
		}
		String sqlString = null;
		for (int i = 0; i < rules.size(); i++) {
			JSONObject object = rules.getJSONObject(i);
			if(i==0){
				sqlString = alias+object.getString("field") + " " +object.getString("order");
			}else{
				sqlString += "," + alias +object.getString("field") + " " +object.getString("order");
			}
		}
		//过滤sql 防止sql注入
		if(null!=sqlString){
			sqlString = StringEscapeUtils.escapeSql(sqlString);
		}
		return sqlString;
	}
}

