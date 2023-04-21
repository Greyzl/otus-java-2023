package ru.otus.aop.logging;

import ru.otus.annotation.Log;
import ru.otus.formatter.LogMethodFormatter;
import ru.otus.service.CalculatorService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

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
        Set<Method> annotatedMethods = new HashSet<>();
        for (Method objectClassMethod : objectClassDeclaredMethods){
            if (objectClassMethod.getDeclaredAnnotation(Log.class) != null){
                annotatedMethods.add(objectClassMethod);
            }
        }
        return annotatedMethods;
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
        if (!invokedMethod.getName().equals(proxiedObjectMethod.getName())
        || invokedMethod.getParameterCount() != proxiedObjectMethod.getParameterCount()){
            return false;
        }
        boolean sameSignature = true;
        Class<?>[] proxyMethodParamTypes = invokedMethod.getParameterTypes();
        Class<?>[] objectTestMethodParamTypes = proxiedObjectMethod.getParameterTypes();
        for (int paramIndex = 0; paramIndex < invokedMethod.getParameterCount(); paramIndex++){
            if (objectTestMethodParamTypes[paramIndex] != proxyMethodParamTypes[paramIndex]){
                sameSignature = false;
                break;
            }
        }
        return sameSignature;
    }
}
