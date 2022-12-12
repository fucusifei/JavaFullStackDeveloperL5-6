package com.yevdokymov.task2.exceptions;

public class WrongDateFormatException extends Exception{
    private String dateFormat= "format = dd-MM-yyyy HH:mm:ss";

    public WrongDateFormatException(String dateFormat) {

        this.dateFormat = dateFormat;
    }

    @Override
    public String toString() {
        return "WrongKeyInPropertiesException{" +
                dateFormat + '\'' +
                '}';
    }
}
