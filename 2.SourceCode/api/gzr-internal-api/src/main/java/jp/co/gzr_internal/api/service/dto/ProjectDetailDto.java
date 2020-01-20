package jp.co.gzr_internal.api.service.dto;

import java.io.Serializable;

public class ProjectDetailDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String employeeCode;
    
    private String employeeName;
    
    private String positionCode;
    
    private String positionCodeName;

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionCodeName() {
        return positionCodeName;
    }

    public void setPositionCodeName(String positionCodeName) {
        this.positionCodeName = positionCodeName;
    }
    
}
