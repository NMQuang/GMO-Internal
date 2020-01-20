package jp.co.gzr_internal.api.service.dto.response;

import java.util.List;

import jp.co.gzr_internal.api.domain.Employee;
import jp.co.gzr_internal.api.domain.MPosition;
import jp.co.gzr_internal.api.domain.ProjectRank;
import jp.co.gzr_internal.api.service.dto.ProjectDetailDto;
import jp.co.gzr_internal.api.service.dto.ProjectDto;

public class DetailProjectResponseDto {

    private ProjectDto project;

    private List<ProjectDetailDto> projectDetail;
    
    private List<ProjectRank> projectRankList;
    
    private List<MPosition> positionList;
    
    private List<Employee> employeeList;

    public ProjectDto getProject() {
        return project;
    }

    public void setProject(ProjectDto project) {
        this.project = project;
    }

    public List<ProjectDetailDto> getProjectDetail() {
        return projectDetail;
    }

    public void setProjectDetail(List<ProjectDetailDto> projectDetail) {
        this.projectDetail = projectDetail;
    }

    public List<ProjectRank> getProjectRankList() {
        return projectRankList;
    }

    public void setProjectRankList(List<ProjectRank> projectRankList) {
        this.projectRankList = projectRankList;
    }

    public List<MPosition> getPositionList() {
        return positionList;
    }

    public void setPositionList(List<MPosition> positionList) {
        this.positionList = positionList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

}
