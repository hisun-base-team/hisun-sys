package com.hisun.saas.sys.auth.realm;

import com.google.common.collect.Sets;
import com.hisun.saas.sys.entity.AbstractResource;
import com.hisun.saas.sys.admin.user.service.UserService;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.Constants;
import com.hisun.saas.sys.entity.AbstractRole;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasAuthenticationException;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Title: CasRealm.java </p>
 * <p>Package com.hisun.cloud.sys.auth.realm </p>
 * <p>Description: 自定义CAS的Realm</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年8月25日 上午9:44:27 
 * @version 
 */
public class CasRealm extends org.apache.shiro.cas.CasRealm {

	@javax.annotation.Resource
	private UserService userService;
	
	private static Logger log = LoggerFactory.getLogger(CasRealm.class);

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {  
        CasToken casToken = (CasToken) token;
        if (token == null) {
            return null;
        }
        
        String ticket = (String)casToken.getCredentials();
        if (!StringUtils.hasText(ticket)) {
            return null;
        }
        
        TicketValidator ticketValidator = ensureTicketValidator();

        try {
            // contact CAS server to validate service ticket
            Assertion casAssertion = ticketValidator.validate(ticket, getCasService());
            // get principal, user id and attributes
            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
            String userId = casPrincipal.getName();
            log.debug("Validate ticket : {} in CAS server : {} to retrieve user : {}", new Object[]{
                    ticket, getCasServerUrlPrefix(), userId
            });

            Map<String, Object> attributes = casPrincipal.getAttributes();
            // refresh authentication token (user id + remember me)
            casToken.setUserId(userId);
            String rememberMeAttributeName = getRememberMeAttributeName();
            String rememberMeStringValue = (String)attributes.get(rememberMeAttributeName);
            Object tenantId = attributes.get("tenantId");
            boolean isRemembered = rememberMeStringValue != null && Boolean.parseBoolean(rememberMeStringValue);
            if (isRemembered) {
                casToken.setRememberMe(true);
            }
            // create simple authentication info
            List<Object> principals = CollectionUtils.asList(userId, attributes);
            PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());
            // 这里可以拿到Cas的登录账号信息,加载到对应权限体系信息放到缓存中...  
            
            Subject subject = SecurityUtils.getSubject();
            
            UserLoginDetails userLoginDetails = userService.findUserLoginDetails(userId);
			subject.getSession().setAttribute(Constants.CURRENT_USER, userLoginDetails);
			
            return new SimpleAuthenticationInfo(principalCollection, ticket);
        } catch (TicketValidationException e) { 
            throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
        }
    }  
  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {  
    	String username = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		Set<String> roleStrs = Sets.newHashSet();
		Set<String> permissions = Sets.newHashSet();
		UserLoginDetails user = this.userService.findUserLoginDetails(username);
		List<AbstractRole> roles = user.getRoles();
        for(AbstractRole role : roles){
        	roleStrs.add(role.getRoleCode());
        }
        
		List<AbstractResource> resources = user.getResources();
		if (resources != null) {
			for (AbstractResource resource : resources) {
				String permission = resource.getPermission();
				permissions.add(permission);
			}
		}
		if (Constants.ADMIN_USERNAME.equalsIgnoreCase(username)) {
			permissions.add(Constants.ADMIN_PERMISSION);
		}
		authorizationInfo.setRoles(roleStrs);
		authorizationInfo.setStringPermissions(permissions); 
		
        return authorizationInfo;  
    }  
  
}
