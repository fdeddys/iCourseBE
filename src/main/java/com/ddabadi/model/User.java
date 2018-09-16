package com.ddabadi.model;


import com.ddabadi.model.enu.EntityStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "m_users", schema = "public")
@Data
@EqualsAndHashCode
@ToString

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "pk_user_seq", sequenceName = "users_id_seq", allocationSize=1)
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator="pk_user_seq")
    private Long id;

    @Column(name ="user_name", length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private EntityStatus status;

    @Column(name = "remember_token", length = 100, nullable = false)
    private String rememberToken;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at", columnDefinition = "timestamp(0) without time zone ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp(0) without time zone ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    public User() {
        super();
        status = EntityStatus.ACTIVE;
        this.setCreatedAt(new Date());
        this.setUpdatedAt(new Date());
    }

    @ManyToOne
    @JoinColumn(name = "outlet_id", columnDefinition = "CHAR(50)")
    private Outlet outlet;

}
