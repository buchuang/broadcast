package com.vr.chatroom.controller;

import com.alibaba.fastjson.JSON;
import com.vr.chatroom.entity.ResponseMessage;
import com.vr.commonutils.utils.Consts;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalTime;

@RestController
public class BroadcastCtl {
    @Autowired
    SimpMessageSendingOperations simpMessageSendingOperations;

    @Autowired
    AmqpTemplate amqpTemplate;


    /**
     * 接收客户端发来的消息
     */
    @MessageMapping("/message/{pushNum}")
    public void handleSubscribe(ResponseMessage responseMessage, @DestinationVariable String pushNum) {

        System.out.println(responseMessage.getMsg());
        System.out.println(pushNum);
        amqpTemplate.convertAndSend(Consts.FANOUT+pushNum, "",  JSON.toJSONString(responseMessage));

    }


    /**
     * 测试对指定用户发送消息方法
     * 浏览器  访问https://xcx.dcssn.com/sendToUser?user=123456 前端小程序会收到信息
     * 客户端接收一对一消息的主题应该是“/user/” + 用户Id + “/message” ,这里的用户id可以是一个普通的字符串，只要每个用户端都使用自己的id并且服务端知道每个用户的id就行。
     *
     * @return 发送的消息内容
     */
    @RequestMapping(path = "/sendToUser", method = RequestMethod.GET)
    public String sendToUser(String user) {
        String payload = "指定用户" + user + "接收信息" + LocalTime.now();
        simpMessageSendingOperations.convertAndSendToUser(user, "/message", payload);
        return payload;
    }

    /**
     * 群发消息 订阅/topic/greetings 会收到
     * 浏览器  访问https://xcx.dcssn.com/sendToAll 前端小程序会收到信息
     *
     * @return 发送的消息内容
     */
    @RequestMapping(path = "/sendToAll", method = RequestMethod.GET)
    public String sendToUser() {
        String payload = "群发消息" + LocalTime.now();
        simpMessageSendingOperations.convertAndSend("/topic/greetings", payload);
        return payload;
    }
}
