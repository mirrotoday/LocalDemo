package com.example.localdemo.utils;

import com.example.localdemo.authentication.RdSeedProduce;
import com.example.localdemo.mq.config.RedisKeyConstant;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.Duration;
import java.util.Random;

public final class CaptchaCode {
    private static HttpServletResponse response;
    private static final String[] rdStr =
            {
                    "a", "b", "c", "d", "e", "f", "h", "i",
                    "j", "k", "m", "n", "p", "q", "r",
                    "s", "t", "u", "v", "w", "x", "y", "z",
                    "2", "3", "4", "5", "6", "7", "8", "9"
            };

    /**
     * @param stringRedisTemplate               自动生成随机验证码
     * @param response
     * @param stringRedisTemplate
     */
    public static void drawCode(HttpServletResponse response, StringRedisTemplate stringRedisTemplate,String userName) {
        CaptchaCode.response = response;
        StringBuilder builder = new StringBuilder();
        for (int k = 1; k <= 4; k++) {
            //取5位随机字符+随机数字
            Random rd = new Random(RdSeedProduce.Produce());
            builder.append(String.format("%s", rdStr[rd.nextInt(rdStr.length)]));
        }
        getCode(builder.toString(), stringRedisTemplate,userName);
    }

    private static void getCode(String code, StringRedisTemplate redis,String userName) {
        //2.定义图片的高度和宽度
        int width = 75;
        int height = 35;
        //建立bufferedImage对象，制定图片的长度和宽度以及色彩
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        //3.获取Graphics2D 绘制对象，开始绘制验证码
        Graphics2D g = bi.createGraphics();
        //4.设置文字的子图验证和大小
        Font font = new Font("微软雅黑", Font.PLAIN, 18);
        //5.设置字体的颜色
        Color color = new Color(0, 0, 0);
        //将颜色和字体放入
        g.setFont(font);
        g.setColor(color);
        g.setBackground(new Color(226, 226, 226));//背景颜色
        //开始绘制对象
        g.clearRect(0, 0, width, height);
        //绘制形状，获取距形对象
        FontRenderContext context = g.getFontRenderContext();//文字读取上下文
        Rectangle2D bounds = font.getStringBounds(code, context);//将生成的验证码放入
        //计算文字的坐标和间距
        double x = (width - bounds.getWidth()) / 2;
        double y = 23;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code, (int) x, (int) y);
        //结束配置
        g.dispose();
        //将图片保存到制定地方，输出
        try {
            ImageIO.write(bi, "jpg", response.getOutputStream());
            //刷新响应流
            response.flushBuffer();
            //存入redis
            String redisKey = RedisKeyConstant.verify_code.getKey() + userName;
            redis.opsForValue().set(redisKey, code.toUpperCase(), Duration.ofMinutes(5));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
