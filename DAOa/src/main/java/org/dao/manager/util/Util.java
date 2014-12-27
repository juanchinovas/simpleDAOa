package org.dao.manager.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.dao.entities.Field;

/**
 * @see Field
 * 
 * @author Juanchi Novas
 * @version 1.0
 * @date 2014/10/22
 * @modify 2014/11/08
 * 
 * */

public class Util {
	private static Util instance;

	private Map<String, String> map = new TreeMap<String, String>();

	public static Util getInstance() {
		return (instance != null ? instance : (instance = new Util()));
	}

	private Util() {
		/**
		 * Data types for SQLite
		 * */
		map.put("TEXT", "TEXT");
		map.put("CHAR", "TEXT");
		map.put("VARCHAR", "TEXT");
		map.put("STRING", "TEXT");
		map.put("DATE", "TEXT");
		map.put("NUMERIC", "NUMERIC");
		map.put("NUM", "NUMERIC");
		map.put("INTEGER", "INTEGER");
		map.put("INT", "INTEGER");
		map.put("REAL", "REAL");
		map.put("DECIMAL", "REAL");
		map.put("DOUBLE", "REAL");
		map.put("FLOAT", "REAL");
	}
	
	/**
	 * Format all info form dao.xml according to SQLite for create tables
	 * 
	 * @param tableField Field object that represent a table column
	 * 
	 * @return StringBuffer object with a ready query
	 * */
	public StringBuffer transformTableField(Field tableField) {
		StringBuffer sqlTranslated = new StringBuffer();
		if (tableField != null) {
			sqlTranslated.append(tableField.getName());
			sqlTranslated.append(" " + map.get(tableField.getType()));
			sqlTranslated.append((tableField.isPrimaryKey() ? " PRIMARY KEY" : ""));
			sqlTranslated.append((tableField.isAutoIncrement() ? " AUTOINCREMENT" : ""));
			sqlTranslated.append((tableField.getDefaultValue() != null ? testFieldAuto(tableField) : ""));
			sqlTranslated.append((tableField.isNullable() ? "" : " NOT NULL ON CONFLICT FAIL"));
		}
		return sqlTranslated;
	}

	private String testFieldAuto(Field field) {
		String auto = "";
		// AUTOINCREMENT BY 1
		// DEFAULT CURRENT_TIMESTAMP YYYY-MM-DD HH:MM:SS
		if (field.getType().equalsIgnoreCase("DATE")) {
			auto = " DEFAULT (DATETIME('now','localtime'))";
		} else {
			auto = " DEFAULT " + (map.get(field.getType()).matches("INTEGER|REAL|NUMERIC") ? field .getDefaultValue() 
					: "'" + field.getDefaultValue() + "'");
		}
		return auto;
	}

	public String formatDB(String dbName) {
		if (dbName == null)return "";
		return dbName.contains(".db") ? dbName : dbName + ".db";
	}
	
	/**
	 * Cast the String value formatted like date in Calendar class
	 * 
	 * @param value String formatted YYYY-MM-DD HH:MM:SS
	 * @param type User class parameter definition class type
	 * 
	 * @return Object wrapper a Calendar or String class
	 * 
	public Object castCalendarType(String value, Class<?> type) {
		
		return value;
	}*/
	
	/**
	 * Cast the String value for specific type Integer o Double
	 * 
	 * @param value Integer or Double value
	 * @param type User class parameter definition class type
	 * 
	 * @return Object wrapper of Integer or Double class
	 * */
	public Object castValue(String value, Class<?> type) {
		Object object = value;
		if (type.equals(Integer.TYPE)) {
			object = value == null ? 0 : Integer.parseInt(value);
		} else if (type.equals(Double.TYPE)) {
			object = value == null ? 0 : Double.parseDouble(value);
		} else if (type.equals(Float.TYPE)) {
			object = value == null ? 0 : Float.parseFloat(value);
		} else if (type.equals(Long.TYPE)) {
			object = value == null ? 0 : Long.parseLong(value);
		} else if (type.equals(Short.TYPE)) {
			object = value == null ? 0 : Short.parseShort(value);
		} else if (type.equals(Byte.TYPE)) {
			object = value == null ? 0 : Byte.parseByte(value);
		} else if (type.equals(Boolean.TYPE)) {
			object = Boolean.parseBoolean(value);
		} else if (isDate(value) && type.isInstance(Calendar.getInstance())) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatString(value), Locale.getDefault());
			Calendar calendar = Calendar.getInstance();
			try {
				calendar.setTime(dateFormat.parse(value));
				return calendar;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		} 
		return object;
	}
	
	private boolean isDate(String date) {
		return date != null && (date.matches("\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}") //YYYY-MM-dd HH:mm:ss
				|| date.matches("\\d{4}-\\d{2}-\\d{2}")); //YYYY-MM-dd
	}
	
	public String dateFormatString(String date) {
		String format = "yyyy-MM-dd HH:mm:ss";
		if(date.matches("\\d{4}-\\d{2}-\\d{2}")) {
			format = "yyyy-MM-dd";
		}
		return format;
	}
}
