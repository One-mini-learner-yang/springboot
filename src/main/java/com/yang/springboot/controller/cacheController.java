package com.yang.springboot.controller;

import com.yang.springboot.Employee;
import com.yang.springboot.MapperInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class cacheController {
    @Autowired(required = false)
    private MapperInterface mapperInterface;
    @Cacheable(cacheNames = "emp",key = "#id")
    @RequestMapping("searchEmployeeById")
    public Employee searchEmployeeById(int id){
        Employee employee=  mapperInterface.selectEmployeeById(id);
        return employee;
    }

}
