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

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.StringUtils;

import org.xml.sax.SAXException;

import hudson.plugins.analysis.core.AbstractAnnotationParser;
import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.analysis.util.model.Priority;

/**
 * A parser for Debt XML files.
 *
 * @author Ulli Hafner
 * @author Andrew Harmel-Law
 */
public class DebtParser extends AbstractAnnotationParser {

    /** Unique ID of this class. */
    private static final long serialVersionUID = 2343264875763456345L;

    /** Debt priorities smaller than this value are mapped to {@link Priority#HIGH}. */
    private static final int DEBT_PRIORITY_MAPPED_TO_HIGH_PRIORITY = 3;
    /** Debt priorities greater than this value are mapped to {@link Priority#LOW}. */
    private static final int DEBT_PRIORITY_MAPPED_TO_LOW_PRIORITY = 4;

    /**
     * Creates a new instance of {@link DebtParser}.
     */
    public DebtParser() {
        super(StringUtils.EMPTY);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>In the simple DebtParser constructor.");
    }

    /**
     * Creates a new instance of {@link DebtParser}.
     *
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     */
    public DebtParser(final String defaultEncoding) {
        super(defaultEncoding);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>In the other DebtParser constructor.");
    }

    /** {@inheritDoc} */
    @Override
    public Collection<FileAnnotation> parse(final InputStream file, final String moduleName) throws InvocationTargetException {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>entered DebtParser.parse()");

        try {
            Digester digester = new Digester();
            digester.setValidating(false);
            digester.setClassLoader(DebtParser.class.getClassLoader());

            String rootXPath = "debt";
            digester.addObjectCreate(rootXPath, Debt.class);
            digester.addSetProperties(rootXPath);

            String fileXPath = "debt/file";
            digester.addObjectCreate(fileXPath, hudson.plugins.debt.parser.File.class);
            digester.addSetProperties(fileXPath);
            digester.addSetNext(fileXPath, "addFile", hudson.plugins.debt.parser.File.class.getName());

            String bugXPath = "debt/file/violation";
            digester.addObjectCreate(bugXPath, Violation.class);
            digester.addSetProperties(bugXPath);
            digester.addCallMethod(bugXPath, "setMessage", 0);
            digester.addSetNext(bugXPath, "addViolation", Violation.class.getName());

            Debt module = (Debt)digester.parse(file);
            if (module == null) {
                throw new SAXException("Input stream is not a valid Debt file.");
            }

            return convert(module, moduleName);
        }
        catch (IOException exception) {
            throw new InvocationTargetException(exception);
        }
        catch (SAXException exception) {
            throw new InvocationTargetException(exception);
        }
    }

    /**
     * Converts the internal structure to the annotations API.
     *
     * @param collection the internal maven module
     * @param moduleName name of the maven module
     * @return a maven module of the annotations API
     */
    private Collection<FileAnnotation> convert(final Debt collection, final String moduleName) {
        ArrayList<FileAnnotation> annotations = new ArrayList<FileAnnotation>();

        for (hudson.plugins.debt.parser.File file : collection.getFiles()) {
            for (Violation warning : file.getViolations()) {
                Priority priority;
                if (warning.getPriority() < DEBT_PRIORITY_MAPPED_TO_HIGH_PRIORITY) {
                    priority = Priority.HIGH;
                }
                else if (warning.getPriority() >  DEBT_PRIORITY_MAPPED_TO_LOW_PRIORITY) {
                    priority = Priority.LOW;
                }
                else {
                    priority = Priority.NORMAL;
                }

                DebtRecord record = new DebtRecord(priority, warning.getMessage() + ".", warning.getRuleset(), warning.getRule(),
                            warning.getBeginline(), warning.getEndline());
                record.setPackageName(warning.getPackage());
                record.setModuleName(moduleName);
                record.setFileName(file.getName());

                try {
                    record.setContextHashCode(createContextHashCode(file.getName(), warning.getBeginline()));
                }
                catch (IOException exception) {
                    // ignore and continue
                }

                annotations.add(record);
            }
        }
        return annotations;
    }
}

