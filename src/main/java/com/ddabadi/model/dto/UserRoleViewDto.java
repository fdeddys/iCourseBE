package com.ddabadi.model.dto;

import com.ddabadi.model.enu.EntityStatus;
import lombok.Data;

@Data
public class UserRoleViewDto {

    private Long userId;
    private Long roleId;
    private String roleName;
    private String roleDescription;
    private EntityStatus status;
}
