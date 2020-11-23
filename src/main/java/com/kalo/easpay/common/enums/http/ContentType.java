package com.kalo.easpay.common.enums.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ContentType {

    /***/
    FORM_URLENCODED("application/x-www-form-urlencoded"),
    /***/
    MULTIPART("multipart/form-data"),
    /***/
    JSON("application/json"),
    /***/
    XML("application/xml"),
    /***/
    TEXT_PLAIN("text/plain"),
    /***/
    TEXT_XML("text/xml"),
    /***/
    TEXT_HTML("text/html");

    private String value;

}
