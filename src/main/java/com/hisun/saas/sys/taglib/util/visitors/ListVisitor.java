/**
 * Copyright (c) 30SAN INFORMATION SYSTEM Co.,Ltd. All Rights Reserved.
 */
package com.hisun.saas.sys.taglib.util.visitors;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

/**
 * @author <a href="mailto:xianghf@30san.com">xianghuafeng</a>
 * @version
 * 
 */
public class ListVisitor extends FieldNameVisitor{
    public Object transform(Object value) {
        if (value instanceof String) {
            return Arrays.asList(StringUtils.split((String) value, ','));
        } else if(value instanceof String[]){
            return Arrays.asList((String[]) value);
        }else{
        	return value;
        }

    }

    public String getName() {
        return "list";
    }
}
