package com.hisun.saas.sys.tenant.role.controller;

import com.google.common.collect.Maps;
import com.hisun.base.controller.BaseController;
import com.hisun.base.exception.ErrorMsgShowException;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.admin.resource.service.ResourceService;
import com.hisun.saas.sys.tenant.role.entity.TenantRole;
import com.hisun.saas.sys.tenant.role.service.TenantRoleService;
import com.hisun.saas.sys.tenant.role.vo.TenantRoleVo;
import com.hisun.saas.sys.tenant.vo.TreeNodeVo;
import com.hisun.util.ValidateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Controller
@RequestMapping("/sys/tenant/role")
public class TenantRoleController extends BaseController {

    @javax.annotation.Resource
    private TenantRoleService tenantRoleService;

    @javax.annotation.Resource
    private ResourceService resourceService;

    @RequiresPermissions("tenant:rolelist")
    @RequestMapping("/list")
    public ModelAndView list(String name,@RequestParam(value="pageNum",defaultValue = "1")int pageNum
            ,@RequestParam(value = "pageSize",defaultValue = "15")int pageSize) throws UnsupportedEncodingException {
        Map<String,Object> model = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(name)){
            name = URLDecoder.decode(name,"UTF-8");
        }
        PagerVo<TenantRole> pager = tenantRoleService.listPager(name,pageNum,pageSize);
        model.put("pager",pager);
        model.put("name",name);
        return new ModelAndView("saas/sys/tenant/role/list",model);
    }

    @RequiresPermissions("tenant:roleadd")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> save(TenantRoleVo vo)  throws GenericException {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,String> vMap = ValidateUtil.validAll(vo);
        if(vMap.size()>0){
            returnMap.put("message","数据验证不通过");
            returnMap.put("code",-1);
            return returnMap;
        }
        try{
            tenantRoleService.add(vo);
            returnMap.put("code",1);
        }catch (Exception e){
            logger.error(e,e);
            returnMap.put("code",-1);
        }
        return returnMap;
    }

    @RequiresPermissions("tenant:roleadd")
    @RequestMapping(value = "/add")
    public ModelAndView add(){
        Map<String, Object> map = Maps.newHashMap();
        return new ModelAndView("saas/sys/tenant/role/add",map);
    }

    @RequiresPermissions("tenant:roledel")
    @RequestMapping(value="/delete/{roleId}")
    public @ResponseBody Map<String,Object> delete(@PathVariable("roleId") String roleId){
        Map<String,Object> map = new HashMap<String,Object>();
        if(roleId!=null && roleId.trim().equals("")==false){
            tenantRoleService.deleteById(roleId);
            map.put("code", 1);
        }else{
            map.put("code",-1);
            map.put("message","角色不存在");
        }
        return map;
    }

    @RequiresPermissions("tenant:roledel")
    @RequestMapping(value="/edit/{roleId}",method = RequestMethod.GET)
    public ModelAndView update(@PathVariable("roleId") String roleId){
        Map<String,Object> map = new HashMap<String,Object>();
        TenantRole entity = tenantRoleService.getByPK(roleId);
        if(entity == null){
            throw new ErrorMsgShowException("数据不存在");
        }
        map.put("entity", entity);
        map.put("id",roleId);
        return new ModelAndView("saas/sys/tenant/role/edit",map);
    }

    @RequiresPermissions("tenant:roledel")
    @RequestMapping(value="/update",method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> update(TenantRoleVo vo)throws GenericException{
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,String> vMap = ValidateUtil.validAll(vo);
        if(vMap.size()>0){
            returnMap.put("message","数据验证不通过");
            returnMap.put("code",-1);
            return returnMap;
        }
        tenantRoleService.update(vo);
        returnMap.put("code",1);
        return  returnMap;
    }

    /**
     * 关联资源
     * @param roleId
     * @return
     */
    @RequiresPermissions("tenant:roleresource")
    @RequestMapping(value="/setresource/{roleId}",method = RequestMethod.GET)
    public ModelAndView authorization(@PathVariable("roleId") String roleId){
        Map<String,Object> map = new HashMap<String,Object>();
        TenantRole tenantRole = tenantRoleService.getByPK(roleId);
        if(tenantRole==null){
            throw new ErrorMsgShowException("角色不存在");
        }
        map.put("entity", tenantRole);
        return new ModelAndView("saas/sys/tenant/role/resourceTree",map);
    }

    /**
     * 关联资源获取资源树
     * @param roleId
     * @return
     */
    @RequiresPermissions("tenant:roleresource")
    @RequestMapping("/setresource/tree/{roleId}")
    public @ResponseBody List<TreeNodeVo> getSetResourceTree(@PathVariable(value = "roleId")String roleId){
        List<TreeNodeVo> list = Collections.EMPTY_LIST;
        try{
            list = tenantRoleService.getRoleInstallResourceTree(roleId);
        }catch (Exception e){
            logger.error(e,e);
        }
        return list;
    }

    /**
     * 保存关联资源
     * @param roleId
     * @param resourceIds
     * @return
     * @throws GenericException
     */
    @RequiresPermissions("tenant:roleresource")
    @RequestMapping(value = "/setresource/save/{roleId}", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object>  setAuth(@PathVariable(value="roleId")String roleId,String[] resourceIds)
            throws GenericException {
        Map<String, Object> map =  Maps.newHashMap();
        TenantRole role = tenantRoleService.getByPK(roleId);
        if(role==null){
            map.put("code",-1);
            map.put("message","角色不存在");
            return map;
        }
        tenantRoleService.saveResources(roleId,resourceIds);
        map.put("code",1);
        return map;
    }
}
