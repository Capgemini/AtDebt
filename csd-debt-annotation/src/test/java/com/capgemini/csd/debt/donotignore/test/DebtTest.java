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
package com.capgemini.csd.debt.donotignore.test;

import org.junit.Test;

import com.capgemini.csd.debt.Debt;
import com.capgemini.csd.debt.Debts;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
@Debt(desc = "The retention policy of the @Debt annotation is compile time only. " +
		"This makes it difficult to unit test.", count = 5)
public class DebtTest
{
	@Debt(desc = "Remove this redundant var.",count=4)
	private int dummyInt;
	private String unannotatedField;
	
	@Debts({
		@Debt(desc = "Should be a more meaningful name.", count = 2),
		@Debt(desc = "Not used anywhere?", count = 1)
	})
	private String multipleDebts = "multipleDebts";
	
	@Debt(desc = "a test class.",count=1)
	class TestClass{
		
	}
	
	@Debt(desc = "This is a constructor level debt annotation.",count = 4)
	public DebtTest()
	{
		
	}
	
	@Debt(desc = "this is a dummy method.",count=3)
	private void dummyMethod()
	{
		
	}
	
	private void unAnnotatedMethod()
	{
		
	}
	
	@Debts({
		@Debt(desc = "Debt instance A", count = 1),
		@Debt(desc = "Debt instance B", count = 2)
	})
	private void dummyDebtsMethods()
	{
		
	}
	
	@Test
	public void testDebtAnnotation()
	{
//		@Debt(desc = "Refactor this string to be more meaningful.",size=1)
		String dummyVar = "some dummy text";
	}
}
