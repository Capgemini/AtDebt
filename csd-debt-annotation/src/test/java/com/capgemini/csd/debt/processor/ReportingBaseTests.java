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
import com.capgemini.csd.debt.reporter.impl.XMLDebtReporter;
import org.junit.Test;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class ReportingBaseTests {

    @Test
    public void debtProcessorShouldDelegateSupportedOptionsToReporterFactory() {

        DebtProcessor processor = new DebtProcessor();
        ProcessingEnvironment mockEnv = mock(ProcessingEnvironment.class);
        Messager mockMessager = mock(Messager.class);
        when(mockEnv.getMessager()).thenReturn(mockMessager);

        processor.init(mockEnv);

        Set<String> supportedOptions = new HashSet<String>() {{
            // options supported by DebtProcessor
            add(DebtProcessorProperties.SCAN_PACKAGE);
            add(DebtProcessorProperties.FAIL_THRESHOLD);
            add(DebtProcessorProperties.WARN_THRESHOLD);

            // options provided by DebtReportingFactory
            add(DebtReportingFactory.REPORT_ENABLED);

            // options provided by the XMLDebtReporter
            add(XMLDebtReporter.REPORT_DIR);
            add(XMLDebtReporter.REPORT_FILE);

        }};

        assertArrayEquals("reported SupportedOptions are equal", supportedOptions.toArray(), processor.getSupportedOptions().toArray());

    }


}
