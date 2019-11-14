package com.sensor.sellCabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.sensor.sellCabinet.model.entity.Cosumer;
import com.sensor.sellCabinet.service.CosumerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 用户表(消费者)
 *
 * @author chengpan
 * @date 2019-04-10 16:21:00
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cosumer")
public class CosumerController {

  private final  CosumerService cosumerService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param cosumer 用户表(消费者)
   * @return
   */
  @GetMapping("/page")
  public R<IPage<Cosumer>> getCosumerPage(Page<Cosumer> page, Cosumer cosumer) {
    return  new R<>(cosumerService.getCosumerPage(page,cosumer));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<Cosumer> getById(@PathVariable("id") String id){
    return new R<>(cosumerService.getById(id));
  }

  /**
   * 新增记录
   * @param cosumer
   * @return R
   */
  @SysLog("新增用户表(消费者)")
  @PostMapping
  public R save(@RequestBody Cosumer cosumer){
	  cosumer.setCreateDate(LocalDateTime.now());
    return new R<>(cosumerService.save(cosumer));
  }

  /**
   * 修改记录
   * @param cosumer
   * @return R
   */
  @SysLog("修改用户表(消费者)")
  @PutMapping
  public R update(@RequestBody Cosumer cosumer){
    return new R<>(cosumerService.updateById(cosumer));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除用户表(消费者)")
  @DeleteMapping("/{id}")
  public R removeById(@PathVariable String id){
    return new R<>(cosumerService.removeById(id));
  }

}
