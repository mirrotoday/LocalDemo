package com.example.localdemo.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author xieteng
 * @date 2023/7/18 3:24
 * @description TODO
 */
@Data
public class ResetUserArgs {
    @NotBlank
    private String userName;
    @NotBlank
    private String oldPassWord;
    @NotBlank
    private String newPassWord;
}
