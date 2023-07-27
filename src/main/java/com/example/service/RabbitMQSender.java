package com.example.service;

import com.example.document.Teacher;
import com.example.dto.CreateLessonsRequest;

public interface RabbitMQSender {
    Teacher send(Teacher teacher);

    CreateLessonsRequest send(CreateLessonsRequest teacher);
}
