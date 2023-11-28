package com.example.localdemo.service;

import com.example.localdemo.entity.ScoreManage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.localdemo.result.ApiResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
* @author siyu
* @description 针对表【score_manage】的数据库操作Service
* @createDate 2023-08-15 11:51:30
*/
public interface ScoreManageService extends IService<ScoreManage> {

    ApiResult<?> importScore(MultipartFile file);

    ApiResult<?> exportScore(HttpServletResponse response);

    ApiResult<?> getScoreList(String className, String courseName, String studentName);

}
