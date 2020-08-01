package com.yang.springboot.controller;

import com.yang.springboot.Employee;
import com.yang.springboot.MapperInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cache")
public class cacheController {
    @Autowired(required = false)
    private MapperInterface mapperInterface;
    @Autowired
    private RedisTemplate<Object,Employee> myRedisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Cacheable(cacheNames = "emp",key = "#id")
    @RequestMapping("searchEmployeeById")
    public int searchEmployeeById(int id){
        Employee employee=  mapperInterface.selectEmployeeById(id);
//        System.out.println(myRedisTemplate.opsForValue().get("emp"));
        return 1;
    }

}
