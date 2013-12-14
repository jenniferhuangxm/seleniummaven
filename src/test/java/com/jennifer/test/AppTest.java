package com.jennifer.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    public void testApp(){
    	assertEquals("hello selenium", new App().sayHello());
    }
}
