package com.ef.Parser.entities;

import com.ef.Parser.converters.LocalDateTimeAttributeConverter;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AccessLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    @Convert(converter = LocalDateTimeAttributeConverter.class)
    LocalDateTime timestamp;

    String ip;

    String method;

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
                ", client='" + client + '\'' +
                '}';
    }
}
