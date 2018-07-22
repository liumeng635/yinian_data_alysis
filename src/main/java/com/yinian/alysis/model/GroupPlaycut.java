package com.yinian.alysis.model;

import java.util.Date;

public class GroupPlaycut {
    private Integer id;

    private Integer groupid;

    private String playCut;

    private Integer groupsCountOldPre;

    private Integer groupsCountOld;

    private Integer groupsCountNew;

    private Integer playsCountOldPre;

    private Integer playsCountOld;

    private Integer playsCountNew;

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

    public String getPlayCut() {
        return playCut;
    }

    public void setPlayCut(String playCut) {
        this.playCut = playCut == null ? null : playCut.trim();
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

    public Integer getPlaysCountOldPre() {
        return playsCountOldPre;
    }

    public void setPlaysCountOldPre(Integer playsCountOldPre) {
        this.playsCountOldPre = playsCountOldPre;
    }

    public Integer getPlaysCountOld() {
        return playsCountOld;
    }

    public void setPlaysCountOld(Integer playsCountOld) {
        this.playsCountOld = playsCountOld;
    }

    public Integer getPlaysCountNew() {
        return playsCountNew;
    }

    public void setPlaysCountNew(Integer playsCountNew) {
        this.playsCountNew = playsCountNew;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }
}