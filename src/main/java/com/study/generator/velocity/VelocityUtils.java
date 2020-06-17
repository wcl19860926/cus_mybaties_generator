package com.study.generator.velocity;

public class VelocityUtils {

    /**
     * 空字符
     */
    public static final String EMPTY = "";

    /**
     * 判断字符串是否为空
     *
     * @param cs 需要判断字符串
     * @return 判断结果
     */
    public static boolean isEmpty(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否不为空
     *
     * @param cs 需要判断字符串
     * @return 判断结果
     */
    public static boolean isNotEmpty(final CharSequence cs) {
        return !isEmpty(cs);
    }



    public  static  String  getClassName(String  type){
        if (isEmpty(type)) {
            return EMPTY;
        }
        int index  = type.lastIndexOf(".");
        return type.substring(index+1 ,type.length());
    }


    public static void main(String[] args) {

        System.out.println(getClassName("com.study.common.core.mybaties.entity.BaseEntity"));

    }



    public static String firstToLowerCase(String param) {
        if (isEmpty(param)) {
            return EMPTY;
        }
        return param.substring(0, 1).toLowerCase() + param.substring(1);
    }

}
