package net.chinahrd.getSQL;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.chinahrd.eis.permission.dao.RbacAuthorizerDao;
import net.chinahrd.utils.SqlHelper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test.xml")
@ActiveProfiles("test")
public class GetSQLTest {

	@Resource
	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void setUp() throws Exception {
	}

//	@Ignore
	@Test
	public void rbacAuthorizerDaoTest() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		RbacAuthorizerDao dao = sqlSession.getMapper(RbacAuthorizerDao.class);
		String sql = SqlHelper.getMapperSql(dao, "findRoleList", "becb6306729540bc8c291ef59a7b076d", null);
		System.out.println(sql);
	}

}
