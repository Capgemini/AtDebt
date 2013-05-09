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
package com.capgemini.csd.debt;

import com.capgemini.csd.debt.processor.AddRecordTests;
import com.capgemini.csd.debt.processor.DebtInstanceInfoTests;
import com.capgemini.csd.debt.processor.OnInitialisationOfDebtProcessor;
import com.capgemini.csd.debt.processor.ProcessorTests;
import com.capgemini.csd.debt.processor.ReportingBaseTests;
import com.capgemini.csd.debt.reporter.DebtReportingFactoryTests;
import com.capgemini.csd.debt.reporter.ReportingTests;
import com.capgemini.csd.debt.reporter.impl.ConsoleReporterTests;
import com.capgemini.csd.debt.reporter.impl.HudsonXMLReporterTests;
import com.capgemini.csd.debt.reporter.impl.XMLDebtReporterTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
      OnInitialisationOfDebtProcessor.class,
      ReportingBaseTests.class,
      AddRecordTests.class,
      ReportingTests.class,
      DebtReportingFactoryTests.class,
      XMLDebtReporterTests.class,
      ConsoleReporterTests.class,
      ProcessorTests.class,
      DebtInstanceInfoTests.class,
      HudsonXMLReporterTests.class
})
public class DebtProcessingTestSuite {}
