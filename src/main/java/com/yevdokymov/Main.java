package com.yevdokymov;

import com.yevdokymov.task1.files.FileWriter;
import com.yevdokymov.task1.reader.Parser;
import com.yevdokymov.task2.Exemplar;
import com.yevdokymov.task2.UtilityClass;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {

         Parser parser = new Parser();
       FileWriter j = new FileWriter();
       j.writeObjectListToJson(parser.firstParse());

    }
}