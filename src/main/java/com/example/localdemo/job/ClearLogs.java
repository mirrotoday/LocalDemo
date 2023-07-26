package com.example.localdemo.job;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author xieteng
 * @date 2023/7/26 ❤ 16:55
 * @description TODO 通过代码执行shell脚本-----Windows/Linux
 */
@Slf4j
public class ClearLogs implements Job {
    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("===============[start]进入执行日志清理脚本的后台事务===============");
        Process process = Runtime.getRuntime().exec("notepad.exe");
        //取得命令结果的输出流
        InputStream inputStream = process.getInputStream();
        //用一个读输出流类去读
        InputStreamReader isr = new InputStreamReader(inputStream, Charset.forName("GBK"));
        //用缓冲器读行
        BufferedReader br = new BufferedReader(isr);
        String line=null;
        //知道读完为止
        while ((line=br.readLine())!=null){
            log.info("读取的行数据为:"+line);
        }
        log.info("===============[end]脚本执行完毕===============");
    }
}
