/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.admin.log.aop;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public enum LogOperateStatus {
    NORMAL(0,"正常"),EXCEPTION(1,"异常");

    private int status;
    private String description;

    LogOperateStatus (int status,String description){
        this.status = status;
        this.description =description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
