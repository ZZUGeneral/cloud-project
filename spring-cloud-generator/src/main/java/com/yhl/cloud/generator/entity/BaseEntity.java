package com.yhl.cloud.generator.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.sql.Timestamp;

public class BaseEntity {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String createBy;
    private Timestamp createDate;
    private String updateBy;
    private Timestamp updateDate;
    private Integer deleteFlag;

    public BaseEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlaf(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof BaseEntity)) {
            return false;
        } else {
            BaseEntity other = (BaseEntity) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                Object this$id = this.getId();
                Object other$id = other.getId();
                if (this$id == null) {
                    if (other$id != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$id)) {
                    return false;
                }
                Object this$deleteFlag = this.getDeleteFlag();
                Object other$deleteFlag = other.getDeleteFlag();
                if (this$deleteFlag == null) {
                    if (other$deleteFlag != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$deleteFlag)) {
                    return false;
                }

                Object this$createBy = this.getCreateBy();
                Object other$createBy = other.getCreateBy();
                if (this$createBy == null) {
                    if (other$createBy != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$createBy)) {
                    return false;
                }

                label62:
                {
                    Object this$createDate = this.getCreateDate();
                    Object other$createDate = other.getCreateDate();
                    if (this$createDate == null) {
                        if (other$createDate == null) {
                            break label62;
                        }
                    } else if (this$id.equals(other$createDate)) {
                        break label62;
                    }
                    return false;
                }

                label55:
                {
                    Object this$updateBy = this.getUpdateBy();
                    Object other$updateBy = other.getUpdateBy();
                    if (this$updateBy == null) {
                        if (other$updateBy != null) {
                            break label55;
                        }
                    } else if (this$id.equals(other$updateBy)) {
                        break label55;
                    }
                    return false;
                }
                Object this$updateDate = this.getUpdateDate();
                Object other$updateDate = other.getUpdateDate();
                if (this$updateDate == null) {
                    if (other$updateDate != null) {
                        return false;
                    }
                } else if (!this$id.equals(other$updateDate)) {
                    return false;
                }
            }
        }
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseEntity;
    }

    @Override
    public int hashCode() {
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $deleteFlag = this.getDeleteFlag();
        result = result * 59 + ($deleteFlag == null ? 43 : $deleteFlag.hashCode());
        Object $createBy = this.getCreateBy();
        result = result * 59 + ($createBy == null ? 43 : $createBy.hashCode());
        Object $createDate = this.getCreateDate();
        result = result * 59 + ($createDate == null ? 43 : $createDate.hashCode());
        Object $updateBy = this.getUpdateBy();
        result = result * 59 + ($updateBy == null ? 43 : $updateBy.hashCode());
        Object $updateDate = this.getUpdateDate();
        result = result * 59 + ($updateDate == null ? 43 : $updateDate.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate=" + updateDate +
                ", deleteFlaf=" + deleteFlag +
                '}';
    }
}
