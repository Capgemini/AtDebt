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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public class MethodRecord extends SkeletonRecord {

    protected MethodRecord(Element methodElement, SkeletonRecord parent) {
        this.name = methodElement.toString();
        this.debtRecords = new HashSet<Debt>();
        this.parent = parent;
    }

    public Set<Debt> getDebtRecords() {
        return debtRecords;
    }

    @Override
    public String getFileName() {
        return "need to implement this";
    }
}