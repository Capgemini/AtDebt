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
package com.capgemini.csd.debt.processor;

import com.capgemini.csd.debt.Debt;
import org.junit.Test;

import javax.lang.model.element.Element;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class DebtInstanceInfoTests {

    private final String EXPECTED_DESC = "Test Desc";
    private final int EXPECTED_COUNT = 1;
    private final String EXPECTED_CONTEXT = "Test Context";

    @Test
    public void getDescShouldReturnDebtAnnotationsDesc() {
        DebtInstanceInfo debtInfo = createDebtInfo();
        assertEquals("desc does not match Debts desc", EXPECTED_DESC, debtInfo.getDesc());
    }

    @Test
    public void getCountShouldReturnDebtAnnotationsCount() {
        DebtInstanceInfo debtInfo = createDebtInfo();
        assertEquals("count does not match Debts count",EXPECTED_COUNT,debtInfo.getCount());
    }

    @Test
    public void getContextShouldReturnElementToString() {
        DebtInstanceInfo debtInfo = createDebtInfo();
        assertEquals("context does not match expected context",EXPECTED_CONTEXT, debtInfo.getContext());
    }

    @Test
    public void getElementShouldReturnElement() {
        Debt mockDebt = createMockDebt(EXPECTED_DESC, EXPECTED_COUNT);
        Element mockElement = createMockElemet();
        DebtInstanceInfo debtInfo = new DebtInstanceInfo(mockElement, mockDebt);
        assertEquals(mockElement, debtInfo.getElement());
    }

    @Test (expected = IllegalArgumentException.class)
    public void constructingWithNullElementShouldThrowIllegalArgumentException() {
        Debt mockDebt = createMockDebt(EXPECTED_DESC, EXPECTED_COUNT);
        DebtInstanceInfo debtInfo = new DebtInstanceInfo(null, mockDebt);
    }

    @Test (expected = IllegalArgumentException.class)
    public void constructingWithNullDebtShouldThrowIllegalArgumentException() {
        Element mockElement = createMockElemet();
        DebtInstanceInfo debtInfo = new DebtInstanceInfo(mockElement, null);
    }

    private Debt createMockDebt(String expectedDesc, int expectedCount) {
        Debt mockDebt = mock(Debt.class);
        when(mockDebt.desc()).thenReturn(expectedDesc);
        when(mockDebt.count()).thenReturn(expectedCount);
        return mockDebt;
    }

    private DebtInstanceInfo createDebtInfo() {
        Element mockElement = createMockElemet();
        Debt mockDebt = createMockDebt(EXPECTED_DESC, EXPECTED_COUNT);
        return new DebtInstanceInfo(mockElement, mockDebt);
    }

    private Element createMockElemet() {
        Element mockElement = mock(Element.class);
        when(mockElement.toString()).thenReturn(EXPECTED_CONTEXT);
        return mockElement;
    }
}
