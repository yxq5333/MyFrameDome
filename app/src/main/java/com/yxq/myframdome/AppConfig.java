package com.yxq.myframdome;

import android.os.Environment;

import java.io.File;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-05  10:21
 */
public interface AppConfig {

    AppCompany APP_COMPANY = AppCompany.XHTT;

    int EVENTBUS_MENU = 100;
    int EVENTBUS_HIDDENSTORE = 101;
    int EVENTBUS_HIDDENDENGER = 102;
    int EVENTBUS_HIDDENCHECKTABLE = 103;
    int EVENTBUS_HIDDENCHECKTABLEADD1 = 104;
    int EVENTBUS_HIDDENCHECKTABLEADD2 = 105;
    int EVENTBUS_LECSBEAN = 106;
    /**
     * 检测内容是否可编辑
     */
    String EVENTBUS_HIDDENDENGER_IAEDIT = "EVENTBUS_HIDDENDENGER_IAEDIT";
    String APP_NAME = "AnDaiBao";

    /************************************** 存储根路径 **************************************/
    String STORAGE_HOME_PATH = Environment.getExternalStorageDirectory() + File.separator;


    /************************************** APP缓存路径 **************************************/
    String APP_CACHE_PATH = STORAGE_HOME_PATH + APP_NAME;
    /**
     * 登录状态
     */
    String KEY_LOGIN_STATUS = "KEY_LOGIN_STATUS";
    /**
     * 登录用户信息
     */
    String KEY_USER_INFO = "KEY_USER_INFO";
    /**
     * 登录用户名称
     */
    String KEY_LOGIN_NAME = "KEY_LOGIN_NAME";

    /**
     * 服务计划标题
     */
    String SERVICE_TITLE = "SERVICE_TITLE";

    /**
     * 服务类型
     */
    String SERVICE_TYPE = "SERVICE_TYPE";

    /**
     * 服务id
     */
    String SERVICE_ID = "SERVICE_ID";


    /**
     * 服务频率id
     */
    String SERVICE_TIMEID = "SERVICE_TIMEID";
    /**
     * 服务项是否可编辑
     */
    String SERVICE_ISEDIT = "SERVICE_ISEDIT";

    /**
     * 对象
     */
    String SERIALIZABLE = "SERIALIZABLE";
    /**
     * 状态
     */
    String STATUS = "STATUS";
    String ID = "id";


    String LECBEAN = "LECBEAN";

    String DATA = "DATA";

    /**
     * 企业报表--第一次月份
     */
    String KEY_STARMONTH = "KEY_STARMONTH";

    /**************************************
     * APP 本地图片ImageLoader缓存路径
     **************************************/
    String IMAGE_LOADER_CACHE_PATH = APP_NAME + File.separator + "imgCache";

    String MY_IMAGE_LOADER_PATH = STORAGE_HOME_PATH + APP_NAME + File.separator + "mImg";

    String KEY_TOKEN = "token";

    String KEY_USER_ID = "user_id";

    String KEY_ORIGIN_USER_ID = "origin_user_id";

    Integer FILE_QYPMT = 110101;//企业平面图
    Integer FILE_QYYYZZ = 110102;//企业营业执照
    Integer FILE_QYPMT_YQYD = 110111;//企业平面图(一企一档)
    Integer FILE_QYYYZZ_YQYD = 110112;//企业营业执照(一企一档)
    Integer FILE_JGYYZZ = 210101;//机构营业执照
    Integer FILE_HTFJ = 310101;//合同附件
    Integer FILE_HTYYZZ = 310102;//合同-营业执照
    Integer FILE_AQSSPMBZT = 510201;//安全疏散平面布置图
    Integer FILE_GYLCJT = 510202;//工艺流程简图
    Integer FILE_PXZSY = 510203;//培训证书1
    Integer FILE_PXZSR = 510204;//培训证书2
    Integer FILE_YHZP = 510301;//隐患照片
    Integer FILE_AQTGBG = 510302;//安全托管报告
    Integer FILE_AQZRQDY = 510303;//安全责任签订1
    Integer FILE_AQZRQDE = 510304;//安全责任签订2
    Integer FILE_ZDGCBZY = 510305;//制度规程编制1
    Integer FILE_ZDGCBZE = 510306;//制度规程编制2
    Integer FILE_AQTZJL = 510307;//安全台账建立
    Integer FILE_MSHGLY = 510308;//目视化管理1
    Integer FILE_MSHGLR = 510309;//目视化管理2
    int FILE_AQJYPXY = 510310;//安全教育培训1-培训记录
    Integer FILE_YJJYYL = 510311;//应急救援演练
    Integer FILE_ZYWSGLE = 510312;//职业卫生管理2-职业病因素辨识
    Integer FILE_DWFUZJ = 510313;//代维服务总结
    Integer FILE_FXBSGK = 510314;//风险辨识管控
    Integer FILE_YHPCLBYHZP = 510315;//隐患排查列表-隐患照片
    Integer FILE_YHPCLBZGCKSL = 510316;//隐患排查列表-整改参照示例
    int FILE_AQJYPXE = 510317;//安全教育培训2-培训文件
    Integer FILE_ZYWSGLY = 510318;//职业卫生管理1-检测报告


    /**
     * 请求码
     */
    interface RequestCode {

        /**
         * 拍照
         */
        int REQUESTCODE_CAPTURE_PHOTO = 0x001;

        /**
         * 裁剪
         */
        int REQUESTCODE_CROP_IMG = 0x002;

        /**
         * 授权  拍照 sd卡读写
         */
        int REQUESTCODE_PERMISSION_CODE_TAKE_PHOTO = 0x003;

        /**
         * 授权  友盟分享
         */
        int REQUESTCODE_PERMISSION_UMENG_SHARE = 0x004;


        /**
         * 选取文件
         */
        int REQUESTCODE_SELECT_FILE_LOCAL = 0x010;


    }

    String[] hetongTypes = {
            "托管服务",
            "标准化服务"
    };
    String[] Grades = {
            "一级",
            "二级",
            "三级",
            "小微",
            "无",
    };
    String[] Files = {
            "照片",
            "本地"
    };
    /**
     * 合同信息
     */
    int MENUID_14 = 14;
    /**
     * 合同审核
     */
    int MENUID_15 = 15;
    /**
     * 业务员綜合查询
     */
    int MENUID_16 = 16;
    /**
     * 安排托管人员
     */
    int MENUID_17 = 17;
    /**
     * 托管人员受理
     */
    int MENUID_18 = 18;
    /**
     * 托管员綜合查询
     */
    int MENUID_19 = 19;
    /**
     * 托管服务
     */
    int MENUID_112 = 112;
    /**
     * 服务审核
     */
    int MENUID_46 = 46;
    /**
     * 服务进度
     */
    int MENUID_47 = 47;
    /**
     * 审核结果综合查询
     */
    int MENUID_49 = 49;
    /**
     * 企业安全分析报表
     */
    int MENUID_85 = 85;
    /**
     * 机构服务统计报表
     */
    int MENUID_86 = 86;
    /**
     * 机构合同统计报表
     */
    int MENUID_87 = 87;
    /**
     * 政府企业风险评估
     */
    int MENUID_88 = 88;
    /**
     * 政府企业隐患统计
     */
    int MENUID_89 = 89;
    /**
     * 政府整体风险评估报表
     */
    int MENUID_90 = 90;
    /**
     * 政府风险辨识分析报表
     */
    int MENUID_91 = 91;
    /**
     * 服务单管理
     */
    int MENUID_123 = 123;
    /**
     * 安全台账审核
     */
    int MENUID_126 = 126;
    /**
     * 合同作废
     */
    int MENUID_127 = 127;
    /**
     * 人员变更
     */
    int MENUID_129 = 129;
}
