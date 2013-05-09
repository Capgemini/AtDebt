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

import java.util.Iterator;
import com.capgemini.csd.debt.Debt;
import javax.lang.model.element.Name;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class AddRecordTests {

    DebtCollector recorder =  null;

    Element firstMockedPackageElement;
    String firstExpectedPackageName = "com.capgemini.csd.debt.processor";
    Name firstMockedPackageName;

    Element firstMockedClassElement;
    String firstExpectedClassName = "MyClass";
    Name firstMockedClassName;

    Element firstMockedMethodElement;
    String firstExpectedMethodName = "main(java.lang.String[])";
    Name firstMockedMethodName;

    Element secondMockedMethodElement;
    String secondExpectedMethodName = "test()";
    Name secondMockedMethodName;

    Element firstMockedFieldElement;
    String firstExpectedFieldName = "test";
    Name firstMockedFieldName;

    String firstExpectedDebtDesc = "This is the first Debt description";
    String secondExpectedDebtDesc = "This is the second Debt description";

    String secondExpectedPackageName = "com.capgemini.csd.debt.processor.another";

    Debt firstMockedDebtRecord;
    Debt secondMockedDebtRecord;


    public AddRecordTests() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        recorder = new DebtCollector();
        setupFirstMockedPackageElement();
        setupFirstMockedClassElement();
        setupFirstMockedMethodElement();
        setupSecondMockedMethodElement();
        setupFirstMockedFieldElement();
        setupFirstMockedDebtRecord();
        setupSecondMockedDebtRecord();
    }

    private void setupFirstMockedFieldElement() {
        firstMockedFieldElement = mock(Element.class);
        when(firstMockedFieldElement.toString()).thenReturn(firstExpectedFieldName);
        when(firstMockedFieldElement.getKind()).thenReturn(ElementKind.FIELD);
        firstMockedFieldName = mock(Name.class);
        when(firstMockedFieldElement.getSimpleName()).thenReturn(firstMockedFieldName);
        when(firstMockedFieldElement.getEnclosingElement()).thenReturn(firstMockedClassElement);
    }

    private void setupSecondMockedDebtRecord() {
        secondMockedDebtRecord = mock(Debt.class);
        when(secondMockedDebtRecord.desc()).thenReturn(secondExpectedDebtDesc);
        when(secondMockedDebtRecord.count()).thenReturn(1);
    }

    private void setupFirstMockedDebtRecord() {
        firstMockedDebtRecord = mock(Debt.class);
        when(firstMockedDebtRecord.desc()).thenReturn(firstExpectedDebtDesc);
        when(firstMockedDebtRecord.count()).thenReturn(1);
    }

    private void setupSecondMockedMethodElement() {
        secondMockedMethodElement = mock(Element.class);
        when(secondMockedMethodElement.toString()).thenReturn(secondExpectedMethodName);
        when(secondMockedMethodElement.getKind()).thenReturn(ElementKind.ENUM.PACKAGE);
        secondMockedMethodName = mock(Name.class);
        when(secondMockedMethodElement.getSimpleName()).thenReturn(secondMockedMethodName);
        when(secondMockedMethodElement.getEnclosingElement()).thenReturn(firstMockedClassElement);
    }

    private void setupFirstMockedMethodElement() {
        firstMockedMethodElement = mock(Element.class);
        when(firstMockedMethodElement.toString()).thenReturn(firstExpectedMethodName);
        when(firstMockedMethodElement.getKind()).thenReturn(ElementKind.ENUM.METHOD);
        firstMockedMethodName = mock(Name.class);
        when(firstMockedMethodElement.getSimpleName()).thenReturn(firstMockedMethodName);
        when(firstMockedMethodElement.getEnclosingElement()).thenReturn(firstMockedClassElement);
    }

    private void setupFirstMockedClassElement() {
        firstMockedClassElement = mock(Element.class);
        when(firstMockedClassElement.toString()).thenReturn(firstExpectedClassName);
        firstMockedClassName = mock(Name.class);
        when(firstMockedClassElement.getSimpleName()).thenReturn(firstMockedClassName);
        when(firstMockedClassElement.getEnclosingElement()).thenReturn(firstMockedPackageElement);
    }

    private void setupFirstMockedPackageElement() {
        firstMockedPackageElement = mock(Element.class);
        when(firstMockedPackageElement.toString()).thenReturn(firstExpectedPackageName);
        when(firstMockedPackageElement.getKind()).thenReturn(ElementKind.ENUM.PACKAGE);
        firstMockedPackageName = mock(Name.class);
        when(firstMockedPackageElement.getSimpleName()).thenReturn(firstMockedPackageName);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void addDebtRecord () {
        
        System.out.println("\naddDebtRecord");
        
        boolean debtAdded = false;
        int amountOfDebt = 1;
        MethodRecord methodRecord = new MethodRecord(firstMockedMethodElement, new ClassRecord(firstMockedClassElement, new PackageRecord(firstMockedPackageElement)));
        
        // Add the first debt record
        debtAdded = methodRecord.recordDebt(firstMockedDebtRecord);
        
        assertTrue("The debt was not added.", debtAdded);
        assertEquals("The amount of debt was not as expected.", 
                amountOfDebt, 
                methodRecord.debtRecords.size());
        assertEquals("The debt description was not as expected.", 
                firstExpectedDebtDesc, 
                methodRecord.debtRecords.iterator().next().desc());
        
        // Try and add the first debt record again
        debtAdded = methodRecord.recordDebt(firstMockedDebtRecord);
        
        assertFalse("The debt was added again.", debtAdded);
        assertEquals("The amount of debt was not as expected.", 
                amountOfDebt, 
                methodRecord.debtRecords.size());
        
        // Add the second debt record to the same method element
        amountOfDebt = 2;
        debtAdded = methodRecord.recordDebt(secondMockedDebtRecord);
        
        assertTrue("The debt was not added.", debtAdded);
        assertEquals("The amount of debt was not as expected.", 
                amountOfDebt, 
                methodRecord.debtRecords.size());
        Iterator<Debt> i = methodRecord.debtRecords.iterator();
        assertNotSame("The debt descriptions were not different.", 
                i.next().desc(), 
                i.next().desc());
    }
    
    @Test
    public void addDuplicateMethodRecords() {
    
        System.out.println("\naddDuplicateMethodRecords");
        
        MethodRecord firstResult;
        MethodRecord secondResult;
        MethodRecord thirdResult;
        int expectedSize = 1;
        ClassRecord classRecord = new ClassRecord(firstMockedClassElement, new PackageRecord(firstMockedPackageElement));
        
        // Add the first method element
        firstResult = classRecord.addMethodRecord(firstMockedMethodElement);
        
        assertNotNull("Recording the debt didn't return a MethodRecord result.", firstResult);
        assertEquals("The name of the method was not as expected.", firstExpectedMethodName, firstResult.getName());
        assertEquals("The number of method records was not as expected.", expectedSize, classRecord.getMethodRecords().size());
        
        // Try and add the first method element again
        expectedSize = 1; // no change
        secondResult = classRecord.addMethodRecord(firstMockedMethodElement);
        
        assertNotNull("Recording the debt didn't return a MethodRecord result.", secondResult);
        assertEquals("The name of the method was not as expected.", firstExpectedMethodName, secondResult.getName());
        assertEquals("The number of method records was not as expected.", expectedSize, classRecord.getMethodRecords().size());
        assertEquals("The method records returned were not the same.", firstResult, secondResult);
        
        // Try and add a new, second method element
        expectedSize = 2;
        thirdResult = classRecord.addMethodRecord(secondMockedMethodElement);
        
        assertNotNull("Recording the debt didn't return a MethodRecord result.", thirdResult);
        assertEquals("The name of the method was not as expected.", secondExpectedMethodName, thirdResult.getName());
        assertEquals("The number of method records was not as expected.", expectedSize, classRecord.getMethodRecords().size());
        assertNotSame("The method records returned were the same.", firstResult, thirdResult);
    }
    
    @Test
    public void addPackageLevelDebt() {
        
        System.out.println("\naddPackageLevelDebt");
        
        int expectedRecordNo = 1;
        boolean expectedRecordingResult = true;
        
        // Add a new package element
        recorder.collectDebt(firstMockedPackageElement, firstMockedDebtRecord);
        
        assertTrue("Recording the debt didn't return true", expectedRecordingResult);
        assertTrue("There were no debtRecords", !recorder.getDebtCollection().getPackageRecords().isEmpty());
        assertEquals("There was not the expected single package-level debtRecord.", expectedRecordNo,  recorder.getDebtCollection().getPackageRecords().size());
        
        // Try and add the first package element again
        expectedRecordingResult = recorder.collectDebt(firstMockedPackageElement, firstMockedDebtRecord);
        System.out.println("package records: " + recorder.getDebtCollection().getPackageRecords().size());
        
        assertFalse("Recording the debt didn't return false", expectedRecordingResult);
        assertEquals("There was not still only one package-level debtRecord.", expectedRecordNo,  recorder.getDebtCollection().getPackageRecords().size());
        
        expectedRecordNo = 2;
        
        // Now add the second package element
        expectedRecordingResult = recorder.collectDebt(secondMockedMethodElement, firstMockedDebtRecord);
        
        assertTrue("Recording the debt didn't return true", expectedRecordingResult);
        assertEquals("There were not a pair of package-level debtRecords.", expectedRecordNo,  recorder.getDebtCollection().getPackageRecords().size());
    }
    
    @Test
    public void recordMethodLevelDebt() {
        
        System.out.println("\nrecordMethodLevelDebt");
        
        boolean result = false;
        int expectedNumberOfClassElements = 1;
        int expectedNumberOfMethodElements = 1;
        int expectedNumberOfDebtRecords = 1;
        
        PackageRecord packageRecord = new PackageRecord(firstMockedPackageElement);
        
        // Add the first method element and record the debt against it
        result = packageRecord.recordMethodDebt(firstMockedMethodElement, firstMockedDebtRecord);
        
        assertTrue("Recording the debt didn't return true.", result);
        assertEquals("The number of class elements was not as expected.", 
                expectedNumberOfClassElements, 
                packageRecord.getClassRecords().size());
        assertEquals("The number of method elements was not as expected.", 
                expectedNumberOfMethodElements, 
                packageRecord.getClassRecords().iterator().next().getMethodRecords().size());
        assertEquals("The name of the method element was not as expected.",
                firstExpectedMethodName, 
                packageRecord.getClassRecords().iterator().next().getMethodRecords().iterator().next().getName());
        assertEquals("The number of method-level debt records was not as expected.",
                expectedNumberOfDebtRecords,
                packageRecord.getClassRecords().iterator().next().getMethodRecords().iterator().next().debtRecords.size());

        // Try and add the first method element and associated debt again
        expectedNumberOfClassElements = 1;
        expectedNumberOfMethodElements = 1;
        expectedNumberOfDebtRecords = 1;
        
        result = packageRecord.recordMethodDebt(firstMockedMethodElement, firstMockedDebtRecord);
        
        assertFalse("Recording the debt didn't return false.", result);
        assertEquals("The number of class elements was not as expected.", 
                expectedNumberOfClassElements, 
                packageRecord.getClassRecords().size());
        assertEquals("The number of method elements was not as expected.", 
                expectedNumberOfMethodElements, 
                packageRecord.getClassRecords().iterator().next().getMethodRecords().size());
        assertEquals("The name of the method element was not as expected.",
                firstExpectedMethodName, 
                packageRecord.getClassRecords().iterator().next().getMethodRecords().iterator().next().getName());
        assertEquals("The number of method-level debt records was not as expected.",
                expectedNumberOfDebtRecords,
                packageRecord.getClassRecords().iterator().next().getMethodRecords().iterator().next().debtRecords.size());
        
    }
    
    @Test
    public void addMethodLevelDebt() {
        
        System.out.println("\naddMethodLevelDebt");
        
        int expectedRecordNo = 1;
        boolean expectedRecordingResult = true;
        
        // Record the first method element
        expectedRecordingResult = recorder.collectDebt(firstMockedMethodElement, firstMockedDebtRecord);
        
        assertTrue("Recording the debt didn't return true", expectedRecordingResult);
        assertTrue("There were no method-level debtRecords", !recorder.getDebtCollection().getPackageRecords().isEmpty());
        assertEquals("There was not the expected single debtRecord.", expectedRecordNo,  recorder.getDebtCollection().getPackageRecords().size());
        
        // Try and record the first method element again
        expectedRecordingResult = recorder.collectDebt(firstMockedMethodElement, firstMockedDebtRecord);
        
        assertFalse("Recording the debt didn't return false", expectedRecordingResult);
        assertEquals("There was not still only one method-level debtRecord.", expectedRecordNo,  recorder.getDebtCollection().getPackageRecords().size());
    }

    @Test
    public void addFieldLevelDebt() {

        System.out.println("\naddFieldLevelDebt");

        int expectedRecordNo = 1;
        boolean expectedRecordingResult = true;

        // Record the first field element
        expectedRecordingResult = recorder.collectDebt(firstMockedFieldElement, firstMockedDebtRecord);

        assertTrue("Recording the debt didn't return true", expectedRecordingResult);
        assertTrue("There were no field-level debtRecords", !recorder.getDebtCollection().getPackageRecords().isEmpty());
        assertEquals("There was not the expected single debtRecord.", expectedRecordNo,  recorder.getDebtCollection().getPackageRecords().size());

        // Try and record the first method element again
        expectedRecordingResult = recorder.collectDebt(firstMockedFieldElement, firstMockedDebtRecord);

        assertFalse("Recording the debt didn't return false", expectedRecordingResult);
        assertEquals("There was not still only one method-level debtRecord.", expectedRecordNo,  recorder.getDebtCollection().getPackageRecords().size());

    }

}
