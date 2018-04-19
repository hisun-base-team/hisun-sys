/**
 * 
 */
package com.hisun.saas.sys.tenant.tenant.vo;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * 
 *<p>类名称：OrganizationVo</p>
 *<p>类描述: </p>
 *<p>公司：湖南海数互联信息技术有限公司</p>
 *@创建人：qinjw
 *@创建时间：2015年3月27日 上午9:34:34
 *@创建人联系方式：qinjw@30wish.net
 *@version
 */
public class TenantVo {
	
	private String id;//主键

	@NotBlank
	@Size(min=1,max=15)
	private String name;//组织名

	@NotBlank
	@Size(min=4, max = 30)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
	private String email;

	@NotBlank
	@Size(min=6, max = 20)
	private String password;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
