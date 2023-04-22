package ru.otus.aop.logging;

import ru.otus.annotation.Log;
import ru.otus.formatter.LogMethodFormatter;
import ru.otus.service.CalculatorService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class LoggingHandler implements InvocationHandler {

    private final CalculatorService calculatorService;

    private final LogMethodFormatter logMethodFormatter = new LogMethodFormatter();

    private final Set<Method> loggingMethods;

    public LoggingHandler(CalculatorService calculatorService){
        this.calculatorService = calculatorService;
        loggingMethods = getLogAnnotatedMethods(calculatorService);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (isLoggingMethod(method)){
            System.out.println(logMethodFormatter.format(method, args));
        }
        return method.invoke(calculatorService,args);
    }

    private Set<Method> getLogAnnotatedMethods(Object object){
        Class<?> objectClass = object.getClass();
        Method[] objectClassDeclaredMethods = objectClass.getDeclaredMethods();
        return Arrays.stream(objectClassDeclaredMethods).filter(
                method -> method.isAnnotationPresent(Log.class)).collect(Collectors.toSet());
    }

    private Boolean isLoggingMethod(Method method){
        for (Method loggingMethod : loggingMethods){
            if (isSameMethods(method, loggingMethod)){
                return true;
            }
        }
        return false;
    }

    private Boolean isSameMethods(Method invokedMethod, Method proxiedObjectMethod){
        if (!invokedMethod.getName().equals(proxiedObjectMethod.getName())){
            return false;
        }
        Class<?>[] proxyMethodParamTypes = invokedMethod.getParameterTypes();
        Class<?>[] objectTestMethodParamTypes = proxiedObjectMethod.getParameterTypes();
        return Arrays.equals(proxyMethodParamTypes, objectTestMethodParamTypes);
    }
}
