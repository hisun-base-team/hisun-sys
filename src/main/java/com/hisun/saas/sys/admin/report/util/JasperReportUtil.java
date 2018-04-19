package com.hisun.saas.sys.admin.report.util;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * Created by jamin30 on 2015/10/22.
 */
public class JasperReportUtil {

    /**
     * 获取json对应的数据
     * @param jsonStr
     * @return
     */
    public static Map<String,Object> getJsonValue(String jsonStr)
    {
        Map<String,Object> objectMap = new HashMap<String,Object>();
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Iterator iter = jsonObject.keySet().iterator();
        while (iter.hasNext())
        {
            String key = (String) iter.next();
            Object value = jsonObject.getString(key);
            objectMap.put(key,value);
        }
            return objectMap;
    }

    /**
     * 获取需要填充到报表的数据源值
     * @param jsonStr
     * @return
     */
    public static List<Object> jsonToArray(String jsonStr)
    {
        List<Object> listAll = new ArrayList<Object>();
        List<List<Map>> listValue = new ArrayList<List<Map>>();//存放值的所有list集合
        List<String> cloumList = new ArrayList<String>();
        List<Map> listMap = null;
        Map<String,Object> map = null;
        JSONObject jsonObjectValue = null;
        JSONObject jsonObject = JSONObject.fromObject(jsonStr);
        Iterator iter = jsonObject.keySet().iterator();
        while (iter.hasNext())
        {
            String key = (String) iter.next();
            Object value = jsonObject.getString(key);
            if("data".equals(key))
            {
                JSONArray jsonArray = JSONArray.fromObject(value);
                for(Object o :jsonArray){
                    listMap = new ArrayList<Map>();
                    jsonObjectValue =JSONObject.fromObject(o);
                    Iterator it = jsonObjectValue.keySet().iterator();
                    while(it.hasNext())
                    {
                        map = new HashMap<String,Object>();
                        String keys = (String)it.next();
                        Object values = jsonObjectValue.getString(keys);
                        map.put(keys, values);
                        listMap.add(map);
                        cloumList.add(keys);
                    }
                    listValue.add(listMap);
                }
            }
        }
        listAll.add(cloumList);
        listAll.add(listValue);
        return listAll;
    }

    /**
     * 获取json对应的数据
     * @param data
     * @return
     */
    public static Map<String,Object> getParameters(Object data)
    {
        Map<String,Object> parameters = new HashMap<String,Object>();
        if(data instanceof Map){
            String mainDsKey = "";
            Map<String,Object> dataMap = (Map)data;
            for(Map.Entry<String,Object> entry : dataMap.entrySet()){
                String key = entry.getKey();
                Object value = entry.getValue();
                if(key.equals("mainDs")){
                    mainDsKey = value.toString();
                }else{
                    if(value instanceof Map){

                    }else if(value instanceof List){
                        JRBeanCollectionDataSource dsData = new JRBeanCollectionDataSource((List)value);
                        parameters.put(key, dsData);
                    }else{
                        parameters.put(key,value);
                    }
                }
            }
            parameters.remove(mainDsKey);
        }else if(data instanceof JSONObject){
            String mainDsKey = "";
            JSONObject obj = (JSONObject)data;
            Iterator it = obj.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                if(key.equals("mainDs")){
                    mainDsKey = obj.getString(key);
                }else{
                    Object value = obj.get(key);
                    if(value instanceof JSONArray){
                        JSONArray array = (JSONArray) value;
                        Collection list = JSONArray.toCollection(array);
                        JRBeanCollectionDataSource dsData = new JRBeanCollectionDataSource(list);
                        parameters.put(key, dsData);
                    }else if(value instanceof String){
                        parameters.put(key, obj.getString(key));
                    }
                }
            }
            parameters.remove(mainDsKey);
        }else if(data instanceof String){
            String mainDsKey = "";
            JSONObject obj = JSONObject.fromObject(data);
            Iterator it = obj.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                if(key.equals("mainDs")){
                    mainDsKey = obj.getString(key);
                }else{
                    Object value = obj.get(key);
                    if(value instanceof JSONArray){
                        JSONArray array = (JSONArray) value;
                        Collection list = JSONArray.toCollection(array);
                        JRBeanCollectionDataSource dsData = new JRBeanCollectionDataSource(list);
                        parameters.put(key, dsData);
                    }else if(value instanceof String){
                        parameters.put(key, obj.getString(key));
                    }
                }
            }
            parameters.remove(mainDsKey);
        }
        return parameters;
    }

    public static JRBeanCollectionDataSource getMainDs(Object data) {
        JRBeanCollectionDataSource dsData = null;
        if(data instanceof Map){
            Map<String,Object> dataMap = (Map)data;
            for(Map.Entry<String,Object> entry : dataMap.entrySet()){
                String key = entry.getKey();
                Object value = entry.getValue();
                if(key.equals("mainDs")){
                    String mainDsKey = (String)value;
                    Object mainDs = dataMap.get(mainDsKey);
                    if(mainDs instanceof List){
                        dsData = new JRBeanCollectionDataSource((List)mainDs);
                        break;
                    }
                }
            }
        }else if(data instanceof JSONObject){
            JSONObject obj = (JSONObject)data;
            Iterator it = obj.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                if(key.equals("mainDs")){
                    Object value = obj.get(key);
                    if(value instanceof JSONArray){
                        JSONArray array = (JSONArray) value;
                        Collection list = JSONArray.toCollection(array);
                        dsData = new JRBeanCollectionDataSource(list);
                    }
                    break;
                }
            }
        }else if(data instanceof String){
            JSONObject obj = JSONObject.fromObject(data);
            Iterator it = obj.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                if(key.equals("mainDs")){
                    String tempKey = obj.getString(key);
                    Object value = obj.get(tempKey);
                    if(value instanceof JSONArray){
                        JSONArray array = (JSONArray) value;
                        Collection list = JSONArray.toCollection(array);
                        dsData = new JRBeanCollectionDataSource(list);
                    }
                    break;
                }
            }
        }
        return dsData;
    }
}
