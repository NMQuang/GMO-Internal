/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 26, 2019 
 */
package jp.co.gzr_internal.api.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jp.co.gzr_internal.api.domain.RequestDetail;
import jp.co.gzr_internal.api.service.dto.RequestDetailDto;

/**
 * The Interface RequestDetailRepository.
 */
@Repository
public interface RequestDetailRepository extends JpaRepository<RequestDetail, Integer> {

    /**
     * Insert request detail.
     *
     * @param requestId the request id
     * @param employeeCode the employee code
     * @param dateOT the date OT
     * @param timeOT the time OT
     * @param note the note
     * @param deleteFlag the delete flag
     * @param createTime the create time
     * @param updateTime the update time
     * @return the int
     */
    @Query(name = "insertRequestDetail", nativeQuery = true)
    @Modifying
    int insertRequestDetail(@Param("requestId") Integer requestId, @Param("employeeCode") String employeeCode,
        @Param("positionCode") Integer positionCode,
        @Param("dateOT") Date dateOT, @Param("timeOT") Integer timeOT, @Param("note") String note,
        @Param("deleteFlag") Integer deleteFlag, @Param("createTime") LocalDateTime createTime,
        @Param("updateTime") LocalDateTime updateTime);

    /**
     * Select detail by request id.
     *
     * @param requestId the request id
     * @return the list
     */
    @Query(name = "selectDetailByRequestId", nativeQuery = true)
    List<RequestDetailDto> selectDetailByRequestId(@Param("requestId") Integer requestId);

    @Query(name = "selectDetailSearchByRequestId", nativeQuery = true)
    List<RequestDetailDto> selectDetailSearchByRequestId(@Param("requestId") Integer requestId, @Param("dateSearch") String dateSearch);
    /**
     * Update request detail.
     *
     * @param id the id
     * @param dateOt the date ot
     * @param employeeCode the employee code
     * @param timeOT the time OT
     * @param note the note
     * @param currentTime the current time
     * @return the int
     */
    @Query(name = "updateRequestDetail")
    @Modifying
    int updateRequestDetail(
        @Param("id") Integer id,
        @Param("dateOt") Date dateOt,
        @Param("employeeCode") String employeeCode,
        @Param("timeOT") Integer timeOT,
        @Param("note") String note,
        @Param("currentTime") LocalDateTime currentTime);

    /**
     * Update approval time OT by id.
     *
     * @param id the id
     * @param approvalTimeOT the approval time OT
     * @param note the note
     * @param currentTime the current time
     * @return the int
     */
    @Query(name = "updateApprovalTimeOTById")
    @Modifying
    int updateApprovalTimeOTById(
        @Param("id")Integer id,
        @Param("approvalTimeOT") int approvalTimeOT,
        @Param("note") String note,
        @Param("currentTime") LocalDateTime currentTime);
    
    @Query(name = "deleteRequestDetail")
    @Modifying
    int deleteRequestDetail(@Param("id") Integer id, @Param("currentTime") LocalDateTime currentTime);
}
