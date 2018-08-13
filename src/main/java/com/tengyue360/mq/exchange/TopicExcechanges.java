package com.tengyue360.mq.exchange;

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
     * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
     * @param queueMessage
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue helloQueue, TopicExchange exchange) {
        return BindingBuilder.bind(helloQueue).to(exchange).with("topic.message");
    }

    /**
     * 将队列topic.messages与exchange绑定，binding_key为topic.#,模糊匹配
     * @param queueMessage
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessages(Queue userQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userQueue).to(exchange).with("topic.#");
    }

}
