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

import org.junit.Test;

import javax.annotation.processing.Messager;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class DebtReportingFactoryTests {

    @Test
    public void reportEnabledShouldBeReadFromEnvAsPresent() throws IOException {
        Map<String, String> mockOptions = new HashMap<String, String>() {{
            put(DebtReportingFactory.REPORT_ENABLED,"true");
        }};
        Messager mockMessager = mock(Messager.class);

        DebtReportingProperties debtReportingProperties = new DebtReportingProperties(mockOptions, mockMessager);
        DebtReportingFactory factory = DebtReportingFactory.setupReportingFactory(debtReportingProperties);

        assertEquals(true, factory.getReportEnabled());
    }

    @Test
    public void reportEnabledShouldBeDefaultIfAbsentInEnv() throws IOException {
        Map<String, String> mockOptions = new HashMap<String, String>();
        Messager mockMessager = mock(Messager.class);

        DebtReportingProperties debtReportingProperties = new DebtReportingProperties(mockOptions, mockMessager);
        DebtReportingFactory factory = DebtReportingFactory.setupReportingFactory(debtReportingProperties);

        assertEquals(false, factory.getReportEnabled());
    }



}
