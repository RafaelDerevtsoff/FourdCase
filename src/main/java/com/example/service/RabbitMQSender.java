package com.example.service;

import com.example.document.Teacher;
import com.example.dto.CreateLessonsRequest;
import com.example.dto.UpdateLessonRequest;

public interface RabbitMQSender {
    void send(Teacher teacher);

    void send(CreateLessonsRequest teacher);
    void update(UpdateLessonRequest teacher);
}
