package com.yinian.alysis.model;

import java.util.Date;

public class ActUserCluster {
    private Integer id;

    private Integer groupid;

    private String retentionPeriod;

    private String userCut;

    private Integer usersTotal;

    private Integer usersReupload;

    private Integer usersCreate;

    private Integer usersUpload;

    private Integer usersPlay;

    private Integer countsCreate;

    private Integer countsUpload;

    private Integer countsPlay;

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

    public String getRetentionPeriod() {
        return retentionPeriod;
    }

    public void setRetentionPeriod(String retentionPeriod) {
        this.retentionPeriod = retentionPeriod == null ? null : retentionPeriod.trim();
    }

    public String getUserCut() {
        return userCut;
    }

    public void setUserCut(String userCut) {
        this.userCut = userCut == null ? null : userCut.trim();
    }

    public Integer getUsersTotal() {
        return usersTotal;
    }

    public void setUsersTotal(Integer usersTotal) {
        this.usersTotal = usersTotal;
    }

    public Integer getUsersReupload() {
        return usersReupload;
    }

    public void setUsersReupload(Integer usersReupload) {
        this.usersReupload = usersReupload;
    }

    public Integer getUsersCreate() {
        return usersCreate;
    }

    public void setUsersCreate(Integer usersCreate) {
        this.usersCreate = usersCreate;
    }

    public Integer getUsersUpload() {
        return usersUpload;
    }

    public void setUsersUpload(Integer usersUpload) {
        this.usersUpload = usersUpload;
    }

    public Integer getUsersPlay() {
        return usersPlay;
    }

    public void setUsersPlay(Integer usersPlay) {
        this.usersPlay = usersPlay;
    }

    public Integer getCountsCreate() {
        return countsCreate;
    }

    public void setCountsCreate(Integer countsCreate) {
        this.countsCreate = countsCreate;
    }

    public Integer getCountsUpload() {
        return countsUpload;
    }

    public void setCountsUpload(Integer countsUpload) {
        this.countsUpload = countsUpload;
    }

    public Integer getCountsPlay() {
        return countsPlay;
    }

    public void setCountsPlay(Integer countsPlay) {
        this.countsPlay = countsPlay;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }
}