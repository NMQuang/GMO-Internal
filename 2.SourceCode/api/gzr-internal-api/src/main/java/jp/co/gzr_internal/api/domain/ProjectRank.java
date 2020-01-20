package jp.co.gzr_internal.api.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The Class ProjectRank.
 */
@Entity
@Table(name = "project_rank")
public class ProjectRank implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /** The rank. */
    @Column(name = "rank")
    private String rank;
    
    /** The description. */
    @Column(name = "description")
    private String description;
    
    /** The create date. */
    @Column(name = "create_time")
    private LocalDateTime createDate;
    
    /** The update date. */
    @Column(name = "update_time")
    private LocalDateTime updateDate;
    
    /** The delete flag. */
    @Column(name = "delete_flag")
    private Integer deleteFlag;

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the rank.
     *
     * @return the rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * Sets the rank.
     *
     * @param rank the new rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the creates the date.
     *
     * @return the creates the date
     */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Sets the creates the date.
     *
     * @param createDate the new creates the date
     */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets the update date.
     *
     * @return the update date
     */
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the update date.
     *
     * @param updateDate the new update date
     */
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * Gets the delete flag.
     *
     * @return the delete flag
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * Sets the delete flag.
     *
     * @param deleteFlag the new delete flag
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
