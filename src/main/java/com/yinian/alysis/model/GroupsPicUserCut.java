package com.yinian.alysis.model;

import java.util.Date;

public class GroupsPicUserCut {
    private Integer id;

    private Integer groupid;

    private String usersCut;

    private String pictureCut;

    private Integer groupsCount;

    private Integer picturesCount;

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

    public String getPictureCut() {
        return pictureCut;
    }

    public void setPictureCut(String pictureCut) {
        this.pictureCut = pictureCut == null ? null : pictureCut.trim();
    }

    public Integer getGroupsCount() {
        return groupsCount;
    }

    public void setGroupsCount(Integer groupsCount) {
        this.groupsCount = groupsCount;
    }

    public Integer getPicturesCount() {
        return picturesCount;
    }

    public void setPicturesCount(Integer picturesCount) {
        this.picturesCount = picturesCount;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }
}