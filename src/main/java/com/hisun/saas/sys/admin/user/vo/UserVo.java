package com.hisun.saas.sys.admin.user.vo;

/**
 * 
 *<p>类名称：UserVo</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：qinjw
 *@创建时间：2015年3月18日 下午5:11:28
 *@创建人联系方式：qinjw@30wish.net
 *@version
 */
public class UserVo {
	
	private String id;//主键
	
	private String email;
	private String username;
	private String realname;
	private String tel;
	private String password;
	
	private String roleName;
	
	private String tenantId;
	private String tenantName;
	
	private boolean locked;

	private boolean sex;

	public boolean isLocked() {
		return locked;
	}

	public boolean getLocked(){ return  locked;}

	public void setLocked(boolean locked) {
		this.locked = locked;
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getTenantName() {
		return tenantName;
	}
	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isSex() {
		return sex;
	}

	public boolean getSex(){
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}
}
