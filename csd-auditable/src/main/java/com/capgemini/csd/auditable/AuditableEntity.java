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

import java.util.Date;

/**
 * <p>
 * This interface should be implemented by all entities to give them the ability
 * to be audited whenever the {@link Auditable} annotation is encountered on a method. 
 * </p>
 * <p>
 * For example, the following method that takes in a parameter of type 
 * {@link AuditableEntity} has been marked up as auditable -
 * <code>
 * <pre>
 * @Auditable(operation = "LOGIN")
 * public void login(UserEntity user)
 * {
 *    // Login code here.
 * }
 * 
 * class UserEntity implements AuditableEntity
 * {
 *    ...
 * }
 * </pre>
 * </code>
 * </p>
 * <p>
 * This method when invoked will cause the UserEntity object to
 * be audited with information held in a {@link Audit} object.
 * </p>
 * 
 * @author Andrew Harmel-Law
 * @author Wadud Ruf
 *
 */
public interface AuditableEntity 
{       
    /**
     * The caller to associate with object with when it is audited.
     * 
     * @return user identifier.
     */
    public abstract String getCallerId();
    
    /**
     * String representation  of this {@link AuditableEntity}.
     * 
     * @return
     */
    public abstract String toString();
}
