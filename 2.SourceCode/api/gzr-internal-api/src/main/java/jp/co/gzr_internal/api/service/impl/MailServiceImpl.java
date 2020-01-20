/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Oct 01, 2019
 */
package jp.co.gzr_internal.api.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import io.github.jhipster.config.JHipsterProperties;
import jp.co.gzr_internal.api.service.MailService;
import jp.co.gzr_internal.api.service.dto.AccountAfterLoginDto;
import jp.co.gzr_internal.api.service.dto.EmployeeDto;
import jp.co.gzr_internal.api.service.dto.MailInfoDto;
import jp.co.gzr_internal.api.service.dto.request.CreateProjectRequestDto;
import jp.co.gzr_internal.api.service.dto.request.MailSendRequestDto;
import jp.co.gzr_internal.api.web.rest.commons.Contants;
import jp.co.gzr_internal.api.web.rest.commons.MessageContants;
import jp.co.gzr_internal.api.web.rest.commons.ResponseCommon;
import jp.co.gzr_internal.api.web.rest.util.Utils;

/**
 * The Class MailServiceImpl.
 */
@Service
public class MailServiceImpl implements MailService {

    /** The log. */
    private final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    /** The j hipster properties. */
    private final JHipsterProperties jHipsterProperties;

    /** The java mail sender. */
    private final JavaMailSender javaMailSender;

    /** The template engine. */
    private final SpringTemplateEngine templateEngine;

    /**
     * Instantiates a new mail service.
     *
     * @param jHipsterProperties the j hipster properties
     * @param javaMailSender the java mail sender
     * @param messageSource the message source
     * @param templateEngine the template engine
     */
    public MailServiceImpl(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
        MessageSource messageSource, SpringTemplateEngine templateEngine) {
        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    /**
     * Send email.
     *
     * @param to the to
     * @param toCc the to cc
     * @param toBcc the to bcc
     * @param subject the subject
     * @param content the content
     * @param isHtml the is html
     */
    @Async
    private void sendEmail(String[] to, String[] toCc, String[] toBcc, String subject, String content, boolean isHtml) {

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
            message.setTo(to);
            if (toCc != null && toCc.length != 0) {
                message.setCc(toCc);
            }
            if (toBcc != null && toBcc.length != 0) {
                message.setBcc(toBcc);
            }
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);

            message.setText(content, isHtml);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.warn("Email could not be sent to user '{}'", to, e);
            } else {
                log.warn("Email could not be sent to user '{}': {}", to, e.getMessage());
            }
        }
    }

    /**
     * Send password reset mail.
     *
     * @param dto the dto
     * @param newPassword the new password
     * @param email the email
     * @throws Exception the exception
     */
    @Override
    @Async
    public void sendPasswordResetMail(final AccountAfterLoginDto dto, final String newPassword, final String email)
        throws Exception {
        Context context = new Context();
        context.setVariable(Contants.CONST_STR_EMPLOYEE_NAME, Utils.upperCaseWords(dto.getEmployeeName()));
        context.setVariable(Contants.CONST_STR_NEW_PASSWORD, newPassword);
        String content = templateEngine.process(Contants.CONST_STR_TEMPLATE_PASSWORD_RESET, context);
        String subject = Contants.CONST_STR_TITLE_PASSWORD_RESET;
        String[] toArr = new String[]{email};
        sendEmail(toArr, null, null, subject, content, true);
    }

    /**
     * Send request OT mail.
     *
     * @param mailInfo the mail info
     * @throws Exception the exception
     */
    @Override
    @Async
    public void sendRequestOTMail(MailInfoDto mailInfo) throws Exception {
        Context context = new Context();
        context.setVariable(Contants.CONST_STR_MAIL_INFO, mailInfo);
        String content = templateEngine.process(Contants.CONST_STR_TEMPLATE_REQUEST_OT, context);
        String subject = "[HCM] " + Contants.CONST_STR_TITLE_REQUEST_OT + mailInfo.getProjectName();

        if (mailInfo.getTo() == null) {
            mailInfo.setTo(new ArrayList<String>());
        }

        if (mailInfo.getToCc() == null) {
            mailInfo.setToCc(new ArrayList<String>());
        }

        sendEmail(mailInfo.getTo().toArray(new String[0]), mailInfo.getToCc().toArray(new String[0]), null, subject,
            content, true);
    }

    @Override
    @Async
    public void sendApprovalOTMail(MailInfoDto mailInfo) throws Exception {
        Context context = new Context();
        context.setVariable(Contants.CONST_STR_MAIL_INFO, mailInfo);
        String content = templateEngine.process(Contants.CONST_STR_TEMPLATE_APPROVAL_OT, context);
        String subject = "[HCM] " + Contants.CONST_STR_TITLE_APPROVAL_OT + mailInfo.getProjectName();

        if (mailInfo.getTo() == null) {
            mailInfo.setTo(new ArrayList<String>());
        }
        if (mailInfo.getToCc() == null) {
            mailInfo.setToCc(new ArrayList<String>());
        }

        sendEmail(mailInfo.getTo().toArray(new String[0]), mailInfo.getToCc().toArray(new String[0]), null, subject,
            content, true);
    }

    /**
     * Send email to.
     *
     * @param mailSendRequestDto the mail request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @Override
    @Async
    public ResponseEntity<?> sendEmailTo(MailSendRequestDto mailSendRequestDto) throws Exception {

        sendEmail(mailSendRequestDto.getEmailTo(), mailSendRequestDto.getEmailToCC(),
            mailSendRequestDto.getEmailToBcc(), mailSendRequestDto.getSubject(), mailSendRequestDto.getContent(), true);
        return new ResponseEntity<>(ResponseCommon.createResponse(null, MessageContants.SUCCESS_CODE), HttpStatus.OK);
    }

    @Override
    @Async
    public void sendCreateProjectMail(String to, CreateProjectRequestDto projectRequestDto,
        EmployeeDto projectManagement, EmployeeDto brSE, EmployeeDto teamLead, String projectRank) throws Exception {
        Context context = new Context();
        String projectName = Contants.CONST_STR_BLANK;

        if (!projectRequestDto.getProjectNameVN().isEmpty()) {
            projectName = projectRequestDto.getProjectNameVN();
        } else {
            projectName = projectRequestDto.getProjectNameJP();
        }

        context.setVariable("projectName", projectName);
        context.setVariable("projectNameJP", projectRequestDto.getProjectNameJP());
        context.setVariable("projectNameVN", projectRequestDto.getProjectNameVN());
        context.setVariable("buillableEffort", projectRequestDto.getBillableEffort());
        context.setVariable("startDate", projectRequestDto.getStartDate());

        if (!Contants.CONST_STR_BLANK.equals(projectRequestDto.getEndDate())) {
            context.setVariable("endDate", projectRequestDto.getEndDate());
        } else {
            context.setVariable("endDate", "N/A");
        }

        context.setVariable("customerName", projectRequestDto.getCustomerName());
        context.setVariable("sale", projectRequestDto.getSale());
        context.setVariable("projectManagement", Utils.upperCaseWords(projectManagement.getEmployeeName()));
        context.setVariable("brSE", Utils.upperCaseWords(brSE.getEmployeeName()));
        context.setVariable("teamLead", Utils.upperCaseWords(teamLead.getEmployeeName()));
        context.setVariable("projectRank", projectRank);
        context.setVariable("scope", projectRequestDto.getScope());
        context.setVariable("objectives", projectRequestDto.getObjectives());
        String content = templateEngine.process(Contants.CONST_STR_TEMPLATE_CREATE_PROJECT, context);
        String subject = "[HCM] Quyết định mở dự án <" + projectName + ">";
        String[] toCC = Utils.addX(projectRequestDto.getEmailCC().length, projectRequestDto.getEmailCC(), to);

        sendEmail(new String[]{to, projectManagement.getEmail(), brSE.getEmail(), teamLead.getEmail()}, toCC, null,
            subject, content, true);
    }

    @Override
    @Async
    public void sendVerifyOTMail(MailInfoDto mailInfo) throws Exception {
        Context context = new Context();
        context.setVariable(Contants.CONST_STR_MAIL_INFO, mailInfo);
        String content = templateEngine.process(Contants.CONST_STR_TEMPLATE_VERIFY_OT, context);
        String subject = "[HCM] " + Contants.CONST_STR_TITLE_VERIFY_OT + mailInfo.getProjectName();

        if (mailInfo.getTo() == null) {
            mailInfo.setTo(new ArrayList<String>());
        }

        if (mailInfo.getToCc() == null) {
            mailInfo.setToCc(new ArrayList<String>());
        }

        sendEmail(mailInfo.getTo().toArray(new String[0]), mailInfo.getToCc().toArray(new String[0]), null, subject,
            content, true);
    }
}
