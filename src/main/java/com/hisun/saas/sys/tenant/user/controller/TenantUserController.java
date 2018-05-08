/*
 * Copyright (c) 2018. Hunan Hisun Union Information Technology Co, Ltd. All rights reserved.
 * http://www.hn-hisun.com
 * 注意:本内容知识产权属于湖南海数互联信息技术有限公司所有,除非取得商业授权,否则不得用于商业目的.
 */

package com.hisun.saas.sys.tenant.user.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.base.dao.util.CommonOrder;
import com.hisun.base.dao.util.CommonOrderBy;
import com.hisun.saas.sys.admin.user.entity.User;
import com.hisun.saas.sys.auth.Constants;
import com.hisun.saas.sys.auth.service.SessionHelper;
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
import com.hisun.saas.sys.log.LogOperateType;
import com.hisun.saas.sys.log.RequiresLog;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.role.service.TenantRoleService;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.entity.TenantDepartment;
import com.hisun.saas.sys.tenant.tenant.entity.TenantRegister;
import com.hisun.saas.sys.tenant.tenant.service.TenantRegisterService;
import com.hisun.saas.sys.tenant.tenant.service.TenantService;
import com.hisun.saas.sys.tenant.tenant.vo.TenantDepartmentVo;
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
import com.hisun.saas.sys.util.EntityWrapper;
import com.hisun.util.UUIDUtil;
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
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Rocky {rockwithyou@126.com}
 */
@Controller
@RequestMapping("/sys/tenant/user")
public class TenantUserController extends BaseController {

    @Resource
    private TenantUserService tenantUserService;
    @Value("${sys.upload.absolute.path}")
    private String uploadBasePath;
    @Resource
    private ActivationService activationService;
    @Resource
    private MailService mailService;
    @Value(value = "${sys.domain}")
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


    @RequiresPermissions("tenantUser:*")
    @RequestMapping(value = "/index")
    public ModelAndView index() {
        Map<String, Object> map = Maps.newHashMap();
        return new ModelAndView("saas/sys/tenant/user/index", map);
    }

    @RequiresPermissions("tenantUser:*")
    @RequestMapping(value = "/ajax/list")
    public ModelAndView list(HttpServletRequest request,
                             @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                             @RequestParam(value = "searchContent", required = false) String searchContent) throws GenericException {
        UserLoginDetails currentUser = UserLoginDetailsUtil.getUserLoginDetails();
        String currentNodeId = com.hisun.util.StringUtils.trimNull2Empty(request.getParameter("currentNodeId"));
        String currentNodeName = com.hisun.util.StringUtils.trimNull2Empty(request.getParameter("currentNodeName"));
        String currentNodeParentId = com.hisun.util.StringUtils.trimNull2Empty(request.getParameter("currentNodeParentId"));
        Map<String, Object> map = Maps.newHashMap();
        try {
            CommonConditionQuery query = new CommonConditionQuery();
            query.add(CommonRestrictions.and(" tombstone =:tombstone", "tombstone", 0));
            if (com.hisun.util.StringUtils.isNotBlank(searchContent)) {
                query.add(CommonRestrictions.and(" (username like :username or realname like :username) ", "username", "%" + searchContent + "%"));
            }

            if (com.hisun.util.StringUtils.isEmpty(currentNodeParentId)) {
                //query.add(CommonRestrictions.and(" tenantDepartment.id is null ",null,null));
            } else {
                query.add(CommonRestrictions.and(" tenantDepartment.id = :id ", "id", currentNodeId));
            }
            CommonOrderBy orderBy = new CommonOrderBy();
            //orderBy.add(CommonOrder.asc("tenantDepartment.sort"));
            orderBy.add(CommonOrder.asc("sort"));
            Long total = tenantUserService.count(query);
            List<TenantUser> entities = this.tenantUserService.list(query, orderBy, pageNum, pageSize);
            List<TenantUserVo> vos = new ArrayList<>();
            TenantUserVo vo = null;
            if (entities != null) {
                for (TenantUser entity : entities) {
                    vo = new TenantUserVo();
                    BeanUtils.copyProperties(vo, entity);
                    vos.add(vo);
                }
            }
            PagerVo<TenantUserVo> pager = new PagerVo<TenantUserVo>(vos, total.intValue(), pageNum, pageSize);
            map.put("pager", pager);
            map.put("currentNodeId", currentNodeId);
            map.put("currentNodeName", currentNodeName);
            map.put("currentNodeParentId", currentNodeParentId);
            map.put("searchContent", searchContent);
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e);
        }
        return new ModelAndView("saas/sys/tenant/user/userList", map);
    }

    @RequiresPermissions("tenantUser:*")
    @RequestMapping(value = "/add")
    public ModelAndView add(HttpServletRequest request) {
        Map<String, Object> map = Maps.newHashMap();
        String currentNodeId = com.hisun.util.StringUtils.trimNull2Empty(request.getParameter("currentNodeId"));
        //CommonConditionQuery query = new CommonConditionQuery();
        //List<TenantRole> roles = this.tenantRoleService.list(query, null);
        //map.put("roles", roles);
        map.put("currentNodeId", currentNodeId);
        return new ModelAndView("saas/sys/tenant/user/addUser", map);
    }

    @RequestMapping(value = "/check/username")
    public
    @ResponseBody
    Map<String, Object> checkUserName(@RequestParam(value = "username") String username) throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        CommonConditionQuery query = new CommonConditionQuery();
        query.add(CommonRestrictions.and(" username = :username", "username", username.trim()));
        try {
            long tenantUserCount = tenantUserService.count(query);
            if (tenantUserCount == 0) {
                map.put("success", true);
            } else {
                map.put("success", false);
                map.put("message", "该账号已存在!");
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "系统错误!");
        }
        return map;
    }

    @RequiresLog(operateType = LogOperateType.SAVE, description = "新增用户:${userVo.userRealName}")
    @RequiresPermissions("tenantUser:*")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> save(@ModelAttribute("userVo") TenantUserVo userVo, HttpServletRequest request) throws GenericException {
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        Map<String, Object> map = Maps.newHashMap();
        TenantUser user = new TenantUser();
        try {
            if (this.tenantUserService.checkUsername(userVo.getUsername()) == false) {
                String headPhotoImg = request.getParameter("headPhotoImg");
                String photoFile = "avatar.JPEG";
                if (StringUtils.isNotBlank(headPhotoImg)) {
                    headPhotoImg = headPhotoImg.replace("data:image/jpeg;base64,", "");
                    byte[] img = Base64.decodeBase64(headPhotoImg);
                    String photoStoreRealPath = uploadBasePath + File.separator + "tenant"
                            + File.separator + userLoginDetails.getTenantId() + File.separator + "user";
                    File photoStoreRealPathFile = new File(photoStoreRealPath);
                    if (!photoStoreRealPathFile.exists()) {
                        photoStoreRealPathFile.mkdirs();
                    }
                    photoFile = userVo.getUsername() + "_" + UUIDUtil.getUUID() + ".JPEG";
                    File destFile = new File(photoStoreRealPath + File.separator + photoFile);
                    if (!destFile.exists()) {
                        destFile.createNewFile();
                    }
                    FileUtils.writeByteArrayToFile(destFile, img);
                }
                BeanUtils.copyProperties(user, userVo);
                user.setHeadPhoto(photoFile);
                user.setTenant(userLoginDetails.getTenant());
                user.setPassword(userVo.getPwd());
                this.tenantUserService.save(user);
                map.put("success", true);
                map.put("message", "保存成功!");
            } else {
                map.put("success", false);
                map.put("message", "该账号已存在!");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GenericException(e.getMessage());
        }
        return map;
    }


    @RequiresLog(operateType = LogOperateType.UPDATE, description = "锁定用户:${userid}")
    @RequiresPermissions("tenantUser:*")
    @RequestMapping(value = "/lock/{userid}")
    public
    @ResponseBody
    Map<String, Object> lock(@PathVariable(value = "userid") String userid)
            throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        try {
            UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
            TenantUser user = this.tenantUserService.getByPK(userid);
            if (user.getTenant().getId().equals(userLoginDetails.getTenantId())) {
                user.setLocked(true);
                EntityWrapper.wrapperUpdateBaseProperties(user, userLoginDetails);
                this.tenantUserService.update(user);
                map.put("success", true);
                map.put("message", "已锁定!");
            } else {
                map.put("success", false);
                map.put("msg", "锁定失败!");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GenericException(e.getMessage());
        }
        return map;
    }


    @RequiresLog(operateType = LogOperateType.UPDATE, description = "解锁用户:${userid}")
    @RequiresPermissions("tenantUser:*")
    @RequestMapping(value = "/unlock/{userid}", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> unLock(@PathVariable(value = "userid") String userid)
            throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        try {
            UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
            TenantUser user = this.tenantUserService.getByPK(userid);
            if (user.getTenant().getId().equals(userLoginDetails.getTenantId())) {
                user.setLocked(false);
                EntityWrapper.wrapperUpdateBaseProperties(user, userLoginDetails);
                this.tenantUserService.update(user);
                map.put("success", true);
                map.put("message", "已解锁!");
            } else {
                map.put("success", false);
                map.put("msg", "解锁失败!");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GenericException(e.getMessage());
        }
        return map;
    }


    @RequiresLog(operateType = LogOperateType.DELETE, description = "删除用户:${userId}")
    @RequiresPermissions("tenantUser:*")
    @RequestMapping(value = "/delete/{userId}")
    public
    @ResponseBody
    Map<String, Object> delete(@PathVariable("userId") String userId) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        try {
            UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
            TenantUser user = this.tenantUserService.getByPK(userId);
            if (user.getTenant().getId().equals(userLoginDetails.getTenantId())) {
                this.tenantUserService.addTombstone(user);
                map.put("success", true);
                map.put("message", "注销成功!");
            } else {
                map.put("success", false);
                map.put("msg", "注销失败!");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GenericException(e.getMessage());
        }
        return map;
    }


    @RequiresPermissions("tenantUser:*")
    @RequestMapping(value = "/edit/{id}")
    public ModelAndView edit(@PathVariable("id") String id,HttpServletRequest request) {
        Map<String, Object> map = Maps.newHashMap();
        String currentNodeId = com.hisun.util.StringUtils.trimNull2Empty(request.getParameter("currentNodeId"));
        try {
            TenantUser entity = this.tenantUserService.getByPK(id);
            TenantUserVo vo = new TenantUserVo();
            BeanUtils.copyProperties(vo, entity);
            map.put("vo", vo);
            map.put("currentNodeId", currentNodeId);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GenericException(e.getMessage());
        }
        return new ModelAndView("saas/sys/tenant/user/editUser", map);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> get(@PathVariable("id") String id) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        try {
            TenantUser entity = this.tenantUserService.getByPK(id);
            TenantUserVo vo = new TenantUserVo();
            BeanUtils.copyProperties(vo, entity);
            map.put("vo", vo);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "获取用户信息失败!");
        }
        return map;
    }


    @RequiresLog(operateType = LogOperateType.UPDATE, description = "重置用户密码:${id}")
    @RequiresPermissions("tenantUser:*")
    @RequestMapping(value = "/reset/password/{userId}")
    public
    @ResponseBody
    Map<String, Object> resetPwd(@PathVariable("userId") String userId) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        try {
            String newPwd = userId + "111111";
            TenantUser user = this.tenantUserService.getByPK(userId);
            if (user.getTenant().getId().equals(userLoginDetails.getTenantId())) {
                EntityWrapper.wrapperUpdateBaseProperties(user, userLoginDetails);
                this.tenantUserService.resetPwd(user, newPwd);
            } else {
                map.put("success", false);
                map.put("msg", "重置密码失败!");
            }
            map.put("success", true);
            map.put("msg", "重置密码成功!");
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GenericException(e.getMessage());
        }
        return map;
    }

    @RequiresLog(operateType = LogOperateType.UPDATE, description = "修改用户信息:${userVo.userRealName}")
    @RequiresPermissions("tenantUser:*")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> update(TenantUserVo userVo) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        try {
            TenantUser entity = tenantUserService.getByPK(userVo.getId());
            if (entity.getTenant().getId().equals(userLoginDetails.getTenantId())) {
                EntityWrapper.wrapperUpdateBaseProperties(entity, userLoginDetails);
                entity.setRealname(userVo.getRealname());
                entity.setSpecialty(userVo.getSpecialty());
                entity.setPositional(userVo.getPositional());
                entity.setAbout(userVo.getAbout());
                entity.setWebsite(userVo.getWebsite());
                entity.setTel(userVo.getTel());
                entity.setEmail(userVo.getEmail());
                this.tenantUserService.update(entity);
                map.put("success", true);
                map.put("msg", "修改成功!");
            } else {
                map.put("success", false);
                map.put("msg", "修改失败!");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new GenericException(e.getMessage());
        }
        return map;
    }


    @RequiresPermissions("tenantUser:*")
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
                    if (tenantUser.getTenant() != null && tenantId.equals(tenantUser.getTenant().getId())) {
                        map.put("status", "0");// 已经注册到当前租户（属于重复邀请的情况,无需再注册）
                        map.put("user", user);
                        map.put("tenant", tenant);
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
                        map.put("tenant", tenant);
                        map.put("user", user);
                        map.put("registerName", tenantRegister.getTenantName());
                        map.put("tenant", tenant);
                        ret = new ModelAndView("registerToNewTenant", map);
                    } else {
                        map.put("tenant", tenant);
                        map.put("user", user);
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

    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> activateSave(@RequestParam(value = "activationId") String activationId,
                                     TenantRegisterVo vo) throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        Activation activation = activationService.getByPK(activationId);
        if (activation == null || !"0".equals(activation.getStatus())) {
            map.put("code", -1);
            map.put("message", "邀请不存在，或已激活");
        } else {
            vo.setEmail(activation.getEmail());
            tenantUserService.saveInviteRegister(vo, activationId, activation.getRoleId(), activation.getInviteTenantId());
            map.put("code", 1);
        }
        return map;
    }


    @RequestMapping(value = "/getRoleSelection/{userId}")
    public
    @ResponseBody
    Map<String, Object> getRoleSelection(
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
            logger.error(e, e);
            map.put("success", false);
        }
        return map;
    }

    @RequiresPermissions("tenant:userlist")
    @RequestMapping(value = "/saveRelation", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> saveRelation(
            @RequestParam(value = "userId") String userId,
            @RequestParam(value = "chooseRoles", required = false) String[] chooseRoles)
            throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        try {

            this.tenantUserRoleService.saveOrUpdate(userId, chooseRoles);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
        }
        return map;
    }


    @RequestMapping(value = "/profile")
    public ModelAndView profile() {
        UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
        Map<String, Object> map = Maps.newHashMap();
        TenantUser user = tenantUserService.findByUsername(userLoginDetails.getUsername());
        if (null == user.getHeadPhoto()) {
            map.put("headPhoto", "noPhoto");
        } else {
            map.put("headPhoto", user.getHeadPhoto());
        }

        map.put("user", user);
        return new ModelAndView("saas/sys/tenant/user/profile", map);
    }

    @RequestMapping("/photo/{photoName}")
    public ResponseEntity<byte[]> photoToStream(@PathVariable("photoName") String photoName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        File file = new File(uploadBasePath + "/tenant/user/" + photoName + ".JPEG");
        if (file.exists()) {
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.OK);
        } else {
            //为空或者没有返回默认图片
            headers.setContentType(MediaType.IMAGE_PNG);
            file = new File(request.getServletContext().getRealPath(DEFAULT_IMG_HEAD_PATH));
            return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                    headers, HttpStatus.OK);
        }
    }

    @RequestMapping("/headimg/{id}")
    public void headImgToStream(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        TenantUser tenantUser = tenantUserService.getByPK(id);
        File file = null;
        if (tenantUser == null || StringUtils.isBlank(tenantUser.getHeadPhoto())) {
            //为空或者没有返回默认图片
            headers.setContentType(MediaType.IMAGE_PNG);
            file = new File(request.getServletContext().getRealPath(DEFAULT_IMG_HEAD_PATH));
        } else {
            file = new File(uploadBasePath + "/tenant/user/" + tenantUser.getHeadPhoto());
            if (file.exists()) {
                headers.setContentType(MediaType.IMAGE_JPEG);
            } else {
                headers.setContentType(MediaType.IMAGE_PNG);
                file = new File(request.getServletContext().getRealPath(DEFAULT_IMG_HEAD_PATH));
            }
        }
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(file.toString())));
            byte[] b = new byte[1024];
            OutputStream os = response.getOutputStream();
            while (bis.read(b) != -1) {
                os.write(b);
            }
            bis.close();
            os.flush();
        } catch (FileNotFoundException e) {
            logger.error("头像文件不存在:" + id, e);
        } catch (IOException e) {
            logger.error("头像IO异常:" + id, e);
        }
    }

    @RequestMapping(value = "/checkOldPwd", method = RequestMethod.GET)
    public
    @ResponseBody
    Map<String, Object> checkOldPwd(@RequestParam(value = "oldPwd") String oldPwd,
                                    HttpServletResponse response) throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
            String userId = userLoginDetails.getUserid();
            TenantUser user = this.tenantUserService.getByPK(userId);
            boolean checkRes = this.tenantUserService.credentialsPassword(user, oldPwd);
            if (checkRes) {
                map.put("success", true);
            } else {
                map.put("success", false);
                map.put("message", "旧密码不正确");
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "系统错误，请联系管理员!");
        }
        return map;
    }

    @RequestMapping("/profile/password")
    public ModelAndView resetPassword(@RequestParam("key") String key, HttpServletRequest request) {
        Serial serial = this.serialService.findByKey(key);
        Map<String, Object> maps = Maps.newHashMap();
        if (serial == null || new DateTime(serial.getEndDate()).isBeforeNow()) {//key值无效
            maps.put("invalidKey", false);
        } else {
            maps.put("invalidKey", true);
        }
        return new ModelAndView("saas/sys/tenant/user/resetPassword", maps);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> handleFileUpload(@RequestParam("filename") String filename, @RequestParam("Filedata") MultipartFile file, HttpServletRequest request) {
        Map<String, Object> datas = Maps.newHashMap();
        UserLoginDetails userLoginDetails = (UserLoginDetails) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        // 拿到上下文路径
        String filePath = uploadBasePath + "/tenant/user";
        File fileTemp = new File(filePath);
        if (!fileTemp.exists()) {
            // 建立文件夹
            fileTemp.mkdirs();
        }
        // 写入到硬盘
        try {
            String newFileName = userLoginDetails.getUserid() + "_" + System.currentTimeMillis() + ".JPEG";
            File destFile = new File(filePath + "/" + newFileName);
            if (!destFile.exists()) {
                // 建立文件
                destFile.createNewFile();
            }
            FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
            String str = request.getParameter("uploadtype");
            if (StringUtils.isBlank(str)) {
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
            logger.error("写入硬盘失败", e);
            datas.put("success", false);
        }
        return datas;
    }

    @RequestMapping(value = "/changePwd", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> changePwd(
            @RequestParam(value = "password") String password, @RequestParam(value = "key", required = false) String key)
            throws GenericException {

        Map<String, Object> map = Maps.newHashMap();
        try {
            TenantUser user;
            if (StringUtils.isBlank(key)) {
                UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
                String userId = userLoginDetails.getUserid();
                user = this.tenantUserService.getByPK(userId);
                user.setPassword(password);
                this.tenantUserService.update(user, true);
                map.put("success", true);
            } else {
                Serial serial = this.serialService.findByKey(key);
                if (serial == null || new DateTime(serial.getEndDate()).isBeforeNow()) {
                    //key值无效
                    map.put("success", false);
                    map.put("message", "无效的重设密码链接!");
                } else {
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

    @RequestMapping(value = "/update/photo", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> updateHeadPhoto(HttpServletRequest request) throws GenericException {
        Map<String, Object> map = Maps.newHashMap();
        try {
            UserLoginDetails userLoginDetails = UserLoginDetailsUtil.getUserLoginDetails();
            TenantUser entity = tenantUserService.getByPK(userLoginDetails.getUserid());
            String headPhotoImg = request.getParameter("headPhotoImg");
            String newFileName = "avatar.JPEG";
            if (StringUtils.isNotBlank(headPhotoImg)) {
                headPhotoImg = headPhotoImg.replace("data:image/jpeg;base64,", "");
                byte[] img = Base64.decodeBase64(headPhotoImg);
                String filePath = uploadBasePath + "/tenant/user";
                File fileTemp = new File(filePath);
                if (!fileTemp.exists()) {
                    // 建立文件夹
                    fileTemp.mkdirs();
                }
                newFileName = userLoginDetails.getUserid() + "_" + System.currentTimeMillis() + ".JPEG";
                File destFile = new File(filePath + "/" + newFileName);
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
        } catch (IOException e) {
            logger.error(e);
            map.put("success", false);
            map.put("message", "头像保存失败");
        } catch (Exception e) {
            logger.error(e);
            map.put("success", false);
            map.put("message", "系统错误，请联系管理员");
        }
        return map;
    }


    @RequestMapping(value = "/detail/{userId}")
    public ModelAndView detail(@PathVariable(value = "userId") String userId) {
        UserLoginDetails userLoginDetails = (UserLoginDetails) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        Map<String, Object> map = Maps.newHashMap();
        TenantUser user = tenantUserService.getByPK(userId);
        if (userLoginDetails.getTenant() != null && StringUtils.isNotBlank(userLoginDetails.getTenantId()) && !userLoginDetails.getTenant().getId().equals(user.getTenant().getId())) {
            map.put("error", "您所在的租户下不存在相关用户，请重新查询");
            map.put("headPhoto", "error");
            map.put("user", new TenantUser());
        } else {
            map.put("headPhoto", user.getHeadPhoto());
            map.put("user", user);
        }
        return new ModelAndView("saas/sys/tenant/user/userDetail", map);
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public ModelAndView forgot() {
        return new ModelAndView("saas/sys/tenant/user/forgot");
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.POST)
    public ModelAndView forgotSend(String email) throws GenericException {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            passwordResetService.addForgot(email);
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
        }
        return new ModelAndView("saas/sys/tenant/user/forgotApplySuc", map);
    }

    @RequestMapping(value = "/pwdreset/{id}", method = RequestMethod.GET)
    public ModelAndView pwdReset(@PathVariable(value = "id") String passwordResetId) {
        Map<String, Object> map = new HashMap<String, Object>();
        PasswordReset entity = passwordResetService.getByPK(passwordResetId);
        if (entity == null) {
            return new ModelAndView("saas/sys/tenant/user/resetPasswordNotValidate", map);
        }
        map.put("entity", entity);
        return new ModelAndView("saas/sys/tenant/user/resetPassword", map);
    }

    @RequestMapping(value = "/pwdreset/{id}", method = RequestMethod.POST)
    public
    @ResponseBody
    Map<String, Object> pwdResetSave(@PathVariable(value = "id") String passwordResetId, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        PasswordReset entity = passwordResetService.getByPK(passwordResetId);
        if (entity == null) {
            map.put("code", -1);
            map.put("message", "链接已失效，请重新申请重置密码");
        }
        passwordResetService.addReset(passwordResetId, password);
        map.put("code", 1);
        return map;
    }

}
