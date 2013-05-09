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

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import java.io.File;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class OnInitialisationOfDebtProcessor {

    @Test
    public void scanPackageShouldBeReadFromEnvAsPresent() {
        ProcessingEnvironment mockEnv = mock(ProcessingEnvironment.class);
        final String expectedScanPackage = "test.scan.package";
        when(mockEnv.getOptions()).thenReturn(new HashMap<String, String>() {{ put(DebtProcessorProperties.SCAN_PACKAGE, expectedScanPackage); }} );

        DebtProcessorProperties properties = new DebtProcessorProperties(mockEnv.getOptions());

        assertEquals(expectedScanPackage, properties.getScanPackage());
    }

    @Test
    public void scanPackageShouldBeDefaultIfAbsentInEnv() {
        DebtProcessorProperties properties = new DebtProcessorProperties(new HashMap<String, String>());

        assertEquals("", properties.getScanPackage());
    }

    @Test
    public void warnThresholdShouldBeReadFromEnvAsPresent() {
        final int expectedWarnThreshold = 10;
        ProcessingEnvironment mockEnv = mock(ProcessingEnvironment.class);
        when(mockEnv.getOptions()).thenReturn(new HashMap<String, String>() {{ put(DebtProcessorProperties.WARN_THRESHOLD, "10"); }} );

        DebtProcessorProperties properties = new DebtProcessorProperties(mockEnv.getOptions());

        assertEquals(expectedWarnThreshold, properties.getWarnThreshold());
    }

    @Test
    public void warnThresholdShouldBeDefaultAsNotPresent() {
        final int expectedWarnThreshold = -1;

        DebtProcessorProperties properties = new DebtProcessorProperties(new HashMap<String, String>());

        assertEquals(expectedWarnThreshold, properties.getWarnThreshold());
    }

    @Test
    public void warnThresholdShouldBeDefaultIfInvalid() {
        final int expectedWarnThreshold = -1;

        DebtProcessorProperties properties = new DebtProcessorProperties(new HashMap<String, String>(){{
            put(DebtProcessorProperties.WARN_THRESHOLD, "INVALID!");
        }});

        assertEquals(expectedWarnThreshold, properties.getWarnThreshold());
    }

    @Test
    public void failThresholdShouldBeReadFromEnvAsPresent() {
        final int expectedFailThreshold = 10;
        ProcessingEnvironment mockEnv = mock(ProcessingEnvironment.class);
        when(mockEnv.getOptions()).thenReturn(new HashMap<String, String>() {{ put(DebtProcessorProperties.FAIL_THRESHOLD, "10"); }} );

        DebtProcessorProperties properties = new DebtProcessorProperties(mockEnv.getOptions());

        assertEquals(expectedFailThreshold, properties.getFailThreshold());
    }

    @Test
    public void failThresholdShouldBeDefaultAsNotPresent() {
        final int expectedFailThreshold = -1;

        DebtProcessorProperties properties = new DebtProcessorProperties(new HashMap<String, String>());

        assertEquals(expectedFailThreshold, properties.getFailThreshold());
    }

    @Test
    public void failThresholdShouldBeDefaultIfInvalid() {
        final int expectedFailThreshold = -1;

        DebtProcessorProperties properties = new DebtProcessorProperties(new HashMap<String, String>(){{
            put(DebtProcessorProperties.FAIL_THRESHOLD, "INVALID!");
        }});

        assertEquals(expectedFailThreshold, properties.getFailThreshold());
    }
}
