package com.yevdokymov.task1.reader;

import com.yevdokymov.task1.Violation;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Handler extends DefaultHandler {
    public List<String> violations = new ArrayList<>();

    String content;
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

    }
    public void endElement(String uri, String localName, String qName){
        switch (qName) {
            case "type" -> {
                // проверяю как считываются данные, судя по всему проблема в синхронности работы ибо некоторые поля читает правильно а в некоторых проблема
                /*
                PHONE 340.00
                PARKING 280.00
                ALCOHOL Ivanov
                SPEEDING 200.00
                340.00
                SPEEDING 2400.00
                PARKING RED_COLOR 540.00                  как я понимаю два потока пытаються записать свои данные, то есть нужно
                280.00                                    при нахождении нового штрафа заморозить все потоки пока не запишет тот, что работает,
                PHONE ALCOHOL 5600.00                     и так же скорее всего можно пофиксить запись сразу в обьект, как я делал без многопоточки
                340.00                                    Иногда вообще вытягивает имя нарушителя или дату штрафа, что мне вообще не понятно
                PARKING 280.00
                ALCOHOL 5600.00
                SPEEDING 200.00
                SPEEDING 2400.00
                RED_COLOR 540.00*/
                System.out.print(content + " ");
            }
            case "Fine_amount" -> {
                System.out.println(content);
            }
        }

    }
    public void characters(char[] ch, int start, int length){
        content = String.copyValueOf(ch,start,length).trim();
    }
}
