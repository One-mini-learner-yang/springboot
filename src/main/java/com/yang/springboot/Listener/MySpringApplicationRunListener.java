package com.yang.springboot.Listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.env.ConfigurableEnvironment;

public class MySpringApplicationRunListener implements SpringApplicationRunListener {
    public MySpringApplicationRunListener(SpringApplication application, String[] args) {

    }
    @Override
    public void starting(){
        Logger logger= LoggerFactory.getLogger(getClass().getName());
        logger.error("Starting....");
    }
    @Override
    public void environmentPrepared(ConfigurableEnvironment environment){
        Logger logger= LoggerFactory.getLogger(getClass().getName());
        logger.info("environmentPrepared......");
    }
    @Override
    public void contextPrepared(ConfigurableApplicationContext context){
        Logger logger= LoggerFactory.getLogger(getClass().getName());
        logger.info("contextPrepared.......");
    }
}
