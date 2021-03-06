package com.ddabadi.model;

import com.ddabadi.model.auditor.Audit;
import com.ddabadi.model.enu.EntityStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "m_room")
public class Room extends Audit implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column
    private String id;

    @Column(length = 100)
    private String name;

//    @JsonIgnore
//    @JsonProperty(access = Access WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "outlet_id")
    private Outlet outlet;

    @Column
    private EntityStatus status;



}
