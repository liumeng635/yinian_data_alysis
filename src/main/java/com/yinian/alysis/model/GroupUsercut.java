package com.yinian.alysis.model;

import java.util.Date;

public class GroupUsercut {
    private Integer id;

    private Integer groupid;

    private String usersCut;

    private Integer groupsCountOldPre;

    private Integer groupsCountOld;

    private Integer groupsCountNew;

    private Integer usersCountOldPre;

    private Integer usersCountOld;

    private Integer usersCountNew;

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

    public String getUsersCut() {
        return usersCut;
    }

    public void setUsersCut(String usersCut) {
        this.usersCut = usersCut == null ? null : usersCut.trim();
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

    public Integer getUsersCountOldPre() {
        return usersCountOldPre;
    }

    public void setUsersCountOldPre(Integer usersCountOldPre) {
        this.usersCountOldPre = usersCountOldPre;
    }

    public Integer getUsersCountOld() {
        return usersCountOld;
    }

    public void setUsersCountOld(Integer usersCountOld) {
        this.usersCountOld = usersCountOld;
    }

    public Integer getUsersCountNew() {
        return usersCountNew;
    }

    public void setUsersCountNew(Integer usersCountNew) {
        this.usersCountNew = usersCountNew;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }
}