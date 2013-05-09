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
import com.capgemini.csd.debt.processor.DebtCollector;
import com.capgemini.csd.debt.processor.DummyDebtRecord;
import org.custommonkey.xmlunit.*;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class HudsonXMLReporterTests extends XMLTestCase {

    private String endgameXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<debt version=\"0.7.0\" timestamp=\"Wed Jan 04 13:44:18 GMT 2012\">" +
            "<file name=\"\">" +
            "<violation beginline=\"\" endline=\"\" begincolumn=\"\" endcolumn=\"\" rule=\"Cheese\" ruleset=\"Puffs\" package=\"\" class=\"\" method=\"\" variable=\"\" priority=\"\"></violation>" +
            "</file>" +
            "</debt>";

    private final String xmlDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
    private final String startDebtTag = "<debt version=\"%s\" timestamp=\"%s\">";
    private final String startFileTag = "<file name=\"%s\">";
    private final String startViolationTag = "<violation beginline=\"%s\" endline=\"%s\" begincolumn=\"%s\" endcolumn=\"%s\" rule=\"%s\" ruleset=\"%s\" package=\"%s\" class=\"%s\" method=\"%s\" variable=\"%s\" priority=\"%s\">";
    private final String endViolationTag = "</violation>";
    private final String endFileTag = "</file>";
    private final String endDebtTag = "</debt>";
    private static final String TEST_VERSION = "0.7.0";
    private static final String TEST_TIMESTAMP = "Timestamp";
    private HudsonXMLDebtReporter reporter;
    private DebtCollector debtCollector;
    private String testPackage;
    private String testPackage2;
    private String testPackageDebtFilename;
    private String testPackageDebtFilename2;
    private String testClassDebtFilename;
    private String testClassName;
    private String testFieldName;

    public HudsonXMLReporterTests (String name) {
        super(name);
    }

    @Before
    public void setUp() {
        reporter = new HudsonXMLDebtReporter();
        debtCollector = new DebtCollector();
        testPackage = "com.test";
        testPackage2 = "com.testtwo";
        testClassName = "TestClass";
        testFieldName = "testField";
        testPackageDebtFilename = "com/test/package.java";
        testPackageDebtFilename2 = "com/testtwo/package.java";
        testClassDebtFilename = "com/test/TestClass.java";

        XMLUnit.setControlParser("org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
        XMLUnit.setTestParser("org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
        XMLUnit.setSAXParserFactory("org.apache.xerces.jaxp.SAXParserFactoryImpl");
        XMLUnit.setTransformerFactory("org.apache.xalan.processor.TransformerFactoryImpl");

    }

    @Test
    public void testEmptyDebtCollection() throws IOException, SAXException {
        String controlXML = createStartOfControlXML() +
                createEndOfControlXML();
        DebtCollection emptyDebtCollection = new DebtCollection();

        String generatedXML = reporter.generateXML(emptyDebtCollection, TEST_VERSION, TEST_TIMESTAMP);

        assertXMLEqual(controlXML, generatedXML);
    }

    @Test
    public void testSinglePackageLevelDebtRecord() throws IOException, SAXException {
        String debtDescription = "Test package debt";
        String controlXML = createStartOfControlXML() +
                createStartFileTag(testPackageDebtFilename) +
                createViolation(testPackage, debtDescription) +
                createEndFileTag() +
                createEndOfControlXML();
        debtCollector.collectDebt(createMockPackageElement(testPackage), new DummyDebtRecord(debtDescription, 1));

        String generatedXML = reporter.generateXML(debtCollector.getDebtCollection(), TEST_VERSION, TEST_TIMESTAMP);

        assertXMLEqual(controlXML, generatedXML);
    }

    @Test
    public void testSinglePackageWithCountOf2() throws IOException, SAXException {
        String debtDescription = "Test package debt";
        String controlXML = createStartOfControlXML() +
                            createStartFileTag(testPackageDebtFilename) +
                            createViolation(testPackage, debtDescription) +
                            createViolation(testPackage, debtDescription) +
                            createEndFileTag() +
                            createEndOfControlXML();
        debtCollector.collectDebt(createMockPackageElement(testPackage), new DummyDebtRecord(debtDescription, 2));

        String generatedXML = reporter.generateXML(debtCollector.getDebtCollection(), TEST_VERSION, TEST_TIMESTAMP);

        assertXMLEqual(controlXML, generatedXML);
    }

    @Test
    public void testMultiplePackageRecords() throws IOException, SAXException {
        String debtDescription1 = "Test package debt 1";
        String debtDescription2 = "Test package debt 2";
        String controlXML = createStartOfControlXML() +
                createStartFileTag(testPackageDebtFilename) +
                createViolation(testPackage, debtDescription1) +
                createEndFileTag() +
                createStartFileTag(testPackageDebtFilename2) +
                createViolation(testPackage2, debtDescription2) +
                createEndFileTag() +
                createEndOfControlXML();
        debtCollector.collectDebt(createMockPackageElement(testPackage), new DummyDebtRecord(debtDescription1, 1));
        debtCollector.collectDebt(createMockPackageElement(testPackage2), new DummyDebtRecord(debtDescription2, 1));

        String generatedXML = reporter.generateXML(debtCollector.getDebtCollection(), TEST_VERSION, TEST_TIMESTAMP);

        assertXMLEqualIgnoringOrder(controlXML, generatedXML);
    }

    @Test
    public void testMultiplePackageRecordsWithMultipleDebtCount() throws IOException, SAXException {
        String debtDescription1 = "Test package debt 1";
        String debtDescription2 = "Test package debt 2";
        String controlXML = createStartOfControlXML() +
                createStartFileTag(testPackageDebtFilename) +
                createViolation(testPackage, debtDescription1) +
                createViolation(testPackage, debtDescription1) +
                createEndFileTag() +
                createStartFileTag(testPackageDebtFilename2) +
                createViolation(testPackage2, debtDescription2) +
                createViolation(testPackage2, debtDescription2) +
                createEndFileTag() +
                createEndOfControlXML();
        debtCollector.collectDebt(createMockPackageElement(testPackage), new DummyDebtRecord(debtDescription1, 2));
        debtCollector.collectDebt(createMockPackageElement(testPackage2), new DummyDebtRecord(debtDescription2, 2));

        String generatedXML = reporter.generateXML(debtCollector.getDebtCollection(), TEST_VERSION, TEST_TIMESTAMP);

        assertXMLEqualIgnoringOrder(controlXML, generatedXML);
    }

    @Test
    public void testClassLevelDebt() throws IOException, SAXException {
        String debtDescription = "Test class debt";
        String controlXML = createStartOfControlXML() +
                            createStartFileTag(testClassDebtFilename) +
                            createViolation(testPackage, testClassName, debtDescription) +
                            createEndFileTag() +
                            createEndOfControlXML();
        debtCollector.collectDebt(createMockClassElement(testPackage, testClassName), new DummyDebtRecord(debtDescription, 1));

        String generatedXML = reporter.generateXML(debtCollector.getDebtCollection(), TEST_VERSION, TEST_TIMESTAMP);

        assertXMLEqual(controlXML, generatedXML);
    }

    @Test
    public void testFieldLevelDebt() throws IOException, SAXException {
        String debtDescription = "Test class debt";
        String controlXML = createStartOfControlXML() +
                            createStartFileTag(testClassDebtFilename) +
                            createViolation(testPackage, testClassName, testFieldName, debtDescription) +
                            createEndFileTag() +
                            createEndOfControlXML();
        debtCollector.collectDebt(createMockFieldElement(testPackage, testClassName, testFieldName), new DummyDebtRecord(debtDescription, 1));

        String generatedXML = reporter.generateXML(debtCollector.getDebtCollection(), TEST_VERSION, TEST_TIMESTAMP);

        assertXMLEqual(controlXML, generatedXML);
    }

    private Element createMockFieldElement(String packageName, String className, String fieldName) {
        Element mockFieldElement = mock(Element.class);
        when(mockFieldElement.toString()).thenReturn(packageName + "." + className + "." + fieldName);
        when(mockFieldElement.getKind()).thenReturn(ElementKind.FIELD);
        Element mockClassElement = createMockClassElement(packageName, className);
        when(mockFieldElement.getEnclosingElement()).thenReturn(mockClassElement);
        return mockFieldElement;
    }

    private String createViolation(String testPackage, String className, String fieldName, String description) {
        // TODO: overload this so that the attributes are in ever increasing levels of detail: package, class, field/method
        return "<violation rule=\"" + description + "\" ruleset=\"Debt\" package=\"" + testPackage + "\" class=\"" + testPackage + "." + className + "\" field=\"" + testPackage + "." + className + "." + fieldName + "\" />";
    }

//    @Test
//    public void testTimeToRefactorAndTidyUp() {
//        assertTrue("Time to refactor", false);
//    }

    private void assertXMLEqualIgnoringOrder(String controlXML, String generatedXML) throws SAXException, IOException {
        Diff d = new Diff(controlXML, generatedXML);
        d.overrideElementQualifier(new ElementNameAndAttributeQualifier());
        assertXMLEqual(d,true);
    }

    // TODO - @Debts !!

    private Element createMockClassElement(String packageName, String testClassName) {
        Element mockClassElement = mock(Element.class);
        when(mockClassElement.toString()).thenReturn(packageName + "." + testClassName);
        when(mockClassElement.getKind()).thenReturn(ElementKind.CLASS);
        Element mockPackage = createMockPackageElement(packageName);
        when(mockClassElement.getEnclosingElement()).thenReturn(mockPackage);
        return mockClassElement;
    }

    private String createViolation(String testPackage, String className, String description) {
        return "<violation rule=\"" + description + "\" ruleset=\"Debt\" package=\"" + testPackage + "\" class=\"" + testPackage + "." + className + "\" />";
    }


    private String startDebtTag(String testVersion, String testTimestamp) {
        return String.format(startDebtTag, testVersion, testTimestamp);
    }

    private String createEndOfControlXML() {
        return  endDebtTag;
    }

    private String createEndFileTag() {
        return "</file>";
    }

    private String createStartOfControlXML() {
        return xmlDeclaration + startDebtTag(TEST_VERSION, TEST_TIMESTAMP);
    }

    private Element createMockPackageElement(String testPackage) {
        Element mockPackageElement = mock(Element.class);
        when(mockPackageElement.toString()).thenReturn(testPackage);
        when(mockPackageElement.getKind()).thenReturn(ElementKind.PACKAGE);
        return mockPackageElement;
    }

    private String createViolation(String testPackage, String description) {
        return "<violation rule=\"" + description + "\" ruleset=\"Debt\" package=\"" + testPackage + "\"/>";
    }


    private String createStartFileTag(String testFilename) {
        return String.format(startFileTag, testFilename);
    }


}
