package com.alex.hornsby.employees.rabbitmq;

import com.alex.hornsby.amqp.RabbitMQMessageProducer;
import com.alex.hornsby.employees.entity.Employee;
import com.alex.hornsby.employees.services.EmployeesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class EmployeesConsumer {
    private final EmployeesService employeesService;

    @RabbitListener(queues = "${rabbitmq.queues.employees}")
    public void consumer(Employee employee) {
        log.info("Consumed {} from queue", employee);

    }

}
