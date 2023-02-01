package com.example.localdemo.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.localdemo.entity.Person;
import com.example.localdemo.entity.Timequartz;
import com.example.localdemo.service.TimequartzService;
import com.example.localdemo.mapper.TimequartzMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* @author siyu
* @description 针对表【timequartz】的数据库操作Service实现
* @createDate 2023-01-16 14:21:35
 * Transactional 开启事务，操作数据库失败后回滚
*/
@Service
@Transactional
@Slf4j
public class TimequartzServiceImpl extends ServiceImpl<TimequartzMapper, Timequartz>
    implements TimequartzService {

    @Resource
    private TimequartzMapper timequartzMapper;

    @Override
    public int insert(Timequartz timequartz) {
        QueryWrapper<Timequartz> wrapper = new QueryWrapper();
        wrapper.eq("number",timequartz.getNumber());
        boolean exit = timequartzMapper.exists(wrapper);
        if(!exit){
            int count = timequartzMapper.insert(timequartz);
            log.info(count+"条数据新增成功！");
            return count;
        }
        log.error("数据库中该编码已经存在！");
       return 0;
    }

    @Override
    public List<Timequartz> getList() {
        List<Timequartz> list = timequartzMapper.selectList();
        if(list.size() != 0){
            log.info("列表查询成功！共计："+list.size());
            return list;
        }
        log.info("列表数据为空！共计："+list.size());
      return new ArrayList<>();
    }

    @Override
    public int delete(List<String> number) {
        int count = timequartzMapper.deleteBatchIds(number);
        if(count == number.size()){
            log.info(count+"条数据删除成功");
            return count;
        }
        log.error("删除失败");
       return 0;
    }

    @Override
    public int updateData(Timequartz timequartz) {
        String id = timequartzMapper.getId(timequartz.getNumber());
        timequartz.setId(id);
        int count = timequartzMapper.updateById(timequartz);
        log.info(count+"条数据更新成功！");
        return count;
    }

    /**
     * @author siyu
     * @datetime 2023-01-17
     * 使用@DS注解，连接多数据源中的sqlserver数据库
     * @return
     */
    @DS("sqlserver")
    @Override
    public List<Person> getPersonList() {
        List<Person> personInfo = timequartzMapper.getPersonList();
        if(personInfo.size() > 0){
            log.info("获取到人员数量："+personInfo.size());
            return personInfo;
        }
       return new ArrayList<>();
    }

    /**
     * 数据导出
     * ExcelUtil.getWriter(true); 开启文件后缀为xlsx的文件，没有行数长度限制
     * writer.autoSizeColumnAll(); 设置自适应单元格的长宽
     * @param response
     */
    @DS("sqlserver")
    @Override
    public void export(HttpServletResponse response)  {
        List<Person> list = timequartzMapper.getPersonList();
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定义标题别名,（"实体名称"，"自定义名称"）
        writer.addHeaderAlias("fid","ID");
        writer.addHeaderAlias("name","名称");
        writer.addHeaderAlias("fnumber","编码");
        writer.merge(writer.getHeaderAlias().size() - 1,"人员信息导出");
        writer.write(list,true);
        // 设置所有列为自动宽度，不考虑合并单元格
        writer.autoSizeColumnAll();
//        备用方案
//        for (int i = 0; i < writer.getColumnCount(); ++i) {
//            double width = SheetUtil.getColumnWidth(writer.getSheet(), i, false);
//            if (width != -1.0D) {
//                width *= 256.0D;
//                //此处可以适当调整，调整列空白处宽度
//                width += 220D;
//                writer.setColumnWidth(i, Math.toIntExact(Math.round(width / 256D)));
//            }
//        }
        response.setContentType("application/vnd.msexcel;charset=utf-8");
        try {
        String name = DateUtil.today()+"人员信息导出";
        response.setHeader("Content-Disposition","attachment;filename="+new String(name.getBytes("gb2312"), "ISO8859-1")+".xlsx");
        ServletOutputStream out = null;
            out = response.getOutputStream();
            writer.flush(out,true);
            log.info("导出成功！共计"+writer.getHeaderAlias().size()+"列,"+(writer.getRowCount() - 2)+"行数据！");
        }catch (IOException e){
            log.info(e.getMessage());
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }

}




