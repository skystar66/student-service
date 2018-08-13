package com.tengyue360.mq.topic.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 * 队列订阅
 *
 * @author xuliang 2018/08/12
 */

@Component
public class QueueMessage {


    @Bean
    public Queue helloQueue() {
        return new Queue("topic.message");
    }

    @Bean
    public Queue userQueue() {
        return new Queue("topic.messages");
    }


}
