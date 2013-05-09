/*
 * @Debt Copyright (C) 2010 Andrew Harmel-Law
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * To contact the development team, please refer to the project site at:
 *
 *      http://kenai.com/projects/csdutilities
 */
package com.capgemini.csd.auditable.test;

import static org.junit.Assert.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.capgemini.csd.auditable.AuditMessager;
import com.capgemini.csd.auditable.Auditable;
import com.capgemini.csd.auditable.AuditableEntity;
import com.capgemini.csd.auditable.test.AuditableTestClass.AuditableEntityImpl;


public class AuditableTest
{
	private static final Log LOGGER = LogFactory.getLog(AuditableTest.class);
		
	private AuditableTestClass auditableTest;
	private AuditMessagerTestImpl messager;
	
	@Before
	public void setUp()
	{
		  ApplicationContext context = new ClassPathXmlApplicationContext(
				  new String[] {"classpath:test-app-config.xml"});

          auditableTest = (AuditableTestClass)context.getBean("auditableTestClass");
          messager = (AuditMessagerTestImpl)context.getBean("messager");
	}
	
	@Test
	public void testAuditableMethod()
	{
	    // Create test entity instance.
	    AuditableEntityImpl impl = new AuditableTestClass.AuditableEntityImpl();
	    
	    // Call the method to invoke the auditable annotation.
		auditableTest.auditableMethod(impl);
		
		assertTrue("Message not received by messager.",messager.isMessageReceived());
		assertEquals("Operation not equal.",AuditableTestClass.AUDIT_LOGIN,messager.getAudit().getOperation());
		assertEquals("Caller id not equal.",impl.getCallerId(),messager.getAudit().getCallerId());
		assertEquals("toString not equal.",impl.toString(),messager.getAudit().getSubject().toString());
	}

    @Test
    public void testAuditableMethodWithReturnType()
    {
        AuditableEntityImpl impl = new AuditableEntityImpl();
       
        // Call the method to invoke the auditable.returnEntity annotation.
        impl = auditableTest.auditableMethodWithReturnType(impl);
        
        assertTrue("Message not received by messager.",messager.isMessageReceived());
        assertEquals("Operation not equal.",AuditableTestClass.AUDIT_USER_UPDATED,messager.getAudit().getOperation());
        assertEquals("User id not equal.",impl.getCallerId(),messager.getAudit().getCallerId());
        assertEquals("toString not equal.",impl.toString(),messager.getAudit().getSubject().toString());
    }
    
    @Test
    public void testSimpleAuditableMethod()
    {
        auditableTest.simpleAuditableMethod();
        
        assertTrue("Message not received by messager.",messager.isMessageReceived());
        assertEquals("Audit statement not as expected.",AuditableTestClass.AUDIT_STATEMENT,messager.getAudit().getStatement());
        assertEquals("Operation type not as expected.",Auditable.Statement.OPERATION_TYPE,messager.getAudit().getOperation());
        assertEquals("Caller id not as expected.",Auditable.Statement.STATEMENT_OWNER,messager.getAudit().getCallerId());
    }
}
