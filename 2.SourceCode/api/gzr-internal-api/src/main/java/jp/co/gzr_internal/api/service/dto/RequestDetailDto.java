/*  
 * COPYRIGHT(C) GMO-Z.com RUNSYSTEM
 *  
 * Author: VietAnh  
 * Creation Date : Sep 26, 2019 
 */
package jp.co.gzr_internal.api.service.dto;

/**
 * The Class RequestDetailDto.
 */
public class RequestDetailDto
{
    /** The request detail id. */
    private Integer requestDetailId;
    
    /** The date OT. */
    private String dateOT;

    /** The plan time ot. */
    private Integer planTimeOT;

    /** The actual time ot. */
    private Integer actualTimeOT;

    /** The approval time ot. */
    private Integer approvalTimeOT;

    /** The employee code. */
    private String employeeCode;
    
    private Integer positionCode;

    /** The note. */
    private String note;

    /**
     * Instantiates a new request detail dto.
     */
    public RequestDetailDto() {
        super();
    }

    public RequestDetailDto(Integer requestDetailId, String dateOT, Integer planTimeOT, Integer actualTimeOT,
        Integer approvalTimeOT, String employeeCode, Integer positionCode, String note) {
        super();
        this.requestDetailId = requestDetailId;
        this.dateOT = dateOT;
        this.planTimeOT = planTimeOT;
        this.actualTimeOT = actualTimeOT;
        this.approvalTimeOT = approvalTimeOT;
        this.employeeCode = employeeCode;
        this.positionCode = positionCode;
        this.note = note;
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
    public Integer getRequestDetailId() {
        return requestDetailId;
    }

    /**
     * @param requestDetailId the requestDetailId to set
     */
    public void setRequestDetailId(Integer requestDetailId) {
        this.requestDetailId = requestDetailId;
    }

    /**
     * @return the planTimeOT
     */
    public Integer getPlanTimeOT() {
        return planTimeOT;
    }

    /**
     * @param planTimeOT the planTimeOT to set
     */
    public void setPlanTimeOT(Integer planTimeOT) {
        this.planTimeOT = planTimeOT;
    }

    /**
     * @return the actualTimeOT
     */
    public Integer getActualTimeOT() {
        return actualTimeOT;
    }

    /**
     * @param actualTimeOT the actualTimeOT to set
     */
    public void setActualTimeOT(Integer actualTimeOT) {
        this.actualTimeOT = actualTimeOT;
    }

    /**
     * @return the approvalTimeOT
     */
    public Integer getApprovalTimeOT() {
        return approvalTimeOT;
    }

    /**
     * @param approvalTimeOT the approvalTimeOT to set
     */
    public void setApprovalTimeOT(Integer approvalTimeOT) {
        this.approvalTimeOT = approvalTimeOT;
    }


    public RequestDetailDto(Integer requestDetailId) {
        super();
        this.requestDetailId = requestDetailId;
    }

    public Integer getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(Integer positionCode) {
        this.positionCode = positionCode;
    }
    
}
