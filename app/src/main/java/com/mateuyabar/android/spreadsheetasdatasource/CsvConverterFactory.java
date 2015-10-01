package com.mateuyabar.android.spreadsheetasdatasource;

import com.squareup.okhttp.ResponseBody;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * Factory to use the CsvConverter
 */
public class CsvConverterFactory extends Converter.Factory{
    @Override
    public Converter<ResponseBody, ?> fromResponseBody(Type type, Annotation[] annotations) {
        return new CsvConverter(type);
    }
}
