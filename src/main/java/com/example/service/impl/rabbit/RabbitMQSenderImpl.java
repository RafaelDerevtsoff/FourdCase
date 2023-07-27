package com.example.service.impl.rabbit;

import com.example.document.Lesson;
import com.example.document.Teacher;
import com.example.dto.CreateLessonsRequest;
import com.example.service.RabbitMQSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitMQSenderImpl implements RabbitMQSender {
    private final RabbitTemplate rabbitTemplate;


    @Autowired
    public RabbitMQSenderImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.json.routing.key}")
    private String routingkey;
    @Value("${rabbit.lesson.queue.name}")
    private String lessonQueue;
    @Override
    public Teacher send(Teacher teacher) {
        rabbitTemplate.convertAndSend(exchange, routingkey, teacher);
        return teacher;
    }
    @Override
    public CreateLessonsRequest send(CreateLessonsRequest lessons) {
        rabbitTemplate.convertAndSend(exchange,"LESSON_ROUNTING_KEY",lessons);
        return lessons;
    }
}