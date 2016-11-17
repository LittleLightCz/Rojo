package com.svetylkovo.rojo.matcher;

import com.svetylkovo.rojo.annotations.Group;

import java.lang.reflect.Method;
import java.util.function.Function;

public class BeanField<T> {

    private Method setter;
    private Group group;
    private Function<String, T> conversion;
    private Class<T> fieldType;

    public BeanField(Method setter, Group group, Function<String, T> conversion, Class<T> fieldType) {
        this.setter = setter;
        this.group = group;
        this.conversion = conversion;
        this.fieldType = fieldType;
    }

    public Method getSetter() {
        return setter;
    }

    public void setSetter(Method setter) {
        this.setter = setter;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Function<String, T> getConversion() {
        return conversion;
    }

    public void setConversion(Function<String, T> conversion) {
        this.conversion = conversion;
    }

    public Class<T> getFieldType() {
        return fieldType;
    }

    public void setFieldType(Class<T> fieldType) {
        this.fieldType = fieldType;
    }
}
