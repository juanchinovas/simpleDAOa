package org.dao.manager;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.dao.entities.DataBase;
import org.dao.manager.util.Util;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

/**
 * This class offer a interface to the user for access to data base and read XML file.
 * 
 * @see XmlPullParserFactory
 * @see DataBase
 * @see Util
 * @see DBHelper
 * 
 * @author Juanchi Novas
 * @version 1.0
 * @date 2014/10/22
 * @modify 2014/12/09
 * 
 * */

public class DBManager {

	private static DBManager instance;
	private DBHelper dbHelper;
	private XMLConfigReader configReader;
	private Util util;

	/**
	 * Singleton method
	 * 
	 * @param context The current activity context
	 * 
	 * @return oneself DBManager instance
	 **/
	public static DBManager getInstance(Context context) {
		return instance != null ? instance : (instance = new DBManager(context));
	}

	private DBManager(Context context) {
		util = Util.getInstance();
		try {
			configReader = new XMLConfigReader(context);
			DataBase db = configReader.getDataBase();
			dbHelper = new DBHelper(context, db);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * add a specific data for giving table
	 * 
	 * @param table is the table name of data base for read data
	 * 
	 * @param values data key/value for add to table.
	 * 		  Format must be column=value
	 * 
	 * @return Long type that represent the number of rows affected
	 * */
	public long insert(String table, ContentValues values) {
		return dbHelper.getWritableDatabase().insert(table, null, values);
	}
	
	/**
	 * delete a specific row or rows for giving table
	 * 
	 * @param table is the table name of data base for read data
	 * 
	 * @param condition is the condition for filter the data.
	 * 		  Format must be column=value for one and column1=value1 and|or column1=value1 for more
	 * 
	 * @return Long type that represent the number of rows affected
	 * */
	public long deleteFrom(String table, String condition) {
		return dbHelper.getWritableDatabase().delete(table, condition, null);
	}

	/**
	 * Select data from specific table. For use this method you have to declare a _class table attribute in the dao.xml
	 * Example <table name='table_name' class='pkg.ClassName'>....
	 * The class must have a constructor with cursor columns size parameters and the same type, the first match will be taken
	 * 
	 * @param table is the table name of data base for read data
	 * 
	 * @param where is the condition for search and filter the data.
	 * 		  Format must be column=value for one and column1=value1 and column1=value1 for more
	 * 
	 * @param columns is a array of string with the columns of the table
	 * 
	 * @return This method will return a List of Object type of Class on _class column. If found more the one rows. 
	 * 			Can return one object. If the result is just one row or null if data is no
	 * 			found
	 **/
	//@SuppressWarnings("unchecked")
	public Object selectFrom(String table,String where, String... columns ) {
		columns = testPhantomColumn(columns); // add _class column
		
		Cursor cursor = dbHelper.getReadableDatabase().query(table, columns, where, null, null, null, null);
		/*Initialize variables*/
		Object[] data = new Object[cursor.getColumnCount() -1 ]; // columns
		Object type = null;
		List<Object> types = null;
		Class<?>[] _classParameters = null;
		Class<Object> _class = null;
		int cursorTotalRow = cursor.getCount();
		
		if (cursorTotalRow > 1) { // 2+ rows
			types = new ArrayList<Object>(cursorTotalRow); 
			type = types; // for return
		}
		
		for (int p = 0; p < cursorTotalRow; p++) {
			cursor.moveToPosition(p);
			try {
				if (p == 0) {
					String pkgClass = cursor.getString(cursor.getColumnIndex("_class")); //pkg.Class
					
					_class = (Class<Object>) Class.forName(pkgClass);
					Constructor<Object>[] constructors = (Constructor<Object>[]) _class.getConstructors(); // all Class Constructors
					for (Constructor<Object> constructor : constructors) {
                        _classParameters = constructor.getParameterTypes();
						if (_classParameters.length == data.length) { //The class must have a constructor with cursor columns size parameters
							break;
						}
					}
					type = loadData(_classParameters, _class, data, cursor);
					
				} else {
					if (p == 1) types.add(type); type = types; // for return
					types.add(loadData(_classParameters, _class, data, cursor));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println(e.getMessage());
			}
		}
		cursor.close();
		return type;
	}
	
	/**
	 * Load row columns data in current position. 
	 * Throws InstantiationException, IllegalAccessException
	 * 
	 * @param _classParameters of _class type constructor parameters
	 * @param _class of _class type
	 * @param data of _class type constructor parameters values
	 * @param cursor query results
	 * 
	 * @return Instance of _class data
	 * */
	private Object loadData(Class<?>[] _classParameters, Class<Object> _class, Object[] data, Cursor cursor) throws InstantiationException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
		for (int i= 0 ; i < _classParameters.length; i++) {
			String value = cursor.getString(i);
			data[i] = util.castValue(value,  _classParameters[i]);
			/*if (data[i] instanceof String) {
				data[i] = util.castCalendarType(value, _classParamenters[i]);
			}*/
		}
		
		return _class.getConstructor(_classParameters).newInstance(data); // New instance of _class type
	}

	/**
	 * Execute user custom query. The developer have the responsible to close the cursor object
	 * 
	 * @param query Represent the user query
	 * 
	 * @return the return is a Android Cursor or null
	 **/

	public Cursor query(String query) {
		Pattern pattern = Pattern.compile("^(SELECT)", Pattern.CASE_INSENSITIVE);
		if (pattern.matcher(query).find()) {
			return dbHelper.getReadableDatabase().rawQuery(query, null);
		} else {
			dbHelper.getWritableDatabase().execSQL(query);
		}
		return null;
	}
	
	
	/**
	 * Add _class column to columns array if not found
	 * 
	 * @param columns user define select columns
	 * 
	 * @return String[] all columns plus _class
	 * 
	 * */
	private String[] testPhantomColumn(String[] columns) {
		if (columns == null || columns.length == 0)return null;
		String phantomColumn = "_class";
		String[] columnsNew = new String[columns.length + 1];
		int counter = 0;
		for (String col : columns) {
			if (col.equalsIgnoreCase(phantomColumn)) {
				return columns;
			} else {
				columnsNew[counter++] = col;
				if (counter == columns.length)columnsNew[counter] = phantomColumn;
			}
		}
		return columnsNew;
	}
}
