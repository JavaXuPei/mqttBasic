package com.mqtt.mqttdemo;


import com.mqtt.mqttbasis.MqttBasisApplication;
import com.mqtt.mqttbasis.service.MqttMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqttBasisApplication.class)
public class test {

    @Autowired
    private MqttMessageService mqttMessageService;

    @Test
    public void mqttOutbound() {
        String content = "{\n" +
                "    \"timestamp\": 1606444900930,\n" +
                "    \"values\": [\n" +
                "    {\n" +
                "        \"a\": 5,\n" +
                "        \"t\": 1606444900930,\n" +
                "        \"b\": {\n" +
                "            \"c\": 6\n" +
                "        },\n" +
                "        \"ignored\": \"I'm a string\"\n" +
                "    }\n" +
                "]\n" +
                "}";
        String content1 = "{\"timestamp\":1606461655523,\"values\":[{\"a\":5,\"t\":1606461655523,\"b\":{\"c\":6,\"my_field\":\"description\"},\"my_tag_1\":\"foo\",\"name\":\"my_json\"}]}";
        String content2 = "{\"timestamp\":1606466160263,\"values\":[{\"a\":5,\"name\":\"json3\",\"b\":{\"c\":6,\"time\":\"1606466160263\"}},{\"a\":7,\"name\":\"json2\",\"b\":{\"c\":8,\"time\":\"1606466160263\"}}]}";
        String content3="{\"timestamp\":1606465427352,\"values\":[{\"name\":{\"first\":\"Tom\",\"last\":\"Anderson\"},\"age\":37,\"children\":[\"Sara\",\"Alex\",\"Jack\"],\"fav.movie\":\"Deer Hunter\",\"friends\":[{\"first\":\"Dale\",\"last\":\"Murphy\",\"age\":44},{\"first\":\"Roger\",\"last\":\"Craig\",\"age\":68},{\"first\":\"Jane\",\"last\":\"Murphy\",\"age\":47}]}]}";
        mqttMessageService.mqttOutbound(content3);
    }


    @Test
    public void  mathTest(){
        BigDecimal bigDecimal=new BigDecimal(1.55);
        double f1 = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        System.out.println(f1);
    }

    @Test
    public void  imple(){
        File file=new File("HELP.md");
        try {
            FileInputStream fis=new FileInputStream(file);
            System.out.println(file.exists());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(file);
    }


}
