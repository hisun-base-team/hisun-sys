/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.task;

import com.hisun.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.net.InetAddress;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class AbsTask {

    private final static Logger logger = Logger.getLogger(AbsTask.class);

    @Value("${quartz.on}")
    private boolean on;
    @Value("${quartz.execute.hostname}")
    private String executeHostname;
    //外部程序设置是否运行,默认为true
    private boolean isSet = true;
    private boolean isStart;

    private static String hostname;

    static {
        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public boolean isStart() {
        if (on && (executeHostname.toLowerCase().equals(StringUtils.trimNull2Empty(hostname)))) {
            isStart = isSet;
        } else {
            isStart = false;
        }
        return isStart;
    }

    public void setStart(boolean start) {
        isSet = start;
        isStart = start;
    }

}
