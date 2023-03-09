package com.example.localdemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author xieteng
 * @date 2023/3/9 20:09
 * @description TODO Serializable 序列化
 * @description TODO 此处为什么要实现Serializable 将对象状态转换为可保持或传输的格式的过程。与序列化相对的是反序列化，它将流转换为对象。这两个过程结合起来，可以轻松地存储和传输数据
 */
@Data
@TableName("sys_quartz_job")
@ToString //对象toString
public class QuartzJob implements Serializable {
    private static final long serialVersionUID = 1L;
    /**id*/
    @TableId(type = IdType.ASSIGN_ID)
    private java.lang.String id;
    /**创建人*/
    private java.lang.String createBy;
    /**创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date createTime;
    /**删除状态*/
    private java.lang.Integer delFlag;
    /**修改人*/
    private java.lang.String updateBy;
    /**修改时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;
    /**任务类名*/
    @NotBlank(message="任务类名不能为空")
    private java.lang.String jobClassName;
    /**cron表达式*/
    @NotBlank(message="cron表达式不能为空")
    private java.lang.String cronExpression;
    /**参数*/
    private java.lang.String parameter;
    /**描述*/
    private java.lang.String description;
    /**状态 0正常 -1停止*/
    private java.lang.Integer status;

}
