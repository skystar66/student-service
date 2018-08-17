package com.tengyue360.mq.exchange;

import com.tengyue360.constant.FanoutExancheConstant;
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
        return new TopicExchange(FanoutExancheConstant.FANOUT_EXANCHE_CONSTANT_A);
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
     * @param sendCourseReadedMessageQueue
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeSendCourseReaded(Queue sendCourseReadedMessageQueue, TopicExchange exchange) {
        return BindingBuilder.bind(sendCourseReadedMessageQueue).to(exchange).with(QueueConstant.QUEUE_MESSAGE_SEND_COURSE_READED);
    }

    /**
     * 将队列topic.QUEUE_MESSAGE_PUSH_MESSAGE与exchange绑定，binding_key为topic.#,模糊匹配
     *
     * @param sendCourseRmindMessageQueue
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeSendCourseRemind(Queue sendCourseRmindMessageQueue, TopicExchange exchange) {
        return BindingBuilder.bind(sendCourseRmindMessageQueue).to(exchange).with(QueueConstant.QUEUE_MESSAGE_SEND_COURSE_REMIND);
    }

    /**
     * 将队列topic.QUEUE_MESSAGE_PUSH_MESSAGE与exchange绑定，binding_key为topic.#,模糊匹配
     *
     * @param pushCourseReadedMessageQueue
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangePushCourseMessage(Queue pushCourseReadedMessageQueue, TopicExchange exchange) {
        return BindingBuilder.bind(pushCourseReadedMessageQueue).to(exchange).with(QueueConstant.QUEUE_MESSAGE_PUSH_READED_MESSAGE);
    }

    /**
     * 将队列topic.QUEUE_MESSAGE_PUSH_MESSAGE与exchange绑定，binding_key为topic.#,模糊匹配
     *
     * @param pushCourseRemindMessageQueue
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangePushRemind(Queue pushCourseRemindMessageQueue, TopicExchange exchange) {
        return BindingBuilder.bind(pushCourseRemindMessageQueue).to(exchange).with(QueueConstant.QUEUE_MESSAGE_PUSH_REMIND_MESSAGE);
    }








}
