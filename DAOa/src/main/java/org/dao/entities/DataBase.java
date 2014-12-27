package org.dao.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Object that represent a current data base defined by the user in a XML file
 * located in assets android directory.
 * 
 * @author Juanchi Novas
 * @version 1.0
 * @date 2014/09/18
 * 
 * */

public class DataBase {
	private String name;
	private int version = 1;
	private List<Table> tables;

	public DataBase() {
		tables = new ArrayList<Table>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<Table> getTables() {
		return tables;
	}

	public void setTables(List<Table> tables) {
		this.tables = tables;
	}

	public void addTable(Table table) {
		this.tables.add(table);
	}

}
