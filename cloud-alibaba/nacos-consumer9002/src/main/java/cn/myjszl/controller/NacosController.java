package cn.myjszl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/nacos")
public class NacosController {

    @Autowired
    private RestTemplate restTemplate;

    //获取配置文件中的微服务访问地址
    @Value("${service-url.nacos-provider}")
    private String serviceUrl;


    @GetMapping("/test/{id}")
    public ResponseEntity<String> test(@PathVariable("id")Integer id){
        return restTemplate.getForEntity(serviceUrl+"/nacos/test/"+id,String.class);
    }

}
