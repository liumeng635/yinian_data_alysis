package com.yinian.alysis.model;

import java.util.Date;

public class UserPlaycut {
    private Integer id;

    private Integer groupid;

    private String playsCut;

    private Integer usersCountOldHd;

    private Integer usersCountNewHd;

    private Integer playsCountOldHd;

    private Integer playsCountNewHd;

    private Integer usersCountOldPreSm;

    private Integer usersCountOldSm;

    private Integer usersCountNewSm;

    private Integer playsCountOldPreSm;

    private Integer playsCountOldSm;

    private Integer playsCountNewSm;

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

    public String getPlaysCut() {
        return playsCut;
    }

    public void setPlaysCut(String playsCut) {
        this.playsCut = playsCut == null ? null : playsCut.trim();
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

    public Integer getPlaysCountOldHd() {
        return playsCountOldHd;
    }

    public void setPlaysCountOldHd(Integer playsCountOldHd) {
        this.playsCountOldHd = playsCountOldHd;
    }

    public Integer getPlaysCountNewHd() {
        return playsCountNewHd;
    }

    public void setPlaysCountNewHd(Integer playsCountNewHd) {
        this.playsCountNewHd = playsCountNewHd;
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

    public Integer getPlaysCountOldPreSm() {
        return playsCountOldPreSm;
    }

    public void setPlaysCountOldPreSm(Integer playsCountOldPreSm) {
        this.playsCountOldPreSm = playsCountOldPreSm;
    }

    public Integer getPlaysCountOldSm() {
        return playsCountOldSm;
    }

    public void setPlaysCountOldSm(Integer playsCountOldSm) {
        this.playsCountOldSm = playsCountOldSm;
    }

    public Integer getPlaysCountNewSm() {
        return playsCountNewSm;
    }

    public void setPlaysCountNewSm(Integer playsCountNewSm) {
        this.playsCountNewSm = playsCountNewSm;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }
}