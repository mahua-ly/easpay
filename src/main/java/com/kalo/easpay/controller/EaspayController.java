package com.kalo.easpay.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Panguaxe
 * @version 1.0.0
 * @date 2020年 11月 25日 星期三 22:20:05
 */
@CrossOrigin
@RestController
public class EaspayController {

    @RequestMapping({"MP_verify_rP7ufkI7M6jA0luU.txt"})
    private String weChatConfig() {
        return "rP7ufkI7M6jA0luU";
    }
}
