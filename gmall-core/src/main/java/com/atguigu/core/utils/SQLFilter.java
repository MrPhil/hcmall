/**
 * Copyright (c) 2016-2019 谷粒开源 All rights reserved.
 *
 * https://www.guli.cloud
 *
 * 版权所有，侵权必究！
 */

package com.atguigu.core.utils;

import com.atguigu.core.exception.RRException;
import org.apache.commons.lang.StringUtils;

/**
 * SQL过滤
 *
 * @author Mark sunlightcs@gmail.com
 */
public class SQLFilter {/*mySQLplus提供的工具，防止sql注入。因为有好多字符用户是不能输入的防止他企图sql注入*/

    /**
     * SQL注入过滤
     * @param str  待验证的字符串
     */
    public static String sqlInject(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        //判断是否包含非法字符
        for(String keyword : keywords){
            if(str.indexOf(keyword) != -1){
                throw new RRException("包含非法字符");
            }
        }

        return str;
    }
}
