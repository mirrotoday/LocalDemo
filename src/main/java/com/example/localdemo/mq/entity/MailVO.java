package com.example.localdemo.mq.entity;

import cn.hutool.core.util.RandomUtil;
import com.example.localdemo.utils.CodeUtils;
import lombok.Data;

/**
 * @author xieteng
 * @date 2023/3/6 16:29
 * @description TODO
 */
@Data
public class MailVO {

    private String sender;
    private String receiver;
    private String code;
    private String type;
    private String content;

    public MailVO(){
        String mailCode = CodeUtils.getMailCode(4);
        this.type = "验证码";
        this.code = mailCode;
        this.content = "您的验证码为 " + mailCode + " 有效期5分钟，请不要告诉他人哦！";
    }
}
