package com.sensor.sellCabinet.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.sensor.sellCabinet.model.dto.RunCabinetProductInfo;
import com.sensor.sellCabinet.model.entity.RunCabinetProduct;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 运行货柜商品信息
 *
 * @author chengpan
 * @date 2019-04-15 17:36:58
 */
public interface RunCabinetProductMapper extends BaseMapper<RunCabinetProduct> {
  /**
    * 运行货柜商品信息简单分页查询
    * @param runCabinetProduct 运行货柜商品信息
    * @return
    */
  IPage<RunCabinetProductInfo> getRunCabinetProductPage(Page page, @Param("runCabinetProduct") RunCabinetProduct runCabinetProduct,@Param("user") SysUser user);

	List<RunCabinetProductInfo> getRunCabinetProductDtoList(@Param("cabinetNo")String cabinetNo,@Param("user") SysUser user);

	List<RunCabinetProductInfo> getWeRunCabinetProductDtoList(@Param("cabinetNo")String cabinetNo);

	int updateChannelStatus(@Param("cabinetNo")String cabinetNo, @Param("channelNo")Integer channelNo);

	int updateProductPrice(@Param("productCode")String productCode, @Param("currentPrice")String currentPrice, @Param("teamCode")String teamCode);

	Boolean updateRunCabinet(@Param("cabinetNo") String cabinetNo,@Param("currentNum")Integer currentNum,@Param("effictSdate") LocalDateTime effictSdate, @Param("effictEdate") LocalDateTime effictEdate, @Param("updateTime") LocalDateTime updateTime);

	void updateProduct(@Param("runCabinetProduct") RunCabinetProduct runCabinetProduct);

	RunCabinetProduct getRunCabinetProduct(@Param("cabinetNo")String cabinetNo, @Param("channelNo")Integer channelNo);
}
