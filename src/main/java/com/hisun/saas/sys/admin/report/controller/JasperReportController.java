package com.hisun.saas.sys.admin.report.controller;

import com.hisun.saas.sys.admin.report.entity.JasperReportTemp;
import com.hisun.saas.sys.admin.report.service.JasperReportTempService;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.saas.sys.auth.Constants;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.exception.GenericException;
import com.hisun.base.vo.PagerVo;
import com.hisun.saas.sys.tenant.tenant.entity.Tenant;
import com.hisun.saas.sys.tenant.tenant.service.TenantService;
import com.hisun.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by jamin30 on 2015/10/23.
 */

@Controller
@RequestMapping("/sys/report")
public class JasperReportController extends BaseController {

    @javax.annotation.Resource(name="resourcesProperties")
    private Properties resourcesProperties;

    @Resource
    private JasperReportTempService jasperReportTempService;

    @Resource
    private TenantService tenantService;

    private static final String OPERATION_ADD = "0";//新增操作

    private static final String OPERATION_UPDATE = "1";//修改操作

    /**
     * 查询列表
     * @param request
     * @param pageNum
     * @param pageSize
     * @param status
     * @return
     */
    @RequiresPermissions("report:list")
    @RequestMapping(value="/list", method= RequestMethod.GET)
    public ModelAndView executorList(HttpServletRequest request,
                                     @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10") int pageSize
            ,@RequestParam(value="status",defaultValue="-1")int status)  throws GenericException {
//        pageSize = pageSize>10?10:pageSize;
        Map<String, Object> map = new HashMap<String, Object>();
        try{
                Long total = this.jasperReportTempService.count();
            CommonConditionQuery query = new CommonConditionQuery();
            List<JasperReportTemp> resultList = this.jasperReportTempService.list(query , null, pageNum, pageSize);
            PagerVo<JasperReportTemp> pager = new PagerVo<JasperReportTemp>(resultList, total.intValue(), pageNum, pageSize);
            map.put("pager", pager);
        }catch(Exception e){
            logger.error(e);
            throw new GenericException(e);
        }
        ModelAndView modelView = new ModelAndView("saas/sys/admin/report/reportTemplatelist", map);
        return modelView;
    }

    /**
     * 新增页面
     * @return
     */
    @RequestMapping(value="/ajax/add", method=RequestMethod.GET)
    public ModelAndView openAddReportTemp(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type",OPERATION_ADD);
        ModelAndView modelView = new ModelAndView("saas/sys/admin/report/addReportTemp",map);
        return modelView;
    }

    /**
     * 新增跟修改保存的方法
     * @param jasperReportTemp
     * @param request
     * @param response
     * @param type
     * @return
     */
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public @ResponseBody Map<String,Object> save(@ModelAttribute("jasperReportTemp") JasperReportTemp jasperReportTemp, HttpServletRequest request,HttpServletResponse response,String type)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        UserLoginDetails userLoginDetails = (UserLoginDetails) SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        String user_id = userLoginDetails.getUserid();
        String user_name = userLoginDetails.getUsername();
        boolean flag = false;
        try
        {
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            String newName = "";
            String uploadPath=resourcesProperties.getProperty("sys.upload.absolute.path");
            uploadPath = uploadPath+File.separator+"reportTemp";
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String tempFile = formatter.format(date);
            uploadPath = uploadPath+File.separator+tempFile;
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                String prefix=null;
                String postfix=null;
                while (iter.hasNext()){
                    MultipartFile file = multiRequest.getFile(iter.next());
                    String fileName=file.getOriginalFilename();
                    int index=fileName.lastIndexOf('.');
                    if(index==-1){
                        prefix=fileName;
                    }else{
                        prefix=fileName.substring(0, index);//前缀
                        postfix=fileName.substring(index+1,fileName.length());//后缀
                    }
                    newName= FileUtil.getNewFileName(prefix)+"."+postfix;
//						newName = fileName;
                    FileUtil.saveFile(file, uploadPath, newName);
                    flag = true;
                    break;
                }
            }

            if(OPERATION_ADD.equals(type))
            {
                jasperReportTemp.setReportTempPath(uploadPath +File.separator + newName);
                jasperReportTemp.setCreateDate(date);
                //jasperReportTemp.setCreateUser(new User(user_id));
                jasperReportTemp.setCreateUserName(user_name);
                jasperReportTemp.setUpdateDate(date);
                //jasperReportTemp.setUpdateUser(new User(user_id));
                jasperReportTemp.setUpdateUserName(user_name);
                //jasperReportTemp.setTenant(getTenant());
                this.jasperReportTempService.save(jasperReportTemp);
            }
            else
            {
                JasperReportTemp jasperReportTempNew = this.jasperReportTempService.getByPK(jasperReportTemp.getId());
                if(null==jasperReportTempNew)
                {
                    map.put("success", false);
                    map.put("message", "该条数据不存在");
                    map.put("code", 2);
                    return map;
                }
                if(flag)
                {
                    jasperReportTempNew.setReportTempPath(uploadPath +File.separator + newName);
                }
                else
                {
                    jasperReportTempNew.setReportTempPath(jasperReportTemp.getReportTempPath());
                }
                jasperReportTempNew.setReportTempName(jasperReportTemp.getReportTempName());
                jasperReportTempNew.setReportTempType(jasperReportTemp.getReportTempType());
                jasperReportTempNew.setReportTempDesc(jasperReportTemp.getReportTempDesc());
                jasperReportTempNew.setTombstone(jasperReportTemp.getTombstone());
                jasperReportTempNew.setUpdateDate(date);
                //jasperReportTempNew.setUpdateUser(new User(user_id));
                jasperReportTempNew.setUpdateUserName(user_name);
                //jasperReportTempNew.setTenant(getTenant());
                this.jasperReportTempService.update(jasperReportTempNew);
            }

            map.put("success", true);
            map.put("code", 1);

        }catch (Exception e)
        {
            map.put("success", false);
            map.put("message", e.getMessage());
            map.put("code", 2);
        }
        return map;
    }

    /**
     * 删除某个报表文件
     * @param id
     * @return
     */
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    public @ResponseBody Map<String, Object> delete(String id){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        try {
           if(StringUtils.isNotBlank(id))
           {
               this.jasperReportTempService.deleteByPK(id);
           }
        } catch (Exception e) {
            logger.error(e);
            map.put("code", -1);
            map.put("message", "系统错误，请联系管理员");
            return map;
        }
        map.put("code", 1);
        return map;
    }


    /**
     * 更新页面
     * @param id
     * @return
     */
    @RequestMapping(value="/ajax/update", method=RequestMethod.GET)
    public ModelAndView openUpdate(String id)  throws GenericException{
        Map<String, Object> map = new HashMap<String, Object>();
        JasperReportTemp jasperReportTemp = this.jasperReportTempService.getByPK(id);
        if(jasperReportTemp==null){
            logger.error("数据不存在");
            throw new GenericException("数据不存在");
        }
        map.put("type",OPERATION_UPDATE);//标识是新增操作还是修改操作
        map.put("jasperReportTemp", jasperReportTemp);
        ModelAndView modelView = new ModelAndView("saas/sys/admin/report/addReportTemp",map);
        return modelView;
    }

    public Tenant getTenant()
    {
        UserLoginDetails sysUser = (UserLoginDetails)SecurityUtils.getSubject().getSession().getAttribute(Constants.CURRENT_USER);
        if (sysUser != null) {
            Tenant tenant = this.tenantService.getByPK(sysUser.getTenantId());
            return tenant;
        }
        return null;
    }
}
