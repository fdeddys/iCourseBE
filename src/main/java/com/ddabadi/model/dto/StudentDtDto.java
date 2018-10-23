package com.ddabadi.model.dto;

import com.ddabadi.model.StudentDt;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StudentDtDto extends StudentDt implements Serializable {

    private String studentId;
    private String classesId;
    private String errCode;
    private String errDesc;
}

