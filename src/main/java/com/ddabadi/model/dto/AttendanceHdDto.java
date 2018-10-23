package com.ddabadi.model.dto;


import com.ddabadi.model.AttendanceHd;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AttendanceHdDto extends AttendanceHd implements Serializable {

    private String roomId;
    private String teacherId;
    private String outletId;
    private String strAttendanceDate;
    private String strAttendanceTime;

    private String errCode;
    private String errDesc;
}
