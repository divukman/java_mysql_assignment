package com.ef.Parser.entities;

import com.ef.Parser.converters.LocalDateTimeAttributeConverter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GenericGenerator(name = "generic", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator")
    long id;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    LocalDateTime timestamp;

    String ip;

    String method;

    Integer statusCode;

    String client;

    public AccessLog() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "AccessLog{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", ip='" + ip + '\'' +
                ", method='" + method + '\'' +
                ", statusCode='" + statusCode + '\'' +
                ", client='" + client + '\'' +
                '}';
    }
}
