package com.assess.entities;
public class Result {
    private String resultType;
    private String value;

    // Constructors, getters, and setters

    public Result() {}

    public Result(String resultType, String value) {
        this.resultType = resultType;
        this.value = value;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
