package com.ddabadi.model.dto;

import com.ddabadi.model.enu.EntityStatus;
import lombok.Data;

@Data
public class RoleMenuViewDto {

    private String menuDescription;
    private EntityStatus status;
    private Long menuId;
}
