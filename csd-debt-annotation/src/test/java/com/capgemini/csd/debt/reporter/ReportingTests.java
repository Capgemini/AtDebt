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
package com.capgemini.csd.debt.reporter;

import com.capgemini.csd.debt.reporter.impl.ConsoleReporter;
import org.junit.Test;

import javax.annotation.processing.Messager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class ReportingTests extends ReportingTestsBase {

    @Test
    public void shouldBeAbleToInitialiseReporterFromProperties() {
        DebtReportingProperties debtReportingProperties = createPropertiesWithEnv("true");
        Messager messager = mock(Messager.class);
        debtReportingProperties.setMessager(messager);

        ConsoleReporter reporter = new ConsoleReporter(debtReportingProperties);

        assertEquals(messager, reporter.getMessager());
    }

    @Test (expected = ReporterInitialisationException.class)
    public void messagerShouldNotBeNull() {
        DebtReportingProperties debtReportingProperties = createPropertiesWithEnv("true");
        debtReportingProperties.setMessager(null);

        Reporter reporter = new ConsoleReporter(debtReportingProperties);

        fail("ReporterInitialisationException should have been thrown before now - as null messager passed");
    }

//    @Ignore // No longer a valid test as ReportingFactory is responsible for calling report if report enabled.
//    @Test
//    public void nothingShouldHappenIfReportNotEnabled() {
//        DebtReportingProperties properties = createPropertiesWithEnv("false");
//        Messager mockMessager = mock(Messager.class);
//        properties.setMessager(mockMessager);
//
//        Reporter reporter = new ConsoleReporter(properties);
//
//        DebtCollection debtCollection = new DebtCollection();
//        String expectedReportText = "Test";
//        debtCollection.setReportText(expectedReportText);
//        reporter.report(debtCollection, new DebtProcessorProperties(new HashMap<String, String>()));
//
//        verify(mockMessager, never()).printMessage(Diagnostic.Kind.NOTE, expectedReportText);
//    }



    // TODO Carry on here!
//    @Test
//    public void reporterFactoryShouldDelegateReportingToRegisteredReporters() {
//
//        Reporter mockConsoleReporter = mock(ConsoleReporter.class);
//        DebtReportingFactory factory = new DebtReportingFactory();
//        factory.registerReporter(mockConsoleReporter);
//        factory.setReportEnabled(true);
//
//        factory.report(debtCollection,properties);
//
//    }
}
