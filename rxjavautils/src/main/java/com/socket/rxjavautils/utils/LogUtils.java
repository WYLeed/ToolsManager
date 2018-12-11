/*
 * Copyright (C) 2016 venshine.cn@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.socket.rxjavautils.utils;

import android.util.Config;
import android.util.Log;


/**
 * Log日志打印操作
 *
 * @author venshine
 */
public class LogUtils {

    private static final String TAG = "venshine";

    /**
     * debug log
     *
     * @param msg
     */
    public static void d(String msg) {
            Log.d(TAG, msg);
        }

    /**
     * error log
     *
     * @param msg
     */
    public static void e(String msg) {
            Log.e(TAG, msg);
    }

}
