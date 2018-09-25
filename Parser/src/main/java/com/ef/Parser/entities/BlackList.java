package com.ef.Parser.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String ip;
    private String reason;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "BlackList{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
