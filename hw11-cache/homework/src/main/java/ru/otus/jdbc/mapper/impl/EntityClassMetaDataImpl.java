package ru.otus.jdbc.mapper.impl;

import ru.otus.core.annotation.Id;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> tClass;

    public EntityClassMetaDataImpl(Class<T> tClass){
        this.tClass = tClass;
    }
    @Override
    public String getName() {
        return tClass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return tClass.getConstructor();
        }catch (NoSuchMethodException e){
            throw new DataTemplateException(e);
        }
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(tClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class)).findFirst().orElseThrow();
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.stream(tClass.getDeclaredFields()).toList();
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(tClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class)).toList();
    }
}
