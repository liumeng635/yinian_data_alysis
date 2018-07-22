package com.yinian.alysis.model;

import java.util.Date;

public class Hot {
    private Integer id;

    private String type;

    private Date date;

    private String cut;

    private Integer count;

    private Date period;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCut() {
        return cut;
    }

    public void setCut(String cut) {
        this.cut = cut == null ? null : cut.trim();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }
}