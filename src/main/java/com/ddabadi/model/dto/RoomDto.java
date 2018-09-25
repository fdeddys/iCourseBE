package com.ddabadi.model.dto;

import com.ddabadi.model.Room;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RoomDto extends Room implements Serializable {

    private String errCode;
    private String errDesc;
}
