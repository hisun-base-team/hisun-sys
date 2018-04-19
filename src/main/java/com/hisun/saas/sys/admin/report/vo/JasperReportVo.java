package com.hisun.saas.sys.admin.report.vo;

/**
 * Created by Administrator on 2015/11/13.
 */
public class JasperReportVo {

    private String id;

    /**
     * 报表模板名称
     */
    private String reportTempName;

    /**
     * 报表模板类型
     */
    private String reportTempType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportTempName() {
        return reportTempName;
    }

    public void setReportTempName(String reportTempName) {
        this.reportTempName = reportTempName;
    }

    public String getReportTempType() {
        return reportTempType;
    }

    public void setReportTempType(String reportTempType) {
        this.reportTempType = reportTempType;
    }
}
