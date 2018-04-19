package com.hisun.saas.sys.admin.report.util;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jamin30 on 2015/10/21.
 */
public class ReportDataSource  implements JRDataSource {
    private int index = -1;

    private List<List<Map>> dataSet = new ArrayList<List<Map>>();//需要解析填充到报表的值的集合

    private List<String> attriButeNameList = new ArrayList<String>();//属性集合

    public List<List<Map>> getDataSet() {
        return dataSet;
    }

    public void setDataSet(List<List<Map>> dataSet) {
        this.dataSet = dataSet;
    }

    public List<String> getAttriButeNameList() {
        return attriButeNameList;
    }

    public void setAttriButeNameList(List<String> attriButeNameList) {
        this.attriButeNameList = attriButeNameList;
    }

    @Override
    public boolean next() throws JRException {
        index++;
        return (index < dataSet.size());
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        List<Map> valueSet = new ArrayList<Map>();//属性对应的值的集合
        Object value = null;//属性对应的具体值
        String filedName = jrField.getName();
        for(String clounName:attriButeNameList)
        {
            if(clounName.equals(filedName))
            {
                valueSet = dataSet.get(index);
                for(Map map:valueSet)
                {
                    value = map.get(clounName);
                    if(!"".equals(value)&&null!=value)
                    {
                        if("id".equals(clounName))
                        {
                            return Integer.parseInt((String.valueOf(value)));
                        }
                        return String.valueOf(value);
                    }
                }
            }
        }
        return value;
    }
}
