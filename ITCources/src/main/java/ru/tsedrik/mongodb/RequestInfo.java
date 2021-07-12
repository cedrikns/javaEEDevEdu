package ru.tsedrik.mongodb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import ru.tsedrik.entity.Identifired;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "RequestInfo")
public class RequestInfo implements Identifired<UUID> {
    @Id
    private UUID id;

    @Field(value = "user_name")
    private String userName;

    @Field(value = "datetime")
    private LocalDateTime dateTime;

    @Field(value = "method")
    private String method;

    public RequestInfo(String userName, LocalDateTime dateTime, String method) {
        this.id = UUID.randomUUID();
        this.userName = userName;
        this.dateTime = dateTime;
        this.method = method;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", dateTime=" + dateTime +
                ", method='" + method + '\'' +
                '}';
    }
}
