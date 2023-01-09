package com.alex.hornsby.employees;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeesConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.routing-keys.internal-employees}")
    private String internalEmployeesRoutingKey;

    @Value("${rabbitmq.queues.employees}")
    private String employeesQueue;

    @Bean
    public TopicExchange  internalFanoutExchange() {
        return new TopicExchange(this.internalExchange, true, false);
    }

    @Bean
    public Queue employeesQueue() {
        return new Queue(this.employeesQueue, true, false, false);
    }

    @Bean
    public Binding internalToEmployeesBinding() {
        return BindingBuilder
                .bind(employeesQueue())
                .to(internalFanoutExchange())
                .with(this.internalEmployeesRoutingKey);
    }

    public String getInternalExchange() {
        return internalExchange;
    }

    public String getEmployeesQueue() {
        return employeesQueue;
    }

    public String getInternalEmployeesRoutingKey() {
        return internalEmployeesRoutingKey;
    }

}
