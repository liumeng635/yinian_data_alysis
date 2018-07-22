package com.yinian.alysis.model;

import java.util.Date;

public class UserGroupcut {
    private Integer id;

    private Integer groupid;

    private String groupsCut;

    private Integer usersCountOldPre;

    private Integer usersCountOld;

    private Integer usersCountNew;

    private Integer groupsCountOldPre;

    private Integer groupsCountOld;

    private Integer groupsCountNew;

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

    public String getGroupsCut() {
        return groupsCut;
    }

    public void setGroupsCut(String groupsCut) {
        this.groupsCut = groupsCut == null ? null : groupsCut.trim();
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

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }
}