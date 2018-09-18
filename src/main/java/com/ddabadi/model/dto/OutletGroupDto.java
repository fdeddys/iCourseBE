package com.ddabadi.model.dto;

import com.ddabadi.model.GroupOutlet;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;

@Data
public class OutletGroupDto extends GroupOutlet implements Serializable {

    private String errCode;
    private String errDesc;

}
