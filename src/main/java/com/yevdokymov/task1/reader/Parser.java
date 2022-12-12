package com.yevdokymov.task1.reader;

import com.yevdokymov.task1.Violation;
import com.yevdokymov.task1.files.FileFounder;
import com.yevdokymov.task1.files.FileWriter;
import org.xml.sax.SAXException;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.io.IOException;

import java.util.*;
import java.util.concurrent.*;


public class Parser {
    public List<Violation> firstParse() throws Exception{
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        Handler handler = new Handler();
        FileFounder fileFounder = new FileFounder();

        long start = System.nanoTime();

        ExecutorService service = Executors.newFixedThreadPool(8);
        for (File file: fileFounder.files){
            CompletableFuture.supplyAsync(() -> file,service).thenAccept(x -> {
                            try {
                                parser.parse(file.getPath(), handler);
                            } catch (IOException | SAXException e) {
                                throw new RuntimeException(e);
                            }
                    }
            ).join();
        }
        service.shutdown();
        System.out.println(System.nanoTime() - start);


        handler.violations.sort(Comparator.comparing(Violation::getFineAmount));
        Collections.reverse(handler.violations);
        System.out.println(handler.violations);
        return handler.violations;
    }
    public static void main(String[] args) throws Exception {

        Parser parser = new Parser();
        FileWriter j = new FileWriter();
        j.writeObjectListToJson(parser.firstParse());

    }


}
