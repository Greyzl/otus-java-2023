package ru.otus.formatter;

import java.lang.reflect.Method;

public class LogMethodFormatter {

    public String format(Method loggingMethod, Object[] args){
        StringBuilder builder = new StringBuilder();
        for (Object object: args){
            builder.append(object).append(" ");
        }
        return String.format("executed method: %s, params: %s",loggingMethod.getName(), builder);
    }
}
