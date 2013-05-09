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
 * Java Bean class for a violations collection of the Debt format.
 *
 * @author Ulli Hafner
 * @author Andrew Harmel-Law
 */
public class Debt {
    /** All files of this violations collection. */
    private final List<File> files = new ArrayList<File>();

    /**
     * Adds a new file to this debt collection.
     *
     * @param file the file to add
     */
    public void addFile(final File file) {
        files.add(file);
    }

    /**
     * Returns all files of this violations collection. The returned collection
     * is read-only.
     *
     * @return all files of this debt collection
     */
    public Collection<File> getFiles() {
        return Collections.unmodifiableCollection(files);
    }
}

