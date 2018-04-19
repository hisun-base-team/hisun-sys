package com.hisun.saas.sys.taglib.util;

import java.util.Map;

public interface ParameterVisitor {
    String getName();

    boolean support(Map fieldMap, Map.Entry entry);

    Object transform(Object value);
}
