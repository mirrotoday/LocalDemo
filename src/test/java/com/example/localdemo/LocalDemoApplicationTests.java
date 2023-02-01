package com.example.localdemo;

import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootTest
class LocalDemoApplicationTests {



    @Test
    void contextLoads() throws ParseException {
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
