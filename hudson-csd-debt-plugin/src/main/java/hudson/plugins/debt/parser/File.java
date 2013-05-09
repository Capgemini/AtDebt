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
 *      http://kenai.com/projects/csdutilities
 */
package hudson.plugins.debt.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Java Bean class for a file of the Debt format.
 *
 * @author Ulli Hafner
 * @author Andrew Harmel-Law
 */
public class File {

    /** Name of the file. */
    private String name;
    /** All violations of this file. */
    private final List<Violation> violations = new ArrayList<Violation>();

    /**
     * Adds a new Debt Record to this file.
     *
     * @param violation the new debt record
     */
    public void addViolation(final Violation violation) {
        violations.add(violation);
    }

    /**
     * Returns all debt records of this file. The returned collection is
     * read-only.
     *
     * @return all debt records in this file
     */
    public Collection<Violation> getViolations() {
        return Collections.unmodifiableCollection(violations);
    }

    /**
     * Returns the name of this file.
     *
     * @return the name of this file
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this file to the specified value.
     *
     * @param name the value to set
     */
    public void setName(final String name) {
        this.name = name;
    }
}