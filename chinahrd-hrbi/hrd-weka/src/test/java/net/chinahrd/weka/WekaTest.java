package net.chinahrd.weka;

import java.util.Random;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import net.chinahrd.weka.config.JdbcConfig;
import net.chinahrd.weka.dao.SalesDataDao;
import net.chinahrd.weka.mybatis.HrdInstanceQuery;
import net.chinahrd.weka.mybatis.SqlHelper;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.Utils;
import weka.experiment.InstanceQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-test.xml")
@ActiveProfiles("test")
public class WekaTest {

	@Resource
	private JdbcConfig jdbcConfig;
	@Resource
	private SqlSessionFactory sqlSessionFactory;

	@Test
	public void testWeka() throws Exception {
		InstanceQuery query = null;
		Instances data = null;

		SqlSession sqlSession = sqlSessionFactory.openSession();
		SalesDataDao salesDataDao = sqlSession.getMapper(SalesDataDao.class);
		String sql = SqlHelper.getMapperSql(salesDataDao, "queryEmpSalesData", "becb6306729540bc8c291ef59a7b076d");

		query = new HrdInstanceQuery(sqlSession.getConnection());
		// sqlSession.getConfiguration().getEnvironment().getDataSource().getConnection()
		// query.setDatabaseURL(jdbcConfig.getUrl());
		// query.setUsername(jdbcConfig.getUsername());
		// query.setPassword(jdbcConfig.getPassword());
		query.setQuery(sql);
		data = query.retrieveInstances();
		System.out.println("数据集内容：");
		System.out.println(data);

		// 使用最后一个属性作为类别属性
		if (data.classIndex() == -1)
			data.setClassIndex(data.numAttributes() - 1);

		 String classname = "weka.classifiers.functions.MultilayerPerceptron";
		 String[] options = Utils.splitOptions("-L 0.3 -M 0.3 -N 500 -V 0 -S 0 -E 20 -H 1");

		// String classname = "weka.classifiers.functions.LinearRegression";
		// String[] options = Utils.splitOptions("-S 0 -R 1.0E-8
		// -num-decimal-places 4");

		//String classname = "weka.classifiers.rules.M5Rules";
		//String[] options = Utils.splitOptions("-M 4.0");

		Classifier cls = (Classifier) Utils.forName(Classifier.class, classname, options);
		System.out.println("分类器：" + Utils.toCommandLine(cls));

		int seed = 1234;
		int folds = 10;

		Random rand = new Random(seed);
		Instances randData = new Instances(data);
		randData.randomize(rand);
		if (randData.classAttribute().isNominal())
			randData.stratify(folds);

		// perform cross-validation
		Evaluation eval = new Evaluation(randData);
		for (int n = 0; n < folds; n++) {
			// 训练集
			Instances train = randData.trainCV(folds, n);
			// 测试集
			Instances test = randData.testCV(folds, n);

			// 构建并评估分类器
			Classifier clsCopy = AbstractClassifier.makeCopy(cls);
			clsCopy.buildClassifier(train);

			eval.evaluateModel(clsCopy, test);
			System.out.println("\n第" + (n + 1) + "折验证结果");
			for (int i = 0; i < test.numInstances(); i++) {
				// 得到预测值
				double pred = clsCopy.classifyInstance(test.instance(i));
				System.out.println("预测值:" + pred + ",实际值：" + test.instance(i).classValue());
			}

		}

		// 输出评估
		System.out.println();
		System.out.println("=== 分类器设置  ===");
		System.out.println("分类器：" + Utils.toCommandLine(cls));
		System.out.println("数据集：" + data.relationName());
		System.out.println("折数：" + folds);
		System.out.println("随机种子：" + seed);
		System.out.println();
		System.out.println(eval.toSummaryString("=== " + folds + "折交叉验证 ===", false));

	}

}
