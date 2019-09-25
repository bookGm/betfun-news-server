package com.guansuo.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;
public class SmsUtil {
    public static boolean sendSMS(String phone, String content){
        BufferedReader in=null;
        int dayId = (int) (System.currentTimeMillis() / (1000 * 24 * 60 * 60));
        StringBuffer sb = new StringBuffer(Constant.mtUrl + "action=send&");
        sb.append("account=" + Constant.mtUsername);
        sb.append("&password=" + Constant.mtPassword);
        //&mobile=1312607811,1312607811
        sb.append("&mobile="+ phone);
        try {
            sb.append("&content="+ URLEncoder.encode(content,"utf-8"));
            sb.append("&extno=1069088964");
            sb.append("&rt=json");

            URL url = new URL(sb.toString());
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.connect();
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            String result = "";
            while ((line=in.readLine())!=null){
                result += line;
            }
            in.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(null!=in){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
