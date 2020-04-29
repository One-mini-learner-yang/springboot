package com.yang.springboot;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
public interface mapperInterface {
    public List<Map<String,Object>> selectA();
    public List<Map<String,Object>> selectB();
}
