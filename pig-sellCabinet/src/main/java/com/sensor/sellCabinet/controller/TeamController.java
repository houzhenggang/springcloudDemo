package com.sensor.sellCabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.sensor.sellCabinet.config.SequenceConfiguration;
import com.sensor.sellCabinet.model.dto.TeamDto;
import com.sensor.sellCabinet.model.entity.Team;
import com.sensor.sellCabinet.service.TeamService;
import com.sensor.sellCabinet.service.UserInfoService;
import com.sensor.sellCabinet.util.CodeUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 场地表
 *
 * @author chengpan
 * @date 2019-04-15 10:23:43
 */
@RestController
@AllArgsConstructor
@RequestMapping("/team")
public class TeamController {

  private final  TeamService teamService;

	private final UserInfoService userInfoService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param team 场地表
   * @return
   */
  @GetMapping("/page")
  public R<IPage<TeamDto>> getTeamPage(Page<TeamDto> page, TeamDto team) {
	  SysUser user = userInfoService.getSysUserInfo();
    return  new R<>(teamService.getTeamPage(page,team,user));
  }

	/**
	 * 查询场地list
	 * @param team 场地表
	 * @return
	 */
	@GetMapping(value = "/getTeamList")
	public R<List<TeamDto>> getTeamList(TeamDto team) {
		SysUser user = userInfoService.getSysUserInfo();
		return  new R<>(teamService.getTeamList(team,user));
	}
  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<Team> getById(@PathVariable("id") Integer id){
    return new R<>(teamService.getById(id));
  }

  /**
   * 新增记录
   * @param team
   * @return R
   */
  @SysLog("新增场地表")
  @PostMapping
  public R save(@RequestBody Team team){
	  String name = SequenceConfiguration.TEAM;
	  String code = CodeUtil.getPrimaryKey(name);
	  SysUser user = userInfoService.getSysUserInfo();
	  team.setTeamCode(code);
	  team.setCreateDate(LocalDateTime.now());
	  team.setCreateUser(user.getUserId().toString());
	  if (("").equals(team.getFirstAdmin())||("0").equals(team.getFirstAdmin())){
		  team.setFirstAdmin(null);
		  team.setFirstRate(new BigDecimal("0"));
	  }
	  if (("").equals(team.getSecondAdmin())||("0").equals(team.getSecondAdmin())){
		  team.setSecondAdmin(null);
		  team.setSecondRate(new BigDecimal("0"));
	  }
	  if (("").equals(team.getThirdAdmin())||("0").equals(team.getThirdAdmin())){
		  team.setThirdAdmin(null);
		  team.setThirdRate(new BigDecimal("0"));
	  }
	  if (("").equals(team.getFourthAdmin())||("0").equals(team.getFourthAdmin())){
		  team.setFourthAdmin(null);
		  team.setFourthRate(new BigDecimal("0"));
	  }
	  if (("").equals(team.getFifthAdmin())||("0").equals(team.getFifthAdmin())){
		  team.setFifthAdmin(null);
		  team.setFifthRate(new BigDecimal("0"));
	  }
    return new R<>(teamService.save(team));
  }

  /**
   * 修改记录
   * @param team
   * @return R
   */
  @SysLog("修改场地表")
  @PutMapping
  public R update(@RequestBody Team team){
	  SysUser user = userInfoService.getSysUserInfo();
	  if (("").equals(team.getFirstAdmin())||("0").equals(team.getFirstAdmin())){
		  team.setFirstAdmin(null);
		  team.setFirstRate(new BigDecimal("0"));
	  }
	  if (("").equals(team.getSecondAdmin())||("0").equals(team.getSecondAdmin())){
		  team.setSecondAdmin(null);
		  team.setSecondRate(new BigDecimal("0"));
	  }
	  if (("").equals(team.getThirdAdmin())||("0").equals(team.getThirdAdmin())){
		  team.setThirdAdmin(null);
		  team.setThirdRate(new BigDecimal("0"));
	  }
	  if (("").equals(team.getFourthAdmin())||("0").equals(team.getFourthAdmin())){
		  team.setFourthAdmin(null);
		  team.setFourthRate(new BigDecimal("0"));
	  }
	  if (("").equals(team.getFifthAdmin())||("0").equals(team.getFifthAdmin())){
		  team.setFifthAdmin(null);
		  team.setFifthRate(new BigDecimal("0"));
	  }
	  team.setUpdateDate(LocalDateTime.now());
	  team.setCreateUser(user.getUserId().toString());
	  return new R<>(teamService.updateOne(team));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除场地表")
  @DeleteMapping("/{id}")
  public R removeById(@PathVariable Integer id){
    return new R<>(teamService.removeById(id));
  }

}
