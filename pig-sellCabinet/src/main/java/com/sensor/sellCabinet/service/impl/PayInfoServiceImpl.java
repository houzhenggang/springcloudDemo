package com.sensor.sellCabinet.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.SensorString;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.sensor.sellCabinet.config.AliPayConfigurer;
import com.sensor.sellCabinet.config.SequenceConfiguration;
import com.sensor.sellCabinet.config.WechatConfigurer;
import com.sensor.sellCabinet.mapper.*;
import com.sensor.sellCabinet.model.dto.PayInfoDto;
import com.sensor.sellCabinet.model.dto.PayInfoV;
import com.sensor.sellCabinet.model.dto.PayStatus;
import com.sensor.sellCabinet.model.dto.SaleCount;
import com.sensor.sellCabinet.model.entity.*;
import com.sensor.sellCabinet.service.PayInfoService;
import com.sensor.sellCabinet.service.WebSocketService;
import com.sensor.sellCabinet.util.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 实收汇总表
 *
 * @author chengpan
 * @date 2019-04-23 16:20:54
 */
@Slf4j
@Service("payInfoService")
@AllArgsConstructor
public class PayInfoServiceImpl extends ServiceImpl<PayInfoMapper, PayInfo> implements PayInfoService {

	@Autowired
	private WechatConfigurer wechatConfigurer;
	@Autowired
	private AliPayConfigurer aliPayConfigurer;
	@Autowired
	WebSocketService webSocketService;

	private PayDetailMapper payDetailMapper;

	private RunCabinetProductMapper runCabinetProductMapper;

	private UserMapper userMapper;

	private RefundMapper refundMapper;

	private TeamMapper teamMapper;

	private IncomeMapper incomeMapper;

  /**
   * 实收汇总表简单分页查询
   * @param payInfo 实收汇总表
   * @return
   */
  @Override
  public IPage<PayInfoV> getPayInfoPage(Page<PayInfo> page, PayInfoV payInfo){
		  Integer userId = SecurityUtils.getUser().getId();
		  SysUser user = userMapper.getSysUser(userId);
		  if (payInfo.getEDate()!=null&&!"".equals(payInfo.getEDate())){
		  	  payInfo.setEDate(DateUtil.getTheLastTime(payInfo.getEDate()));
		  }
      	return baseMapper.getPayInfoPage(page,payInfo,user);
  }

	@Override
	public List<PayInfoV> getPayInfoListForExcel(PayInfoV payInfo) {
		Integer userId = SecurityUtils.getUser().getId();
		SysUser user = userMapper.getSysUser(userId);
		return baseMapper.getPayInfoPage(payInfo,user);
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean savePayInfoDtoByAli(PayInfoDto payInfoDto){
		try {
			int reinfo = 0;
			int redetail = 0;
			PayInfo payInfo = payInfoDto.getPayInfo();
//			String name = SequenceConfiguration.PAY_INFO;
//			String code = CodeUtil.getPrimaryKey(name);
			if (null!=payInfo){
				payInfo.setOrderCode(payInfoDto.getPayInfo().getOrderCode());
				payInfo.setCreateDate(LocalDateTime.now());
				payInfo.setPayDate(LocalDateTime.now());
				reinfo = baseMapper.insert(payInfo);
			}
			List<PayDetail> payDetailList = payInfoDto.getPayDetailList();
			if (null!=payDetailList&&payDetailList.size()>0){
				for (PayDetail detail:payDetailList) {
					detail.setOpenStatus("9");
					detail.setOrderCode(payInfoDto.getPayInfo().getOrderCode());
					detail.setCreateDate(LocalDateTime.now());
					int res = payDetailMapper.insert(detail);
					if (res>0){
						redetail++;
					}
				}
			}
			if (reinfo>0&&redetail>0){
				return true;
			}else{
				throw new RuntimeException("生成订单失败!");
			}
		}catch (Exception e){
			throw e;
		}
	}
	@Override
	public PayInfo getPayInfoByOrderCode(String ordercode) {
		PayInfo payInfo = baseMapper.getPayInfo(ordercode);
		return payInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean savePayInfoDto(PayInfoDto payInfoDto){
		try {
			int reinfo = 0;
			int redetail = 0;
			PayInfo payInfo = payInfoDto.getPayInfo();
			String name = SequenceConfiguration.PAY_INFO;
			String code = CodeUtil.getPrimaryKey(name);
			if (null!=payInfo){
				payInfo.setOrderCode(code);
				payInfo.setCreateDate(LocalDateTime.now());
				payInfo.setPayDate(LocalDateTime.now());
				reinfo = baseMapper.insert(payInfo);
			}
			List<PayDetail> payDetailList = payInfoDto.getPayDetailList();
			if (null!=payDetailList&&payDetailList.size()>0){
				for (PayDetail detail:payDetailList) {
					detail.setOpenStatus("9");
					detail.setOrderCode(code);
					detail.setCreateDate(LocalDateTime.now());
					int res = payDetailMapper.insert(detail);
					if (res>0){
						redetail++;
					}
				}
			}
			if (reinfo>0&&redetail>0){
				return true;
			}else{
				throw new RuntimeException("生成订单失败!");
			}
		}catch (Exception e){
			throw e;
		}
	}

	@Override
	public List<PayInfo> getWechatPayInfoList(PayInfo payInfo) {
		return baseMapper.getWechatPayInfoList(payInfo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JsonMap pay(PayInfoDto payInfoDto,String userId,String openId) throws Exception{
		try {
			log.info("===付款===\n"+"userId:"+userId+"\n"+"openId:"+openId);
			String name = SequenceConfiguration.PAY_INFO;
			String code = CodeUtil.getPrimaryKey(name);
			payInfoDto.getPayInfo().setOrderCode(code);
			if (null!=payInfoDto){
				StringBuffer sb = new StringBuffer();
				Map<String, String> payMap = new HashMap<>();
				String orderCode = payInfoDto.getPayInfo().getOrderCode();
				payMap.put("openid", openId);
				//回传参数，自定义，穿什么回什么
				payMap.put("attach", "DMG");
				payMap.put("appid", wechatConfigurer.getAppId());
				payMap.put("trade_type", "JSAPI");
				payMap.put("nonce_str", SensorString.randString(32));
				payMap.put("mch_id", wechatConfigurer.getMchId());
				payMap.put("notify_url", wechatConfigurer.getNotifyUrl());
				payMap.put("body", payInfoDto.getPayInfo().getBody());
				payMap.put("detail", payInfoDto.getPayInfo().getBody());
				payMap.put("out_trade_no", orderCode);
				//微信是用分为单位,这里乘以100
				BigDecimal orderPrice = payInfoDto.getPayInfo().getOrderPrice();
				BigDecimal bdmultiply = new BigDecimal("100");
				payMap.put("total_fee", Integer.toString(orderPrice.multiply(bdmultiply).intValue()));
				String key = wechatConfigurer.getKey();

				Collection<String> keyset= payMap.keySet();
				List list=new ArrayList<String>(keyset);
				Collections.sort(list);
				//这种打印出的字符串顺序和微信官网提供的字典序顺序是一致的
				for(int i=0;i<list.size();i++){
					if("key".equals(list.get(i))){
						continue;
					}
					sb.append(list.get(i)+"="+payMap.get(list.get(i)));
					sb.append("&");
				}
				String rtnXml = null;
				//获取签名
				String sign = WeChatUtils.getpaySign(sb,key);
				payMap.put("sign", sign);
				String payXml = WeChatUtils.mapToXml(payMap);
				rtnXml = HttpClientUtil.httpsRequest(wechatConfigurer.getUnifiedOrderUrl(), "POST", payXml);
				Sax sax = new Sax();
				Map<String, String> mapres = sax.parserXml(rtnXml);
				mapres.put("packageValue", "Sign=WXPay");
				mapres.put("timeStamp", WeChatUtils.genTimeStamp()+"");
				JSONObject rtnJson = JSONObject.fromObject(mapres);
				rtnJson.put("orderCode",orderCode);
				JsonMap jsonMap;
				if(rtnJson!=null){
					if("FAIL".equals(rtnJson.getString("return_code"))){
						return new JsonMap(rtnJson.getString("return_msg"), "0106","" );
					}
					//2.付款成功，生成订单
					if(rtnJson.containsKey("prepay_id")){
						payInfoDto.getPayInfo().setWechatNumber(rtnJson.getString("prepay_id"));
						payInfoDto.getPayInfo().setBillType("0");
						savePayInfoDto(payInfoDto);
					}
					jsonMap= new JsonMap("success", "0000", rtnJson.toString());
				}else{
					jsonMap = new JsonMap("network error", "0107", "");
				}
				return jsonMap;
			}else{
				throw new Exception("订单信息有误");
			}
		}catch (Exception e){
			throw e;
		}
	}

	@Override
	public JsonMap payByAliPay(PayInfoDto payInfoDto, String userId, String alipayuserid) throws Exception {
		try {
			log.info("===付款===\n"+"userId:"+userId+"\n"+"alipayuserid:"+alipayuserid);
			String name = SequenceConfiguration.PAY_INFO;
			String code = CodeUtil.getPrimaryKey(name);
			payInfoDto.getPayInfo().setOrderCode(code);
			if (null!=payInfoDto){
				StringBuffer sb = new StringBuffer();
				String out_trade_no = payInfoDto.getPayInfo().getOrderCode();
				String total_amount = payInfoDto.getPayInfo().getOrderPrice().toString();
				String subject = payInfoDto.getPayInfo().getBody();
				String buyer_id = alipayuserid;
				// 实例化客户端
				AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfigurer.getURL(),
					aliPayConfigurer.getAPP_ID(), aliPayConfigurer.getAPP_PRIVATE_KEY(),
					aliPayConfigurer.getFORMAT(), aliPayConfigurer.getCHARSET(),
					aliPayConfigurer.getALIPAY_PUBLIC_KEY(), aliPayConfigurer.getSIGNTYPE());
				// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.create.
				AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
				// SDK已经封装掉了公共参数，这里只需要传入业务参数。
				request.setBizContent("{" + "\"out_trade_no\":\"" + out_trade_no + "\"," + "\"total_amount\":\"" + total_amount
					+ "\"," + "\"subject\":\"" + subject + "\"," + "\"buyer_id\":\"" + buyer_id + "\"" + "}");
				request.setNotifyUrl(aliPayConfigurer.getNotifyUrl());
				String trade_no = "";
				try {
					// 使用的是execute
					AlipayTradeCreateResponse response = alipayClient.execute(request);
					trade_no = response.getTradeNo();// 获取返回的tradeNO。
				} catch (AlipayApiException e) {
					e.printStackTrace();
				}
				if (!SensorString.hashEmpty(trade_no)) {
					log.error("trade_no is empty");
					return new JsonMap("trade_no is null", "0106", "");
				} else {
					JSONObject retJson = new JSONObject();
					retJson.put("trade_no", trade_no);
					retJson.put("orderCode", code);
					retJson.put("code", code);
					payInfoDto.getPayInfo().setAlipayTradeno(trade_no);
					payInfoDto.getPayInfo().setBillType("1");
					savePayInfoDtoByAli(payInfoDto);
					return new JsonMap("success", "0000", retJson.toString());
				}
			}else{
				throw new Exception("订单信息有误");
			}
		}catch (Exception e){
			throw e;
		}
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public String receiveSgin(String param) throws Exception{
		try {
			Sax sax = new Sax();
			Map<String, String> resultMap = new HashMap<String, String>();
			Map<String, String> mapres = sax.parserXml(param);
			String orderCode = mapres.get("out_trade_no");
			PayInfo payInfo = baseMapper.getPayInfo(orderCode);
			String teamCode = payInfo.getTeamCode();
			if(payInfo==null||!SensorString.hashEmpty(payInfo.getOrderCode())){
				resultMap.put("return_code", "<![CDATA[FAIL]]>");
				resultMap.put("return_msg", "<![CDATA[参数格式校验错误]]>");
				return WeChatUtils.mapToXml(resultMap);
			}else if("1".equals(payInfo.getOrderStatus())){
				resultMap.put("return_code", "<![CDATA[SUCCESS]]>");
				resultMap.put("return_msg", "<![CDATA[OK]]>");
				return WeChatUtils.mapToXml(resultMap);
			}
			if("SUCCESS".equals(mapres.get("result_code"))){
				if("DMG".equals(mapres.get("attach"))){
					//微信解签
					StringBuffer sb = new StringBuffer();
					String key = wechatConfigurer.getKey();
					String signWechat = mapres.get("sign");
					mapres.remove("sign");
					Collection<String> keyset= mapres.keySet();
					List list=new ArrayList<String>(keyset);
					Collections.sort(list);
					for(int i=0;i<list.size();i++){
						if("key".equals(list.get(i))){
							continue;
						}
						sb.append(list.get(i)+"="+mapres.get(list.get(i)));
						sb.append("&");
					}
					//获取签名
					String sign = WeChatUtils.getpaySign(sb,key);
					if(sign.equals(signWechat)){
						//根据订单号 修改金额和支付状态
//					String totalFee = mapres.get("total_fee");
						//订单金额(分)
						BigDecimal totalFee = new BigDecimal(mapres.get("total_fee"));
						BigDecimal bdmultiply = new BigDecimal("100");
						//订单金额(元)
						BigDecimal reBigDecimal = totalFee.divide(bdmultiply,2,BigDecimal.ROUND_DOWN);
						//更新订单状态
						int sizePay = baseMapper.updatePayStatus(orderCode,reBigDecimal.toString());
						//更新订单详情状态
						int sizePde = payDetailMapper.updatePayDetailStatus(orderCode,"", PayStatus.PAYED.getTypeName());
						//添加分润记录
						log.info("添加分润记录"+"\n"+"===orderCode===:"+orderCode+"\n"+"===reBigDecimal===:"+reBigDecimal);
						addIncome(orderCode,teamCode,reBigDecimal);

						if(sizePay==1&&sizePde>=1){
							resultMap.put("return_code", "<![CDATA[SUCCESS]]>");
							resultMap.put("return_msg", "<![CDATA[OK]]>");
						}else{
							resultMap.put("return_code", "<![CDATA[FAIL]]>");
							resultMap.put("return_msg", "<![CDATA[参数格式校验错误]]>");
						}
						return WeChatUtils.mapToXml(resultMap);
					}
				}
			}
			return null;
		}catch (Exception e){
			throw e;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String receiveSginByAli(Map<String, String> params) throws Exception{
		try {
			String result = "";
			String orderCode = params.get("out_trade_no");
			//拿到商户seller_id
			String seller_id = params.get("seller_id");
			//拿到商户app_id
			String app_id = params.get("app_id");
			//拿到订单支付状态
			String trade_status = params.get("trade_status");
			//判断商户sellerid是否一致
			if (!seller_id.equals(aliPayConfigurer.getSELLER_ID())) {
				result = "seller_id is Mismatched";
				log.debug(result);
				return "fail";
			}
			//判断商户appid是否一致
			if (!app_id.equals(aliPayConfigurer.getAPP_ID())) {
				result = "app_id is Mismatched";
				log.debug(result);
				return "fail";
			}
			PayInfo payInfo = baseMapper.getPayInfo(orderCode);
			if(payInfo==null||!SensorString.hashEmpty(payInfo.getOrderCode())){
				return "fail";
			}else if("1".equals(payInfo.getOrderStatus())){
				return "success";
			}
			String teamCode = payInfo.getTeamCode();
			//进行支付宝签名解签
			boolean flag = AlipaySignature.rsaCheckV1(params, aliPayConfigurer.getALIPAY_PUBLIC_KEY(), aliPayConfigurer.getCHARSET(),
				aliPayConfigurer.getSIGNTYPE());
			if (!flag) {
				result = "Failure to verify signature";
				log.debug(result);
				return "fail";
			}
			if (trade_status.equals("TRADE_SUCCESS")) {

				//订单金额(元)
				BigDecimal reBigDecimal = new BigDecimal(params.get("total_amount"));
				//更新订单状态
				int sizePay = baseMapper.updatePayStatus(orderCode,reBigDecimal.toString());
				//更新订单详情状态
				int sizePde = payDetailMapper.updatePayDetailStatus(orderCode,"", PayStatus.PAYED.getTypeName());
				//添加分润记录
				log.info("添加分润记录"+"\n"+"===orderCode===:"+orderCode+"\n"+"===reBigDecimal===:"+reBigDecimal);
				addIncome(orderCode,teamCode,reBigDecimal);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("To", payInfo.getUserId());
				jsonObject.put("message", "yes");
				webSocketService.sendMessage(jsonObject.toString());
				if(sizePay==1&&sizePde>=1){
					return "success";
				}else{
					return "fail";
				}
			}
			return "fail";
		}catch (Exception e){
			throw e;
		}
	}



	@Transactional(rollbackFor = Exception.class)
	public void addIncome(String orderCode,String teamCode,BigDecimal reBigDecimal) {
		try {
			Team team = teamMapper.getTeamByCode(teamCode);
			Income income = new Income();
			income.setOrderCode(orderCode);
			income.setTeamCode(team.getTeamCode());
			income.setTotalAmount(reBigDecimal);
			income.setSensorIncome(reBigDecimal.multiply(team.getSensorRate()));
			income.setStatus("0");
			income.setCreateDate(LocalDateTime.now());
			if (team.getFirstAdmin()!=null&&team.getFirstRate()!=new BigDecimal("0")){
				income.setFirstAdmin(team.getFirstAdmin());
				income.setFirstIncome(reBigDecimal.multiply(team.getFirstRate()));
			}
			if (team.getSecondAdmin()!=null&&team.getSecondRate()!=new BigDecimal("0")){
				income.setSecondAdmin(team.getSecondAdmin());
				income.setSecondIncome(reBigDecimal.multiply(team.getSecondRate()));
			}
			if (team.getThirdAdmin()!=null&&team.getThirdRate()!=new BigDecimal("0")){
				income.setThirdAdmin(team.getThirdAdmin());
				income.setThirdIncome(reBigDecimal.multiply(team.getThirdRate()));
			}
			if (team.getFourthAdmin()!=null&&team.getFourthRate()!=new BigDecimal("0")){
				income.setFourthAdmin(team.getFourthAdmin());
				income.setFourthIncome(reBigDecimal.multiply(team.getFourthRate()));
			}
			if (team.getFifthAdmin()!=null&&team.getFifthRate()!=new BigDecimal("0")){
				income.setFifthAdmin(team.getFifthAdmin());
				income.setFifthIncome(reBigDecimal.multiply(team.getFifthRate()));
			}
			log.info("生成分润记录:"+"\n"+"===income===:"+income);
			incomeMapper.insert(income);
		}catch (Exception e){
			throw e;
			}
	}

	/**
	 * //更新代理商余额
	 * @param income
	 */
	@Transactional(rollbackFor = Exception.class)
	public Boolean updateBalance(Income income) {
		if (income!=null){
			income.setStatus("1");
			try {
				int res = incomeMapper.updateById(income);
				if (res==1){
					Team team = teamMapper.getTeamByCode(income.getTeamCode());
					if (team.getFirstAdmin()!=null&&team.getFirstRate().compareTo(BigDecimal.ZERO)!=0){
						userMapper.updateByUserId(income.getFirstAdmin(),income.getFirstIncome(),"1");
					}
					if (team.getSecondAdmin()!=null&&team.getSecondRate().compareTo(BigDecimal.ZERO)!=0){
						userMapper.updateByUserId(income.getSecondAdmin(),income.getSecondIncome(),"1");
					}
					if (team.getThirdAdmin()!=null&&team.getThirdRate().compareTo(BigDecimal.ZERO)!=0){
						userMapper.updateByUserId(income.getThirdAdmin(),income.getThirdIncome(),"1");
					}
					if (team.getFourthAdmin()!=null&&team.getFourthRate().compareTo(BigDecimal.ZERO)!=0){
						userMapper.updateByUserId(income.getFourthAdmin(),income.getFourthIncome(),"1");
					}
					if (team.getFifthAdmin()!=null&&team.getFifthRate().compareTo(BigDecimal.ZERO)!=0){
						userMapper.updateByUserId(income.getFifthAdmin(),income.getFifthIncome(),"1");
					}
				}
			}catch (Exception e){
				throw e;
			}
		}
		return true;
	}

	@Override
	public IPage<CountByTeam> getCountByTeam(Page page, CountByTeam countByTeam, SysUser user) {
		return baseMapper.getCountByTeam(page, countByTeam,user);
	}

	@Override
	public IPage<CountByTeam> getCountByCabinet(Page page, CountByTeam countByTeam, SysUser user) {
		return baseMapper.getCountByCabinet(page, countByTeam,user);
	}

	@Override
	public IPage<CountByTeam> getCountByProxy(Page page, CountByTeam countByTeam, SysUser user) {
		return baseMapper.getCountByProxy(page, countByTeam,user);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean refund(Refund refund) {
		try{
			log.info("===退款审核===\n"+"id:"+refund.getId()+"\n"+"orderCode:"+refund.getOrderCode()+"\n"+"channelNo:"+refund.getChannelNo()+"\n"+"refundMoney:"+refund.getRefundMoney());
			String orderCode = refund.getOrderCode();
			String refundId = refund.getId().toString();
			String channelNo = refund.getChannelNo();
			PayInfo payInfo = baseMapper.getPayInfo(orderCode);
			if(payInfo==null||!SensorString.hashEmpty(orderCode)){
				return false;
			}
			Sax sax = new Sax();
			Map<String, String> parm = new HashMap<String, String>();
			parm.put("appid", wechatConfigurer.getAppId());
			parm.put("mch_id", wechatConfigurer.getMchId());
			parm.put("nonce_str", SensorString.randString(32));
			parm.put("out_trade_no",orderCode);
			BigDecimal bdmultiply = new BigDecimal("100");
			BigDecimal bdmultiplyto = payInfo.getOrderPrice();
			parm.put("total_fee",Integer.toString(bdmultiplyto.multiply(bdmultiply).intValue()));
			parm.put("refund_fee",Integer.toString(refund.getRefundMoney().multiply(bdmultiply).intValue()));
			parm.put("refund_desc", "设备开启失败，退款");
			parm.put("out_refund_no",refundId);
			String sign = WechatSign.getSing(parm, wechatConfigurer.getKey());
			parm.put("sign", sign);

			String restxml =  HttpUtils.posts("https://api.mch.weixin.qq.com/secapi/pay/refund", WeChatUtils.mapToXml(parm));
			Map<String, String> mapres = sax.parserXml(restxml);
			String returnCode = mapres.get("return_code");
			String returnMsg = mapres.get("return_msg");
			String resultCode = mapres.get("result_code");
			if("SUCCESS".equals(returnCode)&&"OK".equals(returnMsg)&&"SUCCESS".equals(resultCode)){
				//更新退款状态
				int sizeRe = refundMapper.updateRefundStatus(refundId, "2");
				int sizePa = payDetailMapper.updatePayDetailStatus(orderCode,channelNo,PayStatus.BAKPAYED.getTypeName());
				if (sizeRe==1&&sizePa==1){
					deIncome(orderCode,refund.getRefundMoney());
					return true;
				}
			}else{
				return false;
			}
			return true;
		}catch (Exception e){
			throw e;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean refundByAli(Refund refund) throws Exception {
		try{
			log.info("===退款审核===\n"+"id:"+refund.getId()+"\n"+"orderCode:"+refund.getOrderCode()+"\n"+"channelNo:"+refund.getChannelNo()+"\n"+"refundMoney:"+refund.getRefundMoney());
			String orderCode = refund.getOrderCode();
			String refundId = refund.getId().toString();
			String channelNo = refund.getChannelNo();
			PayInfo payInfo = baseMapper.getPayInfo(orderCode);
			if(payInfo==null||!SensorString.hashEmpty(orderCode)){
				return false;
			}
			String total_amount = refund.getRefundMoney().toString();
			String trade_no = payInfo.getAlipayTradeno();
			AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfigurer.getURL(),
				aliPayConfigurer.getAPP_ID(), aliPayConfigurer.getAPP_PRIVATE_KEY(),
				aliPayConfigurer.getFORMAT(), aliPayConfigurer.getCHARSET(),
				aliPayConfigurer.getALIPAY_PUBLIC_KEY(), aliPayConfigurer.getSIGNTYPE());
			AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
			request.setBizContent("{" + "\"out_trade_no\":\"" + orderCode + "\"," + "\"trade_no\":\"" + trade_no
				+ "\"," + "\"refund_amount\":\"" + total_amount + "\"," + "\"refund_reason\":\"正常退款\"" + "  }");
			AlipayTradeRefundResponse response = alipayClient.execute(request);
			if (response.isSuccess()) {
				log.debug("调用成功");
				//更新退款状态
				int sizeRe = refundMapper.updateRefundStatus(refundId, "2");
				int sizePa = payDetailMapper.updatePayDetailStatus(orderCode,channelNo,PayStatus.BAKPAYED.getTypeName());
				if (sizeRe==1&&sizePa==1){
					deIncome(orderCode,refund.getRefundMoney());
					return true;
				}
			} else {
				log.debug("调用失败");
				return false;
			}
			return false;
		}catch (Exception e){
			throw e;
		}

	}



	@Transactional(rollbackFor = Exception.class)
	public void deIncome(String orderCode,BigDecimal refundMoney){
		try{
			Income income = incomeMapper.getIncomeByOrderCode(orderCode);
			if (null!=income){
				Team team = teamMapper.getTeamByCode(income.getTeamCode());
				if (null!=team){
					BigDecimal totalAmount = income.getTotalAmount().subtract(refundMoney);
					income.setTotalAmount(totalAmount);
					income.setSensorIncome(totalAmount.multiply(team.getSensorRate()));
					if (team.getFirstAdmin()!=null&&team.getFirstRate()!=new BigDecimal("0")){
						income.setFirstIncome(totalAmount.multiply(team.getFirstRate()));
					}
					if (team.getSecondAdmin()!=null&&team.getSecondRate()!=new BigDecimal("0")){
						income.setSecondIncome(totalAmount.multiply(team.getSecondRate()));
					}
					if (team.getThirdAdmin()!=null&&team.getThirdRate()!=new BigDecimal("0")){
						income.setThirdIncome(totalAmount.multiply(team.getThirdRate()));
					}
					if (team.getFourthAdmin()!=null&&team.getFourthRate()!=new BigDecimal("0")){
						income.setFourthIncome(totalAmount.multiply(team.getFourthRate()));
					}
					if (team.getFifthAdmin()!=null&&team.getFifthRate()!=new BigDecimal("0")){
						income.setFifthIncome(totalAmount.multiply(team.getFifthRate()));
					}
					log.info("修改分润记录:"+"\n"+"===income===:"+income);
					incomeMapper.updateById(income);
				}
			}
		}catch (Exception e){
			throw e;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean openCabinet(String cabinetNo,String orderCode,Integer channelNo,String openStatus) {
		try{
			int rePd = payDetailMapper.openCabinet(orderCode,channelNo,openStatus);
			if (openStatus.equals("1")){
				runCabinetProductMapper.updateChannelStatus(cabinetNo,channelNo);
			}
			if (rePd==1){
				return true;
			}
			return false;
		}catch (Exception e){
			throw e;
		}
	}

	@Override
	public SaleCount getSaleCount(PayInfoV payInfoV, SysUser user) {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		SaleCount saleCount = new SaleCount();

		log.info("user==="+user.getUsername()+"=="+user.getUserId().toString());
		BigDecimal totolSale = baseMapper.getSaleCount(payInfoV,user);
		int totolBill = baseMapper.getBillCount(payInfoV,user);
		saleCount.setTotolSale(totolSale==null?new BigDecimal("0"):totolSale);
		saleCount.setTotolBill(totolBill);

		payInfoV.setSDate(df.format(DateUtil.getMonthBeginTime()));
		payInfoV.setEDate(df.format(DateUtil.getMonthEndTime()));
		BigDecimal monthSale = baseMapper.getSaleCount(payInfoV,user);
		int monthBill = baseMapper.getBillCount(payInfoV,user);
		saleCount.setMonthSale(monthSale==null?new BigDecimal("0"):monthSale);
		saleCount.setMonthBill(monthBill);

		payInfoV.setSDate(df.format(DateUtil.getYesBeginTime()));
		payInfoV.setEDate(df.format(DateUtil.getYesEndTime()));
		BigDecimal yesterdaySale = baseMapper.getSaleCount(payInfoV,user);
		int yesterdayBill = baseMapper.getBillCount(payInfoV,user);
		saleCount.setYesterdaySale(yesterdaySale==null?new BigDecimal("0"):yesterdaySale);
		saleCount.setYesterdayBill(yesterdayBill);

		payInfoV.setSDate(df.format(DateUtil.getToBeginTime()));
		payInfoV.setEDate(df.format(DateUtil.getToEndTime()));
		BigDecimal todaySale = baseMapper.getSaleCount(payInfoV,user);
		int todayBill = baseMapper.getBillCount(payInfoV,user);
		saleCount.setTodaySale(todaySale==null?new BigDecimal("0"):todaySale);
		saleCount.setTodayBill(todayBill);
		saleCount.setUserId(user.getUserId());
//
//		BigDecimal extracted = baseMapper.getExtracted(user);
//		BigDecimal noExtracted = baseMapper.getNoExtracted(user);
//		saleCount.setExtracted(extracted);
//		saleCount.setNoExtracted(noExtracted);

		return saleCount;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean addRefund(Refund refund) throws Exception{
		try{
			refund.setCreateDate(LocalDateTime.now());
			List<Refund> refundList = refundMapper.getRefund(refund);
			if (refundList.size()>0){
				throw new Exception("请勿重复提交");
			}else {
				int resRe = refundMapper.insert(refund);
				int resPa = payDetailMapper.updatePayDetailStatus(refund.getOrderCode(),refund.getChannelNo(),PayStatus.BAKPAYING.getTypeName());
				if (resRe==1&&resPa==1){
					return true;
				}
			}
			return true;
		}catch (Exception e){
			throw e;
		}
	}
}
