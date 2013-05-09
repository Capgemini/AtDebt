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
package com.capgemini.csd.debt.reporter;

import com.capgemini.csd.debt.processor.DebtProcessorProperties;

import javax.annotation.processing.Messager;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class DebtReportingProperties implements Map<String,String> {

    private Map<String,String> options;
    private Messager messager;
    private DebtProcessorProperties debtProcessingProperties;

    public DebtReportingProperties(Map<String, String> options, Messager messager) {
        this.options = options;
        this.messager = messager;
    }

    public Messager getMessager() {
        return this.messager;
    }

    @Override
    public int size() {
        return options.size();
    }

    @Override
    public boolean isEmpty() {
        return options.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return options.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return options.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return options.get(key);
    }

    public String put(String key, String value) {
        return options.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return options.remove(key);
    }

    public void putAll(Map<? extends String, ? extends String> m) {
        options.putAll(m);
    }

    @Override
    public void clear() {
        options.clear();
    }

    @Override
    public Set<String> keySet() {
        return options.keySet();
    }

    @Override
    public Collection<String> values() {
        return options.values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return options.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return options.equals(o);
    }

    @Override
    public int hashCode() {
        return options.hashCode();
    }

    public void setMessager(Messager messager) {
        this.messager = messager;
    }

    public DebtProcessorProperties getDebtProcessingProperties() {
        return debtProcessingProperties;
    }

    public void setDebtProcessingProperties(DebtProcessorProperties debtProcessingProperties) {
        this.debtProcessingProperties = debtProcessingProperties;
    }

}
