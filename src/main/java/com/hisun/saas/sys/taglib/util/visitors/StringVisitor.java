/**
 * Copyright (c) 30SAN INFORMATION SYSTEM Co.,Ltd. All Rights Reserved.
 */
package com.hisun.saas.sys.taglib.util.visitors;

import com.hisun.saas.sys.taglib.util.ParameterVisitor;

import java.util.Map;


/**
 * @author <a href="mailto:xianghf@30san.com">xianghuafeng</a>
 * @version
 * 
 */
public class StringVisitor implements ParameterVisitor {
    public boolean support(Map fieldMap, Map.Entry entry) {
        Object value = entry.getValue();
        return value instanceof String[] && ((String[]) value).length == 1;
    }

    public Object transform(Object value) {
        return ((String[]) value)[0].trim();
    }

    public String getName() {
        return "string";
    }
}
