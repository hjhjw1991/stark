package com.hjhjw1991.stark.scaffold.debug;

import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * todo delete me when release
 * @author barney
 * @since 2020/4/21
 */
public class HJ {
    public static final String TAG = "Stark_Plugin";
    private static AtomicInteger count = new AtomicInteger(0);

    public static void say(String ...s) {
        if(s != null) {
            StringBuilder sb = new StringBuilder();
            for (String s1: s) {
                sb.append(s1);
            }
            Log.d(TAG, sb.toString());
        }
    }

    public static void printCallerTrace(){
        Log.d(TAG, "count " + count.incrementAndGet());
        StringBuilder s = new StringBuilder(String.format("[[%s]]Caller:\n", TAG));
        for (int i=3;i<13&&i<Thread.currentThread().getStackTrace().length;i++){
            StackTraceElement element = Thread.currentThread().getStackTrace()[i];
            s.append(String.format("[[%s]]method: %s.%s(%s:%d)\n", TAG,
                    element.getClassName(),
                    element.getMethodName(),
                    element.getFileName(),
                    element.getLineNumber()));
        }
        Log.d(TAG, s.toString());
    }
}
