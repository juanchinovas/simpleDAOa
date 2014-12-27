package org.dao.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Object that represent a table from current data base defined by the user in a XML file
 * located in assets android directory.
 * 
 * @author Juanchi Novas
 * @version 1.0
 * @date 2014/09/18
 * 
 * */

public class Table {
	private List<Field> fields;
	private String name;
	private String referenceClass;

	public Table() {
		fields = new ArrayList<Field>();
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public void addField(Field field) {
		this.fields.add(field);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReferenceClass() {
		return referenceClass;
	}

	public void setReferenceClass(String referenceClass) {
		this.referenceClass = referenceClass == null ? "" : referenceClass;
	}
}
