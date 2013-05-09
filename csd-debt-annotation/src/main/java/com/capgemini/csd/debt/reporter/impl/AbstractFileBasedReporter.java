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
import com.capgemini.csd.debt.reporter.DebtReportingProperties;
import com.capgemini.csd.debt.reporter.Reporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public abstract class AbstractFileBasedReporter implements Reporter {

    public static final String REPORT_DIR = "com.capgemini.csd.debt.report.dir";
    public static final String REPORT_FILE = "com.capgemini.csd.debt.report.name";

    // TODO - consider how to make version parameterised
    protected String version = "0.7.0";
    protected Map<String, String> properties;
    private String reportDirLocation = "";
    private File reportDir;
    private File reportFile;
    protected Writer writer;

    protected AbstractFileBasedReporter(Map<String, String> properties) {
        //To change body of created methods use File | Settings | File Templates.
        this.properties = properties;
    }

    protected AbstractFileBasedReporter() {
    }

    protected void init() throws IOException {
        setReportDirLocationFromProperties();
        createReportDirIfNeeded();
        createReportFileWriter(setReportFileFromFromEnv(reportDir));
    }

    private void createReportFileWriter(File reportFile) throws IOException {
        writer = new FileWriter(reportFile);
    }

    protected void setReportDirLocationFromProperties() {
        if (properties.containsKey(REPORT_DIR)) {
            reportDirLocation = properties.get(XMLDebtReporter.REPORT_DIR);
        }
    }

    protected final void createReportDirIfNeeded() {
        if (reportDir == null) {
            reportDir = new File(reportDirLocation);
        }
        if (!reportDir.exists()) {
            reportDir.mkdirs();
        }
    }

    protected final File setReportFileFromFromEnv(File reportDir) throws IOException {
        if (isReportFilePresentAndValid()) {
            reportFile = new File(reportDir.getPath() + "/" + properties.get(REPORT_FILE));
        } else {
            reportFile = new File(reportDir.getPath() + "/debt.xml");
        }
        return reportFile;
    }

    private boolean isReportFilePresentAndValid() {
        return properties.containsKey(REPORT_FILE) && !"".equals(properties.get(REPORT_FILE));
    }

    @Override
    public abstract void report(DebtCollection debtCollection, DebtReportingProperties debtReportingProperties);

    @Override
    public Set<String> getSupportedOptions() {
        return new HashSet<String>() {{
            add(REPORT_DIR);
            add(REPORT_FILE);
        }};
    }

    @Override
    public abstract void reportDebtInfo(DebtInstanceInfo debtInstanceInfo);

    protected final String getReportDirLocation() {
        return reportDirLocation;
    }

    protected final void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    protected final void setReportDir(File reportDir) {
        this.reportDir = reportDir;
    }

    public File getReportFile() {
        return reportFile;
    }
}
