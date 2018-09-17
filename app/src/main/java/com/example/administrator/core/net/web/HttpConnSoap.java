package com.example.administrator.core.net.web;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class HttpConnSoap
{

    private String getHeaderProtocol(String methodName,String nameSpace,Map<String,String > map){

        String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body />";
        String s1, s2, s3, s4 = "";
        s4 = "<" + methodName + " xmlns=\""+nameSpace+"\">";
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                s1=entry.getKey();
                s2=entry.getValue();
                s3 = "<" + s1 + ">" + s2 + "</" + s1 + ">";
                s4 = s4 + s3;
            }
        }
        s4 = s4 + "</" + methodName + ">";
        String s5 = "</soap:Envelope>";
        return  soap + s4 + s5;
    }



    public synchronized ArrayList<String> getData(String MethodName,String nameSpace,String baseUrl,Map<String,String> map){
        ArrayList<String> Values =new ArrayList<>();
        String ServerUrl = baseUrl+"Service.asmx";
        String soapAction = nameSpace + MethodName;
        String requestData = getHeaderProtocol(MethodName, nameSpace, map);
        HttpURLConnection con=null;
        try
        {
            URL url = new URL(ServerUrl);
            con = (HttpURLConnection) url.openConnection();
            return post(con,ServerUrl,requestData,soapAction,MethodName);
        }
        catch (Exception e)
        {return null;
        }finally {
            //noinspection ConstantConditions
            con.disconnect();
        }
    }


    public synchronized ArrayList<String> getData(String MethodName,String nameSpace,String baseUrl){
        ArrayList<String> Values =new ArrayList<>();

        String ServerUrl = baseUrl+"Service.asmx";
        String soapAction = nameSpace + MethodName;
        String requestData = getHeaderProtocol(MethodName, nameSpace, null);
        HttpURLConnection con=null;
        try {
            URL url = new URL(ServerUrl);
            con = (HttpURLConnection) url.openConnection();
            return post(con, ServerUrl, requestData, soapAction, MethodName);
        }catch (StringIndexOutOfBoundsException e){
            Values.clear();
            return Values;
        }
        catch (Exception e)
        {
            return null;
        }finally {
            if(con!=null){
                con.disconnect();
            }
        }
    }

    private ArrayList<String> post(HttpURLConnection con,String serverUrl,String requestData,String soapAction,String methodName) throws IOException {
        URL url = new URL(serverUrl);
        byte[] bytes = requestData.getBytes("utf-8");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);
        con.setConnectTimeout(6000);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
        con.setRequestProperty("SOAPAction", soapAction);
        con.setRequestProperty("Content-Length", "" + bytes.length);
        OutputStream outStream = con.getOutputStream();
        outStream.write(bytes);
        outStream.flush();
        outStream.close();
        InputStream inStream = con.getInputStream();
        if(inStream!=null){
            return StreamtoValue(inStream, methodName);
        }
        return null;
    }





    public  ArrayList<String> GetWebServer(String nameSpace,String MethodName,String baseUrl,Map<String, String> map)
    {
        ArrayList<String> Values =new ArrayList<>();
        String ServerUrl = baseUrl+"Service.asmx";
        String soapAction = nameSpace + MethodName;
        String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                + "<soap:Body />";
        String s1, s2, s3, s4 = "";
        s4 = "<" + MethodName + " xmlns=\""+nameSpace+"\">";
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                s1=entry.getKey();
                s2=entry.getValue();
                s3 = "<" + s1 + ">" + s2 + "</" + s1 + ">";
                s4 = s4 + s3;
            }
        }

        s4 = s4 + "</" + MethodName + ">";
        String s5 = "</soap:Envelope>";
        String requestData = soap + s4 + s5;
        Log.e("--请求体-",requestData);
        HttpURLConnection con = null;
        try
        {
            URL url = new URL(ServerUrl);
            con = (HttpURLConnection) url.openConnection();
            byte[] bytes = requestData.getBytes("utf-8");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setConnectTimeout(6000);
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
            con.setRequestProperty("SOAPAction", soapAction);
            con.setRequestProperty("Content-Length", "" + bytes.length);
            OutputStream outStream = con.getOutputStream();
            outStream.write(bytes);
            outStream.flush();
            outStream.close();
            InputStream inStream = con.getInputStream();
            if(inStream!=null){
                Values = StreamtoValue(inStream, MethodName);
                return Values;
            }
            return null;

        }
        catch (StringIndexOutOfBoundsException e){
            Values.clear();
            return Values;
        }
        catch (Exception e)
        {
            return null;
        }finally {
            if(con!=null){
                con.disconnect();
            }

        }
    }





    private ArrayList<String> StreamtoValue(InputStream in, String MethodName) throws IOException
    {
        StringBuffer out = new StringBuffer();
        String s1 = "";
        byte[] b = new byte[4096];  
        ArrayList<String> Values = new ArrayList<String>();
        Values.clear();
        for (int n; (n = in.read(b)) != -1;)
        {
            s1 = new String(b, 0, n);
            out.append(s1);
        }
        Log.e("--响应体--",out.toString());
        //分割
        String[] s2 = out.toString().split("><");
        String s5 = MethodName + "Result";
        String s3 = "",s4 = "";
        Boolean getValueBoolean = false;
        for (int i = 0; i < s2.length; i++) 
        {  
            s3 = s2[i];

            int FirstIndexPos, LastIndexPos, LengthofS5;  
            FirstIndexPos = s3.indexOf(s5);  
            LastIndexPos = s3.lastIndexOf(s5);  
            /*
            	删除:deleteCargoInfoResult>boolean</deleteCargoInfoResult
            	插入:insertCargoInfoResult>boolean</insertCargoInfoResult
            	查询:
            	selectAllCargoInforResult
            	string>string</string
            	string>string</string
            	/selectAllCargoInforResult
            */


            if (FirstIndexPos >= 0) 
            {  
                if (getValueBoolean == false) 
                {  
                    getValueBoolean = true;  
                }
                //如果返回的是布尔值,对应删除和增加操作
                if ((FirstIndexPos >= 0) && (LastIndexPos > FirstIndexPos))
                {    
                    LengthofS5 = s5.length() + 1;  
                    s4 = s3.substring(FirstIndexPos + LengthofS5, LastIndexPos - 2);    
                    Values.add(s4);    
                    getValueBoolean = false;  
                    return Values;  
                }  
  
            }


            //查询操作取值结束
            if (s3.lastIndexOf("/" + s5) >= 0) 
            {  
                getValueBoolean = false;  
                return Values;  
            }
            //如果返回的不是布尔值,对应查询操作
            if ((getValueBoolean) && (s3.lastIndexOf("/" + s5) < 0) && (FirstIndexPos < 0))
            {
                LastIndexPos = s3.length();  
                s4 = s3.substring(7, LastIndexPos - 8);    
                Values.add(s4);  
            }  
        }
        in.close();
        return Values;  
    }  
}  