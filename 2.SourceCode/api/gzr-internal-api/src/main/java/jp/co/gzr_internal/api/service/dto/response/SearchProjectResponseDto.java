package jp.co.gzr_internal.api.service.dto.response;

import java.util.List;

import jp.co.gzr_internal.api.domain.MDivision;
import jp.co.gzr_internal.api.domain.MPosition;
import jp.co.gzr_internal.api.domain.MStatus;
import jp.co.gzr_internal.api.domain.MTypeContact;
import jp.co.gzr_internal.api.domain.Project;
import jp.co.gzr_internal.api.service.dto.EmployeeDto;
import jp.co.gzr_internal.api.service.dto.ProjectDetailSearchDto;

public class SearchProjectResponseDto {

    private List<Project> projectList;

    private List<ProjectDetailSearchDto> projectDetailList;

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public List<ProjectDetailSearchDto> getProjectDetailList() {
        return projectDetailList;
    }

    public void setProjectDetailList(List<ProjectDetailSearchDto> projectDetailList) {
        this.projectDetailList = projectDetailList;
    }

}
