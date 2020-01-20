/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 25, 2019 
 */
package jp.co.gzr_internal.api.service.dto.request;

/**
 * The Class RequestDetailRequestDto.
 */
public class RequestDetailRequestDto
{
    
    /** The request detail id. */
    private String requestDetailId;
    
    /** The date OT. */
    private String dateOT;
    
    /** The employee code. */
    private String employeeCode;
    
    private String positionCode;
    
    /** The note. */
    private String note;
    
    /** The time OT. */
    private String planTimeOT;

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
     * @param note the new note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * @return the requestDetailId
     */
    public String getRequestDetailId() {
        return requestDetailId;
    }

    /**
     * @param requestDetailId the requestDetailId to set
     */
    public void setRequestDetailId(String requestDetailId) {
        this.requestDetailId = requestDetailId;
    }

    /**
     * @return the planTimeOT
     */
    public String getPlanTimeOT() {
        return planTimeOT;
    }

    /**
     * @param planTimeOT the planTimeOT to set
     */
    public void setPlanTimeOT(String planTimeOT) {
        this.planTimeOT = planTimeOT;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }
    
}
