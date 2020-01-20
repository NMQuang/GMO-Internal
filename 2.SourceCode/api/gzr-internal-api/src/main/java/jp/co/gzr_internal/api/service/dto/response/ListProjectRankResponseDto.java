package jp.co.gzr_internal.api.service.dto.response;

import javax.persistence.Column;

public class ListProjectRankResponseDto {

    private Integer id;

    private String rank;

    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
