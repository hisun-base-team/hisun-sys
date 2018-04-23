package com.hisun.test.saas.sys.admin.resource.controller;

import com.hisun.saas.sys.entity.AbstractResource;
import com.hisun.saas.sys.admin.resource.controller.AdminResourceController;
import com.hisun.saas.sys.admin.resource.service.ResourceService;
import com.hisun.saas.sys.auth.UserLoginDetails;
import com.hisun.test.ShiroTestHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration(value = "src/test/resources")
@ContextHierarchy({
		@ContextConfiguration(name = "parent", locations = "classpath:application-context.xml"),
		@ContextConfiguration(name = "child", locations = "classpath:spring-servlet.xml")
})  
public class ResourceControllerTest {

	@Resource 
    private WebApplicationContext wac;  
    private MockMvc mockMvc;  
    
    @InjectMocks
	AdminResourceController resourceController;
    
    @Mock
	ResourceService resourceService;
    
    @Mock
	UserLoginDetails userLoginDetails;
    
	@Before
	public void setUp() throws Exception {
		//mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  
		MockitoAnnotations.initMocks(this);
	    this.mockMvc = MockMvcBuilders.standaloneSetup(resourceController).build();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTreeResources() throws Exception {
		
		
        //测试普通控制器
        mockMvc.perform(get("/sys/admin/resource/list/tree")) //执行请求
                //.andExpect(model().attributeExists("user")) //验证存储模型数据
                .andExpect(view().name("/saas/sys/admin/resource/listTreeResource")) //验证viewName
                .andExpect(forwardedUrl("/saas/sys/admin/resource/listTreeResource"))//验证视图渲染时forward到的jsp
                .andExpect(status().isOk())//验证状态码
                .andDo(print()); //输出MvcResult到控制台
	}

	@Test
	public void testQueryCodeLink() throws Exception {
		//Session session = SecurityUtils.getSubject().getSession();
		//session.setAttribute(Constants.CURRENT_USER, userLoginDetails);
		UserLoginDetails userLoginDetails = new UserLoginDetails();
		userLoginDetails.setResources(Collections.<AbstractResource>emptyList());
		ShiroTestHelper.mockSubject("test",userLoginDetails);
		MvcResult result = mockMvc.perform(post("/sys/admin/resource/link").param("menuCode", "001"))//执行请求
				.andDo(print())
                //.andExpect(status().isNotFound())//验证控制器不存在
                .andReturn(); //返回MvcResult
        Assert.assertEquals("{\"success\":true}",result.getResponse().getContentAsString()); //自定义断言
	}

	@Test
	public void testResources() {

	}

	@Test
	public void testTree() {

	}

	@Test
	public void testTreeSelect() {

	}

	@Test
	public void testAdd() {

	}

	@Test
	public void testDelete() {
		
	}

	@Test
	public void testUpdate() {

	}

	@Test
	public void testGet() {
	}

	@Test
	public void testCheck() {
	}

	@Test
	public void testGetMenu() {
	}

	@Test
	public void testGetMaxSort() {
	}

}
