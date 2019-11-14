package com.sensor.sellCabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.core.util.SensorString;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.sensor.sellCabinet.model.dto.CabinetInfoDto;
import com.sensor.sellCabinet.model.entity.CabinetInfo;
import com.sensor.sellCabinet.model.entity.RunCabinetProduct;
import com.sensor.sellCabinet.service.*;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * 货柜信息
 *
 * @author chengpan
 * @date 2019-04-03 11:21:45
 */
@RestController
@AllArgsConstructor
@RequestMapping("/cabinetinfo")
public class CabinetInfoController {

	private final  CabinetInfoService cabinetInfoService;

	private final RunCabinetProductService runCabinetProductService;

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param cabinetInfo 货柜信息
	 * @return
	 */
	@GetMapping("/page")
	public R<IPage<CabinetInfoDto>> getCabinetInfoPage(Page<CabinetInfoDto> page, CabinetInfoDto cabinetInfo) {
		return new R<>(cabinetInfoService.getCabinetInfoPage(page,cabinetInfo));
	}
	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param cabinetInfo 货柜信息
	 * @return
	 */
	@GetMapping("/reportElectric")
	public R<IPage<CabinetInfoDto>> reportElectric(Page<CabinetInfoDto> page, CabinetInfoDto cabinetInfo) {
		return new R<>(cabinetInfoService.reportElectric(page,cabinetInfo));
	}

	/**
	 * 通过id查询单条记录
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R<CabinetInfo> getById(@PathVariable("id") String id){
		return new R<>(cabinetInfoService.getById(id));
	}

	/**
	 * 新增记录
	 * @param cabinetInfo
	 * @return R
	 */
	@SysLog("新增货柜信息")
	@PostMapping
	public R save(@RequestBody CabinetInfo cabinetInfo){
		cabinetInfo.setImportDate(LocalDateTime.now());
		return new R<>(cabinetInfoService.save(cabinetInfo));
	}

	/**
	 * 修改记录
	 * @param cabinetInfo
	 * @return R
	 */
	@SysLog("修改货柜信息")
	@PutMapping
	public R update(@RequestBody CabinetInfo cabinetInfo){
		return new R<>(cabinetInfoService.updateById(cabinetInfo));
	}
	/**
	 * 绑定场地
	 * @param cabinetInfo
	 * @return R
	 */
	@SysLog("绑定场地")
	@PutMapping("/updateCabinetTeamCode")
	public R updateCabinetTeamCode(@RequestBody CabinetInfo cabinetInfo){
		cabinetInfo.setImportDate(LocalDateTime.now());
		return new R<>(cabinetInfoService.updateById(cabinetInfo));
	}

	@PostMapping("/importBatch")
	@Transactional(rollbackFor = Exception.class)
	public R importBatch(@RequestParam("file") MultipartFile file, String teamCode) {
		List<CabinetInfo> cabinetInfoList = new ArrayList<CabinetInfo>();
		List<RunCabinetProduct> runCabinetProductList = new ArrayList<RunCabinetProduct>();
		try{
			InputStream is = file.getInputStream();
			HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(is));
			//有多少个sheet
			int sheets = workbook.getNumberOfSheets();
			if(sheets==2){
				HSSFSheet sheeTwo = workbook.getSheetAt(1);
				int rowsTwo = sheeTwo.getPhysicalNumberOfRows();
				for (int m = 1; m < rowsTwo; m++) {
					HSSFRow rowtwo = sheeTwo.getRow(m);
					String channelNo = rowtwo.getCell(0).toString();
					String productCode = rowtwo.getCell(1).toString();
					String currentPrice = rowtwo.getCell(2).toString();
					RunCabinetProduct runCabinetProduct = new RunCabinetProduct();
					runCabinetProduct.setChannelNo((int)Float.parseFloat(channelNo));
					runCabinetProduct.setProductCode(productCode);
					runCabinetProduct.setCurrentPrice(new BigDecimal(currentPrice));
					runCabinetProductList.add(runCabinetProduct);
				}
			}
	//			for (int i = 0; i < sheets; i++) {
	//			}
				//第一个sheet
				HSSFSheet sheetOne = workbook.getSheetAt(0);
				//获取多少行
				int rowsOne = sheetOne.getPhysicalNumberOfRows();
				//遍历每一行，注意：第 0 行为标题
				for (int j = 1; j < rowsOne; j++) {
					//获得第 j 行
					HSSFRow row = sheetOne.getRow(j);
					String cabinetNo = row.getCell(0).toString();
					String cabinetAddress = "";
					if(row.getCell(1)!=null){
						cabinetAddress = row.getCell(1).toString();
					}
					if (cabinetNo!=null&&!"".equals(cabinetNo)){
						CabinetInfo cabinetInfo = cabinetInfoService.getCabinetByUser(cabinetNo);
						if (null!=cabinetInfo){
							if (runCabinetProductList.size()>0){
								runCabinetProductList.stream().forEach(item ->updateProduct(cabinetNo,item));
							}
							cabinetInfo.setTeamCode(teamCode);
							cabinetInfo.setCabinetAddress(cabinetAddress);
							cabinetInfo.setImportDate(LocalDateTime.now());
							cabinetInfoList.add(cabinetInfo);
						}else{
							return new R(Boolean.FALSE,"第"+j+"行"+"柜子编号:"+cabinetNo+"\n"+"该柜子不存在或不属于当前登录人");
						}
					}
				}
		}catch(Exception e){
			e.printStackTrace();
			return new R(Boolean.FALSE,"下发失败");
		}
		if (null!=cabinetInfoList&&cabinetInfoList.size()>0){
			Integer res = cabinetInfoService.updateCabinetTeamCodeBatch(cabinetInfoList);
			if (res>0){
				return new R(Boolean.TRUE,"success");
			}
		}
		return new R(Boolean.TRUE,"success");
	}

	private void updateProduct(String cabinetNo,RunCabinetProduct runCabinetProduct) {
		runCabinetProduct.setCabinetNo(cabinetNo);
		runCabinetProductService.updateProduct(runCabinetProduct);
	}

	/**
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/rebackBatch")
	@Transactional(rollbackFor = Exception.class)
	public R rebackBatch(@RequestParam("file") MultipartFile file) throws Exception {
		List<CabinetInfo> cabinetInfoList = new ArrayList<CabinetInfo>();
		try{
			InputStream is = file.getInputStream();
			HSSFWorkbook workbook = new HSSFWorkbook(new POIFSFileSystem(is));
			//有多少个sheet
			int sheets = workbook.getNumberOfSheets();

			for (int i = 0; i < sheets; i++) {
				HSSFSheet sheet = workbook.getSheetAt(i);
				//获取多少行
				int rows = sheet.getPhysicalNumberOfRows();
				//遍历每一行，注意：第 0 行为标题
				for (int j = 1; j < rows; j++) {
					//获得第 j 行
					HSSFRow row = sheet.getRow(j);
					String cabinetNo = row.getCell(0).toString();
					if (cabinetNo!=null){
						CabinetInfo cabinetInfo = cabinetInfoService.getCabinet(cabinetNo);
						if (null!=cabinetInfo){
							cabinetInfo.setTeamCode("TEA1000000004CurBxIM");
							cabinetInfo.setImportDate(LocalDateTime.now());
							cabinetInfoList.add(cabinetInfo);
						}
					}

				}
			}
		}catch(Exception e){
			return new R(Boolean.FALSE,"Fail");
		}
		if (null!=cabinetInfoList&&cabinetInfoList.size()>0){
			Integer res = cabinetInfoService.updateCabinetTeamCodeBatch(cabinetInfoList);
			if (res>0){
				return new R(Boolean.TRUE,"success");
			}
		}
		return new R(Boolean.TRUE,"sussess");
	}
	/**
	 * 通过id删除一条记录
	 * @param id
	 * @return R
	 */
	@SysLog("删除货柜信息")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable String id){
		return new R<>(cabinetInfoService.removeById(id));
	}

}
