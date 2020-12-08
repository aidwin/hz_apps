package com.hz.hz_apps.repo;

import com.hz.hz_apps.entity.HealthReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface HealthReportRepo extends JpaRepository<HealthReport,Integer> ,JpaSpecificationExecutor {

//    List<HealthReport> findByEtlDatetimeIn();

//    @Query(value = "select count(distinct Health_report_id ) as cnt from health_report where create_date = ?1 ", nativeQuery = true)
//    Integer getHealthReportCountsByEtlDate( String etlDate);
//
//    @Query(value = "select create_date  from health_report where create_date is not null group by create_date ", nativeQuery = true)
//    List<String> getHealthReportAllDate();

    @Query(value = "select *  from health_report where create_date < 20201129 ", nativeQuery = true)
    List<HealthReport> findAll();

    HealthReport getHealthReportByCreateDate( String createDate );
}
