package com.yxq.myframdome.util.IP;

import com.google.gson.reflect.TypeToken;
import com.yxq.myframdome.util.CacheUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HG on 2017-07-11.
 */

public class IPDBHelper {

    public List<IPConfig> findAll() {
        return CacheUtils.create().load(IPConfig.class.getSimpleName(), new TypeToken<List<IPConfig>>() {
        }.getType());
    }

    public void save(ArrayList<IPConfig> data) {
        CacheUtils.create().save(IPConfig.class.getSimpleName(), data);
    }

}
