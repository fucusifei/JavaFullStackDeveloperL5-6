package com.yevdokymov.task2.exceptions;

public class WrongKeyInPropertiesException extends Exception {

        private final String keyProperty;

        public WrongKeyInPropertiesException(String keyProperty) {

            this.keyProperty = keyProperty;
        }

        @Override
        public String toString() {
            return "WrongKeyInPropertiesException{" +
                    "keyProperty='" + keyProperty + '\'' +
                    '}';
        }


}
