/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Sep 27, 2019
 */
package jp.co.gzr_internal.api.repository.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import jp.co.gzr_internal.api.repository.ListRequestRepository;
import jp.co.gzr_internal.api.service.dto.ListRequestDto;
import jp.co.gzr_internal.api.service.dto.request.ListRequestRequestDto;
import jp.co.gzr_internal.api.web.rest.util.Utils;
import jp.co.gzr_internal.api.web.rest.validator.StringValidation;

/**
 * The Class ListRequestRepositoryImpl.
 */
@Repository
public class ListRequestRepositoryImpl implements ListRequestRepository
{
    /** The entity manager. */
    private final EntityManager entityManager;

    /**
     * Instantiates a new m user custom rpository impl.
     *
     * @param entityManager the entity manager
     */
    public ListRequestRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Find all request with search setting.
     *
     * @param dto the dto
     * @param limit the limit
     * @param offset the offset
     * @return the list
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ListRequestDto> findAllRequestWithSearchSetting(ListRequestRequestDto dto, Integer limit,
        Integer offset) {
        StringBuilder sql = new StringBuilder();
        List<ListRequestDto> results = new ArrayList<>();
        boolean searchProjectName = !StringValidation.isNullOrEmpty(dto.getProjectName());
        boolean searchStatus = !StringValidation.isNullOrEmpty(dto.getStatus());
        boolean searchDateRequest = !StringValidation.isNullOrEmpty(dto.getRequestOtDate());

        /*
         * Add query to get field want to get.
         * 
         * requestId : id of request
         * projectName : project name of request
         * employeeCode : employees code of request.
         * createAt : create time of request.
         * totalRequest : total request of request detail.
         * totalApproval : total approval of request detail.
         * status : status of request.
         * note : note of request.
         * 
         */
        sql.append("SELECT                                                                                                  ");
        sql.append("        result.requestId ,                                                                              ");
        sql.append("        result.projectCode ,                                                                            ");
        sql.append("        result.projectName ,                                                                            ");
        sql.append("        result.employeeCode ,                                                                           ");
        sql.append("        result.employeeName ,                                                                           ");
        sql.append("        result.createAt ,                                                                               ");
        sql.append("        result.updateAt ,                                                                               ");
        sql.append("        result.dateOt ,                                                                                 ");
        sql.append("        result.totalRequest ,                                                                           ");
        sql.append("        result.totalApproval ,                                                                          ");
        sql.append("        result.status ,                                                                                 ");
        sql.append("        result.note ,                                                                                   ");
        sql.append("        employee.employee_name as projectManagement                                                     ");
        sql.append("FROM    (                                                                                               ");
        sql.append("SELECT  detail_sum.request_id                                      AS requestId                        ,");
        sql.append("        request.project_code                           AS projectCode                                  ,");
        sql.append("        CASE                                                                                            ");
        sql.append(
            "            WHEN project.project_name_vn = '' AND project.project_name_jp <> '' THEN project.project_name_jp   ");
        sql.append(
            "            WHEN project.project_name_vn <> '' AND project.project_name_jp = '' THEN project.project_name_vn   ");
        sql.append("            ELSE  project.project_name_vn                                                               ");
        sql.append("        END AS projectName                                                                             ,");
        sql.append("        request.employee_code                           AS employeeCode                                ,");
        sql.append("        employee.employee_name                           AS employeeName                               ,");
        sql.append("        DATE_FORMAT( request.create_time, '%Y-%m-%d %T' )  AS createAt                                 ,");
        sql.append("        DATE_FORMAT( request.update_time, '%Y-%m-%d %T' )  AS updateAt                                 ,");
        sql.append("        detail_sum.dateOt                      AS dateOt                                               ,");
        sql.append("        detail_sum.totalRequest                      AS totalRequest                                   ,");
        sql.append("        detail_sum.totalApproval                  AS totalApproval                                      ,");
        sql.append("        request.status                                AS status                                         ,");
        sql.append("        request.note                                    AS note                                         ");
        sql.append("FROM                                                                                                    ");
        sql.append("        (                                                                                               ");
        sql.append("         SELECT                                                                                         ");
        sql.append("               detail.request_id                                                                        ,");
        sql.append("               DATE_FORMAT( detail.date_ot, '%Y-%m' )   AS dateOt                                       ,");
        sql.append("               SUM( detail.plan_time_ot )               AS totalRequest                                 ,");
        sql.append("               SUM( detail.approval_time_ot )           AS totalApproval                                ");
        sql.append("         FROM                                                                                           ");
        sql.append("              request_detail detail                                                                     ");
        sql.append("         WHERE     detail.delete_flag = 0                                                               ");
        // Binding data to condition when date request not null
        if (searchDateRequest) {
            sql.append(" and DATE_FORMAT( detail.date_ot, '%Y-%m' ) =:createTime                                            ");
        }
        sql.append("         GROUP BY                                                                                       ");
        sql.append("              detail.request_id                                                                         ,");
        sql.append("              DATE_FORMAT( detail.date_ot, '%Y-%m' )                                                    ");
        sql.append("         ) detail_sum                                                                                   ");
        sql.append("         INNER JOIN request request ON request.id = detail_sum.request_id                               ");
        sql.append("         INNER JOIN employees employee ON request.employee_code = employee.employee_code                ");
        sql.append("         INNER JOIN project project ON project.project_code = request.project_code                      ");
        sql.append("                                                                                                        ");
        sql.append(" WHERE    1=1                                                                                           ");
        sql.append(" AND request.delete_flag = 0                                                                            ");
        sql.append(" AND employee.delete_flag = 0                                                                           ");
        sql.append(" AND project.delete_flag = 0                                                                            ");
        // Binding data to condition when project name not null
        if (searchProjectName) {
            sql.append(" and (project.project_name_vn= :projectName  or project.project_name_jp= :projectName )             ");
        }

        // Binding data to condition when status not null
        if (searchStatus) {
            sql.append(" and request.status= :status                                                                        ");
        }
        sql.append("  ) result                                                                                              ");
        sql.append("   INNER JOIN project_detail detail ON detail.project_code = result.projectCode                         ");
        sql.append("   INNER JOIN employees employee ON detail.employee_code = employee.employee_code                       ");
        sql.append(" WHERE                                                                                                  ");
        sql.append("      detail.position_code = 3                                                                          ");
        sql.append("  AND detail.delete_flag = 0                                                                            ");
        sql.append("  AND employee.delete_flag = 0                                                                          ");
        sql.append("ORDER BY result.createAt DESC                                                                           ");
        sql.append("LIMIT :totalRecord  OFFSET :offset                                                                      ");
        Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);

        /* Execute query and get result list */
        setValueForParamAndQuery(query, dto, limit, offset);

        List<Tuple> resultData = query.getResultList();
        resultData.forEach(data -> {
            if (data != null) {
                ListRequestDto listRequestDto = new ListRequestDto();
                listRequestDto.setRequestId((Integer) data.get("requestId"));
                listRequestDto.setProjectCode((String) data.get("projectCode"));
                listRequestDto.setProjectName((String) data.get("projectName"));
                listRequestDto.setEmployeeCode((String) data.get("employeeCode"));
                listRequestDto.setCreateAt((String) data.get("createAt"));
                listRequestDto.setUpdaetAt((String) data.get("updateAt"));
                listRequestDto.setNote((String) data.get("note"));
                listRequestDto.setStatus((Integer) data.get("status"));
                listRequestDto.setEmployeeName(Utils.upperCaseWords((String) data.get("employeeName")));
                listRequestDto.setTotalRequest((BigDecimal) data.get("totalRequest"));
                listRequestDto.setTotalApproval((BigDecimal) data.get("totalApproval"));
                listRequestDto.setDateOt((String) data.get("dateOt"));
                listRequestDto.setProjectManagement(Utils.upperCaseWords(data.get("projectManagement").toString()));
                results.add(listRequestDto);
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
     * @return the list
     */
    private static void setValueForParamAndQuery(Query query, ListRequestRequestDto dto, Integer limit,
        Integer offset) {

        /*Set param total record, offset*/
        if (limit != null && offset != null) {
            query.setParameter("totalRecord", limit);
            query.setParameter("offset", offset);
        }

        /* Set project name */
        if (StringUtils.isNotEmpty(dto.getProjectName())) {
            query.setParameter("projectName", dto.getProjectName());
        }

        /* Set update time */
        if (StringUtils.isNotEmpty(dto.getRequestOtDate())) {
            query.setParameter("createTime", dto.getRequestOtDate());
        }

        /* Set status */
        if (StringUtils.isNotEmpty(dto.getStatus())) {
            query.setParameter("status", dto.getStatus());
        }
    }

    /**
     * Count request with search setting.
     *
     * @param dto the dto
     * @return the int
     */
    @Override
    public int countRequestWithSearchSetting(ListRequestRequestDto dto) {
        StringBuilder sql = new StringBuilder();
        boolean searchProjectName = !StringValidation.isNullOrEmpty(dto.getProjectName());
        boolean searchStatus = !StringValidation.isNullOrEmpty(dto.getStatus());
        boolean searchDateRequest = !StringValidation.isNullOrEmpty(dto.getRequestOtDate());
        sql.append("SELECT  COUNT(detail_sum.request_id)                               as count                             ");
        sql.append("FROM                                                                                                    ");
        sql.append("        (                                                                                               ");
        sql.append("         SELECT                                                                                         ");
        sql.append("               detail.request_id                                                                        ,");
        sql.append("               DATE_FORMAT( detail.date_ot, '%Y-%m' )   AS dateOt                                       ,");
        sql.append("               SUM( detail.plan_time_ot )               AS totalRequest                                 ,");
        sql.append("               SUM( detail.approval_time_ot )           AS totalApproval                                ");
        sql.append("         FROM                                                                                           ");
        sql.append("              request_detail detail                                                                     ");
        sql.append("         WHERE     detail.delete_flag = 0                                                               ");
        // Binding data to condition when date request not null
        if (searchDateRequest) {
            sql.append(" and DATE_FORMAT( detail.date_ot, '%Y-%m' ) =:createTime                                            ");
        }
        sql.append("         GROUP BY                                                                                       ");
        sql.append("              detail.request_id                                                                         ,");
        sql.append("              DATE_FORMAT( detail.date_ot, '%Y-%m' )                                                    ");
        sql.append("         ) detail_sum                                                                                   ");
        sql.append("         INNER JOIN request request ON request.id = detail_sum.request_id                               ");
        sql.append("         INNER JOIN employees employee ON request.employee_code = employee.employee_code                ");
        sql.append("         INNER JOIN project project ON project.project_code = request.project_code                      ");
        sql.append("                                                                                                        ");
        sql.append(" WHERE    1=1                                                                                           ");
        sql.append(" AND request.delete_flag = 0                                                                            ");
        sql.append(" AND employee.delete_flag = 0                                                                           ");
        sql.append(" AND project.delete_flag = 0                                                                            ");
        // Binding data to condition when project name not null
        if (searchProjectName) {
            sql.append(" and (project.project_name_vn= :projectName  or project.project_name_jp= :projectName )              ");
        }

        // Binding data to condition when status not null
        if (searchStatus) {
            sql.append(" and request.status= :status                                                                        ");
        }

        Query query = entityManager.createNativeQuery(sql.toString());

        /* Execute query */
        setValueForParamAndQuery(query, dto, null, null);
        BigInteger result = (BigInteger) query.getSingleResult();
        return result.intValue();
    }
    
    @Override
    public List<Integer> selectListDetailIdByRequestId(String requestId) {
        StringBuilder sql = new StringBuilder();
        List<Integer> results = new ArrayList<>();
        
        sql.append("SELECT  rd.id           as requestDetailId              ");
        sql.append("FROM  request_detail rd                                 ");
        sql.append("INNER JOIN request r ON rd.request_id=r.id              ");
        sql.append("WHERE   r.id=:requestId                                 ");
        sql.append("AND r.delete_flag <> 1 AND  rd.delete_flag <> 1         ");
        
        Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);
        query.setParameter("requestId", requestId);
        
        @SuppressWarnings("unchecked")
        List<Tuple> resultData = query.getResultList();
        resultData.forEach(data -> {
            if (data != null) {
                results.add((Integer) data.get("requestDetailId"));
            }
        });
        return results;
    }
}
