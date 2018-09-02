package com.ddabadi.model;

import com.ddabadi.model.auditor.Audit;
import com.ddabadi.model.compositekey.RoleMenuId;
import com.ddabadi.model.enu.EntityStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "m_role_menu", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleMenu extends Audit implements Serializable {

    @EmbeddedId
    private RoleMenuId roleMenuId;

    @Column
    private EntityStatus status;

}
