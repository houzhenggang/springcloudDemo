package com.sensor.sellCabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.sensor.sellCabinet.model.dto.IncomeDto;
import com.sensor.sellCabinet.model.entity.Income;
import com.sensor.sellCabinet.service.IncomeService;
import com.sensor.sellCabinet.service.PayInfoService;
import com.sensor.sellCabinet.service.UserInfoService;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * 分润表
 *
 * @author chengpan
 * @date 2019-07-02 10:13:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/income")
public class IncomeController {

  private final  IncomeService incomeService;

	private UserInfoService userInfoService;

	private PayInfoService payInfoService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param income 分润表
   * @return
   */
  @GetMapping(value = "/page")
  public R<IPage<IncomeDto>> getIncomePage(Page<IncomeDto> page, IncomeDto income) {
	  SysUser user = userInfoService.getSysUserInfo();
    return new R<>(incomeService.getIncomePage(page,income,user));
  }

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param income 分润表
	 * @return
	 */
	@GetMapping(value = "/deatilPage")
	public R<IPage<IncomeDto>> deatilPage(Page<IncomeDto> page, IncomeDto income) {
		SysUser user = userInfoService.getSysUserInfo();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime minusDay = localDateTime.minusDays(1);
		income.setEDate(df.format(minusDay));
		income.setStatus("0");
		return new R<>(incomeService.getIncomePage(page,income,user));
	}

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param income 待分润表
	 * @return
	 */
	@GetMapping(value = "/confirmPage")
	public R<IPage<IncomeDto>> getIncomeConfirmPage(Page<IncomeDto> page, IncomeDto income) {
		SysUser user = userInfoService.getSysUserInfo();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime minusDay = localDateTime.minusDays(1);
		income.setEDate(df.format(minusDay));
		income.setIncomeStatus("0");
		income.setStatus("0");
		return new R<>(incomeService.getIncomeConfirmPage(page,income,user));
	}

	@GetMapping(value = "/confirmIncome")
	@Transactional(rollbackFor = Exception.class)
	public R confirmIncome(IncomeDto income) throws Exception {
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime minusDay = localDateTime.minusDays(1);
		income.setEDate(df.format(minusDay));
		income.setIncomeStatus("0");
		try {
			if (income.getTeamCode()!=null&&!"".equals(income.getTeamCode())){
				List<IncomeDto> incomeDtoList = incomeService.getIncomeList(income);
				for (Income inco:incomeDtoList) {
					payInfoService.updateBalance(inco);
				}
			return new R<>(Boolean.TRUE);
			}else {
				return new R<>(Boolean.FALSE,"场地编码不存在或无效");
			}
		}catch (Exception e){
			throw new Exception("审核失败");
		}
	}

  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<Income> getById(@PathVariable("id") Integer id){
    return new R<>(incomeService.getById(id));
  }

  /**
   * 新增记录
   * @param income
   * @return R
   */
  @SysLog("新增分润表")
  @PostMapping
  public R save(@RequestBody Income income){
    return new R<>(incomeService.save(income));
  }

  /**
   * 修改记录
   * @param income
   * @return R
   */
  @SysLog("修改分润表")
  @PutMapping
  public R update(@RequestBody Income income){
    return new R<>(incomeService.updateById(income));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除分润表")
  @DeleteMapping("/{id}")
  public R removeById(@PathVariable Integer id){
    return new R<>(incomeService.removeById(id));
  }

}
