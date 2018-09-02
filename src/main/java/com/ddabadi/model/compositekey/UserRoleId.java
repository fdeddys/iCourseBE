package com.ddabadi.model.compositekey;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class UserRoleId implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Id
//    @Column
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "user_id")
    private Long userId;

    public UserRoleId(Long roleId, Long userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    public UserRoleId() {
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        return true;

        if (obj == null)
        return false;

        if (!(obj instanceof UserRoleId))
        return false;

        UserRoleId other = (UserRoleId) obj;

        if (roleId == null) {
            if (other.roleId != null) return false;
        } else if (!roleId.equals(other.roleId)) return false;

        if (userId== null) {
            if (other.userId != null) return false;
        } else if (!userId.equals(other.userId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = (roleId != null ? roleId.hashCode() : 0);
        result = 17 * result + (userId != null ? userId.hashCode() : 0);
        return result;

    }
}
