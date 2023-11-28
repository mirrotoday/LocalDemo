package com.example.localdemo.controller;

import com.example.localdemo.entity.QuartzJob;
import com.example.localdemo.result.ApiResult;
import com.example.localdemo.service.DbLockService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xieteng
 * @date 2023/3/26 17:18
 * @description TODO Mysql InnoDb行锁的实现是通过索引项加锁来实现的，如果没有索引，InnoDB将通过隐藏的聚簇索引来对记录加锁（表锁）
 * @description TODO InnoDB 默认行级别的锁，当有明确指定的主键/索引的时候是行级锁，否则是表级别
 */
//@Api(tags = "高并发下数据库行级锁和表锁")
@RestController
@RequestMapping("/DbLock")
public class LockRowController {

    @Resource
    DbLockService dbLockService;

    /**
     *【有】明确指定的主键/索引的时候是【行级锁】
     * @param id
     * @return
     */
//    @ApiOperation(value  ="Mysql行锁查询")
    @GetMapping("/row-lock/{id}")
    public ApiResult<?> mysqlLockRow(@PathVariable("id") String id) throws InterruptedException {
        return dbLockService.getOneRowData(id);
    }

//    @ApiOperation(value ="Mysql行锁更新")
    @GetMapping("/rowupdate/{id}")
    public ApiResult<?> updateMysqlLockRow(@PathVariable("id") String id){
        return dbLockService.updateOneRowData(id);
    }
//    @ApiOperation(value ="Mysql行锁/表锁删除")
    @DeleteMapping("/delete/{id}")
    public ApiResult<?> deleteMysqlLockRow(@PathVariable("id") String id){
        return dbLockService.deleteOneRowData(id);
    }
    /**
     *【没有】明确指定的主键/索引的时候是【表级锁】
     * 5.7.27版本问题：对非主键/索引的情况下，show open tables where In_use > 0 查询下是已经锁表了，除了锁定的那一行数据，其他行都能操作数据【BUG】
     * 5.7.28正常
     * 该问题下一次解决
     * @param code
     * @return
     */
//    @ApiOperation(value ="Mysql表锁")
    @GetMapping("/table-lock/{code}")
    public ApiResult<?> mysqlLockTable(@PathVariable("code") String code) throws InterruptedException {
        return dbLockService.getOneTableData(code);
    }

//    @ApiOperation(value ="Mysql表锁更新")
    @GetMapping("/tableupdate/{code}")
    public ApiResult<?> updateMysqlTableRow(@PathVariable("code") String code){
        return dbLockService.updateOneTableData(code);
    }

}
