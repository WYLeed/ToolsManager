package com.socket.usbhelper.mv;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.socket.rxjavautils.BaseModel;

/**
 * Created by wangyu on 2018/12/12 0012.
 */

public class ReceivePcMessageModel extends BaseModel {

    private MutableLiveData<String> socketMessage;

    public ReceivePcMessageModel(@NonNull Application application) {
        super(application);
    }

    private void getSocketMessage() {

    }
}
