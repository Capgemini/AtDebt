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

import org.custommonkey.xmlunit.XMLTestCase;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class XMLDebtReporterTests extends XMLTestCase {

    private String standardTestDirLocation = "test/dir";
    private String standardTestReportFileName = "standardTestReport.xml";

    @Test
    public void testReportDirShouldBeReadFromEnvAsPresent() {
        XMLDebtReporter reporter = new XMLDebtReporter();
        reporter.setProperties(createProperties());

        reporter.setReportDirLocationFromProperties();

        assertEquals(standardTestDirLocation, reporter.getReportDirLocation());
    }

    @Test
    public void testReportDirShouldNotBeCreatedAsItExists() {
        File mockReportDir = mock(File.class);
        when(mockReportDir.exists()).thenReturn(Boolean.TRUE);

        XMLDebtReporter reporter = new XMLDebtReporter();
        reporter.setReportDir(mockReportDir);

        reporter.createReportDirIfNeeded();

        verify(mockReportDir, never()).mkdirs();
    }


    @Test
    public void testReportDirShouldBeCreatedAsItDoesNotExist() {
        File mockReportDir = mock(File.class);
        when(mockReportDir.exists()).thenReturn(Boolean.FALSE);
        XMLDebtReporter reporter = new XMLDebtReporter();
        reporter.setReportDir(mockReportDir);

        reporter.createReportDirIfNeeded();

        verify(mockReportDir, times(1)).mkdirs();
    }


    @Test
    public void testReportFileShouldBeReadFromEnvAsPresent() throws IOException {
        XMLDebtReporter reporter = new XMLDebtReporter();
        reporter.setProperties(createProperties());
        File mockReportDir = mock(File.class);
        when(mockReportDir.getPath()).thenReturn(standardTestDirLocation);
        reporter.setReportDir(mockReportDir);

        reporter.setReportFileFromFromEnv(mockReportDir);

        assertEquals(standardTestReportFileName, reporter.getReportFile().getName());
    }

    @Test
    public void testReportFileShouldBeDefaultAsKeyPresentButSetToEmptyString() throws IOException {
        XMLDebtReporter reporter = new XMLDebtReporter();
        Map<String, String> mockOptions = new HashMap<String, String>();
        mockOptions.put(XMLDebtReporter.REPORT_DIR, standardTestDirLocation);
        mockOptions.put(XMLDebtReporter.REPORT_FILE, "");
        reporter.setProperties(mockOptions);
        File mockReportDir = mock(File.class);
        when(mockReportDir.getPath()).thenReturn(standardTestDirLocation);
        reporter.setReportDir(mockReportDir);

        reporter.setReportFileFromFromEnv(mockReportDir);

        assertEquals("debt.xml", reporter.getReportFile().getName());
    }

    @Test
    public void testReportFileShouldBeDefaultAsNotPresent() throws IOException {
        XMLDebtReporter reporter = new XMLDebtReporter();
        Map<String, String> mockOptions = new HashMap<String, String>();
        mockOptions.put(XMLDebtReporter.REPORT_DIR, standardTestDirLocation);
        reporter.setProperties(mockOptions);
        File mockReportDir = mock(File.class);
        when(mockReportDir.getPath()).thenReturn(standardTestDirLocation);
        reporter.setReportDir(mockReportDir);
        reporter.setReportFileFromFromEnv(mockReportDir);
        assertEquals("debt.xml", reporter.getReportFile().getName());
    }

    private Map<String, String> createProperties() {
        Map<String, String> mockOptions = new HashMap<String, String>();
        mockOptions.put(XMLDebtReporter.REPORT_DIR, standardTestDirLocation);
        mockOptions.put(XMLDebtReporter.REPORT_FILE, standardTestReportFileName);
        return mockOptions;
    }
}
