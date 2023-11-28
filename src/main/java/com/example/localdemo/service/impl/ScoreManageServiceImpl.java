package com.example.localdemo.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.localdemo.entity.ScoreManage;
import com.example.localdemo.response.SomeData;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.ScoreManageService;
import com.example.localdemo.mapper.ScoreManageMapper;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.*;
import java.util.List;

/**
* @author siyu
* @description 针对表【score_manage】的数据库操作Service实现
* @createDate 2023-08-15 11:51:30
*/
@Service
public class ScoreManageServiceImpl extends ServiceImpl<ScoreManageMapper, ScoreManage>
    implements ScoreManageService {
    @Resource
    ScoreManageMapper scoreManageMapper;
    @Transactional
    @Override
    public ApiResult<?> importScore(MultipartFile file) {
        List<ScoreManage> listScore = CollUtil.newArrayList();
        try (InputStream inputStream = file.getInputStream()) {
            // 调用用 hutool 方法读取数据 默认调用第一个sheet
            ExcelReader excelReader = ExcelUtil.getReader(inputStream);
            //根据行获取数据
            List<List<Object>> list = excelReader.read(1);
            //这个同上导入
            for (List<Object> row : list) {
                ScoreManage scoreManage = new ScoreManage();
                //获取excel第二行指定列的数据进行填充

                //学生
                if (null != row.get(0) || !"".equals(row.get(0))) {
                    scoreManage.setStudentname(scoreManageMapper.getUserName(row.get(0).toString()));
                }
                //所在班级
                if (null != row.get(1) || !"".equals(row.get(1))) {
                    scoreManage.setOnclass(scoreManageMapper.getClassName(row.get(1).toString()));
                }

                //课程
                if (null != row.get(2) || !"".equals(row.get(2))) {
                    scoreManage.setCoursename(scoreManageMapper.getCourseName(row.get(2).toString()));
                }
                //成绩
                if (null != row.get(3) || !"".equals(row.get(3))) {
                    scoreManage.setScore(Double.valueOf(row.get(3).toString()));
                }
                QueryWrapper<ScoreManage> filter = new QueryWrapper<>();
                filter.eq("studentname", scoreManage.getStudentname());
                filter.eq("coursename", scoreManage.getCoursename());
                ScoreManage isExit = getOne(filter, false);
                if (isExit == null) {
                    listScore.add(scoreManage);
                } else {
                    updateById(isExit);
                }

            }
            boolean isSuccess = saveBatch(listScore);
            if (isSuccess) {
                return new ApiResult<>().success("导入成功");
            }
            return new ApiResult<>().error("导入失败");
        } catch (Exception e) {
            throw new RuntimeException("导入出错：" + e.getMessage());
        }
    }
    @SneakyThrows
    @Override
    public ApiResult<?> exportScore(HttpServletResponse response) {
        String dynamicSql = scoreManageMapper.getDynamicSQl();
        String[] split = dynamicSql.split(",");
        List<SomeData> columnList = scoreManageMapper.getListData(dynamicSql);
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(columnList, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("数据表信息表", "UTF-8");    // 设置名称和字符集
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
        return new ApiResult<>().success("导出成功");
    }
    @SneakyThrows
    @Override
    public ApiResult<?> getScoreList(String className, String courseName, String studentName) {
//        String dynamicSql = scoreManageMapper.getDynamicSQl();
//       List<SomeData> data = scoreManageMapper.getListData(dynamicSql);
        //使用存储过程
        List<SomeData> someData = scoreManageMapper.getSomeData();

        return new ApiResult<>().result(someData);
    }

}




