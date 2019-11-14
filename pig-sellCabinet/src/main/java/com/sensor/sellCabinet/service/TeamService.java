package com.sensor.sellCabinet.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.model.dto.TeamDto;
import com.sensor.sellCabinet.model.entity.Team;

import java.util.List;

/**
 * 场地表
 *
 * @author chengpan
 * @date 2019-04-15 10:23:43
 */
public interface TeamService extends IService<Team> {

  /**
   * 场地表简单分页查询
   * @param team 场地表
   *
   * @return
   */
  IPage<TeamDto> getTeamPage(Page<TeamDto> page, TeamDto team, SysUser user);

	List<TeamDto> getTeamList(TeamDto team, SysUser user);

	Boolean updateOne(Team team);
}
