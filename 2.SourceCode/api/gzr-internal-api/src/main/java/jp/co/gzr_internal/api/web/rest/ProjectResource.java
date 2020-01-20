package jp.co.gzr_internal.api.web.rest;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.gzr_internal.api.service.ProjectService;
import jp.co.gzr_internal.api.service.dto.request.CreateProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.DetailProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.SearchProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.UpdateProjectRequestDto;

@RestController
@RequestMapping("api")
public class ProjectResource {

    private final ProjectService projectService;

    public ProjectResource(ProjectService projectService) {
        super();
        this.projectService = projectService;
    }

    @PostMapping(path = "/project/create")
    public ResponseEntity<?> executeCreateProject(@Valid @RequestBody CreateProjectRequestDto createProjectRequestDto)
        throws Exception {
        return projectService.createProject(createProjectRequestDto);
    }
    
    @PostMapping(path = "/project/list-rank")
    public ResponseEntity<?> executeGetProjectRank()
        throws Exception {
        return projectService.getProjectRank();
    }
    
    @PostMapping(path = "/project/search")
    public ResponseEntity<?> executeSearchProject(@Valid @RequestBody SearchProjectRequestDto requestDto)
        throws Exception {
        return projectService.searchProject(requestDto);
    }
    
    @PostMapping(path = "/project/detail")
    public ResponseEntity<?> executeDetailProject(@Valid  @RequestBody DetailProjectRequestDto requestDto) throws Exception {
        return projectService.getDetailProject(requestDto);
    }
    
    @PostMapping(path = "/project/update")
    public ResponseEntity<?> executeUpdateProject(@Valid  @RequestBody UpdateProjectRequestDto requestDto) throws Exception {
        return projectService.updateProject(requestDto);
    }
}
