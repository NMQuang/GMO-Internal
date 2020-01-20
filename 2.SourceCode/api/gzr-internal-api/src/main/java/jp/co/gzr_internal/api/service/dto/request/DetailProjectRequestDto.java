package jp.co.gzr_internal.api.service.dto.request;

import javax.validation.constraints.NotNull;

import jp.co.gzr_internal.api.web.rest.validator.annotation.number.Number;

public class DetailProjectRequestDto {

    @NotNull
    @Number
    private String projectCode;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

}
