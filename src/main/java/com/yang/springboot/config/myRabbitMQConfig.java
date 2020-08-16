package com.yang.springboot.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class myRabbitMQConfig {
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public String createEnvironment(){
        Map map=new HashMap();
        map.put("x-dead-letter-exchange","dead_Exchange");
        map.put("x-dead-letter-routing-key","receive_key");
        amqpAdmin.declareExchange(new DirectExchange("dead_Exchange",true,false));
        amqpAdmin.declareQueue(new Queue("dead_Queue",true,false,false,map));
        amqpAdmin.declareQueue(new Queue("receive_Queue",true));
        amqpAdmin.declareBinding(new Binding("dead_Queue", Binding.DestinationType.QUEUE,"dead_Exchange","dead_Message",null));
        amqpAdmin.declareBinding(new Binding("receive_Queue", Binding.DestinationType.QUEUE,"dead_Exchange","receive_key",null));
        return "创建完成";
    }
}
