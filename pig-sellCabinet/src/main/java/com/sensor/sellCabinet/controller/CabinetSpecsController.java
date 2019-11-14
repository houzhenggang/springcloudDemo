package com.sensor.sellCabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.sensor.sellCabinet.model.entity.CabinetSpecs;
import com.sensor.sellCabinet.service.CabinetSpecsService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 售货柜规格表
 *
 * @author chengpan
 * @date 2019-05-07 09:31:50
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cabinetspecs")
public class CabinetSpecsController {

  private final  CabinetSpecsService cabinetSpecsService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param cabinetSpecs 售货柜规格表
   * @return
   */
  @GetMapping("/page")
  public R<IPage<CabinetSpecs>> getCabinetSpecsPage(Page<CabinetSpecs> page, CabinetSpecs cabinetSpecs) {
    return  new R<>(cabinetSpecsService.getCabinetSpecsPage(page,cabinetSpecs));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<CabinetSpecs> getById(@PathVariable("id") Integer id){
    return new R<>(cabinetSpecsService.getById(id));
  }

	/**
	 * 根据柜子编号来获取柜子规格
	 * @Param cabinetNo
	 * @return R
	 */
	@GetMapping("/getCabinetSpecsByNo")
	public R<List<CabinetSpecs>> getCabinetSpecsByNo(@Param("String specsNo")String specsNo){
		return new R<>(cabinetSpecsService.getCabinetSpecsByNo(specsNo));
	}

	/**
   * 新增记录
   * @param cabinetSpecs
   * @return R
   */
  @SysLog("新增售货柜规格表")
  @PostMapping
  public R save(@RequestBody CabinetSpecs cabinetSpecs){
    return new R<>(cabinetSpecsService.save(cabinetSpecs));
  }

  /**
   * 修改记录
   * @param cabinetSpecs
   * @return R
   */
  @SysLog("修改售货柜规格表")
  @PutMapping
  public R update(@RequestBody CabinetSpecs cabinetSpecs){
    return new R<>(cabinetSpecsService.updateById(cabinetSpecs));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除售货柜规格表")
  @DeleteMapping("/{id}")
  public R removeById(@PathVariable Integer id){
    return new R<>(cabinetSpecsService.removeById(id));
  }

}
