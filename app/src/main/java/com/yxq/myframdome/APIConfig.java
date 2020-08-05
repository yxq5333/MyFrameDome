package com.yxq.myframdome;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-05  10:21
 */
public class APIConfig {
    /**
     * 服务器
     */
    public static String BASEURL = "http://192.168.1.253:8765/api/";

    /**
     * 登录
     */
    public static String LOGIN = "auth/jwt/app/token";
    /**
     * 注册
     */
    public static String REGISTER = "core/register";
    /**
     * 获取菜单树
     */
    public static String MENU = "core/sys-menu/list";
    /**
     * 获取用户信息
     */
    public static String GETUSERINFO = "core/getUserInfo";
    /**
     * 修改密码
     */
    public static String CHANGEPASSWORD = "core/sys-user/password";
    /**
     * 上传文件
     */
    public static String FILEUPLOAD = "core/upload";

    /**
     * 上传文件信息
     */
    public static String FILEDOWN = "core/down/";
    public static String FILEDOWN2 = "core/down2/";
    public static String FILESUPLOAD = "core/uploadList";

    // 版本更新
    public static String UPDATE_APP_VERSION = "core/appVersion/info";
    /**
     * 获取轮播图
     */
    public static String BANNERLIST = "core/web-adv/list";

    // 获取系统信息
    public static String GET_SYSTEM_INFO = "core/sys/system/getInfo";

    /**
     * 完善企业信息
     */
    public static String COMPANYINFO = "core/complete/companyInfo";
    /**
     * 获取企业信息
     */
    public static String GET_COMPANY_INFO = "core/get/enterprise/info";
    /**
     * 完善机构信息
     */
    public static String INSTITUTEINFO = "core/complete/instituteInfo";
    /**
     * 获取机构信息
     */
    public static String GET_INSTITUTE_INFO = "core/get/institute/info";



    /****************消息中心 star********************/
    /**
     * 消息列表
     */
    public static String LIST = "core/sys-message/list";
    /**
     * 更新消息状态
     */
    public static String UPDATESTATUS = "core/sys-message/updateStatus";
    /****************消息中心 end********************/




    /****************字典表数据 star********************/
    /**
     * 根据上级获取下级区域 （区域）
     */
    public static String LISTBYPARENTID = "core/cfg-area/listByParentId?parentId=";
    /**
     * 根据获取行业-大类
     */
    public static String LIST_INDUSTRY = "core/cfg-industry/list";
    /**
     * 根据获取行业-小类
     */
    public static String SPECIALTYBYINDUSTRY = "core/cfg-industry/specialtyByIndustry?industryId=";
    /**
     * 根据获取审批流程
     */
    public static String CONTRACTINFO = "core/work-flow/get/contract/info?contractId=";

    /****************字典表数据 end********************/

    //    分页查询风险标识列表
    public static String RISK_SEARCH_BUILDING_LIST = "core//risk-identify/get/all/buildings";

    // 获取合同类型
    public static String GET_CONTRACT_TYPE = "core/web-contract-type/comboList";

    // 验证是否有检查风险点的权限
    public static String CHECK_ROLE = "core/service/entrust/canService";

}
