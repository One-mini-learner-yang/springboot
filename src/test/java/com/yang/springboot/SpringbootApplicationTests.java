package com.yang.springboot;

import ch.qos.logback.classic.Logger;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest

class SpringbootApplicationTests {
    @Autowired
    private DataSource dataSource;
    @Test
    void contextLoads() {
        Logger logger = (Logger) LoggerFactory.getLogger(getClass().getName());
//        logger.info("111111");
        logger.info(String.valueOf(dataSource));
    }

}
