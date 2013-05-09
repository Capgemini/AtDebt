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

import com.capgemini.csd.debt.processor.DebtCollection;
import com.capgemini.csd.debt.processor.DebtInstanceInfo;
import com.capgemini.csd.debt.reporter.impl.ConsoleReporter;
import com.capgemini.csd.debt.reporter.impl.XMLDebtReporter;

import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class DebtReportingFactory {

    public static final String REPORT_ENABLED = "com.capgemini.csd.debt.report.enabled";

    private DebtReportingProperties debtReportingProperties;
    private List<Reporter> registeredReporters;

    protected boolean reportEnabled = false;

    protected DebtReportingFactory(DebtReportingProperties debtReportingProperties) throws IOException {
        registeredReporters = new ArrayList<Reporter>();
        this.debtReportingProperties = debtReportingProperties;
        initialiseFactory();
    }

    private void initialiseFactory() throws IOException {
        setReportEnabledFromProperties();
        initialiseReporters();
    }

    public void setReportEnabledFromProperties() {
        if (debtReportingProperties.containsKey(REPORT_ENABLED)) {
            reportEnabled = Boolean.parseBoolean(debtReportingProperties.get(REPORT_ENABLED));
        }
    }

    private void initialiseReporters() throws IOException {
        registeredReporters.add(new ConsoleReporter(debtReportingProperties));
        registeredReporters.add(new XMLDebtReporter(debtReportingProperties));
    }

    public static DebtReportingFactory setupReportingFactory(DebtReportingProperties debtReportingProperties) throws IOException {
        DebtReportingFactory factory = new DebtReportingFactory(debtReportingProperties);
        return factory;
    }

    public Set<String> getSupportedOptions() {
        Set<String> options = new HashSet<String>();

        options.add(REPORT_ENABLED);

        for (Reporter reporter : registeredReporters) {
            options.addAll(reporter.getSupportedOptions());
        }

        return options;
    }

    public boolean getReportEnabled() {
        return reportEnabled;
    }

    public void report(DebtCollection debtCollection, DebtReportingProperties debtReportingProperties) {
        for (Reporter reporter: registeredReporters) {
            try {
                reporter.report(debtCollection, debtReportingProperties);
            } catch (IOException e) {
                debtReportingProperties.getMessager().printMessage(Diagnostic.Kind.ERROR, "[ERROR] Exception thrown by reporter: " + reporter.getClass().getName());
                debtReportingProperties.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }
    }

    public void reportDebtInfo(DebtInstanceInfo debtInstanceInfo) {
        for (Reporter reporter: registeredReporters) {
            reporter.reportDebtInfo(debtInstanceInfo);
        }
    }
}
