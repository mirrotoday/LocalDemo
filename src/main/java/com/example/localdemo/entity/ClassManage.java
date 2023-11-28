package com.example.localdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import com.example.localdemo.utils.SnowFlakeId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @TableName class_manage
 */
@TableName(value ="class_manage")
@Data
public class ClassManage implements Serializable {
    /**
     * 
     */
    @TableId
    private Long id;

    /**
     * 
     */
    @NotBlank(message = "班级名称不能为空！")
    private String classname;

    /**
     * 
     */
    private String classcode;

    /**
     * 
     */
    private String classsimple;
    /**
     * 是否启用
     */
    @NotNull(message = "是否启用必填！true Or false")
    private Boolean isuse;
    public ClassManage(){
        this.id = SnowFlakeId.nextId();
        //this.isuse = true;
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ClassManage other = (ClassManage) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getClassname() == null ? other.getClassname() == null : this.getClassname().equals(other.getClassname()))
            && (this.getClasscode() == null ? other.getClasscode() == null : this.getClasscode().equals(other.getClasscode()))
            && (this.getClasssimple() == null ? other.getClasssimple() == null : this.getClasssimple().equals(other.getClasssimple()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getClassname() == null) ? 0 : getClassname().hashCode());
        result = prime * result + ((getClasscode() == null) ? 0 : getClasscode().hashCode());
        result = prime * result + ((getClasssimple() == null) ? 0 : getClasssimple().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", classname=").append(classname);
        sb.append(", classcode=").append(classcode);
        sb.append(", classsimple=").append(classsimple);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}