package com.ddabadi.model;


import com.ddabadi.model.auditor.Audit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "m_menus", schema = "public")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Menu extends Audit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "pk_menu_seq", sequenceName = "menus_id_seq", allocationSize=1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator="pk_menu_seq")
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String description;

    @Column(length = 200)
    private String link;

    private Long parentId;

    @Column(length = 50)
    private String icon;

    @Column(nullable = false)
    private int status;

}
