package com.example.service;

import com.example.document.Teacher;

public interface RabbitMQSender {
    Runnable send(Teacher teacher);
}
