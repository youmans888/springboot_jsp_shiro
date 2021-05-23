package com.baizhi.springboot_jsp_shiro.utils;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.util.Random;

public class SaltUtils {
    /**
     *  生成salt 的静态方法
     * @param n
     * @return
     */
    public static String getSalt(int n){
        char[] chars = "ABCDEFGHIJKLMNOBQRSTUVWXYZabcdefghijklmnobqrstuvwxyz0123456789!@#$%^&*()".toCharArray();
        StringBuilder sb = new StringBuilder() ;
        for (int i = 0; i < n; i++) {
            char aChar=chars[new Random().nextInt(chars.length)] ;
            sb.append(aChar) ;
        }
        return sb.toString() ;
    }

    public static void main(String[] args) {
        System.out.println(getSalt(5));
    }
}
