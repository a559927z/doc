package net.chinahrd.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import net.chinahrd.utils.PropertiesUtil;
import net.chinahrd.utils.version.core.entity.ColumnModel;
import net.chinahrd.utils.version.core.entity.DBConfig;
import net.chinahrd.utils.version.core.entity.EntityModel;
import net.chinahrd.utils.version.sql.AlloteSQL;
import net.chinahrd.utils.version.util.SysUrlUtil;
import net.chinahrd.utils.version.util.Util;


/**
 * 创建标准读ETL
 * 
 * @author jxzhang on 2017年4月27日
 * @Verdion 1.0 版本
 */
public class ConstuctorETL {

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
//			StringBuffer importBuff = new StringBuffer("package net.chinahrd.etl.impl.allDim;\n\n");
			StringBuffer bodyBuff = new StringBuffer();
			StringBuffer attriBuff = new StringBuffer();
			StringBuffer methodBuff = new StringBuffer();

			EntityModel entityModel = map.get(str);
			String ClassNameEntity = entityModel.getEntityClassName() + "Entity";
			String ClassName = entityModel.getEntityClassName() + "ETL";
			importBuff.append("import net.chinahrd.etl.SimpleAbstractEtl;\n");
			importBuff.append("import net.chinahrd.etl.entity." + ClassNameEntity + ";\n");
			importBuff.append("import net.chinahrd.etl.sql.SqlAuxiliary;\n");

//			bodyBuff.append("public class " + ClassName + " extends SimpleAbstractEtl<" + ClassNameEntity + "> implements Runnable {\n");
			bodyBuff.append("public class " + ClassName + " extends SimpleAbstractEtl<" + ClassNameEntity + "> {\n");

//			attriBuff.append("\tpublic static String URL = \"jdbc:mysql://172.16.9.50:3369/mup-large\";\n");
//			attriBuff.append("\tpublic static String USER = \"mup\";\n");
//			attriBuff.append("\tpublic static String PASSWORD = \"1q2w3e123\";\n");

			methodBuff.append("\t@Override\n");
			methodBuff.append("\tpublic boolean isActive() {\n");
			methodBuff.append("\t\treturn true;\n");
			methodBuff.append("\t}\n\n");

			methodBuff.append("\t@Override\n");
			methodBuff.append("\tprotected String getUrl() {\n");
			methodBuff.append("\t\treturn URL;\n");
			methodBuff.append("\t}\n\n");

			methodBuff.append("\t@Override\n");
			methodBuff.append("\tprotected String getUser() {\n");
			methodBuff.append("\t\treturn USER;\n");
			methodBuff.append("\t}\n\n");

			methodBuff.append("\t@Override\n");
			methodBuff.append("\tprotected String getPassword() {\n");
			methodBuff.append("\t\treturn PASSWORD;\n");
			methodBuff.append("\t}\n\n");

			methodBuff.append("\t@Override\n");
			methodBuff.append("\tprotected String getDriver() {\n");
			methodBuff.append("\t\treturn MY_SQL_DRIVER;\n");
			methodBuff.append("\t}\n\n");

			methodBuff.append("\t@Override\n");
			methodBuff.append("\tprotected SqlAuxiliary getSqlAuxiliary() {\n");
			methodBuff.append("\t\treturn " + ClassNameEntity + ".getEntityAuxiliary()\n");

			for (ColumnModel columnModel : entityModel.getColList()) {
				String asMethodName = Util.getMethodNameByName("as", columnModel.getColname());
				methodBuff.append("\t\t\t." + asMethodName + "(\"t.`" + columnModel.getColname() + "`\")\n");
			}
			
			methodBuff.append("\t\t\t.setSqlBody(\" "+entityModel.getTabName().replaceFirst("source_", "")+" t where 1=1\");\n");
			methodBuff.append("\t}\n\n");

			// run 方法
//			methodBuff.append("\t@Override\n");
//			methodBuff.append("\tpublic void run() {\n");
//			methodBuff.append("\t\tthis.execute();\n");
//			methodBuff.append("\t}\n");
			
			// 属性
			bodyBuff.append(attriBuff);
			bodyBuff.append("\n");
			// 方法
			bodyBuff.append(methodBuff);
			bodyBuff.append("}\n");

			// 文件流
			buff.append(importBuff);
			buff.append("\n");
			buff.append(bodyBuff);

			createFileIO(fs, buff, ClassName);

		}
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
