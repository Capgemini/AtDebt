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

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.capgemini.csd.auditable.Auditable;
import com.capgemini.csd.auditable.AuditableEntity;

/**
 * A test class wired through Spring to test the @Auditable annotation.
 * 
 * @author Andrew Harmel-Law
 * @author Wadud Ruf
 *
 */
public class AuditableTestClass
{
    public static final String AUDIT_LOGIN = "LOGIN";
    public static final String AUDIT_USER_UPDATED = "USER_UPDATED";
    public static final String AUDIT_STATEMENT = "This is a simple audit statement.";
    private static final Log LOGGER = LogFactory.getLog(AuditableTestClass.class); 
	    
	@Auditable(operation = AUDIT_LOGIN)
	public void auditableMethod(AuditableEntity entity)
	{
		LOGGER.info("This is an auditable method taking in an auditable entity.");
	}
	
	@Auditable.ReturnEntity(operation = AUDIT_USER_UPDATED)
    public AuditableEntityImpl auditableMethodWithReturnType(AuditableEntityImpl entity)
    {
        LOGGER.info("This is an auditable method returning an auditable entity.");
        return entity;
    }
	
	@Auditable.Statement(AUDIT_STATEMENT)
	public void simpleAuditableMethod()
	{
		LOGGER.info("This is a simple auditable method.");
	}
	
	/**
	 * an implementation of an {@link AuditableEntity} for test purposes.
	 * 
	 * @author Andrew Harmel-Law
	 * @author Wadud Ruf
	 *
	 */
	public static class AuditableEntityImpl implements AuditableEntity
	{	 
		@Override
		public String getCallerId()
		{
			return "caller-id";
		}
		
		@Override
		public String toString()
		{
			return "String representation of this entity.";
		}
	}
}
