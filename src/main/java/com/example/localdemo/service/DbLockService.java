package com.example.localdemo.service;

import com.example.localdemo.result.ApiResult;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

/**
 * @author xieteng
 * @date 2023/3/26 17:24
 * @description TODO
 */
//@Service
public interface DbLockService {
    // 1OMfvv8KjJV8PvCQ2bqAZ1678795420292
   // @Select("sleect * from message where id = #{id}} for update")
    ApiResult<?> getOneRowData(String id) throws InterruptedException;
    //zxpc
   // @Select("sleect * from message where code = #{code}}")
    ApiResult<?> getOneTableData(String code) throws InterruptedException;
    // 1OMfvv8KjJV8PvCQ2bqAZ1678795420292
   // @Update("update message set code = 'abcdTest' where id = #{id}}")
    ApiResult<?> updateOneRowData(String id);
    //zxpc
   // @Update("update message set title = '系统验证码测试锁' where code = #{code}} for update")
    ApiResult<?> updateOneTableData(String code);
    // 1OMfvv8KjJV8PvCQ2bqAZ1678795420292
    //@Delete("delete message where id = #{id}}")
    ApiResult<?> deleteOneRowData(String id);


}
