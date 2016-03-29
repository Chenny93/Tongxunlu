package com.android.tongxunlu.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {
	
	public static String parse (long date) {
		Calendar now = Calendar.getInstance();
		
		Calendar other = Calendar.getInstance();
		other.setTimeInMillis(date);
		
		if(now.get(Calendar.YEAR) == other.get(Calendar.YEAR) &&
				now.get(Calendar.DAY_OF_YEAR) == other.get(Calendar.DAY_OF_YEAR)) {
			return new SimpleDateFormat("HH:mm").format(other.getTime());
		}
		
		int day = other.get(Calendar.DAY_OF_WEEK);
		String weekday = "";
		switch (day) {
		case Calendar.MONDAY:
				weekday = "星期一";
			break;
		case Calendar.TUESDAY:
			weekday = "星期二";
		break;
		case Calendar.WEDNESDAY:
			weekday = "星期三";
		break;
		case Calendar.THURSDAY:
			weekday = "星期四";
		break;
		case Calendar.FRIDAY:
			weekday = "星期五";
		break;
		case Calendar.SATURDAY:
			weekday = "星期六";
		break;
		case Calendar.SUNDAY:
			weekday = "星期日";
		break;
		}
		return weekday;
	}
}
