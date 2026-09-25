package org.eclipsefeaturesdemo.eclipsefeatures.editing;

import java.util.ArrayList;
import java.util.List;

public class ContentAssistDemo {

    public static void main(String[] args) {

        String message = "Learning Eclipse";
        String result = message.toUpperCase();
        
        List<String> list = new ArrayList<String>();
        list.add("Java");
        
        System.out.println(result);
        
        
    }
}