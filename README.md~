simpleDAOa
==========

A Simple Data Access Object for Android to control your android app databases.
==========
How to use simpleDAOa?

    1. Create a xml file in assets directory named dao.xml. That file have to have the following structure
        &lt;db name='db_name' version='1'&gt;
            &lt;table name='tbl_name1' class='testdaoa.testingdaoa.Person'&gt;
                &lt;field type='INTEGER' nullable='false' key='true' auto='true'&gt;_id&lt;/field&gt;
                &lt;field type='TEXT'&gt;name&lt;/field&gt;
                &lt;field type='TEXT' default='test'&gt;dummy_column&lt;/field&gt;
                &lt;field type='DATE' nullable='false' default='DATE'&gt;entry_date&lt;/field&gt;
            &lt;/table&gt;
            &lt;table name='tbl_name2' class='pck.Vehicle'&gt;
                &lt;field type='INTEGER' nullable='false'&gt;lincense_plate&lt;/field&gt;
                &lt;field type='TEXT' nullable='true'&gt;model&lt;/field&gt;
                &lt;field type='DATE' nullable='false' default='DATE'&gt;entry_date&lt;/field&gt;
                &lt;field type='INTEGER' nullable='false'&gt;owner&lt;/field&gt;
            &lt;/table&gt;
        &lt;/db&gt;
    2. Add the DAOa project as library in your Android project
    3. Create a Instance of a singleton class <b>DBManager</b>. and you can access all methods in this class.
