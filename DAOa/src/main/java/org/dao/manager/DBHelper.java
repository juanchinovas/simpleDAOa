package org.dao.manager;

import java.util.List;

import org.dao.entities.DataBase;
import org.dao.entities.Field;
import org.dao.entities.Table;
import org.dao.manager.util.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Juanchi Novas
 * @version 1.0
 * @date 2014/09/18
 * 
 * */

public class DBHelper extends SQLiteOpenHelper {
	private DataBase dBase;
	
	
	public DBHelper(Context context, DataBase dataBase) {
		super(context, dataBase.getName(), null, dataBase.getVersion());
		// TODO Auto-generated constructor stub
		this.dBase = dataBase;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		List<Table> tables = dBase.getTables();
		if (tables != null) {
			for (Table table : tables) {
				List<Field> fields = table.getFields();
				String tableFields = "";
				for (int i = 0; i < fields.size(); i++) {
					tableFields += (i == 0 ? "" : ", ")
							+ Util.getInstance().transformTableField(fields.get(i));
					System.out.println("Field:" + fields.get(i).getName());
				}
				if (table.getReferenceClass() != null
						&& !table.getReferenceClass().equals("")) {
					tableFields += (fields.size() == 0 ? "" : ", ")
							+ " _class TEXT DEFAULT '"
							+ table.getReferenceClass() + "'";
				}
				
				db.execSQL("CREATE TABLE " + table.getName() + " (" + tableFields + ");");
				System.out.println("table:" + table.getName());
				System.out.println("fields:" + tableFields);
			}
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		List<Table> tables = dBase.getTables();
		if (tables != null) {
			for (Table table : tables) {
				db.execSQL("DROP TABLE " + table.getName());
			}
			onCreate(db);
		}
	}

}
