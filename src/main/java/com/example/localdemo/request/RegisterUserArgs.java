package com.example.localdemo.request;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.localdemo.utils.SnowFlakeId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import lombok.Data;

import java.util.Date;

/**
 * @author xieteng
 * @date 2023/7/18 12:07
 * @description TODO
 */
@Data
public class RegisterUserArgs {

    private String username;

    private String password;

    private String address;

    private String email;

    private String idCard;

    private String smsCode;


}
