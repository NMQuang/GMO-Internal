package jp.co.gzr_internal.api.service.dto.response;

import java.util.List;

import jp.co.gzr_internal.api.service.dto.ProjectAfterLoginDto;
import jp.co.gzr_internal.api.web.rest.UserJWTController.JWTToken;

/**
 * The Class MCompanyDetailResponseDto.
 */
public class LoginResponseDto {

    /** The comp id. */
    private String employeeCode;

    /** The jwt. */
    private JWTToken jwt;

    /** The comp name. */
    private String employeeName;

    /** The cd name. */
    private Integer roleId;

    private List<ProjectAfterLoginDto> projectInfo;

    /**
     * Instantiates a new m company detail response dto.
     */
    public LoginResponseDto() {
    }

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
     * Gets the jwt.
     *
     * @return the jwt
     */
    public JWTToken getJwt() {
        return jwt;
    }

    /**
     * Sets the jwt.
     *
     * @param jwt the jwt to set
     */
    public void setJwt(JWTToken jwt) {
        this.jwt = jwt;
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

    /**
     * Gets the role id.
     *
     * @return the roleId
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * Sets the role id.
     *
     * @param roleId the roleId to set
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<ProjectAfterLoginDto> getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(List<ProjectAfterLoginDto> projectInfo) {
        this.projectInfo = projectInfo;
    }

}
