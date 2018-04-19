/**
 * Copyright (c) 30SAN INFORMATION SYSTEM Co.,Ltd. All Rights Reserved.
 */
package com.hisun.saas.sys.taglib.util.visitors;

import org.apache.commons.lang.StringUtils;

/**
 * @author <a href="mailto:xianghf@30san.com">xianghuafeng</a>
 * @version
 * 
 */
public class BooleanVisitor extends FieldNameVisitor{
    public Object transform(Object value) {
        if (StringUtils.isNumeric((String) value)) {
            if (StringUtils.equals((String) value, "1")) {
                return new Boolean(true);
            } else {
                return new Boolean(false);
            }
        } else {
            return Boolean.valueOf((String) value);
        }
    }

    public String getName() {
        return "bool";
    }
}
