/**
 * Copyright (c) 30SAN INFORMATION SYSTEM Co.,Ltd. All Rights Reserved.
 */
package com.hisun.saas.sys.taglib.util.visitors;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:xianghf@30san.com">xianghuafeng</a>
 * @version
 * 
 */
public class DateVisitor extends FieldNameVisitor {
	private static final DateFormat dateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd");

	public Object transform(Object value) {
		if (value == null) {
			return null;
		} else if (value instanceof String) {
			String txt = (String)value;
			String format = "";
			String reg = "^(((1[6-9]|[2-9][0-9])[0-9]{2})-(0?[123456789]|1[0-2])-(0?[1-9]|[12][0-9]|3[01]))$";
			Pattern ms = Pattern.compile(reg);
			Matcher m = ms.matcher(txt);
			if (m.find()) {
				format = "yyyy-MM-dd";
			} else {
				reg = "^(((1[6-9]|[2-9][0-9])[0-9]{2})(0?[123456789]|1[0-2])(0?[1-9]|[12][0-9]|3[01]))$";
				ms = Pattern.compile(reg);
				m = ms.matcher(txt);
				if (m.find()) {
					format = "yyyyMMdd";
				} else {
					reg = "^(((1[6-9]|[2-9][0-9])[0-9]{2})[.](0?[123456789]|1[0-2]))$";
					ms = Pattern.compile(reg);
					m = ms.matcher(txt);
					if (m.find()) {
						format = "yyyy.MM";
					} else {
						reg = "^(((1[6-9]|[2-9][0-9])[0-9]{2})-(0?[123456789]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])) (0?[0-9]|[1-5][0-9]):(0?[0-9]|[1-5][0-9]):(0?[0-9]|[1-5][0-9])$";
						ms = Pattern.compile(reg);
						m = ms.matcher(txt);
						if(m.find()){
							format = "yyyy-MM-dd HH:mm:ss";
						}else{
							return null;
						}
					}
				}
			}
			SimpleDateFormat sf = new SimpleDateFormat(format);
			try {
				Date date = sf.parse(txt);
				return date;
			} catch (Exception e) {
				return null;
			}
		}else{
			return null;
		}
	}

	public String getName() {
		return "date";
	}
}
