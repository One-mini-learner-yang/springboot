package com.yang.springboot;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class Employee implements Serializable {
    private int id;
    private String lastName;
    private String email;
    private int gender;
    private int dId;
}
