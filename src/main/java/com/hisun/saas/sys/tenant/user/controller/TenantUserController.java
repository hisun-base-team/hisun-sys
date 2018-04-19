/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.user.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.base.auth.Constants;
import com.hisun.base.auth.service.SessionHelper;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.base.entity.TombstoneEntity;
import com.hisun.base.exception.ErrorMsgShowException;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.communication.service.MailService;
import com.hisun.saas.sys.admin.communication.vo.MailResult;
import com.hisun.saas.sys.admin.communication.vo.MailSendSingleVo;
import com.hisun.saas.sys.admin.user.entity.Serial;
import com.hisun.saas.sys.admin.user.service.SerialService;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.UserLoginDetailsUtil;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.role.service.TenantRoleService;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.entity.TenantRegister;
import com.hisun.saas.sys.tenant.tenant.service.TenantRegisterService;
import com.hisun.saas.sys.tenant.tenant.service.TenantService;
import com.hisun.saas.sys.tenant.tenant.vo.TenantRegisterVo;
import com.hisun.saas.sys.tenant.user.entity.Activation;
import com.hisun.saas.sys.tenant.user.entity.PasswordReset;
import com.hisun.saas.sys.tenant.user.entity.TenantUser;
import com.hisun.saas.sys.tenant.user.entity.TenantUserRole;
import com.hisun.saas.sys.tenant.user.service.ActivationService;
import com.hisun.saas.sys.tenant.user.service.PasswordResetService;
import com.hisun.saas.sys.tenant.user.service.TenantUserRoleService;
import com.hisun.saas.sys.tenant.user.service.TenantUserService;
import com.hisun.saas.sys.tenant.user.vo.TenantRoleSelection;
import com.hisun.saas.sys.tenant.user.vo.TenantUserVo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Controller
@RequestMapping("/sys/tenant/user")
public class TenantUserController extends BaseController{

    @Resource
    private TenantUserService tenantUserService;
    @Value("${sys.upload.absolute.path}")
    private String uploadBasePath;
    @Resource
    private ActivationService activationService;
    @Resource
    private MailService mailService;
    @Value(value= "${sys.domain}")
    public String domain;
    @Resource
    private TenantRoleService tenantRoleService;
    @Resource
    private TenantUserRoleService tenantUserRoleService;
    @Resource
    private SerialService serialService;

    @Resource
    private TenantService tenantService;

    @Resource
    private TenantRegisterService tenantRegisterService;

    @Resource
    private PasswordResetService passwordResetService;

    @Resource
    private SessionHelper sessionHelper;


    private final static String DEFAULT_IMG_HEAD_PATH = "/WEB-INF/images/defaultHeadImage.png";
    /**
     * 获取用户列表
     * @param modelMap
     * @param pageNum
     * @param pageSize
     * @param searchContent
     * @return
     * @throws GenericException
     */
//    @RequiresPermissions("tenantUser:list")
    @RequestMapping(value = "/sys/list")
    public ModelAndView sysList(ModelMap modelMap,
                             @RequestParam(value="pageNum",defaultValue="1")int pageNum,
                             @RequestParam(value="pageSize",defaultValue="10") int pageSize,
                             @RequestParam(value="searchContent",required=false)String searchContent) throws GenericException {
        return list(modelMap,pageNum,pageSize,searchContent);
    }

    @RequestMapping(value = "/sysAdmin/list")
    public ModelAndView sysAdminList(ModelMap modelMap,String tenantId,
                                @RequestParam(value="pageNum",defaultValue="1")int pageNum,
                                @RequestParam(value="pageSize",defaultValue="10") int pageSize,
                                @RequestParam(value="searchContent",required=false)String searchContent) throws GenericException,UnsupportedEncodingException {
        if(StringUtils.isNotBlank(searchContent)){
            searchContent = URLDecoder.decode(searchContent,"UTF-8");
        }
        TenantUserVo userVo = new TenantUserVo();
        userVo.setTenantId(tenantId);
        userVo.setUsername(searchContent);
        PagerVo<TenantUserVo> pager = tenantUserService.pagerList(pageSize, pageNum, userVo);
        modelMap.put("pager", pager);
        modelMap.put("tenantId",tenantId);
        modelMap.put("searchContent", searchContent);
        return new ModelAndView("saas/sys/tenant/user/sysUserList",modelMap);
    }

    @RequestMapping(value = "/ajax/sysAdmin/list")
    public ModelAndView ajaxSysAdminList(ModelMap modelMap,String tenantId,
                                     @RequestParam(value="pageNum",defaultValue="1")int pageNum,
                                     @RequestParam(value="pageSize",defaultValue="10") int pageSize,
                                     @RequestParam(value="searchContent",required=false)String searchContent) throws GenericException {
        TenantUserVo userVo = new TenantUserVo();
        userVo.setTenantId(tenantId);
        userVo.setUsername(searchContent);
        PagerVo<TenantUserVo> pager = tenantUserService.pagerList(pageSize, pageNum, userVo);
        modelMap.put("pager", pager);
        return new ModelAndView("saas/sys/tenant/user/sysUserListAjaxData",modelMap);
    }

    /**
     * 获取用户列表
     * @param modelMap
     * @param pageNum
     * @param pageSize
     * @param searchContent
     * @return
     * @throws GenericException
     */
//    @RequiresPermissions("tenantUser:list")
    @RequestMapping(value = "/list")
    public ModelAndView list(ModelMap modelMap,
                       @RequestParam(value="pageNum",defaultValue="1")int pageNum,
                       @RequestParam(value="pageSize",defaultValue="10") int pageSize,
                       @RequestParam(value="searchContent",required=false)String searchContent) throws GenericException {
        UserLoginDetails currentUser = UserLoginDetailsUtil.getUserLoginDetails();

        TenantUserVo userVo = new TenantUserVo();
        userVo.setTenantId(currentUser.getTenantId());
        if (StringUtils.isNotBlank(searchContent)) {
            userVo.setUsername(searchContent);
        }
        PagerVo<TenantUserVo> pager = tenantUserService.pagerList(pageSize, pageNum, userVo);
        modelMap.put("pager", pager);
        modelMap.put("searchContent", searchContent);
        return new ModelAndView("saas/sys/tenant/user/userList",modelMap);
    }

    /**
     * 新增用户
     * @return
     */
    @RequiresPermissions("tenant:userlist")
    @RequestMapping(value = "/add")
    public ModelAndView add(){
        Map<String, Object> map = Maps.newHashMap();
        CommonConditionQuery query = new CommonConditionQuery();
        List<TenantRole> roles = this.tenantRoleService.list(query, null);
        map.put("roles", roles);
        return new ModelAndView("saas/sys/tenant/user/addUser", map);
    }

    /**
     * 邀请用户
     * @return
     */
    @RequestMapping(value = "/invite")
    public ModelAndView invite(ModelMap modelMap){
        CommonConditionQuery query = new CommonConditionQuery();
        List<TenantRole> roles = this.tenantRoleService.list(query, null);
        modelMap.put("roles", roles);
        return new ModelAndView("saas/sys/tenant/user/invite",modelMap);
    }

    /**
     * 添加用户
     * @param userVo
     * @param request
     * @return
     * @throws GenericException
     */
    @RequiresPermissions("tenant:userlist")
    @RequestMapping(value = "/saveByAdd", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveByAdd(@ModelAttribute("userVo") TenantUserVo userVo,
                                  HttpServletRequest request) throws GenericException {
        UserLoginDetails currentUser = UserLoginDetailsUtil.getUserLoginDetails();
        Map<String, Object> map = Maps.newHashMap();
        TenantUser user = new TenantUser();

        try {
            // 保存头像
            String headPhotoImg = request.getParameter("headPhotoImg");
            String newFileName = "avatar.JPEG";
            if(StringUtils.isNotBlank(headPhotoImg)){
                headPhotoImg = headPhotoImg.replace("data:image/jpeg;base64,", "");
                byte[] img = Base64.decodeBase64(headPhotoImg);
                String filePath = uploadBasePath+"/tenant/user";
                File fileTemp = new File(filePath);
                if (!fileTemp.exists()) {
                    // 建立文件夹
                    fileTemp.mkdirs();
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
            Tenant tenant = new Tenant();
            if (StringUtils.isBlank(userVo.getTenantId())) {
                tenant.setId(currentUser.getTenantId());
            } else {
                tenant.setId(userVo.getTenantId());
            }

            user.setTenant(tenant);
            user.setPassword(userVo.getPwd());
            if (StringUtils.isBlank(id)) {
                if (StringUtils.isBlank(userVo.getRoleId())) {
                    // 角色为空，添加进默认角色
                    this.tenantUserService.save(user);
                } else {
                    // 角色不为空
                    this.tenantUserService.saveUserAndRole(user, userVo.getRoleId());
                }

            } else {
                this.tenantUserService.update(user);
            }
            map.put("success", true);
        }catch (IOException e) {
            map.put("success", false);
            map.put("message" , "头像保存失败，请联系管理员");
        }
        catch (Exception e) {
            map.put("success", false);
            map.put("message" , "系统错误，请联系管理员");
        }
        return map;
    }

    /**
     * 加载用户信息
     * @param id
     * @return
     * @throws GenericException
     */
    @RequestMapping(value = "/load/{id}", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> load(@PathVariable("id") String id) throws GenericException {
        Map<String,Object> map = Maps.newHashMap();
        try {
            TenantUser entity = this.tenantUserService.getByPK(id);
            TenantUserVo vo = new TenantUserVo();
            BeanUtils.copyProperties(vo, entity);
            map.put("vo", vo);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "获取用户信息失败");
        }
        return map;
    }

    /**
     * 删除用户（移除）
     * @param userId
     * @return
     * @throws GenericException
     */
    @RequiresPermissions("tenant:userlist")
    @RequestMapping(value = "/delete/{userId}")
    public @ResponseBody Map<String, Object> delete(
            @PathVariable("userId") String userId) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        try {
            if (userId != null && userId.trim().equals("") == false) {
                TenantUser user = this.tenantUserService.getByPK(userId);
                user.setTenant(null);
                this.tenantUserService.update(user);
                //this.tenantUserService.deleteByPK(userId);
                map.put("success", true);
            } else {
                map.put("success", false);
                map.put("msg", "操作失败");
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "系统错误请联系管理员");
        }
        return map;
    }

    /**
     * 锁定用户
     * @param userid
     * @param locked
     * @return
     * @throws GenericException
     */
    @RequestMapping(value = "/locked", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> locked(
            @RequestParam(value="userid") String userid,
            @RequestParam(value="locked") boolean locked)
            throws GenericException {
        Map<String,Object> map = Maps.newHashMap();
        try {
            TenantUser user = new TenantUser();
            user.setId(userid);
            user.setLocked(locked);
            user.setUpdateDate(new Date());
            this.tenantUserService.update(user, new String[]{"locked", "updateDate"});
            map.put("success", true);
            if(locked){
                map.put("msg","锁定成功");
            }else{
                map.put("msg","解锁成功");
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "锁定/解锁失败");
        }
        return map;
    }

    /**
     * 修改用户
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/update",method=RequestMethod.POST)
    public @ResponseBody Map<String, Object> update(TenantUserVo userVo)  throws GenericException{
        Map<String, Object> map = Maps.newHashMap();
        try {
            TenantUser entity = tenantUserService.getByPK(userVo.getId());
            if(StringUtils.isNotBlank(userVo.getRealname())){
                entity.setRealname(userVo.getRealname());
            }
            if(StringUtils.isNotBlank(userVo.getSpecialty())){
                entity.setSpecialty(userVo.getSpecialty());
            }
            if(StringUtils.isNotBlank(userVo.getPositional())){
                entity.setPositional(userVo.getPositional());
            }
            if(StringUtils.isNotBlank(userVo.getAbout())){
                entity.setAbout(userVo.getAbout());
            }
            if(StringUtils.isNotBlank(userVo.getWebsite())){
                entity.setWebsite(userVo.getWebsite());
            }
            if(StringUtils.isNotBlank(userVo.getTel())){
                entity.setTel(userVo.getTel());
            }
            if(StringUtils.isNotBlank(userVo.getEmail())){
                entity.setEmail(userVo.getEmail());
            }
            if(userVo.getBirthday() != null){
                entity.setBirthday(userVo.getBirthday());
            }
            this.tenantUserService.update(entity);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "系统错误，请联系管理员！");
        }
        return map;
    }

    /**
     * 邀请用户
     * @param inviteEmail
     * @param emailcontent
     * @param request
     * @return
     * @throws GenericException
     */
    @RequiresPermissions("tenant:userlist")
    @RequestMapping(value = "/send/invite")
    public @ResponseBody Map<String,Object> sendInvite(@RequestParam(value = "inviteEmail") String inviteEmail[],@RequestParam(value = "emailcontent") String emailcontent,
                                                       @RequestParam(value = "roles") String roles[],
                                                       HttpServletRequest request) throws GenericException {
        Map<String,Object> map = Maps.newHashMap();
        try {

            //String[] mails = StringUtils.splitByWholeSeparator(inviteEmail, ",");
            Pattern pattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
            boolean bool = false ;
            for(String mail : inviteEmail){
                if(StringUtils.isNotBlank(mail)&&!pattern.matcher(mail).matches()){
                    map.put("success", false);
                    bool=true;
                    break;
                }
            }
            if(!bool){
                for(int i = 0; i < inviteEmail.length; i ++){
                    if(StringUtils.isNotBlank(inviteEmail[i])){
                        this.activationService.saveAndSendEmail(inviteEmail[i], emailcontent, roles[i]);
                    }
                }
                map.put("success", true);
            }
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }

    /**
     * 邀请注册账号
     * @param map
     * @param activationId
     * @return
     * @throws GenericException
     */
    @RequestMapping(value = "/activate", method = RequestMethod.GET)
    public ModelAndView activate(ModelMap map,
            @RequestParam(value = "activationId") String activationId) throws GenericException {
        ModelAndView ret = null;
        Activation entity = this.activationService.getByPK(activationId);
        String tenantId = entity.getInviteTenantId();
        if (entity != null) {
            String status = entity.getStatus();
            if (!status.equals("1")) {// 未激活
                // 邀请的租户
                Tenant tenant = tenantService.getByPK(tenantId);
                // 邀请人
                TenantUser user = tenantUserService.getByPK(entity.getInviteUserId());
                map.put("activationId", activationId);
                map.put("email", entity.getEmail());

                // 检查邮箱是否已经被注册(激活)到其他租户
                CommonConditionQuery query = new CommonConditionQuery();
                query.add(CommonRestrictions.and(" email = :email", "email", entity.getEmail().trim()));
                List<TenantUser> tenantUsers = tenantUserService.list(query, null, false);
                if (tenantUsers != null && tenantUsers.size() > 0) {
                    TenantUser tenantUser = tenantUsers.get(0);
                    // 邀请人已经存在当前租户
                    if (tenantUser.getTenant()!=null && tenantId.equals(tenantUser.getTenant().getId())) {
                        map.put("status", "0");// 已经注册到当前租户（属于重复邀请的情况,无需再注册）
                        map.put("user",user);
                        map.put("tenant",tenant);
                    } else {
                        map.put("status", "1");// 收到新租户的邀请
                        map.put("oldTenant", tenantUser.getTenant());// 原租户
                        map.put("newTenant", tenant); // 新租户
                        map.put("user", tenantUser);
                    }
                    ret = new ModelAndView("registerToNewTenant", map);
                } else {
                    // 未注册（分为两种情况,1:完全未注册,2:已经注册但是未激活）
                    query = new CommonConditionQuery();
                    query.add(CommonRestrictions.and(" email = :email", "email", entity.getEmail().trim()));
                    List<TenantRegister> registers = tenantRegisterService.list(query, null, false);
                    // 已经注册租户,但是未激活
                    if (registers != null && registers.size() > 0) {
                        TenantRegister tenantRegister = registers.get(0);
                        map.put("registerId", tenantRegister.getId());
                        map.put("status", "2"); // 已经注册,但是未激活
                        map.put("tenant",tenant);
                        map.put("user",user);
                        map.put("registerName", tenantRegister.getTenantName());
                        map.put("tenant",tenant);
                        ret = new ModelAndView("registerToNewTenant", map);
                    } else {
                        map.put("tenant",tenant);
                        map.put("user",user);
                        ret = new ModelAndView("inviteRegister", map);
                    }

                }
            } else {// 提示：已激活
                throw new ErrorMsgShowException("邀请已激活");
            }
        } else {// 提示：无效的激活链接
            throw new ErrorMsgShowException("无效邀请链接");
        }
        return ret;
    }

    /**
     * 激活账号
     * @param activationId
     * @return
     * @throws GenericException
     */
    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> activateSave(@RequestParam(value = "activationId") String activationId,
                                 TenantRegisterVo vo) throws GenericException {
        Map<String,Object> map = new HashMap<String,Object>();
        Activation activation = activationService.getByPK(activationId);
        if(activation==null || !"0".equals(activation.getStatus())){
            map.put("code",-1);
            map.put("message","邀请不存在，或已激活");
        }else{
            vo.setEmail(activation.getEmail());
            tenantUserService.saveInviteRegister(vo,activationId,activation.getRoleId(),activation.getInviteTenantId());
            map.put("code",1);
        }
        return map;
    }

    /**
     * 检查用户名
     *
     * @param username
     * @return
     * @throws GenericException
     */
    @RequestMapping(value = "/un/checkUserName")
    public @ResponseBody Map<String,Object> checkUserName(@RequestParam(value = "username") String username) throws GenericException {
        Map<String,Object> map = new HashMap<String,Object>();
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" username = :username", "username", username.trim()));
        try {
            long count1 = tenantUserService.count(query);
            long count2 = tenantRegisterService.count(query);
            if (count1+count2 == 0) {
                map.put("code",1);
            }else{
                map.put("code", -2);//-1被底层错误占用，用-2代替邮箱已存在
            }
        } catch (Exception e) {
            map.put("code",-1);
            map.put("message","系统错误");
        }
        return map;
    }

    /**
     * 检查邮箱
     * @param email
     * @throws GenericException
     */
    @RequestMapping(value = "/un/checkEmail")
    public @ResponseBody Map<String,Object> checkEmail(@RequestParam(value = "email") String email) throws GenericException {
        Map<String,Object> map = new HashMap<String,Object>();
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" email = :email", "email", email.trim()));
        try {
            long count1 = this.tenantUserService.count(query);
            long count2 = tenantRegisterService.count(query);
            if ((count1 + count2 ) == 0) {
                map.put("code",1);
            }else{
                map.put("code", -2);//-1被底层错误占用，用-2代替邮箱已存在
            }
        } catch (Exception e) {
            map.put("code",-1);
            map.put("message","系统错误");
        }
        return map;
    }

    /**
     * 保存角色赋予
     * @param userId
     * @return
     * @throws GenericException
     */
    @RequestMapping(value = "/getRoleSelection/{userId}")
    public @ResponseBody Map<String, Object> getRoleSelection(
            @PathVariable("userId") String userId)
            throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        try {
            CommonConditionQuery query = new CommonConditionQuery();
            List<TenantRole> roles = this.tenantRoleService.list(query, null);
            List<TenantRoleSelection> roleSelections = Lists.newArrayList();
            //查找当前用户的角色
            List<TenantUserRole> userRoles = this.tenantUserRoleService.getUserRoleByUserId(userId);
            List<String> selectedIds = Lists.newArrayList();
            for (TenantUserRole userRole : userRoles) {
                selectedIds.add(userRole.getRole().getId());
            }
            for (TenantRole role : roles) {
                TenantRoleSelection roleSelection = new TenantRoleSelection();
                BeanUtils.copyProperties(roleSelection, role);
                if (selectedIds.contains(roleSelection.getId())) {
                    roleSelection.setSelected("checked");
                }
                roleSelections.add(roleSelection);
            }
            map.put("roleSelections", roleSelections);
            map.put("success", true);
        } catch (Exception e) {
            logger.error(e,e);
            map.put("success", false);
        }
        return map;
    }

    /**
     * 用户设置角色的保存处理，异步访问，结果以 success:true/false 的json格式返回
     * @param userId
     * @return
     * @throws GenericException
     */
    @RequiresPermissions("tenant:userlist")
    @RequestMapping(value = "/saveRelation", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> saveRelation(
            @RequestParam(value="userId") String userId,
            @RequestParam(value="chooseRoles",required=false) String[] chooseRoles)
            throws GenericException {
        Map<String,Object> map = Maps.newHashMap();
        try {

            this.tenantUserRoleService.saveOrUpdate(userId, chooseRoles);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }

    /**
     * 检查用户名
     *
     * @param username
     * @return
     * @throws GenericException
     */
    @RequestMapping(value = "/checkUserName", method = RequestMethod.GET)
    public void checkUserName(
            @RequestParam(value = "username") String username,
            HttpServletResponse response) throws GenericException {
        String ret = "false";
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" username = :username", "username", username));
        try {
            long count = this.tenantUserService.count(query);
            if (count == 0) {
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

    /**
     * 检查邮箱
     * @param email
     * @param response
     * @throws GenericException
     */
    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
    public void checkEmail(
            @RequestParam(value = "email") String email,
            HttpServletResponse response) throws GenericException {
        String ret = "false";
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" email = :email", "email", email));
        try {
            long count = this.tenantUserService.count(query);
            if (count == 0) {
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

    /**
     * 个人信息维护
     * @return
     */
    @RequestMapping(value = "/profile")
    public ModelAndView profile(){
        UserLoginDetails userLoginDetails = (UserLoginDetails)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        Map<String, Object> map = Maps.newHashMap();
        TenantUser user = tenantUserService.findByUsername(userLoginDetails.getUsername());
        if (null == user.getHeadPhoto()) {
            map.put("headPhoto", "noPhoto");
        } else {
            map.put("headPhoto", user.getHeadPhoto());
        }

        map.put("user", user);
        return new ModelAndView("/saas/sys/tenant/user/profile",map);
    }

    /**
     * 获取头像
     * @param photoName
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/photo/{photoName}")
    public ResponseEntity<byte[]> photoToStream (@PathVariable("photoName")String photoName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        File file = new File(uploadBasePath + "/tenant/user/" + photoName + ".JPEG");
        if(file.exists()){
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.OK);
        }else{
            //为空或者没有返回默认图片
            headers.setContentType(MediaType.IMAGE_PNG);
            file = new File(request.getServletContext().getRealPath(DEFAULT_IMG_HEAD_PATH));
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.OK);
        }
    }

    /**
     * 获取头像
     * @param id
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/headimg/{id}")
    public void headImgToStream (@PathVariable("id")String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        TenantUser tenantUser = tenantUserService.getByPK(id);
        File file = null;
        if(tenantUser==null || StringUtils.isBlank(tenantUser.getHeadPhoto())){
            //为空或者没有返回默认图片
            headers.setContentType(MediaType.IMAGE_PNG);
            file = new File(request.getServletContext().getRealPath(DEFAULT_IMG_HEAD_PATH));
        }else{
            file = new File(uploadBasePath + "/tenant/user/" + tenantUser.getHeadPhoto());
            if(file.exists()){
                headers.setContentType(MediaType.IMAGE_JPEG);
            }else{
                headers.setContentType(MediaType.IMAGE_PNG);
                file = new File(request.getServletContext().getRealPath(DEFAULT_IMG_HEAD_PATH));
            }
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(file.toString())));
            byte[] b = new byte[1024];
            OutputStream os = response.getOutputStream();
            while(bis.read(b) != -1){
                os.write(b);
            }
            bis.close();
            os.flush();
        } catch (FileNotFoundException e) {
            logger.error("头像文件不存在:"+id,e);
        } catch (IOException e) {
            logger.error("头像IO异常:"+id,e);
        }
    }

    /**
     * 检查旧密码
     *
     * @return
     * @throws GenericException
     */
    @RequestMapping(value = "/checkOldPwd", method = RequestMethod.GET)
    public @ResponseBody Map<String,Object> checkOldPwd(@RequestParam(value = "oldPwd") String oldPwd,
            HttpServletResponse response) throws GenericException {
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
            String userId = userLoginDetails.getUserid();
            TenantUser user = this.tenantUserService.getByPK(userId);
            boolean checkRes = this.tenantUserService.credentialsPassword(user, oldPwd);
            if(checkRes){
                map.put("success",true);
            }else{
                map.put("success", false);
                map.put("message", "旧密码不正确");
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "系统错误，请联系管理员!");
        }
        return map;
    }

    /**
     * 修改密码
     * @param key
     * @param request
     * @return
     */
    @RequestMapping("/profile/password")
    public ModelAndView resetPassword(@RequestParam("key") String key,HttpServletRequest request){
        Serial serial = this.serialService.findByKey(key);
        Map<String, Object> maps = Maps.newHashMap();
        if(serial==null||new DateTime(serial.getEndDate()).isBeforeNow()){//key值无效
            maps.put("invalidKey", false);
        }else{
            maps.put("invalidKey", true);
        }
        return new ModelAndView("saas/sys/tenant/user/resetPassword",maps);
    }

    /**
     * 上传头像
     * @param filename
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public @ResponseBody Map<String, Object> handleFileUpload(@RequestParam("filename") String filename, @RequestParam("Filedata") MultipartFile file, HttpServletRequest request){
        Map<String, Object> datas = Maps.newHashMap();
        UserLoginDetails userLoginDetails = (UserLoginDetails)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        // 拿到上下文路径
        String filePath = uploadBasePath + "/tenant/user";
        File fileTemp = new File(filePath);
        if (!fileTemp.exists()) {
            // 建立文件夹
            fileTemp.mkdirs();
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

                TenantUser user = new TenantUser();
                user.setId(userLoginDetails.getUserid());
                user.setHeadPhoto(newFileName);
                tenantUserService.update(user, new String[]{"headPhoto"});
            }
            datas.put("success", true);
            datas.put("photoName", newFileName);
        } catch (IOException e) {
            logger.error("写入硬盘失败",e);
            datas.put("success", false);
        }
        return datas;
    }

    /**
     * 保存修改的密码
     * @return
     * @throws GenericException
     */
    @RequestMapping(value = "/changePwd", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> changePwd(
            @RequestParam(value="password") String password,@RequestParam(value="key",required=false) String key)
            throws GenericException {

        Map<String,Object> map = Maps.newHashMap();
        try {
            TenantUser user;
            if(StringUtils.isBlank(key)){
                UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
                String userId = userLoginDetails.getUserid();
                user = this.tenantUserService.getByPK(userId);
                user.setPassword(password);
                this.tenantUserService.update(user, true);
                map.put("success", true);
            }else{
                Serial serial = this.serialService.findByKey(key);
                if(serial==null||new DateTime(serial.getEndDate()).isBeforeNow()){
                    //key值无效
                    map.put("success", false);
                    map.put("message", "无效的重设密码链接!");
                }else{
                    this.serialService.delete(serial);
                    user = this.tenantUserService.findByEmail(serial.getEmail());
                    user.setPassword(password);
                    map.put("success", true);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            map.put("success", false);
            map.put("message", "系统错误，请联系管理员");
        }
        return map;
    }

    /**
     * 更新头像
     * @param request
     * @return
     */
    @RequestMapping(value = "/update/photo",method=RequestMethod.POST)
    public @ResponseBody Map<String, Object> updateHeadPhoto(HttpServletRequest request)  throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        try {
            UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
            TenantUser entity = tenantUserService.getByPK(userLoginDetails.getUserid());
            String headPhotoImg = request.getParameter("headPhotoImg");
            String newFileName = "avatar.JPEG";
            if(StringUtils.isNotBlank(headPhotoImg)){
                headPhotoImg = headPhotoImg.replace("data:image/jpeg;base64,", "");
                byte[] img = Base64.decodeBase64(headPhotoImg);
                String filePath = uploadBasePath + "/tenant/user";
                File fileTemp = new File(filePath);
                if (!fileTemp.exists()) {
                    // 建立文件夹
                    fileTemp.mkdirs();
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
            this.tenantUserService.update(entity);
            userLoginDetails.setHeadPhoto(newFileName);
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.getSession().setAttribute(Constants.CURRENT_USER, userLoginDetails);
            map.put("success", true);
            map.put("photoName", newFileName);
        } catch(IOException e){
            logger.error(e);
            map.put("success", false);
            map.put("message", "头像保存失败");
        }catch (Exception e) {
            logger.error(e);
            map.put("success", false);
            map.put("message", "系统错误，请联系管理员");
        }
        return map;
    }

    @RequestMapping(value="/un/admin/activate")
    public String adminActivate(@PathVariable(value="id")String id){
        TenantUser tenantUser = tenantUserService.getByPK(id);
        if(TombstoneEntity.TOMBSTONE_TRUE == tenantUser.getTombstone() && TenantUser.STATUS_NO_ACTIVATION == tenantUser.getStatus()){
            tenantUser.setTombstone(TombstoneEntity.TOMBSTONE_FALSE);
            tenantUser.setStatus(TenantUser.STATUS_ACTIVATION);
            tenantUserService.update(tenantUser);
            return "redirect:/login";
        }else{
            throw new ErrorMsgShowException("激活链接异常");
        }

    }

    /**
     * 详情
     * @return
     */
    @RequestMapping(value = "/detail/{userId}")
    public ModelAndView detail(@PathVariable(value="userId") String userId){
        UserLoginDetails userLoginDetails = (UserLoginDetails)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        Map<String, Object> map = Maps.newHashMap();
        TenantUser user = tenantUserService.getByPK(userId);
        if (userLoginDetails.getTenant()!= null && StringUtils.isNotBlank(userLoginDetails.getTenantId()) && !userLoginDetails.getTenant().getId().equals(user.getTenant().getId())) {
            map.put("error", "您所在的租户下不存在相关用户，请重新查询");
            map.put("headPhoto", "error");
            map.put("user", new TenantUser());
        } else {
            map.put("headPhoto", user.getHeadPhoto());
            map.put("user", user);
        }
        return new ModelAndView("/saas/sys/tenant/user/userDetail",map);
    }

    @RequestMapping(value="/forgot",method = RequestMethod.GET)
    public ModelAndView forgot(){
        return new ModelAndView("saas/sys/tenant/user/forgot");
    }

    @RequestMapping(value="/forgot",method = RequestMethod.POST)
    public ModelAndView forgotSend(String email)  throws GenericException{
        Map<String,Object> map = new HashMap<String,Object>();
        try{
            passwordResetService.addForgot(email);
            map.put("success",true);
        }catch (Exception e){
            map.put("success",false);
            map.put("message",e.getMessage());
        }
        return new ModelAndView("saas/sys/tenant/user/forgotApplySuc",map);
    }

    @RequestMapping(value="/pwdreset/{id}", method = RequestMethod.GET)
    public ModelAndView pwdReset(@PathVariable(value="id")String passwordResetId){
        Map<String,Object> map = new HashMap<String,Object>();
        PasswordReset entity = passwordResetService.getByPK(passwordResetId);
        if(entity == null){
            return new ModelAndView("saas/sys/tenant/user/resetPasswordNotValidate",map);
        }
        map.put("entity", entity);
        return new ModelAndView("saas/sys/tenant/user/resetPassword",map);
    }

    @RequestMapping(value="/pwdreset/{id}", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> pwdResetSave(@PathVariable(value="id")String passwordResetId,String password){
        Map<String,Object> map = new HashMap<String,Object>();
        PasswordReset entity = passwordResetService.getByPK(passwordResetId);
        if(entity == null){
            map.put("code",-1);
            map.put("message","链接已失效，请重新申请重置密码");
        }
        passwordResetService.addReset(passwordResetId,password);
        map.put("code",1);
        return map;
    }

    /**
     * 注册到新的租户
     * @param userId
     * @param newTenantId
     * @param newTenantName
     * @return
     */
    @RequestMapping(value = "/registerToNewTenant")
    public @ResponseBody Map<String, Object> registerToNewTenant(@RequestParam("userId") String userId,
                                                                 @RequestParam("newTenantId") String newTenantId,
                                                                 @RequestParam("newTenantName") String newTenantName,
                                                                 @RequestParam("activationId") String activationId) throws GenericException{
        Map<String, Object> map = Maps.newHashMap();
        TenantUser tenantUser = tenantUserService.getByPK(userId);
        // 修改邀请表的状态
        Activation activation = activationService.getByPK(activationId);
        activation.setStatus("1");
        try {
            if (tenantUser == null) {
                map.put("code", "1");// 用户不存在
                map.put("message", "用户已经不存在,不能加入到新租户");
                activationService.update(activation, new String[]{"status"});
                return map;
            }
            Tenant tenant = tenantService.getByPK(newTenantId);
            if (tenant == null) {
                map.put("code", "2");// 租户不存在
                map.put("message", "[" + newTenantName + "]已经不存在,不能加入到新租户");
                activationService.update(activation, new String[]{"status"});
                return map;
            }

            tenantUserService.saveRegisterToNewTenant(tenantUser, activation, tenant);
            map.put("code", "0");
            map.put("message", "您已经成功加入到[" + newTenantName + "],"+"请尽快登录并修改个人信息!");

            // 强制踢出用户
            sessionHelper.kickOutSession(tenantUser.getUsername());
        } catch (Exception e) {
            map.put("code", "-1");
            map.put("message", "您已经成功加入到[" + newTenantName + "],"+"请尽快登录并修改个人信息!");
        }
        return map;
    }


    /**
     *
     * @param email
     * @param type
     * @return
     * @throws GenericException
     */
    @RequestMapping(value = "/getActivateLink")
    public @ResponseBody Map<String, Object> getActivateLink(@RequestParam("email") String email,
                                                             @RequestParam("type") String type) throws GenericException {

        Map<String, Object> map = Maps.newHashMap();
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" email = :email", "email", email));
        try {
            List<TenantRegister> tenantRegisters = tenantRegisterService.list(query, null, false);
            if (tenantRegisters != null && tenantRegisters.size() > 0) {
                TenantRegister tenantRegister = tenantRegisters.get(0);
                // 获取激活链接
                if ("0".equals(type)) {
                    StringBuilder url = new StringBuilder();
                    url.append(domain + "/sys/tenant/register/activate/" + tenantRegister.getId());
                    String content = "您好，你注册\"" + tenantRegister.getTenantName() + "\"新租户以成功，请点击如下地址进行激活。\n" + url;
                    //mailService.sendEmail("【三零优异服务网】租户管理员激活", content, tenantRegister.getEmail());//发送邀请邮件
                    Map<String,String> paramMap = new HashMap<String,String>();
                    paramMap.put("tenantName",tenantRegister.getTenantName());
                    paramMap.put("url",url.toString());
                    MailSendSingleVo mailSendVo = new MailSendSingleVo(tenantRegister.getEmail(),paramMap);
                    MailResult mailResult = mailService.sendEmail("registertenant", null, null, mailSendVo,Boolean.FALSE);
                    map.put("code", "1");

                    map.put("message", "激活链接已经发到您的邮箱，请登录邮箱查看");
                } else if ("1".equals(type)) {
                    // 注册到新租户,删除未激活的记录
                    tenantRegisterService.deleteByPK(tenantRegister.getId());
                    map.put("code", "2");
                }
            } else {
                map.put("code", "-1");
                map.put("message", "链接已经过期，请刷新页面");
            }
        } catch (Exception e) {
            map.put("code", "-1");
            map.put("message", "链接已经过期，请刷新页面");
        }

        return map;
    }
}
