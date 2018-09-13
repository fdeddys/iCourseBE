package com.ddabadi.model;

import com.ddabadi.model.auditor.Audit;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person extends Audit implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;

    @Column(length = 150)
    private String name;

    @Column
    private String address1;

    @Column
    private String address2;

    @Column(length = 100)
    private String phone;

}
