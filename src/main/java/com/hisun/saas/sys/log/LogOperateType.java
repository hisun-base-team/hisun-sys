/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.log;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public enum LogOperateType {

    ADD(1,"新增"),UPDATE(2,"修改"),DELETE(3,"删除"),QUERY(4,"查询"),LOGIN(5,"登录"),LOGOUT(6,"退出");

    private int type;
    private String description;


    LogOperateType(int type, String description){
        this.type = type;
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }


    public static LogOperateType getEnum(int type){
        for(LogOperateType l:LogOperateType.values()){
            if(l.type==type){
                return l;
            }
        }
        return null;
    }

}
