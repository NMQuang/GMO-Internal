/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 26, 2019 
 */
package jp.co.gzr_internal.api.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jp.co.gzr_internal.api.service.MailService;
import jp.co.gzr_internal.api.service.dto.request.MailSendRequestDto;

/**
 * The Class RequestResource.
 */
@RestController
@RequestMapping("api")
public class SendMailResource
{
    /** The mail service. */
    private final MailService mailService;
    
    /**
     * Instantiates a new employee resource.
     *
     * @param mailService the mail service
     */
    public SendMailResource(MailService mailService) {
        this.mailService = mailService;
    }
    
    /**
     * List request.
     *
     * @param mailSendRequestDto the mail request dto
     * @return the response entity
     * @throws Exception the exception
     */
    @PostMapping(path = "/email/send")
    public ResponseEntity<?> listRequest( @RequestBody MailSendRequestDto mailSendRequestDto)
        throws Exception {
        return mailService.sendEmailTo(mailSendRequestDto);
    }

}