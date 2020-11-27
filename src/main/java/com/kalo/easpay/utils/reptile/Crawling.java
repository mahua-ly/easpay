package com.kalo.easpay.utils.reptile;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 27日 星期五 09:47:26
 */
@Slf4j
public class Crawling {

    private static final String BASE_URL = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2020/";
    private static final Charset CHARSET = CharsetUtil.CHARSET_GBK;

    public static void main(String[] args) throws InterruptedException {
        String provinceUrl = BASE_URL + "index.html";
        crawlingProvinceData(provinceUrl);

//        String cityUrl = BASE_URL + "11.html";
//        crawlingCityData(cityUrl, new Node());
//
//        String countyUrl = BASE_URL + "15/1509.html";
//        crawlingCountyData(countyUrl, new Node());
//
//        String towntrUrl = BASE_URL + "15/09/150981.html";
//        crawlingTowntrData(towntrUrl, new Node());

    }

    /**
     * TODO     爬取省数据
     * @param url
     * @return void
     * @Title crawlingProvinceData
     * @author Panguaxe
     * @date 2020/11/27 10:00
     */
    private static void crawlingProvinceData(String url) throws InterruptedException {
        List<Node> provinces = new LinkedList<>();
        String htmlStr = HttpUtil.get(url, CHARSET);
        Document document = Jsoup.parse(htmlStr);
        // 获取 class='provincetr' 的元素
        Elements elements = document.getElementsByClass("provincetr");
        for (Element element : elements) {
            // 获取 elements 下属性是 href 的元素
            Elements links = element.getElementsByAttribute("href");
            for (Element link : links) {
                String href = link.attr("href");
                String provinceCode = href.substring(0, 2);
                if (provinceCode.startsWith("11")){
                    String provinceName = link.text();
                    Node provinceNode = Node.builder().code(provinceCode).name(provinceName).dataFromUrl(url).build();
                    log.info("省级数据:  {}", JSON.toJSONString(provinceNode));
                    //爬取市级数据
                    crawlingCityData(BASE_URL + href, provinceNode);
                    provinces.add(provinceNode);
                }
            }
        }
        StaticLog.info(JSONUtil.toJsonPrettyStr(provinces));
    }
    /**
     * TODO     爬取市级数据
     * @Title   crawlingCityData
     * @author Panguaxe
     * @param url
     * @param provinceNode
     * @return  void
     * @date    2020/11/27 10:05
     */
    private static void crawlingCityData(String url, Node provinceNode) throws InterruptedException {
        String htmlStr = HttpUtil.get(url, CHARSET);
        Document document = Jsoup.parse(htmlStr);
        Elements trs = document.getElementsByClass("citytr");
        List<Node> cities = new LinkedList<>();
        for (Element tr : trs) {
            Elements links = tr.getElementsByTag("a");
            String href = links.get(0).attr("href");
            String cityCode = links.get(0).text().substring(0, 4);
            String cityName = links.get(1).text();
            Node cityNode = Node.builder().name(cityName).code(cityCode).dataFromUrl(url).build();
            log.info("市级数据:  {}", JSON.toJSONString(cityNode));
            //爬取区县数据
            crawlingCountyData(BASE_URL + href, cityNode);
            cities.add(cityNode);
        }
        provinceNode.setNodes(cities);
    }
    /**
     * TODO     爬取区县数据
     * @Title   crawlingCountyData
     * @author Panguaxe
     * @param url
     * @param cityNode
     * @return  void
     * @date    2020/11/27 10:07
     */
    private static void crawlingCountyData(String url, Node cityNode) throws InterruptedException {
        List<Node> counties = new LinkedList<>();
        String htmlStr = HttpUtil.get(url, CHARSET);
        Document document = Jsoup.parse(htmlStr);
        Elements trs = document.getElementsByClass("countytr");
        for (Element tr : trs) {
            Elements links = tr.getElementsByTag("a");
            if (links == null || links.size() != 2) {
                continue;
            }
            String href = links.get(0).attr("href");
            String countyCode = links.get(0).text().substring(0, 6);
            String countyName = links.get(1).text();
            Node countyNode = Node.builder().name(countyName).code(countyCode).dataFromUrl(url).build();
            log.info("区县数据:  {}", JSON.toJSONString(countyNode));
            crawlingTowntrData(BASE_URL + href.subSequence(2, 5).toString() + "/" + href, countyNode);
            counties.add(cityNode);
        }
        cityNode.setNodes(counties);
    }

    /**
     * TODO     爬取乡镇数据
     * @Title   crawlingTowntrData
     * @author Panguaxe
     * @param url
     * @param countyNode
     * @return  void
     * @date    2020/11/27 10:12
     */
    private static void crawlingTowntrData(String url, Node countyNode) throws InterruptedException {
        List<Node> counties = new LinkedList<>();
        Thread.sleep(2000);
        String htmlStr = HttpUtil.get(url, CHARSET);
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Thread t1 = new Thread(htmlStr);
        long t1Id = t1.getId();
        executor.execute(t1);
        executor.shutdown();
        boolean terminated = executor.isTerminated();
        Document document = Jsoup.parse(htmlStr);
        Elements trs = document.getElementsByClass("towntr");
        for (Element tr : trs) {
            Elements links = tr.getElementsByTag("a");
            if (links == null || links.size() != 2) {
                continue;
            }
            String href = links.get(0).attr("href");
            String towntrCode = links.get(0).text().substring(0, 9);
            String towntrName = links.get(1).text();
            Node towntrNode = Node.builder().name(towntrName).code(towntrCode).dataFromUrl(url).build();
            log.info("线程：{}, 乡镇数据:  {}", t1Id,JSON.toJSONString(towntrNode));
            crawlingVillagetrData(BASE_URL + href.subSequence(2, 5).toString() + "/" + href.substring(5, 7) + "/" + href,countyNode);
            counties.add(towntrNode);
        }
        countyNode.setNodes(counties);
    }

    /**
     * TODO     爬取村级数据
     * @Title   crawlingVillagetrData
     * @author Panguaxe
     * @param url
     * @param countyNode
     * @return  void
     * @date    2020/11/27 10:13
     */
    private static void crawlingVillagetrData(String url, Node countyNode) throws InterruptedException {
        List<Node> counties = new LinkedList<>();
        Thread.sleep(3000);
        String htmlStr = HttpUtil.get(url, CHARSET);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Thread t1 = new Thread(htmlStr);
        long tid = t1.getId();
        executor.execute(t1);
        executor.shutdown();
        boolean terminated = executor.isTerminated();
        Document document = Jsoup.parse(htmlStr);
        Elements trs = document.getElementsByClass("villagetr");
        for (Element tr : trs) {
            Elements tds = tr.getElementsByTag("td");
            if (tds == null || tds.size() != 3) {
                continue;
            }
            String villagetrCode = tds.get(0).text();
            String villagetrName = tds.get(2).text();
            Node villagetrNode = Node.builder().code(villagetrCode).name(villagetrName).dataFromUrl(url).build();
            log.info("线程：{}, 村级数据:  {}",tid, JSON.toJSONString(villagetrNode));
            counties.add(villagetrNode);
        }
        countyNode.setNodes(counties);
    }




}
