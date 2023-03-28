package ru.otus.testframework;


import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;


import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.*;

public class TestExecutor {
    public static void run(Class<?> classWithTests){
        Method[] methods = classWithTests.getDeclaredMethods();
        List<Method> tests = getAccessibleMethodsWithTestAnnotation(methods);
        if (tests.size() == 0) return;

        Optional<Method> after = Optional.ofNullable(getFirstAccessibleMethodWithAnnotation(methods,After.class));
        Optional<Method> before = Optional.ofNullable(getFirstAccessibleMethodWithAnnotation(methods,Before.class));

        int testsPassed = 0;
        int testsFailed = 0;
        int testsCount = tests.size();

        for (Method test : tests){
            Object testClassObj = createTestClassObject(classWithTests);
            try {
                if (before.isPresent()){
                    before.get().invoke(testClassObj);
                }
                try {
                    test.invoke(testClassObj);
                    testsPassed++;
                } catch (Exception e){
                    testsFailed++;
                }
                if (after.isPresent()){
                    after.get().invoke(testClassObj);
                }
            } catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        printResult(testsPassed, testsFailed, testsCount);
    }
    private static List<Method> getAccessibleMethodsWithTestAnnotation(Method[] methods){
        List<Method> methodsWithAnnotation = new ArrayList<>();
        for (Method method: methods){
            if (Objects.nonNull(method.getAnnotation(Test.class))){
                method.setAccessible(true);
                methodsWithAnnotation.add(method);
            }
        }
        return methodsWithAnnotation;
    }

    private static <T extends Annotation> Method getFirstAccessibleMethodWithAnnotation(
            Method[] methods,
            Class<T> annotationClass){
        for (Method method: methods){
            if (Objects.nonNull(method.getAnnotation(annotationClass))){
                method.setAccessible(true);
                return method;
            }
        }
        return null;
    }

    private static Object createTestClassObject(Class<?> classWithTests){
        Constructor<?>[] constructors = classWithTests.getConstructors();
        try {
            for (Constructor<?> constructor : constructors){
                if (constructor.getParameterCount()==0){
                    return constructor.newInstance();
                }
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }

    private static void printResult(int testsPassed, int testsFailed, int testsCount){
        System.out.printf(
                "Tests passed: %d, tests failed: %d, tests count: %d%n",
                testsPassed,
                testsFailed,
                testsCount);
    }

}
