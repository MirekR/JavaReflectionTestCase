package cz.mirek.reflection.domain;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DataObjectTest {
    @Test
    public void shouldCreateObject() {
        final String firstNameLocal = "Mirek";
        final String lastNameLocal = "Rousal";
        final int ageLocal = 33;

        DataObject object = new DataObject(){{
            age = ageLocal;
            firstName = firstNameLocal;
            lastName = lastNameLocal;
        }};

        assertThat(object.getFirstName(), is(firstNameLocal));
        assertThat(object.getLastName(), is(lastNameLocal));
        assertThat(object.getAge(), is(ageLocal));
    }
}