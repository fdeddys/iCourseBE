package com.ddabadi.model;

import com.ddabadi.model.auditor.Audit;
import com.ddabadi.model.compositekey.UserRoleId;
import com.ddabadi.model.enu.EntityStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "m_role_user", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserRole extends Audit implements Serializable {

    @EmbeddedId
    private UserRoleId userRoleId;

    @Column
    private EntityStatus status;

}
