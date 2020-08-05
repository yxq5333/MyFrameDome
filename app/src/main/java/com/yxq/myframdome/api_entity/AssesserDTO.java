package com.yxq.myframdome.api_entity;

import java.util.ArrayList;
import java.util.List;

public class AssesserDTO {

    /**
     * workFlowType : 1
     * auditorType : 3
     * auditorIds : [3]
     * roleName : 机构1
     */

    private int workFlowType;
    private int auditorType;
    private String roleName;
    private List<Integer> auditorIds=new ArrayList<>();

    public int getWorkFlowType() {
        return workFlowType;
    }

    public void setWorkFlowType(int workFlowType) {
        this.workFlowType = workFlowType;
    }

    public int getAuditorType() {
        return auditorType;
    }

    public void setAuditorType(int auditorType) {
        this.auditorType = auditorType;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Integer> getAuditorIds() {
        return auditorIds;
    }

    public void setAuditorIds(List<Integer> auditorIds) {
        this.auditorIds = auditorIds;
    }
}
