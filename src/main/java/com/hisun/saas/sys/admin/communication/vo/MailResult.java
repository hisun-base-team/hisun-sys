package com.hisun.saas.sys.admin.communication.vo;

import java.util.List;

/**
 * <p>Title: MailResult.java</p>
 * <p>Description: 邮件api调用vo</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 *
 * @author Jason
 * @version v0.1
 * @email jason4j@qq.com
 * @date 2015-12-04 16:08
 */
public class MailResult {
    public final static String SUCCESS = "success";
    private String message;
    private int addCount;
    private int updateCount;
    private int delCount;
    private List<String> errors;
    private List<String> email_id_list;
    private List<Integer> mail_list_task_id_list;
    private List<TemplateListEntity> templateList;

    public void setMessage(String message) {
        this.message = message;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }


    public void setEmail_id_list(List<String> email_id_list) {
        this.email_id_list = email_id_list;
    }

    public List<String> getEmail_id_list() {
        return email_id_list;
    }

    public void setMail_list_task_id_list(List<Integer> mail_list_task_id_list) {
        this.mail_list_task_id_list = mail_list_task_id_list;
    }

    public List<Integer> getMail_list_task_id_list() {
        return mail_list_task_id_list;
    }

    public void setTemplateList(List<TemplateListEntity> templateList) {
        this.templateList = templateList;
    }

    public List<TemplateListEntity> getTemplateList() {
        return templateList;
    }

    public int getAddCount() {
        return addCount;
    }

    public void setAddCount(int addCount) {
        this.addCount = addCount;
    }

    public int getUpdateCount() {
        return updateCount;
    }

    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }

    public int getDelCount() {
        return delCount;
    }

    public void setDelCount(int delCount) {
        this.delCount = delCount;
    }

    public static class TemplateListEntity {
        private String invoke_name;
        private String name;
        private String html;
        private String subject;
        private int email_type;
        private int is_verify;
        private String gmt_created;
        private String gmt_modified;

        public void setInvoke_name(String invoke_name) {
            this.invoke_name = invoke_name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setEmail_type(int email_type) {
            this.email_type = email_type;
        }

        public void setIs_verify(int is_verify) {
            this.is_verify = is_verify;
        }

        public void setGmt_created(String gmt_created) {
            this.gmt_created = gmt_created;
        }

        public void setGmt_modified(String gmt_modified) {
            this.gmt_modified = gmt_modified;
        }

        public String getInvoke_name() {
            return invoke_name;
        }

        public String getName() {
            return name;
        }

        public String getHtml() {
            return html;
        }

        public String getSubject() {
            return subject;
        }

        public int getEmail_type() {
            return email_type;
        }

        public int getIs_verify() {
            return is_verify;
        }

        public String getGmt_created() {
            return gmt_created;
        }

        public String getGmt_modified() {
            return gmt_modified;
        }
    }
}
