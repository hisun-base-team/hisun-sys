package com.hisun.saas.sys.tenant.user.vo;

import com.hisun.util.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


public class TenantUserVo implements Serializable {
    private static final long serialVersionUID = 5488580691891957241L;

    private String id;
    private String email;
    private String username;
    private String realname;
    private String pwd;
    private String tel;
    private String salt;
    private boolean locked = Boolean.FALSE;
    private boolean sex;
    private String sexStr="1";
    private String tenantId;
    private boolean isOnline = false;
    private String specialty;//特长
    private String positional;//职称
    private String website;//个人主页
    private String about;//个人简介

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthday = new Date();
    private String roleId;
    private String roleName;
    private int sort;
    private String departmentId;
    private String departmentName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getPositional() {
        return positional;
    }

    public void setPositional(String positional) {
        this.positional = positional;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isSex() {
        return sex;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {

        this.sort = sort;
    }

    public String getSexStr() {
        if(sex){
            sexStr="1";
        }else{
            sexStr="0";
        }
        return sexStr;
    }

    public void setSexStr(String sexStr) {
        if(StringUtils.isNotBlank(sexStr)){
            if(sexStr.equals("0")){
                this.sex = false;
            }else{
                this.sex = true;
            }
        }else{
            this.sex =true;
        }
        this.sexStr = sexStr;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
