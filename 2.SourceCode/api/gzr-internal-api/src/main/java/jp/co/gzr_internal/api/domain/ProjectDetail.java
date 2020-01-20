package jp.co.gzr_internal.api.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import jp.co.gzr_internal.api.domain.key.ProjectDetailKey;

@Entity
@Table(name = "project_detail")
public class ProjectDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProjectDetailKey projectDetailKey;
    
    @Column(name = "create_time")
    private LocalDateTime createDate;
    
    @Column(name = "update_time")
    private LocalDateTime updateDate;
    
    @Column(name = "delete_flag")
    private Integer deleteFlag;

    public ProjectDetailKey getProjectDetailKey() {
        return projectDetailKey;
    }

    public void setProjectDetailKey(ProjectDetailKey projectDetailKey) {
        this.projectDetailKey = projectDetailKey;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
