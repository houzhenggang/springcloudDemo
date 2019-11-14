package com.sensor.sellCabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.sensor.sellCabinet.model.dto.ProductUserDto;
import com.sensor.sellCabinet.model.entity.ProductUser;
import com.sensor.sellCabinet.service.ProductUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 商品代理商关系表
 *
 * @author chengpan
 * @date 2019-08-20 17:09:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/productuser")
public class ProductUserController {

  private final  ProductUserService productUserService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param productUser 商品代理商关系表
   * @return
   */
  @GetMapping("/page")
  public R<IPage<ProductUser>> getProductUserPage(Page<ProductUser> page, ProductUser productUser) {
    return  new R<>(productUserService.getProductUserPage(page,productUser));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<ProductUser> getById(@PathVariable("id") Integer id){
    return new R<>(productUserService.getById(id));
  }

	/**
	 * 新增记录
	 * @param productUserDto
	 * @return R
	 */
	@SysLog("新增商品代理商关系表")
	@PostMapping(value = "/saveBatch")
	public R saveBatch(@RequestBody ProductUserDto productUserDto){
		List<ProductUser> productUserList = productUserDto.getProductUserList();
		if(null!=productUserList&&productUserList.size()>0){
			for (ProductUser productUser:productUserList) {
				ProductUser resPre = productUserService.getProductUser(productUser);
				if (null==resPre){
					productUserService.save(productUser);
				}
			}
		}
		return new R(Boolean.TRUE,"success");
	}

	/**
	 * 新增记录
	 * @param productUserDto
	 * @return R
	 */
	@SysLog("删除商品代理商关系表")
	@DeleteMapping(value = "/removeBatch")
	public R removeBatch(@RequestBody ProductUserDto productUserDto){
		List<ProductUser> productUserList = productUserDto.getProductUserList();
		if(null!=productUserList&&productUserList.size()>0){
			for (ProductUser productUser:productUserList) {
				ProductUser resPre = productUserService.getProductUser(productUser);
				if (null!=resPre){
					productUserService.removeById(resPre.getId());
				}
			}
		}
		return new R(Boolean.TRUE,"success");
	}
  /**
   * 新增记录
   * @param productUser
   * @return R
   */
  @SysLog("新增商品代理商关系表")
  @PostMapping(value = "/save")
  public R save(@RequestBody ProductUser productUser){
    return new R<>(productUserService.save(productUser));
  }

  /**
   * 修改记录
   * @param productUser
   * @return R
   */
  @SysLog("修改商品代理商关系表")
  @PutMapping
  public R update(@RequestBody ProductUser productUser){
    return new R<>(productUserService.updateById(productUser));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除商品代理商关系表")
  @DeleteMapping("/{id}")
  public R removeById(@PathVariable Integer id){
    return new R<>(productUserService.removeById(id));
  }
}
