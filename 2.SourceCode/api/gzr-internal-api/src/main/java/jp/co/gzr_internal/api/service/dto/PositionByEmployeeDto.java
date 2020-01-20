package jp.co.gzr_internal.api.service.dto;

public class PositionByEmployeeDto {

    private Integer positionCode;
    private String positionName;
    
    
    public PositionByEmployeeDto() {
        super();
    }
    public PositionByEmployeeDto(Integer positionCode, String positionName) {
        super();
        this.positionCode = positionCode;
        this.positionName = positionName;
    }
    public Integer getPositionCode() {
        return positionCode;
    }
    public void setPositionCode(Integer positionCode) {
        this.positionCode = positionCode;
    }
    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
}
