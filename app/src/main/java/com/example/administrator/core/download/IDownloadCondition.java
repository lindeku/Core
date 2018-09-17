package com.example.administrator.core.download;

/**
 * Created by Administrator on 2018/8/14 0014.
 */

public interface IDownloadCondition {
   void onNext(Integer value);
   void onError(Throwable e);
   void onComplete();
}
