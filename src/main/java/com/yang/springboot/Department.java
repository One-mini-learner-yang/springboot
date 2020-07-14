package com.yang.springboot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class Department implements Serializable {
    private int id;
    private String departmentName;
}
