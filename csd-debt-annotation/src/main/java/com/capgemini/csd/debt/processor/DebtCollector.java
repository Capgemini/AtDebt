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

import java.util.Map;

import javax.lang.model.element.Element;

import com.capgemini.csd.debt.Debt;

/**
 * This component collects debt instances as they occur, storing them in a 
 * composite structure, ready to be output as an XML file when required.
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
*/
public class DebtCollector {

    private DebtCollection debtCollection = new DebtCollection();

    /**
     * Records the Code Element and the Debt associated with it.  
     * If the Code element is not already in the Map of package records it will 
     * be added. The Debt will then be recorded against this element.
     * @param element The Code element to record the debt against
     * @param debt the debt item
     * @return <code>true</code> if the debt is successfully recorded against 
     * the provided element.
     */
    public boolean collectDebt(Element element, Debt debt) {
        debtCollection.incrementCount(debt.count());

        switch (element.getKind()) {
            case PACKAGE     :
                return createOrReturnPackageRecordForElement(element)
                        .recordDebt(debt);
            case CLASS       :
                return createOrReturnPackageRecordForElement(element.getEnclosingElement())
                        .recordClassDebt(element, debt);
            case CONSTRUCTOR :
            case METHOD      :
                return createOrReturnPackageRecordForElement(element.getEnclosingElement().getEnclosingElement())
                        .recordMethodDebt(element, debt);
            case FIELD       :
                return createOrReturnPackageRecordForElement(element.getEnclosingElement().getEnclosingElement())
                        .recordFieldDebt(element, debt);
            default:
                return false;
        }
    }

    private PackageRecord createOrReturnPackageRecordForElement(Element packageElement) {
        Map<String, PackageRecord> packageRecords = debtCollection.getPackageRecords();
        if (packageRecords.containsKey(packageElement.toString())) {
            return packageRecords.get(packageElement.toString());
        } else {
            PackageRecord newPackage = new PackageRecord(packageElement);
            packageRecords.put(packageElement.toString(), newPackage);
            return newPackage;
        }
    }

    public DebtCollection getDebtCollection() {
        return debtCollection;
    }
}
