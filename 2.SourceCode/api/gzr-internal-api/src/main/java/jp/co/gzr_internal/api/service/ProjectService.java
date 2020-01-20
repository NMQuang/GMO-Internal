package jp.co.gzr_internal.api.service;

import org.springframework.http.ResponseEntity;

import jp.co.gzr_internal.api.service.dto.request.CreateProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.DetailProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.SearchProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.UpdateProjectRequestDto;

public interface ProjectService {
    
    ResponseEntity<?> createProject(CreateProjectRequestDto requestRequestDto) throws Exception;
    
    ResponseEntity<?> getProjectRank() throws Exception;
    
    ResponseEntity<?> searchProject(SearchProjectRequestDto requestDto) throws Exception;
    
    ResponseEntity<?> getDetailProject(DetailProjectRequestDto requestDto) throws Exception;
    
    ResponseEntity<?> updateProject(UpdateProjectRequestDto requestDto) throws Exception;
}
