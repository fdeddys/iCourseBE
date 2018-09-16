package com.ddabadi.model.dto;

import com.ddabadi.model.enu.EntityStatus;
import lombok.Data;

@Data
public class FilterDto {

    private String name;
    private String description;
    private EntityStatus active;
//    private GlobalTypeEnum globalType;
//
    private String email;
    private String firstName;
    private String lastName;
    private EntityStatus status;

}
