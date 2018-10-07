package com.ddabadi.model.dto;

import com.ddabadi.model.Classes;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ClassesDto extends Classes implements Serializable {
    private String errCode;
    private String errDesc;
}
