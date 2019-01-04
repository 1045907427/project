/**
 * @(#)SysLogServiceImpl.java
 * @author chenwei
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2012-12-22 chenwei 创建版本
 */
package com.hd.agent.common.service.impl;

import com.hd.agent.common.dao.SysLogMapper;
import com.hd.agent.common.model.SysLog;
import com.hd.agent.common.service.ISysLogService;
import com.hd.agent.common.util.JSONUtils;
import com.hd.agent.common.util.PageData;
import com.hd.agent.common.util.PageMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 
 * 系统用户日志操作
 * @author chenwei
 */
public class SysLogServiceImpl implements ISysLogService {

	private SysLogMapper sysLogMapper;
	
	public SysLogMapper getSysLogMapper() {
		return sysLogMapper;
	}

	public void setSysLogMapper(SysLogMapper sysLogMapper) {
		this.sysLogMapper = sysLogMapper;
	}

	@Override
	public boolean addSysLog(SysLog sysLog) throws Exception {
		int i = sysLogMapper.addSysLog(sysLog);
		return i>0;
	}

	@Override
	public PageData showSearchSysLog(PageMap pageMap) throws Exception{
		int total=sysLogMapper.getSysLogCount(pageMap);
		List list=sysLogMapper.searchSysLog(pageMap);
		PageData pageData=new PageData(total, list, pageMap);
		return pageData;
	}
	
	public SysLog showSysLogInfo(String id) throws Exception{
		SysLog sysLog=sysLogMapper.getSysLogInfo(id);
		return sysLog;
		
	}

    @Override
    public SysLog showSysLogInfoData(String id) throws Exception {

        SysLog sysLog=sysLogMapper.getSysLogInfoData(id);
        String olddata = sysLog.getOlddata();
        String newdata = sysLog.getNewdata();

        //如果修改前后为空，设置变更字段为空
        if(StringUtils.isEmpty(olddata) && StringUtils.isEmpty(newdata)){
            sysLog.setChangedata("");

        }else if(olddata.length()<60000 && newdata.length()<60000){

            if(!"GoodsInfo".equals(sysLog.getKeyname())){
                //去掉修改前后数据里的基础商品信息
                while(olddata.contains("goodsInfo")){
                    olddata = simpleData(olddata,"goodsInfo");
                    olddata = olddata.replace(",,",",");
                }

                while(newdata.contains("goodsInfo")){
                    newdata = simpleData(newdata,"goodsInfo");
                    newdata = newdata.replace(",,",",");
                }
                //去掉修改后数据里冗余的字段
                while(newdata.contains("oldFromData")){
                    newdata = simpleData(newdata,"oldFromData");
                    newdata = newdata.replace(",,",",");
                }

            }
            Map map = sortData(olddata,newdata);
            sysLog.setNewdata((String)map.get("new"));
            sysLog.setOlddata((String)map.get("old"));

        }else{
            //数据过多的情况下，避免数据传到前台无法解析，设置为空
            sysLog.setNewdata("");
            sysLog.setOlddata("");
            sysLog.setChangedata("数据过多，日志无法显示");
        }

        String changedata = sysLog.getChangedata();
        if(changedata.length() >= 60000){
            sysLog.setChangedata("数据过多，日志无法显示");
        }
        //根据修改信息判断修改的字段
        if(StringUtils.isNotEmpty(sysLog.getChangedata())
                && olddata.length()<60000 && newdata.length()<60000 && changedata.length()<60000){

            //变更的数据处理
            Map changeMap = JSONUtils.jsonStrToMap(changedata);

            Iterator it = changeMap.entrySet().iterator();
            String changeKey = "";

            while(it.hasNext()){
                Map.Entry entry = (Map.Entry) it.next();

                Object key = entry.getKey();
                String value = entry.getValue().toString();

                if(value.contains("(空) ===> ") || value.contains("[] ===> ")){
                    changeKey += key + ",";
                }else if(value.contains("===>") && value.contains("[")){ //如果Json字符中又包含一个json字符，进行进一步拆分

                    String beforeData = value.substring(0,value.indexOf("===>"));

                    value = value.replace(beforeData,"");
                    String afterData = value.substring(value.indexOf("[") , value.length());

                    while(beforeData.contains("goodsInfo")){
                        beforeData = simpleData(beforeData,"goodsInfo");
                    }
                    //重组修改前数据
                    String[] beforeStr = beforeData.split("\\},");

                    while(afterData.contains("goodsInfo")){
                        afterData = simpleData(afterData,"goodsInfo");
                    }
                    //重组修改后数据
                    String[] afterStr = afterData.split("\\},");


                    for (int i = 0; i < beforeStr.length; i++) {

                        if(beforeStr[i].startsWith("[{")){
                            beforeStr[i] =  beforeStr[i].replace("[{","{");
                        }

                        if(beforeStr[i].endsWith("]")){
                            beforeStr[i] = beforeStr[i].replace("]","");
                        }else{
                            beforeStr[i] = beforeStr[i] + "}" ;
                        }

                        if(afterStr[i].startsWith("[{")){
                            afterStr[i] =  afterStr[i].replace("[{","{");
                        }

                        if(afterStr[i].endsWith("]")){
                            afterStr[i] = afterStr[i].replace("]","");
                        }else{
                            afterStr[i] = afterStr[i] + "}" ;
                        }
                        //以修改前的数据为标准依次对明细进行比较
                        if(i<afterStr.length){

                            if(beforeStr[i].contains(":") && afterStr[i].contains(":")){
                                Map map1 = JSONUtils.jsonStrToMap(beforeStr[i]);
                                Map map2 = JSONUtils.jsonStrToMap(afterStr[i]);

                                changeKey += JSONUtils.compareMap(map1,map2,i);
                            }
                        }
                    }
                }else{

                    int index = value.indexOf("===>");
                    String beforeData =  value.substring(0,index).trim();
                    String afterData =  value.substring(index+5,value.length()).trim();

                    Pattern pattern = Pattern.compile("[0-9.]+");
                    if(pattern.matcher(beforeData).matches() && pattern.matcher(afterData).matches()){

                        beforeData = removeInvalidZero(beforeData);
                        afterData = removeInvalidZero(afterData);

                        if(!afterData.equals(beforeData)){
                            changeKey += key + ",";
                        }
                    }else{
                        changeKey += key + ",";
                    }

                }
            }
            if(StringUtils.isNotEmpty(changeKey)){
                changeKey = changeKey.substring(0,changeKey.lastIndexOf(","));
            }
            sysLog.setChangedata(changeKey);
        }
        return sysLog;
    }

    /**
     * 对包含数字和小数点的字符串去除其中无效的零
     * @param data
     * @return
     * @throws Exception
     */
    public String removeInvalidZero(String data) throws Exception {

        if(data.indexOf(".") > 0){
            data = data.replaceAll("0+?$", "");//去掉后面无用的零
        }
        if(data.lastIndexOf(".") == data.length()-1){
            data = data.replace(".","");
        }
        return data ;
    }


    /**
     * data数据中去掉对应example（map类型）参数，方便数据解析,去除代码冗余
     * @param data
     * @return
     * @throws Exception
     */
    public String simpleData(String data,String example) throws Exception {

        int goodsInfoIndex = data.indexOf(example);
        int end = data.indexOf("}",goodsInfoIndex);
        if(end != -1){
            String goodsjson = data.substring(goodsInfoIndex-2,end+1);
            data = data.replace(goodsjson,"");
        }

        return  data ;
    }

    /**
     * 对两个map中的数据依据键值对进行排序
     * @param olddata
     * @param newdata
     * @return
     * @throws Exception
     */
    public Map sortData(String olddata , String newdata) throws Exception {
        Map map = new HashMap();
        String oldStr = "{";
        String newStr = "{";
        String otherString = "";
        String oldJSon = "";
        String newJSon = "";

        Map oldMap = JSONUtils.jsonStrToMap(olddata);
        Map newMap = JSONUtils.jsonStrToMap(newdata);

        int oldSize = oldMap.size();
        int newSize = newMap.size();

        if(newSize <= oldSize){
            Iterator<Map.Entry<String, String>> it = newMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
                String key = entry.getKey();
                //去掉批次管理参数
                if("isbatch".equals(key)){
                    continue;
                }

                String value = entry.getValue();
                if(value.contains("{")){

                    oldJSon = oldJSon + entry.getKey()+":"+oldMap.get(key)+"}";
                    newJSon = newJSon + entry.getKey()+":"+entry.getValue()+"}";

                }else if(oldMap.containsKey(key)){

                    oldStr = oldStr + entry.getKey()+":"+oldMap.get(key)+",";
                    newStr = newStr + entry.getKey()+":"+entry.getValue()+",";
                }else{
                    otherString = otherString + entry.getKey()+":"+entry.getValue()+",";
                }
            }
            newStr = newStr + otherString ;

        }else{

            Iterator<Map.Entry<String, String>> it = oldMap.entrySet().iterator();
            while(it.hasNext()){
                Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
                String key = entry.getKey();
                if(newMap.containsKey(key)){

                    oldStr = oldStr + entry.getKey()+":"+newMap.get(key)+",";
                    newStr = newStr +  entry.getKey()+":"+entry.getValue()+",";
                }else{

                    otherString = otherString + entry.getKey()+":"+entry.getValue()+",";
                }
            }
            oldStr = oldStr + otherString ;
        }
        oldStr = oldStr + oldJSon;
        newStr = newStr + newJSon;

        map.put("old",oldStr);
        map.put("new",newStr);

        return map ;
    }

    public boolean deleteSysLog(String id) throws Exception{
		int i=sysLogMapper.deleteSysLog(id);
		return i>0;
	}

	@Override
	public boolean clearSysLog() throws Exception {
		sysLogMapper.clearSysLog();
		return true;
	}

    /**
     * 添加系统修改数据记录
     *
     * @param id         编号
     * @param olddata    修改前数据
     * @param newdata    修改后数据
     * @param changedata 变更数据
     * @return
     * @throws Exception
     */
    @Override
    public boolean addSysLogData(String id, String olddata, String newdata, String changedata) throws Exception {
        int i = sysLogMapper.insertSysLogData(id, olddata, newdata, changedata);
        return i>0;
    }
}

