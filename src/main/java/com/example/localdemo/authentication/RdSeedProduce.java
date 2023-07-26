/*
 *   编写日期：2022/6/8 12:00
 *   作者：张赵
 *   说明：
 */

package com.example.localdemo.authentication;

import com.google.common.hash.HashCode;
import java.util.UUID;

public final class RdSeedProduce {
    /**
     * @return guid生成种子
     */
    public static int Produce() {
        return UUID.randomUUID().hashCode();
    }

    /**
     * @return 使用RNGCryptoServiceProvider生成种子
     */
    public static HashCode GetRandomSeed() {
        return HashCode.fromLong(System.currentTimeMillis());
    }
}
