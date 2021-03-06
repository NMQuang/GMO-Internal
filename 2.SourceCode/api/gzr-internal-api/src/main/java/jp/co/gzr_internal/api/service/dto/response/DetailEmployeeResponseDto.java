package jp.co.gzr_internal.api.service.dto.response;

import java.util.List;

import jp.co.gzr_internal.api.domain.MDivision;
import jp.co.gzr_internal.api.domain.MPosition;
import jp.co.gzr_internal.api.domain.MStatus;
import jp.co.gzr_internal.api.domain.MTypeContact;
import jp.co.gzr_internal.api.service.dto.EmployeeDto;

public class DetailEmployeeResponseDto {

    private List<EmployeeDto> employee;

    private List<MDivision> divisionList;

    private List<MPosition> positionList;

    private List<MStatus> statusList;

    private List<MTypeContact> typeContactList;

    public List<EmployeeDto> getEmployee() {
        return employee;
    }

    public void setEmployee(List<EmployeeDto> employee) {
        this.employee = employee;
    }

    public List<MDivision> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(List<MDivision> divisionList) {
        this.divisionList = divisionList;
    }

    public List<MPosition> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<MPosition> positionList) {
        this.positionList = positionList;
    }

    public List<MStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<MStatus> statusList) {
        this.statusList = statusList;
    }

    public List<MTypeContact> getTypeContactList() {
        return typeContactList;
    }

    public void setTypeContactList(List<MTypeContact> typeContactList) {
        this.typeContactList = typeContactList;
    }
    
}
