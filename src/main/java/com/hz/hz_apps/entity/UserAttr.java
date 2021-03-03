package com.hz.hz_apps.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "ads_bi_user_attr_di")
public class UserAttr {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userId;
    private String createTime;
    private String phoneNumber;
    private String nickName;
    private String genderId;
    private String birthday;
    private String userName;
    private String hasMarried;
    private String hasGivenBirth;
    private String isDue;
    private String dueType;
    private String isPartner;
    private String userRole;
    private String activeFlag;
    private String reportFlag;
    private String type;
    private String importTime;
}
