package com.sensor.sellCabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.sensor.sellCabinet.model.dto.RunCabinetProductInfo;
import com.sensor.sellCabinet.model.dto.RunCabinetProductV;
import com.sensor.sellCabinet.model.dto.TeamDto;
import com.sensor.sellCabinet.model.entity.ProductInfo;
import com.sensor.sellCabinet.model.entity.RunCabinetProduct;
import com.sensor.sellCabinet.model.entity.Team;
import com.sensor.sellCabinet.service.*;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 运行货柜商品信息
 *
 * @author chengpan
 * @date 2019-04-15 17:36:58
 */
@RestController
@AllArgsConstructor
@RequestMapping("/runcabinetproduct")
public class RunCabinetProductController {

  private final RunCabinetProductService runCabinetProductService;

  private final CabinetInfoService cabinetInfoService;

	private UserInfoService userInfoService;

	private final ProductInfoService productInfoService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param runCabinetProduct 运行货柜商品信息
   * @return
   */
  @GetMapping("/page")
  public R<IPage<RunCabinetProductInfo>> getRunCabinetProductPage(Page<RunCabinetProduct> page, RunCabinetProduct runCabinetProduct) {
	  SysUser user = userInfoService.getSysUserInfo();
    return  new R<>(runCabinetProductService.getRunCabinetProductPage(page,runCabinetProduct,user));
  }
  /**
   * 简单分页查询
   * @param cabinetNo 运行货柜商品信息
   * @return
   */
  @GetMapping("/getRunCabinetProductList")
  public R<List<RunCabinetProductInfo>> getRunCabinetProductDtoList(@Param("cabinetNo") String cabinetNo) {
	  SysUser user = userInfoService.getSysUserInfo();
    return  new R<>(runCabinetProductService.getRunCabinetProductDtoList(cabinetNo,user));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<RunCabinetProduct> getById(@PathVariable("id") String id){
    return new R<>(runCabinetProductService.getById(id));
  }

  /**
   * 新增记录
   * @param runCabinetProduct
   * @return R
   */
  @SysLog("新增运行货柜商品信息")
  @PostMapping
  public R save(@RequestBody RunCabinetProduct runCabinetProduct){
    return new R<>(runCabinetProductService.save(runCabinetProduct));
  }
	/**
	 * 批量新增记录
	 * @param runCabinetProductV
	 * @return R
	 */
	@SysLog("新增运行货柜商品信息")
	@PostMapping("/addRunCabinetProductBatch")
	public R save(@RequestBody RunCabinetProductV runCabinetProductV){
		List<RunCabinetProduct> runCabinetProductDtoList = runCabinetProductV.getRunCabinetProductDtoList();
		if (null!=runCabinetProductDtoList&&runCabinetProductDtoList.size()>0){
			for(RunCabinetProduct runCabinetProduct:runCabinetProductDtoList){
				runCabinetProduct.setEffictSdate(LocalDateTime.now());
				runCabinetProduct.setEffictEdate(LocalDateTime.now());
				runCabinetProduct.setUpdateTime(LocalDateTime.now());
			}
		}
		String cabinetNo = runCabinetProductDtoList.get(0).getCabinetNo();
		cabinetInfoService.updetaIsEmpty(cabinetNo);
		return new R<>(runCabinetProductService.updateBatchById(runCabinetProductV.getRunCabinetProductDtoList()));
	}

	/**
	 * 修改运行货柜商品价格
	 * @param productCode
	 * @param currentPrice
	 * @return R
	 */
	@SysLog("修改运行货柜商品价格")
	@PostMapping("/updateProductPrice")
	public R updateProductPrice(@Param("productCode")String productCode, @Param("currentPrice")String currentPrice,@Param("teamCode")String teamCode){
		int res = runCabinetProductService.updateProductPrice(productCode,currentPrice,teamCode);
		if (res>0){
			return new R<>(Boolean.TRUE);
		}
		return new R<>(Boolean.FALSE,"改价失败");
	}
  /**
   * 修改记录
   * @param runCabinetProduct
   * @return R
   */
  @SysLog("修改运行货柜商品信息")
  @PutMapping
  public R update(@RequestBody RunCabinetProduct runCabinetProduct){
    return new R<>(runCabinetProductService.updateById(runCabinetProduct));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除运行货柜商品信息")
  @DeleteMapping("/{id}")
  public R removeById(@PathVariable String id){
    return new R<>(runCabinetProductService.removeById(id));
  }

}
