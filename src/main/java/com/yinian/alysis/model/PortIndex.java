package com.yinian.alysis.model;

import java.util.Date;

public class PortIndex {
    private Integer id;

    private String portName;

    private Integer portType;

    private Date startTime;

    private Integer isvalue;

    private Date systemTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName == null ? null : portName.trim();
    }

    public Integer getPortType() {
        return portType;
    }

    public void setPortType(Integer portType) {
        this.portType = portType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getIsvalue() {
        return isvalue;
    }

    public void setIsvalue(Integer isvalue) {
        this.isvalue = isvalue;
    }

    public Date getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Date systemTime) {
        this.systemTime = systemTime;
    }
}