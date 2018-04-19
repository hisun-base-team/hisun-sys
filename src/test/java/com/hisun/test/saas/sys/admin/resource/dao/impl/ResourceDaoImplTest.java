package com.hisun.test.saas.sys.admin.resource.dao.impl;

import com.hisun.saas.sys.admin.resource.entity.Resource;
import com.hisun.saas.sys.admin.resource.dao.ResourceDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;

/**
 * <p>Title: ResourceDaoImplTest.java </p>
 * <p>Package com.hisun.cloud.sys.resource.dao.impl </p>
 * <p>Description: TODO</p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: 湖南海数互联信息技术有限公司</p>
 * @author Jason
 * @email jason4j@qq.com
 * @date 2015年9月14日 下午3:00:40 
 * @version 
 */
@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@Transactional
@TransactionConfiguration(transactionManager="transactionManager", defaultRollback=true)
@RunWith(SpringJUnit4ClassRunner.class)
public class ResourceDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests{

	@Inject
	ResourceDao resourceDao;

	Resource resource;
	
	@Before
	public void setUp() throws Exception {
		resource = new Resource();
		resource.setpId("1");
		resource.setPermission("test:test");
		resource.setUrl("/sys/test");
		resource.setResourceName("測試資源");
		resource.setResourceType(Integer.valueOf(1));
		resource.setSort(Integer.valueOf(99));
		resource.setQueryCode("099");
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void testGetByTenantUserId() throws Exception {
		//存在的租户用户
		List<Resource> resources = this.resourceDao.getByTenantUserId("40283f84511f5da101511f5eef2e0000");
		Assert.assertNotNull(resources);
		Assert.assertThat(resources.size(), allOf(greaterThan(0), lessThan(999)));

		//边界测试
		//不存在的租户用户
		resources = this.resourceDao.getByTenantUserId("123");

		Assert.assertTrue(resources.isEmpty());

	}


}
