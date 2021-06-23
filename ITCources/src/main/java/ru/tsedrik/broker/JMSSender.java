package ru.tsedrik.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.tsedrik.entity.Course;

import java.util.UUID;

/**
 * Класс для отправки сообщений в очередь
 */
@Component
public class JMSSender implements Sender{

    private static final Logger logger = LoggerFactory.getLogger(ru.tsedrik.broker.JMSSender.class.getName());

    private final JmsTemplate jmsTemplate;

    public JMSSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(final String queueName, final Course course) {
        logger.info("Sending message {} to queue - {}", course, queueName);

        UUID uuid = UUID.randomUUID();
        String body = "";

        try{
            body = new ObjectMapper().writeValueAsString(course);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }

        jmsTemplate.convertAndSend(queueName, body, message -> {
                    message.setJMSCorrelationID(uuid.toString());
                    return message;
                }
        );
    }
}
