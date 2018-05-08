/**
 * 
 */
package com.hisun.saas.sys.tenant.tenant.vo;

import com.hisun.base.vo.TombstoneVo;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;


public class TenantVo extends TombstoneVo{

	@Size(max=100)
	private String id;//主键
	@NotBlank
	@Size(min=1,max=100)
	private String name;//组织名
	@NotBlank
	@Size(min=1,max=60)
	private String shortName;//组织简称
	@NotBlank
	@Size(min=1,max=15)
	private String shortNamePy;//拼音简称
	@NotBlank
	@Size(min=4, max = 32)
	private String adminUserName;//默认管理账号
	@NotBlank
	@Size(min=6, max = 20)
	private String adminUserPassword;//默认管理员密码
	@Size(max = 50)
	@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
	private String adminUserEmail;//默认管理邮箱

	private int usersCount;//用户数

	private int tombstone=0;
	private Date createDate;

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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShortNamePy() {
		return shortNamePy;
	}

	public void setShortNamePy(String shortNamePy) {
		this.shortNamePy = shortNamePy;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public String getAdminUserPassword() {
		return adminUserPassword;
	}

	public void setAdminUserPassword(String adminUserPassword) {
		this.adminUserPassword = adminUserPassword;
	}

	public String getAdminUserEmail() {
		return adminUserEmail;
	}

	public void setAdminUserEmail(String adminUserEmail) {
		this.adminUserEmail = adminUserEmail;
	}

	public int getUsersCount() {
		return usersCount;
	}

	public void setUsersCount(int usersCount) {
		this.usersCount = usersCount;
	}



}
