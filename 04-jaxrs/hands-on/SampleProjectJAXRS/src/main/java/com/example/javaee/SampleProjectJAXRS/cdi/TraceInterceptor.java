package com.example.javaee.SampleProjectJAXRS.cdi;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@Trace
public class TraceInterceptor {

	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
		System.out.println("CDI interceptor before method: " + ic.getTarget().getClass().getSimpleName() + "#" + ic.getMethod().getName());
		Object result = ic.proceed();
		System.out.println("CDI interceptor after method returned: " + ic.getTarget().getClass().getSimpleName() + "#" + ic.getMethod().getName());
		return result;
	}
}
