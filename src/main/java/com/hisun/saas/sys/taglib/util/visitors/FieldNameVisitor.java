/**
 * Copyright (c) 30SAN INFORMATION SYSTEM Co.,Ltd. All Rights Reserved.
 */
package com.hisun.saas.sys.taglib.util.visitors;

import java.util.Map;

import com.hisun.saas.sys.taglib.util.ParameterVisitor;
import org.apache.commons.lang.ArrayUtils;


/**
 * @author <a href="mailto:xianghf@30san.com">xianghuafeng</a>
 * @version
 * 
 */
public abstract class FieldNameVisitor implements ParameterVisitor {
	public boolean support(Map fieldMap, Map.Entry entry) {
        String[] fields = (String[]) fieldMap.get(getName());
        return ArrayUtils.contains(fields, entry.getKey());
    }

}
