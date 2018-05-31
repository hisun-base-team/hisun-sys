/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.exchange;

import com.aspose.cells.*;
import com.hisun.util.AsposeLicenseUtil;
import com.hisun.util.JacksonUtil;
import com.hisun.util.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public abstract class AbsExcelExchange {

    private final static Logger logger = Logger.getLogger(AbsExcelExchange.class);

    //属性字段匹配规则,例如:${a0101}
    protected static String FIELD_REGEXP = "(?<=(?<!\\\\)\\$\\{)(.*?)(?=(?<!\\\\)\\})";
    protected static String FIELD_IMAGE = "#image";
    protected static int FIELD_TYPE_NORMAL = 1;
    protected static int FIELD_TYPE_LIST = 2;
    protected static int FIELD_TYPE_IMAGE = 3;


    public Workbook read(String file) throws Exception {
        InputStream stream = new FileInputStream(new File(file));
        Workbook wb = new Workbook(stream);
        stream.close();
        return wb;
    }

    public void save(Workbook wb, String file) throws Exception {
        wb.save(file);
    }

    public void toExcel(String json, String tmplateFile, String destFile) throws Exception {
        JSONObject jsonObject = new JSONObject(json);
        AsposeLicenseUtil.newInstance().init();
        Workbook workbook = read(tmplateFile);
        WorksheetCollection worksheets = workbook.getWorksheets();
        for (Iterator<Worksheet> iterator = worksheets.iterator(); iterator.hasNext(); ) {
            Worksheet worksheet = iterator.next();
            Cells cells = worksheet.getCells();
            for (Iterator<Cell> cellIterator = cells.iterator(); cellIterator.hasNext(); ) {
                Cell cell = cellIterator.next();
                String value = cell.getStringValue();
                List<String> fields = this.parseField(value);
                String realValue = "";
                if (fields != null) {
                    for (String field : fields) {
                        if (isListField(value)) {
                            int size = getListFieldSize(jsonObject, field);
                            int row = cell.getRow();
                            int column = cell.getColumn();
                            for (int i = 0; i < size; i++) {
                                realValue = getListValue(jsonObject, field, i);
                                cells.get(row + i, column).setValue(realValue);
                            }
                            if(size==0){
                                cell.setValue(realValue);
                            }
                        } else if (isImageField(value)) {

                        } else {
                            realValue = getValue(jsonObject, field);
                            cell.setValue(realValue);
                        }
                    }
                }
            }
        }
        workbook.save(destFile);
    }




    public void toExcel(Object object, String tmplateFile, String destFile) throws Exception {
        toExcel(JacksonUtil.nonDefaultMapper().toJson(object), tmplateFile, destFile);
    }

    public void toExcelByManyPojo(List<? extends Object> objects, String tmplateFile, String destFile) throws Exception {
        //多个对象一个Excel
        List<String> jsons = new ArrayList<>();
        for(Object obj : objects){
            String json =  JacksonUtil.nonDefaultMapper().toJson(obj);
            jsons.add(json);
        }
        toExcelByManyJson(jsons,tmplateFile,destFile);
    }

    public void toExcelByManyJson(List<String> jsons, String tmplateFile, String destFile) throws Exception {
        List<JSONObject> jsonObjects = new ArrayList<>();
        for(String json : jsons){
            JSONObject jsonObject = new JSONObject(json);
            jsonObjects.add(jsonObject);
        }
        AsposeLicenseUtil.newInstance().init();
        Workbook workbook = read(tmplateFile);
        WorksheetCollection worksheets = workbook.getWorksheets();
        for (Iterator<Worksheet> iterator = worksheets.iterator(); iterator.hasNext(); ) {
            Worksheet worksheet = iterator.next();
            Cells cells = worksheet.getCells();
            for (Iterator<Cell> cellIterator = cells.iterator(); cellIterator.hasNext(); ) {
                Cell cell = cellIterator.next();
                String value = cell.getStringValue();
                List<String> fields = this.parseField(value);
                String realValue = "";
                if (fields != null) {
                    for (String field : fields) {
                        if (isListField(value)) {
                        } else if (isImageField(value)) {
                        } else {
                            int row = cell.getRow();
                            int column = cell.getColumn();
                            for(int i = 0;i<jsonObjects.size();i++){
                                realValue = getValue(jsonObjects.get(i), field);
                                cells.get(row+i,column).setValue(realValue);
                            }
                            if(jsonObjects.size()==0){
                                cell.setValue(realValue);
                            }
                        }
                    }
                }
            }
        }
        workbook.save(destFile);
    }



    public JSONObject fromExcel(String tmplateFile, String srcFile) throws Exception {
        JSONObject jsonObject = new JSONObject();
        AsposeLicenseUtil.newInstance().init();
        //模板Excel
        Workbook tpltWorkbook = read(tmplateFile);
        WorksheetCollection tpltWorksheets = tpltWorkbook.getWorksheets();
        //数据Excel
        Workbook srcWorkbook = read(srcFile);
        WorksheetCollection srcWorksheets = srcWorkbook.getWorksheets();
        int sheetIndex = 0;
        for (Iterator<Worksheet> iterator = tpltWorksheets.iterator(); iterator.hasNext(); ) {
            Worksheet tpltWorksheet = iterator.next();
            Cells tpltCells = tpltWorksheet.getCells();
            for (Iterator<Cell> tpltCellIterator = tpltCells.iterator(); tpltCellIterator.hasNext(); ) {
                Cell tpltCell = tpltCellIterator.next();
                String value = tpltCell.getStringValue();
                List<String> fields = this.parseField(value);
                String realValue = "";
                if (fields != null) {
                    for (String field : fields) {
                        if (isListField(value)) {
                            Cells srcCells = srcWorksheets.get(sheetIndex).getCells();
                            setListValue(jsonObject,field,tpltCell,srcCells);
                        } else if (isImageField(value)) {

                        } else {
                            realValue = srcWorksheets.get(sheetIndex).getCells().get(tpltCell.getRow(), tpltCell.getColumn()).getStringValue();
                            setValue(jsonObject,field,realValue);
                        }
                    }
                }
            }
            sheetIndex++;
        }
        return jsonObject;
    }

    public Object fromExcel(Class clazz, String tmplateFile, String srcFile) throws Exception {
        JSONObject jsonObject = fromExcel(tmplateFile,srcFile);
        return JacksonUtil.nonDefaultMapper().fromJson(jsonObject.toString(), clazz);
    }

    public List<Object> fromExcel2ManyPojo(Class clazz, String tmplateFile, String srcFile) throws Exception {
        List<Object> objects = new ArrayList<>();
        List<JSONObject> jsonObjects = new ArrayList<>();
        AsposeLicenseUtil.newInstance().init();
        //模板Excel
        Workbook tpltWorkbook = read(tmplateFile);
        WorksheetCollection tpltWorksheets = tpltWorkbook.getWorksheets();
        //数据Excel
        Workbook srcWorkbook = read(srcFile);
        WorksheetCollection srcWorksheets = srcWorkbook.getWorksheets();
        int sheetIndex = 0;
        for (Iterator<Worksheet> iterator = tpltWorksheets.iterator(); iterator.hasNext(); ) {
            Worksheet tpltWorksheet = iterator.next();
            Cells tpltCells = tpltWorksheet.getCells();
            Cells srcCells = srcWorksheets.get(sheetIndex).getCells();
            for (Iterator<Cell> tpltCellIterator = tpltCells.iterator(); tpltCellIterator.hasNext(); ) {
                Cell tpltCell = tpltCellIterator.next();
                String value = tpltCell.getStringValue();
                List<String> fields = this.parseField(value);
                if (fields != null) {
                    for (String field : fields) {
                        if (isListField(value)) {
                        } else if (isImageField(value)) {
                        } else {
                            setValue(jsonObjects,field,tpltCell,srcCells);
                        }
                    }
                }
            }
            sheetIndex++;
        }

        for(JSONObject jsonObject : jsonObjects){
            objects.add(JacksonUtil.nonDefaultMapper().fromJson(jsonObject.toString(), clazz));
        }
        return objects;
    }



    protected List<String> parseField(String src) {
        Matcher matcher = Pattern.compile(FIELD_REGEXP).matcher(src);
        List<String> fields = new ArrayList<String>();
        while (matcher.find()) {
            fields.add(matcher.group());
        }
        return fields;
    }

    protected boolean isListField(String field) {
        int dot = field.indexOf(".");//判断是否存在dot,如果是则认为是list
        if (dot > -1) {
            return true;
        } else {
            return false;
        }
    }

    protected int getListFieldSize(JSONObject json, String field) {
        int size = 0;
        int dot = field.indexOf(".");
        String listFieldName = field.substring(0, dot);
        try {
            JSONArray jsonArray = json.getJSONArray(listFieldName);
            size = jsonArray.length();
        } catch (Exception e) {
        }
        return size;
    }

    protected boolean isImageField(String field) {
        if (field.startsWith(FIELD_IMAGE)) {
            return true;
        }
        return false;
    }

    protected String getValue(JSONObject jsonObject, String field) {
        String value = "";
        try {
            value = jsonObject.get(field).toString();
        } catch (Exception e) {
        }
        return value;
    }

    protected void setValue(JSONObject jsonObject,String field,String value){
        jsonObject.put(field, value);
    }

    protected String getListValue(JSONObject jsonObject, String field, int index) {
        String value = "";
        try {
            int dot = field.indexOf(".");
            String listFieldName = field.substring(0, dot);
            String fieldName = field.substring(dot + 1, field.length());
            JSONArray jsonArray = jsonObject.getJSONArray(listFieldName);
            value = jsonArray.getJSONObject(index).get(fieldName).toString();
        } catch (Exception e) {
        }
        return value;
    }


    protected void setListValue(JSONObject jsonObject,String field,Cell tpltCell,Cells srcCells){
        int dot = field.indexOf(".");
        String listFieldName = field.substring(0, dot);
        String fieldName = field.substring(dot + 1, field.length());
        String realValue = "";
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray(listFieldName);
        } catch (Exception ex) {
        }
        if (jsonArray == null) {
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (Iterator<Cell> srcCellIterator = srcCells.iterator(); srcCellIterator.hasNext(); ) {
                Cell srcCell = srcCellIterator.next();
                if (srcCell.getRow()>=tpltCell.getRow()
                        &&srcCell.getColumn() == tpltCell.getColumn()) {
                    realValue = StringUtils.trimNull2Empty(srcCell.getStringValue());
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put(fieldName,realValue);
                    jsonObjectList.add(jsonObject1);
                }
            }
            jsonObject.put(listFieldName, jsonObjectList);
        } else {
            int listIndex = 0;
            for (Iterator<Cell> srcCellIterator = srcCells.iterator(); srcCellIterator.hasNext(); ) {
                Cell srcCell = srcCellIterator.next();
                if (srcCell.getRow()>=tpltCell.getRow()
                        &&srcCell.getColumn() == tpltCell.getColumn()) {
                    if(jsonArray.length()>listIndex){
                        realValue = StringUtils.trimNull2Empty(srcCell.getStringValue());
                        jsonArray.getJSONObject(listIndex).put(fieldName,realValue);
                    }
                    listIndex++;
                }
            }
        }
    }

    protected void setValue(List<JSONObject> jsonObjects,String field,Cell tpltCell,Cells srcCells){
        String realValue = "";
        if(jsonObjects.size()==0){
            for (Iterator<Cell> srcCellIterator = srcCells.iterator(); srcCellIterator.hasNext();) {
                Cell srcCell = srcCellIterator.next();
                if (srcCell.getRow()>=tpltCell.getRow()
                        &&srcCell.getColumn() == tpltCell.getColumn()) {
                    realValue = StringUtils.trimNull2Empty(srcCell.getStringValue());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put(field,realValue);
                    jsonObjects.add(jsonObject);
                }
            }

        }else{
            int index =0;
            for (Iterator<Cell> srcCellIterator = srcCells.iterator(); srcCellIterator.hasNext();) {
                Cell srcCell = srcCellIterator.next();
                if (srcCell.getRow()>=tpltCell.getRow()
                        &&srcCell.getColumn() == tpltCell.getColumn()) {
                    realValue = StringUtils.trimNull2Empty(srcCell.getStringValue());
                    if(index<=(jsonObjects.size()-1)){
                        JSONObject jsonObject = jsonObjects.get(index);
                        jsonObject.put(field,realValue);
                    }
                    index++;
                }
            }
        }
    }



    public void insertRows(Cells cells ,int rowIndex,int totalRows){
        cells.insertRows(rowIndex,totalRows);
    }

    public void deleteRows(Cells cells,int rowIndex,int totalRows){
        cells.deleteRows(rowIndex,totalRows);
    }


//    public static void main(String[] args)throws Exception{
//        AsposeLicenseUtil.newInstance().init();
//        AbsExcelExchange absExcelExchange = new AbsExcelExchange();
//        Workbook workbook = absExcelExchange.read("/Users/zhouying/Desktop/new.xlsx");
//        Worksheet worksheet = workbook.getWorksheets().get(1);
//        worksheet.getCells().insertRows(5,10);
//        workbook.save("/Users/zhouying/Desktop/aa.xlsx");
//    }

    public JSONObject fromExcelWithLines(String tmplateFile, String srcFile) throws Exception {
        JSONObject jsonObject = new JSONObject();
        AsposeLicenseUtil.newInstance().init();
        //模板Excel
        Workbook tpltWorkbook = read(tmplateFile);
        WorksheetCollection tpltWorksheets = tpltWorkbook.getWorksheets();
        //数据Excel
        Workbook srcWorkbook = read(srcFile);
        WorksheetCollection srcWorksheets = srcWorkbook.getWorksheets();
        int sheetIndex = 0;
        for (Iterator<Worksheet> iterator = tpltWorksheets.iterator(); iterator.hasNext(); ) {
            Worksheet tpltWorksheet = iterator.next();
            Cells tpltCells = tpltWorksheet.getCells();
            for (Iterator<Cell> tpltCellIterator = tpltCells.iterator(); tpltCellIterator.hasNext(); ) {
                Cell tpltCell = tpltCellIterator.next();
                String value = tpltCell.getStringValue();
                List<String> fields = this.parseField(value);
                String realValue = "";
                if (fields != null) {
                    for (String field : fields) {
                        if (isListField(value)) {
                            Cells srcCells = srcWorksheets.get(sheetIndex).getCells();
                            setListValueWithLines(jsonObject,field,tpltCell,srcCells);
                        } else if (isImageField(value)) {

                        } else {
                            realValue = srcWorksheets.get(sheetIndex).getCells().get(tpltCell.getRow(), tpltCell.getColumn()).getStringValue();
                            setValue(jsonObject,field,realValue);
                        }
                    }
                }
            }
            sheetIndex++;
        }
        return jsonObject;
    }

    public Object fromExcelWithLines(Class clazz, String tmplateFile, String srcFile) throws Exception {
        JSONObject jsonObject = fromExcelWithLines(tmplateFile,srcFile);
        return JacksonUtil.nonDefaultMapper().fromJson(jsonObject.toString(), clazz);
    }

    protected void setListValueWithLines(JSONObject jsonObject,String field,Cell tpltCell,Cells srcCells){
        int dot = field.indexOf(".");
        String listFieldName = field.substring(0, dot);
        String fieldName = field.substring(dot + 1, field.length());
        String realValue = "";
        JSONArray jsonArray = null;
        try {
            jsonArray = jsonObject.getJSONArray(listFieldName);
        } catch (Exception ex) {
        }
        if (jsonArray == null) {
            List<JSONObject> jsonObjectList = new ArrayList<>();
            for (Iterator<Cell> srcCellIterator = srcCells.iterator(); srcCellIterator.hasNext(); ) {
                Cell srcCell = srcCellIterator.next();
                if (srcCell.getRow()>=tpltCell.getRow()
                        &&srcCell.getColumn() == tpltCell.getColumn()) {
                    realValue = StringUtils.trimNull2Empty(srcCell.getStringValue());
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put(fieldName,realValue);
                    jsonObject1.put("row",srcCell.getRow()+1);
                    jsonObjectList.add(jsonObject1);
                }
            }
            jsonObject.put(listFieldName, jsonObjectList);
        } else {
            int listIndex = 0;
            for (Iterator<Cell> srcCellIterator = srcCells.iterator(); srcCellIterator.hasNext(); ) {
                Cell srcCell = srcCellIterator.next();
                if (srcCell.getRow()>=tpltCell.getRow()
                        &&srcCell.getColumn() == tpltCell.getColumn()) {
                    if(jsonArray.length()>listIndex){
//                        realValue = StringUtils.trimNull2Empty(srcCell.getStringValue());
//                        jsonArray.getJSONObject(listIndex).put(fieldName,realValue);
                        for(int i=0;i<jsonArray.length();i++){
                            int jsonObjectRow = (int) jsonArray.getJSONObject(i).get("row")-1;
                            int srcRow = srcCell.getRow();
                            if(srcRow == jsonObjectRow){
                                realValue = StringUtils.trimNull2Empty(srcCell.getStringValue());
                                jsonArray.getJSONObject(i).put(fieldName,realValue);
                            }
                        }
                    }
                    listIndex++;
                }
            }
        }
    }

    public List<Object> fromExcel2ManyPojoWithLines(Class clazz, String tmplateFile, String srcFile) throws Exception {
        List<Object> objects = new ArrayList<>();
        List<JSONObject> jsonObjects = new ArrayList<>();
        AsposeLicenseUtil.newInstance().init();
        //模板Excel
        Workbook tpltWorkbook = read(tmplateFile);
        WorksheetCollection tpltWorksheets = tpltWorkbook.getWorksheets();
        //数据Excel
        Workbook srcWorkbook = read(srcFile);
        WorksheetCollection srcWorksheets = srcWorkbook.getWorksheets();
        int sheetIndex = 0;
        for (Iterator<Worksheet> iterator = tpltWorksheets.iterator(); iterator.hasNext(); ) {
            Worksheet tpltWorksheet = iterator.next();
            Cells tpltCells = tpltWorksheet.getCells();
            Cells srcCells = srcWorksheets.get(sheetIndex).getCells();
            for (Iterator<Cell> tpltCellIterator = tpltCells.iterator(); tpltCellIterator.hasNext(); ) {
                Cell tpltCell = tpltCellIterator.next();
                String value = tpltCell.getStringValue();
                List<String> fields = this.parseField(value);
                if (fields != null) {
                    for (String field : fields) {
                        if (isListField(value)) {
                        } else if (isImageField(value)) {
                        } else {
                            setValueWithLines(jsonObjects,field,tpltCell,srcCells);
                        }
                    }
                }
            }
            sheetIndex++;
        }

        for(JSONObject jsonObject : jsonObjects){
            objects.add(JacksonUtil.nonDefaultMapper().fromJson(jsonObject.toString(), clazz));
        }
        return objects;
    }

    protected void setValueWithLines(List<JSONObject> jsonObjects,String field,Cell tpltCell,Cells srcCells){
        String realValue = "";
        if(jsonObjects.size()==0){
            for (Iterator<Cell> srcCellIterator = srcCells.iterator(); srcCellIterator.hasNext();) {
                Cell srcCell = srcCellIterator.next();
                if (srcCell.getRow()>=tpltCell.getRow()
                        &&srcCell.getColumn() == tpltCell.getColumn()) {
                    realValue = StringUtils.trimNull2Empty(srcCell.getStringValue());
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("row",srcCell.getRow()+1);
                    jsonObject.put(field,realValue);
                    jsonObjects.add(jsonObject);
                }
            }

        }else{
            int index =0;
            for (Iterator<Cell> srcCellIterator = srcCells.iterator(); srcCellIterator.hasNext();) {
                Cell srcCell = srcCellIterator.next();
                if (srcCell.getRow()>=tpltCell.getRow()
                        &&srcCell.getColumn() == tpltCell.getColumn()) {
                    realValue = StringUtils.trimNull2Empty(srcCell.getStringValue());
                    if(index<=(jsonObjects.size()-1)){
                        for(int i=0;i<jsonObjects.size();i++){
                            int jsonObjectRow = (int) jsonObjects.get(i).get("row")-1;
                            int srcRow = srcCell.getRow();
                            if(srcRow == jsonObjectRow){
                                realValue = StringUtils.trimNull2Empty(srcCell.getStringValue());
                                jsonObjects.get(i).put(field,realValue);
                            }
                        }
//                        JSONObject jsonObject = jsonObjects.get(index);
//                        jsonObject.put(field,realValue);
                    }
                    index++;
                }
            }
        }
    }
}
