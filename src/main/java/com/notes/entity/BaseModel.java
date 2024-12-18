package com.notes.entity;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass

public abstract class BaseModel {

    @CreatedBy
    @Column(updatable = false)
    private Integer createdBy;

    @CreatedDate
    @Column(updatable = false)
    private Date createdOn;

    @LastModifiedBy
    @Column(insertable = false)
    private Integer updatedBy;

    @LastModifiedDate
    @Column(insertable = false)
    private Date updatedOn;
}
