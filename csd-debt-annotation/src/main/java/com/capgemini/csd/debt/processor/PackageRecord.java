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
import java.util.Set;

import javax.lang.model.element.Element;

import com.capgemini.csd.debt.Debt;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class PackageRecord extends SkeletonRecord {

    private Set<ClassRecord> classRecords;

    public PackageRecord(Element packageElement) {
        this.name = packageElement.toString();
        this.debtRecords = new HashSet<Debt>();
        this.classRecords = new HashSet<ClassRecord>();
        this.parent = null;
    }

    protected ClassRecord addClassRecord(Element classElement) {
        ClassRecord addedClassRecord = null;
        for (ClassRecord classRecord : classRecords) {
            if (classRecord.represents(classElement)) {
                addedClassRecord = classRecord;
            }
        }

        if (addedClassRecord == null) {
            addedClassRecord = new ClassRecord(classElement, this);
            classRecords.add(addedClassRecord);
        }

        return addedClassRecord;
    }

    protected boolean recordClassDebt(Element classElement, Debt debt) {
        ClassRecord classRecord = addClassRecord(classElement);
        return classRecord.recordDebt(debt);
    }

    protected boolean recordMethodDebt(Element methodElement, Debt debt) {
        ClassRecord classRecord = addClassRecord(methodElement.getEnclosingElement());
        boolean resultIndicator = classRecord.recordMethodDebt(methodElement, debt);
        return resultIndicator;
    }

    protected boolean recordFieldDebt(Element fieldElement, Debt debt) {
        ClassRecord classRecord = addClassRecord(fieldElement.getEnclosingElement());
        return classRecord.recordFieldDebt(fieldElement, debt);
    }

    public Set<ClassRecord> getClassRecords() {
        return classRecords;
    }

    public Set<Debt> getDebtRecords() {
        return debtRecords;
    }

    @Override
    public String getFileName() {
        return name.replace(".", "/") + "/package.java";
    }
}