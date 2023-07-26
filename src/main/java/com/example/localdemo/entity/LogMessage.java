package com.example.localdemo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.localdemo.utils.DateUtils;
import com.example.localdemo.utils.SnowFlakeId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author xieteng
 * @date 2023/7/15 11:19
 * @description TODO
 */
@Data
@ToString
@TableName(value ="logmessage")
public class LogMessage implements Serializable {
    public LogMessage(){
        this.id = SnowFlakeId.nextId();
    }
    @TableId
    private Long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "startTime")
    private Date startTime;
    @Column(name = "requestStyle")
    private String requestStyle;
    @Column(name = "requestUrl")
    private String requestUrl;
    @Column(name = "requestType")
    private String requestType;
    @Column(name = "requestIp")
    private String requestIp;
    @Column(name = "requestResult")
    private String requestResult;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @Column(name = "endTime")
    private Date endTime;
    @Column(name = "expendTime")
    private long expendTime;

}
