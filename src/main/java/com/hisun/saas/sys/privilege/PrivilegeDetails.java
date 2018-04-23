/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.privilege;

import com.google.common.collect.Lists;
import com.hisun.util.StringUtils;

import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class PrivilegeDetails {

    public final static String AND = "and";
    public final static String OR = "or";
    private String logic = AND;
    private List<PrivilegeDetail> privilegeDetails = Lists.newArrayList();

    public PrivilegeDetails() {
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public List<PrivilegeDetail> getPrivilegeDetails() {
        return privilegeDetails;
    }

    public void setPrivilegeDetails(List<PrivilegeDetail> privilegeDetails) {
        this.privilegeDetails = privilegeDetails;
    }

    public void add(PrivilegeDetail privilegeDetail) {
        privilegeDetails.add(privilegeDetail);
    }


    public String getSqlFilterExpress() {
        StringBuffer sb = new StringBuffer();
        int j = 0;
        for (PrivilegeDetail privilegeDetail : privilegeDetails) {
            if (!StringUtils.isEmpty(privilegeDetail.getSqlFilterExpress())){
                if (j == 0) {
                    sb.append(" (").append(privilegeDetail.getSqlFilterExpress()).append(") ");
                } else {
                    sb.append(" ").append(logic).append(" (").append(privilegeDetail.getSqlFilterExpress()).append(")");
                }
                j++;
            }
        }
        return sb.toString();
    }

    public String getHqlFilterExpress() {
        StringBuffer sb = new StringBuffer();
        int j = 0;
        for (PrivilegeDetail privilegeDetail : privilegeDetails) {
            if (!StringUtils.isEmpty(privilegeDetail.getHqlFilterExpress())){
                if (j == 0) {
                    sb.append(" (").append(privilegeDetail.getHqlFilterExpress()).append(") ");
                } else {
                    sb.append(" ").append(logic).append(" (").append(privilegeDetail.getHqlFilterExpress()).append(")");
                }
                j++;
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "PrivilegeDetails{" +
                "logic='" + logic + '\'' +
                ", privilegeDetails=" + privilegeDetails +
                '}';
    }
}
