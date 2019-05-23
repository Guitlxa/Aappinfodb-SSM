package cn.app.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.jdt.internal.compiler.tool.EclipseCompiler;
import org.springframework.core.convert.converter.Converter;

/**
 * 将字符串转换为指定格式的时间对象Date的自定义转换器
 * @author BaBa
 *
 */
public class StringToDateConverter implements Converter<String, Date>{
	
	private String datePattern;
	
	public StringToDateConverter(String datePattern) {
		System.out.println("StringToDateConverter convert:"+datePattern);
		this.datePattern=datePattern;
	}
	
	@Override
	public Date convert(String s) {
		Date date=null;
		try {
			date=new SimpleDateFormat(datePattern).parse(s);
			System.out.println("StringToDateConverter convert date:"+date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
}
