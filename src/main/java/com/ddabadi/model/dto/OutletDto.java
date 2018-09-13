package com.ddabadi.model.dto;

import com.ddabadi.model.Outlet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OutletDto implements Serializable {

    private String errCode;
    private String errDesc;
    private Outlet outlet;
    private Page<Outlet> resultPage;

}
