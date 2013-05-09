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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class DebtCollection {

    private int count;
    Map<String, PackageRecord> packageRecords = new HashMap<String, PackageRecord>();

    public int getCount() {
        return count;
    }

    public void incrementCount(int count) {
        this.count += count;
    }

    public Collection<PackageRecord> getValues() {
        return this.packageRecords.values();
    }

    public boolean containsKey(String s) {
        return this.packageRecords.containsKey(s);
    }

    public PackageRecord get(String s) {
        return this.packageRecords.get(s);
    }

    public void put(String s, PackageRecord packageRecord) {
        this.packageRecords.put(s, packageRecord);
    }

    public Map<String,PackageRecord> getPackageRecords() {
        return packageRecords;
    }

    public void recordPackageLevelDebt(PackageRecord packageRecord, Debt debt) {

    }


}
