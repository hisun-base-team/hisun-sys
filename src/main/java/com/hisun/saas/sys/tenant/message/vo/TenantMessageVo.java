package com.hisun.saas.sys.tenant.message.vo;

import java.io.Serializable;

/**
 * <p>类名称：TenantMessageVo</p>
 * <p>类描述：</p>
 * <p>公司：湖南海数互联信息技术有限公司</p>
 *
 * @创建人：lihaiming
 * @创建时间：15/11/20 下午4:24
 * @创建人联系方式：lihm_gz@30wish.net
 */
public class TenantMessageVo implements Serializable{

    private static final long serialVersionUID = -2149166619190103605L;

    private String id;//主键

    private boolean noticeEmail ;//是否开启邮件通知

    private boolean noticeSmart ;//是否开启免打扰模式

    private boolean noticeSMS;//是否开启短信通知

    private boolean noticeExpress ;//是否开启每月速递

    //private boolean noticeDailyMail ;//是否开启每日邮件提醒

    //private boolean noticeWeeklyMail ;//是否开启每周邮件提醒

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNoticeEmail() {
        return noticeEmail;
    }

    public void setNoticeEmail(boolean noticeEmail) {
        this.noticeEmail = noticeEmail;
    }

    public boolean isNoticeSmart() {
        return noticeSmart;
    }

    public void setNoticeSmart(boolean noticeSmart) {
        this.noticeSmart = noticeSmart;
    }

    public boolean isNoticeExpress() {
        return noticeExpress;
    }

    public void setNoticeExpress(boolean noticeExpress) {
        this.noticeExpress = noticeExpress;
    }

    public boolean isNoticeSMS() {
        return noticeSMS;
    }

    public void setNoticeSMS(boolean noticeSMS) {
        this.noticeSMS = noticeSMS;
    }

}
