package com.hz.hz_apps.entity;

import lombok.Data;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;


@Entity
@Data
//@Accessors(chain = true)
//@Document(indexName = "report_idcard_info", type = "report")
public class ReportIdcard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "id_card_number")
    private String idCardNo;

    private String healthReportId;

    private Integer sex;

    private String birthday;

    private DateTime createDatetime;

    private String createDate;

    private DateTime upDatetime;
}
