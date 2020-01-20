/*
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 * 
 * Author: VietAnh
 * Creation Date : Sep 25, 2019
 */
package jp.co.gzr_internal.api.service.dto.request;

/**
 * The Class ApprovalDetailRequestDto.
 */
public class ApprovalDetailRequestDto
{
    /** The request detail id. */
    private String requestDetailId;

    /** The note. */
    private String note;

    /** The time OT. */
    private String approvalTimeOT;

    /** The employee code. */
    private String employeeCode;

    private String positionCode;
    
    /** The date OT. */
    private String dateOT;

    /**
     * Gets the employee code.
     *
     * @return the employee code
     */
    public String getEmployeeCode() {
        return employeeCode;
    }

    /**
     * Sets the employee code.
     *
     * @param employeeCode the new employee code
     */
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    /**
     * Gets the date OT.
     *
     * @return the date OT
     */
    public String getDateOT() {
        return dateOT;
    }

    /**
     * Sets the date OT.
     *
     * @param dateOT the new date OT
     */
    public void setDateOT(String dateOT) {
        this.dateOT = dateOT;
    }

    /**
     * Gets the request detail id.
     *
     * @return the requestDetailId
     */
    public String getRequestDetailId() {
        return requestDetailId;
    }

    /**
     * Sets the request detail id.
     *
     * @param requestDetailId the requestDetailId to set
     */
    public void setRequestDetailId(String requestDetailId) {
        this.requestDetailId = requestDetailId;
    }

    /**
     * Gets the note.
     *
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * Sets the note.
     *
     * @param note the note to set
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Gets the approval time OT.
     *
     * @return the approvalTimeOT
     */
    public String getApprovalTimeOT() {
        return approvalTimeOT;
    }

    /**
     * Sets the approval time OT.
     *
     * @param approvalTimeOT the approvalTimeOT to set
     */
    public void setApprovalTimeOT(String approvalTimeOT) {
        this.approvalTimeOT = approvalTimeOT;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }
    
}
