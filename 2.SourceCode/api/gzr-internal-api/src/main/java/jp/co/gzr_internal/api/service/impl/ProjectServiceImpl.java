package jp.co.gzr_internal.api.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.gzr_internal.api.domain.Employee;
import jp.co.gzr_internal.api.domain.MPosition;
import jp.co.gzr_internal.api.domain.Project;
import jp.co.gzr_internal.api.domain.ProjectDetail;
import jp.co.gzr_internal.api.domain.ProjectRank;
import jp.co.gzr_internal.api.domain.key.ProjectDetailKey;
import jp.co.gzr_internal.api.repository.CustomProjectRepository;
import jp.co.gzr_internal.api.repository.EmployeeRepository;
import jp.co.gzr_internal.api.repository.MPositionRepository;
import jp.co.gzr_internal.api.repository.ProjectDetailRepository;
import jp.co.gzr_internal.api.repository.ProjectRankRepository;
import jp.co.gzr_internal.api.repository.ProjectRepository;
import jp.co.gzr_internal.api.service.MailService;
import jp.co.gzr_internal.api.service.ProjectService;
import jp.co.gzr_internal.api.service.dto.EmployeeDto;
import jp.co.gzr_internal.api.service.dto.PageDto;
import jp.co.gzr_internal.api.service.dto.ProjectDetailDto;
import jp.co.gzr_internal.api.service.dto.ProjectDetailSearchDto;
import jp.co.gzr_internal.api.service.dto.ProjectDto;
import jp.co.gzr_internal.api.service.dto.request.CreateProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.DetailProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.SearchProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.UpdateProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.response.DataAndPageResponseDto;
import jp.co.gzr_internal.api.service.dto.response.DetailProjectResponseDto;
import jp.co.gzr_internal.api.service.dto.response.ListProjectRankResponseDto;
import jp.co.gzr_internal.api.service.dto.response.SearchProjectResponseDto;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.commons.MessageContants;
import jp.co.gzr_internal.api.web.rest.commons.ResponseCommon;
import jp.co.gzr_internal.api.web.rest.commons.enums.PositionEnum;
import jp.co.gzr_internal.api.web.rest.util.Utils;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final ProjectDetailRepository projectDetailRepository;

    private final CustomProjectRepository customProjectRepository;

    private final MailService mailService;

    private final EmployeeRepository employeeRepository;

    private final MPositionRepository mPositionRepository;

    private final ProjectRankRepository projectRankRepository;

    public ProjectServiceImpl(ProjectRankRepository projectRankRepository, ProjectRepository projectRepository,
        MailService mailService, ProjectDetailRepository projectDetailRepository,
        CustomProjectRepository customProjectRepository, EmployeeRepository employeeRepository,
        MPositionRepository mPositionRepository) {

        super();
        this.projectRepository = projectRepository;
        this.mailService = mailService;
        this.projectDetailRepository = projectDetailRepository;
        this.customProjectRepository = customProjectRepository;
        this.employeeRepository = employeeRepository;
        this.mPositionRepository = mPositionRepository;
        this.projectRankRepository = projectRankRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> createProject(CreateProjectRequestDto projectRequestDto) throws Exception {

        String email = Utils.getEmailFromLogin();

        Project project = new Project();
        project.setProjectNameJP(projectRequestDto.getProjectNameJP());
        project.setProjectNameVN(projectRequestDto.getProjectNameVN());
        project.setBillableEffort(projectRequestDto.getBillableEffort());
        project.setStartDate(Utils.stringToDate(projectRequestDto.getStartDate(), Contants.CONST_STR_PATTERN_YYYYMMDD));
        if (!Contants.CONST_STR_BLANK.equals(projectRequestDto.getEndDate())) {
            project.setEndDate(Utils.stringToDate(projectRequestDto.getEndDate(), Contants.CONST_STR_PATTERN_YYYYMMDD));
        }
        project.setCustomerName(projectRequestDto.getCustomerName());
        project.setSale(projectRequestDto.getSale());

        project.setProjectRank(Integer.valueOf(projectRequestDto.getProjectRank()));

        project.setScope(projectRequestDto.getScope());
        project.setObjectives(projectRequestDto.getObjectives());

        StringBuffer emailCC = new StringBuffer();
        for (int i = 0; i < projectRequestDto.getEmailCC().length; i++) {
            if (i == (projectRequestDto.getEmailCC().length - 1)) {
                emailCC.append(projectRequestDto.getEmailCC()[i]);
            } else {
                emailCC.append(projectRequestDto.getEmailCC()[i]);
                emailCC.append(",");
            }
        }
        project.setEmailCC(emailCC.toString());
        project.setCreateDate(Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME));
        project.setUpdateDate(Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME));
        project.setDeleteFlag(0);

        projectRepository.saveAndFlush(project);

        List<ProjectDetail> ProjectDetailList = new ArrayList<>();

        if (!projectRequestDto.getProjectManagement().isEmpty()) {
            ProjectDetail projectDetail = new ProjectDetail();
            projectDetail.setProjectDetailKey(new ProjectDetailKey(project.getProjectCode(),
                projectRequestDto.getProjectManagement(), Contants.CONST_PROJECT_PROJECTMANAGEMENT));
            projectDetail.setCreateDate(Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME));
            projectDetail.setUpdateDate(Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME));
            projectDetail.setDeleteFlag(0);
            ProjectDetailList.add(projectDetail);
        }

        if (!projectRequestDto.getBrSE().isEmpty()) {
            ProjectDetail projectDetail = new ProjectDetail();
            projectDetail.setProjectDetailKey(new ProjectDetailKey(project.getProjectCode(),
                projectRequestDto.getBrSE(), Contants.CONST_PROJECT_BRSE));
            projectDetail.setCreateDate(Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME));
            projectDetail.setUpdateDate(Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME));
            projectDetail.setDeleteFlag(0);
            ProjectDetailList.add(projectDetail);
        }

        if (!projectRequestDto.getTeamLead().isEmpty()) {
            ProjectDetail projectDetail = new ProjectDetail();
            projectDetail.setProjectDetailKey(new ProjectDetailKey(project.getProjectCode(),
                projectRequestDto.getTeamLead(), Contants.CONST_PROJECT_TEAMLEAD));
            projectDetail.setCreateDate(Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME));
            projectDetail.setUpdateDate(Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME));
            projectDetail.setDeleteFlag(0);
            ProjectDetailList.add(projectDetail);
        }

        for (ProjectDetail detail : ProjectDetailList) {
            projectDetailRepository.saveAndFlush(detail);
        }

        EmployeeDto projectManagement = employeeRepository
            .selectByEmployeeCode(projectRequestDto.getProjectManagement());
        EmployeeDto brSE = employeeRepository.selectByEmployeeCode(projectRequestDto.getBrSE());
        EmployeeDto teamLead = employeeRepository.selectByEmployeeCode(projectRequestDto.getTeamLead());

        String rank = Contants.CONST_STR_BLANK;

        if (Integer.valueOf(projectRequestDto.getProjectRank()).intValue() == 0) {
            rank = "N/A";
        } else {
            Optional<ProjectRank> optionalRank = projectRankRepository
                .findByIdAndDeleteFlag(Integer.valueOf(projectRequestDto.getProjectRank()), 0);
            if (optionalRank.isPresent()) {
                rank = optionalRank.get().getRank();
            } else {
                rank = "N/A";
            }
        }

        mailService.sendCreateProjectMail(email, projectRequestDto, projectManagement, brSE, teamLead, rank);

        return new ResponseEntity<>(ResponseCommon.createResponse(null, MessageContants.SUCCESS_CODE),
            HttpStatus.CREATED);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getProjectRank() throws Exception {
        List<ProjectRank> projectRankList = projectRankRepository.findByDeleteFlag(0);
        List<ListProjectRankResponseDto> response = new ArrayList<>();
        projectRankList.forEach(projectRank -> {
            ListProjectRankResponseDto responseDto = new ListProjectRankResponseDto();
            responseDto.setId(projectRank.getId());
            responseDto.setRank(projectRank.getRank());
            responseDto.setDescription(projectRank.getDescription());
            response.add(responseDto);
        });
        return new ResponseEntity<>(ResponseCommon.createResponse(response, MessageContants.SUCCESS_CODE),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> searchProject(SearchProjectRequestDto requestDto) throws Exception {
        // Initial variable
        DataAndPageResponseDto response = new DataAndPageResponseDto();
        SearchProjectResponseDto responseDto = new SearchProjectResponseDto();
        List<ProjectDetailSearchDto> projectDetailList = new ArrayList<>();
        List<Project> projectList = new ArrayList<>();

        projectList = projectRepository.findByDeleteFlag(0);
        projectDetailList = customProjectRepository.getListProject(requestDto);
        responseDto.setProjectList(projectList);
        responseDto.setProjectDetailList(projectDetailList);
        BigInteger totalRecord = (BigInteger) customProjectRepository.countProject(requestDto);
        PageDto pageDto = new PageDto(totalRecord.intValue(), requestDto.getPage(), requestDto.getTotalRecordOfPage());
        // Set data and page to response
        response.setDataResult(responseDto);
        response.setPageDto(pageDto);
        return new ResponseEntity<>(ResponseCommon.createResponse(response, MessageContants.SUCCESS_CODE),
            HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getDetailProject(DetailProjectRequestDto requestDto) throws Exception {
        // Check projectCode is empty or null
        if (StringUtils.isEmpty(requestDto.getProjectCode())) {
            return new ResponseEntity<>(
                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_FIELD_NOT_EMPTY),
                HttpStatus.NOT_FOUND);
        }
        DetailProjectResponseDto response = new DetailProjectResponseDto();
        List<ProjectDetailDto> projectDetail = new ArrayList<>();
        List<ProjectRank> projectRankList = new ArrayList<>();
        List<MPosition> positionList = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        List<Employee> employeeList = new ArrayList<>();
        ProjectDto project = customProjectRepository.getProjectByCode(requestDto.getProjectCode());
        if (project == null) {
            return new ResponseEntity<>(
                ResponseCommon.createResponseError(MessageContants.CONST_ERROR_CODE_FIELD_NOT_EMPTY),
                HttpStatus.NOT_FOUND);
        }
        projectDetail = customProjectRepository.getDetailProject(requestDto);
        projectRankList = projectRankRepository.findByDeleteFlag(0);
        positionList = mPositionRepository.findByDeleteFlag(0);
        employees = employeeRepository.findByDeleteFlag(0);
        // Set upperCase employeeName
        employees.forEach(data -> {
            data.setEmployeeName(Utils.upperCaseWords(data.getEmployeeName()));
            employeeList.add(data);
        });

        response.setProject(project);
        response.setProjectDetail(projectDetail);
        response.setProjectRankList(projectRankList);
        response.setPositionList(positionList);
        response.setEmployeeList(employeeList);
        return new ResponseEntity<>(ResponseCommon.createResponse(response, MessageContants.SUCCESS_CODE),
            HttpStatus.OK);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> updateProject(UpdateProjectRequestDto requestDto) throws Exception {

        Integer role = Utils.getRoleFromLogin();
        boolean isUpdate = false;

        if (role.intValue() == 1) {
            isUpdate = true;
        } else {
            String employeeCode = Utils.getEmployeeCodeFromLogin();
            int projectDto = projectRepository.checkProjectOfEmployee(
                Integer.valueOf(requestDto.getProjectCode()).intValue(), employeeCode,
                PositionEnum.PROJECT_MANAGER.getValue().intValue());

            if (projectDto > 0) {
                isUpdate = true;
            }
        }

        if (isUpdate) {
            Optional<Project> projectOptional = projectRepository
                .findByProjectCodeAndDeleteFlag(Integer.valueOf(requestDto.getProjectCode()).intValue(), 0);
            if (projectOptional.isPresent()) {

                Project project = projectOptional.get();
                project.setProjectNameJP(requestDto.getProjectNameJP());
                project.setProjectNameVN(requestDto.getProjectNameVN());
                project.setBillableEffort(requestDto.getBillableEffort());
                project
                    .setStartDate(Utils.stringToDate(requestDto.getStartDate(), Contants.CONST_STR_PATTERN_YYYYMMDD));
                project.setEndDate(Utils.stringToDate(requestDto.getEndDate(), Contants.CONST_STR_PATTERN_YYYYMMDD));
                project.setCustomerName(requestDto.getCustomerName());
                project.setSale(requestDto.getSale());
                project.setScope(requestDto.getScope());
                project.setProjectRank(Integer.valueOf(requestDto.getProjectRank()));

                project.setScope(requestDto.getScope());
                project.setObjectives(requestDto.getObjectives());

                StringBuffer emailCC = new StringBuffer();
                for (int i = 0; i < requestDto.getEmailCC().length; i++) {
                    if (i == (requestDto.getEmailCC().length - 1)) {
                        emailCC.append(requestDto.getEmailCC()[i]);
                    } else {
                        emailCC.append(requestDto.getEmailCC()[i]);
                        emailCC.append(",");
                    }
                }
                project.setEmailCC(emailCC.toString());
                project.setUpdateDate(Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME));

                projectRepository.saveAndFlush(project);
                List<ProjectDetail> projectDetailList = projectDetailRepository
                    .findByProjectDetailKeyProjectCode(Integer.valueOf(requestDto.getProjectCode()));

                projectDetailList.forEach(detail -> {
                    projectDetailRepository.delete(detail);
                });

                requestDto.getMemberList().forEach(detail -> {
                    ProjectDetail projectDetail = new ProjectDetail();
                    try {
                        ProjectDetailKey key = new ProjectDetailKey(Integer.valueOf(requestDto.getProjectCode()),
                            detail.getEmployeeCode(), Integer.valueOf(detail.getPositionCode()));
                        projectDetail.setProjectDetailKey(key);
                        projectDetail
                            .setCreateDate(Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME));
                        projectDetail
                            .setUpdateDate(Utils.getFullCurrentDateString(Contants.CONST_STR_PATTERN_UPDATE_TIME));
                        projectDetail.setDeleteFlag(0);

                        projectDetailRepository.saveAndFlush(projectDetail);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
            return new ResponseEntity<>(ResponseCommon.createResponse(null, MessageContants.SUCCESS_CODE),
                HttpStatus.OK);
        } else {
            return new ResponseEntity<>(
                ResponseCommon.createResponse(null, MessageContants.CONST_ERROR_CODE_NOT_ACCESS), HttpStatus.FORBIDDEN);
        }
    }
}