package org.windwant.elasticjob;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App {
        // CHECKSTYLE:OFF
    public static void main(final String[] args){
        // CHECKSTYLE:ON
        new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
    }
}
