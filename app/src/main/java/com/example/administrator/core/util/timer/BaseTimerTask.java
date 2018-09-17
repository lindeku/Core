package com.example.administrator.core.util.timer;

import java.util.TimerTask;

/**
 * Created by 林颖 on 2018/5/18.
 *
 */

public class BaseTimerTask extends TimerTask {
    private  ITimerListener mITimerListener=null;
    private BaseTimerTask(){}
    public BaseTimerTask(ITimerListener timerListener){
        mITimerListener=timerListener;
    }
    @Override
    public void run() {
        if(mITimerListener!=null){
            mITimerListener.onTimer();
        }
    }
}
