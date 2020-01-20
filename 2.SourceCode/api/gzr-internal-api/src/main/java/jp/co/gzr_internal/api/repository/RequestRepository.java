/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 26, 2019 
 */
package jp.co.gzr_internal.api.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.gzr_internal.api.domain.Request;
import jp.co.gzr_internal.api.service.dto.RequestDto;

/**
 * The Interface RequestRepository.
 */
@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    /**
     * Select by request id.
     *
     * @param requestId the request id
     * @return the request detail respone dto
     */
    @Query(name = "selectByRequestId", nativeQuery = true)
    Optional<RequestDto> selectByRequestId(@Param("requestId") Integer requestId);

    /**
     * Update request.
     *
     * @param requestId the request id
     * @param projectName the project name
     * @param reason the reason
     * @param updateAt the update at
     * @param currentTime the current time
     * @return the int
     */
    @Query(name = "updateRequest")
    @Modifying
    int updateRequest(@Param("requestId") Integer requestId, @Param("projectCode") String projectCode,
        @Param("reason") String reason, @Param("updateAt") String updateAt,
        @Param("currentTime") LocalDateTime currentTime);

    /**
     * Update status by id and status required.
     *
     * @param requestId the request id
     * @param status the status
     * @param statusRequired the status required
     * @param updateAt the update at
     * @param string
     * @param currentTime the current time
     * @return the int
     */
    @Query(name = "updateStatusByIdAndStatusRequired")
    @Modifying
    int updateStatusByIdAndStatusRequired(@Param("requestId") Integer requestId, @Param("status") Integer status,
        @Param("statusRequired") Integer statusRequired, @Param("approver") String approver,
        @Param("updateAt") String updateAt, @Param("currentTime") LocalDateTime currentTime);

    /**
     * Update status by id.
     *
     * @param requestId the request id
     * @param status the status
     * @param updateAt the update at
     * @param currentTime the current time
     * @return the int
     */
    @Query(name = "updateStatusById")
    @Modifying
    int updateStatusById(@Param("requestId") Integer requestId, @Param("status") Integer status,
        @Param("approver") String approver, @Param("updateAt") String updateAt,
        @Param("currentTime") LocalDateTime currentTime);

    /**
     * Select project name.
     *
     * @return the list
     */
    @Query(name = "selectProjectName")
    List<String> selectProjectName();
}
