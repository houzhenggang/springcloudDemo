package com.sensor.sellCabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.sensor.sellCabinet.model.dto.PayInfoDto;
import com.sensor.sellCabinet.model.dto.PayInfoV;
import com.sensor.sellCabinet.model.dto.SaleCount;
import com.sensor.sellCabinet.model.entity.CountByTeam;
import com.sensor.sellCabinet.model.entity.PayInfo;
import com.sensor.sellCabinet.service.EurekaClientFeign;
import com.sensor.sellCabinet.service.PayInfoService;
import com.sensor.sellCabinet.service.UserInfoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * 实收汇总表
 *
 * @author chengpan
 * @date 2019-04-23 16:20:54
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/payinfo")
public class PayInfoController {

  private final  PayInfoService payInfoService;

  private final EurekaClientFeign eurekaClientFeign;

  private final UserInfoService userInfoService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param payInfo 实收汇总表
   * @return
   */
  @GetMapping("/page")
  public R<IPage<PayInfoV>> getPayInfoPage(Page<PayInfo> page, PayInfoV payInfo) {
  	//feign调用方法，此处不用，因为速度太慢
//	  R r = eurekaClientFeign.info();
    return  new R<>(payInfoService.getPayInfoPage(page,payInfo));
  }

	/**
	 * //首页统计信息
	 * @param payInfoV
	 * @return
	 */
  @GetMapping(value = "/getSaleCount")
  public R<SaleCount> getSaleCount(PayInfoV payInfoV){
	  SysUser user = userInfoService.getSysUserInfo();
	  SaleCount saleCount = payInfoService.getSaleCount(payInfoV,user);
  	return new R<>(saleCount);
  }

	/**
	 * 场地统计
	 * @param countByTeam
	 * @return
	 */
	@GetMapping(value = "/getCountByTeam")
	public R<IPage<CountByTeam>> getCountByTeam(Page page, CountByTeam countByTeam){
		SysUser user = userInfoService.getSysUserInfo();
		return new R<>(payInfoService.getCountByTeam(page, countByTeam,user));
	}

	/**
	 * 柜子统计
	 * @param countByTeam
	 * @return
	 */
	@GetMapping(value = "/getCountByCabinet")
	public R<IPage<CountByTeam>> getCountByCabinet(Page page, CountByTeam countByTeam){
		SysUser user = userInfoService.getSysUserInfo();
		return new R<>(payInfoService.getCountByCabinet(page, countByTeam,user));
	}

	/**
	 * 人员统计
	 * @param countByTeam
	 * @return
	 */
	@GetMapping(value = "/getCountByProxy")
	public R<IPage<CountByTeam>> getCountByProxy(Page page, CountByTeam countByTeam){
		SysUser user = userInfoService.getSysUserInfo();
		return new R<>(payInfoService.getCountByProxy(page, countByTeam,user));
	}

  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<PayInfo> getById(@PathVariable("id") Integer id){
    return new R<>(payInfoService.getById(id));
  }

  /**
   * 新增记录
   * @param payInfoDto
   * @return R
   */
  @SysLog("新增实收汇总表")
  @PostMapping
  public R save(@RequestBody PayInfoDto payInfoDto){
    return new R<>(payInfoService.savePayInfoDto(payInfoDto));
  }

  /**
   * 修改记录
   * @param payInfo
   * @return R
   */
  @SysLog("修改实收汇总表")
  @PutMapping
  public R update(@RequestBody PayInfo payInfo){
    return new R<>(payInfoService.updateById(payInfo));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除实收汇总表")
  @DeleteMapping("/{id}")
  public R removeById(@PathVariable Integer id){
    return new R<>(payInfoService.removeById(id));
  }

//	@PostMapping("/upload")
//	public String addpayInfoexcel(@RequestParam("file") MultipartFile file,String type) throws Exception {
//		String fileName = file.getOriginalFilename();
//		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
//			throw new Exception("上传文件格式不正确");
//		}
//		List<PayInfo> payInfos = new ArrayList<PayInfo>();
//		try{
//			InputStream is = file.getInputStream();
//			HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(is));
//			//有多少个sheet
//			int sheets = workbook.getNumberOfSheets();
//
//			for (int i = 0; i < sheets; i++) {
//				HSSFSheet sheet = workbook.getSheetAt(i);
//				//获取多少行
//				int rows = sheet.getPhysicalNumberOfRows();
//				PayInfo payInfo = null;
//				//遍历每一行，注意：第 0 行为标题
//				for (int j = 1; j < rows; j++) {
//					payInfo = new PayInfo();
//					Object object = new Object();
//					//获得第 j 行
//					HSSFRow row = sheet.getRow(j);
//					payInfo.setOrderCode(row.getCell(0).toString());
//					payInfo.setCabinetNo(row.getCell(1).toString());
//					payInfos.add(payInfo);
//				}
//			}
//		}catch(Exception e){
//			return "导入数据格式有误，请检查上传文件";
//		}
//		if (null!=payInfos&&payInfos.size()>=0){
//			payInfoService.saveBatch(payInfos);
//		}
//		return "导入数据成功";
//	}
}
