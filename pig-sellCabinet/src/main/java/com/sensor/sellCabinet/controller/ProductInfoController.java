package com.sensor.sellCabinet.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.core.util.SensorString;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.sensor.sellCabinet.config.SequenceConfiguration;
import com.sensor.sellCabinet.config.WechatConfigurer;
import com.sensor.sellCabinet.model.dto.ProductInfoDto;
import com.sensor.sellCabinet.model.entity.ProductInfo;
import com.sensor.sellCabinet.service.ProductInfoService;
import com.sensor.sellCabinet.service.UserInfoService;
import com.sensor.sellCabinet.util.CodeUtil;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDateTime;


/**
 * 商品信息表
 *
 * @author chengpan
 * @date 2019-04-15 16:24:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/productinfo")
public class ProductInfoController {

  private final ProductInfoService productInfoService;

	private UserInfoService userInfoService;

	@Autowired
	private WechatConfigurer wechatConfigurer;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param productInfo 商品信息表
   * @return
   */
  @GetMapping("/wpage")
  public R<IPage<ProductInfoDto>> getProductInfoPage(Page<ProductInfoDto> page, ProductInfoDto productInfo) {
	  SysUser user = userInfoService.getSysUserInfo();
    return  new R<>(productInfoService.getProductInfoPage(page,productInfo,user));
  }


	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param productInfo 商品信息表
	 * @return
	 */
	@GetMapping("/page")
	public R<IPage<ProductInfoDto>> getProductInfoMobilePage(Page<ProductInfoDto> page, ProductInfoDto productInfo) {
		SysUser user = userInfoService.getSysUserInfo();
		return  new R<>(productInfoService.getProductInfoMobilePage(page,productInfo,user));
	}
	/**
	 * 定价维护根据场地获取商品
	 * @param page 分页对象
	 * @param teamCode 场地编码
	 * @return
	 */
	@GetMapping("/getProductByTeamCode")
	public R<IPage<ProductInfo>> getProductByTeamCode(Page page,@Param("teamCode") String teamCode) {
		return  new R<>(productInfoService.getProductByTeamCode(page,teamCode));
	}

  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<ProductInfo> getById(@PathVariable("id") String id){
    return new R<>(productInfoService.getById(id));
  }

  /**
   * 新增记录
   * @param productInfo
   * @return R
   */
  @SysLog("新增商品信息表")
  @PostMapping
  public R save(@RequestBody ProductInfo productInfo)throws Exception{
	  SysUser user = userInfoService.getSysUserInfo();
	  String name = SequenceConfiguration.PRODUCT_INFO;
	  String code = CodeUtil.getPrimaryKey(name);
	  productInfo.setProductCode(code);
	  productInfo.setProductImages(productInfo.getProductImages());
	  productInfo.setProxyBusiness(user.getUserId().toString());
	  productInfo.setOperator(user.getUserId().toString());
	  productInfo.setImportDate(LocalDateTime.now());
	  productInfo.setLastModifydate(LocalDateTime.now());
//	  String resultPwd = "";
//	  String tempStr = "/static/images";
//	  //获取上传后原图的相对地址
//	  String imageUrl=uploadImage(file, resultPwd,tempStr);
    return new R<>(productInfoService.save(productInfo));
  }

	/**
	 * 上传图片
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@SysLog("上传图片")
	@PostMapping("/upload")
	public String upload(@RequestParam(value = "file") MultipartFile file,@Param("lastImagePath") String lastImagePath)throws Exception{
		String tempStr = "/usr/local/sellCabinet/images";
//		String tempStr = "D:"+File.separator+"SVN";
		if (!"".equals(lastImagePath)&&null!=lastImagePath){
//			String temPath = tempStr+lastImagePath.substring(26);
//			String temPath = tempStr+lastImagePath.substring(6);
			String temPath = tempStr+File.separator+lastImagePath.substring(24);
			updateImage(temPath);
		}
	  //获取上传后原图的相对地址
	  String pictureName=uploadImage(file,tempStr);
	  return pictureName;
	}

	public String uploadImage(MultipartFile file,String tempStr) throws IOException {
		//如果目录不存在则创建目录
		String realUploadPath = tempStr;
		File uploadFile=new File(realUploadPath);
		String pictureName = SensorString.randString(16)+".jpg";
		if(!uploadFile.exists()){
			uploadFile.mkdirs();
		}
		//创建输入流
		InputStream inputStream=file.getInputStream();
		//生成输出地址URL
		String outputPath=realUploadPath+File.separator+pictureName;
		//创建输出流
		OutputStream outputStream=new FileOutputStream(outputPath);
		//设置缓冲区
		byte[] buffer=new byte[1024];
		//输入流读入缓冲区，输出流从缓冲区写出
		while((inputStream.read(buffer))>0){
			outputStream.write(buffer);
		}
		outputStream.close();
		//返回原图上传后的相对地址
//		return tempStr+File.separator+pictureName;
		return wechatConfigurer.getRealmName()+"/"+pictureName;
	}

	/**
	 * 删除图片
	 * @param tempStr
	 * @return
	 * @throws IOException
	 */
	public Boolean updateImage(String tempStr) throws IOException {
		//先判断文件是否存在
		String realUploadPath = tempStr;
		File uploadFile=new File(realUploadPath);
		if (uploadFile.isFile() && uploadFile.exists()) {
			uploadFile.delete();
			return true;
	 	}
		return false;
	}

  /**
   * 修改记录
   * @param productInfo
   * @return R
   */
  @SysLog("修改商品信息表")
  @PutMapping
  public R update(@RequestBody ProductInfo productInfo){
	  productInfo.setLastModifydate(LocalDateTime.now());
    return new R<>(productInfoService.updateById(productInfo));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除商品信息表")
  @DeleteMapping("/{id}")
  public R removeById(@PathVariable String id){
    return new R<>(productInfoService.removeById(id));
  }

}
