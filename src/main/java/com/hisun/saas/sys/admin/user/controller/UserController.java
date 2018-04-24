package com.hisun.saas.sys.admin.user.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.communication.service.MailService;
import com.hisun.saas.sys.log.LogOperateType;
import com.hisun.saas.sys.log.RequiresLog;
import com.hisun.saas.sys.admin.role.entity.Role;
import com.hisun.saas.sys.admin.role.service.RoleService;
import com.hisun.saas.sys.admin.role.vo.RoleSelection;
import com.hisun.saas.sys.admin.user.entity.Serial;
import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.saas.sys.admin.user.entity.UserRole;
import com.hisun.saas.sys.admin.user.service.SerialService;
import com.hisun.saas.sys.admin.user.service.UserRoleService;
import com.hisun.saas.sys.admin.user.service.UserService;
import com.hisun.saas.sys.admin.user.vo.UserVo;
import com.hisun.saas.sys.auth.Constants;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
/**
 * 
 * <p>
 * 类名称：UserController
 * </p>
 * <p>
 * 类描述:
 * </p>
 * <p>
 * 公司：湖南海数互联信息技术有限公司
 * </p>
 *
 * @创建人：qinjw
 * @创建时间：2015年3月18日 下午3:55:47
 * @创建人联系方式：qinjw@30wish.net
 * @version
 */

@Controller
@RequestMapping("/sys/admin/user")
public class UserController extends BaseController {

	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	@Resource
	private MailService mailService;
	@Resource(name="resourcesProperties")
	private Properties resourcesProperties;

	@Resource
	private UserRoleService userRoleService;

	@Resource
	private SerialService serialService;
	
	@Value(value= "${sys.domain}")
	public String domain;

	@RequiresPermissions("admin-platform:user_add")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> save(@ModelAttribute("userVo") UserVo userVo, HttpServletRequest request) throws GenericException {

		Map<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		try {
			BeanUtils.copyProperties(user, userVo);
			user.setHeadPhoto("avatar.JPEG");
			user.setPassword(userVo.getPassword());
			this.userService.save(user);
			map.put("data", userVo);
			map.put("success", true);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return map;
	}

	@RequiresPermissions("admin-platform:user_add")
	@RequestMapping(value = "/add")
	public ModelAndView add(){
		return new ModelAndView("saas/sys/admin/user/addUser");
	}
	
	/**
	 * 手动新增用户的保存处理
	 * @param userVo
	 * @throws GenericException
	 */
	@RequestMapping(value = "/saveByAdd", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveByAdd(@ModelAttribute("userVo") UserVo userVo,
			HttpServletRequest request) throws GenericException {

		Map<String, Object> map = new HashMap<String, Object>();
		User user = new User();
		
		try {
			String headPhotoImg = request.getParameter("headPhotoImg");
			String newFileName = "avatar.JPEG";
			if(StringUtils.isNotBlank(headPhotoImg)){
				headPhotoImg = headPhotoImg.replace("data:image/jpeg;base64,", "");
				byte[] img = Base64.decodeBase64(headPhotoImg);
				String filePath = resourcesProperties.getProperty("sys.upload.absolute.path")+"/admin/user";
				File filetemp = new File(filePath);
				if (!filetemp.exists()) {
					// 建立文件夹
					filetemp.mkdirs();
				}
				newFileName = userVo.getUsername()+"_"+System.currentTimeMillis()+".JPEG";
				File destFile = new File(filePath+"/"+newFileName);
				if (!destFile.exists()) {
					// 建立文件
					destFile.createNewFile();
				}
				FileUtils.writeByteArrayToFile(destFile, img);
			}
			BeanUtils.copyProperties(user, userVo);
			String id = user.getId();
			user.setHeadPhoto(newFileName);
			if (id == null || id.length() == 0) {
				this.userService.save(user);
			} else {
				this.userService.update(user);
			}
			map.put("success", true);
		}  catch (IOException e) {
			logger.error(e, e);
			map.put("success", false);
			map.put("message", "头像保存错误，请联系管理员");
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
			map.put("message", "系统错误，请联系管理员");
		}
		return map;
	}
	
	
	@RequestMapping(value = "/update/photo",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> updateHeadPhoto(HttpServletRequest request)  throws GenericException {
		Map<String, Object> map = Maps.newHashMap();
		try {
			UserLoginDetails userLoginDetails = (UserLoginDetails)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
			User entity = userService.getByPK(userLoginDetails.getUserid());
			//BeanMapper.copy(user, entity);
			String headPhotoImg = request.getParameter("headPhotoImg");
			String newFileName = "avatar.JPEG";
			if(StringUtils.isNotBlank(headPhotoImg)){
				headPhotoImg = headPhotoImg.replace("data:image/jpeg;base64,", "");
				byte[] img = Base64.decodeBase64(headPhotoImg);
				String filePath = resourcesProperties.getProperty("sys.upload.absolute.path")+"/admin/user";
				File filetemp = new File(filePath);
				if (!filetemp.exists()) {
					// 建立文件夹
					filetemp.mkdirs();
				}
				newFileName = userLoginDetails.getUserid()+"_"+System.currentTimeMillis()+".JPEG";
				File destFile = new File(filePath+"/"+newFileName);
				if (!destFile.exists()) {
					// 建立文件
					destFile.createNewFile();
				}
				FileUtils.writeByteArrayToFile(destFile, img);
			}
			entity.setHeadPhoto(newFileName);
			this.userService.update(entity);
			userLoginDetails.setHeadPhoto(newFileName);
			Subject currentUser = SecurityUtils.getSubject();
			currentUser.getSession().setAttribute(Constants.CURRENT_USER, userLoginDetails);
			map.put("success", true);
			map.put("photoName", newFileName);
		} catch (IOException e) {
			logger.error(e, e);
			map.put("success", false);
			map.put("message", "头像保存错误，请联系管理员");
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
			map.put("message", "系统错误，请联系管理员");
		}
		return map;
	}
	
	/**
	 * 读取用户信息的方法
	 * 
	 * @param userId
	 * @return
	 * @throws GenericException
	 */
	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> get(
			@PathVariable("userId") String userId) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (userId != null && userId.trim().equals("") == false) {
				User entity = this.userService.getByPK(userId);
				UserVo vo = new UserVo();
				BeanUtils.copyProperties(vo, entity);
				map.put("data", vo);
				map.put("success", true);
			} else {
				throw new GenericException("错误：参数不正确。");
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return map;
	}

	@RequiresPermissions("admin-platform:user_delete")
	@RequestMapping(value = "/delete/{userId}")
	public @ResponseBody Map<String, Object> delete(
			@PathVariable("userId") String userId) throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (userId != null && userId.trim().equals("") == false) {
				User user = this.userService.getByPK(userId);
				this.userService.addTombstone(user);
				
				this.userService.deleteByPK(userId);
				map.put("success", true);
			} else {
				throw new GenericException("错误：参数不正确。");
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return map;
	}
	
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> update(HttpServletRequest request,UserVo userVo)  throws GenericException{
		Map<String, Object> map = Maps.newHashMap();
		try {
			UserLoginDetails userLoginDetails = (UserLoginDetails)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
			User entity;
			if(StringUtils.isNotBlank(userVo.getId())){
				entity = userService.getByPK(userVo.getId());
				entity.setRealname(userVo.getRealname());
				entity.setTel(userVo.getTel());
			}else{
				entity = userService.findByUsername(userLoginDetails.getUsername());
			}
			//BeanMapper.copy(user, entity);
			if(StringUtils.isNotBlank(request.getParameter("realname"))){
				entity.setRealname(request.getParameter("realname"));
			}
			if(StringUtils.isNotBlank(request.getParameter("specialty"))){
				entity.setSpecialty(request.getParameter("specialty"));
			}
			if(StringUtils.isNotBlank(request.getParameter("positional"))){
				entity.setPositional(request.getParameter("positional"));
			}
			if(StringUtils.isNotBlank(request.getParameter("about"))){
				entity.setAbout(request.getParameter("about"));
			}
			if(StringUtils.isNotBlank(request.getParameter("website"))){
				entity.setWebsite(request.getParameter("website"));
			}
			if(StringUtils.isNotBlank(request.getParameter("tel"))){
				entity.setTel(request.getParameter("tel"));
			}
			if(StringUtils.isNotBlank(request.getParameter("email"))){
				entity.setEmail(request.getParameter("email"));
			}
			if(StringUtils.isNotBlank(request.getParameter("birthday"))){
				entity.setBirthday(new DateTime(request.getParameter("birthday")).toDate());
			}
			this.userService.update(entity);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
		}
		return map;
	}

	/**
	 * 授予角色
	 * 
	 * @param userVo
	 * @return
	 * @throws GenericException
	 */
	@RequestMapping(value = "/grantRole", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> grantRole(
			@ModelAttribute("userVo") UserVo userVo)
			throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if (userVo != null && userVo.getId() != null
					&& userVo.getId().equals("") == false) {
				User user = this.userService.getByPK(userVo.getId());
				BeanUtils.copyProperties(user, userVo);
				this.userService.update(user);
				map.put("success", true);
			}
		} catch (Exception e) {
			map.put("success", false);
		}
		return map;
	}
	
	/**
	 * 保存角色赋予
	 * @param userId
	 * @return
	 * @throws GenericException
	 */
	@RequestMapping(value = "/getRoleSelection")
	public @ResponseBody Map<String, Object> getRoleSelection(
			@RequestParam(value = "userId") String userId)
			throws GenericException {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CommonConditionQuery query = new CommonConditionQuery();
			UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
			List<Role> roles = this.roleService.list(query, null);
			List<RoleSelection> roleSelections = new ArrayList<RoleSelection>();
			//查找当前用户的
			//query = new CommonConditionQuery();
			//query.add(CommonRestrictions.and(" UserRoleRelation.user.id = :userId", "userId", userId));
			//List<UserRoleRelation> relations = this.userRoleRelationService.list(query, null);//因为现在角色是单选，所有用户和角色是1:n，不存在排序
			User user = this.userService.getByPK(userId);
			List<UserRole> userRoles = user.getUserRoles();
			List<String> selectedIds = Lists.newArrayList();
			for (UserRole userRole : userRoles) {
				selectedIds.add(userRole.getRole().getId());
				//map.put("relationId", user.getRole().getId());
			}
			for (Role role : roles) {
				RoleSelection roleSelection = new RoleSelection();
				BeanUtils.copyProperties(roleSelection, role);
				if (selectedIds.contains(roleSelection.getId())) {
					roleSelection.setSelected("checked");
				}
				roleSelections.add(roleSelection);
			}
			map.put("roleSelections", roleSelections);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
		}
		return map;
	}

	@RequestMapping(value="/forgot",method = RequestMethod.POST)
	public ModelAndView forgotSend(@RequestParam("email") String email, @RequestParam("code") String code,HttpServletRequest request)  throws GenericException {
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			String smsCode = (String) request.getSession().getAttribute(com.hisun.saas.sys.admin.Constants.SMS_SESSION_KEY);
			if(StringUtils.equalsIgnoreCase(smsCode,code)) {
				User user = this.userService.findByEmail(email);
				String key = RandomStringUtils.randomAlphanumeric(160);
				StringBuilder sb = new StringBuilder();
				Serial serial = new Serial();
				serial.setEmail(email);
				serial.setKey(key);
				sb.append(domain + "/sys/admin/user/profile/password?key=").append(key);
				boolean bool = this.serialService.save(serial, user.getRealname(), email, sb.toString());
				//sb.append("尊敬的" +user.getRealname()+ ":\n");
				//sb.append("您最近请求重设自己的  帐户密码。\n\n");
				//sb.append("请单击下面链接以重设您的密码。此链接的有效期为 1 天，只能使用一次：\n");
				//logger.info(sb.toString());
				if (bool) {
					map.put("success", true);
					return new ModelAndView("saas/sys/admin/user/forgotApplySuc",map);
				} else {
					map.put("success", false);
					logger.error("邮件发送失败!false");
				}
			}else{
				map.put("success",false);
				map.put("message","短信验证码错误!");
			}
		}catch (Exception e){
			logger.error("邮件发送失败",e);
			map.put("success",false);
			map.put("message","邮件发送失败!");
		}
		return new ModelAndView("saas/sys/admin/user/forgot",map);
	}

	/**
	 * 检查用户名
	 * 
	 * @param username
	 * @return
	 * @throws GenericException
	 */
	@RequestMapping(value = "/checkUserName", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> checkUserName(
			@RequestParam(value = "username") String username,
			HttpServletResponse response) throws GenericException {
		Map<String,Object> map = new HashMap<String,Object>();
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" User.username = :username",
				"username", username));
		try {
			long count = this.userService.count(query);
			//System.out.println(count);
			if (count == 0) {
				map.put("code",1);
			}else{
				map.put("code",-2);
				map.put("message","邮箱已存在");
			}
		} catch (Exception e) {
			logger.error(e, e);
			map.put("code",-1);
			map.put("message","系统错误");
		}
		return map;
	}
	
	@RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> checkEmail(
			@RequestParam(value = "email") String email,
			HttpServletResponse response) throws GenericException {
		Map<String,Object> map = new HashMap<String,Object>();
		CommonConditionQuery query = new CommonConditionQuery();
		query.add(CommonRestrictions.and(" User.email = :email",
				"email", email));
		try {
			long count = this.userService.count(query);
			//System.out.println(count);
			if (count == 0) {
				map.put("code",1);
			}else{
				map.put("code",-2);
				map.put("message","邮箱已存在");
			}
		} catch (Exception e) {
			logger.error(e, e);
			map.put("code",-1);
			map.put("message","系统错误");
		}
		return map;
	}
	
	/**
	 * 检查旧密码
	 * 
	 * @return
	 * @throws GenericException
	 */
	@RequestMapping(value = "/checkOldPwd", method = RequestMethod.GET)
	public void checkOldPwd(
			@RequestParam(value = "oldPwd") String oldPwd,
			HttpServletResponse response) throws GenericException {
		String ret = "false";
		try {
			
			UserLoginDetails userLoginDetails = (UserLoginDetails)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
			String userId = userLoginDetails.getUserid();
			User user = this.userService.getByPK(userId);
			boolean checkRes = this.userService.credentialsPassword(user, oldPwd);
			if (checkRes == true) {
				ret = "true";
			}
			PrintWriter pw = response.getWriter();
			pw.write(ret);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}

	@RequiresLog(operateType = LogOperateType.QUERY,description = "用户列表,查询条件为:${searchContent}")
	@RequiresPermissions(value = {"adminsys:*"})
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum,
			@RequestParam(value="pageSize",defaultValue="10") int pageSize,
			@RequestParam(value="searchContent",required=false)String searchContent) throws GenericException {
		try{
			UserLoginDetails currentUser = UserLoginDetailsUtil.getUserLoginDetails();
			PagerVo<UserVo> pager = null;
			if(StringUtils.isBlank(searchContent)){
				StringBuilder hql = new StringBuilder();
				StringBuilder hql1 = new StringBuilder();
				boolean bool = false ;
				List<Object> list = Lists.newArrayList();
				hql.append("select count(1) FROM User user where user.username !='admin'");
				hql1.append(" FROM User user where user.username !='admin' order by user.username");
				int total = this.userService.count(hql.toString(),list);
				//CommonOrderBy orderBy = new CommonOrderBy();
				List<User> queryList = this.userService.list(hql1.toString(), list, pageNum, pageSize);
				List<UserVo> resultList = new ArrayList<UserVo>();
				for (User entity : queryList) {
					boolean b = true;
					UserVo vo = new UserVo();
					BeanUtils.copyProperties(vo, entity);

					/*if(!entity.getUserRoles().isEmpty()){
						List<UserRole> userRoles = entity.getUserRoles();
						for(UserRole userRole : userRoles){
							if(userRole.getRole()!=null){
								b = false;
								break;
							}
						}
					}
					if(b){*/
						resultList.add(vo);
					//}
				}
				pager = new PagerVo<UserVo>(resultList, total, pageNum, pageSize);
			}else{
				UserVo userVo = new UserVo();
				userVo.setUsername(searchContent);
				pager = this.userService.searchUserByName(pageSize, pageNum, userVo);
				request.setAttribute("username", currentUser.getUsername());
				request.setAttribute("searchContent", userVo.getUsername());
			}
			
			request.setAttribute("pager", pager);
			request.setAttribute("username", currentUser.getUsername());
		}catch(Exception e){
			throw new GenericException(e);
		}
		return "saas/sys/admin/user/userList";
	}

	/**
	 * 用户设置角色的保存处理，异步访问，结果以 success:true/false 的json格式返回
	 * @param userId
	 * @param relationId
	 * @return
	 * @throws GenericException
	 */
	@RequiresPermissions("admin-platform:user_save_rolerelation")
	@RequestMapping(value = "/saveRelation", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> saveRelation(
			@RequestParam(value="userId") String userId,
			@RequestParam(value="chooseRoles",required=false) String[] chooseRoles,
			@RequestParam(value="relationId") String relationId)
			throws GenericException {
		Map<String,Object> map = new HashMap<String,Object>();
		try {

			this.userRoleService.saveOrUpdate(userId, chooseRoles);
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
		}
		return map;
	}

	@RequiresPermissions("admin-platform:user_lock")
	@RequestMapping(value = "/locked", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> locked(
			@RequestParam(value="userid") String userid,
			@RequestParam(value="locked") boolean locked)
			throws GenericException {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			User user = new User();
			user.setId(userid);
			user.setLocked(locked);
			user.setUpdateDate(new Date());
			this.userService.update(user,new String[]{"locked","updateDate"});
			map.put("success", true);
			if(locked){
				map.put("message","锁定成功");
			}else{
				map.put("message","解锁成功");
			}
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
			map.put("message","系统错误，请联系管理员");
		}
		return map;
	}


	/**
	 * 读取用户信息
	 * @param id
	 * @param response
	 * @return
	 * @throws GenericException
	 */
	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public @ResponseBody Map<String,Object> load(
			@RequestParam(value = "id") String id,
			HttpServletResponse response) throws GenericException {
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			User entity = this.userService.getByPK(id);
			UserVo vo = new UserVo();
			BeanUtils.copyProperties(vo, entity);
			map.put("vo", vo);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return map;
	}
	
	/**
	 * 修改密码
	 * @return
	 * @throws GenericException
	 */
	@RequestMapping(value = "/changePwd", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> changePwd(
			@RequestParam(value="password") String password,@RequestParam(value="key",required=false) String key)
			throws GenericException {
		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			User user;
			if(StringUtils.isBlank(key)){
				UserLoginDetails userLoginDetails = (UserLoginDetails)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
				String userId = userLoginDetails.getUserid();
				user = this.userService.getByPK(userId);
				user.setPassword(password);
			}else{
				Serial serial = this.serialService.findByKey(key);
				if(serial==null||new DateTime(serial.getEndDate()).isBeforeNow()){//key值无效
					throw new GenericException("无效的重设密码链接!");
				}else{
					this.serialService.delete(serial);
					user = this.userService.findByEmail(serial.getEmail());
					user.setPassword(password);
				}
			}
			this.userService.update(user, true);
			map.put("success", true);
		} catch (Exception e) {
			logger.error(e, e);
			map.put("success", false);
			map.put("message", "系统错误，请联系管理员");
		}
		return map;
	}
	@RequestMapping(value = "/invite")
	public ModelAndView invite(){
		return new ModelAndView("saas/sys/admin/user/invite");
	}


	@RequiresPermissions("adminUser:profile")
	@RequestMapping(value = "/profile")
	public ModelAndView profile(){
		UserLoginDetails userLoginDetails = (UserLoginDetails)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
		Map<String, Object> map = Maps.newHashMap();
		User user = userService.findByUsername(userLoginDetails.getUsername());
		map.put("headPhoto", user.getHeadPhoto());
		map.put("user", user);
		return new ModelAndView("/saas/sys/admin/user/profile",map);
	}
	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> handleFileUpload(@RequestParam("filename") String filename,@RequestParam("Filedata") MultipartFile file,HttpServletRequest request){
		Map<String, Object> datas = Maps.newHashMap();
		UserLoginDetails userLoginDetails = (UserLoginDetails)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
		/*System.out.println(file.getOriginalFilename() + " : " + file.getSize()
				+ " : " + filename);*/
		// 拿到上下文路径
		String filePath = resourcesProperties.getProperty("sys.upload.absolute.path")+"/admin/user";
		File filetemp = new File(filePath);
		if (!filetemp.exists()) {
			// 建立文件夹
			filetemp.mkdirs();
		}
		// 写入到硬盘
		try {
			String newFileName = userLoginDetails.getUserid()+"_"+System.currentTimeMillis()+".JPEG";
			File destFile = new File(filePath+"/"+newFileName);
			if (!destFile.exists()) {
				// 建立文件
				destFile.createNewFile();
			}
			FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
			String str = request.getParameter("uploadtype");
			if(StringUtils.isBlank(str)){
				userLoginDetails.setHeadPhoto(newFileName);
				Subject currentUser = SecurityUtils.getSubject();
				currentUser.getSession().setAttribute(Constants.CURRENT_USER, userLoginDetails);
				
				User user = new User();
				user.setId(userLoginDetails.getUserid());
				user.setHeadPhoto(newFileName);
				userService.update(user, new String[]{"headPhoto"});
			}
			datas.put("success", true);
			datas.put("photoName", newFileName);
		} catch (IOException e) {
			logger.error("写入硬盘失败",e);
			datas.put("success", false);
		}
		return datas;
	}
	
	@RequestMapping("/photo/{photoName}")  
	public ResponseEntity<byte[]> photoToStream (@PathVariable("photoName")String photoName,HttpServletRequest request,HttpServletResponse response) throws IOException{
		HttpHeaders headers = new HttpHeaders();   
		headers.setContentType(MediaType.IMAGE_JPEG);
		File file = new File(resourcesProperties.getProperty("sys.upload.absolute.path")+"/admin/user/"+photoName+".JPEG");
		//String s = file.getAbsolutePath();
		if (file.exists()) {
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
					headers, HttpStatus.OK);
		}else{
			headers.setContentType(MediaType.IMAGE_PNG);
			file = new File(request.getServletContext().getRealPath("/WEB-INF/images/defaultHeadImage.png"));
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/forget/password",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> forgetPassword(@RequestParam("email") String email,HttpServletRequest request){
		Map<String,Object> map = Maps.newHashMap();
		User user = this.userService.findByEmail(email);
		if(user==null){
			map.put("success", false);
			map.put("msg", "该邮箱未注册");
		}else{
			try {
				String key = RandomStringUtils.randomAlphanumeric(160);
				StringBuilder sb = new StringBuilder();
				Serial serial = new Serial();
				serial.setEmail(email);
				serial.setKey(key);
				this.serialService.save(serial);
				sb.append("尊敬的" +user.getRealname()+ ":\n");
				sb.append("您最近请求重设自己的 三零优异 帐户密码。\n\n");
				sb.append("请单击下面链接以重设您的密码。此链接的有效期为 1 天，只能使用一次：\n");
				sb.append(domain +"/sys/user/profile/password?key=").append(key);
				//mailService.sendEmailForgetPass("【三零优异服务网】忘记密码",sb.toString(),email);
				map.put("success", true);
			} catch (Exception e) {
				logger.error("邮件发送失败",e);
				map.put("success", false);
				map.put("msg", "邮件发送失败!");
			}
		}
		return map;
	}
	
	@RequestMapping("/profile/password")
	public ModelAndView resetPassword(@RequestParam("key") String key,HttpServletRequest request){
		Serial serial = this.serialService.findByKey(key);
		Map<String, Object> maps = Maps.newHashMap();
		if(serial==null||new DateTime(serial.getEndDate()).isBeforeNow()){//key值无效
			maps.put("invalidKey", false);
		}else{
			maps.put("key",key);
			maps.put("invalidKey", true);
		}
		return new ModelAndView("saas/sys/admin/user/resetPassword",maps);
	}

	@RequestMapping(value="/pwdreset/{id}", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> pwdResetSave(@PathVariable(value="id")String id,String password){
		Map<String,Object> map = new HashMap<String,Object>();
		Serial serial = this.serialService.findByKey(id);
		User user = this.userService.findByEmail(serial.getEmail());
		user.setPassword(password);
		this.userService.update(user,true);
		this.serialService.delete(serial);
		map.put("code",1);
		return map;
	}


	@RequestMapping(value = "/searchUser")
	public String searchUserList(HttpServletRequest request,
			@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10") int pageSize,String searchContent) throws GenericException {
		
		try{
			//CommonConditionQuery query = new CommonConditionQuery();
			Subject subject = SecurityUtils.getSubject();
			UserLoginDetails currentUser = (UserLoginDetails) subject.getSession().getAttribute(Constants.CURRENT_USER);
			UserVo userVo = new UserVo();
			userVo.setUsername(searchContent);
			PagerVo<UserVo> pager = this.userService.searchUserByName(pageSize, pageNum, userVo);
			request.setAttribute("pager", pager);
			request.setAttribute("username", currentUser.getUsername());
			request.setAttribute("searchContent", userVo.getUsername());
		}catch(Exception e){
			throw new GenericException(e);
		}
		
		return "saas/sys/admin/user/userList";
		//return new ModelAndView("saas/sys/user/userListTree",map);
	}

}
