package com.ddabadi.model.auditor;

import com.ddabadi.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

import java.util.Date;

@MappedSuperclass
@Data
public abstract class Audit implements Serializable {

    @JoinColumn(name = "updated_by")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User updatedBy;

    @JoinColumn(name = "created_by")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User createdBy;

    @Column(name = "created_at", columnDefinition = "timestamp(0) without time zone ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "timestamp(0) without time zone ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @PrePersist
    public void prePersist(){
        this.updatedAt = new Date();
        this.createdAt = new Date();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = new Date();
    }

}
