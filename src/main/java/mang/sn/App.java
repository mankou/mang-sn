
package mang.sn;

import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import mang.sn.service.TestSnService;

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
        
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-oracle.xml");
//        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-mysql.xml");
        
        TestSnService service=ctx.getBean(TestSnService.class);
//        service.testTransaction();
//        service.testDaySn();
        service.testWeekSn();
//        service.testYearSn();
//        service.testMonthSn();
        
        
        
        System.out.println("hah");

    }
}
