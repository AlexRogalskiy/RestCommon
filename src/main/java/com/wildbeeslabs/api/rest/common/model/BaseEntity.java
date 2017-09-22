package com.wildbeeslabs.api.rest.common.model;

import com.wildbeeslabs.api.rest.common.utils.DateUtils;
import java.io.Serializable;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
//import javax.persistence.PrePersist;
//import javax.persistence.PreUpdate;
import javax.persistence.Temporal;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * Base REST Application Model
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 * @param <T>
 */
@MappedSuperclass
public abstract class BaseEntity<T extends Serializable> implements IBaseEntity<T> {

    @CreationTimestamp
    //@CreatedDate
    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN)
    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createdAt;

    @UpdateTimestamp
    //@LastModifiedDate
    @DateTimeFormat(pattern = DateUtils.DEFAULT_DATE_FORMAT_PATTERN)
    @Column(name = "modified_at", nullable = true, insertable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date modifiedAt;

    @CreatedBy
    @NotBlank(message = "Field <createdBy> cannot be blank")
    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by", nullable = true, insertable = false)
    private String modifiedBy;

//    @PrePersist
//    protected void onCreate() {
//        this.createdAt = new Date();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        this.modifiedAt = new Date();
//    }
//    public String getCreatedAt() {
//        return (Objects.nonNull(this.createdAt)) ? DateUtils.dateToStr(this.createdAt) : null;
//    }
//
//    public void setCreatedAt(final String str) {
//        this.createdAt = (Objects.nonNull(str)) ? DateUtils.strToDate(str) : null;
//    }
    public Date getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(final Date createdAt) {
        this.createdAt = createdAt;
    }

//    public String getModifiedAt() {
//        return (Objects.nonNull(this.modifiedAt)) ? DateUtils.dateToStr(this.modifiedAt) : null;
//    }
//
//    public void setModifiedAt(final String str) {
//        this.modifiedAt = (Objects.nonNull(str)) ? DateUtils.strToDate(str) : null;
//    }
    public Date getModifiedAt() {
        return this.modifiedAt;
    }

    public void setModifiedAt(final Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(final String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || obj.getClass() != this.getClass()) {
            return false;
        }
        final BaseEntity other = (BaseEntity) obj;
        return Objects.equals(this.createdBy, other.createdBy)
                && Objects.equals(this.modifiedBy, other.modifiedBy)
                && Objects.equals(this.createdAt, other.createdAt)
                && Objects.equals(this.modifiedAt, other.modifiedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.createdAt, this.modifiedAt, this.createdBy, this.modifiedBy);
    }

    @Override
    public String toString() {
        return String.format("BaseEntity {createdAt: %s, createdAt: %s, createdBy: %s, modifiedBy: %s}", this.createdAt, this.modifiedAt, this.createdBy, this.modifiedBy);
    }
}
