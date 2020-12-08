package com.hz.hz_apps.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class HealthReport {

    @Id
    private Integer id ;

    private String etlDatetime ;

    private String createDate;

    private Integer minId;

    private Integer maxId;
}
