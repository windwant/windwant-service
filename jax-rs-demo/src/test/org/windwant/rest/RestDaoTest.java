package org.windwant.rest;

import junit.framework.Test; 
import junit.framework.TestSuite; 
import junit.framework.TestCase;
import org.windwant.rest.dao.RestDao;

/** 
* BaseDao Tester.
* 
* @author <Authors name> 
* @since <pre>12/19/2017</pre> 
* @version 1.0 
*/ 
public class RestDaoTest extends TestCase { 
public RestDaoTest(String name) { 
super(name); 
} 

public void setUp() throws Exception { 
super.setUp(); 
} 

public void tearDown() throws Exception { 
super.tearDown(); 
} 

/** 
* 
* Method: getPerson() 
* 
*/ 
public void testGetStudent() throws Exception {
    new RestDao().selectStudent("lilei");
} 



public static Test suite() { 
return new TestSuite(RestDaoTest.class); 
} 
}