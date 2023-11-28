package com.example.localdemo.controller;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.example.localdemo.annotation.Log;
import com.example.localdemo.annotation.TakeTime;
import com.example.localdemo.entity.Person;
import com.example.localdemo.entity.Timequartz;
import com.example.localdemo.service.TimequartzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

//@Api(tags = "事务测试版实现 ")
@RequestMapping("/api")
@RestController
@Slf4j
@Log
public class TimeQuartzController {

    @Resource
    private TimequartzService timequartzService;
    @TakeTime
//    @ApiOperation("事务创建")
    @PostMapping("/create")
    public String create(@RequestBody Timequartz timequartz) {
        timequartzService.insert(timequartz);
        return "";
    }
    @TakeTime
//    @ApiOperation("获取所有事务列表")
    @PostMapping("/getList")
    public List<Timequartz> getList(){
        return timequartzService.getList();
    }
    @TakeTime
//    @ApiOperation("事务删除")
    @PostMapping("/delete")
    public int delete(@RequestBody List<String> number){
        return timequartzService.delete(number);
    }

    @TakeTime
//    @ApiOperation("事务更新")
    @PostMapping("/update")
    public int update(@RequestBody Timequartz timequartz){
        return timequartzService.updateData(timequartz);
    }

//    @ApiOperation("多数据源测试")
    @GetMapping("/getSqlServerPerson")
    public List<Person> getPersonInfo() throws Exception {
        return timequartzService.getPersonList();
    }

    /**
     * 数据导出
     * @param response
     */

//    @ApiOperation("数据导出Excel")
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response) throws Exception {
        Cookie cookie = new Cookie("username","");
        response.addCookie(cookie);
        timequartzService.export(response);
    }
    /**
     * 开启事务
     */
//    @ApiOperation("启动事务（待完成）")
    @PostMapping("/begin")
    public void begin(@RequestParam String number){

    }

}

