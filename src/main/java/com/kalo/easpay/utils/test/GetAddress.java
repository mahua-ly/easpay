package com.kalo.easpay.utils.test;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Panguaxe
 * @Version V1.0.0
 * @Description TODO
 * @DateTime 2020年 11月 26日 星期四 16:19:34
 */
@Slf4j
public class GetAddress {

    private static final String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2020/index.html";

    public static void main(String[] args) throws IOException, InterruptedException {
        List<RegionTable> preservation = preservation(baseUrl);
        log.info("处理结果：{}", JSON.toJSONString(preservation));
    }


    public static List<RegionTable> preservation(String url) throws IOException, InterruptedException {
        List<RegionTable> nations = new ArrayList<>();
        //获取：省、市、区县、乡镇/街道、村级
        List<Province> province = getProvince(url);
        RegionTable nation;
        for (Province province1 : province) {
            nation = new RegionTable();
            nation.setCode(province1.getCode());
            nation.setName(province1.getName());
            nation.setType("A");
            nations.add(nation);
            List<City> cities = province1.getCities();
            for (City city : cities) {
                nation = new RegionTable();
                nation.setCode(city.getCode());
                nation.setName(city.getName());
                nation.setType("B");
                nations.add(nation);
                List<County> counties = city.getCounties();
                for (County county : counties) {
                    nation = new RegionTable();
                    nation.setCode(county.getCode());
                    nation.setName(county.getName());
                    nation.setType("C");
                    nations.add(nation);
                    List<Country> countries = county.getCountries();
                    for (Country country : countries) {
                        nation = new RegionTable();
                        nation.setCode(country.getCode());
                        nation.setName(country.getName());
                        nation.setType("D");
                        nations.add(nation);
                        List<Town> towns = country.getTowns();
                        for (Town town : towns) {
                            nation = new RegionTable();
                            nation.setCode(town.getCode());
                            nation.setName(town.getName());
                            nation.setClassification(town.getClassification());
                            nation.setType("E");
                            nations.add(nation);
                        }
                    }
                }
            }
        }
        return nations;
    }

    /**
     * TODO     获取省信息
     * @title   getProvince
     * @author  Panguaxe
     * @param url
     * @return  java.util.List<com.kalo.easpay.utils.test.Province>
     * @date    2020/11/26 22:12
     */
    private static List<Province> getProvince(final String url) throws InterruptedException, IOException {
        List<Province> provinces = new ArrayList<>();
        //省
        Elements elements = getElements(url, 1);
        for (Element element : elements) {
            Elements elementsTags = element.getElementsByTag("a");
            for (Element tag : elementsTags) {
                //获取省code
                String href = tag.attr("href");
                //调用转换方法
                href = gbkConvertToUTF(href);
                String code = href.substring(0, href.lastIndexOf('.'));
                if (code.startsWith("11")){//TODO
                    //获取省name
                    String name = tag.text();
                    //调用转换方法
                    name = gbkConvertToUTF(name);
                    log.info("【省级】code：{}, name：{}", code, name);
                    Province province = new Province();
                    province.setCode(code);
                    province.setName(name);
                    //获取市
                    List<City> cities = indexCity(tag.absUrl("href"));
                    province.setCities(cities);
                    provinces.add(province);
                }
            }
        }
        return provinces;
    }

    /**
     * TODO     获取市
     * @title   indexCity
     * @author  Panguaxe 
     * @param url
     * @return  java.util.List<com.kalo.easpay.utils.test.City>
     * @date    2020/11/26 22:30
     */
    private static List<City> indexCity(String url) throws InterruptedException, IOException {
        List<City> cities = new ArrayList<>();
        //市
        Elements elements = getElements(url, 2);
        for (Element element : elements) {
            Elements citys = element.getElementsByTag("a");
            for (int m = 0; m < citys.size(); m++) {
                Element city = citys.get(m);
                //市级code
                String code = gbkConvertToUTF(city.text());
                //市级name
                String name = gbkConvertToUTF(citys.get(++m).text());
                log.info("【市级】code：{}, name：{}", code, name);

                City cityInfo = new City();
                cityInfo.setCode(code);
                cityInfo.setName(name);

                //获取县
                String href = city.absUrl("href");
                List<County> counties = indexCounty(href);
                cityInfo.setCounties(counties);

                cities.add(cityInfo);
            }
        }
        return cities;
    }

    /**
     * TODO     获取县
     * @title   indexCounty
     * @author  Panguaxe
     * @param url
     * @return  java.util.List<com.kalo.easpay.utils.test.County>
     * @date    2020/11/26 22:36
     */
    private static List<County> indexCounty(String url) throws InterruptedException, IOException {
        List<County> counties = new ArrayList<>();
        Elements elements = getElements(url, 3);
        for (Element element : elements) {
            Elements countys = element.getElementsByTag("a");
            for (int m = 0; m < countys.size(); m++) {
                Element county = countys.get(m);
                //区县code
                String code =  gbkConvertToUTF(county.text());
                //区县name
                String name = gbkConvertToUTF(countys.get(++m).text());
                log.info("【区县】code：{}, name：{}", code, name);

                County countyInfo = new County();
                countyInfo.setName(name);
                countyInfo.setCode(code);
                //获取镇
                List<Country> countries = indexCountry(county.absUrl("href"));
                countyInfo.setCountries(countries);

                counties.add(countyInfo);
            }
        }
        return counties;
    }

    /**
     * TODO     获取乡镇/街道
     * @title   indexCountry
     * @author  Panguaxe
     * @param url
     * @return  java.util.List<com.kalo.easpay.utils.test.Country>
     * @date    2020/11/26 23:15
     */
    private static List<Country> indexCountry(String url) throws InterruptedException, IOException {
        List<Country> countries = new ArrayList<>();
        Elements elements = getElements(url, 4);
        //乡镇/街道
        for (Element element : elements) {
            Elements towntrs = element.getElementsByTag("a");
            for (int i1 = 0; i1 < towntrs.size(); i1++) {
                Element towntr = towntrs.get(i1);
                //乡镇/街道code
                String code =  gbkConvertToUTF(towntr.text());
                //乡镇/街道name
                String name =  gbkConvertToUTF(towntrs.get(++i1).text());
                log.info("【乡镇/街道】code：{}, name：{}", code, name);
                Country country1 = new Country();
                country1.setCode(code);
                country1.setName(name);

                //获取村
                List<Town> towns = indexTown(towntr.absUrl("href"));
                country1.setTowns(towns);

                countries.add(country1);
            }
        }
        return countries;
    }

    /**
     * TODO     获取村级
     * @title   indexTown
     * @author  Panguaxe
     * @param url
     * @return  java.util.List<com.kalo.easpay.utils.test.Town>
     * @date    2020/11/26 23:20
     */
    private static List<Town> indexTown(String url) throws InterruptedException, IOException {
        List<Town> towns = new ArrayList<>();
        Elements elements = getElements(url, 5);
        for (Element element : elements) {
            Elements villages = element.getElementsByTag("td");
            String code =  gbkConvertToUTF(villages.get(0).text());
            String classification = gbkConvertToUTF(villages.get(1).text());
            String name = gbkConvertToUTF(villages.get(2).text());
            log.info("【村级】code：{}, name：{}, classification：{}", code, name,classification);
            Town town1 = new Town();
            town1.setCode(code);
            town1.setClassification(classification);
            town1.setName(name);
            towns.add(town1);
        }
        return towns;
    }

    /**
     * TODO     获取 Elements 对象
     * @title   getElements
     * @author  Panguaxe
     * @param url
     * @param level
     * @return  org.jsoup.select.Elements
     * @date    2020/11/26 22:04
     */
    private static Elements getElements(String url, Integer level) throws InterruptedException {
        Document document;
        long millis = SleepTime.millis(level);
        try {
            Thread.sleep(millis);
            document = Jsoup.parse(new URL(url).openStream(), "GBK", url);
//            document = Jsoup.connect(url).postDataCharset("GBK").userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36").timeout(1000).get();
        } catch (IOException e) {
            Thread.sleep(millis);
            //调用重新载入方法
            document = heavyLoadIn(url);
        }
        String cssQuery = CssQuery.cssQuery(level);
        Elements elements;
        if (Arrays.asList(4,5).contains(level)){
            try {
                elements = document.select(cssQuery);
            } catch (Exception e) {
                //调用重新载入方法
                document = heavyLoadIn(url);
                elements = document.select(cssQuery);
            }
        }else {
            elements = document.select(cssQuery);
        }
        return elements;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    enum SleepTime {
        PROVINCE(1, 1000),
        CITY(2, 1000),
        DISTRICT(3, 2000),
        STREET(4, 2000),
        VILLAGE(5, 3000);

        private Integer level;
        private long millis;

        public static long millis(Integer level) {
            long millis = 0;
            SleepTime[] values = values();
            for (SleepTime value : values) {
                if (value.getLevel().equals(level)) {
                    millis = value.getMillis();
                }
            }
            return millis;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    enum CssQuery {
        PROVINCE(1, ".provincetr"),
        CITY(2, ".citytr"),
        DISTRICT(3, ".countytr"),
        STREET(4, ".towntr"),
        VILLAGE(5, ".villagetr");
        private Integer level;
        private String cssQuery;

        public static String cssQuery(Integer level) {
            String cssQuery = "";
            CssQuery[] values = values();
            for (CssQuery value : values) {
                if (value.getLevel().equals(level)) {
                    cssQuery = value.getCssQuery();
                }
            }
            return cssQuery;
        }
    }

    /**
     * TODO     重载方法
     * @title   heavyLoadIn
     * @author  Panguaxe 
     * @param url
     * @return  org.jsoup.nodes.Document
     * @date    2020/11/26 22:39
     */
    private static Document heavyLoadIn(String url) {
        try {
            return Jsoup.parse(new URL(url).openStream(), "GBK", url);
        } catch (IOException e) {
            heavyLoadIn(url);
        }
        return null;
    }


    /**
     * TODO     有损转换 【GBK 转换 UTF8】
     * @title   gbkConvertToUTF
     * @author  Panguaxe 
     * @param gbkStr
     * @return  java.lang.String
     * @date    2020/11/26 22:41
     */
    public static String gbkConvertToUTF(String gbkStr) {
        int n = gbkStr.length();
        byte[] utfBytes = new byte[3 * n];
        int k = 0;
        for (int i = 0; i < n; i++) {
            int m = gbkStr.charAt(i);
            if (m < 128 && m >= 0) {
                utfBytes[k++] = (byte) m;
                continue;
            }
            utfBytes[k++] = (byte) (0xe0 | (m >> 12));
            utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));
            utfBytes[k++] = (byte) (0x80 | (m & 0x3f));
        }
        if (k < utfBytes.length) {
            byte[] tmp = new byte[k];
            System.arraycopy(utfBytes, 0, tmp, 0, k);
            utfBytes = tmp;
        }
        String str = null;
        try {
            str = new String(utfBytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
