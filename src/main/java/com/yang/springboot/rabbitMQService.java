package com.yang.springboot;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class rabbitMQService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;
    public static int a=0;
    public static int b=0;
    @RequestMapping("/rabbitMQService")
    public void rabbitMQService(){
//        Map map=new HashMap();
//        map.put("yang","good!");
//        rabbitTemplate.convertAndSend("yang.create","yang.nb",map);
        for (int i=0;i<50;i++){
            rabbitTemplate.convertAndSend("yang.create","yang.nb",i);
        }
    }
    @RabbitListener(queues = "create.queue")
    public void rabbitListener(Message message, Channel channel) throws IOException, InterruptedException {
        channel.basicQos(1,false);
        a++;
        System.out.println("消费者1消费："+message+"a:"+a);
        Thread.sleep(200);
//        int i=1/0;
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
    @RabbitListener(queues = "create.queue")
    public void rabbitListener2(Message message,Channel channel) throws InterruptedException, IOException {
        channel.basicQos(1,false);
        b++;
        System.out.println("消费者2消费："+message+"b:"+b);
        Thread.sleep(1000);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
//    @RequestMapping("/getMessage")
//    public Object getMessage(){
//        Object o =rabbitTemplate.receiveAndConvert("yang.create");
//        return o;
//    }
//    @RequestMapping("/createQueue")
//    public String createQueue(){
//        amqpAdmin.declareExchange(new DirectExchange("yang.create"));
//        amqpAdmin.declareQueue(new Queue("create.queue"));
//        amqpAdmin.declareBinding(new Binding("create.queue", Binding.DestinationType.QUEUE,"yang.create","yang.nb",null));
//        return "创建完成";
//    }
}
