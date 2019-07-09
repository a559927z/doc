package net.chinahrd.tx;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test.xml")
@ActiveProfiles("test")
public class TxTest {

	@Resource
	private SqlSessionFactory sqlSessionFactory;

//	@Before
//	public void setUp() throws Exception {
//
//	}

	@Test
	public void test() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		System.out.println(sqlSession);
		int a = 1;
		assertEquals(1, a);
	}
}
