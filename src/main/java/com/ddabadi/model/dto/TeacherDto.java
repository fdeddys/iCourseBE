package com.ddabadi.model.dto;

import com.ddabadi.model.Teacher;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TeacherDto extends Teacher implements Serializable {

    private String errCode;
    private String errDesc;

}
