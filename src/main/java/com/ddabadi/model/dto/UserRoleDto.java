package com.ddabadi.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserRoleDto {

    private Long userId;
    private List<Long> roleId;

}
