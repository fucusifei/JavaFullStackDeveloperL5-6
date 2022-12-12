package com.yevdokymov.task2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.nio.file.Path;

public class ExemplarTest {

    Exemplar rawClass = new Exemplar();

    @Test
    void addDataSuccessful() {
        try {
            rawClass = UtilityClass.loadFromProperties(rawClass.getClass(), Path.of("src\\main\\resources\\res.properties"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(rawClass.getStringProperty(), "value1");
        Assertions.assertEquals(rawClass.getMyNumber(), 10);
        Assertions.assertEquals(rawClass.getTimeProperty().toString(), "2022-09-02T20:37:05Z");

    }


}
