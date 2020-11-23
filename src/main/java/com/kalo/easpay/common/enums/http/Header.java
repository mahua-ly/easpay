package com.kalo.easpay.common.enums.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum Header {

    /***/
    AUTHORIZATION("Authorization"),
    DATE("Date"),
    CONNECTION("Connection"),
    MIME_VERSION("MIME-Version"),
    TRAILER("Trailer"),
    TRANSFER_ENCODING("Transfer-Encoding"),
    UPGRADE("Upgrade"),
    VIA("Via"),
    CACHE_CONTROL("Cache-Control"),
    PRAGMA("Pragma"),
    CONTENT_TYPE("Content-Type"),
    HOST("Host"),
    REFERER("Referer"),
    ORIGIN("Origin"),
    USER_AGENT("User-Agent"),
    ACCEPT("Accept"),
    ACCEPT_LANGUAGE("Accept-Language"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_CHARSET("Accept-Charset"),
    COOKIE("Cookie"),
    CONTENT_LENGTH("Content-Length"),
    WWW_AUTHENTICATE("WWW-Authenticate"),
    SET_COOKIE("Set-Cookie"),
    CONTENT_ENCODING("Content-Encoding"),
    CONTENT_DISPOSITION("Content-Disposition"),
    ETAG("ETag"),
    LOCATION("Location");

    private String value;
}
