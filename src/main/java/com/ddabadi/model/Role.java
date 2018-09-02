package com.ddabadi.model;

import com.ddabadi.model.auditor.Audit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "m_roles", schema = "public")
@Data
@EqualsAndHashCode
public class Role extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "pk_role_seq", sequenceName = "roles_id_seq", allocationSize=1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator="pk_role_seq")
//    @GeneratedValue( generator="uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private Long id;



    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 255, nullable = false)
    private String description;

}
