package com.yinian.alysis.model;

import java.util.Date;

public class UserInvite {
    private Integer id;

    private Integer groupid;

    private Integer usersInviteHdOld;

    private Integer usersInviteHdNew;

    private Integer invitesCountHdOld;

    private Integer invitesCountHdNew;

    private Integer usersInviteSmOld;

    private Integer usersInviteSmNew;

    private Integer invitesCountSmOld;

    private Integer invitesCountSmNew;

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

    public Integer getUsersInviteHdOld() {
        return usersInviteHdOld;
    }

    public void setUsersInviteHdOld(Integer usersInviteHdOld) {
        this.usersInviteHdOld = usersInviteHdOld;
    }

    public Integer getUsersInviteHdNew() {
        return usersInviteHdNew;
    }

    public void setUsersInviteHdNew(Integer usersInviteHdNew) {
        this.usersInviteHdNew = usersInviteHdNew;
    }

    public Integer getInvitesCountHdOld() {
        return invitesCountHdOld;
    }

    public void setInvitesCountHdOld(Integer invitesCountHdOld) {
        this.invitesCountHdOld = invitesCountHdOld;
    }

    public Integer getInvitesCountHdNew() {
        return invitesCountHdNew;
    }

    public void setInvitesCountHdNew(Integer invitesCountHdNew) {
        this.invitesCountHdNew = invitesCountHdNew;
    }

    public Integer getUsersInviteSmOld() {
        return usersInviteSmOld;
    }

    public void setUsersInviteSmOld(Integer usersInviteSmOld) {
        this.usersInviteSmOld = usersInviteSmOld;
    }

    public Integer getUsersInviteSmNew() {
        return usersInviteSmNew;
    }

    public void setUsersInviteSmNew(Integer usersInviteSmNew) {
        this.usersInviteSmNew = usersInviteSmNew;
    }

    public Integer getInvitesCountSmOld() {
        return invitesCountSmOld;
    }

    public void setInvitesCountSmOld(Integer invitesCountSmOld) {
        this.invitesCountSmOld = invitesCountSmOld;
    }

    public Integer getInvitesCountSmNew() {
        return invitesCountSmNew;
    }

    public void setInvitesCountSmNew(Integer invitesCountSmNew) {
        this.invitesCountSmNew = invitesCountSmNew;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }
}