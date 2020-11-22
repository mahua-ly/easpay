package com.kalo.easpay.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 20日 星期五 16:44:53
 */
@Slf4j
public class RequestUtil {

    /**
     * TODO     URL---解码
     * @Title   URLDecoder
     * @author  Panguaxe
     * @param url
     * @return  java.lang.String
     * @date    2020/11/22 14:36
     */
    public static String URLDecoder(String url) {
        if (StringUtils.isNotBlank(url)){
            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return url;
            }
        }
        return url;
    }

    /**
     * TODO     URL---编码
     * @Title   URLEncode
     * @author  Panguaxe
     * @param url
     * @return  java.lang.String
     * @date    2020/11/22 14:36
     */
    public static String URLEncode(String url) {
        if (StringUtils.isNotBlank(url)) {
            try {
                url = URLEncoder.encode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return url;
            }
        }
        return url;
    }
    /**
     * @Title removeParam
     * @Author Panguaxe
     * @param request
     * @param paramName
     * @return java.lang.String
     * @Time   2020/11/20 17:37
     * @Description      TODO  移除request指定参数
     */
    public String removeParam(HttpServletRequest request, String paramName) {
        String result = "";
        Enumeration<String> keys = request.getParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (key.equals(paramName)) {
                continue;
            }
            if (StringUtils.isBlank(result)) {
                result = key + "=" + request.getParameter(key);
            } else {
                result += "&" + key + "=" + request.getParameter(key);
            }
        }
        return result;
    }

    /**
     * @Title getBasePath
     * @Author Panguaxe
     * @param request
     * @return java.lang.String
     * @Time   2020/11/20 17:40
     * @Description      TODO  获取请求BasePath
     */
    public static String getBasePath(HttpServletRequest request) {
        StringBuilder basePath = new StringBuilder();
        basePath.append(request.getScheme());
        basePath.append("://");
        basePath.append(request.getServerName());
        int port = request.getServerPort();
        if ("http".equalsIgnoreCase(request.getScheme()) && 80 != port) {
            basePath.append(":").append(port);
        } else if ("https".equalsIgnoreCase(request.getScheme()) && port != 443) {
            basePath.append(":").append(port);
        }
        return basePath.toString();
    }

    /**
     * @Title getParamsMap
     * @Author Panguaxe 
     * @param request
     * @return java.util.Map<java.lang.String,java.lang.String> 
     * @Time   2020/11/20 17:41
     * @Description      TODO  获取请求参数
     */
    public static Map<String, String> getParamsMap(HttpServletRequest request) {
        Enumeration parameterNames = request.getParameterNames();
        Map<String, String> result = new HashMap<>();
        while (parameterNames.hasMoreElements()) {
            String parameterName = (String) parameterNames.nextElement();
            result.put(parameterName, request.getParameter(parameterName));
        }
        return result;
    }

    /**
     * @Title getIpAddr
     * @Author Panguaxe
     * @param request
     * @return java.lang.String
     * @Time   2020/11/20 17:49
     * @Description      TODO  获取终端IP
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        // 如果是多级代理，那么取第一个ip为客户端ip
        if (ip != null && ip.contains(",")) {
            ip = ip.substring(0, ip.indexOf(",")).trim();
        }
        return ip;
    }

    /**
     * @Title getTerminalIP
     * @Author Panguaxe
     * @param request
     * @return java.lang.String
     * @Time   2020/11/20 17:49
     * @Description      TODO  获取服务端IP
     */
    public static String getTerminalIP(HttpServletRequest request) throws UnknownHostException {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                //根据网卡取本机配置的IP
                ipAddress = InetAddress.getLocalHost().getHostAddress();
            }
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        return ipAddress;
    }

    /**
     * @Title getHostIp
     * @Author Panguaxe
     * @return java.lang.String
     * @Time   2020/11/20 17:49
     * @Description      TODO  获取本机IP
     */
    public static String getHostIp() throws SocketException {
        String hostIP = "0:0:0:0:0:0:0:1";//默认本机IP 0:0:0:0:0:0:0:1
        InetAddress ip = null;
        boolean bFindIP = false;
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		while (netInterfaces.hasMoreElements()) {
			if (bFindIP) {
				break;
			}
			Enumeration<InetAddress> ips = netInterfaces.nextElement().getInetAddresses();
			while (ips.hasMoreElements()) {
				ip = ips.nextElement();
				if (!ip.isLoopbackAddress() && ip.getHostAddress().matches("(\\d{1,3}\\.){3}\\d{1,3}")) {
					bFindIP = true;
					break;
				}
			}
		}
        return null != ip ? ip.getHostAddress() : hostIP;
    }

    /**
     * @Title isAjaxRequest
     * @Author Panguaxe
     * @param request
     * @return boolean
     * @Time   2020/11/20 17:49
     * @Description      TODO  判断是否为Ajax请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        return requestType != null && requestType.equals("XMLHttpRequest");
    }

}
