package jp.co.gzr_internal.api.service.dto;

public class ProjectListDto {

    private String projectCode;

    private String projectName;

    private String projectManagementCode;

    private String projectManagementName;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectManagementCode() {
        return projectManagementCode;
    }

    public void setProjectManagementCode(String projectManagementCode) {
        this.projectManagementCode = projectManagementCode;
    }

    public String getProjectManagementName() {
        return projectManagementName;
    }

    public void setProjectManagementName(String projectManagementName) {
        this.projectManagementName = projectManagementName;
    }

}
