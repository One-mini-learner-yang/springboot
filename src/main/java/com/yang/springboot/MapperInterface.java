package com.yang.springboot;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
public interface MapperInterface {
    public Department selectDepartmentById(int id);
    public Employee selectEmployeeById(int id);
}
