package com.example.localdemo;

import cn.hutool.core.date.DateUtil;
import com.example.localdemo.entity.Person;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class LocalDemoApplicationTests {



    @Test
    void contextLoads() throws ParseException {
        HashMap MyhashMap = new MyHashMap();
        String cdfghrt1 = "姓名1"+ "@"+Long.parseLong("67890");
        String cdfghrt2 = "姓名2"+ "@"+Long.parseLong("12345");
        MyhashMap.put(cdfghrt1,"A1");
        MyhashMap.put(cdfghrt1,"A2");
        MyhashMap.put(cdfghrt2,"A3");
        MyhashMap.put(cdfghrt2,"A4");
        MyhashMap.forEach((key, value) -> {
            String[] split = key.toString().split("@");
            System.out.println("用户姓名："+split[0]);
            System.out.println("用户ID："+split[1]);
            System.out.println("运维工单编号:"+value);
        });
        System.out.println(MyhashMap.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String gqformat = "2023-02-25 15:26:22";
        String jgformat = "2023-02-25 17:11:07";

//        LocalDateTime start = LocalDateTime.parse(new CharSequence(gqformat));
////        LocalDateTime end = LocalDateTime.parse(jgformat);
////        Duration duration = Duration.between(start, end);
////        long hours = duration.toHours();
////        System.out.println(hours);
       // extracted1();
//        Date parse1 = sdf.parse(gqformat);
//        Date parse2 = sdf.parse(jgformat);
//        if(new Date().getTime() > parse2.getTime()){
//            System.out.println("当前时间大于parse2，就相当于parse2过期了s");
//        }else{
//            System.out.println("当前时间小于parse2，就相当于parse2没过期");
//        }
 //       System.out.println(formatDuration(parse1,parse2));
    }
    public static String formatDuration(Date startTime, Date endTime) {
        if (null == startTime || null == endTime || startTime.after(endTime)) {
            return null;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(startTime);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endTime);

        int minuteDiff = endCalendar.get(Calendar.MINUTE) - startCalendar.get(Calendar.MINUTE);
        if (minuteDiff < 0) {
            endCalendar.add(Calendar.HOUR_OF_DAY, -1);
            minuteDiff = minuteDiff + 60;
        }
        int hourDiff = endCalendar.get(Calendar.HOUR_OF_DAY) - startCalendar.get(Calendar.HOUR_OF_DAY);
        if (hourDiff < 0) {
            endCalendar.add(Calendar.DAY_OF_MONTH, -1);
            hourDiff = hourDiff + 24;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(hourDiff).append(".").append(minuteDiff);

        return builder.toString();
    }
    // 24 * (differ / dayM) 这里拿到被舍弃的整数，整数是几，就代表相隔几天，一天24小时，那就整数乘以24即可。
    private static int getDifferHour(Date startDate, Date endDate) {
        long dayM = 1000 * 24 * 60 * 60;
        long hourM = 1000 * 60 * 60;
        long differ = endDate.getTime() - startDate.getTime();
        long hour = differ % dayM / hourM + 24 * (differ / dayM);
        return Integer.parseInt(String.valueOf(hour));
    }
    private void extracted1() throws ParseException {
        Double cc1 = -100.0;
        if(cc1 < 0.0){
            System.out.println("小于0");
        }else{
            System.out.println("大于0");
        }
        List<String> data = new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("2");
        Set<String> VBC = new HashSet<>();
        VBC.add("cc");
        VBC.add("cc");
        VBC.add("cc");
        VBC.add("dd");
        VBC.add("ee");
        System.out.println(VBC.toString());
        /**
         * stream流去重
         */
        List<String> newData = data.stream().distinct().collect(Collectors.toList());
        System.out.println("去重数据："+newData);

        /**
         * stream流 排序
         * sorted
         */
        List<Integer> intData = Arrays.asList(1,2,3,4,5,6,7,8,100,45,89,23,80,28,34);
        List<Integer> sordData = intData.stream().sorted().collect(Collectors.toList());
        System.out.println("排序后的数据："+sordData);
        //out: 考试成绩90分以上的学生姓名：[Alis, Jessie, Mike, Jack, Allon, Lucy]
        List<Person> students = new ArrayList<>();
        for(int i =0;i<5;i++){
            Person person = new Person();
            person.setFid(""+i+"");
            person.setName("张飞"+i);
            person.setFnumber("9"+i);
        }
//        students.add(new Person("Mike", 10, "male", 88));
//        students.add(new Person("Jack", 13,"male", 90));
//        students.add(new Person("Lucy", 15,"female", 100));
//        students.add(new Person("Jessie", 12,"female", 78));
//        students.add(new Person("Allon", 16,"female", 92));
//        students.add(new Person("Alis", 22,"female", 50));

        List<String> nameList = students.stream().sorted(Comparator.comparing(Person::getFnumber)).map(Person::getName).collect(Collectors.toList());
        System.out.println("按成绩排序输出学生姓名：" + nameList);

        /**
         * stream 输出
         */
        System.out.println("Lambda表达式双个冒号输出");
        intData.forEach(System.out::println);
        System.out.println("Lambda表达式遍历输出");
        intData.forEach(v ->{
            System.out.println(v);
        });

        /**
         * Map输出
         */
        Map<Integer,String> mapData = new HashMap<>();
        mapData.put(1,"张飞");
        mapData.put(2,"刘备");
        mapData.put(3,"关羽");
        mapData.forEach((k,v) ->{
            System.out.println("Lambda表达式遍历输出Map,[key]:"+k+",[value]:"+v);
        });
        /**
         * stream 条件过滤 取其中任意一个/取其中第一个
         */
        Optional<Integer> filerAnyData= intData.stream().filter(x -> x > 10).findAny();
        List<Integer> data3 = Collections.singletonList(filerAnyData.get());
        System.out.println("通过条件过滤其中任意一个数据："+filerAnyData);
        Optional<Integer> filerFirstData= intData.stream().filter(x -> x > 10).findFirst();
        List<Integer> data4 = Collections.singletonList(filerFirstData.get());
        System.out.println("通过条件过滤满足条件的第一个数据："+filerAnyData);
        /**
         * 使用stream流转化成字符串
         * 1.stream
         * 2.StringUtils 工具类 com.sun.deploy.util
         */
        String cc = data.stream().collect(Collectors.joining(",","[{","}]"));
        System.out.println("cc:"+cc);

//        String aa = StringUtils.join(data,",");
//        System.out.println("aa:"+aa+","+data.toString());

        extracted();
        /**
         * 递归算法
         */
        int sum = getSum(2022);
        int sum2 =  getSum2(2022);

        System.out.println("递归1到2022自然数相加的和为："+sum);
        System.out.println("循环1到2022自然数相加的和为："+sum2);
        getSum3(2023);
    }

    public int getSum(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n + getSum(n - 1);
        }
    }
    public int getSum2(int n) {
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            if(i == 0){
                sum =i;
            }else{
                sum += i;
            }
        }
        return sum;
    }
    public void getSum3(int n) {
        switch (n) {
            case 1:
                System.out.println("8RMB");
                break;
            case 2:
                System.out.println("6RMB");
                break;
            case 3:
                System.out.println("20RMB");
                break;
            case 4:
                System.out.println("5RMB");
                break;
            case 5:
            case 6:
                System.out.println("7RMB");
                break;
            default:
                System.out.println("选择错误");
                break;
        }
    }

    private void extracted() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(format.parse("2022-01-08"));
        cal.add(Calendar.DAY_OF_MONTH,-1);
        String date = format.format(cal.getTime());
        System.out.println("前一天1为:"+date);
        String yesterday = format.format(DateUtil.yesterday().getTime());
        System.out.println("前一天2为:"+yesterday);
    }


}
