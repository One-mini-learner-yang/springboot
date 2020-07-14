package com.yang.springboot;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Employee {
    private int id;
    private String lastName;
    private String email;
    private int gender;
    private int dId;
}
