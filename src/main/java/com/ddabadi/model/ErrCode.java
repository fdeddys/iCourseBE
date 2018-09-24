package com.ddabadi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "m_error_code", schema = "public")
@Data
@NoArgsConstructor
public class ErrCode implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5, nullable = false)
    private String code;

    @Column(length = 255, nullable = false)
    private String description;

}
