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
     * 发送上课准备短信 课程开始前一天18:00： topic
     *
     * @return
     * @throws Exception
     */
    @Bean
    public Queue sendCourseReadedMessageQueue() {
        return new Queue(QueueConstant.QUEUE_MESSAGE_SEND_COURSE_READED);
    }


    /**
     * 发送上课准备短信 课程开始前2小时： topic
     *
     * @return
     * @throws Exception
     */
    @Bean
    public Queue sendCourseRmindMessageQueue() {
        return new Queue(QueueConstant.QUEUE_MESSAGE_SEND_COURSE_REMIND);
    }




    /**
     * 上课准备推送 课程开始前一天18:00
     *
     * @return
     * @throws Exception
     */
    @Bean
    public Queue pushCourseReadedMessageQueue() {
        return new Queue(QueueConstant.QUEUE_MESSAGE_PUSH_READED_MESSAGE);
    }




    /**
     * 上课准备推送 课程开始前2小时
     *
     * @return
     * @throws Exception
     */
    @Bean
    public Queue pushCourseRemindMessageQueue() {
        return new Queue(QueueConstant.QUEUE_MESSAGE_PUSH_REMIND_MESSAGE);
    }





















}
