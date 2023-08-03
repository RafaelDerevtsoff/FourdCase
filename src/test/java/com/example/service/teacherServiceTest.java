package com.example.service;

import com.example.document.Teacher;
import com.example.repository.TeacherRepository;
import com.example.service.impl.TeacherServiceImpl;
import com.example.service.impl.rabbit.RabbitMQSenderImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TeacherService.class)
public class teacherServiceTest {
    @Mock
    private  TeacherRepository teacherRepository;
    @Mock
    private RabbitTemplate rabbitTemplate;
    @Mock
    private RabbitMQSenderImpl rabbitMQSender;
    @InjectMocks
    private TeacherServiceImpl teacherService;
    private final Teacher teacher = new Teacher("UserTest",
            "12345",
            false,
            "emailTest",
            List.of(),
            new HashMap<>());
    @Test
    public void createNewTeacherTest(){
        doNothing().when(rabbitTemplate).convertAndSend(anyString(),anyString(),any(Teacher.class));
//        doNothing().when(r).send(any(Teacher.class));
        doNothing().when(rabbitMQSender).send(any(Teacher.class));
        StepVerifier.create(teacherService.createNewTeacher(teacher))
                .expectNextMatches(response -> response.equals(ResponseEntity.ok().body("User Created")))
                .verifyComplete();
    }
}
