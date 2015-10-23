package com.example.javaee;

import org.junit.rules.TestWatcher;

public class TracingTestWatcher extends TestWatcher {
    
    protected void starting(org.junit.runner.Description description) {
        System.out.println("----- starting test case: " + description.getClassName() + "." + description.getMethodName());
    }

    protected void finished(org.junit.runner.Description description) {
        System.out.println("----- finished test case: " + description.getClassName() + "." + description.getMethodName());
    }

    protected void failed(Throwable e, org.junit.runner.Description description) {
        System.out.println("----- failed test case: " + description.getClassName() + "." + description.getMethodName() + " reason: " + e.getMessage());
    }
}