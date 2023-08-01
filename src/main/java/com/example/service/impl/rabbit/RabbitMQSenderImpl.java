package com.example.service.impl.rabbit;

import com.example.document.Teacher;
import com.example.dto.CreateLessonsRequest;
import com.example.dto.UpdateLessonRequest;
import com.example.service.RabbitMQSender;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
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
    @Value("${rabbitmq.create.teacher.routing.key}")
    private String routingkey;
    @Value("${rabbitmq.lesson.routing.key}")
    private String lessonRountingKey;
    @Value("${rabbitmq.lesson.update.routing.key}")
    private String lessonUpdateRoutingKey;



    @Override
    public void send(Teacher teacher) {
        rabbitTemplate.convertAndSend(exchange, routingkey, teacher);
    }

    @Override
    public void send(CreateLessonsRequest lessons) {
        rabbitTemplate.convertAndSend(exchange, lessonRountingKey, lessons);
    }

    @Override
    public void update(UpdateLessonRequest updatedLessons) {
        rabbitTemplate.convertAndSend(exchange, lessonUpdateRoutingKey, updatedLessons);
    }
}