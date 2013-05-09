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

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class DebtProcessorProperties {

    protected static final String SCAN_PACKAGE = "com.capgemini.csd.debt.scan";
    public static final String WARN_THRESHOLD = "com.capgemini.csd.debt.warnthreshold";
    public static final String FAIL_THRESHOLD = "com.capgemini.csd.debt.failthreshold";

    private static final String DEFAULT_SCAN_PACKAGE = "";
    private static final int DEFAULT_WARN_THRESHOLD = -1;
    private static final int DEFAULT_FAIL_THRESHOLD = -1;

    private String scanPackage = DEFAULT_SCAN_PACKAGE;
    private int failThreshold = DEFAULT_FAIL_THRESHOLD;
    private int warnThreshold = DEFAULT_WARN_THRESHOLD;

    public DebtProcessorProperties(Map<String, String> properties) {
        initialiseScanPackageFromProperties(properties);
        initialiseWarnThresholdFromProperties(properties);
        initialiseFailThresholdFromProperties(properties);
    }

    private void initialiseScanPackageFromProperties(Map<String, String> properties) {
        if (properties.containsKey(SCAN_PACKAGE)) {
            scanPackage = properties.get(SCAN_PACKAGE);
        }
    }

    private void initialiseWarnThresholdFromProperties(Map<String, String> properties) {
        try {
            warnThreshold = Integer.valueOf(properties.get(WARN_THRESHOLD));
        } catch (NumberFormatException nfex) {
            // silently fail, as already initialised to default
        }
    }

    public void initialiseFailThresholdFromProperties(Map<String, String> properties) {
        try {
            failThreshold = Integer.valueOf(properties.get(FAIL_THRESHOLD));
        } catch (NumberFormatException nfex) {
            // silently fail, as already initialised to default
        }
    }

    public String getScanPackage() {
        return scanPackage;
    }

    public int getFailThreshold() {
        return failThreshold;
    }

    public int getWarnThreshold() {
        return warnThreshold;
    }

    public static Set<String> getSupportedOptions() {
        return new HashSet<String>() {{
            add(SCAN_PACKAGE);
            add(FAIL_THRESHOLD);
            add(WARN_THRESHOLD);
        }};
    }
}
