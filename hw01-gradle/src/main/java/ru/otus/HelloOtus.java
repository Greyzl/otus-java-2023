package ru.otus;

import com.google.common.collect.*;

import java.util.Map;

public class HelloOtus {
    private static final ImmutableMap<String,String> guavaLinks =
            ImmutableMap.of(
                    "Introduction","https://github.com/google/guava/wiki/Home",
                    "Guava Philosophy","https://github.com/google/guava/wiki/PhilosophyExplained"
            );
    public static void main(String[] args) {
        for (Map.Entry<String, String> entry : guavaLinks.entrySet()) {
            String topic = entry.getKey();
            String link = entry.getValue();
            System.out.println(topic + ": " + link);
        }
    }
}