/**
 * @(#)SmsCommander.java
 *
 * @author zhanghonghui
 *
 * 版本历史
 * -------------------------------------------------------------------------
 * 时间 作者 内容
 * -------------------------------------------------------------------------
 * 2013-4-12 zhanghonghui 创建版本
 */
package com.hd.agent.message.sms;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.IOutboundMessageNotification;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.Message.MessageEncodings;
import org.smslib.modem.SerialModemGateway;

/**
 * 
 * 
 * @author zhanghonghui
 */
public class SmsSender {
	
	private static Service service = null;

	protected static final Logger logger = Logger.getLogger(SmsSender.class);

	public static boolean creatService() {
		service = Service.getInstance();
		
//		SerialModemGateway gateway = new SerialModemGateway("SMS", "COM3",
//				9600, "", ""); 
		//Win32Driver
		
		Map<String,String> map = getProperties("sms.properties");
		
		SerialModemGateway gateway = new SerialModemGateway(map.get("gatewayId"), map.get("comPort"),
				Integer.parseInt(map.get("baudRate")), map.get("manufacturer"), map.get("model"));

		gateway.setInbound(true);
		gateway.setOutbound(true);
		if(null!=map.get("smscNumber") && null!=map.get("smscNumber").toString() && !"".equals(map.get("smscNumber").toString().trim())){
			gateway.setSmscNumber(map.get("smscNumber").toString());
		}
		try {
			//启用轮循模式
			service.S.SERIAL_POLLING = true;
			service.addGateway(gateway);

			service.startService();
			
//			System.out.println("Modem connected.");
//			System.out.println("Modem Information:");
//			System.out.println("  Manufacturer: " + gateway.getManufacturer());
//			System.out.println("  Model: " + gateway.getModel());
//			System.out.println("  Serial No: " + gateway.getSerialNo());
//			System.out.println("  SIM IMSI: " + gateway.getImsi());
//			System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
//			System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");

		} catch (Exception ex) {
			logger.error("短信猫初始化异常", ex);
			return false;
		}
		return true;
	}
	
	public static Service getService() {
		if (service == null) {
			creatService();
		}
		return service;
	}

	public static void main(String args[]) {
		// creatService();
		sendSms("13968620868", "发送测试啊测试");
		// close();
//		System.out.println( System.getProperty("java.library.path"));
	}

	public static boolean sendSms(String mobile, String content) {
		if (service == null) {
			if(!creatService()){
				close();
				service=null;
				return false;
			}
		}
		boolean flag=false;
		OutboundMessage msg = new OutboundMessage(mobile, content);
		msg.setEncoding(MessageEncodings.ENCUCS2);
		int irun=1;
		while(irun<3 && !flag){
			try {
				flag=service.sendMessage(msg);
				//System.out.println(msg);
				break;
			} catch (Exception ex) {
				if(irun==2){
					logger.error("短信猫发送短信异常",ex);
				}
				irun=irun+1;
			}
		}
		return flag;
	}

	public static void close() {
		try {
			logger.info("短信猫关闭");
			//System.out.println("Modem disconnected.");
			service.stopService();
		} catch (Exception ex) {

			logger.error("短信猫关闭异常", ex);
		}
	}
	/**
	 * 读取配置文件
	 * @param path
	 * @return
	 * @author zhanghonghui 
	 * @date 2013-4-17
	 */
	public static Map<String,String> getProperties(String path) {
		Properties p = new Properties();
		InputStream inputStream = SmsSender.class.getResourceAsStream(path);
		
		try {
			p.load(inputStream);
		} catch (IOException ex) {
			//ex.printStackTrace();
			logger.error("短信猫配置文件读取异常", ex);
		}
		
		Map<String,String> map = new HashMap<String,String>();
		Iterator<Object> it =p.keySet().iterator();
		String key="";
		while(it.hasNext()){
			key = it.next().toString();
			map.put(key, p.getProperty(key).trim());
		}
		return map;

	}
	
   /**
    * 短信发送成功后，调用该接口。并将发送短信的网关和短信内容对象传给process接口 
    * @author zhanghonghui
    * 时间 2013-4-17
    */
   public class OutboundNotification implements IOutboundMessageNotification  
   {  
       public void process(AGateway gateway, OutboundMessage msg)  
       {  
           System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());  
           System.out.println(msg);  
       }  
   }  

}

