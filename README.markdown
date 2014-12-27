simpleDAOa
==========

A Simple Data Access Object for Android to control your android app databases.
==========
How to use simpleDAOa?

    1. Create a xml file in assets directory named dao.xml. That file have to have the following structure
    ~~~~~ xml
    <?xml version="1.0" encoding="UTF-8"?>
    <db name='db_name' version='1'>
        <table name='tbl_name1' class='testdaoa.testingdaoa.Person'>
            <field type='INTEGER' nullable='false' key='true' auto='true'>_id</field>
            <field type='TEXT'>name</field>
            <field type='TEXT' default='test'>dummy_column</field>
            <field type='DATE' nullable='false' default='DATE'>entry_date</field>
        </table>
        <table name='tbl_name2' class='pck.Vehicle'>
            <field type='INTEGER' nullable='false'>lincense_plate</field>
            <field type='TEXT' nullable='true'>model</field>
            <field type='DATE' nullable='false' default='DATE'>entry_date</field>
            <field type='INTEGER' nullable='false'>owner</field>
        </table>
    </db>
    ~~~~~
    2. Add the DAOa project as library in your Android project
    3. Create a Instance of a singleton class `<b>DBManager</b>`. and you can access all methods in this class.

==========

~~~~~ java
# Initializes a DBManager
private DBManager dbManager = DBManager.getInstance(this); //(this) a activity instance

# Read data from database

Object objct = dbManager.selectFrom("tbl_name1",null); // all rows
if (objct instanceof List) {
    List<Person> ps = (List<Person>)objct;
} else if (objct != null) {
    Person person  = (((Person)objct).getName());
}
~~~~~
