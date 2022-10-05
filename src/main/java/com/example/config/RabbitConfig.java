package com.example.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {


    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public CachingConnectionFactory cachingConnectionFactory(){
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host,port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        //** infinity cache increment for limit setConnectionLimit
//        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
//        cachingConnectionFactory.setConnectionLimit(5);
//        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
        return cachingConnectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(){
            SimpleRabbitListenerContainerFactory factory =
                    new SimpleRabbitListenerContainerFactory();
            factory.setConnectionFactory(cachingConnectionFactory());
            factory.setAutoStartup(false);
//            factory.setMessageConverter(converter());
//            factory.setConcurrentConsumers(2);
//            factory.setMaxConcurrentConsumers(10);
            return factory;
    }


    @Bean
    public Jackson2JsonMessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate template = new RabbitTemplate(cachingConnectionFactory());
//        RetryTemplate retryTemplate = new RetryTemplate();
//        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy ();
//        backOffPolicy.setInitialInterval(500);
//        backOffPolicy.setMultiplier(10.0);
//        backOffPolicy.setMaxInterval(10000);
//        retryTemplate.setBackOffPolicy(backOffPolicy);
//        template.setRetryTemplate(retryTemplate);
        template.setMessageConverter(converter());
        return template;
    }

}
