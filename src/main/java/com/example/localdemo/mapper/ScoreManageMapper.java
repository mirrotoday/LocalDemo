package com.example.localdemo.mapper;

import com.example.localdemo.entity.ScoreManage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.localdemo.response.SomeData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

/**
* @author siyu
* @description 针对表【score_manage】的数据库操作Mapper
* @createDate 2023-08-15 11:51:30
* @Entity com.example.localdemo.entity.ScoreManage
*/
@Mapper
public interface ScoreManageMapper extends BaseMapper<ScoreManage> {
    Long getUserName(String name);
    Long getClassName(String name);
    Long getCourseName(String name);
    List<SomeData> getListData(String dynamicSql);

    String getDynamicSQl();

    @Select("{call p1()}")
    @Options(statementType = StatementType.CALLABLE)
    List<SomeData> getSomeData();
    //  List getListData(String className, String courseName, String studentName); #{className},#{courseName},#{studentName}
}




