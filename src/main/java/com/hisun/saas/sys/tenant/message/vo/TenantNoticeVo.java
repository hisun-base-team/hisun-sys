package com.hisun.saas.sys.tenant.message.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>类名称：TenantNoticeVo</p>
 * <p>类描述：</p>
 * <p>公司：湖南海数互联信息技术有限公司</p>
 *
 * @创建人：lihaiming
 * @创建时间：15/11/20 下午4:24
 * @创建人联系方式：lihm_gz@30wish.net
 */
public class TenantNoticeVo implements Serializable{

    private static final long serialVersionUID = -3739429195758865182L;

    private String id;

    private String noticeTitle;//通知标题

    private String noticeContent;//通知内容

    private short noticeLevel;//通知等级

    private short pushWay;//推送方式  0'短信',1'邮件',2'在线'

    private boolean status; //公告状态 true显示,false不显示

    private Date startDate =  new Date();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeContent() {
        return noticeContent;
    }

    public void setNoticeContent(String noticeContent) {
        this.noticeContent = noticeContent;
    }

    public short getNoticeLevel() {
        return noticeLevel;
    }

    public void setNoticeLevel(short noticeLevel) {
        this.noticeLevel = noticeLevel;
    }

    public short getPushWay() {
        return pushWay;
    }

    public void setPushWay(short pushWay) {
        this.pushWay = pushWay;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

}
