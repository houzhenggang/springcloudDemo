package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.model.dto.TeamDto;
import com.sensor.sellCabinet.model.entity.Team;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 场地表
 *
 * @author chengpan
 * @date 2019-04-15 10:23:43
 */
public interface TeamMapper extends BaseMapper<Team> {
  /**
    * 场地表简单分页查询
    * @param team 场地表
    * @return
    */
  IPage<TeamDto> getTeamPage(Page page, @Param("team") TeamDto team, @Param("user") SysUser user);

  List<TeamDto> getTeamPage(@Param("team") TeamDto team, @Param("user") SysUser user);

	Team getTeamByCode(@Param("teamCode") String teamCode);

	Boolean updateOne(@Param("team") Team team);
}
