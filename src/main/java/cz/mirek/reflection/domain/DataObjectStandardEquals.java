package cz.mirek.reflection.domain;

import java.util.Objects;

public class DataObjectStandardEquals {
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

    public DataObjectStandardEquals setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public DataObjectStandardEquals setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public DataObjectStandardEquals setAge(int age) {
        this.age = age;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataObjectStandardEquals that = (DataObjectStandardEquals) o;
        return age == that.age &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, age);
    }
}
