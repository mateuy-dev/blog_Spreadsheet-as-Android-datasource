package com.mateuyabar.android.spreadsheetasdatasource.sample;

/**
 * Cooking Recipe Model.
 * It includes a attributes for the different types supported.
 */
public class Recipe {
    String id;
    String name;
    Integer intTest;
    Double doubleTest;
    Long longTest;
    Boolean booleanTest;

    public Recipe() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIntTest() {
        return intTest;
    }

    public void setIntTest(Integer intTest) {
        this.intTest = intTest;
    }

    public Double getDoubleTest() {
        return doubleTest;
    }

    public void setDoubleTest(Double doubleTest) {
        this.doubleTest = doubleTest;
    }

    public Long getLongTest() {
        return longTest;
    }

    public void setLongTest(Long longTest) {
        this.longTest = longTest;
    }

    public Boolean getBooleanTest() {
        return booleanTest;
    }

    public void setBooleanTest(Boolean booleanTest) {
        this.booleanTest = booleanTest;
    }
}
