package net.chinahrd.trainBoard.mvc.pc.service;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.PaginationDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainBoardDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainLecturerDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainRecordDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainTypeDto;

/**
 * 培训看板Service
 * @author qpzhu by 2016-03-29
 */
public interface TrainBoardService {

	TrainBoardDto getTrainCostYear(String customerId, String organId);

	TrainBoardDto getTrainPlan(String customerId, String organId);

	Map<String, Object> getTrainCover(String customerId, String organId);

	Map<String, Object> getTrainingCost(String customerId, String organId);

	List<TrainBoardDto> getPerCapitaCost(String customerId, String organId);

	Map<String, Object> getPlanCompletion(String customerId, String organId);

	Map<String, Object> getPerCapitaHours(String customerId, String organId);

	Map<String, Object> getExpenseStatistics(String customerId, String organId);

	List<TrainBoardDto> getYearCost(String customerId, String organId);

	Map<String, Object> getSubOrganizationCost(String customerId, String organId);

	Map<String, Object> getImplementation(String customerId, String organId);

	Map<String, Object> getSubOrganizationPassengers(String customerId, String organId);

	Map<String, Object> getSubOrganizationCover(String customerId, String organId);

	Map<String, Object> getTrainingType(String customerId, String organId);

	List<TrainBoardDto> getTrainingSatisfaction(String customerId, String organId);

	List<TrainBoardDto> getInternalTrainer(String customerId, String organId);

	PaginationDto<TrainLecturerDto> getInternalTrainerList(String customerId, String organId,PaginationDto<TrainLecturerDto> dto);

	PaginationDto<TrainRecordDto> findTrainingRecord(String keyName, String organId, PaginationDto<TrainRecordDto> dto,
			String sidx, String sord, String customerId);

	PaginationDto<TrainTypeDto> findTrainingTypeRecord(String type, String organId, PaginationDto<TrainTypeDto> dto, String sidx,
			String sord, String customerId);
	
	List<TrainTypeDto> findTrainingTypeRecordNoPage(String type, String organId, String customerId);

	List<TrainBoardDto> getSubOrganizationAvgCost(String customerId, String organId);

	
}
