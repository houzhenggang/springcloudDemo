package com.sensor.sellCabinet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.core.util.SensorString;
import com.sensor.sellCabinet.config.WechatConfigurer;
import com.sensor.sellCabinet.mapper.ExtractCashRecordMapper;

import com.sensor.sellCabinet.mapper.*;
import com.sensor.sellCabinet.model.dto.ExtractAsk;
import com.sensor.sellCabinet.model.dto.ExtractCashRecordDto;
import com.sensor.sellCabinet.model.entity.ExtractCashRecord;
import com.sensor.sellCabinet.service.ExtractCashRecordService;
import com.sensor.sellCabinet.util.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 提现记录表
 *
 * @author chengpan
 * @date 2019-06-27 21:01:37
 */
@Slf4j
@Service("extractCashRecordService")
@AllArgsConstructor
public class ExtractCashRecordServiceImpl extends ServiceImpl<ExtractCashRecordMapper, ExtractCashRecord> implements ExtractCashRecordService {

	@Autowired
	private WechatConfigurer wechatConfigurer;

	private UserMapper userMapper;

  /**
   * 提现记录表简单分页查询
   * @param extractCashRecord 提现记录表
   * @return
   */
  @Override
  public IPage<ExtractCashRecordDto> getExtractCashRecordPage(Page<ExtractCashRecordDto> page, ExtractCashRecordDto extractCashRecord,SysUser user){
  	if (extractCashRecord.getEDate()!=null&&!"".equals(extractCashRecord.getEDate())){
		extractCashRecord.setEDate(DateUtil.getTheLastTime(extractCashRecord.getEDate()));
	}
      return baseMapper.getExtractCashRecordPage(page,extractCashRecord,user);
  }

	@Override
	@Transactional(rollbackFor = Exception.class)
	public R transfers(SysUser user, ExtractAsk extractAsk) {
		BigDecimal bextractMoney = new BigDecimal(extractAsk.getExtractMoney());
		userMapper.updateByUserId(user.getUserId().toString(),bextractMoney,"0");

		Map<String, String> parm = new HashMap<String, String>();
		parm.put("mch_appid", wechatConfigurer.getAppId());
		parm.put("mchid", wechatConfigurer.getMchId());
		parm.put("nonce_str", SensorString.randString(32));
		String recordId = SensorString.randString(32);
		parm.put("partner_trade_no",recordId);//提现单号
		parm.put("openid",user.getWxOpenid());
		parm.put("check_name","FORCE_CHECK");//写死
		parm.put("re_user_name", user.getRealName()); //真实名字

		BigDecimal bdmultiply = new BigDecimal("100");
		parm.put("amount",Integer.toString(bextractMoney.multiply(bdmultiply).intValue()));//钱数：分
		parm.put("desc","分润提现");
		parm.put("spbill_create_ip","120.78.214.77");//服务器名字

		String sign = WechatSign.getSing(parm, wechatConfigurer.getKey());
		parm.put("sign", sign);
		Sax sax = new Sax();

		String restxml =  HttpUtils.posts("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers", WeChatUtils.mapToXml(parm));
		Map<String, String> mapres = sax.parserXml(restxml);
		String resultCode = mapres.get("result_code");
		String resultMsg = mapres.get("result_msg");
		String errCodeDes = mapres.get("err_code_des");
		String errCode = mapres.get("err_code");
		log.info("===transfersresult_code==="+resultCode+"\n");
		log.info("===transfersresult_msg==="+resultMsg+"\n");
		log.info("===transferserr_code_des==="+errCodeDes+"\n");
		log.info("===transferserr_code==="+errCode+"\n");

		ExtractCashRecord extractCashRecord = new ExtractCashRecord();
		extractCashRecord.setTransNo(recordId);
		extractCashRecord.setOpenid(user.getWxOpenid());
		extractCashRecord.setExtractPer(user.getUserId().toString());
		extractCashRecord.setExtractDate(LocalDateTime.now());
		extractCashRecord.setExtractStatus("0");
		extractCashRecord.setExtractMoney(bextractMoney);
		extractCashRecord.setExtractRemark(extractAsk.getExtractRemark());
		extractCashRecord.setExtractBeforeBalance(user.getBalance());
		extractCashRecord.setExtractWay(extractAsk.getExtractWay());

		baseMapper.insert(extractCashRecord);
		ExtractCashRecord extractCash = baseMapper.getExtractCashRecordByTranNo(recordId);
		if("SUCCESS".equals(resultCode)){
			extractCash.setExtractStatus("1");
			extractCash.setExtractAfterBalance(user.getBalance().subtract(bextractMoney));
			baseMapper.updateById(extractCash);
			return new R(Boolean.TRUE);
		}else{
			userMapper.updateByUserId(user.getUserId().toString(),bextractMoney,"1");
			extractCash.setExtractStatus("2");
			extractCash.setExtractAfterBalance(user.getBalance());
			extractCash.setExtractFailReason(errCodeDes);
			baseMapper.updateById(extractCash);
			return new R(Boolean.FALSE,"Fail");
		}
	}

}
