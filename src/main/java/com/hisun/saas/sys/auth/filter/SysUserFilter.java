/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.auth.filter;

import com.hisun.base.entity.AbstractResource;
import com.hisun.saas.sys.admin.resource.service.ResourceService;
import com.hisun.saas.sys.admin.user.service.UserService;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.base.auth.Constants;
import com.hisun.saas.sys.tenant.resource.entity.TenantResource;
import com.hisun.saas.sys.tenant.resource.service.TenantResourceService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Rocky {rockwithyou@126.com}
 */
public class SysUserFilter extends PathMatchingFilter {

	@Resource
    private UserService userService;
	@Resource
	private ResourceService resourceService;
	@Resource
	private TenantResourceService tenantResourceService;
	
	public static final String AJAX_REQUEST_HEADER= "XMLHttpRequest";
	 
    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
    	HttpServletRequest httpRequest = (HttpServletRequest)request;  
    	String requestType =(String) httpRequest.getHeader("X-Requested-With");   
        if(StringUtils.isBlank(requestType)&&!StringUtils.equals(AJAX_REQUEST_HEADER, requestType)){
        	Session session = SecurityUtils.getSubject().getSession();
        	UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        	List<AbstractResource> resources = userLoginDetails.getResources();
        	
        	String contextPath = httpRequest.getServletPath();
        	int i = contextPath.indexOf("?");
        	if(i>0){
        		contextPath = contextPath.substring(0,i-1);
        	}
        	if(StringUtils.equals("/monitor/overview/businessMonitItem", contextPath)){
        		contextPath="/monitor/overview/applications";
        	}else if(StringUtils.equals("/management/ticket/warning/add", contextPath)){
        		contextPath="/management/dashboard";
        	}
        	for(AbstractResource resource:resources){
        		// ?（匹配任何单字符），*（匹配0或者任意数量的字符），**（匹配0或者更多的目录）
        		if(new AntPathMatcher().match(resource.getUrl(),StringUtils.trim(contextPath))){
        			//if(StringUtils.equalsIgnoreCase(StringUtils.trim(contextPath), StringUtils.trim(resource.getUrl()))){
        			
        			//List<String> lists = Lists.newArrayList();
        			/*Pattern pattern = Pattern.compile("[0-9]{3}");
                    Matcher matcher = pattern.matcher(resource.getQueryCode());
                    while (matcher.find()){
                        lists.add(matcher.group());
                    }*/
        			StringBuilder sb = new StringBuilder();
        			if(StringUtils.isNotBlank(resource.getQueryCode())){
        				splitQueryCode(resource, sb);
        			}
                    session.setAttribute(Constants.RESOURCE_QUERY_CODE, sb.toString());
					session.setAttribute(Constants.MENU_RESOURCE_ID, getMenuResource(resource));
                    session.setAttribute(Constants.RESOURCE_ID, resource.getId());
                    break;
        		}
        	}
        }
        return true;
    }

	private void splitQueryCode(AbstractResource resource, StringBuilder sb) {
		for(int i =1 ;i<=resource.getQueryCode().length()/3; i++){
			sb.append(resource.getQueryCode().substring(0,i*3)+",");
		}
		sb.deleteCharAt(sb.length()-1);
	}

	private String getMenuResource(AbstractResource resource ){
		if(resource.getResourceType() != 1){
			//不是操作类型，直接返回ID，因为系统和菜单在页面上是会展现处理啊
			return resource.getId();
		}
		UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
		AbstractResource parentResource = null;
		if(userLoginDetails.getUserType()==Constants.USER_TYPE_ADMIN){
			parentResource = resourceService.getByPK(resource.getpId());
		}else{
			parentResource = tenantResourceService.getByPK(resource.getpId());
		}
		while(parentResource != null){
			if(parentResource.getResourceType() != 1){
				//不是操作类型，直接返回ID，因为系统和菜单在页面上是会展现处理啊
				return parentResource.getId();
			}else{
				parentResource = resourceService.getByPK(parentResource.getpId());
			}
		}
		//到最后都没找到菜单或者系统直接返回当前ID
		return resource.getId();
	}
}
