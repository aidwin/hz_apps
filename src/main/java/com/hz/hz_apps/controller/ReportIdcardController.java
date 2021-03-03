package com.hz.hz_apps.controller;

import com.alibaba.fastjson.JSON;
import com.hz.hz_apps.entity.HealthReport;
import com.hz.hz_apps.entity.ReportIdcard;
import com.hz.hz_apps.repo.HealthReportRepo;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hz.hz_apps.repo.ReportIdcardRepo;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportIdcardController {
    private static final Logger log = LoggerFactory.getLogger(ReportIdcardController.class);




    @Value("${report_idcard.idcard_url}")
    private String url;




    @Autowired
    private ReportIdcardRepo reportIdcardRepo;



    @Autowired
    private HealthReportRepo healthReportRepo;




    @RequestMapping({"getIdCardInfo2"})
    public List<ReportIdcard> getReportIdCradInfo2(String healthReposts) throws Exception {
        String data = "{\"healthReportIds\": \"" + healthReposts + "\"}";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(this.url);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        StringEntity stringEntity = new StringEntity(data, "utf-8");
        httpPost.setEntity((HttpEntity)stringEntity);
        CloseableHttpResponse res = httpclient.execute((HttpUriRequest)httpPost);
        String responseBody = EntityUtils.toString(res.getEntity());
        Object resData = JSON.parseObject(responseBody).get("data");
        if (resData != null) {
            List<ReportIdcard> idCards1 = JSON.parseArray(resData.toString(), ReportIdcard.class);
            return idCards1;
        }
        return null;
    }






    @RequestMapping({"getIdCardInfoTxt"})
    public void getReportIdCradInfoTxt(String executeDate) {
        try {
            if (executeDate == null) {
                executeDate = getYesterDay();
            }
            String filePath = "/home/ubuntu/app/hz_apps/data/";
            FileWriter fileWriter = new FileWriter(filePath + executeDate + ".txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            HealthReport healthReport = healthReportRepo.getHealthReportByCreateDate(executeDate);
            StringBuffer stringBuffer = new StringBuffer();
            int a = 0;
            Integer minId = healthReport.getMinId();
            Integer maxId = healthReport.getMaxId();
            for (int start = minId.intValue(); start <= maxId.intValue(); start++) {
                stringBuffer.append(start);
                stringBuffer.append(",");
                a++;
                if (a % 500 == 0) {
                    String healthReports = stringBuffer.substring(0, stringBuffer.length() - 1);
                    List<ReportIdcard> reportIdcardList = getReportIdCradInfo2(healthReports);
                    if (reportIdcardList != null) {
                        for (ReportIdcard reportIdcard : reportIdcardList) {
                            reportIdcard.setCreateDate(executeDate);
                            bufferedWriter.write(reportIdcard.getHealthReportId() + "||" + reportIdcard
                                    .getBirthday() + "||" + reportIdcard
                                    .getCreateDate() + "||" + reportIdcard
                                    .getIdCardNo() + "||" + reportIdcard
                                    .getSex());

                            bufferedWriter.newLine();
                        }
                    }
                    stringBuffer.delete(0, stringBuffer.length());
                }
                if (start == maxId.intValue()) {
                    String healthReports = stringBuffer.substring(0, stringBuffer.length() - 1);
                    List<ReportIdcard> reportIdcardList = getReportIdCradInfo2(healthReports);
                    if (reportIdcardList != null) {
                        for (ReportIdcard reportIdcard : reportIdcardList) {
                            reportIdcard.setCreateDate(executeDate);
                            bufferedWriter.write(reportIdcard.getHealthReportId() + "||" + reportIdcard
                                    .getBirthday() + "||" + reportIdcard
                                    .getCreateDate() + "||" + reportIdcard
                                    .getIdCardNo() + "||" + reportIdcard
                                    .getSex());

                            bufferedWriter.newLine();
                        }
                    }
                    stringBuffer.delete(0, stringBuffer.length());
                }
            }
            bufferedWriter.close();
            log.info(executeDate + "==========执行完成");
        } catch (Exception e) {
            log.info(executeDate + "============执行失败");
            e.printStackTrace();
        }
    }

    @RequestMapping({"getIdCardInfoInitTxt"})
    public void getReportIdCradInfoInitTxt() throws Exception {
        List<HealthReport> healthReportList = this.healthReportRepo.findAll();
        if (healthReportList != null) {
            for (HealthReport healthReport : healthReportList) {
                getReportIdCradInfoTxt(healthReport.getCreateDate());
            }
        }
    }






    @RequestMapping({"getIdCardInfoMonthTxt"})
    public void getReportIdCradInfoInitTxt(String exec_month) throws Exception {
        List<HealthReport> healthReportList = this.healthReportRepo.findByMonth(exec_month);
        if (healthReportList != null) {
            for (HealthReport healthReport : healthReportList) {
                getReportIdCradInfoTxt(healthReport.getCreateDate());
            }
        }
    }






    private String getYesterDay() {
        Date excuteTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(excuteTime);
        calendar.add(5, -1);
        excuteTime = calendar.getTime();
        log.info(sdf.format(excuteTime));
        return sdf.format(excuteTime);
    }


    @RequestMapping({"test"})
    public String test() { return "test"; }
}
