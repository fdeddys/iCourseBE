package com.ddabadi.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserMenuDto {

    private Long id;
    private String name;
    private String description;
    private String link;
    private String icon;
    private Long parentId;
}
