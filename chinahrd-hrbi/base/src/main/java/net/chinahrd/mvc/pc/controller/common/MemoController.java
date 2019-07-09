package net.chinahrd.mvc.pc.controller.common;

import java.util.List;

import net.chinahrd.entity.dto.pc.common.ItemDto;
import net.chinahrd.entity.dto.pc.common.MemoCreateDto;
import net.chinahrd.entity.dto.pc.common.MemoResultDto;
import net.chinahrd.entity.dto.pc.common.ResultDto;
import net.chinahrd.mvc.pc.controller.BaseController;
import net.chinahrd.mvc.pc.service.common.MemoService;
import net.chinahrd.utils.DateUtil;
import net.chinahrd.utils.Identities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 备忘录相关Controller
 * Created by wqcai on 15/6/24.
 */
@Controller
@RequestMapping(value = "/memo")
public class MemoController extends BaseController {

	@Autowired
	private MemoService memoService;

	/**
	 * 查询锦囊相关信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findPackItem", method = RequestMethod.POST)
	public List<ItemDto> findPackItem() {
		List<ItemDto> items = memoService.findMemoTips(getCustomerId());
		return items;
	}

	/**
	 * 查询备忘录
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/findMemo")
	public List<MemoResultDto> findMemo(String quotaId, String organizationId) {
		List<MemoResultDto> resultDtos = memoService.findAllMemo(
				getCustomerId(), quotaId, organizationId, getUserEmpId());
		return resultDtos;
	}

	/**
	 * 添加备忘录
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addMemo")
	public ResultDto<Object> addMemo(String quotaId, String organizationId,
			String content) {
		ResultDto<Object> result = new ResultDto<>();

		MemoCreateDto dto = new MemoCreateDto();
		dto.setMemoId(Identities.uuid2());
		dto.setCustomerId(getCustomerId());
		dto.setQuataId(quotaId);
		dto.setOrganizationId(organizationId);
		dto.setContent(content);
		dto.setCreateEmpId(getUserEmpId());
		dto.setCreateTime(DateUtil.getTimestamp());

		memoService.addMemo(dto);
		result.setType(true);
		List<MemoResultDto> resultDtos = memoService.findAllMemo(
				getCustomerId(), quotaId, organizationId, getUserEmpId());
		result.setT(resultDtos);
		return result;
	}

	/**
	 * 移除备忘录信息
	 *
	 * @param memoId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteMemo", method = RequestMethod.POST)
	public ResultDto<Object> deleteMemo(String quotaId, String organizationId,String memoId) {
		ResultDto<Object> resultDto = new ResultDto<>();
		memoService.deleteMemo(getCustomerId(), memoId);

		resultDto.setType(true);
		List<MemoResultDto> resultDtos = memoService.findAllMemo(
				getCustomerId(), quotaId, organizationId, getUserEmpId());
		resultDto.setT(resultDtos);
		return resultDto;
	}

	/**
	 * 修改备忘已读状态
	 *
	 * @param memoId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateMemoStatus", method = RequestMethod.POST)
	public ResultDto<String> updateMemoStatus(String memoId) {
		ResultDto<String> resultDto = new ResultDto<>();
		memoService.updateMemoIsReadStatus(getCustomerId(), memoId,
				getUserEmpId());
		resultDto.setType(true);
		return resultDto;
	}
}
