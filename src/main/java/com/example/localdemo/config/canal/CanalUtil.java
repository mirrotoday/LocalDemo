package com.example.localdemo.config.canal;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author xieteng
 * @date 2023/4/2 19:34
 * @description TODO
 */
@Slf4j
@Component
public class CanalUtil {

    public void run() throws Exception {

        /*
         * 此处Ip地址为linux虚拟机地址
         * 端口号为固定 11111
         */
        CanalConnector conn = CanalConnectors.newSingleConnector(new InetSocketAddress("127.0.0.1", 11111), "example", "", "");
        while (true) {
            conn.connect();
            //订阅实例中所有的数据库和表
            /*
             这里注意下：
               canal会一直向你的example.log日志文件写入日志，
               测了一下大概12小时会写入20M大小的日志。
            */
            //conn.subscribe(".*\\..*");  //此处监听的配置会使instance.properties里的配置失效
            conn.subscribe("mycustomdb\\..*");

            // 回滚到未进行ack的地方
            conn.rollback();
            // 获取数据 每次获取一百条改变数据
            Message message = conn.getWithoutAck(100);
            //获取这条消息的id
            long id = message.getId();
            int size = message.getEntries().size();
            if (id != -1 && size > 0) {
                // 数据解析
                analysis(message.getEntries());
            }else {
                //暂停1秒防止重复链接数据库
                Thread.sleep(1000);
            }
            // 确认消费完成这条消息
            conn.ack(message.getId());
            // 关闭连接
            conn.disconnect();
        }
    }

    /**
     * 数据解析
     */
    private void analysis(List<CanalEntry.Entry> entries) {
        for (CanalEntry.Entry entry : entries) {
            // 解析binlog
            CanalEntry.RowChange rowChange = null;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());  //此段代码重要，所有的数据从这里解析出来
            } catch (Exception e) {
                throw new RuntimeException("解析出现异常 data:" + entry.toString(), e);
            }

            if (CollUtil.isNotEmpty(rowChange.getRowDatasList())) {
                //操作类型
                CanalEntry.EventType eventType = rowChange.getEventType();
                // 获取当前操作所属的表
                String tableName = entry.getHeader().getTableName();

                log.info("<====>当前监听到数据库的操作：[事务提交时间]：" + time(entry.getHeader().getExecuteTime())+",[数据库]：" + entry.getHeader().getSchemaName()+",[表名]：" + tableName+",[操作类型]:" + eventType);
                if (rowChange.getIsDdl()) {
                    System.out.println("================》;isDdl: true,sql:" + rowChange.getSql());
                }
                for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                    String sql=parseCRUDSql(tableName,rowData.getBeforeColumnsList(),rowData.getAfterColumnsList(),eventType);
                    log.info(sql);
                }
            }
        }
    }

    private String time(long timestamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        return simpleDateFormat.format(date);
    }
    private  String parseCRUDSql( String tableName,List<CanalEntry.Column> beforeColumns, List<CanalEntry.Column> afterColumns, CanalEntry.EventType eventType) {
        String sql="";
        if (eventType == CanalEntry.EventType.DELETE) { //删除
            //DELETE FROM table_name WHERE ID=1
            StringBuffer deleteBuffer=new StringBuffer();
            deleteBuffer.append("DELETE FROM "+tableName+" WHERE ");
            for (CanalEntry.Column column : beforeColumns) {
                String columnName=column.getName();
                String columnValue="'"+ column.getValue()+"'";
                if("id".equals(columnName)){
                    deleteBuffer.append(columnName+"="+columnValue);
                    break;
                }
//                if(StringUtils.isNotEmpty(columnValue)){
//                    deleteBuffer.append(columnName+"="+columnValue);
//                    deleteBuffer.append(" AND ");
//                }
            }
            //截取掉最后一个AND
            sql = deleteBuffer.toString();
        } else if (eventType == CanalEntry.EventType.INSERT) {  //如果是新增语句
            StringBuffer sqlBeforeBuffer=new StringBuffer();//sql前一部分
            StringBuffer sqlAfterBuffer=new StringBuffer();//sql后一部分
            // INSERT INTO `canal.sys_holiday` (`year`, `date_str`, `day_type`) VALUES ('2020', '2020-10-01', '0');
            sqlBeforeBuffer.append("INSERT INTO "+tableName);
            sqlBeforeBuffer.append('(');//前半部分
            sqlAfterBuffer.append(" VALUES (");//后半部分
            for (CanalEntry.Column column : afterColumns) {
                String columnName=column.getName();
                String columnValue="'"+column.getValue()+"'";
                sqlBeforeBuffer.append(columnName+",");
                sqlAfterBuffer.append(columnValue+",");
            }
            String beforeStr=sqlBeforeBuffer.substring(0,sqlBeforeBuffer.length()-1);
            String afterStr=sqlAfterBuffer.substring(0,sqlAfterBuffer.length()-1);
            beforeStr=beforeStr+")";
            afterStr=afterStr+")";
            sql=beforeStr+afterStr;//拼接前一部分和后一部分

        } else {  //如果是更新的语句
            //UPDATE runoob_tbl SET id=1,runoob_title='学习 C++' WHERE runoob_id=3 AND TITLE=45;
            StringBuffer updateBuffer=new StringBuffer();
            StringBuffer whereBuffer=new StringBuffer();
            updateBuffer.append("UPDATE "+tableName+" SET ");
            for (CanalEntry.Column column : afterColumns) {
                String columnName=column.getName();
                String columnValue="'"+ column.getValue()+"'";
                boolean updated = column.getUpdated();
                if(updated){//表示更新的字段
                    updateBuffer.append(columnName+"="+columnValue+",");
                }else{
                    if("id".equals(columnName)) whereBuffer.append(" "+columnName+"="+columnValue+""); continue;
                }
            }
            String beforeStr=updateBuffer.substring(0,updateBuffer.length()-1);
            String whereStr=whereBuffer.toString();
            sql=beforeStr+" WHERE "+whereStr;
        }
        return sql;
    }
}

