package ru.tsedrik.broker;

import ru.tsedrik.entity.Course;

public interface Sender {
    void sendMessage(String topicName, Course course);
}
