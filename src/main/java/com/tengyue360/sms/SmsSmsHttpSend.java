package com.tengyue360.sms;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http 请求
 *
 * @author xuliang 2017/06/18
 */
public class SmsSmsHttpSend {
    
	public static String postSend(String strUrl,String param){
		URL url = null;
		HttpURLConnection connection = null;
		
		try {
			url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-type", "text/html");
			connection.setUseCaches(false);
			connection.setConnectTimeout(5000);  //设置连接超时时间为 3s
			connection.connect();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "utf-8"));
			bw.write(param);
			bw.flush();
			bw.close();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
		
			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
				}
		}
		
		
	}
}
