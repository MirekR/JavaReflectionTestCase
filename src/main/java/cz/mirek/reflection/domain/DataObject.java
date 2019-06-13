package cz.mirek.reflection.domain;

public class DataObject extends DefaultOverrides{
    protected String firstName;
    protected String lastName;
    protected int age;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public DataObject setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public DataObject setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public DataObject setAge(int age) {
        this.age = age;
        return this;
    }
}
