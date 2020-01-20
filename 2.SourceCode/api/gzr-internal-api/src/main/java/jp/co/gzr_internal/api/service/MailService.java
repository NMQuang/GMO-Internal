/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Oct 01, 2019
 */
package jp.co.gzr_internal.api.service;

import org.springframework.http.ResponseEntity;

import jp.co.gzr_internal.api.service.dto.AccountAfterLoginDto;
import jp.co.gzr_internal.api.service.dto.EmployeeDto;
import jp.co.gzr_internal.api.service.dto.MailInfoDto;
import jp.co.gzr_internal.api.service.dto.request.CreateProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.MailSendRequestDto;

/**
 * The interface MailService.
 */
public interface MailService {

    /**
     * Send password reset mail.
     *
     * @param dto the dto
     * @param newPassword the new password
     * @param email the email
     * @throws Exception the exception
     */
    void sendPasswordResetMail(final AccountAfterLoginDto dto, final String newPassword, final String email)
        throws Exception;

    /**
     * Send request OT mail.
     *
     * @param mailInfo the mail info
     * @throws Exception the exception
     */
    void sendRequestOTMail(final MailInfoDto mailInfo) throws Exception;
    
    void sendApprovalOTMail(final MailInfoDto mailInfo) throws Exception;

    void sendVerifyOTMail(final MailInfoDto mailInfo) throws Exception;
    /**
     * Send email to.
     *
     * @param mailSendRequestDto the mail request dto
     * @return the response entity
     * @throws Exception the exception
     */
    ResponseEntity<?> sendEmailTo(MailSendRequestDto mailSendRequestDto) throws Exception;

    void sendCreateProjectMail(final String to, final CreateProjectRequestDto projectRequestDto,
        final EmployeeDto projectManagement, final EmployeeDto brSE, final EmployeeDto teamLead, final String projectRank)
        throws Exception;

}
