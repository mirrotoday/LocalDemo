package com.example.localdemo.controller;

import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.ScoreManageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xieteng
 * @date 2023/8/15 ❤ 11:53
 * @description TODO
 */
@RestController
@RequestMapping("/api/score")
public class ScoreManageController {
    @Resource
    private ScoreManageService scoreManageService;

    //导入学生成绩
    @PostMapping("/importScore")
    public ApiResult<?> importScore(MultipartFile file) {
       return scoreManageService.importScore(file);
    }
    //导出学生成绩
    @GetMapping("/exportScore")
    public ApiResult<?> exportScore(HttpServletResponse response) {
        return scoreManageService.exportScore(response);
    }
    //分页查询学生信息
    @GetMapping("/getScoreList")
    public ApiResult<?> getScoreList(@RequestParam String className,@RequestParam String courseName,@RequestParam String studentName) {
        return scoreManageService.getScoreList(className,courseName,studentName);
    }

}
