package com.mateuyabar.android.spreadsheetasdatasource;


import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import retrofit.Converter;

/**
 * Retrofit converter for CSV files. The first line of csv containt the name of the attributes to be filled.
 *
 * Limitations:
 * - It only works to produce List<T> results.
 * - It can only be used for server responses
 *
 */
public class CsvConverter<T> implements Converter<ResponseBody, List<T>> {
    Type type;
    Class<T> itemClass;

    public CsvConverter(Type type) {
        this.type = type;
        itemClass = (Class<T>) ((ParameterizedType)type).getActualTypeArguments()[0];
    }

    @Override
    public List<T> convert(ResponseBody body) throws IOException {
        CSVReader csvreader = new CSVReader(body.charStream());
        String [] nextLine;

        List<T> result = new ArrayList<>();
        String[] titles = csvreader.readNext();
        List<Field> fields = processFirstLine(titles);
        while ((nextLine = csvreader.readNext()) != null) {
            T model = createModel(nextLine, fields);
            result.add(model);
        }
        return result;
    }

    /**
     * Creates a model from the line and the fields
     */
    private T createModel(String[] nextLine, List<Field> fields) throws IOException {
        try {
            T model = itemClass.newInstance();
            for(int i = 0; i<nextLine.length; ++i){
                Field attribute = fields.get(i);
                setValue(model, attribute, nextLine[i]);

            }
            return model;
        } catch (InstantiationException e) {
            throw new IOException(e);
        } catch (IllegalAccessException e) {
            throw new IOException(e);
        }
    }

    /**
     * Sets the value to the field of the model. It will do necessary conversions from String.
     * @param model
     * @param field
     * @param value
     * @throws IllegalAccessException
     */
    private void setValue(T model, Field field, String value) throws IllegalAccessException {
        field.setAccessible(true);
        Class<?> attributeClass = field.getType();
        field.set(model, getValue(value, attributeClass));
    }

    /**
     * Converts a String to a desired class, using default conversions.
     * @param value
     * @param desiredClass
     * @param <K>
     * @return
     */
    private <K> K getValue(String value, Class<K> desiredClass){
        if(desiredClass.isAssignableFrom(String.class)){
            return (K) value;
        } else if(isInt(desiredClass)){
            return (K) Integer.valueOf(value);
        } else  if(isBoolean(desiredClass)) {
            return (K) Boolean.valueOf(value);
        } else  if(isDouble(desiredClass)) {
            return (K) Double.valueOf(value);
        } else  if(isLong(desiredClass)) {
            return (K) Long.valueOf(value);
        } else if(desiredClass.isAssignableFrom(Calendar.class)){
            throw new UnsupportedOperationException();//Todo implement
        } else if(desiredClass.isAssignableFrom(Date.class)){
            throw new UnsupportedOperationException();//Todo implement
        } else {
            throw new UnsupportedOperationException();//Todo implement
        }
    }

    /**
     * Reads the first line of the csv and gets the list of fields
     */
    private List<Field> processFirstLine(String[] titles) throws IOException {
        List<Field> fields = new ArrayList<>();
        for(int i = 0; i< titles.length; ++i){
            try {
                fields.add(itemClass.getDeclaredField(titles[i]));
            } catch (NoSuchFieldException e) {
                throw new IOException(e);
            }
        }
        return fields;
    }

    public static boolean isInt(Class<?> fieldClass){
        return Integer.class.isAssignableFrom(fieldClass) || int.class.isAssignableFrom(fieldClass);
    }

    public static boolean isDouble(Class<?> fieldClass){
        return Double.class.isAssignableFrom(fieldClass) || double.class.isAssignableFrom(fieldClass);
    }

    public static boolean isBoolean(Class<?> fieldClass){
        return Boolean.class.isAssignableFrom(fieldClass) || boolean.class.isAssignableFrom(fieldClass);
    }

    public static boolean isLong(Class<?> fieldClass){
        return Long.class.isAssignableFrom(fieldClass) || long.class.isAssignableFrom(fieldClass);
    }

}
