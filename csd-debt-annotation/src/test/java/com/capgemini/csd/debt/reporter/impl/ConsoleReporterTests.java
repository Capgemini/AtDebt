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
package com.capgemini.csd.debt.reporter.impl;

import com.capgemini.csd.debt.processor.DebtCollection;
import com.capgemini.csd.debt.processor.DebtProcessorProperties;
import com.capgemini.csd.debt.reporter.DebtReportingProperties;
import com.capgemini.csd.debt.reporter.Reporter;
import com.capgemini.csd.debt.reporter.ReportingTestsBase;
import org.junit.Test;
import org.junit.Ignore;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class ConsoleReporterTests extends ReportingTestsBase{

    @Test
    public void simpleDebtCollectionShouldBeReportedToMessager() throws IOException {
        DebtReportingProperties debtReportingProperties = createPropertiesWithEnv("true");
        Messager mockMessager = mock(Messager.class);
        debtReportingProperties.setMessager(mockMessager);
        debtReportingProperties.setDebtProcessingProperties(new DebtProcessorProperties(new HashMap<String, String>()));

        Reporter reporter = new ConsoleReporter(debtReportingProperties);

        DebtCollection debtCollection = new DebtCollection();
        String expectedReportText = getSummaryText();
        reporter.report(debtCollection, debtReportingProperties);

        verify(mockMessager, atMost(1)).printMessage(Diagnostic.Kind.NOTE, expectedReportText);
    }

    @Test
    public void warningThresholdExceededShouldBeReported() throws IOException {
        DebtReportingProperties debtReportingProperties = createPropertiesWithEnv("true", 0, 0);
        Messager mockMessager = mock(Messager.class);
        debtReportingProperties.setMessager(mockMessager);
        debtReportingProperties.setDebtProcessingProperties(new DebtProcessorProperties(new HashMap<String, String>()));

        Reporter reporter = new ConsoleReporter(debtReportingProperties);

        DebtCollection debtCollection = new DebtCollection();
        String expectedReportText = getSummaryText();
        reporter.report(debtCollection, debtReportingProperties);

        verify(mockMessager, atMost(1)).printMessage(Diagnostic.Kind.NOTE, expectedReportText);
    }

    private String getSummaryText() {
        return
            "------------------------------------------------------------------------\n" +
            "PROCESSED: count = 0, fail threshold: -1, warning threshold: -1\n" +
            "------------------------------------------------------------------------\n" +
            "TOTAL ACCRUED DEBT: 0\n" +
            "------------------------------------------------------------------------\n";
    }



}
