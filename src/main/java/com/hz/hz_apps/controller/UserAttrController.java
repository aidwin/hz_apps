package com.hz.hz_apps.controller;

import com.hz.hz_apps.entity.UserAttr;
import com.hz.hz_apps.repo.UserAttrRepo;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserAttrController {

    @Autowired
    private UserAttrRepo userAttrRepo;

    @RequestMapping("loadShenceUserAttr")
    public void shenceUserAttr(String execDate) throws IOException, InvalidArgumentException {

        if (execDate == null){
            execDate = getDate();
        }
        List<UserAttr> userAttrList = userAttrRepo.getUserAttrsByImportTime(execDate);

        final SensorsAnalytics sa = new SensorsAnalytics(new SensorsAnalytics.ConcurrentLoggingConsumer("/home/ubuntu/app/hz_apps/shence/haozhuo-userAttr.log"));

        for (UserAttr rs:userAttrList) {
                String distinctId = rs.getUserId();
                Map<String, Object> properties = new HashMap<String, Object>();
                properties.put("userId",rs.getUserId());
                properties.put("createTime",rs.getCreateTime());
                properties.put("nickName",rs.getNickName());
                properties.put("genderId",rs.getGenderId());
                properties.put("birthday",rs.getBirthday());
                properties.put("hasMarried",rs.getHasMarried());
                properties.put("hasGivenBirth",rs.getHasGivenBirth());
                properties.put("isDue",rs.getIsDue());
                properties.put("dueType",rs.getDueType());
                properties.put("isPartner",rs.getIsPartner());
                properties.put("userRole",rs.getUserRole());
                properties.put("activeFlag",rs.getActiveFlag());
                properties.put("reportFlag",rs.getReportFlag());
                properties.put("type",rs.getType());
                sa.profileSet(distinctId, true, properties);
                sa.flush();
            }
    }
    public static String getDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");//yyyy-MM-dd HH:mm:ss 看个人需求
        Date date = null;
        String strDate=null;
        Date nowDate=new Date();
        try {
            date = simpleDateFormat.parse(simpleDateFormat.format(new Date(nowDate.getTime()-1000*60*60*24)));// 你想多少天就是多少天 //获取 星期 不建议用该方法 请使用Calendar.DAY_OF_WEEK
            strDate = simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strDate;
    }
}
