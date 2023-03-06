package com.example.localdemo.mq.entity;

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
    private String type;
    private String content;
}
