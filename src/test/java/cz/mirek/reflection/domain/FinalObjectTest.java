package cz.mirek.reflection.domain;

import org.junit.Test;

import java.lang.reflect.Field;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FinalObjectTest {

    @Test
    public void shouldSetNameToFinalObject() throws NoSuchFieldException, IllegalAccessException {
        String firstName = "Mirek";
        String lastName = "Rousal";

        final FinalObject finalObject = new FinalObject.Builder()
                .withName(firstName)
                .build();

        assertThat(finalObject.getName(), is(firstName));

        setNewNameViaReflection(finalObject, lastName);

        assertThat(finalObject.getName(), is(lastName));
    }

    private void setNewNameViaReflection(FinalObject finalObject, String name) throws NoSuchFieldException, IllegalAccessException {
        Field nameField = finalObject.getClass().getDeclaredField("name");
        nameField.setAccessible(true);

        nameField.set(finalObject, name);
    }
}