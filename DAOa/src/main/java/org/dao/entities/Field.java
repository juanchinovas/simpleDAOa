package org.dao.entities;

/**
 * Object that represent a column from table of current data base defined by the user in a XML file
 * located in assets android directory.
 * 
 * @author Juanchi Novas
 * @version 1.0
 * @date 2014/09/18
 * 
 * */

public class Field {
	private String name;
	private String type;
	private boolean nullable = true;
	private boolean autoIncrement = false;
	private boolean primaryKey = false;
	private String defaultValue;

	public Field(String name, String type, boolean nullable,
			boolean autoIncrement, boolean primaryKey, String defaultValue) {
		this.name = name;
		this.type = type;
		this.nullable = nullable;
		this.autoIncrement = autoIncrement;
		this.primaryKey = primaryKey;
		this.defaultValue = defaultValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
}
