package com.hd.agent.wechat.action;

import com.hd.agent.basefiles.model.CustomerUser;
import com.hd.agent.common.util.CommonUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by wanghongteng on 2017/5/22.
 */
public class WechatUserAction extends BaseWechatAction {

    private CustomerUser customerUser;

    public CustomerUser getCustomerUser() {
        return customerUser;
    }

    public void setCustomerUser(CustomerUser customerUser) {
        this.customerUser = customerUser;
    }


    /**
     * 显示微信用户信息
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public String index()  throws Exception {

        return "success";
    }

    /**
     * 显示微信用户注册界面
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public String showWechatRegisterPage()  throws Exception {
        return "success";
    }

    /**
     * 获取验证码
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public String showCheckIamge() throws Exception{

        try{
            // 告诉客户端，输出的格式
            response.setContentType("image/jpeg");

            int width = 80;
            int height = 40;
            int lines = 5;
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = img.getGraphics();
            OutputStream os=response.getOutputStream();
            // 设置背景色
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, width, height);

            // 设置字体
            g.setFont(new Font("宋体", Font.BOLD, 20));

            // 随机数字
            Random r = new Random(new Date().getTime());
            String str="";
            for (int i = 0; i < 4; i++) {
                int a = r.nextInt(10);
                if(StringUtils.isEmpty(str)){
                    str=""+a;
                }else{
                    str=str+a;
                }
                int y = 10 + r.nextInt(20);// 10~30范围内的一个整数，作为y坐标

                Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
                g.setColor(c);

                g.drawString("" + a, 5 + i * width / 4, y);
            }

            // 干扰线
            for (int i = 0; i < lines; i++) {
                Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
                g.setColor(c);
                g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
            }

            g.dispose();// 类似于流中的close()带动flush()---把数据刷到img对象当中
            ImageIO.write(img, "JPG",os);
            //注意看以下几句的使用
            os.flush();
            os.close();
            os=null;
            response.flushBuffer();

            request.getSession().removeAttribute("imagenumber");
            request.getSession().setAttribute("imagenumber",str);
            Map map=new HashMap();
            addJSONObject(map);
        }catch(Exception e){

        }
         return "success";
    }

    /**
     * 检测微信注册手机号码是否重复
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public String checkRepeatPhone()throws Exception {
        String phone = request.getParameter("phone");
        boolean flag = customerUserService.checkRepeatPhone(phone);
        Map map = new HashMap();
        map.put("flag",flag);
        addJSONObject(map);
        return "success";
    }


    /**
     * 图片验证码验证
     * @return java.util.Map
     * @throws
     * @author wanghongteng
     * @date 2017/5/22
     */

    public String checkImageNumber() {
        Map map=new HashMap();
        String num=request.getParameter("num");
        String imagenumber=(String)request.getSession().getAttribute("imagenumber");
        if(num.equals(imagenumber)){
            map.put("flag",true);
        }else{
            map.put("flag",false);
        }
        addJSONObject(map);
        return "success";
    }

    /**
     * 短信验证
     */

    public String sendMessage() throws Exception{
        String mobile=request.getParameter("mobile");
        String type=request.getParameter("type");
        Map resMap=wechatUserService.sendMessage(mobile,type);
        Boolean flag=(Boolean)resMap.get("flag");
//        String returnMsg= DigestUtils.md5Hex((String) resMap.get("randomNum"));
        Map map=new HashMap();
        map.put("flag",flag);
//        map.put("returnNumberMsg",(String) resMap.get("randomNum"));
        request.getSession().setAttribute("returnNumberMsg",(String) resMap.get("randomNum"));
        addJSONObject(map);
        return "success";
    }
    /**
     * 新增客户用户
     *
     * @return
     * @throws Exception
     * @author wanghongteng
     * @date 2017/5/22
     */
    public String addInviteCustomerUser() throws Exception{
        //添加人用户编号
        customerUser.setPassword(CommonUtils.MD5("123456"));
        boolean flag = customerUserService.addInviteCustomerUser(customerUser);
        Map map = new HashMap();
        map.put("flag",flag);
        addJSONObject(map);
        return "success";
    }


}
