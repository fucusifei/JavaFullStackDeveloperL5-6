package com.yevdokymov.task1.reader;

import com.yevdokymov.task1.Violation;
import com.yevdokymov.task1.files.FileFounder;
import com.yevdokymov.task1.files.FileWriter;
import org.xml.sax.SAXException;


import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;
import java.util.concurrent.*;


public class Parser {

    public List<Violation> firstParse() throws Exception {
        Handler handler = new Handler();
        FileFounder fileFounder = new FileFounder();

        long start = System.nanoTime();

        ExecutorService service = Executors.newFixedThreadPool(10);
        for (final File f : fileFounder.files) {
            if (!f.isFile()) {
                continue;
            }
            CompletableFuture.runAsync(new Runnable() {
                                           @Override
                                           public void run() {
                                               try {
                                                   SAXParserFactory parserFactory = SAXParserFactory.newInstance();
                                                   SAXParser parser = parserFactory.newSAXParser();
                                                   parser.parse(f.getPath(), handler);

                                               } catch (SAXException | IOException | ParserConfigurationException ex) {
                                                   throw new RuntimeException(ex);
                                               }
                                               //System.out.println(handler.violations);
                                               //  System.out.println("Поток: " + Thread.currentThread().getName() + ". Файл: " + f.getName());
                                           }
                                       },service
            );
        }
        service.shutdown();

        // System.out.println(System.nanoTime() - start);


        // handler.violations.sort(Comparator.comparing(Violation::getFineAmount));
        //  Collections.reverse(handler.violations);
        //  System.out.println(handler.violations);
        //return handler.violations;
        return null;
    }
    //пытался получить все в строку а потом сделать обьект
    public List<Violation> convertToObject(List<String> textFromXMLFile) {
        List<Violation> violationsObj = new ArrayList<>();
        //System.out.println(textFromXMLFile);
        Violation violation = null;
        for (int i = 0; i < textFromXMLFile.size(); i++) {
            String stringFromList = textFromXMLFile.get(i);
            System.out.println(stringFromList);
            if (i % 2 == 0) {
                if (violationsObj.stream().filter(x -> x.violationName.equals(stringFromList)).toList().isEmpty()) {
                    violation = new Violation();
                    violationsObj.add(violation);
                } else {
                    violation = violationsObj.stream().filter(x -> x.violationName.equals(stringFromList)).toList().get(0);
                }
                violation.violationName = stringFromList;
            }
            else {
                violation.fineAmount = violation.fineAmount + Double.parseDouble(stringFromList);
            }
        }
        violationsObj.sort(Comparator.comparing(Violation::getFineAmount));
        Collections.reverse(violationsObj);
        return violationsObj;
    }


    public static void main(String[] args) throws Exception {

        Parser parser = new Parser();
        FileWriter j = new FileWriter();
        j.writeObjectListToJson(parser.firstParse());

    }


}

