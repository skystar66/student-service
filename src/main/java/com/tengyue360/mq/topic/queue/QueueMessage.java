package com.tengyue360.mq.topic.queue;

import com.tengyue360.constant.QueueConstant;
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

    /**
     * 验证码订阅
     *
     * @return
     * @throws Exception
     */
    @Bean
    public Queue sendMessageQueue() {
        return new Queue(QueueConstant.QUEUE_MESSAGE_SEND_VALIDATE_CODE);
    }

    /**
     * 推送订阅
     *
     * @return
     * @throws Exception
     */
    @Bean
    public Queue pushMessageQueue() {
        return new Queue(QueueConstant.QUEUE_MESSAGE_PUSH_READED_MESSAGE);
    }


}
