package com.yxq.myframdome.util.IP;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by HG on 2017-07-11.
 */
public class IPConfig implements Serializable {

    private String protocol = "http://";
    private String ip;
    private String port;
    private String projectName = "";
    private String realmName = "";
    private boolean isChecked = false;

    public IPConfig setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public IPConfig setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public IPConfig setPort(String port) {
        this.port = port;
        return this;
    }

    public IPConfig setProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public IPConfig setRealmName(String realmName) {
        this.realmName = realmName;
        return this;
    }

    public IPConfig setChecked(boolean checked) {
        isChecked = checked;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getRealmName() {
        return realmName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public String getRequestHead() {

        StringBuilder url = new StringBuilder();
        if (!TextUtils.isEmpty(protocol)) {
            url.append(protocol);
        }
        if (!TextUtils.isEmpty(ip)) {
            url.append(ip);
        } else {
            url.append("adb");
        }
        if (!TextUtils.isEmpty(port)) {
            url.append(":");
            url.append(port);
        }

        return url.toString();
    }

    public String getRequestUrl(String api) {

        StringBuilder url = new StringBuilder(getRequestHead());
        if (!TextUtils.isEmpty(projectName)) {
            url.append("/");
            url.append(projectName);
        }
        if (!TextUtils.isEmpty(realmName)) {
            url.append("/");
            url.append(realmName);
        }
        if (!TextUtils.isEmpty(api)) {
            if (!api.startsWith("/")) {
                url.append("/");
            }
            url.append(api);
        }

        return url.toString();
    }

    public String getRequestUrl() {
        return getRequestUrl(null);
    }

    public static boolean equals(IPConfig a, IPConfig b) {
        return TextUtils.equals(a.protocol, b.protocol)
                && TextUtils.equals(a.ip, b.ip)
                && TextUtils.equals(a.port, b.port)
                && TextUtils.equals(a.projectName, b.projectName)
                && TextUtils.equals(a.realmName, b.realmName);
    }

    public static IPConfig copy(IPConfig ipConfig) {

        IPConfig result = new IPConfig();

        result.protocol = ipConfig.protocol;
        result.ip = ipConfig.ip;
        result.port = ipConfig.port;
        result.projectName = ipConfig.projectName;
        result.realmName = ipConfig.realmName;
        result.isChecked = ipConfig.isChecked;

        return result;
    }
}
