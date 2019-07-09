//package net.chinahrd.salaryBoard;
//
//import javax.annotation.Resource;
//
//import net.chinahrd.eis.permission.dao.RbacAuthorizerDao;
//import net.chinahrd.salaryBoard.mvc.pc.service.SalaryBoardService;
//import net.chinahrd.utils.SqlHelper;
//
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:spring-test.xml")
//@ActiveProfiles("test")
//public class SalaryBoardJobTest {
//
//	private final String customerIdVal = "b5c9410dc7e4422c8e0189c7f8056b5f";
//	@Resource
//	private SqlSessionFactory sqlSessionFactory;
//	@Resource
//	private SalaryBoardService salaryBoService;
//
//	@Before
//	public void setUp() throws Exception {
//		System.out.println("********************************setUp*************************");
//	}
//	@Test
//	public void main(){
//		System.out.println("********************************Testing*************************");
//		try{
//		salaryBoService.proFetchPayCollectYear();
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//	}
//	
//	@Ignore
//	@Test
//	public void daoTest() {
//		SqlSession sqlSession = sqlSessionFactory.openSession();
//		RbacAuthorizerDao dao = sqlSession.getMapper(RbacAuthorizerDao.class);
//		String sql = SqlHelper.getMapperSql(dao, "findRoleList", "becb6306729540bc8c291ef59a7b076d", null);
//		System.out.println(sql);
//	}
//}
