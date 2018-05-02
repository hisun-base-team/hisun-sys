/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.privilege;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class PrivilegeUtil {

    public static String toSqlInParameterExpress(String[] values){
        StringBuffer sb = new StringBuffer();
        sb.append("(");
        int i = 0;
        for(String value : values){
            if(i==0){
                sb.append("'").append(value).append("'");
            }else{
                sb.append(",").append("'").append(value).append("'");
            }
            i++;
        }
        sb.append(")");
        return sb.toString();
    }
}
