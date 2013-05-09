
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

import com.capgemini.csd.debt.Debt;
import com.capgemini.csd.debt.Debts;
import com.capgemini.csd.debt.reporter.DebtReportingFactory;
import com.capgemini.csd.debt.reporter.DebtReportingProperties;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * An @Debt annotation processor that runs at compile time to find
 * {@link Element}s with the @Debt or @Debts annotations.  Scanning of the classes for annotations is limited to a
 * scan package - by specifying the root package using the com.capgemini.csd.debt.scan property.  Additionally a
 * warning threshold can be specified using the com.capgemini.csd.debt.warnthreshold property, and if required a fail threshold
 * using the com.capgemini.csd.debt.failthreshold property.
 *
 * Note: this annotation can only be used in Java 6 and upwards.
 *
 * @author Andrew Harmel-Law
 * @author Wadud Ruf
 * @author Rob Horn
 */
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes({
        "com.capgemini.csd.debt.Debt",
        "com.capgemini.csd.debt.Debts"
})
public class DebtProcessor extends AbstractProcessor {

    private DebtProcessorProperties debtProcessorProperties = null;
    private DebtReportingProperties debtReportingProperties;
    private DebtReportingFactory debtReportingFactory = null;
    private DebtCollector debtCollector;
    private int count = 0;

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment processor) {
        // If we've already processed this annotation then don't do anything further.
        if (processor.processingOver()) return true;
        processAnnotations(annotations, processor);
        debtReportingFactory.report(debtCollector.getDebtCollection(), debtReportingProperties);
        return true;
    }

    private void processAnnotations(Set<? extends TypeElement> annotations, RoundEnvironment processor) {
        for (TypeElement annotation : annotations) {
            processAnnotation(annotation, processor);
        }
    }

    private void processAnnotation(TypeElement annotation, RoundEnvironment processor) {
        for (Element element : processor.getElementsAnnotatedWith(annotation)) {
            count += processAnnotatedElement(element);
        }
    }

    private int processAnnotatedElement(Element element) {
        if (isInScanPackage(element)) {
            return processElementInScanPackage(element);
        } else {
            return 0; // we have added zero debt
        }
    }

    private boolean isInScanPackage(Element element) {
        Element enclosingElement = element;
        while (enclosingElement != null) {
            if (matchesScanPackage(enclosingElement)) {
                return true;
            }
            enclosingElement = enclosingElement.getEnclosingElement();
        }
        return false;
    }

    private boolean matchesScanPackage(Element element) {
        return element.toString().startsWith(debtProcessorProperties.getScanPackage());
    }

    private int processElementInScanPackage(Element element) {
        if (isDebtsElementPresent(element)) {
            return processDebtsElement(element);
        } else {
            return processDebtElement(element);
        }
    }

    private boolean isDebtsElementPresent(Element element) {
        return element.getAnnotation(Debts.class) != null;
    }

    private int processDebtsElement(Element element) {
        Debts debts = element.getAnnotation(Debts.class);
        int childDebtCount = 0;
        for (int i = 0; i < debts.value().length; i++) {
            childDebtCount += recordDebtInfo(element, debts.value()[i]);
        }
        return childDebtCount;
    }

    private int processDebtElement(Element element) {
        return recordDebtInfo(element, element.getAnnotation(Debt.class));
    }

    private int recordDebtInfo(Element element, Debt debt) {
        DebtInstanceInfo debtInstanceInfo = new DebtInstanceInfo(element, debt);
        debtReportingFactory.reportDebtInfo(debtInstanceInfo);
        debtCollector.collectDebt(element, debt);
        return debt.count();
    }

    @Override
    public synchronized void init(ProcessingEnvironment env) {
        debtProcessorProperties = new DebtProcessorProperties(env.getOptions());
        debtReportingProperties = createReportingPropertiesWrapperFromEnv(env);
        try {
            debtReportingFactory = createReportingFactoryFromProperties(debtReportingProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        debtCollector = new DebtCollector();
    }

    private DebtReportingFactory createReportingFactoryFromProperties(DebtReportingProperties debtReportingProperties) throws IOException {
        DebtReportingFactory factory = DebtReportingFactory.setupReportingFactory(debtReportingProperties);
        return factory;
    }

    private DebtReportingProperties createReportingPropertiesWrapperFromEnv(ProcessingEnvironment env) {
        DebtReportingProperties debtReportingProperties = new DebtReportingProperties(env.getOptions(), env.getMessager());
        debtReportingProperties.setDebtProcessingProperties(debtProcessorProperties);
        return debtReportingProperties;
    }

    @Override
    public Set<String> getSupportedOptions() {
        Set<String> options = new HashSet<String>();
        options.addAll(DebtProcessorProperties.getSupportedOptions());
        options.addAll(debtReportingFactory.getSupportedOptions());
        return options;
    }

    @Override
    public Iterable<? extends Completion> getCompletions(Element arg0, AnnotationMirror arg1,
                                                         ExecutableElement arg2, String arg3) {
        return super.getCompletions(arg0, arg1, arg2, arg3);
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return super.getSupportedAnnotationTypes();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }

    @Override
    protected synchronized boolean isInitialized() {
        return super.isInitialized();
    }

    protected void setDebtReportingFactory(DebtReportingFactory debtReportingFactory) {
        this.debtReportingFactory=debtReportingFactory;
    }

    protected void setDebtCollector(DebtCollector debtCollector) {
        this.debtCollector = debtCollector;
    }
}

