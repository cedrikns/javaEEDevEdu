package ru.tsedrik.broker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.Message;


/**
 * Класс для получения сообщений из очереди и дальнейшей их обработки
 */
@Component
@Transactional
public class JMSMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ru.tsedrik.broker.JMSMessageListener.class.getName());

    public JMSMessageListener() {
    }

    @JmsListener(destination = "${it.jms.course.queue}")
    public void receiveMessage(Message message, String body) {

        try {
            logger.info("Received message {}: {}", message.getJMSCorrelationID(), body);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
