package com.hd.agent.basefiles.dao;

import com.hd.agent.basefiles.model.CustomerUser;
import com.hd.agent.common.util.PageMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerUserMapper {
    /**
     * 显示系统用户信息
     * @param mobilephone
     * @return
     * @author wanghongteng
     * @date 2017/5/22
     */
    public CustomerUser showCustomerUserInfo(@Param("mobilephone")String mobilephone);
    /**
     * 客户用户绑定客户
     *
     * @return
     * @author wanghongteng
     * @date 2017/5/22
     */
    public int addCustomerUserAddCustomer(@Param("mobilephone") String mobilephone,@Param("customerid") String customerid);
    /**
     * 检测微信注册手机号码是否重复
     *
     * @return
     * @author wanghongteng
     * @date 2017/5/22
     */
    public int checkRepeatPhone(@Param("phone") String phone);

    /**
     * 新增客户用户
     *
     * @return
     * @author wanghongteng
     * @date 2017/5/22
     */
    int addCustomerUser(CustomerUser customerUser);

    /**
     * 获取客户用户列表
     * @param pageMap
     * @return
     * @author wanghongteng
     * @date 2017/5/26
     */
    public List SelectCustomerUserList(PageMap pageMap);

    /**
     * 获取客户用户列表数量
     * @param pageMap
     * @return
     * @author wanghongteng
     * @date 2017/5/26
     */
    public int SelectCustomerUserListCount(PageMap pageMap);


    /**
     * 启用客户用户
     * @param mobilephone
     * @return
     * @author wanghongteng
     * @date 2017/5/26
     */
    public int enableCustomerUser(@Param("mobilephone")String mobilephone);

    /**
     * 禁用客户用户
     * @param mobilephone
     * @return
     * @author wanghongteng
     * @date 2017/5/26
     */
    public int disableCustomerUser(@Param("mobilephone")String mobilephone);
}