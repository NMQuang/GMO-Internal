package jp.co.gzr_internal.api.repository;

import java.math.BigInteger;
import java.util.List;

import jp.co.gzr_internal.api.service.dto.ProjectAfterLoginDto;
import jp.co.gzr_internal.api.service.dto.ProjectDetailDto;
import jp.co.gzr_internal.api.service.dto.ProjectDetailSearchDto;
import jp.co.gzr_internal.api.service.dto.ProjectDto;
import jp.co.gzr_internal.api.service.dto.ProjectListDto;
import jp.co.gzr_internal.api.service.dto.request.DetailProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.SearchProjectRequestDto;

public interface CustomProjectRepository {

    List<ProjectDetailSearchDto> getListProject(SearchProjectRequestDto requestDto);

    BigInteger countProject(SearchProjectRequestDto requestDto);
    
    List<ProjectDetailDto> getDetailProject(DetailProjectRequestDto requestDto);
    
    ProjectDto getProjectByCode(String projectCode);
    
    List<ProjectAfterLoginDto> getRoleOfProject(String employeeCode);
    
    List<ProjectListDto> getListProjectByEmployeeCode(String employeeCode);
    
}
