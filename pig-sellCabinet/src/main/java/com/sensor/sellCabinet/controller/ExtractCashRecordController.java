package com.sensor.sellCabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.sensor.sellCabinet.model.dto.ExtractAsk;
import com.sensor.sellCabinet.model.dto.ExtractCashRecordDto;
import com.sensor.sellCabinet.model.entity.ExtractCashRecord;
import com.sensor.sellCabinet.service.ExtractCashRecordService;
import com.sensor.sellCabinet.service.UserInfoService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


/**
 * 提现记录表
 *
 * @author chengpan
 * @date 2019-06-27 21:01:37
 */
@RestController
@AllArgsConstructor
@RequestMapping("/extractcashrecord")
public class ExtractCashRecordController {

  private final  ExtractCashRecordService extractCashRecordService;

	private UserInfoService userInfoService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param extractCashRecord 提现记录表
   * @return
   */
  @GetMapping("/page")
  public R<IPage<ExtractCashRecordDto>> getExtractCashRecordPage(Page<ExtractCashRecordDto> page, ExtractCashRecordDto extractCashRecord) {
	  SysUser user = userInfoService.getSysUserInfo();
	  extractCashRecord.setOpenid(user.getWxOpenid());
    return  new R<>(extractCashRecordService.getExtractCashRecordPage(page,extractCashRecord,user));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<ExtractCashRecord> getById(@PathVariable("id") String id){
    return new R<>(extractCashRecordService.getById(id));
  }

	/**
	 * 提现
	 * @param extractAsk
	 * @return
	 */
	@GetMapping("/transfers")
	public R transfers(ExtractAsk extractAsk) {
		SysUser user = userInfoService.getSysUserInfo();
		BigDecimal balance = user.getBalance();
		String extractMoney = extractAsk.getExtractMoney();
		BigDecimal exMoney = new BigDecimal(extractMoney);
		if (balance==new BigDecimal("0")){
			return new R(Boolean.FALSE,"余额不能为零");
		}
		if (balance.compareTo(new BigDecimal("0"))==-1){
			return new R(Boolean.FALSE,"余额不能为负");
		}
		if (exMoney.compareTo(balance)==1){
			return new R(Boolean.FALSE,"提现金额不能大于余额");
		}
		return extractCashRecordService.transfers(user,extractAsk);
	}

	@GetMapping("/getQRCode")
	public R<String> getQRCode() throws Exception {
		SysUser user = userInfoService.getSysUserInfo();
		String QRCode = "https://xj.sensorte.com/xjSell6/app/bind?"+"userId="+user.getUserId()+"&username="+user.getUsername()+"&realName="+user.getRealName()+"&phone="+user.getPhone();
		return new R<>(QRCode);
	}

  /**
   * 新增记录
   * @param extractCashRecord
   * @return R
   */
  @SysLog("新增提现记录表")
  @PostMapping
  public R save(@RequestBody ExtractCashRecord extractCashRecord){
    return new R<>(extractCashRecordService.save(extractCashRecord));
  }

  /**
   * 修改记录
   * @param extractCashRecord
   * @return R
   */
  @SysLog("修改提现记录表")
  @PutMapping
  public R update(@RequestBody ExtractCashRecord extractCashRecord){
    return new R<>(extractCashRecordService.updateById(extractCashRecord));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除提现记录表")
  @DeleteMapping("/{id}")
  public R removeById(@PathVariable String id){
    return new R<>(extractCashRecordService.removeById(id));
  }

}
