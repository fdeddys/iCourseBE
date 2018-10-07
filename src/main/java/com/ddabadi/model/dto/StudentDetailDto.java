package com.ddabadi.model.dto;


import com.ddabadi.model.StudentDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class StudentDetailDto extends StudentDetail implements Serializable {

    private String classId;
    private String studentId;
    private String errCode;
    private String errDesc;

}
