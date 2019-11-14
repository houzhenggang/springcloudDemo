package com.sensor.sellCabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.sensor.sellCabinet.model.dto.PayDetailDto;
import com.sensor.sellCabinet.model.entity.PayDetail;
import com.sensor.sellCabinet.service.PayDetailService;
import com.sensor.sellCabinet.service.UserInfoService;
import com.sensor.sellCabinet.model.dto.PayStatus;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * 实收明细表
 *
 * @author chengpan
 * @date 2019-04-16 14:48:24
 */
@RestController
@AllArgsConstructor
@RequestMapping("/paydetail")
public class PayDetailController {

  private final  PayDetailService payDetailService;
	private final UserInfoService userInfoService;
  /**
   * 简单分页查询
   * @param page 分页对象
   * @param payDetail 实收明细表
   * @return
   */
  @GetMapping(value = "/page")
  public R<IPage<PayDetailDto>> getPayDetailPage(Page<PayDetail> page, PayDetailDto payDetail) {
	  SysUser user = userInfoService.getSysUserInfo();
    return  new R<>(payDetailService.getPayDetailPage(page,payDetail,user));
  }

	@GetMapping(value = "/getPayDetailSum")
	public R<BigDecimal> getPayDetailSum(PayDetailDto payDetail) {
		SysUser user = userInfoService.getSysUserInfo();
		return  new R<>(payDetailService.getPayDetailSum(payDetail,user));
	}
  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<PayDetail> getById(@PathVariable("id") Integer id){
    return new R<>(payDetailService.getById(id));
  }

  /**
   * 新增记录
   * @param payDetail
   * @return R
   */
  @SysLog("新增实收明细表")
  @PostMapping
  public R save(@RequestBody PayDetail payDetail){
    return new R<>(payDetailService.save(payDetail));
  }

  /**
   * 修改记录
   * @param payDetail
   * @return R
   */
  @SysLog("修改实收明细表")
  @PutMapping
  public R update(@RequestBody PayDetail payDetail){
    return new R<>(payDetailService.updateById(payDetail));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除实收明细表")
  @DeleteMapping("/{id}")
  public R removeById(@PathVariable Integer id){
    return new R<>(payDetailService.removeById(id));
  }

	/**
	 创建表头
	 */
	private void createTitle(HSSFWorkbook workbook, HSSFSheet sheet){
		HSSFRow row = sheet.createRow(0);
		//设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
		sheet.setColumnWidth(0,15*256);
		sheet.setColumnWidth(1,25*256);
		sheet.setColumnWidth(2,10*256);
		sheet.setColumnWidth(3,25*256);
		sheet.setColumnWidth(4,25*256);
		sheet.setColumnWidth(5,25*256);
		sheet.setColumnWidth(6,25*256);
		sheet.setColumnWidth(7,10*256);
		sheet.setColumnWidth(8,10*256);
		sheet.setColumnWidth(9,10*256);
		sheet.setColumnWidth(10,10*256);
		sheet.setColumnWidth(11,10*256);
		sheet.setColumnWidth(12,25*256);

		//设置为居中加粗
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		style.setAlignment(HorizontalAlignment.GENERAL);
		style.setFont(font);

		HSSFCell cell;

		cell = row.createCell(0);
		cell.setCellValue("序号");
		cell.setCellStyle(style);

		cell = row.createCell(1);
		cell.setCellValue("实收总账编号");
		cell.setCellStyle(style);

		cell = row.createCell(2);
		cell.setCellValue("通道号");
		cell.setCellStyle(style);

		cell = row.createCell(3);
		cell.setCellValue("货柜编号");
		cell.setCellStyle(style);

		cell = row.createCell(4);
		cell.setCellValue("场地名称");
		cell.setCellStyle(style);

		cell = row.createCell(5);
		cell.setCellValue("商品编号");
		cell.setCellStyle(style);

		cell = row.createCell(6);
		cell.setCellValue("商品名称");
		cell.setCellStyle(style);

		cell = row.createCell(7);
		cell.setCellValue("开门是否成功");
		cell.setCellStyle(style);

		cell = row.createCell(8);
		cell.setCellValue("订单付款状态");
		cell.setCellStyle(style);

		cell = row.createCell(9);
		cell.setCellValue("分润模式");
		cell.setCellStyle(style);

		cell = row.createCell(10);
		cell.setCellValue("费率");
		cell.setCellStyle(style);

		cell = row.createCell(11);
		cell.setCellValue("金额");
		cell.setCellStyle(style);

		cell = row.createCell(12);
		cell.setCellValue("创建日期");
		cell.setCellStyle(style);

	}

	@GetMapping(value = "/exportExcel")
	public String exportPayDetaiExcel(HttpServletResponse response, PayDetailDto payDetail) throws Exception{
		SysUser user = userInfoService.getSysUserInfo();
		List<PayDetailDto> payDetialList = payDetailService.getPayDetailForExcel(payDetail,user);

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("统计表");
		createTitle(workbook,sheet);
		//设置日期格式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy h:mm"));

		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		//新增数据行，并且设置单元格数据
		int rowNum=1;
		if (null!=payDetialList&&payDetialList.size()>0){
			for(PayDetailDto detail:payDetialList){
				HSSFRow row = sheet.createRow(rowNum);
				row.createCell(0).setCellValue(rowNum);
				row.createCell(1).setCellValue(detail.getOrderCode()==null?"":detail.getOrderCode());
				row.createCell(2).setCellValue(detail.getChannelNo()==null?"":Integer.toString(detail.getChannelNo()));
				row.createCell(3).setCellValue(detail.getCabinetNo()==null?"":detail.getCabinetNo());
				row.createCell(4).setCellValue(detail.getTeamName()==null?"":detail.getTeamName());
				row.createCell(5).setCellValue(detail.getProductCode()==null?"":detail.getProductCode());
				row.createCell(6).setCellValue(detail.getProductName()==null?"":detail.getProductName());

				String openStatus = detail.getOpenStatus();
					switch(openStatus){
						case "0":
							row.createCell(7).setCellValue("失败");
							break;
						case "1":
							row.createCell(7).setCellValue("成功");
							break;
						default:
							row.createCell(7).setCellValue("");
					}

				String payStatus = detail.getPayStatus();
				PayStatus payStatusEnum = PayStatus.fromTypeName(payStatus);
					switch (payStatusEnum){
						case NOPAY:
							row.createCell(8).setCellValue("未付款");
							break;
						case PAYED:
							row.createCell(8).setCellValue("付款成功");
							break;
						case BAKPAYING:
							row.createCell(8).setCellValue("退款中");
							break;
						case BAKPAYED:
							row.createCell(8).setCellValue("已退款");
							break;
						case REFUSED:
							row.createCell(8).setCellValue("退款失败");
							break;
						default:
							row.createCell(8).setCellValue("");
					}
				String rateType = detail.getRateType();
					switch(rateType){
						case "0":
							row.createCell(9).setCellValue("比例");
							break;
						case "1":
							row.createCell(9).setCellValue("定价");
							break;
						default:
							row.createCell(9).setCellValue("");
					}
				row.createCell(10).setCellValue(detail.getProductPrice()==null?"":detail.getProductPrice().toString());
				row.createCell(11).setCellValue(detail.getPayAmount()==null?"":detail.getPayAmount().toString());
				row.createCell(12).setCellValue(detail.getCreateDate()==null?"":df.format(detail.getCreateDate()));
				rowNum++;
			}
		}

		String fileName = "实收明细.xls";

		//生成excel文件
//			buildExcelFile(fileName, workbook,response);
		//浏览器下载excel
		buildExcelDocument(fileName,workbook,response);
		return "download excel";
	}
	/**
	 *  //生成excel文件
	 * @param filename
	 * @param workbook
	 * @param response
	 * @throws Exception
	 */
	protected void buildExcelFile(String filename,HSSFWorkbook workbook,HttpServletResponse response) throws Exception{
		//生成file
		FileOutputStream fos = new FileOutputStream(filename);
		workbook.write(fos);
		fos.flush();
		fos.close();
		//下载file
//		File file = new File(filename);
//		byte[] buffer = new byte[1024];
//		FileInputStream fis = null;
//		BufferedInputStream bis = null;
//		try{
//			fis = new FileInputStream(file);
//			bis = new BufferedInputStream(fis);
//			OutputStream outputStream = response.getOutputStream();
//			int i = bis.read(buffer);
//			while(i!=-1){
//				outputStream.write(buffer, 0, i);
//				i = bis.read(buffer);
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally {
//			if (bis!=null){
//				try{
//					bis.close();
//				}catch (Exception e){
//					e.printStackTrace();
//				}
//			}
//			if (fis!=null){
//				try{
//					fis.close();
//				}catch (Exception e){
//					e.printStackTrace();
//				}
//			}
//		}
	}

	//浏览器下载excel
	protected void buildExcelDocument(String filename,HSSFWorkbook workbook,HttpServletResponse response) throws Exception{
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(filename, "utf-8"));
		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		outputStream.flush();
//		outputStream.close();
		workbook.close();
	}

}
