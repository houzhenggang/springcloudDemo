package com.sensor.sellCabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.core.util.SensorString;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.sensor.sellCabinet.config.SequenceConfiguration;
import com.sensor.sellCabinet.config.WechatConfigurer;
import com.sensor.sellCabinet.model.dto.PayInfoDto;
import com.sensor.sellCabinet.model.dto.RunCabinetProductInfo;
import com.sensor.sellCabinet.model.dto.WeChatProductDto;
import com.sensor.sellCabinet.model.entity.*;
import com.sensor.sellCabinet.service.*;
import com.sensor.sellCabinet.util.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * 微信小程序接口
 * 直接越过权限框架，url放行
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/wechat")
public class WeChatController {
	@Autowired
	private WechatConfigurer wechatConfigurer;

	private final WeChatService weChatService;

	private final CosumerService cosumerService;

	private final PayInfoService payInfoService;

	private final PayDetailService payDetailService;

	private final RunCabinetProductService runCabinetProductService;

	private final CabinetInfoService cabinetInfoService;

	private final  CabinetSpecsService cabinetSpecsService;

	private final UserInfoService userInfoService;

	/**
	 * 微信小程序登录login请求
	 * @param code
	 * @return
	 */
	@Inner(value=false)
	@GetMapping("/login")
	public R login(@Param("code")String code){
		JSONObject retJson = new JSONObject();
		if(!SensorString.hashEmpty(code)){
			return new R<>(Boolean.FALSE,"parameters inconsistent with rules");
		}
		String path = wechatConfigurer.getOpenidPath()+"?"
			+ "appid="+ wechatConfigurer.getAppId()
			+ "&secret="+ wechatConfigurer.getAppSecret()
			+ "&js_code="+ code
			+ "&grant_type=authorization_code";

		JSONObject jsonObject = CommonUtil.httpsRequest(path, "GET", null);
		String openId = jsonObject.containsKey("openid")?jsonObject.getString("openid"):"";
		if (SensorString.hashEmpty(openId)){
			Cosumer cosumer = cosumerService.getCosumerByOpenId(openId);
			if(null!=cosumer){
				retJson.put("userId", cosumer.getUserId());
				retJson.put("openId", cosumer.getOpenId());
			}else{
				String name = SequenceConfiguration.COSUMER;
				String userIdCode  = CodeUtil.getPrimaryKey(name);
				Cosumer coser = new Cosumer();
				coser.setUserId(userIdCode);
				coser.setOpenId(openId);
				coser.setCreateDate(LocalDateTime.now());
				cosumerService.save(coser);
				log.info("===userId===:", coser.getUserId());
				log.info("===openId===:", coser.getOpenId());
				retJson.put("userId", coser.getUserId());
				retJson.put("openId", coser.getOpenId());
			}
		}
		return new R(retJson);
	}
	/**
	 * 微信小程序获取售货柜信息
	 * @param
	 * @param
	 * @return
	 */
	@GetMapping("/getRunCabinetProductList")
	public R<WeChatProductDto> getProductInfo(@Param("cabinetNo")String cabinetNo) {
		CabinetInfo cabinetInfo = cabinetInfoService.getCabinet(cabinetNo);
		if (null==cabinetInfo){
			return new R(Boolean.FALSE,"获取售货柜信息失败");
		}else{
			WeChatProductDto weChatProductDto = new WeChatProductDto();
			String specsNo = cabinetInfo.getSpecsNo();
			List<CabinetSpecs> cabinetSpecsList = cabinetSpecsService.getCabinetSpecsByNo(specsNo);
			List<RunCabinetProductInfo> runCabinetProductDtoList = runCabinetProductService.getWeRunCabinetProductDtoList(cabinetNo);
			weChatProductDto.setCabinetSpecsList(cabinetSpecsList);
			weChatProductDto.setRunCabinetProductDtoList(runCabinetProductDtoList);
			weChatProductDto.setTeamCode(cabinetInfo.getTeamCode());
			return new R<>(weChatProductDto);
		}
	}
	/**
	 * 微信小程序新增记录
	 * @param payInfoDto
	 * @return R
	 */
	@Inner(value=false)
	@SysLog("新增实收汇总表")
	@PostMapping("/addPayInfo")
	public R save(@RequestBody PayInfoDto payInfoDto){
		return new R<>(payInfoService.savePayInfoDto(payInfoDto));
	}
	/**
	 * 修改记录
	 * @param payInfo
	 * @return R
	 */
	@Inner(value=false)
	@SysLog("修改实收汇总表")
	@PutMapping
	public R update(@RequestBody PayInfo payInfo){
		return new R<>(payInfoService.updateById(payInfo));
	}
	/**
	 * 微信小程序查询订单
	 * @param
	 * @param
	 * @return
	 */
	@Inner(value=false)
	@GetMapping("/getPayInfo")
	public R<List<PayInfo>> getPayInfo(@Param("userId")String userId,PayInfo payInfo) {
		if (!SensorString.hashEmpty(userId)){
            return new R(Boolean.FALSE,"用户id为空或不存在");
		}else{
			payInfo.setUserId(userId);
			return new R<>(payInfoService.getWechatPayInfoList(payInfo));
		}
	}
	/**微信小程序查询订单明细
	 * 简单分页查询
	 * @param page 分页对象
	 * @param payDetail 实收明细表
	 * @return
	 */
	@Inner(value=false)
	@GetMapping("/getPayDetailPage")
	public R<IPage<PayDetail>> getPayDetailPage(Page<PayDetail> page, PayDetail payDetail) {
		return new R<>(payDetailService.getWechatPayDetailPage(page,payDetail));
	}

	/**
	 * 付款接口
	 * @param payInfoDto
	 * @param userId
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/pay")
	public R<JsonMap> pay(@RequestBody PayInfoDto payInfoDto,@Param("userId")String userId,@Param("openId")String openId) throws Exception{
		List<PayDetail> payDetailList = payInfoDto.getPayDetailList();
		for (PayDetail payDetail:payDetailList) {
			String cabinetNo = payDetail.getCabinetNo();
			Integer channelNo = payDetail.getChannelNo();
			RunCabinetProduct runCabinetProduct = runCabinetProductService.getRunCabinetProduct(cabinetNo,channelNo);
			Integer currentNum = runCabinetProduct.getCurrentNum();
			if (currentNum<1){
				return new R(new JsonMap("fail", "", channelNo+"货道商品已售罄"));
			}
		}
		return new R(payInfoService.pay(payInfoDto,userId,openId));
	}

	/**
	 * 付款回执接口
	 * @param param
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/receiveSgin", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String receiveSgin(@RequestBody String param) {
		String resultStr = "";
		try {
			log.info("###WeChatController.receiveSgin\n"+param);
			resultStr = payInfoService.receiveSgin(param);
		} catch (Exception e) {
			log.info(e.getMessage());
			return e.getMessage();
		}
		return resultStr;
	}

	/**
	 * 微信退款申请
	 * @param refund
	 * @return
	 */
	@PostMapping("/addRefund")
	public R addRefund(@RequestBody Refund refund) throws Exception{
		return new R(payInfoService.addRefund(refund));
	}

	/**
	 * 微信开柜加密
	 * @param paramList
	 * @return
	 */
	@PostMapping("/encrypt")
	public R<List<String>> encrypt(@RequestBody List<Integer>paramList){
		return new R(weChatService.encrypt(paramList));
		}

	/**
	 * 开柜结果回传
	 * @param orderCode
	 * @param channelNo
	 * @param openStatus
	 * @return
	 */
	@GetMapping("/openCabinet")
	public R<Boolean> openCabinet(@Param("cabinetNo")String cabinetNo,@Param("orderCode")String orderCode,@Param("channelNo")Integer channelNo,@Param("openStatus")String openStatus){
		return new R<>(payInfoService.openCabinet(cabinetNo,orderCode,channelNo,openStatus));
	}

	/**
	 * 按键补货
	 * @param cabinetNo
	 * @return
	 */
	@GetMapping("/updateCabinetInfo")
	public R<Boolean> updateCabinetInfo(String cabinetNo){
		CabinetInfo cabinetInfo = cabinetInfoService.getCabinet(cabinetNo);
		if (null==cabinetInfo){
			return new R(Boolean.FALSE,"获取售货柜信息失败");
		}else{
			String specsNo = cabinetInfo.getSpecsNo();
			int currentNum = 0;
			if ("cabinet004".equals(specsNo)){
				currentNum = 6;
			}
			if ("cabinet006".equals(specsNo)){
				currentNum = 8;
			}
			LocalDateTime nowTime = LocalDateTime.now();
			return new R<>(runCabinetProductService.updateRunCabinet(cabinetNo,currentNum,nowTime,nowTime,nowTime));
		}
	}

	/**
	 * 绑定微信账户
	 * @param userId
	 * @param openId
	 * @return
	 */
	@GetMapping("/updateuserInfo")
	public R<Boolean> updateuserInfo(Integer userId,String openId,String realName){
		if (null!=openId&&!"".equals(openId)){
			if (userId!=null&&userId!=0){
				Integer res = userInfoService.updateOpenId(userId,openId,realName);
				if (res>0){
					return new R<>(Boolean.TRUE);
				}
			}
		}
		return new R<>(Boolean.FALSE,"Fail");
	}

	/**
	 * 更新柜子电量
	 * @param cabinetNo
	 * @param electric
	 * @return
	 */
	@GetMapping("/updateBattery")
	public R<Boolean> updateBattery(String cabinetNo,Integer electric,String specs){
		return new R<>(cabinetInfoService.updateBattery(cabinetNo,electric,specs));
	}
}
