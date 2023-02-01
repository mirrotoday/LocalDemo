package com.example.localdemo.entity;

import cn.hutool.core.lang.id.NanoId;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author siyu
 * @TableName timequartz
 */
@Data
@TableName(value ="timequartz")
public class Timequartz implements Serializable {

    public Timequartz(){
        this.id = NanoId.randomNanoId(22);
        this.creattime = new Date();
        this.status = 1;
    }
    /**
     * 
     */
    @TableId
    private String id;

    /**
     * 
     */
    private String number;

    /**
     * 
     */
    private String classname;

    /**
     * 
     */
    private String classmethod;

    /**
     * 
     */
    private String cron;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date creattime;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date effdatetime;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date leffdatetime;

    /**
     * 
     */
    private Integer status;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", number=").append(number);
        sb.append(", classname=").append(classname);
        sb.append(", classmethod=").append(classmethod);
        sb.append(", cron=").append(cron);
        sb.append(", creattime=").append(creattime);
        sb.append(", effdatetime=").append(effdatetime);
        sb.append(", leffdatetime=").append(leffdatetime);
        sb.append(", status=").append(status);
        sb.append("]");
        return sb.toString();
    }
}