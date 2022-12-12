package com.yevdokymov.task2.exceptions;

public class WrongPropertiesFormatException extends Exception{
    private final int numInProperties;
    private final int numOfFields;


    public WrongPropertiesFormatException(int numInProperties, int numOfFields) {
        this.numInProperties = numInProperties;
        this.numOfFields = numOfFields;
    }

    @Override
    public String toString() {
        return "WrongPropertiesFormatException{" +
                "In Properties = " + numInProperties + " properties." +  " In Object = " + numOfFields + " fields"+
                '}';
    }

}
