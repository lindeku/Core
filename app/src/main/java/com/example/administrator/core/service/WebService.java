package com.example.administrator.core.service;

import android.util.Log;

import com.example.administrator.core.net.web.HttpConnSoap;
import com.example.administrator.core.net.web.IBaseCache;
import com.example.administrator.core.net.web.Observer;

import java.util.ArrayList;
import java.util.List;

public class WebService {
    private  String BASE_URL;
    private  String SPACE_NAME;
    private final HttpConnSoap HTTP_CONN_SOAP;
    private final Asynchronous mAsynchronousThread;
    private int collectiveOvertime;
    private int collectiveFail;
    private int collectiveSuccess;
    private WebService(){
        BASE_URL=null;
        SPACE_NAME=null;
        HTTP_CONN_SOAP=new HttpConnSoap();
        mAsynchronousThread=null;
    }

    private WebService(String baseUrl, String spaceName) {
        this.BASE_URL = baseUrl;
        this.SPACE_NAME = spaceName;

        HTTP_CONN_SOAP=new HttpConnSoap();
        mAsynchronousThread=new Asynchronous();
    }


    public void setBaseUrl(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    public void setSpacename(String SPACE_NAME) {
        this.SPACE_NAME = SPACE_NAME;
    }

    public void asynchronousRequest(Observer observer, RequestBody requestBody){
        assert mAsynchronousThread != null;
        mAsynchronousThread.setRequestContent(observer,requestBody);
        mAsynchronousThread.start();
    }
    public void synchronizationRequest(Observer observer,RequestBody requestBody){
        request(observer,requestBody);
    }

    private ArrayList<String> sendOut(NetBody netBody){
        if(netBody.getParameter()!=null){
            return  HTTP_CONN_SOAP.GetWebServer(SPACE_NAME,netBody.getModuleName(),BASE_URL,netBody.getParameter());
        }else {
            return HTTP_CONN_SOAP.getData(netBody.getModuleName(),SPACE_NAME,BASE_URL);
        }
    }

    private void request(Observer observer,RequestBody requestBody){
        int number=0;
        collectiveOvertime=0;
        collectiveFail=0;
        collectiveSuccess=0;
        if(requestBody.getNetBody()!=null){
            if(requestBody.getNetBody().getParameter()!=null){
                handle(observer,requestBody.getNetBody());
                number++;
            }
        }
        if(requestBody.getNetBodys()!=null&&requestBody.getNetBodys().size()!=0){
            for (NetBody netBody:requestBody.getNetBodys()) {
                handle(observer,netBody);
                number++;
            }
        }

        if(number==collectiveSuccess){
            observer.update(requestBody.getSuccess());
        }else if(number==collectiveFail){
            observer.update(requestBody.getFail());
        }else if(number==collectiveOvertime){
            observer.update(404);
        }
    }
    private void handle(Observer observer,NetBody netBody){
        ArrayList<String> body=sendOut(netBody);
        if(body!=null) {
            if (body.size()==0) {
                if (netBody.getFail() != 0) {
                    collectiveFail++;
                    observer.update(netBody.getFail());
                }
            }else {
                for (int k = 0; k < body.size(); k += netBody.getAnalyzeCount()) {
                    List<String> datas = null;
                    try {
                        datas = body.subList(k, k + netBody.getAnalyzeCount());
                    } catch (Exception e) {
                        Log.e("查看setAnalyzeCount()是否正确", "解析异常 返回集长度错误");
                    }
                    IBaseCache iBaseCache = netBody.getIBaseCache();
                    if (iBaseCache != null) {
                        assert datas != null;
                        String[] data = new String[datas.size()];
                        data = datas.toArray(data);
                        iBaseCache.cache(data);
                    }
                }
                if (netBody.getSuccess() != 0) {
                    collectiveSuccess++;
                    observer.update(netBody.getSuccess());
                }
            }
        }else {
            collectiveOvertime++;
        }
    }

    public class WebServiceBuilder{
        private String mBaseUrl=null;
        private String mSpaceName=null;
        public WebServiceBuilder writeBaseUrl(String baseUrl){
            this.mBaseUrl=baseUrl;
            return this;
        }
        public WebServiceBuilder writeSpaceName(String spaceName){
            this.mSpaceName=spaceName;
            return this;
        }
       public WebService build(){
            return new WebService(mBaseUrl,mSpaceName);
       }
    }

    private class Asynchronous extends Thread{
       private Observer mObserver;
       private RequestBody mRequestBody;

       void setRequestContent(Observer observer, RequestBody requestBody) {
            this.mObserver = observer;
            this.mRequestBody = requestBody;
       }
        @Override
        public void run() {
            request(mObserver,mRequestBody);
        }
    }
}
