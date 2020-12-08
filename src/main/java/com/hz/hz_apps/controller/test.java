package com.hz.hz_apps.controller;

import com.hz.hz_apps.entity.HealthReport;
import com.hz.hz_apps.repo.HealthReportRepo;
import com.hz.hz_apps.vo.SearchVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class test {

    @Autowired
    private HealthReportRepo healthReportRepo;
//
//    @RequestMapping("test")
//    public void getHealthReport(HealthReport healthReport, SearchVo searchVo) {
//        Pageable pageable = new PageRequest(1, 20);
//        searchVo.setExcuteDate("20201125");
//        Page page1 = healthReportRepo.findAll(
//                new Specification<HealthReport>() {
//                    @Override
//                    public Predicate toPredicate(Root<HealthReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
////                CriteriaQuery<?> criteriaQuery = query.where(cb.equal(root.get("etl_date"), "20201125"));
//                        Path<String> etlDateField = root.get("etlDate");
//                        List<Predicate> list = new ArrayList<Predicate>();
//
//                        if (StringUtils.isNotBlank(searchVo.getExcuteDate())) {
//                            list.add(cb.equal(etlDateField, searchVo.getExcuteDate()));
//                        }
//                        query.where(list.toArray(new Predicate[list.size()]));
//                        return null;
//                    }
//                }, pageable);
//        System.out.println(page1.getTotalElements());
//    }

    public static void main(String[] args) {
        System.out.println(Math.ceil(35 / 20));
    }
}

