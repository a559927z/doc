package net.chinahrd.annotation.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.chinahrd.annotation.Column;
import net.chinahrd.annotation.Table;
import net.chinahrd.annotation.configure.AnnotationType;
import net.chinahrd.annotation.configure.EntityAnnotationModel;
import net.chinahrd.etl.Entity;

/**
 * 解析注解类
 * 
 * @author htpeng 2017年4月14日下午4:05:20
 */
public abstract class AnnotationCore {
	private static AnnotationCore annotationCore = null;
	private static Map<Class<?>, EntityAnnotationModel> map = new HashMap<Class<?>, EntityAnnotationModel>();

	/**
	 * 单例
	 * 
	 * @return
	 * @throws Exception
	 */
	public static AnnotationCore getInstance() throws Exception {
		if (null == annotationCore) {
			annotationCore = new Ann();
		}
		return annotationCore;
	}

	/**
	 * 根据Class类型获取对应的 实体类注解模型
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public synchronized EntityAnnotationModel getEntityAnnotationModel(Class<? extends Entity> clazz) throws Exception {
		EntityAnnotationModel entityAnnotationModel = map.get(clazz);
		if (null == entityAnnotationModel) {
			entityAnnotationModel = praseEntityAnnotationModel(clazz);
		}
		return entityAnnotationModel;
	}

	/**
	 * 根据Class类型 解析出对应的 实体类注解模型
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	private EntityAnnotationModel praseEntityAnnotationModel(Class<? extends Entity> clazz) throws Exception {
		EntityAnnotationModel model = new EntityAnnotationModel();
		Annotation[] annotationClass = clazz.getAnnotations();
		if (annotationClass.length > 0) {
			boolean isExist = false;
			for (Annotation annotation : annotationClass) {
				if (AnnotationType.getType(annotation.toString()).equals(AnnotationType.TABLE)) {
					model.setTable((Table) annotation);
					isExist = true;
					break;
				}
			}
			if (!isExist) {
				throw new Exception("实体类没有注解：" + Table.class);
			}
		} else {
			throw new Exception("实体类没有注解");
		}
		// Method Annotation
		try {
			for (Method method : clazz.getDeclaredMethods()) {
				Annotation[] annotationMethod = method.getAnnotations();
				if (annotationMethod.length > 0) {
					for (Annotation annotation : annotationMethod) {
						if (AnnotationType.getType(annotation.toString()).equals(AnnotationType.COLUMN)) {
							model.setColList(method.getName(), (Column) annotation);
							break;
						}
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// 实体类, 实体类模型
		map.put(clazz, model);
		return model;
	}

}

class Ann extends AnnotationCore {

	public Ann() throws Exception {
		super();
	}

}