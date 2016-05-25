package com.example.javaee.cdi;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Polite
public class PoliteInterceptor {

    @AroundInvoke
    public Object aroundInvoke(InvocationContext ic) throws Exception {
        System.out.println("polite filter : " + ic.getParameters());
        Object[] parameters = ic.getParameters();
        if (parameters.length > 0 && parameters[0] instanceof String) {
            String param = (String) parameters[0];
            parameters[0] = "oh, Hi " + param + " !";
            ic.setParameters(parameters);
        }
        Object result = ic.proceed();
        return result;
    }
}
