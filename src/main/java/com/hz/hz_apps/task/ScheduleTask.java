package com.hz.hz_apps.task;

import com.hz.hz_apps.controller.ReportIdcardController;
import com.hz.hz_apps.controller.UserAttrController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;


@Slf4j
@Configuration
@EnableScheduling   // 2.开启定时任务
public class ScheduleTask {

    @Autowired
    ReportIdcardController reportIdcardController;
    @Autowired
    UserAttrController userAttrController;

    @Scheduled(cron = "0 0 6 * * ?")
    private void configureTasks() throws Exception {
        reportIdcardController.getReportIdCradInfoTxt(null);
        log.info("执行静态定时任务时间: " +LocalDateTime.now() ) ;
    }
    @Scheduled(cron = "0 0 8 * * ?")
    private void configureTasks2() throws Exception {
        userAttrController.shenceUserAttr(null);
        log.info("执行静态定时任务时间: " +LocalDateTime.now() ) ;
    }
}
