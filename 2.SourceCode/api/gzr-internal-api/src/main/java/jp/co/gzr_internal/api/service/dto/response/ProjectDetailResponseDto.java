package jp.co.gzr_internal.api.service.dto.response;

import java.util.List;

import jp.co.gzr_internal.api.service.dto.ProjectDetailDto;
import jp.co.gzr_internal.api.service.dto.ProjectDto;

public class ProjectDetailResponseDto {

    private ProjectDto projectDto;
    private List<ProjectDetailDto> projectDetailDto;
    
    public ProjectDto getProjectDto() {
        return projectDto;
    }
    public void setProjectDto(ProjectDto projectDto) {
        this.projectDto = projectDto;
    }
    public List<ProjectDetailDto> getProjectDetailDto() {
        return projectDetailDto;
    }
    public void setProjectDetailDto(List<ProjectDetailDto> projectDetailDto) {
        this.projectDetailDto = projectDetailDto;
    }
}
