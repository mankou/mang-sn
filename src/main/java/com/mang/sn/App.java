package com.mang.sn;

import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mang.sn.service.TestService;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        DOMConfigurator.configure(ClassLoader.getSystemResource("log4j.xml"));
        
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        TestService service=ctx.getBean(TestService.class);
        service.testTransaction();
        
        System.out.println("hah");

    }
}
