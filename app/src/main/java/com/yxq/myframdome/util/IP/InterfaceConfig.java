package com.yxq.myframdome.util.IP;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.crazyhuskar.myandroidsdk.api.MyRetrofitUtil;
import com.crazyhuskar.myandroidsdk.util.MyUtilToast;
import com.yxq.myframdome.APIConfig;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.UserVO;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 接口配置
 * Created by HG on 2016-11-21.
 */

public class InterfaceConfig {

    /**
     * IP配置集合
     */
    private static ArrayList<IPConfig> IP = new ArrayList<>();

    /**
     * 保存IP配置
     */
    private static void saveIP() {
        new IPDBHelper().save(IP);
    }

    /**
     * 添加IP配置信息
     *
     * @param ipConfig
     */
    private static void addIP(IPConfig ipConfig) {

        int position = isExist(ipConfig);

        if (position == -1) {
            IP.add(ipConfig);
        }
    }

    private static int isExist(IPConfig ipConfig) {

        for (int i = 0; i < IP.size(); i++) {
            if (IPConfig.equals(ipConfig, IP.get(i))) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 初始化IP配置
     */
    public static void initIP(IPConfig... defaultIPs) {

        List<IPConfig> result = new IPDBHelper().findAll();

        if (defaultIPs != null) {
            for (IPConfig t : defaultIPs) {
                addIP(t);
            }
        }

        if (result != null) {
            for (IPConfig t : result) {
                addIP(t);
            }
        }
    }
   private <T> void _initIP(T t){

    }

    public static IPConfig getNowIPConfig() {

        IPConfig ipConfig = null;

        if (IP.size() == 0) {
            ipConfig = new IPConfig();
        }

        for (IPConfig t : IP) {
            if (t.isChecked()) {
                ipConfig = t;
            }
        }

        if (ipConfig == null) {
            IP.get(0).setChecked(true);
            saveIP();

            ipConfig = IP.get(0);
        }

        return IPConfig.copy(ipConfig);
    }

    private AlertDialog ipDialog;
    private EditText protocol;
    private EditText ip;
    private EditText port;
    private EditText projectName;
    private EditText realmName;
    private TextView pre;
    private View ipHistory;
    private IPConfig ipConfig;

    public void showIPDialog(AppCompatActivity activity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("IP地址配置").setView(R.layout.dialog_ip_address).setNegativeButton("取消", (dialog, which) -> {

        }).setPositiveButton("确定", (dialog, which) -> {
            addIP(ipConfig);
            int position = isExist(ipConfig);
            for (IPConfig t : IP) {
                t.setChecked(false);
            }
            IP.get(position).setChecked(true);
            saveIP();
            MyUtilToast.showLong(activity, "当前地址："
                    + ipConfig.getRequestUrl());
            APIConfig.BASEURL = InterfaceConfig.getNowIPConfig().getRequestUrl() + "/";
            MyRetrofitUtil.getInstance().init(APIConfig.BASEURL);
        });

        ipDialog = builder.create();
        ipDialog.show();

        protocol = ipDialog.findViewById(R.id.et_protocol);
        ip = ipDialog.findViewById(R.id.et_ip);
        port = ipDialog.findViewById(R.id.et_port);
        projectName = ipDialog.findViewById(R.id.et_projectName);
        realmName = ipDialog.findViewById(R.id.et_realmName);
        pre = ipDialog.findViewById(R.id.tv_pre);
        ipHistory = ipDialog.findViewById(R.id.tv_ip_history);

        refreshIPDialog();

        protocol.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                ipConfig.setProtocol(s.toString());
                pre.setText(ipConfig.getRequestUrl());
            }
        });

        ip.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                ipConfig.setIp(s.toString());
                pre.setText(ipConfig.getRequestUrl());
            }
        });

        port.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                ipConfig.setPort(s.toString());
                pre.setText(ipConfig.getRequestUrl());
            }
        });

        projectName.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                ipConfig.setProjectName(s.toString());
                pre.setText(ipConfig.getRequestUrl());
            }
        });

        realmName.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                ipConfig.setRealmName(s.toString());
                pre.setText(ipConfig.getRequestUrl());
            }
        });
    }

    private void refreshIPDialog() {

        ipConfig = getNowIPConfig();

        protocol.setText(ipConfig.getProtocol());
        ip.setText(ipConfig.getIp());
        port.setText(ipConfig.getPort());
        projectName.setText(ipConfig.getProjectName());
        realmName.setText(ipConfig.getRealmName());
        pre.setText(ipConfig.getRequestUrl());
    }

    private class TextWatcherAdapter implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

}
