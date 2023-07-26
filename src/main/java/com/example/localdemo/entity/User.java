package com.example.localdemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.localdemo.utils.SnowFlakeId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.command.BaseModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Date;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
/**
 * @author xieteng
 * @date 2023/7/17 21:49
 * @description TODO
 */
@Data
@TableName("user")
public class User{
    @TableId
    private Long id;

    @TableField("username")
    @Column(name  = "username")
    private String username;

    @TableField("password")
    @Column(name  = "password")
    private String password;

    @TableField("address")
    @Column(name  = "address")
    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @TableField("createTime")
    @Column(name  = "createTime")
    private Date createTime;

    @TableField("phone")
    @Column(name  = "phone")
    private long phone;

    @TableField("email")
    @Column(name  = "email")
    private String email;

    @TableField("idCard")
    @Column(name  = "idCard")
    private String idCard;

    @TableField("image")
    @Column(name  = "image")
    private String image;

    @TableField("loginCount")
    @Column(name  = "image")
    private Integer loginCount;
    public User(){
        this.id = SnowFlakeId.nextId();
        this.createTime = new Date();
        this.image = "https://img1.baidu.com/it/u=2526782352,137544254&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1689786000&t=1580a189d0ed0389df5b7bfb513e205d";
    }
}
