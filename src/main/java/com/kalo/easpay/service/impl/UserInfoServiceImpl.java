package com.kalo.easpay.service.impl;

import com.kalo.easpay.common.enums.LoginWayEnum;
import com.kalo.easpay.common.result.ResponseResult;
import com.kalo.easpay.common.vo.AppLoginVO;
import com.kalo.easpay.domain.entity.UserInfo;
import com.kalo.easpay.domain.mapper.UserInfoMapper;
import com.kalo.easpay.service.UserInfoService;
import com.kalo.easpay.utils.date.DateUtil;
import com.kalo.easpay.utils.generator.SnowFlakeID;
import com.kalo.easpay.utils.obj.ObjectUtil;
import com.kalo.easpay.utils.security.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 21日 星期六 17:24:16
 */
@Slf4j
@Service
@RefreshScope
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoMapper userInfoMapper;

    @Autowired
    public UserInfoServiceImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }
    /**
     * TODO     根据账号查找用户信息
     * @Title   findUserInfo
     * @author  Panguaxe
     * @param userName
     * @return  com.kalo.easpay.domain.entity.UserInfo
     * @date    2020/11/21 22:55
     */
    @Override
    public UserInfo findUserInfo(String userName) {
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("userName", userName);
        return userInfoMapper.selectOneByExample(example);
    }
    /**
     * TODO     根据手机号查找用户信息
     * @Title   getUserInfo
     * @author  Panguaxe
     * @param mobile
     * @return  com.kalo.easpay.domain.entity.UserInfo
     * @date    2020/11/21 22:55
     */
    @Override
    public UserInfo getUserInfo(String mobile) {
        Example example = new Example(UserInfo.class);
        example.createCriteria().andEqualTo("mobile", mobile);
        return userInfoMapper.selectOneByExample(example);
    }
    /**
     * TODO     用户注册
     * @Title   registerUser
     * @author  Panguaxe 
     * @param mobile
     * @return  com.kalo.easpay.domain.entity.UserInfo 
     * @date    2020/11/21 22:55
     */
    @Override
    public UserInfo registerUser(String mobile,String clientIp) {
        String salt = PasswordUtil.genSalt();
        String password = PasswordUtil.defaultPwd(mobile, salt);
        Date time = DateUtil.currentDateTime();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(SnowFlakeID.workerId());
        userInfo.setUserName(mobile);
        userInfo.setNickName("游客");
        userInfo.setInviteCode(ObjectUtil.genInviteCode());
        userInfo.setMobile(mobile);
        userInfo.setHeadImg("HeadImg");
        userInfo.setPassword(password);
        userInfo.setSalt(salt);
        userInfo.setLastLoginTime(time);
        userInfo.setLastLoginIp(clientIp);
        userInfo.setCreateTime(time);
        userInfo.setScore(0);
        userInfo.setSex(0);
        userInfo.setIsAuth(0);
        userInfo.setIsAdmin(0);
        userInfo.setIsUsable(1);
        userInfoMapper.insertSelective(userInfo);
        return userInfo;
    }
    /**
     * TODO     登陆：手机号验证码、账号密码
     * @Title   login
     * @author  Panguaxe
     * @param result
     * @param request
     * @return  com.kalo.easpay.common.result.ResponseResult
     * @date    2020/11/21 22:56
     */
    @Override
    public ResponseResult login(ResponseResult result, AppLoginVO request) {
        UserInfo userInfo = null;
        if (LoginWayEnum.MOBILE_SMS_CODE.getType().equals(request.getType())) {
            //1. 验证码校验

            // 判断验证码是否通过：存在、匹配、有效时间内

            //校验通过后处理返回数据
            userInfo = getUserInfo(request.getMobile());
            if (ObjectUtil.isBlank(userInfo)) {
                //创建用户信息
                userInfo = registerUser(request.getMobile(),request.getClientIp());
            }
        }
        if (LoginWayEnum.USER_NAME.getType().equals(request.getType())) {
            userInfo = findUserInfo(request.getUsername());
            if (ObjectUtil.isBlank(userInfo)) {
                return result.fail("账号不存在");
            }
            boolean checkPwd = PasswordUtil.checkPassword(request.getUsername(), request.getPassword(), userInfo.getSalt(), userInfo.getPassword());
            if (!checkPwd) {
                return result.fail("密码错误,请重新输入!");
            }
        }
        //用户响应信息
        userInfo.setToken("token");
        userInfo.setOpenid("openid");
        return result.success(userInfo);
    }


}
