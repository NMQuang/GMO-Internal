/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Otc 22, 2019
 */
package jp.co.gzr_internal.api.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.gzr_internal.api.domain.OTTimeApproval;
import jp.co.gzr_internal.api.service.dto.OTTimeApprovalDto;

/**
 * The Interface OTTimeApprovalRepository.
 */
@Repository
public interface OTTimeApprovalRepository extends JpaRepository<OTTimeApproval, Integer>
{
    /**
     * Select by request id.
     *
     * @param dateOT the date OT
     * @param employeeCode the employee code
     * @param projectName the project name
     * @return the optional
     */
    @Query(name = "selectApprovalByRequestId", nativeQuery = true)
    Optional<OTTimeApprovalDto> selectByRequestId(
        @Param("dateOT") String dateOT, 
        @Param("employeeCode") String employeeCode, 
        @Param("projectCode") String projectCode,
        @Param("positionCode") String positionCode);
    
    /**
     * Update status by condition.
     *
     * @param requestId the request id
     * @param approveTimeOt the approve time ot
     * @param projectName the project name
     * @param employeeCode the employee code
     * @param dateOt the date ot
     * @param currentTime the current time
     * @return the int
     */
    @Query(name = "updateOTTimeApproval")
    @Modifying
    int updateStatusByCondition(
        @Param("requestId")Integer requestId,
        @Param("approveTimeOt") Integer approveTimeOt,
        @Param("projectCode") String projectCode,
        @Param("employeeCode") String employeeCode,
        @Param("dateOt") Date dateOt,
        @Param("currentTime") LocalDateTime currentTime,
        @Param("positionCode") String positionCode);
    
    @Query(name = "updateUnApproval")
    @Modifying
    int updateUnApproval(
        @Param("requestId")Integer requestId,
        @Param("projectCode") String projectCode,
        @Param("employeeCode") String employeeCode,
        @Param("dateOt") Date dateOt,
        @Param("currentTime") LocalDateTime currentTime,
        @Param("positionCode") String positionCode);
    /**
     * Insert OT time approval.
     *
     * @param requestId the request id
     * @param employeeCode the employee code
     * @param projectName the project name
     * @param dateOT the date OT
     * @param approvalTimeOT the approval time OT
     * @param createTime the create time
     * @param updateTime the update time
     * @return the int
     */
    @Query(name = "insertOTTimeApproval")
    @Modifying
    int insertOTTimeApproval(@Param("requestId") Integer requestId, @Param("employeeCode") String employeeCode,@Param("projectCode") String projectCode,
        @Param("positionCode") String positionCode, @Param("dateOT") Date dateOT, @Param("approvalTimeOT") Integer approvalTimeOT, @Param("createTime") LocalDateTime createTime,
        @Param("updateTime") LocalDateTime updateTime);
}   
