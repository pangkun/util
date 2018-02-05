package utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 庞昆 on 2017/9/5.
 */
public class NetUtils {
    public static HttpURLConnection httpURLConnection = null;

    public static BufferedWriter upLoad(String request) throws IOException {
        URL url = new URL(request);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpURLConnection.connect();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(),"utf-8"));
        return bufferedWriter;
    }

    public static String downLoad() throws IOException {
        BufferedReader bufferedReader =new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder stringBuilder=new StringBuilder();
        String data=null;
        while((data=bufferedReader.readLine())!=null){
            stringBuilder.append(data+"\r\n");
        }
        bufferedReader.close();
        httpURLConnection.disconnect();
        return stringBuilder.toString();
    }
    public static String downLoad(String request) throws IOException {
        URL url = new URL(request);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String data = null;
        while ((data = bufferedReader.readLine()) != null)
            stringBuilder.append(data + "\r\n");
        return stringBuilder.toString();
    }
}
