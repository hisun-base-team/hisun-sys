package com.hisun.saas.sys.tenant.vo;

/**
 * <p>类名称:TreeNodeVo</p>
 * <p>类描述:</p>
 * <p>公司:湖南海数互联信息技术有限公司</p>
 *
 * @创建者:init
 * @创建人:15/11/18下午7:20
 * @创建人联系方式:init@hn-hisun.com
 */
public class TreeNodeVo {

    private String id;

    private String name;

    private String pId;

    /**
     * 不需要单选框
     */
    private Boolean nocheck;

    private Boolean checked;

    public TreeNodeVo(){}

    public TreeNodeVo(String id ,String name,String pId,Boolean nocheck,Boolean checked){
        this.id = id;
        this.name = name;
        this.pId = pId;
        this.nocheck = nocheck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Boolean getNocheck() {
        return nocheck;
    }

    public void setNocheck(Boolean nocheck) {
        this.nocheck = nocheck;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
