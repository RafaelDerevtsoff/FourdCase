package com.example.service.impl.rabbit;

import com.example.document.Teacher;
import com.example.dto.CreateLessonsRequest;
import com.example.service.RabbitMQSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    @Value("${rabbitmq.lesson.routing.key}")
    private String lessonRountingKey;
    @Value("${rabbitmq.lesson.update.routing.key}")
    private String lessonUpdateRoutingKey;

    @Override
    public Teacher send(Teacher teacher) {
        rabbitTemplate.convertAndSend(exchange, routingkey, teacher);
        return teacher;
    }

    @Override
    public CreateLessonsRequest send(CreateLessonsRequest lessons) {
        rabbitTemplate.convertAndSend(exchange, lessonRountingKey, lessons);
        return lessons;
    }

    @Override
    public CreateLessonsRequest update(CreateLessonsRequest updatedLessons) {
        rabbitTemplate.convertAndSend(exchange, lessonUpdateRoutingKey, updatedLessons);
        return updatedLessons;
    }
}