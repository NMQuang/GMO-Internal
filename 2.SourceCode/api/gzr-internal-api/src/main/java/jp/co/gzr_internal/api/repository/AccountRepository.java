package jp.co.gzr_internal.api.repository;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.gzr_internal.api.domain.Account;
import jp.co.gzr_internal.api.service.dto.AccountAfterLoginDto;
import jp.co.gzr_internal.api.service.dto.AccountDto;

/**
 * Spring Data repository for the MCompany entity.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    /**
     * Select account info.
     *
     * @param email the email
     * @return the account after login dto
     */
    @Query(name = "selectAccountInfo", nativeQuery = true)
    AccountAfterLoginDto selectAccountInfo(@Param("email") String email);

    /**
     * Update last log out.
     *
     * @param employeeCode the employee code
     * @param currentDateTime the current date time
     * @return the integer
     */
    @Modifying
    @Query(name = "updateLastLogOut", nativeQuery = true)
    Integer updateLastLogOut(
        @Param("employeeCode") String employeeCode,
        @Param("currentDateTime") LocalDateTime currentDateTime);

    /**
     * Update last access date by employee code.
     *
     * @param employeeCode the employee code
     * @param currentDateTime the current date time
     * @return the int
     */
    @Query(name = "updateLastAccessDateByEmployeeCode", nativeQuery = true)
    @Modifying
    int updateLastAccessDateByEmployeeCode(
        @Param("employeeCode") String employeeCode,
        @Param("currentDateTime") LocalDateTime currentDateTime);

    /**
     * Gets the account with authorities.
     *
     * @param login the login
     * @return the account with authorities
     */
    @Query(name = "getAccountWithAuthorities", nativeQuery = true)
    AccountDto getAccountWithAuthorities(
        @Param("email") String login);

    /**
     * Update password for account.
     *
     * @param email the email
     * @param password the password
     * @param currentDateTime the current date time
     * @return the int
     */
    @Query(name = "updatePasswordForAccount", nativeQuery = true)
    @Modifying
    int updatePasswordForAccount(
        @Param("email") String email,
        @Param("password") String password, 
        @Param("currentDateTime") Date currentDateTime);

    /**
     * Select password by email.
     *
     * @param email the email
     * @return the string
     */
    @Query(name = "selectPasswordByEmail", nativeQuery = true)
    String selectPasswordByEmail(@Param("email") String email);
}
