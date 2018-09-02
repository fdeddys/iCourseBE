package com.ddabadi.model.dto;

import com.ddabadi.model.Role;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RoleDto extends Role implements Serializable {

    private String errMsg;

}
