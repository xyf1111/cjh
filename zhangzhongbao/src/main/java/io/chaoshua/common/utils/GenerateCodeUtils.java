package io.chaoshua.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 *  生成编码类
 *
 * @author dws
 * @date 2018-04-19 15:06
 */
public class GenerateCodeUtils {
    private final static char[] NUMBERS = "0123456789".toCharArray();
    private final static char[] LETTERS = "QWERTYUIOPASDFGHJKLZXCVBNM".toCharArray();
    private final static char[] MIX_NUMBERS_AND_LETTERS = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789".toCharArray();

    public final static int MODE_NUMBER = 0;
    public final static int MODE_LOWER_STR = 1;
    public final static int MODE_UPPER_STR = 2;
    public final static int MODE_STR = 3;
    public final static int MODE_MIX = 4;

    /**
     * 生成订单编号
     *
     * @return
     */
    public static String getMissionCode(Long taskId) {
        StringBuilder stringBuilder = new StringBuilder(taskId.toString());
        stringBuilder.append("_");
        stringBuilder.append(Calendar.getInstance().getTimeInMillis());
        //stringBuilder.append((Math.random() * 9 + 1) * 10000);
        return stringBuilder.toString();
    }

    /**
     * 生成带前缀的字符串，格式为前缀+yyyyMMddHHmmssSSS+随机字符串
     * 如果长度超过count，将会在保留前缀的情况下压缩其余部分
     *
     * @param prefix 前缀
     * @param count  长度
     * @param mode   模式
     * @return
     */
    public static String generateFormatWithPrefix(String prefix, int count, int mode) {
        return generateFormat(prefix, null, count, mode);
    }

    /**
     * 生成带后缀的字符串，格式为yyyyMMddHHmmssSSS+随机字符串+后缀
     * 如果长度超过count，将会在保留后缀的情况下压缩其余部分
     *
     * @param suffix 后缀
     * @param count  长度
     * @param mode   模式
     * @return
     */
    public static String generateFormatWithSuffix(String suffix, int count, int mode) {
        return generateFormat(null, suffix, count, mode);
    }

    /**
     * 生成不带前后缀的字符串，格式为yyyyMMddHHmmssSSS+随机字符串，长度为count
     *
     * @param count 长度
     * @param mode  模式
     * @return
     */
    public static String generateFormat(int count, int mode) {
        return generateFormat(null, null, count, mode);
    }

    /**
     * 生成格式化的字符串，格式为前缀+yyyyMMddHHmmssSSS(17位)+中间随机字符串+后缀。
     * 如果前缀+后缀+中间字符串的长度超过count，将会压缩中间字符串的长度来满足count
     *
     * @param prefix 字符串前缀
     * @param suffix 字符串后缀
     * @param count  生成字符串的长度
     * @param mode   模式
     * @return
     */
    public static String generateFormat(String prefix, String suffix, int count, int mode) {
        if (count <= 17) {
            count = 18;
        }
        int prefixLen = 0;
        int suffixLen = 0;
        StringBuilder sb = new StringBuilder();
        if (prefix != null && (!"".equals(prefix))) {
            prefixLen = prefix.length();
            sb.append(prefix);
        }
        if (suffix != null && (!"".equals(suffix))) {
            suffixLen = suffix.length();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String date = sdf.format(new Date());

        int len = count - prefixLen - suffixLen - date.length();
        if (len > 0) {
            switch (mode) {
                case MODE_NUMBER:
                    date = date + randomNumber(len);
                    break;
                case MODE_LOWER_STR:
                    date = date + randomLowerStr(len);
                    break;
                case MODE_UPPER_STR:
                    date = date + randomUpperStr(len);
                    break;
                case MODE_STR:
                    date = date + randomStr(len);
                    break;
                case MODE_MIX:
                    date = date + randomMix(len);
                    break;
                default:
                    date = date + randomNumber(len);
                    break;
            }
        }
        sb.append(date.substring(0, count - prefixLen - suffixLen));
        if (suffixLen > 0) {
            sb.append(suffix);
        }
        return sb.toString();
    }

    /**
     * 生成写字母和数字随机字符串
     *
     * @param count
     * @return
     */
    public static String randomMix(int count) {
        return generator(count, MIX_NUMBERS_AND_LETTERS);
    }

    /**
     * 生成大小写混合字母随机字符串
     *
     * @param count
     * @return
     */
    public static String randomStr(int count) {
        return generator(count, LETTERS);
    }

    /**
     * 生成纯大写字母随机字符串
     *
     * @param count
     * @return
     */
    public static String randomUpperStr(int count) {
        return generator(count, LETTERS).toUpperCase();
    }

    /**
     * 生成纯数字随机字符串
     *
     * @param count
     * @return
     */
    public static String randomNumber(int count) {
        return generator(count, NUMBERS);
    }

    /**
     * 生成纯小写字母随机字符串
     *
     * @param count
     * @return
     */
    public static String randomLowerStr(int count) {
        return generator(count, LETTERS).toLowerCase();
    }

    /**
     * 生成器
     *
     * @param count
     * @param arr
     * @return
     */
    private static String generator(int count, char[] arr) {
        if (count <= 0) {
            count = 6;
        }
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            int index = random.nextInt(arr.length);
            sb.append(arr[index]);
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        System.out.println(generateFormatWithPrefix("S", 20, MODE_NUMBER));
        System.out.println(generateFormatWithSuffix("S", 20, MODE_NUMBER));
        System.out.println(generateFormat(20, MODE_NUMBER));
        System.out.println(randomMix(6));
    }
}
