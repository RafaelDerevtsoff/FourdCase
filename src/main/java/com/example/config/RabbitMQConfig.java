package com.example.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.json.queue.name}")
    private String jsonQueue;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.json.routing.key}")
    private String jsonRoutingKey;
    @Value("${rabbitmq.lesson.routing.key}")
    private String lessonsRoutingKey;
    @Value("spring.rabbitmq.host")
    private String host;
    @Value("${rabbit.lesson.queue.name}")
    private String lessonQueue;





    @Bean
    public Declarables topicBindings() {
        Queue topicQueue1 = new Queue(jsonQueue);
        Queue topicQueue2 = new Queue(lessonQueue);

        TopicExchange topicExchange = new TopicExchange(exchange);

        return new Declarables(
                topicQueue1,
                topicQueue2,
                topicExchange,
                BindingBuilder
                        .bind(topicQueue1)
                        .to(topicExchange).with(jsonRoutingKey),
                BindingBuilder
                        .bind(topicQueue2)
                        .to(topicExchange).with(lessonsRoutingKey));
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }


}
