package com.hisun.saas.sys.admin.communication.vo;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: MailSendVo.java</p>
 * <p>Description: 邮件发送vo</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 *
 * @author Jason
 * @version v0.1
 * @email jason4j@qq.com
 * @date 2015-12-05 10:53
 */
public class MailSendVo {


    protected List<String> to;
    protected Map<String,List<String>> sub;

    public void setTo(List<String> to) {
        this.to = to;
    }

    public List<String> getTo() {
        return to;
    }

    public Map<String, List<String>> getSub() {
        return sub;
    }

    public void setSub(Map<String, List<String>> sub) {
        this.sub = sub;
    }
}
