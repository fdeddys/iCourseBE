package com.ddabadi.model.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AccountDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String imageUrl;
    private boolean activated;
    private String langKey;
    private String createdBy;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;
    private List<String> roleList;

}
