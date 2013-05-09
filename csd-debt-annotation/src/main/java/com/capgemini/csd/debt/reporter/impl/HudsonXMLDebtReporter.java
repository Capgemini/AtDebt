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

import com.capgemini.csd.debt.Debt;
import com.capgemini.csd.debt.processor.*;

import java.util.Set;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class HudsonXMLDebtReporter {

    protected String generateXML(DebtCollection debtCollection, String version, String timestamp) {
        String xml =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        xml += String.format("<debt version=\"%s\" timestamp=\"%s\">", version, timestamp);
        if (containsPackageRecords(debtCollection)) {
            xml += processPackageRecords(debtCollection);
        }
        xml+= "</debt>";
        return xml;
    }

    private String processPackageRecords(DebtCollection debtCollection) {
        String xml = "";
        for (PackageRecord packageRecord : debtCollection.getPackageRecords().values()) {
            xml += processPackageRecord(packageRecord);
        }
        return xml;
    }

    private String processPackageRecord(PackageRecord packageRecord) {
        String xml = "";
        if (hasDebt(packageRecord)) {
            xml = "<file name=\"" + packageRecord.getFileName() + "\">";
            xml += createViolations(packageRecord);
            xml += "</file>";
        }
        xml += processClassRecords(packageRecord.getClassRecords());
        return xml;
    }

    private String processClassRecords(Set<ClassRecord> classRecords) {
        String xml = "";
        for (ClassRecord classRecord : classRecords) {
            xml += processClassRecord(classRecord);
        }
        return xml;
    }

    private String processClassRecord(ClassRecord classRecord) {
        String xml = "";
        if (hasDebt(classRecord)) {
            xml = "<file name=\"" + classRecord.getFileName() + "\">";
            xml += createViolations(classRecord);
            xml += "</file>";
        }
        xml += processFieldRecords(classRecord.getFieldRecords());
        xml += processMethodRecords(classRecord.getMethodRecords());
        return xml;
    }

    private String processFieldRecords(Set<FieldRecord> fieldRecords) {
        String xml = "";
        for (FieldRecord fieldRecord : fieldRecords) {
            xml += processFieldRecord(fieldRecord);
        }
        return xml;
    }

    private String processFieldRecord(FieldRecord fieldRecord) {
        String xml = "";
        if (hasDebt(fieldRecord)) {
            xml = "<file name=\"" + fieldRecord.getParentRecord().getFileName() + "\">";
            xml += createViolations(fieldRecord);
            xml += "</file>";
        }

        return xml;
    }

    private String processMethodRecords(Set<MethodRecord> methodRecords) {
        return "";
    }

    private String createViolations(PackageRecord packageRecord) {
        String xml = "";
        for (Debt debt : packageRecord.getDebtRecords()) {
            Violation v = new Violation(debt, packageRecord);
            xml += v.toXML();
        }
        return xml;
    }

    private String createViolations(ClassRecord classRecord) {
        String xml = "";
        for (Debt debt : classRecord.getDebtRecords()) {
//            xml += createViolation(debt, classRecord.getParentRecord().getName(), classRecord.getName());
            Violation v = new Violation(debt, classRecord);
            xml += v.toXML();
        }
        return xml;
    }

    private String createViolations(FieldRecord fieldRecord) {
        String xml = "";
        for (Debt debt : fieldRecord.getDebtRecords()) {
            xml += createViolation(debt, fieldRecord.getParentRecord().getParentRecord().getName(), fieldRecord.getParentRecord().getName(), fieldRecord.getName());
        }
        return xml;
    }

    private String createViolation(Debt debt, String packageName, String className) {
        String xml = "";
        for (int count = 0; count < debt.count() ; count++) {
            xml += "<violation rule=\"" + debt.desc() + "\" ruleset=\"Debt\" package=\"" + packageName + "\" class=\"" + className + "\"/>";
        }
        return xml;
    }

    private String createViolation(Debt debt, String packageName, String className, String fieldName) {
        String xml = "";
        for (int count = 0; count < debt.count() ; count++) {
            xml += "<violation rule=\"" + debt.desc() + "\" ruleset=\"Debt\" package=\"" + packageName + "\" class=\"" + className + "\" field=\"" + fieldName + "\" />";
        }
        return xml;
    }

    private boolean hasDebt(ClassRecord record) {
        return !record.getDebtRecords().isEmpty();
    }

    private boolean hasDebt(FieldRecord fieldRecord) {
        return !fieldRecord.getDebtRecords().isEmpty();
    }

    private boolean hasDebt(PackageRecord packageRecord) {
        return !packageRecord.getDebtRecords().isEmpty();
    }

    private boolean containsPackageRecords(DebtCollection debtCollection) {
        return !debtCollection.getPackageRecords().isEmpty();
    }

}
