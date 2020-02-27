package com.yang.springboot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component("data")
@ConfigurationProperties(prefix = "data")//将在配置文件中的数据注入到实体中
public class data {
    private String url;
    private String driver;
}
