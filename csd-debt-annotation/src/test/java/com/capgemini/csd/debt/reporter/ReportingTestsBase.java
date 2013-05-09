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

import com.capgemini.csd.debt.processor.DebtProcessorProperties;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class ReportingTestsBase {

    protected DebtReportingProperties createPropertiesWithEnv(final String reportEnabled) {
        ProcessingEnvironment mockEnv = createMockEnv(reportEnabled, null, null);
        Messager mockMessager = mock(Messager.class);
        DebtReportingProperties debtReportingProperties = new DebtReportingProperties(mockEnv.getOptions(), mockMessager);
        return debtReportingProperties;
    }

    protected DebtReportingProperties createPropertiesWithEnv(final String reportEnabled, final Integer failThreshold, final Integer warnThreshold) {
        ProcessingEnvironment mockEnv = createMockEnv(reportEnabled, failThreshold, warnThreshold);
        Messager mockMessager = mock(Messager.class);
        DebtReportingProperties debtReportingProperties = new DebtReportingProperties(mockEnv.getOptions(), mockMessager);
        return debtReportingProperties;
    }

    private ProcessingEnvironment createMockEnv(final String reportEnabled, final Integer failThreshold, final Integer warnThreshold) {
        ProcessingEnvironment mockEnv = mock(ProcessingEnvironment.class);
        HashMap<String, String> options = new HashMap<String, String>();
        options.put(DebtReportingFactory.REPORT_ENABLED, reportEnabled);
        if (failThreshold != null) {
            options.put(DebtProcessorProperties.FAIL_THRESHOLD, "" + failThreshold.intValue());
        }
        if (warnThreshold != null) {
            options.put(DebtProcessorProperties.WARN_THRESHOLD, "" + warnThreshold.intValue());
        }
        when(mockEnv.getOptions()).thenReturn(options);
        return mockEnv;
    }


}
