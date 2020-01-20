package jp.co.gzr_internal.api.service.dto;

public class ProjectAfterLoginDto {

    private String projectCode;
    
    private String positionCode;

    
    public ProjectAfterLoginDto() {
        super();
    }

    public ProjectAfterLoginDto(String projectCode, String positionCode) {
        super();
        this.projectCode = projectCode;
        this.positionCode = positionCode;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

}
