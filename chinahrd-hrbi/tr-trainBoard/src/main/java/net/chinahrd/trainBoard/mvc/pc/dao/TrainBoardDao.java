package net.chinahrd.trainBoard.mvc.pc.dao;

import java.util.List;
import java.util.Map;

import net.chinahrd.entity.dto.pc.trainBoard.TrainBoardDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainLecturerDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainRecordDto;
import net.chinahrd.entity.dto.pc.trainBoard.TrainTypeDto;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

/**
 * 培训看板Dao
 *
 * @author qpzhu by 2016-03-29
 */
@Repository("trainBoardDao")
public interface TrainBoardDao {

    TrainBoardDto queryTrainCostYear(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    TrainBoardDto queryTrainPlan(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    TrainBoardDto queryTrainCover(@Param("customerId") String customerId, @Param("organId") String organId, @Param("empCount") int empCount, @Param("year") String year);

    List<TrainBoardDto> queryTrainMonthCostList(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    List<TrainBoardDto> queryTrainMonthAvgCostList(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    List<TrainBoardDto> queryTrainMonthPlanList(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    List<TrainBoardDto> queryPerCapitaHours(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    int findTrainingRecordCount(@Param("organId") String organId, @Param("keyName") String keyName, @Param("customerId") String customerId, @Param("organPermitIds") List<String> organPermitIds);

    List<TrainRecordDto> findTrainingRecord(@Param("organPermitIds") List<String> organPermitIds, @Param("organId") String organId, @Param("keyName") String keyName, @Param("sidx") String sidx, @Param("sord") String sord, @Param("customerId") String customerId,
                                            RowBounds rowBounds);

    int queryInternalTrainerCount(@Param("customerId") String customerId, @Param("organId") String organId);
    
    List<TrainLecturerDto> queryInternalTrainerList(@Param("customerId") String customerId, @Param("organId") String organId, RowBounds rowBounds);

    List<TrainBoardDto> queryInternalTrainer(@Param("customerId") String customerId, @Param("organId") String organId);

    List<TrainBoardDto> queryTrainingSatisfaction(@Param("customerId") String customerId, @Param("organId") String organId);

    List<TrainBoardDto> queryTrainingType(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    List<TrainBoardDto> queryYearCost(@Param("customerId") String customerId, @Param("organId") String organId);

    List<TrainBoardDto> querySubOrganizationCost(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    List<TrainBoardDto> querySubOrganizationPassengers(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    int findTrainingTypeRecordCount(Map<String, Object> map);

    List<TrainTypeDto> findTrainingTypeRecord(Map<String, Object> map, RowBounds rowBounds);
    
    List<TrainTypeDto> findTrainingTypeRecordNoPage(Map<String, Object> map);

    int queryEmpCount(@Param("customerId") String customerId, @Param("organId") String organId);

    Double queryTrainTotal(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    Double queryCostTotal(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    int queryTrainNum(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    int queryTrainPassengers(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    Double queryTrainTime(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    List<TrainBoardDto> queryTrainQuarterCostList(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

    List<TrainBoardDto> querySubOrganizationAvgCost(@Param("customerId") String customerId, @Param("organId") String organId, @Param("year") String year);

	List<TrainBoardDto> querySubOrganization(@Param("customerId") String customerId, @Param("organId") String organId);

	TrainBoardDto queryCompanyOrganization(@Param("customerId") String customerId);

}
