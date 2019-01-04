package com.hd.agent.phone.action;

import com.hd.agent.common.action.BaseAction;
import com.hd.agent.common.annotation.UserOperateLog;
import com.hd.agent.common.util.CommonUtils;
import com.hd.agent.common.util.OfficeUtils;
import com.hd.agent.crm.model.CrmVisitRecord;
import com.hd.agent.crm.model.CrmVisitRecordDetail;
import com.hd.agent.crm.service.ICrmVisitPlanService;
import com.hd.agent.crm.service.ICrmVisitRecordService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 客户拜访手机相关操作action
 * Created by chenwei on 2015-04-07.
 */
public class CustomerVisitAction extends BaseAction{
    /**
     * 客户拜访计划service
     */
    private ICrmVisitPlanService crmVisitPlanService;
    /**
     * 客户拜访记录service
     */
    private ICrmVisitRecordService crmVisitRecordService;

    public ICrmVisitPlanService getCrmVisitPlanService() {
        return crmVisitPlanService;
    }

    public void setCrmVisitPlanService(ICrmVisitPlanService crmVisitPlanService) {
        this.crmVisitPlanService = crmVisitPlanService;
    }

    public ICrmVisitRecordService getCrmVisitRecordService() {
        return crmVisitRecordService;
    }

    public void setCrmVisitRecordService(ICrmVisitRecordService crmVisitRecordService) {
        this.crmVisitRecordService = crmVisitRecordService;
    }

    /**
     * 同步用户的客户拜访计划明细数据
     * @return
     * @throws Exception
     */
    public String getSyncCustomerVistPlan() throws Exception{
        String synctime = request.getParameter("synctime");
        Map map = crmVisitPlanService.getCrmVisitPlanBySysUser(synctime);
        if(null!=map){
            map.put("flag",true);
            addJSONObject(map);
        }else{
            addJSONObject("flag",false);
        }
        return "success";
    }

    /**
     * 上传客户拜访记录
     * @return
     * @throws Exception
     */
    @UserOperateLog(key="phone",type=2)
    public String uploadVisitRecord() throws Exception{
        Map returnMap = new HashMap();
        boolean flag = false;
        String msg = "";
        String returnBillid = "";
        String json = request.getParameter("json");
        if(StringUtils.isNotEmpty(json)){
            JSONObject jsonObject = JSONObject.fromObject(json);
            String oldbillid =jsonObject.getString("billid");
            String keyid = oldbillid;
            if(jsonObject.has("keyid")){
                keyid = jsonObject.getString("keyid");
            }
            String customerid = jsonObject.getString("customerid");
            String businessdate = jsonObject.getString("businessdate");
            String phototime = jsonObject.getString("phototime");
            String remark = jsonObject.getString("remark");
            String gps = jsonObject.getString("gps");
            String weekday = jsonObject.getString("weekday");
            String imgsrc = "";

            //判断该单据是否重复
            String rbillid = crmVisitRecordService.geCrmVisitRecordByKeyid(keyid);
            if(null==rbillid){
                String isplan = "1";
                if(jsonObject.has("isplan")){
                    isplan = jsonObject.getString("isplan");
                }
                JSONArray jsonArray = jsonObject.getJSONArray("list");

                CrmVisitRecord crmVisitRecord = new CrmVisitRecord();
                crmVisitRecord.setKeyid(keyid);
                crmVisitRecord.setIsplan(isplan);
                crmVisitRecord.setCustomerid(customerid);
                crmVisitRecord.setBusinessdate(businessdate);
                if(StringUtils.isNotEmpty(phototime)){
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    crmVisitRecord.setPhototime(sdf.parse(phototime));
                }else{
                    crmVisitRecord.setPhototime(new Date());
                }
                crmVisitRecord.setRemark(remark);
                crmVisitRecord.setGps(gps);
                crmVisitRecord.setWeekday(Integer.parseInt(weekday));

                List<CrmVisitRecordDetail> detailList = new ArrayList<CrmVisitRecordDetail>();
                for(int i=0;i<jsonArray.size();i++){
                    JSONObject detailObject = jsonArray.getJSONObject(i);
                    String id = detailObject.getString("id");
                    String brandid = detailObject.getString("brandid");
                    String standard = detailObject.getString("standard");
                    String dremark = detailObject.getString("remark");
                    String ptime = detailObject.getString("ptime");
                    String dgps = detailObject.getString("gps");

                    CrmVisitRecordDetail crmVisitRecordDetail = new CrmVisitRecordDetail();
                    crmVisitRecordDetail.setBrandid(brandid);
                    crmVisitRecordDetail.setStandard(standard);
                    crmVisitRecordDetail.setRemark(dremark);
                    crmVisitRecordDetail.setPtime(ptime);
                    crmVisitRecordDetail.setGps(dgps);
                    crmVisitRecordDetail.setFileid(id);
                    detailList.add(crmVisitRecordDetail);
                }
                //生成上传的附件
                MultiPartRequestWrapper requestWrapper = (MultiPartRequestWrapper)request;
                File[] files = requestWrapper.getFiles("file");
                String[] fileNames = requestWrapper.getFileNames("file");
                //文件存放路径
                String filepath = OfficeUtils.getFilepath();
                String path = filepath + File.separator+"visit";
                File pathfile = new File(path);
                if(!pathfile.exists()){
                    pathfile.mkdir();
                }
                //保存图片 图片与数据对应
                for(int i=0; i<files.length; i++){
                    String filename = fileNames[i];
                    String newFilename = CommonUtils.getDataNumberWithRand()+"_"+filename+".jpg";
                    File file = new File(path,newFilename);
                    String filestr = "upload/visit/"+newFilename;
                    FileUtils.copyFile(files[i], file);
                    if(filename.equals(customerid)){
                        imgsrc = filestr;
                    }else{
                        for(CrmVisitRecordDetail crmVisitRecordDetail : detailList){
                            if(filename.equals(crmVisitRecordDetail.getFileid())){
                                crmVisitRecordDetail.setImgsrc(filestr);
                            }
                        }
                    }
                }
                crmVisitRecord.setImgsrc(imgsrc);
                crmVisitRecord.setCrmVisitRecordDetailList(detailList);
                flag = crmVisitRecordService.addCrmVisitRecordAndDetail(crmVisitRecord);
                returnBillid = crmVisitRecord.getId();
                addLog("上传客户拜访记录 客户编号："+crmVisitRecord.getCustomerid()+"，单据编号："+returnBillid,flag);
            }else{
                flag = true;
                returnBillid = rbillid;
                addLog("上传客户拜访记录 客户编号："+customerid+"，单据编号："+returnBillid +",该单据是否重复上传",flag);
            }
        }
        returnMap.put("flag",flag);
        returnMap.put("billid",returnBillid);
        addJSONObject(returnMap);
        return "success";
    }
}
