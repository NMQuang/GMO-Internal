package jp.co.gzr_internal.api.service.dto.response;

// TODO: Auto-generated Javadoc
/**
 * The Class ListRequestResponseDto.
 */
public class EmployeeListResponseDto {

    /** The employee code. */
    private String employeeCode;

    /** The employee name. */
    private String employeeName;

    private String position;

    private String division;

    /**
     * Gets the employee code.
     *
     * @return the employeeCode
     */
    public String getEmployeeCode() {
        return employeeCode;
    }

    /**
     * Sets the employee code.
     *
     * @param employeeCode the employeeCode to set
     */
    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    /**
     * Gets the employee name.
     *
     * @return the employeeName
     */
    public String getEmployeeName() {
        return employeeName;
    }

    /**
     * Sets the employee name.
     *
     * @param employeeName the employeeName to set
     */
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }
    
    
}
