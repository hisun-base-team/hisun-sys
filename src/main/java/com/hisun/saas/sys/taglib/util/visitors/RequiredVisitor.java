/**
 * Copyright (c) 30SAN INFORMATION SYSTEM Co.,Ltd. All Rights Reserved.
 */
package com.hisun.saas.sys.taglib.util.visitors;

import java.util.Map;

import com.hisun.saas.sys.taglib.util.ParameterVisitor;
import org.apache.commons.lang.StringUtils;


/**
 * @author <a href="mailto:xianghf@30san.com">xianghuafeng</a>
 * @version
 * 
 */
public class RequiredVisitor implements ParameterVisitor {
	public boolean support(Map fieldMap, Map.Entry entry) {
		final Object value = entry.getValue();
		if (value == null) {
			return false;
		}

		if (value instanceof String[]) {
			String[] strings = (String[]) value;
			for (String string : strings) {
				if (StringUtils.isNotEmpty(string)) {
					return true;
				}
			}
			return false;
		}

		return true;
	}

	public Object transform(Object value) {
		return value;
	}

	public String getName() {
		return "required";
	}
}
