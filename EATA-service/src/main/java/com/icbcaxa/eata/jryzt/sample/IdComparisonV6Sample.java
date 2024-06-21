package com.icbcaxa.eata.jryzt.sample;

import com.alibaba.fastjson.JSONObject;
import com.icbcaxa.eata.jryzt.ai.client.AiClient;
import com.icbcaxa.eata.jryzt.ai.pojo.dto.request.BaseRequest;
import com.icbcaxa.eata.jryzt.ai.pojo.dto.request.IdcomparisonV6Request;
import com.icbcaxa.eata.jryzt.ai.pojo.dto.result.FaceCompareResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gyas-itwb-fsl01 on 2020/3/17.
 */
public class IdComparisonV6Sample {

    private static final Logger logger = LoggerFactory.getLogger(IdComparisonV6Sample.class);
    /**
     * 对应接口的业务参数,请自行替换
     */
	/*public static String realName = "马庆妮";// "****客户姓名,请自行替换，例如：张三****"
	public static String clientCardNo = "532927199305040923";// "****客户身份证号,请自行替换，例如：320602199507016513****"
	//public static String bioChoose = "3";// "***活体选择，必填，1、无活体、3、xface活体***"
	public static String deviceId = "os111";// "****设备id,请自行替换，例如：os111****"
	public static String sdkVersion = "o123";// "****sdk版本号,请自行替换，例如：o123****"
	public static String deviceModel = "000";// "****设备机型,请自行替换，例如：000****"
	public static String deviceOSAndVersion = "ios10.0";// "****客户端平台类型与版本,请自行替换，例如：ios10.0****"
	//public static String imgType = "1";// "****标示图片类别,请自行替换，1.手机自拍照片 2.身份证照片 3.护照照片 默认为1。****"
	//public static String imgFormat = "jpg";// "****图片格式,自行替换 如:jpg, png****"
	public static String personId = "customerId_PersonId";// "****客户所在应用/APP的唯一编号,自行替换 例如：customerId_PersonId****"
	public static String secondChannel = "secondChannelId";// "****二级渠道号，长度不能超过100,自行替换， 非必填****"
	public static String secondChannelName = "secondChannelName";// "****二级渠道名,，长度不能超过300，自行替换 ，非必填****"
	public static String description = "test";// "****备注，长度不能超过500，自行替换 ，非必填****"
	public static String isSave = "Y";// "****是否保存图片，为Y时保存图片，为N时不保存图片，非必填，不填默认保存****"*/

    public static String imgFormat = "jpg";// "****图片格式,自行替换 如:jpg, png****"
    public static String imgType = "1";// "****标示图片类别,请自行替换，1.手机自拍照片 2.身份证照片 3.护照照片 默认为1。****"
    public static String bioChoose = "1";// "***活体选择，必填，1、无活体、3、xface活体***"

    public static FaceCompareResult IdcomparisonV6(IdcomparisonV6Request idcomparisonV6Request) throws Exception {
        //logger.info("平安一账通idcomparisonV6Request={}"+ JSONObject.toJSONString(idcomparisonV6Request));
        AiClient aiClient = new AiClient();
        IdcomparisonV6Request request = new IdcomparisonV6Request();
        BaseSample baseSample = new BaseSample();

        // 通过BaseSample.getConfig()获取客户配置文件的信息
        aiClient.setConfig(baseSample.getConfig());
        // 组装业务参数
        request.setRealName(idcomparisonV6Request.getRealName());
        request.setClientCardNo(idcomparisonV6Request.getClientCardNo());
        request.setImgFormat(imgFormat);
        request.setImgType(imgType);
        request.setChannelId(aiClient.getConfig().getChannelId());
        request.setSdkVersion("o123");
        request.setDeviceId("os111");
        request.setDeviceModel("000");
        request.setDeviceOSAndVersion("ios10.0");
        request.setPersonId(idcomparisonV6Request.getPersonId());
        request.setSecondChannel("secondChannelId");
        request.setSecondChannelName("secondChannelName");
        request.setDescription("test");
        request.setIsSave("Y");
        request.setBioChoose(bioChoose);
        // 也可以通过getImageBase64(参数为磁盘绝对路径)获取图片base64;
        request.setImgData(idcomparisonV6Request.getImgData());
        // 调用结果
        logger.info("平安一账通请求===姓名={},证件号码={},渠道号={},渠道的唯一编号={},活体选择={}", request.getRealName(),request.getClientCardNo(),request.getSecondChannel(),request.getPersonId(),request.getBioChoose());
        FaceCompareResult result = aiClient.idComparisonV6(request);
        logger.info("平安一账通返回结果result={}"+ JSONObject.toJSONString(result));
        return result;
    }

    public static FaceCompareResult IdVerificationB(IdcomparisonV6Request idcomparisonV6Request) throws Exception {
        AiClient aiClient = new AiClient();
        IdcomparisonV6Request request = new IdcomparisonV6Request();
        BaseSample baseSample = new BaseSample();
        // 通过BaseSample.getConfig()获取客户配置文件的信息
        aiClient.setConfig(baseSample.getConfig());
        // 组装业务参数
        request.setRealName(idcomparisonV6Request.getRealName());
        request.setClientCardNo(idcomparisonV6Request.getClientCardNo());
        request.setChannelId(aiClient.getConfig().getChannelId());
        request.setSdkVersion("o123");
        request.setDeviceId("os111");
        request.setDeviceModel("000");
        request.setDeviceOSAndVersion("ios10.0");
        request.setPersonId(idcomparisonV6Request.getPersonId());
        request.setSecondChannel("secondChannelId");
        request.setSecondChannelName("secondChannelName");
        request.setDescription("test");
        // 调用结果
        logger.info("平安一账通请求===姓名={},证件号码={},渠道号={},渠道的唯一编号={}", request.getRealName(),request.getClientCardNo(),request.getSecondChannel(),request.getPersonId());
        FaceCompareResult result = aiClient.idVerificationB(request);
        logger.info("平安一账通返回结果result={}"+ JSONObject.toJSONString(result));
        return result;
    }
	/*public static void main(String[] args) {
		AiClient aiClient = new AiClient();
		IdcomparisonV6Request request = new IdcomparisonV6Request();
		BaseSample baseSample = new BaseSample();
		System.out.print("baseSample.getConfig()"+ JSONObject.toJSONString(baseSample.getConfig()));
		// 通过BaseSample.getConfig()获取客户配置文件的信息
		aiClient.setConfig(baseSample.getConfig());

		// 组装业务参数
		request.setRealName(realName);
		request.setClientCardNo(clientCardNo);
		request.setImgFormat(imgFormat);
		request.setImgType(imgType);
		request.setChannelId(aiClient.getConfig().getChannelId());
		request.setSdkVersion(sdkVersion);
		request.setDeviceId(deviceId);
		request.setDeviceModel(deviceModel);
		request.setDeviceOSAndVersion(deviceOSAndVersion);
		request.setPersonId(IdComparisonV6Sample.personId);
		request.setSecondChannel(secondChannel);
		request.setSecondChannelName(secondChannelName);
		request.setDescription(description);
		request.setIsSave(isSave);
		request.setBioChoose(bioChoose);
		// 也可以通过getImageBase64(参数为磁盘绝对路径)获取图片base64;
		request.setImgData(aiClient.getImageBase64("C:\\Users\\it-di-dev010\\AppData\\tangZhiHui2.jpg"));

		// 调用结果
		FaceCompareResult result = aiClient.idComparisonV6(request);
		System.out.println("返回参数：similarity:" + result.getSimilarity() + ",ref_thres:" + result.getRef_thres());
	}*/

}
