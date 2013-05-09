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

import java.util.HashSet;
import java.util.Set;
import javax.lang.model.element.Element;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class ClassRecord extends SkeletonRecord {

    private Set<FieldRecord> fieldRecords;
    private Set<MethodRecord> methodRecords;

    protected ClassRecord(Element classElement, SkeletonRecord parent) {
        this.name = classElement.toString();
        this.debtRecords = new HashSet<Debt>();
        this.fieldRecords = new HashSet<FieldRecord>();
        this.methodRecords = new HashSet<MethodRecord>();
        this.parent = parent;
    }

    protected boolean recordMethodDebt(Element methodElement, Debt debt) {
        MethodRecord methodRecord = addMethodRecord(methodElement);
        boolean resultIndicator = methodRecord.recordDebt(debt);
        return resultIndicator;
    }

    protected boolean recordFieldDebt(Element fieldElement, Debt debt) {
        FieldRecord fieldRecord = addFieldRecord(fieldElement);
        return fieldRecord.recordDebt(debt);
    }

    protected FieldRecord addFieldRecord(Element fieldElement) {
        FieldRecord addedFieldRecord = null;

        for (FieldRecord fieldRecord : fieldRecords) {
            if (fieldRecord.represents(fieldElement)) {
                addedFieldRecord = fieldRecord;
            }
        }

        if (addedFieldRecord == null) {
            addedFieldRecord = new FieldRecord(fieldElement, this);
            fieldRecords.add(addedFieldRecord);
        }

        return addedFieldRecord;
    }

    // TODO: This can be made far simpler. Method records is a Set.
    protected MethodRecord addMethodRecord(Element methodElement) {
        MethodRecord methodRecordAdded = null;

        for (MethodRecord methodRecord : methodRecords) {
            if (methodRecord.represents(methodElement)) {
                methodRecordAdded = methodRecord;
            }
        }

        if (methodRecordAdded == null) {
            methodRecordAdded = new MethodRecord(methodElement, this);
            methodRecords.add(methodRecordAdded);
        }

        return methodRecordAdded;
    }

    public Set<MethodRecord> getMethodRecords() {
        return methodRecords;
    }

    public Set<Debt> getDebtRecords() {
        return debtRecords;
    }

    public Set<FieldRecord> getFieldRecords() {
        return fieldRecords;
    }

    @Override
    public String getFileName() {
        return name.replace(".", "/") + ".java";
    }
}