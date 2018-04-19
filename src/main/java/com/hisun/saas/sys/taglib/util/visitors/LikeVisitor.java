/**
 * Copyright (c) 30SAN INFORMATION SYSTEM Co.,Ltd. All Rights Reserved.
 */
package com.hisun.saas.sys.taglib.util.visitors;

/**
 * @author <a href="mailto:xianghf@30san.com">xianghuafeng</a>
 * @version
 * 
 */
public class LikeVisitor extends FieldNameVisitor{
    public Object transform(Object value) {
        return "%" + ((String) value).trim() + "%";
    }

    public String getName() {
        return "like";
    }
}
