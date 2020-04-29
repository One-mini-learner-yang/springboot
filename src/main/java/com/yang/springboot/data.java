package com.yang.springboot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component("data")
public class data implements Serializable {
    private String url;
    private String driver;
}
