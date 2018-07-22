package com.yinian.alysis.model;

import java.util.Date;

public class GroupEventcut {
    private Integer id;

    private Integer groupid;

    private String eventsCut;

    private Integer groupsCountOldPre;

    private Integer groupsCountOld;

    private Integer groupsCountNew;

    private Integer eventsCountOldPre;

    private Integer eventsCountOld;

    private Integer eventsCountNew;

    private Date period;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

    public String getEventsCut() {
        return eventsCut;
    }

    public void setEventsCut(String eventsCut) {
        this.eventsCut = eventsCut == null ? null : eventsCut.trim();
    }

    public Integer getGroupsCountOldPre() {
        return groupsCountOldPre;
    }

    public void setGroupsCountOldPre(Integer groupsCountOldPre) {
        this.groupsCountOldPre = groupsCountOldPre;
    }

    public Integer getGroupsCountOld() {
        return groupsCountOld;
    }

    public void setGroupsCountOld(Integer groupsCountOld) {
        this.groupsCountOld = groupsCountOld;
    }

    public Integer getGroupsCountNew() {
        return groupsCountNew;
    }

    public void setGroupsCountNew(Integer groupsCountNew) {
        this.groupsCountNew = groupsCountNew;
    }

    public Integer getEventsCountOldPre() {
        return eventsCountOldPre;
    }

    public void setEventsCountOldPre(Integer eventsCountOldPre) {
        this.eventsCountOldPre = eventsCountOldPre;
    }

    public Integer getEventsCountOld() {
        return eventsCountOld;
    }

    public void setEventsCountOld(Integer eventsCountOld) {
        this.eventsCountOld = eventsCountOld;
    }

    public Integer getEventsCountNew() {
        return eventsCountNew;
    }

    public void setEventsCountNew(Integer eventsCountNew) {
        this.eventsCountNew = eventsCountNew;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }
}