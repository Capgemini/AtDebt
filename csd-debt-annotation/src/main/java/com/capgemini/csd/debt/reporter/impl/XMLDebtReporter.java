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
import com.capgemini.csd.debt.processor.ClassRecord;
import com.capgemini.csd.debt.processor.DebtCollection;
import com.capgemini.csd.debt.processor.DebtInstanceInfo;
import com.capgemini.csd.debt.processor.FieldRecord;
import com.capgemini.csd.debt.processor.MethodRecord;
import com.capgemini.csd.debt.processor.PackageRecord;
import com.capgemini.csd.debt.reporter.DebtReportingProperties;
import com.capgemini.csd.debt.reporter.Reporter;

import javax.tools.Diagnostic;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Andrew Harmel-Law
 * @author Rob Horn
 */
public class XMLDebtReporter extends AbstractFileBasedReporter {


    protected XMLDebtReporter() {
    }

    public XMLDebtReporter(Map<String, String> properties) throws IOException {
        super(properties);
        init();
    }

    @Override
    public void report(DebtCollection debtCollection, DebtReportingProperties debtReportingProperties) {

        try {
            writer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.append("<debt version=\"" + version + "\" timestamp=\"" + new Date().toString() + "\">\n");
            for (PackageRecord packageRecord : debtCollection.getValues()) {
                writePackageRecords(packageRecord);
            }
            writer.append("</debt>");
        } catch (IOException e) {
            debtReportingProperties.getMessager().printMessage(Diagnostic.Kind.ERROR, "[ERROR] Exception thrown writing to the report file:");
            debtReportingProperties.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                debtReportingProperties.getMessager().printMessage(Diagnostic.Kind.ERROR, "[ERROR] Exception thrown closing the file.");
                debtReportingProperties.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            }
        }
    }

    private void writePackageRecords(PackageRecord packageRecord) throws IOException {
        writer.append("\t<package name=\"" + forXML(packageRecord.getName()) + "\">\n");
        for (Debt debt : packageRecord.getDebtRecords()) {
            writer.append("\t\t<debtRecord desc=\"" + forXML(debt.desc()) + "\" count=\"" + debt.count() + "\" />\n");
        }
        for (ClassRecord classRecord : packageRecord.getClassRecords()) {
            writeClassRecords(classRecord);
        }
        writer.append("\t</package>\n");
    }

    private void writeClassRecords(ClassRecord classRecord) throws IOException {
        writer.append("\t\t<class name=\"" + forXML(classRecord.getName()) + "\">\n");
        for (Debt debt : classRecord.getDebtRecords()) {
            writer.append("\t\t\t<debtRecord desc=\"" + forXML(debt.desc()) + "\" count=\"" + debt.count() + "\" />\n");
        }
        for (FieldRecord fieldRecord : classRecord.getFieldRecords()) {
            writeFieldRecord(fieldRecord);
        }
        for (MethodRecord methodRecord : classRecord.getMethodRecords()) {
            writeMethodRecord(methodRecord);
        }
        writer.append("\t\t</class>\n");
    }

    private void writeMethodRecord(MethodRecord record) throws IOException {
        writer.append("\t\t\t<method name=\"" + forXML(record.getName()) + "\">\n");
        for (Debt debt : record.getDebtRecords()) {
            writer.append("\t\t\t\t<debtRecord desc=\"" + forXML(debt.desc()) + "\" count=\"" + debt.count() + "\" />\n");
        }
        writer.append("\t\t\t</method>\n");
    }

    private void writeFieldRecord(FieldRecord record) throws IOException {
        writer.append("\t\t\t<field name=\"" + forXML(record.getName()) + "\">\n");
        for (Debt debt : record.getDebtRecords()) {
            writer.append("\t\t\t\t<debtRecord desc=\"" + forXML(debt.desc()) + "\" count=\"" + debt.count() + "\" />\n");
        }
        writer.append("\t\t\t</field>\n");
    }

    @Override
    public void reportDebtInfo(DebtInstanceInfo debtInstanceInfo) {
        // This reporter does not report individual debts, so not implemented
    }

    /**
     * <P><B>NOTE</B>Taken from:
     * http://www.javapractices.com/topic/TopicAction.do?Id=96
     * <p/>
     * <P>Escape characters for text appearing as XML data, between tags.
     * <p/>
     * <P>The following characters are replaced with corresponding character entities :
     * <table border='1' cellpadding='3' cellspacing='0'>
     * <tr><th> Character </th><th> Encoding </th></tr>
     * <tr><td> < </td><td> &lt; </td></tr>
     * <tr><td> > </td><td> &gt; </td></tr>
     * <tr><td> & </td><td> &amp; </td></tr>
     * <tr><td> " </td><td> &quot;</td></tr>
     * <tr><td> ' </td><td> &#039;</td></tr>
     * </table>
     * <p/>
     * <P>Note that JSTL's {@code <c:out>} escapes the exact same set of
     * characters as this method. <span class='highlight'>That is, {@code <c:out>}
     * is good for escaping to produce valid XML, but not for producing safe
     * HTML.</span>
     */
    public static String forXML(String aText) {
        final StringBuilder result = new StringBuilder();
        final StringCharacterIterator iterator = new StringCharacterIterator(aText);
        char character = iterator.current();
        while (character != CharacterIterator.DONE) {
            if (character == '<') {
                result.append("&lt;");
            } else if (character == '>') {
                result.append("&gt;");
            } else if (character == '\"') {
                result.append("&quot;");
            } else if (character == '\'') {
                result.append("&#039;");
            } else if (character == '&') {
                result.append("&amp;");
            } else {
                //the char is not a special one
                //add it to the result as is
                result.append(character);
            }
            character = iterator.next();
        }
        return result.toString();
    }

}
