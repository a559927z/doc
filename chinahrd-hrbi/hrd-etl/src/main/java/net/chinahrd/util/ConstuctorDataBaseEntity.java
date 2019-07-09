package net.chinahrd.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import net.chinahrd.utils.version.configure.ColumnType;
import net.chinahrd.utils.version.core.entity.ColumnModel;
import net.chinahrd.utils.version.core.entity.DBConfig;
import net.chinahrd.utils.version.core.entity.EntityModel;
import net.chinahrd.utils.version.sql.AlloteSQL;
import net.chinahrd.utils.version.util.SysUrlUtil;
import net.chinahrd.utils.version.util.Util;

/**
 * 创建数据库实体类
 * 
 * @author htpeng 2017年4月12日下午3:22:49
 */
public class ConstuctorDataBaseEntity {

	// 写数据连接
	public static final String URL="jdbc:mysql://172.16.9.50:3369/mup-source?characterEncoding=utf-8";
	public static final String USER="mup";
	public static final String PASSWORD="1q2w3e123";
	public static final String DRIVER="com.mysql.jdbc.Driver";
	
	
	public static void init(String packge) {
		init(SysUrlUtil.getProjectDevelopRoot(), packge);
	}

	public static void init(String filePath, String packge) {
		DBConfig dbConfig = new DBConfig(USER, PASSWORD, URL, DRIVER);
		createEntity(filePath, packge, AlloteSQL.allotSQL(dbConfig).init().getEntityModel());
	}

	public static void main(String[] ar) {
		init("net.chinahrd.etl.entity");
	}

	/**
	 * 创建实体流
	 * 
	 * @param filename
	 *            绝对路径
	 * @param packge
	 *            文件流-包路径
	 * @param map
	 *            db数据Map
	 */
	private static void createEntity(String filename, String packge, Map<String, EntityModel> map) {
		File fs = new File(SysUrlUtil.packToPath(filename, packge));
		if (fs.exists() && fs.isDirectory()) {
			for (File file : fs.listFiles()) {
				file.delete();
			}
			// return;
		}
		fs.mkdirs();
		for (String str : map.keySet()) {
			StringBuffer buff = new StringBuffer();

			StringBuffer importBuff = new StringBuffer("package " + packge + ";\n\n");
			StringBuffer bodyBuff = new StringBuffer();
			StringBuffer attriBuff = new StringBuffer();
			StringBuffer methodBuff = new StringBuffer();
			EntityModel entityModel = map.get(str);
			// if(entityModel.getTabName().indexOf("soure")!=0){
			// continue;
			// }
			String ClassName = entityModel.getEntityClassName() + "Entity";
			String primayKey = entityModel.getPrimaryKey();
			importBuff.append("import net.chinahrd.utils.version.configure.ColumnType;\n");
			importBuff.append("import net.chinahrd.annotation.Table;\n");
			importBuff.append("import net.chinahrd.annotation.Column;\n");
			importBuff.append("import net.chinahrd.etl.Entity;\n");
			importBuff.append("import net.chinahrd.etl.sql.AbstractSqlAuxiliary;\n");
			importBuff.append("import net.chinahrd.etl.sql.CustomColumn;\n");

			bodyBuff.append("@Table(name = \"" + entityModel.getTabName() + "\")\n");
			bodyBuff.append("public class " + ClassName + " implements Entity{\n\n");

			StringBuffer constuctorMethodParm = new StringBuffer();
			StringBuffer constuctorMethodBody = new StringBuffer();

			for (ColumnModel columnModel : entityModel.getColList()) {
				// String dbtype = pramValueMap.get(columnModel.getDbtype());
				String dbtype = columnModel.getDbtype();
				String inportType = "";
				String showBodyType = "";
				// if (null == dbtype) {
				// dbtype = columnModel.getDbtype();
				// }
				// System.out.println(dbtype);
				ColumnType columnType = ColumnType.getColumnTypeByDataBaseType(dbtype);

				inportType = columnType.getImportName();
				showBodyType = columnType.getType();
				if (inportType.length() > 0) {
					importBuff.append("import " + inportType + ";\n");
				}
				attriBuff.append("\tprivate " + showBodyType + " _" + columnModel.getEntityname() + ";\n");

				// String accessQualifier="public";
				if (primayKey.equals(columnModel.getEntityname())) {
					methodBuff.append("\t@Id\n");
					importBuff.append("import net.chinahrd.utils.version.annotation.Id;\n");
					// accessQualifier="private";
				}
				methodBuff.append("\t@Column(name = \"" + columnModel.getColname() + "\",type=ColumnType." + columnType.getTypeName() + ")\n");
				methodBuff.append("\tpublic " + showBodyType + " " + columnModel.getGetMethodName() + "(){\n");
				methodBuff.append("\t\treturn this._" + columnModel.getEntityname() + "; \n");
				methodBuff.append("\t}\n\n");

				methodBuff.append("\tpublic void " + columnModel.getSetMethodName() + "(" + showBodyType + " _" + columnModel.getEntityname() + "){\n");
				methodBuff.append("\t\tthis._" + columnModel.getEntityname() + " = _" + columnModel.getEntityname() + ";\n");
				methodBuff.append("\t}\n\n");

				constuctorMethodParm.append("," + showBodyType + " _" + columnModel.getEntityname());
				constuctorMethodBody.append("\t\tthis._" + columnModel.getEntityname() + " = _" + columnModel.getEntityname() + ";\n");
			}
			
			StringBuffer toStringMethodBody = new StringBuffer();
			toStringMethodBody.append("\t\treturn \"" +ClassName+" [\"+\n");
			for (ColumnModel columnModel : entityModel.getColList()) {
				toStringMethodBody.append("\t\t\t\"\t"+columnModel.getEntityname()+"=\"+"+ "_"+columnModel.getEntityname()+"+\n");
			}
			toStringMethodBody.append("\t\t\t\"]\";\n");
			
			// 属性
			bodyBuff.append(attriBuff);
			bodyBuff.append("\n");
			bodyBuff.append("\tpublic " + ClassName + "(){\n");
			bodyBuff.append("\t\tsuper();\n");
			bodyBuff.append("\t}\n\n");
			bodyBuff.append("\tpublic " + ClassName + "(" + constuctorMethodParm.toString().replaceFirst(",", "") + "){\n");
			// 构造方法
			bodyBuff.append(constuctorMethodBody);
			bodyBuff.append("\t}\n\n");
			
			//重写toString方法
			bodyBuff.append("\t@Override\n");
			bodyBuff.append("\tpublic String toString() {\n");
			bodyBuff.append(toStringMethodBody);
			bodyBuff.append("\t}\n\n");
			
			// 方法
			bodyBuff.append(methodBuff);
			// 内部类
			bodyBuff.append(ConstuctorDataBaseEntity.createAuxiliaryClass(ClassName, entityModel));
			bodyBuff.append("}\n");
			// 文件流
			buff.append(importBuff);
			buff.append("\n");
			buff.append(bodyBuff);

			createFileIO(fs, buff, ClassName);

////			String compile = "cmd /c javac " + f.getAbsolutePath() + " -d " + projectPackage;
//			String compile = "cmd /c javac " + f.getAbsolutePath() + " -d D: ";
//			System.out.println(compile);
//			try {
//				Runtime.getRuntime().exec(compile);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//
//			int results = compiler.run(null, null, null, f.getAbsolutePath());
//
//			System.out.println((results == 0) ? "编译成功" : "编译失败");
		}
	}


	/**
	 * 创建内部类 - 并实现 AbstractSqlAuxiliary 接口
	 * 
	 * @param className
	 *            类名
	 * @param entityModel
	 *            实体模型
	 * @return
	 */
	public static String createAuxiliaryClass(String className, EntityModel entityModel){
		// 内部类-类名
		String auxiliaryClassName = className + "Auxiliary";
		StringBuffer auxiliaryClassBuff = new StringBuffer();
		StringBuffer auxiliaryClassMethodBuff = new StringBuffer();
		StringBuffer newInsMethodBuff = new StringBuffer();
		
		newInsMethodBuff.append("\t// 实例化内部类\n");
		newInsMethodBuff.append("\tpublic static " + auxiliaryClassName + "  getEntityAuxiliary(){\n");
		newInsMethodBuff.append("\t\treturn new " + auxiliaryClassName + "();\n");
		newInsMethodBuff.append("\t}\n\n");
		
		auxiliaryClassBuff.append(newInsMethodBuff);
		auxiliaryClassBuff.append("\tpublic static class " + auxiliaryClassName + " extends AbstractSqlAuxiliary{\n");
		for (ColumnModel columnModel : entityModel.getColList()) {

			String asMethodName = Util.getMethodNameByName("as", columnModel.getColname());
			auxiliaryClassMethodBuff.append("\t\tpublic " + auxiliaryClassName + " " + asMethodName + "(String colName, CustomColumn<?, ?>... cols){\n");
			auxiliaryClassMethodBuff.append("\t\t\tsetColName(\"" + columnModel.getColname() + "\",\"`" + columnModel.getColname() + "`\", colName, \"" + columnModel.getSetMethodName() + "\", \"" + ColumnType.getColumnTypeByDataBaseType(columnModel.getDbtype()).getResultSetMethodName()+ "\", cols);\n");
			auxiliaryClassMethodBuff.append("\t\t\treturn this;\n");
			auxiliaryClassMethodBuff.append("\t\t}\n");
		}
		auxiliaryClassBuff.append(auxiliaryClassMethodBuff);
		auxiliaryClassBuff.append("\t}\n");
		return auxiliaryClassBuff.toString();
	}
	
	/**
	 * 生成文件
	 * 
	 * @param fs
	 *            已组装好的File对像
	 * @param buff
	 *            流
	 * @param ClassName
	 *            生成的文件名
	 */
	private static void createFileIO(File fs, StringBuffer buff, String ClassName) {
		File f = new File(fs, ClassName + ".java");
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {

		}
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(f));
			osw.flush();
			try {
				osw.write(buff.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (osw != null) {
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
