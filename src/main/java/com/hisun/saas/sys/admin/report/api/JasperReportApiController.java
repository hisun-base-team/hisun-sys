package com.hisun.saas.sys.admin.report.api;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hisun.saas.sys.admin.report.entity.JasperReportTemp;
import com.hisun.saas.sys.admin.report.service.JasperReportTempService;
import com.hisun.saas.sys.admin.report.vo.JasperReportVo;
import com.hisun.base.auth.Constants;
import com.hisun.base.controller.BaseController;
import com.hisun.base.dao.util.CommonConditionQuery;
import com.hisun.base.dao.util.CommonRestrictions;
import com.hisun.saas.sys.admin.report.util.JasperReportUtil;
import com.hisun.saas.sys.admin.report.util.ReportDataSource;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBaseReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: JasperReportApiController.java </p>
 * <p>Package com.hisun.saas.sys.report.api </p>
 * <p>Description: 报表的外部接口</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author jamin30
 * @email
 * @date 2015年10月22日 am 9:43:03
 * @version 
 */
@Controller
@RequestMapping("/api/jasperReport")
public class JasperReportApiController extends BaseController{
	
	@Resource
	JasperReportTempService jasperReportTempService;

	@RequestMapping(value="/list",method=RequestMethod.POST)
	public @ResponseBody List<JasperReportVo> reportList(@RequestParam("tenantId") String tenantId) {
		List<JasperReportVo> resultList = Lists.newArrayList();
		try{
			CommonConditionQuery query = new CommonConditionQuery();
			query.add(CommonRestrictions.and(" tenant_id = :tenantId", "tenantId", tenantId));
			List<JasperReportTemp> reportTempList = this.jasperReportTempService.list(query, null);
			JasperReportVo vo = null;
			for(JasperReportTemp reportTemp : reportTempList){
				vo = new JasperReportVo();
				BeanUtils.copyProperties(vo, reportTemp);
				resultList.add(vo);
			}
		}catch (Exception e){
			resultList = Lists.newArrayList();
			logger.error("查询失败!",e);
		}
		return resultList;
	}

	/**
	 * 预览报表
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value="/effect/preview",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> reportPreview(@RequestParam String jsonStr){
		Map<String,Object> map = Maps.newHashMap();
		JasperReport jasperReport;
		JasperPrint jasperPrint;

		ReportDataSource reportDataSource = new ReportDataSource();
		try {
			Map<String,Object> objectMap = JasperReportUtil.getJsonValue(jsonStr);
			//获取模板路径：
			String tempId = (String)objectMap.get("id");
			JasperReportTemp jasperReportTemp = this.jasperReportTempService.getByPK(tempId);
			String tempPath = jasperReportTemp.getReportTempPath();
			if(StringUtils.isNotBlank(tempPath))
			{
				jasperReport = JasperCompileManager.compileReport(tempPath);
				List<Object> objectList = JasperReportUtil.jsonToArray(jsonStr);
				reportDataSource.setAttriButeNameList((List<String>)objectList.get(0));
				reportDataSource.setDataSet((List<List<Map>>) objectList.get(1));

				jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<String, Object>(), reportDataSource);

				JasperViewer jasperViewer = new JasperViewer(jasperPrint,false);
				jasperViewer.pack();
				jasperViewer.setVisible(true);
			}

				map.put("success", true);
		} catch (Exception e) {
			map.put("message", "预览失败!");
			map.put("success", false);
			logger.error("预览失败!",e);
		}
		return map;
	}


	/**
	 * 导出下载报表
	 * @param request
	 * @param response
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value="/effect/downLoad",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> reportDownLoad(HttpServletRequest request,HttpServletResponse response,@RequestParam String jsonStr){
		Map<String,Object> map = Maps.newHashMap();
		JasperReport jasperReport;
		ReportDataSource reportDataSource = new ReportDataSource();
		try {
			Map<String,Object> objectMap = JasperReportUtil.getJsonValue(jsonStr);
			String tempId = (String)objectMap.get("id");
				String downLoadType = (String)objectMap.get("downLoadType");
			JasperReportTemp jasperReportTemp = this.jasperReportTempService.getByPK(tempId);
			String tempPath = jasperReportTemp.getReportTempPath();
			if(StringUtils.isNotBlank(tempPath))
			{
				jasperReport = JasperCompileManager.compileReport(tempPath);
				prepareReportForXml(jasperReport,downLoadType);
				List<Object> objectList = JasperReportUtil.jsonToArray(jsonStr);
				reportDataSource.setAttriButeNameList((List<String>)objectList.get(0));
				reportDataSource.setDataSet((List<List<Map>>) objectList.get(1));
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<String, Object>(), reportDataSource);
				pareperDownload(downLoadType, jasperPrint, jasperReportTemp.getReportTempName(), request, response);
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("message", "导出文件发生异常!");
			map.put("success", false);
			logger.error("导出文件发生异常!",e);
		}
		return map;
	}

	/**
	 * 导出下载报表
	 * @param request
	 * @param response
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value="/effect/downLoad2",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> reportDownLoad2(HttpServletRequest request,HttpServletResponse response,@RequestParam String jsonStr){
		Map<String,Object> map = Maps.newHashMap();
		JasperReport report;
		try {
			Map<String,Object> objectMap = JasperReportUtil.getJsonValue(jsonStr);
			String identCode = (String)objectMap.get("identCode");
			String downLoadType = (String)objectMap.get("downLoadType");
			JasperReportTemp jasperReportTemp = this.jasperReportTempService.getByIdentCode(identCode);
			JasperReportTemp subJasperReportTemp = null;
			if(objectMap.containsKey("subIdentCode")){
				String subIdentCode = (String)objectMap.get("subIdentCode");
				subJasperReportTemp = this.jasperReportTempService.getByIdentCode(subIdentCode);
			}

			String tempPath = jasperReportTemp.getReportTempPath();
			if(StringUtils.isNotBlank(tempPath)) {
				report = (JasperReport) JRLoader.loadObject(new File(tempPath));
				Map<String,Object>  parameters = JasperReportUtil.getParameters(objectMap.get("data"));
				if(objectMap.containsKey("subIdentCode")){
					parameters.put("SUBREPORT_DIR", subJasperReportTemp.getReportTempPath());
				}
				JRBeanCollectionDataSource ds = JasperReportUtil.getMainDs(objectMap.get("data"));
				JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, ds);
				pareperDownload(downLoadType, jasperPrint, jasperReportTemp.getReportTempName(), request, response);
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("message", "导出文件发生异常!");
			map.put("success", false);
			logger.error("导出文件发生异常!",e);
		}
		return map;
	}
	public void pareperDownload(String downLoadType, JasperPrint jasperPrint, String defaultFileName,
					HttpServletRequest request,HttpServletResponse response) throws Exception{
		if(Constants.PDF_TYPE.equals(downLoadType)) {
			exportPdf(jasperPrint,defaultFileName,request,response);
		} else if(Constants.WORD_TYPE.equals(downLoadType)) {
			exportDoc(jasperPrint,defaultFileName,request,response);
		} else if(Constants.HTML_TYPE.equals(downLoadType)) {
			exportHtml(jasperPrint,defaultFileName,request,response);
		} else if(Constants.EXCEL_TYPE.equals(downLoadType)) {
			exportExcl(jasperPrint,defaultFileName,request,response);
		}
	}

	/**
	 * 打印报表
	 * @param request
	 * @param response
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value="/effect/print",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> reportPrint(HttpServletRequest request,HttpServletResponse response,@RequestParam String jsonStr){
		Map<String,Object> map = Maps.newHashMap();
		JasperReport jasperReport;
		ReportDataSource reportDataSource = new ReportDataSource();
		try {
			Map<String,Object> objectMap = JasperReportUtil.getJsonValue(jsonStr);
			String tempId = (String)objectMap.get("id");
			JasperReportTemp jasperReportTemp = this.jasperReportTempService.getByPK(tempId);
			String tempPath = jasperReportTemp.getReportTempPath();
			if(StringUtils.isNotBlank(tempPath))
			{
				jasperReport = JasperCompileManager.compileReport(tempPath);
				List<Object> objectList = JasperReportUtil.jsonToArray(jsonStr);
				reportDataSource.setAttriButeNameList((List<String>)objectList.get(0));
				reportDataSource.setDataSet((List<List<Map>>) objectList.get(1));
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<String, Object>(), reportDataSource);
				printReport(jasperPrint);
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("message", "打印报表发生异常!");
			map.put("success", false);
			logger.error("打印报表发生异常!",e);
		}
		return map;
	}

	/**
	 * 打印文件
	 * @param jasperPrint
	 * @throws JRException
	 */
	private void printReport(JasperPrint jasperPrint)throws JRException
	{
		if(null!=jasperPrint)
		{
			final JasperPrint print = jasperPrint;
			Thread thread = new Thread(
					new Runnable()
					{
						@Override
						public void run()
						{
							try
							{
								JasperPrintManager.printReport(print,true);
							}catch (Exception e)
							{
								logger.error("执行打印报表线程出现异常!",e);
							}

						}
					}
			);
			thread.start();
		}

	}

	/**
	 * 导出为PDF格式的文件
	 * @param jasperPrint
	 * @param defaultFileName
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws JRException
	 */
	private void exportPdf(JasperPrint jasperPrint,String defaultFileName,HttpServletRequest request,HttpServletResponse response)throws IOException,JRException
	{
		response.setContentType("application/pdf");
		String defaultname=null;
		if(defaultFileName.trim()!=null&&defaultFileName!=null){
			defaultname=defaultFileName+".pdf";
		}else{
			defaultname="export.pdf";
		}
		String filename = new String(defaultname.getBytes("GBK"),"ISO8859_1");
		response.setHeader("Content-disposition","attachment; filename="+filename);
		ServletOutputStream outputStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
		outputStream.flush();
		outputStream.close();
	}

	/**
	 * 导出为DOC格式的文件
	 * @param jasperPrint
	 * @param defaultFileName
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws JRException
	 */
	private void exportDoc(JasperPrint jasperPrint,String defaultFileName,HttpServletRequest request,HttpServletResponse response)throws IOException,JRException
	{
		response.setContentType("application/msword;charset=UTF-8");
		String fileName=null;
		if(defaultFileName.trim()!=null&&defaultFileName!=null){
			//fileName = URLEncoder.encode(defaultFileName + ".doc","UTF-8");
			byte[] yte = (defaultFileName + ".doc").getBytes( "UTF-8" );
			fileName = new String(yte, "ISO-8859-1" );
		}else{
			fileName = "export.doc";
		}
		//String fileName = new String(defaultname.getBytes("GBK"),"UTF-8");
		response.setHeader("Content-Disposition","attachment; filename="+ fileName);
		JRExporter jrExporter = new JRRtfExporter();
		jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		jrExporter.exportReport();
	}

	/**
	 * 导出为HTML格式的文件
	 * @param jasperPrint
	 * @param defaultFileName
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws JRException
	 */
	private void exportHtml(JasperPrint jasperPrint,String defaultFileName,HttpServletRequest request,HttpServletResponse response)throws IOException,JRException
	{
		response.setContentType("text/html");
		ServletOutputStream outputStream = response.getOutputStream();
		JRHtmlExporter jrHtmlExporter = new JRHtmlExporter();
		jrHtmlExporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE);
		jrHtmlExporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
		jrHtmlExporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,"utf-8");
		jrHtmlExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
		jrHtmlExporter.exportReport();

		outputStream.flush();
		outputStream.close();
	}

	/**
	 * 导出为EXCEL格式的文件
	 * @param jasperPrint
	 * @param defaultFileName
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws JRException
	 */
	private void exportExcl(JasperPrint jasperPrint,String defaultFileName,HttpServletRequest request,HttpServletResponse response)throws IOException,JRException
	{

		response.setContentType("application/vnd.ms-excel");
		String fileName = null;
		if(defaultFileName.trim()!=null&&defaultFileName!=null){
			//fileName = URLEncoder.encode(defaultFileName + ".xls","UTF-8");
			byte[] yte = (defaultFileName + ".xls").getBytes( "UTF-8" );
			fileName = new String(yte, "ISO-8859-1" );
		}else{
			fileName = "export.xls";
		}
		//String fileName = new String(defaultname.getBytes("gbk"), "utf-8");
		response.setHeader("Content-Disposition","attachment; filename=" + fileName);
		ServletOutputStream outputStream = response.getOutputStream();
		JRXlsExporter jrXlsExporter = new JRXlsExporter();
		jrXlsExporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
		jrXlsExporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outputStream);
		jrXlsExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
		jrXlsExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
		jrXlsExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
		jrXlsExporter.exportReport();

		outputStream.flush();
		outputStream.close();

	}

	/**
	 * 如果导出格式为EXCEL 需要去掉周围的margin
	 * @param jasperReport
	 * @param downLoadType
	 */
	private void prepareReportForXml(JasperReport jasperReport,String downLoadType)
	{
		if("EXCEL".equals(downLoadType))
		{
			try {
				Field margin = JRBaseReport.class.getDeclaredField("leftMargin");
				margin.setAccessible(true);
				margin.setInt(jasperReport, 0);
				margin = JRBaseReport.class.getDeclaredField("topMargin");
				margin.setAccessible(true);
				margin.setInt(jasperReport, 0);
				margin = JRBaseReport.class.getDeclaredField("bottomMargin");
				margin.setAccessible(true);
				margin.setInt(jasperReport, 0);

				Field pageHeight = JRBaseReport.class.getDeclaredField("pageHeight");
				pageHeight.setAccessible(true);
				pageHeight.setInt(jasperReport,2147483647);
			} catch (NoSuchFieldException e) {
				//e.printStackTrace();
				logger.error(e,e);
			}catch (IllegalAccessException e) {
				//e.printStackTrace();
				logger.error(e,e);
			}
		}
	}

}
