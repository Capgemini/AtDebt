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

import com.capgemini.csd.debt.reporter.DebtReportingFactory;
import org.junit.Test;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class ProcessorTests {

    @Test
    public void processingCompletedShouldReturnTrue() {
        RoundEnvironment mockEnv = mock(RoundEnvironment.class);
        when(mockEnv.processingOver()).thenReturn(true);
        DebtProcessor processor = new DebtProcessor();
        boolean returnVal = processor.process(new HashSet<TypeElement>(), mockEnv);
        assertTrue("annotation already processed", returnVal);
    }

    @Test
    public void processingNotCompletedShouldContinue() {
        RoundEnvironment mockEnv = mock(RoundEnvironment.class);
        when(mockEnv.processingOver()).thenReturn(false);
        DebtProcessor processor = new DebtProcessor();
        DebtReportingFactory mockFactory = mock(DebtReportingFactory.class);
        processor.setDebtReportingFactory(mockFactory);
        DebtCollector mockDebtCollector = mock(DebtCollector.class);
        DebtCollection mockDebtCollection = mock(DebtCollection.class);
        when(mockDebtCollector.getDebtCollection()).thenReturn(mockDebtCollection);

        processor.setDebtCollector(mockDebtCollector);

        boolean returnVal = processor.process(new HashSet<TypeElement>(), mockEnv);

    }



}
