package com.lamvanhai.entity;

import com.lamvanhai.annotation.Column;
import com.lamvanhai.annotation.Entity;
import com.lamvanhai.annotation.Id;

@Entity(name = "permission")
public class Permission {
    @Id(name = "user_id")
    private long userId;
    @Column(name = "role_id")
    private long roleId;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }
}
