package com.yxq.myframdome.api_entity;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-14  15:37
 */
public class ExamineInfoVO {

    /**
     * 名称
     */
    private String name;
    /**
     * 省
     */
    private Integer provinceId;

    /**
     * 市
     */
    private Integer cityId;

    /**
     * 区/县
     */
    private Integer areaId;

    /**
     * 街道
     */
    private Integer streetId;

    /**
     * 详细地址
     */
    private String address;
    /**
     * 经营范围
     */
    private String businessScope;

    /**
     * 营业执照
     */
    private String businessLicence;
    private FileVO businessLicenceFile;

    /**
     * 实际经营地址
     */
    private String addressProduct;
    /**
     * 统一社会信用代码
     */
    private String creditCode;
    /**
     * 联系人邮箱
     */
    private String contactsEmail;
    /**
     * 联系人传真
     */
    private String contactsFax;
    /**
     * 联系人座机
     */
    private String contactsMobile;
    /**
     * 联系人名字
     */
    private String contactsName;
    /**
     * 联系人手机
     */
    private String contactsPhone;
    /**
     * 联系人QQ
     */
    private String contactsQq;
    /**
     * 法人邮箱
     */
    private String corporationEmail;
    /**
     * 法人传真
     */
    private String corporationFax;
    /**
     * 法人座机
     */
    private String corporationMobile;
    /**
     * 法人名字
     */
    private String corporationName;
    /**
     * 法人手机
     */
    private String corporationPhone;
    /**
     * 法人QQ
     */
    private String corporationQq;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getBusinessLicence() {
        return businessLicence;
    }

    public void setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence;
    }

    public String getContactsEmail() {
        return contactsEmail;
    }

    public void setContactsEmail(String contactsEmail) {
        this.contactsEmail = contactsEmail;
    }

    public String getContactsFax() {
        return contactsFax;
    }

    public void setContactsFax(String contactsFax) {
        this.contactsFax = contactsFax;
    }

    public String getContactsMobile() {
        return contactsMobile;
    }

    public void setContactsMobile(String contactsMobile) {
        this.contactsMobile = contactsMobile;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsPhone() {
        return contactsPhone;
    }

    public void setContactsPhone(String contactsPhone) {
        this.contactsPhone = contactsPhone;
    }

    public String getContactsQq() {
        return contactsQq;
    }

    public void setContactsQq(String contactsQq) {
        this.contactsQq = contactsQq;
    }

    public String getCorporationEmail() {
        return corporationEmail;
    }

    public void setCorporationEmail(String corporationEmail) {
        this.corporationEmail = corporationEmail;
    }

    public String getCorporationFax() {
        return corporationFax;
    }

    public void setCorporationFax(String corporationFax) {
        this.corporationFax = corporationFax;
    }

    public String getCorporationMobile() {
        return corporationMobile;
    }

    public void setCorporationMobile(String corporationMobile) {
        this.corporationMobile = corporationMobile;
    }

    public String getCorporationName() {
        return corporationName;
    }

    public void setCorporationName(String corporationName) {
        this.corporationName = corporationName;
    }

    public String getCorporationPhone() {
        return corporationPhone;
    }

    public void setCorporationPhone(String corporationPhone) {
        this.corporationPhone = corporationPhone;
    }

    public String getCorporationQq() {
        return corporationQq;
    }

    public void setCorporationQq(String corporationQq) {
        this.corporationQq = corporationQq;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getStreetId() {
        return streetId;
    }

    public void setStreetId(Integer streetId) {
        this.streetId = streetId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressProduct() {
        return addressProduct;
    }

    public void setAddressProduct(String addressProduct) {
        this.addressProduct = addressProduct;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public FileVO getBusinessLicenceFile() {
        return businessLicenceFile;
    }

    public void setBusinessLicenceFile(FileVO businessLicenceFile) {
        this.businessLicenceFile = businessLicenceFile;
    }
}
