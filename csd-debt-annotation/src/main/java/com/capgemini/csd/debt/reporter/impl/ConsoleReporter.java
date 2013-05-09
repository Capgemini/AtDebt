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
import com.capgemini.csd.debt.processor.DebtInstanceInfo;
import com.capgemini.csd.debt.processor.DebtProcessorProperties;
import com.capgemini.csd.debt.reporter.DebtReportingProperties;
import com.capgemini.csd.debt.reporter.Reporter;
import com.capgemini.csd.debt.reporter.ReporterInitialisationException;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class ConsoleReporter implements Reporter {

    private Messager messager;

    public ConsoleReporter (DebtReportingProperties debtReportingProperties) {
        if (debtReportingProperties.getMessager() == null) {
            throw new ReporterInitialisationException("Messager cannot be null");
        }
        messager = debtReportingProperties.getMessager();
    }

    public Messager getMessager() {
        return messager;
    }

    @Override
    public void report(DebtCollection debtCollection, DebtReportingProperties debtReportingProperties) {
        DebtProcessorProperties properties = debtReportingProperties.getDebtProcessingProperties();
        printReportSummary(debtCollection, properties);
        if (isFailThresholdExceeded(debtCollection, properties)) {
            printFailMessage(debtCollection, properties);
        } else if (isWarnThresholdExceeded(debtCollection, properties)) {
            printWarnMessage(debtCollection, properties);
        }
    }

    private void printWarnMessage(DebtCollection debtCollection, DebtProcessorProperties properties) {
        messager.printMessage(Diagnostic.Kind.MANDATORY_WARNING,
                "Technical debt [" + debtCollection.getCount() + "] has "
                        + "exceeded the warn threshold [" + properties.getWarnThreshold() + "].");
    }

    private void printFailMessage(DebtCollection debtCollection, DebtProcessorProperties properties) {
        messager.printMessage(Diagnostic.Kind.ERROR,
                "Technical debt [" + debtCollection.getCount() + "] has "
                        + "exceeded the fail threshold [" + properties.getFailThreshold() + "]. Build has failed.");
    }

    private boolean isWarnThresholdExceeded(DebtCollection debtCollection, DebtProcessorProperties properties) {
        return properties.getWarnThreshold() > 0
                       &&
                   debtCollection.getCount() > properties.getWarnThreshold();
    }

    private boolean isFailThresholdExceeded(DebtCollection debtCollection, DebtProcessorProperties properties) {
        return properties.getFailThreshold() > 0
                &&
            debtCollection.getCount() > properties.getFailThreshold();
    }

    private void printReportSummary(DebtCollection debtCollection, DebtProcessorProperties properties) {
        messager.printMessage(
                Diagnostic.Kind.NOTE, "\n------------------------------------------------------------------------"
                + "\nPROCESSED: count = " + debtCollection.getCount() + ", fail threshold: " + properties.getFailThreshold() + ", warning threshold: " + properties.getWarnThreshold()
                + "\n------------------------------------------------------------------------"
                + "\nTOTAL ACCRUED DEBT: " + debtCollection.getCount()
                + "\n------------------------------------------------------------------------");
    }

    @Override
    public Set<String> getSupportedOptions() {
        return new HashSet<String>();
    }

    @Override
    public void reportDebtInfo(DebtInstanceInfo debtInstanceInfo) {
        messager.printMessage(Diagnostic.Kind.NOTE,
                "\n[DEBT] " + debtInstanceInfo.getContext() + " [desc: "
                        + debtInstanceInfo.getDesc() + "] " + "[count: " + debtInstanceInfo.getCount() + "]", debtInstanceInfo.getElement());
    }

}
