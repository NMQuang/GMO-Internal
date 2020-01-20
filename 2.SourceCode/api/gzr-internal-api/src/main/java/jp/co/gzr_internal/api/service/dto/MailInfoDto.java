/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Oct 01, 2019
 */
package jp.co.gzr_internal.api.service.dto;

import java.util.List;

/**
 * The Class MailInfoDto.
 */
public class MailInfoDto {
    
    /** The to name. */
    private List<String> toName;

    /** The owner. */
    private String owner;

    /** The project name. */
    private String projectName;
    
    /** The reason. */
    private String reason;

    /** The to. */
    private List<String> to;

    /** The to cc. */
    private List<String> toCc;

    /** The to bcc. */
    private List<String> toBcc;
    
    private String requestStatus;

    /**
     * @return the toName
     */
    public List<String> getToName() {
        return toName;
    }

    /**
     * @param toName the toName to set
     */
    public void setToName(List<String> toName) {
        this.toName = toName;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the to
     */
    public List<String> getTo() {
        return to;
    }

    /**
     * @param to the to to set
     */
    public void setTo(List<String> to) {
        this.to = to;
    }

    /**
     * @return the toCc
     */
    public List<String> getToCc() {
        return toCc;
    }

    /**
     * @param toCc the toCc to set
     */
    public void setToCc(List<String> toCc) {
        this.toCc = toCc;
    }

    /**
     * @return the toBcc
     */
    public List<String> getToBcc() {
        return toBcc;
    }

    /**
     * @param toBcc the toBcc to set
     */
    public void setToBcc(List<String> toBcc) {
        this.toBcc = toBcc;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the requestStatus
     */
    public String getRequestStatus() {
        return requestStatus;
    }

    /**
     * @param requestStatus the requestStatus to set
     */
    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }
}
