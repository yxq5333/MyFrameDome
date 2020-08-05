package com.yxq.myframdome.module.user.adapter;


import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.crazyhuskar.myandroidsdk.adapter.recyclerview.CommonAdapter;
import com.crazyhuskar.myandroidsdk.adapter.recyclerview.base.ViewHolder;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.yxq.myframdome.AppCache;
import com.yxq.myframdome.AppConfig;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.MenuVO;

import java.util.List;

public class MainFragment_1_Adapter_2_1 extends CommonAdapter<MenuVO> {

    public MainFragment_1_Adapter_2_1(Context context, int layoutId, List<MenuVO> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(final ViewHolder viewHolder, final MenuVO item, int position) {
        viewHolder.setText(R.id.name, item.getName());
        switch (item.getMenuId()) {
            /**
             * 合同信息
             */
            case AppConfig.MENUID_14:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo_mas);
                break;
            /**
             * 合同审核
             */
            case AppConfig.MENUID_15:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo_mas);
                break;
            /**
             * 业务员綜合查询
             */
            case AppConfig.MENUID_16:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo_mas);
                break;
            /**
             * 安排托管人员
             */
            case AppConfig.MENUID_17:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo_mas);
                break;
            /**
             * 托管人员受理
             */
            case AppConfig.MENUID_18:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo_mas);
                break;
            /**
             * 托管员綜合查询
             */
            case AppConfig.MENUID_19:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo_mas);
                break;
            /**
             * 托管服务
             */
            case AppConfig.MENUID_112:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo_mas);
                break;
            /**
             * 服务审核
             */
            case AppConfig.MENUID_46:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo_mas);
                break;
            /**
             * 服务进度
             */
            case AppConfig.MENUID_47:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo_mas);
                break;
            /**
             * 审核结果综合查询
             */
            case AppConfig.MENUID_49:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo_mas);
                break;
            /**
             * 企业安全分析报表
             */
            case AppConfig.MENUID_85:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo_mas);
                break;
            /**
             * 机构服务统计报表
             */
            case AppConfig.MENUID_86:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo_mas);
                break;
            /**
             * 机构合同统计报表
             */
            case AppConfig.MENUID_87:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo_mas);
                break;
            default:
                viewHolder.setImageResource(R.id.icon, R.mipmap.logo);
                break;
        }


        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (item.getMenuId()) {
                    /**
                     * 合同信息
                     */
                    case AppConfig.MENUID_14:
                        Bundle bundle0 = new Bundle();
                        bundle0.putInt("clickName", 0);
//                        MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, HeTongListActivity.class, bundle0);

                        break;
                    /**
                     * 合同审核
                     */
                    case AppConfig.MENUID_15:
                        Bundle bundle1 = new Bundle();
                        bundle1.putInt("clickName", 1);
//                        MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, HeTongListActivity.class, bundle1);

                        break;
                    /**
                     * 业务员綜合查询
                     */
                    case AppConfig.MENUID_16:
                        Bundle bundle4 = new Bundle();
                        bundle4.putInt("clickName", 4);
//                        MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, HeTongListActivity.class, bundle4);

                        break;
                    /**
                     * 安排托管人员
                     */
                    case AppConfig.MENUID_17:
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt("clickName", 2);
//                        MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, HeTongListActivity.class, bundle2);
                        break;
                    /**
                     * 托管人员受理
                     */
                    case AppConfig.MENUID_18:
                        Bundle bundle3 = new Bundle();
                        bundle3.putInt("clickName", 3);
//                        MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, HeTongListActivity.class, bundle3);
                        break;
                    /**
                     * 托管员綜合查询
                     */
                    case AppConfig.MENUID_19:
                        Bundle bundle5 = new Bundle();
                        bundle5.putInt("clickName", 5);
//                        MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, HeTongListActivity.class, bundle5);
                        break;
                    /**
                     * 托管服务
                     */
                    case AppConfig.MENUID_112:
//                        MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, TuoGuanServiceListActivity.class);
                        break;
                    /**
                     * 服务审核
                     */
                    case AppConfig.MENUID_46:
//                        MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, FuWuShenHeListActivity.class);
                        break;
                    /**
                     * 服务进度
                     */
                    case AppConfig.MENUID_47:
//                        MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, FuWuJinDuListActivity.class);
                        break;
                    /**
                     * 审核结果综合查询
                     */
                    case AppConfig.MENUID_49:
                        break;
                    /**
                     * 企业安全分析报表
                     */
                    case AppConfig.MENUID_85:
//                        MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, QiYeBaoBiaoActivity.class);
                        break;
                    /**
                     * 机构服务统计报表
                     */
                    case AppConfig.MENUID_86:
//                        MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, JiGouFuWuBaoBiaoActivity.class);
                        break;
                    /**
                     * 机构合同统计报表
                     */
                    case AppConfig.MENUID_87:
//                        MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, JiGouHeTongBaoBiaoActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
