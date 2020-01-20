/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Otc 07, 2019
 */
package jp.co.gzr_internal.api.repository.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import jp.co.gzr_internal.api.repository.ListApprovalRepository;
import jp.co.gzr_internal.api.service.dto.ListApprovalDto;
import jp.co.gzr_internal.api.service.dto.request.ApprovalSearchRequestDto;
import jp.co.gzr_internal.api.web.rest.util.Utils;
import jp.co.gzr_internal.api.web.rest.validator.StringValidation;

/**
 * The Class ListApprovalRepositoryImpl.
 */
@Repository
public class ListApprovalRepositoryImpl implements ListApprovalRepository {
    /** The entity manager. */
    private final EntityManager entityManager;

    /**
     * Instantiates a new m user custom rpository impl.
     *
     * @param entityManager the entity manager
     */
    public ListApprovalRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Find all approval with search setting.
     *
     * @param dto the dto
     * @param limit the limit
     * @param offset the offset
     * @return the list
     * @throws Exception the exception
     */
    @Override
    public List<ListApprovalDto> findAllApproval(ApprovalSearchRequestDto dto, Integer limit, Integer offset,
        boolean flag) throws Exception {

        StringBuilder sql = new StringBuilder();
        List<ListApprovalDto> results = new ArrayList<>();
        boolean searchProjectName = !StringValidation.isNullOrEmpty(dto.getProjectName());
        boolean searchDate = !StringValidation.isNullOrEmpty(dto.getRequestDate());

        sql.append(" SELECT    list_data.approval_id                                    AS approvalId               ,");
        sql.append("           list_data.employee_code                                  AS employeeCode             ,");
        sql.append("           list_data.employee_name                                  AS employeeName             ,");
        sql.append("           list_data.position_name                                  AS positionName             ,");
        sql.append("           list_data.project_name                                   AS projectName              ,");
        sql.append("           list_data.date_ot                                        AS dateOt                   ,");
        sql.append("           list_data.plan_time_ot                                   AS planTimeOt               ,");
        sql.append("           list_data.approve_time_ot                                AS approveTimeOt             ");
        sql.append(" FROM                                                                                            ");
        sql.append("    ( SELECT   approval_data.*                                                                  ,");
        sql.append("               detail.plan_time_ot                                                              ,");
        sql.append("               employee.employee_name                                                           ,");
        sql.append("               position.position_name                                                            ");
        sql.append("     FROM                                                                                        ");
        sql.append("           (SELECT approval.id                                      AS approval_id              ,");
        sql.append("                   approval.employee_code                           AS employee_code            ,");
        sql.append("                   project.project_code                             AS project_code             ,");
        sql.append("                   CASE                                                                          ");
        sql.append("                       WHEN project.project_name_vn = ''                                         ");
        sql.append("                            AND project.project_name_jp <> '' THEN project.project_name_jp       ");
        sql.append("                       WHEN project.project_name_vn <> ''                                        ");
        sql.append("                            AND project.project_name_jp = '' THEN project.project_name_vn        ");
        sql.append("                       ELSE project.project_name_vn                                              ");
        sql.append("                   END                                              AS project_name             ,");
        sql.append("                   approval.date_ot                                 AS date_ot                  ,");
        sql.append("                   approval.approve_time_ot                         AS approve_time_ot          ,");
        sql.append("                   request.id                                       AS request_id               ,");
        sql.append("                   approval.position_code                           AS position_code             ");
        sql.append("            FROM ot_time_approval approval                                                       ");
        sql.append("            INNER JOIN request request ON approval.request_id = request.id                       ");
        sql.append("            INNER JOIN project project ON project.project_code = approval.project_code           ");
        sql.append("            WHERE request.status = 3                                                             ");
        sql.append("              AND request.delete_flag = 0                                                        ");
        sql.append("              AND approval.delete_flag = 0                                                       ");
        sql.append("              AND project.delete_flag = 0 ) approval_data                                        ");
        sql.append("      INNER JOIN request_detail detail ON approval_data.request_id = detail.request_id           ");
        sql.append("      AND approval_data.employee_code = detail.employee_code                                     ");
        sql.append("      AND approval_data.date_ot = detail.date_ot                                                 ");
        sql.append("      AND approval_data.position_code = detail.position_code                                     ");
        sql.append("      INNER JOIN employees employee ON approval_data.employee_code = employee.employee_code      ");
        sql.append("      INNER JOIN m_position position ON approval_data.position_code = position.id                ");
        sql.append("      WHERE detail.delete_flag = 0                                                               ");
        sql.append("        AND employee.delete_flag = 0                                                             ");
        sql.append("        AND position.delete_flag = 0                                                             ");
     // Binding data to condition when date request not null
        if (searchDate) {
            sql.append(" AND DATE_FORMAT(approval_data.date_ot,'%Y-%m') =:dateTime                                   ");
        }
     // Binding data to condition when project name not null
        if (searchProjectName) {
            sql.append(" AND approval_data.project_name=:projectName                                                 ");
        }
        sql.append("      ORDER BY approval_data.project_code                                                        ,");
        sql.append("               approval_data.position_code                                                       ,");
        sql.append("               approval_data.employee_code) list_data                                             ");
        sql.append(" INNER JOIN                                                                                       ");
        sql.append("     (SELECT approval.project_code                                                               ,");
        sql.append("             approval.position_code                                                              ,");
        sql.append("             approval.employee_code                                                               ");
        sql.append("      FROM ot_time_approval approval                                                              ");
        sql.append("      INNER JOIN request request ON approval.request_id = request.id                              ");
        sql.append("      INNER JOIN project project ON project.project_code = approval.project_code                  ");
        sql.append("      WHERE request.STATUS = 3                                                                    ");
        sql.append("        AND request.delete_flag = 0                                                               ");
        sql.append("        AND approval.delete_flag = 0                                                              ");
        sql.append("        AND project.delete_flag = 0                                                               ");
     // Binding data to condition when date request not null
        if (searchDate) {
            sql.append(" AND DATE_FORMAT(approval.date_ot,'%Y-%m') =:dateTime                                         ");
        }
     // Binding data to condition when project name not null
        if (searchProjectName) {
            sql.append("AND ( project.project_name_jp = :projectName OR project.project_name_vn = :projectName )      ");
        }
        sql.append("      GROUP BY approval.project_code                                                             ,");
        sql.append("               approval.position_code                                                            ,");
        sql.append("               approval.employee_code                                                             ");
        sql.append("      ORDER BY approval.project_code                                                             ,");
        sql.append("               approval.position_code                                                            ,");
        sql.append("               approval.employee_code                                                             ");
     // If flag = true, it add LIMIT query into SQL query
        if (flag) {
            sql.append(" LIMIT :totalRecord  OFFSET :offset                                                           ");
        }
        sql.append("      ) limit_data ON list_data.employee_code = limit_data.employee_code                          ");
        sql.append(" AND list_data.project_code = limit_data.project_code                                             ");
        sql.append(" AND list_data.position_code = limit_data.position_code                                           ");
        sql.append(" ORDER BY list_data.project_name                                                                 ,");
        sql.append("          list_data.employee_name                                                                ,");
        sql.append("          list_data.date_ot                                                                       ");

        Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);
        // If flag = true, it add LIMIT query into SQL query
        if (flag) {
            /* Execute query and get result list */
            setValueForParamAndQuery(query, dto, limit, offset, flag);
        } else {
            setValueForParamAndQuery(query, dto, null, null, flag);
        }
        @SuppressWarnings("unchecked")
        List<Tuple> resultData = query.getResultList();
        resultData.forEach(data -> {
            if (data != null) {
                ListApprovalDto listApprovalDto = new ListApprovalDto();
                listApprovalDto.setId((Integer) data.get("approvalId"));
                listApprovalDto.setEmployeeCode((String) data.get("employeeCode"));
                listApprovalDto.setEmployeeName(Utils.upperCaseWords((String) data.get("employeeName")));
                listApprovalDto.setPositionName((String) data.get("positionName"));
                listApprovalDto.setProjectName((String) data.get("projectName"));
                listApprovalDto.setDateOT((Date) data.get("dateOt"));
                listApprovalDto.setPlanTimeOT((Integer) data.get("planTimeOt"));
                listApprovalDto.setApproveTimeOT((Integer) data.get("approveTimeOt"));
                results.add(listApprovalDto);
            }
        });
        return results;
    }

    /**
     * Sets the value for param and query.
     *
     * @param query the query
     * @param dto the dto
     * @param limit the limit
     * @param offset the offset
     */
    private static void setValueForParamAndQuery(Query query, ApprovalSearchRequestDto dto, Integer limit,
        Integer offset, boolean flag) {

        /* Set param total record, offset */
        if (limit != null && offset != null && flag) {
            query.setParameter("totalRecord", limit);
            query.setParameter("offset", offset);
        }

        /* Set project name */
        if (StringUtils.isNotEmpty(dto.getProjectName())) {
            query.setParameter("projectName", dto.getProjectName());
        }

        /* Set date time */
        if (StringUtils.isNotEmpty(dto.getRequestDate())) {
            query.setParameter("dateTime", dto.getRequestDate());
        }
    }

    /**
     * Count request with search setting.
     *
     * @param dto the dto
     * @return the int
     */
    @Override
    public int countRequestWithSearchSetting(ApprovalSearchRequestDto dto) {

        StringBuilder sql = new StringBuilder();
        boolean searchProjectName = !StringValidation.isNullOrEmpty(dto.getProjectName());
        boolean searchDate = !StringValidation.isNullOrEmpty(dto.getRequestDate());

        sql.append(" SELECT    COUNT( * ) AS count                                                      ");
        sql.append(" FROM ("                                                                             );
        sql.append("   SELECT approval.project_code,                                                    ");
        sql.append("          approval.position_code,                                                   ");
        sql.append("          approval.employee_code,                                                   ");
        sql.append("          DATE_FORMAT( approval.date_ot, '%Y-%m' )                                  ");
        sql.append(" FROM ot_time_approval approval                                                     ");
        sql.append("   INNER JOIN request request ON approval.request_id = request.id                   ");
        sql.append("   INNER JOIN project project ON project.project_code = approval.project_code       ");
        sql.append(" WHERE  1 = 1                                                                       ");
        sql.append("   AND request.STATUS = 3                                                           ");
        sql.append("   AND request.delete_flag = 0                                                      ");
        sql.append("   AND approval.delete_flag = 0                                                     ");
        sql.append("   AND project.delete_flag = 0                                                      ");
        // Binding data to condition when project name not null
        if (searchProjectName) {
            sql.append("AND ( project.project_name_jp = :projectName OR project.project_name_vn = :projectName )");
        }
        // Binding data to condition when date request not null
        if (searchDate) {
            sql.append(" AND DATE_FORMAT(approval.date_ot,'%Y-%m') = :dateTime                             ");
        }
        sql.append(" GROUP BY");
        sql.append("       approval.project_code,                                                       ");
        sql.append("       approval.position_code,                                                      ");
        sql.append("       approval.employee_code,                                                      ");
        sql.append("       DATE_FORMAT( approval.date_ot, '%Y-%m' )                                     ");
        sql.append(") result                                                                            ");
        
        Query query = entityManager.createNativeQuery(sql.toString());

        /* Execute query */
        setValueForParamAndQuery(query, dto, null, null, false);
        BigInteger result = (BigInteger) query.getSingleResult();

        return result.intValue();
    }

}
