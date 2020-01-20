package jp.co.gzr_internal.api.domain.key;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProjectDetailKey implements Serializable{

    private static final long serialVersionUID = 1L;

    @Column(name = "project_code")
    private Integer projectCode;
    
    @Column(name = "employee_code")
    private String employeeCode;
    
    @Column(name = "position_code")
    private Integer positionCode;

    
    public ProjectDetailKey() {
        super();
    }

    public ProjectDetailKey(Integer projectCode, String employeeCode, Integer positionCode) {
        super();
        this.projectCode = projectCode;
        this.employeeCode = employeeCode;
        this.positionCode = positionCode;
    }

    public Integer getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(Integer projectCode) {
        this.projectCode = projectCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public Integer getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(Integer positionCode) {
        this.positionCode = positionCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((employeeCode == null) ? 0 : employeeCode.hashCode());
        result = prime * result + ((positionCode == null) ? 0 : positionCode.hashCode());
        result = prime * result + ((projectCode == null) ? 0 : projectCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProjectDetailKey other = (ProjectDetailKey) obj;
        if (employeeCode == null) {
            if (other.employeeCode != null)
                return false;
        } else if (!employeeCode.equals(other.employeeCode))
            return false;
        if (positionCode == null) {
            if (other.positionCode != null)
                return false;
        } else if (!positionCode.equals(other.positionCode))
            return false;
        if (projectCode == null) {
            if (other.projectCode != null)
                return false;
        } else if (!projectCode.equals(other.projectCode))
            return false;
        return true;
    }
    
}
