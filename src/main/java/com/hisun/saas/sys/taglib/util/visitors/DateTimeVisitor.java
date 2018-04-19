package com.hisun.saas.sys.taglib.util.visitors;

import java.text.DateFormat;
import java.text.ParseException;

import org.apache.commons.lang.StringUtils;

public class DateTimeVisitor extends FieldNameVisitor{
    private static final DateFormat dateFormatter = DateFormat.getDateTimeInstance();

    public String getName() {
        return "datetime";
    }

    public Object transform(Object value) {
        try {
            String strDttemp = (String) value;
            if (StringUtils.split(strDttemp, ':').length < 3) {
                strDttemp += ":00";
            }
            return dateFormatter.parse(strDttemp);
        } catch (ParseException e) {
            throw new RuntimeException("Could not parse datetime", e);
        }
    }
}
