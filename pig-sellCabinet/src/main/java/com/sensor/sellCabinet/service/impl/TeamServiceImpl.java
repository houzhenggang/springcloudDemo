package com.sensor.sellCabinet.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.mapper.TeamMapper;
import com.sensor.sellCabinet.model.dto.TeamDto;
import com.sensor.sellCabinet.model.entity.Team;
import com.sensor.sellCabinet.service.TeamService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 场地表
 *
 * @author chengpan
 * @date 2019-04-15 10:23:43
 */
@Service("teamService")
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

  /**
   * 场地表简单分页查询
   * @param team 场地表
   * @return
   */
  @Override
  public IPage<TeamDto> getTeamPage(Page<TeamDto> page, TeamDto team, SysUser user){
      return baseMapper.getTeamPage(page,team,user);
  }

	@Override
	public List<TeamDto> getTeamList(TeamDto team, SysUser user) {
		return baseMapper.getTeamPage(team,user);
	}
	@Override
	public Boolean updateOne(Team team) {
		return baseMapper.updateOne(team);
	}

}
