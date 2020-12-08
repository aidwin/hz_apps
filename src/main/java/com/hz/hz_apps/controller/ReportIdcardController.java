package com.hz.hz_apps.controller;

import com.alibaba.fastjson.JSON;
import com.hz.hz_apps.entity.HealthReport;
import com.hz.hz_apps.entity.ParmVo;
import com.hz.hz_apps.entity.ReportIdcard;
import com.hz.hz_apps.repo.HealthReportRepo;
import com.hz.hz_apps.repo.ReportIdcardRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Slf4j
@RestController
public class ReportIdcardController {
    //
    @Value("${report_idcard.idcard_url}")
    private String url;

    @Autowired
    private ReportIdcardRepo reportIdcardRepo;

    @Autowired
    private HealthReportRepo healthReportRepo;

    /**
     * 获取身份证号数据
     * @param healthReposts
     * @return
     * @throws Exception
     */
    @RequestMapping("getIdCardInfo2")
    public List<ReportIdcard> getReportIdCradInfo2(String healthReposts ) throws Exception {
//        String url = "192.168.10.156/getInfoByHealthReportIds";
//        String url = "http://opentest.ihaozhuo.com:20002/getInfoByHealthReportIds";
//        String data = "{\"healthReportIds\":\"222751016,2227\"}";
        String data = "{\"healthReportIds\": \"" + healthReposts + "\"}";
        System.out.println(data);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        StringEntity stringEntity = new StringEntity(data, "utf-8");
        System.out.println(httpPost.toString());
        System.out.println(stringEntity);
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse res = httpclient.execute(httpPost);
        String responseBody = EntityUtils.toString(res.getEntity());
        Object resData = JSON.parseObject(responseBody).get("data");
        if (resData != null) {
            List<ReportIdcard> idCards1 = JSON.parseArray(resData.toString(), ReportIdcard.class);
            for (ReportIdcard idCard : idCards1) {
                System.out.println(idCard.getIdCardNo());
                System.out.println(idCard.getHealthReportId());
                System.out.println(idCard.getBirthday());
                System.out.println(idCard.getSex());
            }
            return idCards1;
        }
        return null;
    }


    @RequestMapping("getIdCardInfo3")
    public List<ReportIdcard> getReportIdCradInfo3(String healthReposts ) throws Exception {
        ParmVo parmVo = new ParmVo();
        parmVo.setHealthReportIds(healthReposts);
        String data = JSON.toJSONString(parmVo);
        System.out.println(data);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        StringEntity stringEntity = new StringEntity(data, "utf-8");
        System.out.println(httpPost.toString());
        System.out.println(stringEntity);
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse res = httpclient.execute(httpPost);
        String responseBody = EntityUtils.toString(res.getEntity());
        Object resData = JSON.parseObject(responseBody).get("data");
        if (resData != null) {
            List<ReportIdcard> idCards1 = JSON.parseArray(resData.toString(), ReportIdcard.class);
            for (ReportIdcard idCard : idCards1) {
                System.out.println(idCard.getIdCardNo());
                System.out.println(idCard.getHealthReportId());
                System.out.println(idCard.getBirthday());
                System.out.println(idCard.getSex());
            }
            return idCards1;
        }
        return null;
    }



    /**
     * 根据日期获取身份证数据保存到mysql中
     * @param executeDate
     * @throws Exception
     */
    @RequestMapping("getIdCardInfo")
    public void getReportIdCradInfo(String executeDate) throws Exception {
        if (executeDate == null){
            executeDate = getYesterDay();
        }
        HealthReport healthReport = healthReportRepo.getHealthReportByCreateDate(executeDate);
        Integer init = healthReport.getMinId() - 1;
        StringBuffer stringBuffer = new StringBuffer();

        for( int start = healthReport.getMinId(); start<=healthReport.getMaxId() ; start++ ){
            stringBuffer.append(start);
            stringBuffer.append(",");
            if((start - init)%50 ==0) {
                String healthReports = stringBuffer.substring(1, stringBuffer.length() - 1);
                List<ReportIdcard> reportIdcardList = getReportIdCradInfo2(healthReports);
                if (reportIdcardList != null) {
                    for (ReportIdcard reportIdcard : reportIdcardList) {
                        reportIdcard.setCreateDate(executeDate);
                        Object save = reportIdcardRepo.save(reportIdcard);
                        System.out.println(save);
                    }
                }
                stringBuffer.delete(1,stringBuffer.length());
            }
        }
    }

    /**
     * 初始化
     * @param
     * @throws Exception
     */
    @RequestMapping("getIdCardInfoInit")
    public void getReportIdCradInfoInit() throws Exception {
        List<HealthReport> healthReportList = healthReportRepo.findAll();
        System.out.println(healthReportList.toString()+"===================");
        if (healthReportList !=  null ){
            for (HealthReport healthReport : healthReportList) {
                getReportIdCradInfo(healthReport.getCreateDate());
            }
        }
    }

//    /**
//     * 根据日期获取 和页码 获取分页数据
//     * @param excuteDate
//     * @param pageable
//     * @return
//     */
//    public String getHealthReport(String excuteDate,Pageable pageable) {
//        Page page1 = healthReportRepo.findAll(
//                new Specification<HealthReport>() {
//                    @Override
//                    public Predicate toPredicate(Root<HealthReport> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
////                CriteriaQuery<?> criteriaQuery = query.where(cb.equal(root.get("etl_date"), "20201125"));
//                        Path<String> etlDateField = root.get("createDate");
//                        List<Predicate> list = new ArrayList<Predicate>();
//                        if (StringUtils.isNotBlank(excuteDate)) {
//                            list.add(cb.equal(etlDateField, excuteDate));
//                        }
//                        query.where(list.toArray(new Predicate[list.size()]));
//                        return null;
//                    }
//                }, pageable);
//        System.out.println(page1.getTotalElements());
//        System.out.println(page1.getContent().toString());
//        List healthReportList = page1.getContent();
//        StringBuffer sb = new StringBuffer();
//        for (Object healthReport : healthReportList) {
//            HealthReport health = (HealthReport) healthReport;
//            sb.append(health.getHealthReportId());
//            sb.append(",");
//        }
//        if (sb.length() == 0){
//            return null;
//        }
//        String healthReports = sb.substring(0, sb.length() - 1);
//        return healthReports;
//    }

    /**
     * 获取昨天的日期
     * @return
     */
    private String getYesterDay() {
        Date excuteTime = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(excuteTime);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        excuteTime = calendar.getTime();
        log.info(sdf.format(excuteTime));
        return sdf.format(excuteTime);
    }


    @RequestMapping("test")
    public String test() {
        return "test";
    }


//    private String getHealthReports(){
//        113863168
//    }
}
