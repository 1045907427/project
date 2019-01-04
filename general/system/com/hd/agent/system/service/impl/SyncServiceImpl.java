package com.hd.agent.system.service.impl;

import com.alibaba.druid.filter.config.ConfigTools;
import com.hd.agent.accesscontrol.model.SysUser;
import com.hd.agent.common.model.SysLog;
import com.hd.agent.common.service.ISysLogService;
import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.FileUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import com.hd.agent.common.util.PropertiesUtils;
import com.hd.agent.system.dao.SyncMapper;
import com.hd.agent.system.model.SysDataSource;
import com.hd.agent.system.service.ISyncService;
import com.hd.agent.system.service.ISysDataSourceService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.Reader;
import java.util.*;

/**
 * Created by xuxin on 2017/5/17 0017.
 */
public class SyncServiceImpl extends BaseServiceImpl implements ISyncService {

    static String strTpl = "表名:%s,记录数:%d,耗时:%dms;<br>";
    private SysDataSource fromSource;
    private SysDataSource toSource;
//    private SqlSession sessionFrom;
//    private SqlSession sessionTo;
//    private SyncMapper mapperFrom;
//    private SyncMapper mapperTo;

    private static final Logger logger = Logger.getLogger(SyncServiceImpl.class);

    private ISysDataSourceService sysDataSourceService;

    public ISysDataSourceService getSysDataSourceService() {
        return sysDataSourceService;
    }

    public void setSysDataSourceService(ISysDataSourceService sysDataSourceService) {
        this.sysDataSourceService = sysDataSourceService;
    }

    private ISysLogService sysLogService;

    public ISysLogService getSysLogService() {
        return sysLogService;
    }

    public void setSysLogService(ISysLogService sysLogService) {
        this.sysLogService = sysLogService;
    }

    private SqlSession getSession(SysDataSource sysDataSource) throws Exception {
        Properties properties = new Properties();
        properties.setProperty("THIRD_JDBC.driver", sysDataSource.getJdbcdriver());
        properties.setProperty("THIRD_JDBC.url", sysDataSource.getJdbcurl());
        properties.setProperty("THIRD_JDBC.username", sysDataSource.getDbuser());
        properties.setProperty("THIRD_JDBC.password", sysDataSource.getDbpasswdclear());
        properties.setProperty("THIRD_MAPPER", "com/hd/agent/system/dao/mysql/SyncMapper.xml");
        String resource = "MyBatis-thirdpart.xml";
        Reader reader = Resources.getResourceAsReader(resource);
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSessionFactory factory = builder.build(reader, properties);
        return factory.openSession();
    }

    /**
     * 获取数据表的行信息
     *
     * @param tableSchema
     * @param tablePrefix
     * @return
     */
    private List<String> getTables(String tableSchema, String tablePrefix) {
        List<String> tableNames = null;
        SqlSession sessionFrom = null;
        try {
            sessionFrom = getSession(fromSource);
            SyncMapper mapperFrom = sessionFrom.getMapper(SyncMapper.class);
            tableNames = mapperFrom.selectTableNames(tableSchema, tablePrefix);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sessionFrom != null)
                sessionFrom.close();
        }
        return tableNames;
    }

    /**
     * 数据同步
     *
     * @param dataSource    源端数据源code
     * @param code          数据表缩写code
     * @param businessdate1 开始业务日期
     * @param businessdate2 结束业务日期
     * @return map   flag:同步状态 msg:同步结果描述
     * @throws Exception
     */
    @Override
    public Map syncData(String dataSource, String code, String businessdate1, String businessdate2) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        logger.debug(String.format("dataSource:%s,code:%s,businessdate1:%s,businessdate2:%s", dataSource, code, businessdate1, businessdate2));
        if (StringUtils.isEmpty(dataSource)) {
            result.put("flag", false);
            result.put("msg", "源数据库必须要选择");
            return result;
        }
        fromSource = sysDataSourceService.getSysDataSourceByCode(dataSource);

        //获取数据库配置信息
        Map map = PropertiesUtils.readPropertiesFileNew(FileUtils.getClassPath() + "config.properties");
        SysDataSource sysDataTarget = new SysDataSource();
        sysDataTarget.setDbpasswdclear(ConfigTools.decrypt((String) map.get("jdbc_password")));
        sysDataTarget.setDbuser((String) map.get("jdbc_username"));
        sysDataTarget.setJdbcdriver((String) map.get("driverClassName"));
        sysDataTarget.setJdbcurl((String) map.get("jdbc_url"));
        sysDataTarget.setDbname(sysDataTarget.getJdbcurl().substring(sysDataTarget.getJdbcurl().lastIndexOf("/") + 1, sysDataTarget.getJdbcurl().indexOf("?")));

        toSource = sysDataTarget;
        if (fromSource == null) {
            result.put("flag", false);
            result.put("msg", "找不到相应的源数据库信息");
            return result;
        }
        try {
//            SqlSession sessionFrom = getSession(fromSource);
//            SyncMapper mapperFrom = sessionFrom.getMapper(SyncMapper.class);
//            sessionTo = getSession(toSource);
//            mapperTo = sessionTo.getMapper(SyncMapper.class);

            if ("base".equals(code)) {
                List<String> tableNames = getTables(fromSource.getDbname(), "t_base");
                //List<String> tableNames = new ArrayList<String>();
                //tableNames.add("t_base_goods_info");
                String msg = insertAllTableData(tableNames);
                result.put("flag", !msg.contains("失败"));
                result.put("msg", "基础信息同步" + (msg.contains("失败") ? "失败" : "成功") + "。<br>" + msg);
            } else if ("sales_promotion".equals(code)) {
                List<String> tableNames = getTables(fromSource.getDbname(), "t_sales_promotion");
                String msg = insertAllTableData(tableNames);
                result.put("flag", !msg.contains("失败"));
                result.put("msg", "销售促销数据同步" + (msg.contains("失败") ? "失败" : "成功") + "。<br>" + msg);
            } else if ("sysnumber".equals(code)) {
                List<String> tableNames = new ArrayList<String>();
                tableNames.add("t_sys_number");
                tableNames.add("t_sys_numberrule");
                //tableNames.add("t_sys_numberserial");
                String msg = insertAllTableData(tableNames);
                String msg2 = insertSysNumberSerial();
                result.put("flag", !msg.contains("失败"));
                result.put("msg", "单据编号规则数据同步" + (msg.contains("失败") ? "失败" : "成功") + "。<br>" + msg + "<br>" + msg2);
            } else if ("business".equals(code)) {
                String msg = insertBusiness(businessdate1, businessdate2);
                result.put("flag", true);
                result.put("msg", msg);
            } else {
                result.put("flag", false);
                result.put("msg", "未知的code参数");
            }
        } catch (Exception ex) {
            result.put("flag", false);
            result.put("msg", "同步失败." + ex.getMessage());
        }
        return result;
    }

    /***
     * 是否已经同步过
     * @param log 日志
     * @return
     * @throws Exception
     */
    private boolean isSynced(String log) throws Exception {
        PageMap pageMap = new PageMap();
        pageMap.getCondition().put("content", log);
        pageMap.getCondition().put("keyname", "datasync");
        pageMap.setRows(1);
        PageData logList = sysLogService.showSearchSysLog(pageMap);
        return logList.getTotal() > 0;
    }

    /**
     * 添加同步的日志
     *
     * @param log
     * @throws Exception
     */
    private void AddSyncLog(String log) throws Exception {
        SysLog sysLog = new SysLog();
        sysLog.setAddtime(new Date());
        sysLog.setContent(log);
        sysLog.setIp("127.0.0.1");
        sysLog.setType("2");
        sysLog.setKeyname("datasync");

        SysUser userInfo = getSysUser();
        sysLog.setUserid(userInfo.getUserid());
        sysLog.setName(userInfo.getName());

        sysLogService.addSysLog(sysLog);
    }

    private String successOrFail(String msg) {
        return (msg == null || msg.contains("失败")) ? "失败" : "成功";
    }

    private String insertBusiness(String businessdate1, String businessdate2) throws Exception {
        String sysLog = "业务数据同步.业务日期:%s - %s";
        sysLog = String.format(sysLog, businessdate1, businessdate2);
        if (isSynced(sysLog)) {
            return "该月份的业务数据已经同步过了,不能再进行同步!";
        }
        StringBuilder result = new StringBuilder();
        String msg = insertTableAndDetail("t_sales_order", "t_sales_order_detail", "orderid", businessdate1, businessdate2);
        result.append("销售订单数据同步" + successOrFail(msg) + "。<br>" + msg);

        String msg2 = insertTableAndDetail("t_sales_order_car", "t_sales_order_car_detail", "orderid", businessdate1, businessdate2);
        result.append("零售车销订单数据同步" + successOrFail(msg2) + "。<br>" + msg2);

        String msg3 = insertTableAndDetail("t_sales_rejectbill", "t_sales_rejectbill_detail", "billid", businessdate1, businessdate2);
        result.append("销售退货通知单数据同步" + successOrFail(msg3) + "。<br>" + msg3);

        String msg4 = insertTableAndDetail("t_storage_other_enter", "t_storage_other_enter_detail", "billid", businessdate1, businessdate2);
        result.append("其他入库单数据同步" + successOrFail(msg4) + "。<br>" + msg4);

        String msg5 = insertTableAndDetail("t_storage_adjustments", "t_storage_adjustments_detail", "adjustmentsid", businessdate1, businessdate2);
        result.append("报损报溢单数据同步" + successOrFail(msg5) + "。<br>" + msg5);

        String msg6 = insertTableAndDetail("t_purchase_returnorder", "t_purchase_returnorder_detail", "orderid", businessdate1, businessdate2);
        result.append("采购退货通知单数据同步" + successOrFail(msg6) + "。<br>" + msg6);

        String msg7 = insertTableAndDetail("t_purchase_buyorder", "t_purchase_buyorder_detail", "orderid", businessdate1, businessdate2);
        result.append("采购订单数据同步" + successOrFail(msg7) + "。<br>" + msg7);

        String msg8 = insertTable("t_account_customer_push_balance", businessdate1, businessdate2);
        result.append("客户应收款冲差单数据同步" + successOrFail(msg8) + "。<br>" + msg8);

        AddSyncLog(sysLog);
        return result.toString();
    }

    /**
     * 导入该前缀开始的所有表数据
     *
     * @param tableNames 表名集合
     * @return 数据导入结果描述
     * @throws Exception
     */
    private String insertAllTableData(List<String> tableNames) throws Exception {
        StringBuilder result = new StringBuilder();
        for (String tableName : tableNames) {
            SqlSession sessionFrom = getSession(fromSource);
            SqlSession sessionTo = getSession(toSource);
            try {
                SyncMapper mapperFrom = sessionFrom.getMapper(SyncMapper.class);
                SyncMapper mapperTo = sessionTo.getMapper(SyncMapper.class);
                long beginTime = System.currentTimeMillis();
                String countSql = "select count(1) from " + tableName;
                Integer count = mapperFrom.selectCount(countSql);
                if (count == 0) {
                    result.append(String.format(strTpl, tableName, count, (System.currentTimeMillis() - beginTime)));
                    continue;
                }
                mapperTo.deleteSql("delete from " + tableName);

                Map sqlPart = getInsertSqlPart(tableName, false, null, mapperFrom, mapperTo);
                if ("false".equals(sqlPart.get("flag").toString())) {
                    result.append(sqlPart.get("msg").toString());
                    continue;
                }
                Integer pageSize = 10000;
                Integer pages = count / pageSize + (count % pageSize == 0 ? 0 : 1);
                Integer recordNums = 0;
                for (int i = 0; i < pages; i++) {
                    String selectSql = String.format("select * from %s limit %d,%d", tableName, i * pageSize, pageSize);
                    long time1 = System.currentTimeMillis();
                    List<LinkedHashMap<String, Object>> dataList = mapperFrom.selectList(selectSql);
                    recordNums += mapperTo.insertList(tableName, sqlPart.get("columnSql").toString(), sqlPart.get("valueSql").toString(), dataList);
                    dataList.clear();
                    System.out.println(selectSql + "插入时间." + (System.currentTimeMillis() - time1));
                }
                sqlPart.clear();
                result.append(String.format(strTpl, tableName, recordNums, (System.currentTimeMillis() - beginTime)));
                sessionTo.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sessionFrom.close();
                sessionTo.close();
            }
        }
        return result.toString();
    }

    /**
     * 单据编号规则 不能覆盖,需要先判断,不存在则添加,存在则跳过
     *
     * @return
     * @throws Exception
     */
    private String insertSysNumberSerial() throws Exception {
        StringBuilder result = new StringBuilder();
        long beginTime = System.currentTimeMillis();
        String tableName = "t_sys_numberserial";
        Integer recordNums = 0, successNums = 0;
        SqlSession sessionFrom = getSession(fromSource);
        SqlSession sessionTo = getSession(toSource);
        try {
            SyncMapper mapperFrom = sessionFrom.getMapper(SyncMapper.class);
            SyncMapper mapperTo = sessionTo.getMapper(SyncMapper.class);
            List<LinkedHashMap<String, Object>> dataList = mapperFrom.selectList("select * from " + tableName);
            recordNums = dataList.size();
            String sql_count = "select count(1) from " + tableName + " where numberid='%s' and serialkey='%s'";
            String sql_insert = "insert into " + tableName + " (numberid,serialkey,serialval) VALUE ('%s','%s','%s')";
            for (LinkedHashMap<String, Object> item : dataList) {
                Integer c = mapperTo.selectCount(String.format(sql_count, item.get("numberid"), item.get("serialkey")));
                if (c > 0)
                    continue;
                if (mapperTo.insert(String.format(sql_insert, item.get("numberid"), item.get("serialkey"), item.get("serialval"))) > 0) {
                    successNums++;
                }
            }
            sessionTo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFrom.close();
            sessionTo.close();
        }
        result.append(String.format("表名:%s,记录数:%d,新增%d条,耗时:%dms", tableName, recordNums, successNums, (System.currentTimeMillis() - beginTime)));
        return result.toString();
    }

    /**
     * 获取拼接后的insert 部分语句
     *
     * @param tableName   表名
     * @param exceptId    是否排除id,从表导入的时候id字段为自增,需要忽略掉id字段
     * @param resetStatus 是否重置状态.如果要修改status状态为2,则此处填2,否则填写null
     * @return 返回map, columnSql 表示指定插入的字段sql片段,valueSql表示 值部分的sql语句(mybatis形式) flag 为false时表示两边数据结构不一致,不能同步
     */
    private Map<String, String> getInsertSqlPart(String tableName, boolean exceptId, String resetStatus, SyncMapper mapperFrom, SyncMapper mapperTo) {
        Map<String, String> result = new HashMap<String, String>();
        List<Map> columns = mapperFrom.selectTableColumn(fromSource.getDbname(), tableName);
        List<Map> to_columns = mapperTo.selectTableColumn(toSource.getDbname(), tableName);
        if (columns.size() != to_columns.size()) {
            result.put("flag", "false");
            result.put("msg", "数据表:" + tableName + "源端与目标端结构不一致,同步失败.<br>");
            return result;
        } else {
            for (Map from : columns) {
                boolean isExist = false;
                for (Map to : to_columns) {
                    if (from.get("column_name").toString().equals(to.get("column_name").toString())) {
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    result.put("flag", "false");
                    result.put("msg", "数据表:" + tableName + "源端与目标端结构不一致,同步失败.<br>");
                    return result;
                }
            }
        }
        StringBuffer valueSql = new StringBuffer();
        StringBuffer columnSql = new StringBuffer();
        ;
        for (Map columnInfo : columns) {
            if (exceptId && "id".equals(columnInfo.get("column_name")))
                continue;
            if (StringUtils.isEmpty(valueSql)) {
                columnSql.append(columnInfo.get("column_name"));
                if (StringUtils.isNotEmpty(resetStatus) && "status".equals(columnInfo.get("column_name"))) {
                    valueSql.append(resetStatus);
                } else {
                    valueSql.append("#{item." + columnInfo.get("column_name") + "}");
                }
            } else {
                columnSql.append("," + columnInfo.get("column_name"));
                if (StringUtils.isNotEmpty(resetStatus) && "status".equals(columnInfo.get("column_name"))) {
                    valueSql.append("," + resetStatus);
                } else {
                    valueSql.append("," + "#{item." + columnInfo.get("column_name") + "}");
                }
            }
        }
        result.put("columnSql", "(" + columnSql.toString() + ")");
        result.put("valueSql", "(" + valueSql.toString() + ")");
        result.put("flag", "true");
        columns.clear();
        to_columns.clear();
        columnSql = null;
        valueSql = null;
        return result;
    }

    private String insertTable(String tableName, String businessdate1, String businessdate2) throws Exception {
        SqlSession sessionFrom = getSession(fromSource);
        SqlSession sessionTo = getSession(toSource);
        String result = "";
        try {
            SyncMapper mapperFrom = sessionFrom.getMapper(SyncMapper.class);
            SyncMapper mapperTo = sessionTo.getMapper(SyncMapper.class);
            long beginTime = System.currentTimeMillis();
            //源端主表记录信息
            List<LinkedHashMap<String, Object>> data_order = mapperFrom.selectBusinessdateList(tableName, businessdate1, businessdate2);
            if (data_order.size() == 0) {
                return String.format(strTpl, tableName, 0, (System.currentTimeMillis() - beginTime));
            }
            Map sqlPart = getInsertSqlPart(tableName, false, "2", mapperFrom, mapperTo);
            if ("false".equals(sqlPart.get("flag").toString())) {
                return sqlPart.get("msg").toString();
            }
            //目标端数据插入
            Integer recordNums = mapperTo.insertList(tableName, sqlPart.get("columnSql").toString(), sqlPart.get("valueSql").toString(), data_order);
            result = String.format(strTpl, tableName, recordNums, (System.currentTimeMillis() - beginTime));
            sessionTo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFrom.close();
            sessionTo.close();
        }
        return result;
    }

    /**
     * 主从表数据同步导入
     *
     * @param tableName       主表表名
     * @param detailTableName 从表表名
     * @param parentKey       主键名
     * @param businessdate1   开始业务日期
     * @param businessdate2   结束业务日期
     * @return
     * @throws Exception
     */
    private String insertTableAndDetail(String tableName, String detailTableName, String parentKey, String businessdate1, String businessdate2) throws Exception {
        SqlSession sessionFrom = getSession(fromSource);
        SqlSession sessionTo = getSession(toSource);
        String result = "";
        try {
            SyncMapper mapperFrom = sessionFrom.getMapper(SyncMapper.class);
            SyncMapper mapperTo = sessionTo.getMapper(SyncMapper.class);
            long beginTime = System.currentTimeMillis();
            //源端主表记录信息
            List<LinkedHashMap<String, Object>> data_order = mapperFrom.selectBusinessdateList(tableName, businessdate1, businessdate2);
            if (data_order.size() == 0) {
                return String.format(strTpl, tableName, 0, (System.currentTimeMillis() - beginTime));
            }
            Map sqlPart = getInsertSqlPart(tableName, false, "2", mapperFrom, mapperTo);
            if ("false".equals(sqlPart.get("flag").toString())) {
                return sqlPart.get("msg").toString();
            }
            //目标端数据插入
            Integer recordNums = mapperTo.insertList(tableName, sqlPart.get("columnSql").toString(), sqlPart.get("valueSql").toString(), data_order);
            result = String.format(strTpl, tableName, recordNums, (System.currentTimeMillis() - beginTime));
            //先删除目标端当前业务日期的数据
            mapperTo.deleteBusinessdateDetailList(tableName, detailTableName, parentKey, businessdate1, businessdate2);
            long detail_BeginTime = System.currentTimeMillis();
            //目标端子表记录
            List<LinkedHashMap<String, Object>> data_detail = mapperFrom.selectBusinessdateDetailList(tableName, detailTableName, parentKey, businessdate1, businessdate2);
            if (data_detail.size() == 0) {
                result += String.format(strTpl, detailTableName, 0, (System.currentTimeMillis() - detail_BeginTime));
                return result;
            }
            Map detail_SqlPart = getInsertSqlPart(detailTableName, true, null, mapperFrom, mapperTo);
            if ("false".equals(detail_SqlPart.get("flag").toString())) {
                return result + sqlPart.get("msg").toString();
            }
            //目标端数据插入
            Integer detail_RecordNums = mapperTo.insertList(detailTableName, detail_SqlPart.get("columnSql").toString(), detail_SqlPart.get("valueSql").toString(), data_detail);
            result += String.format(strTpl, detailTableName, detail_RecordNums, (System.currentTimeMillis() - detail_BeginTime));
            sessionTo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sessionFrom.close();
            sessionTo.close();
        }

        return result;
    }
}
