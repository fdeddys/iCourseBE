package com.ddabadi.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class RoleMenuDto {

    private Long roleId;
    private List<Long> menuId;

}
