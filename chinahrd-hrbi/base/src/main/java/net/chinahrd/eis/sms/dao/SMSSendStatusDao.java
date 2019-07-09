package net.chinahrd.eis.sms.dao;

import java.util.List;

import net.chinahrd.eis.sms.SMSSendStatus;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("smsSendStatusDao")
public interface SMSSendStatusDao {

	void addSMSSendStatus(@Param("dtos")List<SMSSendStatus> dtos);
	List<SMSSendStatus> querySMSSendStatus(@Param("mobile")String mobile);
}
