package com.mqtt.mqttdemo;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;

import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class main {

    /**
     * 炉温 and 辊温
     */
    static String[] lwAndGw = {"4rQ84VjLAzxU2WPkiPys", "r8pNydVfxBvq8OOqBS1J", "M0JqQRLS1oLZF7VAdT99", "ulBKoqcwdP8CmOx0PWiU",
            "6J1llv0H3wvxG3hQ1Et0"
            , "7XYTQAGKaS63AwznY3vy", "C0nq4bSmPWuPCM9SqHad", "SFd6GrNFoJR4dIjLOPI6", "tZgDvMm5I44XwIVL9OpK", "nulsQejI0ZkiqCyKHJuV", "V5WXQEWWfbwMuQABTnCB", "l1alpeuNoTJAvrBPImU1"};

    /**
     * 炉温
     */
    static String[] lw = {"4rQ84VjLAzxU2WPkiPys", "r8pNydVfxBvq8OOqBS1J", "M0JqQRLS1oLZF7VAdT99", "ulBKoqcwdP8CmOx0PWiU", "6J1llv0H3wvxG3hQ1Et0"
            , "7XYTQAGKaS63AwznY3vy"};


    static String template = "http://192.168.18.194:8080/api/v1/{}/telemetry";

    public static void main(String[] args) throws AWTException {
        a();
//        b();
    }


    /**
     * 随机发数
     *
     * @param
     * @return
     */
    static public void a() throws AWTException {
        for (int m = 1; m < 100000; m++) {
            Robot r = new Robot();
            for (int i = 0; i < lwAndGw.length; i++) {
                Map<String, Object> stringStringMap = new HashMap<>();
                //Double va = RandomUtil.randomDouble(1, 10, 2, RoundingMode.HALF_DOWN) * m*1;
                Integer va = m * 10;
                if (i <= 500) {
                    stringStringMap.put("temperature", String_(String.valueOf(va*30)));
                    System.out.println(String_(String.valueOf(va*30)));
                }
                if (i >= 500 && i < 2000) {
                    stringStringMap.put("temperature", String_(String.valueOf(va * 0.5*30)));
                    System.out.println(String_(String.valueOf(va * 0.5*30)));
                }
                if (i >= 2000) {
                    stringStringMap.put("temperature", String_(String.valueOf(va - (2*30))));
                    System.out.println((String_(String.valueOf(va - (2*30)))));
                }
                String str = StrUtil.format(template, lwAndGw[i]); //str -> 我爱你，就像老鼠爱大米
                String result2 = HttpRequest.post(str)
                        .header(Header.CONTENT_TYPE, "application/json")//头信息，多个头信息多次调用此方法即可
                        .form(stringStringMap)//表单内容
                        .timeout(20000)//超时，毫秒
                        .body(JSON.toJSONString(stringStringMap))
                        .execute().body();
                r.delay(10000);
            }
            r.delay(60000);
        }
    }


    /**
     * 递减发数
     *
     * @param
     * @return
     */

    static public void b() throws AWTException {
        for (int m = 0; m < 350; m++) {
            Robot r = new Robot();
            for (int i = 0; i < lw.length; i++) {
                Map<String, Object> stringStringMap = new HashMap<>();
                stringStringMap.put("temperature", 350 - m * 10);
                String str = StrUtil.format(template, lw[i]); //str -> 我爱你，就像老鼠爱大米
                String result2 = HttpRequest.post(str)
                        .header(Header.CONTENT_TYPE, "application/json")//头信息，多个头信息多次调用此方法即可
                        .form(stringStringMap)//表单内容
                        .timeout(20000)//超时，毫秒
                        .body(JSON.toJSONString(stringStringMap))
                        .execute().body();
                r.delay(1000);
            }
            r.delay(5000);
        }
    }


    static public String String_(String abc) {
        DecimalFormat format = new DecimalFormat("0.00");
        String a = format.format(new BigDecimal(abc));
        return a;
    }
}
