package com.sensor.sellCabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.core.util.SensorString;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.sensor.sellCabinet.config.SequenceConfiguration;
import com.sensor.sellCabinet.model.dto.RefundDto;
import com.sensor.sellCabinet.model.entity.PayInfo;
import com.sensor.sellCabinet.model.entity.Refund;
import com.sensor.sellCabinet.service.PayDetailService;
import com.sensor.sellCabinet.service.PayInfoService;
import com.sensor.sellCabinet.service.RefundService;
import com.sensor.sellCabinet.util.CodeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * 退款信息表
 *
 * @author chengpan
 * @date 2019-05-15 12:15:32
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/refund")
public class RefundController {

  private final  RefundService refundService;

  private final  PayInfoService payInfoService;

	private final PayDetailService payDetailService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param refund 退款信息表
   * @return
   */
  @GetMapping("/page")
  public R<IPage<RefundDto>> getRefundPage(Page<Refund> page, RefundDto refund) {
    return  new R<>(refundService.getRefundPage(page,refund));
  }
	/**
	 * 待审核退款记录
	 * @return
	 */
	@GetMapping("/getRefundList")
	public R<IPage<RefundDto>> getRefundList(Page<Refund> page) {
		return  new R<>(refundService.getRefundList(page));
	}

	/**
	 * 退款审核
	 * @param refund
	 * @return
	 */
	@PostMapping("/confirmRefund")
	public R confirmRefund(@RequestBody Refund refund) throws Exception{
		Boolean res = false;
		Refund rfund = refundService.getById(refund.getId());
		if (null==rfund){
			throw new Exception("找不到该退款订单");
		}
		if (null!=rfund){
			if(rfund.getRefundStatus().equals("2")){
				throw new Exception("该订单已退款，请勿重复操作");
			}
		}
		PayInfo payInfo = payInfoService.getPayInfoByOrderCode(rfund.getOrderCode());
		if(payInfo==null||!SensorString.hashEmpty(rfund.getOrderCode())){
			return new R(res,"退款失败");
		}
		//如果是支付宝订单，走支付宝退款
		if(payInfo.getBillType().equals("1")){
			res = payInfoService.refundByAli(refund);
			if (res==false){
				return new R(res,"支付宝退款失败");
			}
		}else {
			res = payInfoService.refund(refund);
			if (res==false){
				return new R(res,"退款失败");
			}
		}
		return new R(res);
	}


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<Refund> getById(@PathVariable("id") String id){
    return new R<>(refundService.getById(id));
  }

  /**
   * 新增记录
   * @param refund
   * @return R
   */
  @SysLog("新增退款信息表")
  @PostMapping
  public R save(@RequestBody Refund refund){
    return new R<>(refundService.save(refund));
  }

  /**
   * 修改记录
   * @param refund
   * @return R
   */
  @SysLog("修改退款信息表")
  @PutMapping
  public R update(@RequestBody Refund refund){
	  payDetailService.updatePayDetailStatus(refund.getOrderCode(),refund.getChannelNo(),"4");
    return new R<>(refundService.updateById(refund));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除退款信息表")
  @DeleteMapping("/{id}")
  public R removeById(@PathVariable String id){
    return new R<>(refundService.removeById(id));
  }

}
