package cz.mirek.reflection.domain;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.DAYS;

public class DefaultOverrides {
    private static final Cache<Class, Collection<Field>> FIELDS = CacheBuilder.newBuilder()
            .maximumSize(100_000)
            .expireAfterAccess(7, DAYS)
            .build();

    @Override
    public int hashCode() {
        return this.getFields().stream()
                .map(field -> getFieldValue(this, field))
                .filter(Objects::nonNull)
                .mapToInt(data -> 31 * data.hashCode())
                .sum();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null || this.getClass() != that.getClass()) {
            return false;
        }

        Collection<Field> fields = this.getFields();

        for (Field field: fields) { // stream are significantly slower unfortunately for this usage.
            if (!Objects.equals(getFieldValue(this, field), getFieldValue(that, field))) {
                return false;
            }
        }

        return true;
    }

    private Collection<Field> getFields() {
        try {
            return FIELDS.get(this.getClass(), () -> getInheritedFileds(this.getClass()));
        } catch (ExecutionException e) {
            return Collections.emptyList();
        }
    }

    private Object getFieldValue(Object object, Field field) {
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            return null; // null can be returned here as we already know objects are same type so exception here is unlikely
        }
    }

    private static Collection<Field> getInheritedFileds(Class<?> type) {
        ArrayDeque<Field> result = new ArrayDeque<>(20);

        Class<?> clazz = type;
        while (clazz != null && clazz != DefaultOverrides.class) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                result.add(field);
            }
            clazz = clazz.getSuperclass();
        }

        return result;
    }
}
