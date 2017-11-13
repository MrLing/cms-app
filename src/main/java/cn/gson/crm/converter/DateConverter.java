package cn.gson.crm.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * 日期转换器
 * 
 * @author ____′↘夏悸
 *
 */
@Component
public class DateConverter implements Converter<String, Date> {

	String[] patterns = { "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH", "yyyy-MM-dd", "HH:mm" };
	SimpleDateFormat sdf = new SimpleDateFormat();

	@Override
	public Date convert(String source) {
		Date date = null;
		for (String pattern : patterns) {
			try {
				sdf.applyPattern(pattern);
				date = sdf.parse(source);
				break;
			} catch (Exception e) {
			}
		}
		if (date == null) {
			throw new RuntimeException("日期格式转换异常");
		}
		return date;
	}

}
