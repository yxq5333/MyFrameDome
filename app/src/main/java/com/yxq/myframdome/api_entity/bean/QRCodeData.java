package com.yxq.myframdome.api_entity.bean;

/**
 * Created by Hollow Goods on 2019-05-16.
 */
public class QRCodeData {

    private int companyId;
    private int serviceId;
    private int riskId;
    private int contractId;
    private int riskCheckId;

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getRiskId() {
        return riskId;
    }

    public void setRiskId(int riskId) {
        this.riskId = riskId;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public int getRiskCheckId() {
        return riskCheckId;
    }

    public void setRiskCheckId(int riskCheckId) {
        this.riskCheckId = riskCheckId;
    }
}
