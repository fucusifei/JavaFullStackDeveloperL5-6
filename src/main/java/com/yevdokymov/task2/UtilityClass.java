package com.yevdokymov.task2;

import com.yevdokymov.task2.anotation.Property;
import com.yevdokymov.task2.exceptions.WrongDateFormatException;
import com.yevdokymov.task2.exceptions.WrongKeyInPropertiesException;
import com.yevdokymov.task2.exceptions.WrongPropertiesFormatException;

import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class UtilityClass {
    public static <T> T loadFromProperties(Class<T> cls, Path propertiesPath) throws Exception {
        T newClass = cls.getDeclaredConstructor().newInstance();
        Field[] fields = cls.getDeclaredFields();
        FileInputStream fileInputStream = new FileInputStream(propertiesPath.toFile());
        Properties property = new Properties();
        property.load(fileInputStream);

        Set<Map.Entry<Object, Object>> properties = property.entrySet();

        checkSameNumberOfIncluding(properties,fields);
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(newClass, getFieldValue(properties, field));
        }

        return newClass;
    }

    private static Object getFieldValue(Set<Map.Entry<Object, Object>> properties, Field field)
            throws WrongKeyInPropertiesException, WrongDateFormatException {
        String fieldName  = field.getName();
        if (field.isAnnotationPresent(Property.class) && field.getAnnotation(Property.class).name().length() > 0) {
            fieldName = field.getAnnotation(Property.class).name();
        }

        checkIncluding(properties,fieldName);
        for (Map.Entry<Object, Object> map : properties) {
            if (fieldName.equals(map.getKey())) {

                var type = field.getType();
                if (String.class.equals(type)) {
                    return String.valueOf(map.getValue());
                }
                else if (int.class.equals(type)) {
                    return Integer.parseInt(String.valueOf(map.getValue()));
                }
                else if (Integer.class.equals(type)) {
                    return Integer.parseInt(String.valueOf(map.getValue()));
                }
                else if (Instant.class.equals(type)){

                        String fieldFormat = field.getAnnotation(Property.class).format();
                        SimpleDateFormat format = new SimpleDateFormat(fieldFormat);
                        Date docDate;
                        try {
                             docDate = format.parse(String.valueOf(map.getValue()));
                        } catch (Exception e) {
                            throw new WrongDateFormatException(fieldFormat);
                        }

                    return docDate.toInstant();
                }
                else {
                    throw new UnsupportedOperationException("invalid data type: " + type);
                }

            }

        }
        return null;
    }
    public static void checkIncluding(Set<Map.Entry<Object, Object>> properties, String fieldName) throws WrongKeyInPropertiesException {
        if(properties.stream().map(x->x.getKey().toString()).noneMatch(fieldName::equals)){
            throw new WrongKeyInPropertiesException(properties.toString());
        }
    }
    public static void checkSameNumberOfIncluding(Set<Map.Entry<Object, Object>> properties, Field[] fields) throws WrongPropertiesFormatException {
        if (properties.size()!=fields.length) {
            throw new WrongPropertiesFormatException(properties.size(),fields.length);
        }
    }







}
