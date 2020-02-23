package com.yang.springboot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Data
@Component("data")
@ConfigurationProperties(prefix = "data")
public class data {
    private String url;
    private String driver;
}
