package com.kalo.easpay.utils.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 22日 星期日 15:08:06
 */
@Slf4j
public class HttpUtil {


    public static String doGetAuth(String url) {
        String response = "";
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpResponse = client.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            HttpEntity entity = httpResponse.getEntity();
            response = EntityUtils.toString(entity, "UTF-8");
        } catch (IOException e) {
            log.info("doGetAuth HttpClient execute IOException:{}", e.getMessage());
        } finally {
            //释放链接
            httpGet.releaseConnection();
        }
        return response;
    }
}
