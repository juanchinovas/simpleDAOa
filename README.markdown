simpleDAOa
==========

A Simple Data Access Object for Android to control your android app databases.
------------------------------------------------------------------------------
## How to use simpleDAOa?

    1. Create a xml file in assets directory named dao.xml. 
        That file have to have the following structure
        <pre>
            <code>
                &lt;?xml version="1.0" encoding="UTF-8"?&gt;
                &lt;db name='db_name' version='1'&gt;
                  &lt;table name='tbl_name1' class='testdaoa.testingdaoa.Person'&gt;
                      &lt;field type='INTEGER' nullable='false' key='true' auto='true'&gt;_id&lt;/field&gt;
                      &lt;field type='TEXT'&gt;name&lt;/field&gt;
                      &lt;field type='TEXT' default='test'&gt;dummy_column&lt;/field&gt;
                      &lt;field type='DATE' nullable='false' default='DATE'&gt;entry_date&lt;/field&gt;
                  &lt;/table&gt;
                  &lt;table name='tbl_name2' class='pck.Class'&gt;
                      &lt;field type='INTEGER' nullable='false'&gt;lincense_plate&lt;/field&gt;
                      &lt;field type='TEXT' nullable='true'&gt;model&lt;/field&gt;
                      &lt;field type='DATE' nullable='false' default='DATE'&gt;entry_date&lt;/field&gt;
                      &lt;field type='INTEGER' nullable='false'&gt;owner&lt;/field&gt;
                  &lt;/table&gt;
                &lt;/db&gt;
            </code>
        </pre>
    2. Add the DAOa project as library in your Android project
    3. Create a Instance of a singleton class **DBManager**. 
        And you can access all methods in this class.

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
