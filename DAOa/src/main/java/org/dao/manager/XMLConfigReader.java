package org.dao.manager;

import java.io.IOException;
import java.io.InputStream;

import org.dao.entities.DataBase;
import org.dao.entities.Field;
import org.dao.entities.Table;
import org.dao.entities.Tag;
import org.dao.manager.util.Util;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Class for read the XML file located in assets android directory. 
 * This class using a Android provide class XmlPullParserFactory
 * 
 * @see XmlPullParserFactory
 * @see DataBase
 * @see Table
 * @see Field
 * 
 * @author Juanchi Novas
 * @version 1.0
 * @date 2014/09/22
 * 
 * */

public class XMLConfigReader {
	
	/*initialize variables*/
	private Context context;
	private DataBase dataBase;
	private boolean dbExists;

	public XMLConfigReader(Context ctx) throws Exception { // constructor
		this.context = ctx;
		init();
	}
	
	/**
	 * Start reading the dao.xml file from assets directory
	 * @throws XmlPullParserException 
	 * @throws IOException 
	 * 
	 * */
	private void init() throws XmlPullParserException, IOException {
		
		XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
		XmlPullParser parser = xmlPullParserFactory.newPullParser();
		
		InputStream inputStream = context.getApplicationContext().getAssets().open("dao.xml");
		parser.setInput(inputStream, "UTF-8");
		read(parser);
	}
	
	/**
	 * Read system database info from dao.xml file. 
	 * Throws IOException if fail reading a dao.xml file.
	 * 
	 * @param parser a Android class XmlPullParser
	 * */
	private void read(XmlPullParser parser) throws IOException,
			XmlPullParserException {
		int eventType = parser.getEventType();
		String tagName = "";
		Table table = null;
		Field field = null;

		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {

			case XmlPullParser.START_TAG:
				tagName = parser.getName(); // current tag name

				System.out.println("Start Tag: " + tagName);
				if (tagName.equalsIgnoreCase(Tag.DB.toString())) { // db tag
					dataBase = new DataBase();
					dataBase.setVersion(Integer.valueOf(parser.getAttributeValue(null, "version")));
					dataBase.setName(Util.getInstance().formatDB(parser.getAttributeValue(null, "name")));
					
					/**
					 * Exists databases on this current context and the user define database is there too then
					 * test the current version of this database of context. The propose is not let create tables and fields objects 
					 * when the version are equals
					 * */
					this.dbExists = this.context.databaseList().length > 0 && isCurrentDBExists(dataBase.getName());
					if (this.dbExists) { //dao.xml database exists 
						SQLiteDatabase database = SQLiteDatabase.openDatabase(this.context.getDatabasePath(dataBase.getName()).getPath(), 
								null,SQLiteDatabase.OPEN_READONLY);
						this.dbExists = database.getVersion() == dataBase.getVersion(); // set dbExists value if version are equals
						database.releaseReference(); 
					}

				} else if (tagName.equalsIgnoreCase(Tag.TABLE.toString()) && !this.dbExists) {
					table = new Table();
					table.setName(parser.getAttributeValue(null, "name"));
					table.setReferenceClass(parser.getAttributeValue(null,"class"));
					
				} else if (tagName.equalsIgnoreCase(Tag.FIELD.toString()) && !this.dbExists) {
						field = new Field(parser.getText(),
								parser.getAttributeValue(null, "type"),
								Boolean.valueOf(parser.getAttributeValue(null,"nullable")),
								Boolean.valueOf(parser.getAttributeValue(null, "auto")),
								Boolean.valueOf(parser.getAttributeValue(null,"key")), 
								parser.getAttributeValue(null,"default"));
				}
				break;
			case XmlPullParser.TEXT:
				if (!this.dbExists && tagName.equalsIgnoreCase(Tag.FIELD.toString())) {
					field.setName(parser.getText());
				}
				break;
			case XmlPullParser.END_TAG:
				tagName = parser.getName();
				System.out.println("End Tag: " + tagName);
				if (tagName.equalsIgnoreCase(Tag.TABLE.toString()) && !this.dbExists) {
					dataBase.addTable(table);
				} else if (tagName.equalsIgnoreCase(Tag.FIELD.toString()) && !this.dbExists) {
					table.addField(field);
					tagName = "";
				}
				break;
			}
			eventType = parser.next();
		}
	}
	
	/**
	 * Test if dao.xml database name exists. 
	 * 
	 * @param dbName
	 * 
	 * @return true/false
	 * */

	private boolean isCurrentDBExists(String dbName) {
		String dbs[] = this.context.databaseList();
		boolean isExists = false;
		for (String db : dbs) {
			if (db.equals(dbName)) {
				isExists = true;
				break;
			}
		}
		return isExists;
	}
	
	public DataBase getDataBase() {
		return dataBase;
	}
}
