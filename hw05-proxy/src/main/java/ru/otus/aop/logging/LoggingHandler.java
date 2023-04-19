package ru.otus.aop.logging;

import ru.otus.annotation.Log;
import ru.otus.service.CalculatorService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingHandler implements InvocationHandler {

    private final CalculatorService calculatorService;

    public LoggingHandler(CalculatorService calculatorService){
        this.calculatorService = calculatorService;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> calculatorServiceClass = calculatorService.getClass();
        Method[] calculatorServiceMethods = calculatorServiceClass.getDeclaredMethods();
        for (Method calculatorServiceMethod : calculatorServiceMethods){
            if (isSameMethods(method, calculatorServiceMethod) &&
                    calculatorServiceMethod.getDeclaredAnnotation(Log.class) != null){
                StringBuilder builder = new StringBuilder();
                for (Object object: args){
                    builder.append(object).append(" ");
                }
                System.out.println("executed method: " + calculatorServiceMethod.getName()
                        + ", params:" + builder);
            }
        }
        return method.invoke(calculatorService,args);
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
            if (!objectTestMethodParamTypes[paramIndex].getName().equals(
                    proxyMethodParamTypes[paramIndex].getName())){
                sameSignature = false;
                break;
            }
        }
        return sameSignature;
    }
}
