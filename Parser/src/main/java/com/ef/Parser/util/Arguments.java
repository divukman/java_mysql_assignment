package com.ef.Parser.util;

import java.time.LocalDateTime;

public class Arguments {
    String logfile;
    LocalDateTime startDate;
    String duration;
    Long threshold;

    public String getLogfile() {
        return logfile;
    }

    public void setLogfile(String logfile) {
        this.logfile = logfile;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Long getThreshold() {
        return threshold;
    }

    public void setThreshold(Long threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "Arguments{" +
                "logfile='" + logfile + '\'' +
                ", startDate=" + startDate +
                ", duration='" + duration + '\'' +
                ", threshold=" + threshold +
                '}';
    }
}
