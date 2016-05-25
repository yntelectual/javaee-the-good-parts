package com.example.javaee.cdi;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import com.example.javaee.dao.MetricsDAO;
import com.example.javaee.model.Measurement;

@Interceptor
@Trace
@Priority(Interceptor.Priority.APPLICATION)
public class TraceInterceptor {
    //I can inject here as well
    @Inject
    private MetricsDAO dao;
    
	@AroundInvoke
	public Object aroundInvoke(InvocationContext ic) throws Exception {
	    long start = System.currentTimeMillis();
		System.out.println("CDI interceptor before method: " + ic.getTarget().getClass().getSimpleName() + "#" + ic.getMethod().getName());
		Object result = ic.proceed();
		System.out.println("CDI interceptor after method returned: " + ic.getTarget().getClass().getSimpleName() + "#" + ic.getMethod().getName());
		long time = System.currentTimeMillis() - start;
		Measurement m  =  new Measurement();
		m.setName(ic.getMethod().getName());
		m.setExecutionTime(time);
		dao.save(m);
		System.out.println("Metrics saved");
		return result;
	}
	
	@PostConstruct
	public Object postConstruct(InvocationContext ic) throws Exception {
	    System.out.println("CDI postConstruct: " + ic.getTarget().getClass().getSimpleName() + "#" + ic.getConstructor().getName());
        Object result = ic.proceed();
        System.out.println("CDI postConstruct end"); 
        return result;
	}
	
	@PreDestroy
    public Object preDestroy(InvocationContext ic) throws Exception {
	    System.out.println("CDI preDestroy: " + ic.getTarget().getClass().getSimpleName() + "#");
        Object result = ic.proceed();
        System.out.println("CDI preDestroy end"); 
        return result;
    }
}
