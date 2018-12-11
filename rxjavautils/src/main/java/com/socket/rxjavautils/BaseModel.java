package com.socket.rxjavautils;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

/**
 * Created by wangyu on 2018/12/12 0012.
 */

public class BaseModel extends AndroidViewModel {
    public BaseModel(@NonNull Application application) {
        super(application);
    }
}
