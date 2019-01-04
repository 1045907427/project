package com.hd.agent.hr.service.impl;

import com.hd.agent.common.service.impl.BaseServiceImpl;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.hr.service.IMapService;
import com.hd.agent.phone.dao.LocationMapper;
import com.hd.agent.phone.model.Location;
import com.hd.agent.phone.model.RouteDistance;
import com.hd.agent.phone.service.IPhoneService;
import com.hd.agent.system.model.SysParam;
import com.hd.agent.system.service.ISysParamService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xuxin on 2017/6/12 0012.
 */
public class MapServiceImpl extends BaseServiceImpl implements IMapService {
    //region 参数初始化
    private static final Logger logger = Logger.getLogger(MapServiceImpl.class);
    private static String baseUrl = "http://yingyan.baidu.com/api/v3/";
    private static CloseableHttpClient httpclient = HttpClients.custom().build();

    private LocationMapper locationMapper;

    public LocationMapper getLocationMapper() {
        return locationMapper;
    }

    public void setLocationMapper(LocationMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    private ISysParamService sysParamService;
    private IPhoneService phoneService;

    public ISysParamService getSysParamService() {
        return sysParamService;
    }

    public void setSysParamService(ISysParamService sysParamService) {
        this.sysParamService = sysParamService;
    }

    public IPhoneService getPhoneService() {
        return phoneService;
    }

    public void setPhoneService(IPhoneService phoneService) {
        this.phoneService = phoneService;
    }

    /**
     * 获取轨迹信息
     *
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @param userId       用户编号
     * @param elseLocation 如果鹰眼轨迹未获取,是否采用定位法重新计算
     * @return
     * @throws Exception
     */
    @Override
    public Map addRootByDateAndUserId(String startDate, String endDate, String userId, boolean elseLocation) throws Exception {
        Map result = new HashMap();
        StringBuilder detailMsg = new StringBuilder();
        detailMsg.append("车辆轨迹数据获取结果:");
        List<String> dateList = getAllDate(startDate, endDate);
        try {
            SysParam sys_ak = sysParamService.getSysParam("baiduWebAK");
            SysParam sys_service_id = sysParamService.getSysParam("baiduYYServerid");
            SysParam sys_radius_threshold = sysParamService.getSysParam("baiduYYRadiusThreshold");
            SysParam phoneGPSTime = sysParamService.getSysParam("PhoneGPSTime");
            if (sys_ak == null || sys_service_id == null || phoneGPSTime == null || sys_radius_threshold == null) {
                //如果未配置,并且要求采用定位法重新计算
                if (elseLocation) {
                    result = phoneService.addRootByDateAndUserId(startDate, endDate, userId);
                    return result;
                }
                String msg = "车辆轨迹数据获取异常.系统参数baiduWebAK或baiduYYServerid或PhoneGPSTime或baiduYYRadiusThreshold不存在";
                logger.error(msg);
                result.put("flag", false);
                result.put("msg", msg);
                return result;
            }
            for (String day : dateList) {
                Map<String, Long> startEndTime = getStartEndTime(CommonUtils.stringToDate(day), phoneGPSTime.getPvalue());
                List<Map> entityList = getEntityList(sys_ak.getPvalue(), sys_service_id.getPvalue(), userId, startEndTime);
                logger.debug("获取到的有轨迹用户数:" + entityList.size());
                detailMsg.append(String.format("<br>日期:%s,有轨迹用户数:%d", day, entityList.size()));
                List<String> addedUserId = new ArrayList<String>();

                Date addDate = new Date();
                Integer successNum = 0, failNum = 0, ignoreNum = 0;
                for (Map item : entityList) {
                    if (item.get("user_id") == null) {
                        ignoreNum++;
                        continue;
                    }
                    //先删除旧数据
                    phoneService.deleteRouteDistance(day, item.get("user_id").toString());
                    Double len = getDistance(sys_ak.getPvalue(), sys_service_id.getPvalue(), sys_radius_threshold.getPvalue(), item.get("entity_name").toString(), startEndTime);
                    RouteDistance distance = new RouteDistance();
                    distance.setAdddate(day);
                    distance.setAddtime(addDate);
                    distance.setUserid(item.get("user_id").toString());
                    distance.setDistance(len.intValue());
                    if (len.intValue() == 0) {
                        failNum++;
                        continue;
                    }
                    //添加新数据
                    boolean isSuccess = phoneService.addRouteDistance(distance);
                    if (isSuccess) {
                        successNum++;
                        addedUserId.add(distance.getUserid());
                    } else
                        failNum++;
                }
                detailMsg.append(String.format(",其中成功:%d,失败:%d,无用户编号:%d", successNum, failNum, ignoreNum));
                //如果指定用户没有获取到轨迹信息,并且采用定位法获取
                if (StringUtils.isNotEmpty(userId) && addedUserId.size() == 0 && elseLocation) {
                    phoneService.addRootByDateAndUserId(day, day, userId);
                    detailMsg.append(String.format("用户编号:%s,采用定位法补充获取.", userId));
                    continue;
                }
                //如果全员获取轨迹信息,并且采用定位法获取,则再取出全部人员信息,将未采集到的人员信息,通过定位法获取数据
                if (StringUtils.isEmpty(userId) && elseLocation) {
                    List<String> locationUserIds = new ArrayList<String>();
                    Map paramMap = new HashMap();
                    paramMap.put("begindate", day);
                    paramMap.put("enddate", day);
                    paramMap.put("ifgroup", 1);
                    List<Location> locations = locationMapper.getLocationListByDateAndUserId(paramMap);
                    for (Location location : locations) {
                        String addId = location.getUserid();
                        if (addedUserId.contains(addId))
                            continue;
                        phoneService.addRootByDateAndUserId(day, day, addId);
                        locationUserIds.add(addId);
                    }
                    if (locationUserIds.size() > 0)
                        detailMsg.append(String.format("用户编号:%s,采用定位法补充获取.", StringUtils.join(locationUserIds, ",")));
                }
            }
        } catch (Exception ex) {
            logger.error("车辆轨迹数据获取异常.", ex);
            result.put("flag", false);
            result.put("msg", "车辆轨迹数据获取异常." + ex.getMessage());
            return result;
        }
        result.put("flag", true);
        result.put("msg", detailMsg.toString());
        return result;
    }

    /**
     * 获取两个日期字符串之间所有的日期
     *
     * @param begindate
     * @param enddate
     * @return
     * @throws ParseException
     * @author huangzhiqian
     * @date 2016年1月20日
     */
    private List<String> getAllDate(String begindate, String enddate) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String> dateList = new ArrayList<String>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fmt.parse(begindate));

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(fmt.parse(enddate));
        //enddate>=begindate
        while (!calendar2.before(calendar)) {
            dateList.add(fmt.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
        return dateList;
    }

    /**
     * 获取今天的开始时间和结束时间的时间戳
     *
     * @param gpsTime
     * @return startTime:开始时间 endTime:结束时间
     */
    private Map<String, Long> getStartEndTime(Date day, String gpsTime) {
        Map<String, Long> result = new HashMap<String, Long>();
        String time1 = gpsTime.substring(0, gpsTime.indexOf("~"));
        String time2 = gpsTime.substring(gpsTime.indexOf("~") + 1, gpsTime.length());

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(day);
        //开始时间
        String[] t1 = time1.split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t1[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(t1[1]));
        calendar.set(Calendar.SECOND, 0);
        result.put("startTime", calendar.getTime().getTime() / 1000);
        //结束时间
        String[] t2 = time2.split(":");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(t2[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(t2[1]));
        calendar.set(Calendar.SECOND, 0);
        result.put("endTime", calendar.getTime().getTime() / 1000);
        return result;
    }

    /**
     * 获取昨天开始有上传信息的车辆
     *
     * @param ak
     * @param serviceId
     * @return
     * @throws Exception
     */
    private List<Map> getEntityList(String ak, String serviceId, String userId, Map<String, Long> startEndTime) throws Exception {
        String url_entityList = baseUrl + "entity/list";
        String query = "ak=" + ak + "&service_id=" + serviceId + "&filter=active_time:" + startEndTime.get("startTime");
        if (StringUtils.isNotEmpty(userId))
            query += "%7Cuser_id:" + userId;
        url_entityList = url_entityList + "?" + query;
        CloseableHttpResponse response = null;
        ObjectMapper mapper = new ObjectMapper();
        List<Map> result = null;
        try {
            HttpGet httpget = new HttpGet(url_entityList);
            httpget.setConfig(RequestConfig.custom().setConnectTimeout(4000).setConnectionRequestTimeout(4000).setSocketTimeout(4000).build());
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                Map map = mapper.readValue(entity.getContent(), Map.class);
                Integer status = (Integer) map.get("status");
                if (status == 0) {
                    result = (List<Map>) map.get("entities");
                } else if (status == 3003) {
                    result = new ArrayList<Map>();
                } else {
                    //返回状态码不为0
                    throw new RuntimeException("百度API返回状态码为" + map.get("status") + " 错误信息:" + map.get("message") + ".url : " + url_entityList);
                }
            }
            EntityUtils.consume(entity);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 获取对应车辆的里程数
     *
     * @param ak              百度地图ak
     * @param serviceId       百度地图serviceId
     * @param radiusThreshold 定位精度过滤
     * @param entity_name     entity唯一标识
     * @return
     * @throws Exception
     */
    private Double getDistance(String ak, String serviceId, String radiusThreshold, String entity_name, Map<String, Long> startEndTime) throws Exception {
        String url_entityList = baseUrl + "track/getdistance?ak=" + ak + "&service_id=" + serviceId + "&entity_name=" + entity_name;
        url_entityList += "&start_time=" + startEndTime.get("startTime") + "&end_time=" + startEndTime.get("endTime");
        url_entityList += "&is_processed=1&process_option=need_denoise=1,need_mapmatch=1,radius_threshold=" + radiusThreshold + ",transport_mode=driving&supplement_mode=driving";
        CloseableHttpResponse response = null;
        ObjectMapper mapper = new ObjectMapper();
        Double result = new Double("0");
        try {
            HttpGet httpget = new HttpGet(url_entityList);
            httpget.setConfig(RequestConfig.custom().setConnectTimeout(4000).setConnectionRequestTimeout(4000).setSocketTimeout(4000).build());
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                Map map = mapper.readValue(entity.getContent(), Map.class);
                if ((Integer) map.get("status") == 0) {
                    result = Double.parseDouble(map.get("distance").toString());
                } else {
                    //返回状态码不为0
                    throw new RuntimeException("百度API返回状态码为" + map.get("status") + " .url : " + url_entityList);
                }
            }
            EntityUtils.consume(entity);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
