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
package com.capgemini.csd.auditable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.capgemini.csd.debt.Debt;

/**
 * Marks a method as being auditable. See {@link AuditableEntity} for usage.
 * 
 * @author Andrew Harmel-Law
 * @author Wadud Ruf
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Auditable 
{    
	/**
	 * A description of the audit operation being performed.
	 * 
	 * @return the audit operation being performed.
	 */
	@Debt(desc = "This should ideally be an enum. However using an enum severly restricts " +
				 "extensibility to only the declared types at API design-time.", count = 1)
    // TODO: Refactor this to use an Enum following the pattern in Effective Java for extensible Enums
    String operation();

	/**
	 * Marks a method as being auditable, using the specified audit statement. 
	 * This annotation does not audit {@link AuditableEntity} but rather should 
	 * be used to log simple audit statements.
	 * 
	 * @author Andrew Harmel-Law
	 * @author Wadud Ruf
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	static @interface Statement
	{    
	    /**
	     * The type of operation the audit statement falls under.
	     */
	    public static final String OPERATION_TYPE = "STATEMENT";
	    
	    /**
	     * A constant representing the system as the owner of this statement.
	     */
	    public static final String STATEMENT_OWNER = "SYSTEM";
	    
	    /**
	     * An optional statement to log.
	     * 
	     * @return the statement to log.
	     */
	    String value();
	}
	
	/**
     * Marks a method as being auditable, using the specified audit statement. 
     * This annotation does not audit {@link AuditableEntity} but rather should 
     * be used to log simple audit statements.
     * 
     * @author Andrew Harmel-Law
     * @author Wadud Ruf
     *
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    static @interface ReturnEntity
    {            
        /**
         * The type of operation the audit statement falls under.
         */        
        String operation();
    }
}
