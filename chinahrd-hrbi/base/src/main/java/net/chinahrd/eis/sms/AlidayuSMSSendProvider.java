package net.chinahrd.eis.sms;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
//import com.taobao.api.ApiException;
//import com.taobao.api.DefaultTaobaoClient;
//import com.taobao.api.TaobaoClient;
//import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
//import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * 淘宝阿里大鱼的短信网关接口
 * @author hhzhou
 *
 */
public class AlidayuSMSSendProvider extends AbstractSMSSendProvider {

	private static Logger log = LoggerFactory.getLogger(AlidayuSMSSendProvider.class);
	
	@Override
	public int getRemain() {
		
		return 0;
	}

	@Override
	public void sendSMS(String[] mobiles, String signName, String smsText) {
		// no support
		log.warn("No support.");
	}
	
	@Override
	public void sendSMS(String[] mobiles, String signName, String smsTemplate,
			Map<String, String> params) {
		
//		try {
//			TaobaoClient client = new DefaultTaobaoClient(sendUrl, uid, authKey);
//			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//			req.setSmsType("normal");
//			req.setSmsFreeSignName(signName);
//			req.setRecNum(StringUtils.join(mobiles, ","));
//			req.setSmsTemplateCode(smsTemplate);
//			req.setSmsParam(JSONObject.toJSONString(params));
//			AlibabaAliqinFcSmsNumSendResponse response = client.execute(req);
//			System.out.println(response.getBody());
//		} catch (ApiException e) {
//			e.printStackTrace();
//		}
		
		log.info("短信已发送至" + Arrays.toString(mobiles));
	}

	@Override
	protected List<SMSSendStatus> getSendStatus() {
		/*try {
			TaobaoClient client = new DefaultTaobaoClient(sendUrl, uid, authKey);
			AlibabaAliqinFcSmsNumQueryRequest req = new AlibabaAliqinFcSmsNumQueryRequest();
			//req.setBizId("100640943324^1101042839891");//短信发送流水，可选
			req.setRecNum("");//短信接收号码，必选
			req.setQueryDate("20160129");//短信发送日期，支持近30天记录查询，格式yyyyMMdd，必须
			req.setCurrentPage(1L);
			req.setPageSize(10L);
			AlibabaAliqinFcSmsNumQueryResponse rsp = client.execute(req);
			System.out.println(rsp.getBody());
		} catch (ApiException e) {
			e.printStackTrace();
		}*/
		return null;
	}
	
}
