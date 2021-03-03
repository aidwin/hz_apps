//package com.hz.hz_apps.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.hz.hz_apps.entity.HealthReport;
//import com.hz.hz_apps.entity.ReportIdcard;
//import com.hz.hz_apps.repo.HealthReportRepo;
//import com.hz.hz_apps.repo.ReportIdcardRepo;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.*;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//@Slf4j
//@RestController
//public class ReportIdcardController {
//    //
//    @Value("${report_idcard.idcard_url}")
//    private String url;
//
//    @Autowired
//    private ReportIdcardRepo reportIdcardRepo;
//
//    @Autowired
//    private HealthReportRepo healthReportRepo;
//
//    /**
//     * 获取身份证号数据
//     * @param healthReposts
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("getIdCardInfo2")
//    public List<ReportIdcard> getReportIdCradInfo2(String healthReposts ) throws Exception {
////        String url = "192.168.10.156/getInfoByHealthReportIds";
////        String url = "http://opentest.ihaozhuo.com:20002/getInfoByHealthReportIds";
////        String data = "{\"healthReportIds\":\"222751016,2227\"}";
//        String data = "{\"healthReportIds\": \"" + healthReposts + "\"}";
//        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
//        httpPost.setHeader("Accept", "application/json");
//        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//        StringEntity stringEntity = new StringEntity(data, "utf-8");
//        httpPost.setEntity(stringEntity);
//        CloseableHttpResponse res = httpclient.execute(httpPost);
//        String responseBody = EntityUtils.toString(res.getEntity());
//        Object resData = JSON.parseObject(responseBody).get("data");
//        if (resData != null) {
//            List<ReportIdcard> idCards1 = JSON.parseArray(resData.toString(), ReportIdcard.class);
//            return idCards1;
//        }
//        return null;
//    }
//
//    /**
//     * 保存文件
//     * @param executeDate
//     * @throws Exception
//     */
//    @RequestMapping("getIdCardInfoTxt")
//    public void getReportIdCradInfoTxt(String executeDate){
//        try {
//            if (executeDate == null){
//                executeDate = getYesterDay();
//            }
//            String filePath = "/home/ubuntu/app/hz_apps/data/";
//            FileWriter fileWriter = new FileWriter(filePath + executeDate + ".txt",true);
//            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
//            HealthReport healthReport = healthReportRepo.getHealthReportByCreateDate(executeDate);
//            StringBuffer stringBuffer = new StringBuffer();
//            int a = 0;
//            Integer minId = healthReport.getMinId();
//            Integer maxId = healthReport.getMaxId();
//            for( int start = minId; start<=maxId ; start++ ){
//                stringBuffer.append(start);
//                stringBuffer.append(",");
//                a++;
//                if(a%500 ==0) {
//                    String healthReports = stringBuffer.substring(0, stringBuffer.length() - 1);
//                    List<ReportIdcard> reportIdcardList = getReportIdCradInfo2(healthReports);
//                    if (reportIdcardList != null) {
//                        for (ReportIdcard reportIdcard : reportIdcardList) {
//                            reportIdcard.setCreateDate(executeDate);
//                            bufferedWriter.write(reportIdcard.getHealthReportId()+"||"
//                                +reportIdcard.getBirthday()+"||"
//                                +reportIdcard.getCreateDate()+"||"
//                                +reportIdcard.getIdCardNo()+"||"
//                                +reportIdcard.getSex()
//                                );
//                            bufferedWriter.newLine();
//                        }
//                    }
//                    stringBuffer.delete(0,stringBuffer.length());
//                }
//                if(start ==maxId) {
//                    String healthReports = stringBuffer.substring(0, stringBuffer.length() - 1);
//                    List<ReportIdcard> reportIdcardList = getReportIdCradInfo2(healthReports);
//                    if (reportIdcardList != null) {
//                        for (ReportIdcard reportIdcard : reportIdcardList) {
//                            reportIdcard.setCreateDate(executeDate);
//                            bufferedWriter.write(reportIdcard.getHealthReportId()+"||"
//                                    +reportIdcard.getBirthday()+"||"
//                                    +reportIdcard.getCreateDate()+"||"
//                                    +reportIdcard.getIdCardNo()+"||"
//                                    +reportIdcard.getSex()
//                            );
//                            bufferedWriter.newLine();
//                        }
//                    }
//                    stringBuffer.delete(0,stringBuffer.length());
//                }
//            }
//            bufferedWriter.close();
//            log.info(executeDate+"==========执行完成");
//        } catch (Exception e) {
//            log.info(executeDate+"============执行失败");
//            e.printStackTrace();
//        }
//    }
//
//    @RequestMapping("getIdCardInfoInitTxt")
//    public void getReportIdCradInfoInitTxt() throws Exception {
//        List<HealthReport> healthReportList = healthReportRepo.findAll();
//        if (healthReportList !=  null ){
//            for (HealthReport healthReport : healthReportList) {
//                getReportIdCradInfoTxt(healthReport.getCreateDate());
//            }
//        }
//    }
//
//    /**
//     * 指定具体月份 yyyymm
//     * @param
//     * @throws Exception
//     */
//    @RequestMapping("getIdCardInfoMonthTxt")
//    public void getReportIdCradInfoInitTxt(String exec_month ) throws Exception {
//        List<HealthReport> healthReportList = healthReportRepo.findByMonth(exec_month);
//        if (healthReportList !=  null ){
//            for (HealthReport healthReport : healthReportList) {
//                getReportIdCradInfoTxt(healthReport.getCreateDate());
//            }
//        }
//    }
//
//
//    /**
//     * 获取昨天的日期
//     * @return
//     */
//    private String getYesterDay() {
//        Date excuteTime = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(excuteTime);
//        calendar.add(Calendar.DAY_OF_MONTH, -1);
//        excuteTime = calendar.getTime();
//        log.info(sdf.format(excuteTime));
//        return sdf.format(excuteTime);
//    }
//
//    @RequestMapping("test")
//    public String test() {
//        return "test";
//    }
//
//
//}
