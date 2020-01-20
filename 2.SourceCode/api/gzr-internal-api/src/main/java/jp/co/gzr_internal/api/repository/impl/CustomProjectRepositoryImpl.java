package jp.co.gzr_internal.api.repository.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import jp.co.gzr_internal.api.repository.CustomProjectRepository;
import jp.co.gzr_internal.api.service.dto.ProjectAfterLoginDto;
import jp.co.gzr_internal.api.service.dto.ProjectDetailDto;
import jp.co.gzr_internal.api.service.dto.ProjectDetailSearchDto;
import jp.co.gzr_internal.api.service.dto.ProjectDto;
import jp.co.gzr_internal.api.service.dto.ProjectListDto;
import jp.co.gzr_internal.api.service.dto.request.DetailProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.SearchProjectRequestDto;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.util.Utils;

@Repository
public class CustomProjectRepositoryImpl implements CustomProjectRepository {

    /** The entity manager. */
    private final EntityManager entityManager;

    /**
     * Instantiates a new custom SMS push message repository impl.
     *
     * @param entityManager the entity manager
     */
    public CustomProjectRepositoryImpl(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProjectDetailSearchDto> getListProject(SearchProjectRequestDto requestDto) {
        // Initial variable
        List<ProjectDetailSearchDto> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  detail.project_code                           as projectCode                ,");
        sql.append("        CASE                                                                        ");
        sql.append(
            "            WHEN project.project_name_vn = '' AND project.project_name_jp <> '' THEN project.project_name_jp  ");
        sql.append(
            "            WHEN project.project_name_vn <> '' AND project.project_name_jp = '' THEN project.project_name_vn  ");
        sql.append("            ELSE  project.project_name_vn                                            ");
        sql.append("        END AS projectName                ,");
        sql.append("        employee.employee_code                        as memberCode                 ,");
        sql.append("        employee.employee_name                        as memberName                 ,");
        sql.append("        position.position_name                        as position                   ,");
        sql.append("        project.start_date                            as startDate                  ,");
        sql.append("        project.end_date                              as endDate                    ,");
        sql.append("        project.delete_flag                           as deleteFlag                 ");
        sql.append("FROM                                                                                ");
        sql.append("        project_detail detail                                                       ");
        sql.append("        INNER JOIN project project ON detail.project_code = project.project_code    ");
        sql.append("        INNER JOIN employees employee ON detail.employee_code = employee.employee_code ");
        sql.append("        INNER JOIN m_position position ON detail.position_code = position.id        ");
        sql.append("WHERE                                                                               ");
        sql.append("            detail.delete_flag = 0                                                  ");
        sql.append("        AND project.delete_flag = 0                                                 ");
        sql.append("        AND employee.delete_flag = 0                                                ");
        sql.append("        AND position.delete_flag = 0                                                ");

        // Add condition position = PM
        sql.append("        AND position.id = :projectManagement                                                        ");
        // Add condition
        addConditionForQuery(sql, requestDto);
        sql.append("        ORDER BY project.update_time DESC, project.start_date , project.end_date          ");
        sql.append("LIMIT   :offset, :limit                                                             ");

        // Create native query
        Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);

        // Add parameter
        addParameterForQuery(query, requestDto);
        
        query.setParameter("projectManagement", Contants.CONST_PROJECT_PROJECTMANAGEMENT);

        Integer limit = Integer.valueOf(requestDto.getTotalRecordOfPage());
        Integer offset = (Integer.valueOf(requestDto.getPage()) - 1) * limit;

        // Set parameter limit
        query.setParameter("limit", limit);
        // Set parameter offset
        query.setParameter("offset", offset);

        // Execute query and get result list
        List<Tuple> resultData = query.getResultList();
        resultData.forEach(data -> {
            if (data != null) {
                ProjectDetailSearchDto projectDetailDto = new ProjectDetailSearchDto();
                projectDetailDto.setProjectCode(data.get("projectCode").toString());
                projectDetailDto.setProjectName(data.get("projectName").toString());
                projectDetailDto.setMemberCode(data.get("memberCode").toString());
                projectDetailDto.setMemberName(Utils.upperCaseWords(data.get("memberName").toString()));
                projectDetailDto.setPosition(data.get("position").toString());
                projectDetailDto.setStartDate(data.get("startDate").toString());
                if (data.get("endDate") != null) {
                    projectDetailDto.setEndDate(data.get("endDate").toString());
                } else {
                    projectDetailDto.setEndDate(Contants.CONST_STR_BLANK);
                }

                projectDetailDto.setDeleteFlag(data.get("deleteFlag").toString());
                results.add(projectDetailDto);
            }
        });

        return results;
    }

    @Override
    public BigInteger countProject(SearchProjectRequestDto requestDto) {
        // Initial variable
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT  COUNT(detail.project_code)                                                   ");
        sql.append("FROM                                                                                 ");
        sql.append("        project_detail detail                                                       ");
        sql.append("        INNER JOIN project project ON detail.project_code = project.project_code    ");
        sql.append("        INNER JOIN employees employee ON detail.employee_code = employee.employee_code ");
        sql.append("        INNER JOIN m_position position ON detail.position_code = position.id        ");
        sql.append("WHERE                                                                                ");
        sql.append("            detail.delete_flag <> 1                                                 ");
        sql.append("        AND project.delete_flag <> 1                                                ");
        sql.append("        AND employee.delete_flag <> 1                                               ");
        sql.append("        AND position.delete_flag <> 1                                               ");
        // Add condition position = PM
        sql.append("        AND position.id = :projectManagement                                        ");
        // Add condition
        addConditionForQuery(sql, requestDto);
        // Create native query
        Query query = entityManager.createNativeQuery(sql.toString());

        // Add parameter
        addParameterForQuery(query, requestDto);
        query.setParameter("projectManagement", Contants.CONST_PROJECT_PROJECTMANAGEMENT);
        // Execute query and get result list
        BigInteger result = (BigInteger) query.getSingleResult();

        return result;
    }

    private void addConditionForQuery(StringBuilder updateSql, SearchProjectRequestDto dto) {
        // If projectName exists, it will add search conditions with projectName
        if (!StringUtils.isEmpty(dto.getProjectName())) {
            updateSql.append("  AND   project.project_name_vn        LIKE :projectName                      ");
        }
        // If StartDate exists, it will add search conditions with StartDate
        if (!StringUtils.isEmpty(dto.getStartDate())) {
            updateSql.append("  AND   project.start_date        >= :startDate                               ");
        }
        // If EndDate exists, it will add search conditions with projectName
        if (!StringUtils.isEmpty(dto.getEndDate())) {
            updateSql.append("  AND   project.end_date          <= :endDate                                 ");
        }
    }

    private void addParameterForQuery(Query query, SearchProjectRequestDto dto) {
        // If projectName exists, it will add search conditions with projectName
        if (!StringUtils.isEmpty(dto.getProjectName())) {
            query.setParameter("projectName",
                Contants.CONST_STR_PERCENT_SIGN + dto.getProjectName().trim() + Contants.CONST_STR_PERCENT_SIGN);
        }
        // If StartDate exists, it will add search conditions with StartDate
        if (!StringUtils.isEmpty(dto.getStartDate())) {
            query.setParameter("startDate", dto.getStartDate());
        }
        // If EndDate exists, it will add search conditions with projectName
        if (!StringUtils.isEmpty(dto.getEndDate())) {
            query.setParameter("endDate", dto.getEndDate());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProjectDetailDto> getDetailProject(DetailProjectRequestDto requestDto) {
        // Initial variable
        List<ProjectDetailDto> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  detail.project_code                           as projectCode                ,");
        sql.append("        employee.employee_code                        as memberCode                 ,");
        sql.append("        employee.employee_name                        as memberName                 ,");
        sql.append("        position.id                                   as positionCode               ,");
        sql.append("        position.position_name                        as positionName               ,");
        sql.append("        detail.delete_flag                            as deleteFlag                 ");
        sql.append("FROM                                                                                ");
        sql.append("        project_detail detail                                                       ");
        sql.append("        INNER JOIN employees employee ON detail.employee_code = employee.employee_code ");
        sql.append("        INNER JOIN m_position position ON detail.position_code = position.id        ");
        sql.append("WHERE                                                                               ");
        sql.append("            detail.delete_flag = 0                                                  ");
        sql.append("        AND employee.delete_flag = 0                                                ");
        sql.append("        AND position.delete_flag = 0                                                ");
        sql.append("        AND detail.project_code = :projectCode                                      ");
        sql.append("ORDER BY  position.id, employee.employee_name                                       ");

        // Create native query
        Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);

        // Set parameter limit
        query.setParameter("projectCode", requestDto.getProjectCode());
        // Execute query and get result list
        List<Tuple> resultData = query.getResultList();
        resultData.forEach(data -> {
            if (data != null) {
                ProjectDetailDto projectDetailDto = new ProjectDetailDto();
                projectDetailDto.setEmployeeCode(data.get("memberCode").toString());
                projectDetailDto.setEmployeeName(Utils.upperCaseWords(data.get("memberName").toString()));
                projectDetailDto.setPositionCode(data.get("positionCode").toString());
                projectDetailDto.setPositionCodeName(data.get("positionName").toString());
                results.add(projectDetailDto);
            }
        });

        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ProjectDto getProjectByCode(String projectCode) {
        List<ProjectDto> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        ProjectDto projectDto = null;

        sql.append("SELECT  project.project_code                            as projectCode          ,");
        sql.append("        project.project_name_jp                         as projectNameJP        ,");
        sql.append("        project.project_name_vn                         as projectNameVN        ,");
        sql.append("        project.billable_effort                         as billableEffort       ,");
        sql.append("        project.start_date                              as startDate            ,");
        sql.append("        project.end_date                                as endDate              ,");
        sql.append("        project.customer_name                           as customerName         ,");
        sql.append("        project.sale                                    as sale                 ,");
        sql.append("        rank.rank                                       as projectRank          ,");
        sql.append("        project.scope                                   as scope                ,");
        sql.append("        project.objectives                              as objectives           ,");
        sql.append("        project.email_cc                                as emailCC               ");
        sql.append("FROM project project                                                             ");
        sql.append(
            "LEFT JOIN project_rank rank ON project.project_rank = rank.id  AND rank.delete_flag = 0                 ");
        sql.append("WHERE project.project_code = :projectCode                                        ");
        sql.append("AND   project.delete_flag = 0                                                    ");
        // Create native query
        Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);

        // Set parameter limit
        query.setParameter("projectCode", projectCode);

        // Execute query and get result list
        List<Tuple> resultData = query.getResultList();
        resultData.forEach(data -> {
            ProjectDto project = new ProjectDto();
            project.setProjectCode(Integer.valueOf(data.get("projectCode").toString()));
            if (data.get("projectNameJP") != null) {
                project.setProjectNameJP(data.get("projectNameJP").toString());
            } else {
                project.setProjectNameJP(Contants.CONST_STR_BLANK);
            }
            if (data.get("projectNameVN") != null) {
                project.setProjectNameVN(data.get("projectNameVN").toString());
            } else {
                project.setProjectNameVN(Contants.CONST_STR_BLANK);
            }

            project.setBillableEffort(data.get("billableEffort").toString());
            project.setStartDate(data.get("startDate").toString());
            if (data.get("endDate") != null) {
                project.setEndDate(data.get("endDate").toString());
            } else {
                project.setEndDate(Contants.CONST_STR_BLANK);
            }
            project.setCustomerName(data.get("customerName").toString());
            if (data.get("sale") != null) {
                project.setSale(data.get("sale").toString());
            } else {
                project.setSale(Contants.CONST_STR_BLANK);
            }
            if (data.get("projectRank") != null) {
                project.setProjectRank(data.get("projectRank").toString());
            } else {
                project.setProjectRank(Contants.CONST_STR_BLANK);
            }

            project.setScope(data.get("scope").toString());
            project.setObjectives(data.get("objectives").toString());
            if (data.get("emailCC") != null) {
                project.setEmailCC(data.get("emailCC").toString());
            } else {
                project.setEmailCC(Contants.CONST_STR_BLANK);
            }

            results.add(project);
        });
        if (results.size() > 0) {
            projectDto = results.get(0);
        }
        return projectDto;

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProjectAfterLoginDto> getRoleOfProject(String employeeCode) {
        // Initial variable
        List<ProjectAfterLoginDto> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  detail.project_code                           as projectCode               ,");
        sql.append("        detail.position_code                          as positionCode               ");
        sql.append("FROM                                                                                ");
        sql.append("        project_detail detail                                                       ");
        sql.append("WHERE                                                                               ");
        sql.append("            detail.delete_flag = 0                                                  ");
        sql.append("        AND detail.employee_code = :employeeCode                                    ");

        // Create native query
        Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);

        // Set parameter limit
        query.setParameter("employeeCode", employeeCode);
        // Execute query and get result list
        List<Tuple> resultData = query.getResultList();
        resultData.forEach(data -> {
            if (data != null) {
                ProjectAfterLoginDto projectDetailDto = new ProjectAfterLoginDto();
                projectDetailDto.setProjectCode(data.get("projectCode").toString());
                projectDetailDto.setPositionCode(data.get("positionCode").toString());
                results.add(projectDetailDto);
            }
        });

        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ProjectListDto> getListProjectByEmployeeCode(String employeeCode) {
        // Initial variable
        List<ProjectListDto> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        // Check get list project in page list request
        if (!StringUtils.equals(Contants.CONST_STR_BLANK, employeeCode)) {
            sql.append("SELECT   result.projectCode AS projectCode,                                           ");
            sql.append("         result.projectName AS projectName,                                           ");
            sql.append("         result.projectManagementCode AS projectManagementCode,                       ");
            sql.append("         result.projectManagementName AS projectManagementName                        ");
            sql.append("FROM  (                                                                               ");
            sql.append("SELECT  project.project_code                           as projectCode                ,");
            sql.append("        project.project_name                           as projectName                ,");
            sql.append("        detail.employee_code                           as projectManagementCode      ,");
            sql.append("        employee.employee_name                         as projectManagementName       ");
            sql.append("FROM    (                                                                          ");
            sql.append("SELECT  detail.project_code                           as project_code               ,");
            sql.append("        CASE                                                                        ");
            sql.append(
                "            WHEN project.project_name_vn = '' AND project.project_name_jp <> '' THEN project.project_name_jp  ");
            sql.append(
                "            WHEN project.project_name_vn <> '' AND project.project_name_jp = '' THEN project.project_name_vn  ");
            sql.append("            ELSE  project.project_name_vn                                            ");
            sql.append("        END AS project_name                                                         ");
            sql.append("FROM                                                                                ");
            sql.append("        project_detail detail                                                       ");
            sql.append("INNER JOIN                                                                          ");
            sql.append("        project project  ON detail.project_code = project.project_code              ");
            sql.append("WHERE                                                                               ");
            sql.append("            detail.delete_flag = 0   AND project.delete_flag = 0                    ");
            sql.append("        AND detail.employee_code = :employeeCode                                    ");
            sql.append("        AND ( detail.position_code = 6 OR detail.position_code = 3 )                ");
            sql.append("        ) project                                                                   ");
            sql.append("INNER JOIN project_detail detail ON detail.project_code = project.project_code      ");
            sql.append("INNER JOIN employees employee ON employee.employee_code = detail.employee_code      ");
            sql.append("WHERE    detail.delete_flag = 0                                                     ");
            sql.append("     AND employee.delete_flag = 0                                                   ");
            sql.append("     AND detail.position_code = 3                                                   ");
            sql.append(" ) result                                                                           ");
            sql.append(" GROUP BY                                                                           ");
            sql.append("         result.projectCode,                                                        ");
            sql.append("         result.projectName,                                                        ");
            sql.append("         result.projectManagementCode,                                              ");
            sql.append("         result.projectManagementName                                               ");
        } else {
            sql.append("SELECT   result.projectCode AS projectCode,                                           ");
            sql.append("         result.projectName AS projectName,                                           ");
            sql.append("         result.projectManagementCode AS projectManagementCode,                       ");
            sql.append("         result.projectManagementName AS projectManagementName                        ");
            sql.append("FROM  (                                                                               ");
            sql.append("SELECT  project.project_code                           as projectCode                ,");
            sql.append("        project.project_name                           as projectName                ,");
            sql.append("        detail.employee_code                           as projectManagementCode      ,");
            sql.append("        employee.employee_name                         as projectManagementName       ");
            sql.append("FROM    (                                                                          ");
            sql.append("SELECT  detail.project_code                           as project_code               ,");
            sql.append("        CASE                                                                        ");
            sql.append(
                "            WHEN project.project_name_vn = '' AND project.project_name_jp <> '' THEN project.project_name_jp  ");
            sql.append(
                "            WHEN project.project_name_vn <> '' AND project.project_name_jp = '' THEN project.project_name_vn  ");
            sql.append("            ELSE  project.project_name_vn                                            ");
            sql.append("        END AS project_name                                                         ");
            sql.append("FROM                                                                                ");
            sql.append("        project_detail detail                                                       ");
            sql.append("INNER JOIN                                                                          ");
            sql.append("        project project  ON detail.project_code = project.project_code              ");
            sql.append("WHERE                                                                               ");
            sql.append("            detail.delete_flag = 0   AND project.delete_flag = 0                    ");
            sql.append("        AND ( detail.position_code = 6 OR detail.position_code = 3 )                ");
            sql.append("        ) project                                                                   ");
            sql.append("INNER JOIN project_detail detail ON detail.project_code = project.project_code      ");
            sql.append("INNER JOIN employees employee ON employee.employee_code = detail.employee_code      ");
            sql.append("WHERE    detail.delete_flag = 0                                                     ");
            sql.append("     AND employee.delete_flag = 0                                                   ");
            sql.append("     AND detail.position_code = 3                                                   ");
            sql.append(" ) result                                                                           ");
            sql.append(" GROUP BY                                                                           ");
            sql.append("         result.projectCode,                                                        ");
            sql.append("         result.projectName,                                                        ");
            sql.append("         result.projectManagementCode,                                              ");
            sql.append("         result.projectManagementName                                               ");
        }

        // Create native query
        Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);

        // Set parameter
        if (!StringUtils.equals(Contants.CONST_STR_BLANK, employeeCode)) {
            query.setParameter("employeeCode", employeeCode);
        }
        // Execute query and get result list
        List<Tuple> resultData = query.getResultList();
        resultData.forEach(data -> {
            if (data != null) {
                ProjectListDto projectDto = new ProjectListDto();
               projectDto.setProjectCode(data.get("projectCode").toString());
               projectDto.setProjectName(data.get("projectName").toString());
               projectDto.setProjectManagementCode(data.get("projectManagementCode").toString());
               projectDto.setProjectManagementName(Utils.upperCaseWords(data.get("projectManagementName").toString()));
                results.add(projectDto);
            }
        });

        return results;
    }

}
