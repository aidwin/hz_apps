package com.hz.hz_apps.controller;

import java.io.*;

public class Test {
    public static void main(String[] args) throws IOException {
//        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("fsdfsdf");
//        System.out.println(stringBuffer);
//        stringBuffer.delete(0,stringBuffer.length());
//        System.out.println(stringBuffer);

        String filePath = "D:\\";
        Writer out =new BufferedWriter(new FileWriter(filePath+"213213"+".txt",true));
        out.write("fafdf");
        out.write("\r\n");
        out.close();
    }
}
