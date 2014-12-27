package org.dao.entities;

/**
 * Enumerable that represent a tag of XML file
 * located in assets android directory.
 * 
 * @author Juanchi Novas
 * @version 1.0
 * @date 2014/09/18
 * 
 * */
public enum Tag {
	DB("db"), TABLE("table"), FIELD("field");

	Tag(String tag) {
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String tag = "";
		switch (this) {
		case DB:
			tag = "db";
			break;
		case TABLE:
			tag = "table";
			break;
		case FIELD:
			tag = "field";
			break;
		}
		return tag;
	}
}
