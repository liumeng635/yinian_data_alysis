package com.yinian.alysis.model;

import java.util.Date;

public class CreateAcc {
    private Integer id;

    private Integer groupid;

    private Integer groupType;

    private String usersCut;

    private Integer groupsCount;

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

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    public String getUsersCut() {
        return usersCut;
    }

    public void setUsersCut(String usersCut) {
        this.usersCut = usersCut == null ? null : usersCut.trim();
    }

    public Integer getGroupsCount() {
        return groupsCount;
    }

    public void setGroupsCount(Integer groupsCount) {
        this.groupsCount = groupsCount;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }
}