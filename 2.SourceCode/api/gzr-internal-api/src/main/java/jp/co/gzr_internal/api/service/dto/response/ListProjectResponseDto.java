package jp.co.gzr_internal.api.service.dto.response;

import java.util.List;

import jp.co.gzr_internal.api.service.dto.ListProjectDto;

public class ListProjectResponseDto {

    private List<ListProjectDto> data;

    public List<ListProjectDto> getData() {
        return data;
    }

    public void setData(List<ListProjectDto> data) {
        this.data = data;
    }
}
