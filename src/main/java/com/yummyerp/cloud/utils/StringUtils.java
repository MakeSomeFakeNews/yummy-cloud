package com.yummyerp.cloud.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     *
     * @param str 需要判断的字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 需要判断的字符串
     * @return 是否不为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为空白
     *
     * @param str 需要判断的字符串
     * @return 是否为空白
     */
    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否不为空白
     *
     * @param str 需要判断的字符串
     * @return 是否不为空白
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 判断对象是否为空
     *
     * @param obj 需要判断的对象
     * @return 是否为空
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return isEmpty((String) obj);
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length() == 0;
        } else if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        } else if (obj instanceof Object[]) {
            return ((Object[]) obj).length == 0;
        }
        return false;
    }

    /**
     * 判断对象是否不为空
     *
     * @param obj 需要判断的对象
     * @return 是否不为空
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 截取指定长度的字符串，避免超长
     *
     * @param str 字符串
     * @param len 长度
     * @return 截取后的字符串
     */
    public static String substring(String str, int len) {
        if (isEmpty(str)) {
            return str;
        }
        return str.length() > len ? str.substring(0, len) : str;
    }

    /**
     * 格式化字符串，使用 {} 作为占位符
     *
     * @param template 模板字符串
     * @param args 参数列表
     * @return 格式化后的字符串
     */
    public static String format(String template, Object... args) {
        if (isEmpty(template) || args == null || args.length == 0) {
            return template;
        }
        
        StringBuilder sb = new StringBuilder(template.length() + 50);
        
        int templateIndex = 0;
        int argIndex = 0;
        while (templateIndex < template.length()) {
            int start = template.indexOf("{}", templateIndex);
            if (start < 0) {
                sb.append(template.substring(templateIndex));
                break;
            }
            
            sb.append(template, templateIndex, start);
            if (argIndex < args.length) {
                sb.append(args[argIndex++]);
            } else {
                sb.append("{}");
            }
            
            templateIndex = start + 2;
        }
        
        return sb.toString();
    }
} 