package org.eclipsefeaturesdemo.eclipsefeatures.maven;


public class Java21SyncDemo {

    public static String describe(Object value) {
        return switch (value) {
            case String text -> "Text: " + text;
            case Integer number -> "Number: " + number;
            default -> "Other";
        };
    }
}