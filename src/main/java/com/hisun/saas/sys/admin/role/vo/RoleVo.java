package com.hisun.saas.sys.admin.role.vo;

/**
 * 
 *<p>类名称：RoleVo</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：qinjw
 *@创建时间：2015年3月18日 下午3:59:59
 *@创建人联系方式：qinjw@30wish.net
 *@version
 */
public class RoleVo {
	
	private String id;//主键
	
	private String roleName;//角色名
	private int sort;
	private String property;//性质，0：服务提供者；1：使用者，与组织团队管理中的一致
	private String roleCode;//角色字符串
	private String description;
	private String pId;
	private String tenantId;
	
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}