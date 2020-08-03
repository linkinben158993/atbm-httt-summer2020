package com.atbm.quanlybenhvien.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateFormatter extends AbstractFormatter {

	private static final long serialVersionUID = 1L;
	private static final String format_String = "dd-MM-yy";
	private SimpleDateFormat dateFormat = new SimpleDateFormat(format_String);

	@Override
	public Object stringToValue(String text) throws ParseException {
		return dateFormat.parseObject(text);
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		if (value != null) {
			Calendar cal = (Calendar) value;
			return dateFormat.format(cal.getTime());
		}
		return "";
	}

}
