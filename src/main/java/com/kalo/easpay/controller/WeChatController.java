package com.kalo.easpay.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 22日 星期日 15:57:17
 */
@CrossOrigin
@RestController
public class WeChatController {

    @RequestMapping({"MP_verify_rP7ufkI7M6jA0luU.txt"})
    private String returnConfigFile() {
        return "rP7ufkI7M6jA0luU";
    }
}
