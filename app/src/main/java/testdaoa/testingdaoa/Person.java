package testdaoa.testingdaoa;

import java.util.Calendar;

/**
 * Created by juanchi on 12/23/14.
 */
public class Person {
    private int _id;
    private String name;
    private String lastName;
    private Calendar date;

    public Person(int _id, String name, String lastName) {
        this._id = _id;
        this.name = name;
        this.lastName = lastName;
    }



    public Person(int _id, String name, String lastName, Calendar date) {
        this._id = _id;
        this.name = name;
        this.lastName = lastName;
        this.date = date;
    }



    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Calendar getDate() {
        return date;
    }


    public void setDate(Calendar date) {
        this.date = date;
    }
}
