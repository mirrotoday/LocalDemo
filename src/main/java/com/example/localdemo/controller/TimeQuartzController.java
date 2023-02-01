package com.example.localdemo.controller;

import com.example.localdemo.entity.Person;
import com.example.localdemo.entity.Timequartz;
import com.example.localdemo.service.TimequartzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping("/api")
@RestController
@Slf4j
public class TimeQuartzController {

    @Resource
    private TimequartzService timequartzService;

    @PostMapping("/create")
    public String create(@RequestBody Timequartz timequartz) {
        timequartzService.insert(timequartz);
        return "";
    }
    @PostMapping("/getList")
    public List<Timequartz> getList(){
        return timequartzService.getList();
    }
    @PostMapping("/delete")
    public int delete(@RequestBody List<String> number){
        return timequartzService.delete(number);
    }
    @PostMapping("/update")
    public int update(@RequestBody Timequartz timequartz){
        return timequartzService.updateData(timequartz);
    }
    @GetMapping("/getSqlServerPerson")
    public List<Person> getPersonInfo(){
        return timequartzService.getPersonList();
    }

    /**
     * 数据导出
     * @param response
     */
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response){
        timequartzService.export(response);
    }
    /**
     * 开启事务
     */
    @PostMapping("/begin")
    public void begin(@RequestParam String number){

    }

}

