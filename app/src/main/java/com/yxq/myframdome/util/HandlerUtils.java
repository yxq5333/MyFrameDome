package com.yxq.myframdome.util;

import android.os.Handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HandlerUtils {
    private static HandlerUtils instance;
    private Map<String, Handler> defaultThreadHandlerTag;
    private Map<String, List<Runnable>> runnableTag;

    private HandlerUtils() {
    }

    public static HandlerUtils create() {
        if (instance == null) {
            instance = new HandlerUtils();
            instance.defaultThreadHandlerTag = new HashMap();
            instance.runnableTag = new HashMap();
        }

        return instance;
    }

    private Handler getHandler(String key) {
        Handler handler = (Handler)this.defaultThreadHandlerTag.get(key);
        if (handler == null) {
            handler = new Handler();
            this.defaultThreadHandlerTag.put(key, handler);
        }

        return handler;
    }

    public void handlerBindRunnableAndPostDelayedOnDefaultThread(String key, Runnable runnable, long delayMillis) {
        List<Runnable> runnableList = (List)this.runnableTag.get(key);
        if (runnableList == null) {
            runnableList = new ArrayList();
            this.runnableTag.put(key, runnableList);
        }

        ((List)runnableList).add(runnable);
        this.getHandler(key).postDelayed(runnable, delayMillis);
    }

    public void unbindRunnable4Handler(String key) {
        Handler handler = (Handler)this.defaultThreadHandlerTag.get(key);
        if (handler != null) {
            List<Runnable> runnableList = (List)this.runnableTag.get(key);
            if (!Lists.notEmpty(runnableList)) {
                Iterator var4 = runnableList.iterator();

                while(var4.hasNext()) {
                    Runnable t = (Runnable)var4.next();
                    handler.removeCallbacks(t);
                }

                this.runnableTag.remove(key);
            }

            this.defaultThreadHandlerTag.remove(key);
        }

    }
}
