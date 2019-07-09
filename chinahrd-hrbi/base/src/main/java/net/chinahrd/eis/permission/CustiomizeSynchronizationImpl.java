///**
//*net.chinahrd.eis.permission
//*/
//package net.chinahrd.eis.permission;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import net.chinahrd.core.customize.CustiomizeSynchronization;
//import net.chinahrd.core.customize.model.CustomizeHtmlSynchronizationModel;
//import net.chinahrd.core.customize.model.CustomizeLayoutSynchronizationModel;
//import net.chinahrd.eis.permission.dao.CustiomizeSynchronizationDao;
//import net.chinahrd.eis.permission.dto.CustomizeHtmlDto;
//import net.chinahrd.eis.permission.dto.CustomizeLayoutDto;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * @author htpeng
// *2016年12月9日上午10:18:03
// */
//@Component("custiomizeSynchronization")
//public class CustiomizeSynchronizationImpl implements CustiomizeSynchronization{
//
//	@Autowired
//	CustiomizeSynchronizationDao custiomizeSynchronizationDao;
//	/* (non-Javadoc)
//	 * @see net.chinahrd.core.customize.CustiomizeSynchronization#synchronization()
//	 */
//	@Override
//	public List<CustomizeHtmlSynchronizationModel> synchronization() {
//		List<CustomizeHtmlDto> allCustiomize = custiomizeSynchronizationDao.getAllCustiomize();
//		List<CustomizeHtmlSynchronizationModel> list=new ArrayList<CustomizeHtmlSynchronizationModel>();
//		for(CustomizeHtmlDto customizeHtmlDto:allCustiomize){
//			CustomizeHtmlSynchronizationModel customizeHtmlSynchronizationModel=new CustomizeHtmlSynchronizationModel();
//			customizeHtmlSynchronizationModel.setClazz(customizeHtmlDto.getClazz());
//			customizeHtmlSynchronizationModel.setMethod(customizeHtmlDto.getMethod());
//			customizeHtmlSynchronizationModel.setUrl(customizeHtmlDto.getUrl());
//			customizeHtmlSynchronizationModel.setRoleId(customizeHtmlDto.getRoleId());
//			for(CustomizeLayoutDto customizeLayoutDto:customizeHtmlDto.getLayoutList()){
//				customizeHtmlSynchronizationModel.setLayout(new CustomizeLayoutSynchronizationModel(customizeLayoutDto.getCode()));
//			}
//			list.add(customizeHtmlSynchronizationModel);
//		}
//		return list;
//		
//	}
//	/* (non-Javadoc)
//	 * @see net.chinahrd.core.customize.CustiomizeSynchronization#update(net.chinahrd.core.customize.model.CustomizeHtmlSynchronizationModel)
//	 */
//	
//	@Transactional(rollbackFor=Exception.class)
//	@Override
//	public void update(CustomizeHtmlSynchronizationModel customizeHtmlSynchronizationModel) {
//		CustomizeHtmlDto customizeHtmlDto=new CustomizeHtmlDto(customizeHtmlSynchronizationModel);
//		custiomizeSynchronizationDao.deleteCustiomize(customizeHtmlDto);
////		custiomizeSynchronizationDao.deleteCustiomizeLayout(customizeHtmlDto.getId());
//		custiomizeSynchronizationDao.insertCustiomize(customizeHtmlDto);
//		custiomizeSynchronizationDao.insertCustiomizeLayout(customizeHtmlDto);
// 	}
//
//
//
//}
