package ru.otus.testframework;


import ru.otus.annotation.After;
import ru.otus.annotation.Before;
import ru.otus.annotation.Test;
import ru.otus.logic.TestCalculator;


import java.lang.reflect.Method;
import java.util.ArrayList;

public class testExecutor {
    public static void run(Class<?> classWithTests){
        Method[] methods = classWithTests.getDeclaredMethods();
        Method after = null;
        Method before = null;
        ArrayList<Method> tests = new ArrayList<>();

        int testsPassed = 0;
        int testsFailed = 0;

        for (Method method : methods){
            if (method.getAnnotation(Before.class) != null){
                before = method;
                before.setAccessible(true);
            }
            if (method.getAnnotation(After.class) != null){
                after = method;
                after.setAccessible(true);
            }
            if (method.getAnnotation(Test.class) != null){
                tests.add(method);
            }
        }

        int testsCount = tests.size();

        for (Method test : tests){
            TestCalculator testCalculator = new TestCalculator();
            try {
                if (before != null){
                    before.invoke(testCalculator);
                }
                test.setAccessible(true);
                test.invoke(testCalculator);
                if (after != null){
                    after.invoke(testCalculator);
                }
            } catch (Exception e){
                testsFailed++;
                continue;
            }
            testsPassed++;
        }
        System.out.printf(
                "Tests passed: %d, tests failed: %d, tests count: %d%n",
                testsPassed,
                testsFailed,
                testsCount);
    }
}
