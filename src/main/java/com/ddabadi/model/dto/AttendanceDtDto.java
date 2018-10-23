package com.ddabadi.model.dto;

import com.ddabadi.model.AttendanceDt;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AttendanceDtDto extends AttendanceDt implements Serializable {

    private String attendanceHdId;
    private String studentId;

    private String errCode;
    private String errDesc;

}
