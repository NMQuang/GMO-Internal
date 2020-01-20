package jp.co.gzr_internal.api.repository.impl;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import jp.co.gzr_internal.api.repository.CustomEmployeeRepository;
import jp.co.gzr_internal.api.service.dto.EmployeeDto;
import jp.co.gzr_internal.api.service.dto.EmployeeOfProjectDto;
import jp.co.gzr_internal.api.service.dto.request.DetailEmployeeRequestDto;
import jp.co.gzr_internal.api.service.dto.request.SearchEmployeeRequestDto;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.util.Utils;

@Repository
public class CustomEmployeeRepositoryImpl implements CustomEmployeeRepository {

    /** The entity manager. */
    private final EntityManager entityManager;

    /**
     * Instantiates a new custom SMS push message repository impl.
     *
     * @param entityManager the entity manager
     */
    public CustomEmployeeRepositoryImpl(EntityManager entityManager) {
        super();
        this.entityManager = entityManager;
    }

    @Override
    public BigInteger countEmployeeByCode(String employeeCode) {
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT  COUNT(employee.id)                                                         ");
        sql.append("FROM    employees employee                                                         ");
        sql.append("WHERE   employee.employee_code = :employeeCode                                      ");
        // Create native query
        Query query = entityManager.createNativeQuery(sql.toString());
        // Set value for parameters update
        query.setParameter("employeeCode", employeeCode);

        // Execute query and get result list
        BigInteger result = (BigInteger) query.getSingleResult();
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EmployeeDto> getListEmployee(SearchEmployeeRequestDto requestDto) {
        // Initial variable
        List<EmployeeDto> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        // If flag is search, it will get all field in table
        // If flag is export, it will get all field from hashmap
        if (StringUtils.equals(requestDto.getSearchOrExportFlag(), Contants.CONST_STR_OPTION_SEARCH)) {
            sql.append("SELECT  employee.id                                     as id                       ,");
            sql.append("        employee.employee_code                          as employeeCode             ,");
            sql.append("        employee.employee_name                          as employeeName             ,");
            sql.append("        employee.email                                  as email                    ,");
            sql.append("        employee.gender                                 as gender                   ,");
            sql.append("        employee.address                                as address                  ,");
            sql.append("        employee.phone_number                           as phoneNumber              ,");
            sql.append("        employee.birthday                               as birthday                 ,");
            sql.append("        employee.birthplace                             as birthplace               ,");
            sql.append("        employee.province_pr                            as provincePr               ,");
            sql.append("        employee.district_pr                            as districtPr               ,");
            sql.append("        employee.ward_pr                                as wardPr                   ,");
            sql.append("        employee.nation_ca                              as nationCa                 ,");
            sql.append("        employee.district_ca                            as districtCa               ,");
            sql.append("        employee.ward_ca                                as wardCa                   ,");
            sql.append("        employee.probationary_day                       as probationaryDay          ,");
            sql.append("        employee.official_day                           as officialDay              ,");
            sql.append("        employee.work_location                          as workLocation             ,");
            sql.append("        type.type_contact_name                          as typeContract             ,");
            sql.append("        status.unit_name                                as status                   ,");
            sql.append("        employee.reason_leave                           as reasonLeave              ,");
            sql.append("        employee.day_off                                as dayOff                   ,");
            sql.append("        employee.create_time                            as createTime               ,");
            sql.append("        employee.update_time                            as updateTime               ,");
            sql.append("        division.division_name                          as division                 ,");
            sql.append("        position.position_name                          as position                 ,");

        } else if (StringUtils.equals(requestDto.getSearchOrExportFlag(), Contants.CONST_STR_OPTION_EXPORT)) {

            sql.append("SELECT                                                                               ");
            // If flag is export all column, it will get all field in table
            // If flag is export specified column, it will get specified field in table
            if (StringUtils.equals(requestDto.getHeaderOptionFlag(), Contants.CONST_STR_OPTION_EXPORT_ALL_COLUMN)) {
                // Loop linkedHashMap header
                Contants.CONST_STR_HEADER.entrySet().forEach(entry -> {

                    // Add alias table
                    if (StringUtils.equals(entry.getValue().get(0), "type_contact_name")) {
                        sql.append("        type.               ");
                    } else if (StringUtils.equals(entry.getValue().get(0), "unit_name")) {
                        sql.append("        status.             ");
                    } else if (StringUtils.equals(entry.getValue().get(0), "division_name")) {
                        sql.append("        division.           ");
                    } else if (StringUtils.equals(entry.getValue().get(0), "position_name")) {
                        sql.append("        position.           ");
                    } else {
                        sql.append("        employee.           ");
                    }
                    // Add column of table
                    sql.append(entry.getValue().get(0));
                    sql.append("        as                      ");
                    sql.append(entry.getValue().get(1));
                    sql.append("        ,                       ");
                });
            } else if (StringUtils.equals(requestDto.getHeaderOptionFlag(),
                Contants.CONST_STR_OPTION_EXPORT_SPECIFIED_COLUMN)) {
                // Loop linkedHashMap header
                Contants.CONST_STR_HEADER.entrySet().forEach(entry -> {
                    if (requestDto.getHeaderList().contains(entry.getKey())) {
                        // Add alias table
                        if (StringUtils.equals(entry.getValue().get(0), "type_contact_name")) {
                            sql.append("        type.               ");
                        } else if (StringUtils.equals(entry.getValue().get(0), "unit_name")) {
                            sql.append("        status.             ");
                        } else if (StringUtils.equals(entry.getValue().get(0), "division_name")) {
                            sql.append("        division.           ");
                        } else if (StringUtils.equals(entry.getValue().get(0), "position_name")) {
                            sql.append("        position.           ");
                        } else {
                            sql.append("        employee.           ");
                        }
                        // Add column of table
                        sql.append(entry.getValue().get(0));
                        sql.append("        as                      ");
                        sql.append(entry.getValue().get(1));
                        sql.append("        ,                       ");
                    }
                });
            }

        }

        sql.append("        employee.delete_flag                            as deleteFlag                ");
        sql.append("FROM    employees employee                                                           ");
        sql.append("LEFT JOIN    m_division division ON employee.division_id = division.id               ");
        sql.append("LEFT JOIN    m_position position ON employee.position_id = position.id               ");
        sql.append("LEFT JOIN    m_type_contact type ON employee.type_contract = type.id                 ");
        sql.append("LEFT JOIN    m_status status     ON employee.status = status.id                      ");
        sql.append("WHERE   employee.delete_flag <> '1'                                                  ");
        sql.append("AND     division.delete_flag <> '1'                                                  ");
        sql.append("AND     position.delete_flag <> '1'                                                  ");
        sql.append("AND     type.delete_flag <> '1'                                                      ");
        sql.append("AND     status.delete_flag <> '1'                                                    ");

        // Add condition
        addConditionForQuery(sql, requestDto);

        sql.append("LIMIT   :offset, :limit                                                              ");

        // Create native query
        Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);

        // Add parameter
        addParameterForQuery(query, requestDto);

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
                EmployeeDto employeeDto = new EmployeeDto();

                // If flag is search, it will get all field in table
                // If flag is export, it will get all field from hashmap
                if (StringUtils.equals(requestDto.getSearchOrExportFlag(), Contants.CONST_STR_OPTION_SEARCH)) {
                    employeeDto.setId((Integer) data.get("id"));
                    employeeDto.setEmployeeCode((String) data.get("employeeCode"));
                    employeeDto.setEmployeeName((String) data.get("employeeName"));
                    employeeDto.setEmail((String) data.get("email"));
                    employeeDto.setGender((Integer) data.get("gender"));
                    employeeDto.setAddress((String) data.get("address"));
                    employeeDto.setPhoneNumber((String) data.get("phoneNumber"));
                    employeeDto.setBirthday((String)data.get("birthday").toString());
                    employeeDto.setBirthplace((String) data.get("birthplace"));
                    employeeDto.setProvincePr((String) data.get("provincePr"));
                    employeeDto.setDistrictPr((String) data.get("districtPr"));
                    employeeDto.setWardPr((String) data.get("wardPr"));
                    employeeDto.setNationCa((String) data.get("nationCa"));
                    employeeDto.setDistrictCa((String) data.get("districtCa"));
                    employeeDto.setWardCa((String) data.get("wardCa"));
                    employeeDto.setProbationaryDay((String)data.get("probationaryDay").toString());
                    employeeDto.setOfficialDay((String)data.get("officialDay").toString());
                    employeeDto.setWorkLocation((String) data.get("workLocation"));
                    employeeDto.setTypeContract((String) data.get("typeContract"));
                    employeeDto.setStatus((String) data.get("status"));
                    employeeDto.setReasonLeave((String) data.get("reasonLeave"));
                    employeeDto.setDayOff((String) data.get("dayOff"));
                    employeeDto.setDeleteFlag((Integer) data.get("deleteFlag"));
                    employeeDto.setCreateTime((String)data.get("createTime").toString());
                    employeeDto.setUpdateTime((String)data.get("updateTime").toString());
                    employeeDto.setDivision((String) data.get("division"));
                    employeeDto.setPosition((String) data.get("position"));
                } else if (StringUtils.equals(requestDto.getSearchOrExportFlag(), Contants.CONST_STR_OPTION_EXPORT)) {
                    // If flag is export all column, it will get all field in table
                    // If flag is export specified column, it will get specified field in table
                    if (StringUtils.equals(requestDto.getHeaderOptionFlag(), Contants.CONST_STR_OPTION_EXPORT_ALL_COLUMN)) {
                        employeeDto.setEmployeeCode((String) data.get("employeeCode"));
                        employeeDto.setEmployeeName((String) data.get("employeeName"));
                        employeeDto.setEmail((String) data.get("email"));
                        employeeDto.setGender((Integer) data.get("gender"));
                        employeeDto.setAddress((String) data.get("address"));
                        employeeDto.setPhoneNumber((String) data.get("phoneNumber"));
                        employeeDto.setBirthday((String)data.get("birthday").toString());
                        employeeDto.setBirthplace((String) data.get("birthplace"));
                        employeeDto.setProvincePr((String) data.get("provincePr"));
                        employeeDto.setDistrictPr((String) data.get("districtPr"));
                        employeeDto.setWardPr((String) data.get("wardPr"));
                        employeeDto.setNationCa((String) data.get("nationCa"));
                        employeeDto.setDistrictCa((String) data.get("districtCa"));
                        employeeDto.setWardCa((String) data.get("wardCa"));
                        employeeDto.setProbationaryDay((String)data.get("probationaryDay").toString());
                        employeeDto.setOfficialDay((String)data.get("officialDay").toString());
                        employeeDto.setWorkLocation((String) data.get("workLocation"));
                        employeeDto.setTypeContract((String) data.get("typeContract"));
                        employeeDto.setStatus((String) data.get("status"));
                        employeeDto.setReasonLeave((String) data.get("reasonLeave"));
                        employeeDto.setDayOff((String) data.get("dayOff"));
                        employeeDto.setDeleteFlag((Integer) data.get("deleteFlag"));
                        employeeDto.setDivision((String) data.get("division"));
                        employeeDto.setPosition((String) data.get("position"));
                    } else if (StringUtils.equals(requestDto.getHeaderOptionFlag(),
                        Contants.CONST_STR_OPTION_EXPORT_SPECIFIED_COLUMN)) {
                        Class<?> clazz = employeeDto.getClass();
                        Contants.CONST_STR_HEADER.entrySet().forEach(entry -> {
                            if (requestDto.getHeaderList().contains(entry.getKey())) {
                                
                                try {
                                    Field field = clazz.getDeclaredField(entry.getValue().get(1));
                                    field.setAccessible(true);
                                    if (StringUtils.equals(entry.getValue().get(1), "gender") || StringUtils.equals(entry.getValue().get(1), "deleteFlag")) {
                                        field.set(employeeDto, (Integer) data.get(entry.getValue().get(1)));
                                    } else {
                                        field.set(employeeDto, (String) data.get(entry.getValue().get(1)).toString());
                                    }
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                } 
                            }
                        });
                    }
                }

                results.add(employeeDto);
            }
        });
        return results;
    }

    @Override
    public BigInteger countEmployee(SearchEmployeeRequestDto requestDto) {
        // Initial variable
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT  COUNT(employee.id)                                                         ");
        sql.append("FROM    employees employee                                                         ");
        sql.append("WHERE   employee.delete_flag <> '1'                                                ");

        // Add condition
        addConditionForQuery(sql, requestDto);
        // Create native query
        Query query = entityManager.createNativeQuery(sql.toString());

        // Add parameter
        addParameterForQuery(query, requestDto);

        // Execute query and get result list
        BigInteger result = (BigInteger) query.getSingleResult();

        return result;
    }

    private void addConditionForQuery(StringBuilder updateSql, SearchEmployeeRequestDto dto) {
        // If employee code exists, it will add search conditions with employee code
        if (!StringUtils.isEmpty(dto.getEmployeeCode())) {
            updateSql.append("  AND   employee.employee_code        LIKE :employeeCode                       ");
        }

        // If employee name exists, it will add search conditions with phone name
        if (!StringUtils.isEmpty(dto.getEmployeeName())) {
            updateSql.append("  AND   employee.employee_name        LIKE :employeeName                       ");
        }

        // If birthday exists, it will add search conditions with birthday
        if (!StringUtils.isEmpty(dto.getBirthday())) {
            updateSql.append("  AND   employee.birthday        LIKE :birthday                                 ");
        }

        // If position exists, it will add search conditions with position
        if (!StringUtils.isEmpty(dto.getPosition())) {
            updateSql.append("  AND   employee.position_id        LIKE :position                                ");
        }

        // If division_id exists, it will add search conditions with division_id
        if (!StringUtils.isEmpty(dto.getDivision())) {
            updateSql.append("  AND   employee.division_id        LIKE :division                                ");
        }

        // If probationary_day exists, it will add search conditions with probationary_day
        if (!StringUtils.isEmpty(dto.getProbationaryDay())) {
            updateSql.append("  AND   employee.probationary_day        LIKE :probationaryDay                    ");
        }

        // If official_day exists, it will add search conditions with official_day
        if (!StringUtils.isEmpty(dto.getOfficialDay())) {
            updateSql.append("  AND   employee.official_day        LIKE :officialDay                            ");
        }

        // If type_contract exists, it will add search conditions with type_contract
        if (!StringUtils.isEmpty(dto.getWorkContact())) {
            updateSql.append("  AND   employee.type_contract        LIKE :workContact                           ");
        }

        // If status exists, it will add search conditions with status
        if (!StringUtils.isEmpty(dto.getStatus())) {
            updateSql.append("  AND   employee.status        LIKE :status                                       ");
        }
    }

    private void addParameterForQuery(Query query, SearchEmployeeRequestDto dto) {
        // If employee code exists, it will add search conditions with employee code
        if (!StringUtils.isEmpty(dto.getEmployeeCode())) {
            query.setParameter("employeeCode",
                Contants.CONST_STR_PERCENT_SIGN + dto.getEmployeeCode().trim() + Contants.CONST_STR_PERCENT_SIGN);
        }

        // If employee name exists, it will add search conditions with employee name
        if (!StringUtils.isEmpty(dto.getEmployeeName())) {
            query.setParameter("employeeName",
                Contants.CONST_STR_PERCENT_SIGN + dto.getEmployeeName().trim() + Contants.CONST_STR_PERCENT_SIGN);
        }

        // If birthday exists, it will add search conditions with birthday
        if (!StringUtils.isEmpty(dto.getBirthday())) {
            query.setParameter("birthday", dto.getBirthday());
        }

        // If position exists, it will add search conditions with position
        if (!StringUtils.isEmpty(dto.getPosition())) {
            query.setParameter("position", dto.getPosition());
        }

        // If division_id exists, it will add search conditions with division_id
        if (!StringUtils.isEmpty(dto.getDivision())) {
            query.setParameter("division", dto.getDivision());
        }

        // If probationary_day exists, it will add search conditions with probationary_day
        if (!StringUtils.isEmpty(dto.getProbationaryDay())) {
            query.setParameter("probationaryDay", dto.getProbationaryDay());
        }

        // If official_day exists, it will add search conditions with official_day
        if (!StringUtils.isEmpty(dto.getOfficialDay())) {
            query.setParameter("officialDay", dto.getOfficialDay());
        }

        // If type_contract exists, it will add search conditions with type_contract
        if (!StringUtils.isEmpty(dto.getWorkContact())) {
            query.setParameter("workContact", dto.getWorkContact());
        }

        // If status exists, it will add search conditions with status
        if (!StringUtils.isEmpty(dto.getStatus())) {
            query.setParameter("status", dto.getStatus());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EmployeeDto> getDetailEmployee(DetailEmployeeRequestDto employeeRequest) {
     // Initial variable
        List<EmployeeDto> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  employee.id                                     as id                       ,");
        sql.append("        employee.employee_code                          as employeeCode             ,");
        sql.append("        employee.employee_name                          as employeeName             ,");
        sql.append("        employee.email                                  as email                    ,");
        sql.append("        employee.gender                                 as gender                   ,");
        sql.append("        employee.address                                as address                  ,");
        sql.append("        employee.phone_number                           as phoneNumber              ,");
        sql.append("        employee.birthday                               as birthday                 ,");
        sql.append("        employee.birthplace                             as birthplace               ,");
        sql.append("        employee.province_pr                            as provincePr               ,");
        sql.append("        employee.district_pr                            as districtPr               ,");
        sql.append("        employee.ward_pr                                as wardPr                   ,");
        sql.append("        employee.nation_ca                              as nationCa                 ,");
        sql.append("        employee.district_ca                            as districtCa               ,");
        sql.append("        employee.ward_ca                                as wardCa                   ,");
        sql.append("        employee.probationary_day                       as probationaryDay          ,");
        sql.append("        employee.official_day                           as officialDay              ,");
        sql.append("        employee.work_location                          as workLocation             ,");
        sql.append("        type.type_contact_name                          as typeContract             ,");
        sql.append("        status.unit_name                                as status                   ,");
        sql.append("        employee.reason_leave                           as reasonLeave              ,");
        sql.append("        employee.day_off                                as dayOff                   ,");
        sql.append("        employee.create_time                            as createTime               ,");
        sql.append("        employee.update_time                            as updateTime               ,");
        sql.append("        division.division_name                          as division                 ,");
        sql.append("        position.position_name                          as position                 ,");
        sql.append("        employee.delete_flag                            as deleteFlag                ");
        sql.append("FROM    employees employee                                                           ");
        sql.append("LEFT JOIN    m_division division ON employee.division_id = division.id               ");
        sql.append("LEFT JOIN    m_position position ON employee.position_id = position.id               ");
        sql.append("LEFT JOIN    m_type_contact type ON employee.type_contract = type.id                 ");
        sql.append("LEFT JOIN    m_status status     ON employee.status = status.id                      ");
        sql.append("WHERE   employee.delete_flag <> '1'                                                  ");
        sql.append("AND     employee.employee_code = :employeeCode                                       ");
        sql.append("AND     division.delete_flag <> '1'                                                  ");
        sql.append("AND     position.delete_flag <> '1'                                                  ");
        sql.append("AND     type.delete_flag <> '1'                                                      ");
        sql.append("AND     status.delete_flag <> '1'                                                    ");
        
     // Create native query
        Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);
     // Set parameter limit
        query.setParameter("employeeCode", employeeRequest.getEmployeeCode());
        
     // Execute query and get result list
        List<Tuple> resultData = query.getResultList();
        resultData.forEach(data -> {
            if (data != null) {
                EmployeeDto employeeDto = new EmployeeDto();
                employeeDto.setId((Integer) data.get("id"));
                employeeDto.setEmployeeCode((String) data.get("employeeCode"));
                employeeDto.setEmployeeName((String) data.get("employeeName"));
                employeeDto.setEmail((String) data.get("email"));
                employeeDto.setGender((Integer) data.get("gender"));
                employeeDto.setAddress((String) data.get("address"));
                employeeDto.setPhoneNumber((String) data.get("phoneNumber"));
                employeeDto.setBirthday((String)data.get("birthday").toString());
                employeeDto.setBirthplace((String) data.get("birthplace"));
                employeeDto.setProvincePr((String) data.get("provincePr"));
                employeeDto.setDistrictPr((String) data.get("districtPr"));
                employeeDto.setWardPr((String) data.get("wardPr"));
                employeeDto.setNationCa((String) data.get("nationCa"));
                employeeDto.setDistrictCa((String) data.get("districtCa"));
                employeeDto.setWardCa((String) data.get("wardCa"));
                employeeDto.setProbationaryDay((String)data.get("probationaryDay").toString());
                employeeDto.setOfficialDay((String)data.get("officialDay").toString());
                employeeDto.setWorkLocation((String) data.get("workLocation"));
                employeeDto.setTypeContract((String) data.get("typeContract"));
                employeeDto.setStatus((String) data.get("status"));
                employeeDto.setReasonLeave((String) data.get("reasonLeave"));
                employeeDto.setDayOff((String) data.get("dayOff"));
                employeeDto.setDeleteFlag((Integer) data.get("deleteFlag"));
                employeeDto.setCreateTime((String)data.get("createTime").toString());
                employeeDto.setUpdateTime((String)data.get("updateTime").toString());
                employeeDto.setDivision((String) data.get("division"));
                employeeDto.setPosition((String) data.get("position"));
                results.add(employeeDto);
            }
        });
        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<EmployeeOfProjectDto> getListEmployeeByProjectCode(String projectCode) {
     // Initial variable
        List<EmployeeOfProjectDto> results = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  result.employeeCode                           as employeeCode               ,");
        sql.append("        result.employeeName                           as employeeName                ");
        sql.append("FROM    (                                                                          ");
        sql.append("SELECT  employee.employee_code                           as employeeCode           ,");
        sql.append("        employee.employee_name                           as employeeName            ");
        sql.append("FROM                                                                                ");
        sql.append("        project_detail detail                                                       ");
        sql.append("INNER JOIN                                                                          ");
        sql.append("        employees employee ON detail.employee_code = employee.employee_code         ");
        sql.append("WHERE                                                                               ");
        sql.append("            detail.delete_flag = 0   AND employee.delete_flag = 0                   ");
        sql.append("        AND detail.project_code = :projectCode                                    ");
        sql.append("        ) result                                                                   ");
        sql.append("GROUP BY  result.employeeCode, result.employeeName                                   ");

        // Create native query
        Query query = entityManager.createNativeQuery(sql.toString(), Tuple.class);

        // Set parameter limit
        query.setParameter("projectCode", projectCode);
        // Execute query and get result list
        List<Tuple> resultData = query.getResultList();
        resultData.forEach(data -> {
            if (data != null) {
                EmployeeOfProjectDto employeeDto = new EmployeeOfProjectDto();
                employeeDto.setEmployeeCode(data.get("employeeCode").toString());
                employeeDto.setEmployeeName(Utils.upperCaseWords(data.get("employeeName").toString()));
                results.add(employeeDto);
            }
        });

        return results;
    }

   

}
