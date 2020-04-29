package com.yang.springboot;

import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@SpringBootTest

class SpringbootApplicationTests {
    @Autowired
    mapperInterface mapperInterface;
    @Test
    void contextLoads() {
        Logger logger = (Logger) LoggerFactory.getLogger(getClass().getName());
        List<Map<String,Object>> a=mapperInterface.selectA();
        List<Map<String,Object>> b=mapperInterface.selectB();
        logger.info(String.valueOf(a));
        logger.info(String.valueOf(b));
    }

}
