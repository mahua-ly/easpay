package com.kalo.easpay.controller;

import com.alibaba.fastjson.JSON;
import com.kalo.easpay.common.dto.AccessTokenDTO;
import com.kalo.easpay.common.dto.PullUserInfoDTO;
import com.kalo.easpay.common.enums.LoginWayEnum;
import com.kalo.easpay.common.enums.ResultCode;
import com.kalo.easpay.common.result.ResponseResult;
import com.kalo.easpay.common.service.CommonService;
import com.kalo.easpay.common.service.LoginService;
import com.kalo.easpay.common.vo.AppLoginVO;
import com.kalo.easpay.service.UserAuthInfoService;
import com.kalo.easpay.utils.http.HttpUtil;
import com.kalo.easpay.utils.http.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 20日 星期五 16:44:53
 */
@Slf4j
@RefreshScope
@CrossOrigin
@RestController
@RequestMapping("/app/api/home/")
public class HomeController {

    /**
     * 微信开发者APPID
     */
    @Value("${wechat.pub.plat.AppID}")
    private String APPID;
    /**
     * 微信开发者AppSecret
     */
    @Value("${wechat.pub.plat.AppSecret}")
    private String AppSecret;

    /**
     * 网页授权 AccessToken 请求URL
     */
    @Value("${wechat.pub.plat.authorize.getAccessTokenURL}")
    private String getAccessTokenURL;
    /**
     * 拉取用户微信信息URL
     */
    @Value("${wechat.pub.plat.authorize.snsUserinfoURL}")
    private String snsUserinfoURL;

    private final CommonService commonService;
    private final LoginService loginService;
    private final UserAuthInfoService userAuthInfoService;

    @Autowired
    public HomeController(CommonService commonService, LoginService loginService, UserAuthInfoService userAuthInfoService) {
        this.commonService = commonService;
        this.loginService = loginService;
        this.userAuthInfoService = userAuthInfoService;
    }
    /**
     * TODO     登陆
     * @Title   login
     * @author  Panguaxe
     * @param request
     * @param response
     * @param loginVO
     * @param bindingResult
     * @return  com.kalo.easpay.common.result.ResponseResult
     * @date    2020/11/22 17:04
     */
    @RequestMapping("login")
    public ResponseResult login(HttpServletRequest request, HttpServletResponse response, @Validated AppLoginVO loginVO, BindingResult bindingResult) {
        ResponseResult result = commonService.paramVerify(new ResponseResult(), bindingResult);
        if (ResultCode.SUCCESS.getErrcode().equals(result.getErrcode())) {
            if (LoginWayEnum.ways().contains(loginVO.getType())){
                loginVO.setClientIp(RequestUtil.getIpAddr(request));
                loginService.login(response,result,loginVO);
            }else {
                result.customError(ResultCode.BAD_PARAMS_ERROR.getErrcode(),ResultCode.BAD_PARAMS_ERROR.getErrmsg().replace("MSG","未知登录方式"),"登录方式：" + loginVO.getType());
            }
        }
        return result;
    }

    /**
     * TODO     微信授权回调
     * @Title   weChatAuthCallBack
     * @author  Panguaxe 
     * @param request
     * @return  com.kalo.easpay.common.result.ResponseResult 
     * @date    2020/11/22 17:03
     */
    @RequestMapping("weChatAuthCallBack")
    public ResponseResult weChatAuthCallBack(HttpServletRequest request){
        ResponseResult responseResult = new ResponseResult();
        String code = request.getParameter("code");
        //1. 拼装获取 AccessToken 请求地址
        String url = getAccessTokenURL.replace("APPID", APPID).replace("SECRET", AppSecret).replace("CODE", code);
        log.info("网页授权 AccessToken 请求URL：{}",url);
        //2. 通过授权Code码换取网页授权 AccessToken
        String accessTokenData = HttpUtil.doGetAuth(url);
        AccessTokenDTO tokenDTO = JSON.parseObject(accessTokenData, AccessTokenDTO.class);
        log.info("网页授权 AccessToken 返回报文：{}",JSON.toJSONString(tokenDTO));
        //3. AccessToken 获取成功调用拉取用户信息(需scope为 snsapi_userinfo)
        if (StringUtils.isNotBlank(tokenDTO.getAccess_token()) && StringUtils.isNotBlank(tokenDTO.getOpenid())) {
            //4. 拉取用户微信信息URL拼接
            String pullUserInfoUrl = snsUserinfoURL.replace("ACCESS_TOKEN", tokenDTO.getAccess_token()).replace("OPENID", tokenDTO.getOpenid());
            log.info("拉取用户微信信息请求URL：{}",pullUserInfoUrl);
            //5. 调起拉取用户信息请求
            String pullUserInfo = HttpUtil.doGetAuth(pullUserInfoUrl);
            PullUserInfoDTO pullUserInfoDTO = JSON.parseObject(pullUserInfo, PullUserInfoDTO.class);
            log.info("拉取用户微信信息返回报文：{}",pullUserInfo);
            //6.保存 OR 更新微信授权信息
            userAuthInfoService.weChatAuth(pullUserInfoDTO);
            responseResult.success(pullUserInfoDTO);
        }else {
            //3.1 AccessToken 获取失败 返回错误信息
            responseResult.customError(tokenDTO.getErrcode(),tokenDTO.getErrmsg(),tokenDTO);
        }
        return responseResult;
    }



}
