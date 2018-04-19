package com.hisun.test.saas.sys.admin.resource.service.impl;

import com.hisun.saas.sys.admin.resource.entity.Resource;
import com.hisun.saas.sys.admin.resource.service.ResourceService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

/**
 * <p>Title: TenantServiceImplTest.java </p>
 * <p>Package com.hisun.saas.sys.resource.service.impl </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年6月29日 下午7:50:29 
 * @version 
 */
@WebAppConfiguration(value = "src/test/resources")
@ContextHierarchy({
		@ContextConfiguration(name = "parent", locations = "classpath:application-context.xml"),
		@ContextConfiguration(name = "child", locations = "classpath:spring-servlet.xml")
})
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@RunWith(SpringJUnit4ClassRunner.class)
public class ResourceServiceImplTest extends AbstractTransactionalJUnit4SpringContextTests{

	/*@Mock
	private ResourceDao resourceDao;
	
	@InjectMocks
	@Inject
	private ResourceService resourceService;*/
	
	private Resource resource;

	@Before
	public void setUp() throws Exception {
		resource = new Resource();
		resource.setPermission("test:test");
		resource.setQueryCode("012");
		resource.setResourceName("测试");
		resource.setResourceType(Integer.valueOf(0));
		resource.setUrl("/test");
		resource.setStatus(Integer.valueOf(0));
		resource.setpId("0");

		//MockitoAnnotations.initMocks(this);
	}
	
	@After
	public void tearDown() throws Exception {
		
	}

	/*@Test
	public void testSaveResource() throws Exception {
		ShiroTestHelper.mockSubject("test1");
		//设置方法的预期返回值
		when(resourceDao.getMaxSort(anyString(), anyInt())).thenReturn(0);

		when(resourceDao.getByPK(anyString())).thenReturn(null);
		//无返回值的方法
		when(resourceDao.save(any(Resource.class))).thenReturn(null);

		resourceService.saveResource(resource, 0);
		//验证方法调用
		*/
	/**
		 * 基本的验证方法
		 * verify方法验证mock对象是否有没有调用方法
		 * 不关心其是否有返回值，如果没有调用测试失败。
	 *//*
		//默认调用一次,times(1)可以省略  
		verify(resourceDao).getMaxSort(anyString(), anyInt());
		verify(resourceDao,times(2)).getByPK(anyString());
		//resourceService.updateSortAndQueryCode(any(Resource.class),eq(1));
	    verify(resourceDao).save(any(Resource.class));
	    
	   *//* when(resourceDao.getMaxSort(anyString())).thenReturn(null);
		resourceService.getMaxSort(anyString());
		verify(resourceDao).getMaxSort(anyString());*//*

		when(resourceDao.list()).thenReturn(Collections.<Resource>emptyList());

		List<Resource> list = resourceService.list();

		assertTrue(list.isEmpty());

		verify(resourceDao).list();
	}*/

	@Inject
	ResourceService resourceService;

	@Test
	public void testSaveResource() throws Exception {
		resource.setSort(1);
		this.resourceService.saveResource(resource, 0);
		Resource resource1 = this.resourceService.getByPK(resource.getId());
		Assert.assertNotNull(resource1);
	}

}
