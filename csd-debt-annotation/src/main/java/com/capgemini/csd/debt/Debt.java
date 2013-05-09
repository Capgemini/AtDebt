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
 *   http://kenai.com/projects/csdutilities
 */
package com.capgemini.csd.debt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An Annotation to track instances of technical debt, with a field for
 * description, and another to count the number of times it has been incurred.
 * 
 * @author Andrew Harmel-Law
 * @author Rob Horn
 * @author Wadud Ruf
 */
@Target({ElementType.CONSTRUCTOR,ElementType.FIELD,
		ElementType.METHOD,ElementType.PACKAGE,
		ElementType.PARAMETER,ElementType.TYPE
		/* Local Variable annotations in Java 6 are ignored.
		ElementType.LOCAL_VARIABLE,*/})
@Retention(RetentionPolicy.CLASS)
public @interface Debt {

    /**
     * A short description of the nature of the debt.
     *
     * @return the description
     */
    String desc();

    /**
     * Number of times debt incurred. (Updated manually by developers.)
     *
     * @return the count
     */
    int count() default 1;
}
