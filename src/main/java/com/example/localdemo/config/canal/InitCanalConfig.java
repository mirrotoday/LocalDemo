package com.example.localdemo.config.canal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author xieteng
 * @date 2023/4/2 19:33
 * @description TODO
 */
//@Configuration
public class InitCanalConfig implements CommandLineRunner {

    @Resource
    private CanalUtil canalUtil;


    @Override
    public void run(String... args) throws Exception {
        canalUtil.run();
    }
}

