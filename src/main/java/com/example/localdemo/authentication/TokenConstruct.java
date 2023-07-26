package com.example.localdemo.authentication;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.localdemo.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;


import java.util.Date;
import java.util.Random;
@Slf4j
public final class TokenConstruct {
    private static final String[] rdStr =
            {
                    "a", "b", "c", "d", "e", "f", "h", "i",
                    "j", "k", "l", "m", "n", "o", "p", "q", "r",
                    "s", "t", "u", "v", "w", "x", "y", "z"
            };

    private static final Integer[] rdInt = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    private static final Integer effectHour = 2;

    /**
     * @param userId 用户id
     * @return token=base64(用户id+随机字符串+时间戳)构成
     */
    public static String tokenId(String userId) {
        StringBuilder builder = new StringBuilder().append(String.format("%s|", userId));
        for (int k = 1; k < 6; k++) {
            //取5位随机字符+随机数字
            Random rd = new Random(RdSeedProduce.Produce());
            builder.append(String.format("%s", rdStr[rd.nextInt(rdStr.length)]));
            builder.append(String.format("%d", rdInt[rd.nextInt(rdInt.length)]));
        }
        //生成时间戳
        long ticks = System.currentTimeMillis();
        builder.append(String.format("|%s", ticks));
        return Encrypt.toBase64(builder);
    }

    /**
     * @param token 用户登录token值
     * @return 解析token，返回用户id、随机字符串、时间戳
     */
    public static boolean deToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        } else {
            boolean isValid = true;
            try {
                String str = new String(Encrypt.fromBase64(token));
                String[] result = str.split("\\|");
                Date start = DateUtils.toDate(new Long(result[2]));//token生成的时间
                if (DateUtils.addHours(start, effectHour).getTime() < new Date().getTime()) {
                    //登录超时
                    isValid = false;
                }
            } catch (Exception ex) {
                isValid = false;
                log.info(ex.getMessage());
            }
            return isValid;
        }
    }
}
