package com.ddabadi.model.dto;

import com.ddabadi.model.Registration;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RegistrationDto extends Registration implements Serializable {

    private String strRegDate;
    private String errCode;
    private String errDesc;
    private StudentDto studentDto;
    private String studentId;
    private String[] classesId;
}
