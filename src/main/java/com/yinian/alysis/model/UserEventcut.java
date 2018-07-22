package com.yinian.alysis.model;

import java.util.Date;

public class UserEventcut {
    private Integer id;

    private Integer groupid;

    private String eventsCut;

    private Integer usersCountOldHd;

    private Integer usersCountNewHd;

    private Integer eventsCountOldHd;

    private Integer eventsCountNewHd;

    private Integer usersCountOldPreSm;

    private Integer usersCountOldSm;

    private Integer usersCountNewSm;

    private Integer eventsCountOldPreSm;

    private Integer eventsCountOldSm;

    private Integer eventsCountNewSm;

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

    public Integer getUsersCountOldHd() {
        return usersCountOldHd;
    }

    public void setUsersCountOldHd(Integer usersCountOldHd) {
        this.usersCountOldHd = usersCountOldHd;
    }

    public Integer getUsersCountNewHd() {
        return usersCountNewHd;
    }

    public void setUsersCountNewHd(Integer usersCountNewHd) {
        this.usersCountNewHd = usersCountNewHd;
    }

    public Integer getEventsCountOldHd() {
        return eventsCountOldHd;
    }

    public void setEventsCountOldHd(Integer eventsCountOldHd) {
        this.eventsCountOldHd = eventsCountOldHd;
    }

    public Integer getEventsCountNewHd() {
        return eventsCountNewHd;
    }

    public void setEventsCountNewHd(Integer eventsCountNewHd) {
        this.eventsCountNewHd = eventsCountNewHd;
    }

    public Integer getUsersCountOldPreSm() {
        return usersCountOldPreSm;
    }

    public void setUsersCountOldPreSm(Integer usersCountOldPreSm) {
        this.usersCountOldPreSm = usersCountOldPreSm;
    }

    public Integer getUsersCountOldSm() {
        return usersCountOldSm;
    }

    public void setUsersCountOldSm(Integer usersCountOldSm) {
        this.usersCountOldSm = usersCountOldSm;
    }

    public Integer getUsersCountNewSm() {
        return usersCountNewSm;
    }

    public void setUsersCountNewSm(Integer usersCountNewSm) {
        this.usersCountNewSm = usersCountNewSm;
    }

    public Integer getEventsCountOldPreSm() {
        return eventsCountOldPreSm;
    }

    public void setEventsCountOldPreSm(Integer eventsCountOldPreSm) {
        this.eventsCountOldPreSm = eventsCountOldPreSm;
    }

    public Integer getEventsCountOldSm() {
        return eventsCountOldSm;
    }

    public void setEventsCountOldSm(Integer eventsCountOldSm) {
        this.eventsCountOldSm = eventsCountOldSm;
    }

    public Integer getEventsCountNewSm() {
        return eventsCountNewSm;
    }

    public void setEventsCountNewSm(Integer eventsCountNewSm) {
        this.eventsCountNewSm = eventsCountNewSm;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }
}