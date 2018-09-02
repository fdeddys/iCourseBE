package com.ddabadi.model.dto;

import com.ddabadi.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString(callSuper = true, exclude = {"password"})
public class UserDto extends User {

    private String errMsg;
    private String oldPass;
    private Long userId;
    private Long roleId;

}
