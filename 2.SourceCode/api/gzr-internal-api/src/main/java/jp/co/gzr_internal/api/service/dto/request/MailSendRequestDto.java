package jp.co.gzr_internal.api.service.dto.request;

/**
 * The Class MailFeedBackRequestDto.
 */
public class MailSendRequestDto
{
    
    /** The email to. */
    private String[] emailTo;
    
    /** The email to CC. */
    private String[] emailToCC;
    
    /** The email to bcc. */
    private String[] emailToBcc;
    
    /** The content. */
    private String content;
    
    /** The subject. */
    private String subject;
    
    
    /**
     * Gets the email to.
     *
     * @return the email to
     */
    public String[] getEmailTo() {
        return emailTo;
    }
    
    /**
     * Sets the email to.
     *
     * @param emailTo the new email to
     */
    public void setEmailTo(String[] emailTo) {
        this.emailTo = emailTo;
    }
    
    /**
     * Gets the email to CC.
     *
     * @return the email to CC
     */
    public String[] getEmailToCC() {
        return emailToCC;
    }
    
    /**
     * Sets the email to CC.
     *
     * @param emailToCC the new email to CC
     */
    public void setEmailToCC(String[] emailToCC) {
        this.emailToCC = emailToCC;
    }
    
    /**
     * Gets the email to bcc.
     *
     * @return the email to bcc
     */
    public String[] getEmailToBcc() {
        return emailToBcc;
    }
    
    /**
     * Sets the email to bcc.
     *
     * @param emailToBcc the new email to bcc
     */
    public void setEmailToBcc(String[] emailToBcc) {
        this.emailToBcc = emailToBcc;
    }
    
    /**
     * Gets the content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }
    
    /**
     * Sets the content.
     *
     * @param content the new content
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    /**
     * Gets the subject.
     *
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }
    
    /**
     * Sets the subject.
     *
     * @param subject the new subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
}
