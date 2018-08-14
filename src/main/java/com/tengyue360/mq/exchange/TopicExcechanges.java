package com.tengyue360.mq.exchange;

import com.tengyue360.constant.QueueConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * 消息路由转换器
 *
 * @author xuliang 2018/08/12
 */

@Component
public class TopicExcechanges {


    @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }


    /**
     * 将队列topic.QUEUE_MESSAGE_SEND_VALIDATE_CODE与exchange绑定，binding_key为topic.message,就是完全匹配
     *
     * @param sendMessageQueue
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeValidateCode(Queue sendMessageQueue, TopicExchange exchange) {
        return BindingBuilder.bind(sendMessageQueue).to(exchange).with(QueueConstant.QUEUE_MESSAGE_SEND_VALIDATE_CODE);
    }

    /**
     * 将队列topic.QUEUE_MESSAGE_PUSH_MESSAGE与exchange绑定，binding_key为topic.#,模糊匹配
     *
     * @param pushMessageQueue
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeTimeingPush(Queue pushMessageQueue, TopicExchange exchange) {
        return BindingBuilder.bind(pushMessageQueue).to(exchange).with(QueueConstant.QUEUE_MESSAGE_PUSH_MESSAGE);
    }


}
