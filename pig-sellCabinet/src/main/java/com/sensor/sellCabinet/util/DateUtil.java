package com.sensor.sellCabinet.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {
	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	//获取当天初始时间
	public static LocalDateTime getToBeginTime() {
		//获取当前日期
		LocalDate nowDate = LocalDate.now();
		//设置零点
		LocalDateTime beginTime = LocalDateTime.of(nowDate, LocalTime.MIN);
		//将时间进行格式化
//		return beginTime.format(dtf);
		return  beginTime;
	}
	//获取当天结尾时间
	public static LocalDateTime getToEndTime() {
		//获取当前日期
		LocalDate nowDate = LocalDate.now();
		//设置当天的结束时间
		LocalDateTime endTime = LocalDateTime.of(nowDate, LocalTime.MAX);
		//将时间进行格式化
//		return endTime.format(dtf);
		return endTime;
	}
	//获取昨天初始时间
	public static LocalDateTime getYesBeginTime() {
		//获取当前日期
		LocalDate nowDate = LocalDate.now();
		//设置零点
		LocalDateTime beginTime = LocalDateTime.of(nowDate.minusDays(1), LocalTime.MIN);
		//将时间进行格式化
//		return beginTime.format(dtf);
		return beginTime;
	}
	//获取昨天结束时间
	public static LocalDateTime getYesEndTime() {
		//获取当前日期
		LocalDate nowDate = LocalDate.now();
		//设置当天的结束时间
		LocalDateTime endTime = LocalDateTime.of(nowDate.minusDays(1), LocalTime.MAX);
		//将时间进行格式化
//		return endTime.format(dtf);
		return endTime;
	}
	//获取当月第一天
	public static LocalDateTime getMonthBeginTime() {
		//获取当前日期
		LocalDate nowDate = LocalDate.now();
		//设置零点
		//本月的第一天
		LocalDate sTime = LocalDate.of(nowDate.getYear(),nowDate.getMonth(),1);

		LocalDateTime beginTime = LocalDateTime.of(sTime, LocalTime.MIN);
		//将时间进行格式化
//		return beginTime.format(dtf);
		return beginTime;
	}
	//获取当月最后一天
	public static LocalDateTime getMonthEndTime() {
		//获取当前日期
		LocalDate nowDate = LocalDate.now();
		//本月的第一天
		LocalDate firstday = LocalDate.of(nowDate.getYear(),nowDate.getMonth(),1);
		//本月的最后一天
		LocalDate eTime =nowDate.with(TemporalAdjusters.lastDayOfMonth());

		LocalDateTime endTime = LocalDateTime.of(eTime, LocalTime.MAX);
//		return endTime.format(dtf);
		return endTime;
	}
	public static String getTheLastTime(String dateStr){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDate beginDateTime = LocalDate.parse(dateStr, dtf);
		LocalDateTime endTime = LocalDateTime.of(beginDateTime, LocalTime.MAX);
		return endTime.format(dtf);
	}
	public static LocalDateTime StringToLocalDateTime(String dateStr){
		LocalDateTime ldt = LocalDateTime.parse(dateStr,dtf);
		return ldt;
	}

	public static String LocalDateTimeToString(LocalDateTime date){
		String localTime = dtf.format(date);
		return localTime;
	}
}
